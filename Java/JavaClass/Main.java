import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.*;
import controleponto.*;


public class Main {
    public static void main(String[] args) {
        // Criar instância de funcionário
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("João Silva");
        funcionario.setSalario(3000.0); // Exemplo de salário base

        // Criar instância de folha de pagamento
        FolhaPagamento folhaPagamento = new FolhaPagamento();
        double salarioTotal = folhaPagamento.calcularSalario(funcionario);
        System.out.println("Salário total: " + salarioTotal);

        // Criar instância de registro de ponto
        RegistroPonto registroPonto = new RegistroPonto();
        registroPonto.registrarEntrada(funcionario, new Date());
        registroPonto.registrarSaida(funcionario, new Date());

        // Criar lista de funcionários para geração de relatório
        List<Funcionario> listaFuncionarios = new ArrayList<>();
        listaFuncionarios.add(funcionario);

        // Criar instância de relatório
        Relatorio relatorio = new Relatorio();
        relatorio.gerarRelatorioHorasTrabalhadas(listaFuncionarios, new Date(), new Date());

        // Criar instância de validação de ponto
        ValidacaoPonto validacaoPonto = new ValidacaoPonto();
        boolean pontoValido = validacaoPonto.validarPonto(funcionario, new Date(), TipoRegistro.ENTRADA);
        System.out.println("Ponto válido? " + pontoValido);
    }
}