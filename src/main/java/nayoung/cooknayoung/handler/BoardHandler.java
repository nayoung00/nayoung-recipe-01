package nayoung.cooknayoung.handler;

import java.sql.Date;
import java.util.Scanner;
import nayoung.cooknayoung.domain.Board;

public class BoardHandler {

  Board[] boards;
  int boardCount = 0;

  Scanner input;

  static final int BOARD_SIZE = 100;

  public BoardHandler(Scanner input) {
    this. input = input;
    this. boards = new Board[BOARD_SIZE];
  }

  public BoardHandler(Scanner input, int capacity) {
    this.input = input;
    if (capacity < BOARD_SIZE || capacity > 10000)
      this.boards = new Board[BOARD_SIZE];
    else
      this.boards = new Board[capacity];
  }

  public void addBoard() {
    Board board = new Board();

    System.out.print("번호? ");
    board.no = input.nextInt();
    input.nextLine(); // 줄바꿈 기호 제거용

    System.out.print("내용? ");
    board.title = input.nextLine();

    board.date = new Date(System.currentTimeMillis());
    board.viewCount = 0;

    this.boards[this.boardCount++] = board;
    System.out.println("저장하였습니다.");
  }

  public void listBoard() {
    for (int i = 0; i < this.boardCount; i++) {
      Board b = this.boards[i];
      System.out.printf("%d, %s, %s, %d\n", 
          b.no, b.title, b.date, b.viewCount);
    }

  }
  public void detailBoard() {
    System.out.println("게시물 번호? ");
    int no = input.nextInt();
    input.nextLine(); // 숫자 뒤의 남은 공백 제거 

    Board board = null;

    for (int i = 0; i < this.boardCount; i++) {
      if (this.boards[i].no == no) {
        board = this.boards[i];
        break;       
      }

    }   
    if (board == null) {
      System.out.println("게시물 번호가 유효하지 않습니다. ");
      return;
    }
    System.out.printf("번호: %d\n", board.no);
    System.out.printf("제목: %s\n", board.title);
    System.out.printf("등록일: %s\n", board.date);
    System.out.printf("조회수: %d\n", board.viewCount);    
  }
}