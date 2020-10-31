import java.util.*;
class AVLNode
{
	public int ht;
	public int data;
	public AVLNode lchild;
	public AVLNode rchild;

	public AVLNode(int x)
	{
		data = x;
		lchild = null;
		rchild = null;
		this.ht=0;
	}

	public AVLNode(int x,int ht)
	{
		data = x;
		lchild = null;
		rchild = null;
		this.ht = ht;
	}
}
class AVLTree
{
	private AVLNode root;

	public AVLTree()
	{
		root = null;
	}

	public AVLTree(ArrayList<Integer> arr) //throws ErrorInTreeException
	{
		root = new AVLNode(arr.get(arr.size()-1));
		arr.remove(arr.size()-1);
		
		for(int p:arr)
		{
			insert(p);
		}
	}
	private int max(int x,int y) { return x>=y?x:y; }
	private int getht(AVLNode node) { return node==null?-1:node.ht; }
	private AVLNode lrRotate(AVLNode root)
	{
		AVLNode head = root;
		AVLNode mid = root.lchild;
		AVLNode lower = root.lchild.rchild;

		mid.ht = getht(mid.lchild)>getht(mid.rchild.lchild) ? getht(mid.lchild)+1:getht(mid.rchild.lchild)+1;
		head.ht = getht(head.rchild)>getht(lower.rchild) ? getht(head.rchild)+1:getht(lower.rchild)+1;
		lower.ht = getht(mid)>getht(head) ? getht(mid)+1:getht(head)+1;

		AVLNode temp = lower.lchild;
		lower.lchild = mid;
		mid.rchild = temp;

		temp = lower.rchild;
		lower.rchild = head;
		head.lchild = temp;
		return lower;
	}

	private AVLNode rlRotate(AVLNode root)
	{
		AVLNode head = root;
		AVLNode mid = root.rchild;
		AVLNode lower = root.rchild.lchild;

		mid.ht = getht(mid.rchild)>getht(mid.lchild.rchild) ? getht(mid.rchild)+1:getht(mid.lchild.rchild)+1;
		head.ht = getht(head.lchild)>getht(lower.lchild) ? getht(head.lchild)+1:getht(lower.lchild)+1;
		lower.ht = getht(mid)>getht(head) ? getht(mid)+1:getht(head)+1;

		AVLNode temp = lower.rchild;
		lower.rchild = mid;
		mid.lchild = temp;

		temp = lower.lchild;
		lower.lchild = head;
		head.rchild = temp;
		return lower;
	}
	private AVLNode llRotate(AVLNode root)
	{
		AVLNode head = root;
		AVLNode mid = root.lchild;
		AVLNode lower = root.lchild.lchild;

		head.ht = getht(mid.rchild)>getht(head.rchild) ? getht(mid.rchild)+1:getht(head.rchild)+1;
		mid.ht = getht(head)>getht(lower) ? getht(head)+1:getht(lower)+1;

		AVLNode temp = mid.rchild;
		mid.rchild = head;
		head.lchild = temp;

		return mid;
	}

	private AVLNode rrRotate(AVLNode root)
	{
		AVLNode head = root;
		AVLNode mid = root.rchild;
		AVLNode lower = root.rchild.rchild;

		head.ht = getht(mid.lchild)>getht(head.lchild) ? getht(mid.lchild)+1:getht(head.lchild)+1;
		mid.ht = getht(head)>getht(lower) ? getht(head)+1:getht(lower)+1;

		AVLNode temp = mid.lchild;
		mid.lchild = head;
		head.rchild = temp;

		return mid;
	}

	public ArrayList<Integer> getPreorderTraversal()
	{
		ArrayList<Integer> soln = new ArrayList<Integer>();
		if(root == null) return soln;
		soln.add(root.data);
		Stack<AVLNode> stk = new Stack<AVLNode>();
		AVLNode itr = root.lchild;
		stk.push(root);
		while(!stk.isEmpty())
		{
			while(itr!=null)
			{
				soln.add(itr.data);
				stk.push(itr);
				itr = stk.peek().lchild;
			}
			itr = stk.pop().rchild;
			while(itr!=null)
			{
				soln.add(itr.data);
				stk.push(itr);
				itr = stk.peek().lchild;
			}
		}
		return soln;
	}

	public ArrayList<Integer> getPreorderTraversalht()
	{
		ArrayList<Integer> soln = new ArrayList<Integer>();
		if(root == null) return soln;
		soln.add(root.ht);
		Stack<AVLNode> stk = new Stack<AVLNode>();
		AVLNode itr = root.lchild;
		stk.push(root);
		while(!stk.isEmpty())
		{
			while(itr!=null)
			{
				soln.add(itr.ht);
				stk.push(itr);
				itr = stk.peek().lchild;
			}
			itr = stk.pop().rchild;
			while(itr!=null)
			{
				soln.add(itr.ht);
				stk.push(itr);
				itr = stk.peek().lchild;
			}
		}
		return soln;
	}

