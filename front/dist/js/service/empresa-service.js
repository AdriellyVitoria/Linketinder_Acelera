import { empresaBase } from "../listasBases/empresasBases.js";
export class EmpresaService {
    empresas;
    constructor() {
        this.empresas = this.buscarListaNoLocalStorage();
        if (!this.empresas) {
            this.criarListaNoLocalStorage();
            this.empresas = this.buscarListaNoLocalStorage();
        }
    }
    buscarListaNoLocalStorage() {
        return JSON.parse(localStorage.getItem('empresas'))?.empresas;
    }
    criarListaNoLocalStorage() {
        localStorage.setItem('empresas', JSON.stringify({ empresas: empresaBase }));
    }
    atualizaListaNoLocalStorage() {
        localStorage.setItem('empresas', JSON.stringify({ empresas: this.empresas }));
    }
    salvarMudancas() {
        this.atualizaListaNoLocalStorage();
        this.empresas = this.buscarListaNoLocalStorage();
    }
    criarEmpresa(empresa) {
        this.empresas.push(empresa);
        this.salvarMudancas();
    }
    buscarPorId(id) {
        return this.empresas.find(e => e.id == id);
    }
    validarLogin(email, senha) {
        const empresa = this.empresas.find(e => e.email === email);
        if (empresa?.senha === senha)
            return empresa;
    }
    buscaEmpresas(ids = null) {
        if (ids) {
            return this.empresas.filter(c => {
                return ids.includes(c.id);
            });
        }
        return this.empresas;
    }
    editarEmpresa(empresa) {
        this.excluirEmpresa(empresa.id);
        this.criarEmpresa(empresa);
    }
    excluirEmpresa(id) {
        const empresa = this.buscarPorId(id);
        const index = this.empresas.indexOf(empresa);
        this.empresas.splice(index, 1);
        this.salvarMudancas();
    }
}
