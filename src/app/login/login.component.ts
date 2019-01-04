import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {UserService} from '../service/user.service';
import {User} from '../models/user.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  submitted = false;
  invalidLogin = false;
  currentUser: User;
  users: User[];

  constructor(private formBuilder: FormBuilder, private router: Router, private userService: UserService) {
  }

  onSubmit() {
    this.submitted = true;
    if (this.loginForm.invalid) {
      return;
    }
    if (this.login(this.loginForm.controls.email.value, this.loginForm.controls.password.value)) {
      this.router.navigate(['list-auctions']);
    } else {
      this.invalidLogin = true;
      alert('Email or password is invalid!');
    }
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    });
    this.userService
      .getUsers()
      .subscribe(data => this.users = data);
  }

  login(email: string, password: string) {  // simple user check logic (just a crutch)
    this.currentUser = this.users
      .filter(user => email === user.email)
      .filter(user => password === user.password)[0];

    localStorage.removeItem('currentUser');
    localStorage.setItem('currentUser', this.currentUser.firstName);

    return this.currentUser.password === password;
  }
}
