import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Book } from './book.model';
import { BookService } from './service'
import {MatTableModule} from '@angular/material/table';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import {MatToolbarModule} from '@angular/material/toolbar'
import { OAuthService } from 'angular-oauth2-oidc';
import { DeleteDialogComponent } from './delete-dialog/delete-dialog.component';
import { EditDialogComponent } from './edit-dialog/edit-dialog.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MatTableModule, MatIconModule, MatButtonModule, MatDialogModule, MatToolbarModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'SSO-WebApp2';
  displayedColumns: string[] = ['title', 'isbn', 'price', 'edit', 'delete'];
  books: Book[] = [];
  firstName = '';
  lastName = '';

  constructor(private bookService: BookService, private oauthService: OAuthService, private dialog:MatDialog) {
   
  }

  ngOnInit(): void {
    this.loadBooks();
    let claims = this.oauthService.getIdentityClaims();
    this.firstName = claims['given_name'];
    this.lastName = claims['family_name'];
  }

  logout() {
    this.oauthService.logOut();
  }

  openDeleteDialog(book: Book) {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: book,
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        this.deleteBook(result);
      }
    });
  }

  openEditDialog(book: Book) {
    const dialogRef = this.dialog.open(EditDialogComponent, {
      data: book,
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        result['id'] = book.id;
        this.updateBook(result);
      }
    });
  }

  loadBooks(): void {
    this.bookService.getBooks().subscribe((data: Book[]) => {
      this.books = data;
    });
  }

  updateBook(book: Book): void {
    this.bookService.updateBook(book).subscribe(() => {
      this.loadBooks();
    });
  }


  deleteBook(id: number): void {
    this.bookService.deleteBook(id).subscribe(() => {
      this.loadBooks();
    });
  }


}
