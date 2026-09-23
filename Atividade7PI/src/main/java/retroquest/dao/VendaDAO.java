package retroquest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import retroquest.model.ItemVenda;
import retroquest.model.Venda;
import java.util.ArrayList;
import java.util.List;
import retroquest.model.Cliente;

public class VendaDAO {

    public void inserir(Venda venda, ItemVenda item, int novoEstoque) throws SQLException {
        String sqlVenda = "INSERT INTO venda (data_venda, valor_total, cliente_id) "
                + "VALUES (NOW(), ?, ?)";

        String sqlItem = "INSERT INTO item_venda "
                + "(quantidade, preco_unitario, venda_id, jogo_id) "
                + "VALUES (?, ?, ?, ?)";

        String sqlEstoque = "UPDATE jogo SET quantidade_estoque = ? WHERE id = ?";

        Connection conn = Conexao.conectar();

        try {
            conn.setAutoCommit(false);

            int vendaId;

            try ( PreparedStatement stmtVenda = conn.prepareStatement(
                    sqlVenda, PreparedStatement.RETURN_GENERATED_KEYS)) {

                stmtVenda.setDouble(1, venda.getValorTotal());
                stmtVenda.setInt(2, venda.getCliente().getId());

                stmtVenda.executeUpdate();

                try ( ResultSet rs = stmtVenda.getGeneratedKeys()) {
                    if (rs.next()) {
                        vendaId = rs.getInt(1);
                    } else {
                        throw new SQLException("Não foi possível obter o ID da venda.");
                    }
                }
            }

            try ( PreparedStatement stmtItem = conn.prepareStatement(sqlItem)) {

                stmtItem.setInt(1, item.getQuantidade());
                stmtItem.setDouble(2, item.getPrecoUnitario());
                stmtItem.setInt(3, vendaId);
                stmtItem.setInt(4, item.getJogo().getId());

                stmtItem.executeUpdate();
            }

            try ( PreparedStatement stmtEstoque = conn.prepareStatement(sqlEstoque)) {
                stmtEstoque.setInt(1, novoEstoque);
                stmtEstoque.setInt(2, item.getJogo().getId());
                stmtEstoque.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            conn.rollback();
            throw e;

        } finally {
            conn.close();
        }
    }

    public List<Venda> listar() throws SQLException {
        List<Venda> vendas = new ArrayList<>();

        String sql = "SELECT v.id, v.data_venda, v.valor_total, "
                + "c.id AS cliente_id, c.nome "
                + "FROM venda v "
                + "JOIN cliente c ON v.cliente_id = c.id";

        try ( Connection conn = Conexao.conectar();  PreparedStatement stmt = conn.prepareStatement(sql);  ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("cliente_id"));
                cliente.setNome(rs.getString("nome"));

                Venda venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setDataVenda(rs.getTimestamp("data_venda"));
                venda.setValorTotal(rs.getDouble("valor_total"));
                venda.setCliente(cliente);

                vendas.add(venda);
            }
        }

        return vendas;
    }

}
