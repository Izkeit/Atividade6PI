package retroquest.app;

import retroquest.model.Cliente;
import retroquest.model.ItemVenda;
import retroquest.model.Jogo;
import retroquest.model.Venda;
import retroquest.service.ClienteService;
import retroquest.service.JogoService;
import retroquest.service.VendaService;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== TESTES DO SISTEMA RETROQUEST ===");
        System.out.println();

        testarClienteSemNome();
        testarJogoComEstoqueNegativo();
        testarVendaComQuantidadeInvalida();

    }

    private static void testarClienteSemNome() {

        System.out.println("Teste 1 - Cliente sem nome");

        Cliente cliente = new Cliente();
        cliente.setNome("");
        cliente.setEmail("teste@email.com");
        cliente.setTelefone("99999-9999");

        ClienteService clienteService = new ClienteService();

        try {
            clienteService.cadastrar(cliente);
            System.out.println("FALHOU: cliente inválido foi cadastrado.");
        } catch (Exception e) {
            System.out.println("PASSOU: " + e.getMessage());
        }

        System.out.println();
    }

    private static void testarJogoComEstoqueNegativo() {

        System.out.println("Teste 2 - Jogo com estoque negativo");

        Jogo jogo = new Jogo();
        jogo.setTitulo("Jogo Teste");
        jogo.setAnoLancamento(1999);
        jogo.setGenero("Ação");
        jogo.setPrecoCompra(30.0);
        jogo.setPrecoVenda(50.0);
        jogo.setQuantidadeEstoque(-1);

        JogoService jogoService = new JogoService();

        try {
            jogoService.cadastrar(jogo);
            System.out.println("FALHOU: jogo inválido foi cadastrado.");
        } catch (Exception e) {
            System.out.println("PASSOU: " + e.getMessage());
        }

        System.out.println();
    }

    private static void testarVendaComQuantidadeInvalida() {

        System.out.println("Teste 3 - Venda com quantidade inválida");

        Cliente cliente = new Cliente();
        cliente.setId(13);

        Jogo jogo = new Jogo();
        jogo.setId(8);

        Venda venda = new Venda();
        venda.setCliente(cliente);

        ItemVenda item = new ItemVenda();
        item.setJogo(jogo);
        item.setQuantidade(0);
        item.setPrecoUnitario(70.0);

        VendaService vendaService = new VendaService();

        try {
            vendaService.realizarVenda(venda, item);
            System.out.println("FALHOU: venda com quantidade inválida foi realizada.");
        } catch (Exception e) {
            System.out.println("PASSOU: " + e.getMessage());
        }

        System.out.println();
    }
}
