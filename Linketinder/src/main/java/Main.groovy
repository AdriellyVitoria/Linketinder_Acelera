import listas.ListaCandidatos
import listas.ListaEmpresa

class Main {
    static void main(String[] args) {
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
                    def listarCandidatos = new ListaCandidatos()
                    println listarCandidatos.exibirCandidatos()
                    break
                case 2:
                    def listaEmpresas = new ListaEmpresa()
                    println listaEmpresas.exibirEmpresas()
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
