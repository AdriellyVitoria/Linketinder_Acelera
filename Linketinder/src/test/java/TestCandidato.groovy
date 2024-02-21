import modelos.PessoaFisica
import org.junit.Assert
import org.junit.Test
import servicos.ServicoPessoaFisica

class TestCandidato {
    PessoaFisica candidato
    ServicoPessoaFisica sevicoPessoaFisica

    TestCandidato() {
        this.candidato = new PessoaFisica('teste',
                'test@gmail.com',
                'Mockito',
                '58457',
                'AAA',
                ['T', 'T'],
                '12375398463',
                1000)
        this.sevicoPessoaFisica = new ServicoPessoaFisica()
    }

    @Test
    void testAddNaListaCandidato(){
        def candidatos = sevicoPessoaFisica.obterCandidatos()

        sevicoPessoaFisica.addCandidato(candidato)

        Assert.assertTrue(candidatos.contains(candidato))
    }

    @Test
    void testListarCandidato(){
        def candidatosString = sevicoPessoaFisica.listarCandidatos()

        Assert.assertTrue(candidatosString.length() > 0)
    }

}
