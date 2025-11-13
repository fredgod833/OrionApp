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

  public subsribeTopic(t:Topic) {
    //Appel de l'api pour se desabonner
    this.topicService.subscribeTopic(t.id).subscribe();
    // Appel ok -> on supprime l'element de la liste courante pour eviter un nouvel appel d'API
    if (this.currenTopics != undefined) {
      let i: number = this.currenTopics.indexOf(t);
      if (i != undefined && i>=0) {
        this.currenTopics.splice(i,1);
        this.topicListSubject.next(this.currenTopics);
      }
    }
  }

}
