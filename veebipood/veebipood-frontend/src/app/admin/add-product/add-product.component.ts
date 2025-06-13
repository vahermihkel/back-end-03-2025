import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';

@Component({
  selector: 'app-add-product',
  imports: [FormsModule],
  templateUrl: './add-product.component.html',
  styleUrl: './add-product.component.css'
})
export class AddProductComponent {
  message = "";

  constructor(private http: HttpClient) {}

  addProduct(form: NgForm) {
    // form.value.id = 99;
    form.value.category = {id: form.value.category};

    this.http.post("http://localhost:8080/products", form.value).subscribe({
      next: () => {this.message = "Õnnestus"},
      error: (result) => {this.message = result.error.message}
    });
  }
}
