import {Component, Input} from '@angular/core';
import {Commentary} from "../../interfaces/comment.interface";

@Component({
  selector: 'app-commentary-view',
  templateUrl: './commentary-view.component.html',
  styleUrls: ['./commentary-view.component.scss']
})
export class CommentaryViewComponent {

  @Input()
  public comment!: Commentary;

}
