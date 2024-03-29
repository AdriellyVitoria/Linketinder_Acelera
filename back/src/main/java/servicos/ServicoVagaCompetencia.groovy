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

    ArrayList<Competencia> listarCompetencia(Integer id_vaga) {
        try {
            Connection conexao = servicoConectar.conectar()
            PreparedStatement compentenciasQuery = conexao.prepareStatement(
                    buscarCompetencia(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );

            compentenciasQuery.setInt(1, id_vaga)
            ResultSet res = compentenciasQuery.executeQuery()

            res.last()
            int qtd = res.getRow()
            res.beforeFirst()

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
            System.err.println("Erro ao buscar competencia")
            System.exit(-42)
        }
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
            System.err.println("Erro ao buscar competencia")
            System.exit(-42)
        }
    }

    boolean deletar(Integer id_competencia, Integer id_vaga) {
        String DELETAR = "DELETE FROM linlketinder.vaga_competencia " +
                "WHERE id_vaga= ? AND id_competencia = ?"
        try {
            Connection conn = servicoConectar.conectar();
            PreparedStatement del = conn.prepareStatement(DELETAR)
            del.setInt(1, id_vaga)
            del.setInt(2, id_competencia)

            del.executeUpdate()
            del.close()
            servicoConectar.desconectar(conn)
            return true
        } catch (Exception exception) {
            System.err.println("Erro em deletar Competencia em vaga");
            System.exit(-42);
        }
        return false
    }

    boolean inserir(Integer id_competencia, Integer id_vaga) {
        String INSERIR = "INSERT INTO linlketinder.vaga_competencia(id_competencia, id_vaga) " +
                "VALUES (?, ?)"
        try {
            Connection conn = servicoConectar.conectar()
            PreparedStatement salvar = conn.prepareStatement(INSERIR);
            salvar.setInt(1, id_competencia)
            salvar.setInt(2, id_vaga)

            salvar.executeUpdate();
            salvar.close();
            servicoConectar.desconectar(conn);
            return true
        } catch (Exception exception) {
            System.err.println("Erro em inserir")
            if (exception.message.contains("vaga_competencia_pkey")){
                System.err.println("Voce já inserir essa competencia")
            }
            if (exception.message.contains("vaga_competencia_id_competencia_fkey")){
                System.err.println("essa competencia não existe")
            }

        }
        return false
    }
}