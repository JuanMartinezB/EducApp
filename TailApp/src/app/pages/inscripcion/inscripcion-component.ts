import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
// 🚨 Importar el nuevo DTO de Request
import { AcademicoService, Asignatura, Inscripcion, InscripcionRequest } from '../../core/services/academico-service';
import { NavbarComponent } from '../../components/navbar/navbar-component';

// 🚨 NOTA: La interfaz Inscripcion ahora debe reflejar el DTO de Java
// Inscripcion no necesita 'asignaturaId' ni 'estudianteId' directos.

// El tipo de Inscripcion ahora ya incluye la asignatura por diseño del DTO de respuesta.
// Puedes eliminar la interfaz InscripcionDetallada o renombrarla si deseas.
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
  // Usamos la interfaz del servicio, que ya trae la asignatura anidada
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
      // Cargamos disponibles e inscripciones en paralelo o secuencia
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
      // 🚨 Ahora 'data' ya es la lista de Inscripcion[] detalladas
      (data: Inscripcion[]) => { 
        // Ya no se necesita el map ni enrichInscripciones() si el DTO es correcto
        this.asignaturasInscritas = data; 
      },
      error => console.error('Error cargando inscripciones:', error)
    );
  }

  // ❌ Se elimina enrichInscripciones() ya que el backend debe proveer el detalle

  inscribir() {
    if (!this.studentId || !this.selectedAsignaturaId) {
      alert('Selecciona una asignatura válida.');
      return;
    }

    // 🚨 PASO 1: Construir el DTO de Request
    const requestBody: InscripcionRequest = {
        estudianteId: this.studentId,
        asignaturaId: this.selectedAsignaturaId,
        operador: 'CLIENTE' // Puedes obtenerlo de localStorage o dejar 'CLIENTE'
    };

    // 🚨 PASO 2: Llamar al servicio, pasando el DTO en el cuerpo (body)
    this.academicoService.inscribirAsignatura(requestBody).subscribe({
      next: () => {
        alert('Asignatura inscrita con éxito.');
        this.loadAsignaturasDisponibles(); 
        this.loadInscripciones(this.studentId!);
        this.selectedAsignaturaId = null;
      },
      error: (err) => {
        // La API de Spring Boot devuelve el mensaje en 'err.error.message'
        const msg = err.error?.message || 'Error desconocido al inscribir.';
        alert('Error: ' + msg);
        console.error(err);
      }
    });
}

 cancelar(inscripcionId: number, asignaturaNombre: string) {
    if (confirm(`¿Estás seguro de que quieres cancelar la inscripción de ${asignaturaNombre}?`)) {
      this.academicoService.cancelarInscripcion(inscripcionId).subscribe({
        next: () => {
          alert('Inscripción cancelada con éxito.');
          // Recargar datos para actualizar la lista de inscripciones activas
          this.loadAsignaturasDisponibles();
          this.loadInscripciones(this.studentId!);
        },
        error: (err) => {
          // Usa 'message' para mensajes de error de Spring Boot
          const msg = err.error?.message || 'Error desconocido al cancelar.';
          alert('Error: ' + msg);
          console.error(err);
        }
      });
    }
  }
}