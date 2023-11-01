import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Topic } from '../interfaces/topic.interface';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  private apiUrl = 'http://localhost:8080/api/topics';

  constructor(private http: HttpClient) { }

  getTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>(this.apiUrl);
  }

  subscribeToTopic(topicId: number, userId: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${topicId}/subscribe/${userId}`, {});
  }

  unsubscribeFromTopic(topicId: number, userId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${topicId}/unsubscribe/${userId}`);
  }

  getSubscribedTopicsByUserId(userId: number): Observable<Topic[]> {
    return this.http.get<Topic[]>(`${this.apiUrl}/subscribed/${userId}`);
  }

}
