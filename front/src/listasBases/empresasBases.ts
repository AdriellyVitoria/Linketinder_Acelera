import { Empresa } from "../interfaces/empresa.js";

export const empresaBase: Empresa[] = [
    {
        id: 0,
        nome: "Companhia Galley-la",
        email: "galleyla@gmail.com",
        senha: "123",
        estado: "Water 7",
        cep: "12356",
        descricao: "descricao",
        cnpj: "12345678000190",
        pais: "Brasil",
        competencias: [
            "JavaScript",
            "PostgreSQL",
            "TypeScript",
            "Rust",
            "Swift",
            "Kotlin",
            "Scala",
            "PHP"
        ],
        candidatos: []
    },

    {
        id: 1,
        nome: "Empresa Revestimento De Rayleigh",
        email: "r@yleigh@gmail.com",
        senha: "1990",
        estado: "Sabaody",
        cep: "54586",
        descricao: "descricao",
        cnpj: "98765432000110",
        pais: "Brasil",
        competencias: [
            "Angula",
            "Python",
            "C#",
            "Django",
            "Ruby",
            "GO",
            "JavaScript",
            "PostgreSQL"
        ],
        candidatos: []
    },

    {
        id: 2,
        nome: "Baratie",
        email: "estaurentebaratie@gmail.com",
        senha: "6565",
        estado: "Região sambas no east blue",
        cep: "55245",
        descricao: "descricao",
        cnpj: "11122333444455",
        pais: "Brasil",
        competencias: [ 
            "Ruby",
            "GO",
            "JavaScript",
            "PostgreSQL",
            "TypeScript",
            "Rust",
            "Swift"
        ],
        candidatos: []
    },

    {
        id: 3,
        nome: "Donquixote Doflamingo",
        email: "doflamingo@gmail.com",
        senha: "777",
        estado: "Dressrosa",
        cep: "56823",
        descricao: "descricao",
        cnpj: "7452389688822",
        pais: "Brasil",
        competencias: [ 
            "Java",
            "Angula",
            "Python",
            "C#",
            "Django",
            "Ruby",
            "GO",
            "JavaScript",
            "PostgreSQL"
        ],
        candidatos: []
    }
]