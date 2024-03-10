import {Component, OnDestroy, OnInit} from '@angular/core';
import {User} from "../../../interfaces/user.interface";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../../services/user.service";
import {SessionService} from "../../../services/session.service";
import {Router} from "@angular/router";
import {Theme} from "../../../features/articles/interfaces/theme";
import {Subject, takeUntil} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss']
})
export class UserDetailsComponent implements OnInit, OnDestroy {
  private destroy$: Subject<void> = new Subject<void>();

  public themes!: Theme[];
  public user!: User;
  public onError = false;

  public form: FormGroup = this.fb.group({
    username: ['', [Validators.required, Validators.min(3)]],
    email: ['', [Validators.required, Validators.email]]
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
      .subscribe((user: User) => {
        this.user = user
        this.form.controls['username'].setValue(this.user.username);
        this.form.controls['email'].setValue(this.user.email);
        this.themes = user.subscriptions;
      });
  }

  public submit(): void {
    this.userService.update(this.user.id, this.form).subscribe(data => {
      this.matSnackBar.open("changes saved", "Close", { duration: 3000 });
      this.form.markAsPristine();
    });
  }

  public logout() {
    this.sessionService.logOut();
    this.router.navigate([''])
  }

  public unSubscribe(id: number) {
    this.userService.unFollow(id)
      .pipe(takeUntil(this.destroy$))
      .subscribe(user => {
        this.themes = user.subscriptions;
      });
  }

  public canExit() {
    return this.form.dirty ? confirm('You have unsaved changes. Do you want to navigate away?') : true;
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
