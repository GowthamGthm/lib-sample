import { Component, OnInit } from "@angular/core";
import { BookService } from "../../core/services/book.service";
import { Book } from "../../core/models/book.model";

@Component({
  selector: "app-books",
  templateUrl: "./books.component.html",
  styleUrls: ["./books.component.scss"],
})
export class BooksComponent implements OnInit {
  books: Book[] = [];
  filteredBooks: Book[] = [];
  showAddForm = false;
  searchTerm = "";
  newBook: Book = {
    title: "",
    author: "",
    isbn: "",
    genre: "",
    publicationYear: new Date().getFullYear(),
    totalCopies: 1,
    availableCopies: 1,
  };
  loading = false;
  error: string = "";
  success: string = "";

  constructor(private bookService: BookService) {}

  ngOnInit(): void {
    this.loadBooks();
  }

  loadBooks(): void {
    this.bookService.getAllBooks().subscribe({
      next: (books) => {
        this.books = books;
        this.filteredBooks = books;
      },
      error: (error) => {
        this.showError("Failed to load books");
      },
    });
  }

  searchBooks(): void {
    if (!this.searchTerm.trim()) {
      this.filteredBooks = this.books;
      return;
    }

    this.filteredBooks = this.books.filter(
      (book) =>
        book.title.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        book.author.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        book.genre.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        book.isbn.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        book.genre.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        book.genre.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        book.publicationYear
          .toString()
          .toLowerCase()
          .includes(this.searchTerm.toLowerCase()) ||
        book.totalCopies
          .toString()
          .toLowerCase()
          .includes(this.searchTerm.toLowerCase()) ||
        book.availableCopies
          .toString()
          .toLowerCase()
          .includes(this.searchTerm.toLowerCase())
    );
  }

  toggleAddForm(): void {
    this.showAddForm = !this.showAddForm;
    if (!this.showAddForm) {
      this.resetForm();
    }
  }

  resetForm(): void {
    this.newBook = {
      title: "",
      author: "",
      isbn: "",
      genre: "",
      publicationYear: new Date().getFullYear(),
      totalCopies: 1,
      availableCopies: 1,
    };
  }

  addBook(): void {
    if (!this.newBook.title || !this.newBook.author || !this.newBook.isbn) {
      this.showError("Title, Author, and ISBN are required");
      return;
    }

    this.loading = true;
    this.error = "";

    this.bookService.createBook(this.newBook).subscribe({
      next: (book) => {
        this.books.push(book);
        this.filteredBooks = this.books;
        this.showSuccess("Book added successfully");
        this.resetForm();
        this.showAddForm = false;
        this.loading = false;
      },
      error: (error) => {
        this.showError(error.error || "Failed to add book");
        this.loading = false;
      },
    });
  }

  deleteBook(id: number): void {
    if (confirm("Are you sure you want to delete this book?")) {
      this.bookService.deleteBook(id).subscribe({
        next: () => {
          this.books = this.books.filter((book) => book.id !== id);
          this.filteredBooks = this.books;
          this.showSuccess("Book deleted successfully");
        },
        error: (error) => {
          // this.error = "Failed to delete book";
          this.showError(error.error?.message || "Failed to delete book");
        },
      });
    }
  }

  showError(message: string) {
    this.error = message;
    setTimeout(() => (this.error = ""), 5000);
  }

  showSuccess(message: string) {
    this.success = message;
    setTimeout(() => (this.success = ""), 5000);
  }

}
