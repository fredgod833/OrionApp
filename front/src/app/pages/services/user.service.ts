import { Injectable } from "@angular/core";
import AuthService from "./auth.component";
import User from "src/app/interfaces/user.interface";
import { HttpClient } from "@angular/common/http";
import { SubjectService } from "./subject.service";
import PostInterface from "../model/post";
import { Observable } from "rxjs";
import Comments from "../model/comments";

@Injectable({
providedIn: 'root'
})
export class UserService{

    public path = "api/user";

    constructor(private authService: AuthService, public subjectService: SubjectService, private httpClient: HttpClient){}

    //Persist user subscription
    subscribe(idSubject: number):void{
      
      //User authenticated
      this.authService.me().subscribe({next:(val)=> {
         
        //Add new subject to subscription list
         this.httpClient.post<User>(`${this.path}/subscribe/${val.id_user}/${idSubject}`, {}).subscribe({
          next() {
            console.log("Inscrit !!!");
          },
         })
        }
      ,})

    }

    //Persist user unsubscription
    unsubscribe(idSubject: number):void{

      //User authenticated
      this.authService.me().subscribe({
        next:(value)=> {
          //Update subscription 
          this.httpClient.put<User>(`${this.path}/unsubscribe/${value.id_user}/${idSubject}`, {}).subscribe(
            {next() {
            console.log("Desinscrit !!!");
          },});
        },
      })
    }

    //Persist a post comment
    commentPost(post:PostInterface):Observable<PostInterface>{
      return this.httpClient.put<PostInterface>(`${this.path}/comments`, post);
    }

    //Persist new user username and email
    changeUserUsernameAndEmail(user: User):Observable<User>{
      return this.httpClient.put<User>(`${this.path}/change-user/username-email`, user);
    }

    // THIS IS A TEST
    newComment(comments: Comments, post_id:number):Observable<Comments>{
      return this.httpClient.post<Comments>(`${this.path}/comment/${post_id}`, {comment: comments.comment, author: comments.author});
    }
}