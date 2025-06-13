import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  url: string = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  getUserOrders() {
    return this.http.get<any[]>(this.url + "/orders", 
      {headers: {
      "Authorization": "Bearer " + sessionStorage.getItem("token")
      }});
  }
}
