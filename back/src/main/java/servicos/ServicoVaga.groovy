package servicos

import database.ServicoConectarBanco
import modelos.Vaga

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class ServicoVaga {
    private ServicoConectarBanco servicoConectar

    ServicoVaga(){
        servicoConectar = new ServicoConectarBanco()
    }

    String montarQueryBuscarPorCnpj() {
        return "SELECT id_vaga, descricao_vaga, titulo_vaga, local_vaga " +
                "FROM linlketinder.vaga WHERE cnpj_empresa = ?"
    }

    String montarQueryBuscarTodos() {
        return "SELECT id_vaga descricao_vaga, titulo_vaga, local_vaga " +
                "FROM linlketinder.vaga "
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

    void alertaErro(Exception tipoDeErro) {
        tipoDeErro.printStackTrace();
        System.err.println("Erro em " + {tipoDeErro});
        System.exit(-42);
    }

    void criar(Vaga vaga) {
        String INSERIR = "INSERT INTO linlketinder.vaga" +
                "(descricao_vaga, titulo_vaga, local_vaga, cnpj_empresa)\n" +
                "VALUES ( ?, ?, ?, ?)"
        try {
            salvarImformacao(INSERIR, vaga)
        } catch (Exception exception) {
            alertaErro("criar vaga")
        }
    }

    Vaga listar(String cnpj_vaga) {
        try {
            Connection conn = servicoConectar.conectar();
            PreparedStatement vaga = conexao.prepareStatement(
                    montarQueryBuscarPorCnpj(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            )

            vaga.setString(1, cnpj_vaga)
            ResultSet res = vaga.executeQuery();
            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            def vagas = []
            if(qtd > 0) {
                PreparedStatement del = conn.prepareStatement(montarQueryBuscarPorCnpj())
                del.setString(1, cnpj_vaga)
                while (res.next()) {
                    Vaga v = new Vaga (
                            res.getInt(1),
                            res.getString(2),
                            res.getString(3),
                            res.getString(4)
                    )
                    vagas.add(v)
                }
            }
            return vagas

        }catch(Exception exep){
            alertaErro("listar empresas")
        }
    }

}
