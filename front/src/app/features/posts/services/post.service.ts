import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Post} from "../interfaces/post.interface";
import {PostRequest} from "../interfaces/postRequest.interface";
import {CommentRequest} from "../interfaces/commentRequest.interface";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private pathService = 'api/posts';

  constructor(private httpClient: HttpClient) { }

  /**
   * Lister les articles associés à un theme
   * @param topicId
   */
  public byTopic(topicId: number): Observable<Post[]> {
    return this.httpClient.get<Post[]>(`${this.pathService}/${topicId}`);
  }

  /**
   * Lister les articles des themes souscrits
   */
  public fromSubscribedTopic(): Observable<Post[]> {
    return this.httpClient.get<Post[]>(`${this.pathService}/subscribed`);
  }

  /**
   * Lister les commentaires d'un article
   * @param postId
   */
  public getPostComments(postId:number): Observable<Comment[]> {
    return this.httpClient.get<Comment[]>(`${this.pathService}/comments`);
  }

  /**
   * Créer un nouvel article
   *
   * @param postRequest : PostRequest
   */
  public create(postRequest: PostRequest): Observable<Post> {
    return this.httpClient.post<Post>(`${this.pathService}`, postRequest);
  }

  /**
   * Modifier un article
   *
   * @param postId : number
   * @param postRequest : PostRequest
   */
  public update(postId: number, postRequest : PostRequest) : Observable<Post> {
    return this.httpClient.put<Post>(`${this.pathService}/${postId}`, postRequest);
  }

  /**
   * Ajouter un commentaire
   *
   * @param postId : number
   * @param commentRequest : CommentRequest
   */
  public addComment(postId: number, commentRequest: CommentRequest): Observable<Post> {
    return this.httpClient.post<Post>(`${this.pathService}/${postId}/comments`, commentRequest);
  }

  /**
   * Modifier un commentaire
   *
   * @param postId : number
   * @param commentId : number
   * @param commentRequest : CommentRequest
   */
  public updateComment(postId: number, commentId: number, commentRequest: CommentRequest): Observable<Post> {
    return this.httpClient.post<Post>(`${this.pathService}/${postId}/comments/${commentId}`, commentRequest);
  }

  /**
   * Supprimer un commentaire
   *
   * @param postId : number
   * @param commentId : number
   */
  public deleteComment(postId: number, commentId: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.pathService}/${postId}/comments/${commentId}`);
  }

}
