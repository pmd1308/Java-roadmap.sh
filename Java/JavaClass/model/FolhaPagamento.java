package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class FolhaPagamento {
    private static final double HORAS_TRABALHADAS_PADRAO = 160.0;
    private static final double HORAS_EXTRA_PERCENTUAL = 1.5;

    private double obterSalarioMinimo() throws IOException {
        String url = "http://www.ipeadata.gov.br/exibeserie.aspx?stub=1&serid1739471028=1739471028";
        String html = obterDadosPagina(url);
        return extrairSalarioUltimoItem(html);
    }

    private String obterDadosPagina(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder(); // Usando StringBuilder em vez de StringBuffer
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private double extrairSalarioUltimoItem(String html) {
        Document doc = Jsoup.parse(html);
        Elements tabela = doc.select("table#grd tr");
        Elements lastLine = tabela.last();
        Elements wage = lastLine.select("td:last-child");
        String salarioStr = wage.text().replace(",", "");
        return Double.parseDouble(salarioStr);
    }


    public double calcularSalario(Funcionario funcionario) {
        double salarioTotal = 0.0;
        try {
            double salarioBase = funcionario.getSalario();
            salarioTotal = salarioBase;


            List<String> beneficios = funcionario.getBeneficios();
            for (String beneficio : beneficios) {
                if (beneficio.equals("plano de saúde")) {
                    salarioTotal += 200.0;
                } else if (beneficio.equals("vale transporte")) {
                    salarioTotal += 100.0;
                }
            }

            double salarioMinimo = obterSalarioMinimo();
            if (salarioTotal < salarioMinimo) {
                salarioTotal = salarioMinimo;
            }
        } catch (IOException e) {
            // Tratar exceção de IO aqui, se necessário
            e.printStackTrace();
        }
        return salarioTotal;
    }
}
