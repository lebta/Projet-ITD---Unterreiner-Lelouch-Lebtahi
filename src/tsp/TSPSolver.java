package tsp;
 import java.util.*;

/** test2
 * 
 * This class is the place where you should enter your code and from which you can create your own objects.
 * 
 * The method you must implement is solve(). This method is called by the programmer after loading the data.
 * 
 * The TSPSolver object is created by the Main class.
 * The other objects that are created in Main can be accessed through the following TSPSolver attributes: 
 * 	- #m_instance :  the Instance object which contains the problem data
 * 	- #m_solution : the Solution object to modify. This object will store the result of the program.
 * 	- #m_timeLimit : the maximum time limit (in seconds) given to the program.
 *  
 * @author Damien Prot, Fabien Lehuede, Axel Grimault
 * @version 2017
 * 
 */
public class TSPSolver {
	

	// -----------------------------
	// ----- ATTRIBUTS -------------
	// -----------------------------

	/**
	 * The Solution that will be returned by the program.
	 */
	private Solution m_solution;

	/** The Instance of the problem. */
	private Instance m_instance;

	/** Time given to solve the problem. */
	private long m_timeLimit;

	
	// -----------------------------
	// ----- CONSTRUCTOR -----------
	// -----------------------------

	/**
	 * Creates an object of the class Solution for the problem data loaded in Instance
	 * @param instance the instance of the problem
	 * @param timeLimit the time limit in seconds
	 */
	public TSPSolver(Instance instance, long timeLimit) {
		m_instance = instance;
		m_solution = new Solution(m_instance);
		m_timeLimit = timeLimit;
	}

	// -----------------------------
	// ----- METHODS ---------------
	// -----------------------------

	/**
	 * **TODO** Modify this method to solve the problem.
	 * 
	 * Do not print text on the standard output (eg. using `System.out.print()` or `System.out.println()`).
	 * This output is dedicated to the result analyzer that will be used to evaluate your code on multiple instances.
	 * 
	 * You can print using the error output (`System.err.print()` or `System.err.println()`).
	 * 
	 * When your algorithm terminates, make sure the attribute #m_solution in this class points to the solution you want to return.
	 * 
	 * You have to make sure that your algorithm does not take more time than the time limit #m_timeLimit.
	 * 
	 * @throws Exception may return some error, in particular if some vertices index are wrong.
	 */
	
	
	
