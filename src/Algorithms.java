import java.util.ArrayList;

public class Algorithms {

	public static int proportional_allocation(ArrayList<Process> input_process_list, int frame_number){
		int pages_errors = 0;
		int all_processes_size = 0;
		
		ArrayList<Process> process_list = new ArrayList<Process>();
		for(Process process : input_process_list){
			process_list.add(new Process(process));
		}

		for(int i = 0; i < process_list.size(); i++)
			if(process_list.get(i).references.size() < 1)
				process_list.remove(i);
			
		for(int i = 0; i < process_list.size(); i++){
		all_processes_size += process_list.get(i).references.size();
	}
	
	for(int i = 0; i < process_list.size(); i++){
		pages_errors += LRU((process_list.get(i).references.size()*frame_number)/all_processes_size, process_list.get(i).references);
	}

		return pages_errors;
	}
	
	public static int even_allocation(ArrayList<Process> input_process_list, int frame_number){
		int pages_errors = 0;
		
		ArrayList<Process> process_list = new ArrayList<Process>();
		for(Process process : input_process_list){
			process_list.add(new Process(process));
		}
		
		for(int i = 0; i < process_list.size(); i++)
			if(process_list.get(i).references.size() < 1)
				process_list.remove(i);
			
			for(int i = 0; i < process_list.size(); i++){
				pages_errors += LRU(frame_number/process_list.size(), process_list.get(i).references);
			}
		
		return pages_errors;
	}
	
	public static int zone_allocation(ArrayList<Process> input_process_list, int frame_number, int N){ //N - constant
		int pages_errors = 0;
		ArrayList<Integer> working_set = new ArrayList<Integer>();	//list of used pages in the process
		int[] working_set_number = new int[input_process_list.size()]; 	//number of used pages in each process in process_list	
		int working_set_sum_actual = 0;
		int working_set_sum_previous = 0;
		
		ArrayList<Process> process_list = new ArrayList<Process>();
		for(Process process : input_process_list){
			process_list.add(new Process(process));
		}
		
		for(int i = 0; i < working_set_number.length; i++)
			working_set_number[i] = 0;
		
		for(int i = 0; i < process_list.size(); i++){	// even frames allocation before working set is set
			pages_errors += ZLRU(frame_number/process_list.size(), process_list.get(i).references, working_set, N);
			working_set_number[i] = working_set.size();
			working_set_sum_previous += working_set.size();
			working_set.removeAll(working_set);
		}
		
		while(process_list.size() > 0 && working_set_sum_previous != 0){

			for(int i = 0; i < process_list.size(); i++)
				if(process_list.get(i).references.size() < 1)
					process_list.remove(i);
			
			working_set_sum_actual = 0;
			for(int i = 0; i < process_list.size(); i++){
				ZLRU((frame_number*working_set_number[i])/working_set_sum_previous, process_list.get(i).references, working_set, N);	//frames allocation proportional to working set
				working_set_number[i] = working_set.size();
				working_set_sum_actual += working_set.size();
				working_set.removeAll(working_set);
			}
			working_set_sum_previous = working_set_sum_actual;
		}
		
		return pages_errors;
	}
	
	public static int frequency_control_page_fault_allocation(ArrayList<Process> input_process_list, int frame_number, int min_erros, int max_erros, int N){ //N necessery??
		int pages_errors = 0;
		int free_frames = frame_number;
		int[] process_frame_number = new int[input_process_list.size()];		//remember how many frames which process have
		int[] process_pages_errors = new int[input_process_list.size()];		//remember how many pages errors which process have
		
		for(int i = 0; i < process_frame_number.length; i++){						//reserves some free frames for algorithm
			process_frame_number[i] = frame_number/(input_process_list.size()+1);	
			free_frames -= process_frame_number[i];
		}
		
		ArrayList<Process> process_list = new ArrayList<Process>();					//copy process_list
		for(Process process : input_process_list){
			process_list.add(new Process(process));
		}
		
		while(process_list.size() > 0){

			for(int i = 0; i < process_list.size(); i++)
				if(process_list.get(i).references.size() < 1)
					process_list.remove(i);
			
			for(int i = 0; i < process_list.size(); i++){
				process_pages_errors[i] = FLRU(process_frame_number[i], process_list.get(i).references, N);
				pages_errors +=process_pages_errors[i];
				if(process_pages_errors[i] > max_erros)
					process_pages_errors[i]++;
				if(process_pages_errors[i] < min_erros)
					process_pages_errors[i]--;
			}
		}
		
		return pages_errors;
	}
	
