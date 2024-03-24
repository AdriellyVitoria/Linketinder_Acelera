package views

import modelos.Vaga
import servicos.ServicoLogin
import servicos.ServicoVaga
import utils.InputValidation

class VagaViews {
    private InputValidation inputValidation
    private ServicoVaga servicoVaga
    private Vaga vaga
    private Scanner scanner
    private Integer opcao

    VagaViews() {
        inputValidation = new InputValidation()
        servicoVaga = new ServicoVaga()
        vaga = new Vaga()
        scanner = new Scanner(System.in)
        this.opcao = opcao
    }

    void menuVagas() {
        opcao = inputValidation.validaEntradaDeInteiro("-----MENU-----" +
                "\n1- Criar Vaga\n2- Excluir vaga\n3- Atualizar vaga\n4- Listar vagas\n5- Voltar",
                1, 5)
        if (opcao == 1) {
            def criar = servicoVaga.criar(imformacoesCriarVaga())
            if (criar != null) {
                println("Erro ao criar vaga")
            } else {
                println("Vaga criada com sucesso")
                menuVagas()
            }
        } else if(opcao == 2){
            deletarVaga()
        }
    }

    Vaga imformacoesCriarVaga() {
        println("Descricao da vaga:")
        vaga.setDescricao(scanner.nextLine())

        println("Titulo da vaga:")
        vaga.setTitulo(scanner.nextLine())

        println("Local da vaga:")
        vaga.setLocal(scanner.nextLine())

        vaga.setCnpj_empresa(
                ServicoLogin.getEmpresa().cnpj
        )
        return vaga
    }

     void deletarVaga() {
         println("Digite o id da vaga para excluir: ")
          servicoVaga.listar(ServicoLogin.getEmpresa().cnpj)
    }
}
