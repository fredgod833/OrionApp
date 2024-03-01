import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, catchError, tap } from 'rxjs';
import { ApiService } from '../api/api.service';
import { UserBasicInfo, UserEntity } from '@core/types/user.type';
import { Message } from '@core/types/message.type';

@Injectable({
  providedIn: 'root',
})
export class UserService extends ApiService {
  private API_PATHNAME: string = 'api/user';

  public isLoading$ = new BehaviorSubject<boolean>(false);

  public errorMessage$ = new BehaviorSubject<string>('');

  public hasError$ = new BehaviorSubject<boolean>(false);

  constructor() {
    super();

    this.updateLoadingState = this.updateLoadingState.bind(this);
    this.handleErrors = this.handleErrors.bind(this);
  }

  public getUser(): Observable<UserEntity> {
    this.isLoading$.next(true);

    return this.fetchGet<UserEntity>(`${this.API_PATHNAME}`).pipe(
      tap(this.updateLoadingState),
      catchError(this.handleErrors)
    );
  }

  public updateUser(
    updatedUser: Omit<UserBasicInfo, 'id'>
  ): Observable<Message> {
    this.isLoading$.next(true);

    return this.fetchPut<Message>(`${this.API_PATHNAME}`, updatedUser).pipe(
      tap(this.updateLoadingState),
      catchError(this.handleErrors)
    );
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
