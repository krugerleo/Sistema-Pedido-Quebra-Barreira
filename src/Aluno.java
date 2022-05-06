import java.util.ArrayList;
import java.util.List;

public class Aluno {
    private String nome;
    private int grr;
    private String curso;
    private String telefone;
    private String email;

    private Historico historico;
    private Desempenho desempenho;

    public Aluno(String nome, int grr, String curso, String telefone, String email){
        this.nome = nome;
        this.grr = grr;
        this.curso = curso;
        this.telefone = telefone;
        this.email = email;
        this.desempenho = new Desempenho();
    }
    public Aluno(){}

    public void imprimeInformacoes(){
        System.out.printf(ConsoleColors.stringColor("Nome: %s, GRR: GRR%d, Curso: %s, Telefone: %s, E-mail: %s") ,this.nome,this.grr,this.curso,this.telefone,this.email);
    }

    public int getGrr() {
        return grr;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCurso() {
        return curso;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGrr(int grr) {
        this.grr = grr;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Historico getHistorico() {
        return historico;
    }

    public void setHistorico(Historico historico) {
        this.historico = historico;
    }

    public void materiasBarreira( Grade grade){
        System.out.println(ConsoleColors.stringColor("\nImprimindo materias faltantes na barreira", ConsoleColors.GREEN) );
        for(DisciplinaGrade g : grade.getDisciplinas()){
            if(g.getBarreira() && !Disciplina.containsName(this.historico.getList(), g.getNome()) )
                System.out.println(ConsoleColors.stringColor("Nome: ") + g.getNome()
                        + ConsoleColors.stringColor(" Pré-barreira: ") + g.getBarreira()
                        + ConsoleColors.stringColor(" Periodo: ", ConsoleColors.RED) + g.getPeriodo()
                        + ConsoleColors.stringColor(" Ofertada: ") + g.getOfertada());

        }
    }
    public List<DisciplinaGrade> materiasProximoSemestre( Grade grade){
        List<DisciplinaGrade> ofertadas = new ArrayList<DisciplinaGrade>(50);
        for(DisciplinaGrade g : grade.getDisciplinas()){
            if(!Disciplina.containsName(this.historico.getList(), g.getNome()) && g.getOfertada() ) {
//                System.out.println(ConsoleColors.stringColor(" Nome: ") + g.getNome()
//                        + ConsoleColors.stringColor(" Pré-barreira: ") + g.getBarreira()
//                        + ConsoleColors.stringColor(" Periodo: ", ConsoleColors.RED) + g.getPeriodo()
//                        + ConsoleColors.stringColor(" Ofertada: ") + g.getOfertada());
                ofertadas.add(g);
            }
        }
        return ofertadas;
    }

    public Desempenho getDesempenho() {
        return desempenho;
    }

    public void desempenhoSemestre(String semestre){
        int aprovacao = 0;
        int reprovacao = 0;
        int faltante = 0;
        int cursadas = 0;
        if ((this.historico != null))
            for (DisciplinaHistorico h: this.historico.getList()) {
                if(h.getUltimoSemestreCursada().equals(semestre)){
                    //status 2 ou 3 = reprovacao
                    if( h.getStatus() == 3  ){
                        reprovacao +=1;
                        faltante +=1;
                    }else if (h.getStatus() == 2)
                        reprovacao +=1;
                    else if(h.getStatus() == 1)
                        aprovacao +=1;
                    cursadas+=1;
                }
            }
        this.desempenho.setSemestre(semestre);
        this.desempenho.setAprovacoes(aprovacao);
        this.desempenho.setCursadas(cursadas);
        this.desempenho.setReprovacoes(reprovacao);
        this.desempenho.setFaltantes(faltante);
    }
    public void imprimeDesempenho(){
        System.out.println(ConsoleColors.stringColor("\nDesempenho no ultimo semestre (ano/semestre): ",ConsoleColors.GREEN) + this.desempenho.getSemestre());
        float value = (float)this.desempenho.getAprovacoes()/(float)this.desempenho.getCursadas()*100;
        System.out.println(ConsoleColors.stringColor("Quantidade materias cursadas: ") + (int)this.desempenho.getCursadas()
                + ConsoleColors.stringColor(" Aprovaçções: ") + this.desempenho.getAprovacoes()
                + ConsoleColors.stringColor(" Porcentagem de aprovação: ") + value +"%"
                + ConsoleColors.stringColor(" Reprovações por falta: ") + this.desempenho.getFaltantes());
    }

}

