import { HttpClient, HttpParams } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-manage-admins',
  imports: [],
  templateUrl: './manage-admins.component.html',
  styleUrl: './manage-admins.component.css'
})
export class ManageAdminsComponent {
  persons: any[] = [];
  url = "http://localhost:8080";

  // teiste failide (moodulitega) ühendamiseks
  constructor(private http: HttpClient) {}

  // kui lehele tullakse, siis see funktsioon käivitub
  ngOnInit() {
    this.http.get<any[]>(this.url + "/persons", {
      headers: {
        "Authorization": "Bearer " + sessionStorage.getItem("token")
      }
    }).subscribe(result => {
      this.persons = result;
    })
  }
  changeAdmin(id: number) {
    const params = new HttpParams().set("personId", id);
    this.http.patch<any[]>(this.url + "/change-admin", null, {params, headers: {
      "Authorization": "Bearer " + sessionStorage.getItem("token")
    }})
    .subscribe(result => {
      this.persons = result;
    })
  }
}
