import {Component, OnInit} from '@angular/core';
import {User} from '../../../../interfaces/user.interface';
import {SessionService} from '../../../auth/services/session.service';
import {TopicService} from "../../../topics/services/topic.service";
import {Topic} from "../../../topics/interfaces/topic.interface";
import {TopicResponse} from "../../../topics/interfaces/topicResponse.interface";
import {BehaviorSubject, Observable, take} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SessionInformation} from "../../../../interfaces/sessionInformation.interface";
import {UpdateUserRequest} from "../../interfaces/updateRequest.interface";
import {AuthService} from "../../../auth/services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {

  public hide = true;

  public user: User | undefined;

  public onError = false;

  public errorMessage? : string;

  private currenTopics: Topic[] | undefined;

  private topicListSubject = new BehaviorSubject<Topic[]>([]);

  public form! : FormGroup;

  constructor(private formBuilder: FormBuilder,
              private sessionService: SessionService,
              private authService: AuthService,
              private topicService : TopicService,
              private router: Router ) {
  }

  private initFormFromUser(user?: User): void {
    this.form = this.formBuilder.group({
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
      password: [
        '',
        [
          Validators.required,
          Validators.min(8),
          Validators.max(40)
        ]
      ]
    });
  }

  private initFormFromSession(session : SessionInformation | undefined) {
    this.form = this.formBuilder.group({
      email: [
        session ? session.email : '',
        [
          Validators.required,
          Validators.email
        ]
      ],
      login: [
        session ? session.username : '',
        [
          Validators.required,
          Validators.min(3),
          Validators.max(20)
        ]
      ],
      password: [
        '',
        [
          Validators.required,
          Validators.min(8),
          Validators.max(40)
        ]
      ]
    });
  }

  public ngOnInit(): void {
    this.initFormFromSession(this.sessionService.sessionInformation);
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
    const updateRequest:UpdateUserRequest = this.form.value as UpdateUserRequest;
    this.authService.update(updateRequest)
      .pipe(take(1))
      .subscribe({
        next: (response: SessionInformation) => {
          this.sessionService.logOut();
          this.router.navigateByUrl('/');
        },
        error: error => {
          this.onError = true;
          if (error.error instanceof Object && error.error.hasOwnProperty("message")) {
            this.errorMessage = error.error.message;
          } else {
            this.errorMessage = "une erreur est survenue.";
          }
        },
      });
  }

  logout() {
    this.sessionService.logOut();
    this.router.navigate(['/']);
  }

}
