import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';

@Component({
  selector: 'app-add-product-2',
  imports: [FormsModule],
  templateUrl: './add-product-2.component.html',
  styleUrl: './add-product-2.component.css'
})
export class AddProduct2Component {
  message = "";
  // name = "";
  // price = "";
  // image = "";
  // active = "";
  // category = "";
  newProduct = {
    "name": "",
    "price": "",
    "image": "",
    "active": "",
    "category": {"id": ""}
  }

  constructor(private http: HttpClient) {}

  addProduct() {
    // const newProduct = {
      // "name": this.name,
      // "price": this.price,
      // "image": this.image,
      // "active": this.active,
      // "category": {"id": this.category}
    // }
    // form.value.id = 99;
    //form.value.category = {id: form.value.category};

    this.http.post("http://localhost:8080/products", this.newProduct).subscribe({
      next: () => {this.message = "Õnnestus"},
      error: (result) => {this.message = result.error.message}
    });
  }
}
