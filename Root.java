import java.util.*;

public class Root{
	public static void main(String[] args) {
		ArgPars a = new ArgPars(args, "test", "testing");
		a.AddOpt("-i", "--ip-addr", "ip_addr", "str","help menue!!");
		a.AddOpt("-p", "--port", "port", "int","help menue!!");
		
		a.AddOpt("NONE", "NONE", "file", "str","help menue!!");
		a.AddOpt("NONE", "NONE", "path", "str","help menue!!");
		
		a.AddOpt("-a", "NONE", "NONE", "NONE", "help menue!!");
		a.AddOpt("-e", "NONE", "NONE", "NONE", "help menue!!");
		
		// a.createHelpMenu();
		
		a.parseArg();

		System.out.println(a.optional_arg.get("a"));
		System.out.println(a.optional_arg.get("e"));
		System.out.println(a.optional_arg_value.get("ip_addr"));
		System.out.println(a.optional_arg_value.get("port"));
		System.out.println(a.positional_argument.get("file"));
		System.out.println(a.positional_argument.get("path"));

	}
	// public void report(@Default("Hello") String message) {
 //    	System.out.println("Message: " + message);
	// }
}