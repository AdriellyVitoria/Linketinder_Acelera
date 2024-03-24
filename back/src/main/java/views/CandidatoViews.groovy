package views

import modelos.PessoaFisica
import servicos.ServicoCandidato
import servicos.ServicoLogin
import utils.InputValidation

class CandidatoViews {

    private  Scanner scanner
    private InputValidation input
    private ServicoCandidato servicoCandidato
    private PessoaFisica candidato
    private opcao

    CandidatoViews(){
        scanner = new Scanner(System.in)
        input = new InputValidation()
        candidato = new PessoaFisica()
        servicoCandidato = new ServicoCandidato()
        opcao
    }

    void entradaCandidato() {
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

            } else {
                println("Email ou senha incorretos")
            }
        } else if (opcao == 2) {
            System.out.println("Informe o cpf");
            candidato.cpf = scanner.nextLine();
            imformacoesCandidato()

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
