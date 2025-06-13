
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing-module';
import { AppComponent } from './app';

// Components
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { UsersComponent } from './components/users/users.component';
import { BooksComponent } from './components/books/books.component';
import { BorrowingComponent } from './components/borrowing/borrowing.component';
import { NavbarComponent } from './shared/navbar/navbar.component';

// Services and Guards
import { AuthService } from './core/services/auth.service';
import { UserService } from './core/services/user.service';
import { BookService } from './core/services/book.service';
import { BorrowingService } from './core/services/borrowing.service';
import { AuthGuard } from './core/guards/auth.guard';
import { AuthInterceptor } from './core/interceptors/auth.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DashboardComponent,
    UsersComponent,
    BooksComponent,
    BorrowingComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule
  ],
  providers: [
    AuthService,
    UserService,
    BookService,
    BorrowingService,
    AuthGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
