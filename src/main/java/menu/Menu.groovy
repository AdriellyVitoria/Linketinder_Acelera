package menu

import servicos.ServicoPessoaFisica
import servicos.ServicoPessoaJuridica

class Menu {
    static servicoPessoaFisica = new ServicoPessoaFisica()
    static servicoPessoaJuridica = new ServicoPessoaJuridica()

    static menuInicial(){
        Scanner scanner = new Scanner(System.in)
        def opcao
        while (true){
            println """
*** MENU ***
1 - Listar candidados
2 - Listar Empresas
3 - Sair do programa
"""
            opcao = Integer.parseInt(scanner.nextLine())
            switch(opcao){
                case 1:
                    println servicoPessoaFisica.listarCandidatos()
                    break
                case 2:
                    println servicoPessoaJuridica.listarEmpresas()
                    break
                case 3:
                    println "Saindo do programa, Até mais"
                    break
                default:
                    println "Opção inválida!! Por favor, tente novamente"
                    break
            }
            if (opcao == 3)
                break
        }
    }
}

