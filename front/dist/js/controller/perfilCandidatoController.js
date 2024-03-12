import { CandidatoService } from "../service/candidato-service.js";
import { EmpresaService } from "../service/empresa-service.js";
export class PerfilCandidatoController {
    cabecalho;
    conteudo;
    usuarioLogadoService;
    loginController;
    candidatoService;
    empresaService;
    usuario;
    constructor(cabecalho, conteudo, usuarioLogadoService, loginController) {
        this.cabecalho = cabecalho;
        this.conteudo = conteudo;
        this.usuarioLogadoService = usuarioLogadoService;
        this.loginController = loginController;
        this.candidatoService = new CandidatoService();
        this.empresaService = new EmpresaService();
    }
    carregarTelaDePerfil() {
        this.usuario = this.usuarioLogadoService.usuario;
        this.montarEstruturaHtml();
        this.preencherPerfil();
        this.preencherFeed();
        this.setupBotoes();
    }
    montarEstruturaHtml() {
        this.cabecalho.innerHTML = `
            <div>
                <h1>Linketinder</h1>
                <p>Olá, ${this.usuario.nome}</p>
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
            <p>Nome: ${this.usuario.nome}</p>
            <p>Idade: ${this.usuario.idade} anos</p>
            <p>Email: ${this.usuario.email}</p>
            <p>Estado: ${this.usuario.estado}</p>
            <p>Cep: ${this.usuario.cep}</p>
            <p>CPF: ${this.usuario.cpf}</p>
            <p>Competencias: ${this.usuario.competencias.join(', ')}</p>
            <p>Descrição: ${this.usuario.descricao}</p>`;
    }
    preencherFeed() {
        const feedCandidato = document.querySelector(".feed__do__candidato");
        feedCandidato.innerHTML = '';
        this.empresaService.buscaEmpresas().forEach(e => {
            feedCandidato.innerHTML += `
                <div class="empresa__no__feed" id="${e.id}">
                    <div>
                        <p>Descrição: ${e.descricao}</p>
                        <p>Competencia: ${e.competencias.join(', ')}
                    </div>
                    ${this.criarBotaoAplicar(e.id)}
                </div>`;
        });
    }
    criarBotaoAplicar(empresaId) {
        if (this.usuario.aplicacoes_em_empresas.includes(empresaId)) {
            return `<button class="botao__aplicar__empresa" disabled>Aplicada</button>`;
        }
        return `<button class="botao__aplicar__empresa">Aplicar para empresa</button>`;
    }
    setupBotoes() {
        const botaoSair = document.querySelector('.botao__sair');
        botaoSair.addEventListener("click", () => {
            this.usuarioLogadoService.logout();
            this.usuario = null;
            this.loginController.carregarTelaLogin();
        });
        const botoesAplicar = document.querySelectorAll(".botao__aplicar__empresa");
        botoesAplicar.forEach((botao) => {
            botao.addEventListener("click", () => {
                this.aplicarVaga(botao);
            });
        });
    }
    aplicarVaga(botao) {
        const empresaId = Number.parseInt(botao.parentElement.id);
        const empresa = this.empresaService.buscarPorId(empresaId);
        empresa.candidatos.push(this.usuario.id);
        this.empresaService.editarEmpresa(empresa);
        this.usuario.aplicacoes_em_empresas.push(empresa.id);
        this.candidatoService.editarCandidato(this.usuario);
        this.preencherFeed();
        this.setupBotoes();
    }
}
