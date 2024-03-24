package servicos

import database.ServicoConectarBanco
import modelos.PessoaFisica

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class ServicoCandidato {
    private ServicoConectarBanco servicoConectar
    private ServicoCandidatoCompetencia servicoCompetencia

    ServicoCandidato(){
        servicoCompetencia = new ServicoCandidatoCompetencia()
        servicoConectar = new ServicoConectarBanco()
    }

    String verificacaoParalogin() {
        return "SELECT c.cpf_candidato, " +
                "c.nome_candidato, " +
                "c.email_candidato, \n" +
                "c.telefone_candidato, " +
                "c.cep_candidato, " +
                "c.descricao_candidato\n " +
                "FROM linlketinder.candidato As c\n " +
                "WHERE email_candidato=? AND senha_candidato=?"
    }

    PessoaFisica entradaCandidato(String email_candidato, String senha_candidato) {
        try {
            Connection conexao = servicoConectar.conectar();
            PreparedStatement candidato = conexao.prepareStatement(
                    verificacaoParalogin(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            candidato.setString(1, email_candidato)
            candidato.setString(2, senha_candidato)
            ResultSet res = candidato.executeQuery()

            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            if (qtd > 0) {
                while (res.next()) {
                    PessoaFisica c = new PessoaFisica(
                            res.getInt(1),
                            res.getString(2),
                            res.getString(3),
                            res.getString(4),
                            res.getString(5),
                            res.getString(6),
                            res.getString(7)
                    )
                    c.setCompetencias(
                            servicoCompetencia.buscarCompetencia(c.cpf)
                    )
                    return c
                }
            }
        } catch (Exception exception){
            exception.printStackTrace();
            System.err.println("Erro em entrar");
            System.exit(-42);
        }
        return null
    }

     boolean inserir(PessoaFisica candidato){

        String INSERIR = "INSERT INTO linlketinder.Candidato(cpf_candidato,\n" +
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

            salvar.setString(1, candidato.getCpf());
            salvar.setString(2, candidato.getNome());
            salvar.setString(3, candidato.getEmail());
            salvar.setString(4, candidato.getSenha());
            salvar.setString(5, candidato.getTelefone());
            salvar.setString(6, candidato.getCep());
            salvar.setString(7, candidato.getEstado());
            salvar.setInt(8, candidato.getIdade());
            salvar.setString(9, candidato.getDescricao());

            salvar.executeUpdate();
            salvar.close();
            servicoConectar.desconectar(conn);
            return true
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro a salvar");
        }
         return false
    }
}