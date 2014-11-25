package uebung_03;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Rastern_jasminLienhard extends JPanel {

	private BufferedImage canvas;

	/* ---------- für kantentabellen und filter ---------- */
	/* initialize test polygon A */
	static Point2D[] polyA = new Point2D[5];
	/* initialize test polygon B */
	static Point2D[] polyB = new Point2D[5];
	// initialize test polygon C */
	static Point2D[] polyC = new Point2D[12];

	static int width = 640;
	static int height = 480;

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

	public Rastern_jasminLienhard(int width, int height) {
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

		Color bgColor = new Color(255, 255, 166);
		JFrame frame = new JFrame("Direct draw demo");

		Color red = new Color(255, 0, 0);
		Color blue = new Color(0, 0, 255);
		Color pink = new Color(255, 0, 255);
		Color yellow = new Color(255, 255, 0);

		Rastern_jasminLienhard panel = new Rastern_jasminLienhard(width, height);

		/*
		 * drawLine
		 */
		panel.drawLine(10, 10, 100, 20, pink);
		panel.drawLine(100, 20, 400, 400, pink);
		panel.drawLine(0, 100, 200, 400, pink);

		/*
		 * drawMidpointLine
		 */
		panel.drawMidpointLine(0, 100, 200, 400, blue);
		panel.drawMidpointLine(20, 20, 100, 30, blue);
		panel.drawMidpointLine(0, 0, 110, 410, blue);

		/*
		 * drawAALine
		 */
		panel.drawAALine(40, 30, 50, 80, red);
		panel.drawAALine(50, 80, 40, 30, red);
		panel.drawAALine(0, 0, 10, 80, red);

		/*
		 * CirclePoints
		 */
		panel.drawCirclePoints(30, 40, 135, 110, yellow);

		panel.drawMidpointCircle(200, 200, 30, yellow);

		panel.drawFloodFill(210, 300, yellow, pink);
		/*
		 * draw ScanlineFill
		 */
		panel.drawScanlineFill(polyC, 10, yellow);
		panel.drawScanlineFill(polyB, 5, red);
		panel.drawScanlineFill(polyA, 5, blue);

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
		// TODO: 1. Fixen der drawLine Methode und drawMidpointLine Methode für
		// m==0, m==unendlich, m>1&&m<-1
		// sowie m zwischen 1 und -1 (der im Unterricht behandelte Normalfall).

		double m = 0;

		if (x1 != x0) {
			m = (y1 - y0) / (x1 - x0);
		}

		if (x0 > x1) {
			double tempx = x0;
			x0 = x1;
			x1 = tempx;
			double tempy = y0;
			y0 = y1;
			y1 = tempy;
		}
		double y = y0;
		System.out.println("m: " + m);

		for (double x = x0; x <= x1; x++) {

			if ((int) x <= width && (int) Math.round(y) <= height) {
				if (m == 0) {
					// System.out.println("null");
					canvas.setRGB((int) x, (int) Math.round(y), c.getRGB());
				} else if (m > 1) {
					// System.out.println("m > 1");
					for (int i = 0; i < m; i++) {
						canvas.setRGB((int) x, (int) Math.round(y + i),
								c.getRGB());
					}
					y += m;
				} else if (m < -1) {
					// System.out.println("m < -1");
					for (double i = m; i < 0; i += 1) {
						canvas.setRGB((int) x, (int) Math.round(y + i),
								c.getRGB());
					}
					y += m;
				} else {
					// System.out.println("normal");
					canvas.setRGB((int) x, (int) Math.round(y), c.getRGB());
					y += m;
				}
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
		int pix = c.getRGB();
		int dy = (int) (y1 - y0);
		int dx = (int) (x1 - x0);
		int stepx, stepy;
		if (dy < 0) {
			dy = -dy;
			stepy = -1;
		} else {
			stepy = 1;
		}
		if (dx < 0) {
			dx = -dx;
			stepx = -1;
		} else {
			stepx = 1;
		}
		dy <<= 1; // dy is now 2*dy
		dx <<= 1; // dx is now 2*dx

		canvas.setRGB((int) x0, (int) y0, pix);

		if (dx > dy) {
			int fraction = dy - (dx >> 1); // same as 2*dy - dx
			while (x0 != x1) {
				if (fraction >= 0) {
					y0 += stepy;
					fraction -= dx; // same as fraction -= 2*dx
				}
				x0 += stepx;
				fraction += dy; // same as fraction -= 2*dy
				canvas.setRGB((int) x0, (int) y0, pix);
			}
		} else {
			int fraction = dx - (dy >> 1);
			while (y0 != y1) {
				if (fraction >= 0) {
					x0 += stepx;
					fraction -= dy;
				}
				y0 += stepy;
				fraction += dx;
				canvas.setRGB((int) x0, (int) y0, pix);
			}
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
	public void drawAALine(float x0, float y0, float x1, float y1, Color c) {
		/* http://en.wikipedia.org/wiki/Xiaolin_Wu's_line_algorithm */

		float grad, xd, yd, xgap, ygap, xend, yend, xf, yf, brigheness1, brigheness2;
		int x, y, ix1, ix2, iy1, iy2;

		// width and height of line
		xd = x1 - x0;
		yd = y1 - y0;

		float[] hsbVals = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(),
				null);

		if (Math.abs(xd) == 0 || Math.abs(yd) == 0) {
			// capture straight lines, they dont need antialising
			drawLine(x0, y0, x1, y1, c);
			return;
		}
		if (Math.abs(xd) > Math.abs(yd)) {
			// line is horizontal
			if (x0 > x1) {
				float temp = x0;
				x0 = x1;
				x1 = temp;
				temp = y0;
				y0 = y1;
				y1 = temp;
				xd = x1 - x0;
				yd = y1 - y0;
			}
			grad = yd / xd;

			// End point 1
			xend = trunc(x0 + .5f);
			yend = y0 + grad * (xend - x0);
			xgap = invfrac(x0 + .5f);
			ix1 = (int) xend;
			iy1 = (int) yend;

			// Currently not using brightness, because lightning can be done by
			// Color
			brigheness1 = invfrac(yend) * xgap;
			brigheness2 = frac(yend) * xgap;

			Color ca = new Color(c.getRed(), c.getGreen(), c.getBlue(),
					Math.round(brigheness1 * 100));
			Color cb = new Color(c.getRed(), c.getGreen(), c.getBlue(),
					Math.round(brigheness2 * 100));
			// Color ca = Color.getHSBColor(hsbVals[0], hsbVals[1], brigheness1
			// * (1f - hsbVals[2]));
			// Color cb = Color.getHSBColor(hsbVals[0], hsbVals[1], brigheness2
			// * hsbVals[2]);

			canvas.setRGB(ix1, iy1, cb.getRGB());
			canvas.setRGB(ix1 + 1, iy1, ca.getRGB());

			yf = yend + grad;

			// End point 2
			xend = trunc(x1 + .5f);
			yend = y1 + grad * (xend - x1);
			xgap = invfrac(x1 - .5f);
			ix2 = (int) xend;
			iy2 = (int) yend;

			brigheness1 = invfrac(yend) * xgap;
			brigheness2 = frac(yend) * xgap;

			ca = new Color(c.getRed(), c.getGreen(), c.getBlue(),
					Math.round(brigheness1 * 100));
			cb = new Color(c.getRed(), c.getGreen(), c.getBlue(),
					Math.round(brigheness2 * 100));

			canvas.setRGB(ix2, iy2, cb.getRGB());
			canvas.setRGB(ix2 + 1, iy2, ca.getRGB());

			for (x = ix1 + 1; x <= ix2 - 1; ++x) {
				brigheness1 = invfrac(yf);
				brigheness2 = frac(yf);
				ca = new Color(c.getRed(), c.getGreen(), c.getBlue(),
						Math.round(brigheness1 * 100));
				cb = new Color(c.getRed(), c.getGreen(), c.getBlue(),
						Math.round(brigheness2 * 100));
				// ca = Color.getHSBColor(hsbVals[0], hsbVals[1], 1f -
				// brigheness2);
				// cb = Color.getHSBColor(hsbVals[0], hsbVals[1], 1f -
				// brigheness1);
				canvas.setRGB(x, (int) yf, cb.getRGB());
				canvas.setRGB(x + 1, (int) yf, ca.getRGB());
				yf = yf + grad;
			}

		} else {
			// Line has to be verticallx aalised
			if (y0 > y1) {
				float temp = y0;
				y0 = y1;
				y1 = temp;
				temp = x0;
				x0 = x1;
				x1 = temp;
				yd = y1 - y0;
				xd = x1 - x0;
			}
			grad = xd / yd;

			// End point 1
			yend = trunc(y0 + .5f);
			xend = x0 + grad * (yend - y0);
			ygap = invfrac(y0 + .5f);
			iy1 = (int) yend;
			ix1 = (int) xend;

			// Currentlx not using brightness, because lightning can be done bx
			// Color
			brigheness1 = invfrac(xend) * ygap;
			brigheness2 = frac(xend) * ygap;

			Color ca = new Color(c.getRed(), c.getGreen(), c.getBlue(),
					Math.round(brigheness1 * 100));
			Color cb = new Color(c.getRed(), c.getGreen(), c.getBlue(),
					Math.round(brigheness2 * 100));

			canvas.setRGB(ix1, iy1, cb.getRGB());
			canvas.setRGB(ix1, iy1 + 1, ca.getRGB());

			xf = xend + grad;

			// End point 2
			yend = trunc(y1 + .5f);
			xend = x1 + grad * (yend - y1);
			ygap = invfrac(y1 - .5f);
			iy2 = (int) yend;
			ix2 = (int) xend;

			brigheness1 = invfrac(xend) * ygap;
			brigheness2 = frac(xend) * ygap;

			ca = new Color(c.getRed(), c.getGreen(), c.getBlue(),
					Math.round(brigheness1 * 100));
			cb = new Color(c.getRed(), c.getGreen(), c.getBlue(),
					Math.round(brigheness2 * 100));

			canvas.setRGB(ix2, iy2 + 1, cb.getRGB());
			canvas.setRGB(ix2, iy2 + 1, ca.getRGB());

			for (y = iy1 + 1; y <= iy2 - 1; y++) {
				brigheness1 = invfrac(xf);
				brigheness2 = frac(xf);
				ca = new Color(c.getRed(), c.getGreen(), c.getBlue(),
						Math.round(brigheness1 * 100));
				cb = new Color(c.getRed(), c.getGreen(), c.getBlue(),
						Math.round(brigheness2 * 100));
				// ca = Color.getHSBColor(hsbVals[0], hsbVals[1], 1 -
				// (brigheness1 * hsbVals[2]));
				// cb = Color.getHSBColor(hsbVals[0], hsbVals[1], 1 -
				// (brigheness2 * hsbVals[2]));
				canvas.setRGB((int) xf, y, cb.getRGB());
				canvas.setRGB((int) xf, y + 1, ca.getRGB());
				xf = xf + grad;
			}

		}
		repaint();
	}

	private int trunc(float x) {
		long l = (long) x;
		return (int) x;
	}

	private float frac(float x) {
		long iPart = (long) x;
		return x - iPart;
	}

	private float invfrac(float x) {
		long iPart = (long) x;
		return 1 - (x - iPart);
	}

	/*
	 * Method drawCirclePoints: mirrors the 1/8 circle to the 7 other quarters
	 * pixel per pixel zeichne nur 1/8 kreis und spiegle ihn 7mal neu. bis kreis
	 * vollendet. minus/plus bei x und y nicht vergessen
	 * 
	 * @param double x: x coordinate of the pixel to mirror
	 * 
	 * @param double y: y coordinate of the pixel to mirror
	 * 
	 * @param Color c: Color variable for mirror pixel
	 */
	public void drawCirclePoints(int cx, int cy, int x, int y, Color c) {
		int color = c.getRGB();
		{
			if (x == 0) {
				canvas.setRGB(cx, cy + y, color);
				canvas.setRGB(cx, cy - y, color);
				canvas.setRGB(cx + y, cy, color);
				canvas.setRGB(cx - y, cy, color);
			} else if (x == y) {
				canvas.setRGB(cx + x, cy + y, color);
				canvas.setRGB(cx - x, cy + y, color);
				canvas.setRGB(cx + x, cy - y, color);
				canvas.setRGB(cx - x, cy - y, color);
			} else if (x < y) {
				canvas.setRGB(cx + x, cy + y, color);
				canvas.setRGB(cx - x, cy + y, color);
				canvas.setRGB(cx + x, cy - y, color);
				canvas.setRGB(cx - x, cy - y, color);
				canvas.setRGB(cx + y, cy + x, color);
				canvas.setRGB(cx - y, cy + x, color);
				canvas.setRGB(cx + y, cy - x, color);
				canvas.setRGB(cx - y, cy - x, color);
			}
		}
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
	public void drawSimpleCircle(double x0, double y0, double radius, Color c) {
		double x, y, r2;

		r2 = radius * radius;
		for (x = -radius; x <= radius; x++) {
			y = (int) (Math.sqrt(r2 - x * x) + 0.5);

			drawCirclePoints((int) x0, (int) y0, (int) x, (int) y, c);
		}

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
	public void drawMidpointCircle(double cx, double cy, double radius, Color c) {
		double x = 0, y = radius;
		double p = (5 - radius * 4) / 4;
		drawCirclePoints(Math.round(Math.round(cx)),
				Math.round(Math.round(cy)), Math.round(Math.round(x)),
				Math.round(Math.round(y)), c);
		while (y > x) {
			x++;
			if (p < 0) {
				p += 2 * x + 1;
			} else {
				y--;
				p += 2 * (x - y - 1);
			}
			drawCirclePoints(Math.round(Math.round(cx)),
					Math.round(Math.round(cy)), Math.round(Math.round(x)),
					Math.round(Math.round(y)), c);
		}
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
		if (canvas.getRGB((int) x, (int) y) == Color.white.getRGB()) {
			canvas.setRGB((int) x, (int) y, c.getRGB());
			drawFloodFill(x, y - 1, floodc, c);
			drawFloodFill(x, y + 1, floodc, c);
			drawFloodFill(x - 1, y, floodc, c);
			drawFloodFill(x + 1, y, floodc, c);
		}
		repaint();
	}
	/*
	 * Method drawScanlineFill uses the scanlinefill algorithm to fill a polygon
	 * given evt nach ymin und ymax einschränken. man durchläuft je man fängt
	 * unten an und geht hoch, wenn wir auf ein pixel treffen haben wir einen
	 * kantenschnittpunkt siehe folie: beispiel wir haben kantentabellen
	 * geradengleichung machen und schnittpunkte mit scanlinie berechnen. by the
	 * method drawRect
	 * 
	 * @param double []poly: array with vertices
	 * 
	 * @param double nV: incrementor for current vertice
	 * 
	 * @param Color c: Color variable of the fill
	 */
	public void drawScanlineFill(Point2D[] poly, double nV, Color c) {

		/*
		 * Method drawScanlineFill uses the scanlinefill algorithm to fill a
		 * polygon given by the method drawRect
		 * 
		 * @param double []poly: array with vertices
		 * 
		 * @param double nV: incrementor for current vertice
		 * 
		 * @param Color c: Color variable of the fill
		 */

		double[] scanline = new double[100];
		int schnittpunkte = 0;
		int x, y;
		int dx, dy, miny, maxy, maxx, minx;
		double m, xspunkt, cxy, sp1, sp2;
		Point2D pointa, pointb;

		for (y = 0; y < 480; y++) // jede scanline (je nach grösse der objekte
									// auf dem
									// bildschirm, lohnt es sich evtl. schon
									// hier nach
									// ymin und ymax einzuschränken)
		{
			// AUFBAU DER KANTENTABELLE (=scanline array)
			for (int i = 0; i < nV; i++) // jede kante durchlaufen, um
											// Schnittpunkten zu finden
			{
				// wenn letzter knoten, dann Gerade letzter - erster knoten
				if (i == (nV - 1)) {
					pointa = poly[i];
					pointb = poly[0];
				} else {
					pointa = poly[i];
					pointb = poly[i + 1];
				}

				// y min und max Werte berechnen für diese Gerade
				if (pointa.getY() <= pointb.getY()) {

					miny = (int) pointa.getY();

					maxy = (int) pointb.getY();
				} else {

					miny = (int) pointb.getY();

					maxy = (int) pointa.getY();
				}

				if ((y <= maxy) && (y > miny)) // ymin ymax Regel auch bei
												// vertikaler Geraden
				{
					// dx und dy berechnen
					dx = (int) (pointb.getX() - pointa.getX());
					dy = (int) (pointb.getY() - pointa.getY());

					// vertikale gerade mit steigung unendlich
					if (dx == 0) {
						scanline[schnittpunkte] = pointa.getX();
						schnittpunkte++;
					} else // horizontale oder andere gerade mit steigung
							// kleiner unendlich
					{
						m = (double) dy / (double) dx; // wenn dy == 0 dann ist
														// m = 0 //-->
														// Waagrechte,
														// Horizontale
						// achsenabschnitt berechnen egal mit welchem punktpaar
						// der geraden
						cxy = pointa.getY() - (m * (pointa.getX()));

						// schnittpunkt mit scanline berechnen
						xspunkt = ((double) y - cxy) / m;

						scanline[schnittpunkte] = xspunkt;
						schnittpunkte++;
					}
				}
			}// end kanten loop --> alle relevanten Schnittpunkte im scanline
				// array drin und counter gesetzt

			// ACHTUNG: Array scanline enthält 0.0 Werte für nicht
			// initialisierte Punkte! --> entfernen!
			double[] scanlineCleaned = Arrays.copyOfRange(scanline, 0,
					schnittpunkte);

			// sortieren der Kantentablle mit quicksort nach x Werten

			Arrays.sort(scanlineCleaned);

			// Zeichnen mit eigener Methode drawLine, diese bekommt
			// normalerweise int Werte und
			// schneidet float also automatisch auf kleineren Wert ab

			for (int i = 0; i < schnittpunkte; i = i + 2) {

				sp1 = scanlineCleaned[i];
				sp2 = scanlineCleaned[i + 1];
				drawLine(sp1, y, sp2, y, c);
			}

			schnittpunkte = 0; // schnittpunkte-counter zur¸cksetzen
		}// end scanline loop

		repaint();
	}

	// Step1: jede scanline (je nach groesse der objekte auf dem
	// bildschirm, lohnt es sich evtl. schon hier nach
	// ymin und ymax einzuschraenken)

	// Step2: jede kante durchlaufen, um Schnittpunkten zu finden
	// doppelte for schleife: einmal über y

	// Step3: wenn letzter knoten, dann Gerade letzter - erster knoten

	// Step4: y min und max Werte berechnen fuer diese Gerade

	// Step5: ymin ymax Regel auch bei vertikaler Geraden

	// Step6: dx und dy berechnen

	// Step7: vertikale Gerade mit steigung unendlich

	// Step8: horizontale oder andere gerade mit steigung kleiner unendlich

	// Step9: wenn dy == 0 dann ist m = 0 --> Waagrechte, Horizontale

	// Step10: achsenabschnitt berechnen egal mit welchem pounktpaar der geraden

	// Step11: schnittpunkt mit scanline berechnen

	// Step12: end kanten loop --> alle relevanten Schnittpunkte im scanline
	// array drin und counter gesetzt

	// Step13: sortieren mit quicksort nach x Werten

	// Step14: Zeichnen mit eigener Methode drawLine, diese bekommt int Werte
	// und schneidet float also automatisch auf kleineren Wert ab

	// Step15: schnittpunkte-counter zuruecksetzen

	// Step16: end scanline loop

}