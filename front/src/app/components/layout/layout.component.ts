import { Component, EventEmitter, Input, Output } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss'],
})
export class LayoutComponent {
  @Input() displayNavBar: boolean;
  public isConnected: boolean;
  public showMenu: BehaviorSubject<boolean>;
  @Output() parentMethodEvent = new EventEmitter();
  @Input() containerClass: string;

  constructor(private sessionService: SessionService) {
    this.displayNavBar = false;
    this.isConnected = false;
    this.showMenu = new BehaviorSubject<boolean>(false);
    this.containerClass = '';
  }

  public ngOnInit(): void {
    this.sessionService.$isLogged().subscribe({
      next: (isLogged: boolean) => {
        console.log('isLogged', isLogged);
        this.isConnected = isLogged;
      },
    });
  }

  getShowMenu(): boolean {
    return this.showMenu.value;
  }

  setShowMenu(value: boolean): void {
    this.showMenu.next(value);
  }

  public toggleMenu(): void {
    const currentValue = this.getShowMenu();
    this.showMenu.next(!currentValue);
  }
}
