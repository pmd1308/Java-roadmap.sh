package model;

import java.util.List;

public class HistoricoProfissional {
    private List<String> experienciasAnteriores;
    private List<String> cursosRealizados;
    private List<String> certificacoes;

    public HistoricoProfissional(List<String> experienciasAnteriores, List<String> cursosRealizados, List<String> certificacoes) {
        this.experienciasAnteriores = experienciasAnteriores;
        this.cursosRealizados = cursosRealizados;
        this.certificacoes = certificacoes;
    }

    public List<String> getExperienciasAnteriores() {
        return experienciasAnteriores;
    }

    public void setExperienciasAnteriores(List<String> experienciasAnteriores) {
        this.experienciasAnteriores = experienciasAnteriores;
    }

    public List<String> getCursosRealizados() {
        return cursosRealizados;
    }

    public void setCursosRealizados(List<String> cursosRealizados) {
        this.cursosRealizados = cursosRealizados;
    }

    public List<String> getCertificacoes() {
        return certificacoes;
    }

    public void setCertificacoes(List<String> certificacoes) {
        this.certificacoes = certificacoes;
    }
}
