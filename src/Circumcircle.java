import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Vector;

public class Circumcircle {
  float radius;
  float thickness;
  float angle;
  int numberOfProteins;
  String email;
  Formatter fmt1;
  String filename;
  String filelipid;
  float maxYY = 0, minYY=0;
  Vector<Protein> proteins;

  void determine2d() throws IOException {
    float ninetydeg = (float)(Math.PI / 2.0);

    for(int i=0; i<numberOfProteins ;i++) {
      proteins.elementAt(i).rotateAroundYAxis(ninetydeg);
      proteins.elementAt(i).rotateAroundXAxis(ninetydeg);
      proteins.elementAt(i).translate(radius+(thickness/2), 0, 0);
      proteins.elementAt(i).rotateAroundZAxis((float)Math.toRadians(i*(angle/numberOfProteins)));
      proteins.elementAt(i).print();
      if(proteins.elementAt(i).maxY.y > maxYY)
	maxYY=proteins.elementAt(i).maxY.y;
      if(proteins.elementAt(i).minY.y < minYY)
	minYY=proteins.elementAt(i).minY.y;
    }
  }

  void determine3d() throws IOException
  {
    int i;
    float ninetydeg = (float)(Math.PI / 2.0);
    float thirtydeg = (float)(Math.PI / 6.0);
    float fiftendeg = (float)(Math.PI / 12.0);
    float rt = (float)(radius + (thickness / 2.0)); 

    if (angle <= 180) {

      for(i=0; i < numberOfProteins / 2.0; i++) {
	proteins.elementAt(i).rotateAroundYAxis(ninetydeg);
	proteins.elementAt(i).translate(rt, 0, 0);
	proteins.elementAt(i).rotateAroundZAxis(thirtydeg);
	proteins.elementAt(i).rotateAroundYAxis((float)Math.toRadians(i * (360.0 / ((numberOfProteins / 2)-1) )));
	proteins.elementAt(i).print();
      }

      for(; i < numberOfProteins; i++) {
	proteins.elementAt(i).rotateAroundYAxis(ninetydeg);
	proteins.elementAt(i).translate(rt, 0, 0);
	proteins.elementAt(i).rotateAroundZAxis(fiftendeg);
	proteins.elementAt(i).rotateAroundYAxis((float)Math.toRadians(i * (360.0 / ((numberOfProteins / 2)+1) )));
	proteins.elementAt(i).print();
      }

    } else {

      System.out.print("Your Mom");
      for(i=0; i < numberOfProteins / 2.0; i++) {
	proteins.elementAt(i).rotateAroundYAxis(ninetydeg);
	proteins.elementAt(i).translate(rt, 0, 0);
	proteins.elementAt(i).rotateAroundZAxis(thirtydeg);
	proteins.elementAt(i).rotateAroundYAxis((float)Math.toRadians(i * (360.0  / (numberOfProteins / 2.0) )));
	proteins.elementAt(i).print();
      }

      for(; i<numberOfProteins; i++) {
	proteins.elementAt(i).rotateAroundYAxis(ninetydeg);
	proteins.elementAt(i).translate(rt, 0, 0);
	proteins.elementAt(i).rotateAroundZAxis((float)(-1.0*thirtydeg));
	proteins.elementAt(i).rotateAroundYAxis((float)Math.toRadians(i * (360.0 / (numberOfProteins / 2.0) )));
	proteins.elementAt(i).print();
      }

    }
  }

  public Circumcircle(String Radius, 
      String Thickness, 
      String Angle, 
      String email,
      int numberOfProteins,
      Vector<String> namesOfProteins) throws IOException {

    this.radius = 150; 
    if (numberOfProteins > 5)
      this.radius = 350;

    if(!Radius.startsWith("auto"))
      this.radius = Float.parseFloat(Radius);

    if(!Thickness.startsWith("auto"))
      this.thickness = Float.parseFloat(Thickness);
    else
      this.thickness=30;

    if(!Angle.startsWith("auto"))
      this.angle = Float.parseFloat(Angle);
    else
      this.angle=360;

    this.numberOfProteins = numberOfProteins;
    this.email=email;
    Date dNow = new Date( );
    SimpleDateFormat ft = new SimpleDateFormat ("yyyyMMddhhmmss");		
    this.filename=ft.format(dNow)+".pdb";
    this.filelipid=ft.format(dNow)+"lipids.pdb";
    fmt1 = new Formatter(filename);
    proteins = new Vector<Protein>();

    for(int i=0; i<numberOfProteins ;i++)
      proteins.addElement(new Protein(namesOfProteins.elementAt(i),radius,fmt1));

  }

