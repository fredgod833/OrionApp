import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from 'src/app/interfaces/post.interface'
import { Comment } from 'src/app/interfaces/comment.interface';
import { PostsService } from 'src/app/services/post.service';
import { CommentsService } from 'src/app/services/comments.service';

@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.scss']
})
export class PostDetailsComponent {
  public hide = true;
  public onError = false;

  public postId?: number;
  post?: Post;
  comments?: Comment[];

  public form;

  constructor(
    private postService: PostsService,
    private commentService: CommentsService,
    private router: Router,
    private fb: FormBuilder,
    private route: ActivatedRoute
  ) {
    this.form = this.fb.group({
      content: [
        '',
        [Validators.required, Validators.min(3), Validators.max(500)],
      ],
    });
    try {
      this.postId = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    } catch (error) {
      console.error('Error parsing article ID:', error);
    }
  }

  public ngOnInit(): void {
    if (!this.postId) {
      this.router.navigate(['/posts']);
      return;
    }
    this.postService.getPost(this.postId).subscribe({
      next: (post: Post) => {
        this.post = post;
      },
    });
    this.commentService.getCommentsByPost(this.postId).subscribe({
      next: (comments: Comment[]) => {
        this.comments = comments;
      },
    });
  }
  public submit(): void {
    if (this.form.valid && this.postId) {
      const commentRequest: Partial<Comment> = {
        post: {
          id: this.postId,
          title: '',
        },
        content: this.form.value.content as string,
      };
      this.commentService.createComment(commentRequest).subscribe({
        next: (response: Comment) => {
          this.commentService.getCommentsByPost(this.postId as number).subscribe({
            next: (comments: Comment[]) => {
              this.comments = comments;
            },
          });
          this.form.reset();
        },
        error: (_) => (this.onError = true),
      });
    }
  }
  public back(): void {
    this.router.navigate(['/posts']);
  }
}
