import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Post} from "../../interfaces/post.interface";
import {Router} from "@angular/router";
import {PostService} from "../../services/post.service";

@Component({
  selector: 'app-post-preview',
  templateUrl: './post-preview.component.html',
  styleUrls: ['./post-preview.component.scss']
})
export class PostPreviewComponent {

  @Input()
  post!:Post;

  private router : Router;

  private postService : PostService;

  constructor(router: Router, postService : PostService) {
    this.router = router;
    this.postService = postService;
  }

  public onSelect() : void {
      this.postService.setSelectedPost(this.post)
      this.router.navigateByUrl("/post/view");
  }
}
