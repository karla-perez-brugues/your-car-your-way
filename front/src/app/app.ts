import {Component, inject, OnInit} from '@angular/core';
import {Router, RouterLink, RouterOutlet} from '@angular/router';
import {AuthService} from './core/services/auth.service';
import {SessionService} from './core/services/session.service';
import {User} from './core/models/user';
import {Observable} from 'rxjs';
import {AsyncPipe} from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, AsyncPipe, RouterLink],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  private authService: AuthService = inject(AuthService);
  private sessionService: SessionService = inject(SessionService);
  private router: Router = inject(Router);

  ngOnInit() {
    this.autoLog();
  }

  public $isLogged(): Observable<boolean> {
    return this.sessionService.$isLogged();
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate(['/'])
  }

  private autoLog() {
    this.authService.me().subscribe({
      next: (user: User) => {
        this.sessionService.logIn(user);
      },
      error: error => {
        this.sessionService.logOut();
      }
    });
  }
}
