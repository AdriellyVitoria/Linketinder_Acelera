import { Empresa } from "../interfaces/empresa.js";

export class EmpresaService {
    private empresas: Empresa[]

    constructor() {
        this.empresas = this.buscarListaNoLocalStorage()
        if(!this.empresas) {
            this.criarListaNoLocalStorage()
            this.empresas = this.buscarListaNoLocalStorage()
        }
    }

    private buscarListaNoLocalStorage(): Empresa[] {
        return JSON.parse(localStorage.getItem('empresa'))?.empresas
    }
    
    private criarListaNoLocalStorage(): void {
        localStorage.setItem('empresas', JSON.stringify({empresas: []}))
    }

    private atualizaListaNoLocalStorage(): void {
        localStorage.setItem('empresas', JSON.stringify({empresas: this.empresas}))
    }

    private salvarMudancas(): void {
        this.atualizaListaNoLocalStorage()
        this.empresas = this.buscarListaNoLocalStorage()
    }

    public criarCandidato(empresa: Empresa): void {
        this.empresas.push(empresa)
        this.salvarMudancas()
    }

    public buscarPorId(id: number): Empresa {
        return this.empresas.find(e => e.id == id)
    }

    public validarLogin(email: string, senha: string): Empresa | null {
        const empresa = this.empresas.find(e => e.email === email)
        if(empresa?.senha === senha) return empresa
    }

    public buscaEmpresas(ids: number[] | null = null): Empresa[] {
        if (ids) {
            return this.empresas.filter(c => {
                ids.includes(c.id)
            })
        }
        return this.empresas
    }

    public editarCandidato(empresa: Empresa): Empresa {
        this.excluirCandidato(empresa.id)
        this.criarCandidato(empresa)
        return empresa
    }

    public excluirCandidato(id: number): void {
        const empresa = this.buscarPorId(id)
        const index = this.empresas.indexOf(empresa)
        this.empresas.splice(index, 1)
        this.salvarMudancas()
    } 
} 