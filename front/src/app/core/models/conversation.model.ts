import {MessageModel} from './message.model';

export interface ConversationModel {
  id: number;
  customerFullName: string;
  status: string;
  messages: MessageModel[];
}
