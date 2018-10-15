package tsp;

public class Arc {
	private int Point1;
	private int Point2;
	private long distance;
	private Instance instance;
	
	public Arc(int pt1, int pt2, long dist, Instance i) {
		this.Point1 = pt1;
		this.Point2 = pt2;
		this.distance = dist;
		this.instance = i;
	}
	
	public int compareTo(Arc a) throws Exception {
		if ( (this.instance.getDistances(this.Point1, this.Point2)==(a.instance.getDistances(a.Point1 ,a.Point2)))) {
			return 0;
		}else if ((this.instance.getDistances(this.Point1, this.Point2)<(a.instance.getDistances(a.Point1 ,a.Point2)))) {
			return 1;
		}else {
			return -1;
		}
	}
}
