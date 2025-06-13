
import { Component, OnInit } from '@angular/core';
import { BookService } from '../../core/services/book.service';
import { Book } from '../../core/models/book.model';

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.scss']
})
export class BooksComponent implements OnInit {
  books: Book[] = [];
  filteredBooks: Book[] = [];
  showAddForm = false;
  searchTerm = '';
  newBook: Book = {
    title: '',
    author: '',
    isbn: '',
    genre: '',
    publicationYear: new Date().getFullYear(),
    totalCopies: 1,
    availableCopies: 1
  };
  loading = false;
  error = '';
  success = '';

  constructor(private bookService: BookService) { }

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
        this.error = 'Failed to load books';
      }
    });
  }

  searchBooks(): void {
    if (!this.searchTerm.trim()) {
      this.filteredBooks = this.books;
      return;
    }

    this.filteredBooks = this.books.filter(book =>
      book.title.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      book.author.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      book.genre.toLowerCase().includes(this.searchTerm.toLowerCase())
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
      title: '',
      author: '',
      isbn: '',
      genre: '',
      publicationYear: new Date().getFullYear(),
      totalCopies: 1,
      availableCopies: 1
    };
    this.error = '';
    this.success = '';
  }

  addBook(): void {
    if (!this.newBook.title || !this.newBook.author || !this.newBook.isbn) {
      this.error = 'Title, Author, and ISBN are required';
      return;
    }

    this.loading = true;
    this.error = '';

    this.bookService.createBook(this.newBook).subscribe({
      next: (book) => {
        this.books.push(book);
        this.filteredBooks = this.books;
        this.success = 'Book added successfully';
        this.resetForm();
        this.showAddForm = false;
        this.loading = false;
      },
      error: (error) => {
        this.error = error.error || 'Failed to add book';
        this.loading = false;
      }
    });
  }

  deleteBook(id: number): void {
    if (confirm('Are you sure you want to delete this book?')) {
      this.bookService.deleteBook(id).subscribe({
        next: () => {
          this.books = this.books.filter(book => book.id !== id);
          this.filteredBooks = this.books;
          this.success = 'Book deleted successfully';
        },
        error: (error) => {
          this.error = 'Failed to delete book';
        }
      });
    }
  }
}
