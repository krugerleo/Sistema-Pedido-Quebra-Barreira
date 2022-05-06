public class DisciplinaGrade extends Disciplina{
    private Boolean ofertada;
    private Boolean barreira;

    public DisciplinaGrade(String nome,char barreira, int periodo, char ofertada ) {
        super(nome, periodo);
        if(barreira == 's')
            this.barreira = true;
        else
            this.barreira = false;

        if(ofertada == 's')
            this.ofertada = true;
        else
            this.ofertada = false;
    }

    public Boolean getBarreira() {
        return barreira;
    }

    public Boolean getOfertada() {
        return ofertada;
    }
}
