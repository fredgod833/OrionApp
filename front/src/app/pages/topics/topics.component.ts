import { Component, signal } from '@angular/core';
import { Topic } from '@core/types/topic.type';
import { TopicSubscriptionComponent } from '@components/common/topics/topic-subscription/topic-subscription.component';

@Component({
  selector: 'app-topics',
  standalone: true,
  templateUrl: './topics.component.html',
  styleUrl: './topics.component.scss',
  imports: [TopicSubscriptionComponent],
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

  updateUserThemeSubscription(newValue?: {
    id: number;
    isSubscribed: boolean;
  }) {
    console.log('PARENT Clicked subs toggle btn', newValue);
    this.topicsArray.map((t) => {
      t.id === newValue?.id;
    });
  }
}
