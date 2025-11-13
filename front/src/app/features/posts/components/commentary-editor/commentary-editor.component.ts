import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {PostService} from "../../services/post.service";
import {Commentary} from "../../interfaces/comment.interface";
import {take} from "rxjs";
import {CommentRequest} from "../../interfaces/commentRequest.interface";

@Component({
  selector: 'app-commentary-editor',
  templateUrl: './commentary-editor.component.html',
  styleUrls: ['./commentary-editor.component.scss']
})
export class CommentaryEditorComponent {

  @Input()
  public postId!: number;

  constructor(private formBuilder: FormBuilder,
              private postService:PostService) {}

  public errorMessage : String = "";

  public onError : boolean = false;

  public form = this.formBuilder.group({
    comment: [
      '',
      [
        Validators.required
      ]
    ]
  });

  public sendComment() {
    let commentRequest : CommentRequest = this.form.value as CommentRequest;
    this.postService.addComment(this.postId, commentRequest);
  }

}
