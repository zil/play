package fun.play.interview;

import com.google.common.base.Strings;

public class Misc {
	private static void param(String s,char[] chars) {
		s = "changed??";
		chars[0]='?';
	}
	
	public static void main(String[] args) {
		String string = Strings.repeat("hello",1);
		char[] cs = {'a','b','c'};
		param(string, cs);
		System.err.printf("%s %c",string,cs[0]);
	}
}
