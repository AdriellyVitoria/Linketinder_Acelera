package modelos

class PessoaJuridica extends Pessoa {
    String cnpj
    String pais

    PessoaJuridica() {}

    PessoaJuridica(
            String cnpj,
            String nome,
            String email,
            String telefone,
            String cep,
            String descricao,
            String estado,
            String pais
    ) {
        super(nome, email, telefone, estado, cep, descricao)
        this.cnpj = cnpj
        this.pais = pais
    }

    @Override
    String toString() {
        return " CNPJ: ${cnpj}\n" +
                " Nome: ${nome}\n" +
                " Telefone: ${telefone}\n" +
                " Email: ${email}\n" +
                " Estado: ${estado}\n" +
                " Pais: ${pais}\n" +
                " CEP: ${cep}\n" +
                " Descricao: ${descricao}"
    }
}
