package tsp.heuristic;
import java.awt.List;
import java.util.ArrayList;
import tsp.Instance;

public class Fourmi extends AHeuristic {
	
	private ArrayList<Integer> villesVisitees;     // toutes les villes visit�es par la fourmi
	private ArrayList<Integer> villesAVisiter;     // toutes les villes encore � visiter
	
	private long longChemin;                       // compteur de longueur du chemin parcouru

	public Fourmi(Instance instance, String s) throws Exception {
		super(instance,s);
		this.villesAVisiter= new ArrayList<Integer>(instance.getNbCities());
		for (int i=0;i<instance.getNbCities();i++) {
			this.villesAVisiter.add(i);
		}
		this.villesVisitees=new ArrayList<Integer>();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void solve() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void avance(Pheromones pheromone, Instance instance) {                         //faire �voluer la fourmi � chaque itteration
		int n = this.villesVisitees.get(this.villesVisitees.size());                      //derni�re ville parcourue par la fourmi : n
		ArrayList<Integer> L=pheromone.get(n, instance);                                 //Liste des ph�romones au d�part de la ville n
	}
	
	public int villeSuivante(int n, Instance instance, Pheromones pheronome) {
		int M=0;
		int index=0;
		for (int i=0;i<instance.getNbCities()-1;i++) {
			if (pheromone.get(n).get(i)>M && i!=n) {
				M=pheromone.get(n).get(i);
				index=i;
			}
			
		}
		if (this.villesVisitees.contains(index)) {
			ArrayList<Integer> copie = pheromone.get(n).clone();
			int M2=0;
			int index2=index;
			while (this.villesVisitees.contains(index2)) {
			
				for (int i=0;i<copie.size();i++) {
					if (pheromone.get(n).get(i)>M && i!=n) {
						M=pheromone.get(n).get(i);
						index2=i;
					}
				
			}
		}
		
	}

}
