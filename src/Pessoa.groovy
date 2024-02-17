class Pessoa {
    String nome
    String email
    String estado
    long cep
    String descricao
    List<String> competencias =[]

    Pessoa(String nome, String email, String estado, long cep, String descricao, List<String> competencias) {
        this.nome = nome
        this.email = email
        this.estado = estado
        this.cep = cep
        this.descricao = descricao
        this.competencias = competencias
    }

    String toString() {
        " Nome: ${this.nome}," +
        " Email: ${this.email}," +
        " Estado: ${this.estado}," +
        " CEP: ${this.cep}," +
        " Descrição: ${this.descricao}"
        " Competencias: ${competencias}"
    }
}
