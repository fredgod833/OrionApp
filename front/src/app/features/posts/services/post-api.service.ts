import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Post} from "../interfaces/post";
import {FormGroup} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class PostApiService {

  private pathService = '/api/posts';

  constructor(private http: HttpClient) {
  }

  public getAll(): Observable<Post[]> {
    return this.http.get<Post[]>(this.pathService);
  }

  public create(form: FormGroup): Observable<Post> {
    return this.http.post<Post>(this.pathService, form.value);
  }

  public getById(id: number): Observable<Post> {
    return this.http.get<Post>(`${this.pathService}/${id}`);
  }
}
