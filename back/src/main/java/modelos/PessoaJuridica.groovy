package modelos

class PessoaJuridica extends Pessoa {
    String cnpj
    String pais
    ArrayList<Competencia> competencias

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

    PessoaJuridica(String cnpj, String nome, String email, String telefone, String cep, String descricao) {
        this.cnpj = cnpj
        this.nome = nome
        this.email = email
        this.telefone = telefone
        this.cep = cep
        this.descricao = descricao
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
