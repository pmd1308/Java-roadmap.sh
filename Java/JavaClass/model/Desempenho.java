package model;

import java.util.List;

public class Desempenho {
    private List<String> avaliacoes;
    private List<String> feedback;
    private List<String> metas;

    public Desempenho(List<String> avaliacoes, List<String> feedback, List<String> metas) {
        this.avaliacoes = avaliacoes;
        this.feedback = feedback;
        this.metas = metas;
    }

    public List<String> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<String> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public List<String> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<String> feedback) {
        this.feedback = feedback;
    }

    public List<String> getMetas() {
        return metas;
    }

    public void setMetas(List<String> metas) {
        this.metas = metas;
    }

}
