import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MainDriver {

	public static void main(String[] args) throws IOException
	{
		MyLinkedList<String> qe=new MyLinkedList<String>();
		MyLinkedList<String> firstLetters = new MyLinkedList<String>();
		ArrayList <String> l = new ArrayList<String>();
		FileInputStream file = new FileInputStream("input.txt"); //file input
		FileWriter output = new FileWriter("output.txt"); //file output
		Scanner input = new Scanner(file); //scanner input
		Scanner scan = new Scanner (System.in);
		while(input.hasNextLine())
		{
			String s = input.nextLine().toLowerCase();
			l.add(s);
			firstLetters.add(s.substring(0,1));
		}
		
		Collections.shuffle(l);
		//System.out.println(l.toString());
		
		for(int i = 0; i<l.size();i++)
		{
			qe.add(l.get(i));
			output.write(qe.get(i) +"\n");
		}
		output.close();
		
		while(firstLetters.size()!= 0)
		{
			System.out.println("What is the index of the word that begins with " + firstLetters.getFirst());
			int answer = scan.nextInt();
			boolean b = check(qe.get(answer), firstLetters.getFirst());
			if (b == true)
			{
				firstLetters.removeFirst();
			}
			else
			{
				String s = firstLetters.getFirst();
				firstLetters.removeFirst();
				firstLetters.add(s);
			}
		}
		
		input.close();
		scan.close();
	}

	private static boolean check(String answer, String firstLetter) {
		
		String a = answer.charAt(0) + "";
		
		if (a.equals(firstLetter))
			return true;
		else
			return false;
	}

	
	
	

}
