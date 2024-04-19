package org.example

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements


class ServicoColetarDados {

    private ServicoArquivos arquivos

    ServicoColetarDados(){
        arquivos = new ServicoArquivos()
    }

    private Document buscarPagina(String url) {
        return Jsoup.connect(url).get()
    }

     private String buscarPaginaTiss(){
        Document paginaInicial = Jsoup.connect("https://www.gov.br/ans/pt-br").get()
        Element linhaHtml = paginaInicial.getElementById("ce89116a-3e62-47ac-9325-ebec8ea95473")
        String linkPaginaPrestador = linhaHtml.getElementsByTag("a").attr("href")

        Document paginaEspacoPrestador = Jsoup.connect(linkPaginaPrestador).get()
        Element botaoTiss = paginaEspacoPrestador.getElementsByClass("govbr-card-content").first()
        return botaoTiss.getElementsByTag("a").attr("href")
    }

    void obterTabela(){
        Document paginaTiss = buscarPagina(buscarPaginaTiss())
        Element linhaHtml = paginaTiss.getElementsByClass("internal-link").first()
        String linkPaginaPadraoTiss = linhaHtml.attr("href")

        Document paginaPadraoTiss = buscarPagina(linkPaginaPadraoTiss)
        Element Pegartabela = paginaPadraoTiss.getElementsByTag("tbody").first().getElementsByTag("tr").last()
        String url = Pegartabela.lastElementChild().firstElementChild().attr("href")

       arquivos.baixarAquivo(url, "Documento_do_TISS.zip")
    }

    void obterHistorico(){
        Document paginaTiss = buscarPagina(buscarPaginaTiss())
        Element linhaHtml = paginaTiss.getElementsByClass("internal-link").get(1)
        String url = linhaHtml.select("a").attr("href")

        Document paginaPadraoTiss = buscarPagina(url)
        Element corpoTabela = paginaPadraoTiss.getElementsByTag("tbody").get(0)
        Elements conteudoTabela = corpoTabela.getElementsByTag("tr")

        ArrayList<ArrayList<String>> historico = []

        historico.add(["Competência", "Publicação", "Início de Vigência"])
        conteudoTabela.each {
            tr ->
                Elements listaTd = tr.getElementsByTag("td")
                String competencia = listaTd.get(0).text()

                ArrayList<String> pegarAno = competencia.split("/")

                Integer ano = Integer.parseInt(pegarAno[1])

                if (ano >= 2016) {
                    String publicacao = listaTd.get(1).text()
                    String inicioVigencia = listaTd.get(2).text()
                    historico.add([competencia, publicacao, inicioVigencia])
                }
        }

        arquivos.criarArquivo(historico, "./Downloads/historico_versoes_TISS.txt")
    }

    void obterTabelaErros(){
        Document paginaTiss= buscarPagina(buscarPaginaTiss())
        Element linhaHtml = paginaTiss.getElementsByClass("internal-link").get(2)
        String urlTabela = linhaHtml.getElementsByTag("a").attr("href")

        Document paginaTabelaRelacionadas = buscarPagina(urlTabela)
        Element linhaTabelaErro = paginaTabelaRelacionadas.getElementsByClass("internal-link").first()
        String linkTabelaErro = linhaTabelaErro.getElementsByTag("a").attr("href")

        arquivos.baixarAquivo(linkTabelaErro, "tabela_de_erros_ANS.xlsx")
    }
}
