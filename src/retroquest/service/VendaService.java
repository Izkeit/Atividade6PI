package retroquest.service;

import retroquest.dao.JogoDAO;
import retroquest.dao.VendaDAO;
import java.sql.SQLException;
import retroquest.model.ItemVenda;
import retroquest.model.Venda;

public class VendaService {

    private final VendaDAO vendaDAO;
    private final JogoDAO jogoDAO;

    public VendaService() {
        this.vendaDAO = new VendaDAO();
        this.jogoDAO = new JogoDAO();
    }

    public void realizarVenda(Venda venda, ItemVenda item) throws SQLException {

        if (venda == null || venda.getCliente() == null || venda.getCliente().getId() <= 0) {
            throw new IllegalArgumentException("O cliente da venda é obrigatório.");
        }

        if (item == null || item.getJogo() == null || item.getJogo().getId() <= 0) {
            throw new IllegalArgumentException("O jogo da venda é obrigatório.");
        }

        if (item.getQuantidade() <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        int estoqueAtual = jogoDAO.buscarEstoquePorId(item.getJogo().getId());

        if (estoqueAtual < item.getQuantidade()) {
            throw new IllegalArgumentException("Estoque insuficiente para realizar a venda.");
        }

        double total = item.getQuantidade() * item.getPrecoUnitario();
        venda.setValorTotal(total);

        int novoEstoque = estoqueAtual - item.getQuantidade();

        vendaDAO.inserir(venda, item, novoEstoque);
    }
}
