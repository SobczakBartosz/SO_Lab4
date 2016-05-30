import java.util.ArrayList;
import java.util.Random;

public class Generator {

	public static void main(String[] args){
		
		ArrayList<Process> process_list = generateProcesses(10, 30, 50);
		
		System.out.printf("%s %d %n", "Iloœæ b³êdów stron dla 10 dla 100 ramek procesów dla algorytmu równego: ", Algorithms.even_allocation(process_list, 100)); 
		System.out.printf("%s %d %n", "Iloœæ b³êdów stron dla 10 dla 100 ramek procesów dla algorytmu proporcjonalnego: ", Algorithms.proportional_allocation(process_list, 100));
		System.out.printf("%s %d %n", "Iloœæ b³êdów stron dla 10 dla 100 ramek procesów dla algorytmu sterowania czêstoœci¹ b³êdów: ", Algorithms.frequency_control_page_fault_allocation(process_list, 100, 5, 10, 20));
		System.out.printf("%s %d %n", "Iloœæ b³êdów stron dla 10 dla 100 ramek procesów dla algorytmu strefowego: ", Algorithms.zone_allocation(process_list, 100, 30));
	}
	
	public static ArrayList<Process> generateProcesses(int number_of_processes, int max_pages, int max_references){
		ArrayList<Process> process_list = new ArrayList<Process>();
		Random rand = new Random();
		
		for(int i = 0; i < number_of_processes; i++)
			process_list.add(new Process(rand.nextInt(max_pages-5)+5, rand.nextInt(max_references-10)+10));
		
	return process_list;
	}
}
