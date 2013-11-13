import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Fenetre extends JFrame {

	private JPanel panel;
	private ArrayList<JPanel> listPanels;
	private GridLayout layoutDuPanel;

	public Fenetre() {
		
		//JFrame		
		this.setVisible(true);
		this.setMinimumSize(new Dimension(Main.WidthFenetreMin, Main.HeightFenetreMin));
		this.setLocation(Main.LocationFenetreX, Main.LocationFenetreY);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		//Panel
		this.panel = new JPanel();
		this.getContentPane().add(this.panel);

		//Layout
		//this.panel.setLayout(new GridLayout(1, 2));
		this.panel.setLayout(this.layoutDuPanel = new GridLayout(1, 2));
	}

	public JPanel getPanel() {
		return this.panel;
	}
	
	public void modifierLayout(int nbComposants) {
		
		
		if(nbComposants % 2 == 0) {
			/*GridLayout g = new GridLayout(2, nbComposants /2 +1);
			this.panel.setLayout(g);*/
			this.layoutDuPanel.setRows(nbComposants /2 +1);
		}
		//GridLayout g = new GridLayout(2, nbComposants % 2 == 0 ? nbComposants /2 +1 : (nbComposants+1)/2);
		
		
	}
	
	

}
