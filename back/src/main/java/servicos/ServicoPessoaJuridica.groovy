package servicos


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

    void addEmpresa(PessoaJuridica empresa){
        empresas.add(empresa)
    }

    List<PessoaJuridica> obterEmpresas(){
        return empresas
    }
}
