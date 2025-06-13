import { Injectable } from "@angular/core";
import { HttpClient, HttpContext, HttpHeaders } from "@angular/common/http";
import { BehaviorSubject, Observable, of } from "rxjs";
import { catchError, filter, first, map, tap } from "rxjs/operators";
import { environment } from "../../../environments/environment";
import { User } from "../models/user.model";
import { AppUtils } from "../utils/app.utils";
import { BYPASS_INTERCEPTOR } from "../interceptors/auth.interceptor";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private currentUserSubject: BehaviorSubject<User | null>;
  public currentUser: Observable<User | null>;
  private authToken: string | null = null;
  // private isInitialized = false;
  private isInitialized = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User | null>(null);
    this.currentUser = this.currentUserSubject.asObservable();

    // Initialize auth state
    this.initializeAuthState();
  }

  private initializeAuthState(): void {
    const token = localStorage.getItem("authToken");

    if (!token) {
      this.isInitialized.next(true); // Mark as initialized if no token
      return;
    }
    this.authToken = token;
    this.validateSession().subscribe({
      next: (user) => {
        this.currentUserSubject.next(user);
        this.isInitialized.next(true);
      },
      error: () => {
        this.clearSession();
        this.isInitialized.next(true);
      },
    });
  }

  register(user: User): Observable<User> {
    return this.http.post<User>(`${environment.apiUrl}/auth/register`, user);
  }

  login(username: string, password: string): Observable<User> {
    return this.http
      .post<User>(`${environment.apiUrl}/auth/login`, { username, password })
      .pipe(
        tap((response) => {
          if (response?.token) {
            this.setSession(response.token, response);
          }
        }),
        catchError((error) => {
          this.clearSession();
          throw error;
        })
      );
  }

  validateSession(): Observable<User | null> {
    const token = localStorage.getItem("authToken");
    if (!token) {
      return of(null);
    }

    return this.http
      .get<User>(`${environment.apiUrl}/auth/validate`, {
        headers: new HttpHeaders({
          Authorization: `Bearer ${token}`,
        }),
        context: new HttpContext().set(BYPASS_INTERCEPTOR, true),
      })
      .pipe(
        // tap((user) => this.currentUserSubject.next(user)),
        catchError((error) => {
          console.log("error in validate session: ", error);
          this.clearSession();
          throw error;
        })
      );
  }

  logout(): Observable<void> {
    return this.http
      .post<void>(
        `${environment.apiUrl}/auth/logout`,
        {},
        {
          headers: AppUtils.getHeaders(),
        }
      )
      .pipe(
        tap(() => this.clearSession()),
        catchError((error) => {
          this.clearSession();
          throw error;
        })
      );
  }

  private setSession(token: string, user: User): void {
    this.authToken = token;
    localStorage.setItem("authToken", token);
    this.currentUserSubject.next(user);
  }

  clearSession(): void {
    localStorage.removeItem("authToken");
    this.authToken = null;
    this.currentUserSubject.next(null);
  }

  getAuthToken(): string | null {
    return this.authToken;
  }

  isAuthenticated(): Observable<boolean> {
    return this.currentUser.pipe(map((user) => !!user));
  }

  waitForInitialization(): Observable<boolean> {
    return this.isInitialized.pipe(
      filter((initialized) => initialized),
      first()
    );
  }

}