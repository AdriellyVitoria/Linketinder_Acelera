import { Cantidato } from "../interfaces/candidato.js";

export class CantidatoService{
    private candidatos: Cantidato[]

    constructor() {
        this.candidatos = this.buscarListaNoLocalStorage()
        if (!this.candidatos) {
            this.criarListaNoLocalStorage()
            this.candidatos = this.buscarListaNoLocalStorage()
        }
    }

    private buscarListaNoLocalStorage(): Cantidato[] {
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

    public criarCandidato(candidato: Cantidato): void {
        this.candidatos.push(candidato)
        this.salvarMudancas()
    }

    public buscarPorId(id: number): Cantidato {
        return this.candidatos.find(c => c.id == id)
    }

    public buscaCandidatos(ids: number[] | null = null): Cantidato[] {
        if (ids) {
            return this.candidatos.filter(c => {
                ids.includes(c.id)
            })
        }
        return this.candidatos
    }

    public editarCandidato(candidato: Cantidato): Cantidato {
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
