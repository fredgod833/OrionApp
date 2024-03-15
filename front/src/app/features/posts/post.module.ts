import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatCardModule} from "@angular/material/card";
import {MatDividerModule} from "@angular/material/divider";
import {PostRoutingModule} from "./post-routing.module";
import {MatButtonModule} from "@angular/material/button";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatInputModule} from "@angular/material/input";
import {FormComponent} from './components/form/form.component';
import {ListComponent} from './components/list/list.component';
import {DetailComponent} from './components/detail/detail.component';
import {MatIconModule} from "@angular/material/icon";
import {ReactiveFormsModule} from "@angular/forms";
import {MatSelectModule} from "@angular/material/select";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";

const materialModule = [
  MatInputModule,
  MatIconModule,
  MatCardModule,
  MatDividerModule,
  MatButtonModule,
  MatSelectModule,
  MatProgressSpinnerModule
]

@NgModule({
  declarations: [FormComponent, ListComponent, DetailComponent],
    imports: [
        CommonModule,
        PostRoutingModule,
        FlexLayoutModule,
        ReactiveFormsModule,
        ...materialModule
    ],
  exports: [],
  providers: []
})
export class PostModule {
}
