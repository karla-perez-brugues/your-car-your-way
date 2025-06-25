import {Component, inject, NgZone, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {ConversationService} from '../../../core/services/conversation.service';
import {Message} from '../../../core/models/message';
import {ActivatedRoute} from '@angular/router';
import {Conversation} from '../../../core/models/conversation';
import {MessageService} from '../../../core/services/message.service';
import {AuthService} from '../../../core/services/auth.service';
import {User} from '../../../core/models/user';
import {BehaviorSubject, Observable} from 'rxjs';
import {WebSocketService} from '../../../core/services/websocket.service';
import {AsyncPipe} from '@angular/common';

@Component({
  selector: 'app-single-conversation',
  imports: [
    ReactiveFormsModule,
    AsyncPipe
  ],
  templateUrl: './single-conversation.html',
  styleUrl: './single-conversation.css'
})
export class SingleConversation implements OnInit, OnDestroy {
  private fb: FormBuilder = inject(FormBuilder);
  private route: ActivatedRoute = inject(ActivatedRoute);
  private messageService: MessageService = inject(MessageService);
  private authService: AuthService = inject(AuthService);
  private webSocketService: WebSocketService = inject(WebSocketService);
  private ngZone: NgZone = inject(NgZone);

  public user!: User;

  public onError = false;
  public title = 'Discussion avec le service client';

  public messagesBehaviorSubject: BehaviorSubject<Message[]> = new BehaviorSubject<Message[]>([]);
  public messages$: Observable<Message[]> = this.messagesBehaviorSubject.asObservable();
  public messages!: Message[];
  public conversationId: string;

  public form = this.fb.group({
    content: ['',  [Validators.required]]
  });

  constructor() {
    this.conversationId = this.route.snapshot.params['id'];
  }

  ngOnInit(): void {
    this.startWebSocket();
    this.fetchMessages();
    this.fetchUser();
  }

  ngOnDestroy(): void {
    this.webSocketService.socket?.off('chatMessage');
    this.webSocketService.disconnect();
  }

  public back() {
    window.history.back();
  }

  public submit() {
    const message = this.form.value as Message;
    message.conversationId = Number(this.conversationId);
    message.senderType = this.user.userType;

    if (this.form.valid) {
      this.webSocketService.sendMessage(message);
      this.form.reset();
    }
  }

  private fetchMessages(): void {
    this.messageService
      .listByConversation(this.conversationId)
      .subscribe(messages => {
        this.messagesBehaviorSubject.next(messages);
        this.messages = messages;
      });
  }

  private fetchUser(): void {
    this.authService.me().subscribe(user => {
      this.user = user;
      if (user.userType === 'ADMIN') {
        this.title = 'Discussion avec le client';
      }
    })
  }

  private startWebSocket(): void {
    if (this.webSocketService.socket?.connected) {
      this.webSocketService.disconnect();
    }

    this.webSocketService.initializeWebSocketConnection();

    this.webSocketService.socket?.on('connect', () => {
      console.log('Connected to WebSocket');
    });

    this.webSocketService.socket?.on('chatMessage', (message: Message) => {
      this.ngZone.run(() => {
        this.messagesBehaviorSubject.next([...this.messages, message]);
      });
    });

    this.webSocketService.socket?.on('connect_error', (error: any) => {
      console.error('Failed to connect to WebSocket:', error);
    });
  }
}
