
import { Component, OnInit } from '@angular/core';
import { UserService } from '../../core/services/user.service';
import { User } from '../../core/models/user.model';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {
  users: User[] = [];
  showAddForm = false;
  newUser: User = {
    username: '',
    email: '',
    fullName: '',
    password: ''
  };
  loading = false;
  error = '';
  success = '';

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: (users) => {
        this.users = users;
      },
      error: (error) => {
        this.error = 'Failed to load users';
      }
    });
  }

  toggleAddForm(): void {
    this.showAddForm = !this.showAddForm;
    if (!this.showAddForm) {
      this.resetForm();
    }
  }

  resetForm(): void {
    this.newUser = {
      username: '',
      email: '',
      fullName: '',
      password: ''
    };
    this.error = '';
    this.success = '';
  }

  addUser(): void {
    if (!this.newUser.username || !this.newUser.email || !this.newUser.fullName || !this.newUser.password) {
      this.error = 'All fields are required';
      return;
    }

    this.loading = true;
    this.error = '';

    this.userService.createUser(this.newUser).subscribe({
      next: (user) => {
        this.users.push(user);
        this.success = 'User added successfully';
        this.resetForm();
        this.showAddForm = false;
        this.loading = false;
      },
      error: (error) => {
        this.error = error.error || 'Failed to add user';
        this.loading = false;
      }
    });
  }

  deleteUser(id: number): void {
    if (confirm('Are you sure you want to delete this user?')) {
      this.userService.deleteUser(id).subscribe({
        next: () => {
          this.users = this.users.filter(user => user.id !== id);
          this.success = 'User deleted successfully';
        },
        error: (error) => {
          this.error = 'Failed to delete user';
        }
      });
    }
  }
}
