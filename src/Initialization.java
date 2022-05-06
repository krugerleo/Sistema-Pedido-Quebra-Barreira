import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
public class Initialization {
    public static Aluno manualInitialization(){
        Scanner scan = new Scanner(System.in);  // Create a Scanner object
        System.out.println(ConsoleColors.stringColor("Prenchimento manual: Digite seu nome:", ConsoleColors.CYAN));
        String nome = scan.nextLine();  // Read user input

        System.out.println(ConsoleColors.stringColor("Digite seu curso:",ConsoleColors.CYAN));
        String curso = scan.nextLine();

        System.out.println(ConsoleColors.stringColor("Digite seu telefone:",ConsoleColors.CYAN));
        String telefone = scan.nextLine();

        System.out.println(ConsoleColors.stringColor("Digite seu email:",ConsoleColors.CYAN));
        String email = scan.nextLine();

        System.out.println(ConsoleColors.stringColor("Digite seu GRR (apenas números):",ConsoleColors.CYAN));
        int grr = scan.nextInt();

        Aluno user = new Aluno(nome,grr,curso,telefone,email);
        user.imprimeInformacoes();

        return user;
    }

    public static Aluno csvInitialization() throws Exception{
        String path = System.getProperty("user.dir") + "/config/user.csv";
        System.out.println(ConsoleColors.stringColor("O arquivo utilizado para importação é path: src/config/user.csv (no formato: nome,grr,curso,telefone,email )",ConsoleColors.CYAN));
        String line = "";
        String splitBy = ",";
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null)
            //returns a Boolean value
            {
                String[] aluno = line.split(splitBy);
                //use comma as separator
                if(isNumeric(aluno[1])){
                    return new Aluno(aluno[0],Integer.parseInt(aluno[1]),aluno[2],aluno[3],aluno[4] );
                }
            }
        }
        catch(IOException e) {
            System.out.println(ConsoleColors.stringColor("ERRO NA LEITURA CSV",ConsoleColors.RED_BOLD_BRIGHT));
            e.printStackTrace();
        }
        return null;
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static Pedido jsonPedidoInitialization(){
        String path = System.getProperty("user.dir") + "/config/pedido.json";
        Pedido pedido = new Pedido();
        pedido.setBarreira(new ArrayList<Disciplina>(50));
        pedido.setSolicita(new ArrayList<Disciplina>(50));
        try {

            BufferedReader br = new BufferedReader(new FileReader(path));
            Gson gson = new Gson();

            //Converte String JSON para objeto Java
            pedido = gson.fromJson(br, Pedido.class);

            return pedido;
        } catch (IOException e) {
            System.out.println(ConsoleColors.stringColor("ERRO AO LER JSON",ConsoleColors.RED));
            e.printStackTrace();
        }

        return null;
    }
}
