import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Post} from "../interfaces/post.interface";
import {AuthSuccess} from "../../../auth/interfaces/authSuccess.interface";
import {Comment} from "../interfaces/comment.interface";

@Injectable({
  providedIn: 'root'
})
export class PostsService {

  private pathService = 'api/comments';

  constructor(private httpClient: HttpClient) { }

  public getPostsComments(postId: Array<number>): Observable<Array<Post>> {
    return this.httpClient.get<Array<Post>>(`${this.pathService}/post/${postId}`);
  }

  public createComments(comment: Comment): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(`${this.pathService}`, comment);
  }
}
