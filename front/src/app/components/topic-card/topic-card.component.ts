import { Component, Input } from '@angular/core';
import { Topic } from 'src/app/interfaces/topic.interface';
import { UserService } from 'src/app/services/user.service';


@Component({
  selector: 'app-topic-card',
  templateUrl: './topic-card.component.html',
  styleUrls: ['./topic-card.component.scss']
})
export class TopicCardComponent {

  @Input() topic: Topic | undefined;
  @Input() unsubscribe: boolean;

  constructor(private userService: UserService) {
    this.topic = undefined;
    this.unsubscribe = false;
  }

  public subscribeTopic(): void {
    if (this.topic) {
      this.userService.subscribeTopic(this.topic.id).subscribe({
        next: () => {
          this.unsubscribe = true;
        },
      });
    }
  }
  public unsubscribeTopic(): void {
    if (this.topic) {
      this.userService.unsubscribeTopic(this.topic.id).subscribe({
        next: () => {
          this.unsubscribe = false;
        },
      });
    }
  }
}
