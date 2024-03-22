package utils

class InputValidation {
    private Scanner scanner;

    InputValidation() {
        scanner = new Scanner(System.in);
    }

    Integer validaEntradaDeInteiro(String pergunta, Integer opcaoInicial, Integer opcaoFinal) {
        while (true) {
            System.out.println(pergunta);
            try {
                Integer opcao = Integer.parseInt(scanner.next());
                if (opcao >= opcaoInicial && opcao <= opcaoFinal) {
                    return opcao;
                }
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Opcao Inválida... Tente novamente\n");
            }
        }
    }
}