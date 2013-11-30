package com.example.avds;


import java.io.File;
import org.jdom2.Document;
import org.jdom2.Element;




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
