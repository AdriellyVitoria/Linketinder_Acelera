package menu

import modelos.PessoaJuridica
import servicos.ServicoCandidato
import servicos.ServicoEmpresa

class Menu {
    static void main(String[] args) {
        servicoEmpresa.listar()
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


}

