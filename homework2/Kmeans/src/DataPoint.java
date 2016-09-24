import java.util.ArrayList;
import java.util.List;

public class DataPoint {
	private List<Integer> data;
	private int cluster;
	private int id;
	private int rightcluster;//original cluster from the data
	public DataPoint(List<Integer> temp) {
		data = new ArrayList<Integer>();
		this.cluster = 0;
		this.id = temp.get(0);
		for (int i = 1; i < temp.size() - 1; i++) {
			this.data.add(temp.get(i));
		}
		this.rightcluster = temp.get(temp.size() - 1);
	}
	public void printa(){
		for (int a: data) {
			System.out.print(a);
			System.out.print(' ');
		}
		System.out.print('\n');
		
	}
	public int getrightcluster(){
		return rightcluster;
	}
	public int size(){
		return data.size();
	}
	public int get(int index) {
		return data.get(index);
	}
	public void setCluster(int a){
		cluster = a;
	}
	public void set(int index, int value) {
		data.set(index, value);
	}
	public int getCluster() {
		return cluster;
	}
	public List<Integer> getdata() {
		return data;
	}
}
