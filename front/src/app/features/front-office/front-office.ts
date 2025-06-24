import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {Message} from '../../core/models/message';
import {ConversationService} from '../../core/services/conversation.service';
import {Router, RouterLink} from '@angular/router';
import {Conversation} from '../../core/models/conversation';
import {FrontOfficeService} from '../../core/services/frontOffice.service';

@Component({
  selector: 'app-front-office',
  imports: [
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './front-office.html',
  styleUrl: './front-office.css'
})
export class FrontOffice implements OnInit {
  private fb: FormBuilder = inject(FormBuilder);
  private frontOfficeService: FrontOfficeService = inject(FrontOfficeService);
  private conversationService: ConversationService = inject(ConversationService);
  private router: Router = inject(Router);

  public conversation: Conversation | null | undefined;

  public onError = false;

  public form = this.fb.group({
    message: ['', Validators.required, Validators.minLength(10), Validators.maxLength(3000)],
  })

  ngOnInit() {
    this.fetchConversation();
  }

  public submit() {
    const message = this.form.value as Message;

    if (this.form.valid) {
      this.conversationService.create(message).subscribe({
        next: (conversation) => {
          this.router.navigate(['/single-conversation', conversation.id]);
        },
        error: error => this.onError = true,
      });
    }
  }

  private fetchConversation() {
    this.frontOfficeService.home().subscribe({
      next: (conversation) => {
        this.conversation = conversation;
      }
    });
  }

}
