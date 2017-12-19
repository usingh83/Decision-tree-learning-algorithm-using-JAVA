import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
public class Tree
{
	public Node root;
	private int index;
	public void prun(int L,int K,ArrayList<List<List<Integer>>> data) throws CloneNotSupportedException 
	{
		Node D_best=(Node)root.clone();
		for(int i=0;i<L;i++) 
		{
			Node D=(Node)root.clone();
			Random random=new Random();
			int M=random.nextInt(K);
			for(int j=0;j<M;j++)
			{
				int P=random.nextInt(index);
				deleteBranch(D,P);
			}
			if(accuracy(data,D)>accuracy(data,D_best)) D_best=(Node)D.clone();
		}
		root=(Node)D_best.clone();
	}
	public double gain(double curE,ArrayList<Double> childE,ArrayList<Integer> childSize,double Size) 
	{
		double gain=curE;
		for(int j=0;j<childE.size();j++) gain-=(childSize.get(j)/Size)*childE.get(j);
		return gain;
	}
	public double entropy(ArrayList<List<List<Integer>>> data) 
	{
		double[] count=new double[2];
		for(int i=0;i<data.size();i++) count[data.get(i).get(data.get(i).size()-1).get(1)]++;
		double entropy=0;
		for(int j=0;j<count.length;j++) entropy-=count[j]==0?0:count[j]*((Math.log(count[j])/Math.log(2))-(Math.log(data.size())/Math.log(2)));
		entropy/=data.size();
		return entropy;
	}
	public double impurity(ArrayList<List<List<Integer>>> data) 
	{
		double[] K=new double[2];
		for(int i=0;i<data.size();i++) K[data.get(i).get(data.get(i).size()-1).get(1)]++;
		double impurity=1;
		for(int i=0;i<K.length;i++) impurity*=K[i];
		return impurity/(Math.pow(data.size(),K.length));
	}
	private void deleteBranch(Node root,int index) 
	{
		if(root.i==index) 
		{
			if(root.child[0].data.size()>root.child[1].data.size())
			{
				int[] count=new int[2];
				for(List<List<Integer>> childrow:root.child[0].data) count[childrow.get(childrow.size()-1).get(1)]++;
				root.child[0].v=count[0]>count[1]?0:1;
				root.child[0].child=null;
				root.child[0].i=-1;
			} 
			else
			{
				int[] count=new int[2];
				for(List<List<Integer>> childrow:root.child[1].data) count[childrow.get(childrow.size()-1).get(1)]++;
				root.child[1].v=count[0]>count[1]?0:1;
				root.child[1].child=null;
				root.child[1].i=-1;
			}
		} 
		else if(root.child!=null)
		{
			deleteBranch(root.child[0],index);
			deleteBranch(root.child[1],index);
		}
	}
	public String print(Node root,int level) 
	{
		String s=new String();
		for(int j=0;j<root.child.length;j++)
		{
			for(int i=0;i<level;i++) s+="| ";
			s+=root.h+" = "+j+" :";
			if(root.child[j].child!=null) s+="\n"+print(root.child[j],level+1);
			else s+=" "+root.child[j].v+"\n";
		}
		return s.toString();
	}
	public void buildTree(ArrayList<List<List<Integer>>> data,Node root,boolean heuristic) 
	{
		String head=null;
		int val=0;
		int value;
		double maxGain=0.0;
		ArrayList<List<List<Integer>>> left=new ArrayList<>();
		ArrayList<List<List<Integer>>> right=new ArrayList<>();
		int index=-1;
		if(heuristic) root.e=entropy(data);
		else root.e=impurity(data);
		for(int i=0;i<data.get(0).size()-1;i++) 
		{
			ArrayList<List<List<Integer>>> leftSet=new ArrayList<>();
			ArrayList<List<List<Integer>>> rightSet=new ArrayList<>();
			for(int j=0;j<data.size();j++) 
			{
				ArrayList<List<Integer>> list=new ArrayList<>();
				list.addAll(data.get(j));
				if(data.get(j).get(i).get(1)==0) leftSet.add(list);
				else rightSet.add(list);
			}
			ArrayList<Double> subE=new ArrayList<>();
			if(heuristic)
			{
				subE.add(entropy(leftSet));
				subE.add(entropy(rightSet));
			} 
			else
			{
				subE.add(impurity(rightSet));
				subE.add(impurity(leftSet));
			}
			ArrayList<Integer> sizesOfSets=new ArrayList<>();
			sizesOfSets.add(leftSet.size());
			sizesOfSets.add(rightSet.size());
			double gain=gain(root.e,subE,sizesOfSets,data.size());
			if(gain>maxGain)
			{
				maxGain=gain;
				head=Assignment1.Column_header[data.get(0).get(i).get(0)];
				val=data.get(0).get(i).get(1);
				index=i;
				left=(ArrayList<List<List<Integer>>>)leftSet.clone();
				right=(ArrayList<List<List<Integer>>>)rightSet.clone();
			}
		}
		if(index>-1) 
		{
			for(List<List<Integer>> row:left) row.remove(index);
			for(List<List<Integer>> row:right) row.remove(index);
			Node leftChild=new Node();
			Node rightChild=new Node();
			leftChild.data=left;
			rightChild.data=right;
			root.child=new Node[2];
			root.child[0]=leftChild;
			root.child[1]=rightChild;
			root.h=head;
			root.v=val;
			root.i=++this.index;
			buildTree(right,rightChild,heuristic);
			buildTree(left,leftChild,heuristic);
		} 
		else
		{
			root.v=data.get(0).get(data.get(0).size()-1).get(1);
			return;
		}
		this.root=root;
	}
	public double accuracy(ArrayList<List<List<Integer>>> data,Node root) 
	{
		double accuracy=0.0;
		for(List<List<Integer>> arrayList:data) if(treeMatchesClass(root,arrayList)) accuracy++;
		return (accuracy*100/data.size());
	}
	private boolean treeMatchesClass(Node root,List<List<Integer>> data) 
	{
		if(root.child==null) return data.get(data.size()-1).get(1)==root.v;
		else
		{
			int value=-1;
			for(int i=0;i<data.size();i++)
			{
				if(Assignment1.Column_header[data.get(i).get(0)].equals(root.h)) 
				{
					value=data.get(i).get(1);
					break;
				}
			}
			return treeMatchesClass(root.child[value],data);
		}
	}
}
