import { Component, inject, signal } from '@angular/core';
import { Topic, TopicSubscription } from '@core/types/topic.type';
import { TopicSubscriptionComponent } from '@components/common/topics/topic-subscription/topic-subscription.component';
import { TopicsContainerComponent } from '@components/shared/topics-container/topics-container.component';
import { TopicService } from '@core/services/topic/topic.service';
import { Observable, combineLatest, forkJoin, map } from 'rxjs';

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

  constructor() {
    this.updateUserThemeSubscription =
      this.updateUserThemeSubscription.bind(this);
  }

  ngOnInit() {
    this.topicService.getAllThemesWithSubscription().subscribe((v) => {
      this.topicsArray.update(() => {
        return v;
      });
      console.log(this.topicsArray());
    });
  }

  updateUserThemeSubscription(id?: number) {
    console.log('PARENT Clicked subs toggle btn', this);

    this.topicsArray.update((topics) => {
      for (let i = 0; i < topics.length; i++) {
        const topic = topics[i];

        if (topic.id !== id) {
          continue;
        }

        topics[i].isSubscribed = !topic.isSubscribed;
      }

      return topics;
    });
  }
}
