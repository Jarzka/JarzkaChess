package org.voimala.jarzkachess.programbody;

import javax.swing.JOptionPane;

public class main {
    public static void main(String[] args) {
        try {
            ChessProgram.getInstance().run();
        } catch (Exception e) {
            String errorMessage = "Serious error occurred and the execution can not continue." + "\n\n";
            errorMessage += "Error information" + ": " + "\n";
            errorMessage += e.toString() + "\n";
            errorMessage += "Call stack" + ": " + "\n";
            for (StackTraceElement element : e.getStackTrace()) {
                errorMessage += element.toString() + "\n";
            }
            
            JOptionPane.showMessageDialog(null, errorMessage);
            System.exit(0);
        }
        
    }
}
