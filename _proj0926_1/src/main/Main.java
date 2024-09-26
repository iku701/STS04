package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import config.AppCtx;
import dao.MemberDao;

public class Main {

    private static AnnotationConfigApplicationContext ctx = null;
    private static MemberDao memberDao = null;

    public static void main(String[] args) throws IOException {
        ctx = new AnnotationConfigApplicationContext(AppCtx.class);
        memberDao = ctx.getBean(MemberDao.class);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        printHelp();

        while (true) {
        	printHelp();
            System.out.println("명령어를 입력하세요:");
            String command = reader.readLine();
            if (command.equalsIgnoreCase("0")) {
                System.out.println("종료합니다.");
                break;
            }
            if (command.startsWith("1")) {
                memberListCommand();
                continue;
            }
            if (command.startsWith("2")) {
                memberOneCommand(reader);
                continue;
            }
            if (command.startsWith("3")) {
                newMemberCommand(reader);
                continue;
            }
            if (command.startsWith("4")) {
                updateMemberCommand(reader);
                continue;
            }
            
            printHelp();
        }

        ctx.close();
    }

    private static void memberListCommand() {
        memberDao.selectAllMember().forEach(member -> {
            System.out.println(member + "\n");
        });
    }

    private static void memberOneCommand(BufferedReader reader) throws IOException {
        System.out.println("조회할 멤버의 이름을 입력하세요:");
        String name = reader.readLine();

        memberDao.selectOneMember(name).forEach(member -> {
            System.out.println(member + "\n");
        });
    }
    
    private static void newMemberCommand(BufferedReader reader) throws IOException {
        System.out.println("이름을 입력하세요:");
        String name = reader.readLine();

        System.out.println("거주지(시/구)를 입력하세요: (예: 서울시 동대문구)");
        String city = reader.readLine();

        System.out.println("도로명주소(로)를 입력하세요: (예: 길음로)");
        String street = reader.readLine();

        System.out.println("우편번호를 입력하세요:");
        String zipcode = reader.readLine();

        memberDao.insertMember(name, city, street, zipcode);
        System.out.println("새로운 멤버를 추가하였습니다.");
    }
    
    private static void updateMemberCommand(BufferedReader reader) throws IOException {
        System.out.println("수정할 멤버의 이름을 입력하세요:");
        String oldName = reader.readLine();

        System.out.println("새로운 이름을 입력하세요:");
        String newName = reader.readLine();

        System.out.println("새로운 거주지(시/구)를 입력하세요: (예: 서울시 동대문구)");
        String city = reader.readLine();

        System.out.println("새로운 도로명주소(로)를 입력하세요: (예: 길음로)");
        String street = reader.readLine();

        System.out.println("새로운 우편번호를 입력하세요:");
        String zipcode = reader.readLine();

        memberDao.updateMember(oldName, newName, city, street, zipcode);
        System.out.println("멤버 정보가 업데이트되었습니다.");
    }


    private static void printHelp() {
        System.out.println("----------------------");
        System.out.println("명령어 번호를 입력하세요.");
        System.out.println("-0.exit");
        System.out.println("-1.member (모든 멤버 조회)");
        System.out.println("-2.one member (특정 멤버 조회)");
        System.out.println("-3.new member (새로운 멤버 추가)");
        System.out.println("-4.update member (멤버 정보 수정)");
        System.out.println("----------------------");
    }
}
