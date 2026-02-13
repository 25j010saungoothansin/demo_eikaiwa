import java.io.*;
import java.util.*;
import java.util.Scanner;


public class Nyuukai_taikai {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        StudentManager manager = new StudentManager();
        StudentView view = new StudentView(scan, manager);
        manager.loadData();
        view.showMainMenu();

        scan.close();
    }
}


class StudentManager {
    private List<NyuukaiTaikai> students = new ArrayList<>();

    public void loadData() {
        students.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("student.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                students.add(new NyuukaiTaikai(
                        Integer.parseInt(data[0]),
                        data[1],
                        data[2],
                        Integer.parseInt(data[3])
                ));
            }
        } catch (Exception e) {
            System.out.println("データ読み込みエラー: " + e.getMessage());
        }
    }

    public void saveData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("student.csv"))) {
            for (NyuukaiTaikai s : students) {
                bw.write(s.getId() + "," + s.getName() + "," + s.getGender() + "," + s.getPoint());
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("データ保存エラー: " + e.getMessage());
        }
    }

    public NyuukaiTaikai addStudent(String name, String gender, int point) {
        NyuukaiTaikai s = new NyuukaiTaikai(getNextId(), name, gender, point);
        students.add(s);
        saveData();
        return s;
    }

    public boolean removeStudent(int id) {
        NyuukaiTaikai target = findById(id);
        if (target == null) return false;
        students.remove(target);
        saveData();
        return true;
    }

    public NyuukaiTaikai findById(int id) {
        for (NyuukaiTaikai s : students) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    public List<NyuukaiTaikai> getAllStudents() {
        return students;
    }

    private int getNextId() {
        return students.stream()
                .mapToInt(NyuukaiTaikai::getId)
                .max()
                .orElse(1000) + 1;
    }
}


class StudentView {
    private Scanner scan;
    private StudentManager manager;

    public StudentView(Scanner scan, StudentManager manager) {
        this.scan = scan;
        this.manager = manager;
    }

    public void showMainMenu() {
        String choice = "";
        while (!choice.equals("4")) {
            System.out.println("1.一覧 2.入会 3.退会 4.終了");
            choice = scan.next();
            if (choice.equals("1")) showAllStudents();
            else if (choice.equals("2")) showJoinMenu();
            else if (choice.equals("3")) taikai();
        }
    }

    private void showJoinMenu() {
        while (true) {
            System.out.println("1:追加 2:終了");
            String choice = scan.next();
            switch (choice) {
                case "1": addOne(); break;
                case "2": return;
            }
        }
    }

    private void showAllStudents() {
        for (NyuukaiTaikai s : manager.getAllStudents()) {
            System.out.println(
                    s.getId() + " | " + s.getName() + " | " + s.getGender() + " | " + s.getPoint()
            );
        }
    }

    private void addOne() {
        System.out.print("お名前：");
        String name = scan.next();

        System.out.print("性別(1:女 2:男):");
        int g = scan.nextInt();

        System.out.print("支払い(1:未 2:済):");
        int p = scan.nextInt();

        int point = 0;
        if (p == 2) {
            System.out.print("支払い金額(円)：");
            point = scan.nextInt() / 1000;
            System.out.println("ポイント:" + point);
        }

        String gender = g == 1 ? "女" : g == 2 ? "男" : "不明";
        System.out.println("1.確定 2.キャンセル");
        int k = scan.nextInt();
        if (k == 1) {
            NyuukaiTaikai s = manager.addStudent(name, gender, point);
            System.out.println("----------------------");
            System.out.println("▼ 登録完了");
            showStudentInfo(s);
            System.out.println("----------------------");
        } else {
            System.out.println("キャンセルしました。");
        }
    }

    private void taikai() {
        System.out.print("学生ID：");
        int id = scan.nextInt();

        NyuukaiTaikai target = manager.findById(id);
        if (target == null) {
            System.out.println("IDが見つかりません。");
            return;
        }

        showStudentInfo(target);

        if (target.getPoint() == 0) {
            manager.removeStudent(id);
            System.out.println("削除しました。");
        } else {
            pointRefund(target);
        }
    }

    private void pointRefund(NyuukaiTaikai target) {
        System.out.println("現在" + target.getPoint() + "ptが残っています");
        System.out.println("消費か返金をどちらか選んでください(1.消費, 2.返金)：");
        int choice = scan.nextInt();

        if (choice == 1 || choice == 2) {
            String type = choice == 1 ? "消費" : "返金";
            logRefund(target, type);

            System.out.println("1.確定 2.キャンセル");
            int c = scan.nextInt();
            if (c == 1) {
                manager.removeStudent(target.getId());
            }

        } else {
            System.out.println("キャンセルしました。");
        }
    }

    private void showStudentInfo(NyuukaiTaikai s) {
        System.out.println(s.getId() + " | " + s.getName() + " | " + s.getPoint());
    }

    private void logRefund(NyuukaiTaikai s, String type) {
        System.out.println(
                s.getId() + " / " + s.getName() + " / " + type + " / pt:" + s.getPoint()
        );
    }
}


class NyuukaiTaikai {
    private int id;
    private String name;
    private String gender;
    private int point;

    public NyuukaiTaikai(int id, String name, String gender, int point) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.point = point;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getGender() { return gender; }
    public int getPoint() { return point; }
}
