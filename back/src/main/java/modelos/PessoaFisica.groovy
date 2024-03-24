package modelos

class PessoaFisica extends Pessoa {
    String cpf
    int idade
    ArrayList<Competencia> competencias

    PessoaFisica() {}
    
    // constr para imprimir
    PessoaFisica(String cpf, String nome, String email,
                 String telefone, String cep, String descricao){
        this.cpf = cpf
        this.nome = nome
        this.email = email
        this.telefone = telefone
        this.cep = cep
        this.descricao = descricao
    }
    
    // constr para criação
    PessoaFisica(String nome,
                 String email,
                 String senha,
                 String telefone,
                 String estado,
                 String cep,
                 String descricao,
                 String cpf,
                 int idade) {
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
