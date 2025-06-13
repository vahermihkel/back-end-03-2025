import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-signup',
  imports: [FormsModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  signUpForm = {
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    personalCode: "",
  }

  constructor(
    private auth: AuthService,
    private router: Router
  ) {

  }

  signup() {
    this.auth.signup(this.signUpForm).subscribe({
      next: (response) => {
        this.router.navigate(['/'])
      },
      error: (err) => console.log(err)
    })
  };
}
