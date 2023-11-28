import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Comment } from '../interfaces/comment.interface';

@Injectable({
  providedIn: 'root',
})
export class CommentsService {
  private pathService = 'http://localhost:8080/api/comments';

  constructor(private httpClient: HttpClient) { }

  public getComments(): Observable<Comment[]> {
    return this.httpClient.get<Comment[]>(`${this.pathService}`);
  }

  public getCommentsByPost(postId: number): Observable<Comment[]> {
    return this.httpClient.get<Comment[]>(`${this.pathService}/post/${postId}`);
  }

  public getComment(id: number): Observable<Comment> {
    return this.httpClient.get<Comment>(`${this.pathService}/${id}`);
  }

  public createComment(comment: Partial<Comment>): Observable<Comment> {
    return this.httpClient.post<Comment>(`${this.pathService}`, comment);
  }

  public updateComment(comment: Comment): Observable<Comment> {
    return this.httpClient.put<Comment>(`${this.pathService}`, comment);
  }

  public deleteComment(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.pathService}/${id}`);
  }
}
