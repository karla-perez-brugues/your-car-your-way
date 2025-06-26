import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router, RouterOutlet} from '@angular/router';
import {AuthService} from './core/services/auth.service';
import {SessionService} from './core/services/session.service';
import {User} from './core/models/user';
import {AsyncPipe} from '@angular/common';
import {Title} from '@angular/platform-browser';
import {filter, map} from 'rxjs';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, AsyncPipe],
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
    });
  }
}
