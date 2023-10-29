import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {  // Nom modifié pour refléter la fonctionnalité

  private apiUrl = 'http://localhost:8080/api/me';

  constructor(private http: HttpClient) { }

  // Méthode pour obtenir les informations de l'utilisateur actuel
  getCurrentUser(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }
}
