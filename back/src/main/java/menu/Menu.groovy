package menu

import servicos.ServicoCandidato
import servicos.ServicoEmpresa

class Menu {
    static void main(String[] args) {
        entradaEmpresa()
    }
    static servicoEmpresa = new ServicoEmpresa()
    static servicoCandidato = new ServicoCandidato();
    static Scanner scanner = new Scanner(System.in)
    static opcao

    static menuInicial(){
        while (true) {
            println("Olá, Bem vindo ao LINKETINDER\nEntrar como:\n1 - Candidato\n2 - Empresa\n3 - Sair ")
            opcao = Integer.parseInt(scanner.nextLine())
            if (opcao == 1) {
                entradaEmpresa()
            } else if (opcao == 2) {
                entradaCandidato();
            } else {
                println("Saindo do programa... Volter sempre")
                break
            }
        }
    }

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
            String cpf_candidato = scanner.nextLine();

            System.out.println("Informe o nome ");
            String nome_candidato = scanner.nextLine();

            System.out.println("Informe o email ");
            String email_candidato = scanner.nextLine();

            System.out.println("Informe a senha ");
            String senha_candidato = scanner.nextLine();

            System.out.println("Informe o telefone ");
            String telefone_candidato = scanner.nextLine();

            System.out.println("Informe o cep ");
            String cep_candidato = scanner.nextLine();

            System.out.println("Informe o estado ");
            String estado_candidato = scanner.nextLine();

            System.out.println("Informe a descricao");
            String descricao_candidato = scanner.nextLine();

            System.out.println("Informe a idade ");
            String idade_candidato = scanner.nextLine();

            servicoCandidato.inserir(cpf_candidato, nome_candidato, email_candidato,
                    senha_candidato, telefone_candidato, cep_candidato,
                    estado_candidato, descricao_candidato, idade_candidato)
        }
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
            System.out.println("Informe o cpf");
            String cnpj_empresa = scanner.nextLine();

            System.out.println("Informe o nome ");
            String nome_empresa = scanner.nextLine();

            System.out.println("Informe o email ");
            String email_empresa = scanner.nextLine();

            System.out.println("Informe a senha ");
            String senha_empresa = scanner.nextLine();

            System.out.println("Informe o telefone ");
            String telefone_empresa = scanner.nextLine();

            System.out.println("Informe o cep ");
            String cep_empresa = scanner.nextLine();

            System.out.println("Informe o estado ");
            String estado_empresa = scanner.nextLine();

            System.out.println("Informe o Pais ");
            String pais_empresa = scanner.nextLine()

            System.out.println("Informe a descricao");
            String descricao_empresa = scanner.nextLine();

            servicoEmpresa.inserir(cnpj_empresa, nome_empresa, email_empresa,
                    senha_empresa, telefone_empresa, cep_empresa,
                    estado_empresa, pais_empresa, descricao_empresa)
        }
    }


}

