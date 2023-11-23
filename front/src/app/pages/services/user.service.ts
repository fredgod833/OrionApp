import { Injectable } from "@angular/core";
import AuthService from "./auth.component";
import User from "src/app/interfaces/user.interface";
import { HttpClient } from "@angular/common/http";
import { SubjectService } from "./subject.service";

@Injectable({
providedIn: 'root'
})
export class UserService{

    public path = "api/user/subscribe";
    public user!:User;

    constructor(private authService: AuthService, public subjectService: SubjectService, private httpClient: HttpClient){}


    subscribe(idSubject: number){
      
      this.authService.me().subscribe({next:(val)=> {
        this.user = val;
      
      },complete:()=> {
    
        this.httpClient.post<User>(`${this.path}/${this.user.id_user}/${idSubject}`, {}).subscribe({
          next() {
            return "Vous êtes inscrit";
          },
        })
       
    
      },})

    }
    
}