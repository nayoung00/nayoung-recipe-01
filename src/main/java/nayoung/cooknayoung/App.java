package nayoung.cooknayoung;

import java.util.Scanner;
import nayoung.cooknayoung.handler.BoardHandler;
import nayoung.cooknayoung.handler.MemberHandler;
import nayoung.cooknayoung.handler.RecipeHandler;

public class App {

  static Scanner keyboard = new Scanner(System.in);

  public static void main(String[] args) {

    RecipeHandler recipe = new RecipeHandler(keyboard);

    MemberHandler member = new MemberHandler(keyboard);

    BoardHandler board = new BoardHandler(keyboard);
    BoardHandler board2 = new BoardHandler(keyboard);
    BoardHandler board3 = new BoardHandler(keyboard);

    String command;

    do {
      System.out.print("\n명령> ");
      command = keyboard.nextLine();

      switch (command) {
        case "/recipe/add":
          recipe.addRecipe();
          break;
        case "/recipe/list":
          recipe.listRecipe();
          break;
        case "/member/add":
          member.addMember();
          break;
        case "/member/list":
          member.listMember();
          break;
        case "/board/add":
          board.addBoard();
          break;
        case "/board/list":
          board.listBoard();
          break;
        case "/board/detail":
          board.detailBoard();
          break;
        case "/board2/add":
          board2.addBoard();
          break;
        case "/board2/list":
          board2.listBoard();
          break;
        case "/board2/detail":
          board2.detailBoard();
          break;
        case "/board3/add":
          board3.addBoard();
          break;
        case "/board3/list":
          board3.listBoard();
          break;
        case "/board3/detail":
          board3.detailBoard();
          break;

        default:
          if (!command.equalsIgnoreCase("quit")) {
            System.out.println("실행할 수 없는 명령입니다.");
          }
      }
    } while (!command.equalsIgnoreCase("quit"));

    System.out.println("안녕!");

    keyboard.close();
  }
}

