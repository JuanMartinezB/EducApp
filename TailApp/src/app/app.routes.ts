import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login-component';
import { InscripcionComponent } from './pages/inscripcion/inscripcion-component';
import { HistorialComponent } from './pages/historial/historial-component';
import { HorarioComponent } from './pages/horario/horario-component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'inscripcion', component: InscripcionComponent },
  { path: 'historial', component: HistorialComponent },
  { path: 'horario', component: HorarioComponent },
  
  // Ruta por defecto y comodín
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' } 
];

