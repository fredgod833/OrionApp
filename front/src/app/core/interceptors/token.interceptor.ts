import { HttpInterceptorFn } from '@angular/common/http';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  // TODO: Copy the request then add the JWT from the cookies
  return next(req);
};
