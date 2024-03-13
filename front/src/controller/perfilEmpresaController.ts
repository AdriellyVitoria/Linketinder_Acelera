import { Empresa } from "../interfaces/empresa.js"
import { CandidatoService } from "../service/candidato-service.js"
import { EmpresaService } from "../service/empresa-service.js"
import { UsuarioLogadoService } from "../service/usuario-logado-service.js"
import { LoginController } from "./loginController.js"

export class PerfilEmpresaController {
    private readonly candidatoService: CandidatoService
    private readonly empresaService: EmpresaService
    private empresa: Empresa | null

    constructor(
        private readonly cabecalho: HTMLElement,
        private readonly conteudo: HTMLElement,
        private readonly usuarioLogadoService: UsuarioLogadoService,
        private readonly loginController: LoginController
    ) {
        this.candidatoService = new CandidatoService()
        this.empresaService = new EmpresaService()
    }

    public carregarTelaDePerfil(): void {
        this.empresa = this.usuarioLogadoService.usuario as Empresa
        this.montarEstruturaHtml()
        this.preencherPerfil()
        this.preencherFeed()
        this.setupBotoes()
    }

    private montarEstruturaHtml(): void {
        this.cabecalho.innerHTML = `
            <div>
                <h1>Linketinder</h1>
                <p>Olá, ${this.empresa.nome}</p>
            </div>
            <button class="botao__sair">Sair</button>`

        this.conteudo.innerHTML = `
            <div class="contudo__perfil">
                <div class="perfil__usuario">
                    
                </div>
                <div class="feed__do__usuario">
                
                </div>
            </div>`
    }

    private preencherPerfil(): void {
        const perfilUsuario = document.querySelector(".perfil__usuario") as HTMLDivElement
        perfilUsuario.innerHTML = `
            <p><span>Nome:</span> ${this.empresa.nome}</p>
            <p><span>Idade:</span> ${this.empresa.pais} anos</p>
            <p><span>Email:</span> ${this.empresa.email}</p>
            <p><span>Estado:</span> ${this.empresa.estado}</p>
            <p><span>Cep:</span> ${this.empresa.cep}</p>
            <p><span>CPF:</span> ${this.empresa.cnpj}</p>
            <p><span>Competencias:</span> ${this.empresa.competencias.join(', ')}</p>
            <p><span>Descrição:</span> ${this.empresa.descricao}</p>`
    }

    private preencherFeed(): void {
        const feedCandidato = document.querySelector(".feed__do__usuario") as HTMLDivElement
        feedCandidato.innerHTML = ''
        this.candidatoService.buscaCandidatos(this.empresa.candidatos).forEach(c => {
            feedCandidato.innerHTML += `
                <div class="empresa__no__feed" id="${c.id}">
                    <div>
                        <p><span>Competencias:</span> ${c.competencias.join(', ')}
                    </div>
                </div>`
                // grafico
        })
    }

    private setupBotoes(): void {
        const botaoSair = document.querySelector('.botao__sair') as HTMLButtonElement

        botaoSair.addEventListener("click", () => {
            this.usuarioLogadoService.logout()
            this.empresa = null
            location.reload()
        })
    }
}
