import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Topic } from '../interfaces/topic.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TopicsService {
  private pathService = 'http://localhost:8080/api/topics';

  constructor(private httpClient: HttpClient) { }

  public getTopics(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.pathService}`);
  }

  public getTopic(id: number): Observable<Topic> {
    return this.httpClient.get<Topic>(`${this.pathService}/${id}`);
  }

  public createTopic(topic: Topic): Observable<Topic> {
    return this.httpClient.post<Topic>(`${this.pathService}`, topic);
  }

  public updateTopic(topic: Topic): Observable<Topic> {
    return this.httpClient.put<Topic>(`${this.pathService}`, topic);
  }

  public deleteTopic(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.pathService}/${id}`);
  }
}
