import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Aluno user;
        char option = 'd';
        List<DisciplinaGrade> ofertadas = null;
        Pedido pedido;
        String line;
        Path path = Path.getInstance(System.getProperty("user.dir") + "/config/");
        Initialization.createFolder();
        Scanner scan = new Scanner(System.in);  // Create a Scanner object
        System.out.println(ConsoleColors.stringColor("Informações básicas sobre o aluno são: Nome, GRR (int), curso, telefone (int), email",ConsoleColors.CYAN));
        System.out.println(ConsoleColors.stringColor("Deseja inserir as informçãoes básicas manualmente ou carregar de csv? Digite", ConsoleColors.CYAN)
                + ConsoleColors.stringColor(" m ",ConsoleColors.RED)
                + ConsoleColors.stringColor("para manual ou",ConsoleColors.CYAN)
                + ConsoleColors.stringColor(" c ",ConsoleColors.RED)
                + ConsoleColors.stringColor("para CSV ",ConsoleColors.CYAN) + "( PATH:"+ path + "historico.csv )" );
        System.out.println(ConsoleColors.stringColor("Após inicialização serão impressos informações sobre historico, grade curricular e outros.", ConsoleColors.CYAN));


        line = scan.nextLine();  // Read user input
        if(!line.equals(""))
            option = line.charAt(0);
        while(option != 'c' && option != 'm'){
            System.out.println(ConsoleColors.stringColor("Opção invalida",ConsoleColors.RED));
            option = scan.nextLine().charAt(0);
        }
        if('c' == option){
            // Opção csv de inserção das informações básicas do usuario
            user = Initialization.csvInitialization();
            if(user == null)
                user = Initialization.manualInitialization();
            user.imprimeInformacoes();
        }else {
            // Opção manual de inserção das informações básicas do usuario
            user = Initialization.manualInitialization();
            user.imprimeInformacoes();
        }

        // Inicializa o historico do usuario a partir do csv
        user.setHistorico(Historico.readHistoric());
        user.getHistorico().imprimeDisciplinas();

        // Inicializar grade
        Grade grade = Grade.getInstance();
        grade.imprimeDisciplinas();

        // Imprimindo materias faltantes
        user.materiasBarreira(grade);

        // Imprimir desempenho ultimo semestre
        user.desempenhoSemestre("22/01");
        user.imprimeDesempenho();

        System.out.println(ConsoleColors.stringColor("\nDeseja importar um pedido existente? (path: config/pedido.json):",ConsoleColors.CYAN)
                + ConsoleColors.stringColor(" s ",ConsoleColors.RED)
                + ConsoleColors.stringColor("ou",ConsoleColors.CYAN) + ConsoleColors.stringColor(" n ", ConsoleColors.RED)
                + ConsoleColors.stringColor("(criar um novo)", ConsoleColors.CYAN));

        line = scan.nextLine();  // Read user input
        if(line != "")
            option = line.charAt(0);
        while(option != 's' && option != 'n'){
            System.out.println(ConsoleColors.stringColor("Opção invalida",ConsoleColors.RED));
            option = scan.nextLine().charAt(0);
        }

        if('s' == option){
            pedido = Initialization.jsonPedidoInitialization();
            if (pedido == null){
                System.out.println(ConsoleColors.stringColor("INICIANDO MANUAL:",ConsoleColors.RED));
                pedido = new Pedido(user,"22/01");
                pedido.listPendentes(user.getHistorico().getList(),grade);
                ofertadas = user.materiasProximoSemestre(grade);
                pedido.readSolicita(ofertadas);
            }
        }else{
            pedido = new Pedido(user,"22/01");
            pedido.listPendentes(user.getHistorico().getList(),grade);
            ofertadas = user.materiasProximoSemestre(grade);
            pedido.readSolicita(ofertadas);
        }

        System.out.println(ConsoleColors.stringColor("Seu pedido esta finalizado e salvo em /config/pedido.json",ConsoleColors.CYAN));
        System.out.println(ConsoleColors.stringColor("Processo finalizado, escolha a opção que desejar:",ConsoleColors.CYAN));
        System.out.printf(ConsoleColors.stringColor("Gerar arquivo de saida (path:/config/saida.txt): "));
        System.out.printf(ConsoleColors.stringColor("1\n",ConsoleColors.RED));
        System.out.printf(ConsoleColors.stringColor("Editar materias solicitadas: "));
        System.out.printf(ConsoleColors.stringColor("2\n",ConsoleColors.RED));
        System.out.printf(ConsoleColors.stringColor("Sair: "));
        System.out.printf(ConsoleColors.stringColor("-1\n",ConsoleColors.RED));
        int value = scan.nextInt();
        while (value >= 0){
            if (value == 1){
                pedido.salvar();
            }else if( value == 2 && ofertadas != null){
                pedido.readSolicita(ofertadas);
            }else
                System.out.println(ConsoleColors.stringColor("Valor invalido",ConsoleColors.RED));

            System.out.println(ConsoleColors.stringColor("Processo finalizado, escolha a opção que desejar:",ConsoleColors.CYAN));
            System.out.printf(ConsoleColors.stringColor("Gerar arquivo de saida (path:/config/saida.txt): "));
            System.out.printf(ConsoleColors.stringColor("1\n",ConsoleColors.RED));
            System.out.printf(ConsoleColors.stringColor("Editar materias solicitações: "));
            System.out.printf(ConsoleColors.stringColor("2\n",ConsoleColors.RED));
            System.out.printf(ConsoleColors.stringColor("Sair: "));
            System.out.printf(ConsoleColors.stringColor("-1\n",ConsoleColors.RED));
            value = scan.nextInt();
        }

    }
}
