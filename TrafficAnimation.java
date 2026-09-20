import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Driver class for CS 121 Project 0: Traffic Animation
 *
 * Animates a surfer waiting to cross the street and go to the beach.
 *
 * @author BSU CS 121 Instructors
 * @author Morgan Needham
 */
@SuppressWarnings("serial")
public class TrafficAnimation extends JPanel
{
	// The top of the class is where you declare constants and variables that need
	// to keep their values between calls to paintComponent(). Any other variables
	// should be declared locally, in paintComponent(), where they are used.

	/**
	 * A constant to regulate the frequency of Timer events.
	 * Note: 100ms is 10 frames per second - you should not need
	 * a faster refresh rate than this
	 */
	private final int DELAY = 100; //milliseconds

	/**
	 * The anchor coordinate for drawing / animating. All of your vehicle's
	 * coordinates should be relative to this offset value.
	 */
	private int xOffset = -1;

	/**
	 * The number of pixels added to xOffset each time paintComponent() is called.
	 */
	private int stepSize = 15;

	private int birdOffset = 0;

	/**
	 * Background color for the scene.
	 */
	private final Color BACKGROUND_COLOR = new Color(153, 204, 255);

	/* This method draws on the panel's Graphics context.
	 * This is where the majority of your work will be.
	 *
	 * (non-Javadoc)
	 * @see java.awt.Container#paint(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g)
	{
		// Get the current width and height of the window.
		int width = getWidth(); // panel width
		int height = getHeight(); // panel height

		// Fill the graphics page with the background color.
		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, width, height); //fills entire window

		// Calculate the new xOffset position of the moving object.
		// xOffset  = (xOffset + stepSize) % width;

		// Unit size as basis for all scaled visual elements.
		// The divisor determines the resolution. You could think of
		// it as deciding how small the boxes are on graph paper.
		// The larger the number, the smaller the boxes. Recommend
		// staying in the 10-20 range. Recommend all shape dimensions
		// should be multiples of this unit. If a fraction of a unit
		// is needed, use fractions, not floating points.
		int unit = Math.min(width, height) / 20;

		// *Background shapes behind vehicle.* //
		int oceanY = height / 3;
		int beachY = height / 2;
		int roadY = height * 3 / 5;
		int foregroundY = height * 4 /5;
		
		// *Ocean* //
		g.setColor(new Color(0, 119, 190));
		g.fillRect(0, oceanY, width, beachY - oceanY);

		// *BEACH* //
		g.setColor(new Color(238, 214, 185));
		g.fillRect(0, beachY, width, roadY - beachY);
		
		// *BIRDS* //
		int birdY = unit * 2;
		int birdBob = (int)(Math.sin(birdOffset * 0.15) * unit / 3);

		int bird1X = width + unit * 2 - birdOffset;
		int bird2X = width + unit * 6 - birdOffset;

		int birdWidth = unit;
		int birdHeight = unit;

		g.setColor(new Color(240, 240, 240));

		// Bird 1
		int bird1Y = birdY + birdBob;

		g.drawArc(bird1X, bird1Y, birdWidth, birdHeight, 20, 140);
		g.drawArc(bird1X, bird1Y + 1, birdWidth, birdHeight, 20, 140);

		g.drawArc(bird1X + birdWidth - unit / 4, bird1Y, birdWidth, birdHeight, 20, 140);
		g.drawArc(bird1X + birdWidth - unit / 4, bird1Y + 1, birdWidth, birdHeight, 20, 140);
		
		// Bird 2
		int bird2Y = birdY + unit / 2 + birdBob;

		g.drawArc(bird2X, bird2Y, birdWidth, birdHeight, 20, 140);
		g.drawArc(bird2X, bird2Y + 1, birdWidth, birdHeight, 20, 140);

		g.drawArc(bird2X + birdWidth - unit / 4, bird2Y, birdWidth, birdHeight, 20, 140);
		g.drawArc(bird2X + birdWidth - unit / 4, bird2Y + 1, birdWidth, birdHeight, 20, 140);
		
		birdOffset = birdOffset + 3;

		if (bird2X + birdWidth * 2 < 0)
		{
   			birdOffset = 0;
		}

		// *ROAD* //
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, roadY, width, foregroundY - roadY);

		int roadMiddleY = roadY + (foregroundY - roadY) / 2;

		g.setColor(Color.YELLOW);
		g.drawLine(0, roadMiddleY, width, roadMiddleY);

		// *FOREGROUND* //
		g.setColor(new Color(238, 214, 175));
		g.fillRect(0, foregroundY, width, height - foregroundY);

		// * Vehicle *//
		int carX = xOffset;
		int carY = roadY + unit;
		int carWidth = unit * 6;
		int carHeight = unit * 2;

		g.setColor(new Color(220, 50, 50));
		g.fillRect(carX, carY, carWidth, carHeight);

