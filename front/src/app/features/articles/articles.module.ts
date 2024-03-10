import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ThemesComponent} from "./components/themes/themes.component";
import {MatCardModule} from "@angular/material/card";
import {MatDividerModule} from "@angular/material/divider";
import {ArticlesRoutingModule} from "./articles-routing.module";
import {MatButtonModule} from "@angular/material/button";
import {MatGridListModule} from "@angular/material/grid-list";
import {FlexLayoutModule} from "@angular/flex-layout";
import {ThemeApiService} from "./services/theme-api.service";
import {MatInputModule} from "@angular/material/input";

@NgModule({
  declarations: [ThemesComponent],
  imports: [
    CommonModule,
    ArticlesRoutingModule,
    MatCardModule,
    MatDividerModule,
    MatButtonModule,
    MatGridListModule,
    FlexLayoutModule,
    MatInputModule
  ],
  exports: [
    ThemesComponent
  ],
  providers: [ThemeApiService]
})
export class ArticlesModule {
}
