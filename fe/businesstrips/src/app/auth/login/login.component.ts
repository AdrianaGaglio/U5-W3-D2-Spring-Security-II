import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  constructor(private fb: FormBuilder, private authSvc: AuthService) {}

  loginForm!: FormGroup;

  ngOnInit() {
    this.loginForm = this.fb.group({
      username: this.fb.control(''),
      email: this.fb.control('', [Validators.email]),
      password: this.fb.control('', [Validators.required]),
    });
  }

  isTouchedInvalid(field: string) {
    return (
      this.loginForm.get(field)?.touched && this.loginForm.get(field)?.invalid
    );
  }

  message(field: string) {
    let input = this.loginForm.get(field)!;
    if (input.hasError('required')) {
      return 'Field is required.';
    }
    if (input.hasError('email')) {
      return 'Invalid email.';
    }
    return '';
  }

  formValid() {
    return this.loginForm.valid;
  }

  login() {
    if (this.formValid()) {
      this.authSvc.login(this.loginForm.value).subscribe({
        next: (res) => {
          console.log(res.user);
          this.loginForm.reset();
        },
        error: (err) => {
          console.log(err);
        },
      });
    }
  }
}
