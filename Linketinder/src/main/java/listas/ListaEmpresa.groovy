package listas

class ListaEmpresa {
    def empresas =[
            [nome:'Companhia Galley-La',
             emailCorp: 'galleyla@gmail.com',
             cnpj: 12345678000190,
             estado: 'Water 7',
             pais: "Brasil",
             cep: 50000,
             descricao: 'Descrição da vaga',
             Competencias: "construcao de código, trabalho em equipe"],

            [nome:'Empresa Revestimento De Rayleigh',
             emailCorp: 'r@yleigh@gmail.com',
             cnpj: 98765432000110,
             estado: 'Sabaody',
             pais: "Brasil",
             cep: 11845,
             descricao: 'Descrição da vaga',
             Competencias: "revestir com uma boa segurança a aplicação"],

            [nome:'Baratie',
             emailCorp: 'restaurentebaratie@gmail.com',
             cnpj: 11122333444455,
             estado: 'regiao sambas no east blue',
             pais: "Brasil",
             cep: 55245,
             descricao: 'Descrição da vaga',
             Competencias: "saber brigar e cozinhar"],

            [nome:'Marinha',
             emailCorp: 'marinha.com',
             cnpj: 17475368999855,
             estado: 'Grand Line',
             pais: "Brasil",
             cep: 84366,
             descricao: 'Descrição da vaga',
             Competencias: "forte"
            ],

            [nome:'Donquixote Doflamingo',
             emailCorp: 'doflamingo@gmail.com',
             cnpj: 7452389688822,
             pais: "Brasil",
             estado: 'Dressrosa',
             cep: 50630,
             descricao: 'Descrição da vaga',
             Competencias: "vender bem"]
    ]

    String exibirEmpresas(){
        String res = ""
        empresas.each { empresa ->
            res += "Nome: ${empresa.nome} " +
                    " Email: ${empresa.emailCorp} " +
                    " CNPJ: ${empresa.cnpj} " +
                    " Estado: ${empresa.estado} " +
                    " Pais: ${empresa.pais} " +
                    " CEP: ${empresa.cep} " +
                    " Descricao: ${empresa.descricao}\n"
        }
        return res
    }
}
