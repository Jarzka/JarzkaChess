package org.voimala.jarzkaengine.programbody;

public abstract class Program {
    
    protected String appName = "";
    
    public Program(final String appName) {
        this.appName = appName;
    }
    
    public final String getAppName() {
        return appName;
    }
    
}
