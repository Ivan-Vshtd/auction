import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {UserService} from './user.service';
import {User} from '../models/user.model';

@Injectable()
export class AuthenticationService {

  users: User [];
  loggedIn = false;

  private authUrl = 'http://localhost:8080/user-portal/users/authenticate';

  constructor(private http: HttpClient, private userService: UserService) {
  }

  login(email: string, password: string) {
    this.userService.getUsers().subscribe(data => {this.users = data;
    this.loggedIn = (this.users.filter(user => email === user.email).map(user => user.password)[0] === password);
      console.log(this.loggedIn);
      return this.loggedIn;
    });
    console.log('here ' + this.loggedIn);
    return this.loggedIn;
  }
}
