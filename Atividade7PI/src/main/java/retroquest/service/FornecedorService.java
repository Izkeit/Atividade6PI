package retroquest.service;

import java.sql.SQLException;
import retroquest.dao.FornecedorDAO;
import retroquest.model.Fornecedor;

public class FornecedorService {

    private final FornecedorDAO fornecedorDAO;

    public FornecedorService() {
        this.fornecedorDAO = new FornecedorDAO();
    }

    public void cadastrar(Fornecedor fornecedor) throws SQLException {
        if (fornecedor.getNome() == null || fornecedor.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do fornecedor é obrigatório.");
        }

        if (fornecedor.getContato() == null || fornecedor.getContato().trim().isEmpty()) {
            throw new IllegalArgumentException("O contato do fornecedor é obrigatório.");
        }

        fornecedorDAO.inserir(fornecedor);
    }
}