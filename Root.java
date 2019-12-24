import java.util.*;

public class Root{
	public static void main(String[] args) {
		ArgPars a = new ArgPars(args, "test", "testing");
		
		// Adding the cli options.

		// the options that have argument.
		a.AddOpt("-i", "--ip-addr", "ip_addr", "str","help menue!!");
		a.AddOpt("-p", "--port", "port", "int","help menue!!");
		
		// the positional arguments.
		a.AddOpt("NONE", "NONE", "file", "str","help menue!!");
		a.AddOpt("NONE", "NONE", "path", "str","help menue!!");
		
		// the optional options
		a.AddOpt("-a", "NONE", "NONE", "NONE", "help menue!!");
		a.AddOpt("-e", "NONE", "NONE", "NONE", "help menue!!");
		
		// parsing the arguments that user gave.
		a.parseArg();

		// showing the result.
		
		// if there is any defined optional options then the "optional_arg" Hashtable will 
		// store a "true" value for that option like the example.Else that will store a "null".
		// Now it's up to you how would you use it.
		System.out.println(a.optional_arg.get("a"));
		System.out.println(a.optional_arg.get("e"));
		
		// if there is any defined optional long option then the "optional_arg_value" Hashtable will 
		// store the argument value for that option like the example.Else that will store a "null".
		System.out.println(a.optional_arg_value.get("ip_addr"));
		System.out.println(a.optional_arg_value.get("port"));

		// this are the positional argument.When the program will run user must put those.
		System.out.println(a.positional_argument.get("file"));
		System.out.println(a.positional_argument.get("path"));

	}

}