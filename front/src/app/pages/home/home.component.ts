import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  constructor(private route: Router){}

  //Log user in
  login():Promise<boolean>{
    return this.route.navigate(['/login']);
  }

  //Register user
  register():Promise<boolean>{
    return this.route.navigate(['register']);
  }
}
