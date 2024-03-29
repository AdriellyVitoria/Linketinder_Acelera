package menu

import sun.awt.X11.XInputMethod
import utils.InputValidation
import views.CandidatoViews
import views.EmpresaViews

class Menu {
    private InputValidation input
    private CandidatoViews candidato
    private EmpresaViews empresa
    private opcao

    Menu() {
        input = new InputValidation()
        candidato = new CandidatoViews()
        empresa = new EmpresaViews()
        opcao
    }

    void menuInicial(){
        while (true) {
            opcao = input.validaEntradaDeInteiro("Olá, Bem vindo ao LINKETINDER\n" +
                    "Entrar como:\n1 - Candidato\n2 - Empresa\n3 - Sair", 1, 3)
            if (opcao == 1) {
                candidato.entradaCandidato()
            } else if (opcao == 2) {
                empresa.opcaoLoginCadastroEmpresa()
            } else {
                println("Saindo do programa... Volter sempre")
                break
            }
        }

    }
}