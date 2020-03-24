package kny.cook.servlet;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import kny.cook.domain.Member;
import kny.cook.service.MemberService;

public class MemberListServlet implements Servlet {

  MemberService memberService;

  public MemberListServlet(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public void service(Scanner in, PrintStream out) throws Exception {
    List<Member> members = memberService.list();

    for (Member member : members) {
      out.printf("%d, %s, %s, %s, %s, %s\n", member.getNo(), member.getName(), member.getEmail(),
          member.getRegisteredDate(), member.getTel(), member.getPhoto());
    }
  }
}
