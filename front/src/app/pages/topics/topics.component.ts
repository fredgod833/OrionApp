import { Component, signal } from '@angular/core';
import { Topic } from '@core/types/topic.type';
import { TopicSubscriptionComponent } from '@components/common/topics/topic-subscription/topic-subscription.component';
import { TopicsContainerComponent } from '@components/shared/topics-container/topics-container.component';

@Component({
  selector: 'app-topics',
  standalone: true,
  templateUrl: './topics.component.html',
  styleUrl: './topics.component.scss',
  imports: [TopicSubscriptionComponent, TopicsContainerComponent],
})
export class TopicsComponent {
  public topicsArray = signal<Topic[]>([
    {
      id: 1,
      userId: 1,
      themeId: 1,
      title: 'First Topic',
      description: 'Description of the first topic',
      isSubscribed: false,
    },
    {
      id: 2,
      userId: 2,
      themeId: 2,
      title: 'Second Topic',
      description: 'Description of the second topic',
      isSubscribed: true,
    },
  ]);

  constructor() {
    this.updateUserThemeSubscription =
      this.updateUserThemeSubscription.bind(this);
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
