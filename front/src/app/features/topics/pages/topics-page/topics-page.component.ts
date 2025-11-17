import {Component} from '@angular/core';
import {Topic} from "../../interfaces/topic.interface";
import {BehaviorSubject, Observable, take} from "rxjs";
import {TopicService} from "../../services/topic.service";
import {TopicResponse} from "../../interfaces/topicResponse.interface";

@Component({
  selector: 'app-topics-page',
  templateUrl: './topics-page.component.html',
  styleUrls: ['./topics-page.component.scss']
})
export class TopicsPageComponent {

  private currenTopics: Topic[] | undefined;

  private topicListSubject = new BehaviorSubject<Topic[]>([]);

  constructor(private topicService : TopicService) {

  }

  public ngOnInit(): void {
    this.topicService.listAvailable()
      .pipe(take(1))
      .subscribe((value: TopicResponse) => {
      this.currenTopics = value.topics;
      this.topicListSubject.next(this.currenTopics);
    });
  }

  public $topics(): Observable<Topic[]> {
    return this.topicListSubject.asObservable();
  }

  public switchSubscription(t:Topic) {
    if (t.subscribed) {
      this.unsubsribeTopic(t);
    } else {
      this.subscribeTopic(t);
    }
  }

  private subscribeTopic(t:Topic) {
    //Appel de l'api pour se desabonner
    this.topicService.subscribeTopic(t.id).subscribe();
    // Appel ok -> on met à jour l'état de l'abonnement pour eviter un nouvel appel d'API
    t.subscribed = true;
  }

  private unsubsribeTopic(t:Topic) {
    //Appel de l'api pour se desabonner
    this.topicService.unSubscribeTopic(t.id).pipe(take(1)).subscribe();
    // Appel ok -> on met à jour l'état de l'abonnement pour eviter un nouvel appel d'API
    t.subscribed = false;
  }

}