  public Circumcircle(String Radius, 
      String Thickness, 
      String Angle, 
      String email,
      int numberOfProteins,
      Vector<String> namesOfProteins, 
      String str) throws IOException {

    // Parse Radius Entry
    this.radius = 150; 
    if (numberOfProteins > 5)
      this.radius = 350;

    if(!Radius.startsWith("auto"))
      this.radius = Float.parseFloat(Radius);

    // Parse Angle Entry
    if(!Thickness.startsWith("auto"))
      this.thickness = Float.parseFloat(Thickness);
    else
      this.thickness=30;

    if(!Thickness.startsWith("auto"))
      this.thickness = Float.parseFloat(Thickness);
    else
      this.thickness=30;

    if(!Angle.startsWith("auto"))
      this.angle = Float.parseFloat(Angle);
    else
      this.angle=(float) 360;

    this.numberOfProteins = numberOfProteins;
    this.email=email;

    Date dNow = new Date( );
    SimpleDateFormat ft = new SimpleDateFormat ("yyyyMMddhhmmss");
    this.filename=ft.format(dNow)+".pdb";
    this.filelipid=ft.format(dNow)+"lipids.pdb";
    fmt1 = new Formatter(filename);
    fmt1.format("%s\n",str);
    proteins = new Vector<Protein>();
    for(int i=0; i<numberOfProteins ;i++)
      proteins.addElement(new Protein(namesOfProteins.elementAt(i),radius,fmt1));

  }

  void drawMembrane(int dis) throws FileNotFoundException {
    float insideRadius = radius, outsideRadius = insideRadius+thickness;
    float Teta=0;
    float teta=dis*((float) Math.asin(1/insideRadius));
    double angle1 = Math.toRadians(angle);

    for(int i = 0; Teta <= angle1; Teta+=teta) {
      for(float j=minYY; j<maxYY; j=j+dis,i++) {
	fmt1.format("HETATM%5d  N   DUM  %4d    %8.3f%8.3f%8.3f\n", 
	    i, 9999, (float)insideRadius*Math.cos(Teta),(float)insideRadius*Math.sin(Teta), (float)j);
	fmt1.format("HETATM%5d  O   DUM  %4d    %8.3f%8.3f%8.3f\n", 
	    i, 9998, (float)outsideRadius*Math.cos(Teta),(float)outsideRadius*Math.sin(Teta), (float)j);
      }
    }
    fmt1.close();
  }

  void draw3DMembrane() {
    float insideRadius = radius, outsideRadius = insideRadius+thickness;
    float Teta=0, Feta=0;
    float teta=8*(float) Math.asin(1.0 / insideRadius);
    float feta=8*(float) Math.asin(1.0 / insideRadius);

    double angle1 = Math.toRadians(angle);
    double angle2 = Math.PI;

    Feta=feta;
    for(int i=0; Feta <= angle1; Feta+=feta) {
      for(Teta = 0; Teta <= angle2; Teta+=teta,i++) {
	fmt1.format("HETATM%5d  N   DUM  %4d    %8.3f%8.3f%8.3f\n", 
	    i, 9999, (float)insideRadius*Math.sin(Feta)*Math.cos(Teta),
	    (float)insideRadius*Math.sin(Feta)*Math.sin(Teta), 
	    (float)insideRadius*Math.cos(Feta));
	fmt1.format("HETATM%5d  O   DUM  %4d    %8.3f%8.3f%8.3f\n", 
	    i, 9998, (float)outsideRadius*Math.sin(Feta)*Math.cos(Teta),
	    (float)outsideRadius*Math.sin(Feta)*Math.sin(Teta), 
	    (float)outsideRadius*Math.cos(Feta));
      }
    }
    fmt1.close();
  }

}
