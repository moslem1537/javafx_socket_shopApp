package applicationserver;
import java.io.Serializable;
import java.util.*;


public class Response implements Serializable
{ 		
	private static final long serialVersionUID = 1L;	
	private ArrayList<String> ar = new ArrayList<String>();
	private int cols;	
	
	

	public Response() {}
	
	

	public Response(final ArrayList<String> v, int column)
	{
		 this.ar = v;
		 this.cols = column;
	}
	
		

	public ArrayList<String>  getValue() 
	{
		return this.ar;
	}
	

	public String value(int x, int y) {
		return this.ar.get(y * this.cols + x);
	}
}