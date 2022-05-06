import java.util.List;

public class Disciplina {
    private String nome;
    private int periodo;

    public Disciplina(String nome, int periodo){
        this.nome = nome;
        this.periodo = periodo;
    }

    public String getNome() {
        return nome;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public static boolean containsName(final List<DisciplinaHistorico> list, final String name){
        return list.stream().filter(o -> o.getNome().equals(name)).findFirst().isPresent();
    }
    public static boolean containsNamed(final List<Disciplina> list, final String name){
        return list.stream().filter(o -> o.getNome().equals(name)).findFirst().isPresent();
    }
}
