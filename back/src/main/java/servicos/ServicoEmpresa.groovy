package servicos

import database.ServicoConectarBanco
import modelos.Empresa
import modelos.PessoaJuridica

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class ServicoEmpresa {

    def servicoConectar = new ServicoConectarBanco()
    def servicoCompetencia = new ServiceEmpresaCompetencia()

    String montarQueryBuscarTodos() {
        return "SELECT \n" +
                "\te.cnpj_empresa, \n" +
                "\te.nome_empresa,\n" +
                "\te.email_empresa,\n" +
                "\te.telefone_empresa,\n" +
                "\te.cep_empresa,\n" +
                "\te.descricao_empresa\t\n" +
                "FROM \n" +
                "\tlinlketinder.empresa AS e"
    }

    String montarQueryBuscarPorCnpj(){
        return "SELECT * FROM linlketinder.empresa WHERE cnpj_empresa=?";
    }

     salvarInformaçoes(String comado,PessoaJuridica empresa){

        Connection conn = servicoConectar.conectar()
        PreparedStatement salvar = conn.prepareStatement(comado);

        salvar.setString(1, empresa.getCnpj());
        salvar.setString(2, empresa.getNome());
        salvar.setString(3, empresa.getEmail());
        salvar.setString(4, empresa.getSenha());
        salvar.setString(5, empresa.getTelefone());
        salvar.setString(6,empresa.getCep());
        salvar.setString(7, empresa.getEstado());
        salvar.setString(8, empresa.getPais());
        salvar.setString(9, empresa.getDescricao());

        salvar.executeUpdate();
        salvar.close();
        servicoConectar.desconectar(conn);
    }

    alertaErro(String tipoDeErro) {
        tipoDeErro.printStackTrace();
        System.err.println("Erro em " + {tipoDeErro});
        System.exit(-42);
    }

    buscarLista() {
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
            res.beforeFirst();

            def empresas = []
            if (qtd > 0) {
                System.out.println("Listando empresas");
                System.out.println("--------------------------------");
                while (res.next()) {
                    Empresa e = new Empresa(
                            res.getString(1),
                            res.getString(2),
                            res.getString(3),
                            res.getString(4),
                            res.getString(5),
                            res.getString(6),
                    )
                    e.setCompetencias(
                            servicoCompetencia.buscarCompetencia(e.cnpj)
                    )
                    empresas.add(e)
                }
            }
            return empresas
        }catch(Exception exep){
           alertaErro("Busca empresa")
        }
    }

    inserir(PessoaJuridica empresa){

        String INSERIR = "INSERT INTO linlketinder.empresa(cnpj_empresa, nome_empresa, email_empresa,\n" +
                "                    senha_empresa, telefone_empresa, cep_empresa,\n" +
                "                    estado_empresa, pais_empresa, descricao_empresa) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            salvarInformaçoes(INSERIR,empresa)
        }catch (Exception e) {
            alertaErro("inserir")
        }
    }

    atualizar(imformacoesEmpresa) {
        try {
            Connection conn = servicoConectar.conectar();
            PreparedStatement empresa = conn.prepareStatement(
                    montarQueryBuscarPorCnpj(imformacoesEmpresa.cnpj),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            empresa.setString(1, imformacoesEmpresa.cnpj);
            ResultSet res = empresa.executeQuery();

            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            if(qtd >0){
                String ATUALIZAR = "UPDATE linlketinder.empresa " +
                        "SET cnpj_empresa=?, nome_empresa=?, email_empresa=?,\n" +
                        "senha_empresa=?, telefone_empresa=?, cep_empresa=?,\n" +
                        "estado_empresa=?, pais_empresa=?, descricao_empresa=?" +
                        "WHERE cnpj_empresa=?";

                salvarInformaçoes(ATUALIZAR,imformacoesEmpresa)
            }
        } catch (Exception e) {
           alertaErro("atualizar")
        }
    }

    deletar(String cnpj_empresa) {
        String DELETAR = "DELETE FROM linlketinder.empresa WHERE cnpj_empresa=?"
        try {
            Connection conn = servicoConectar.conectar();
            PreparedStatement empresa = conn.prepareStatement(
                    montarQueryBuscarPorCnpj(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );

            empresa.setString(1, cnpj_empresa);
            ResultSet res = empresa.executeQuery();
            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            if (qtd > 0) {
                PreparedStatement del = conn.prepareStatement(DELETAR);
                del.setString(1, cnpj_empresa);
                del.executeUpdate();
                del.close();
                servicoConectar.(conn);
                System.out.println("Deletou");
            } else {
                System.out.println("não deletou");
            }

        } catch (Exception exeption) {
           alertaErro("deletar")
        }
    }
}
