import { Component, input } from '@angular/core';
import { Topic } from '@core/types/topic.type';
import { TopicSubscriptionComponent } from '@components/common/topics/topic-subscription/topic-subscription.component';

@Component({
  selector: 'app-topics-container',
  standalone: true,
  templateUrl: './topics-container.component.html',
  styleUrl: './topics-container.component.scss',
  imports: [TopicSubscriptionComponent],
})
export class TopicsContainerComponent {
  public topicsArray = input.required<Topic[]>();

  public onSubscriptionChange = input.required<(emittedEvent: number) => any>();
}
