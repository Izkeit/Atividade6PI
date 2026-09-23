package retroquest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import retroquest.model.Jogo;

public class JogoDAO {

    public void inserir(Jogo jogo) throws SQLException {
        String sql = "INSERT INTO jogo "
                + "(titulo, ano_lancamento, genero, preco_compra, preco_venda, quantidade_estoque) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try ( Connection conn = Conexao.conectar();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, jogo.getTitulo());
            stmt.setInt(2, jogo.getAnoLancamento());
            stmt.setString(3, jogo.getGenero());
            stmt.setDouble(4, jogo.getPrecoCompra());
            stmt.setDouble(5, jogo.getPrecoVenda());
            stmt.setInt(6, jogo.getQuantidadeEstoque());

            stmt.executeUpdate();
        }
    }

    public List<Jogo> listar() throws SQLException {
        List<Jogo> jogos = new ArrayList<>();

        String sql = "SELECT id, titulo, ano_lancamento, genero, "
                + "preco_compra, preco_venda, quantidade_estoque FROM jogo";

        try ( Connection conn = Conexao.conectar();  PreparedStatement stmt = conn.prepareStatement(sql);  ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Jogo jogo = new Jogo();

                jogo.setId(rs.getInt("id"));
                jogo.setTitulo(rs.getString("titulo"));
                jogo.setAnoLancamento(rs.getInt("ano_lancamento"));
                jogo.setGenero(rs.getString("genero"));
                jogo.setPrecoCompra(rs.getDouble("preco_compra"));
                jogo.setPrecoVenda(rs.getDouble("preco_venda"));
                jogo.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));

                jogos.add(jogo);
            }
        }

        return jogos;
    }

    public int buscarIdPorTitulo(String titulo) throws SQLException {
        String sql = "SELECT id FROM jogo WHERE titulo = ?";

        try ( Connection conn = Conexao.conectar();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, titulo);

            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }

        return -1;
    }

    public int buscarEstoquePorId(int id) throws SQLException {
        String sql = "SELECT quantidade_estoque FROM jogo WHERE id = ?";

        try ( Connection conn = Conexao.conectar();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quantidade_estoque");
                }
            }
        }

        return 0;
    }

    public void atualizarEstoque(int id, int novoEstoque) throws SQLException {
        String sql = "UPDATE jogo SET quantidade_estoque = ? WHERE id = ?";

        try ( Connection conn = Conexao.conectar();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, novoEstoque);
            stmt.setInt(2, id);

            stmt.executeUpdate();
        }
    }

    public double buscarPrecoPorTitulo(String titulo) throws SQLException {
        String sql = "SELECT preco_venda FROM jogo WHERE titulo = ?";

        try ( Connection conn = Conexao.conectar();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, titulo);

            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("preco_venda");
                }
            }
        }

        return 0;
    }
}
