import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CartService } from '../services/cart.service';

@Component({
  selector: 'app-cart',
  imports: [RouterLink],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css',
})
export class CartComponent {
  products: any[] = JSON.parse(localStorage.getItem("cart") || "[]");

  constructor(private cartService: CartService) {}

  emptyCart() {
    // this.products.splice(0);
    this.products = [];
    // this.products.splice(0);
    localStorage.setItem("cart", JSON.stringify(this.products));
  }

  deleteFromCart(index: number) {
    this.products.splice(index,1);
    localStorage.setItem("cart", JSON.stringify(this.products));
  } 

  calculateCartSum(): number {
    let sum = 0;
    // sum = sum + 1.5;
    // sum = sum + 1.5;
    // sum = sum + 1.2;
    this.products.forEach(p => sum = sum + p.price)
    return sum;
  }

  orderProducts() {
    this.cartService.order(this.products).subscribe(res => {
      console.log(res.url);
      window.location.href = res.url;
    });
  }
}
