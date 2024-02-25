import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { CookiesService } from '@core/services/cookies.service';
import { CookieType } from '@lephenix47/cookies-utility';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  const cookiesService = inject(CookiesService);

  const jwtCookie: CookieType | null = cookiesService.getJwt();

  console.log('tokenInterceptor called', jwtCookie);

  const jwt: Partial<string> = jwtCookie?.value;

  if (!jwt) {
    return next(req);
  }

  const requestWithToken = req.clone({
    headers: req.headers.set('Authorization', `Bearer ${jwt}`),
  });

  return next(requestWithToken);
};
