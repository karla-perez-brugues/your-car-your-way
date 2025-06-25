import {HttpHandlerFn, HttpInterceptorFn, HttpRequest} from "@angular/common/http";

export const authenticationInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next: HttpHandlerFn) => {
  const userToken = localStorage.getItem('token');
  if (userToken) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${userToken}`,
      },
    });
  }

  return next(req);
};
