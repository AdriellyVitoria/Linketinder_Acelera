import { CandidatoService } from "../service/candidato-service.js";
import { EmpresaService } from "../service/empresa-service.js";
export class PerfilCandidatoController {
    cabecalho;
    conteudo;
    usuarioLogadoService;
    candidatoService;
    empresaService;
    usuario;
    constructor(cabecalho, conteudo, usuarioLogadoService) {
        this.cabecalho = cabecalho;
        this.conteudo = conteudo;
        this.usuarioLogadoService = usuarioLogadoService;
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
            <div class="contudo__perfil">
                <div class="perfil__usuario">
                    
                </div>
                <div class="feed__do__usuario">
                
                </div>
            </div>`;
    }
    preencherPerfil() {
        const perfilUsuario = document.querySelector(".perfil__usuario");
        perfilUsuario.innerHTML = `
            <p><span>Nome:</span> ${this.usuario.nome}</p>
            <p><span>Idade:</span> ${this.usuario.idade} anos</p>
            <p><span>Email:</span> ${this.usuario.email}</p>
            <p><span>Estado:</span> ${this.usuario.estado}</p>
            <p><span>Cep:</span> ${this.usuario.cep}</p>
            <p><span>CPF:</span> ${this.usuario.cpf}</p>
            <p><span>Competências:</span> ${this.usuario.competencias.join(', ')}</p>
            <p><span>Descrição:</span> ${this.usuario.descricao}</p>`;
    }
    preencherFeed() {
        const feedCandidato = document.querySelector(".feed__do__usuario");
        feedCandidato.innerHTML = '';
        this.empresaService.buscaEmpresas().forEach(e => {
            const compatibilidade = this.calcularCompatibilidade(e);
            feedCandidato.innerHTML += `
                <div class="empresa__no__feed" id="${e.id}">
                    <div>
                        ${this.preencheEmpresaMatch(e)}
                        <p><span>Descrição:</span> ${e.descricao}</p>
                        <p><span>Competencia:</span> ${e.competencias.join(', ')}
                    </div>
                    <div class="acoes__aplicacao">
                        <p>${compatibilidade}%</p>
                        ${this.criarBotaoAplicar(e.id)}
                    </div>
                </div>`;
        });
    }
    preencheEmpresaMatch(empresa) {
        if (this.usuario.aplicacoes_em_empresas.find(a => a.id == empresa.id)?.match == true) {
            return `
                <p><span>Nome:</span> ${empresa.nome}</p>
                <p><span>Pais:</span> ${empresa.pais}</p>
                <p><span>Email:</span> ${empresa.email}</p>
                <p><span>Estado:</span> ${empresa.estado}</p>
                <p><span>Cep:</span> ${empresa.cep}</p>
                <p><span>CNPJ:</span> ${empresa.cnpj}</p>`;
        }
        return '';
    }
    criarBotaoAplicar(empresaId) {
        if (this.usuario.aplicacoes_em_empresas.find(a => a.id == empresaId)?.match == false) {
            return `<button class="botao__aplicar__empresa" disabled>Aplicada</button>`;
        }
        if (this.usuario.aplicacoes_em_empresas.find(a => a.id == empresaId)?.match == true) {
            return `<button class="botao__aplicar__empresa" disabled>Match</button>`;
        }
        return `<button class="botao__aplicar__empresa">Aplicar</button>`;
    }
    calcularCompatibilidade(empresa) {
        let habilidadesPossuidas = 0;
        this.usuario.competencias.forEach(c => {
            if (empresa.competencias.includes(c))
                habilidadesPossuidas++;
        });
        const compatibilidade = 100 / empresa.competencias.length * habilidadesPossuidas;
        return Math.trunc(compatibilidade);
    }
    setupBotoes() {
        const botaoSair = document.querySelector('.botao__sair');
        botaoSair.addEventListener("click", () => {
            location.reload();
        });
        const botoesAplicar = document.querySelectorAll(".botao__aplicar__empresa");
        botoesAplicar.forEach((botao) => {
            botao.addEventListener("click", () => {
                this.aplicarVaga(botao);
            });
        });
    }
    aplicarVaga(botao) {
        const empresaId = Number.parseInt(botao.parentElement.parentElement.id);
        const empresa = this.empresaService.buscarPorId(empresaId);
        empresa.candidatos.push({ id: this.usuario.id, match: false });
        this.empresaService.editarEmpresa(empresa);
        this.usuario.aplicacoes_em_empresas.push({ id: empresa.id, match: false });
        this.candidatoService.editarCandidato(this.usuario);
        this.preencherFeed();
        this.setupBotoes();
    }
}
