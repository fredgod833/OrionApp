import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Topic} from "../../interfaces/topic.interface";

@Component({
  selector: 'app-topic-preview',
  templateUrl: './topic-preview.component.html',
  styleUrls: ['./topic-preview.component.scss']
})
export class TopicPreviewComponent {

  @Input()
  topic!:Topic;

  @Input()
  btnLabel?:string;

  @Output()
  public clickBtn: EventEmitter<Topic> = new EventEmitter();

  constructor() { }

  onClickBtn(): void {
    this.clickBtn.emit(this.topic);
  }

}
