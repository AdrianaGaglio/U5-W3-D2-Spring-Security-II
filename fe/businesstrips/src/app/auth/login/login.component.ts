import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { iLoginrequest } from '../../interfaces/iloginrequest';

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
      usernameOrEmail: this.fb.control('', [Validators.required]),
      password: this.fb.control('', [Validators.required]),
    });
  }

  isTouchedInvalid(field: string) {
    return (
      this.loginForm.get(field)?.touched &&
      this.loginForm.get(field)?.invalid &&
      this.loginForm.get(field)?.dirty
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
      let usernameOrEmail: string = <string>(
        this.loginForm.get('usernameOrEmail')?.value
      );
      let request: Partial<iLoginrequest> = {
        password: this.loginForm.get('password')?.value,
      };
      if (usernameOrEmail.includes('@')) {
        request.email = usernameOrEmail;
      } else {
        request.username = usernameOrEmail;
      }

      this.authSvc.login(request).subscribe({
        next: (res) => {
          this.loginForm.reset();
        },
        error: (err) => {
          console.log(err);
        },
      });
    }
  }
}
