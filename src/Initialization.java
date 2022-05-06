import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
public class Initialization {
    public static Aluno manualInitialization(){
        int grr = 01010101;
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
        try {
            grr = scan.nextInt();
        }catch (Exception e){
            e.printStackTrace();
        }


        Aluno user = new Aluno(nome,grr,curso,telefone,email);
        user.imprimeInformacoes();

        return user;
    }
    public static void createFolder() throws Exception{
        Path path = Path.getInstance();
        String directoryName = path.getPath();

        File directory = new File(directoryName);

        if (!directory.exists() ){
            System.out.println("Existe" + directoryName);
            directory.mkdir();
        }
    }

    public static Aluno csvInitialization() throws Exception{
        Path path = Path.getInstance();
        System.out.println(ConsoleColors.stringColor("O arquivo utilizado para importação é path: src/config/user.csv (no formato: nome,grr,curso,telefone,email )",ConsoleColors.CYAN));
        String line = "";
        String splitBy = ",";
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(path.getPath() + "user.csv"));
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
        Path path = Path.getInstance();
        Pedido pedido = new Pedido();
        pedido.setBarreira(new ArrayList<Disciplina>(50));
        pedido.setSolicita(new ArrayList<Disciplina>(50));
        try {

            BufferedReader br = new BufferedReader(new FileReader(path.getPath() + "pedido.json"));
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
