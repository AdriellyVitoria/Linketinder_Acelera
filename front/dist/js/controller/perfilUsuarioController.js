import { CandidatoService } from "../service/candidato-service.js";
import { EmpresaService } from "../service/empresa-service.js";
export class PerfilUsuarioController {
    cabecalho;
    conteudo;
    candidatoService;
    empresaService;
    usuario;
    constructor(cabecalho, conteudo, usuarioLogadoService) {
        this.cabecalho = cabecalho;
        this.conteudo = conteudo;
        this.candidatoService = new CandidatoService();
        this.empresaService = new EmpresaService();
        this.usuario = usuarioLogadoService.usuario;
    }
    carregarTelaDePerfil() {
        this.montarEstruturaHtml();
        this.preencherPerfil();
        this.preencherFeed();
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
            <p>Competencias: ${this.usuario.competencias.concat(', ')}</p>
            <p>Descrição: ${this.usuario.descricao}</p>`;
    }
    preencherFeed() {
        const feedCandidato = document.querySelector(".feed__do__candidato");
        this.empresaService.buscaEmpresas().forEach(e => {
            feedCandidato.innerHTML += `
                <div class="empresa__no__feed" id="${e.id}">
                    <div>
                        <p>Descrição: ${e.descricao}</p>
                        <p>Competencia: ${e.competencias.concat(', ')}
                    </div>
                    <button class="botao__aplicar__empresa">Aplicar para empresa</button>
                </div>`;
        });
    }
}
