package views

import modelos.PessoaJuridica
import servicos.ServicoEmpresa

class empresa {
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
    }

    static entradaEmpresa() {
        println("1- Login | 2- Cadastra")
        opcao = Integer.parseInt(scanner.nextLine())

        if (opcao == 1) {
            println("Email: ")
            String email_empresa = scanner.nextLine();
            println("Senha: ")
            String senha_empresa = scanner.nextLine();

            // verificar se está no banco
        } else {
            String informcacoesEmpresa = input()
            servicoEmpresa.inserir(informcacoesEmpresa)
            println("Empresa " + empresa.getNome() + " foi inserido com sucesso")
            menuPrincipalEmpresa()
        }
    }

    static menuPrincipalEmpresa() {
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
