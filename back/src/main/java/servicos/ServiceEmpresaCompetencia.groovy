package servicos

import database.ServicoConectarBanco
import modelos.Competencia
import modelos.Empresa

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class ServiceEmpresaCompetencia {
    def servicoConectar = new ServicoConectarBanco()

    String montarQueryBuscarPorCnpj(){
        return "select \n" +
                "\tc.id_competencia,\n" +
                "\tc.descricao_competencia\n" +
                "from \n" +
                "\tlinlketinder.empresa_competencia AS ec\n" +
                "\tjoin linlketinder.competencia AS c on c.id_competencia = ec.id_competencia\n" +
                "where\n" +
                "\tec.cnpj_empresa = ?"
    }

     ArrayList<Competencia> buscarCompetencia(String cnpj_empresa){
        try {
            Connection conexao = servicoConectar.conectar();
            PreparedStatement compentenciasQuery = conexao.prepareStatement(
                    montarQueryBuscarPorCnpj(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );

            compentenciasQuery.setString(1, cnpj_empresa);
            ResultSet res = compentenciasQuery.executeQuery();

            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            def competencias = []
            if (qtd > 0){
                while (res.next()) {
                    Competencia c = Competencia (
                            res.getInt(1),
                            res.getString(2)
                    )
                    competencias.add(c)
                }
            }
            return competencias
        } catch (Exception exception) {
            exception.printStackTrace();
            System.err.println("Erro ao buscar competencia")
            System.exit(-42)
        }
    }

}
