package views

import modelos.PessoaFisica

class candidato {
    static Scanner scanner = new Scanner(System.in)
    static PessoaFisica empresa = new PessoaFisica();
    static opcao

    static entradaCandidato() {
        println("1- Login | 2- Cadastra")
        opcao = Integer.parseInt(scanner.nextLine())

        if (opcao == 1) {
            println("Email: ")
            String email_empresa = scanner.nextLine();
            println("Senha: ")
            String senha_empresa = scanner.nextLine();

            // verificar se está no banco
        } else if (opcao == 2) {

            System.out.println("Informe o cpf");
            empresa.cpf = scanner.nextLine();

            System.out.println("Informe o nome ");
            empresa.nome = scanner.nextLine();

            System.out.println("Informe o email ");
            empresa = scanner.nextLine();

            System.out.println("Informe a senha ");
            empresa = scanner.nextLine();

            System.out.println("Informe o telefone ");
            empresa = scanner.nextLine();

            System.out.println("Informe o cep ");
            empresa = scanner.nextLine();

            System.out.println("Informe o estado ");
            empresa = scanner.nextLine();

            System.out.println("Informe a descricao");
            empresa = scanner.nextLine();

            System.out.println("Informe a idade ");
            empresa = scanner.nextLine();

            servicoCandidato.inserir(empresa)
        }
    }
}
