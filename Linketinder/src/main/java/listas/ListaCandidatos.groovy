package listas

class ListaCandidatos {
    def candidatos = [
            [nome: 'Monkey D. Luffy',
            email: 'Luffy@gmail.com',
            estado: 'Vila Foosha',
            cep: 58457,
            descricao: 'Sou um garoto relaxado, despreocupado e alegre',
            competencias: ['competencias'],
            cpf: 12375398463,
            idade: 19],

            [nome: 'Roronoa Zoro',
             email: 'zoro@gmail.com',
             cpf: 45732198652,
             idade: 21,
             estado: 'Shimotsuki',
             cep: 55245,
             descricao: 'JAVA',
             competencias: "competencias"],

            [nome: 'Nami',
             email: 'nami@gmail.com',
             cpf: 24578324899,
             idade: 20,
             estado: 'Vila Cocoyasi',
             cep: 11845,
             descricao: 'Angular',
             competencias: "competencias"],

            [nome: 'Tony Tony Chopper',
             email: 'chopper@gmail.com',
             cpf: 45687025487,
             idade: 17,
             estado: 'Ilha Drum',
             cep: 84366,
             descricao: 'NODE.JS',
             competencias: "competencias"],

            [nome: 'Franky',
             email: 'franky@gmail.com',
             cpf: 74027509833,
             idade: 36,
             estado: 'South Blue',
             cep: 50630,
             descricao: 'JAVASCRIPT',
             competencias: "competencias"],
    ]

    String exibirCandidatos() {
        String res = ""
        candidatos.each { candidato ->
            res += "Nome: ${candidato.nome}," +
                    " email: ${candidato.email}," +
                    " CPF: ${candidato.cpf}," +
                    " Idade: ${candidato.idade}," +
                    " Estado: ${candidato.estado}," +
                    " CEP: ${candidato.cep}," +
                    " Descrição: ${candidato.descricao}" +
                    " Competencias: ${candidato.competencias}\n"
        }
        return res
    }
}
