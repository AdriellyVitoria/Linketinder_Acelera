package servicos

import database.ServicoConectarBanco
import modelos.Vaga

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class ServicoVaga {
    private ServicoConectarBanco servicoConectar
    private ServicoVagaCompetencia servicoVaga
    private ServicoVagaCompetencia servicoVagaCompetencia

    ServicoVaga(){
        servicoConectar = new ServicoConectarBanco()
        servicoVaga = new ServicoVagaCompetencia()
        servicoVagaCompetencia = new ServicoVagaCompetencia()
    }

    String montarQueryBuscarPorId() {
        return "SELECT id_vaga, descricao_vaga, titulo_vaga, local_vaga\n " +
                "FROM linlketinder.vaga WHERE id_vaga =?"
    }

    String montarQueryBuscarTodas() {
        return "SELECT id_vaga, descricao_vaga, titulo_vaga, local_vaga " +
                "FROM linlketinder.vaga"
    }

    String montarQueryBuscarPorCnpj() {
        return "SELECT id_vaga, descricao_vaga, titulo_vaga, local_vaga " +
                "FROM linlketinder.vaga WHERE cnpj_empresa=?"
    }


    void salvarImformacao(String comado, Vaga vaga){
        Connection conn = servicoConectar.conectar()
        PreparedStatement salvar = conn.prepareStatement(comado);

        salvar.setString(1, vaga.getDescricao())
        salvar.setString(2, vaga.getTitulo())
        salvar.setString(3, vaga.getLocal())
        salvar.setString(4, vaga.getCnpj_empresa())

        salvar.executeUpdate();
        salvar.close();
        servicoConectar.desconectar(conn);
    }

    void criar(Vaga vaga) {
        String INSERIR = "INSERT INTO linlketinder.vaga" +
                "(descricao_vaga, titulo_vaga, local_vaga, cnpj_empresa)\n" +
                "VALUES (?, ?, ?, ?)"
        try {
            salvarImformacao(INSERIR, vaga)
        } catch (Exception exception) {
            System.err.println("Erro em criar" )
            System.exit(-42);
        }
    }

    Integer buscaIdVagaCriada() {
        String sql = "SELECT v.id_vaga " +
                "FROM linlketinder.vaga v " +
                "ORDER BY v.id_vaga DESC " +
                "LIMIT 1"

        Connection conn = servicoConectar.conectar();
        PreparedStatement vaga = conn.prepareStatement(
                sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
        )
        ResultSet res = vaga.executeQuery();
        res.last();
        int qtd = res.getRow();
        res.beforeFirst();

        if(qtd > 0) {
            while (res.next()) {
                return res.getInt(1)
            }
        }
        return null
    }

    def listarTodas() {
        try {
            Connection conn = servicoConectar.conectar();
            PreparedStatement vaga = conn.prepareStatement(
                    montarQueryBuscarTodas(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            )
            ResultSet res = vaga.executeQuery();
            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

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
            exception.printStackTrace();
            System.err.println("Erro em listar" )
            System.exit(-42);
        }
    }

    def listar(String cnpj_empresa) {
        try {
            Connection conn = servicoConectar.conectar();
            PreparedStatement vaga = conn.prepareStatement(
                    montarQueryBuscarPorCnpj(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            )
            vaga.setString(1, cnpj_empresa)
            ResultSet res = vaga.executeQuery();
            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

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
                          servicoVagaCompetencia.listarCompetencia(v.getId())
                    )
                    vagas.add(v)
                }
            }
            return vagas
        } catch (Exception exception) {
            exception.printStackTrace();
            System.err.println("Erro em listar" )
            System.exit(-42);
        }
    }

    boolean atualizar(Integer id_vaga, Vaga vaga) {
        try {
            Connection conn = servicoConectar.conectar()
            PreparedStatement atualizarVaga = conn.prepareStatement(
                    montarQueryBuscarPorId(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            )
            atualizarVaga.setInt(1, id_vaga)
            ResultSet res = atualizarVaga.executeQuery();

            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            if(qtd >0){
                String ATUALIZAR = "UPDATE linlketinder.vaga " +
                        "SET descricao_vaga=?, titulo_vaga =?, local_vaga =?\n " +
                        "\tWHERE cnpj_empresa =? AND id_vaga = ?"
                PreparedStatement salvar = conn.prepareStatement(ATUALIZAR);

                salvar.setString(1, vaga.getDescricao())
                salvar.setString(2, vaga.getTitulo())
                salvar.setString(3, vaga.getLocal())
                salvar.setString(4, vaga.getCnpj_empresa())
                salvar.setInt(5, id_vaga)

                salvar.executeUpdate();
                salvar.close();
                servicoConectar.desconectar(conn);
                return vaga
            }
        } catch (Exception exeption) {
            System.err.println("Erro em atualizar descricao");
            System.exit(-42);
        }
        return null
    }

    void deletar(Integer id_vaga) {
        String DELETAR = "DELETE FROM linlketinder.vaga WHERE id_vaga =?"

        try {
            Connection conn = servicoConectar.conectar();
            PreparedStatement del = conn.prepareStatement(DELETAR)
            del.setInt(1, id_vaga)
            del.executeUpdate()
            del.close()
            servicoConectar.desconectar(conn)
        } catch (Exception exception) {
            System.err.println("Erro em deletar vaga");
            System.exit(-42);
        }
    }

}
