public class Example{
	public static void main(String[] args) {
		// Creating an object.
		
		// The 1st parameter is the "args" that user supply's.
		// 2nd is the program name.
		// 3rd is the program description.
		ArgPars a = new ArgPars(args, "test", "Just for showing the usage.");
		
		// Adding the cli options.

		// Defing the options that have argument.
		a.AddOpt("-i", "--ip-addr", "ip_addr", "str","help menu!!");
		a.AddOpt("-p", "--port", "port", "int","help menu!!");
		
		// Defing the positional arguments.
		a.AddOpt("NONE", "NONE", "file", "str","help menu!!");
		
		// Defing the optional options
		a.AddOpt("-q", "NONE", "NONE", "NONE", "help menu!!");
		
		// Parsing the arguments that user gave.
		a.parseArg();

		// Showing the result.
		
		// If there is any defined optional options then the "optionalArg" Hashtable will 
		// store a "true" value for that option like the example.Else that will store a "null".
		// Now it's up to you how would you use it.
		if (a.optionalArg.get("q") != null)
			System.out.println("The [q] option is used.");
		
		// If there is any defined optional long option then the "optionalArgValue" Hashtable will 
		// store the argument value for that option like the example.Else that will store a "null".
		if (a.optionalArgValue.get("ip_addr") != null)
			System.out.println("The ip is : " + a.optionalArgValue.get("ip_addr"));
		
		if (a.optionalArgValue.get("port") != null)
			System.out.println("The port number is : " + a.optionalArgValue.get("port"));

		// This are the positional argument.When the program will run user must put those.
		if (a.positionalArgument.get("file") != null)
			System.out.println("The file name is : " + a.positionalArgument.get("file"));
	}
}
