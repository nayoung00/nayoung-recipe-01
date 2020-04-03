package kny.cook.util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import org.apache.ibatis.io.Resources;

// 역할:
// - 클래스를 찾아 객체를 생성한다.
// - 객체가 일을 하는데 필요로하는 의존 객체를 주입한다.
// - 객체를 생성과 소멸을 관리한다.
//
public class ApplicationContext {


  ArrayList<Class<?>> concreteClasses = new ArrayList<>();

  HashMap<String, Object> objPool = new HashMap<>();

  public ApplicationContext(String packageName, HashMap<String, Object> beans) throws Exception {

    Set<String> keySet = beans.keySet();
    for (String key : keySet) {
      objPool.put(key, beans.get(key));
    }

    File path = Resources.getResourceAsFile(packageName.replace('.', '/'));

    // 해당 경로를 뒤져서 모든 클래스의 이름을 알아낸다.
    findClasses(path, packageName);

    for (Class<?> clazz : concreteClasses) {
      try {
        createObject(clazz);
      } catch (Exception e) {
        System.out.printf("%s 클래스의 객체를 생성할 수 없습니다.\n", clazz.getName());

      }
    }
  }

  public void printBeans() {
    System.out.println("--------------------------------------------");
    Set<String> beanNames = objPool.keySet();
    for (String beanName : beanNames) {
      System.out.printf("%s ===> %s\n", beanName, objPool.get(beanName).getClass().getName());
    }
  }

  public Object getBean(String name) {
    return objPool.get(name);
  }

  private Object createObject(Class<?> clazz) throws Exception {
    Constructor<?> constructor = clazz.getConstructors()[0];
    Parameter[] params = constructor.getParameters();

    // 생성자의 파라미터 값 준비한다.
    System.out.printf("%s()\n", clazz.getName());
    Object[] paramValues = getParameterValues(params);

    // 객체를 생성한다.
    Object obj = constructor.newInstance(paramValues);

    // 객체풀에 보관한다.
    objPool.put(getBeanName(clazz), obj);
    System.out.println(clazz.getName() + "객체 생성");

    return obj;
  }

  private String getBeanName(Class<?> clazz) {
    Component compAnno = clazz.getAnnotation(Component.class);
    if (compAnno == null || compAnno.value().length() == 0) {
      // @Component 애노테이션이 없거나 이름을 지정하지 않았으면
      // 클래스 이름을 빈의 이름으로 사용한다.
      return clazz.getName();
    }
    return compAnno.value();
  }

  private Object[] getParameterValues(Parameter[] parmas) throws Exception {
    Object[] values = new Object[parmas.length];
    System.out.println("파라미터 값: {");
    for (int i = 0; i < values.length; i++) {
      values[i] = getParameterValues(parmas[i].getType());
      System.out.printf("%s ==> %s,\n", parmas[i].getType().getSimpleName(),
          values[i].getClass().getName());
    }
    System.out.println("}");
    return values;
  }



  private Object getParameterValues(Class<?> type) throws Exception {
    Collection<?> objs = objPool.values();
    for (Object obj : objs) {
      if (type.isInstance(obj)) {
        return obj;
      }
    }
    Class<?> availableClass = findAvailableClass(type);
    if (availableClass == null) {
      return null;
    }
    return createObject(availableClass);
  }


  private Class<?> findAvailableClass(Class<?> type) throws Exception {
    for (Class<?> clazz : concreteClasses) {
      if (type.isInterface()) {
        Class<?>[] interfaces = clazz.getInterfaces();
        for (Class<?> interfaceInfo : interfaces) {
          if (interfaceInfo == type) {
            return clazz;
          }
        }
      } else if (isChildClass(clazz, type)) {
        return clazz;
      }
    }
    return null;
  }


  private boolean isChildClass(Class<?> clazz, Class<?> type) {
    // 수퍼 클래스로 따라 올라가면서 같은 타입인지 검사한다.
    if (clazz == type) {
      return true;
    }

    if (clazz == Object.class) {
      // 더 이상 상위클래스가 없다면,
      return false;
    }
    return isChildClass(clazz.getSuperclass(), type);
  }



  private void findClasses(File path, String packageName) throws Exception {
    File[] files = path.listFiles(file -> {
      if (file.isDirectory() || file.getName().endsWith(".class") && !file.getName().contains("$"))
        return true;
      return false;
    });
    for (File f : files) {
      String className = String.format("%s.%s", packageName, f.getName().replace(".class", ""));
      if (f.isFile()) {
        Class<?> clazz = Class.forName(className);
        if (isComponentClass(clazz)) {
          concreteClasses.add(clazz);
        }
      } else {
        findClasses(f, className);
      }
    }
  }

  private boolean isComponentClass(Class<?> clazz) {
    if (clazz.isInterface() || clazz.isEnum() || Modifier.isAbstract(clazz.getModifiers())) {
      return false;
    }

    // 클래스에서 @Component 애노테이션 정보를 추출한다.
    Component compAnno = clazz.getAnnotation(Component.class);
    if (compAnno == null) {
      return false;
    }

    // 오직 @Component 애노테이션이 붙은 일반 클래스만이 객체 생성 대상이다.
    return true;
  }
}

