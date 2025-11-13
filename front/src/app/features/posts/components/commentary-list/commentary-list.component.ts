import {Component, Input} from '@angular/core';
import {BehaviorSubject, Observable, take} from "rxjs";
import {Commentary} from "../../interfaces/comment.interface";
import {PostService} from "../../services/post.service";
import {CommentsListResponse} from "../../interfaces/commentsListResponse.interface";

@Component({
  selector: 'app-commentary-list',
  templateUrl: './commentary-list.component.html',
  styleUrls: ['./commentary-list.component.scss']
})
export class CommentaryListComponent {

  $commentaryList?:Observable<Commentary[]>;

  constructor(private postService : PostService) {
  }

  @Input()
  public set postId(postId:number) {
    this.$commentaryList = this.postService.loadCommentsList(postId);
  }

}
