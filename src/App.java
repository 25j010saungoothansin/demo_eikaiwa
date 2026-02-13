import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        shuseki shusseki= new shuseki();
        shusseki.start();

        //入会・退会
         Scanner scan = new Scanner(System.in);
        StudentManager manager = new StudentManager();
        StudentView view = new StudentView(scan, manager);

        manager.loadData();
        view.showMainMenu();

        scan.close();

    }
        
}

