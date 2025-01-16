import { Injectable } from '@angular/core';
import {
  ActivatedRoute,
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateChild,
  GuardResult,
  MaybeAsync,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class SameuseroradminGuard implements CanActivate, CanActivateChild {
  constructor(private router: Router, private authSvc: AuthService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): MaybeAsync<GuardResult> {
    let id = +route.params['id'];
    this.authSvc.user$.subscribe((user) => {
      if (this.authSvc.decodeRole() === 'ADMIN') {
        return true;
      } else if (user?.id !== id) {
        if (!localStorage.getItem('authData')) {
          setTimeout(() => {
            this.router.navigate(['/auth/login']);
          }, 100);
        } else {
          setTimeout(() => {
            this.authSvc.logout();
          }, 100);
        }
        return false;
      } else {
        return true;
      }
    });

    return true;
  }
  canActivateChild(
    childRoute: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): MaybeAsync<GuardResult> {
    return true;
  }
}
