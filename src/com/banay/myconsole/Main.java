package com.banay.myconsole;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import com.banay.myconsole.data.structures.BinTreeNode;
import com.banay.myconsole.data.structures.MyList;
import com.banay.myconsole.data.structures.Node;
import com.banay.myconsole.data.structures.Queue;

public class Main {

	public final class Command {
		public static final String BTN = "btn";
		public static final String HELP = "help";
		public static final String EXIT = "exit";
		public static final String PRINT = "print";
		public static final String QUEUE = "queue";
		public static final String CREATE = "create";
	}

	public final class Text {
		public static final String WELCOME_PART_1 = "Welcome, motherfucker!";
		public static final String WELCOME_PART_2 = "Write something and if it is not bullshit I will execute it";
	}

	public final class Man {
		public static final String CREATE = "create [type] [id] (argIds...)";
		public static final String BTN = "btn [id] [info] (left id) (right id) {creates a new btn}";
		public static final String PRINT = "print [id] {prints the object}";
		public static final String QUEUE = "queue\n<new [id] {creates a new queue}>\n<insert [id] [infoId]>";
	}

	public static HashMap<String, Object> objects = new HashMap<String, Object>();
	public static Scanner reader = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println(Text.WELCOME_PART_1);
		System.out.println(Text.WELCOME_PART_2);

		String[] input = reader.nextLine().split("\\s+", 10);

		while (!input[0].equals(Command.EXIT)) {
			if (input[0].equals(Command.HELP)) {
				help();
			} else if (input[0].equals(Command.CREATE)) {
				create(input);
			} else if (input[0].equals(Command.QUEUE)) {
				queue(input);
			} else if (input[0].equals(Command.BTN)) {
				btn(input);
			} else if (input[0].equals(Command.PRINT))
				print(input);

			input = reader.nextLine().split("\\s+", 10);
		}
	}

	static void create(String[] input) {
		
		
		String type = input[1];
		String id = input[2];

		Class<?> cls;
		try {
			cls = Class.forName(type);
		} catch (ClassNotFoundException e) {
			System.out.println("No such class!");
			return;
		}

		ArrayList<Object> args = new ArrayList<Object>();
		for (int i = 3; i < input.length; i++) {
			String argId = input[i];

			Object o = objects.get(argId);

			if (o == null) {
				System.out.println("Object with id " + argId + " not found");
				return;
			}

			args.add(o);
		}

		Class<?>[] argsClasses = new Class<?>[args.size()];
		for (int i = 0; i < argsClasses.length; i++) {
			argsClasses[i] = args.get(i).getClass();
		}

		try {
			Constructor<?> constructor = cls.getConstructor(argsClasses);
		} catch (SecurityException e) {
			System.out.println("Unknown problem...");
			return;
		} catch (NoSuchMethodException e) {
			System.out
					.println("There is no constructor that fits the argument");
		}

	}

	static void print(String[] input) {
		String id = input[1];

		Object object = objects.get(id);
		System.out.println(object.toString());
	}

	static void help() {
		System.out.println("Commands:");
		System.out
				.println("command [required] (optional) <subcommand> {explanation}");
		System.out.println();
		System.out.println(Man.CREATE);
		System.out.println(Man.PRINT);
		System.out.println(Man.BTN);
		System.out.println(Man.QUEUE);
	}

	static void btn(String[] input) {
		String id = input[1];
		String info = input[2];
		String leftId = "";
		String rightId = "";

		if (input.length >= 4) {
			leftId = input[3];
			if (input.length >= 5) {
				rightId = input[4];
			}
		}

		if (objects.containsKey(id)) {
			System.out.println("Id is caught. Do you want to override it? y/N");
			String answer = reader.next();
			if (!answer.equals("y"))
				return;
		}

		BinTreeNode<String> t = new BinTreeNode<String>(info);

		BinTreeNode<String> leftTree = null;
		try {
			if (!leftId.equals(""))
				leftTree = (BinTreeNode<String>) objects.get(leftId);
		} catch (ClassCastException e) {
			System.out.println("the object with the id " + leftId
					+ " is not of type BinTreeNode<String");
		}

		BinTreeNode<String> rightTree = null;
		try {
			if (!rightId.equals(""))
				rightTree = (BinTreeNode<String>) objects.get(rightId);
		} catch (ClassCastException e) {
			System.out.println("the object with the id " + rightId
					+ " is not of type BinTreeNode<String");
		}

		t.setLeft(leftTree);
		t.setRight(rightTree);

		objects.put(id, t);
	}

	static void queue(String[] input) {
		String command = input[1];
		if (command.equals("new")) {
			String id = input[1];

			Queue queue = new Queue<Object>();
			if (objects.containsKey(id)) {
				System.out
						.println("Id is caught. Do you want to override it? y/N");
				String answer = reader.next();
				if (!answer.equals("y"))
					return;
			}

			objects.put(id, queue);
		} else if (command.equals("insert")) {
			String id = input[2];
			String infoId = input[3];

			Queue<Object> queue;
			try {
				queue = (Queue) objects.get(id);
			} catch (ClassCastException e) {
				System.out.println("the object with the id " + id
						+ " is not of type Queue");
				return;
			}

			if (queue == null) {
				System.out.println("the object with the id " + id
						+ " does not exist");
				return;
			}

			Object info = objects.get(infoId);
			if (info == null) {
				System.out.println("the object with the id " + infoId
						+ " does not exist");
				return;
			}

			queue.insert(info);
		}
	}

	static <T extends Comparable<T>> void addSorted(T value,
			MyList<T> list) {
		Node<T> prev = null;
		Node<T> current = list.getFirst();
		
		while (current != null && current.getInfo().compareTo(value) <= 0) {
			prev = current;
			current = current.getNext();
		}
		list.insert(prev, value);
	}

	private String[] getCommandArgs(String[] input){
		ArrayList<String> commandArgs = new ArrayList<String>();
		
		for(String s : input){
			if(s.startsWith("-")){
				if(s.startsWith("--"))
				{
					commandArgs.add(s.substring(2));
				}
				else
				{
					String commandArgsString = s.substring(1);
					for(char c: commandArgsString.toCharArray()){
						commandArgs.add(Character.toString(c));
					}
				}
			}
		}
		
		String[] result = new String[commandArgs.size()];
		commandArgs.toArray(result);
		return result;
	}
}
