class PessoaFisica extends Pessoa {
    long cpf
    int idade

    PessoaFisica(String nome, String email, String estado, long cep, String descricao, List<String> competencias, long cpf, int idade) {
        super(nome, email, estado, cep, descricao, competencias)
        this.cpf = cpf
        this.idade = idade
    }
}
