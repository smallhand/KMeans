import java.util.List;
import java.util.ArrayList;

import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.dataset.PointDataSet;


public class KMeans_nofile{
	private int NUM_CLUSTERS = 8; // temporary 3 clusters
	private double MIN_COORDINATE = 0.0, MAX_COORDINATE = 1000000.0;
	private double THRESHOLD = 0.05; // the bound of centroids convergence
	
	private List<Point> points;
	private List<Cluster> clusters;

	public KMeans_nofile(){
		this.points = new ArrayList();
		this.clusters = new ArrayList();
	}

	public static void main(String[] args){
		KMeans_nofile k = new KMeans_nofile();
		k.init(args[0], args[1]); //input file
		k.mainControl();
	}

	public void init(String inputFile, String inputCentroids){	// 1. init
		// input data(points)
		points = Point.dataPoints(inputFile);

		//open the new file to write
		//openNewFile();

		List<Point> centroids = Point.dataCentroids(inputCentroids);
		// create and initialize each cluster: id, centroid
		for (int i=0; i<NUM_CLUSTERS; i++){
			Cluster cluster = new Cluster(i);
			cluster.setCentroid(centroids.get(i));
			clusters.add(cluster);
		}

		// print initial
		//for (int i=0; i<NUM_CLUSTERS; i++){
		//	clusters.get(i).outCluster("file.txt");
		//}
	}

	public void mainControl(){ // 2. assign, 3. update
		int itr = 0; // record iteration times		
		boolean finish = false;
		//long timer1, timer2, timer3, timer4;
		//float time=0;
		while (!finish){
			++itr;			
			List<Point> preCentroids = getCentroids(); // non-update Centroid
			clearClusterPoint(); //clear the pre-step points

			//timer1 = System.currentTimeMillis();

			assign(); 
			update();
			List<Point> currentCentroids = getCentroids();

			double dis=0.0;
			for (int i=0; i<NUM_CLUSTERS; i++){
				dis += Point.distance(preCentroids.get(i), currentCentroids.get(i),-1);
			}
			//timer2 = System.currentTimeMillis();
			
			//System.out.println(dis+"\n");

			//sse();
			//outInfo(itr,dis);
			plotCluster();
			/*for (int i=0; i<NUM_CLUSTERS; i++){
				Cluster c = clusters.get(i);
				c.outCluster("file.txt");
			}*/
			//timer3 = System.currentTimeMillis();

			reassign(); // it will be effective if a cluster has no points

			//timer4 = System.currentTimeMillis();
			
			//time = time + ((timer4-timer3)+(timer2-timer1));


			if(dis<THRESHOLD){ // end iteration

				/*try{ // cluser
		    		FileWriter fw = new FileWriter("SSE.txt", true);
		    		fw.write("Executing Time: "+time + "ms\n");
		    		fw.close();
				} 
				catch (IOException e) {
					System.out.println("error to write file");
				}*/
				finish = true;
			}
		}
	}
	public void plotCluster(){
		double[][] p_plot, c_plot; //point & centroid
		JavaPlot graph = new JavaPlot();

		graph.set("xlabel","'x'");
		graph.set("ylabel","'y'");
		graph.set("key", "at -30, 1000");
		graph.set("title","'KMeans'");

		DataSetPlot dataPoints, dataCentroids;

		for (Cluster c: clusters){
			List<Point> ps = c.getPoints();
			p_plot = new double[ps.size()+1][2];
			c_plot = new double[1][2];
			int i=0;
			for(Point p: ps){
				p_plot[i][0] = p.getX();
				p_plot[i][1] = p.getY();
				++i;
			}
			c_plot[0][0] = c.getCentroid().getX();
			c_plot[0][1] = c.getCentroid().getY();

			dataPoints = new DataSetPlot(p_plot);
			dataPoints.setTitle("cluster   "+c.getId());
			dataCentroids = new DataSetPlot(c_plot);
			dataCentroids.setTitle("centroid "+c.getId());

			graph.addPlot(dataPoints);
			graph.addPlot(dataCentroids);
		}
		graph.plot();
	}

	public void assign(){
		double dis = 0.0;  // the distance between a point and a centroid
		double min_dis;
		int belong_cluster=0; // suppose the point belong to cluster0 first
		for (Point p : points) { // all points need to compared to all centroid
			min_dis = MAX_COORDINATE;
			for(int i=0; i<NUM_CLUSTERS; i++){ //compare from cluster0
				Cluster c = clusters.get(i);
				dis = Point.distance(p, c.getCentroid(),-1);
				if(dis<min_dis){
					min_dis=dis;
					belong_cluster=i;
				}
			}
			p.setClusterID(belong_cluster); // set the point belong to which cluster;
			clusters.get(belong_cluster).addPoint(p); // add the point to its belong cluster
		}
	}

	/*public void sse(){
		double dis = 0.0;
		double sum = 0.0;
		System.out.println("sse:");	
		for(Cluster c: clusters){
			List<Point> ps = c.getPoints();
			for(Point p : ps){
				dis += Point.distance(p, c.getCentroid(),NUM_CLUSTERS);	
			}
			sum = sum + dis/ps.size();
		}
		try{ // cluser
		    FileWriter fw = new FileWriter("SSE.txt", true);
		    fw.write("SSE_SUM"+" : "+sum+"\n");
		    fw.close();
		} 
		catch (IOException e) {
			System.out.println("error to write file");
		}
		System.out.println("SSE_SUM"+": "+sum);
		System.out.println();
	}*/

	public void update(){ // 3. update new centroids
		for (Cluster c: clusters){
			List<Point> ps = c.getPoints();
			int num_points = ps.size();
			double sumX, sumY;
			sumX=sumY=0.0;

			for(Point p : ps){
				sumX += p.getX(); 
				sumY += p.getY();
			}

			Point centroid = c.getCentroid();
			if(num_points>0){
				double x = sumX / num_points;
				double y = sumY / num_points;				
				Point p = new Point(x,y);
				c.setCentroid(p);
			}
		}
	}

//////////////check/////////////
	public void reassign(){ //if a cluster has no points, then we will assign a nearest point to it.
		int id = -1;
		for (Cluster c : clusters){
			List<Point> ps = c.getPoints();
			Point min_p = new Point(MAX_COORDINATE,MAX_COORDINATE); 
			double min_dis = MAX_COORDINATE;
			double dis=0.0;
			if(ps.size()==0){
				for (Point p : points){
					dis = Point.distance(p,c.getCentroid(),-1);//0:not sse
					if(dis<min_dis){
						min_dis = dis;
						min_p = p;
					}
				}
				c.setCentroid(min_p);				
			}		
		}
	}

	public void outInfo(int itr, double dis){
		try{ // cluser
		    FileWriter fw = new FileWriter("file.txt", true);
		    fw.write("====================\n");
		    fw.write("iteration: " + itr + "\n");
		    fw.write("distance: " + dis + "\n\n");
		    fw.close();
		} 
		catch (IOException e) {
			System.out.println("error to write file");
		}
	}

	public void openNewFile(){
		try{
			FileWriter fw = new FileWriter("file.txt");
			fw.close();
		}
		catch (IOException e) {
			System.out.println("error to write file");
		}
	}

	public void clearClusterPoint(){
		for (Cluster c : clusters){
			c.clearPoints();
		}
	}
	public List<Point> getCentroids(){
		List<Point> centroids = new ArrayList(NUM_CLUSTERS);
		for (Cluster c : clusters){
			Point centroid = c.getCentroid();
			Point p = new Point(centroid.getX(), centroid.getY());
			centroids.add(centroid);
		}
		return centroids;
	}
}
