import {Component, inject} from '@angular/core';
import {FormBuilder, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {Message} from '../../../core/models/message';
import {FrontOfficeService} from '../../../core/services/frontOffice.service';
import {ConversationService} from '../../../core/services/conversation.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-conversation-form',
    imports: [
        FormsModule,
        ReactiveFormsModule
    ],
  templateUrl: './conversation-form.html',
  styleUrl: './conversation-form.css'
})
export class ConversationForm {
  private fb: FormBuilder = inject(FormBuilder);
  private conversationService: ConversationService = inject(ConversationService);
  private router: Router = inject(Router);

  public onError = false;

  public form = this.fb.group({
    content: ['', Validators.required],
  })

  public back() {
    window.history.back();
  }

  public submit() {
    const message = this.form.value as Message;
    console.log(message);

    if (this.form.valid) {
      this.conversationService.create(message).subscribe({
        next: (conversation) => {
          this.router.navigate(['/single-conversation', conversation.id]);
        },
        error: error => this.onError = true,
      });
    }
  }

}
