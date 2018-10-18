package mcrl2.strategies;

import org.strategoxt.lang.JavaInteropRegisterer;
import org.strategoxt.lang.Strategy;

public class InteropRegisterer extends JavaInteropRegisterer {
    public InteropRegisterer() {
        super(new Strategy[] {
        		mcrl_invoke_0_0.instance,
        		mcrl_invoke_await_0_1.instance
        });
    }
}
