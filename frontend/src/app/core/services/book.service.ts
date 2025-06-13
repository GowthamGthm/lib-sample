
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Book } from '../models/book.model';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private http: HttpClient) { }

  getAllBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(`${environment.privateUrL}/books`);
  }

  getBookById(id: number): Observable<Book> {
    return this.http.get<Book>(`${environment.privateUrL}/books/${id}`);
  }

  getAvailableBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(`${environment.privateUrL}/books/available`);
  }

  searchBooksByTitle(title: string): Observable<Book[]> {
    return this.http.get<Book[]>(`${environment.privateUrL}/books/search/title?title=${title}`);
  }

  searchBooksByAuthor(author: string): Observable<Book[]> {
    return this.http.get<Book[]>(`${environment.privateUrL}/books/search/author?author=${author}`);
  }

  createBook(book: Book): Observable<Book> {
    return this.http.post<Book>(`${environment.privateUrL}/books`, book);
  }

  updateBook(id: number, book: Book): Observable<Book> {
    return this.http.put<Book>(`${environment.privateUrL}/books/${id}`, book);
  }

  deleteBook(id: number): Observable<any> {
    return this.http.delete(`${environment.privateUrL}/books/${id}`);
  }
}
