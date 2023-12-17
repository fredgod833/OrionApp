import { Component, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatSelectModule } from "@angular/material/select";
import { ActivatedRoute, Router, RouterModule } from "@angular/router";
import PostInterface from "../../model/post";
import { UserService } from "../../services/user.service";
import menuBar from "src/app/components/menu.component";

@Component({
    selector: 'app-post-selected',
    templateUrl:'./selected.component.html',
    styleUrls: ['./selected.component.scss'],
    standalone: true,
    imports: [FormsModule, MatFormFieldModule, MatInputModule, RouterModule, MatSelectModule, menuBar],
})
export default class PostSelected implements OnInit{

    public post!:any;
    
    //Collect comments from template
    public comment_text = "";

    constructor(private router:ActivatedRoute, private userService: UserService, private navigate: Router){}

    //Initialization
    ngOnInit(): void {
        this.router.params.subscribe(params => {
            //Stock data from params post
          this.post = params;
        })}

    //Comment a post and return a message
    comment(post: PostInterface){

       //copy object
       const postCopied = {...post};

       //set a value
       postCopied.comments = this.comment_text;

       this.userService.commentPost(postCopied).subscribe({
            next() {
                return "Post commentered !!!";
            },
        })
    }

    //Redirect menu for navigation
    navigateMenu(){
        return this.navigate.navigate(['menu'])
    }
}