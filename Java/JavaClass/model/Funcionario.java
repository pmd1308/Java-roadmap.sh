package model;

import java.util.List;

public class Funcionario {
    private String nome;
    private String endereco;
    private String contato;
    private String escolaridade;
    private List<String> documentos;
    private String cargo;
    private String departamento;
    private double salario;
    private List<String> beneficios;
    
    // Contrutor Padrão
    public Funcionario() {
    }

    // Construtor com Parâmetros
    public Funcionario(String nome, String endereco, String contato, String escolaridade, List<String> documentos, String cargo, String departamento, double salario, List<String> beneficios) {
        this.nome = nome;
        this.endereco = endereco;
        this.contato = contato;
        this.escolaridade = escolaridade;
        this.documentos = documentos;
        this.cargo = cargo;
        this.departamento = departamento;
        this.salario = salario;
        this.beneficios = beneficios;
    }

    
    // Métodos getters e setters para os atributos dos construtores
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public List<String> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<String> documentos) {
        this.documentos = documentos;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public List<String> getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(List<String> beneficios) {
        this.beneficios = beneficios;
    }

}

