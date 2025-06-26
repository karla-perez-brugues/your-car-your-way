import {Component, inject, OnInit} from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import {Conversation} from '../../core/models/conversation';
import {FrontOfficeService} from '../../core/services/frontOffice.service';
import {BehaviorSubject, Observable} from 'rxjs';
import {AsyncPipe} from '@angular/common';

@Component({
  selector: 'app-front-office',
  imports: [
    ReactiveFormsModule,
    RouterLink,
    AsyncPipe
  ],
  templateUrl: './front-office.html',
  standalone: true,
  styleUrl: './front-office.css'
})
export class FrontOffice implements OnInit {
  private frontOfficeService: FrontOfficeService = inject(FrontOfficeService);
  private router: Router = inject(Router);

  public conversationSubject = new BehaviorSubject<Conversation|null>(null);
  public conversation$: Observable<Conversation|null> = this.conversationSubject.asObservable();

  ngOnInit(): void {
    this.fetchConversation();
  }

  private fetchConversation() {
    this.frontOfficeService.home().subscribe({
      next: conversation => {
        this.conversationSubject.next(conversation);
      },
      error: () => {
        this.router.navigate(['/']);
      }
    })
  }
}
