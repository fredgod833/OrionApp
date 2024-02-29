import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { CookiesService } from '@core/services/cookies/cookies.service';
import { CookieType } from '@lephenix47/cookies-utility';

export const unauthGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);

  const cookiesService = inject(CookiesService);

  const jwtCookie: CookieType | null = cookiesService.getJwt();

  if (jwtCookie) {
    router.navigate(['/articles']);
    return false;
  } else {
    return true;
  }
};
