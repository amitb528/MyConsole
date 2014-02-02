public class BinTreeNode<T> {
	private T info;
	private BinTreeNode<T> left;
	private BinTreeNode<T> right;
	
	public BinTreeNode(T info){
		setInfo(info);
	}
	
	public T getInfo() {
		return info;
	}
	public void setInfo(T info) {
		this.info = info;
	}
	public BinTreeNode<T> getLeft() {
		return left;
	}
	public void setLeft(BinTreeNode<T> left) {
		this.left = left;
	}
	public BinTreeNode<T> getRight() {
		return right;
	}
	public void setRight(BinTreeNode<T> right) {
		this.right = right;
	}
	
	public String toString(){
		String left = "";
		String right = "";
		
		if(getLeft()!=null)
			left = getLeft().toString();
		
		if(getRight()!=null)
			right = getRight().toString();
		
		return "{"+left+info.toString()+right+"}";
	}
}
