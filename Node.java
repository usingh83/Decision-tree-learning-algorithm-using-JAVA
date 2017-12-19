import java.util.ArrayList;
import java.util.List;
public class Node implements Cloneable 
{
	public Node[] child;
	public int res;
	public int i;
	String h;
	int v;
	public double e;
	public ArrayList<List<List<Integer>>> data;
	public Node() 
	{
		child = null;
		e = 0.0;
		data = new ArrayList<>();
		res = 0;
		h = "";
		v=0;
		i = -1;
	}
	public Object clone() throws CloneNotSupportedException 
	{
		Node cloned=new Node();
		cloned.i=this.i;
		cloned.v=this.v;
		cloned.h=new String(this.h);
		cloned.v=this.v;
		cloned.e=this.e;
		for(List<List<Integer>> row:this.data) 
		{
			List<List<Integer>> clonedrow=new ArrayList<>();
			for(List<Integer> entry:row) clonedrow.add(new ArrayList(entry));
			cloned.data.add(clonedrow);
		}
		if(this.child!=null) 
		{
			cloned.child=new Node[2];
			for(int i=0;i<2;i++) cloned.child[i]=(Node)this.child[i].clone();
		}
		return cloned;
	}
}