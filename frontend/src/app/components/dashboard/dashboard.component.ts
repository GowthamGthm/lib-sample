import { Component, OnInit } from "@angular/core";
import { AuthService } from "../../core/services/auth.service";
import { BookService } from "../../core/services/book.service";
import { UserService } from "../../core/services/user.service";
import { BorrowingService } from "../../core/services/borrowing.service";
import { User } from "../../core/models/user.model";
import { Book } from "../../core/models/book.model";
import { BorrowedBook } from "../../core/models/borrowed-book.model";
import { Observable } from "rxjs";

@Component({
  selector: "app-dashboard",
  templateUrl: "./dashboard.component.html",
  styleUrls: ["./dashboard.component.scss"],
})
export class DashboardComponent implements OnInit {
  currentUser$: Observable<User | null>;
  totalBooks = 0;
  totalUsers = 0;
  activeBorrowings = 0;
  availableBooks = 0;
  recentBooks: Book[] = [];
  recentBorrowings: BorrowedBook[] = [];

  constructor(
    private authService: AuthService,
    private bookService: BookService,
    private userService: UserService,
    private borrowingService: BorrowingService
  ) {
    this.currentUser$ = this.authService.currentUser;
  }

  ngOnInit(): void {
    this.loadDashboardData();
  }

  loadDashboardData(): void {
    // Load total books
    this.bookService.getAllBooks().subscribe((books) => {
      this.totalBooks = books.length;
      this.availableBooks = books.filter(
        (book) => book.availableCopies > 0
      ).length;
      this.recentBooks = books.slice(-5).reverse();
    });

    // Load total users
    this.userService.getAllUsers().subscribe((users) => {
      this.totalUsers = users.length;
    });

    // Load active borrowings
    this.borrowingService.getAllBorrowedBooks().subscribe((borrowings) => {
      this.activeBorrowings = borrowings.length;
      this.recentBorrowings = borrowings.slice(-5).reverse();
    });
  }
}
