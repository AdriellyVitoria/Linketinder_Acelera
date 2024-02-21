package listas

import modelos.PessoaJuridica

class ListaEmpresa {
    def empresas =[
            new PessoaJuridica('Companhia Galley-La',
            'galleyla@gmail.com',
            'Water 7',
            '12356',
            'descricao',
            ['construcao de código', 'trabalho em equipe'],
            '12345678000190',
            'Brasil'),

            new PessoaJuridica('Empresa Revestimento De Rayleigh',
            'r@yleigh@gmail.com',
            'Sabaody',
            '54586',
            'descricao',
            ['revestir com uma boa segurança'],
            '98765432000110',
            'Brasil'),

            new PessoaJuridica('Baratie',
            'restaurentebaratie@gmail.com',
            'Região sambas no east blue',
            '55245',
            'descricao',
            ['saber brigar', 'cozinhar'],
            '11122333444455',
            'Brasil'
            ),

            new PessoaJuridica('Marinha',
            'marinha.com',
            'Grand Line',
            '45455',
            'descricao',
            ['forte'],
            '17475368999855',
            'Brasil'),

            new PessoaJuridica('Donquixote Doflamingo',
            'doflamingo@gmail.com',
            'Dressrosa',
            '56823',
            'Descricao',
            ['Vender bem'],
            '7452389688822',
            'Brasil')
    ]

    String exibirEmpresas(){
        String res = ""
        empresas.each { empresa ->
            res += empresa.toString()
        }
        return res
    }
}
