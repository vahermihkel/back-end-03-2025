import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm = {
    email: "",
    password: ""
  }

  constructor(
    private auth: AuthService,
    private router: Router
  ) {}

  login() {
    this.auth.login(this.loginForm).subscribe({
      next: (response) => {
        sessionStorage.setItem("token", response.token);
        sessionStorage.setItem("tokenExpiration", response.expiration);
        this.auth.findIfLoggedIn().subscribe(() => {
          this.router.navigate(['/']);
        });
      },
      error: (err) => console.log("Login Failed!" + err)
    })
  }
}
