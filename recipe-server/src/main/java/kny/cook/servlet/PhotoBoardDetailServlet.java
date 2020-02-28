package kny.cook.servlet;

import java.io.PrintStream;
import java.util.Scanner;
import kny.cook.dao.PhotoBoardDao;
import kny.cook.domain.PhotoBoard;

public class PhotoBoardDetailServlet implements Servlet {

  PhotoBoardDao photoBoardDao;

  public PhotoBoardDetailServlet(PhotoBoardDao photoBoardDao) {
    this.photoBoardDao = photoBoardDao;
  }

  @Override
  public void service(Scanner in, PrintStream out) throws Exception {
    out.println("번호? \n!{}!");
    out.flush();
    int no = Integer.parseInt(in.nextLine());

    PhotoBoard photoBoard = photoBoardDao.findByNo(no);

    if (photoBoard != null) {
      out.printf("번호: %d\n", photoBoard.getNo());
      out.printf("제목: %d\n", photoBoard.getTitle());
      out.printf("등록일: %d\n", photoBoard.getCreatedDate());
      out.printf("조회수: %d\n", photoBoard.getViewCount());
      out.printf("요리: %s\n", photoBoard.getRecipe().getCook());

    } else {
      out.println("해당 번호의 사진 게시물이 없습니다.");
    }
  }
}
