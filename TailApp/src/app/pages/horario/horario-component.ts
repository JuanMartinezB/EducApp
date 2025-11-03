import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

import { AcademicoService } from '../../core/services/academico-service';
import { NavbarComponent } from '../../components/navbar/navbar-component';

@Component({
  selector: 'app-horario',
  standalone: true,
  imports: [CommonModule, NavbarComponent],
  templateUrl: './horario-component.html',
})
export class HorarioComponent implements OnInit {
  studentId: number | null = null;
  pdfStatus: string = '';

  constructor(
    private router: Router,
    private academicoService: AcademicoService
  ) { }

  ngOnInit() {
    const idString = localStorage.getItem('currentStudentId');
    this.studentId = idString ? parseInt(idString, 10) : null;

    if (!this.studentId) {
      this.router.navigate(['/login']);
    }
  }

  generarPDF() {
    if (!this.studentId) {
        this.pdfStatus = 'Error: Estudiante no identificado.';
        return;
    }

    this.pdfStatus = 'Iniciando generación del horario... Esto abrirá una ventana de diálogo.';
    
    this.academicoService.generarHorarioPDF(this.studentId).subscribe({
      next: () => {
        this.pdfStatus = 'Proceso de impresión/guardado iniciado. Revisa la ventana de diálogo de tu sistema operativo (guardar como PDF).';
      },
      error: (err) => {
        const msg = err.error?.error || 'Error desconocido al generar PDF.';
        this.pdfStatus = `Error al generar el horario: ${msg}`;
        console.error('Error en PDF:', err);
      }
    });
  }
}