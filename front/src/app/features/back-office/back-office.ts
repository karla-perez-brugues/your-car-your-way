import {Component, inject} from '@angular/core';
import {RouterLink} from '@angular/router';
import {BackOfficeService} from '../../core/services/backOffice.service';
import {Observable} from 'rxjs';
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
export class BackOffice {
  private backOfficeService: BackOfficeService = inject(BackOfficeService);

  public conversations$: Observable<Conversation[]> = this.backOfficeService.home();
}
