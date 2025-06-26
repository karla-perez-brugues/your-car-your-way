import {Message} from './message';

export interface Conversation {
  id: number;
  customerFullName: string;
  status: string;
  lastMessageContent: string;
}
