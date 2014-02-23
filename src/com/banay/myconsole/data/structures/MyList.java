package com.banay.myconsole.data.structures;


public class MyList<T> {
	private Node<T> first;
	
	public MyList(){
		this.first = null;
	}
	
	public Node<T> getFirst(){
		return first;
	}
	
	public Node<T> insert(Node<T> p, T info){
		Node<T> q = new Node<T>(info);
		if(p==null)
		{
			q.setNext(first);
			first = q;
		}
		else
		{
			q.setNext(p.getNext());
			p.setNext(q);
		}
		return q;
	}
	
	public Node<T> remove(Node<T> p){
		if(first == p){
			first = p.getNext();
			return first;
		}
		else{
			Node<T> prev = first;
			while(prev.getNext() != p)
				prev = prev.getNext();
			prev.setNext(p.getNext());
			return prev.getNext();
		}
	}
	
	public boolean isEmpty(){
		return first == null;
	}
	
	public String toString(){
		return "--> " +first;
	}
}
