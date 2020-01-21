package nayoung.cooknayoung.handler;

import java.util.AbstractList;
import nayoung.cooknayoung.domain.Recipe;
import nayoung.cooknayoung.util.Prompt;

public class RecipeDeleteCommand implements Command {

  AbstractList<Recipe> recipeList;
  Prompt prompt;

  public RecipeDeleteCommand(Prompt prompt, AbstractList<Recipe> list) {
    this.prompt = prompt;
    recipeList = list;
  }

  @Override
  public void execute() {
    int index = indexOfRecipe(prompt.inputInt("번호? "));

    if (index == -1) {
      System.out.println("해당 번호의 레시피가 없습니다. ");
      return;
    }

    this.recipeList.remove(index);
    System.out.println("레시피를 삭제했습니다.");
  }

  private int indexOfRecipe(int no) {
    for (int i = 0; i < this.recipeList.size(); i++) {
      if (this.recipeList.get(i).getNo() == no)
        return i;
    }
    return -1;
  }
}