	public void insert(int x)// throws ErrorInTreeException  // duplicates will not be inserted.
	{
		if(this.root == null)
		{
			this.root = new AVLNode(x);
			return;
		}
		Stack<AVLNode> stk = new Stack<AVLNode>();
		AVLNode curr = root;
		stk.push(curr);
		AVLNode temp = null;
		outer:
		while(curr!=null)
		{
			if(x<curr.data)
			{
				if(curr.lchild == null)
				{
					temp = new AVLNode(x);
					curr.lchild = temp;
					break outer;
				}
				else
				{
					curr = curr.lchild;
					if (curr!= null) stk.push(curr);
				}
			}
			else if(x>curr.data)
			{
				if(curr.rchild == null)
				{
					temp = new AVLNode(x);
					curr.rchild = temp;
					break outer;
				}
				else
				{
					curr = curr.rchild;
					if (curr!= null) stk.push(curr);
				}
			}
			else return;
		}
		AVLNode heads = temp,mids = null,lowers,new_head= root;
		while(!stk.isEmpty())
		{
			lowers = mids;
			mids = heads;
			heads = stk.pop();
			heads.ht = max(getht(heads.lchild)+1,getht(heads.rchild)+1);
			int bf = getht(heads.rchild)-getht(heads.lchild);
			if(bf == -2 || bf == +2)
			{
				if(heads.lchild == mids)
				{
					if(mids.lchild == lowers)
					{
						new_head =  llRotate(heads);
					}
					else if(mids.rchild == lowers)
					{
						new_head = lrRotate(heads);
					}
				}
				else if(heads.rchild == mids)
				{
					if(mids.lchild == lowers) new_head = rlRotate(heads);
					else if(mids.rchild == lowers) new_head = rrRotate(heads);
				}
				break;
			}
		}

		if(stk.isEmpty()) root = new_head;
		else
		{
			temp = stk.pop();
			if(temp.lchild == heads) temp.lchild = new_head;
			else if(temp.rchild == heads) temp.rchild = new_head;
		}
	}
	private AVLNode[] getGrandParentParentAndChild(int x)
	{
		AVLNode[] arr = new AVLNode[3];
		arr[0] = arr[1] = arr[2] = null;
		Stack<AVLNode> stk = new Stack<AVLNode>();
		AVLNode curr = root;
		boolean found = false;
		while(curr != null)
		{
			if(curr.data == x)
			{
				stk.push(curr);
				found = true;
				break;
			}
			else
			{
				stk.push(curr);
				if(x<curr.data) curr = curr.lchild;
				else curr = curr.rchild;
			}
		}
		if(found)
		{
			int i=0;
			while(!stk.isEmpty() && i<3)
			{
				arr[i] = stk.pop();
				i++;
			}
		}		
		return arr;
	}
	public void delete(int x)
	{
		root = erase(x,root);
	}
	private AVLNode erase(int x,AVLNode root)
	{
		if(root == null) return null;
		AVLNode del_node_par, del_node, repl_node, repl_node_par;
		Stack<AVLNode> stk = new Stack<AVLNode>();
		if(root.data == x)
		{
			if(root.lchild == null)
			{
				AVLNode temp = root.rchild;
				root.rchild = null;
				root = null;
				return temp;
			}
			else
			{
				del_node = root;
				if(del_node.lchild.rchild == null)
				{
					AVLNode temp = del_node.lchild;
					temp.rchild = del_node.rchild;

					temp.ht = max(getht(temp.lchild),getht(temp.rchild))+1;

					del_node.rchild = null;
					del_node.lchild = null;
					del_node = null;
					return temp;
				}
				else
				{

					repl_node_par = del_node.lchild;
					repl_node = repl_node_par.rchild;
					stk.push(repl_node_par);
					while(repl_node.rchild!=null)
					{
						repl_node = repl_node.rchild;
						repl_node_par = repl_node_par.rchild;
						stk.push(repl_node_par);
					}
					repl_node_par.rchild = repl_node.lchild;
					repl_node.lchild = del_node.lchild;
					del_node.lchild = null;
					repl_node.rchild = del_node.rchild;
					del_node.rchild = null;
					del_node = null;

					
					while(!stk.isEmpty())
					{
						AVLNode temp = stk.pop();
						temp.ht = max(getht(temp.lchild)+1,getht(temp.rchild)+1);
					}
					repl_node.ht = max(getht(repl_node.lchild)+1,getht(repl_node.rchild)+1);


					int bf = getht(repl_node.rchild)-getht(repl_node.lchild);
					if(bf == -2) // height of left sub tree is large.
					{
						if(repl_node.lchild.lchild != null) return llRotate(repl_node);
						else return lrRotate(repl_node);
					}
					else if(bf == 2) // height of right sub tree is large.
					{
						if(repl_node.rchild.rchild != null) return rrRotate(repl_node);
						else return rlRotate(repl_node);
					}
					else return repl_node;
				}
			}
		}
		else
		{
			AVLNode[] arr;
			arr = getGrandParentParentAndChild(x);
			if(arr[0] == null && arr[1] == null && arr[2] == null) return root;
			del_node =arr[0];
			del_node_par = arr[1];
			AVLNode del_node_gpar = arr[2];
			if(del_node_par.lchild == del_node)
			{
				del_node_par.lchild = erase(x,del_node);
				del_node_par.ht = max(getht(del_node_par.lchild),getht(del_node_par.rchild))+1;
				if(del_node_gpar != null)
					del_node_gpar.ht = max(getht(del_node_gpar.lchild),getht(del_node_gpar.rchild))+1;
				doAppropriateRotations(del_node_par,del_node_gpar);
			}
			else if(del_node_par.rchild == del_node)
			{
				del_node_par.rchild = erase(x,del_node);
				del_node_par.ht = max(getht(del_node_par.lchild),getht(del_node_par.rchild))+1;
				if(del_node_gpar != null)
					del_node_gpar.ht = max(getht(del_node_gpar.lchild),getht(del_node_gpar.rchild))+1;
				doAppropriateRotations(del_node_par,del_node_gpar);
			}
			return this.root;
		}
	}
	private void doAppropriateRotations(AVLNode del_node_par, AVLNode del_node_gpar)
	{
		int bf = getht(del_node_par.rchild)-getht(del_node_par.lchild);
		if(bf == -2)// left is longer
		{
			if(del_node_par.lchild.lchild != null)
			{
				if(del_node_gpar != null && del_node_gpar.lchild == del_node_par) 
					del_node_gpar.lchild = llRotate(del_node_par);
				else if(del_node_gpar != null && del_node_gpar.rchild == del_node_par)
					del_node_gpar.rchild = llRotate(del_node_par);
				else this.root = llRotate(del_node_par);
			}
			else
			{
				if(del_node_gpar != null && del_node_gpar.lchild == del_node_par) 
					del_node_gpar.lchild = lrRotate(del_node_par);
				else if(del_node_gpar != null && del_node_gpar.rchild == del_node_par)
					del_node_gpar.rchild = lrRotate(del_node_par);
				else this.root = lrRotate(del_node_par);
			}
		}
		else if(bf == 2) // height of right sub tree is large.
		{
			if(del_node_par.rchild.rchild != null)
			{
				if(del_node_gpar != null && del_node_gpar.lchild == del_node_par) 
					del_node_gpar.lchild = rrRotate(del_node_par);
				else if(del_node_gpar != null && del_node_gpar.rchild == del_node_par)
					del_node_gpar.rchild = rrRotate(del_node_par);
				else this.root = rrRotate(del_node_par);
			}
			else
			{
				if(del_node_gpar != null && del_node_gpar.lchild == del_node_par) 
					del_node_gpar.lchild = rlRotate(del_node_par);
				else if(del_node_gpar != null && del_node_gpar.rchild == del_node_par)
					del_node_gpar.rchild = rlRotate(del_node_par);
				else this.root = rlRotate(del_node_par);
			}
		}
	}

	public boolean search(int x)
	{
		AVLNode curr = this.root;
		if(curr == null) return false;
		while(curr != null)
		{
			if(curr.data == x) return true;
			else if(x<curr.data) curr = curr.lchild;
			else curr = curr.rchild; 
		}
		return false;
	}
}
class ConstructAVL
{
	public static void main(String[] args)
	{
		AVLTree myTree = new AVLTree(new ArrayList<Integer>(Arrays.asList(0,1,5,10,15,20,25,35,3,23,14,34)));
		System.out.println("###########Creation of avl tree is completed.###########");
		System.out.println("pre order traversal ="+ myTree.getPreorderTraversal());
		myTree.insert(56);
		System.out.println("pre order traversal after inserting an element ="+ myTree.getPreorderTraversal());
		myTree.delete(20);
		System.out.println("pre order traversal after deleting an element ="+ myTree.getPreorderTraversal());
		System.out.print("search 35 :"+ myTree.search(35));
	}
}