import { Component } from '@angular/core';
import { TopicsService } from '../../services/topic.service';
import { Topic } from 'src/app/interfaces/topic.interface';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss']
})
export class TopicsComponent {
      public topics$: Observable<Topic[]>;
      constructor(private topicsService: TopicsService) {
        this.topics$ = this.topicsService.getTopics();
      }
}
