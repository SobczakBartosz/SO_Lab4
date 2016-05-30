import java.util.ArrayList;
import java.util.Random;

public class Process {
	ArrayList<Integer> references = new ArrayList<Integer>();
	private int pages_number;
	
	public Process(int pages_number, int references_number){
		this.pages_number = pages_number;
		
		Random rand = new Random();
		
		for(int i = 0; i < references_number; i++){
			references.add(rand.nextInt(pages_number));
		}
	}

	public Process(Process process){
		 this.references = (ArrayList<Integer>)process.references.clone();
		 this.pages_number = process.getPages_number();
	}

	public int getPages_number() {
		return pages_number;
	}
}
