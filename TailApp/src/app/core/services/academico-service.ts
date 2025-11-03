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

// 🚨 AJUSTE DE INTERFAZ: Usar idInscripcion y fechaCancelacion para coincidir con InscripcionDTO de Java
export interface Inscripcion {
    idInscripcion: number; // Propiedad usada en Java para el ID principal
    fechaInscripcion: string;
    fechaCancelacion: string | null; // Añadir para coincidir con DTO y ser opcional/null
    estado: string;
    asignatura: Asignatura;
    // Las propiedades 'estudianteId' y 'asignaturaId' directas no son necesarias aquí.
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

    // --- Login/Estudiante ---

    // Endpoint: GET /api/estudiantes/{id}
    getEstudianteById(id: number): Observable<Estudiante> {
        return this.http.get<Estudiante>(`${this.apiUrl}/estudiantes/${id}`);
    }

    // --- Inscripciones ---

    getAsignaturasActivas(estudianteId: number): Observable<Inscripcion[]> {
    const params = new HttpParams()
      .set('estudianteId', estudianteId.toString());
      // 🚨 CORRECCIÓN CLAVE: Cambiar el path a '/inscripciones/activas'
    return this.http.get<Inscripcion[]>(`${this.apiUrl}/inscripciones/activas`, { params });
  }
    
    // Endpoint: GET /api/asignaturas
    getAsignaturasDisponibles(): Observable<Asignatura[]> {
        return this.http.get<Asignatura[]>(`${this.apiUrl}/asignaturas`);
    }

    // Endpoint: POST /api/inscripciones/inscribir
    inscribirAsignatura(request: InscripcionRequest): Observable<Inscripcion> {
        return this.http.post<Inscripcion>(`${this.apiUrl}/inscripciones/inscribir`, request);
    }

    // Endpoint: PUT /api/inscripciones/cancelar/{id}?operador=CLIENTE
    cancelarInscripcion(inscripcionId: number): Observable<void> {
        const params = new HttpParams().set('operador', 'CLIENTE');
        // El cuerpo es nulo, lo cual es correcto para PUT con parámetros
        return this.http.put<void>(`${this.apiUrl}/inscripciones/cancelar/${inscripcionId}`, null, { params });
    }

    // --- Historial Académico ---

    // Endpoint: GET /api/historial-academico?estudianteId=X
    getHistorialAcademico(estudianteId: number): Observable<HistorialAcademico[]> {
        const params = new HttpParams().set('estudianteId', estudianteId.toString());
        return this.http.get<HistorialAcademico[]>(`${this.apiUrl}/historial-academico`, { params });
    }

    // --- Horario PDF ---

    // 🚨 RECOMENDACIÓN PDF: Cambiar el tipo de respuesta a Blob para descargar el PDF
    // Endpoint: GET /api/horarios/pdf/{estudianteId}
    generarHorarioPDF(estudianteId: number): Observable<Blob> {
        // responseType: 'blob' es necesario para manejar archivos binarios (PDF)
        return this.http.get(`${this.apiUrl}/horarios/pdf/${estudianteId}`, { responseType: 'blob' });
    }
}