
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }


  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${environment.privateUrL}/users`);
  }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(`${environment.privateUrL}/users/${id}`);
  }

  createUser(user: User): Observable<User> {
    return this.http.post<User>(`${environment.privateUrL}/users`, user);
  }

  updateUser(id: number, user: User): Observable<User> {
    return this.http.put<User>(`${environment.privateUrL}/users/${id}`, user);
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete(`${environment.privateUrL}/users/${id}`);
  }


}