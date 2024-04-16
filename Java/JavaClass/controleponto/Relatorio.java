package controleponto;

import model.Funcionario;
import java.util.Date;
import java.util.List;

public class Relatorio {
    public void gerarRelatorioHorasTrabalhadas(List<Funcionario> funcionarios, Date inicio, Date fim) {
        System.out.println("Relatório de Horas Trabalhadas:");
        System.out.println("Período: " + inicio + " - " + fim);
        
        for (Funcionario funcionario : funcionarios) {
            System.out.println("Funcionário: " + funcionario.getNome());
        }
    }
}
