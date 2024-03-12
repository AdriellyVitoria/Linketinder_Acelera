import { CandidatoService } from "../service/candidato-service.js";
import { EmpresaService } from "../service/empresa-service.js";
export class PerfilEmpresaController {
    cabecalho;
    conteudo;
    usuarioLogadoService;
    loginController;
    candidatoService;
    empresaService;
    empresa;
    constructor(cabecalho, conteudo, usuarioLogadoService, loginController) {
        this.cabecalho = cabecalho;
        this.conteudo = conteudo;
        this.usuarioLogadoService = usuarioLogadoService;
        this.loginController = loginController;
        this.candidatoService = new CandidatoService();
        this.empresaService = new EmpresaService();
    }
    carregarTelaDePerfil() {
        this.empresa = this.usuarioLogadoService.usuario;
        this.montarEstruturaHtml();
        this.preencherPerfil();
        this.preencherFeed();
        this.setupBotoes();
    }
    montarEstruturaHtml() {
        this.cabecalho.innerHTML = `
            <div>
                <h1>Linketinder</h1>
                <p>Olá, ${this.empresa.nome}</p>
            </div>
            <button class="botao__sair">Sair</button>`;
        this.conteudo.innerHTML = `
            <div class="perfil__usuario">
                
            </div>
            <div class="feed__do__candidato">
            
            </div>`;
    }
    preencherPerfil() {
        const perfilUsuario = document.querySelector(".perfil__usuario");
        perfilUsuario.innerHTML = `
            <p>Nome: ${this.empresa.nome}</p>
            <p>Idade: ${this.empresa.pais} anos</p>
            <p>Email: ${this.empresa.email}</p>
            <p>Estado: ${this.empresa.estado}</p>
            <p>Cep: ${this.empresa.cep}</p>
            <p>CPF: ${this.empresa.cnpj}</p>
            <p>Competencias: ${this.empresa.competencias.join(', ')}</p>
            <p>Descrição: ${this.empresa.descricao}</p>`;
    }
    preencherFeed() {
        const feedCandidato = document.querySelector(".feed__do__candidato");
        feedCandidato.innerHTML = '';
        this.candidatoService.buscaCandidatos(this.empresa.candidatos).forEach(c => {
            feedCandidato.innerHTML += `
                <div class="empresa__no__feed" id="${c.id}">
                    <div>
                        <p>Competencia: ${c.competencias.join(', ')}
                    </div>

                </div>`;
            // grafico
        });
    }
    setupBotoes() {
        const botaoSair = document.querySelector('.botao__sair');
        botaoSair.addEventListener("click", () => {
            this.usuarioLogadoService.logout();
            this.empresa = null;
            this.loginController.carregarTelaLogin();
        });
    }
}
