export class GeradorID {
    gerarIdCandidato() {
        let idAtual = JSON.parse(localStorage.getItem('idCandidato'))?.value;
        if (!idAtual) {
            localStorage.setItem('idCandidato', JSON.stringify({ value: 4 }));
            idAtual = JSON.parse(localStorage.getItem('idCandidato'))?.value;
        }
        localStorage.setItem('idCandidato', JSON.stringify({ value: idAtual += 1 }));
        return idAtual;
    }
    gerarIdEmpresa() {
        let idAtual = JSON.parse(localStorage.getItem('idEmpresa'))?.value;
        if (!idAtual) {
            localStorage.setItem('idEmpresa', JSON.stringify({ value: 4 }));
            idAtual = JSON.parse(localStorage.getItem('idEmpresa'))?.value;
        }
        localStorage.setItem('idEmpresa', JSON.stringify({ value: idAtual += 1 }));
        return idAtual;
    }
}
