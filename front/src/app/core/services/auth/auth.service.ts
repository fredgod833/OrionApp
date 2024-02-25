import { Injectable, Signal, inject, signal } from '@angular/core';

import { WebStorage } from '@lephenix47/webstorage-utility';

import { CookieService } from '@lephenix47/cookies-utility';
import { BehaviorSubject, Observable, catchError, tap } from 'rxjs';
import { CookiesService } from '@core/services/cookies/cookies.service';
import { HttpClient } from '@angular/common/http';
import { RegisterRequest, LoginRequest } from '@core/types/auth.type';
import { UserInfo } from '@core/types/user.type';
import { ApiService } from '@core/services/api/api.service';

import { toObservable, toSignal } from '@angular/core/rxjs-interop';

@Injectable({
  providedIn: 'root',
})
export class AuthService extends ApiService {
  // private httpClient = inject(HttpClient);

  private API_PATHNAME: string = 'api/auth';

  public isLoading$ = new BehaviorSubject<boolean>(false);

  public hasError$ = new BehaviorSubject<boolean>(false);

  public userInfo$ = new BehaviorSubject<UserInfo | undefined>(undefined);

  public register(registerRequest: RegisterRequest): Observable<UserInfo> {
    this.isLoading$.next(true);

    return this.fetchPost<UserInfo>(
      `${this.API_PATHNAME}/register`,
      registerRequest
    ).pipe(
      tap((value) => {
        this.isLoading$.next(false);
        this.hasError$.next(false);

        this.userInfo$.next(value);
      }),
      catchError((err: any, caught: Observable<UserInfo>) => {
        this.isLoading$.next(false);
        this.hasError$.next(true);

        throw new Error(`An error occurred: ${err}`);
      })
    );
  }

  public login(loginRequest: LoginRequest): Observable<UserInfo> {
    this.isLoading$.next(true);

    return this.fetchPost<UserInfo>(
      `${this.API_PATHNAME}/login`,
      loginRequest
    ).pipe(
      tap((value) => {
        this.isLoading$.next(false);
        this.hasError$.next(false);

        this.userInfo$.next(value);
      }),
      catchError((err: any, caught: Observable<UserInfo>) => {
        this.isLoading$.next(false);
        this.hasError$.next(true);

        throw new Error(`An error occurred: ${err}`);
      })
    );
  }
}
