import java.util.ArrayList;

public class Algorithms {

	public static int proportional_allocation(ArrayList<Process> process_list, int frame_number){
		int pages_errors = 0;
		
		return pages_errors;
	}
	
	public static int even_allocation(ArrayList<Process> process_list, int frame_number){
		int pages_errors = 0;
		
		return pages_errors;
	}
	
	public static int zone_allocation(ArrayList<Process> process_list, int frame_number, int N){ //N - constant
		int pages_errors = 0;
		
		
		return pages_errors;
	}
	
	public static int frequency_control_page_fault_allocation(ArrayList<Process> process_list, int frame_number, int min_erros, int max_erros, int N){ //N necessery??
		int pages_errors = 0;
		
		return pages_errors;
	}
	
	public static int LRU(int frame_number, ArrayList<Integer> input_references) {
		int page_errors = 0;
		int remove_index = 0; 
		ArrayList<Integer> frames_used = new ArrayList<>(); 	// indexes of frames which should be replaced (descending order)
		int[] frames;
		
		ArrayList<Integer> references = (ArrayList<Integer>) input_references.clone();
		
		if(frame_number < references.size()){
			frames = new int[frame_number];
			for(int i = 0; i < frame_number; i++){
				frames[i] = i;
				frames_used.add(frames[i]);
			}	
			//fill frames at the beginning					
					
			while(!references.isEmpty()){
				int index = 0;
				while(index < frame_number && frames[index] != references.get(0))
					index++;
				if(index < frame_number){
					int tmp = frames_used.remove(frames_used.indexOf(index));	//delete index of current reference in frames_used and add it to the end of the list
					frames_used.add(tmp);														
					references.remove(0);
				}else{
					remove_index = frames_used.remove(0);
					frames_used.add(remove_index);
					frames[remove_index] = references.remove(0);

					page_errors++;
				}
			}
		}	
			return page_errors;
	} // LRU algorithm
}
