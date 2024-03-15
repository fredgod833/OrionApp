import {Component, OnDestroy, OnInit} from '@angular/core';
import {User} from "../../interfaces/user.interface";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {SessionService} from "../../services/session.service";
import {Router} from "@angular/router";
import {Theme} from "../../features/themes/interfaces/theme";
import {Subject, takeUntil} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MessageResponse} from "../../interfaces/message-response";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss']
})
export class UserDetailsComponent implements OnInit, OnDestroy {
  private destroy$: Subject<void> = new Subject<void>();

  public themes: Theme[] = [];
  public user!: User;
  public error: string = '';
  public isLoading: boolean = true;
  public form: FormGroup = this.fb.group({
    username: [
      '',
      [
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9_]{6,20}$'),
        Validators.minLength(6),
        Validators.maxLength(30)
      ]
    ],
    email: [
      '',
      [
        Validators.required,
        Validators.pattern('^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$'),
        Validators.maxLength(60)
      ]
    ],
  });

  constructor(private fb: FormBuilder,
              private userService: UserService,
              private sessionService: SessionService,
              private router: Router,
              private matSnackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.userService.getUser()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (user: User) => {
          this.user = user
          this.form.controls['username'].setValue(this.user.username);
          this.form.controls['email'].setValue(this.user.email);
          this.themes = user.subscriptions;
          this.isLoading = false;
        },
        error: (err: HttpErrorResponse) => this.error = err.error.message
      });
  }

  public submit(): void {
    this.userService.update(this.user.id, this.form)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (message: MessageResponse) => {
          console.log(message);
          this.matSnackBar.open("changes saved", "Close", {duration: 3000});
          this.form.markAsPristine();
        },
        error: (err: HttpErrorResponse) => this.error = err.error.message
      });
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate(['']).then(() => console.log("You are logged out"));
  }

  public unSubscribe(id: number): void {
    this.userService.unSubscribe(id)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (user: User) => {
          this.themes = user.subscriptions;
        },
        error: (err: HttpErrorResponse) => this.error = err.error.message
      });
  }

  public canExit(): boolean {
    return this.form.dirty ? confirm('You have unsaved changes. Do you want to navigate away?') : true;
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
