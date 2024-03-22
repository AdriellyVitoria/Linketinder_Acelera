package modelos

class PessoaJuridica extends Pessoa {
    String cnpj
    String pais

    PessoaJuridica() {}

    PessoaJuridica(
            String nome,
            String email,
            String senha,
            String telefone,
            String estado,
            String cep,
            String descricao,
            String cnpj,
            String pais
    ) {
        super(nome, email, senha, telefone, estado, cep, descricao)
        this.cnpj = cnpj
        this.pais = pais
    }

    @Override
    String toString() {
        return "Nome: ${nome}, " +
                " Email: ${email}, " +
                " CNPJ: ${cnpj}, " +
                " Estado: ${estado}, " +
                " Pais: ${pais}, " +
                " CEP: ${cep}, " +
                " Descricao: ${descricao}\n"
    }
}
