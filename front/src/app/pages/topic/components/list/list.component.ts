import { Component, OnInit } from '@angular/core';
import {SessionService} from "../../../../service/session.service";
import {User} from "../../../../interfaces/user.interface";
import {Topic} from "../../../../interfaces/topic.interface";
import {SubscriptionService} from "../../service/subscription.service";
import {TopicService} from "../../service/topic.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {
  public lentListTopics! : number;
  public listTopic! : Array<Topic>;

  constructor(
    private subscriptionService: SubscriptionService,
    private sessionService: SessionService,
    private topicService: TopicService,
    private matSnackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.getNoneSubscriptionsTopics()
  }

  public getNoneSubscriptionsTopics(): void {
    this.subscriptionService.getTopics()
      .subscribe(
        (listInteger: Array<number>) => {
          this.topicService.getNoneSubscriptionsTopics(listInteger)
            .subscribe(
              (list: Array<Topic>) => {
                this.listTopic = list;
                this.lentListTopics = list.length;
              })
        }
      )
  }

  public scribeTopic(indexOfTopic: number, topic: Topic): void {
    this.subscriptionService.create(topic.id)
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
      )
  }

  /*
  get user(): User | undefined {
    return this.sessionService.user;
  }*/

}
