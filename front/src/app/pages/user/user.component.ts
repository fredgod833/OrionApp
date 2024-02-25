import { Component, signal } from '@angular/core';
import { Topic } from '@core/types/topic.type';
import { TopicsContainerComponent } from '@components/shared/topics-container/topics-container.component';

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [TopicsContainerComponent],
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss',
})
export class UserComponent {
  public topicsArray = signal<Topic[]>([
    {
      id: 1,
      userId: 1,
      themeId: 1,
      title: 'First Topic',
      description: 'Description of the first topic',
      isSubscribed: true,
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

    this.topicsArray.update((topics) => topics.filter((t) => t.id !== id));
  }
}
