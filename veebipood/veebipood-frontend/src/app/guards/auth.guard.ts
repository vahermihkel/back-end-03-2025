import { inject } from '@angular/core';
import { CanActivateChildFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { catchError, map, of } from 'rxjs';

export const authGuard: CanActivateChildFn = (childRoute, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  return authService.admin.pipe(
    map(loggedIn => {
      if (loggedIn) {
        return true;
      } else {
        router.navigateByUrl("/login");
        return false;
      }
    })
  );

  // return sessionStorage.getItem("token") !== null;
};
