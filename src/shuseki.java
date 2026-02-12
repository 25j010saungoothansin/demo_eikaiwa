import java.util.*;

public class shuseki{

    Scanner sc = new Scanner(System.in);

    // 出席データ
    class Attendance {
        String date;
        int lessonId;
        String status;
        String time;

        Attendance(String date, int lessonId, String status, String time) {
            this.date = date;
            this.lessonId = lessonId;
            this.status = status;
            this.time = time;
        }
    }

    Map<Integer, String> studentMap = Map.of(
            1001, "AAA",
            1002, "BBB",
            1003, "CCC"
    );

    Map<Integer, List<Attendance>> historyMap = new HashMap<>();

    // メニュー開始
    public void start() {
        while (true) {
            System.out.println("------------------------------------------");
            System.out.println("英会話スクール 経営支援システム - 出席管理");
            System.out.println("------------------------------------------");
            System.out.println("[1]入力");
            System.out.println("[2]履歴");
            System.out.print(">");

            int menu = sc.nextInt();

            if (menu == 1) {
                inputAttendance();
            } else if (menu == 2) {
                showHistory();
            }
        }
    }

    // 出席入力
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

            Attendance att = new Attendance("2026/1/9", lessonId, status, time);
            historyMap.computeIfAbsent(studentId, k -> new ArrayList<>()).add(att);
        }

        showLessonList(lessonId);
    }

    // レッスン一覧
    void showLessonList(int lessonId) {
        System.out.println("------------------------------------------");
        System.out.println("lesson id " + lessonId + " の一覧");
        System.out.println("------------------------------------------");
        System.out.println("[ID]\t[名前]\t[状況]\t[他]");

        for (int id : studentMap.keySet()) {
            Attendance a = historyMap.get(id).get(historyMap.get(id).size() - 1);
            System.out.println(id + "\t" + studentMap.get(id) + "\t" + a.status + "\t" + a.time);
        }

        System.out.println("\n[1]終了");
        System.out.print(">");
        sc.nextInt();
    }

    // 履歴表示
    void showHistory() {
        System.out.print("\n学生IDを入力してください:\n>");
        int id = sc.nextInt();

        System.out.println("------------------------------------------");
        System.out.println("[日付]\t\t[レッスンID]\t[状況]");

        List<Attendance> list = historyMap.get(id);
        if (list != null) {
            for (Attendance a : list) {
                System.out.println(a.date + "\t" + a.lessonId + "\t\t" + a.status);
            }
        }
    }
}
