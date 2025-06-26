import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Message} from '../models/message';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  private pathService = '/api/message';

  constructor(private httpClient: HttpClient) {}

  public reply(message: Message, conversationId: string): Observable<Message> {
    return this.httpClient.post<Message>(`${this.pathService}/${conversationId}`, message);
  }

  public listByConversation(conversationId: string): Observable<Message[]> {
    return this.httpClient.get<Message[]>(`${this.pathService}/${conversationId}`);
  }
}
