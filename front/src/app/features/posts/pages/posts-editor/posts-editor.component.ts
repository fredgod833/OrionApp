import {Component} from '@angular/core';
import {Topic} from "../../../topics/interfaces/topic.interface";
import {FormBuilder, Validators} from "@angular/forms";
import {PostService} from "../../services/post.service";
import {PostRequest} from "../../interfaces/postRequest.interface";
import {Post} from "../../interfaces/post.interface";
import {Router} from "@angular/router";
import {TopicService} from "../../../topics/services/topic.service";
import {Observable, take} from "rxjs";

@Component({
  selector: 'app-posts-editor',
  templateUrl: './posts-editor.component.html',
  styleUrls: ['./posts-editor.component.scss']
})

export class PostsEditorComponent {

  public topics$ : Observable<Topic[]> = this.topicService.listAll();

  public onError : boolean = false;

  public errorMessage?: string;

  public form = this.fb.group({
    topicId: [-1,
      [
        Validators.required
      ]
    ],
    title: [
      '',
      [
        Validators.required,
      ]
    ],
    content: [
      '',
      [
        Validators.required,
      ]
    ]
  });

  constructor(private router: Router, private fb: FormBuilder, private postService:PostService, private topicService:TopicService) {}

  public submitPost() {

    let postRequest : PostRequest = this.form.value as PostRequest;
    this.postService.create(postRequest).pipe(take(1))
      .pipe(take(1))
      .subscribe({
      next: (response: Post) => {
        this.router.navigate(['/posts']);
      },
      error: error => {
        this.onError = true;
        if (error.error instanceof Object && error.error.hasOwnProperty("message")) {
          this.errorMessage = error.error.message;
        } else {
          this.errorMessage = "une erreur est survenue.";
        }
      },
    });

  }


}
