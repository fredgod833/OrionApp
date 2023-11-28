import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequest } from 'src/app/interfaces/loginrequest';
import { RegisterRequest } from 'src/app/interfaces/registerrequest';
import { Session } from 'src/app/interfaces/session.interface';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private pathService = 'http://localhost:8080/api/auth';

  constructor(private httpClient: HttpClient) {}

  public register(registerRequest: RegisterRequest): Observable<void> {
    return this.httpClient.post<void>(
      `${this.pathService}/register`,
      registerRequest
    );
  }

  public login(loginRequest: LoginRequest): Observable<Session> {
    return this.httpClient.post<Session>(
      `${this.pathService}/login`,
      loginRequest
    );
  }

  public getPathService(): string {
    return this.pathService;
  }
}
