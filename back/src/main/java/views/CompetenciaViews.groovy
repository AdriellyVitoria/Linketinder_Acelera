package views

import modelos.Competencia
import servicos.ServicoCandidatoCompetencia
import servicos.ServicoCompetencia
import servicos.ServicoLogin
import utils.InputValidation

class CompetenciaViews {
    private ServicoCompetencia servico
    private Competencia competencia
    private ServicoCandidatoCompetencia candidatoCompetencia
    private InputValidation input
    private opcao
    private Scanner scanner
    private ServicoCompetencia servicoCompetencia

    CompetenciaViews() {
        servico = new ServicoCompetencia()
        competencia = new Competencia()
        candidatoCompetencia = new ServicoCandidatoCompetencia()
        input = new InputValidation()
        scanner = new Scanner(System.in)
        servicoCompetencia = new ServicoCompetencia()
    }

    boolean listarCompetencia() {
        listarCompetencia(ServicoLogin.candidato.getCpf())
    }

    boolean listarCompetencia(String cpf) {
        while (true){
            println("Add suas competencias(Digite 1 id por vez):")
            def listar = servico.listarTodas()
            for (Competencia com : listar) {
                println("Id " + com.getId() + ":" + com.getDescricao())
            }
            println("Digite 1 id por vez para add a competencia")
            Integer id_compentecia = Integer.parseInt(scanner.nextLine())
            candidatoCompetencia.inserir(id_compentecia, cpf)
            opcao = input.validaEntradaDeInteiro("1- Adicionar mais Competencia| " +
                    "2- Outra Competencia | 3- Concluido",
                    1, 2)
            if (opcao == 2) {
                inserirNovaCompetencia()
            } else{
                return true
            }
        }
    }

    void inserirNovaCompetencia() {
        println("Digite sua nova Compencia:")
        String competenciaNova = scanner.nextLine()
        boolean verificacaoDeSalvamento = servicoCompetencia.inserir(competenciaNova)
        if (verificacaoDeSalvamento) {
            println("Competencia nova add")
        }
    }
}