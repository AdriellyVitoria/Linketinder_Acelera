package servicos

import modelos.PessoaFisica
import modelos.PessoaJuridica

class ServicoLogin {
    static PessoaJuridica empresa
    static PessoaFisica candidato

    static logout() {
        empresa = null
        candidato = null
    }
}
