import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LoginRequest} from '../interfaces/loginRequest.interface';
import {SessionInformation} from '../interfaces/sessionInformation.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = '/api/auth';

  constructor(private httpClient: HttpClient) {}

  public login(loginRequest: LoginRequest) {
    return this.httpClient.post(`${this.pathService}/login`, loginRequest);
  }

}
