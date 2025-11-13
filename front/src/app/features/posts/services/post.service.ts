import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {BehaviorSubject, map, Observable, take} from 'rxjs';
import {Post} from "../interfaces/post.interface";
import {PostRequest} from "../interfaces/postRequest.interface";
import {CommentRequest} from "../interfaces/commentRequest.interface";
import {PostListResponse} from "../interfaces/postListResponse.interface";
import {Commentary} from "../interfaces/comment.interface";
import {CommentsListResponse} from "../interfaces/commentsListResponse.interface";

@Injectable({
  providedIn: 'root'
})
/**
 * Service de gestion des Articles
 */
export class PostService {

  /** chemin racine des service Articles */
  private pathService = 'api/posts';

  /** Article sélectionné */
  private selectedPost?:Post;

  /** Liste des Commentaires associés à l'article sélectionné */
  private currentComments?: Commentary[];


  /** Observble de la liste des commentaire de la page courante
   * Permet de conserver en cache les données des services.
   *
   */
  private $commentListSubject = new BehaviorSubject<Commentary[]>([]);

  /** indique si le service est en erreur */
  private onError: boolean= false;


  /**
   * Charge la liste des commentaires pour l'article et initialise le cache
   * @param postId
   */
  public loadCommentsList(postId:number): Observable<Commentary[]> {
    this.getPostComments(postId).pipe(
      take(1)
    ).subscribe((value: CommentsListResponse) => {
      this.currentComments = value.comments;
      this.$commentListSubject.next(this.currentComments);
    })
    return this.$commentListSubject.asObservable();
  }

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
    return this.httpClient.get<PostListResponse>(`${this.pathService}/subscribed`).pipe(
      take(1),
      map<PostListResponse, Post[]>(value => value.posts)
    );
  }

  /**
   * Lister les commentaires d'un article
   * @param postId
   */
  public getPostComments(postId:number): Observable<CommentsListResponse> {
    return this.httpClient.get<CommentsListResponse>(`${this.pathService}/${postId}/comments`);
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
   * Ajouter un commentaire, rafraichi l'observable liste de commentaire.
   *
   * @param postId : number
   * @param commentRequest : CommentRequest
   */
  public addComment(postId: number, commentRequest: CommentRequest): void {

    // init de la liste de commentaires
    let comments : Commentary[]
    if (this.currentComments) {
      comments = this.currentComments;
    } else {
      comments = [];
    }

    // appel du service et mise à jour de la liste des commentaires
    this.httpClient.post<Commentary>(`${this.pathService}/${postId}/comments`, commentRequest)
      .pipe(take(1)).subscribe( {
        next: (response: Commentary) => {
          comments.push(response);
          this.$commentListSubject.next(comments);
          this.currentComments = comments;
        },
        error: error => this.onError = true
    });

  }

  /**
   * Modifier un commentaire
   *
   * @param postId : number
   * @param commentId : number
   * @param commentRequest : CommentRequest
   */
  public updateComment(postId: number, commentId: number, commentRequest: CommentRequest): Observable<Commentary> {
    return this.httpClient.post<Commentary>(`${this.pathService}/${postId}/comments/${commentId}`, commentRequest);
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

  public getSelectedPost() : Post | undefined {
    return this.selectedPost;
  }

  setSelectedPost(post: Post) {
    this.selectedPost = post;
  }
}
