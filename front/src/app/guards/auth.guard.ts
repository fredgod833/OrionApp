import {Injectable, inject} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot} from "@angular/router";
import { SessionService } from "../services/session.service";

export namespace AuthGuard {
  export const canActivate: CanActivateFn = (
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ) => {
    const router = inject(Router);
    const sessionService = inject(SessionService);
    if (!sessionService.isLogged) {
      router.navigate(['login']);
      return false;
    }
    return true;
  };
}
