import { Routes } from '@angular/router';
import {FrontOffice} from './features/front-office/front-office';
import {LoginComponent} from './features/login/login.component';

export const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'front-office', component: FrontOffice },
];
