import { inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
} from '@angular/router';

export const authGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) => {
  const router = inject(Router);

  if (true) {
    // TODO: Create an auth service
    // TODO: Check if the user is logged in from the service 'isLoggedIn' property
    return true;
  } else {
    router.navigate(['/login']);
    return false;
  }
};
