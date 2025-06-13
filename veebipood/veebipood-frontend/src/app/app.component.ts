import { Component } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import {TranslateModule, TranslatePipe} from "@ngx-translate/core";
import {TranslateService} from "@ngx-translate/core";
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, TranslateModule, TranslatePipe], // kõik mis ei eksisteeri tavalises HTMLs, siia
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'veebipood-frontend';
  loggedIn: boolean = false;
  admin: boolean = false;

  constructor(private translate: TranslateService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.translate.use(localStorage.getItem("language") || "en");

    this.authService.findIfLoggedIn().subscribe(() => {
      
    });

    this.authService.loggedIn.subscribe(loggedIn => {
      this.loggedIn = loggedIn;
      this.authService.admin.subscribe(admin => {
        this.admin = admin;
      })
    });
  }

  useLanguage(language: string): void {
    this.translate.use(language);
    localStorage.setItem("language", language);
  }

  logout() {
    this.admin = false;
    this.loggedIn = false;
    sessionStorage.clear();
    this.router.navigateByUrl("/");
  }
}
