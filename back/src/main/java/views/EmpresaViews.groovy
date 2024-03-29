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
    private ServicoLogin servicoLogin
    private PessoaJuridica empresa
    private opcao
    private VagaViews vaga
    private ServicoCandidato candidato

    EmpresaViews() {
        scanner = new Scanner(System.in)
        input = new InputValidation()
        servicoEmpresa = new ServicoEmpresa()
        servicoLogin = new ServicoLogin()
        empresa = new PessoaJuridica()
        vaga = new VagaViews(this)
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
                    break
                } else {
                    println("Email ou senha incorretos")
                }
            } else if(opcao == 2) {

                println("Informe o cnpj")
                empresa.setCnpj(scanner.nextLine())

                def inserir = servicoEmpresa.inserir(imformacoesEmpresa())
                if (inserir){
                    println("Empresa " + empresa.getNome() + " foi inserido com sucesso")
                    ServicoLogin.setEmpresa(empresa)
                    menuPrincipalEmpresa()
                    break
                }
            } else {
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
            } else if (opcao == 2){
                boolean delete = editarEmpresa()
                if (delete) {
                    servicoLogin.logout()
                    break
                }
            } else {
                println("Volte Sempre...")
                servicoLogin.logout()
                break
            }
        }
    }

    boolean editarEmpresa(){
        while (true){
            opcao = input.validaEntradaDeInteiro(
                    "1- Ver perfil\n2- Editar perfil\n3- Excluir Perfil\n4- Voltar para o menu principal",
                    1, 4)
            if (opcao == 1){
                PessoaJuridica empresa = servicoLogin.getEmpresa()
                println(empresa)
            } else if (opcao == 2){
                empresa.setCnpj(
                        ServicoLogin.getEmpresa().cnpj
                )
                PessoaJuridica empresa = imformacoesEmpresa()
                String verificacaoAtualizacao = servicoEmpresa.atualizar(empresa);
                if (verificacaoAtualizacao){
                    println("A empresa foi atualizando com sucesso")
                } else {
                    println("tente novamente")
                }
            } else if(opcao == 3){
                opcao = input.validaEntradaDeInteiro(
                        "Certeza que deseja exluir empresa:\n 1- Sim | 2- Não", 1, 2)
                if (opcao == 1){
                    def cnpj = ServicoLogin.getEmpresa().cnpj
                    servicoEmpresa.deletar(cnpj)
                    println("Apagado com sucesso")
                    return true
                }
            } else {
                break
            }
        }
        return false
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
