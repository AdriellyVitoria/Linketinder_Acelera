import { Candidato } from "../interfaces/candidato.js"
import { Empresa } from "../interfaces/empresa.js"
import { CandidatoService } from "../service/candidato-service.js"
import { EmpresaService } from "../service/empresa-service.js"
import { UsuarioLogadoService } from "../service/usuario-logado-service.js"

export class PerfilCandidatoController {
    private readonly candidatoService: CandidatoService
    private readonly empresaService: EmpresaService
    private usuario: Candidato | null

    constructor(
        private readonly cabecalho: HTMLElement,
        private readonly conteudo: HTMLElement,
        private readonly usuarioLogadoService: UsuarioLogadoService
    ) {
        this.candidatoService = new CandidatoService()
        this.empresaService = new EmpresaService()
    }

    public carregarTelaDePerfil(): void {
        this.usuario = this.usuarioLogadoService.usuario as Candidato
        this.montarEstruturaHtml()
        this.preencherPerfil()
        this.preencherFeed()
        this.setupBotoes()
    }
    
    private montarEstruturaHtml(): void {
        this.cabecalho.innerHTML = `
            <div>
                <h1>Linketinder</h1>
                <p>Olá, ${this.usuario.nome}</p>
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
            <p><span>Nome:</span> ${this.usuario.nome}</p>
            <p><span>Idade:</span> ${this.usuario.idade} anos</p>
            <p><span>Email:</span> ${this.usuario.email}</p>
            <p><span>Estado:</span> ${this.usuario.estado}</p>
            <p><span>Cep:</span> ${this.usuario.cep}</p>
            <p><span>CPF:</span> ${this.usuario.cpf}</p>
            <p><span>Competências:</span> ${this.usuario.competencias.join(', ')}</p>
            <p><span>Descrição:</span> ${this.usuario.descricao}</p>`
    }

    private preencherFeed(): void {
        const feedCandidato = document.querySelector(".feed__do__usuario") as HTMLDivElement
        feedCandidato.innerHTML = ''
        this.empresaService.buscaEmpresas().forEach(e => {
            const compatibilidade = this.calcularCompatibilidade(e)
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
                </div>`
        })
    }

    private preencheEmpresaMatch(empresa: Empresa): string {
        if (this.usuario.aplicacoes_em_empresas.find(a => a.id == empresa.id)?.match == true) {
            return `
                <p><span>Nome:</span> ${empresa.nome}</p>
                <p><span>Pais:</span> ${empresa.pais}</p>
                <p><span>Email:</span> ${empresa.email}</p>
                <p><span>Estado:</span> ${empresa.estado}</p>
                <p><span>Cep:</span> ${empresa.cep}</p>
                <p><span>CNPJ:</span> ${empresa.cnpj}</p>`
        }
        return ''
    }

    private criarBotaoAplicar(empresaId: number): string {
        if (this.usuario.aplicacoes_em_empresas.find(a => a.id == empresaId)?.match == false) {
            return `<button class="botao__aplicar__empresa" disabled>Aplicada</button>`
        }
        if (this.usuario.aplicacoes_em_empresas.find(a => a.id == empresaId)?.match == true) {
            return `<button class="botao__aplicar__empresa" disabled>Match</button>`
        }
        return `<button class="botao__aplicar__empresa">Aplicar</button>`
    }

    private calcularCompatibilidade(empresa: Empresa): number {
        let habilidadesPossuidas = 0
        this.usuario.competencias.forEach(c => {
            if (empresa.competencias.includes(c))
                habilidadesPossuidas++
        })
        const compatibilidade = 100 / empresa.competencias.length * habilidadesPossuidas
        return Math.trunc(compatibilidade)
    }

    private setupBotoes(): void {
        const botaoSair = document.querySelector('.botao__sair') as HTMLButtonElement

        botaoSair.addEventListener("click", () => {
            location.reload()
        })

        const botoesAplicar = document.querySelectorAll(".botao__aplicar__empresa")

        botoesAplicar.forEach((botao: HTMLButtonElement) => {
            botao.addEventListener("click", () => {
                this.aplicarVaga(botao)
            })
        })
    }

    private aplicarVaga(botao: HTMLButtonElement): void {
        const empresaId: number = Number.parseInt(botao.parentElement.parentElement.id)
        const empresa = this.empresaService.buscarPorId(empresaId)

        empresa.candidatos.push({ id: this.usuario.id, match: false})
        this.empresaService.editarEmpresa(empresa)

        this.usuario.aplicacoes_em_empresas.push({ id: empresa.id, match: false})
        this.candidatoService.editarCandidato(this.usuario)

        this.preencherFeed()
        this.setupBotoes()
    }
}