package modelos

class PessoaFisica extends Pessoa {
    String cpf
    int idade

    PessoaFisica(String nome, String email, String estado, String cep, String descricao, List<String> competencias, String cpf, int idade) {
        super(nome, email, estado, cep, descricao, competencias)
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
                " Descrição: ${descricao}," +
                " Competencias: ${competencias}\n"
    }
}
