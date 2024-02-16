class Pessoa {
    String nome
    String email
    String estado
    long cep
    String descricao

    Pessoa(String nome, String email, String estado, long cep, String descricao) {
        this.nome = nome
        this.email = email
        this.estado = estado
        this.cep = cep
        this.descricao = descricao
    }

    String toString() {
        "Nome: ${this.nome}, Email: ${this.email}, Estado: ${this.estado}, CEP: ${this.cep}, Descrição: ${this.descricao}"
    }
}
