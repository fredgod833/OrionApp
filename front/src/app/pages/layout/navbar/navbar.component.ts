import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Router} from "@angular/router";
import {SessionService} from "../../../services/session.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  @Output() public sidenavToggle = new EventEmitter();

  constructor(private sessionService: SessionService) { }

  ngOnInit(): void {
  }

  public onToggleSidenav = () => {
    this.sidenavToggle.emit();
  }

  public $isLogged(): Observable<boolean> {
    return this.sessionService.$isLogged();
  }
}
