import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Historico {
    private List<DisciplinaHistorico> list;
    //Maybe move to initialization
    public static Historico readHistoric(){
        Path path = Path.getInstance();
        System.out.println(ConsoleColors.stringColor("\nUsúario inicializado, iniciando importação de historico (path: src/config/historico.csv)",ConsoleColors.GREEN));
        String line = "";
        String splitBy = ",";
        Historico hist = new Historico();
        hist.list = new ArrayList<DisciplinaHistorico>(50);
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(path.getPath() + "historico.csv"));
            while ((line = br.readLine()) != null)
            //returns a Boolean value
            {
                String[] disciplina = line.split(splitBy);
                if(Initialization.isNumeric(disciplina[1]) && disciplina[2].charAt(0) == 's'){
                    hist.list.add(new DisciplinaHistorico(disciplina[0],Integer.parseInt(disciplina[1]),disciplina[2].charAt(0),disciplina[3].charAt(0), disciplina[4]));
                }
            }
            hist.list.sort(Comparator.comparing(DisciplinaHistorico::getPeriodo));
            return hist;
        }
        catch(IOException e) {
            System.out.println("ERRO NA LEITURA CSV");
            e.printStackTrace();
        }
        hist.list = null;
        return null;
    }
    public void imprimeDisciplinas(){
        System.out.println(ConsoleColors.stringColor("\nImprimindo Historico:",ConsoleColors.GREEN));
        for(DisciplinaHistorico e : this.list){
            System.out.println(ConsoleColors.stringColor("Nome: ") + e.getNome()
                    + ConsoleColors.stringColor("\tCursada: " )  + e.getCursada()
                    + ConsoleColors.stringColor("\tPeriodo: ", ConsoleColors.RED)  + e.getPeriodo()
                    + ConsoleColors.stringColor("\tSemestre cursada: ")  + e.getUltimoSemestreCursada()
                    + ConsoleColors.stringColor("\tStatus: ") + e.getStrStatus());
        }
    }

    public List<DisciplinaHistorico> getList() {
        return list;
    }
}
