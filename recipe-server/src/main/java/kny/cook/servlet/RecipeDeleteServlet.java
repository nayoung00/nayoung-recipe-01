package kny.cook.servlet;

import java.io.PrintStream;
import java.util.Scanner;
import kny.cook.service.RecipeService;
import kny.cook.util.Component;
import kny.cook.util.Prompt;

@Component("/recipe/delete")
public class RecipeDeleteServlet implements Servlet {

  RecipeService recipeService;

  public RecipeDeleteServlet(RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  @Override
  public void service(Scanner in, PrintStream out) throws Exception {

    int no = Prompt.getInt(in, out, "번호? ");

    if (recipeService.delete(no) > 0) {
      out.println("레시피를 삭제했습니다.");
    } else {
      out.println("해당 번호의 레시피가 없습니다.");
    }
  }

}
