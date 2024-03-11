import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router";
import {SessionService} from "../services/session.service";

@Injectable({providedIn: 'root'})
export class UnAuthGuard implements CanActivate {

  constructor(
    private router: Router,
    private sessionService: SessionService,
  ) {
  }

  public canActivate(): boolean {
    if (this.sessionService.isLogged) {
      this.router.navigate(['articles']).then(() => console.log("Redirected to articles page"));
      return false;
    }
    return true;
  }
}
