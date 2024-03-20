package database

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class ServicoConectarBanco {
    static Connection conectar() {
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "123456");
        props.setProperty("ssl", "false");

        String URL_SERVIDOR = "jdbc:postgresql://localhost:5432/linketinder";
        try {
            return DriverManager.getConnection(URL_SERVIDOR, props);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof ClassNotFoundException){
                System.err.println("Verifique o driver de conexão");
            }else {
                System.err.println("Verifique se o servidor está ativo");
            };
            System.exit(-42);
            return null;
        }
    }

    static void desconectar(Connection conn){
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
