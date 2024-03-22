package views

import modelos.PessoaJuridica
import servicos.ServicoEmpresa

class EmpresaViews {
    static Scanner scanner = new Scanner(System.in)
    static servicoEmpresa = new ServicoEmpresa()
    static empresa = new PessoaJuridica()
    static opcao

    static PessoaJuridica input() {
        System.out.println("Informe o cnpj")
        empresa.setCnpj(scanner.nextLine())

        System.out.println("Informe o nome ")
        empresa.setNome(scanner.nextLine())

        System.out.println("Informe o email ")
        empresa.setEmail(scanner.nextLine())

        System.out.println("Informe a senha ")
        empresa.setSenha(scanner.nextLine())

        System.out.println("Informe o telefone ")
        empresa.setTelefone(scanner.nextLine())

        System.out.println("Informe o cep ")
        empresa.setCep(scanner.nextLine())

        System.out.println("Informe o estado ")
        empresa.setEstado(scanner.nextLine())

        System.out.println("Informe o Pais ")
        empresa.setPais(scanner.nextLine())

        System.out.println("Informe a descricao")
        empresa.setDescricao(scanner.nextLine())

        return empresa
    }

    static entradaCadastroEmpresa() {
        println("1- Login | 2- Cadastra")
        opcao = Integer.parseInt(scanner.nextLine())

        if (opcao == 1) {
            println("Email: ")
            String email_empresa = scanner.nextLine();
            println("Senha: ")
            String senha_empresa = scanner.nextLine();

            // verificar se está no banco
            menuPrincipalEmpresa()
        } else {
            PessoaJuridica informcacoesEmpresa = input()
            servicoEmpresa.inserir(informcacoesEmpresa)
            // add if para caso de erro não continuar
            println("Empresa " + empresa.getNome() + " foi inserido com sucesso")
            menuPrincipalEmpresa()
        }
    }

    static menuPrincipalEmpresa() {
       println("-----MENU-----" +
               "\n1- Criar Vaga\n2- Excluir vaga\n3- Atualizar vaga\n4- Listar vagas\n" +
               "5- Listar Candidatos\n6- Dá match em Candidato\n7- Excluir Candidato\n" +
               "8- Editar perfil\n9- Sair")
        opcao = Integer.parseInt(scanner.nextLine())
        //colocar o excluir em menu editar
    }

    static editarEmpresa(){
        println("1- Editar perfil\n2- Excluir Perfil\n3- Sair")
        String opcao = scanner.nextLine()
        if (opcao != 3){

        } else if(opcao == 1){
            println("Informe o cnpj da empresa para atualização: ")
            String cnpj = scanner.nextLine();
            String verificacaoNull = servicoEmpresa.atualizar(cnpj);
            if (verificacaoNull != null){
                String imformaçoesAtualizar = input()
                servicoEmpresa.atualizar(imformaçoesAtualizar)
                println("A empresa foi atualizando com sucesso")
            } else {
                println("Cnpj da empresa não existe, tente novamente")
            }
        }
    }
}
