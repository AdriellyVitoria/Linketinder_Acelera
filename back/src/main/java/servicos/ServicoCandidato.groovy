package servicos

import java.sql.Connection
import java.sql.PreparedStatement

class ServicoCandidato {
    static servicoConectar = new ServicoConectarBanco()

    static void inserir(cpf_candidato, nome_candidato, email_candidato,
                        senha_candidato, telefone_candidato, cep_candidato,
                        estado_candidato, descricao_candidato, idade_candidato){

        String INSERIR = "INSERT INTO linlketinder.candidato(cpf_candidato,\n" +
                "\tnome_candidato, \n" +
                "\temail_candidato, \n" +
                "\tsenha_candidato,\n" +
                "\ttelefone_candidato, \n" +
                "\tcep_candidato, \n" +
                "\testado_candidato,\n" +
                "\tidade_candidato,\n" +
                "\tdescricao_candidato) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = servicoConectar.conectar();
            PreparedStatement salvar = conn.prepareStatement(INSERIR);

            salvar.setString(1, cpf_candidato);
            salvar.setString(2, nome_candidato);
            salvar.setString(3, email_candidato);
            salvar.setString(4, senha_candidato);
            salvar.setString(5, telefone_candidato);
            salvar.setString(6,cep_candidato);
            salvar.setString(7, estado_candidato);
            salvar.setString(8, idade_candidato);
            salvar.setString(9, descricao_candidato);

            salvar.executeUpdate();
            salvar.close();
            servicoConectar.desconectar(conn);
            System.out.println("O candidato " + nome_candidato + " foi inserido com sucesso");
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro a salvar");
        }
    }
}
