import { Injectable } from "@angular/core";
import AuthService from "./auth.component";
import User from "src/app/interfaces/user.interface";
import { HttpClient } from "@angular/common/http";
import { SubjectService } from "./subject.service";
import PostInterface from "../model/post";
import { Observable } from "rxjs";

@Injectable({
providedIn: 'root'
})
export class UserService{

    public path = "api/user";
    public user!:User;

    constructor(private authService: AuthService, public subjectService: SubjectService, private httpClient: HttpClient){}


    subscribe(idSubject: number){
      
      this.authService.me().subscribe({next:(val)=> {
         
         this.httpClient.post<User>(`${this.path}/subscribe/${val.id_user}/${idSubject}`, {}).subscribe({
          next(value) {
            console.log("Inscrit !!!");
          },
         })
      }
      ,})

    }

    unsubscribe(idSubject: number){
        console.log("Unsubscribe", idSubject);
      this.authService.me().subscribe({
        next:(value)=> {

          this.httpClient.put<User>(`${this.path}/unsubscribe/${value.id_user}/${idSubject}`, {}).subscribe(
            {next(value) {
            console.log("Desinscrit !!!");
          },});
        },
      })
     
    
    
    }

    commentPost(post:PostInterface):Observable<PostInterface>{

      return this.httpClient.put<PostInterface>(`${this.path}/comments`, post);

    }

    changeUserUsernameAndEmail(user: User){
      return this.httpClient.put<User>(`${this.path}/change-user/username-email`, user);
    }
}