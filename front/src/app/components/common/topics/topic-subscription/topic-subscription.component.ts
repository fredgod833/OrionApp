import { Component, EventEmitter, Output, input } from '@angular/core';

@Component({
  selector: 'app-topic-subscription',
  standalone: true,
  imports: [],
  templateUrl: './topic-subscription.component.html',
  styleUrl: './topic-subscription.component.scss',
})
export class TopicSubscriptionComponent {
  @Output() toggleSubscription = new EventEmitter<number>();

  public id = input.required<number>();

  public title = input.required<string>();

  public description = input.required<string>();

  public isSubscribed = input.required<boolean>();

  toggleTopicSubscription(): void {
    const id = this.id();

    this.toggleSubscription.emit(id);
  }
}
