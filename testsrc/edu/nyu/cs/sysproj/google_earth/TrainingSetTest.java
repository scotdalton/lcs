/**
 * 
 */
package edu.nyu.cs.sysproj.google_earth;

import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.google.common.collect.Lists;


/**
 * @author Scot Dalton
 *
 */
public class TrainingSetTest {
	public static void main(String[] args) {
		List<Image> arableImages = TrainingSet.getArableTrainingImages();
		final List<Point> arablePoints = Lists.newArrayList();
		for(Image arableImage:arableImages) {
			Float x = 
				ArabilityFeature.DCT_MEAN.instantiate(arableImage).getValue();
			Float y = 
				ArabilityFeature.DCT_DISPERSION.instantiate(arableImage).getValue();
//			System.out.println("(" + x + "," + y + ")");
			Point arablePoint = new Point();
			arablePoint.setLocation(x, y);
			arablePoints.add(arablePoint);
		}
		List<Image> nonArableImages = TrainingSet.getNonArableTrainingImages();
		final List<Point> nonArablePoints = Lists.newArrayList();
		for(Image nonArableImage:nonArableImages) {
			Float x = 
				ArabilityFeature.DCT_MEAN.instantiate(nonArableImage).getValue();
			Float y = 
				ArabilityFeature.DCT_DISPERSION.instantiate(nonArableImage).getValue();
			Point nonArablePoint = new Point();
			nonArablePoint.setLocation(x, y);
			nonArablePoints.add(nonArablePoint);
		}
		JPanel panel = new JPanel() { 
			private static final long serialVersionUID = 1L;
			public void paintComponent(Graphics g) {
				for(Point arablePoint: arablePoints)
					g.drawString("X", arablePoint.x * 10, arablePoint.y * 10);
				for(Point nonArablePoint: nonArablePoints)
					g.drawString("O", nonArablePoint.x * 10, nonArablePoint.y * 10);
			}
        };
        JFrame jframe = new JFrame("Scatterplot");
        jframe.setContentPane(panel);
        //last two numbers below change the initial size of the graph.
        jframe.setBounds(0, 0, 2000, 2000);
        jframe.setVisible(true);
	}
}