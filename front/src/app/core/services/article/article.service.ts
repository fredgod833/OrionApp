import { Injectable } from '@angular/core';
import { ApiService } from '../api/api.service';
import { BehaviorSubject, Observable, catchError, tap } from 'rxjs';
import { Article, ArticleSummary, UserComment } from '@core/types/article.type';
import { Message } from '@core/types/message.type';

@Injectable({
  providedIn: 'root',
})
export class ArticleService extends ApiService {
  private API_PATHNAME: string = 'api/articles';

  public isLoading$ = new BehaviorSubject<boolean>(false);

  public isWaitingForCommentsResponse$ = new BehaviorSubject<boolean>(false);
  public hasCommentSubmissionError$ = new BehaviorSubject<boolean>(false);

  public errorMessage$ = new BehaviorSubject<string>('');

  public hasError$ = new BehaviorSubject<boolean>(false);

  constructor() {
    super();

    this.updateCommentLoading = this.updateCommentLoading.bind(this);
    this.handleCommentSubmissionErrors =
      this.handleCommentSubmissionErrors.bind(this);

    this.updateLoadingState = this.updateLoadingState.bind(this);
    this.handleErrors = this.handleErrors.bind(this);
  }

  public getAllArticles(): Observable<{ articles: Array<ArticleSummary> }> {
    this.isLoading$.next(true);

    return this.fetchGet<{ articles: Array<ArticleSummary> }>(
      `${this.API_PATHNAME}`
    ).pipe(tap(this.updateLoadingState), catchError(this.handleErrors));
  }

  public postArticle(
    themeId: number,
    newArticle: Pick<Article, 'title' | 'description'>
  ): Observable<Message> {
    this.isLoading$.next(true);

    const params = this.changeObjectParamsToArray({ themeId });

    return this.fetchPost<Pick<Article, 'title' | 'description'>>(
      `${this.API_PATHNAME}/`,
      newArticle,
      params
    ).pipe(tap(this.updateLoadingState), catchError(this.handleErrors));
  }

  public getArticleById(articleId: number): Observable<Article> {
    this.isLoading$.next(true);

    const params = this.changeObjectParamsToArray({ articleId });

    return this.fetchGet<ArticleSummary>(`${this.API_PATHNAME}/`, params).pipe(
      tap(this.updateLoadingState),
      catchError(this.handleErrors)
    );
  }

  public createComment(
    articleId: number,
    newComment: Omit<UserComment, 'username'>
  ): Observable<Message> {
    this.isWaitingForCommentsResponse$.next(true);

    const params = this.changeObjectParamsToArray({ articleId });

    return this.fetchPost<Pick<Article, 'title' | 'description'>>(
      `${this.API_PATHNAME}/comment/`,
      newComment,
      params
    ).pipe(
      tap(this.updateCommentLoading),
      catchError(this.handleCommentSubmissionErrors)
    );
  }

  private updateCommentLoading(value: any): void {
    this.isWaitingForCommentsResponse$.next(false);
    this.hasCommentSubmissionError$.next(false);
  }

  private updateLoadingState(value: any): void {
    this.isLoading$.next(false);
    this.hasError$.next(false);
  }

  private handleCommentSubmissionErrors(err: any, caught: any): never {
    this.hasCommentSubmissionError$.next(true);

    this.isWaitingForCommentsResponse$.next(false);

    this.errorMessage$.next(err?.error?.message);

    throw new Error(`An error occurred: ${err?.message}`);
  }

  private handleErrors(err: any, caught: any): never {
    this.isLoading$.next(false);
    this.hasError$.next(true);

    this.isWaitingForCommentsResponse$.next(false);

    this.errorMessage$.next(err?.error?.message);

    throw new Error(`An error occurred: ${err?.message}`);
  }
}
