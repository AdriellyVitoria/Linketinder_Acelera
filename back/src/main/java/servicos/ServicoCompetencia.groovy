package servicos

import com.sun.org.apache.bcel.internal.generic.RET
import database.ServicoConectarBanco
import modelos.Competencia

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class ServicoCompetencia {
    private ServicoConectarBanco servicoConectar

    ServicoCompetencia(){
        servicoConectar = new ServicoConectarBanco()
    }

    String montarQueryBuscarTodos() {
        return "SELECT id_competencia, descricao_competencia FROM linlketinder.competencia"
    }

    String montarQueryBuscarPorCnpj(){
        return "SELECT " +
                "c.id_competencia," +
                "c.descricao_competencia " +
                "FROM linlketinder.competencia c " +
                "JOIN linlketinder.vaga_competencia vc ON vc.id_competencia = c.id_competencia " +
                "JOIN linlketinder.vaga v ON vc.id_vaga = v.id_vaga " +
                "WHERE v.cnpj_empresa = ?" +
                "GROUP BY " +
                "c.id_competencia," +
                "c.descricao_competencia"
    }

    void inserir(String competencia) {
        // tem que passar o nome
        String INSERIR = "INSERT INTO linlketinder.competencia(descricao_competencia) VALUES (?)"
        try {
            Connection conn = servicoConectar.conectar()
            PreparedStatement salvar = conn.prepareStatement(INSERIR)
            salvar.setString(1, competencia)
            salvar.executeUpdate();
            salvar.close();
            servicoConectar.desconectar(conn);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.err.println("Erro em inserir");
            System.exit(-42);
        }
    }

    def listarTodas() {
        try {
            Connection conexao = servicoConectar.conectar()
            PreparedStatement empresa = conexao.prepareStatement(
                    montarQueryBuscarTodos(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            ResultSet res = empresa.executeQuery();

            res.last();
            int qtd = res.getRow();
            res.beforeFirst()
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
        }catch(Exception exception){
            exception.printStackTrace();
            System.err.println("Erro em listar");
            System.exit(-42);
        }
    }

    def listarTodasComCnpj(){
        try {
            Connection conexao = servicoConectar.conectar()
            PreparedStatement empresa = conexao.prepareStatement(
                    montarQueryBuscarPorCnpj(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            )
            empresa.setString(1, ServicoLogin.empresa.getCnpj())
            ResultSet res = empresa.executeQuery();

            res.last();
            int qtd = res.getRow();
            res.beforeFirst()
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
        }catch(Exception exception){
            exception.printStackTrace();
            System.err.println("Erro em listar");
            System.exit(-42);
        }
    }
}
