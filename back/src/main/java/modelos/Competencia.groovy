package modelos

class Competencia {
    Integer id
    String descricao

    Competencia(Integer id, String descricao) {
        this.id = id
        this.descricao = descricao
    }

    Competencia() {

    }

    @Override
    String toString(){
        return "\nId ${id} ${descricao}"
    }
}
