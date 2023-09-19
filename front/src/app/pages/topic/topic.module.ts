import {LOCALE_ID, NgModule} from '@angular/core';
import {CommonModule, registerLocaleData} from '@angular/common';
import { ListComponent } from './components/list/list.component';
import {TopicRoutingModule} from "./topic-routing.module";
import localeFr from '@angular/common/locales/fr';
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {MatTooltipModule} from "@angular/material/tooltip";
registerLocaleData(localeFr);
@NgModule({
  declarations: [
    ListComponent,
  ],
  imports: [
    CommonModule,
    TopicRoutingModule,
    MatButtonModule,
    MatCardModule,
    MatTooltipModule
  ],
  providers: [
    {
      provide: LOCALE_ID,
      useValue: 'fr-FR'
    },
  ]
})
export class TopicModule { }
