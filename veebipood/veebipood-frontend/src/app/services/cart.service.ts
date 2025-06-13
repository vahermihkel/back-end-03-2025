import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  url: string = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  order(products: any[]) {
    return this.http.post<any>(this.url + "/orders", products, {headers: {
      // "Authorization": "Bearer " + JSON.parse(sessionStorage.getItem("token") || "{}").token
      "Authorization": "Bearer " + sessionStorage.getItem("token")
    }});
  }
}
