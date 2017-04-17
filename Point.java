import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Point{
	private double x, y;
	int clusterID=0;

	public Point(double x, double y){
		this.setCoordinate(x, y);
	}
	public void setCoordinate(double x, double y){
		this.x = x;
		this.y = y;
	}

	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}

	//calculate the distance between a point and a centroid
	public static double distance(Point p, Point centroid, int cluster_num){ //cluster_num: control sse
		double dis_square = Math.pow(p.getX()-centroid.getX(),2) + Math.pow(p.getY()-centroid.getY(),2);
		if(cluster_num==-1)
			return Math.sqrt(dis_square);
		return (dis_square);
	}
//////////////////////////need revised////////////////
	//min, max: coordinate bound; 
/*	public static Point randomCentroid(double min, double max){
		Random r = new Random();
		double x = min + (max-min) * r.nextDouble();
		double y = min + (max-min) * r.nextDouble();
		return new Point(x,y);
	}*/

	public static List<Point> dataCentroids(String inputFile){
		List<Point> centroids = new ArrayList();
		double x,y;
		try{
			FileReader fr = new FileReader(inputFile);
			BufferedReader br = new BufferedReader(fr);
			while (br.ready()) {				
				String s_tmp = br.readLine();
				String[] tokens = s_tmp.split(" ");
				x=Double.parseDouble(tokens[0]); 
				y=Double.parseDouble(tokens[1]);
				
				Point p = new Point(x,y);
				centroids.add(p);
			}

			fr.close();
		}
		catch(IOException e){
			System.out.println("error to read file");
		}
		return centroids;

	}

	// read in input points
	public static List<Point> dataPoints(String inputFile){
		List<Point> points = new ArrayList();
		double x,y;
		try{
			FileReader fr = new FileReader(inputFile);
			BufferedReader br = new BufferedReader(fr);
			while (br.ready()) {				
				String s_tmp = br.readLine();
				String[] tokens = s_tmp.split(" ");
				x=Double.parseDouble(tokens[0]); 
				y=Double.parseDouble(tokens[1]);
				
				Point p = new Point(x,y);
				points.add(p);
			}
			fr.close();
		}
		catch(IOException e){
			System.out.println("error to read file");
		}
		return points;
	}

	public void setClusterID(int id){
		this.clusterID = id;
	}
	public int getClusterID(){
		return clusterID;
	}
	public String toString(){ //overrite, for output Cluster
		return x + " " + y ;
	}

}