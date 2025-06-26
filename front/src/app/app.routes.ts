import { Routes } from '@angular/router';
import {LoginComponent} from './features/login/login.component';
import {FrontOffice} from './features/front-office/front-office';
import {ConversationForm} from './features/conversation/conversation-form/conversation-form';
import {SingleConversation} from './features/conversation/single-conversation/single-conversation';
import {BackOffice} from './features/back-office/back-office';

export const routes: Routes = [
  {
    path: '',
    component: LoginComponent,
    title: 'Your Car Your Way'
  },
  {
    path: 'front-office',
    component: FrontOffice,
    title: 'Your Car Your Way'
  },
  {
    path: 'new-conversation',
    component: ConversationForm,
    title: 'Contactez le service client'
  },
  {
    path: 'back-office',
    component: BackOffice,
    title: 'Back Office'
  },
  {
    path: 'single-conversation/:id',
    component: SingleConversation,
    title: 'Conversation'
  },
];
