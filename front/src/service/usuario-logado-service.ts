import { Candidato } from "../interfaces/candidato.js";
import { Empresa } from "../interfaces/empresa.js";

export class UsuarioLogadoService {
    private usuarioLogado: Candidato | Empresa | null = null

    public fazerLogin(usuario: Candidato | Empresa): void {
        this.usuarioLogado = usuario
    }

    get usuario(): Candidato | Empresa | null {
        return this.usuarioLogado
    }
}