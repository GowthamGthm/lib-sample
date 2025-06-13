import { Component, OnInit } from "@angular/core";
import { BorrowingService } from "../../core/services/borrowing.service";
import { BookService } from "../../core/services/book.service";
import { UserService } from "../../core/services/user.service";
import { AuthService } from "../../core/services/auth.service";
import { BorrowedBook } from "../../core/models/borrowed-book.model";
import { Book } from "../../core/models/book.model";
import { User } from "../../core/models/user.model";
import { Observable } from "rxjs";

@Component({
  selector: "app-borrowing",
  templateUrl: "./borrowing.component.html",
  styleUrls: ["./borrowing.component.scss"],
})
export class BorrowingComponent implements OnInit {
  borrowedBooks: BorrowedBook[] = [];
  availableBooks: Book[] = [];
  users: User[] = [];
  currentUser$: Observable<User | null>;
  selectedUserId: number | null = null;
  selectedBookId: number | null = null;
  loading = false;
  error = "";
  success = "";

  constructor(
    private borrowingService: BorrowingService,
    private bookService: BookService,
    private userService: UserService,
    private authService: AuthService
  ) {
    this.currentUser$ = this.authService.currentUser;
  }

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.loadBorrowedBooks();
    this.loadAvailableBooks();
    this.loadUsers();
  }

  loadBorrowedBooks(): void {
    this.borrowingService.getAllBorrowedBooks().subscribe({
      next: (books) => {
        this.borrowedBooks = books;
      },
      error: (error) => {
        this.error = "Failed to load borrowed books";
      },
    });
  }

  loadAvailableBooks(): void {
    this.bookService.getAvailableBooks().subscribe({
      next: (books) => {
        this.availableBooks = books;
      },
      error: (error) => {
        this.error = "Failed to load available books";
      },
    });
  }

  loadUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: (users) => {
        this.users = users;
      },
      error: (error) => {
        this.error = "Failed to load users";
      },
    });
  }

  borrowBook(): void {
    if (!this.selectedUserId || !this.selectedBookId) {
      this.error = "Please select both user and book";
      return;
    }

    this.loading = true;
    this.error = "";

    this.borrowingService
      .borrowBook(this.selectedUserId, this.selectedBookId)
      .subscribe({
        next: (borrowedBook) => {
          this.success = "Book borrowed successfully";
          this.selectedUserId = null;
          this.selectedBookId = null;
          this.loadData();
          this.loading = false;
        },
        error: (error) => {
          this.error = error.error || "Failed to borrow book";
          this.loading = false;
        },
      });
  }

  returnBook(borrowedBook: BorrowedBook): void {
    if (confirm("Are you sure you want to return this book?")) {
      this.borrowingService
        .returnBook(borrowedBook.user.id!, borrowedBook.book.id!)
        .subscribe({
          next: () => {
            this.success = "Book returned successfully";
            this.loadData();
          },
          error: (error) => {
            this.error = "Failed to return book";
          },
        });
    }
  }

  // Helper method for template to calculate total available copies
  getTotalAvailableCopies(): number {
    return this.availableBooks.reduce(
      (sum, book) => sum + book.availableCopies,
      0
    );
  }

  // Helper method for template to get first 5 books
  getFirstFiveBooks(): Book[] {
    return this.availableBooks.slice(0, 5);
  }

  // Helper method to get remaining books count
  getRemainingBooksCount(): number {
    return Math.max(0, this.availableBooks.length - 5);
  }
}
