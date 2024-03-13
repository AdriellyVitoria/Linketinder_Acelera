import { CandidatoService } from "../service/candidato-service.js";
import { EmpresaService } from "../service/empresa-service.js";
import { UsuarioLogadoService } from "../service/usuario-logado-service.js";
import { CadastroCandidato } from "./cadastroCandidato.js";
import { CadastroEmpresa } from "./cadastroEmpresa.js";
import { PerfilCandidatoController } from "./perfilCandidatoController.js";
import { PerfilEmpresaController } from "./perfilEmpresaController.js";

export class LoginController {
    private readonly candidatoService: CandidatoService
    private readonly empresaService: EmpresaService
    private readonly perfilCandidato: PerfilCandidatoController
    private readonly perfilEmpresa: PerfilEmpresaController
    private readonly cadastroCandidato: CadastroCandidato
    private readonly cadastroEmpresa: CadastroEmpresa

    constructor(
        private readonly cabecalho: HTMLElement,
        private readonly conteudo: HTMLElement,
        private readonly usuarioLogadoService: UsuarioLogadoService

    ) {
        this.candidatoService = new CandidatoService()
        this.empresaService = new EmpresaService()
        this.perfilCandidato = new PerfilCandidatoController(cabecalho, conteudo, usuarioLogadoService)
        this.perfilEmpresa = new PerfilEmpresaController(cabecalho, conteudo, usuarioLogadoService)
        this.cadastroCandidato = new CadastroCandidato(cabecalho, conteudo)
        this.cadastroEmpresa = new CadastroEmpresa(cabecalho, conteudo)
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
        const senhaEmpresa = document.querySelector(".login__empresa__senha input") as HTMLInputElement

        const empresa = this.empresaService.validarLogin(emailEmpresa.value, senhaEmpresa.value)
        if (empresa) {
            this.usuarioLogadoService.fazerLogin(empresa)
            this.perfilEmpresa.carregarTelaDePerfil()
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
            this.cadastroCandidato.carregarTelaCadastro()
        })

        botaoCadastroEmpresa.addEventListener("click", () => {
            this.cadastroEmpresa.carregarTelaCadastro()
        })
    }

    private preecherHtml(): void {
        this.cabecalho.innerHTML = `<h1>Linketinder</h1>`
        this.conteudo.innerHTML = `
            <div class="form__login">
                <h2>Empresas</h2>
                
                <div class="login__empresa__email login__input">
                    <label for="email_empresa">Digite seu email</label>
                    <input type="email" name="email_empresa" placeholder="email@gmail.com">
                </div>

                <div class="login__empresa__senha login__input">
                    <label for="senha_empresa">Digite sua senha</label>
                    <input type="password" name="senha_empresa" placeholder="********">
                </div>

                <button class="botao__login__empresa login__botao">Login como Empresa</button>
                <button class="botao__empresa__cadastro cadastro__botao">Cadastrar sua empresa aqui</button>

            </div>

            <div class="form__login">
                <h2>Candidatos</h2>
                
                <div class="login__candidato__email login__input">
                    <label for="email_candidato">Digite seu email</label>
                    <input type="email" name="email_candidato" placeholder="email@gmail.com">
                </div>

                <div class="login__candidato__senha login__input">
                    <label for="senha_candidato">Digite sua senha</label>
                    <input type="password" name="senha_candidato" placeholder="********">
                </div>

                <button class="botao__login__candidato login__botao">Login como Candidato</button>
                <button class="botao__cadastro__candidato cadastro__botao">Cadastrar seu usuário aqui</button>

            </div>`
    }
}