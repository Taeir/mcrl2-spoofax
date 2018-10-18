package mucalculus.strategies;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.spoofax.interpreter.core.Tools;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.lang.Context;

public class mcrl_invoke_0_0 extends TupleStrategy {
	public static final mcrl_invoke_0_0 instance = new mcrl_invoke_0_0();
	
	private mcrl_invoke_0_0() {}
	
	@Override
	public IStrategoTerm call(Context context, IStrategoTerm[] terms) throws Exception {
		String[] commands = new String[terms.length - 1];
		String basePath = Tools.asJavaString(terms[0]) + "/";
		commands[0] = basePath + Tools.asJavaString(terms[1]);
		for (int i = 2; i < terms.length; i++) {
			commands[i - 1] = Tools.asJavaString(terms[i]);
		}
		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.redirectErrorStream(true);
		pb.redirectOutput(new File(basePath + "/output.graph"));
		pb.directory(new File(basePath));
		
		log("Starting " + Arrays.toString(commands));
		Process process = pb.start();
		process.waitFor(2, TimeUnit.SECONDS);
		
//		InputStream is = process.getInputStream();
//		StringBuilder sb = new StringBuilder();
//		try (InputStreamReader isr = new InputStreamReader(is)) {
//			new Thread(() -> {
//				try {
//					Thread.sleep(3000L);
//					isr.close();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}).start();
//
//			int r;
//			while ((r = isr.read()) != -1) {
//				sb.append((char) r);
//			}
//		}
		return context.getFactory().makeInt(0);
	}
}
