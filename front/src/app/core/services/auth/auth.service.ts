import { Injectable } from '@angular/core';

import { BehaviorSubject, Observable, catchError, tap } from 'rxjs';
import { RegisterRequest, LoginRequest } from '@core/types/auth.type';
import { UserInfo } from '@core/types/user.type';
import { ApiService } from '@core/services/api/api.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService extends ApiService {
  private API_PATHNAME: string = 'api/auth';

  public isLoading$ = new BehaviorSubject<boolean>(false);

  public errorMessage$ = new BehaviorSubject<string>('');

  public hasError$ = new BehaviorSubject<boolean>(false);

  constructor() {
    super();

    this.updateLoadingState = this.updateLoadingState.bind(this);
    this.handleErrors = this.handleErrors.bind(this);
  }

  public register(registerRequest: RegisterRequest): Observable<UserInfo> {
    this.isLoading$.next(true);

    return this.fetchPost<UserInfo>(
      `${this.API_PATHNAME}/register`,
      registerRequest
    ).pipe(tap(this.updateLoadingState), catchError(this.handleErrors));
  }

  public login(loginRequest: LoginRequest): Observable<UserInfo> {
    this.isLoading$.next(true);

    return this.fetchPost<UserInfo>(
      `${this.API_PATHNAME}/login`,
      loginRequest
    ).pipe(tap(this.updateLoadingState), catchError(this.handleErrors));
  }

  private updateLoadingState(value: Readonly<UserInfo>): void {
    this.isLoading$.next(false);
    this.hasError$.next(false);
  }

  private handleErrors(err: any, caught: Observable<UserInfo>): never {
    this.isLoading$.next(false);
    this.hasError$.next(true);

    this.errorMessage$.next(err?.error?.message);

    throw new Error(`An error occurred: ${err?.message}`);
  }
}