package distribuido;

import java.rmi.Naming;

public class Cliente {
	
	 public static void main(String[] args) { 
	      try { 
	              InterfaceCalc c = (InterfaceCalc) Naming.lookup( "rmi://localhost:1999/Servidor");
	              System.out.println( c.sub(4, 3) ); 
	              System.out.println( c.add(4, 5) ); 
	              System.out.println( c.mul(3, 6) ); 
	              System.out.println( c.div(9, 3) ); 
	      } 
	        catch (Exception e) {
	              System.out.println(e);
	      } 
	   }
}
