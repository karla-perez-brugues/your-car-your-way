import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Conversation} from '../models/conversation';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BackOfficeService {

  private pathService = '/api/back-office';

  constructor(private httpClient: HttpClient) {}

  public home(): Observable<Conversation[]> {
    return this.httpClient.get<Conversation[]>(`${this.pathService}`);
  }
}
