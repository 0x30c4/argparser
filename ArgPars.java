import java.util.*;

public class ArgPars{
	private List<String> opt_pos_short = new ArrayList<>();
	private List<String> opt_pos_short_char = new ArrayList<>();
	private List<String> opt_pos_long = new ArrayList<>();
	private List<String> opt_pos_long_name = new ArrayList<>();
	
	private List<String> no_val_short = new ArrayList<>();
	private List<String> no_val_short_char = new ArrayList<>();
	
	private List<String> positional_arg = new ArrayList<>();
	private List<String> args = new ArrayList<String>();
	
	public String[] supportedDataTypes = {"str", "int", "float", "double", "boolean"};

	public Hashtable<String, Boolean> optional_arg = new Hashtable<String, Boolean>(); 
	public Hashtable<String, String> optional_arg_value = new Hashtable<String, String>(); 

	public ArgPars(String[] aa){
		for (String i: aa) {
			this.args.add(i);
		}
	}

	/** 
	*For defining a positional argument just fill other
	*parameters with "NONE".
	*@param short_opt  If the option dose not consist any
	*				   argument then just use short option.
	*@param	long_opt   Long option or the double dash 
	*				   option if there is any argument.
	*@param value_type Use it if there is any argument 
	*			       for the long option.
	*@param value      Define the data type that need to 
	*				   be parsed as positional or optional
	*				   argument.There is five types of data
	*				   is supported [str, int, float, double, boolean].
	*@param help_str   This is help string for the option.
	*/
	public void AddOpt(String short_opt, 
						String long_opt, String value, 
						String value_type, String help_str){

		// Error check.
		if (!short_opt.startsWith("-") && short_opt.length() != 2 && 
				!this.checkIfNone(value) && !this.checkIfNone(value_type) &&
				!this.checkIfNone(long_opt)) {
			System.out.println(
				"Error [Short option]: A short option should allays consist of two char.A '-' followed by a single character\n"
				);
			System.out.println(short_opt + ", " + long_opt + ", " + value + ", " + value_type + ", " + help_str);
			System.exit(0);
		}else if (this.checkIfNone(help_str)){
			System.out.println("Error [Help]: Help menu can't be NONE.");
			System.out.println(short_opt + ", " + long_opt + ", " + value + ", " + value_type + ", " + help_str);		
			System.exit(0);
		}else if ((!this.checkIfLongArg(long_opt)) && !this.checkIfNone(long_opt) || long_opt.length() < 3) {
			System.out.println("Error [Long option]: Long option should consist two '-' or dash and should be more then a 1 word.");
			System.out.println(short_opt + ", " + long_opt + ", " + value + ", " + value_type + ", " + help_str);
			System.exit(0);
		}else if (this.checkIfNone(value_type) &&
					!this.checkIfNone(long_opt) && !this.checkIfNone(value)){
			System.out.println("Error [Data type]: Need to specify the data type if your using a argument");
			System.out.println(short_opt + ", " + long_opt + ", " + value + ", " + value_type + ", " + help_str);
			System.exit(0);
		}else if (!this.dataType(value_type) && !(this.checkIfNone(long_opt) && this.checkIfNone(value) &&
					this.checkIfNone(value_type))) {
			System.out.println("Error [Data type]: This data type is not supported.But this are : [str, int, float, double, boolean]");
			System.out.println(short_opt + ", " + long_opt + ", " + value + ", " + value_type + ", " + help_str);
			System.exit(0);
		}else if (this.checkIfNone(short_opt) && !this.checkIfNone(long_opt)){
			
		}

		// Checking for duplicate
		if (this.checkIfMatch(short_opt, opt_pos_short) || this.checkIfMatch(long_opt, this.opt_pos_long) ||
			this.checkIfMatch(value, positional_arg) || this.checkIfMatch(short_opt, no_val_short)){
			System.out.println("Error [Duplicate found]: Can't use same things twice.");
			System.out.println(short_opt + ", " + long_opt + ", " + value + ", " + value_type + ", " + help_str);
			System.exit(0);
		}

		/* 
		*Finds the optional options (short option).
		*/
		if (!this.checkIfNone(short_opt) && this.checkIfNone(long_opt)){
			this.no_val_short.add(short_opt);
			this.no_val_short_char.add(short_opt.substring(1));
		}

		/* 
		*Finds the optional long options.
		*/
		if (!this.checkIfNone(long_opt) && !this.checkIfNone(short_opt)){
			this.opt_pos_short.add(short_opt);
			this.opt_pos_short_char.add(short_opt.substring(1));
			this.opt_pos_long.add(long_opt);
			this.opt_pos_long_name.add(value);
		}

		/* 
		*Finds the positional argument.
		*/
		if (!this.checkIfNone(value) && this.checkIfNone(long_opt) &&
			this.checkIfNone(short_opt)){
			this.positional_arg.add(value);
		}
	}

