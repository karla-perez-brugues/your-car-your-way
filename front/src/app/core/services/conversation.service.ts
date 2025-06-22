import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Conversation} from '../models/conversation';
import {Message} from '../models/message';

@Injectable({
  providedIn: 'root'
})
export class ConversationService {

  private pathService = '/api/conversation';

  constructor(private httpClient: HttpClient) {}

  public show(id: string): Observable<Conversation> {
    return this.httpClient.get<Conversation>(`${this.pathService}/${id}`);
  }

  public create(message: Message): Observable<Conversation> {
    return this.httpClient.post<Conversation>(`${this.pathService}}`, message);
  }
}
