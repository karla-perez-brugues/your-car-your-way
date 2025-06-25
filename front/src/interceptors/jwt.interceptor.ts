import {HttpHandler, HttpHandlerFn, HttpInterceptor, HttpInterceptorFn, HttpRequest} from "@angular/common/http";
import { Injectable } from "@angular/core";

// @Injectable({ providedIn: 'root' })
// export class JwtInterceptor implements HttpInterceptor {
//
//   public intercept(request: HttpRequest<any>, next: HttpHandler) {
//     const token = localStorage.getItem('token');
//     if (token) {
//       request = request.clone({
//         setHeaders: {
//           Authorization: `Bearer ${token}`,
//         },
//       });
//     }
//     return next.handle(request);
//   }
// }

export const authenticationInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next: HttpHandlerFn) => {
  const userToken = localStorage.getItem('token');
  const modifiedReq = req. clone({
    headers: req.headers.set('Authorization', `Bearer ${userToken}`),
  });
  return next(modifiedReq);
};
