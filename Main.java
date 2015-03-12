
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;

public class Main {

	/**
	 * Main code.
	 */
	public static void main(String[] args) {		

		SpriteCanvas canvas = new SpriteCanvas();

		canvas.addSprite(Main.makeSprite());

		JFrame f = new JFrame();
		f.setJMenuBar(Main.makeMenuBar(canvas));
		f.getContentPane().add(canvas);
		f.getContentPane().setLayout(new GridLayout(1, 1));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(800, 600);
		f.setVisible(true);
	}
	
	/* Make necessary sprites in their initial positions */
	private static Sprite makeSprite() {
		Sprite body = new RectangleSprite(34, 60);
		Sprite head = new EllipseSprite(22,15,0,7,50);

		Sprite RupperArm = new EllipseSprite(34, 10,0,5,360);
		Sprite RlowerArm = new EllipseSprite(32, 10,0,5,135);
		Sprite Rhand = new EllipseSprite(10,10,0,5,35);
		Sprite RupperLeg = new EllipseSprite(36,12,0,6,90,36);
		Sprite RlowerLeg = new EllipseSprite(40,12,0,6,90,40);
		Sprite Rfoot = new EllipseSprite(20,10,0,5,35); 

		Sprite LupperArm = new EllipseSprite(34, 10,0,5,360);
		Sprite LlowerArm = new EllipseSprite(32, 10,0,5,135);
		Sprite Lhand = new EllipseSprite(10,10,0,5,35);	
		Sprite LupperLeg = new EllipseSprite(36,12,0,6,90,36);
		Sprite LlowerLeg = new EllipseSprite(40,12,0,6,90,40);
		Sprite Lfoot = new EllipseSprite(20,10,0,5,35);

		/*
		such that initialization values represent:
		(width, height, xAxis of rotation, yAxis, maximum rotation, initial object length)
		*/

		body.transform(AffineTransform.getTranslateInstance(100,100));
		head.transform(AffineTransform.getTranslateInstance(3,-10));

		RupperArm.transform(AffineTransform.getTranslateInstance(34, 5));
		RlowerArm.transform(AffineTransform.getTranslateInstance(34, 0));
		Rhand.transform(AffineTransform.getTranslateInstance(32, 0));
		RupperLeg.transform(AffineTransform.getTranslateInstance(34, 60));
		RlowerLeg.transform(AffineTransform.getTranslateInstance(36, 0));
		Rfoot.transform(AffineTransform.getTranslateInstance(40, 0));
		
		LupperArm.transform(AffineTransform.getTranslateInstance(0, 5));
		LlowerArm.transform(AffineTransform.getTranslateInstance(34, 0));
		Lhand.transform(AffineTransform.getTranslateInstance(32, 0));
		LupperLeg.transform(AffineTransform.getTranslateInstance(12, 60));
		LlowerLeg.transform(AffineTransform.getTranslateInstance(36, 0));
		Lfoot.transform(AffineTransform.getTranslateInstance(40, 2));

		body.addChild(head);
		body.addChild(RupperArm);
		body.addChild(RupperLeg);
		body.addChild(LupperArm);
		body.addChild(LupperLeg);
		RupperArm.addChild(RlowerArm);
		RupperLeg.addChild(RlowerLeg);
		RlowerArm.addChild(Rhand);
		RlowerLeg.addChild(Rfoot);
		LupperArm.addChild(LlowerArm);
		LupperLeg.addChild(LlowerLeg);
		LlowerArm.addChild(Lhand);
		LlowerLeg.addChild(Lfoot);

		RupperLeg.addOtherLeg(LupperLeg);
		RlowerLeg.addOtherLeg(LlowerLeg);
		LupperLeg.addOtherLeg(RupperLeg);
		LlowerLeg.addOtherLeg(RlowerLeg);

		LupperArm.transform(AffineTransform.getRotateInstance(Math.toRadians(180),0,5));
		head.transform(AffineTransform.getRotateInstance(Math.toRadians(-90),7,0));
		RupperLeg.transform(AffineTransform.getRotateInstance(Math.toRadians(90),0,0));
		LupperLeg.transform(AffineTransform.getRotateInstance(Math.toRadians(90),0,0));
		Rfoot.transform(AffineTransform.getRotateInstance(Math.toRadians(-90),0,5));
		Lfoot.transform(AffineTransform.getRotateInstance(Math.toRadians(90),0,5));

		return body;
	}

	/* Menu with recording and playback. */

	
	private static JMenuBar makeMenuBar(final SpriteCanvas canvas) {
		JMenuBar mbar = new JMenuBar();
		
		JMenu script = new JMenu("Scripting");
		JMenu file = new JMenu("File");
		
		final JMenuItem reset = new JMenuItem("Reset (Ctrl-R)");
		final JMenuItem divider = new JMenuItem("----");
		final JMenuItem quit = new JMenuItem("Quit");

		file.add(reset);
		file.add(divider);
		file.add(quit);
		
		final JMenuItem record = new JMenuItem("Start recording");
		final JMenuItem play = new JMenuItem("Start script");

		script.add(record);
		script.add(play);

		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				canvas.resetCanvas();
			}
		});				

		record.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (record.getText().equals("Start recording")) {
					record.setText("Stop recording");
					canvas.startRecording();
				} else if (record.getText().equals("Stop recording")) {
					record.setText("Start recording");
					canvas.stopRecording();
				} else {
					assert false;
				}
			}
		});

		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (play.getText().equals("Start script")) {
					play.setText("Stop script");
					record.setEnabled(false);
					canvas.startDemo();
				} else if (play.getText().equals("Stop script")) {
					play.setText("Start script");
					record.setEnabled(true);
					canvas.stopRecording();
				} else {
					assert false;
				}
			}
		});
		
		mbar.add(file);
		mbar.add(script);
		return mbar;
	}

}
