package servicos

import database.ServicoConectarBanco
import modelos.Vaga

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class ServicoCandidatoVaga {
    private ServicoConectarBanco servicoConectar
    private ServicoVagaCompetencia servicoVaga

    ServicoCandidatoVaga(){
        servicoConectar = new ServicoConectarBanco()
        servicoVaga = new ServicoVagaCompetencia()
    }

    String montarQueryBuscar() {
        return "SELECT id_vaga, descricao_vaga, titulo_vaga, local_vaga " +
                "FROM linlketinder.vaga"
    }

    String montarQueryBuscarPorCpf(){
        return "SELECT id_vaga, descricao_vaga, titulo_vaga, local_vaga " +
                "FROM linlketinder.vaga WHERE cpf_candidato=?"
    }

    String listarVagasAplicadas(){
        return  "SELECT " +
                "v.id_vaga, " +
                "v.descricao_vaga," +
                "v.titulo_vaga," +
                "v.local_vaga " +
                "FROM " +
                "linlketinder.vaga v " +
                "JOIN linlketinder.candidato_vaga cv ON cv.id_vaga = v.id_vaga " +
                "WHERE cv.cpf_candidato = ?"
    }

    void aplicar(Integer id_vaga){
        String APLICAR = "insert into linlketinder.candidato_vaga(cpf_candidato, id_vaga) " +
                " Values (?, ?)"
        try {
            Connection conn = servicoConectar.conectar();
            PreparedStatement vaga = conn.prepareStatement(
                    montarQueryBuscar(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            )
            def cpf = ServicoLogin.candidato.getCpf()

            ResultSet res = vaga.executeQuery()
            res.last()
            int qtd = res.getRow()
            res.beforeFirst();

            if (qtd > 0) {
                PreparedStatement del = conn.prepareStatement(APLICAR)
                del.setString(1, cpf)
                del.setInt(2, id_vaga)
                del.executeUpdate()
                del.close()
                servicoConectar.desconectar(conn)
            }
        } catch (Exception exception) {
            System.err.println("Erro ao aplicar para vaga")
            if (exception.message.contains("candidato_vaga_id_vaga_fkey")){
                System.err.println("Essa vaga não existe")
            }
            if (exception.message.contains("candidato_vaga_pkey")){
                System.err.println("Voce já aplicou nessa vaga")
            }
        }
    }

    def listarPorCpf(String cpf_candidato) {
        try {
            Connection conn = servicoConectar.conectar();
            PreparedStatement vaga = conn.prepareStatement(
                    listarVagasAplicadas(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            )
            vaga.setString(1, cpf_candidato)

            ResultSet res = vaga.executeQuery()
            res.last()
            int qtd = res.getRow()
            res.beforeFirst()

            ArrayList<Vaga> vagas = []
            if(qtd > 0) {
                while (res.next()) {
                    Vaga v = new Vaga (
                            res.getInt(1),
                            res.getString(2),
                            res.getString(3),
                            res.getString(4)
                    )
                    v.setCompetencias(
                            servicoVaga.buscarCompetencia(v.id)
                    )
                    vagas.add(v)
                }
            }
            return vagas
        }catch(Exception exception){
            System.err.println("Erro em listar" )
        }
    }

}
