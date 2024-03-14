import {Component, OnInit} from '@angular/core';
import {PostApiService} from "../../services/post-api.service";
import {ActivatedRoute} from "@angular/router";
import {Post} from "../../interfaces/post";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CommentApiService} from "../../services/comment-api.service";
import {Comment} from "../../interfaces/comment";

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {
  public onError: boolean = false;
  public post!: Post;
  public comments!: Comment[]
  public form: FormGroup = this.fb.group({
    content: [
      '',
      [
        Validators.min(3),
        Validators.max(20)
      ]
    ]
  });
  private id!: number;

  constructor(
    private postApiService: PostApiService,
    private route: ActivatedRoute,
    private commentApiService: CommentApiService,
    private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.postApiService.getPost(this.id).subscribe(post => {
      this.post = post;
    });
    this.commentApiService.getComments(this.id).subscribe(comments => {
      this.comments = comments;
    })
  }

  public back(): void {
    window.history.back();
  }

  public submit() {
    this.commentApiService.saveComment(this.id, this.form).subscribe(comment => {
        this.comments.push(comment);
      }
    );
  }
}
