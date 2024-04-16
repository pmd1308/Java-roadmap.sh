package controleponto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Funcionario;

public class RegistroPonto {
    private List<Date> entradas;
    private List<Date> saidas;
    private static final int LIMITE_HORAS_DIARIAS = 8;
    private static final int LIMITE_HORAS_SEMANAIS = 44;

    public RegistroPonto() {
        this.entradas = new ArrayList<>();
        this.saidas = new ArrayList<>();
    }

    public void registrarEntrada(Funcionario funcionario, Date dataHora) {
        this.entradas.add(dataHora);
        System.out.println("Entrada registrada para o funcionário " + funcionario.getNome() + " às " + dataHora);
    }

    public void registrarSaida(Funcionario funcionario, Date dataHora) {
        this.saidas.add(dataHora);
        System.out.println("Saída registrada para o funcionário " + funcionario.getNome() + " às " + dataHora);
    }

    public void exibirRegistro(Funcionario funcionario) {
        List<Date> entradasFuncionario = new ArrayList<>();
        List<Date> saidasFuncionario = new ArrayList<>();

        for (int i = 0; i < entradas.size(); i++) {
            if (funcionario.equals(entradas.get(i))) {
                entradasFuncionario.add(entradas.get(i));
                saidasFuncionario.add(saidas.get(i));
            }
        }

        System.out.println("Registro de ponto para o funcionário " + funcionario.getNome() + ":");
        for (int i = 0; i < entradasFuncionario.size(); i++) {
            System.out.println("Entrada: " + entradasFuncionario.get(i) + " - Saída: " + saidasFuncionario.get(i));
        }
    }

    public double calcularHorasExtras(Funcionario funcionario) {
        double horasExtras = 0;
        for (int i = 0; i < entradas.size(); i++) {
            long diff = saidas.get(i).getTime() - entradas.get(i).getTime();
            double horasTrabalhadas = diff / (60 * 60 * 1000);
            if (horasTrabalhadas > LIMITE_HORAS_DIARIAS) {
                horasExtras += horasTrabalhadas - LIMITE_HORAS_DIARIAS;
            }
        }
        return horasExtras;
    }

    public boolean verificarControleJornada(Funcionario funcionario) {
        int totalHoras = 0;
        for (int i = 0; i < entradas.size(); i++) {
            long diff = saidas.get(i).getTime() - entradas.get(i).getTime();
            double horasTrabalhadas = diff / (60 * 60 * 1000);
            totalHoras += horasTrabalhadas;
        }
        return totalHoras <= LIMITE_HORAS_SEMANAIS;
    }
}
