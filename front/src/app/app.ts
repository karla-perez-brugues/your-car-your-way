import {Component, inject, OnInit} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {AuthService} from './core/services/auth.service';
import {User} from './core/models/user';
import {SessionService} from './core/services/session.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  private authService: AuthService = inject(AuthService);
  private sessionService: SessionService = inject(SessionService);

  public ngOnInit() {
    this.autoLog();
  }

  private autoLog() {
    this.authService.me().subscribe({
      next: (user: User) => {
        this.sessionService.logIn(user);
      },
      error: error => {
        this.sessionService.logOut();
      }
    })
  }

}
