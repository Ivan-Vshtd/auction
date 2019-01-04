import {Component, OnInit} from '@angular/core';
import {User} from '../models/user.model';
import {Router} from '@angular/router';
import {UserService} from '../service/user.service';

@Component({
  selector: 'app-list-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  users: User [];
  displayedColumns: string[] = ['firstName', 'lastName', 'email', 'edit', 'delete']; // this is the main option to show required table!

  constructor(private router: Router, private userService: UserService) {
  }

  ngOnInit(): void {
    this.userService
      .getUsers()
      .subscribe(data => {
        this.users = data;
      });
  }

  deleteUser(user: User): void {
    this.userService
      .deleteUser(user.id)
      .subscribe(data => {
        this.users = this.users.filter(requiredUser => requiredUser !== user);
      });
  }

  editUser(user: User): void {
    localStorage.removeItem('editUserId');
    localStorage.setItem('editUserId', user.id);
    this.router.navigate(['edit-user']);
  }

  addUser(): void {
    this.router.navigate(['add-user']);
  }

  crntUser(): string {
    return localStorage.getItem('currentUser');
  }
}
