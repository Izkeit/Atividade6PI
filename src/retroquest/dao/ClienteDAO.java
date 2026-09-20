package retroquest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import retroquest.model.Cliente;

public class ClienteDAO {

    public void inserir(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (nome, email, telefone) VALUES (?, ?, ?)";

        try ( Connection conn = Conexao.conectar();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefone());

            stmt.executeUpdate();
        }
    }

    public List<Cliente> listar() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();

        String sql = "SELECT id, nome, email, telefone FROM cliente";

        try ( Connection conn = Conexao.conectar();  PreparedStatement stmt = conn.prepareStatement(sql);  ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();

                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("telefone"));

                clientes.add(cliente);
            }
        }

        return clientes;
    }

    public int buscarIdPorNome(String nome) throws SQLException {
        String sql = "SELECT id FROM cliente WHERE nome = ?";

        try ( Connection conn = Conexao.conectar();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);

            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }

        return -1;
    }

    public boolean deletar(int id) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id = ?";

        try ( Connection conn = Conexao.conectar();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        }
    }

}
