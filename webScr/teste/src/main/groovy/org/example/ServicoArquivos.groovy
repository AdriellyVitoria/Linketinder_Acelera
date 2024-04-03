package org.example

import java.nio.file.Files
import java.nio.file.Paths

class ServicoArquivos {

    protected void baixarAquivo(String urlString, String path = "./Downloads", String fileName) {
        def url = new URL(urlString)
        def destino = Paths.get("${path}/${fileName}")

        try {
            url.withInputStream { inputStream ->
                Files.copy(inputStream, destino)
                println("Arquivo baixado com sucesso!")
            }
        } catch (Exception e) {
            println("Falha ao baixar o arquivo: ${e}")
        }
    }

    void criarArquivo(List<List<String>> data, String caminhoArquivo){
        File file = new File(caminhoArquivo)
        if (file.exists()) {
            file.delete()
        }
        file.createNewFile()
        file.withWriter { writer ->
            data.each { info ->
                writer.writeLine(info.join(", "))
            }
        }
        println("Arquivo salvo em ${caminhoArquivo}")
    }
}
