import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Kmeans {

	public static void main(String[] args) {
		//load the file and put the data without the rightcluster in a list.
		//load the data from mysql database qdata table clenadata
		//cleandata has 669 data and 9 col without the datanumber last one is the rightcluster
		List<DataPoint> Alldata = new ArrayList<DataPoint>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conn = null;
		try {
		    conn =
		       DriverManager.getConnection("jdbc:mysql://localhost/pdata?" +
		                                   "user=root&password=wocao");

		    Statement stmt = null;
		    ResultSet rs = null;    
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery("SELECT * FROM cleandata");
		    
		    //print out the data in the table
		    while(rs.next()){
		    	List<Integer> temp = new ArrayList<Integer>();
		    	for(int i=1; i<11; i++){
		    		temp.add(rs.getInt(i));
		    		//System.out.print(rs.getInt(i)+" ");
		    	}	
		    		DataPoint oneData = new DataPoint(temp);
		    		Alldata.add(oneData);
		    		//System.out.println();;
		    	
		    }
		    
		        // Now do something with the ResultSet ....
		    

		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		//find the two data that has the biggest distance, this part is just for k = 2.
//		int rightbi = 0;
//		int rightmi = 0;
//		List<Cluster> clusters = new ArrayList<Cluster>();
//		int max = 0;
//		for (int i = 0; i < Alldata.size(); i++) {
//			for (int j = i; j < Alldata.size(); j++) {
//				int temp = 0;
//				for (int h = 0; h < Alldata.get(i).size(); h++) {
//					 temp += Math.abs(Alldata.get(i).get(h) - Alldata.get(j).get(h));
//				}
//				if (temp > max) {
//					max = temp;
//					Cluster a = new Cluster(Alldata.get(i), 2);
//					Cluster b = new Cluster(Alldata.get(j), 4);
//					//delete the data in the clusters before. we only need two list
//					clusters = new ArrayList<Cluster>();
//					clusters.add(a);
//					clusters.add(b);
//				}
//			}
//			
//			
//			//print out the right result
//			if (Alldata.get(i).getrightcluster() == 2) {
//				rightbi++;
//			} else {
//				rightmi++;
//			}
//		}
		
		//generator n = k Cluster from Alldata. Just randomly pick n Datapoints from the dataset and make them a Cluster
		//and put them in clusters.
		System.out.println(Alldata.size() + "****");
		List<Cluster> clusters = new ArrayList<Cluster>();
		for (int k = 1; k <= 7; k++ ) {
			Random temran = new Random();
			int picknumber = temran.nextInt(700);
			Cluster tempClu = new Cluster(Alldata.get(picknumber), k);
			clusters.add(tempClu);
			tempClu.printcent();
		}

		//print the right culster and the centeroids and the id of cluster
//		clusters.get(0).printcent();
//		clusters.get(2).printcent();
//		System.out.println(clusters.get(0).getID());
//		System.out.println(clusters.get(1).getID());
//		
		
		//kmeans main function is here.
		int tem = 0;
		int g = 0;
		List<Double> result = new ArrayList<Double>();
		do {
			//assign all the point in data to two different centroids
			for (DataPoint tempPoint: Alldata) {
				int min = Integer.MAX_VALUE;
				for (Cluster tempCluster: clusters) {
					int tempDis = 0;
					for (int h = 0; h < tempPoint.size(); h++) {
						 tempDis += Math.abs(tempPoint.get(h) - tempCluster.getCentroid(h));
					}
					if (tempDis < min) {
						min = Math.min(min, tempDis);
						tempPoint.setCluster(tempCluster.getID());
					}
					
//					System.out.print(tempDis + "  min = " + min);
//					System.out.print(' ');
					//System.out.println(tempPoint.getCluster());
				}
				//System.out.println(tempPoint.getCluster());
				
				// cluster the point to these two cluster
				for (int i = 1; i <= 7; i++) {
					if (tempPoint.getCluster() == i) {
						clusters.get(i - 1).addDataIn(tempPoint);
					}
				}
			}
			double errorSum = 0;
				for (Cluster tempclu: clusters) {
					System.out.println(tempclu.sizeOfDataIn());
					errorSum += tempclu.getError();
				}
				result.add(errorSum);
				System.out.println("this is the errorSum" + errorSum + "******");
				
			//updata the centroid with average
			for (Cluster tempCluster: clusters) {
				tempCluster.updataCentroid();
			}
			// 	present the new centreoids
			//	clusters.get(0).printcent();
			//	clusters.get(1).printcent();
			tem++;
		} while(tem < 20);
		
		for (double i : result) {
			System.out.printf("%.2f",i);
			System.out.print(' ');
		}
//		for (Cluster tempclu: clusters) {
//			System.out.println(tempclu.sizeOfDataIn());
//		}
//		System.out.println(clusters.get(0).sizeOfDataIn());
//		System.out.println(clusters.get(1).sizeOfDataIn());
		
		//calculate how many points in each cluster
//		int bi = 0;
//		int mi = 0;
//		for(DataPoint tempPoint: Alldata) {
//			if (tempPoint.getCluster() == 2) {
//				bi++;
//			} else {
//				mi++;
//			}
//		}
//		System.out.println(bi);
//		System.out.println(mi);
//		System.out.println(bi + mi);

	}

	
}
