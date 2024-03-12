import { Candidato } from "../interfaces/candidato.js";

export class CandidatoService{
    private candidatos: Candidato[]

    constructor() {
        this.candidatos = this.buscarListaNoLocalStorage()
        if (!this.candidatos) {
            this.criarListaNoLocalStorage()
            this.candidatos = this.buscarListaNoLocalStorage()
        }
    }

    private buscarListaNoLocalStorage(): Candidato[] {
        return JSON.parse(localStorage.getItem('candidatos'))?.candidatos
    }
    
    private criarListaNoLocalStorage(): void {
        localStorage.setItem('candidatos', JSON.stringify({candidatos: []}))
    }

    private atualizaListaNoLocalStorage(): void {
        localStorage.setItem('candidatos', JSON.stringify({candidatos: this.candidatos}))
    }

    private salvarMudancas(): void {
        this.atualizaListaNoLocalStorage()
        this.candidatos = this.buscarListaNoLocalStorage()
    }

    public criarCandidato(candidato: Candidato): void {
        this.candidatos.push(candidato)
        this.salvarMudancas()
    }

    public buscarPorId(id: number): Candidato {
        return this.candidatos.find(c => c.id == id)
    }

    public validarLogin(email: string, senha: string): Candidato | null {
        const candidato = this.candidatos.find(c => c.email === email)
        if (candidato?.senha === senha) return candidato
    }

    public buscaCandidatos(ids: number[] | null = null): Candidato[] {
        if (ids) {
            return this.candidatos.filter(c => {
                ids.includes(c.id)
            })
        }
        return this.candidatos
    }

    public editarCandidato(candidato: Candidato): Candidato {
        this.excluirCandidato(candidato.id)
        this.criarCandidato(candidato)
        return candidato
    }

    public excluirCandidato(id: number): void {
        const candidato = this.buscarPorId(id)
        const index = this.candidatos.indexOf(candidato)
        this.candidatos.splice(index, 1)
        this.salvarMudancas()
    } 
}
