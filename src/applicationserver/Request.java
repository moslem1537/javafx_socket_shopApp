package applicationserver;

import java.io.Serializable;


public class Request implements Serializable{ 		
	
	private static final long serialVersionUID = 1L;
	private final String s;


	public Request ( final String s) {	 
	  this.s = s;	 
	  }
	
	

	public String getValue() {
	  return this.s;
	  }
}
