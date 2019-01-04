import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {User} from '../models/user.model';

@Injectable()
export class UserService {


  constructor(private http: HttpClient) {
  }

  // private baseUrl = '/api';
  private baseUrl = 'http://localhost:8080/user-portal/users';

  public getUsers() {
    return this.http
      .get<User[]>(this.baseUrl);
  }

  public deleteUser(id: string) {
    return this.http
      .delete(this.baseUrl + '/' + id);
  }

  public createUser(user: User) {
    return this.http
      .post<User>(this.baseUrl, user);
  }

  public updateUser(user: User) {
    return this.http
      .put<User>(this.baseUrl + '/' + user.id, user);
  }

  public getUserById(id: string) {
    return this.http
      .get<User>(this.baseUrl + '/' + id);
  }
}
