package servicos

import database.ServicoConectarBanco
import modelos.Competencia

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class ServicoCandidatoCompetencia {
    def servicoConectar = new ServicoConectarBanco()

    String montarQueryBuscarPorCpf() {
        return "select \n" +
                "\tc.id_competencia,\n" +
                "\tc.descricao_competencia\n" +
                "from \n" +
                "\tlinlketinder.candidato_competencia AS cc\n" +
                "\tjoin linlketinder.competencia AS c on c.id_competencia = cc.id_competencia\n" +
                "where\n" +
                "\tcc.cpf_candidato = ?"
    }

    ArrayList<Competencia> buscarCompetencia(String cpf_candidato){
        try {
            Connection conexao = servicoConectar.conectar();
            PreparedStatement compentenciasQuery = conexao.prepareStatement(
                    montarQueryBuscarPorCpf(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );

            compentenciasQuery.setString(1, cpf_candidato);
            ResultSet res = compentenciasQuery.executeQuery();

            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            def competencias = []
            if (qtd > 0){
                while (res.next()) {
                    Competencia c = Competencia (
                            res.getInt(1),
                            res.getString(2)
                    )
                    competencias.add(c)
                }
            }
            return competencias
        } catch (Exception exception) {
            exception.printStackTrace();
            System.err.println("Erro ao buscar competencia")
            System.exit(-42)
        }
    }

}