	public static int LRU(int frame_number, ArrayList<Integer> input_references) {
		int page_errors = 0;
		int remove_index = 0; 
		ArrayList<Integer> frames_used = new ArrayList<Integer>(); 	// indexes of frames which should be replaced (descending order)
		int[] frames;
		
		if(frame_number < input_references.size()){
			frames = new int[frame_number];
			for(int i = 0; i < frame_number; i++){
				frames[i] = i;
				frames_used.add(frames[i]);
			}	
			//fill frames at the beginning					
					
			while(!input_references.isEmpty()){
				int index = 0;
				while(index < frame_number && frames[index] != input_references.get(0))
					index++;
				if(index < frame_number){
					int tmp = frames_used.remove(frames_used.indexOf(index));	//delete index of current reference in frames_used and add it to the end of the list
					frames_used.add(tmp);														
					input_references.remove(0);
				}else{
					remove_index = frames_used.remove(0);
					frames_used.add(remove_index);
					frames[remove_index] = input_references.remove(0);

					page_errors++;
				}
			}
		}	
			return page_errors;
	} // LRU algorithm
	
	public static int ZLRU(int frame_number, ArrayList<Integer> input_references, ArrayList<Integer> workin_set, int N) {
		int page_errors = 0;
		int remove_index = 0; 
		int counter = 0;
		ArrayList<Integer> frames_used = new ArrayList<Integer>(); 	// indexes of frames which should be replaced (descending order)
		int[] frames;
		
		if(frame_number != 0){
			if(frame_number < input_references.size()){
				frames = new int[frame_number];
				for(int i = 0; i < frame_number; i++){
					frames[i] = i;
					frames_used.add(frames[i]);
				}	
				//fill frames at the beginning					
						
				while(!input_references.isEmpty() && counter < N){
					int index = 0;
					while(index < frame_number && frames[index] != input_references.get(0))
						index++;
					if(!workin_set.contains(input_references.get(0)))
						workin_set.add(input_references.get(0));					//add pages which were used to working set
					if(index < frame_number){
						int tmp = frames_used.remove(frames_used.indexOf(index));	//delete index of current reference in frames_used and add it to the end of the list
						frames_used.add(tmp);														
						input_references.remove(0);
					}else{
						remove_index = frames_used.remove(0);
						frames_used.add(remove_index);
						frames[remove_index] = input_references.remove(0);

						page_errors++;
					}
					counter++;				
				}
			}
		}
			return page_errors;
	} // LRU algorithm used in zone_allocation (expanded to working set and counter)
	
	public static int FLRU(int frame_number, ArrayList<Integer> input_references, int N) {
		int page_errors = 0;
		int remove_index = 0; 
		int counter = 0;
		ArrayList<Integer> frames_used = new ArrayList<Integer>(); 	// indexes of frames which should be replaced (descending order)
		int[] frames;
		
		
		if(frame_number < input_references.size()){
			frames = new int[frame_number];
			for(int i = 0; i < frame_number; i++){
				frames[i] = i;
				frames_used.add(frames[i]);
			}	
			//fill frames at the beginning					
					
			while(!input_references.isEmpty() && counter < N){
				int index = 0;
				while(index < frame_number && frames[index] != input_references.get(0))
					index++;
				if(index < frame_number){
					int tmp = frames_used.remove(frames_used.indexOf(index));	//delete index of current reference in frames_used and add it to the end of the list
					frames_used.add(tmp);														
					input_references.remove(0);
				}else{
					
					remove_index = frames_used.remove(0);
					frames_used.add(remove_index);
					frames[remove_index] = input_references.remove(0);

					page_errors++;
				}
				counter++;
			}
		}	
			return page_errors;
	} // FLRU algorithm used in frequency_control_page_fault_allocation (expanded to counter)
}
