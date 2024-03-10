import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../interfaces/user.interface";
import {FormGroup} from "@angular/forms";
import {UpdatedUser} from "../interfaces/updated-user";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private pathService = '/api/user';

  constructor(private http: HttpClient) {
  }

  public getUser(): Observable<User> {
    return this.http.get<User>(`${this.pathService}`);
  }

  public update(id: number, form: FormGroup): Observable<UpdatedUser> {
    return this.http.put<UpdatedUser>(`${this.pathService}/update/${id}`, form.value);
  }

  public follow(id: number): Observable<User> {
    return this.http.post<User>(`${this.pathService}/follow/${id}`, id);
  }

  public unFollow(id: number): Observable<User> {
    return this.http.delete<User>(`${this.pathService}/unfollow/${id}`);
  }
}
