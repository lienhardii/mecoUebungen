package uebung_03;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Rastern_orig extends JPanel {

	private BufferedImage canvas;

	/* ---------- f��r kantentabellen und filter ---------- */
	/* initialize test polygon A */
	Point2D[] polyA = new Point2D[5];
	/* initialize test polygon B */
	Point2D[] polyB = new Point2D[5];
	// initialize test polygon C */
	Point2D[] polyC = new Point2D[12];

	private double unweightedFilter(double distance) {
		double intensity, blending, L0, L1;
		/* done: implement a unweighted filter using a linear function */
		intensity = 1 - distance; // f(d)
		// blending = intensity * L0 + (1-intensity) * L1
		return intensity;

	}

	private double weightedFilter(double distance) {
		double intensity, blending, L0, L1;
		/* done: implement a weighted filter using a square root function */
		intensity = 1 - Math.sqrt(distance);
		// blending = intensity * L0 + (1-intensity) * L1
		return intensity;
	}
	/*---------- fuer kantentabellen und filter end ----------*/

	public Rastern_orig(int width, int height) {
		/*---------- fuer kantentabellen und filter start ----------*/
		polyA[0] = new Point2D.Double(10.0, 80.0);
		polyA[1] = new Point2D.Double(50.0, 55.0);
		polyA[2] = new Point2D.Double(90.0, 70.0);
		polyA[3] = new Point2D.Double(65.0, 20.0);
		polyA[4] = new Point2D.Double(25.0, 30.0);

		polyB[0] = new Point2D.Double(10.0, 40.0);
		polyB[1] = new Point2D.Double(20.0, 95.0);
		polyB[2] = new Point2D.Double(60.0, 10.0);
		polyB[3] = new Point2D.Double(60.0, 80.0);
		polyB[4] = new Point2D.Double(95.0, 55.0);

		polyC[0] = new Point2D.Double(30.0, 30.0);
		polyC[1] = new Point2D.Double(30.0, 40.0);
		polyC[2] = new Point2D.Double(60.0, 40.0);
		polyC[3] = new Point2D.Double(60.0, 30.0);
		polyC[4] = new Point2D.Double(50.0, 30.0);
		polyC[5] = new Point2D.Double(50.0, 60.0);
		polyC[6] = new Point2D.Double(60.0, 60.0);
		polyC[7] = new Point2D.Double(60.0, 50.0);
		polyC[8] = new Point2D.Double(30.0, 50.0);
		polyC[9] = new Point2D.Double(30.0, 60.0);
		polyC[10] = new Point2D.Double(40.0, 60.0);
		polyC[11] = new Point2D.Double(40.0, 30.0);
		/*---------- ruer kantentabellen und filter end ----------*/

		canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		// fillCanvas(Color.BLUE);
		// drawRect(Color.RED, 0, 0, width/2, height/2);
	}

	public static void main(String[] args) {
		int width = 640;
		int height = 480;
		JFrame frame = new JFrame("Direct draw demo");

		Rastern_orig panel = new Rastern_orig(width, height);
		panel.drawLine(10, 10, 100, 20, new Color(0, 0, 0));
		panel.drawLine(100, 20, 400, 400, new Color(0, 0, 0));
		
		Color red = new Color(255,0,0);
		panel.drawLine(0, 10, 50, 10, red);
		
		panel.drawLine(10, 0, 10, 40, red);

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public Dimension getPreferredSize() {
		return new Dimension(canvas.getWidth(), canvas.getHeight());
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(canvas, null, null);
	}

	public void fillCanvas(Color c) {
		int color = c.getRGB();
		for (int x = 0; x < canvas.getWidth(); x++) {
			for (int y = 0; y < canvas.getHeight(); y++) {
				canvas.setRGB(x, y, color);
			}
		}
		repaint();
	}

	/*
	 * Method drawLine: Uses the simple math line equotation to draw a line
	 * 
	 * @param double x0: x coordinate start point
	 * 
	 * @param double y0: y coordinate start point
	 * 
	 * @param double x1: x coordinate end point
	 * 
	 * @param double y1: y coordinate start point
	 * 
	 * @param Color c: Color variable for drawing the line
	 */
	public void drawLine(double x0, double y0, double x1, double y1, Color c) {
		// TODO: Implement simple line drawing
		double x = x0;
		double m = (y1 - y0) / (x1 - x0);
		double y =y0;
		
		if(m>1){ //else
			System.out.println(m);
			for(y=y0; y<=y1;y++){
				canvas.setRGB((int)x,(int)y,c.getRGB());
			}
		}
		
		
		if(m==0){
			System.out.println(m);
		for (x = x0; x <= x1; x++) {
			
			canvas.setRGB((int) x, (int) y, c.getRGB());
			y += m;
		}
		}

		repaint();
	}

	/*
	 * Method drawMidpointLine: Uses the midpoint / bresenham algorithm to draw
	 * a line
	 * 
	 * @param double x0: x coordinate start point
	 * 
	 * @param double y0: y coordinate start point
	 * 
	 * @param double x1: x coordinate end point
	 * 
	 * @param double y1: y coordinate start point
	 * 
	 * @param Color c: Color variable for drawing the line
	 */
	public void drawMidpointLine(double x0, double y0, double x1, double y1,
			Color c) {
		double dx = x1 - x0;
		double dy = y1 - y0;
		double d = 2 * dy - dx;
		double incrE = 2 * dy;
		double incrNE = 2 * (dy - dx);
		double x = x0;
		double y = y0;
		canvas.setRGB((int) x, (int) y, c.getRGB());
		while (x < x1) {
			if (d <= 0) {
				d += incrE;
				x++;
			} else {
				d += incrNE;
				y++;
			}
			canvas.setRGB((int) x, (int) y, c.getRGB());
		}

		repaint();
	}
	/*
	 * Method drawAALine Uses a line drawing algorithm with integrated
	 * anti-aliasing (pre or post)
	 * 
	 * @param double x0: x coordinate start point
	 * 
	 * @param double y0: y coordinate start point
	 * 
	 * @param double x1: x coordinate end point
	 * 
	 * @param double y1: y coordinate start point
	 * 
	 * @param Color c: Color variable for drawing the line
	 */
	public void drawAALine(double x0, double y0, double x1, double y1, Color c) {
		// TODO: Implement Antiliasline drawing

		repaint();
	}

	/*
	 * Method drawCirclePoints: mirrors the quarter circle to the 3 other
	 * quarters pixel per pixel
	 * 
	 * @param double x: x coordinate of the pixel to mirror
	 * 
	 * @param double y: y coordinate of the pixel to mirror
	 * 
	 * @param Color c: Color variable for mirror pixel
	 */
	public void drawCirclePoints(int x, int y, Color c) {
		// TODO: Implement circle point drawing, mirrors one pixel to the other
		// three circle quarters

		repaint();
	}

	/*
	 * Method drawSimpleCircle: uses the simple circle equotation to draw a
	 * quarter circle which has to be mirrored pixel per pixel by the method
	 * drawCirclePoints
	 * 
	 * @param double x: x coordinate of circle center
	 * 
	 * @param double y: y coordinate of circle center
	 * 
	 * @param double R: radius of circle
	 * 
	 * @param Color c: Color variable for circle line
	 */
	public void drawSimpleCircle(double x0, double y0, double R, Color c) {
		// TODO: Implement simple circle drawing

		repaint();
	}

	/*
	 * Method drawMidpointCircle: uses the midpointcircle algorithm to draw a
	 * quarter circle which has to be mirrored pixel per pixel by the method
	 * drawCirclePoints
	 * 
	 * @param double x: x coordinate of circle center
	 * 
	 * @param double y: y coordinate of circle center
	 * 
	 * @param double R: radius of circle
	 * 
	 * @param Color c: Color variable for circle line
	 */
	public void drawMidpointCircle(double x, double y, double R, Color c) {
		// TODO: Implement midpoint circle drawing
		repaint();
	}

	/*
	 * Method drawFloodFill (recursive!): uses the regionfill algorithm to fill
	 * a polygon given by the method drawRect
	 * 
	 * @param double x: x coordinate of a start point in the rect
	 * 
	 * @param double y: y coordinate of a start point in the rect
	 * 
	 * @param Color floodc: Color variable of the border
	 * 
	 * @param Color c: Color variable of the fill
	 */
	public void drawFloodFill(double x, double y, Color floodc, Color c) {
		// TODO: Implement the recursive floodfill algorithm

		repaint();
	}

	/*
	 * Method drawScanlineFill uses the scanlinefill algorithm to fill a polygon
	 * given by the method drawRect
	 * 
	 * @param double []poly: array with vertices
	 * 
	 * @param double nV: incrementor for current vertice
	 * 
	 * @param Color c: Color variable of the fill
	 */
	public void drawScanlineFill(Point2D[] poly, double nV, Color c) {
		// TODO: Implement scanlinefill algorithm, calculate intersections on
		// the fly

		// Step1: jede scanline (je nach groesse der objekte auf dem
		// bildschirm, lohnt es sich evtl. schon hier nach
		// ymin und ymax einzuschraenken)

		// Step2: jede kante durchlaufen, um Schnittpunkten zu finden

		// Step3: wenn letzter knoten, dann Gerade letzter - erster knoten

		// Step4: y min und max Werte berechnen fuer diese Gerade

		// Step5: ymin ymax Regel auch bei vertikaler Geraden

		// Step6: dx und dy berechnen

		// Step7: vertikale Gerade mit steigung unendlich

		// Step8: horizontale oder andere gerade mit steigung kleiner unendlich

		// Step9: wenn dy == 0 dann ist m = 0 --> Waagrechte, Horizontale

		// Step10: achsenabschnitt berechnen egal mit welchem pounktpaar der
		// geraden

		// Step11: schnittpunkt mit scanline berechnen

		// Step12: end kanten loop --> alle relevanten Schnittpunkte im scanline
		// array drin und counter gesetzt

		// Step13: sortieren mit quicksort nach x Werten

		// Step14: Zeichnen mit eigener Methode drawLine, diese bekommt int
		// Werte und schneidet float also automatisch auf kleineren Wert ab

		// Step15: schnittpunkte-counter zuruecksetzen

		// Step16: end scanline loop

		repaint();
	}

	public void drawRect(Color c, int x1, int y1, int width, int height) {
		int color = c.getRGB();
		// Implement rectangle drawing
		for (int x = x1; x < x1 + width; x++) {
			for (int y = y1; y < y1 + height; y++) {
				canvas.setRGB(x, y, color);
			}
		}
		repaint();
	}

	public void drawPolygon(Point2D[] poly, Color c) {
		int color = c.getRGB();
		// TODO: Implement a polygon drawing algorithm

		repaint();
	}
}