package project;

public class DisciplinaHistorico extends Disciplina{
    private Boolean cursada;
    private int status;
    private String strStatus;

    private String ultimoSemestreCursada;

    public DisciplinaHistorico(String nome, int periodo, char cursada, char status, String semestre) {
        super(nome, periodo);
        if(cursada == 's'){
            this.cursada = true;
        }else{
            this.cursada = false;
        }
        if (cursada == 'n' && status == 'n') {
            this.status = 0;
            this.strStatus = "Materia não foi cursada";
        }else if(cursada == 's' && status == 's'){
            this.status = 1;
            this.strStatus = "Materia cursada e aluno aprovado";
        }else if(cursada == 's' && status == 'n'){
            this.status = 2;
            this.strStatus = "Materia cursada e aluno reprovado por nota";
        }else if(cursada == 's' && status == 'f'){
            this.status = 3;
            this.strStatus = "Materia cursada e aluno reprovado por falta";
        }else{
            this.status = -1;
            this.strStatus = "Valores não correspondem";
        }
        this.ultimoSemestreCursada = semestre;
    }

    public Boolean getCursada() {
        return cursada;
    }

    public int getStatus() {
        return status;
    }

    public String getStrStatus() {
        return strStatus;
    }

    public String getUltimoSemestreCursada() {
        return ultimoSemestreCursada;
    }
}
