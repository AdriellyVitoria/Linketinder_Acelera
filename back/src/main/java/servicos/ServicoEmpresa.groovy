package servicos

import java.sql.Connection
import java.sql.PreparedStatement

class ServicoEmpresa {

    static servicoConectar = new ServicoConectarBanco()

    static void inserir(cnpj_empresa, nome_empresa, email_empresa,
                        senha_empresa, telefone_empresa, cep_empresa,
                        estado_empresa, pais_empresa, descricao_empresa){

        String INSERIR = "INSERT INTO linlketinder.empresa(cnpj_empresa, nome_empresa, email_empresa,\n" +
                "                    senha_empresa, telefone_empresa, cep_empresa,\n" +
                "                    estado_empresa, pais_empresa, descricao_empresa) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = servicoConectar.conectar()
            PreparedStatement salvar = conn.prepareStatement(INSERIR);

            salvar.setString(1, cnpj_empresa);
            salvar.setString(2, nome_empresa);
            salvar.setString(3, email_empresa);
            salvar.setString(4, senha_empresa);
            salvar.setString(5, telefone_empresa);
            salvar.setString(6,cep_empresa);
            salvar.setString(7, estado_empresa);
            salvar.setString(8, pais_empresa);
            salvar.setString(9, descricao_empresa);

            salvar.executeUpdate();
            salvar.close();
            servicoConectar.desconectar(conn);
            println("Empresa " + nome_empresa + " foi inserido com sucesso")
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao salvar");
        }
    }
}
