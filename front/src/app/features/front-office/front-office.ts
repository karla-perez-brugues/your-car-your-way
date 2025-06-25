import {Component, inject} from '@angular/core';
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
export class FrontOffice {
  private frontOfficeService: FrontOfficeService = inject(FrontOfficeService)

  public conversation$: Observable<Conversation|null> = this.frontOfficeService.home();
}
