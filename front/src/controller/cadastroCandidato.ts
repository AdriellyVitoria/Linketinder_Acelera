import { Candidato } from "../interfaces/candidato.js"
import { listaCompetencias } from "../listasBases/competencias.js"
import { CandidatoService } from "../service/candidato-service.js"
import { GeradorID } from "../service/gerador-id.js"

export class CadastroCandidato {
    private readonly geradorId: GeradorID
    private readonly candidatoService: CandidatoService

    constructor (
        private readonly cabecalho: HTMLElement,
        private readonly conteudo: HTMLElement
    ) {
        this.geradorId = new GeradorID()
        this.candidatoService = new CandidatoService()
    }
    
    public carregarTelaCadastro(): void {
        this.preecherHTML()
        this.preecherCompetencias()
        this.setupBotoes()
    }

    private salvaImformacoes(): void {
        const nome = document.querySelector(".input__nome input") as HTMLInputElement
        const idade = document.querySelector(".input__idade input") as HTMLInputElement
        const telefone = document.querySelector(".input__telefone input") as HTMLInputElement
        const cpf = document.querySelector(".input__cpf input") as HTMLInputElement
        const estado = document.querySelector(".input__estado input") as HTMLInputElement
        const cep = document.querySelector(".input__cep input") as HTMLInputElement
        const email = document.querySelector(".input__email input") as HTMLInputElement
        const senha = document.querySelector(".input__senha input") as HTMLInputElement
        const competencia = document.querySelector(".input__competencias select") as HTMLSelectElement
        const descricao = document.querySelector(".input__descricao textarea") as HTMLTextAreaElement
        
        const competenciasSelecionadas = this.competenciasSelecionadas(competencia)

        const novaCandidato: Candidato = {
            id: this.geradorId.gerarIdCandidato(),
            nome: nome.value,
            telefone: telefone.value,
            cpf: cpf.value,
            estado: estado.value,
            cep: cep.value,
            email: email.value,
            competencias: competenciasSelecionadas,
            descricao: descricao.value,
            idade: Number.parseInt(idade.value),
            senha: senha.value,
            aplicacoes_em_empresas: []
        }
        
        const todosPrenchidos = Object.values(novaCandidato).every((valor, index) => {
            return valor.length > 0 || valor != 0 || index === 11
        })

        if (!todosPrenchidos) {
            alert('Todos os campos devem ser preenchidos')
            return
        }
        if (this.regexFormulario()) {
            this.candidatoService.criarCandidato(novaCandidato)
            alert('Cadastro efetuado com sucesso')
            location.reload()
        }
    }

    private setupBotoes(): void {
        const botaoCadastro = document.querySelector(".botao__cadastrar__candidato") as HTMLButtonElement
        const botaoVoltar = document.querySelector(".botao__voltar") as HTMLButtonElement

        botaoCadastro.addEventListener("click", () => {
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
                <h2>Cadastro de Candidato</h2>
                <form class="form__cadastro">
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
                            <input type="text" name="telefone" maxlength="15" minlength="15" placeholder="(99) 99999-9999">
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
                            <label for="nome">Idade</label>
                            <input type="number" maxlength="2">
                        </div>

                        <div class="input__cep cadastro__input">
                            <label for="cep">CEP</label>
                            <input type="text" placeholder="12345-678" maxlength="10"  minlength="10">
                        </div>

                        <div class="input__cpf cadastro__input">
                            <label for="cpf">CPF</label>
                            <input type="text" placeholder="123.456.789-00" maxlength="14"  minlength="10"required>
                        </div>

                        <div class="input__senha cadastro__input">
                            <label for="nome">Senha</label>
                            <input type="password" maxlength="8">
                        </div>

                        <div class="input__descricao cadastro__input">
                            <label for="descricao">Descrição Pessoal</label>
                            <textarea></textarea>
                        </div>
                    </div>
                </form>
                <button class="botao__cadastrar__candidato">Cadastrar</button>
            </div>`
    }

    private regexFormulario(): boolean {
        const telefone = (document.querySelector(".input__telefone input") as HTMLInputElement).value;
        const cpf = (document.querySelector(".input__cpf input") as HTMLInputElement).value;
        const cep = (document.querySelector(".input__cep input") as HTMLInputElement).value;
        const email = (document.querySelector(".input__email input") as HTMLInputElement).value;
    
        const telefoneRegex = /^\(\d{2}\) \d{4,5}-\d{4}$/
        const cpfRegex = /^\d{3}\.\d{3}\.\d{3}-\d{2}$/
        const cepRegex = /^\d{5}-\d{3}$/
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    
        if (!telefoneRegex.test(telefone)) {
            alert("Número de Telefone no formato errado (99) 99999-9999")
            return false;
        }
        if (!cpfRegex.test(cpf)) {
            alert("CPF no formato errado 123.456.789-01")
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