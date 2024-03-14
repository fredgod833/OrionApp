import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ThemesComponent} from "./components/list/themes.component";
import {MatCardModule} from "@angular/material/card";
import {FlexLayoutModule} from "@angular/flex-layout";
import {ThemeRoutingModule} from "./theme-routing.module";

@NgModule({
  declarations: [ThemesComponent],
  imports: [
    CommonModule,
    ThemeRoutingModule,
    MatCardModule,
    FlexLayoutModule
  ],
  exports: [ThemesComponent]
})
export class ThemeModule { }
