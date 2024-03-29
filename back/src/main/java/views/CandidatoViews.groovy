package views

import menu.Menu
import modelos.PessoaFisica
import modelos.Vaga
import servicos.ServicoCandidato
import servicos.ServicoCandidatoCompetencia
import servicos.ServicoCandidatoVaga
import servicos.ServicoLogin
import servicos.ServicoVaga
import utils.InputValidation

class CandidatoViews {

    private  Scanner scanner
    private InputValidation input
    private ServicoCandidato servicoCandidato
    private PessoaFisica candidato
    private Integer opcao
    private ServicoVaga servicoVaga
    private CompetenciaViews competenciaViews
    private ServicoCandidatoVaga servicoCandidatoVaga
    private ServicoCandidatoCompetencia servicoCandidatoCompetencia
    private ServicoLogin servicoLogin

    CandidatoViews(){
        scanner = new Scanner(System.in)
        input = new InputValidation()
        candidato = new PessoaFisica()
        servicoCandidato = new ServicoCandidato()
        servicoVaga =  new ServicoVaga()
        competenciaViews = new CompetenciaViews()
        servicoCandidatoVaga = new ServicoCandidatoVaga()
        servicoCandidatoCompetencia = new ServicoCandidatoCompetencia()
        servicoLogin = new ServicoLogin()
    }

    void entradaCandidato() {
        while (true){
            opcao = input.validaEntradaDeInteiro("1- Login | 2- Cadastra | 3- Voltar",
                    1 , 3)
            if (opcao == 1) {
                println("Email: ")
                String email_empresa = scanner.nextLine()
                println("Senha: ")
                String senha_empresa = scanner.nextLine()
                PessoaFisica candidato = servicoCandidato.entradaCandidato(email_empresa, senha_empresa)
                if (candidato != null) {
                    ServicoLogin.setCandidato(candidato)
                    menuPrincipalCandidato()
                    break
                } else {
                    println("Email ou senha incorretos")
                }
            } else if (opcao == 2) {
                System.out.println("Informe o cpf")
                candidato.cpf = scanner.nextLine()
                def inserir = servicoCandidato.inserir(imformacoesCandidato())
                if (inserir ) {
                    def addCompetencias = competenciaViews.inserirCompetenciaCandidato(candidato.cpf)
                    if (addCompetencias){
                        println("Candidato " + candidato.getNome() + " foi inserido com sucesso")
                        servicoLogin.setCandidato(
                                servicoCandidato.entradaCandidato(candidato.getEmail(), candidato.getSenha())
                        )
                        menuPrincipalCandidato()
                        break
                    }
                } else {
                    println("Erro ao inserir candidato")
                }
            } else {
                break
            }
        }
    }
    void menuPrincipalCandidato() {
        while (true) {
            opcao = input.validaEntradaDeInteiro("-----MENU-----" +
                    "\n1- Listar todas as vagas\n2- Vagas aplicada\n3- Editar perfil\n4- Sair do programa",
                    1, 4)
            if (opcao == 1) {
                listaVagas()
            } else if (opcao == 2){
                listarVagasAplicadas()
            } else if(opcao == 3) {
                boolean delete = editarPerfil()
                if (delete) {
                    servicoLogin.logout()
                    break
                }
            } else {
                println("Saindo do programa...")
                servicoLogin.logout()
                break
            }
        }
    }

    void listaVagas(){
        while (true){
            ArrayList listar = servicoVaga.listarTodas()
            for (Vaga verVagas : listar){
                println("Id " + verVagas.getId() + ":" + verVagas.getTitulo())
                println("Descricao: " + verVagas.getDescricao())
                println("Local: " + verVagas.getLocal())
                println("Compentecias: " + verVagas.getCompetencias())
                println("---------------------------------------")
            }
            opcao = input.validaEntradaDeInteiro("1- Aplicar em vaga| 2- Sair", 1, 2)
            if (opcao == 1){
                println("Digite o id da vaga para aplicar: ")
                Integer vagaAplicar = Integer.parseInt( scanner.nextLine())
                servicoCandidatoVaga.aplicar(vagaAplicar)
            } else {
                break
            }
        }
    }

    void listarVagasAplicadas() {
       def listaAplicada = servicoCandidatoVaga.listarPorCpf(ServicoLogin.candidato.getCpf())
        for (Vaga verVagas : listaAplicada) {
            println("Id " + verVagas.getId() + " : " + verVagas.getTitulo())
            println("Descricao: " + verVagas.getDescricao())
            println("Local: " + verVagas.getLocal())
            println("Compentecias: " + verVagas.getCompetencias())
            println("---------------------------------------")
        }
    }

    boolean editarPerfil() {
        while (true) {
            opcao = input.validaEntradaDeInteiro(
                    "1- Ver perfil\n2- Editar perfil\n3- Excluir Perfil\n4- Voltar para o menu principal",
                    1, 4)
            if (opcao == 1){
                PessoaFisica candidato = servicoLogin.getCandidato()
                println(candidato)
            } else if(opcao == 2) {
                candidato.cpf = ServicoLogin.candidato.cpf
                opcao = input.validaEntradaDeInteiro("1- Editar detalhes | 2- Editar Competencia | 3- Voltar",
                1, 3)
                if (opcao == 1) {
                    editarDescricao()
                } else if (opcao == 2){
                    editarCompetencia()
                }
            } else if (opcao == 3) {
                opcao = input.validaEntradaDeInteiro(
                        "Certeza que deseja exluir perfil:\n 1- Sim | 2- Não", 1, 2)
                if (opcao == 1){
                    servicoCandidato.deletar(ServicoLogin.getCandidato().cpf)
                    println("Apagado com sucesso")
                    return true
                }
            } else {
                break
            }
        }
        return false
    }

    void editarDescricao(){
        boolean vericacaoAtualizacao = servicoCandidato.atualizar(imformacoesCandidato())
        if (vericacaoAtualizacao) {
            println("O candidato foi atualizando com sucesso")
        } else {
            println("tente novamente")
        }
    }

    void editarCompetencia(){
        while (true){
            opcao = input.validaEntradaDeInteiro("1- Add nova Competencia | 2- Apagar Competencia | 3- Voltar",
                    1, 3)
            if (opcao == 1){
                def addCompetencias = competenciaViews.inserirCompetenciaCandidato(candidato.cpf)
                if (addCompetencias) {
                    println("Atualizando com sucesso")
                } else {
                    println("Erro")
                }
            } else if (opcao == 2){
                competenciaViews.deletarCompetenciaCandidato()
            } else if (opcao == 3) {
                break
            }
        }

    }

    PessoaFisica imformacoesCandidato(){
        println("Informe o nome ");
        candidato.nome = scanner.nextLine();

        println("Informe o email ");
        candidato.email = scanner.nextLine()

        println("Informe a senha ");
        candidato.senha = scanner.nextLine()

        println("Informe o telefone ");
        candidato.telefone = scanner.nextLine()

        println("Informe o cep ");
        candidato.cep = scanner.nextLine()

        println("Informe o estado ");
        candidato.estado = scanner.nextLine()

        println("Informe a descricao");
        candidato.descricao = scanner.nextLine();

        println("Informe a idade ");
        candidato.idade = Integer.parseInt(scanner.nextLine())

        return candidato
    }
}
