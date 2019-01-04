import {Component, OnInit} from '@angular/core';
import {User} from '../models/user.model';
import {FormGroup, FormBuilder, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {UserService} from '../service/user.service';
import {first} from 'rxjs/operators';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

  user: User;
  editForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private router: Router, private userService: UserService) {
  }

  ngOnInit() {
    let userId = localStorage.getItem('editUserId');

    if (!userId) {
      alert('Invalid action!');
      this.router.navigate(['list-user']);
      return;
    }
    this.editForm = this.formBuilder.group({
      id: [],
      email: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      password: ['', Validators.required]
    });
    this.userService
      .getUserById(userId)
      .subscribe(data => {
        this.editForm.setValue(data);
      });
  }

  onSubmit() {
    this.userService
      .updateUser(this.editForm.value)
      .pipe(first())
      .subscribe(data => {
          this.router
            .navigate(['list-user']);
        },
        error => {
          alert(error);
        });
  }

}
