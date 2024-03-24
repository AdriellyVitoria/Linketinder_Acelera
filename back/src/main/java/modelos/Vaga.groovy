package modelos

class Vaga {
    Integer id
    String descricao
    String titulo
    String local
    String cnpj_empresa

    Vaga() {}

    Vaga(Integer id,
         String descricao,
         String titulo,
         String local) {
        this.id = id
        this.descricao = descricao
        this.titulo = titulo
        this.local = local
    }
}
