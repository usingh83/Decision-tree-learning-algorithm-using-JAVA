import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public final class DT
{
	static String[] Column_header;
	public static void main(String args[]) throws CloneNotSupportedException, IOException
	{
		//Scanner in=new Scanner(System.in);
		int L= Integer.parseInt(args[0]); //in.nextInt();
		int K= Integer.parseInt(args[1]);  //in.nextInt();
		String Training_set_path=args[2];   //in.next();
		String Validation_set_path=args[3];   //in.next();
		String Test_set_path=args[4];   //in.next();
		ArrayList<List<List<Integer>>> Training_set=new ArrayList<List<List<Integer>>>();
		ArrayList<List<List<Integer>>> Test_set=new ArrayList<List<List<Integer>>>();
		ArrayList<List<List<Integer>>> Validation_set=new ArrayList<List<List<Integer>>>();
		readcsv(Training_set_path,Training_set);
		readcsv(Test_set_path,Test_set);
		readcsv(Validation_set_path,Validation_set);
		Tree tree = new Tree();
		tree.buildTree(Training_set,new Node(),true);
		boolean print=args[5].equals("yes");
		System.out.println("Accuracy of the tree constructed using information gain: "+tree.accuracy(Test_set,tree.root)+"%");
		tree.prun(L,K,Validation_set);
		System.out.println("Accuracy of the tree constructed using information gain after pruning: "+tree.accuracy(Test_set,tree.root)+"%");
		if(print) System.out.println("After pruning: \n"+tree.print(tree.root,0));
		tree=new Tree();
		tree.buildTree(Training_set,new Node(),false);
		System.out.println("Accuracy of the tree constructed using impurity gain: "+tree.accuracy(Test_set,tree.root)+"%");
		tree.prun(L,K,Validation_set);
		System.out.println("Accuracy of the tree constructed using impurity gain after pruning: "+tree.accuracy(Test_set,tree.root)+"%\n");
		if (print) System.out.println("After pruning: \n"+tree.print(tree.root,0));
		//in.close();
	}
	private static void readcsv(String path,ArrayList<List<List<Integer>>> list) throws IOException 
	{
		String str="";
		String delimiter=",";
		String[] attr=null;
		BufferedReader br=new BufferedReader(new FileReader(path));
		Column_header=br.readLine().split(delimiter);
		while((str=br.readLine())!=null) 
		{
			attr=str.split(delimiter);
			List<List<Integer>> l=new ArrayList<>();
			for(int i=0;i<attr.length;i++)
			{
				List<Integer> lc=new ArrayList<>();
				lc.add(i);lc.add(Integer.parseInt(attr[i]));
				l.add(lc);
			}
			list.add(l);
		}		
	}
}