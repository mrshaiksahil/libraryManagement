
    import java.io.*;
    import java.util.*;
    
    class Book {
        int id;
        String title;
        String author;
        boolean isIssued;
    
        // Constructor
        public Book(int id, String title, String author, boolean isIssued) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.isIssued = isIssued;
        }
    
        // Convert Book object to a string format for file storage
        @Override
        public String toString() {
            return id + "," + title + "," + author + "," + isIssued;
        }
    
        // Parse a line from the file to a Book object
        public static Book fromString(String line) {
            String[] parts = line.split(",");
            return new Book(Integer.parseInt(parts[0]), parts[1], parts[2], Boolean.parseBoolean(parts[3]));
        }
    }
    
    public class LibraryManagementSystem {
        private static final String FILE_NAME = "books.txt";
    
        // Method to add a book
        public static void addBook() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                Scanner sc = new Scanner(System.in);
                System.out.print("Enter Book ID: ");
                int id = sc.nextInt();
                sc.nextLine(); // Consume newline
                System.out.print("Enter Book Title: ");
                String title = sc.nextLine();
                System.out.print("Enter Book Author: ");
                String author = sc.nextLine();
    
                Book book = new Book(id, title, author, false);
                writer.write(book.toString() + "\n");
                System.out.println("Book added successfully!");
            } catch (IOException e) {
                System.out.println("Error writing to file.");
            }
        }
    
        // Method to display all books
        public static void displayBooks() {
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
                String line;
                System.out.println("\n--- Library Books ---");
                while ((line = reader.readLine()) != null) {
                    Book book = Book.fromString(line);
                    System.out.println("ID: " + book.id + ", Title: " + book.title + ", Author: " + book.author
                            + ", Issued: " + (book.isIssued ? "Yes" : "No"));
                }
            } catch (IOException e) {
                System.out.println("No books found!");
            }
        }
    
        // Method to search for a book by title
        public static void searchBook() {
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
                Scanner sc = new Scanner(System.in);
                System.out.print("Enter book title to search: ");
                String searchTitle = sc.nextLine();
    
                String line;
                boolean found = false;
                while ((line = reader.readLine()) != null) {
                    Book book = Book.fromString(line);
                    if (book.title.equalsIgnoreCase(searchTitle)) {
                        System.out.println("Book Found! ID: " + book.id + ", Author: " + book.author
                                + ", Issued: " + (book.isIssued ? "Yes" : "No"));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Book not found!");
                }
            } catch (IOException e) {
                System.out.println("No books found!");
            }
        }
    
        // Method to issue (borrow) a book
        public static void issueBook() {
            List<Book> books = readBooksFromFile();
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Book ID to borrow: ");
            int bookID = sc.nextInt();
    
            boolean found = false;
            for (Book book : books) {
                if (book.id == bookID && !book.isIssued) {
                    book.isIssued = true;
                    found = true;
                    System.out.println("Book issued successfully!");
                    break;
                }
            }
    
            if (!found) {
                System.out.println("Book not available or already issued!");
            } else {
                writeBooksToFile(books);
            }
        }
    
        // Method to return a book
        public static void returnBook() {
            List<Book> books = readBooksFromFile();
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Book ID to return: ");
            int bookID = sc.nextInt();
    
            boolean found = false;
            for (Book book : books) {
                if (book.id == bookID && book.isIssued) {
                    book.isIssued = false;
                    found = true;
                    System.out.println("Book returned successfully!");
                    break;
                }
            }
    
            if (!found) {
                System.out.println("Book not found or not issued!");
            } else {
                writeBooksToFile(books);
            }
        }
    
        // Method to read books from the file
        private static List<Book> readBooksFromFile() {
            List<Book> books = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    books.add(Book.fromString(line));
                }
            } catch (IOException e) {
                System.out.println("Error reading file.");
            }
            return books;
        }
    
        // Method to write books back to the file after updates
        private static void writeBooksToFile(List<Book> books) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                for (Book book : books) {
                    writer.write(book.toString() + "\n");
                }
            } catch (IOException e) {
                System.out.println("Error writing to file.");
            }
        }
    
        // Main menu
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            int choice;
    
            do {
                System.out.println("\n=== Library Management System ===");
                System.out.println("1. Add Book");
                System.out.println("2. Display Books");
                System.out.println("3. Search Book");
                System.out.println("4. Issue Book");
                System.out.println("5. Return Book");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
    
                switch (choice) {
                    case 1: addBook(); break;
                    case 2: displayBooks(); break;
                    case 3: searchBook(); break;
                    case 4: issueBook(); break;
                    case 5: returnBook(); break;
                    case 6: System.out.println("Exiting system..."); break;
                    default: System.out.println("Invalid choice! Try again.");
                }
            } while (choice != 6);
    
            sc.close();
        }
    }
    

















