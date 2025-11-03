import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AcademicoService, Asignatura, Inscripcion, InscripcionRequest } from '../../core/services/academico-service';
import { NavbarComponent } from '../../components/navbar/navbar-component';

type InscripcionDetallada = Inscripcion; 


@Component({
  selector: 'app-inscripcion',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarComponent],
  templateUrl: './inscripcion-component.html',
})
export class InscripcionComponent implements OnInit {
  studentId: number | null = null;
  studentName: string | null = null;
  asignaturasInscritas: Inscripcion[] = []; 
  asignaturasDisponibles: Asignatura[] = [];
  selectedAsignaturaId: number | null = null;
  
  constructor(
    private router: Router,
    private academicoService: AcademicoService
  ) { }

  ngOnInit() {
    const idString = localStorage.getItem('currentStudentId');
    this.studentName = localStorage.getItem('currentStudentName');
    this.studentId = idString ? parseInt(idString, 10) : null;

    if (!this.studentId) {
      this.router.navigate(['/login']);
    } else {
      this.loadAsignaturasDisponibles();
      this.loadInscripciones(this.studentId!); 
    }
  }
  
  loadAsignaturasDisponibles() {
    this.academicoService.getAsignaturasDisponibles().subscribe(
      data => {
        this.asignaturasDisponibles = data;
      },
      error => console.error('Error cargando disponibles:', error)
    );
  }
  
  loadInscripciones(id: number) {
    this.academicoService.getAsignaturasActivas(id).subscribe(
      (data: Inscripcion[]) => { 
        this.asignaturasInscritas = data; 
      },
      error => console.error('Error cargando inscripciones:', error)
    );
  }

  inscribir() {
    if (!this.studentId || !this.selectedAsignaturaId) {
      alert('Selecciona una asignatura válida.');
      return;
    }

    const requestBody: InscripcionRequest = {
        estudianteId: this.studentId,
        asignaturaId: this.selectedAsignaturaId,
        operador: 'ESTUDIANTE'
    };

    this.academicoService.inscribirAsignatura(requestBody).subscribe({
      next: () => {
        alert('Asignatura inscrita con éxito.');
        this.loadAsignaturasDisponibles(); 
        this.loadInscripciones(this.studentId!);
        this.selectedAsignaturaId = null;
      },
      error: (err) => {
        const backendMessage = err.error?.message; 
        let displayMessage = 'Acción no válida.';

        if (backendMessage && typeof backendMessage === 'string') {
            const lowerCaseMessage = backendMessage.toLowerCase();
            
            if (lowerCaseMessage.includes('ya está inscrito en esta asignatura')) {
                displayMessage = '🚫 Error: Ya estás inscrito en esta asignatura (estado ACTIVA).';
            } 
            else if (lowerCaseMessage.includes('límite máximo de')) {
                displayMessage = '⚠️ Límite alcanzado: Has inscrito el número máximo de asignaturas activas.';
            }
            else {
                 displayMessage = backendMessage; 
            }
        } 
        
        setTimeout(() => {
             alert('Error: ' + displayMessage);
        }, 0);
        
        console.error("Error al inscribir (Detalles):", err);
      }
    });
}

 cancelar(inscripcionId: number, asignaturaNombre: string) {
    if (confirm(`¿Estás seguro de que quieres cancelar la inscripción de ${asignaturaNombre}?`)) {
      this.academicoService.cancelarInscripcion(inscripcionId).subscribe({
        next: () => {
          alert('Inscripción cancelada con éxito.');
          this.loadAsignaturasDisponibles();
          this.loadInscripciones(this.studentId!);
        },
        error: (err) => {
          const msg = err.error?.message || 'Acción no válida.';
          alert('Error: ' + msg);
          console.error(err);
        }
      });
    }
  }
}