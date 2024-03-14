import { Aplicado } from "./aplicado.js"

export interface Empresa {
    id: number
    nome: string
    telefone: string
    email: string
    senha: string
    estado: string
    cep: string
    descricao: string
    cnpj: string
    pais: string
    competencias: string[]
    candidatos: Aplicado[]
}