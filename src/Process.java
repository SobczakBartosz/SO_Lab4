import java.util.ArrayList;
import java.util.Random;

public class Process {
	ArrayList<Integer> references = new ArrayList<Integer>();

//	private int frames_number;
	private int pages_number;
	
	public Process(/*int frames_number ,*/ int pages_number, int references_number){
//		this.frames_number = frames_number;
		this.pages_number = pages_number;
		
		Random rand = new Random();
		
		for(int i = 0; i < references_number; i++){
			references.add(rand.nextInt(pages_number));
		}
	}
	
//	public int getFrames_number(){
//		return frames_number;
//	}
//
//	public void setFrames_number(int frames_number){
//		this.frames_number = frames_number;
//	}

	public int getPages_number() {
		return pages_number;
	}

}
