import { Component, OnInit } from '@angular/core';
import { UserService } from  '../../services/me.service';  // Importez le service UserService

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {

  currentUser: any;  // Contiendra les informations de l'utilisateur une fois chargées

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.loadCurrentUser();
  }

  loadCurrentUser() {
    this.userService.getCurrentUser().subscribe(
      data => {
        this.currentUser = data;  // Les données du back-end sont affectées à currentUser
      },
      error => {
        // Gérez ici les erreurs, par exemple en affichant un message d'erreur à l'utilisateur
        console.error('There was an error loading the user data!', error);
      }
    );
  }
}
