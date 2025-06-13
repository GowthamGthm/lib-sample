import { Injectable, Injector } from "@angular/core";
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpContextToken,
} from "@angular/common/http";
import { Observable } from "rxjs";

export const BYPASS_INTERCEPTOR = new HttpContextToken<boolean>(() => false);

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private injector: Injector) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if (request.context.get(BYPASS_INTERCEPTOR)) {
      return next.handle(request);
    }

    const token = localStorage.getItem("authToken");

    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
    }

    return next.handle(request);
  }
}
