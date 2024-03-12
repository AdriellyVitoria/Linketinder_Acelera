import { LoginController } from "./controller/loginController.js"
import { UsuarioLogadoService } from "./service/usuario-logado-service.js"

const cabecalho = document.querySelector(".cabecalho") as HTMLElement
const conteudo = document.querySelector(".conteudo") as HTMLElement

const usuarioLogadoService = new UsuarioLogadoService()
const loginController = new LoginController(cabecalho, conteudo, usuarioLogadoService)

loginController.carregarTelaLogin()