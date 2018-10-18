package mcrl2.strategies;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.spoofax.interpreter.core.Tools;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.lang.Context;

public class mcrl_invoke_await_0_1 extends TupleStrategy {
	public static final mcrl_invoke_await_0_1 instance = new mcrl_invoke_await_0_1();
	
	private mcrl_invoke_await_0_1() {}
	
	@Override
	public IStrategoTerm call(Context context, IStrategoTerm[] terms, IStrategoTerm baseTerm) throws Exception {
		//Build the command
		String[] commands = new String[terms.length];
		String basePath = Tools.asJavaString(baseTerm) + "/";
		commands[0] = basePath + Tools.asJavaString(terms[0]);
		for (int i = 1; i < terms.length; i++) {
			commands[i] = Tools.asJavaString(terms[i]);
		}
		
		//Build the process
		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.redirectErrorStream(true);
		pb.directory(new File(basePath));
		Process process;
		
		log("Starting " + Arrays.toString(commands));
		try {
			process = pb.start();
		} catch (IOException ex) {
			return context.getFactory().makeString("IO Error while attempting to start program: " + ex.getMessage());
		} catch (SecurityException ex) {
			return context.getFactory().makeString("Permission denied to start program: " + ex.getMessage());
		}
		
		//Read the output stream of the program
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append('\n');
			}
		} catch (IOException ex) {
			return context.getFactory().makeString("IO Error while writing output: " + ex.getMessage());
		}
		
		//Return the program output
		return context.getFactory().makeString(sb.toString());
	}
}
