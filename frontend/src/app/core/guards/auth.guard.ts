import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { Observable, switchMap, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  
  constructor(
    private router: Router,
    private authService: AuthService
  ) { }

  canActivate(): Observable<boolean> {
    return this.authService.waitForInitialization().pipe(
      switchMap(() => this.authService.isAuthenticated()),
      tap(isAuthenticated => {
        if (!isAuthenticated) {
          console.log('AuthGuard: Not authenticated, redirecting to login');
          this.router.navigate(['/login']);
        }
      })
    );
  }
}