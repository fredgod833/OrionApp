import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../auth/services/auth.service";
import {Router} from "@angular/router";
import {SessionService} from "../../service/session.service";
import {User} from "../../interfaces/user.interface";
import {Topic} from "../../interfaces/topic.interface";
import {SubscriptionService} from "../topic/service/subscription.service";
import {TopicService} from "../topic/service/topic.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UpdateRequest} from "../../auth/interfaces/UpdateRequest.interface";
import {AuthSuccess} from "../../auth/interfaces/authSuccess.interface";
@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  public user: User | undefined;
  public listTopic! : Array<Topic>;
  public lenghtListTopics! : number;
  public onError = false;
  public userForm: FormGroup = this.formBuilder.group({name: [], email: [], id: []});

  constructor(
    private subscriptionService: SubscriptionService,
    private sessionService: SessionService,
    private topicService: TopicService,
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private matSnackBar: MatSnackBar,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.getTopicsSubscriptions()
    this.authService.me()
      .subscribe(
        (user: User) => {
          this.user = user
          this.initForm(user)
        });
  }

  public initForm(user: User): void {
    this.userForm = this.formBuilder.group({
      name: [
        user.name, [ Validators.required, Validators.min(3) ]
      ],
      email: [
        user.email, [ Validators.required, Validators.email ]
      ],
      id: [
        user.id,
      ]
    });
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate(['/login'])
  }

  public getTopicsSubscriptions(): void {
    this.subscriptionService.getTopics()
      .subscribe(
    (listInteger: Array<number>) => {
            this.topicService.getTopicsSubscriptions(listInteger)
              .subscribe(
            (list: Array<Topic>) => {
                    this.listTopic = list;
                    this.lenghtListTopics = list.length;
                })
        }
      )
  }

  public unsubscribeTopic(indexOfTopic: number, topic: Topic): void {
    this.subscriptionService.delete(topic.id)
      .subscribe({
          next: (_) => {
            this.matSnackBar.open(`${topic.title} unsubscribed !`, 'Close', { duration: 3000 })
            this.listTopic.splice(indexOfTopic, 1);
          },
          error: err => {
            console.error('An error occurred :', err);
            this.matSnackBar.open(`An error occurred !`, 'Close', { duration: 3000 });
          }
        }
      )
  }

  public submit(): void {
    const updateRequest: UpdateRequest = this.userForm.value as UpdateRequest;
    this.authService.update(updateRequest).subscribe({
      next: (authSuccess: AuthSuccess) => {
        this.matSnackBar.open(authSuccess.response, 'Close', { duration: 3000 })
      },
      error: err => {
        console.error('An error occurred :', err);
        this.matSnackBar.open(`An error occurred !`, 'Close', { duration: 3000 });
      }
    });
  }
}
