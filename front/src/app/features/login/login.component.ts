import {Component, inject} from '@angular/core';
import {FormBuilder, FormsModule, Validators} from '@angular/forms';
import {LoginRequest} from '../../core/interfaces/loginRequest.interface';
import {AuthService} from '../../core/services/auth.service';
import {Router} from 'express';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private authService = inject(AuthService);

  public onError = false;

  public form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['',  [Validators.required, Validators.min(8)]]
  });

  public submit() {
    const loginRequest = this.form.value as LoginRequest;

    if (this.form.valid) {
      this.authService.login(loginRequest).subscribe({
        next: (response) => {
          this.router.navigate(['/home']);
        },
        error: error => this.onError = true,
      });
    }
  }
}
