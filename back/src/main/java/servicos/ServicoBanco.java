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
        System.out.println("Informe o cpf");
        String cpf_candidato = scanner.nextLine();

        System.out.println("Informe o nome ");
        String nome_candidato = scanner.nextLine();

        System.out.println("Informe o email ");
        String email_candidato = scanner.nextLine();

        System.out.println("Informe a senha ");
        String senha_candidato = scanner.nextLine();

        System.out.println("Informe o telefone ");
        String telefone_candidato = scanner.nextLine();

        System.out.println("Informe o cep ");
        String cep_candidato = scanner.nextLine();

        System.out.println("Informe o estado ");
        String estado_candidato = scanner.nextLine();

        System.out.println("Informe a descricao");
        String descricao_candidato = scanner.nextLine();

        System.out.println("Informe a idade ");
        int idade_candidato = scanner.nextInt();

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
            Connection conn = conectar();
            PreparedStatement salvar = conn.prepareStatement(INSERIR);

            salvar.setString(1, cpf_candidato);
            salvar.setString(2, nome_candidato);
            salvar.setString(3, email_candidato);
            salvar.setString(4, senha_candidato);
            salvar.setString(5, telefone_candidato);
            salvar.setString(6,cep_candidato);
            salvar.setString(7, estado_candidato);
            salvar.setInt(8, idade_candidato);
            salvar.setString(9, descricao_candidato);

            salvar.executeUpdate();
            salvar.close();
            desconectar(conn);
            System.out.println("O candidato " + nome_candidato + " foi inserido com sucesso");
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro a salvar");
        }
    }

    public static void atualizar() {
        System.out.println("Imforme código do candidato: ");
        int cpf_candidato = Integer.parseInt(scanner.nextLine());

        String BUSCAR_POR_ID = "SELECT * FROM linlketinder.candidato WHERE cpf_candidato=?";

        try {
            Connection conn = conectar();
            PreparedStatement candidato = conn.prepareStatement(
                    BUSCAR_POR_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            candidato.setInt(1,cpf_candidato);
            ResultSet res = candidato.executeQuery();

            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            if(qtd > 0) {
                System.out.println("Informe o nome do produto: ");
                String nome = scanner.nextLine();

                String ATUALIZAR = "UPDATE  SET nome=? linlketinder.candidato cpf_candidato=?";
                PreparedStatement upd = conn.prepareStatement(ATUALIZAR);

                upd.setInt(1, cpf_candidato);
                upd.setString(2, nome);

                upd.executeUpdate();
                upd.close();
                desconectar(conn);
                System.out.println("O produto foi atualizando com sucesso");
            } else {
                System.out.println("Não existe produto com o id informando");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Não foi possivel atualizar produto.");
            System.exit(-42);
        }
    }
    public static void menu(){
        System.out.println(" 1-listas 2-inserir 3-atualizar 4- deletar");
        int opcao = Integer.parseInt((scanner.nextLine()));
        if(opcao == 1) {
            listar();
        } else if (opcao == 2) {
            inserir();
        } else if (opcao == 3) {
            atualizar();
//        } else if (opcao == 4) {
//            deletar();
        } else {
            System.out.println("Opção inválida");
        }
    }
}
