import com.panayotis.gnuplot.JavaPlot;
 
public class test {
    public static void main(String[] args) {
        JavaPlot p = new JavaPlot();
		p.getAxis("x").setLabel("x axis");
		p.getAxis("y").setLabel("y axis");
        p.addPlot("sin(x)");
        p.plot();
    }
}

