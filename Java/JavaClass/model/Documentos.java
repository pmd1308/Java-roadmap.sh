package model;

import java.util.ArrayList;
import java.util.List;

public class Documentos {
    private List<String> documentosArmazenados;

    public Documentos() {
        this.documentosArmazenados = new ArrayList<>();
    }

    public void adicionarDocumento(String documento) {
        this.documentosArmazenados.add(documento);
        System.out.println("Adicionado: " + documento);
    }

    public void removerDocumento(String documento) {
        if (this.documentosArmazenados.contains(documento)) {
            this.documentosArmazenados.remove(documento);
            System.out.println("Removido: " + documento);
        } else {
            System.out.println("Documento não encontrado!");
        }
    }
}
