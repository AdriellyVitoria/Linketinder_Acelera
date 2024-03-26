package views

import menu.Menu
import modelos.PessoaFisica
import modelos.Vaga
import servicos.ServicoCandidato
import servicos.ServicoLogin
import servicos.ServicoVaga
import utils.InputValidation

class CandidatoViews {

    private  Scanner scanner
    private InputValidation input
    private ServicoCandidato servicoCandidato
    private PessoaFisica candidato
    private Integer opcao
    private Menu menu
    private ServicoVaga servicoVaga

    CandidatoViews(Menu menu){
        scanner = new Scanner(System.in)
        input = new InputValidation()
        candidato = new PessoaFisica()
        servicoCandidato = new ServicoCandidato()
        this.menu = menu
        servicoVaga =  new ServicoVaga()
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
                } else {
                    println("Email ou senha incorretos")
                }
            } else if (opcao == 2) {
                System.out.println("Informe o cpf");
                candidato.cpf = scanner.nextLine();
                def inserir = servicoCandidato.inserir(imformacoesCandidato())
                if (inserir) {
                    println("Candidato " + candidato.getNome() + " foi inserido com sucesso")
                    menuPrincipalCandidato()
                } else {
                    println("Erro ao inserir candidato")
                }
            } else {
                menu.menuInicial()
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
                println("fazer")
            } else if(opcao == 3) {
                boolean candidato = servicoCandidato.atualizar(imformacoesCandidato())
                if (candidato){
                    println("Atualização com sucesso")
                } else {
                    println("falta na atualização")
                }
            } else {
                println("Saindo do programa...")
                break
            }
        }
    }

    void listaVagas(){
        ArrayList listar = servicoVaga.listarTodas()
        for (Vaga verVagas : listar){
            println("Id " + verVagas.getId() + ":" + verVagas.getTitulo())
            println("Descricao: " + verVagas.getDescricao())
            println("Local: " + verVagas.getLocal())
            //println("Compentecias: " + )
        }
    }

    void listarVagasAplicadas() {

    }

    void editarPerfil() {
        while (true) {
            opcao = input.validaEntradaDeInteiro(
                    "1- Ver perfil\n2- Editar perfil\n2- Excluir Perfil\n3- Voltar para o menu principal",
                    1, 4)
            if (opcao == 1){
                ArrayList candidados = servicoCandidato.buscarLista()
                for (PessoaFisica candidato: candidados) {
                    println("CPF: "+ candidato.getCpf() + "\nNome: " + candidato.getNome())
                    println("Email: " + candidato.getEmail() + "\nTelefone: " + candidato.getTelefone())
                    println("Cep: " + candidato.getCep() + "\nIdade: " + candidato.getIdade())
                    println("Descricao: " + candidato.getDescricao())
                    //buscar o jeito de imprimir as compentecias um abaixo da outra
                    //println("Competecias: "+ )
                }
            } else if(opcao == 2) {
                boolean vericacaoAtualizacao = servicoCandidato.atualizar(imformacoesCandidato())
                if (vericacaoAtualizacao) {
                    println("O candidato foi atualizando com sucesso")
                } else {
                    println("tente novamente")
                }
            } else if (opcao == 3) {
                opcao = input.validaEntradaDeInteiro(
                        "Certeza que deseja exluir empresa:\n 1- Sim | 2- Não", 1, 2)
                if (opcao == 1){
                    servicoCandidato.deletar(ServicoLogin.getCandidato().cpf)
                    println("Apagando com sucesso")
                }
            } else {
                menuPrincipalCandidato()
                break
            }
        }
    }

    PessoaFisica imformacoesCandidato(){
        System.out.println("Informe o nome ");
        candidato.nome = scanner.nextLine();

        System.out.println("Informe o email ");
        candidato.email = scanner.nextLine()

        System.out.println("Informe a senha ");
        candidato.senha = scanner.nextLine()

        System.out.println("Informe o telefone ");
        candidato.telefone = scanner.nextLine()

        System.out.println("Informe o cep ");
        candidato.cep = scanner.nextLine()

        System.out.println("Informe o estado ");
        candidato.estado = scanner.nextLine()

        System.out.println("Informe a descricao");
        candidato.descricao = scanner.nextLine();

        System.out.println("Informe a idade ");
        candidato.idade = Integer.parseInt(scanner.nextLine())

        return candidato
    }
}
