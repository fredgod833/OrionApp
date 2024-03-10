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

  constructor(private http: HttpClient) {
  }

  public register(registerRequest: RegisterRequest): Observable<void> {
    return this.http.post<void>(`${this.pathService}/register`, registerRequest);
  }

  public login(loginRequest: LoginRequest): Observable<AuthSuccess> {
    return this.http.post<AuthSuccess>(`${this.pathService}/login`, loginRequest);
  }

  public me(): Observable<User> {
    return this.http.get<User>(`${this.pathService}/me`);
  }
}
