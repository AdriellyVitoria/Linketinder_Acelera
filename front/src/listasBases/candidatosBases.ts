import { Candidato } from "../interfaces/candidato.js";

export const candidatoBase: Candidato[] = [
    {
        id: 0,
        nome: "Monkey D. Luffy",
        email: "Luffy@gmail.com",
        senha: "888",
        estado: "Vila Foosha",
        cep: "58457",
        descricao: "Sou um garoto relaxado, despreocupado e alegre",
        cpf: "12375398463",
        idade: 19,
        competencias: [
            "Java",
            "Angular",
            "Python",
            "C#",
            "Django",
            "JavaScript",
            "Rust",
            "PHP"
        ],
        aplicacoes_em_empresas: [{
            id: 0,
            match: true
        }],
        telefone: "1234-5678"
    },

    {
        id: 1,
        nome: "Nami",
        email: "nami@gmail.com",
        senha: "123",
        estado: "Vila Cocoyasi",
        cep: "11845",
        descricao: "Angular",
        cpf: "24578324899",
        idade: 20,
        competencias: [
            "Java",
            "Angular",
            "Python",
            "C#",
            "Django",
            "Rust",
            "Kotlin",
            "Swift"
        ],
        aplicacoes_em_empresas: [{
            id: 0,
            match: true
        }],
        telefone: "1234-5678"
    },

    {
        id: 2,
        nome: "Franky",
        email: "franky@gmail.com",
        senha: "010101",
        estado: "Water 7",
        cep: "50630",
        descricao: "JAVASCRIPT",
        cpf: "74027509833",
        idade: 37,
        competencias: [
            "Java",
            "Ruby",
            "JavaScript",
            "PostgreSQL",
            "TypeScript",
            "Swift",
            "Kotlin",
            "Scala"
        ],
        aplicacoes_em_empresas: [{
            id: 0,
            match: true
        }],
        telefone: "1234-5678"
    },

    {
        id: 3,
        nome: "Tony Tony Chopper",
        email: "chopper@gmail.com",
        senha: "5555",
        estado: "Ilha Drum",
        cep: "84366",
        descricao: "NODE.JS",
        cpf: "45687025487",
        idade: 0,
        competencias: [
            "C#",
            "Django",
            "Ruby",
            "GO",
            "Swift",
            "Kotlin",
            "Scala",
        ],
        aplicacoes_em_empresas: [{
            id: 0,
            match: false
        }],
        telefone: "1234-5678"
    }
]