import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  constructor(private route: Router){}

  //User connection
  login(){
    return this.route.navigate(['/login']);
  }

  //User inscription 
  register(){
    return this.route.navigate(['register']);
  }
}
