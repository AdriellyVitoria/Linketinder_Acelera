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
            <div class="perfil__usuario">
                
            </div>
            <div class="feed__do__candidato">
            
            </div>`
    }

    private preencherPerfil(): void {
        const perfilUsuario = document.querySelector(".perfil__usuario") as HTMLDivElement
        perfilUsuario.innerHTML = `
            <p>Nome: ${this.empresa.nome}</p>
            <p>Idade: ${this.empresa.pais} anos</p>
            <p>Email: ${this.empresa.email}</p>
            <p>Estado: ${this.empresa.estado}</p>
            <p>Cep: ${this.empresa.cep}</p>
            <p>CPF: ${this.empresa.cnpj}</p>
            <p>Competencias: ${this.empresa.competencias.join(', ')}</p>
            <p>Descrição: ${this.empresa.descricao}</p>`
    }

    private preencherFeed(): void {
        const feedCandidato = document.querySelector(".feed__do__candidato") as HTMLDivElement
        feedCandidato.innerHTML = ''
        this.candidatoService.buscaCandidatos(this.empresa.candidatos).forEach(c => {
            feedCandidato.innerHTML += `
                <div class="empresa__no__feed" id="${c.id}">
                    <div>
                        <p>Competencia: ${c.competencias.join(', ')}
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
            this.loginController.carregarTelaLogin()
        })
    }
        
}
