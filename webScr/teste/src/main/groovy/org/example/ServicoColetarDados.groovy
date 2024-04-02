package org.example

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class ServicoColetarDados {
    private Document buscarPagina(String url) {
        return Jsoup.connect(url).get()
    }

    String buscarPaginaTiss(){
        Document paginaInicial = Jsoup.connect("https://www.gov.br/ans/pt-br").get()
        Element linhaHtml = paginaInicial.getElementById("ce89116a-3e62-47ac-9325-ebec8ea95473")
        String linkPaginaPrestador = linhaHtml.getElementsByTag("a").attr("href")

        Document paginaEspacoPrestador = Jsoup.connect(linkPaginaPrestador).get()
        Element botaoTiss = paginaEspacoPrestador.getElementsByClass("govbr-card-content").first()
        String linkPaginaTiss = botaoTiss.getElementsByTag("a").attr("href")
    }

    void obterTabela(){
        Document paginaHtml = buscarPagina(buscarPaginaTiss())
        Element linhaHtml = paginaHtml.getElementsByClass("internal-link").first()
        String linkPaginaPadraoTiss = linhaHtml.attr("href")

        Document paginaPadraoTiss = buscarPagina(linkPaginaPadraoTiss)
        Element Pegartabela = paginaPadraoTiss.getElementsByTag("tbody").first().getElementsByTag("tr").last()
        String url = Pegartabela.lastElementChild().firstElementChild().attr("href")
        println(url)
    }
}
