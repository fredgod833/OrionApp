import { Injectable, inject } from '@angular/core';

import { WebStorage } from '@lephenix47/webstorage-utility';

import { CookieService } from '@lephenix47/cookies-utility';
import { BehaviorSubject, Observable } from 'rxjs';
import { CookiesService } from '@core/services/cookies/cookies.service';
import { HttpClient } from '@angular/common/http';
import { RegisterRequest, LoginRequest } from '@core/types/auth.type';
import { UserInfo } from '@core/types/user.type';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private httpClient = inject(HttpClient);

  private API_PATHNAME: string = 'api/auth';

  public isLoading$ = new BehaviorSubject<boolean>(false);

  public isLoggedIn = false;

  public register(registerRequest: RegisterRequest): Observable<UserInfo> {
    return this.httpClient.post<UserInfo>(
      `${this.API_PATHNAME}/register`,
      registerRequest
    );
  }

  public login(loginRequest: LoginRequest): Observable<UserInfo> {
    return this.httpClient.post<UserInfo>(
      `${this.API_PATHNAME}/login`,
      loginRequest
    );
  }
}
