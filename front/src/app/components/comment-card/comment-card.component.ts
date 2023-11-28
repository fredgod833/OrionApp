import { Component, Input } from '@angular/core';
import { Comment } from 'src/app/interfaces/comment.interface';

@Component({
  selector: 'app-comment-card',
  templateUrl: './comment-card.component.html',
  styleUrls: ['./comment-card.component.scss']
})
export class CommentCardComponent {
    @Input() comment: Comment | undefined;
    constructor() {
      this.comment = undefined;
    }
}
