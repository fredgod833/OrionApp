import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {PostApiService} from "../../services/post-api.service";
import {Router} from "@angular/router";
import {Post} from "../../interfaces/post";

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {

  public posts$: Observable<Post[]> = this.postApiService.getPosts();

  constructor(private postApiService: PostApiService, private router: Router) {
  }

  ngOnInit(): void {
    this.posts$.subscribe(post => {
      console.log('Posts retrieved successfully!');
    })
  }

  public createArticle() {
    this.router.navigate(['form']).then(() => console.log("Navigated to create post page"));
  }
}
