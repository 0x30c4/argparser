import java.util.*;

public class ArgPars{
	private List<String> positional_args = new ArrayList<>();
	private List<String> optional_args = new ArrayList<>();
	private List<String> optional_positional_args = new ArrayList<>();
	private List<String> arg_cmp = new ArrayList<>();
	private List<String> args = new ArrayList<>();

	public ArgPars(String[] aa){
		for (String i: aa) {
			this.args.add(i);			
		}
	}
	public void AddOpt(String[] opt_detail){
	/*		
		If there is no use of any if the element then put "NONE" to maintain the
		correct order.
		Element No:
		  0 | If the option dose not consist any argument then use short option. [ -f ]
		  1 | Long option or the double dash option if there is any argument. [ --file-name ]
		  2 | Use it if there is any argument for the long option. [ --file-name=foo.txt || --file-name foo.txt ]
		  3 | This is help string for the option
	*/
		// Error check.
		boolean e = false;
		if (opt_detail.length != 4){
			System.out.println("Error: Need exactly four items.");
		}else{
			for (int i = 0; i != 4; i++) {
				if ((((opt_detail[i].startsWith("-") || 
						opt_detail[i].charAt(1) == '-') || i >= 2)
						|| opt_detail[i].equals("NONE") )) {
					if (i <= 1){
						this.arg_cmp.add(opt_detail[i]);
					}
				}else{
					e = true;
				}
				if (!opt_detail[1].equals("NONE") && opt_detail[2].equals("NONE")) {
					e = true;
				}
			}
			if (e){
				System.err.println("Error: some thing is missing.");
				System.exit(0);
			}
		}
		/* 
		Finds the optional arguments and adding them to the "optional_args" list.
		*/
		if (opt_detail[1].equals("NONE") && opt_detail[2].equals("NONE")){
			optional_args.add(opt_detail[0]);
		}
		/* 
		Finds the positional arguments and adding them to the "positional_args" list.
		*/
		if (opt_detail[0].equals("NONE") && opt_detail[1].equals("NONE")){
			optional_args.add(opt_detail[2]);
		}
		/* 
		Finds the optional arguments with positional value and adding them to the "optional_positional_args" list.
		*/
		if (!opt_detail[0].equals("NONE") && !opt_detail[1].equals("NONE")
		 	&& !opt_detail[2].equals("NONE")){
			optional_args.add(opt_detail[2]);
		}		
	}

	public void parse_arg(){
		// Parsing optional arguments.
		for (String i: this.args){
			
		}
	}

	public void create_help_menu(){
	/*
		usage: t [-h] [--log LOG] int [int ...]

		sum the integers at the command line

		positional arguments:
		  int         an integer to be summed

		optional arguments:
		  -h, --help  show this help message and exit
		  --log LOG   the file where the sum should be written
	*/
		  System.out.println(1);
	}

	public void result(){
		// Java Program explaining util.Dictionary class Methods 
		// put(), elements(), get(), isEmpty(), keys() 
		// remove(), size()
		System.out.println(arg_cmp);
	}
}