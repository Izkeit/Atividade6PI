package retroquest.service;

import java.sql.SQLException;
import retroquest.dao.JogoDAO;
import retroquest.model.Jogo;

public class JogoService {

    private final JogoDAO jogoDAO;

    public JogoService() {
        this.jogoDAO = new JogoDAO();
    }

    public void cadastrar(Jogo jogo) throws SQLException {
        if (jogo.getTitulo() == null || jogo.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("O título do jogo é obrigatório.");
        }

        if (jogo.getAnoLancamento() <= 0) {
            throw new IllegalArgumentException("O ano de lançamento deve ser válido.");
        }

        if (jogo.getPrecoCompra() < 0 || jogo.getPrecoVenda() < 0) {
            throw new IllegalArgumentException("Os preços não podem ser negativos.");
        }

        if (jogo.getQuantidadeEstoque() < 0) {
            throw new IllegalArgumentException("A quantidade em estoque não pode ser negativa.");
        }

        jogoDAO.inserir(jogo);
    }
}
