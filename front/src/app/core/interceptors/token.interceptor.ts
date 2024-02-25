import { HttpInterceptorFn } from '@angular/common/http';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  // TODO: Create a cookie service to retrieve the JWT
  // TODO: Copy the request then add the JWT to the headers of the request

  const requestWithToken = req.clone({
    headers: req.headers.set('Authorization', `Bearer `),
  });

  return next(requestWithToken);
};
