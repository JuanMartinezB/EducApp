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

    this.academicoService.generarHorarioPdf(this.studentId).subscribe({
        next: (blob) => {
            const url = window.URL.createObjectURL(blob);
            
            const a = document.createElement('a');
            a.href = url;
            a.download = `Horario_Estudiante_${this.studentId}.pdf`; 

            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
            
            window.URL.revokeObjectURL(url);
        },
        error: (err) => {
            alert('Error: No se pudo generar o descargar el horario. Consulte el log del servidor.');
            console.error('Error de descarga de PDF:', err);
        }
    });
  }
}