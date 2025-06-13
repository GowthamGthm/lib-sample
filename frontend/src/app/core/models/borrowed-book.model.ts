
import { User } from './user.model';
import { Book } from './book.model';

export interface BorrowedBook {
  id?: number;
  user: User;
  book: Book;
  borrowedAt: string;
  returnedAt?: string;
  isReturned: boolean;
}
