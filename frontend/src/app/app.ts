import { Component, OnInit } from "@angular/core";
import { AuthService } from "./core/services/auth.service";
import { Observable } from "rxjs";
import { Router } from "@angular/router";

@Component({
  selector: "app-root",
  templateUrl: "./app.html",
  styleUrls: ["./app.scss"],
})
export class AppComponent implements OnInit {
  title = "Library Management System";
  isAuthenticated$: Observable<boolean>;

  constructor(private authService: AuthService, private router: Router) {
    this.isAuthenticated$ = this.authService.isAuthenticated();
  }

  ngOnInit(): void {
    // Validate session on app start
    this.authService.validateSession()
    .subscribe({
      next: (user) => {
        if (user) {
          console.log('Session valid', user);
        } else {
          console.log('No valid session');
          this.router.navigate(["/login"]);
        }
      },
      error: (err) => {
        console.error('Validation error', err);
        this.router.navigate(["/login"]);
      }
    });
  }

}