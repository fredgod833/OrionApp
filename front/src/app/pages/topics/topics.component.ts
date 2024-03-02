import { Component, inject, signal } from '@angular/core';
import { Topic, TopicSubscription } from '@core/types/topic.type';
import { TopicSubscriptionComponent } from '@components/common/topics/topic-subscription/topic-subscription.component';
import { TopicsContainerComponent } from '@components/shared/topics-container/topics-container.component';
import { TopicService } from '@core/services/topic/topic.service';
import { toSignal } from '@angular/core/rxjs-interop';
import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'app-topics',
  standalone: true,
  templateUrl: './topics.component.html',
  styleUrl: './topics.component.scss',
  imports: [TopicSubscriptionComponent, TopicsContainerComponent],
})
export class TopicsComponent {
  private topicService = inject(TopicService);

  public topicsArray = signal<Topic[]>([]);

  public isLoading = toSignal<boolean>(this.topicService.isLoading$);

  public hasError = toSignal<boolean>(this.topicService.hasError$);

  public errorMessage = toSignal<string>(this.topicService.errorMessage$);

  constructor() {
    this.updateUserThemeSubscription =
      this.updateUserThemeSubscription.bind(this);
  }

  ngOnInit() {
    this.topicService.getAllThemesWithSubscription().subscribe((v: Topic[]) => {
      this.topicsArray.update(() => {
        return v;
      });
    });
  }

  updateUserThemeSubscription(id: number): void {
    const topic = this.getTopicById(id) as Topic;
    console.log('PARENT Clicked subs toggle btn', topic);

    const { isSubscribed } = topic;

    let subscriber: Subscription;

    if (isSubscribed) {
      subscriber = this.topicService
        .unsubscribeToTheme(id)
        .subscribe((value) => {
          this.updateTopicsArray(id);
        });
    } else {
      subscriber = this.topicService.subscribeToTheme(id).subscribe((value) => {
        this.updateTopicsArray(id);
      });
    }
  }

  private getTopicById(id: number): Topic | null {
    const topicsArray = this.topicsArray();

    const topic: Topic | null =
      topicsArray.find((topic) => {
        return topic.id === id;
      }) || null;

    return topic;
  }

  private updateTopicsArray(id: number): void {
    this.topicsArray.update((topics: Topic[]) => {
      const index: number = topics.findIndex((topic: Topic) => topic.id === id);
      if (index !== -1) {
        topics[index].isSubscribed = !topics[index].isSubscribed;
      }

      return topics;
    });
  }
}
