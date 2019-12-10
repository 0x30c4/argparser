public class Root{
	public static void main(String[] args) {
		ArgPars a = new ArgPars(args);
		String[] s = {"-p", "--port", "port", "help menue!!"};
		String[] ss = {"-f", "--file-name", "file_name", "help menue!!"};
		String[] sss = {"-i", "--ip-addr", "ip_addr", "help menue!!"};
		a.AddOpt(s);
		a.AddOpt(ss);
		a.AddOpt(sss);
		a.parse_arg();
		a.result();

		// for (String i: args) {
		// 	if (i.equals("s")){
		// 		NetHandler r = new NetHandler("127.0.0.1", 6969, "a.png");
		// 	}else if (i.equals("r")){
		// 		NetHandler r = new NetHandler("None", 6969, "t.png");
		// 	}
		// }
	}
}