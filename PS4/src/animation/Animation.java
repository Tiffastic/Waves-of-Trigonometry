package animation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;

public class Animation
{
	/**
	 * The animation is of the trigonometric functions - sine, cosine, and
	 * tangent along with a ticking clock
	 * 
	 * This is the method that you need to rewrite to create a custom animation.
	 * This method is called repeatedly as the animation proceeds. It needs to
	 * draw to g how the animation should look after t milliseconds have passed.
	 * 
	 * @param g
	 *            Graphics object on which to draw
	 * @param t
	 *            Number of milliseconds that have passed since animation
	 *            started
	 */

	// fields

	private static int increment = 1;

	private static int second = 1;

	private static boolean shrink;

	private static int shrinkGrow = 1;

	private static Stroke thick = new BasicStroke(3);

	private static Color uRed = new Color(0xAD004B);

	private static Color letterColor = new Color(0xF71B34);

	private static Color clockColor = new Color(0xA70F48);

	private static Font letterFont = new Font("Times Roman", Font.BOLD, 16);

	private static Font clockFont = new Font("Times Roman", Font.BOLD, 30);

	// public methods

	public static void paintFrame (Graphics g, int t,  int width, int height)
	{
		// every tick is a frame
		if (t % 150 == 0)  // slows down the animation
		{
			increment++;

		}


		int yBase = (int) (height * .62);
		int size = 12;

		double amplitude = ( 1.5 * increment );
		// the horizontal stretch varies with time creates the
		// cool center tube and tangent patterns
		double horizontalStretch = increment;
		int disparity = 1;

		g.setColor(Color.red);

		if (t % 10 == 0)
		{
			g.setColor(changeColor( ));

		}

		// center tube varies amplitude and horizontal stretch with time
		createCenterMagic(g, size, yBase, .7 * t, amplitude * 1.1, t * .007);

		// waves varies amplitude with time

		wrapWithWaves(g, size, yBase, t, amplitude, disparity);

		// tangent pattern varies amplitude and horizontal stretch with time
		burstingWithTangent(g, size, yBase - width / 8, t * .1,
				amplitude, horizontalStretch * .55);

		flyWithTime(g, t);

	}

	// private methods
	private static Color changeColor ( )
	{
		int red = (int) ( 256 * Math.random( ) );
		int green = (int) ( 256 * Math.random( ) );
		int blue = (int) ( 256 * Math.random( ) );
		
		return new Color(red, green, blue);
	}

	private static void flyWithTime (Graphics g, int t)
	{

		int clockHand = 100;
		int startX = 1200;
		int startY = 200;

		if (t % 100 == 0)
		{
			second++;
		}

		int dx = (int) ( cos(second) * clockHand );
		int dy = (int) ( sin(second) * clockHand );

		// draw second hand
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(uRed);  // red
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(thick);
		g2.drawLine(startX, startY, startX + dx, startY + dy);

		// draw the center letter on the clock
		g2.setColor(letterColor);
		g2.setFont(letterFont);
		g2.drawString("U", startX - 2, startY);

		// draw shrinking growing ball
		g.setColor(changeColor( ));
		g.fillOval(startX + dx - ( shrinkGrow / 2 ), startY + dy
				- ( shrinkGrow / 2 ), shrinkGrow, shrinkGrow);

		if (shrinkGrow % 25 == 0)
		{
			shrink = ( shrink ) ? false : true;
		}
		if (shrink)
		{
			shrinkGrow--;
			if (shrinkGrow < 0)
			{
				shrinkGrow = 0;
			}
		}
		else
		{
			shrinkGrow++;
		}

		// draw the clock hours
		g.setFont(clockFont);
		g.setColor(clockColor);   // dark purple
		g.drawString("12", startX, startY - clockHand);
		g.drawString("3", startX + clockHand, startY);
		g.drawString("6", startX, startY + clockHand);
		g.drawString("9", startX - clockHand, startY);
	}

	private static void burstingWithTangent (Graphics g, int size, int yBase,
			double time, double amplitude, double horizontalStretch)
	{
		for (int i = 0; i < time; i++)
		{
			int x = i;
			int y = calculateTangentHeight(yBase, i, amplitude,
					horizontalStretch, time);
			g.fillOval(y + i, x, size, size);
		}
	}

	private static void createCenterMagic (Graphics g, int size, int yBase,
			double time, double amplitude, double horizontalStretch)
	{
		for (int i = 0; i < time; i++)
		{
			// draw center tube
			// x and y coordinates
			int x = (int) ( i * 1.3 );  // slow movement down
			int y = calculateCosineHeight(yBase, i, amplitude,
					horizontalStretch, time);
			if (time % 2 == 0)
			{
				g.setColor(changeColor( ));
				// g.setColor(new Color(0x00AAFF)); // ocean blue
			}

			g.fillOval(x, y, size, size);

		}
	}

	private static void wrapWithWaves (Graphics g, int size, int yBase,
			int time, double amplitude, int disparity)
	{

		for (int i = 0; i < time; i++)
		{

			// x and y coordinates
			int x = i / 5;  // makes wave slower
			double horizontalStretch = .2;
			double horizontalShift = .7 * time;
			int y = calculateSineHeight(yBase, x, amplitude * 3,
					horizontalStretch, horizontalShift);

			// draw moving cosine wave - ribbon like
			g.fillOval(x * disparity, y, size, size);

			if (i % 90 == 0)
			{
				x = i / 10;
				horizontalStretch = .7;
				horizontalShift = time;
				y = calculateSineHeight(50 + yBase, x, amplitude * 2,
						horizontalStretch, horizontalShift);

				// draw small wave below center tube - height of oval varies
				// with time, ovals drawn
				// every 90 degrees
				g.fillOval(x, y, size, size * i / time);

				// g.fillOval(x, y, size+t*i, size*i/t); // makes another cool
				// design
			}

			if (i % 90 == 0)
			{

				x = i / 5;
				horizontalStretch = .5;
				horizontalShift = time;
				y = calculateSineHeight(yBase, x, amplitude * 3,
						horizontalStretch, horizontalShift);

				// draw sine wave with ovals spread out - ovals drawn every 90
				// degrees
				g.fillOval(x * disparity, y, size, size);
			}
		}

	}

	private static int calculateSineHeight (int yBase, double time,
			double amplitude, double horizontalStretch, double horizontalShift)
	{
		return (int) ( yBase + sin(time * horizontalStretch + horizontalShift)
				* amplitude );
	}

	private static int calculateCosineHeight (int yBase, double time,
			double amplitude, double horizontalStretch, double horizontalShift)
	{
		return (int) ( yBase + cos(time * horizontalStretch + horizontalShift)
				* amplitude );
	}

	private static int calculateTangentHeight (int yBase, double time,
			double amplitude, double horizontalStretch, double horizontalShift)
	{
		return (int) ( yBase + tan(time * horizontalStretch + horizontalShift)
				* amplitude );
	}

	private static double sin (double x)
	{
		return Math.sin(Math.toRadians(x));
	}

	private static double cos (double x)
	{
		return Math.cos(Math.toRadians(x));
	}

	private static double tan (double x)
	{
		return Math.tan(Math.toRadians(x));
	}

}
