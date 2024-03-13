import { candidatoBase } from "../listasBases/candidatosBases.js";
export class CandidatoService {
    candidatos;
    constructor() {
        this.candidatos = this.buscarListaNoLocalStorage();
        if (!this.candidatos) {
            this.criarListaNoLocalStorage();
            this.candidatos = this.buscarListaNoLocalStorage();
        }
    }
    buscarListaNoLocalStorage() {
        return JSON.parse(localStorage.getItem('candidatos'))?.candidatos;
    }
    criarListaNoLocalStorage() {
        localStorage.setItem('candidatos', JSON.stringify({ candidatos: candidatoBase }));
    }
    atualizaListaNoLocalStorage() {
        localStorage.setItem('candidatos', JSON.stringify({ candidatos: this.candidatos }));
    }
    salvarMudancas() {
        this.atualizaListaNoLocalStorage();
        this.candidatos = this.buscarListaNoLocalStorage();
    }
    criarCandidato(candidato) {
        this.candidatos.push(candidato);
        this.salvarMudancas();
    }
    buscarPorId(id) {
        return this.candidatos.find(c => c.id == id);
    }
    validarLogin(email, senha) {
        const candidato = this.candidatos.find(c => c.email === email);
        if (candidato?.senha === senha)
            return candidato;
    }
    buscaCandidatos(ids = null) {
        if (ids) {
            return this.candidatos.filter(c => {
                return ids.includes(c.id);
            });
        }
        return this.candidatos;
    }
    editarCandidato(candidato) {
        this.excluirCandidato(candidato.id);
        this.criarCandidato(candidato);
    }
    excluirCandidato(id) {
        const candidato = this.buscarPorId(id);
        const index = this.candidatos.indexOf(candidato);
        this.candidatos.splice(index, 1);
        this.salvarMudancas();
    }
}