	/**@param arg will be checked against the list given as parameter for any match case.
	*@param list which will be checked.
	*@return True if the any match case.False if it's not.
	*/
	public boolean checkIfMatch(String arg, List<String> list){
		int q = 0;
		for (String i: list) {
			if (arg.equals(i))
				q = 1;
		}
		return q == 1 ? true : false;
	}
	/**@param arg It will be checked if it's a supported data type.
	*@return True if the given parameter is a supported data type.False if it's not.
	*/
	public boolean dataType(String arg){
		int q = 0;
		for (String i: this.supportedDataTypes) {
			if (i.equals(arg))		
				q = 1;
		}
		return q == 1 ? true : false;
	}

	/**@param arg It will be checked if it's equal to "NONE".
	*@return True if the given parameter is equal to "NONE".False if it's not.
	*/
	public boolean checkIfNone(String arg){
		if (arg.equals("NONE")){
			return true;
		}else{
			return false;
		}
	}

	/**@param arg It will be checked if it's a long option or not.
	*@return True if the given option is a long option.False if it's not.
	*/
	public boolean checkIfLongArg(String arg){
		if (arg.startsWith("-") && arg.charAt(1) == '-'){
			return true;
		}else{
			return false;
		}
	}

	//This method parse the argument's that user gave
	public void parse_arg(){
		String arg;
		boolean invalid_arg_err_sort = false;
		boolean invalid_arg_err_long = false;
		boolean need_arg_err_value = false;
		char tmp;
		int ii;
		int iii;
		for ( int i = 0; i != this.args.size() ; i++ ) {
			arg = this.args.get(i);
			try{
			// Finding all the short optional options whether they consist any positional argument or not.
				if (arg.startsWith("-") && arg.charAt(1) != '-' && arg.length() == 2){
					if (this.checkIfMatch(arg, this.no_val_short)){
						ii = this.no_val_short.indexOf(arg);
						this.optional_arg.put(this.no_val_short_char.get(ii), true);
					}else if (this.checkIfMatch(arg, this.opt_pos_short)) {
						if (this.args.size() > i + 1 && !this.args.get(i + 1).startsWith("-")) {
							ii = this.opt_pos_short.indexOf(arg);
							this.optional_arg_value.put(this.opt_pos_long_name.get(ii), this.args.get(i + 1));
						}else {
							need_arg_err_value = true;
						}
						// System.out.println(this.args.size() > i + 1 && !this.args.get(i + 1).startsWith("-"));
					}
				}else if (arg.startsWith("-") && arg.charAt(1) != '-') {
					if (this.checkIfMatch(arg.substring(0, 2), this.opt_pos_short)){
						ii = this.opt_pos_short.indexOf(arg.substring(0, 2));
						this.optional_arg_value.put(this.opt_pos_long_name.get(ii), arg.substring(2, arg.length()));
						if (need_arg_err_value)
							need_arg_err_value = false;
						// System.out.println(arg.substring(2, arg.length()));
					}else {
						for (char c: arg.toCharArray()) {
							for (String cc: this.no_val_short_char) {
								if (cc.indexOf(c) != -1){
									ii = this.no_val_short_char.indexOf(cc);
									this.optional_arg.put(this.no_val_short_char.get(ii), true);
									// System.out.println(c);
								}else {
									invalid_arg_err_sort = true;
								}
							}
							// finding the optonal positional arguments 
							for (String cc: this.opt_pos_short_char) {
								if (cc.indexOf(c) != -1){
									ii = this.opt_pos_short_char.indexOf(cc);										
									if (arg.length() - arg.indexOf(c) - 1 == 0 && 
										this.args.size()  > i + 1 && 
									 	!this.args.get(i + 1).startsWith("-")){
										this.optional_arg_value.
											put(this.opt_pos_long_name.get(ii), this.args.get(i + 1));
										if (need_arg_err_value)
											need_arg_err_value = false;
									}else {
										iii = arg.indexOf(c) + 1;
										this.optional_arg_value.
											put(this.opt_pos_long_name.get(ii), 
												arg.substring(iii, arg.length()));
										if (need_arg_err_value)
											need_arg_err_value = false;											
										// System.out.println(arg.length() - arg.indexOf(c) - 1);
										// System.out.println(arg.indexOf(c));
									}
									if (arg.length() - arg.indexOf(c) - 1 == 0 && 
										!(this.args.size()  > i + 1) && 
									 	this.args.get(i + 1).startsWith("-")){
										need_arg_err_value = true;
									}
									if (invalid_arg_err_sort)
										invalid_arg_err_sort = false;
								}else {
									invalid_arg_err_sort = true;									
								}
							}
						}
					}
				}

			}catch (Exception e){
				System.out.println(e);
			}
			System.out.print(invalid_arg_err_sort);
			System.out.print("+");
			System.out.print(invalid_arg_err_long);
			System.out.print("+");
			System.out.print(need_arg_err_value);
			System.out.print("+\n");
		}
	}

	// public void create_help_menu(){
	/*
		usage: t [-h] [--log LOG] int [int ...]

		sum the integers at the command line

		positional arguments:
		  int         an integer to be summed

		optional arguments:
		  -h, --help  show this help message and exit
		  --log LOG   the file where the sum should be written
	*/
		// System.out.println(1);
	// }

	// public void result(){
	// 	// Java Program explaining util.Dictionary class Methods 
	// 	// put(), elements(), get(), isEmpty(), keys() 
	// 	// remove(), size()
	// 	System.out.println(arg_cmp);
	// }
}