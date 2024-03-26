package servicos

import database.ServicoConectarBanco
import modelos.Competencia

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class ServicoEmpresaCompetencia {
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


}
