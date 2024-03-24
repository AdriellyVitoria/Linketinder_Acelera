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
    private EmpresaViews empresa

    VagaViews(EmpresaViews empresa) {
        inputValidation = new InputValidation()
        servicoVaga = new ServicoVaga()
        vaga = new Vaga()
        scanner = new Scanner(System.in)
        this.opcao = opcao
        this.empresa = empresa
    }

    void menuVagas() {
        while (true){
            opcao = inputValidation.validaEntradaDeInteiro("-----MENU-----" +
                    "\n1- Criar Vaga\n2- Listar vaga\n3- Atualizar vaga\n4- Deletar vagas\n5- Voltar",
                    1, 5)
            if (opcao == 1) {
                def criar = servicoVaga.criar(imformacoesParaVaga())
                if (criar != null) {
                    println("Erro ao criar vaga")
                } else {
                    println("Vaga criada com sucesso")
                    menuVagas()
                }
            } else if(opcao == 2){
                listar(ServicoLogin.getEmpresa().cnpj)
            } else if (opcao == 3) {
                atualizar()
            } else if(opcao == 4) {
                deletarVaga()
            } else {
                empresa.menuPrincipalEmpresa()
                break
            }
        }
    }

    Vaga imformacoesParaVaga() {
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

    boolean listar(String cnpj_empresa){
        ArrayList listarVaga  = servicoVaga.listar(cnpj_empresa)
        if (!listarVaga.isEmpty()) {
            for (Vaga vaga : listarVaga) {
                println("ID: " + vaga.getId());
                println("Descrição: " + vaga.getDescricao());
                println("Título: " + vaga.getTitulo());
                println("Local: " + vaga.getLocal());
                println("----------------------------------");
            }
            return true
        } else {
            println("Nenhuma vaga encontrada para o CNPJ especificado.")
            menuVagas()
        }
    }

    void atualizar(){
        boolean listarVaga = listar(ServicoLogin.getEmpresa().cnpj)
        if (listarVaga) {
            opcao = inputValidation.validaEntradaDeInteiro("1- Digita o id para editar | 2- Voltar",
                    1, 2)
            if (opcao == 1) {
                println("Digite o id da vaga que deseja editar: ")
                Integer id_vaga = Integer.parseInt(scanner.nextLine())
                Vaga vaga = imformacoesParaVaga()
                def confirmacaoAtualizar = servicoVaga.atualizar(id_vaga, vaga)
                if (confirmacaoAtualizar =! null ){
                    println("Vaga atualizada com sucesso")
                } else {
                    println("Tente novamente")
                }
            } else {
                menuVagas()
            }
        }
    }
     void deletarVaga() {
         boolean listarVaga = listar(ServicoLogin.getEmpresa().cnpj)
         if (listarVaga) {
             println("Digite o id da vaga para excluir: ")
             Integer id_vaga = Integer.parseInt(scanner.nextLine())
             opcao = inputValidation.validaEntradaDeInteiro("Certeza que deseja excluir:\n 1- Sim | 2-Não",
                     1, 2)
             if (opcao == 1){
                 String cnpj = ServicoLogin.getEmpresa().cnpj
                 servicoVaga.deletar(id_vaga, cnpj)

                 println("Vaga apagada com sucesso")
             } else {
                 menuVagas()
             }
         }
    }
}
