import { Component, input } from '@angular/core';

@Component({
  selector: 'app-topic-subscription',
  standalone: true,
  imports: [],
  templateUrl: './topic-subscription.component.html',
  styleUrl: './topic-subscription.component.scss',
})
export class TopicSubscriptionComponent {
  public title = input.required<string>();

  public description = input.required<string>();

  public isSubscribed = input.required<boolean>();
}
