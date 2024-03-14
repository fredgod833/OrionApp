import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Post} from "../../interfaces/post";
import {PostApiService} from "../../services/post-api.service";
import {Observable} from "rxjs";
import {ThemesResponse} from "../../../themes/interfaces/api/themesResponse";
import {ThemeApiService} from "../../../themes/services/theme-api.service";

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent {
  public onError = false;
  public themes$: Observable<ThemesResponse> = this.themeApiService.all();

  public form = this.fb.group({
    theme: [
      [
        Validators.required,
      ]
    ],
    title: [
      '',
      [
        Validators.required,
        Validators.max(20)
      ]
    ],
    content: [
      '',
      [
        Validators.required,
        Validators.max(2000)
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
    console.log(this.form.value);
    this.postApiService.create(this.form).subscribe((_: Post) => this.exitPage());
  }

  private exitPage(): void {
    this.matSnackBar.open('Post created !', 'Close', {duration: 3000});
    this.router.navigate(['posts']);
  }

  public back(): void {
    window.history.back();
  }
}
