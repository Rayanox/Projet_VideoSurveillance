import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Fenetre extends JFrame {

	private JPanel panel;
	private JLabel labelImage;

	public Fenetre() {
		
		//JFrame
		this.panel = new JPanel();
		this.setVisible(true);
		this.setMinimumSize(new Dimension(Main.WidthFenetreMin, Main.HeightFenetreMin));
		this.setLocation(300, 300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		//Panel
		this.getContentPane().add(this.panel);

		// JLabel
		this.labelImage = new JLabel();
		this.panel.add(this.labelImage);

	}

	public JPanel getPanel() {
		return this.panel;
	}
	
	public JLabel getLabelImage() {
		return this.labelImage;
	}
	
	public void setLabelImage(JLabel label) {
		this.labelImage = label;
	}
	
	

}
