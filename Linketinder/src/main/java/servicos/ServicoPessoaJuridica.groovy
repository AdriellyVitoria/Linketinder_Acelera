package servicos

import database.Listas
import modelos.PessoaJuridica

class ServicoPessoaJuridica {
    private List<PessoaJuridica> empresas

    ServicoPessoaJuridica() {
        this.empresas = Listas.empresas
    }

    String listarEmpresas(){
        String res = ""
        empresas.each { empresa ->
            res += empresa.toString()
        }
        return res
    }
}
