package views

import menu.Menu
import modelos.PessoaJuridica
import servicos.ServicoCandidato
import servicos.ServicoEmpresa
import servicos.ServicoLogin
import utils.InputValidation

class EmpresaViews {
    private Scanner scanner
    private InputValidation input
    private ServicoEmpresa servicoEmpresa
    private PessoaJuridica empresa
    private opcao
    private VagaViews vaga
    private ServicoCandidato candidato
    private Menu menu

    EmpresaViews(Menu menu) {
        scanner = new Scanner(System.in)
        input = new InputValidation()
        servicoEmpresa = new ServicoEmpresa()
        empresa = new PessoaJuridica()
        vaga = new VagaViews(this)
        this.menu = menu
        candidato = new ServicoCandidato()
    }

    void opcaoLoginCadastroEmpresa() {
        while (true){
            opcao = input.validaEntradaDeInteiro("1- Login | 2- Cadastra | 3- Voltar",
                    1, 3)
            if (opcao == 1) {
                println("Email: ")
                String email_empresa = scanner.nextLine();
                println("Senha: ")
                String senha_empresa = scanner.nextLine();
                PessoaJuridica empresa = servicoEmpresa.entradaEmpresa(email_empresa, senha_empresa)
                if (empresa != null) {
                    ServicoLogin.setEmpresa(empresa)
                    menuPrincipalEmpresa()
                } else {
                    println("Email ou senha incorretos")
                }
            } else if(opcao == 2) {
                println("Informe o cnpj")
                empresa.setCnpj(scanner.nextLine())
                def inserir = servicoEmpresa.inserir(imformacoesEmpresa())
                if (inserir){
                    println("Empresa " + empresa.getNome() + " foi inserido com sucesso")
                    menuPrincipalEmpresa()
                } else {
                    println("Error tente novamente")
                }
            } else {
                menu.menuInicial()
                break
            }
        }
    }

    void menuPrincipalEmpresa() {
        while (true) {
            opcao = input.validaEntradaDeInteiro("-----MENU-----" +
                    "\n1- Menu vaga\n2- Editar perfil\n3- Sair do programa",
                    1, 3)
            if (opcao == 1){
                vaga.menuVagas()
            } else if (opcao == 3){
                editarEmpresa()
            } else {
                println("Volte Sempre...")
                break
            }
        }
    }

    void editarEmpresa(){
        while (true){
            opcao = input.validaEntradaDeInteiro(
                    "1- Editar perfil\n2- Excluir Perfil\n3- Voltar para o menu principal",
                    1, 3)
            if (opcao == 1){
                empresa.setCnpj(
                        ServicoLogin.getEmpresa().cnpj
                )
                PessoaJuridica empresa = imformacoesEmpresa()
                String verificacaoNull = servicoEmpresa.atualizar(empresa);
                if (verificacaoNull){
                    println("A empresa foi atualizando com sucesso")
                } else {
                    println("tente novamente")
                }
            } else if(opcao == 2){
                opcao = input.validaEntradaDeInteiro(
                        "Certeza que deseja exluir empresa:\n 1- Sim | 2- Não", 1, 2)
                if (opcao == 1){
                    def cnpj = ServicoLogin.getEmpresa().cnpj
                    servicoEmpresa.deletar(cnpj)
                    println("Apagando com sucesso")
                }
            } else {
                menuPrincipalEmpresa()
                break
            }
        }
    }

    PessoaJuridica imformacoesEmpresa() {
        println("Informe o nome")
        empresa.setNome(scanner.nextLine())

        println("Informe o email")
        empresa.setEmail(scanner.nextLine())

        println("Informe a senha")
        empresa.setSenha(scanner.nextLine())

        println("Informe o telefone")
        empresa.setTelefone(scanner.nextLine())

        println("Informe o cep")
        empresa.setCep(scanner.nextLine())

        println("Informe o estado")
        empresa.setEstado(scanner.nextLine())

        println("Informe o Pais")
        empresa.setPais(scanner.nextLine())

        println("Informe a descricao")
        empresa.setDescricao(scanner.nextLine())

        return empresa
    }
}
