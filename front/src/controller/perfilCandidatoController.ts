import { Candidato } from "../interfaces/candidato.js"
import { CandidatoService } from "../service/candidato-service.js"
import { EmpresaService } from "../service/empresa-service.js"
import { UsuarioLogadoService } from "../service/usuario-logado-service.js"

export class PerfilCandidatoController {
    private readonly candidatoService: CandidatoService
    private readonly empresaService: EmpresaService
    private readonly usuario: Candidato

    constructor(
        private readonly cabecalho: HTMLElement,
        private readonly conteudo: HTMLElement,
        usuarioLogadoService: UsuarioLogadoService
    ) {
        this.candidatoService = new CandidatoService()
        this.empresaService = new EmpresaService()
        this.usuario = usuarioLogadoService.usuario as Candidato
    }

    public carregarTelaDePerfil(): void {
        this.montarEstruturaHtml()
        this.preencherPerfil()
        this.preencherFeed()
    }

    private montarEstruturaHtml(): void {
        this.cabecalho.innerHTML = `
            <div>
                <h1>Linketinder</h1>
                <p>Olá, ${this.usuario.nome}</p>
            </div>
            <button class="botao__sair">Sair</button>`

        this.conteudo.innerHTML = `
            <div class="perfil__usuario">
                
            </div>
            <div class="feed__do__candidato">
            
            </div>`
    }

    private preencherPerfil(): void {
        const perfilUsuario = document.querySelector(".perfil__usuario") as HTMLDivElement
        perfilUsuario.innerHTML = `
            <p>Nome: ${this.usuario.nome}</p>
            <p>Idade: ${this.usuario.idade} anos</p>
            <p>Email: ${this.usuario.email}</p>
            <p>Estado: ${this.usuario.estado}</p>
            <p>Cep: ${this.usuario.cep}</p>
            <p>CPF: ${this.usuario.cpf}</p>
            <p>Competencias: ${this.usuario.competencias.concat(', ')}</p>
            <p>Descrição: ${this.usuario.descricao}</p>`
    }

    private preencherFeed(): void {
        const feedCandidato = document.querySelector(".feed__do__candidato") as HTMLDivElement
        this.empresaService.buscaEmpresas().forEach(e => {
            feedCandidato.innerHTML += `
                <div class="empresa__no__feed" id="${e.id}">
                    <div>
                        <p>Descrição: ${e.descricao}</p>
                        <p>Competencia: ${e.competencias.concat(', ')}
                    </div>
                    <button class="botao__aplicar__empresa">Aplicar para empresa</button>
                </div>`
        })
    }
}