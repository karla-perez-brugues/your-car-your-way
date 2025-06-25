import { Injectable, NgZone } from '@angular/core';
import { io, Socket } from 'socket.io-client';
import {Message} from "../models/message";

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  public socket: Socket | undefined;
  private readonly SERVER_URL = 'http://127.0.0.1:9093';
  private token = localStorage.getItem('token');

  constructor(private ngZone: NgZone) {}

  initializeWebSocketConnection(): void {
    this.ngZone.runOutsideAngular(() => {
      if (this.socket && this.socket.connected) {
        console.warn('WebSocket connection already established');
        return;
      }

      if (this.token) {
        this.socket = io(this.SERVER_URL, {
          timeout:10000,
          reconnectionAttempts: 3,
          query: {token: this.token},
          transports: ['websocket']
        });

        this.socket.on('connect', () => {
          console.log('Connected to WebSocket server');
        });

        this.socket.on('disconnect', (reason: string) => {
          console.warn('Disconnected from WebSocket server:', reason);
        });

        this.socket.on('connect_error', (error: any) => {
          console.error('WebSocket connection error:', error);
        });
      } else {
        console.error('Missing JWT token');
      }

    });
  }

  sendMessage(message: Message): void {
    if (this.socket && this.token) {
      this.socket.emit('sendMessage', message);
    } else {
      console.error('Missing JWT token');
    }
  }

  disconnect(): void {
    if (this.socket) {
      this.socket.disconnect();
      this.socket = undefined;
    }
  }

}
