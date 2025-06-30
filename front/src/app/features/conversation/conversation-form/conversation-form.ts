import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {Message} from '../../../core/models/message';
import {ConversationService} from '../../../core/services/conversation.service';
import {Router} from '@angular/router';
import {AuthService} from '../../../core/services/auth.service';

@Component({
  selector: 'app-conversation-form',
    imports: [
        FormsModule,
        ReactiveFormsModule
    ],
  templateUrl: './conversation-form.html',
  styleUrl: './conversation-form.css'
})
export class ConversationForm implements OnInit {
  private fb: FormBuilder = inject(FormBuilder);
  private conversationService: ConversationService = inject(ConversationService);
  private router: Router = inject(Router);
  private authService: AuthService = inject(AuthService);

  public onError = false;

  public form = this.fb.group({
    content: ['', Validators.required],
  })

  ngOnInit() {
    this.fetchUser();
  }

  public back() {
    window.history.back();
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

  private fetchUser() {
    this.authService.me().subscribe(user => {
      if (user.role === 'ROLE_ADMIN' || user.hasConversation) {
        this.router.navigate(['/']);
      }
    })
  }

}
