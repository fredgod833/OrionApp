import { Component } from '@angular/core';
import {PostService} from "../../services/post.service";
import {Post} from "../../interfaces/post.interface";
import {Commentary} from "../../interfaces/comment.interface";

@Component({
  selector: 'app-posts-reader',
  templateUrl: './posts-reader.component.html',
  styleUrls: ['./posts-reader.component.scss']
})
export class PostsReaderComponent {

  post? : Post;
  postService : PostService;

  constructor(postService : PostService) {
    this.postService = postService;
    this.post = postService.getSelectedPost();
  }

  public getContent() : string {

    if (this.post !== undefined && this.post?.content !== undefined) {
      return this.post.content.replace(/(\r\n|\r|\n)/g, '<br>');

    } else {
      return '';

    }

  }

}
