
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  loading = false;
  error = '';
  success = '';

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      fullName: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, { validators: this.passwordMatchValidator });
  }

  passwordMatchValidator(form: FormGroup) {
    const password = form.get('password');
    const confirmPassword = form.get('confirmPassword');
    if (password && confirmPassword && password.value !== confirmPassword.value) {
      confirmPassword.setErrors({ passwordMismatch: true });
    } else {
      confirmPassword?.setErrors(null);
    }
    return null;
  }

  get f() { return this.registerForm.controls; }

  onSubmit(): void {
    if (this.registerForm.invalid) {
      return;
    }

    this.loading = true;
    this.error = '';
    this.success = '';

    const user = {
      username: this.f['username'].value,
      email: this.f['email'].value,
      fullName: this.f['fullName'].value,
      password: this.f['password'].value
    };

    this.authService.register(user)
      .subscribe({
        next: () => {
          this.success = 'Registration successful! You can now login.';
          this.loading = false;
          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 2000);
        },
        error: (error) => {
          this.error = error.error || 'Registration failed';
          this.loading = false;
        }
      });
  }

  goToLogin(): void {
    this.router.navigate(['/login']);
  }
}
