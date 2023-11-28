import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../interfaces/post.interface';

@Injectable({
  providedIn: 'root',
})
export class PostsService {
  private pathService = 'http://localhost:8080/api/posts';

  constructor(private httpClient: HttpClient) { }

  public getPosts(): Observable<Post[]> {
    return this.httpClient.get<Post[]>(`${this.pathService}`);
  }

  public getPost(id: number): Observable<Post> {
    return this.httpClient.get<Post>(`${this.pathService}/${id}`);
  }

  public createPost(post: Partial<Post>): Observable<Post> {
    return this.httpClient.post<Post>(`${this.pathService}`, post);
  }

  public updatePost(post: Post): Observable<Post> {
    return this.httpClient.put<Post>(`${this.pathService}`, post);
  }

  public deletePost(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.pathService}/${id}`);
  }
}
