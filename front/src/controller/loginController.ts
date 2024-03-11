import { Cantidato } from "../interfaces/candidato";
import { Empresa } from "../interfaces/empresa";

export class LoginController {
    public usuarioLogado: Cantidato | Empresa | null = null
    
    constructor(
        private readonly cabecalho: HTMLElement,
        private readonly conteudo: HTMLElement
    ) {

    }

    public carregarTelaLogin(): void {
        const cabecalho = document.querySelector(".cabecalho")
        const conteudo = document.querySelector(".conteudo")
    }
}