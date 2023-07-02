-- bard: create mysql table for book, unique key isbn, created_at and updated_at time, named t_book
CREATE TABLE t_book (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  author VARCHAR(255) NOT NULL,
  publisher VARCHAR(255) NOT NULL,
  publication_date DATE NOT NULL,
  isbn VARCHAR(13) NOT NULL UNIQUE,
  description TEXT,
  cover_image VARCHAR(255),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
