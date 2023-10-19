import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Token } from "../../security/interfaces/token.component";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";
import LoginRequest from "src/app/security/interfaces/login.component";
import User from "src/app/interfaces/user.interface";

@Injectable({
    providedIn: 'root'
})
export default class AuthService{

    private path = 'api/auth';

    constructor(private httpClient: HttpClient ){}

    public login(loginRequest: LoginRequest):Observable<Token>{
       return this.httpClient.post<Token>(`${this.path}/login`, loginRequest);
      
    }

    public me():Observable<User>{
        return this.httpClient.get<User>(`${this.path}/me`);
    }

}