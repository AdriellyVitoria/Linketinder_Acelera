package views

import modelos.PessoaFisica
import servicos.ServicoCandidato

class CandidatoViews {
    static scanner = new Scanner(System.in)
    static candidato = new PessoaFisica();
    static servicoCandidato = new ServicoCandidato()
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
            candidato.cpf = scanner.nextLine();

            System.out.println("Informe o nome ");
            candidato.nome = scanner.nextLine();

            System.out.println("Informe o email ");
            candidato = scanner.nextLine();

            System.out.println("Informe a senha ");
            candidato = scanner.nextLine();

            System.out.println("Informe o telefone ");
            candidato = scanner.nextLine();

            System.out.println("Informe o cep ");
            candidato = scanner.nextLine();

            System.out.println("Informe o estado ");
            candidato = scanner.nextLine();

            System.out.println("Informe a descricao");
            candidato = scanner.nextLine();

            System.out.println("Informe a idade ");
            candidato = scanner.nextLine();

            servicoCandidato.inserir(candidato)
        }
    }
}
