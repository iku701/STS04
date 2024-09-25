package test;

public class MyObject {
	@Myanno(number=4)
	public void test1() {
		System.out.println("This is test1");
	}
	
	@Myanno(value = "My new Annotation")
	public void test2() {
		System.out.println("this is test2");
	}
}
