
-- Create database (run this as postgres user)
-- CREATE DATABASE librarydb;

-- Connect to librarydb and create tables
\c librarydb;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    full_name VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create books table
CREATE TABLE IF NOT EXISTS books (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    genre VARCHAR(100),
    publication_year INTEGER,
    total_copies INTEGER NOT NULL DEFAULT 1,
    available_copies INTEGER NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create borrowed_books table
CREATE TABLE IF NOT EXISTS borrowed_books (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    book_id BIGINT NOT NULL REFERENCES books(id) ON DELETE CASCADE,
    borrowed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    returned_at TIMESTAMP,
    is_returned BOOLEAN DEFAULT FALSE
);

-- Insert sample admin user
INSERT INTO users (username, password, email, full_name, created_at) 
VALUES ('admin', 'admin123', 'admin@library.com', 'System Administrator', NOW())
ON CONFLICT (username) DO NOTHING;

-- Insert sample books
INSERT INTO books (title, author, isbn, genre, publication_year, total_copies, available_copies, created_at) 
VALUES 
('The Great Gatsby', 'F. Scott Fitzgerald', '978-0-7432-7356-5', 'Fiction', 1925, 5, 5, NOW()),
('To Kill a Mockingbird', 'Harper Lee', '978-0-06-112008-4', 'Fiction', 1960, 3, 3, NOW()),
('1984', 'George Orwell', '978-0-452-28423-4', 'Dystopian Fiction', 1949, 4, 4, NOW()),
('Pride and Prejudice', 'Jane Austen', '978-0-14-143951-8', 'Romance', 1813, 2, 2, NOW()),
('The Catcher in the Rye', 'J.D. Salinger', '978-0-316-76948-0', 'Fiction', 1951, 3, 3, NOW())
ON CONFLICT (isbn) DO NOTHING;
