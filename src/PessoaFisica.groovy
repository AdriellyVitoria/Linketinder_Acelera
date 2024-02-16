class PessoaFisica extends Pessoa {
    long cpf
    int idade

    PessoaFisica(String nome, String email, String estado, long cep, String descricao, long cpf, int idade) {
        super(nome, email, estado, cep, descricao)
        this.cpf = cpf
        this.idade = idade
    }
}
