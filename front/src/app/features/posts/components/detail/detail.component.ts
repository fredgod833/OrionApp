import {Component, OnDestroy, OnInit} from '@angular/core';
import {PostApiService} from "../../services/post-api.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Post} from "../../interfaces/post";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CommentApiService} from "../../services/comment-api.service";
import {Comment} from "../../interfaces/comment";
import {Subject, takeUntil} from "rxjs";

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit, OnDestroy {
  private destroy$: Subject<void> = new Subject<void>();
  private id!: number;

  public onError: boolean = false;
  public comments!: Comment[]
  public post!: Post;
  public form: FormGroup = this.fb.group({
    content: [
      null,
      [
        Validators.maxLength(500)
      ]
    ]
  });

  constructor(
    private postApiService: PostApiService,
    private route: ActivatedRoute,
    private router: Router,
    private commentApiService: CommentApiService,
    private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.postApiService.getById(this.id)
      .pipe(takeUntil(this.destroy$))
      .subscribe((post: Post) => {
        this.post = post;
      });
    this.commentApiService.getComments(this.id)
      .pipe(takeUntil(this.destroy$))
      .subscribe((comments: Comment[]) => {
        this.comments = comments;
      })
  }

  public back(): void {
    this.router.navigate(['/posts']).then(() => console.log("Navigated to posts page"));
  }

  public submit(): void {
    this.commentApiService.saveComment(this.id, this.form)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (comment: Comment) => {
          this.comments.push(comment);
          this.form.reset();
        },
        error: _ => this.onError = true
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
