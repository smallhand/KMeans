import java.util.ArrayList;
import java.util.List;
import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.dataset.PointDataSet;


//import java.io.File;
//import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.OutputStreamWriter;
import java.io.FileWriter;

public class Cluster{

	public Point centroid;
	public List<Point> points;
	public int id; // cluster id

	public Cluster(int id){
		this.centroid = null;
		this.points = new ArrayList();
		this.id = id;
	}

	public void addPoint(Point p){
		points.add(p);
	}
	public void setPoints(List ps){
		this.points = ps;
	}
	public List<Point> getPoints(){
		return points;
	}
	public void setCentroid(Point c){
		this.centroid = c;
	}
	public Point getCentroid(){
		return centroid;
	}
	public int getId(){
		return id;
	}
	public void clearPoints(){ //used in the beginning of the next round
		points.clear();
	}
	public void outCluster(){
		try{ // cluser
		    FileWriter fw = new FileWriter("the-file-name.txt",true);
		   		    
		    fw.write("cluser: " + id + "\n");
		    fw.write("centroid: " + centroid +"\n");
		    fw.write("points: \n");
			
		    for (Point p: points){
		    	fw.write(p + "\n");			
		    }
			
		    fw.write("\n");
		    fw.close();
		    
		} 
		catch (IOException e) {
			System.out.println("error to write file");
		}
	}

}
