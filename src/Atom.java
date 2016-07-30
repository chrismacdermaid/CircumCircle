
public class Atom {

/*	String atom;
	String atomSerial;
	String spacer1;
	String atomName;
	String resName;
	String spacer2;
	String chainId;
	String resSeq;
	String iCode;
	String spacer3;*/
	Position pos;
	CharSequence rest;
	CharSequence st;
	CharSequence st1;
	char curentChainId;
	char chainId;
	/*oldChainId and index and units are common to all the atoms "static*/
	static char oldChainId='1';
	static int index=0;
	static char []units = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

	/*String occupancy;
	String tempFactor;
	String segId;
	String element;
	String totalCharge;*/
	
	public Atom()
	{
		
	}
	public Atom(String line)
	{
		String x,y,z;
		curentChainId=line.charAt(21);
		if((curentChainId!=oldChainId) && (((curentChainId > 'a') && (curentChainId < 'z'))||((curentChainId > 'A') && (curentChainId < 'Z'))))
		{
			index++;
			//System.out.println(chainId);
		}
		chainId = units[index%52];
		//chainId=curentChainId;
		String s= line.substring(0, 20)+" "+ chainId +line.substring(22, line.length());
		st = s.subSequence(0, 29);
		x  = new String((String) s.subSequence(30, 37));
		y  = new String((String) s.subSequence(38, 45));
		z  = new String((String) s.subSequence(46, 54));
		pos = new Position(x,y,z);
		if(s.length()>54)
			rest = s.subSequence(54, s.length());
		else
			rest="   ";
		oldChainId=curentChainId;
	}	
}
