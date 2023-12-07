import { HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class JwtInterceptor implements HttpInterceptor{
    
    constructor(){}

    // Get token from storage and insert into requests
    public intercept(request: HttpRequest<any>, next: HttpHandler) {
        const token = localStorage.getItem('token');
        if(token){
            request = request.clone({
                setHeaders: {
                    Authorization: `Bearer ${token}`,
                }
            })
        }
        console.log(request);
        return next.handle(request);
    }
    
}