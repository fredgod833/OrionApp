import { Component, OnInit } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import PostInterface from "../model/post";
import { DatePipe, NgFor, NgIf} from "@angular/common";
import { Router, RouterModule, RouterOutlet } from "@angular/router";
import PostSelected from "./comments/selected.component";
import CreatePost from "./create/post.create";
import { PostService } from "../services/post.service";

@Component({
    selector: 'app-post',
    templateUrl: './post.component.html',
    styleUrls: ['post.component.scss'],
    standalone: true,
    providers: [DatePipe],
    imports: [MatCardModule, MatButtonModule, NgFor, NgIf, RouterOutlet, PostSelected, RouterModule, CreatePost]
})
export default class Post implements OnInit{

    //Initialize a list of posts
    post_list:PostInterface[] = [];

    //Stock list of post
    public postList = this.postService.getPostList();

    constructor(private postService: PostService, private router: Router){}

  //Initialization
  ngOnInit(): void {
    this.postList.subscribe((list: PostInterface[])=> list.map((posts: PostInterface)=>{ 
      this.post_list.push(posts);
    }))
  }

    //Change date format and return it
    formatPostDate(date: Date):string{
     const dateFromPost = new Date(date);
 
     const formattedDate = dateFromPost.toLocaleString(undefined, {day: '2-digit', month:'2-digit', year:'numeric'})

     return formattedDate;
    }

    //Get the right post and redirect to comments
    selectPost(post:PostInterface):Promise<boolean>{
      return this.router.navigate(['/comments', post]);
    }
  }