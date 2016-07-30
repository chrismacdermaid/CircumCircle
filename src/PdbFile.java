import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
public class PdbFile {
	int numberOfAtoms;
	Vector<Atom> atomVec;// = new Vector();
	String fileName;
	float thickness;
	public PdbFile()
	{
	}
	public PdbFile(String proteinName) throws IOException
	{
		this.fileName = proteinName+".pdb";
		int count =0;
		@SuppressWarnings("resource")
		BufferedReader inp = new BufferedReader(new FileReader(fileName));
		String s;
		this.atomVec = new Vector<Atom>();
		while( (s = inp.readLine()) != null )
		{
			if((s.startsWith("ATOM")||s.startsWith("HETATM"))&&s.length()>50)
			{
				if(s.contains("DUM"))
				{
					/*count++;
					this.atomVec.addElement(new Atom(s));*/
					thickness  = 2*Math.abs(Float.parseFloat((String) s.subSequence(46, 54)));
				}
				else
				{
					count++;
					this.atomVec.addElement(new Atom(s));
				}
		
			}
			
		}
		this.numberOfAtoms = count;
		inp.close();
	}		
}
