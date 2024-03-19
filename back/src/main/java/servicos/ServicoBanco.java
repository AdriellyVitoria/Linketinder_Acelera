package servicos;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class ServicoBanco {
    public static void main(String[] args) {
        menu();
    }
    static Scanner scanner = new Scanner(System.in);

    public static Connection conectar() {
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

    public static void desconectar(Connection conn){
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static String buscarTodos() {
        String BUSCAR_TODOS = "SELECT * FROM linlketinder.candidato";
        return BUSCAR_TODOS;
    }

    public static void listar() {
        try {
            Connection conexao = conectar();
            PreparedStatement candidato = conexao.prepareStatement(
                    buscarTodos(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            ResultSet res = candidato.executeQuery();

            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            if (qtd > 0) {
                System.out.println("Listando produtos");
                System.out.println("--------------------------------");
                while (res.next()) {
                    System.out.println("Nome " + res.getString(2));
                    System.out.println("Email " + res.getString(3));
                    System.out.println("Telefone " + res.getString(5));
                    System.out.println("Estado " + res.getString(7));
                    System.out.println("idade " + res.getString(8));
                    System.out.println("Descricao " + res.getString(9));
                    System.out.println("--------------------------------");
                }
            } else {
                System.out.println("Não existe produtos cadastrados.");
            }
        }catch(Exception e){
            e.printStackTrace();
            System.err.println("Erro buscando todos os produtos");
            System.exit(-42);
        }
    }

    public static void inserir(){
        System.out.println("Informe o nome do produtos: ");
        String nome = scanner.nextLine();

        System.out.println("Informe o preco do produtos: ");
        int idade = scanner.nextInt();

        System.out.println("Informe o estoque do produtos: ");
        String descricao = scanner.nextLine();

        String INSERIR = "INSERT INTO linlketinder.candidato(nome, idade, descricao) VALUES (?, ?, ?)";

        try {
            Connection conn = conectar();
            PreparedStatement salvar = conn.prepareStatement(INSERIR);

            salvar.setString(2, nome);
            salvar.setInt(3, idade);
            salvar.setString(9, descricao);

            salvar.executeUpdate();
            salvar.close();
            desconectar(conn);
            System.out.println("O candidato " + nome + " foi inserido com sucesso");
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro a salvar");
        }
    }
    public static void menu(){
        System.out.println(" 1-listas 2-inserir 3-atualizar 4- deletar");
        int opcao = Integer.parseInt((scanner.nextLine()));
        if(opcao == 1) {
            listar();
        } else if (opcao == 2) {
            inserir();
//        } else if (opcao == 3) {
//            atualizar();
//        } else if (opcao == 4) {
//            deletar();
        } else {
            System.out.println("Opção inválida");
        }
    }
}
