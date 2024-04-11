"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.RegexCandidato = void 0;
var RegexCandidato = /** @class */ (function () {
    function RegexCandidato() {
    }
    RegexCandidato.prototype.regexFormulario = function () {
        var telefone = document.querySelector(".input__telefone input").value;
        var cpf = document.querySelector(".input__cpf input").value;
        var cep = document.querySelector(".input__cep input").value;
        var email = document.querySelector(".input__email input").value;
        var telefoneRegex = /^\(\d{2}\) \d{4,5}-\d{4}$/;
        var cpfRegex = /^\d{3}\.\d{3}\.\d{3}-\d{2}$/;
        var cepRegex = /^\d{5}-\d{3}$/;
        var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!telefoneRegex.test(telefone)) {
            alert("Número de Telefone no formato errado (99) 99999-9999");
            return false;
        }
        if (!cpfRegex.test(cpf)) {
            alert("CPF no formato errado 123.456.789-01");
            return false;
        }
        if (!cepRegex.test(cep)) {
            alert("CEP no formato errado 12345-678");
            return false;
        }
        if (!emailRegex.test(email)) {
            alert("email no formato errado @gmail.com");
            return false;
        }
        return true;
    };
    return RegexCandidato;
}());
exports.RegexCandidato = RegexCandidato;
