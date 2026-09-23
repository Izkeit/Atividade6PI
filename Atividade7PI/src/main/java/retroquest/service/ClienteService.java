package retroquest.service;

import java.sql.SQLException;
import retroquest.dao.ClienteDAO;
import retroquest.model.Cliente;

public class ClienteService {

    private final ClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }

    public void cadastrar(Cliente cliente) throws SQLException {
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }

        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("O e-mail do cliente é obrigatório.");
        }

        clienteDAO.inserir(cliente);
    }
}
