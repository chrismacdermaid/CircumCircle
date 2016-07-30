
public class Position {
	public float x;
	public float y;
	public float z;
	
	public Position(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Position(String x,String y, String z)
	{
		this.x = Float.parseFloat(x);
		this.y = Float.parseFloat(y);
		this.z = Float.parseFloat(z);
	}
	
	public String toString()
	{
		return (String.format("%8.3f",x)+String.format("%8.3f",y)+ String.format("%8.3f",z));
		
	}
}
