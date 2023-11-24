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
    post_list:PostInterface[] = [];

    public isActive =  false;

    public postList = this.postService.getPostList();

    constructor(private postService: PostService, private router: Router){
    }

  ngOnInit(): void {
    this.postList.subscribe((list: PostInterface[])=> list.map((posts: PostInterface)=>{ 
      this.post_list.push(posts);
    }))
  }

    formatPostDate(date: Date){
     const dateFromPost = new Date(date);
 
     const formattedDate = dateFromPost.toLocaleString(undefined, {day: '2-digit', month:'2-digit', year:'numeric'})

     return formattedDate;
    }

    selectPost(post:PostInterface){
      return this.router.navigate(['/comments', post]);
    }
  }