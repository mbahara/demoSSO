import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Product } from './product.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductService } from './service';
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
  title = 'SSO-WebApp1';
  displayedColumns: string[] = ['name', 'description', 'price', 'edit', 'delete'];
  products: Product[] = [];
  firstName = '';
  lastName = '';

  constructor(private productService: ProductService, private oauthService: OAuthService, private dialog:MatDialog) {
   
  }

  ngOnInit(): void {
    this.loadProducts();
    let claims = this.oauthService.getIdentityClaims();
    this.firstName = claims['given_name'];
    this.lastName = claims['family_name'];
  }

  logout() {
    this.oauthService.logOut();
  }

  openDeleteDialog(product: Product) {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: product,
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        this.deleteProduct(result);
      }
    });
  }

  openEditDialog(product: Product) {
    const dialogRef = this.dialog.open(EditDialogComponent, {
      data: product,
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        result['id'] = product.id;
        this.updateProduct(result);
      }
    });
  }

  loadProducts(): void {
    this.productService.getProducts().subscribe((data: Product[]) => {
      this.products = data;
    });
  }

  updateProduct(product: Product): void {
    this.productService.updateProduct(product).subscribe(() => {
      this.loadProducts();
    });
  }


  deleteProduct(id: number): void {
    this.productService.deleteProduct(id).subscribe(() => {
      this.loadProducts();
    });
  }


}
