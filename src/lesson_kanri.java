import java.io.*;
import java.util.*;

public class lesson_kanri {

    String studentFile = "student.csv";
    String attendanceFile = "attendance.csv";

    public void start() {
        Scanner sc = new Scanner(System.in);

        System.out.println("レッスン管理");
        System.out.println("[1]予約");
        System.out.println("[2]キャンセル");
        System.out.print("> ");
        int menu = sc.nextInt();

        if (menu == 1) reserve(sc);
        if (menu == 2) cancel(sc);
    }

    void reserve(Scanner sc) {
        System.out.print("日付(例:2026-04-15): ");
        String date = sc.next();

        System.out.print("lessonId(301など): ");
        String lessonId = sc.next();

        updateStudent("1001", lessonId);
        writeAttendance(date, lessonId, "1001", "RESERVE");

        System.out.println("予約完了");
    }

    void cancel(Scanner sc) {
        System.out.print("日付: ");
        String date = sc.next();

        updateStudent("1001", "");
        writeAttendance(date, "0", "1001", "CANCEL");

        System.out.println("キャンセル完了");
    }

    void updateStudent(String id, String lessonId) {
        try {
            List<String> lines = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(studentFile));
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.split(",");
                if (s[0].equals(id)) {
                    lines.add(id + "," + s[1] + "," + lessonId);
                } else {
                    lines.add(line);
                }
            }
            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(studentFile));
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {}
    }

    void writeAttendance(String date, String lessonId, String id, String action) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(attendanceFile, true));
            bw.write(date + "," + lessonId + "," + id + "," + action);
            bw.newLine();
            bw.close();
        } catch (Exception e) {}
    }
}

