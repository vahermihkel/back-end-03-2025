import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-manage-products',
  imports: [],
  templateUrl: './manage-products.component.html',
  styleUrl: './manage-products.component.css'
})
export class ManageProductsComponent {
  products: any[] = [];

  // teiste failide (moodulitega) ühendamiseks
  constructor(private http: HttpClient) {}

  // kui lehele tullakse, siis see funktsioon käivitub
  ngOnInit() {
    this.http.get<any[]>("http://localhost:8080/products", {
      headers: {
        "Authorization": "Bearer " + sessionStorage.getItem("token")
      }
    }).subscribe(result => {
      this.products = result;
    })
  }
  deleteProduct(id: number) {
    this.http.delete<any[]>("http://localhost:8080/products?id=" + id).subscribe(result => {
      this.products = result;
    })
  }
}
