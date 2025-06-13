import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map, Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  url: string = "http://localhost:8080";
  person = new BehaviorSubject<any | null>(null);
  loggedIn = new BehaviorSubject<boolean>(false);
  admin = new BehaviorSubject<boolean>(false);
  // alati on 2 poolt --> keegi kuskil teeb .next() ja keegi teeb .subscribe()

  constructor(private http: HttpClient) { }

  findIfLoggedIn(): Observable<any> {
    const personUrl = this.url + '/person';
    return this.http.get<any>(personUrl, {
      headers: {
        "Authorization": "Bearer " + sessionStorage.getItem("token")
      }
    }).pipe(map(person => {
      this.person.next(person);
      this.loggedIn.next(true);
      if (person.role === "ADMIN" || person.role === "SUPERADMIN") {
        this.admin.next(true);
      } else {
        this.admin.next(false);
      }
    }));
  }

  login(loginForm: any): Observable<any> {
    const loginUrl = this.url + '/login';
    return this.http.post<any>(loginUrl, loginForm);
  }

  signup(loginForm: any): Observable<any> {
    const signupUrl = this.url + '/signup';
    return this.http.post<any>(signupUrl, loginForm);
   }
}
