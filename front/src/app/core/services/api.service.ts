import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({providedIn: 'root'})
export class ApiService {

    private readonly baseUrl = 'http://localhost:8080';
    
    constructor(
        private http: HttpClient
    ) {}

    private createUrl(route: string): string {
        return `${this.baseUrl}/api/${route}`;
    }

    public post<T = unknown>(route: string, body: unknown): Observable<T> {
        return this.http.post<T>(this.createUrl(route), body);
    }

    public get<T = unknown>(route: string): Observable<T> {
        return this.http.get<T>(this.createUrl(route));
    }
    
}