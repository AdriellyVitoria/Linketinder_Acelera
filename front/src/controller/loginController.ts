import { CandidatoService } from "../service/candidato-service.js";
import { EmpresaService } from "../service/empresa-service.js";
import { UsuarioLogadoService } from "../service/usuario-logado-service.js";
import { PerfilCandidatoController } from "./perfilCandidatoController.js";

export class LoginController {
    private readonly candidatoService: CandidatoService
    private readonly empresaService: EmpresaService
    private readonly perfilCandidato: PerfilCandidatoController 

    constructor(
        private readonly cabecalho: HTMLElement,
        private readonly conteudo: HTMLElement,
        private readonly usuarioLogadoService: UsuarioLogadoService
    ) {
        this.candidatoService = new CandidatoService()
        this.empresaService = new EmpresaService()
        this.perfilCandidato = new PerfilCandidatoController(cabecalho, conteudo, usuarioLogadoService, this)
    }

    public carregarTelaLogin(): void {
        this.preecherHtml()
        this.setupBotoes()
    }

    private fazerLoginCandidato(): void {
        const emailCandidato = document.querySelector(".login__candidato__email input") as HTMLInputElement
        const senhaCandidato = document.querySelector(".login__candidato__senha input") as HTMLInputElement

        const candidato = this.candidatoService.validarLogin(emailCandidato.value, senhaCandidato.value)
        if (candidato) {
            this.usuarioLogadoService.fazerLogin(candidato)
            this.perfilCandidato.carregarTelaDePerfil()
        } else {
            alert("Email ou Senha incorretos")
        }
    }

    private fazerLoginEmpresa(): void {
        const emailEmpresa = document.querySelector(".login__empresa__email input") as HTMLInputElement
        const senhaEmpresa = document.querySelector("login__empresa__senha input") as HTMLInputElement

        const empresa = this.empresaService.validarLogin(emailEmpresa.value, senhaEmpresa.value)
        if (empresa) {
            this.usuarioLogadoService.fazerLogin(empresa)
            // manda para a tela de perfil
        } else {
            alert("Email ou Senha incorretos")
        }
    }

    private setupBotoes(): void {
        const botaoLoginCandidato = document.querySelector(".botao__login__candidato") as HTMLButtonElement
        const botaoLoginEmpresa = document.querySelector(".botao__login__empresa") as HTMLButtonElement

        const botaoCadastroCandidato = document.querySelector(".botao__cadastro__candidato") as HTMLButtonElement
        const botaoCadastroEmpresa = document.querySelector(".botao__empresa__cadastro") as HTMLButtonElement

        botaoLoginCandidato.addEventListener("click", () => {
            this.fazerLoginCandidato()
        }) 

        botaoLoginEmpresa.addEventListener("click", () => {
            this.fazerLoginEmpresa()
        })

        botaoCadastroCandidato.addEventListener("click", () => {

        })

        botaoCadastroEmpresa.addEventListener("click", () => {

        })
    }

    private preecherHtml(): void {
        this.cabecalho.innerHTML = `<h1>Linketinder</h1>`
        this.conteudo.innerHTML = `<div class="login__empresa">
            <h2>Empresas</h2>
            
            <div class="login__empresa__email">
                <label for="email_empresa">Digite seu email</label>
                <input type="email" name="email_empresa" placeholder="email@gmail.com">
            </div>

            <div class="login__empresa__senha">
                <label for="senha_empresa">Digite sua senha</label>
                <input type="password" name="senha_empresa" placeholder="********">
            </div>

            <button class="botao__login__empresa">Entrar</button>
            <button class="botao__empresa__cadastro">Cadastrar sua empresa aqui</button>

        </div>

        <div class="login__candidato">
            <h2>Candidatos</h2>
            
            <div class="login__candidato__email">
                <label for="email_candidato">Digite seu email</label>
                <input type="email" name="email_candidato" placeholder="email@gmail.com">
            </div>

            <div class="login__candidato__senha">
                <label for="senha_candidato">Digite sua senha</label>
                <input type="password" name="senha_candidato" placeholder="********">
            </div>

            <button class="botao__login__candidato">Entrar</button>
            <button class="botao__cadastro__candidato">Cadastrar seu usuário aqui</button>

        </div>`
    }
}