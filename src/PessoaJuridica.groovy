class PessoaJuridica extends Pessoa{
    int cnpj

    PessoaJuridica(String nome, String email, String estado, long cep,String descricao, int cnpj) {
        super(nome, email, estado, cep, descricao)
        this.cnpj = cnpj
    }
}
