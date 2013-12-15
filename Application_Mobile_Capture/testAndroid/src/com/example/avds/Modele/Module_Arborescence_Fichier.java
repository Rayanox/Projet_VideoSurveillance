package com.example.avds.Modele;


import java.io.File;
import org.jdom2.Document;
import org.jdom2.Element;


//Cette classe n'est pas du tout indispensable, je m'en suis juste servi pour connaitre
// l'arborescence de la mémoire du téléphone. J'ai laissé cette classe de l'éventualité d'une 
// utilisation mais elle va très probablement sauter en version finale.

public class Module_Arborescence_Fichier {

	
	public Module_Arborescence_Fichier() {
		
	}
	
	
	public String LancerTravailString(String path) {
		return getArborescenceFromPathString(path, 0);
	}
	
	public String getArborescenceFromPathString(String path, int tabulation) {
		File f = new File(path);
		String texte = f.getName(); // nom du fichier;
			
		
		if(f.isDirectory()) {
			for (File element : f.listFiles() ) {
									
				texte += "\n";
				for(int i=0;i<tabulation; i++) {
					texte+="\t";
				}
				texte+="\t"+getArborescenceFromPathString(element.getPath(), tabulation+1);			
				
			}
		}
		
		return texte ;
	}
	
	
	
	public Document lancerTravail(String Path) {
		Document doc = new Document(getArborescenceFromPath(Path));
		return doc;
	}
	
	private Element getArborescenceFromPath(String path) {
		File f = new File(path);
		Element noeud = new Element("Element"); // nom du fichier;
		noeud.setText(f.getName());
		
		if(f.isDirectory()) {
			for (File element : f.listFiles() ) {
									
				noeud.addContent(getArborescenceFromPath(element.getPath()));			
				
			}
		}
		
		return noeud ;
		
	}
	
	//Fonctionnel
	public void parcoursXML(Element element, int tabulation) {
		
		for(int i=0; i<tabulation; i++) System.out.print("\t");
		System.out.println(element.getText());
		for (int i =0; i<element.getChildren().size(); i++) {
			parcoursXML((Element)element.getChildren().get(i), tabulation+1);
		}
		
		
		
		
		
		
	}	
	
	
}
