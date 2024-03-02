import { Injectable } from '@angular/core';
import { ApiService } from '../api/api.service';
import { BehaviorSubject, Observable, catchError, tap } from 'rxjs';
import { Topic } from '@core/types/topic.type';

@Injectable({
  providedIn: 'root',
})
export class TopicService extends ApiService {
  private API_PATHNAME: string = 'api/themes';

  public isLoading$ = new BehaviorSubject<boolean>(false);

  public errorMessage$ = new BehaviorSubject<string>('');

  public hasError$ = new BehaviorSubject<boolean>(false);

  constructor() {
    super();

    this.updateLoadingState = this.updateLoadingState.bind(this);
    this.handleErrors = this.handleErrors.bind(this);
  }

  public getAllThemes(): Observable<Array<Topic>> {
    this.isLoading$.next(true);

    return this.fetchGet<Array<Topic>>(`${this.API_PATHNAME}`).pipe(
      tap(this.updateLoadingState),
      catchError(this.handleErrors)
    );
  }

  public getAllSubscribedThemes(): Observable<Array<Topic>> {
    this.isLoading$.next(true);

    return this.fetchGet<Array<Topic>>(`${this.API_PATHNAME}/subscribed`).pipe(
      tap(this.updateLoadingState),
      catchError(this.handleErrors)
    );
  }

  public subscribeToTheme(themeId: number): Observable<Array<Topic>> {
    this.isLoading$.next(true);

    const params = this.changeObjectParamsToArray({ themeId });

    return this.fetchPost<Array<Topic>>(
      `${this.API_PATHNAME}`,
      null,
      params
    ).pipe(tap(this.updateLoadingState), catchError(this.handleErrors));
  }

  public unsubscribeToTheme(themeId: number): Observable<Array<Topic>> {
    this.isLoading$.next(true);

    const params = this.changeObjectParamsToArray({ themeId });

    return this.fetchPost<Array<Topic>>(
      `${this.API_PATHNAME}`,
      null,
      params
    ).pipe(tap(this.updateLoadingState), catchError(this.handleErrors));
  }

  private updateLoadingState(value: any): void {
    this.isLoading$.next(false);
    this.hasError$.next(false);
  }

  private handleErrors(err: any, caught: any): never {
    this.isLoading$.next(false);
    this.hasError$.next(true);

    this.errorMessage$.next(err?.error?.message);

    throw new Error(`An error occurred: ${err?.message}`);
  }
}
