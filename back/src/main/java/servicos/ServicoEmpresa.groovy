package servicos

import database.ServicoConectarBanco
import modelos.PessoaJuridica

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class ServicoEmpresa {

    static servicoConectar = new ServicoConectarBanco()

    static String buscarTodos() {
        String BUSCAR_TODOS = "SELECT * FROM linlketinder.empresa"
        return BUSCAR_TODOS;
    }

    static String buscarPorId(String cnpj_empresa){
        String BUSCAR_POR_ID = "SELECT * FROM linlketinder.empresa WHERE cnpj_empresa=?";
        return BUSCAR_POR_ID
    }

    static salvarInformaçoes(String comado,empresa){

        Connection conn = servicoConectar.conectar()
        PreparedStatement salvar = conn.prepareStatement(comado);

        salvar.setString(1, empresa.cnpj);
        salvar.setString(2, empresa.nome);
        salvar.setString(3, empresa.email);
        salvar.setString(4, empresa.senha);
        salvar.setString(5, empresa.telefone);
        salvar.setString(6,empresa.cep);
        salvar.setString(7, empresa.estado);
        salvar.setString(8, empresa.pais);
        salvar.setString(9, empresa.descricao);

        salvar.executeUpdate();
        salvar.close();
        servicoConectar.desconectar(conn);
    }

    static listar() {
        try {
            Connection conexao = servicoConectar.conectar();
            PreparedStatement empresa = conexao.prepareStatement(
                    buscarTodos(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            ResultSet res = empresa.executeQuery();

            res.last();
            int qtd = res.getRow();
            res.beforeFirst();
            def lista = []
            if (qtd > 0) {
                System.out.println("Listando empresas");
                System.out.println("--------------------------------");
                while (res.next()) {
                    PessoaJuridica p = new PessoaJuridica(res.getString(1), )
                    lista.add(p)
                    System.out.println("Nome " + res.getString(2));
                    System.out.println("Email " + res.getString(3));
                    System.out.println("Telefone " + res.getString(5));
                    System.out.println("Estado " + res.getString(7));
                    System.out.println("País " + res.getString(8));
                    System.out.println("Descricao " + res.getString(9));
                    System.out.println("--------------------------------");
                }
                return lista
            } else {
                System.out.println("Não existe empresa cadastrados.");
            }
        }catch(Exception e){
            e.printStackTrace();
            System.err.println("Erro buscando todas as empresas");
            System.exit(-42);
        }
    }

    static inserir(empresa){

        String INSERIR = "INSERT INTO linlketinder.empresa(cnpj_empresa, nome_empresa, email_empresa,\n" +
                "                    senha_empresa, telefone_empresa, cep_empresa,\n" +
                "                    estado_empresa, pais_empresa, descricao_empresa) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            salvarInformaçoes(INSERIR,empresa)
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao salvar");
        }
    }

    static atualizar(imformacoesEmpresa) {
        try {
            Connection conn = servicoConectar.conectar();
            PreparedStatement empresa = conn.prepareStatement(
                    buscarPorId(imformacoesEmpresa.cnpj),
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
            } else {
                System.out.println("Não foi possivel atualizar empresa");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro na atualização");
            System.exit(-42);
        }
    }

    static deletar() {
        String DELETAR = "DELETE FROM linlketinder.empresa WHERE cnpj_empresa=? "
    }
}
