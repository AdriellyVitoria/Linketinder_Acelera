package modelos

class PessoaJuridica extends Pessoa {
    String cnpj
    String pais

    PessoaJuridica(String nome, String email, String estado, String cep, String descricao, List<String> competencias, String cnpj, String pais) {
        super(nome, email, estado, cep, descricao, competencias)
        this.cnpj = cnpj
        this.pais = pais
    }

    @Override
    String toString() {
        return "Nome: ${nome}, " +
                " Email: ${email}, " +
                " CNPJ: ${cnpj}, " +
                " Estado: ${estado}, " +
                " Pais: ${pais}, " +
                " CEP: ${cep}, " +
                " Descricao: ${descricao}\n"
    }
}
