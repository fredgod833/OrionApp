import { Injectable } from "@angular/core";
import AuthService from "./auth.component";
import User from "src/app/interfaces/user.interface";
import { HttpClient } from "@angular/common/http";
import { SubjectService } from "./subject.service";
import PostInterface from "../model/post";
import { Observable } from "rxjs";
import Comments from "../model/comments";
import { SubjectDto } from "../model/subjectdto";

@Injectable({
providedIn: 'root'
})
export class UserService{

    public path = "api/user";
    constructor(private authService: AuthService, public subjectService: SubjectService, private httpClient: HttpClient){}

    //Persist user subscription
    subscribe(subject: SubjectDto, id_user:number | undefined):Observable<User>{
      
      //User authenticated
         console.log("service subscribe? ", subject)
        //Add new subject to subscription list
        return this.httpClient.post<User>(`${this.path}/subscribe/${id_user}/id_subject`, subject)
        
      
    }

    //Persist user unsubscription
    unsubscribe(subject: SubjectDto):void{

      //User authenticated
      this.authService.me().subscribe({
        next:(value)=> {
          subject.isSubscribed = false;

          //Update subscription 
          this.httpClient.put<User>(`${this.path}/unsubscribe/${value.id_user}/id_subject`, subject).subscribe(
            {next() {
            location.reload();
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