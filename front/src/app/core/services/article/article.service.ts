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

  public errorMessage$ = new BehaviorSubject<string>('');

  public hasError$ = new BehaviorSubject<boolean>(false);

  public getAllArticles(): Observable<ArticleSummary> {
    this.isLoading$.next(true);

    return this.fetchGet<ArticleSummary>(`${this.API_PATHNAME}/`).pipe(
      tap(this.updateLoadingState),
      catchError(this.handleErrors)
    );
  }

  public postArticle(
    themeId: number,
    newArticle: Pick<Article, 'title' | 'description'>
  ): Observable<Message> {
    this.isLoading$.next(true);

    return this.fetchPost<Pick<Article, 'title' | 'description'>>(
      // TODO: Improve the Api service → allow query params as arguments
      `${this.API_PATHNAME}/?themeId=${themeId}`,
      newArticle
    ).pipe(tap(this.updateLoadingState), catchError(this.handleErrors));
  }

  public getArticleById(articleId: number): Observable<Article> {
    this.isLoading$.next(true);

    return this.fetchGet<ArticleSummary>(
      // TODO: Improve the Api service → allow query params as arguments
      `${this.API_PATHNAME}/?articleId=${articleId}`
    ).pipe(tap(this.updateLoadingState), catchError(this.handleErrors));
  }

  public createComment(
    articleId: number,
    newComment: Omit<UserComment, 'username'>
  ): Observable<Message> {
    this.isLoading$.next(true);

    return this.fetchPost<Pick<Article, 'title' | 'description'>>(
      // TODO: Improve the Api service → allow query params as arguments
      `${this.API_PATHNAME}/comment?articleId=${articleId}`,
      newComment
    ).pipe(tap(this.updateLoadingState), catchError(this.handleErrors));
  }

  private updateLoadingState(value: any): void {
    this.isLoading$.next(false);
    this.hasError$.next(false);
  }

  private handleErrors(err: any, caught: any): never {
    this.isLoading$.next(false);
    this.hasError$.next(true);

    this.errorMessage$.next(err?.error.message);

    throw new Error(`An error occurred: ${err?.message}`);
  }
}
