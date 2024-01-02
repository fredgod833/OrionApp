import { Component, OnDestroy, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatSelectModule } from "@angular/material/select";
import { ActivatedRoute, Router, RouterModule } from "@angular/router";
import PostInterface from "../../model/post";
import { UserService } from "../../services/user.service";
import menuBar from "src/app/components/menu.component";
import { Subscription } from "rxjs";
import { Location, NgFor, NgIf } from "@angular/common";
import Comments from "../../model/comments";
import AuthService from "../../services/auth.component";
import { PostService } from "../../services/post.service";

@Component({
    selector: 'app-post-selected',
    templateUrl:'./selected.component.html',
    styleUrls: ['./selected.component.scss'],
    standalone: true,
    imports: [FormsModule, MatFormFieldModule, MatInputModule, RouterModule, MatSelectModule, menuBar, NgFor, NgIf],
})
export default class PostSelected implements OnInit, OnDestroy{

    //Stock data from params
    public post!:any;
    //Get data to comments
    entryComments!:string;
    author!: string;

    //Control comments action
    public isComment:boolean = false;
    //Property stocks subscription
    public subscription!: Subscription;
    
    //Collect comments from template
    public comments!:Comments;

    //Show list of comments
    public commentList:Comments[] = new Array();

    constructor(private router:ActivatedRoute, 
         private userService: UserService, 
         private navigate: Router,
         private location: Location, 
         private authService: AuthService,
         private postService: PostService){}

    //Initialization
    ngOnInit(): void {
        this.router.params.subscribe(params => {
            //Stock data from params post
          this.post = params;
            
        },)
       this.authService.me().subscribe({
            next:(value)=> {
                this.author = value.username
                return this.author;
            },
        })

        this.getPostComments()
    }

    //Comment a post and return a message
    comment(post: PostInterface):Subscription{

       //copy object
        const postCopied = {...post,
        comments:Array.isArray(post.comments) ? [...post.comments] : []};

        this.comments={
            comment: this.entryComments,
            author: this.author
        }
       console.log("LOGS: ", this.comments)
        this.subscription = this.userService.newComment(this.comments, postCopied.id_post).subscribe({
            next:()=> {
                this.isComment = true;
                setTimeout(()=>{
                    window.location.reload();
                }, 2000)
            },
            error(err) {
                console.log(err);
            },
        })
        
        return this.subscription;
    }

    public getPostComments(): Subscription{
        console.log(this.post.id_post);
        console.log("Comment list: ", this.commentList);

        this.subscription = this.postService.getPostList().subscribe({
            next:(value)=> {
                value.map(posts => {
                    if(posts.id_post == this.post.id_post){
                        posts.comments.map(comments => {
                            this.commentList.push(comments);
                            
                            return this.commentList;
                        })
                    }
                })
            },
        })
        return this.subscription;
    }

    //Unsubscribe subscriptions
    ngOnDestroy():void{
        if(this.subscription){
            this.subscription.unsubscribe();
        }
    }

    //Redirect menu for navigation
    navigateMenu():void{
        this.navigate.navigate(['menu'])
    }

    //Redirect route though arrow left
    arrowLeftDirection():void{
        this.location.back();
    }
}