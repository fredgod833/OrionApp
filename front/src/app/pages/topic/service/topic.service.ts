import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Topic} from "../../../interfaces/topic.interface";

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  private pathService = 'api/topics';

  constructor(private httpClient: HttpClient) { }

  public getTopicsSubscriptions(listIds: Array<number>): Observable<Array<Topic>> {
    return this.httpClient.get<Array<Topic>>(`${this.pathService}/subscription?values=${listIds}`);
  }

  public getNoneSubscriptionsTopics(listIds: Array<number>): Observable<Array<Topic>> {
    return this.httpClient.get<Array<Topic>>(`${this.pathService}/none_subscription?values=${listIds}`);
  }
}
