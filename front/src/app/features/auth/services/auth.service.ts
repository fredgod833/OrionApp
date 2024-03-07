import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {LoginRequest} from '../interfaces/loginRequest.interface';
import {RegisterRequest} from '../interfaces/registerRequest.interface';
import {HttpClient} from "@angular/common/http";
import {User} from "../../../interfaces/user.interface";
import {AuthSuccess} from "../interfaces/authSuccess.interface";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = 'api/auth';

  constructor(private httpClient: HttpClient) {
  }

  public register(registerRequest: RegisterRequest): Observable<void> {
    return this.httpClient.post<void>(`${this.pathService}/register`, registerRequest);
  }

  public login(loginRequest: LoginRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(`${this.pathService}/login`, loginRequest);
  }

  public me(): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/me`);
  }
}
