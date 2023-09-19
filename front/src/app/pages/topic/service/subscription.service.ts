import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {User} from "../../../interfaces/user.interface";
import {HttpClient} from "@angular/common/http";
import {LoginRequest} from "../../../auth/interfaces/loginRequest.interface";
import {AuthSuccess} from "../../../auth/interfaces/authSuccess.interface";

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  private pathService = '/api/subscriptions';

  constructor(private httpClient: HttpClient) { }

  public getTopics(): Observable<Array<number>> {
    return this.httpClient.get<Array<number>>(`${this.pathService}/topic_ids`);
  }

  public delete(id: number): Observable<any> {
    return this.httpClient.delete(`${this.pathService}/topic/${id}`);
  }

  public create(id: number): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(`${this.pathService}/topic`, id);
  }
}
