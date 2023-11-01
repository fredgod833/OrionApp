import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Comment } from '../interfaces/comment.interface';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  private baseUrl = 'http://localhost:8080/api/posts';

  constructor(private http: HttpClient) { }

  getComments(postId: number): Observable<Comment[]> {
      return this.http.get<Comment[]>(`${this.baseUrl}/${postId}/comments`);
  }

  createComment(postId: number, comment: Comment): Observable<Comment> {
      return this.http.post<Comment>(`${this.baseUrl}/${postId}/comments`, comment);
  }

}
