class PessoaJuridica extends Pessoa{
    int cnpj
    String pais

    PessoaJuridica(String nome, String email, String estado, long cep,String descricao, List<String> competencias, int cnpj, String pais) {
        super(nome, email, estado, cep, descricao, competencias)
        this.cnpj = cnpj
        this.pais = pais
    }
}
