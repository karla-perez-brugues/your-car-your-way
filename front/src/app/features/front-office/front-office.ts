import {Component, inject, OnInit} from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';
import {RouterLink} from '@angular/router';
import {Conversation} from '../../core/models/conversation';
import {FrontOfficeService} from '../../core/services/frontOffice.service';
import {Observable} from 'rxjs';
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
  private frontOfficeService: FrontOfficeService = inject(FrontOfficeService)

  // public conversation: Conversation | null | undefined;
  public conversation$: Observable<Conversation|null> = this.frontOfficeService.home();

  ngOnInit() {
    // this.fetchConversation();
  }

  // private fetchConversation() {
  //   this.frontOfficeService.home().subscribe({
  //     next: (conversation) => {
  //       this.conversation = conversation;
  //     }
  //   });
  // }
}
