package servicos

import database.ServicoConectarBanco
import modelos.PessoaFisica

import java.sql.Connection
import java.sql.PreparedStatement

class ServicoCandidato {
    def servicoConectar = new ServicoConectarBanco()

     void inserir(PessoaFisica candidato){

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
            //salvar.setString(8, candidato.getIdade());
            salvar.setString(9, candidato.getDescricao());

            salvar.executeUpdate();
            salvar.close();
            servicoConectar.desconectar(conn);
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro a salvar");
        }
    }
}

