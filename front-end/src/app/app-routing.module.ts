import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { AlbumListComponent } from './albums/album-list/album-list.component';
import { UsuarioAutenticadoGuard } from './services/guards/usuario-autenticado.guard';
import { UsuarioNaoAutenticadoGuard } from './services/guards/usuario-nao-autenticado.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent, canActivate:[UsuarioNaoAutenticadoGuard]},
  {
    path: '', component: AlbumListComponent, canActivate: [UsuarioAutenticadoGuard],
   },
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
