import {Component, inject, OnInit} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {BackOfficeService} from '../../core/services/backOffice.service';
import {BehaviorSubject, Observable} from 'rxjs';
import {Conversation} from '../../core/models/conversation';
import {AsyncPipe} from '@angular/common';

@Component({
  selector: 'app-back-office',
  imports: [
    RouterLink,
    AsyncPipe
  ],
  templateUrl: './back-office.html',
  styleUrl: './back-office.css'
})
export class BackOffice implements OnInit {
  private backOfficeService: BackOfficeService = inject(BackOfficeService);
  private router: Router = inject(Router);

  public conversationsSubject: BehaviorSubject<Conversation[]> = new BehaviorSubject<Conversation[]>([]);
  public conversations$: Observable<Conversation[]> = this.conversationsSubject.asObservable();

  ngOnInit() {
    this.fetchConversations();
  }

  private fetchConversations() {
    this.backOfficeService.home().subscribe({
      next: conversations => {
        this.conversationsSubject.next(conversations)
      },
      error: () => {
        this.router.navigate(['/']);
      }
    })
  }
}
