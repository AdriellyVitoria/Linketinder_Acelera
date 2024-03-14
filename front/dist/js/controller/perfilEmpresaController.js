import { CandidatoService } from "../service/candidato-service.js";
import { EmpresaService } from "../service/empresa-service.js";
export class PerfilEmpresaController {
    cabecalho;
    conteudo;
    usuarioLogadoService;
    candidatoService;
    empresaService;
    empresa;
    candidatos;
    constructor(cabecalho, conteudo, usuarioLogadoService) {
        this.cabecalho = cabecalho;
        this.conteudo = conteudo;
        this.usuarioLogadoService = usuarioLogadoService;
        this.candidatoService = new CandidatoService();
        this.empresaService = new EmpresaService();
    }
    carregarTelaDePerfil() {
        this.empresa = this.usuarioLogadoService.usuario;
        this.montarEstruturaHtml();
        this.preencherPerfil();
        this.validaPreecherListas();
        this.criarGrafico();
        this.setupBotoes();
    }
    montarEstruturaHtml() {
        this.cabecalho.innerHTML = `
            <div>
                <h1>Linketinder</h1>
                <p>Olá, ${this.empresa.nome}</p>
            </div>
            <button class="botao__sair">Sair</button>`;
        this.conteudo.innerHTML = `
            <div class="contudo__perfil">
                <div class="perfil__usuario">
                    
                </div>
                <div class="lateral__empresa">
                    <div class="chart__div">

                    </div>
                    <div class="candidatos__com__match">

                    </div>
                    <div class="feed__do__usuario">
                    
                    </div>
                </div>
            </div>`;
    }
    validaPreecherListas() {
        this.buscarCandidatos();
        this.preecherMatch();
        this.preencherFeed();
    }
    preecherMatch() {
        const candidatosComMatch = document.querySelector(".candidatos__com__match");
        if (this.filtrarCandidatosPorMatch(true).length > 0) {
            candidatosComMatch.innerHTML = `
                <h2>Candidatos Com Match</h2>
                <div class="lista__candidatos__match">
                
                </div>`;
        }
        const listaCandidatos = document.querySelector(".lista__candidatos__match");
        this.filtrarCandidatosPorMatch(true).forEach(c => {
            listaCandidatos.innerHTML += `
            <div class="candidato__match">
                <p><span>Nome:</span> ${c.nome}</p>
                <p><span>Email:</span> ${c.email}</p>
                <p><span>Estado:</span> ${c.estado}</p>
                <p><span>Competências:</span> ${c.competencias.join(', ')}</p>
                <p><span>Descrição:</span> ${c.descricao}</p>
            </div>`;
        });
    }
    buscarCandidatos() {
        this.candidatos = this.candidatoService.buscaCandidatos(this.empresa.candidatos.map(c => c.id));
    }
    preencherPerfil() {
        const perfilUsuario = document.querySelector(".perfil__usuario");
        perfilUsuario.innerHTML = `
            <p><span>Nome:</span> ${this.empresa.nome}</p>
            <p><span>Pais:</span> ${this.empresa.pais}</p>
            <p><span>Email:</span> ${this.empresa.email}</p>
            <p><span>Estado:</span> ${this.empresa.estado}</p>
            <p><span>Cep:</span> ${this.empresa.cep}</p>
            <p><span>CNPJ:</span> ${this.empresa.cnpj}</p>
            <p><span>Competências:</span> ${this.empresa.competencias.join(', ')}</p>
            <p><span>Descrição:</span> ${this.empresa.descricao}</p>`;
    }
    preencherFeed() {
        const feedCandidato = document.querySelector(".feed__do__usuario");
        if (this.filtrarCandidatosPorMatch(false).length > 0) {
            feedCandidato.innerHTML = '<h2>Candidatos</h2>';
        }
        this.filtrarCandidatosPorMatch(false).forEach(c => {
            feedCandidato.innerHTML += `
                <div class="candidato__no__feed" id="${c.id}">
                    <div>
                        <p><span>Competências:</span> ${c.competencias.join(', ')}</p>
                        <p><span>Descrição:</span> ${c.descricao}</p>
                    </div>
                    <button class="botao__match">Match</button>
                </div>`;
        });
    }
    filtrarCandidatosPorMatch(match) {
        return this.candidatos.filter(c => {
            return c.aplicacoes_em_empresas.find(a => {
                return a.id == this.empresa.id;
            }).match == match;
        });
    }
    setupBotoes() {
        const botaoSair = document.querySelector('.botao__sair');
        botaoSair.addEventListener("click", () => {
            location.reload();
        });
        const botoesMatch = document.querySelectorAll('.botao__match');
        botoesMatch.forEach((botao) => {
            botao.addEventListener("click", () => {
                this.fazerMatch(botao);
            });
        });
    }
    fazerMatch(botao) {
        const candidatoId = Number.parseInt(botao.parentElement.id);
        const candidato = this.candidatos.find(c => c.id == candidatoId);
        const aplicacaoCandidato = candidato.aplicacoes_em_empresas.find(aplicao => aplicao.id == this.empresa.id);
        aplicacaoCandidato.match = true;
        this.candidatoService.editarCandidato(candidato);
        const aplicacaoEmpresa = this.empresa.candidatos.find(c => c.id == candidatoId);
        aplicacaoEmpresa.match = true;
        this.empresaService.editarEmpresa(this.empresa);
        this.carregarTelaDePerfil();
    }
    criarGrafico() {
        const linhaCandidatos = this.criarlinhaCandidatos();
        google.charts.load('current', { 'packages': ['corechart'] });
        google.charts.setOnLoadCallback(drawChart);
        function drawChart() {
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Topping');
            data.addColumn('number', 'Candidatos com esta competência');
            data.addRows(linhaCandidatos);
            var options = { 'title': 'Afinidade dos candidatos',
                'width': 800,
                'height': 600 };
            var chart = new google.visualization.BarChart(document.querySelector('.chart__div'));
            chart.draw(data, options);
        }
    }
    criarlinhaCandidatos() {
        return this.empresa.competencias.map(c => {
            let possuirCompetencias = 0;
            this.candidatos.forEach(candidato => {
                if (candidato.competencias.includes(c)) {
                    possuirCompetencias++;
                }
            });
            return [c, possuirCompetencias];
        });
    }
}
