import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './navbar-component.html',
})
export class NavbarComponent {
  studentName: string | null = localStorage.getItem('currentStudentName');

  constructor(private router: Router) { }

  logout() {
    // 1. Limpiar datos de sesión
    localStorage.removeItem('currentStudentId');
    localStorage.removeItem('currentStudentName');
    // 2. Redirigir al login
    this.router.navigate(['/login']);
  }
}