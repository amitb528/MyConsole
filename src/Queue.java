
public class Queue<T>{
	private Node<T> first;
	private Node<T> last;
	
	public Queue(){
		this.first = null;
		this.last = null;
	}
	
	public void insert(T info){
		Node<T> temp = new Node<T>(info);
		if(first == null)
			first = temp;
		else
			last.setNext(temp);
		last = temp;
	}
	
	public T remove(){
		T info = first.getInfo();
		first = first.getNext();
		if(first == null)
			last = null;
		return info;
	}
	
	public T head(){
		return first.getInfo();
	}
	
	public boolean isEmpty(){
		return first == null;
	}
	
	@Override
	public String toString() {
		String temp = first.toString();
		return "head ->" + temp.substring(0, temp.length() -4)+"end";
	}
}
