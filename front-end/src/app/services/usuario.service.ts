import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { tap } from 'rxjs/operators';
import { IUsuario } from '../auth/model/IUsuario.model';
@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
constructor(private httpClient: HttpClient,
            private router: Router) { }
logar(usuario: IUsuario) : Observable<any> {

return this.mockUsuarioLogin(usuario).pipe(tap((resposta) => {
if(!resposta.sucesso) return;
localStorage.setItem('token', btoa(JSON.stringify("TokenQueSeriaGeradoPelaAPI")));
localStorage.setItem('usuario', btoa(JSON.stringify(usuario)));
this.router.navigate(['']);
}));
}
  private mockUsuarioLogin(usuario: IUsuario): Observable<any> {
    var retornoMock: any = [];
    if(usuario.email === "admin@admin.com" && usuario.senha == "123"){
      retornoMock.sucesso = true;
      retornoMock.usuario = usuario;
      retornoMock.token = "TokenQueSeriaGeradoPelaAPI";
      return of(retornoMock);
    }
    retornoMock.sucesso = false;
    retornoMock.usuario = usuario;
    return of(retornoMock);
  }
  deslogar() {
      localStorage.clear();
      this.router.navigate(['login']);
  }
get obterUsuarioLogado(): IUsuario | null {
  const usuarioString = localStorage.getItem('usuario');
  return usuarioString ? JSON.parse(atob(usuarioString)) : null;
}
get obterIdUsuarioLogado(): string {
  const usuarioString = localStorage.getItem('usuario');
  if (usuarioString) {
    const usuario: IUsuario = JSON.parse(atob(usuarioString));
    return usuario.id;
  } else {
    return '';
  }
}
get obterTokenUsuario(): string {
  const token = localStorage.getItem('token');
  if (token) {
    return token;
  } else {
    return '';
  }
}

get logado(): boolean {
return localStorage.getItem('token') ? true : false;
}
}
