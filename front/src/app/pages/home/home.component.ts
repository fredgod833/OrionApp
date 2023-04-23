import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../core/services/api.service';
import { map } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {

  readonly test$;

  constructor(
    private apiService: ApiService
  ) {
    this.test$ = this.apiService.get('topic').pipe(map(v => JSON.stringify(v)));
  }

  ngOnInit(): void {}

  start() {
    alert('Commencez par lire le README et à vous de jouer !');
  }
}
