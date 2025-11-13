import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Topic} from "../../interfaces/topic.interface";
import {BehaviorSubject, Observable} from "rxjs";

@Component({
  selector: 'app-topic-list',
  templateUrl: './topic-list.component.html',
  styleUrls: ['./topic-list.component.scss']
})
export class TopicListComponent {

  @Input()
  title?:string;

  @Input()
  topicBtnLabel?:string;

  @Output()
  public clickTopicBtn: EventEmitter<Topic> = new EventEmitter();

  private topicListSubject = new BehaviorSubject<Topic[]>([]);

  constructor() { }

  @Input() set topics(value: Topic[]) {
    this.topicListSubject.next(value);
  }

  onClickBtn(t : Topic): void {
    this.clickTopicBtn.emit(t);
  }

  public $topics(): Observable<Topic[]> {
    return this.topicListSubject.asObservable();
  }

}
