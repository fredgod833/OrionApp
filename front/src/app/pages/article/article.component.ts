import {
  Component,
  ElementRef,
  ViewChild,
  inject,
  signal,
} from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Article, UserComment } from '@core/types/article.type';
import { CommentsComponent } from '@components/common/article/comments/comments.component';
import { toSignal } from '@angular/core/rxjs-interop';
import { ArticleService } from '@core/services/article/article.service';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { SpinLoaderComponent } from '@components/shared/spin-loader/spin-loader.component';
import { Store } from '@ngrx/store';
import { UserBasicInfo } from '@core/types/user.type';
import { Message } from '@core/types/message.type';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-article',
  standalone: true,
  templateUrl: './article.component.html',
  styleUrl: './article.component.scss',
  imports: [
    RouterLink,
    CommentsComponent,
    SpinLoaderComponent,
    ReactiveFormsModule,
  ],
})
export class ArticleComponent {
  @ViewChild('commentTextareaRef')
  commentTextareaRef!: ElementRef<HTMLTextAreaElement>;

  private store = inject(Store);

  public userInfo = toSignal<UserBasicInfo>(this.store.select('userInfo'));

  private formBuilder = inject(FormBuilder);

  private articleService = inject(ArticleService);

  public isLoading = toSignal<boolean>(this.articleService.isLoading$);

  public isWaitingForCommentsResponse = toSignal<boolean>(
    this.articleService.isWaitingForCommentsResponse$
  );

  public hasError = toSignal<boolean>(this.articleService.hasError$);

  public hasCommentSubmissionError = toSignal<boolean>(
    this.articleService.hasCommentSubmissionError$
  );

  public errorMessage = toSignal<string>(this.articleService.errorMessage$);

  public hasSuccess = signal<boolean>(false);

  public activatedRoute = inject(ActivatedRoute);

  public id = Number(this.activatedRoute.snapshot.params['id']);

  public articleComments = signal<UserComment[]>([]);

  public currentArticle = signal<Omit<Article, 'comments'>>({
    id: this.id,
    authorName: '',
    title: '',
    description: '',
    theme: '',
    creationDate: '',
  });

  public commentCreationForm = this.formBuilder.group({
    comment: ['', [Validators.required]],
  });

  ngOnInit() {
    this.setArticle();
  }

  public onCommentSubmission(event: Event) {
    event.preventDefault();

    const { comment } = this.commentCreationForm.getRawValue();

    console.log('onCommentSubmission', comment);
    console.log(this.commentTextareaRef);

       const subscription: Subscription = this.articleService
      .createComment(this.id, { comment: comment as string })
      .subscribe((message: Message) => {
        const { username } = this.userInfo() as UserBasicInfo;

        const newComment = { username, comment } as UserComment;

        this.articleComments.update((previousComments) => {
          return [...previousComments, newComment];
        });

        this.resetTextAreaValue();

                  subscription.unsubscribe();

      });
  }

  private setArticle() {
      const subscription: Subscription =  this.articleService
      .getArticleById(this.id)
      .subscribe((article: Article) => {
        article.creationDate = new Date(
          article.creationDate
        ).toLocaleDateString();

        this.articleComments.update(() => {
          return article.comments;
        });

        this.currentArticle.update(() => {
          return article;
        });

                  subscription.unsubscribe();

      });
  }

  private resetTextAreaValue() {
    const textArea: HTMLTextAreaElement = this.commentTextareaRef.nativeElement;

    textArea.value = '';
  }
}
