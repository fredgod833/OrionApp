import {
  Component,
  ElementRef,
  ViewChild,
  computed,
  inject,
  signal,
} from '@angular/core';
import { Topic } from '@core/types/topic.type';
import { TopicsContainerComponent } from '@components/shared/topics-container/topics-container.component';
import { toSignal } from '@angular/core/rxjs-interop';
import { UserBasicInfo } from '@core/types/user.type';
import { Store } from '@ngrx/store';
import { SpinLoaderComponent } from '@components/shared/spin-loader/spin-loader.component';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CookiesService } from '@core/services/cookies/cookies.service';
import { UserService } from '@core/services/user/user.service';
import { TopicService } from '@core/services/topic/topic.service';
import { Message } from '@core/types/message.type';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [TopicsContainerComponent, ReactiveFormsModule, SpinLoaderComponent],
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss',
})
export class UserComponent {
  // * Services
  private router = inject(Router);

  private store = inject(Store);

  private cookiesService = inject(CookiesService);

  private userService = inject(UserService);

  private topicsService = inject(TopicService);

  public formBuilder = inject(FormBuilder);

  // * Signals
  public userInfo = toSignal<UserBasicInfo>(this.store.select('userInfo'));

  public topicsAreLoading = toSignal<boolean>(this.topicsService.isLoading$);

  public topicsHaveAnError = toSignal<boolean>(this.topicsService.hasError$);

  public topicsErrorMessage = toSignal<string>(
    this.topicsService.errorMessage$
  );

  public userUpdateIsLoading = toSignal<boolean>(this.userService.isLoading$);

  public userHasAnError = toSignal<boolean>(this.userService.hasError$);

  public userErrorMessage = toSignal<string>(this.userService.errorMessage$);

  public userSuccessMessage = signal<string>('');

  public topicsArray = signal<Topic[]>([]);

  public userCredentialsForm = this.formBuilder.group({
    username: [this.userInfo()?.username, Validators.required],
    email: [this.userInfo()?.email, [Validators.required]],
  });

  constructor() {
    this.updateUserThemeSubscription =
      this.updateUserThemeSubscription.bind(this);
  }

  ngOnInit() {
    console.log('ngOnInit', this.userInfo());
  }

  onSubmit(event: Event): void {
    event.preventDefault();

    const { username, email } = this.userCredentialsForm.getRawValue();

    const subscription: Subscription = this.userService
      .updateUser({
        username: username as string,
        email: email as string,
      })
      .subscribe((result: Message) => {
        this.userSuccessMessage.update(() => {
          return result.message;
        });

        subscription.unsubscribe();
      });
  }

  public logout(): void {
    this.cookiesService.deleteJwt();

    this.router.navigate(['/']);
  }

  updateUserThemeSubscription(id?: number) {
    console.log('PARENT Clicked subs toggle btn', this);

    this.topicsArray.update((topics) => topics.filter((t) => t.id !== id));
  }
}
