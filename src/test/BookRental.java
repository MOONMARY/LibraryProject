package test;
import java.util.InputMismatchException;
import java.util.Scanner;
public class BookRental {
    public static void main(String[] args) {
//      Welcome();
//      CustomerIdInput();
//      JoinCustomer();
//      RentOrReturn();
//      SearchBook();
//      ManagerIdInput();
    }
    public static void Welcome() {
        Scanner sc = new Scanner(System.in);
        System.out.println("도서관에 오신걸 환영합니다 ^^ \n회원이시면? 1 \t 회원이 아니시면? 2 \t 관리자 이시면? 3 \n입력해주세요^^");
        int customer = 0;
        while (true) {
            try {
                customer = sc.nextInt();
                if (customer == 1) {
                    System.out.println("1. 회원이십니다. 회원번호를 입력해주세요.");
                    break;
                } else if (customer == 2) {
                    System.out.println("2. 회원이 아닙니다. 회원가입을 진행하겠습니다.");
                    break;
                } else if (customer == 3) {
                    System.out.println("3. 관리자 이십니다. 관리자 로그인 해주세요.");
                    break;
                } else {
                    System.out.println("1 또는 2 또는 3의 숫자값만 입력해주세요.");
                    continue;
                }
            }
            catch (NumberFormatException n) {
                System.out.println("1 또는 2 또는 3의 숫자값만 입력해주세요.");
                sc.next();
                continue;
            } catch (InputMismatchException i) {
                System.out.println("1 또는 2 또는 3의 숫자값만 입력해주세요.");
                sc.next();
                continue;
            }
        }
        sc.close();
    }
    public static void CustomerIdInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("회원 아이디와 비밀번호를 입력해주세요.");
        System.out.print("회원 아이디: ");
        String customersId = sc.next();
        System.out.print("회원 비밀번호: ");
        String customersPassword = sc.next();
        sc.close();
    }
    public static void ManagerIdInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("관리자 아이디와 비밀번호를 입력해주세요.");
        System.out.print("관리자 아이디: ");
        String managerId = sc.next();
        System.out.print("관리자 비밀번호: ");
        String managerPassword = sc.next();
        sc.close();
    }
    public static void JoinCustomer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("이름, 전화번호, 회원 아이디, 비밀번호를 입력해주세요.");
        System.out.print("이름: ");
        String name = sc.next();
        System.out.print("전화번호: ");
        String phone_number = sc.next();
        System.out.print("회원 아이디: ");
        String customersId = sc.next();
        System.out.print("회원 비밀번호: ");
        String customersPassword = sc.next();
        System.out.println("회원으로 등록되었습니다.");
        sc.close();
    }
    public static void RentOrReturn() {
        Scanner sc = new Scanner(System.in);
        System.out.println("회원으로 로그인 되었습니다. 어떤 작업을 하시겠어요?");
        System.out.println("1. 도서검색 및 대여 \t 2. 도서 반납");
        int rent;
        while (true) {
            try {
                rent = sc.nextInt();
                if (rent == 1) {
                    System.out.println("1. 도서 검색을 먼저 시작하겠습니다.");
                    break;
                } else if (rent == 2) {
                    System.out.println("2. 도서 반납을 선택하셨습니다.");
                    break;
                } else {
                    System.out.println("1 또는 2의 숫자값만 입력해주세요.");
                }
            }
            catch (NumberFormatException n) {
                System.out.println("1 또는 2의 숫자값만 입력해주세요.");
                sc.next();
                continue;
            } catch (InputMismatchException i) {
                System.out.println("1 또는 2의 숫자값만 입력해주세요.");
                sc.next();
                continue;
            }
        }
    }
    public static void SearchBook() {
        Scanner sc = new Scanner(System.in);
        System.out.println("검색하실 방법의 숫자를 입력해 주세요.");
        System.out.println("1. 작가로 검색 \t 2.책 제목으로 검색 \t 3.출판사로 검색");
        int searchNumber = 0;
        while (true) {
            try {
                searchNumber = sc.nextInt();
                if (searchNumber == 1) {
                    System.out.println("1번 누르셨습니다. 작가로 검색하겠습니다.");
                    break;
                } else if (searchNumber == 2) {
                    System.out.println("2번 누르셨습니다. 책 제목으로 검색하겠습니다.");
                    break;
                } else if (searchNumber == 3) {
                    System.out.println("3번 누르셨습니다. 출판사로 검색하겠습니다.");
                    break;
                } else {
                    System.out.println("1 또는 2 또는 3의 숫자값만 입력해주세요.");
                    continue;
                }
            }
            catch (NumberFormatException n) {
                System.out.println("1 또는 2 또는 3의 숫자값만 입력해주세요.");
                sc.next();
                continue;
            } catch (InputMismatchException i) {
                System.out.println("1 또는 2 또는 3의 숫자값만 입력해주세요.");
                sc.next();
                continue;
            }
        }
        sc.close();
    }
}