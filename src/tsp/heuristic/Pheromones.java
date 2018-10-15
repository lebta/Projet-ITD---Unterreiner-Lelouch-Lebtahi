package tsp.heuristic;
import java.awt.List;
import java.util.ArrayList;
import tsp.Instance;

public class Pheromones {
	private ArrayList<ArrayList <Integer>> pheromone;
	
	public Pheromones(ArrayList<Integer> pheromone, Instance instance) {
		this.pheromone=new ArrayList< ArrayList<Integer>>(instance.getNbCities());
	}
	
	public ArrayList<Integer> get(int n, Instance instance) {
		ArrayList<Integer> L= new ArrayList<Integer>(instance.getNbCities()-1);
		int x =0;
		for (ArrayList<Integer> X : this.pheromone) {
			if (x<n) {
				L=X;
				x++;
			}
			
		}
		return L;
	}
	
	

}
