package servicos

import database.ServicoConectarBanco
import modelos.Competencia

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class ServicoCandidatoCompetencia {
    def servicoConectar = new ServicoConectarBanco()

    String montarQueryBuscarPorCpf() {
        return "select " +
                "\tc.id_competencia, " +
                "\tc.descricao_competencia " +
                "from " +
                "\tlinlketinder.candidato_competencia AS cc " +
                "\tjoin linlketinder.competencia AS c on c.id_competencia = cc.id_competencia " +
                "where " +
                "\tcc.cpf_candidato = ?"
    }

    ArrayList<Competencia> listarCompetencia(String cpf_candidato){
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
                    Competencia c = new Competencia (
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

    void deletar(Integer id_competencia, String cpf_candidato){
        String DELETAR = "DELETE FROM linlketinder.candidato_competencia\n " +
                "WHERE cpf_candidato =? AND id_competencia =?"
        try {
            Connection conn = servicoConectar.conectar();
            PreparedStatement candidato = conn.prepareStatement(
                    montarQueryBuscarPorCpf(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            )

            candidato.setString(1, cpf_candidato);
            ResultSet res = candidato.executeQuery();
            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            if (qtd > 0) {
                PreparedStatement del = conn.prepareStatement(DELETAR)
                del.setString(1, cpf_candidato)
                del.setInt(2, id_competencia)
                del.executeUpdate()
                del.close()
                servicoConectar.desconectar(conn)
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.err.println("Erro em deletar");
            System.exit(-42);
        }
    }

    boolean inserir(Integer id_competencia, String cpf_candidato){
        String INSERIR = "INSERT INTO linlketinder.candidato_competencia(id_competencia, cpf_candidato)" +
                " VALUES (?, ?)"
        try {
            Connection conn = servicoConectar.conectar()
            PreparedStatement salvar = conn.prepareStatement(INSERIR);

            salvar.setString(1, cpf_candidato)
            salvar.setInt(2, id_competencia)
            salvar.executeUpdate();
            salvar.close();
            servicoConectar.desconectar(conn);
            return true
        } catch (Exception exception) {
            exception.printStackTrace();
            System.err.println("Erro em inserir");
            System.exit(-42);
        }
        return false
    }
}
