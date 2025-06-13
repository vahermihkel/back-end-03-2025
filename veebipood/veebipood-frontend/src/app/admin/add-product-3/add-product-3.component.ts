import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-product-3',
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './add-product-3.component.html',
  styleUrl: './add-product-3.component.css'
})
export class AddProduct3Component implements OnInit {
  message = "";
  addProductForm!: FormGroup;
  categories: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.http.get<any>("http://localhost:8080/categories")
      .subscribe(result => {
      this.categories = result;
    })

    this.addProductForm = new FormGroup({
      "name": new FormControl("", [Validators.required]),
      "price": new FormControl(""),
      "image": new FormControl(""),
      "active": new FormControl(""),
      "category": new FormControl(""),
    })
  }

  addProduct() {
    this.addProductForm.value.category = {id: this.addProductForm.value.category};
    this.http.post("http://localhost:8080/products", this.addProductForm.value).subscribe({
      next: () => {this.message = "Õnnestus"},
      error: (result) => {this.message = result.error.message}
    });
  }
}
