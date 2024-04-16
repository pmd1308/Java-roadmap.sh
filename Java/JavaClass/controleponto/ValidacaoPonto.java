package controleponto;

import java.util.Calendar;
import java.util.Date;

import model.Funcionario;

public class ValidacaoPonto {
    private static final int LIMITE_MINUTOS_TOLERANCIA = 15;

    // Construtor padrão
    public ValidacaoPonto() {
        // Nenhuma lógica especial é necessária para o construtor padrão neste caso
    }

    // Método para validar o registro de ponto
    public boolean validarPonto(Funcionario funcionario, Date dataHora, TipoRegistro tipo) {
        if (tipo == TipoRegistro.ENTRADA) {
            return validarEntrada(funcionario, dataHora);
        } else if (tipo == TipoRegistro.SAIDA) {
            return validarSaida(funcionario, dataHora);
        } else {
            System.out.println("Tipo de registro inválido.");
            return false;
        }
    }

    // Método para validar a entrada do funcionário
    private boolean validarEntrada(Funcionario funcionario, Date entrada) {
        Date entradaPrevista = calcularEntradaPrevista(funcionario);
        long diferencaMinutos = calcularDiferencaMinutos(entradaPrevista, entrada);

        if (diferencaMinutos <= LIMITE_MINUTOS_TOLERANCIA) {
            return true;
        } else {
            System.out.println("Entrada fora do horário previsto.");
            return false;
        }
    }

    // Método para validar a saída do funcionário
    private boolean validarSaida(Funcionario funcionario, Date saida) {
        Date saidaPrevista = calcularSaidaPrevista(funcionario);
        long diferencaMinutos = calcularDiferencaMinutos(saidaPrevista, saida);

        if (diferencaMinutos <= LIMITE_MINUTOS_TOLERANCIA) {
            return true;
        } else {
            System.out.println("Saída fora do horário previsto.");
            return false;
        }
    }

    // Método para calcular o horário de entrada previsto
    private Date calcularEntradaPrevista(Funcionario funcionario) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(new Date());

        // Verifica se é dia útil (segunda a sexta)
        int diaSemana = calendario.get(Calendar.DAY_OF_WEEK);
        if (diaSemana >= Calendar.MONDAY && diaSemana <= Calendar.FRIDAY) {
            // Define o horário de expediente
            Calendar horarioEntrada = Calendar.getInstance();
            horarioEntrada.set(Calendar.HOUR_OF_DAY, 8); // 8:00 da manhã
            horarioEntrada.set(Calendar.MINUTE, 0);
            horarioEntrada.set(Calendar.SECOND, 0);
            horarioEntrada.set(Calendar.MILLISECOND, 0);

            return horarioEntrada.getTime();
        } else {
            // Se não for dia útil, retorna null indicando que não há horário de entrada previsto
            return null;
        }
    }

    // Método para calcular o horário de saída previsto
    private Date calcularSaidaPrevista(Funcionario funcionario) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(new Date());

        // Verifica se é dia útil (segunda a sexta)
        int diaSemana = calendario.get(Calendar.DAY_OF_WEEK);
        if (diaSemana >= Calendar.MONDAY && diaSemana <= Calendar.FRIDAY) {
            // Define o horário de expediente
            Calendar horarioSaida = Calendar.getInstance();
            horarioSaida.set(Calendar.HOUR_OF_DAY, 18); // 18:00 da noite
            horarioSaida.set(Calendar.MINUTE, 0);
            horarioSaida.set(Calendar.SECOND, 0);
            horarioSaida.set(Calendar.MILLISECOND, 0);

            return horarioSaida.getTime();
        } else {
            // Se não for dia útil, retorna null indicando que não há horário de saída previsto
            return null;
        }
    }

    // Método para calcular a diferença em minutos entre duas datas
    private long calcularDiferencaMinutos(Date dataHora1, Date dataHora2) {
        long diferencaMillis = Math.abs(dataHora1.getTime() - dataHora2.getTime());
        return diferencaMillis / (60 * 1000);
    }
}
