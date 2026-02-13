import java.util.*;

import java.io.*;

public class shuseki {

    Scanner sc = new Scanner(System.in);

    // 出席データクラス
    class Attendance {
        int studentId;
        String date;
        int lessonId;
        String status;
        String time;

        Attendance(int studentId, String date, int lessonId, String status, String time) {
            this.studentId = studentId;
            this.date = date;
            this.lessonId = lessonId;
            this.status = status;
            this.time = time;
        }
    }

    // 生徒一覧
    Map<Integer, String> studentMap = Map.of(
            1001, "AAA",
            1002, "BBB",
            1003, "CCC"
    );

    // ✅ ArrayListに変更
    ArrayList<Attendance> attendanceList = new ArrayList<>();

    // ==========================
    // メニュー
    // ==========================
    public void start() {
        while (true) {
            System.out.println("------------------------------------------");
            System.out.println("英会話スクール 経営支援システム - 出席管理");
            System.out.println("------------------------------------------");
            System.out.println("[1]入力");
            System.out.println("[2]履歴");
            System.out.println("[0]終了");
            System.out.print(">");

            int menu = sc.nextInt();

            if (menu == 1) {
                inputAttendance();
            } else if (menu == 2) {
                showHistory();
            } else if (menu == 0) {
                break;
            }
        }
    }

    // ==========================
    // 出席入力
    // ==========================
    void inputAttendance() {

        System.out.print("\nレッスンIDを入力してください:\n>");
        int lessonId = sc.nextInt();

        for (int studentId : studentMap.keySet()) {

            System.out.println("\n生徒ID:" + studentId);
            System.out.println("[1]出席 [2]欠席 [3]遅刻");
            System.out.print(">");

            int choice = sc.nextInt();
            String status = "";
            String time = "-";

            if (choice == 1) status = "出席";
            else if (choice == 2) status = "欠席";
            else {
                status = "遅刻";
                System.out.print("時間を入力してください（例：18:00）:\n>");
                time = sc.next();
            }

            Attendance att = new Attendance(studentId, "2026/1/9", lessonId, status, time);
            attendanceList.add(att);

            saveToCSV(att);  // ✅ CSV保存
        }

        System.out.println("CSVに保存しました。");
    }

    // ==========================
    // 履歴表示
    // ==========================
    void showHistory() {

        System.out.print("\n学生IDを入力してください:\n>");
        int id = sc.nextInt();

        System.out.println("------------------------------------------");
        System.out.println("[日付]\t[レッスンID]\t[状況]");

        for (Attendance a : attendanceList) {
            if (a.studentId == id) {
                System.out.println(a.date + "\t" + a.lessonId + "\t\t" + a.status);
            }
        }
    }

    // ==========================
    // CSV保存処理
    // ==========================
    void saveToCSV(Attendance att) {

        try {
            FileWriter fw = new FileWriter("attendance.csv", true);
            fw.write(att.studentId + "," +
                     att.date + "," +
                     att.lessonId + "," +
                     att.status + "," +
                     att.time + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("CSV保存エラー");
        }
    }
}
