import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Post} from "../../interfaces/post";
import {PostApiService} from "../../services/post-api.service";
import {Subject, takeUntil} from "rxjs";
import {ThemeApiService} from "../../../themes/services/theme-api.service";
import {Theme} from "../../../themes/interfaces/theme";

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit, OnDestroy {
  private destroy$: Subject<void> = new Subject<void>();

  public onError: boolean = false;
  public themes!: Theme[];

  public form: FormGroup = this.fb.group({
    theme: [
      '',
      Validators.required,
    ],
    title: [
      '',
      [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(20)
      ]
    ],
    content: [
      '',
      [
        Validators.required,
        Validators.minLength(20),
        Validators.maxLength(2000)
      ]
    ]
  });

  constructor(
    private fb: FormBuilder,
    private matSnackBar: MatSnackBar,
    private postApiService: PostApiService,
    private router: Router,
    private themeApiService: ThemeApiService,
  ) {
  }

  public submit(): void {
    this.postApiService.create(this.form)
      .pipe(takeUntil(this.destroy$))
      .subscribe((_: Post) => {
        this.form.markAsPristine();
        this.exitPage();
      });
  }

  private exitPage(): void {
    this.matSnackBar.open('Post created !', 'Close', {duration: 3000});
    this.router.navigate(['posts']).then(() => console.log("Redirected to posts page"));
  }

  public back(): void {
    this.router.navigate(['/posts']).then(() => console.log("Navigated to posts page"));
  }

  public canExit() {
    return this.form.dirty ? confirm('You have unsaved changes. Do you want to navigate away?') : true;
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  ngOnInit(): void {
    this.themeApiService.all()
      .pipe(takeUntil(this.destroy$))
      .subscribe((themes: Theme[]) => {
        this.themes = themes;
      });
  }
}
