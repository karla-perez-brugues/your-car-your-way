import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {LoginRequest} from '../../core/interfaces/loginRequest.interface';
import {ConversationService} from '../../core/services/conversation.service';
import {Message} from '../../core/models/message';
import {ActivatedRoute} from '@angular/router';
import {Conversation} from '../../core/models/conversation';
import {MessageService} from '../../core/services/message.service';

@Component({
  selector: 'app-single-conversation',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './single-conversation.html',
  styleUrl: './single-conversation.css'
})
export class SingleConversation implements OnInit {
  private fb: FormBuilder = inject(FormBuilder);
  private conversationService: ConversationService = inject(ConversationService);
  private route: ActivatedRoute = inject(ActivatedRoute);
  private messageService: MessageService = inject(MessageService);

  public conversation: Conversation | undefined;
  public messages: Message[] | undefined;

  public onError = false;

  public conversationId: string;

  public form = this.fb.group({
    content: ['',  [Validators.required, Validators.min(1)]]
  });

  constructor() {
    this.conversationId = this.route.snapshot.params['id'];
  }

  ngOnInit(): void {
    this.fetchConversation();
  }

  public back() {
    window.history.back();
  }

  public submit() {
    const message = this.form.value as Message;

    if (this.form.valid) {
      this.messageService.reply(message).subscribe({
        next: (message) => {
          this.messages?.push(message);
          this.form.reset();
        },
        error: error => this.onError = true,
      });
    }
  }

  private fetchConversation(): void {
    this.conversationService
      .show(this.conversationId)
      .subscribe(conversation => {
          this.conversation = conversation;
          this.messageService
            .listByConversation(this.conversationId)
            .subscribe(messages => {
              this.messages = messages;
            })
        }
      );
  }
}
