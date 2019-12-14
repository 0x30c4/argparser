import java.util.*;

public class ArgPars{
	private List<String> positional_args = new ArrayList<>();
	private List<String> optional_args = new ArrayList<>();
	private List<String> opt_pos_long = new ArrayList<>();
	private List<String> opt_pos_short_chr = new ArrayList<>();
	private List<String> opt_pos_long_chr = new ArrayList<>();
	private List<String> opt_pos_short = new ArrayList<>();
	private List<String> opt_pos_name = new ArrayList<>();
	private List<String> arg_cmp = new ArrayList<>();
	private List<String> _args = new ArrayList<String>();

	Hashtable<String, Boolean> positional_arg = new Hashtable<String, Boolean>(); 
	Hashtable<String, String> positional_arg_value = new Hashtable<String, String>(); 

	public ArgPars(String[] aa){
		for (String i: aa) {
			this._args.add(i);
		}
		// for (int i = 0 ; i <= this._args.size(); i++) {
		// 	System.out.println(this._args.get(i));
		// }
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
		Finds the optional arguments (short option) and adding them to the "optional_args" list.
		*/
		if (opt_detail[1].equals("NONE") && opt_detail[2].equals("NONE")){
			this.optional_args.add(opt_detail[0]);
		}
		/* 
		Finds the positional arguments and adding them to the "positional_args" list.
		*/
		if (opt_detail[0].equals("NONE") && opt_detail[1].equals("NONE")){
			this.positional_args.add(opt_detail[2]);
		}
		/* 
		Finds the optional arguments (long option) with positional value and adding them to the "optional_positional_args" list.
		*/
		if (!opt_detail[0].equals("NONE") && !opt_detail[1].equals("NONE")
		 	&& !opt_detail[2].equals("NONE")){
			this.opt_pos_short.add(opt_detail[0]);
			this.opt_pos_long.add(opt_detail[1]);
			this.opt_pos_name.add(opt_detail[2]);
		}		
	}

	public void parse_arg(){
		String positional_value = "null";
		boolean invalid_arg_err_sort = false;
		boolean invalid_arg_err_long = false;
		boolean need_arg_err_value = false;
		int index;
		// Parsing optional arguments.
		for (int i = 0 ; i <= this._args.size() - 1 ; i++) {
			// Looking for the short options.
			if (this._args.get(i).startsWith("-") && this._args.get(i).charAt(1) != '-') {
				for (String _so: this.optional_args) {
					index = this.optional_args.indexOf(_so);
					if (_so.equals(this._args.get(i))){
							this.positional_arg.put(this.optional_args.get(index), true);
					}else {
						invalid_arg_err_sort = true;
						// System.out.println(this._args.get(i));
					}
				}
				// Looking for optional values provided after the short option.
				for (String _sa: this.opt_pos_short) {
					index = this.opt_pos_short.indexOf(_sa);
					try {	
						if (this._args.get(i).equals(_sa) && 
							(this._args.size() > i + 1)){
							positional_value = this._args.get(i + 1);
							this.positional_arg_value.put(this.opt_pos_name.get(index), positional_value);
							if (invalid_arg_err_sort)
								invalid_arg_err_sort = false;
						}else {
							need_arg_err_value = true;
						}
						if (this._args.get(i).substring(0, 2).equals(_sa)) {
							positional_value = this._args.get(i).substring(2, this._args.get(i).length());
							this.positional_arg_value.put(opt_pos_name.get(index), positional_value);
							if (invalid_arg_err_sort)
								invalid_arg_err_sort = false;							
						}
						if (!this._args.get(i).equals(_sa) &&
							!this._args.get(i).substring(0, 2).equals(_sa)) {
							invalid_arg_err_sort = true;
							System.out.println(this._args.get(i));
							System.out.println(this._args.get(i).equals(_sa) + _sa);
						}
					}catch (Exception e){
						// System.out.println(e);
					}
			
			// Error detection 			
			if (invalid_arg_err_sort || invalid_arg_err_long) {
				System.out.println("Invalid argument error 11");
				System.out.println(invalid_arg_err_sort +" "+ invalid_arg_err_long);
				System.exit(0);
			}else if (need_arg_err_value) {
				System.out.println("Need an argument");
				System.exit(0);
			}					

				}
				// Some change needed in the near future.
			}else if (this._args.get(i).startsWith("-") && this._args.get(i).charAt(1) == '-') {// && this._args[i].length() => 3) {
			// Looking for long options.
				for (int ls = 0 ; ls <= this.opt_pos_long.size() ; ls++) {
					try {
						if (this._args.get(i).equals(this.opt_pos_long.get(ls))){
							if (!(this._args.size() < (i + 2))){
								positional_value = this._args.get(i + 1);
								this.positional_arg_value.put(this.opt_pos_name.get(ls), positional_value);
							}else {
							// Need an argument.Not done yet. :v
								System.out.println("Need an argument");
							}
						}else if (this._args.get(i).charAt(this.opt_pos_long.get(ls).length()) == '=' &&
								 	this._args.get(i).substring(0, this.opt_pos_long.get(ls).length()).equals(this.opt_pos_long.get(ls))){
							positional_value = this._args.get(i).substring(this.opt_pos_long.get(ls).length() + 1, this._args.get(i).length());
							this.positional_arg_value.put(this.opt_pos_name.get(ls), positional_value);
						}
					}catch (Exception e){
						// System.out.println(e);
					}
				}
			}
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

	// public void result(){
	// 	// Java Program explaining util.Dictionary class Methods 
	// 	// put(), elements(), get(), isEmpty(), keys() 
	// 	// remove(), size()
	// 	System.out.println(arg_cmp);
	// }
}