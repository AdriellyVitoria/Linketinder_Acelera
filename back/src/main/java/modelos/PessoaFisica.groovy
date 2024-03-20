package modelos

class PessoaFisica extends Pessoa {
    String cpf
    int idade

    PessoaFisica() {}

    PessoaFisica(String nome, String email, String senha,
                 String telefone,String estado, String cep,
                 String descricao, String cpf, int idade) {
        super(nome, email, senha, telefone, estado, cep, descricao)
        this.cpf = cpf
        this.idade = idade
    }

    @Override
    String toString() {
        return " Nome: ${nome}," +
                " Email: ${email}," +
                " CPF: ${cpf}," +
                " Idade: ${idade}," +
                " Estado: ${estado}," +
                " CEP: ${cep}," +
                " Descrição: ${descricao},"

    }
}
