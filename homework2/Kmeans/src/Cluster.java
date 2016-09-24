import java.util.ArrayList;
import java.util.List;

public class Cluster {
	private DataPoint Centroid;
	private int id;
	private List<DataPoint> dataIn; //all the points in this Cluster
	public Cluster(DataPoint data, int id){
		this.dataIn = new ArrayList<DataPoint>();
		this.Centroid = data;
		this.id = id;
		
	}
	public int size(){
		return Centroid.size();
	}
	public int sizeOfDataIn() {
		return dataIn.size();
	}
	public int getCentroid(int index) {
		return Centroid.get(index);
	}
	public void addDataIn(DataPoint tempdata) {
		dataIn.add(tempdata);
	}
	public DataPoint getDataIn(int number) {
		return dataIn.get(number);		
	}
	public int getID() {
		return id;
	}
	public void printcent(){
		Centroid.printa();
	}
	public void updataCentroid() {
		for (int i = 0; i < Centroid.size(); i++) {
			int sum = 0;
			for (DataPoint tempPoint: this.dataIn) {
				sum += tempPoint.get(i);
			}
			Centroid.set(i, sum/this.dataIn.size());
		}
		this.dataIn = new ArrayList<DataPoint>();
	}
	public double getError(){
		int c = 0;
		int d = 0;
		for (DataPoint tempPoint: dataIn) {
			if (tempPoint.getrightcluster() == 2) {
				c++;
			} else {
				d++;
			}
		}
		//this.id = Math.max(a, b);
		int min1 = Math.min(c, d);
		return (double) min1 / (double) (c + d);
	}
	
}
