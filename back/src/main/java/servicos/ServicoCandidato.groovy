package servicos

import database.ServicoConectarBanco
import modelos.PessoaFisica
import modelos.PessoaJuridica

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
        return "SELECT c.cpf_candidato," +
                "c.nome_candidato, " +
                "c.email_candidato, " +
                "c.telefone_candidato, " +
                "c.cep_candidato, " +
                "c.descricao_candidato, " +
                "c.idade_candidato, " +
                "c.estado_candidato " +
                "FROM linlketinder.candidato As c " +
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
                            res.getString(1),
                            res.getString(2),
                            res.getString(3),
                            res.getString(4),
                            res.getString(5),
                            res.getString(6),
                            res.getInt(7),
                            res.getString(8),
                    )
                    c.setCompetencias(
                            servicoCompetencia.listarCompetencia(c.cpf)
                    )
                    return c
                }
            }
        } catch (Exception exception){
            System.err.println("Erro em entrar");

        }
        return null
    }

    boolean inserir(PessoaFisica candidato){

        String INSERIR = "INSERT INTO linlketinder.Candidato(" +
            "cpf_candidato," +
            "\tnome_candidato, " +
            "\temail_candidato, " +
            "\tsenha_candidato," +
            "\ttelefone_candidato, " +
            "\tcep_candidato, " +
            "\testado_candidato," +
            "\tidade_candidato," +
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
            System.err.println ("ERRO AO CADASTRAR")
            if (e.message.contains("key")) {
                System.err.println("CPF já cadastrado!");
            }
            if (e.message.contains("email")) {
                System.err.println("Email já cadastrado!");
            }
            return false
        }
    }

    boolean atualizar(PessoaFisica candidato){
        try {
            Connection conn = servicoConectar.conectar()
            String ATUALIZAR = "UPDATE linlketinder.candidato \n" +
                    "SET nome_candidato =?, email_candidato =?, " +
                    "senha_candidato =?, telefone_candidato =?," +
                    " estado_candidato =?, cep_candidato =?,\n" +
                    "descricao_candidato =?, idade_candidato =?\n" +
                    "WHERE cpf_candidato= ?"
            PreparedStatement salvar = conn.prepareStatement(ATUALIZAR);

            salvar.setString(1, candidato.getNome())
            salvar.setString(2, candidato.getEmail())
            salvar.setString(3, candidato.getSenha())
            salvar.setString(4, candidato.getTelefone())
            salvar.setString(5, candidato.getEstado())
            salvar.setString(6, candidato.getCep())
            salvar.setString(7,candidato.getDescricao())
            salvar.setInt(8,candidato.getIdade())
            salvar.setString(9, candidato.getCpf())

            salvar.executeUpdate();
            salvar.close();
            servicoConectar.desconectar(conn);
            return true

        } catch (Exception exeption) {
            exeption.printStackTrace()
            System.err.println("Erro em atualizarDescricao")
            System.exit(-42);
        }
        return false
    }

    boolean deletar(String cpf_candidato){
        String DELETAR = "DELETE FROM linlketinder.candidato WHERE cpf_candidato=?"
        try {
            Connection conn = servicoConectar.conectar();
            PreparedStatement del = conn.prepareStatement(DELETAR)
            del.setString(1, cpf_candidato)
            del.executeUpdate()
            del.close()
            servicoConectar.desconectar(conn)
        } catch (Exception exception) {
            exception.printStackTrace();
            System.err.println("Erro em deletarCompetenciaCandidato");
            System.exit(-42);
        }
    }


}