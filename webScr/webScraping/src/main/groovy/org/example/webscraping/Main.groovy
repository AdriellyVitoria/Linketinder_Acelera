package org.example.webscraping

import org.example.webscraping.servico.ServicoColetarDados
import org.w3c.dom.Document
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import javax.lang.model.element.Element

class Main {
    static void main(String[] args) {

        Document paginaInicial =  Jsoup.connect("https://www.gov.br/ans/pt-br").get()
        Element html =  paginaInicial.getElementsContainingOwnText("Espaço do Prestador de Serviço de Saúde")
        println(html)
    }
}
