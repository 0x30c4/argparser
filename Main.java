public class Main{
	public static void main(String[] args) {
		// creating an object.

		// the 1st parameter is the "args" that user supply's.
		// 2nd is the program name.
		// 3rd is the program description.
		ArgPars a = new ArgPars(args, "test", "testing");
		
		// Adding the cli options.

		// Defing the options that have argument.
		a.AddOpt("-i", "--ip-addr", "ip_addr", "str","help menue!!");
		a.AddOpt("-p", "--port", "port", "int","help menue!!");
		
		// Defing the positional arguments.
		a.AddOpt("NONE", "NONE", "file", "str","help menue!!");
		
		// Defing the optional options
		a.AddOpt("-q", "NONE", "NONE", "NONE", "help menue!!");
		
		// parsing the arguments that user gave.
		a.parseArg();

		// showing the result.
		
		// if there is any defined optional options then the "optional_arg" Hashtable will 
		// store a "true" value for that option like the example.Else that will store a "null".
		// Now it's up to you how would you use it.
		if (a.optional_arg.get("q") != null)
			System.out.println("The [q] option is used.");
		
		// if there is any defined optional long option then the "optional_arg_value" Hashtable will 
		// store the argument value for that option like the example.Else that will store a "null".
		if (a.optional_arg_value.get("ip_addr") != null)
			System.out.println("The ip is : " + a.optional_arg_value.get("ip_addr"));
		
		if (a.optional_arg_value.get("port") != null)
			System.out.println("The port number is : " + a.optional_arg_value.get("port"));

		// this are the positional argument.When the program will run user must put those.
		if (a.positional_argument.get("file") != null)
			System.out.println("The file name is : " + a.positional_argument.get("file"));
	}
}