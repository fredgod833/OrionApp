import { Component, OnInit } from '@angular/core';
import { UserService } from  '../../services/me.service';
import { Topic } from 'src/app/interfaces/topic.interface';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {

  currentUser: any;
  subscribedTopics: Topic[] = [];

  constructor(
    private userService: UserService,
    private topicService: TopicService
    ) { }

  ngOnInit() {
    this.loadCurrentUser();
  }

  saveChanges() {
    console.log('Sauvegarder les changements', this.currentUser);
  }


  loadCurrentUser() {
    this.userService.getCurrentUser().subscribe(
      data => {
        this.currentUser = data;
        this.loadSubscribedTopics();

      },
      error => {
        console.error('There was an error loading the user data!', error);
      }
    );
  }

  loadSubscribedTopics() {
    this.topicService.getSubscribedTopicsByUserId(this.currentUser.id).subscribe(
      topics => {
        this.subscribedTopics = topics;
      },
      error => {
        console.error('There was an error loading the subscribed topics!', error);
      }
    );
  }

  unsubscribe(topicId: number) {
    this.topicService.unsubscribeFromTopic(topicId, this.currentUser.id).subscribe(
      () => {
        this.loadSubscribedTopics();
      },
      error => {
        console.error('There was an error unsubscribing from the topic!', error);
      }
    );
  }

}
