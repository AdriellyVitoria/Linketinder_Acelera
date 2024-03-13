import { listaCompetencias } from "../listasBases/competencias.js";
import { CandidatoService } from "../service/candidato-service.js";
import { GeradorID } from "../service/gerador-id.js";
export class CadastroCandidato {
    cabecalho;
    conteudo;
    geradorId;
    candidatoService;
    constructor(cabecalho, conteudo) {
        this.cabecalho = cabecalho;
        this.conteudo = conteudo;
        this.geradorId = new GeradorID();
        this.candidatoService = new CandidatoService();
    }
    carregarTelaCadastro() {
        this.preecherHTML();
        this.preecherCompetencias();
        this.setupBotoes();
    }
    salvaImformacoes() {
        const nome = document.querySelector(".input__nome input");
        const idade = document.querySelector(".input__idade input");
        const telefone = document.querySelector(".input__telefone input");
        const cpf = document.querySelector(".input__cpf input");
        const estado = document.querySelector(".input__estado input");
        const cep = document.querySelector(".input__cep input");
        const email = document.querySelector(".input__email input");
        const senha = document.querySelector(".input__senha input");
        const competencia = document.querySelector(".input__competencias select");
        const descricao = document.querySelector(".input__descricao textarea");
        const competenciasSelecionadas = this.competenciasSelecionadas(competencia);
        const novaCandidato = {
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
        };
        const todosPrenchidos = Object.values(novaCandidato).every((valor, index) => {
            return valor.length > 0 || valor != 0 || index === 11;
        });
        if (!todosPrenchidos) {
            alert('Todos os campos devem ser preenchidos');
            return;
        }
        this.candidatoService.criarCandidato(novaCandidato);
        alert('Cadastro efetuado com sucesso');
        location.reload();
    }
    setupBotoes() {
        const botaoCadastro = document.querySelector(".botao__cadastrar__candidato");
        const botaoVoltar = document.querySelector(".botao__voltar");
        botaoCadastro.addEventListener("click", () => {
            this.salvaImformacoes();
        });
        botaoVoltar.addEventListener("click", () => {
            location.reload();
        });
    }
    competenciasSelecionadas(competenciaInput) {
        let selecionados = [];
        for (let option of competenciaInput.options) {
            if (option.selected) {
                selecionados.push(option.value);
            }
        }
        return selecionados;
    }
    preecherCompetencias() {
        const opcoes = document.querySelector(".select__competencias");
        listaCompetencias.forEach(c => {
            opcoes.innerHTML += `<option value="${c}">${c}</option>`;
        });
    }
    preecherHTML() {
        this.cabecalho.innerHTML = `
            <h1>Linketinder</h1>
            <button class="botao__voltar">Fazer Login</button>`;
        this.conteudo.innerHTML = `
            <div class="cadastro">
                <h2>Cadastro de Candidato</h2>
                <div class="form__cadastro">
                    <div class="form__colunm">
                        <div class="input__nome cadastro__input">
                            <label for="nome">Nome</label>
                            <input type="text">
                        </div>

                        <div class="input__estado cadastro__input">
                            <label for="estado">Estado</label>
                            <input type="text" placeholder="UF">
                        </div>
        
                        <div class="input__telefone cadastro__input">
                            <label for="telefone">Telefone</label>
                            <input type="text" name="telefone">
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
                            <input type="number">
                        </div>

                        <div class="input__cep cadastro__input">
                            <label for="cep">CEP</label>
                            <input type="text" placeholder="123.456-89">
                        </div>

                        <div class="input__cpf cadastro__input">
                            <label for="cpf">CPF</label>
                            <input type="text" placeholder="123.456.789-00">
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
                <button class="botao__cadastrar__candidato">Cadastrar</button>
            </div>`;
    }
}