		int roofWidth = carWidth * 2 / 3;
		int roofHeight = carHeight / 2;
		int roofX = carX + (carWidth - roofWidth) / 2 - unit / 2;
		int roofY = carY - roofHeight;

		g.setColor(new Color(180, 30, 30));
		g.fillRoundRect(roofX, roofY, roofWidth, roofHeight, unit / 3, unit / 3);

		int wheelSize = unit + unit /  2;

		int backWheelX = carX + unit / 2;
		int frontWheelX = carX + carWidth - unit * 2;
		int wheelY = carY + carHeight - unit / 2;

		g.setColor(Color.BLACK);
		g.fillOval(backWheelX, wheelY, wheelSize, wheelSize);
		g.fillOval(frontWheelX, wheelY, wheelSize, wheelSize);

		int windowWidth = unit;
		int windowHeight = unit / 2;

		g.setColor(new Color(173, 216, 230));

		int windowGap = unit / 3;

		int firstWindowX = roofX + unit / 2;
		int secondWindowX = firstWindowX + windowWidth + windowGap;

		g.fillRect(firstWindowX, roofY + unit / 4, windowWidth, windowHeight);
		g.fillRect(secondWindowX, roofY + unit / 4, windowWidth, windowHeight);

		// * CAR MOVEMENTS * //
		xOffset = xOffset + stepSize;

		if (xOffset > width)
		{
    		xOffset = -carWidth;
		}

		// Foreground shapes in front of vehicle
		// *SURFER DUDE* //
		int surferX = width / 5;
		int surferY = foregroundY + unit / 2;

		int headSize = unit + unit / 2;
		int bodyWidth = unit;
		int bodyHeight = unit * 3;

		// HEAD
		int headX = surferX + bodyWidth / 2 - headSize / 2;

		g.setColor(new Color(222, 184, 135));
		g.fillOval(headX, surferY, headSize, headSize);

		// BODY
		g.setColor(new Color(34, 102, 34));
		g.fillRect(surferX, surferY + headSize, bodyWidth, bodyHeight);

		// ARMS
		int shoulderY = surferY + headSize + unit / 2;

		g.setColor(Color.BLACK);
		g.drawLine(surferX, shoulderY, surferX - unit, shoulderY + unit);
		g.drawLine(surferX + bodyWidth, shoulderY, surferX + bodyWidth + unit, shoulderY + unit);

		// LEGS
		int legY = surferY + headSize + bodyHeight;

		g.setColor(Color.BLACK);
		g.drawLine(surferX + bodyWidth / 3, legY, surferX, legY + unit);
		g.drawLine(surferX + bodyWidth * 2 / 3, legY, surferX + bodyWidth, legY + unit);

		// *SURFBOARD* //
		int boardX = surferX + bodyWidth + unit;
		int boardY = surferY + headSize;

		int boardWidth = unit + unit / 2;
		int boardHeight = unit * 4;

		g.setColor(new Color(180, 240, 255));
		g.fillOval(boardX, boardY, boardWidth, boardHeight);

		g.setColor(new Color(0, 102, 204));
		g.drawOval(boardX, boardY, boardWidth, boardHeight);

		// *TEXT* //
		g.setColor(Color.WHITE);
		g.setFont(new Font("SansSerif", Font.BOLD, unit));
		g.setColor(Color.WHITE);
		g.drawString("Surf's Up!", unit, unit);
		
		// Keep all of your code above this line. This makes the drawing smoother.
		Toolkit.getDefaultToolkit().sync();
	}


	//==============================================================
	// You don't need to modify anything beyond this point.
	//==============================================================


	/**
	 * Starting point for this program. Your code will not go in the
	 * main method for this program. It will go in the paintComponent
	 * method above.
	 *
	 * DO NOT MODIFY this method!
	 *
	 * @param args unused
	 */
	public static void main (String[] args)
	{
		// DO NOT MODIFY THIS CODE.
		JFrame frame = new JFrame ("Traffic Animation");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new TrafficAnimation());
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Constructor for the display panel initializes necessary variables.
	 * Only called once, when the program first begins. This method also
	 * sets up a Timer that will call paint() with frequency specified by
	 * the DELAY constant.
	 */
	public TrafficAnimation()
	{
		// Do not initialize larger than 800x600. I won't be able to
		// grade your project if you do.
		int initWidth = 600;
		int initHeight = 400;
		setPreferredSize(new Dimension(initWidth, initHeight));
		this.setDoubleBuffered(true);

		//Start the animation - DO NOT REMOVE
		startAnimation();
	}

	/**
	 * Create an animation thread that runs periodically.
	 * DO NOT MODIFY this method!
	 */
	private void startAnimation()
	{
		ActionListener timerListener = new TimerListener();
		Timer timer = new Timer(DELAY, timerListener);
		timer.start();
	}

	/**
	 * Repaints the graphics panel every time the timer fires.
	 * DO NOT MODIFY this class!
	 */
	private class TimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			repaint();
		}
	}
}