import { Component } from '@angular/core';
import { UserService } from  'src/app/services/user.service'
import { User } from 'src/app/interfaces/user.interface';
import { SessionService } from 'src/app/services/session.service';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent {
  public user?: User;
  public hide = true;
  public onError = false;

  public form;

  constructor(
    private userService: UserService,
    private sessionService: SessionService,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      name: [
        '',
        [Validators.required, Validators.min(3), Validators.max(20)],
      ],
    });
  }

  public ngOnInit(): void {
    console.log('MeComponent');
    this.userService.getUser().subscribe({
      next: (user: User) => {
        this.user = user;
        this.form.setValue({ email: user.email, name: user.name });
        console.log('user', this.user);
        console.log('user topic', this.user?.topics);
      },
    });
  }

  public submit(): void {
    if (this.form.value.email != this.user?.email || this?.form.value.name != this.user?.name) {
      const putRequest = this.form.value as Partial<User>;
      this.userService.updateUser(putRequest).subscribe({
        next: (response: User) => {
          if (this.form.value.email != this.user?.email)
            this.logout();
          else
            this.user = response;
        },
        error: (error) => console.error(error),
      });
    }
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate(['/login']);
  }
}
