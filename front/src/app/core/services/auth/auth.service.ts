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

  public userInfo$ = new BehaviorSubject<UserInfo | undefined>(undefined);

  constructor() {
    super();

    this.updateUserInfo = this.updateUserInfo.bind(this);
    this.handleErrors = this.handleErrors.bind(this);
  }

  public register(registerRequest: RegisterRequest): Observable<UserInfo> {
    this.isLoading$.next(true);

    return this.fetchPost<UserInfo>(
      `${this.API_PATHNAME}/register`,
      registerRequest
    ).pipe(tap(this.updateUserInfo), catchError(this.handleErrors));
  }

  public login(loginRequest: LoginRequest): Observable<UserInfo> {
    this.isLoading$.next(true);

    return this.fetchPost<UserInfo>(
      `${this.API_PATHNAME}/login`,
      loginRequest
    ).pipe(tap(this.updateUserInfo), catchError(this.handleErrors));
  }

  private updateUserInfo(value: Readonly<UserInfo>): void {
    this.isLoading$.next(false);
    this.hasError$.next(false);

    this.userInfo$.next(value);
  }

  private handleErrors(err: any, caught: Observable<UserInfo>): never {
    console.log({ err });

    this.isLoading$.next(false);
    this.hasError$.next(true);

    this.errorMessage$.next(err?.error.message);

    throw new Error(`An error occurred: ${err?.message}`);
  }
}
