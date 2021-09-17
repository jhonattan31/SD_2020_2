package distribuido;

import java.rmi.Naming;

public class Servidor {
	public Servidor() { 
	      try { 
	    	  	System.setProperty("java.rmi.server.hotname", "192.168.0.136");
	    	  	
	            InterfaceCalc c = new Calculadora(); 
	            Naming.rebind("RMI://192.168.0.136:1999/Servidor", c); 
	            
	      } catch (Exception e) { 
	            System.out.println("Error: " + e); 
	      }
	   } 
	   public static void main(String args[]) { 
	         new Servidor();
	   }
}
