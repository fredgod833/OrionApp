import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Post} from "../interfaces/post.interface";
import {AuthSuccess} from "../../../auth/interfaces/authSuccess.interface";
import {PostUpdate} from "../interfaces/postUpdate";

@Injectable({
  providedIn: 'root'
})
export class PostsService {

  private pathService = 'api/posts';

  constructor(private httpClient: HttpClient) { }

  public getPostsSubscriptions(listIds: Array<number>): Observable<Array<Post>> {
    return this.httpClient.get<Array<Post>>(`${this.pathService}/subscription?values=${listIds}`);
  }

  public create(post: Post): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(`${this.pathService}`, post);
  }

  public delete(id: number): Observable<any> {
    return this.httpClient.delete(`${this.pathService}/${id}`);
  }

  public getPost(id: number): Observable<Post> {
    return this.httpClient.get<Post>(`${this.pathService}/${id}`);
  }

  /*
  public update(postUpdate: PostUpdate): Observable<AuthSuccess> {
    return this.httpClient.put<AuthSuccess>(`${this.pathService}`, postUpdate);
  }*/
}
