import { Injectable } from '@angular/core';
import { ApiService } from '../api/api.service';
import {
  BehaviorSubject,
  Observable,
  catchError,
  combineLatest,
  map,
  tap,
} from 'rxjs';
import { Topic, TopicSubscription } from '@core/types/topic.type';
import { Message } from '@core/types/message.type';

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

  public getAllThemes(): Observable<Topic[]> {
    this.isLoading$.next(true);

    return this.fetchGet<Topic[]>(this.API_PATHNAME).pipe(
      tap(this.updateLoadingState),
      catchError(this.handleErrors)
    );
  }

  public getAllSubscribedThemes(): Observable<TopicSubscription[]> {
    this.isLoading$.next(true);

    return this.fetchGet<TopicSubscription[]>(
      `${this.API_PATHNAME}/subscribed`
    ).pipe(tap(this.updateLoadingState), catchError(this.handleErrors));
  }

  public getAllThemesWithSubscription(): Observable<Topic[]> {
    this.isLoading$.next(true);

    const themesObs: Observable<Topic[]> = this.getAllThemes();
    const subscribedIdsObs: Observable<TopicSubscription[]> =
      this.getAllSubscribedThemes();

    return combineLatest([themesObs, subscribedIdsObs]).pipe(
      map(([themes, subscribedIds]) => {
        console.log([themes, subscribedIds]);

        return themes.map((theme: Topic) => {
          const isSubscribed = subscribedIds.some((subscription) => {
            return (
              subscription.themeId === theme.id && subscription.isSubscribed
            );
            // Check if the subscription.themeId is present and isSubscribed is true
          });
          return {
            id: theme.id,
            title: theme.title,
            description: theme.description,
            isSubscribed,
          };
        });
      }),
      tap(this.updateLoadingState),
      catchError(this.handleErrors)
    );
  }

  public subscribeToTheme(themeId: number): Observable<Message> {
    this.isLoading$.next(true);

    const params = this.changeObjectParamsToArray({ themeId });

    return this.fetchPost<Message>(
      `${this.API_PATHNAME}/subscribe/`,
      null, // * Empty body
      params
    ).pipe(tap(this.updateLoadingState), catchError(this.handleErrors));
  }

  public unsubscribeToTheme(themeId: number): Observable<Message> {
    this.isLoading$.next(true);

    const params = this.changeObjectParamsToArray({ themeId });

    return this.fetchPost<Message>(
      `${this.API_PATHNAME}/unsubscribe/`,
      null, // * Empty body
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
