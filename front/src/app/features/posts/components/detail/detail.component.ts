import {Component, OnInit} from '@angular/core';
import {PostApiService} from "../../services/post-api.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Post} from "../../interfaces/post";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {
  public onError: boolean = false;
  public post!: Post;
  public form = this.fb.group({
    content: [
      '',
      [
        Validators.min(3),
        Validators.max(20)
      ]
    ]
  });

  constructor(
    private postApiService: PostApiService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.postApiService.getPost(Number(this.route.snapshot.paramMap.get('id')!)).subscribe(post => {
      this.post = post;
    });
  }

  public back(): void {
    window.history.back();
  }

  submit() {

  }
}
