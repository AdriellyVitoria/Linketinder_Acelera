export class UsuarioLogadoService {
    usuarioLogado = null;
    fazerLogin(usuario) {
        this.usuarioLogado = usuario;
    }
    logout() {
        this.usuarioLogado = null;
    }
    get usuario() {
        return this.usuarioLogado;
    }
}
