import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { PostService } from '../../services/post.service';
import { Post } from '../../interfaces/post.interface';

@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.scss']
})
export class PostDetailsComponent implements OnInit {
  post?: Post;

  constructor(
    private route: ActivatedRoute,
    private postsService: PostService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      const id = params['id'];
      this.postsService.getPostById(id).subscribe((post: Post) => {
        this.post = post;
      }, error => {
        console.error('There was an error loading the post!', error);
      });
    });
  }
}
