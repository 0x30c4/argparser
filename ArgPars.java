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
	public Hashtable<String, String> positional_argument = new Hashtable<String, String>(); 

	public String helpMenu;
	public String programName;
	public String programUsage;
	public String programDescription;
	public String helpPositionalArg;
	public String helpOptionalArg;
	public String gap;
	public int tmp;

	public ArgPars(String[] aa, String programName, String programDescription){
		for (String i: aa) {
			this.args.add(i);
		}
		this.programName =  programName;
		this.programDescription = programDescription + "\n";
		this.programUsage = "Usage: " + programName + " [OPTION]... <ARGUMENTS>...\n";
		this.helpPositionalArg = "positional arguments:\n  ";
		this.helpOptionalArg = "optional arguments:\n  ";
		
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

		this.tmp = 0;
		this.gap = " ";
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
		if (this.checkIfMatch(short_opt, this.opt_pos_short) || this.checkIfMatch(long_opt, this.opt_pos_long) ||
			this.checkIfMatch(value, this.positional_arg) || this.checkIfMatch(short_opt, this.no_val_short) ||
			this.checkIfMatch(value, this.opt_pos_long_name)){
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
			this.helpOptionalArg += String.format("%s%25s%s\n  ",short_opt, this.gap, help_str);
		}

		/* 
		*Finds the optional long options.
		*/
		if (!this.checkIfNone(long_opt) && !this.checkIfNone(short_opt)){
			this.opt_pos_short.add(short_opt);
			this.opt_pos_short_char.add(short_opt.substring(1));
			this.opt_pos_long.add(long_opt);
			this.opt_pos_long_name.add(value);
			this.tmp = 22 - String.format("%s=%s", long_opt, value).length();
			for (int i = 0; i != this.tmp; i++) {
				this.gap += " ";
			}
			this.helpOptionalArg += String.format("%s, %s=%s%s%s\n  ",short_opt ,long_opt, value, this.gap, help_str);
		}

		/* 
		*Finds the positional argument.
		*/
		if (!this.checkIfNone(value) && this.checkIfNone(long_opt) &&
			this.checkIfNone(short_opt)){
			this.positional_arg.add(value);
			this.helpPositionalArg += String.format("%s%23s%s\n  ",value, this.gap, help_str);
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
	public void parseArg(){
		this.AddOpt("-h", "NONE", "NONE", "NONE", "To show this text");
		String arg;
		String error_txt;
		boolean invalid_arg_err_sort = false;
		boolean invalid_arg_err_long = false;
		boolean need_arg_err_value_short = false;
		boolean need_arg_err_value_long = false;
		boolean invalid_positional_arg_err = false;
		boolean l = true;

		char tmp;
		int ii, iii, error ,pc = 0;

		// checking if user wanted the help text.
		if (this.checkIfMatch("-h", this.args) || this.checkIfMatch("--help", this.args)){
			this.showHelpMenu();
		}

		for ( int i = 0; i != this.args.size() ; i++ ) {
			l = true;
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
							need_arg_err_value_short = true;
						}
					}else {
						invalid_arg_err_sort = true;
				// System.out.println(1);

					}
				}else if (arg.startsWith("-") && arg.charAt(1) != '-') {
					if (this.checkIfMatch(arg.substring(0, 2), this.opt_pos_short)){
						ii = this.opt_pos_short.indexOf(arg.substring(0, 2));
						this.optional_arg_value.put(this.opt_pos_long_name.get(ii), arg.substring(2, arg.length()));
						if (need_arg_err_value_short)
							need_arg_err_value_short = false;
						// System.out.println(arg.substring(2, arg.length()));
					}else {
						for (char c: arg.toCharArray()) {
							for (String cc: this.no_val_short_char) {
								if (cc.indexOf(c) != -1){
									ii = this.no_val_short_char.indexOf(cc);
									this.optional_arg.put(this.no_val_short_char.get(ii), true);
									if (invalid_arg_err_sort)
										invalid_arg_err_sort = false;
									// System.out.println(this.no_val_short_char.get(ii));
								}else {
									if (l)
										invalid_arg_err_sort = true;
										// System.out.println(arg.indexOf(c));
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
										if (need_arg_err_value_short){
											need_arg_err_value_short = false;
										}
										if (invalid_arg_err_sort){
											invalid_arg_err_sort = false;
										}
										l = false;
									}else {
										iii = arg.indexOf(c) + 1;
										this.optional_arg_value.
											put(this.opt_pos_long_name.get(ii), 
												arg.substring(iii, arg.length()));
										if (need_arg_err_value_short){
											need_arg_err_value_short = false;
										}
										if (invalid_arg_err_sort){
											invalid_arg_err_sort = false;
										}
										l = false;
									}
									if (arg.length() - arg.indexOf(c) - 1 == 0 && 
										!(this.args.size()  > i + 1) && 
									 	this.args.get(i + 1).startsWith("-")){
										need_arg_err_value_short = true;
									}
								}else if (!arg.startsWith("-")){
									invalid_arg_err_sort = true;
								}
							}
						}
					}
				}else if (arg.startsWith("-") && arg.charAt(1) == '-') {
				// finding the long options.				
					if (this.checkIfMatch(arg, this.opt_pos_long)){
						if (this.args.size() > i + 1){
							ii = this.opt_pos_long.indexOf(arg);
							this.optional_arg_value.put(this.opt_pos_long_name.get(ii), this.args.get(i + 1));
							if (invalid_arg_err_long)
								invalid_arg_err_long = false;
						}else {
							need_arg_err_value_long = true;
						}
					}else {
						for (String ls: this.opt_pos_long) {
							try {
								if (arg.charAt(ls.length()) == '=' &&
								 	arg.substring(0, ls.length()).equals(ls)) {
									ii = this.opt_pos_long.indexOf(ls);
									this.optional_arg_value.put(this.opt_pos_long_name.get(ii), 
										arg.substring(ls.length() + 1, arg.length()));
									if (invalid_arg_err_long)
										invalid_arg_err_long = false;
								}else {
									invalid_arg_err_long = true;
								}
							}catch (StringIndexOutOfBoundsException e){
								invalid_arg_err_long = true;
								System.out.println(e);
							}
						}
					}
				}else if (!arg.startsWith("-")) {
				// finding the positional arguments
					try	{
						if (this.positional_arg.size() != pc && !this.args.get(i - 1).startsWith("-")){
							this.positional_argument.put(this.positional_arg.get(pc), arg);
							pc++;
						}
					}catch (IndexOutOfBoundsException e){
						this.positional_argument.put(this.positional_arg.get(pc), arg);
						pc++;
					}
				}

			}catch (Exception e){
				System.out.println(e);
			}

			System.out.println(invalid_arg_err_long + " " + invalid_arg_err_sort +
			 " "  + need_arg_err_value_short + " " + need_arg_err_value_long +
			  " " + need_arg_err_value_short + " " + need_arg_err_value_long);

			// if (invalid_arg_err_long || invalid_arg_err_sort){
			// 	System.out.println(invalid_arg_err_sort);
			// 	System.out.println(invalid_arg_err_long);
			// 	error_txt = String.format(
			// 				"%s: invalid option -- '%s'\nTry '%s --help' for more information.",
			// 				this.programName, arg, this.programName
			// 				);
			// 	System.out.println(error_txt);
			// 	System.exit(0);
			// }
			// else if (need_arg_err_value_short || need_arg_err_value_long){
			// 	error_txt = String.format(
			// 				"%s: option requires an argument -- '%s'\nTry '%s --help' for more information.",
			// 				this.programName, arg, this.programName
			// 				);
			// 	System.out.println(error_txt);
			// 	System.exit(0);
			// }
		}
		ii = 0;
		iii = this.positional_arg.size();
		pc = this.optional_arg_value.size();
		for (String q: this.args){
			if (!q.startsWith("-")){
				ii++;
			}
		}

		// if ((ii - pc) < iii){
		// 	error_txt = String.format(
		// 				"%s: positional arguments are required\nTry '%s --help' for more information.",
		// 				this.programName, this.programName
		// 				);
		// 	System.out.println(error_txt);
		// 	System.exit(0);			
		// }else if (ii - pc > iii) {
		// 	error_txt = String.format(
		// 				"%s: invalid positional argument \nTry '%s --help' for more information.",
		// 				this.programName, this.programName
		// 				);
		// 	System.out.println(error_txt);
		// 	System.exit(0);
		// }

		if (this.args.size() == 0) {
			error_txt = String.format( 
				"Usage: %s  [OPTION]... <ARGUMENTS>...\nTry '%s --help' for more information.",
				this.programName, this.programName
				);
			System.out.println(error_txt);
			System.exit(0);
		}
		if (this.optional_arg.get("h") != null){
			showHelpMenu();
		}
	}

	public void showHelpMenu(){
		this.helpMenu = String.format("%s\n%s\n%s\n%s", 
									   	"Usage: " + programName + " [OPTION]... <ARGUMENTS>...\n",
										this.programDescription,
									  	this.helpPositionalArg,
										this.helpOptionalArg	  	
									  );
		System.out.println(this.helpMenu);
		System.exit(0);
	}

	// /** this method parse the value from string
	// */
	// public void parseValue(){

	// }

	/*
	int decimalExample = Integer.valueOf("20"); 
	float f = Float.parseFloat(decimal);
	double d = Double.parseDouble(str)
	e.getMessage()
	*/
	// public void result(){
	// 	// Java Program explaining util.Dictionary class Methods 
	// 	// put(), elements(), get(), isEmpty(), keys() 
	// 	// remove(), size()
	// 	System.out.println(arg_cmp);
	// }
}