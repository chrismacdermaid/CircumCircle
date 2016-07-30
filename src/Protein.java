import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.lang.Math;

public class Protein {
	String nameOfProtein;
	PdbFile file;
	float angleOfView;
	Position pos;
	Position maxX;
	Position maxY;
	Position maxZ;
	Position minX;
	Position minY;
	Position minZ;
	Formatter fmt1;

	public Protein(String nameOfProtein, float radius,Formatter fmt1) throws IOException
	{		
		this.nameOfProtein = nameOfProtein;
		this.file= new PdbFile(nameOfProtein);
		this.fmt1=fmt1;
		maxX=new Position(0,0,0);
		maxY=new Position(0,0,0);
		maxZ=new Position(0,0,0);
		minX=new Position(0,0,0);
		minY=new Position(0,0,0);
		minZ=new Position(0,0,0);
		//rotateAroundYAxis((float)(Math.PI/2));
		calculate();
		calculateAngleOfView(radius);		
	}
	//checked
	void calculateAngleOfView(float radius) {
		//u*v=|u||v|cos a
		Position r1 = new Position(maxY.x + radius,maxY.y, maxY.z);
		Position r2 = new Position(minY.x + radius,minY.y, minY.z);
		float sklar  = (r1.x*r2.x)+(r1.y*r2.y)+(r1.z*r2.z);
		float lenMax = (float) Math.sqrt(r1.x*r1.x + r1.y*r1.y + r1.z*r1.z);
		float lenMin = (float) Math.sqrt(r2.x*r2.x + r2.y*r2.y + r2.z*r2.z);
		angleOfView  = (float) Math.acos(sklar/(lenMax*lenMin));
		
	}
	//checked
	void calculate()
	{
		Atom atom;

		for (int i = 0; i < file.atomVec.size(); i++)
		{
			atom = file.atomVec.get(i);
			if(atom.pos.x>maxX.x)
			{
				maxX.x = atom.pos.x;
				maxX.y = atom.pos.y;
				maxX.z = atom.pos.z;
			}
			else {
				if(atom.pos.x<minX.x)
				{
					minX.x = atom.pos.x;
					minX.y = atom.pos.y;
					minX.z = atom.pos.z;
				}
			}
			
			//atom = file.atomVec.get(i);
			if(atom.pos.y>maxY.y)
			{
				maxY.x = atom.pos.x;
				maxY.y = atom.pos.y;
				maxY.z = atom.pos.z;
			}
			else {
				if(atom.pos.y<minY.y)
				{
					minY.x = atom.pos.x;
					minY.y = atom.pos.y;
					minY.z = atom.pos.z;
				}
			}
			/////////////////////////
			
			if(atom.pos.z>maxZ.z)
			{
				maxZ.x = atom.pos.x;
				maxZ.y = atom.pos.y;
				maxZ.z = atom.pos.z;
			}
			else {
				if(atom.pos.z<minZ.z)
				{
					minZ.x = atom.pos.x;
					minZ.y = atom.pos.y;
					minZ.z = atom.pos.z;
				}
			}			
		}
		
	}
			
	void translate(float x, float y, float z)
	{
		Atom atom;
		for (int i = 0; i < file.atomVec.size(); i++)
		{
			atom = file.atomVec.get(i);
			atom.pos.x+=x;
			atom.pos.y+=y;
			atom.pos.z+=z;
		}
	}
	
	void print()
	{
		Atom atom;
		for (int i = 0; i < file.atomVec.size(); i++)
		{
			atom = file.atomVec.get(i);
			fmt1.format("%26s %8.3f%8.3f%8.3f%s\n",atom.st,atom.pos.x,atom.pos.y,atom.pos.z,atom.rest);
		}
	
	}

	void rotateAroundXAxis(float angle) throws IOException
	{
		//PrintWriter writer = new PrintWriter("11.pdb", "UTF-8");
		//Formatter fmt1 = new Formatter("11.pdb");
		Atom atom;
		float x,y,z;
		for (int i = 0; i < file.atomVec.size(); i++)
		{
			atom = file.atomVec.get(i);
			x=atom.pos.x;
			y = (float) (((atom.pos.y)*Math.cos(angle)) - ((atom.pos.z)*Math.sin(angle)));
			z = (float) (((atom.pos.y)*Math.sin(angle)) + ((atom.pos.z)*Math.cos(angle)));
			atom.pos.x=x;
			atom.pos.y=y;
			atom.pos.z=z;
		}
	}

	void rotateAroundYAxis(float angle) throws IOException
	{
		float x,y,z;
		Atom atom;
		for (int i = 0; i < file.atomVec.size(); i++)
		{
			atom = file.atomVec.get(i);
			x=(float) (((atom.pos.x)*Math.cos(angle)) + ((atom.pos.z)*Math.sin(angle)));
			y = atom.pos.y;
			z = (float) (((atom.pos.z)*Math.cos(angle)) - ((atom.pos.x)*Math.sin(angle)));
			atom.pos.x=x;
			atom.pos.y=y;
			atom.pos.z=z;		
		}
	}
	
	void rotateAroundZAxis(float angle) throws IOException
	{
		float x,y,z;
		Atom atom;
		for (int i = 0; i < file.atomVec.size(); i++)
		{
			atom = file.atomVec.get(i);
			x=(float) (((atom.pos.x)*Math.cos(angle)) - ((atom.pos.y)*Math.sin(angle)));
			y = (float) (((atom.pos.x)*Math.sin(angle)) + ((atom.pos.y)*Math.cos(angle)));
			z = atom.pos.z;
			atom.pos.x=x;
			atom.pos.y=y;
			atom.pos.z=z;
			
		}
	}


}