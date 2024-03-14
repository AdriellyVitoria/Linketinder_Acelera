import { Aplicado } from "./aplicado.js"

export interface Candidato {
    id: number
    nome: string
    telefone: string
    email: string
    senha: string
    estado: string
    cep: string
    descricao: string
    cpf: string
    idade: number
    competencias: string[]
    aplicacoes_em_empresas: Aplicado[]
}
