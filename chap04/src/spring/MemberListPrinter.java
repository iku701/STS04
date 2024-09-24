package spring;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

public class MemberListPrinter {
	private MemberDao memberDao;
	private MemberPrinter printer;
	
	public MemberListPrinter() {
	}
	
	@Autowired
	public MemberListPrinter(MemberDao memberDao, MemberPrinter printer) {
		this.memberDao = memberDao;
		this.printer = printer;
	}
	
	public void printAll() {
		Collection<Member> members = memberDao.selectAll();
		members.forEach(m -> printer.print(m));
	}
}
