import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

// --- Interfaces de Datos ---

export interface Estudiante {
    id: number;
    codigoEstudiante: string;
    nombre: string;
    email: string;
    semestreActual: number;
    estado: string; 
}

export interface Asignatura {
    id: number;
    codigo: string;
    nombre: string;
    creditos: number;
    cupoMaximo: number;
    cupoActual: number;
    semestre: number;
    horario: string;
}

export interface Inscripcion {
    idInscripcion: number; 
    fechaInscripcion: string;
    fechaCancelacion: string | null;
    estado: string;
    asignatura: Asignatura;
}

export interface HistorialAcademico {
    id: number;
    nota: number;
    semestre: number;
    asignatura: Asignatura; 
}

export interface InscripcionRequest {
    estudianteId: number;
    asignaturaId: number;
    operador?: string;
}

@Injectable({
    providedIn: 'root'
})
export class AcademicoService {

    private apiUrl = 'http://localhost:8080/api'; 

    constructor(private http: HttpClient) { }

    getEstudianteById(id: number): Observable<Estudiante> {
        return this.http.get<Estudiante>(`${this.apiUrl}/estudiantes/${id}`);
    }

    getAsignaturasActivas(estudianteId: number): Observable<Inscripcion[]> {
    const params = new HttpParams()
      .set('estudianteId', estudianteId.toString());
    return this.http.get<Inscripcion[]>(`${this.apiUrl}/inscripciones/activas`, { params });
  }
    
    getAsignaturasDisponibles(): Observable<Asignatura[]> {
        return this.http.get<Asignatura[]>(`${this.apiUrl}/asignaturas`);
    }

    inscribirAsignatura(request: InscripcionRequest): Observable<Inscripcion> {
        return this.http.post<Inscripcion>(`${this.apiUrl}/inscripciones/inscribir`, request);
    }

    cancelarInscripcion(inscripcionId: number): Observable<void> {
        const params = new HttpParams().set('operador', 'CLIENTE');
        return this.http.put<void>(`${this.apiUrl}/inscripciones/cancelar/${inscripcionId}`, null, { params });
    }

    getHistorialAcademico(estudianteId: number): Observable<HistorialAcademico[]> {
        const params = new HttpParams().set('estudianteId', estudianteId.toString());
        return this.http.get<HistorialAcademico[]>(`${this.apiUrl}/historial-academico`, { params });
    }

    generarHorarioPdf(estudianteId: number): Observable<Blob> {
        const url = `${this.apiUrl}/horarios/pdf/${estudianteId}`;
    
        return this.http.get(url, { responseType: 'blob' });
    }
}