package org.example.webscraping.servico

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import javax.lang.model.element.Element

class ServicoColetarDados {

    private String buscarPagina(String url){
        return Jsoup.connect(url).get()
    }

     String obterUrlPaginaTiss() {
        Document paginaInicial = buscarPagina("https://www.gov.br/ans/pt-br")
        Element html =  paginaInicial.getElementsContainingOwnText("Espaço do Prestador de Serviço de Saúde")
         println(html)
         //String urlPaginaTiss = html.getElementsByTag("a").attr("href")
    }

     void elementosTabela(){
         Document paginaPadrao = buscarPagina("https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/padrao-tiss-historico-das-versoes-dos-componentes-do-padrao-tiss")
         println(paginaPadrao.getElementById("parent-fieldname-text"))
    }

    Document doc = Jsoup.connect("https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss").get()
    def tabela = doc.getElementsContainingOwnText("Clique aqui para acessar a versão Março/2024")


}
