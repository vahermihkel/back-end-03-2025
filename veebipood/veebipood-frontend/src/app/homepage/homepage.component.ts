import { DatePipe, UpperCasePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { TranslatePipe } from '@ngx-translate/core';
import { ShortenerPipe } from '../pipes/shortener.pipe';
import { FormsModule } from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';

@Component({
  selector: 'app-homepage',
  imports: [TranslatePipe, DatePipe, 
    UpperCasePipe, ShortenerPipe, FormsModule, MatButtonModule],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.css'
})
export class HomepageComponent implements OnInit {
  products: any[] = [];
  categories: any[] = [];
  size = 3;
  page = 0;
  totalProducts = 0;
  totalPages = 0;
  activeCategoryId = -1; // lepime kokku, et see on kõik kategooriad
  loading = true;
  sort = "id,asc";
  currentDate = new Date();
  searchInput = "";

  // teiste failide (moodulitega) ühendamiseks
  constructor(private http: HttpClient) {}

  // kui lehele tullakse, siis see funktsioon käivitub
  ngOnInit() {
    this.fetchProducts();
    this.fetchCategories();
  }

// {activeCategory: 1, size, page, sort}
// {categoryId:this.activeCategoryId, size:this.size, page:this.page, sort: this.sort}

  fetchProducts() {
    this.http.get<any>("http://localhost:8080/products-by-category", 
      {params: {categoryId:this.activeCategoryId, size:this.size, page:this.page, sort: this.sort}}
    ).subscribe(result => {
      this.products = result.content;
      this.totalPages = result.totalPages;
      this.totalProducts = result.totalElements;
      this.loading = false;
    })
  }

  fetchCategories() {
    this.http.get<any>("http://localhost:8080/categories")
      .subscribe(result => {
      this.categories = result;
    })
  }

  filterByCategory(category: any) {
    this.activeCategoryId = category.id;
    this.fetchProducts();
  }

  changeSort(newSort: string) {
    this.sort = newSort;
    this.fetchProducts();
  }

  search() {
    this.http.get<any>("http://localhost:8080/search-from-products?searchField=" + this.searchInput, 
      {params: {size:this.size, page:0}}
    ).subscribe(result => {
      this.products = result.content;
      this.totalPages = result.totalPages;
      this.totalProducts = result.totalElements;
      this.loading = false;
    })
  }

  addToCart(product: any) {
    const cartLS = JSON.parse(localStorage.getItem("cart") || "[]");
    cartLS.push(product)
    localStorage.setItem("cart", JSON.stringify(cartLS));
  }

  prevPage() {
    this.page--;
    this.fetchProducts();
  }

  nextPage() {
    this.page++;
    this.fetchProducts();
  }
}
