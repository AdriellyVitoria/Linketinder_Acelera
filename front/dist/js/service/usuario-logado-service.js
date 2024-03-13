export class UsuarioLogadoService {
    usuarioLogado = null;
    fazerLogin(usuario) {
        this.usuarioLogado = usuario;
    }
    get usuario() {
        return this.usuarioLogado;
    }
}
