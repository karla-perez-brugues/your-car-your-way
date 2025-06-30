import {inject, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {User} from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class RedirectionService {
  private router = inject(Router);

  public redirectUser(user: User) {
    if (user.role === 'ROLE_ADMIN') {
      this.router.navigate(['/back-office']);
    } else {
      this.router.navigate(['/front-office']);
    }
  }

}
