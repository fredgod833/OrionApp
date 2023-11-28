import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../interfaces/user.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private pathService = 'api/users';

  constructor(private httpClient: HttpClient) { }

  public getUser(): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}`);
  }

  public updateUser(user: Partial<User>): Observable<User> {
    return this.httpClient.put<User>(`${this.pathService}`, user);
  }

  public subscribeTopic(topicId: number): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/subscribe/${topicId}`, {});
  }

  public unsubscribeTopic(topicId: number): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/unsubscribe/${topicId}`, {});
  }
}
