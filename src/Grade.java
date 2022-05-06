import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public final class Grade {
    private static Grade instance;
    private List<DisciplinaGrade> disciplinas;
    // Singleton
    private Grade(){
        String path = System.getProperty("user.dir") + "/config/grade.csv";
        String line = "";
        String splitBy = ",";
        this.disciplinas = new ArrayList<DisciplinaGrade>(50);
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null)
            //returns a Boolean value
            {
                String[] disciplina = line.split(splitBy);
                if(Initialization.isNumeric(disciplina[2])){
                    this.disciplinas.add(new DisciplinaGrade(disciplina[0],disciplina[1].charAt(0),Integer.parseInt(disciplina[2]),disciplina[3].charAt(0) ));
                }
            }
            this.disciplinas.sort(Comparator.comparing(Disciplina::getPeriodo));
            this.disciplinas = Collections.unmodifiableList(this.disciplinas);
        }
        catch(IOException e) {
            System.out.println("ERRO NA LEITURA CSV");
            e.printStackTrace();
        }
    }

    public static Grade getInstance(){
        if (instance == null)
            instance = new Grade();
        return instance;
    }
    public void imprimeDisciplinas(){
        System.out.println(ConsoleColors.stringColor("\nImprimindo disciplinas obrigatorias da grade.",ConsoleColors.GREEN));
        Iterator<DisciplinaGrade> iter = this.disciplinas.iterator();
        DisciplinaGrade holder;
        while (iter.hasNext()) {
            holder = iter.next();
            System.out.println(ConsoleColors.stringColor("Nome: ") + holder.getNome()
                    + ConsoleColors.stringColor(" Pré-barreira: ") + holder.getBarreira()
                    + ConsoleColors.stringColor(" Periodo: ", ConsoleColors.RED) + holder.getPeriodo()
                    + ConsoleColors.stringColor(" Ofertada: ") + holder.getOfertada());
        }
//        System.out.println("=============");
//        for(DisciplinaGrade e : this.disciplinas){
//            System.out.println(ConsoleColors.stringColor("Nome: ") + e.getNome()
//                    + ConsoleColors.stringColor(" Pré-barreira: ") + e.getBarreira()
//                    + ConsoleColors.stringColor(" Periodo: ", ConsoleColors.RED) + e.getPeriodo()
//                    + ConsoleColors.stringColor(" Ofertada: ") + e.getOfertada());
//        }
    }
    public void imprimeOfertadas(){
        System.out.println(ConsoleColors.stringColor("\nImprimindo disciplinas ofertadas para o proximo semestre.", ConsoleColors.GREEN));
        for(DisciplinaGrade e : this.disciplinas){
            if(e.getOfertada()){
                System.out.println(ConsoleColors.stringColor("Nome: ") + e.getNome()
                        + ConsoleColors.stringColor(" Pré-barreira: ") + e.getBarreira()
                        + ConsoleColors.stringColor(" Periodo: ", ConsoleColors.RED) + e.getPeriodo());
            }
        }
    }

    public List<DisciplinaGrade> getDisciplinas() {
        return disciplinas;
    }
}
