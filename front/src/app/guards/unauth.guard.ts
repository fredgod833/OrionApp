import {Injectable, inject} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot} from "@angular/router";
import { SessionService } from "../services/session.service";

@Injectable({providedIn: 'root'})
export namespace UnauthGuard {
  const canActivate: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  const router = inject(Router);
  const sessionService = inject(SessionService);
  if (sessionService.isLogged) {
    router.navigate(['rentals']);
    return false;
  }
  return true;
  };
}
