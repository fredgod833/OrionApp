import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-sidenav-list',
  templateUrl: './sidenav-list.component.html',
  styleUrls: ['./sidenav-list.component.scss']
})
export class SidenavListComponent {
  @Output() sidenavClose: EventEmitter<any> = new EventEmitter();

  constructor() {
  }

  public onSidenavClose = (): void => {
    this.sidenavClose.emit();
  }
}
