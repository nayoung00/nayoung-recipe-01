package kny.cook.servlet;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import kny.cook.domain.Recipe;

public class RecipeDetailServlet implements Servlet {

  List<Recipe> recipes;

  public RecipeDetailServlet(List<Recipe> recipes) {
    this.recipes = recipes;

  }

  @Override
  public void service(ObjectInputStream in, ObjectOutputStream out) throws Exception {

    int no = in.readInt();

    Recipe recipe = null;
    for (Recipe r : recipes) {
      if (r.getNo() == no) {
        recipe = r;
        break;
      }
    }
    if (recipe != null) {
      out.writeUTF("OK");
      out.writeObject(recipe);
    } else {
      out.writeUTF("FAIL");
      out.writeUTF("해당 번호의 레시피가 없습니다.");
    }

  }

}
