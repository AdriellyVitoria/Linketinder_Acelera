package servicos

import database.Listas
import modelos.PessoaFisica

class ServicoPessoaFisica {
    private List<PessoaFisica> candidatos

    ServicoPessoaFisica(){
        this.candidatos = Listas.candidatos
    }

    String listarCandidatos() {
        String res = ""
        candidatos.each { candidato ->
            res += candidato.toString()
        }
        return res
    }

    void addCandidato(PessoaFisica candidato){
        candidatos.add(candidato)
    }

    List<PessoaFisica> obterCandidatos(){
        return candidatos
    }
}
