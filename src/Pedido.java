import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;


public class Pedido {
    private String nome;
    private String semestre;
    private int grr;
    private String curso;
    private String telefone;
    private String email;
    private List<Disciplina> barreira;
    private int obrigatoias;
    private List<Disciplina> solicita;
    private Desempenho desempenho;

    public Pedido(Aluno aluno,String semestre){
        this.nome = aluno.getNome();
        this.grr = aluno.getGrr();
        this.curso = aluno.getCurso();
        this.telefone = aluno.getTelefone();
        this.email = aluno.getEmail();
        this.semestre = semestre;
        this.desempenho = aluno.getDesempenho();
    }

    public Pedido() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSemestre() {
        return semestre;
    }

    public int getGrr() {
        return grr;
    }

    public void setGrr(int grr) {
        this.grr = grr;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Disciplina> getBarreira() {
        return barreira;
    }

    public void setBarreira(List<Disciplina> barreira) {
        this.barreira = barreira;
    }

    public int getObrigatoias() {
        return obrigatoias;
    }

    public void setObrigatoias(int obrigatoias) {
        this.obrigatoias = obrigatoias;
    }

    public List<Disciplina> getSolicita() {
        return solicita;
    }

    public void setSolicita(List<Disciplina> solicita) {
        this.solicita = solicita;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public void listPendentes(List<DisciplinaHistorico> historico, Grade grade){
        int count = 0;
        this.barreira = new ArrayList<Disciplina>(50);
        for(DisciplinaGrade g : grade.getDisciplinas()){
            if(g.getBarreira() && !Disciplina.containsName(historico, g.getNome()) ) {
                this.barreira.add(new Disciplina(g.getNome(),g.getPeriodo()));
            }
            if( !Disciplina.containsName(historico, g.getNome()) )
                count+=1;
        }
        this.obrigatoias = count;
    }
    public void readSolicita(List<DisciplinaGrade> ofertas){
        int[] disc;
        int index = 0;
        int count = 0;

        System.out.println(ConsoleColors.stringColor("\nImprimindo materias ofertadas e não cursadas para o proximo semestre", ConsoleColors.GREEN) );
        for (DisciplinaGrade o : ofertas){
            System.out.println( ConsoleColors.stringColor(" ID: ",ConsoleColors.RED_BOLD_BRIGHT) + index
                        + ConsoleColors.stringColor(" Nome: ") + o.getNome()
                        + ConsoleColors.stringColor(" Pré-barreira: ") + o.getBarreira()
                        + ConsoleColors.stringColor(" Periodo: ", ConsoleColors.RED) + o.getPeriodo()
                        + ConsoleColors.stringColor(" Ofertada: ") + o.getOfertada());
            index+=1;
        }
        if(this.solicita != null && this.solicita.size() > 0){
            System.out.println(ConsoleColors.stringColor("Suas materias em solicittações são:"));
            for (Disciplina d : this.solicita){
                System.out.println( ConsoleColors.stringColor(" ID: ",ConsoleColors.RED_BOLD_BRIGHT) + count
                        + ConsoleColors.stringColor(" Nome: ") + d.getNome()
                        + ConsoleColors.stringColor(" pediodo: ") + d.getPeriodo() );
                count+=1;
            }
        }

        float category = (float)this.desempenho.getAprovacoes()/(float)this.desempenho.getCursadas()*100;
        System.out.println(ConsoleColors.stringColor("\nA partir do semestre anterior: ",ConsoleColors.CYAN) + this.semestre );
        if(category > 66.0){
            System.out.println(ConsoleColors.stringColor("Seu desempenho foi classificado como A, recomendações do colegiado para o seu pedido serão:",ConsoleColors.RED_BOLD_BRIGHT));
            System.out.println(ConsoleColors.stringColor("--> Conceder cinco matrículas para aqueles que apresentaram bom desempenho (caso A).\n" +
                    "anterior",ConsoleColors.CYAN));
        }else if (category >= 50.0 && category < 66.0){
            System.out.println(ConsoleColors.stringColor("Seu desempenho foi classificado como B, recomendações do colegiado para o seu pedido serão:",ConsoleColors.RED_BOLD_BRIGHT));
            System.out.println(ConsoleColors.stringColor("--> Conceder quatro matrículas para casos intermediários (caso B).",ConsoleColors.CYAN));
        }else if(category < 50.0){
            System.out.println(ConsoleColors.stringColor("Seu desempenho foi classificado como C, recomendações do colegiado para o seu pedido serão:",ConsoleColors.RED_BOLD_BRIGHT));
            System.out.println(ConsoleColors.stringColor("--> Conceder três matrículas para aqueles que apresentaram desempenho ruim (caso C) ",ConsoleColors.CYAN));
        }

        System.out.println(ConsoleColors.stringColor("\nVocê deve digitar de 1 em 1, pelo valor do ",ConsoleColors.CYAN)
                + ConsoleColors.stringColor("ID ",ConsoleColors.RED_BOLD_BRIGHT)
                + ConsoleColors.stringColor("quais materias você deseja solicitar ou digitar ",ConsoleColors.CYAN)
                + ConsoleColors.stringColor("-1 ",ConsoleColors.RED_BOLD_BRIGHT)
                + ConsoleColors.stringColor("quando desejar finalizar",ConsoleColors.CYAN));

        Scanner scan = new Scanner(System.in);  // Create a Scanner object
        int value = scan.nextInt();
        this.solicita = new ArrayList<Disciplina>(index);
        while (value >= 0){
            if( value < index && Disciplina.containsNamed(this.solicita, ofertas.get(value).getNome() ) )
                System.out.println(ConsoleColors.stringColor("Materia já adicionada",ConsoleColors.RED));
            else if(value < index){
                this.solicita.add(new Disciplina(ofertas.get(value).getNome(),ofertas.get(value).getPeriodo()));
                System.out.println(ConsoleColors.stringColor("Materia adicionada as solicitações: ") + ofertas.get(value).getNome());
            }else{
                System.out.println(ConsoleColors.stringColor("Valor invalido, digite novamente: ",ConsoleColors.RED));
            }
            value = scan.nextInt();
        }
        count = 0;
        for (Disciplina d : this.solicita){
            System.out.println( ConsoleColors.stringColor(" ID: ",ConsoleColors.RED_BOLD_BRIGHT) + count
                            + ConsoleColors.stringColor(" Nome: ") + d.getNome()
                            + ConsoleColors.stringColor(" pediodo: ") + d.getPeriodo() );
            count+=1;
        }
        System.out.println(ConsoleColors.stringColor("\nSE DESEJAR DELETAR DA LISTA DE SOLICITAÇÃO\n deve digitar de 1 em 1, pelo valor do ",ConsoleColors.CYAN)
                + ConsoleColors.stringColor("ID ",ConsoleColors.RED_BOLD_BRIGHT)
                + ConsoleColors.stringColor("quais materias você deseja deletar ou digitar ",ConsoleColors.CYAN)
                + ConsoleColors.stringColor("-1 ",ConsoleColors.RED_BOLD_BRIGHT)
                + ConsoleColors.stringColor("quando desejar finalizar, você podera voltar a edição depois de finalizar essas estapas.",ConsoleColors.CYAN));
        value = scan.nextInt();
        while (value >= 0){
            if( value < count && Disciplina.containsNamed(this.solicita, this.solicita.get(value).getNome() ) ){
                this.solicita.remove(value);
                System.out.println(ConsoleColors.stringColor("Materia removida!",ConsoleColors.RED) );
            }else{
                System.out.println(ConsoleColors.stringColor("Valor invalido",ConsoleColors.RED) );
            }
            count = 0;
            for (Disciplina d : this.solicita){
                System.out.println( ConsoleColors.stringColor(" ID: ",ConsoleColors.RED_BOLD_BRIGHT) + count
                        + ConsoleColors.stringColor(" Nome: ") + d.getNome()
                        + ConsoleColors.stringColor(" pediodo: ") + d.getPeriodo() );
                count+=1;
            }
            System.out.println(ConsoleColors.stringColor("Proximo ID:",ConsoleColors.CYAN));
            value = scan.nextInt();
        }
    }
    public void salvar() throws Exception{
        String path = System.getProperty("user.dir") + "/config/";
        Gson gson = new Gson();
        String requestJson = gson.toJson(this);
        Formatter output;

        File requestFile = new File(path + "pedido.json");
        requestFile.createNewFile(); // if file already exists will do nothing
        BufferedWriter writerRequest = new BufferedWriter(new FileWriter(path + "pedido.json"));
        writerRequest.write(requestJson);
        writerRequest.close();
        System.out.println(ConsoleColors.stringColor("JSON salvo:") + requestJson);

        String separator = System.getProperty("line.separator");
        File helperFile = new File(path + "saida.txt");
        helperFile.createNewFile(); // if file already exists will do nothing
        output = new Formatter(path + "saida.txt");

        output.format("%-20s GRR%-10d %n",this.nome,this.grr);
        output.format("Curso:%-20s Telefone:%-15s E-mail:%-15s %n",this.curso,this.telefone,this.email);
        output.format("%nMaterias pendentendes na barreira: %n");
        for (Disciplina d : this.barreira){
            output.format("%-20s %n",d.getNome());
        }
        output.format("%nQuantidade de obrigatorias ainda precisa cursar: %d %n",this.obrigatoias);
        output.format("%nMaterias solicitadas: %n");
        for (Disciplina d : this.solicita){
            output.format("%-20s %n",d.getNome());
        }
        float value = (float)this.desempenho.getAprovacoes()/(float)this.desempenho.getCursadas()*100;
        output.format("%nDesempenho ultimo semestres %s %n",this.semestre);
        output.format("Quantidade de materias cursada: %d %n",this.desempenho.getCursadas());
        output.format("Quantidade de aprovações: %d %n", this.desempenho.getAprovacoes());
        output.format("Porcentagem de aprovação: %f %n", value);
        output.format("Reprovações por falta: %d %n", this.desempenho.getFaltantes());
        output.flush();
        output.close();


    }
}
