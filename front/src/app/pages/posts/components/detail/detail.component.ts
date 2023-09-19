import { Component, OnInit } from '@angular/core';
import {Post} from "../../interfaces/post.interface";
import {PostsService} from "../../services/posts.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {
  public post! : Post;

  constructor(
    private postsService: PostsService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const postId: number = this.route.snapshot.params['id'];
    this.postsService.getPost(postId)
      .subscribe(
        (post: Post) => {
          console.log(post)
          this.post = post
        })
  }






}
