import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanDeactivate, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {UserDetailsComponent} from "../pages/account/user-details/user-details.component";

@Injectable({
  providedIn: 'root'
})
export class CanComponentDeactivateGuard implements CanDeactivate<UserDetailsComponent> {
  canDeactivate(component: UserDetailsComponent, currentRoute: ActivatedRouteSnapshot, currentState: RouterStateSnapshot, nextState?: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return component.canExit();
  }

}
