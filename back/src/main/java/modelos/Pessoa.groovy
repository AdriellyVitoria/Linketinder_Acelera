package modelos

abstract class Pessoa {
    String nome
    String email
    String senha
    String telefone
    String cep
    String estado
    String descricao

    Pessoa() {}

    Pessoa(String nome, String email, String senha, String telefone, String estado, String cep, String descricao) {
        this.nome = nome
        this.email = email
        this.senha = senha
        this.telefone = telefone
        this.estado = estado
        this.cep = cep
        this.descricao = descricao
    }

    abstract String toString()
}
