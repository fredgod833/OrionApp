import { Component, OnInit } from '@angular/core';
import {Topic} from "../../../../interfaces/topic.interface";
import {SubscriptionService} from "../../../topic/service/subscription.service";
import {PostsService} from "../../services/posts.service";
import {formatDate} from "@angular/common";
import {Post} from "../../interfaces/post.interface";
import {Router} from "@angular/router";

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {
  public lentListPosts! : number;
  public listPosts! : Array<Post>;

  constructor(
    private subscriptionService: SubscriptionService,
    private postsService: PostsService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.getSubscriptionsPosts()
  }

  public getSubscriptionsPosts(): void {
    this.subscriptionService.getTopics()
      .subscribe(
        (listInteger: Array<number>) => {
          this.postsService.getPostsSubscriptions(listInteger)
            .subscribe(
              (list: Array<Post>) => {
                this.listPosts = list;
                this.lentListPosts = list.length;
              })
        })
  }

  public createPost(): void {
    console.log("createPost");
    this.router.navigate(['/posts/create'])
  }

  public sortPosts(): void {
    console.log("sortPosts");
  }

  public viewPost(id: number): void {
    console.log("viewPost");
    console.log(id)
    this.router.navigate([`/posts/detail/${id}`])
  }

  public viewDetail(indexOfTopic: number, topic: Topic): void {
    /*this.subscriptionService.create(topic.id)
      .subscribe({
          next: (_) => {
            this.matSnackBar.open(`${topic.title} subscribed !`, 'Close', { duration: 3000 })
            this.listTopic.splice(indexOfTopic, 1);
          },
          error: err => {
            console.error('An error occurred :', err);
            this.matSnackBar.open(`An error occurred !`, 'Close', { duration: 3000 });
          }
        }
      )*/
  }

  protected readonly formatDate = formatDate;
}
