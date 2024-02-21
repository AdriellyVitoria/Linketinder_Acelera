package listas

import modelos.PessoaFisica

class ListaCandidatos {
    def candidatos = [
            new PessoaFisica('Monkey D. Luffy',
                    'Luffy@gmail.com',
                    'Vila Foosha',
                    '58457',
                    'Sou um garoto relaxado, despreocupado e alegre',
                    ['Capitão', 'Yonkou'],
                    '12375398463',
                    19),
            new PessoaFisica('Roronoa Zoro',
                    'zoro@gmail.com',
                    'Shimotsuki',
                    '55245',
                    'JAVA',
                    ['competencias'],
                    '45732198652',
                    21),
            new PessoaFisica('Nami',
                    'nami@gmail.com',
                    'Vila Cocoyasi',
                    '11845',
                    'Angular',
                    ['competencias'],
                    '24578324899',
                    20),
            new PessoaFisica('Tony Tony Chopper',
                    'chopper@gmail.com',
                    'Ilha Drum',
                    '84366',
                    'NODE.JS',
                    ['competencias'],
                    '45687025487',
                    17),
            new PessoaFisica('Franky',
                    'franky@gmail.com',
                    'Water 7',
                    '50630',
                    'JAVASCRIPT',
                    ['competencias'],
                    '74027509833',
                    37),
    ]

    String exibirCandidatos() {
        String res = ""
        candidatos.each { candidato ->
            res += candidato.toString()
        }
        return res
    }
}
