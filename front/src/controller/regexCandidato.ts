export class RegexCandidato{
    public regexFormulario(): boolean {
        const telefone = (document.querySelector(".input__telefone input") as HTMLInputElement).value;
        const cpf = (document.querySelector(".input__cpf input") as HTMLInputElement).value;
        const cep = (document.querySelector(".input__cep input") as HTMLInputElement).value;
        const email = (document.querySelector(".input__email input") as HTMLInputElement).value;
    
        const telefoneRegex = /^\(\d{2}\) \d{4,5}-\d{4}$/
        const cpfRegex = /^\d{3}\.\d{3}\.\d{3}-\d{2}$/
        const cepRegex = /^\d{5}-\d{3}$/
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    
        if (!telefoneRegex.test(telefone)) {
            alert("Número de Telefone no formato errado (99) 99999-9999")
            return false;
        }
        if (!cpfRegex.test(cpf)) {
            alert("CPF no formato errado 123.456.789-01")
            return false;
        }
        if (!cepRegex.test(cep)) {
            alert("CEP no formato errado 12345-678")
            return false;
        }
        if (!emailRegex.test(email)) {
            alert("email no formato errado @gmail.com")
            return false;
        }
        return true;
    }
}