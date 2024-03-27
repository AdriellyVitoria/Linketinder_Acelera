//package servicos;
//
//import java.sql.*;
//import java.util.Properties;
//import java.util.Scanner;
//
//public class ServicoBanco {
//    public static void main(String[] args) {
//        menu();
//    }
//    static Scanner scanner = new Scanner(System.in);
//    static ServicoConectarBanco conectar = new ServicoConectarBanco();
//
//    public static String buscarTodos() {
//        String BUSCAR_TODOS = "SELECT * FROM linlketinder.Candidato";
//        return BUSCAR_TODOS;
//    }
//
//    public static void listar() {
//        try {
//            Connection conexao = conectar();
//            PreparedStatement Candidato = conexao.prepareStatement(
//                    buscarTodos(),
//                    ResultSet.TYPE_SCROLL_INSENSITIVE,
//                    ResultSet.CONCUR_READ_ONLY
//            );
//            ResultSet res = Candidato.executeQuery();
//
//            res.last();
//            int qtd = res.getRow();
//            res.beforeFirst();
//
//            if (qtd > 0) {
//
//                System.out.println("Listando produtos");
//                System.out.println("--------------------------------");
//                while (res.next()) {
//                    System.out.println("Nome " + res.getString(2));
//                    System.out.println("Email " + res.getString(3));
//                    System.out.println("Telefone " + res.getString(5));
//                    System.out.println("Estado " + res.getString(7));
//                    System.out.println("idade " + res.getString(8));
//                    System.out.println("Descricao " + res.getString(9));
//                    System.out.println("--------------------------------");
//                }
//            } else {
//                System.out.println("Não existe produtos cadastrados.");
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//            System.err.println("Erro buscando todos os produtos");
//            System.exit(-42);
//        }
//    }
//
//     static void inserir(){
//        System.out.println("Informe o cpf");
//        String cpf_candidato = scanner.nextLine();
//
//        System.out.println("Informe o nome ");
//        String nome_candidato = scanner.nextLine();
//
//        System.out.println("Informe o email ");
//        String email_candidato = scanner.nextLine();
//
//        System.out.println("Informe a senha ");
//        String senha_candidato = scanner.nextLine();
//
//        System.out.println("Informe o telefone ");
//        String telefone_candidato = scanner.nextLine();
//
//        System.out.println("Informe o cep ");
//        String cep_candidato = scanner.nextLine();
//
//        System.out.println("Informe o estado ");
//        String estado_candidato = scanner.nextLine();
//
//        System.out.println("Informe a descricao");
//        String descricao_candidato = scanner.nextLine();
//
//        System.out.println("Informe a idade ");
//        int idade_candidato = scanner.nextInt();
//
//        String INSERIR = "INSERT INTO linlketinder.Candidato(cpf_candidato,\n" +
//                "\tnome_candidato, \n" +
//                "\temail_candidato, \n" +
//                "\tsenha_candidato,\n" +
//                "\ttelefone_candidato, \n" +
//                "\tcep_candidato, \n" +
//                "\testado_candidato,\n" +
//                "\tidade_candidato,\n" +
//                "\tdescricao_candidato) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//        try {
//            Connection conn = conectar();
//            PreparedStatement salvar = conn.prepareStatement(INSERIR);
//
//            salvar.setString(1, cpf_candidato);
//            salvar.setString(2, nome_candidato);
//            salvar.setString(3, email_candidato);
//            salvar.setString(4, senha_candidato);
//            salvar.setString(5, telefone_candidato);
//            salvar.setString(6,cep_candidato);
//            salvar.setString(7, estado_candidato);
//            salvar.setInt(8, idade_candidato);
//            salvar.setString(9, descricao_candidato);
//
//            salvar.executeUpdate();
//            salvar.close();
//            desconectar(conn);
//            System.out.println("O Candidato " + nome_candidato + " foi inserido com sucesso");
//        }catch (Exception e) {
//            e.printStackTrace();
//            System.err.println("Erro a salvar");
//        }
////    }
//    public static void atualizarDescricao() {
//        System.out.println("Informe o cpf do Candidato para atualização: ");
//        String cpf_candidato = scanner.nextLine();
//
//        String BUSCAR_POR_ID = "SELECT * FROM linlketinder.Candidato WHERE cpf_candidato=?";
//
//        try {
//            Connection conn = conectar();
//            PreparedStatement Candidato = conn.prepareStatement(
//                    BUSCAR_POR_ID,
//                    ResultSet.TYPE_SCROLL_INSENSITIVE,
//                    ResultSet.CONCUR_READ_ONLY
//            );
//            Candidato.setString(1, cpf_candidato);
//            ResultSet res = Candidato.executeQuery();
//
//            res.last();
//            int qtd = res.getRow();
//            res.beforeFirst();
//
//            if(qtd >0){
//                System.out.println("Informe o nome do produto para alteração");
//                String nome_candidado = scanner.nextLine();
//
//                String ATUALIZAR = "UPDATE linlketinder.Candidato SET nome_candidato=? WHERE cpf_candidato=?";
//                PreparedStatement upd = conn.prepareStatement(ATUALIZAR);
//
//                upd.setString(1, nome_candidado);
//                upd.setString(2, cpf_candidato);
//
//                upd.executeUpdate();
//                upd.close();
//                desconectar(conn);
//                System.out.println("O Candidato foi atualizando com sucesso");
//            } else {
//                System.out.println("Não deu bom");
//            }
//        } catch (Exception e) {
//           e.printStackTrace();
//           System.err.println("Não foi possivel atualizarDescricao Candidato");
//           System.exit(-42);
//        }
//    }
//
//    public static void deletar() {
//        String DELETAR = "DELETE FROM linlketinder.Candidato WHERE cpf_candidato=? ";
//        String BUSCAR_POR_ID = "SELECT * FROM linlketinder.Candidato WHERE cpf_candidato=?";
//
//        System.out.println("o cpf para deletar");
//        String cpf_candidato = scanner.nextLine();
//
//        try {
//            Connection conn = conectar();
//            PreparedStatement Candidato = conn.prepareStatement(
//                    BUSCAR_POR_ID,
//                    ResultSet.TYPE_SCROLL_INSENSITIVE,
//                    ResultSet.CONCUR_READ_ONLY
//            );
//
//            Candidato.setString(1, cpf_candidato);
//            ResultSet res = Candidato.executeQuery();
//            res.last();
//            int qtd = res.getRow();
//            res.beforeFirst();
//
//            if (qtd > 0) {
//                PreparedStatement del = conn.prepareStatement(DELETAR);
//                del.setString(1, cpf_candidato);
//                del.executeUpdate();
//                del.close();
//                desconectar(conn);
//                System.out.println("Deletou");
//            } else {
//                System.out.println("não deletou");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Erro ao deletar");
//            System.exit(-42);
//        }
//    }
//    public static void menu(){
//        System.out.println(" 1-listas 2-inserir 3-atualizarDescricao 4- deletar");
//        int opcao = Integer.parseInt((scanner.nextLine()));
//        if(opcao == 1) {
//            listar();
//        } else if (opcao == 2) {
//            inserir();
//        } else if (opcao == 3) {
//            atualizarDescricao();
//        } else if (opcao == 4) {
//            deletar();
//        } else {
//            System.out.println("Opção inválida");
//        }
//    }
//}
