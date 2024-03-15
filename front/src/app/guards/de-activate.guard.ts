import {Injectable} from '@angular/core';
import {CanDeactivate} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class DeActivateGuard implements CanDeactivate<any> {
  canDeactivate(component: any) {
    return component.canExit();
  }

}
