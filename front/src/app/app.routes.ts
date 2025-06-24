import { Routes } from '@angular/router';
import {FrontOffice} from './features/front-office/front-office';
import {LoginComponent} from './features/login/login.component';
import {BackOffice} from './features/back-office/back-office';
import {SingleConversation} from './features/single-conversation/single-conversation';

export const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'front-office', component: FrontOffice },
  { path: 'back-office', component: BackOffice },
  { path: 'single-conversation', component: SingleConversation }, // fixme: add conversation id to path
];
