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

    void inserir(String competencia) {
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

    def listar() {
        try {
            Connection conexao = servicoConectar.conectar();
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
}
