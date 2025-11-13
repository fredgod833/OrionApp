import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {map, Observable, take} from 'rxjs';
import {Topic} from "../interfaces/topic.interface";
import {TopicResponse} from "../interfaces/topicResponse.interface";

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  private pathService = 'api/topics';

  constructor(private httpClient: HttpClient) { }

  /**
   * Lister les themes existants
   */
  public listAll(): Observable<Topic[]> {

    return this.httpClient.get<TopicResponse>(`${this.pathService}`).pipe(
      take(1),
      map<TopicResponse, Topic[]>(value => value.topics)
    );
  }

  /**
   * Lister les themes souscrits par l'utilisateur
   */
  public listSubscribed(): Observable<TopicResponse> {
    return this.httpClient.get<TopicResponse>(`${this.pathService}/subscribed`);
  }

  /**
   * Lister les themes disponibles pour l'utilisateur
   */
  public listAvailable(): Observable<TopicResponse> {
    return this.httpClient.get<TopicResponse>(`${this.pathService}/available`);
  }

  /**
   * Souscrire à un nouveau theme
   * @param topicId
   */
  public subscribeTopic(topicId: number) : Observable<void> {
    return this.httpClient.post<void>(`${this.pathService}/subscription/${topicId}`,null);
  }

  /**
   * Se désabonner d'un theme
   * @param topicId
   */
  public unSubscribeTopic(topicId: number) : Observable<void> {
    return this.httpClient.delete<void>(`${this.pathService}/subscription/${topicId}`);
  }

}
