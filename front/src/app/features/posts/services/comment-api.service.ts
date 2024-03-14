import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {FormGroup} from "@angular/forms";
import {Comment} from "../interfaces/comment";

@Injectable({
  providedIn: 'root'
})
export class CommentApiService {

  private pathService = '/api/post';

  constructor(private http: HttpClient) {
  }

  public getComments(id: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.pathService}/${id}/comments`);
  }

  public saveComment(id: number, form: FormGroup): Observable<Comment> {
    return this.http.post<Comment>(`${this.pathService}/${id}`, form.value);
  }
}
