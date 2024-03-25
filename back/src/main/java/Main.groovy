import modelos.Competencia
import servicos.ServicoCompetencia

class Main {
    static void main(String[] args) {

        def servico = new ServicoCompetencia()
        servico.inserir("lua")
       def listar = servico.listarTodas()
        for (Competencia com : listar){
            println("Id " + com.getId() + ":" + com.getDescricao())

        }
    }
}