	public void solve() throws Exception {
	two_opt(ResolutionPlusProcheVoisin());
		
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 * @param solution la solution à améliorer
	 * @return une solution plus optimale
	 * @throws Exception
	 * 
	 * On essaie d'échanger des arcs 2 à 2, si cela réduit la distance on échange les 2 arcs,
	 * sinon on ne fait rien.
	 * 
	 */

	
	public ArrayList<Integer> two_opt(ArrayList<Integer> solution) throws Exception{
		
		
		boolean amelioration = true; //boolean restant vrai tant que la solution est ameliorable par 
		long startTime = System.currentTimeMillis();
		long spentTime = 0;
		do
		{
			amelioration=false;
			
			for (int i=0; i<solution.size()-1; i++) {
				for (int j=0; j<solution.size()-1; j++) {
					//choix des arcs à échanger
					int Pointi = solution.get(i);  //Point1 de l'arc i
					int Pointi1 = solution.get(i+1); //Point2 de l'arci
					int Pointj = solution.get(j); //Point1 de l'arc j
					int Pointj1 = solution.get(j+1); //Point 2 de l'arc j
					
					//L'échange des arcs raccourcit le parcous ?
					if ((j!=i-1)&&(j!=i)&&(j!=i+1)&&(m_instance.getDistances(Pointi,Pointi1)+m_instance.getDistances(Pointj,Pointj1))>(m_instance.getDistances(Pointi,Pointj)+m_instance.getDistances(Pointi1,Pointj1))) {
						
						//Echange des arcs i et j
						for (int k = 0; k<=(int)((j-i-1)/2); k++) {
							int Stockage = solution.get(i+1+k);
							solution.set(i+1+k, solution.get(j-k));
							solution.set(j-k,  Stockage);
						}
						amelioration=true;
					}
					
					
				}
			}
			
			spentTime = System.currentTimeMillis() - startTime;
		}while((spentTime < (m_timeLimit * 1000 - 100))&&(amelioration==true) );
		
		// remplissage de la solution
		for (int i=0; i<solution.size(); i++) {
			m_solution.setCityPosition(solution.get(i),i);
		}
		m_solution.setCityPosition(solution.get(0), m_instance.getNbCities());	
		return solution;
		}
	
	
	/**
	 * 
	 * @throws Exception
	 * 
	 *On part du point 0.
	 *A chaque étape, le point i+1 suivant est le plus proche voisin encore non visite de i. 
	 *
	 */
	
	public ArrayList<Integer> ResolutionPlusProcheVoisin() throws Exception {
		
		//initialisation
		int nbCities = m_instance.getNbCities();
		ArrayList<Integer> solution = new ArrayList<Integer>(); //liste de stockage de la solution en construction
		ArrayList<Integer> VilleNonVisite = new ArrayList<Integer>(); //liste des villes non visites
		
		for (int i = 1; i<nbCities; i++) { 
			VilleNonVisite.add(i);
		}
		
		int NbVilleVisite = 1;
		int VilleActuelle= 0;
		
		
		// choix du point de depart
		m_solution.setCityPosition(0,0);
		solution.add(0);
		
		
		//parcous de toutes les villes
		while(NbVilleVisite<nbCities) {
			long min = m_instance.getDistances(VilleActuelle, VilleNonVisite.get(0));
			int VilleSuivante = VilleNonVisite.get(0);
			int index = 0;
			
			//recherche de la ville la plus proche
			for( int i = 0; i<VilleNonVisite.size(); i++ ) { //parcours des villes restantes
				if (m_instance.getDistances(VilleActuelle, VilleNonVisite.get(i))<min) { //la ville en question est elles plus proche que les autres ?
					min = m_instance.getDistances(VilleActuelle, VilleNonVisite.get(i));
					VilleSuivante = VilleNonVisite.get(i);
					index = i;
				}
			}
			//Remplissage de la solution
			VilleNonVisite.remove(index);
			VilleActuelle=VilleSuivante;
			NbVilleVisite++;
			m_solution.setCityPosition(VilleActuelle, NbVilleVisite-1);
			solution.add(VilleActuelle);
			
		}
		
		//mettre en derniere position de la solution la premiere ville visitee
		m_solution.setCityPosition(0, nbCities);
		return solution;
		
	}
	
	
	/**
	 * @return solution Une solution du probleme trouvee a partir du tour de l'arbre
	 * @throws Exception
	 * 
	 *On part d'un tour de l'arbre qui est une liste de point avec des doublons.
	 *On reprend cette liste de point en supprimant les doublons.
	 *La liste issue de cette opération est une resolution du problème
	 *
	 */
	
	public ArrayList<Integer> ResolutionTwiceAroundTheTree() throws Exception{
		//Initialisation
		ArrayList<Integer> tour = TwiceAroundTheTree(); //tour de l'arbre
		ArrayList<Integer> solution = new ArrayList<>(); //liste de stockage de la solution en construction
		int j=0;
		//On prend les points du tour dans l'ordre, en 
		for(int i =0; i<tour.size(); i++) {
			
			//on verifie si le point est un doublon
			if(!(solution.contains(tour.get(i)))) {
				// on remplit la solution				
				solution.add(tour.get(i));
				m_solution.setCityPosition(tour.get(i),j);
				
				j++;
			}
		}
		m_solution.setCityPosition(0, m_instance.getNbCities());
		return solution;
	}
	
	/**
	 * @return Tour : un tour de l'arbre càd une liste de point parcourues en faisant "le tour"
	 *  de l'arbre en suivant ses branches.
	 * @throws Exception
	 * 
	 *On part d'un arbre càd d'une liste de couple de point.
	 *On définit le degre d'un point par le nombre de points auxquels il est relié par une branche de l'arbre.
	 *On part arbitrairement du point 0.
	 *A chaque iteration on choisit le point i+1 parmis les points reliés à i par une branche de l'arbre.
	 *On doit passer 2 fois par chaque branche, ce qui revient à passer degre fois par chaque point.
	 *
	 *
	 */
	
	
	public ArrayList<Integer> TwiceAroundTheTree() throws Exception {
		//Declaration
		ArrayList<Integer> Tour = new ArrayList<Integer>(); //liste stockant les points qui forment le tour de l'arbre
		ArrayList<ArrayList<Integer>> arbre = ArbreKruskal(); //liste de couple de points formant l'arbre
		ArrayList<Integer> degre = Degre(arbre); //liste stockant les degre des point de larbre, degre.get(i) represente le degre du point i, on decremente le degre du point i quand on passe par i 
		ArrayList<Integer> degreDebut = Degre(arbre);
		ArrayList<ArrayList<Integer>> PointAdjacentVisite = new ArrayList<ArrayList<Integer>>();  // liste de liste de point. PointAdjacentVisite.get(i) contient la liste des points adjacents à i deja visite par une branche qui part du i
		int sommeDegre = 0;
		
		//initialisation des var
		for (int i =0; i<degre.size(); i++) {
			sommeDegre = sommeDegre + degre.get(i);
			ArrayList<Integer> InitialisationPointAdjacentVisite= new ArrayList<Integer>();
			PointAdjacentVisite.add(InitialisationPointAdjacentVisite);
;		}
		
		//initialisation du tour et declaration des variables de boucles
		Tour.add(0);
		int PointActuel = 0;
		boolean condNonArret;
		int compt; //numero de l'arc en cours
		int PointPrecedent = 0;
		
		
	
		while(sommeDegre !=0) { //quand la somme des degre est nulle on est passe par chaque point degre fois
			// initialisation des variables de boucle
			condNonArret =true ; 
			compt = 0;
			
			while(condNonArret ) { //on parcourt les arcs de l'arbre jusqu'à en trouver un qui convient
				if(degre.get(PointActuel)==0) { //si le point actuel est de degre nul, il n'a plus qu'un voisin accessible, 
					if ((arbre.get(compt).get(0)==PointActuel)&&(degre.get( arbre.get(compt).get(1)) > 0 )) { //on regarde si dans la branche actuelle il y a le point actuel et un autre point de degre non nul		
						Tour.add(arbre.get(compt).get(1)); //on rajoute le point suivant au tour
						degre.set(arbre.get(compt).get(1), degre.get(arbre.get(compt).get(1))-1); //on reduit de 1 le degre du point suivant
						sommeDegre --;
						condNonArret =false; //on repasse au premier arc de la liste
						PointPrecedent = PointActuel; 
						PointActuel = arbre.get(compt).get(1);
						PointAdjacentVisite.get(PointActuel).add(PointPrecedent);
						
					}else if((arbre.get(compt).get(1)==PointActuel)&&(degre.get(arbre.get(compt).get(0))>0)) { //on regarde si dans la branche actuelle il y a le point actuel et un autre point de degre non nul
						Tour.add(arbre.get(compt).get(0));
						degre.set(arbre.get(compt).get(0), degre.get(arbre.get(compt).get(0))-1);
						sommeDegre --;
						condNonArret =false; //on repasse au premier arc de la liste
						PointPrecedent = PointActuel;
						PointActuel = arbre.get(compt).get(0);	
						PointAdjacentVisite.get(PointActuel).add(PointPrecedent);
					}
					
				}else { //le point acteule est de degre non nul, on ne pourra donc pas prendre le premier arc avec un autre point de degre non nul (risque de rater des points)
					if ((arbre.get(compt).get(0)==PointActuel)&&(degre.get( arbre.get(compt).get(1)) > 0 ) && (!(PointAdjacentVisite.get(PointActuel).contains(arbre.get(compt).get(1))) || PointAdjacentVisite.get(PointActuel).size()>=degreDebut.get(PointActuel))) { //on regarde si dans la branche actuelle il y a le point actuel et un autre point de degre non nul et si on est deja passé par cette branche
						Tour.add(arbre.get(compt).get(1));
						degre.set(arbre.get(compt).get(1), degre.get(arbre.get(compt).get(1))-1);
						sommeDegre --;
						condNonArret =false;//on repasse au premier arc de la liste
						PointPrecedent = PointActuel;
						PointActuel = arbre.get(compt).get(1);
						PointAdjacentVisite.get(PointActuel).add(PointPrecedent);
						
					}else if((arbre.get(compt).get(1)==PointActuel)&&(degre.get(arbre.get(compt).get(0))>0)&&(!(PointAdjacentVisite.get(PointActuel).contains(arbre.get(compt).get(0))) || PointAdjacentVisite.get(PointActuel).size()>=degreDebut.get(PointActuel))) { //on regarde si dans la branche actuelle il y a le point actuel et un autre point de degre non nul et si on est deja passé par cette branche
						Tour.add(arbre.get(compt).get(0));
						degre.set(arbre.get(compt).get(0), degre.get(arbre.get(compt).get(0))-1);
						sommeDegre --;
						condNonArret =false; // on repasse au premier arc de la liste
						PointPrecedent = PointActuel;
						PointActuel = arbre.get(compt).get(0);
							
						PointAdjacentVisite.get(PointActuel).add(PointPrecedent);
				}				
			}
			compt++; // on passe à l'arc suivant
				
			}	
		}
		return Tour;				
	}
	
	/**
	 * @param arbre : un arbre de point càd une liste de couple de point (representant les branches dont l'arbre est constitue)
	 * @return degre : la liste des degre des points, degre.get(i) correspond au degre du point i
	 * @throws Exception
	 * 
	 *On part d'un arbre càd d'une liste de couple de point.
	 *On définit le degre d'un point par le nombre de points auxquels il est relié par une branche de l'arbre.
	 *On part arbitrairement du point 0.
	 *
	 */
	
	//renvoi le degre du sommet i dans 1 arbre
	public ArrayList<Integer> Degre(ArrayList<ArrayList<Integer>> arbre){
		ArrayList<Integer> listeDegre = new ArrayList<Integer>();
		for(int i = 0; i<this.m_instance.getNbCities(); i++) { //on parcourt tous les points
			int deg = 0;
			for (int j=0 ; j<arbre.size(); j++) { //on regarde combien de branches contiennent le point i
				if (arbre.get(j).contains(i)) {
					deg++;
				}
			}
			listeDegre.add(deg);
		}
		
		return listeDegre;
	}
	
	
	/**
	 * @return ArbreFinal : un arbre couvrant de poids minimal, trouvé par lalgorithme de kruskal 
	 * C'est une liste de liste de 2 points, chaque couple representant une branche de l'arbre.
	 * @throws Exception
	 * 
	 *On part d'une liste de branches de poids triées.
	 *On ajoute la branche de poid le plus faible à l'arbre si celle ci n'entraine pas la formation d'un cycle
	 *
	 */
	
	public ArrayList<ArrayList<Integer>> ArbreKruskal() throws Exception{
		
		ArrayList<int[]> DistanceTriee = tri(); //liste des branches triées par ordre de poids
		ArrayList<ArrayList<Integer>> LArbre = new ArrayList<ArrayList<Integer>>(); //listes des sous arbres déja construits
		ArrayList<ArrayList<Integer>> ArbreFinal = new ArrayList<ArrayList<Integer>>(); //arbre retournee par lalgo
		
		
		for (int i = 0; i<DistanceTriee.size(); i++) { //on parcourt tout les arcs dans l'ordre
			int Point1 = DistanceTriee.get(i)[0];
			int Point2 = DistanceTriee.get(i)[1];
			int NumeroArbre1 = -1;
			int NumeroArbre2 = -1;
			ArrayList<Integer> arc = new ArrayList<Integer>();
			arc.add(Point1);
			arc.add(Point2);
			
			for (int j=0; j<LArbre.size(); j++) { //on parcourt tout les sous arbres deja construits
				if(LArbre.get(j).contains(Point1)) { //on regarde si Point1 appartient deja à un sous arbre deja construit
					NumeroArbre1 = j;
				}
				if(LArbre.get(j).contains(Point2)) { //on regarde si Point2 appartient à un sous arbre déja construit
					NumeroArbre2 = j;
				}
			}
			
			//On rajoute l'arc seulement si point1 et point2 ne sont pas dans un meme sous arbre, on differencie 4cas
			
			//Cas ou point1 et point2 sont dans 2 sous arbres differents
			if((NumeroArbre1!=NumeroArbre2)&&(NumeroArbre1!=-1)&&(NumeroArbre2!=-1)) { 
				ConcatenationSansDoublon(LArbre.get(NumeroArbre1),LArbre.get(NumeroArbre2)); //les deux sous arbres forment un seul sous arbre
				LArbre.remove(NumeroArbre2); //on supprime le sous arbre excedentaire
				ArbreFinal.add(arc); //on ajout l'arc a l'arbre
			}
			
			//Cas ou point1 est dans un sous arbre et pas point2, 
			if((NumeroArbre1!=-1)&&(NumeroArbre2==-1)) {
				LArbre.get(NumeroArbre1).add(Point2); //on ajoute pointt2 au sous arbre de point1
				ArbreFinal.add(arc);
			}
			
			//Cas ou point2 est dans un sous arbre et pas point1
			if((NumeroArbre2!=-1)&&(NumeroArbre1==-1)) {
				LArbre.get(NumeroArbre2).add(Point1); //on ajoute point1 au sous arbre de point2
				ArbreFinal.add(arc);
			}
			
			//Cas ou ni point1 ni point2 ne sont ds un arbre
			if ((NumeroArbre1 == -1)&&(NumeroArbre2==-1)) {
				//on crée un nouveay sous arbre constitué de point1 et point2
				ArrayList<Integer> NouvelArbre = new ArrayList<Integer>();
				NouvelArbre.add(Point1);
				NouvelArbre.add(Point2);
				LArbre.add(NouvelArbre);
				ArbreFinal.add(arc);
			}
		}
		return ArbreFinal;
		
	}
	
	/**
	 * 
	 * @param liste1 la premiere liste à concatener 
	 * @param liste2 la deuxieme liste à concatener
	 * @throws Exception
	 * 
	 * La méthode concatène les deux listes en éliminant les doublons.
	 * La liste isssue de la concaténation est affcetée à liste1, liste2 est inchangée
	 * 
	 */
	
	public void ConcatenationSansDoublon(ArrayList<Integer> liste1 , ArrayList<Integer> liste2){
		for(int i =0; i<liste2.size(); i++) {
			if (!(liste1.contains(liste2.get(i)))) {
				liste1.add(liste2.get(i));
			}
		}
	}
	
	
	
	/**
	 * 
	 *
	 * @return ListeArc : la liste des arcs du graphes triés par ordre de poid croissant (une liste de liste de 2 entiers)
	 * @throws Exception
	 * 
	 * Pour trier les arcs, on a crée une classe arc afin de pouvoir utiliser un sort directement dessus
	 * On renvoie ensuite une liste de liste de 2 entiers en récupérant les points de la liste d'arcs triés
	 */
	
	public ArrayList<int[]> tri() throws Exception{ 
		//On crée la liste d'arcs et on la trie
		List<Arc> ListeArc = new ArrayList<Arc>();  
		for (int i =0; i<this.m_instance.getNbCities(); i++) {
			for (int j = 0; j<i; j++) {
				ListeArc.add(new Arc(i, j, this.m_instance.getDistances(i, j), this.m_instance));
			}
		}
		Collections.sort(  ListeArc);
		
		//On transforme la liste d'arc en une liste dun bon format
		ArrayList<int[]> ListePointOrdonnee = new ArrayList<int[]>();
		for (int i=0; i<ListeArc.size(); i++) {			
			int[] Couple = new int[2];
			Couple[0] = ListeArc.get(ListeArc.size()-1-i).getPoint1(); 
			Couple[1] = ListeArc.get(ListeArc.size()-1-i).getPoint2();
			ListePointOrdonnee.add(Couple);
		}
		return ListePointOrdonnee;
	}
	
	
	

	// -----------------------------
	// ----- GETTERS / SETTERS -----
	// -----------------------------

	/** @return the problem Solution */
	public Solution getSolution() {
		return m_solution;
	}

	/** @return problem data */
	public Instance getInstance() {
		return m_instance;
	}

	/** @return Time given to solve the problem */
	public long getTimeLimit() {
		return m_timeLimit;
	}

	/**
	 * Initializes the problem solution with a new Solution object (the old one will be deleted).
	 * @param solution : new solution
	 */
	public void setSolution(Solution solution) {
		this.m_solution = solution;
	}

	/**
	 * Sets the problem data
	 * @param instance the Instance object which contains the data.
	 */
	public void setInstance(Instance instance) {
		this.m_instance = instance;
	}

	/**
	 * Sets the time limit (in seconds).
	 * @param time time given to solve the problem
	 */
	public void setTimeLimit(long time) {
		this.m_timeLimit = time;
	}

}
