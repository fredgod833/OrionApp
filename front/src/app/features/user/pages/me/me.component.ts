import {Component, OnInit} from '@angular/core';
import {User} from '../../../../interfaces/user.interface';
import {SessionService} from '../../../auth/services/session.service';
import {UserService} from '../../services/user.service';
import {TopicService} from "../../../topics/services/topic.service";
import {Topic} from "../../../topics/interfaces/topic.interface";
import {TopicResponse} from "../../../topics/interfaces/topicResponse.interface";
import {BehaviorSubject, Observable, take} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {

  public user: User | undefined;

  private currenTopics: Topic[] | undefined;

  private topicListSubject = new BehaviorSubject<Topic[]>([]);

  public form! : FormGroup;

  constructor(private fb: FormBuilder,
              private sessionService: SessionService,
              private userService: UserService,
              private topicService : TopicService) {
  }

  private initForm(user?: User): void {
    this.form = this.fb.group({
      email: [
        user ? user.email : '',
        [
          Validators.required,
          Validators.email
        ]
      ],
      login: [
        user ? user.login : '',
        [
          Validators.required,
          Validators.min(3),
          Validators.max(20)
        ]
      ],
    });
  }

  public ngOnInit(): void {
    this.userService
      .getById(this.sessionService.sessionInformation!.id.toString())
      .pipe(take(1))
      .subscribe(
        (user: User) => {
          this.user = user
          this.initForm(user);
        }
      );
    this.topicService.listSubscribed()
      .pipe(take(1))
      .subscribe((value: TopicResponse) => {
      this.currenTopics = value.topics;
      this.topicListSubject.next(this.currenTopics);
    });
  }

  public updateProfil(): void {
    //TODO
  }

  public $topics(): Observable<Topic[]> {
    return this.topicListSubject.asObservable();
  }

  public unsubsribeTopic(t:Topic) {
    //Appel de l'api pour se desabonner
    this.topicService.unSubscribeTopic(t.id)
      .pipe(take(1)).subscribe();
    // Appel ok -> on supprime l'element de la liste courante pour eviter un nouvel appel d'API
    if (this.currenTopics != undefined) {
      let i: number = this.currenTopics.indexOf(t);
      if (i != undefined && i>=0) {
        this.currenTopics.splice(i,1);
        this.topicListSubject.next(this.currenTopics);
      }
    }
  }

  public submit()  {

  }

}
