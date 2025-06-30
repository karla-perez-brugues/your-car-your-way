import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {LoginRequest} from '../../core/interfaces/loginRequest.interface';
import {AuthService} from '../../core/services/auth.service';
import {Router} from '@angular/router';
import {SessionInformation} from '../../core/interfaces/sessionInformation.interface';
import {User} from '../../core/models/user';
import {SessionService} from '../../core/services/session.service';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './login.component.html',
  standalone: true,
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private authService = inject(AuthService);
  private sessionService = inject(SessionService);

  public onError = false;

  public form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['',  [Validators.required, Validators.min(8)]]
  });

  ngOnInit() {
    this.sessionService.$isLogged().subscribe(isLogged => {
      if (isLogged) {
        this.authService.me().subscribe(user => {
          this.redirectUser(user);
        })
      }
    })
  }

  public submit() {
    const loginRequest = this.form.value as LoginRequest;

    if (this.form.valid) {
      this.authService.login(loginRequest).subscribe({
        next: (sessionInformation: SessionInformation) => {
          localStorage.setItem('token', sessionInformation.token);
          this.authService.me().subscribe({
            next: (user: User) => {
              this.sessionService.logIn(user);
              this.redirectUser(user);
            },
            error: () => this.onError = true,
          });
        },
        error: () => this.onError = true,
      });
    }
  }

  private redirectUser(user: User) {
    if (user.role === 'ROLE_ADMIN') {
      this.router.navigate(['/back-office']);
    } else {
      this.router.navigate(['/front-office']);
    }
  }
}
