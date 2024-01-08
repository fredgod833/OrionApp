import { HttpClient } from "@angular/common/http";
import { Token } from "../../security/interfaces/token.component";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";
import LoginRequest from "src/app/security/interfaces/login.component";
import User from "src/app/interfaces/user.interface";
import RegisterRequest from "src/app/security/interfaces/register.component";

@Injectable({
    providedIn: 'root'
})
export default class AuthService{

    private path = 'api/auth';

    constructor(private httpClient: HttpClient){}
    
    //User authentification and return a token
    public login(loginRequest: LoginRequest):Observable<Token>{
       return this.httpClient.post<Token>(`${this.path}/login`, loginRequest);
      
    }

    //User deconnection
    public logout():Observable<String>{
        return this.httpClient.post<String>(`${this.path}/logout`, {});
    }
    
    //User inscription
    public register(register: RegisterRequest): Observable<User>{
        return this.httpClient.post<User>(`${this.path}/register`, register);
    }

    //Authenticated user information
    public me():Observable<User>{
        const token = localStorage.getItem('token');
        return this.httpClient.get<User>(`${this.path}/me`, {headers:{Authorization: `Bearer ${token}`}});
    }

}