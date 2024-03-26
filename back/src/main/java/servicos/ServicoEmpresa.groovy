package servicos

import database.ServicoConectarBanco
import modelos.PessoaJuridica

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class ServicoEmpresa {
    private ServicoConectarBanco servicoConectar
    private ServicoEmpresaCompetencia servicoCompetencia
    private ServicoEmpresaCompetencia servicoEmpresaCompetencia

    ServicoEmpresa() {
        servicoConectar = new ServicoConectarBanco()
        servicoCompetencia = new ServicoEmpresaCompetencia()
        servicoEmpresaCompetencia = new ServicoEmpresaCompetencia()
    }

    String verificacaoParaLogin(){
        return "SELECT \n" +
                "e.cnpj_empresa," +
                "e.nome_empresa," +
                "e.email_empresa," +
                "e.telefone_empresa," +
                "e.cep_empresa,\n" +
                "e.descricao_empresa" +
                " FROM " +
                "linlketinder.empresa AS e " +
                "WHERE e.email_empresa = ? AND e.senha_empresa = ?"
    }

    String montarQueryBuscarTodosMatch() {
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
        return "SELECT cnpj_empresa FROM linlketinder.empresa WHERE cnpj_empresa=?";
    }

    void salvarInformacoes(String comado, PessoaJuridica empresa){

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

    PessoaJuridica entradaEmpresa(String email_empresa, String senha_empresa){
        try {
            Connection conexao = servicoConectar.conectar();
            PreparedStatement empresa = conexao.prepareStatement(
                    verificacaoParaLogin(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            empresa.setString(1, email_empresa)
            empresa.setString(2, senha_empresa)
            ResultSet res = empresa.executeQuery()

            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            if (qtd > 0) {
                while (res.next()) {
                    PessoaJuridica e = new PessoaJuridica(
                            res.getString(1),
                            res.getString(2),
                            res.getString(3),
                            res.getString(4),
                            res.getString(5),
                            res.getString(6)
                    )
                    e.setCompetencias(
                            servicoCompetencia.listarCompetencia(e.cnpj)
                    )
                    return e
                }
            }
        } catch (Exception exception){
            exception.printStackTrace();
            System.err.println("Erro em entrar");
            System.exit(-42);
        }
        return null
    }

    PessoaJuridica buscarLista() {
        try {
            Connection conexao = servicoConectar.conectar();
            PreparedStatement empresa = conexao.prepareStatement(
                    montarQueryBuscarTodosMatch(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            ResultSet res = empresa.executeQuery();

            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            def empresas = []
            if (qtd > 0) {
                while (res.next()) {
                    PessoaJuridica e = new PessoaJuridica(
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
        }catch(Exception exception){
            exception.printStackTrace();
            System.err.println("Erro em listar");
            System.exit(-42);
        }
    }

    boolean inserir(PessoaJuridica empresa){
        String INSERIR = "INSERT INTO linlketinder.empresa(cnpj_empresa, nome_empresa, email_empresa,\n" +
                "                    senha_empresa, telefone_empresa, cep_empresa,\n" +
                "                    estado_empresa, pais_empresa, descricao_empresa) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            salvarInformacoes(INSERIR,empresa)
            return true
        } catch (Exception exception) {
            exception.printStackTrace();
            System.err.println("Erro em inserir");
            System.exit(-42);
        }
        return false
    }

    boolean atualizar(PessoaJuridica empresa) {
        try {
            Connection conn = servicoConectar.conectar()
            PreparedStatement empresas = conn.prepareStatement(
                    montarQueryBuscarPorCnpj(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            )
            empresas.setString(1, empresa.cnpj);
            ResultSet res = empresas.executeQuery();

            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            if(qtd > 0){
                String ATUALIZAR = "UPDATE linlketinder.empresa " +
                        "SET nome_empresa=?, email_empresa=?,\n" +
                        "senha_empresa=?, telefone_empresa=?, cep_empresa=?,\n" +
                        "estado_empresa=?, pais_empresa=?, descricao_empresa=? " +
                        "WHERE cnpj_empresa=?"
                PreparedStatement salvar = conn.prepareStatement(ATUALIZAR);

                salvar.setString(1, empresa.getNome())
                salvar.setString(2, empresa.getEmail())
                salvar.setString(3, empresa.getSenha())
                salvar.setString(4, empresa.getTelefone())
                salvar.setString(5, empresa.getCep())
                salvar.setString(6, empresa.getEstado())
                salvar.setString(7, empresa.getPais())
                salvar.setString(8, empresa.getDescricao())
                salvar.setString(9, empresa.getCnpj())

                salvar.executeUpdate();
                salvar.close();
                servicoConectar.desconectar(conn);
                return true
            }
        } catch (Exception exeption) {
            exeption.printStackTrace()
            System.err.println("Erro em atualizar")
            System.exit(-42);
        }
        return false
    }

    void deletar(String cnpj_empresa) {
        String DELETAR = "DELETE FROM linlketinder.empresa WHERE cnpj_empresa=?"
        try {
            Connection conn = servicoConectar.conectar();
            PreparedStatement empresa = conn.prepareStatement(
                    montarQueryBuscarPorCnpj(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            )

            empresa.setString(1, cnpj_empresa);
            ResultSet res = empresa.executeQuery();
            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            if (qtd > 0) {
                PreparedStatement del = conn.prepareStatement(DELETAR)
                del.setString(1, cnpj_empresa)
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
}
