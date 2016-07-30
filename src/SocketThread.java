
import java.net.*;
import java.util.Arrays;
import java.util.Vector;
import java.io.*;

public class SocketThread extends Thread {
  static Socket csocket;

  public SocketThread(Socket csocket) {
    this.csocket = csocket;
  }

  public static boolean checkExists(String directory, String file) {
    File dir = new File(directory);
    File[] dir_contents = dir.listFiles();
    boolean check = new File(file).exists();
    //System.out.println("Check"+check+file);  // -->always says false
    return check;
  }

  public static void saveUrl(final String filename, final String urlString)throws MalformedURLException, IOException {

    BufferedInputStream in = null;
    FileOutputStream fout = null;
    try {
      in = new BufferedInputStream(new URL(urlString).openStream());
      fout = new FileOutputStream(filename);

      final byte data[] = new byte[1024];
      int count;
      while ((count = in.read(data, 0, 1024)) != -1) {
	fout.write(data, 0, count);
      }

    } catch(Exception e) {
      PrintStream pstream = new PrintStream(csocket.getOutputStream());
      pstream.println(filename+" doesn't found");
      pstream.close();
    } finally {
      if (in != null) {
	in.close();
      }
      if (fout != null) {
	fout.close();
      }
    }
  }

  public void run() {
    try {
      //PrintWriter out = new PrintWriter(csocket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(csocket.getInputStream()));
      String inputLine, outputLine;
      PrintStream pstream = new PrintStream(csocket.getOutputStream());
      Vector<String> Args = new Vector<String>();
      while (!(inputLine = in.readLine()).equals("exit") ) {
	//System.out.println(inputLine);
	Args.addElement(inputLine);
      }
      //System.out.println("bra");
      String arr[];
      if(Args.elementAt(7).contains(", ")) {
	arr = (Args.elementAt(7)).split(", ");
      } else {
	if(Args.elementAt(7).contains(",")) {
	  arr = (Args.elementAt(7)).split(",");
	} else {
	  if(Args.elementAt(7).contains(" "))
	    arr = (Args.elementAt(7)).split(" ");
	  else 
	    arr = (Args.elementAt(7)).split("(?<=\\G.{4})");
	}
      }
      Vector<String> namesOfProteins= new Vector<String>(Arrays.asList(arr));
      for(int i=0;i<arr.length;i++) {
	if(!(checkExists("Javaserver",arr[i]+".pdb"))&&(arr[i].compareTo("")!=0)) {
	  saveUrl(arr[i]+".pdb", "http://opm.phar.umich.edu/pdb/"+arr[i]+".pdb");
	  if(!checkExists("Javaserver",arr[i]+".pdb")) {
	    in.close();
	    pstream.close();
	    csocket.close();
	    return;
	  }
	} else
	  System.out.println(arr[i]+" exists");
      }
      String arr2[]=(Args.elementAt(6).replaceAll("MB", "")).split(" ");
      for(int i=0;i<arr2.length;i++)
	arr2[i]=(arr2[i].substring(0, arr2[i].indexOf('.')));
      namesOfProteins.addAll(Arrays.asList(arr2));
      for(int i=0;i<namesOfProteins.size();i++)
	System.out.println(namesOfProteins.elementAt(i));
      Circumcircle crm = null;
      if(Args.elementAt(4).equals("circular")) {
	if(Args.elementAt(5).equals("dummy")) {
	  try {
	    //                          radius             thickness          angle              email
	    crm = new Circumcircle(Args.elementAt(0),
		Args.elementAt(1),
		Args.elementAt(2),
		Args.elementAt(3),
		namesOfProteins.size(),
		namesOfProteins);
	    crm.determine2d();
	    crm.drawMembrane(2);
	  } catch(Exception e) {
	    pstream.println("something went wrong! Try again");
	  }
	} else {
	  try {
	    crm = new Circumcircle(Args.elementAt(0),
		Args.elementAt(1),
		Args.elementAt(2),
		Args.elementAt(3),
		namesOfProteins.size(),
		namesOfProteins,
		Args.elementAt(8));

	    crm.determine2d();
	    crm.drawMembrane(8);
	    Process p = new ProcessBuilder("/bin/bash", "run_lipidize.sh", crm.filename).start();
	    p.waitFor(); 
	  } catch(Exception e) {
	    pstream.println(e.getMessage());
	    pstream.println("something went wrong! Try again");
	  }
	}
      } else {
	if(Args.elementAt(5).equals("dummy")) {
	  try {
	    //                           radius             thickness          angle              email
	    crm = new Circumcircle(Args.elementAt(0),
		Args.elementAt(1),
		Args.elementAt(2),
		Args.elementAt(3),
		namesOfProteins.size(),
		namesOfProteins);

	    crm.determine3d();
	    crm.draw3DMembrane();
	  } catch(Exception e) {
	    pstream.println("something went wrong! Try again");
	  }
	} else {
	  try {
	    crm = new Circumcircle(Args.elementAt(0),
		Args.elementAt(1),
		Args.elementAt(2),
		Args.elementAt(3),
		namesOfProteins.size(),
		namesOfProteins,Args.elementAt(8));

	    crm.determine3d();
	    crm.draw3DMembrane();
	    Process p = new ProcessBuilder("/bin/bash", "run_lipidize.sh", crm.filename).start();
	    p.waitFor(); 
	  } catch(Exception e) {
	    pstream.println("something went wrong! Try again");
	  }
	}
      }
      System.out.println("Finished!");
      pstream.println("<br> The PDB file of your proteins in a circular membrane:"); 
      pstream.println("\n <a href='"+crm.filename+"'>link</a>");

      in.close();
      pstream.close();
      csocket.close();
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
