import { NgModule } from '@angular/core';
import { RouterModule, Routes } from "@angular/router";
import { ListComponent } from "./components/list/list.component";

const routes: Routes = [
  { title: 'Topics', path: '', component: ListComponent },
  { title: 'Topics - detail', path: 'detail/:id', component: ListComponent },
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ],
})
export class TopicRoutingModule {}
