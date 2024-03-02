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

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [TopicsContainerComponent, ReactiveFormsModule, SpinLoaderComponent],
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss',
})
export class UserComponent {
  private store = inject(Store);

  private router = inject(Router);

  private cookiesService = inject(CookiesService);

  public userInfo = toSignal<UserBasicInfo>(this.store.select('userInfo'));

  public formBuilder = inject(FormBuilder);

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

    console.log(this.userCredentialsForm.getRawValue());
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
