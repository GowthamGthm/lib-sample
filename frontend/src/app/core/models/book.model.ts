
export interface Book {
  id?: number;
  title: string;
  author: string;
  isbn: string;
  genre: string;
  publicationYear: number;
  totalCopies: number;
  availableCopies: number;
  createdAt?: string;
}
