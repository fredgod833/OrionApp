import { Component, inject, signal } from '@angular/core';
import { Topic, TopicSubscription } from '@core/types/topic.type';
import { TopicSubscriptionComponent } from '@components/common/topics/topic-subscription/topic-subscription.component';
import { TopicsContainerComponent } from '@components/shared/topics-container/topics-container.component';
import { TopicService } from '@core/services/topic/topic.service';
import { toSignal } from '@angular/core/rxjs-interop';
import { Observable, Subscription, take } from 'rxjs';

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
    const subscription: Subscription = this.topicService
      .getAllThemesWithSubscription()
      .subscribe((res: Topic[]) => {
        this.topicsArray.update(() => {
          return res;
        });
        subscription.unsubscribe();
      });
  }

  public updateUserThemeSubscription(id: number): void {
    const topic = this.getTopicById(id) as Topic;
    const { isSubscribed } = topic;

    const subscription: Subscription = this.topicService[
      isSubscribed ? 'unsubscribeToTheme' : 'subscribeToTheme'
    ](id).subscribe(() => {
      this.updateTopicsArray(id);
      subscription.unsubscribe();
    });
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
