import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { AuthService } from "../../core/services/auth.service";
import { first } from "rxjs";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  loading = false;
  error = "";

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) {
    // Redirect to dashboard if already logged in
    // if (this.authService.currentUserValue) {
    //   this.router.navigate(['/dashboard']);
    // }

    this.authService.currentUser.pipe(first()).subscribe((user) => {
      if (user) {
        console.log('from login comp, logged in moving to dashboard');
        this.router.navigate(["/dashboard"]);
      }
    });
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ["", Validators.required],
      password: ["", Validators.required],
    });
  }

  get f() {
    return this.loginForm.controls;
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.error = "";

    this.authService
      .login(this.f["username"].value, this.f["password"].value)
      .subscribe({
        next: () => {
          this.router.navigate(["/dashboard"]);
        },
        error: (error) => {
          this.error = error.error || "Login failed";
          this.loading = false;
        },
      });
  }

  goToRegister(): void {
    this.router.navigate(["/register"]);
  }
}
