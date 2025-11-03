import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AcademicoService } from '../../core/services/academico-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login-component.html',
})
export class LoginComponent {
  studentId: string = ''; 
  errorMessage: string = '';

  constructor(
    private router: Router,
    private academicoService: AcademicoService
  ) { }

  login() {
    this.errorMessage = '';
    const studentIdValue = String(this.studentId || '');

    const id = parseInt(studentIdValue.trim(), 10);

    if (isNaN(id) || id <= 0) {
      this.errorMessage = 'Por favor, ingresa un ID de estudiante numérico válido.';
      return;
    }

    this.academicoService.getEstudianteById(id).subscribe({
      next: (estudiante) => {
        if (estudiante.estado !== 'ACTIVO') {
             this.errorMessage = `Acceso denegado: Tu estado es ${estudiante.estado}. Solo estudiantes ACTIVO pueden acceder.`;
             return;
        }
        
        localStorage.setItem('currentStudentId', id.toString());
        localStorage.setItem('currentStudentName', estudiante.nombre);
        this.router.navigate(['/inscripcion']);
      },
      error: (err) => {
        const reason = err.error?.error || 'ID de estudiante no válido.';
        this.errorMessage = reason;
        console.error('Error de login:', err);
      }
    });
  }
}