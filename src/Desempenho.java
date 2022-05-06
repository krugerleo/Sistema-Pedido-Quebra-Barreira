public class Desempenho {
    private String semestre;
    private int cursadas;
    private int aprovacoes;
    private int reprovacoes;
    private int faltantes;

    public Desempenho(){}
    public Desempenho(String s, int c, int a, int r, int f){
        this.semestre = s;
        this.cursadas = c;
        this.aprovacoes = a;
        this.reprovacoes = r;
        this.faltantes = f;
    }

    public String getSemestre() {
        return semestre;
    }

    public int getAprovacoes() {
        return aprovacoes;
    }

    public int getCursadas() {
        return cursadas;
    }

    public int getFaltantes() {
        return faltantes;
    }

    public int getReprovacoes() {
        return reprovacoes;
    }

    public void setAprovacoes(int aprovacoes) {
        this.aprovacoes = aprovacoes;
    }

    public void setCursadas(int cursadas) {
        this.cursadas = cursadas;
    }

    public void setFaltantes(int faltantes) {
        this.faltantes = faltantes;
    }

    public void setReprovacoes(int reprovacoes) {
        this.reprovacoes = reprovacoes;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }
}
