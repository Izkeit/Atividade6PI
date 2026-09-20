package retroquest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import retroquest.model.Fornecedor;

public class FornecedorDAO {

    public void inserir(Fornecedor fornecedor) throws SQLException {
        String sql = "INSERT INTO fornecedor (empresa, nome, telefone) VALUES (?, ?, ?)";

        try ( Connection conn = Conexao.conectar();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fornecedor.getContato());
            stmt.setString(2, fornecedor.getNome());
            stmt.setString(3, fornecedor.getTelefone());

            stmt.executeUpdate();
        }
    }

    public List<Fornecedor> listar() throws SQLException {
        List<Fornecedor> fornecedores = new ArrayList<>();

        String sql = "SELECT id, empresa, nome, telefone FROM fornecedor";

        try ( Connection conn = Conexao.conectar();  PreparedStatement stmt = conn.prepareStatement(sql);  ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Fornecedor fornecedor = new Fornecedor();

                fornecedor.setId(rs.getInt("id"));
                fornecedor.setContato(rs.getString("empresa"));
                fornecedor.setNome(rs.getString("nome"));
                fornecedor.setTelefone(rs.getString("telefone"));

                fornecedores.add(fornecedor);
            }
        }

        return fornecedores;
    }

    public boolean deletar(int id) throws SQLException {
        String sql = "DELETE FROM fornecedor WHERE id = ?";

        try ( Connection conn = Conexao.conectar();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        }
    }
}
