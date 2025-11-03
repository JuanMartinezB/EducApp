import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

import { AcademicoService, HistorialAcademico } from '../../core/services/academico-service';
import { NavbarComponent } from '../../components/navbar/navbar-component';

@Component({
  selector: 'app-historial',
  standalone: true,
  imports: [CommonModule, NavbarComponent],
  templateUrl: './historial-component.html',
})
export class HistorialComponent implements OnInit {
  studentId: number | null = null;
  studentName: string | null = null;
  historial: HistorialAcademico[] = [];
  
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
      this.loadHistorial(this.studentId);
    }
  }

  loadHistorial(id: number) {
    this.academicoService.getHistorialAcademico(id).subscribe(
      data => this.historial = data,
      error => console.error('Error cargando historial:', error)
    );
  }

  calcularPromedio(): string {
    if (this.historial.length === 0) return '0.00';
    
    let totalCreditos = 0;
    let sumaPonderada = 0;

    this.historial.forEach(item => {
      const creditos = item.asignatura?.creditos || 0; 
      
      if (creditos > 0) {
          totalCreditos += creditos;
          sumaPonderada += item.nota * creditos;
      }
    });

    if (totalCreditos === 0) return '0.00';
    
    const promedio = sumaPonderada / totalCreditos;
    return promedio.toFixed(2);
  }
}