import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("ログイン");
         System.out.println("1.学生 2.スタッフ");
        Scanner scan = new Scanner(System.in);
        int choice = scan.nextInt();
        if(choice == 1){
            
         lesson_kanri lesson = new lesson_kanri();
        lesson.start();
        
       
        
        


        }else if ( choice == 2){
            Staff();
        }

scan.close();



        
       
    }

    static void Staff(){
        System.out.println("1: 入会・退会 2.出席");
        Scanner scan = new Scanner(System.in);
        int sc = scan.nextInt();
      
        if(sc == 1){
             //入会・退会
             Scanner s = new Scanner(System.in);
            StudentManager manager = new StudentManager();
            StudentView view = new StudentView(s, manager);

            manager.loadData();
            view.showMainMenu();

            s.close();



        } else if (sc == 2){
            shuseki shusseki= new shuseki();
            shusseki.start();
        }
        scan.close();

    }

    
        
}

