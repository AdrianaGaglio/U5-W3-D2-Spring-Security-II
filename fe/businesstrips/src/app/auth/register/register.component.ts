import { Component } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent {
  constructor(private fb: FormBuilder, private authSvc: AuthService) {}

  registerForm!: FormGroup;

  ngOnInit() {
    this.registerForm = this.fb.group({
      username: this.fb.control('', [
        Validators.required,
        Validators.minLength(6),
      ]),
      password: this.fb.control('', [
        Validators.required,
        Validators.minLength(8),
      ]),
      employeeRequest: this.fb.group({
        firstName: this.fb.control('', [Validators.required]),
        lastName: this.fb.control('', [Validators.required]),
        email: this.fb.control('', [Validators.required, Validators.email]),
        image: this.fb.control(''),
      }),
    });
  }

  isTouchedInvalid(field: string) {
    return (
      this.registerForm.get(field)?.touched &&
      this.registerForm.get(field)?.invalid &&
      this.registerForm.get(field)?.dirty
    );
  }

  message(field: string) {
    let input = this.registerForm.get(field)!;
    if (input.hasError('required')) {
      return 'Field is required.';
    }
    if (
      input.hasError('minlength') &&
      input.value.length < 6 &&
      field === 'username'
    ) {
      return 'Min length is 6.';
    }
    if (
      input.hasError('minlength') &&
      input.value.length < 8 &&
      field === 'password'
    ) {
      return 'Min length is 8.';
    }
    if (input.hasError('email')) {
      return 'Invalid email.';
    }
    return '';
  }

  formValid() {
    return this.registerForm.valid;
  }

  register() {
    if (this.formValid()) {
      this.authSvc.register(this.registerForm.value).subscribe({
        next: (res) => {
          console.log(res.message);
          this.registerForm.reset();
        },
        error: (err) => {
          console.log(err);
          this.registerForm.reset();
        },
      });
    }
  }
}
