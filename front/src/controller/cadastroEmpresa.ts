import { Empresa } from "../interfaces/empresa.js"
import { listaCompetencias } from "../listasBases/competencias.js"
import { EmpresaService } from "../service/empresa-service.js"
import { GeradorID } from "../service/gerador-id.js"

export class CadastroEmpresa {
    private readonly geradorId: GeradorID
    private readonly empresaService: EmpresaService

    constructor (
        private readonly cabecalho: HTMLElement,
        private readonly conteudo: HTMLElement
    ) {
        this.geradorId = new GeradorID()
        this.empresaService = new EmpresaService
    }

    public carregarTelaCadastro(): void {
        this.preecherHTML()
        this.preecherCompetencias()
        this.setupBotoes()
    }

    private salvaImformacoes(): void {
        const nome = document.querySelector(".input__nome input") as HTMLInputElement
        const pais = document.querySelector(".input__idade input") as HTMLInputElement
        const telefone = document.querySelector(".input__telefone input") as HTMLInputElement
        const cnpj = document.querySelector(".input__cpf input") as HTMLInputElement
        const estado = document.querySelector(".input__estado input") as HTMLInputElement
        const cep = document.querySelector(".input__cep input") as HTMLInputElement
        const email = document.querySelector(".input__email input") as HTMLInputElement
        const senha = document.querySelector(".input__senha input") as HTMLInputElement
        const competencia = document.querySelector(".input__competencias select") as HTMLSelectElement
        const descricao = document.querySelector(".input__descricao textarea") as HTMLTextAreaElement

        const competenciasSelecionadas = this.competenciasSelecionadas(competencia)

        const novaEmpresa: Empresa = {
            id: this.geradorId.gerarIdEmpresa(),
            nome: nome.value,
            telefone: telefone.value,
            cnpj: cnpj.value,
            estado: estado.value,
            cep: cep.value,
            email: email.value,
            competencias: competenciasSelecionadas,
            descricao: descricao.value,
            pais: pais.value,
            senha: senha.value,
            candidatos: []
        }

        const todosPrenchidos = Object.values(novaEmpresa).every((valor, index) => {
            return valor.length > 0 || valor != 0 || index === 11
        })

        if (!todosPrenchidos) {
            alert('Todos os campos devem ser preenchidos')
            return
        }
        if(this.regexFormulario()) {
            this.empresaService.criarEmpresa(novaEmpresa)
            alert('Cadastro efetuado com sucesso')
            location.reload()
        }
    }

    private setupBotoes(): void {
        const botaoEmpresa = document.querySelector(".botao__cadastrar__empresa") as HTMLButtonElement
        const botaoVoltar = document.querySelector(".botao__voltar") as HTMLButtonElement

        botaoEmpresa.addEventListener("click", () => {
            this.salvaImformacoes()
        })

        botaoVoltar.addEventListener("click", () => {
            location.reload()
        })
    }

    private competenciasSelecionadas(competenciaInput: HTMLSelectElement): string[] {
        let selecionados: string[] = []
        for (let option of competenciaInput.options) {
            if(option.selected) {
                selecionados.push(option.value)
            }
        }
        return selecionados
    }

    private preecherCompetencias(): void {
        const opcoes = document.querySelector(".select__competencias") as HTMLSelectElement
        listaCompetencias.forEach(c => {
            opcoes.innerHTML += `<option value="${c}">${c}</option>` 
        })
    }

    private preecherHTML(): void {
        this.cabecalho.innerHTML = `
            <h1>Linketinder</h1>
            <button class="botao__voltar">Fazer Login</button>`
        this.conteudo.innerHTML = `
            <div class="cadastro">
                <h2>Cadastro de Empresa</h2>
                <div class="form__cadastro">
                    <div class="form__colunm">
                        <div class="input__nome cadastro__input">
                            <label for="nome">Nome</label>
                            <input type="text">
                        </div>
                        <div class="input__estado cadastro__input">
                            <label for="estado">Estado</label>
                            <input type="text" placeholder="UF" maxlength="2">
                        </div>

                        <div class="input__telefone cadastro__input">
                            <label for="telefone">Telefone</label>
                            <input type="text" name="telefone" placeholder="(99) 99999-9999"  maxlength="15" minlength="15">
                        </div>

                        <div class="input__email cadastro__input">
                            <label for="email">Email</label>
                            <input type="email" placeholder="email@gmail.com">
                        </div>

                        <div class="input__competencias cadastro__input">
                            <label for="competencias">Competências <span>(mantenha Ctrl precionado para selecionar mais de um)</span></label>
                            <select name="" id="" multiple class="select__competencias">

                            </select>
                        </div>
                    </div>
                    <div class="form__colunm">
                        <div class="input__idade cadastro__input">
                            <label for="nome">Pais</label>
                            <input type="text">
                        </div>

                        <div class="input__cep cadastro__input">
                            <label for="cep">CEP</label>
                            <input type="text" placeholder="12345-789"  maxlength="9" minlength="9">
                        </div>

                        <div class="input__cpf cadastro__input">
                            <label for="cpf">CNPJ</label>
                            <input type="text" placeholder="12.345.678/0001-90"  maxlength="18" minlength="18">
                        </div>

                        <div class="input__senha cadastro__input">
                            <label for="nome">Senha</label>
                            <input type="password">
                        </div>

                        <div class="input__descricao cadastro__input">
                            <label for="descricao">Descrição Pessoal</label>
                            <textarea></textarea>
                        </div>
                    </div>
                </div>
                <button class="botao__cadastrar__empresa">Cadastrar</button>
            </div>`
    }

    private regexFormulario(): boolean {
        const telefone = (document.querySelector(".input__telefone input") as HTMLInputElement).value;
        const cnpj = (document.querySelector(".input__cpf input") as HTMLInputElement).value;
        const cep = (document.querySelector(".input__cep input") as HTMLInputElement).value;
        const email = (document.querySelector(".input__email input") as HTMLInputElement).value;
    
        const telefoneRegex = /^\(\d{2}\) \d{4,5}-\d{4}$/
        const cnpjRegex = /^\d{2}\.\d{3}\.\d{3}\/\d{4}-\d{2}$/
        const cepRegex = /^\d{5}-\d{3}$/
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    
        if (!telefoneRegex.test(telefone)) {
            alert("Número de Telefone no formato errado (99) 99999-9999")
            return false;
        }
        if (!cnpjRegex.test(cnpj)) {
            alert("CNPJ no formato errado 123.456.789-01")
            return false;
        }
        if (!cepRegex.test(cep)) {
            alert("CEP no formato errado 12345-678")
            return false;
        }
        if (!emailRegex.test(email)) {
            alert("email no formato errado @gmail.com")
            return false;
        }
        return true;
    }

}