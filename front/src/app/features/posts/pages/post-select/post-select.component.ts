import {Component, OnInit} from '@angular/core';
import {Observable, take} from "rxjs";
import {User} from "../../../../interfaces/user.interface";
import {TopicResponse} from "../../../topics/interfaces/topicResponse.interface";
import {PostService} from "../../services/post.service";
import {Post} from "../../interfaces/post.interface";
import {Router} from "@angular/router";

@Component({
  selector: 'app-post-select',
  templateUrl: './post-select.component.html',
  styleUrls: ['./post-select.component.scss']
})
export class PostSelectComponent implements OnInit {

  private postService : PostService;
  private router: Router;

  $posts! : Observable<Post[]>;

  constructor(postService : PostService, router : Router) {
    this.postService = postService;
    this.router = router;
  }

  public ngOnInit() {
    this.$posts = this.postService.fromSubscribedTopic();
  }

  goCreate() {

      this.router.navigateByUrl("/post/new");
  }

  goView() {
  }

}
