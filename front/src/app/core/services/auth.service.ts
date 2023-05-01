import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { BehaviorSubject, lastValueFrom, map } from 'rxjs';
import { RegisterRequest } from '../models/auth/requests/register-request.interface';
import { AuthentificationResponse } from '../models/auth/responses/authentification-response.interface';
import { LoginRequest } from '../models/auth/requests/login-request.interface';
import { UserInfo } from '../models/auth/user-info.interface';
import { MeRequest } from '../models/auth/requests/me-request.interface';
import { CookieService } from 'ngx-cookie-service';

@Injectable({providedIn: 'root'})
export class AuthService {

    private readonly BASE_ROUTE = 'auth';

    private readonly userInfo$ = new BehaviorSubject<UserInfo|null>(null);
    
    public readonly isLoggedIn$ = this.userInfo$.pipe(map(userInfo => !!userInfo));
    
    constructor(
        private api: ApiService,
        private cookie: CookieService
    ) {}

    private saveToken(token: string){
        this.cookie.set('token', token, 30, '/', '', false, 'Lax');
        return token;
    }

    private actionRoute(route: string){
        return `${this.BASE_ROUTE}/${route}`;
    }

    public getToken(){
        return this.cookie.get('token');
    }

    public async register(registerReq: RegisterRequest){
        const res = await lastValueFrom(this.api.post<AuthentificationResponse>(this.actionRoute('register'), registerReq));
        this.saveToken(res.token);
        return this.me();
    }

    public async login(loginReq: LoginRequest){
        const res = await lastValueFrom(this.api.post<AuthentificationResponse>(this.actionRoute('login'), loginReq));
        this.saveToken(res.token);
        return this.me();
    }

    private async me(): Promise<boolean>{
        const res = await lastValueFrom(this.api.get<MeRequest>(this.actionRoute('me')));
        this.userInfo$.next(res);
        return this.isLoggedIn;
    }

    public tryAutoLogin(){
        if(!this.getToken()){
            return Promise.resolve(false);
        }
        return this.me();
    }

    public get isLoggedIn(){
        return !!this.userInfo$.value;
    }

    public get userData(){
        return this.userInfo$.value;
    }


    
}