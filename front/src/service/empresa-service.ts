import { Empresa } from "../interfaces/empresa.js";
import { empresaBase } from "../listasBases/empresasBases.js";

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
        return JSON.parse(localStorage.getItem('empresas'))?.empresas
    }
    
    private criarListaNoLocalStorage(): void {
        localStorage.setItem('empresas', JSON.stringify({empresas: empresaBase}))
    }

    private atualizaListaNoLocalStorage(): void {
        localStorage.setItem('empresas', JSON.stringify({empresas: this.empresas}))
    }

    private salvarMudancas(): void {
        this.atualizaListaNoLocalStorage()
        this.empresas = this.buscarListaNoLocalStorage()
    }

    public criarEmpresa(empresa: Empresa): void {
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
                return ids.includes(c.id)
            })
        }
        return this.empresas
    }

    public editarEmpresa(empresa: Empresa): void {
        this.excluirEmpresa(empresa.id)
        this.criarEmpresa(empresa)
    }

    public excluirEmpresa(id: number): void {
        const empresa = this.buscarPorId(id)
        const index = this.empresas.indexOf(empresa)
        this.empresas.splice(index, 1)
        this.salvarMudancas()
    } 
} 