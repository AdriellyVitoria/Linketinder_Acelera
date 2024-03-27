package servicos

import database.ServicoConectarBanco
import modelos.Competencia

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class ServicoVagaCompetencia {

    private ServicoConectarBanco servicoConectar

    ServicoVagaCompetencia() {
        servicoConectar = new ServicoConectarBanco()
    }

    String buscarCompetencia() {
        return "SELECT " +
                "c.id_competencia, " +
                "c.descricao_competencia " +
                "FROM linlketinder.vaga_competencia vc " +
                "JOIN linlketinder.competencia c ON c.id_competencia = vc.id_competencia " +
                "WHERE vc.id_vaga = ?"
    }

    ArrayList<Competencia> buscarCompetencia(Integer id_Vaga) {
        try {
            Connection conexao = servicoConectar.conectar();
            PreparedStatement compentenciasQuery = conexao.prepareStatement(
                    buscarCompetencia(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );

            compentenciasQuery.setInt(1, id_Vaga);
            ResultSet res = compentenciasQuery.executeQuery();

            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            def competencias = []
            if (qtd > 0) {
                while (res.next()) {
                    Competencia c = new Competencia(
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

    void deletar(Integer id_competencia, String cnpj_empresa) {
        String DELETAR = "DELETE FROM linlketinder.empresa_competencia\n " +
                "WHERE cnpj_empresa =? AND id_competencia =?"
        try {
            Connection conn = servicoConectar.conectar();
            PreparedStatement vaga = conn.prepareStatement(
                    buscarCompetencia(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            )

            vaga.setString(1, cnpj_empresa);
            ResultSet res = vaga.executeQuery();
            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            if (qtd > 0) {
                PreparedStatement del = conn.prepareStatement(DELETAR)
                del.setString(1, cnpj_empresa)
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

    boolean inserir(Integer id_competencia, String cnpj_empresa) {
        String INSERIR = "INSERT INTO linlketinder.empresa_competencia(id_competencia, cnpj_empresa)" +
                " VALUES (?, ?)"
        try {
            Connection conn = servicoConectar.conectar()
            PreparedStatement salvar = conn.prepareStatement(INSERIR);
            salvar.setInt(1, id_competencia)
            salvar.setString(2, cnpj_empresa)

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