import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatCardModule} from "@angular/material/card";
import {MatDividerModule} from "@angular/material/divider";
import {PostRoutingModule} from "./post-routing.module";
import {MatButtonModule} from "@angular/material/button";
import {MatGridListModule} from "@angular/material/grid-list";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatInputModule} from "@angular/material/input";
import {FormComponent} from './components/form/form.component';
import {ListComponent} from './components/list/list.component';
import {DetailComponent} from './components/detail/detail.component';
import {MatIconModule} from "@angular/material/icon";
import {ReactiveFormsModule} from "@angular/forms";
import {MatSelectModule} from "@angular/material/select";

@NgModule({
  declarations: [FormComponent, ListComponent, DetailComponent],
  imports: [
    CommonModule,
    PostRoutingModule,
    MatCardModule,
    MatDividerModule,
    MatButtonModule,
    MatGridListModule,
    FlexLayoutModule,
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
    MatSelectModule
  ],
  exports: [],
  providers: []
})
export class PostModule {
}
