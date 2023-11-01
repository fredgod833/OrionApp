import { Component, OnInit } from '@angular/core';
import { TopicService } from '../../services/topic.service';
import { Topic } from 'src/app/interfaces/topic.interface';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss']
})
export class TopicsComponent implements OnInit {

  topics: Topic[] = [];

  constructor(private topicService: TopicService) { }

  ngOnInit(): void {
    this.getTopics();
  }

  getTopics(): void {
    this.topicService.getTopics().subscribe(
      data => this.topics = data,
      error => {
        console.error('Données des topics non trouvées !', error);
      }
    );
  }

  subscribe(topicId: number): void {
    const userId = 1;
    this.topicService.subscribeToTopic(topicId, userId).subscribe(
      () => {
        alert('Abonné avec succès!');
      },
      error => {
        alert('Erreur lors de l\'abonnement');
      }
    );
  }
}
