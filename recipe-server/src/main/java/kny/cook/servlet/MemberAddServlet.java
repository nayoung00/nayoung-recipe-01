package kny.cook.servlet;

import java.io.PrintStream;
import java.util.Scanner;
import kny.cook.dao.MemberDao;
import kny.cook.domain.Member;
import kny.cook.util.Prompt;

public class MemberAddServlet implements Servlet {

  MemberDao memberDao;

  public MemberAddServlet(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void service(Scanner in, PrintStream out) throws Exception {
    Member member = new Member();

    member.setName(Prompt.getString(in, out, "이름? "));
    member.setEmail(Prompt.getString(in, out, "이메일? "));
    member.setPassword(Prompt.getString(in, out, "암호? "));
    member.setPhoto(Prompt.getString(in, out, "사진? "));
    member.setTel(Prompt.getString(in, out, "전화? "));

    out.flush();
    if (memberDao.insert(member) > 0) {
      out.println("새 회원을 등록 했습니다.");
    } else {
      out.println("새 회원을 등록 했습니다.");
    }
  }
}
