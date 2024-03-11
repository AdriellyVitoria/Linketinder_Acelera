import modelos.PessoaJuridica
import org.junit.Assert
import org.junit.Test
import servicos.ServicoPessoaJuridica

class TestEmpresa {
    PessoaJuridica empresa
    ServicoPessoaJuridica servicoPessoaJuridica

    TestEmpresa() {
        this.empresa = new PessoaJuridica('Companhia Galley-La',
                'galleyla@gmail.com',
                'Water 7',
                '12356',
                'descricao',
                ['construcao de código', 'trabalho em equipe'],
                '12345678000190',
                'Brasil')
        this.servicoPessoaJuridica =  new ServicoPessoaJuridica()
    }

    @Test
    void testAddListaEmpresa(){
        def empresas = servicoPessoaJuridica.obterEmpresas()
        servicoPessoaJuridica.addEmpresa(empresa)
        
        Assert.assertTrue(empresas.contains(empresa))
    }

    @Test
    void testListarEmpresa(){
        def empresa = servicoPessoaJuridica.listarEmpresas()
        Assert.assertTrue(empresa.length() > 0)
    }
}
