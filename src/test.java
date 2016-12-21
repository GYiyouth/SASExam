/**
 * Created by geyao on 2016/12/12.
 */
public class test {
	public static void main(String[] args) {
		int a16 = 0x23;
		System.out.println(a16);
		System.out.println(args);
		String str16 = a16 + "";
		System.out.println(str16);
		byte[] bytes = str16.getBytes();
		System.out.println(bytes[0]);


		System.out.println();
		String str = "236d";
		char[] ch = str.toCharArray();
		System.out.println(ch[2]);
		System.out.println(str.substring(2,3));
	}
}
