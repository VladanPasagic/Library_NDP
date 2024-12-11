# Online Library System Specification

## **Overview**
The system simulates the operation of an online library, consisting of three main components:
1. **Library Application (GUI)**: Used by librarians to manage members, books, and reservations.
2. **Member Application (GUI)**: Used by library members to browse, reserve, and download books.
3. **Supplier Application (GUI)**: Used by book suppliers to manage and fulfill book orders.

---

## **Features**

### **1. Library Application**
- **Member Management**:
  - View, add, edit, and delete member accounts.
  - Approve or reject registration requests.
  - Block or delete members.
  - Member accounts are stored in an XML file (`users.xml`) on the server side.
- **Book Management**:
  - CRUD operations (Create, Read, Update, Delete) for books.
  - Books are stored in a Redis database.
- **Reservation Management**:
  - View all book reservations.
  - Approve or reject reservations.
- **New Book Suggestions**:
  - Members can suggest books to be acquired.
  - Suggestions are sent as multicast messages to librarians and registered members.
  - Librarians can respond with a multicast message confirming or rejecting the acquisition.
- **Book Orders**:
  - Order books from suppliers.
  - View all available books from specific suppliers and place orders.
  - Specify the quantity when ordering.
- **Test Data**:
  - Automatically populate test data on application startup.

---

### **2. Member Application**
- **Authentication**:
  - Login using username and password.
  - Registration involves providing personal information:
    - First name, last name, address, email, username, password (entered twice for confirmation).
  - Registration and login handled via RESTful endpoints.
  - Librarians must manually approve registration requests before accounts are activated.
- **Book Browsing and Downloading**:
  - View all books in a tabular format.
  - Search for books.
  - Each row includes basic book details and a **Details** button to:
    - Open a pop-up displaying the book's title, cover, and the first 100 lines of the book's text file.
  - Select multiple books for download:
    - Selected books are packaged into a ZIP archive and sent to the member's email via the library's RESTful email endpoint.
    - The email includes details of all downloaded books.
- **Chat Functionality**:
  - Members can communicate with each other via a chat application.
  - Messages are sent over a secure socket connection.

---

### **3. Supplier Application**
- **Book Management**:
  - Manage a list of books with details:
    - Title, author, publication date, language, cover image, and text content.
  - Book data is stored on the server side.
- **Automated Book Import**:
  - On startup, the `SupplierServer` reads links from a text file (`links.txt`):
    - Each line contains a link to a book on [Project Gutenberg](https://www.gutenberg.org).
    - Example links:
      - Book text: `https://www.gutenberg.org/cache/epub/24022/pg24022.txt`
      - Book cover: `https://www.gutenberg.org/cache/epub/24022/pg24022.cover.medium.jpg`
  - The server downloads the books, extracts metadata, and populates the database.
  - Book cover links are dynamically generated based on the book's link.
- **Order Processing**:
  - Librarians can view available books and place orders through the library application.
  - Orders are processed in the order they are received (FIFO) via MQ.
  - Suppliers can approve or reject orders.
  - Approved orders are sent to the library along with an invoice.
- **Invoices**:
  - Invoices include:
    - List of books, processing date, and price (randomly generated).
  - The supplier and accounting service communicate via an RMI application.
  - All invoices are stored in serialized form on the RMI server.
  - VAT (17%) of the total price must be calculated for each invoice.

---

## **System Assumptions**
- Only one instance of the library application can run at a time.
- Multiple member and supplier applications can run simultaneously.
- Anything not explicitly defined in this document can be implemented as desired.

---

## **Technical Notes**
- **Logging**:
  - Use loggers to handle exceptions.
- **Configuration**:
  - Use properties files for all necessary configurations (e.g., file paths).
