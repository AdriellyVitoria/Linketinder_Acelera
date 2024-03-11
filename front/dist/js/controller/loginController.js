export class LoginController {
    cabecalho;
    conteudo;
    usuarioLogado = null;
    constructor(cabecalho, conteudo) {
        this.cabecalho = cabecalho;
        this.conteudo = conteudo;
    }
    carregarTelaLogin() {
        const cabecalho = document.querySelector(".cabecalho");
        const conteudo = document.querySelector(".conteudo");
    }
}
