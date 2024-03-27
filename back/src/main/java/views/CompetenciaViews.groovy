package views

import modelos.Competencia
import servicos.ServicoCandidatoCompetencia
import servicos.ServicoCompetencia
import servicos.ServicoLogin
import servicos.ServicoVagaCompetencia
import utils.InputValidation

class CompetenciaViews {
    private ServicoCompetencia servico
    private Competencia competencia
    private ServicoCandidatoCompetencia candidatoCompetencia
    private InputValidation input
    private opcao
    private Scanner scanner
    private ServicoCompetencia servicoCompetencia
    private ServicoVagaCompetencia vagaCompetencia

    CompetenciaViews() {
        servico = new ServicoCompetencia()
        competencia = new Competencia()
        candidatoCompetencia = new ServicoCandidatoCompetencia()
        input = new InputValidation()
        scanner = new Scanner(System.in)
        servicoCompetencia = new ServicoCompetencia()
        vagaCompetencia = new  ServicoVagaCompetencia()
    }

    void listarCompetenciaEmAtualizar() {
        inserirCompetenciaCandidato(ServicoLogin.candidato.getCpf())
    }

    void listarCompetencia() {
        println("Add suas competencias(Digite 1 id por vez):")
        def listar = servico.listarTodas()
        for (Competencia com : listar) {
            println("Id " + com.getId() + ":" + com.getDescricao())
        }
    }

    boolean inserirCompetenciaCandidato(String cpf){
        while (true){
            listarCompetencia()
            println("Digite 1 id por vez para add a competencia")
            Integer id_competencia = Integer.parseInt(scanner.nextLine())
            candidatoCompetencia.inserir(id_competencia, cpf)
            opcao = input.validaEntradaDeInteiro("1- Adicionar mais Competencia| " +
                    "2- Outra Competencia | 3- Concluido",
                    1, 2)
            if (opcao == 2) {
                inserirNovaCompetencia()
            } else if (opcao == 3){
                return true
                break
            }
        }
    }

    boolean inserirCompetenciaEmpresa(String cnpj) {
        while (true){
            listarCompetencia()
            println("Digite 1 id por vez para add a competencia")
            Integer id_competencia = Integer.parseInt(scanner.nextLine())
            vagaCompetencia.inserir(id_competencia, cnpj)
            opcao = input.validaEntradaDeInteiro("1- Adicionar mais Competencia| " +
                    "2- Outra Competencia | 3- Concluido",
                    1, 2)
            if (opcao == 2) {
                inserirNovaCompetencia()
            } else if (opcao == 3){
                return true
                break
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

    void deletar(){
        def listar = servicoCompetencia.listarTodasComCnpj()
        for (Competencia com : listar) {
            println("Id " + com.getId() + ":" + com.getDescricao())
        }
    }
}