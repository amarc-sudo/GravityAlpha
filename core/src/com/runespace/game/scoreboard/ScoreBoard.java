package com.runespace.game.scoreboard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.filechooser.FileSystemView;

public class ScoreBoard implements Serializable {
    static private final long serialVersionUID = 6L;
    ArrayList<Integer> scoreBoard;
    private static String OS = System.getProperty("os.name").toLowerCase();
    
	FileSystemView fsv = FileSystemView.getFileSystemView();
	File f = fsv.getDefaultDirectory();
    
    public ScoreBoard(){
    		createSave();
    }
    public void create(){
        scoreBoard = new ArrayList<Integer>();
    }
    public void add(int item){
        if(scoreBoard.size() < 5)
            scoreBoard.add(item);
        else{
            int i = (int) Collections.min(scoreBoard);
            if(i < item) {
                scoreBoard.remove(Collections.min(scoreBoard));
                scoreBoard.add(item);
            }

        }
        Collections.sort(scoreBoard);
    }

    public void save(String name){
        if(isWindows()) {
	        try {
	            FileOutputStream fos = new FileOutputStream(new File(f.toString()+ "\\gravity\\" + name+".serial"));
	            // création d'un "flux objet" avec le flux fichier
	            ObjectOutputStream oos= new ObjectOutputStream(fos);
	            try {
	            // sérialisation : écriture de l'objet dans le flux de sortie
	            oos.writeObject(scoreBoard);
	            // on vide le tampon
	            oos.flush();
	        
	            } finally {
	            //fermeture des flux
	                try {
	                    oos.close();
	                    } finally {
	                        fos.close();
	                    }
	                }
	            }catch(IOException ioe) {
	                ioe.printStackTrace();
	            }
           }
        if(isUnix()) {
	        try {
	        	
	            FileOutputStream fos = new FileOutputStream(new File(f.toString()+"/gravity/" + name+".serial"));
	            // création d'un "flux objet" avec le flux fichier
	            ObjectOutputStream oos= new ObjectOutputStream(fos);
	            try {
	            // sérialisation : écriture de l'objet dans le flux de sortie
	            oos.writeObject(scoreBoard);
	            // on vide le tampon
	            oos.flush();
	        
	            } finally {
	            //fermeture des flux
	                try {
	                    oos.close();
	                    } finally {
	                        fos.close();
	                    }
	                }
	            }catch(IOException ioe) {
	                ioe.printStackTrace();
	            }
           }

    }

    @SuppressWarnings("unchecked")
	public void load(String name){
        if(isWindows()) {
	        try {
	                // ouverture d'un flux d'entrée depuis le fichier "personne.serial"
	                FileInputStream fis = new FileInputStream(new File(f.toString() +"\\gravity\\" + name+".serial"));
	                // création d'un "flux objet" avec le flux fichier
	                ObjectInputStream ois= new ObjectInputStream(fis);
	                try {
	                    // désérialisation : lecture de l'objet depuis le flux d'entrée
	                    scoreBoard = (ArrayList<Integer>) ois.readObject();
	                } finally {
	                    // on ferme les flux
	                    try {
	                        ois.close();
	                    } finally {
	                        fis.close();
	                    }
	                }
	            } catch(IOException ioe) {
	                create();
	                ioe.printStackTrace();
	            } catch(ClassNotFoundException cnfe) {
	                cnfe.printStackTrace();
	            }
	            if(scoreBoard != null) {
	            	
	            }
        }
        if(isUnix()) {
        	 try {
	                // ouverture d'un flux d'entrée depuis le fichier "personne.serial"
	                FileInputStream fis = new FileInputStream(new File(f.toString() +"/gravity/" + name+".serial"));
	                // création d'un "flux objet" avec le flux fichier
	                ObjectInputStream ois= new ObjectInputStream(fis);
	                try {
	                    // désérialisation : lecture de l'objet depuis le flux d'entrée
	                    scoreBoard = (ArrayList<Integer>) ois.readObject();
	                } finally {
	                    // on ferme les flux
	                    try {
	                        ois.close();
	                    } finally {
	                        fis.close();
	                    }
	                }
	            } catch(IOException ioe) {
	                create();
	                ioe.printStackTrace();
	            } catch(ClassNotFoundException cnfe) {
					 create();
	                cnfe.printStackTrace();
	            }
	            if(scoreBoard != null) {

	            }
        	}	
        }
    


    public Object maxList(){
        return Collections.max(scoreBoard);
    }

    public void createSave(){
        File file;
        if(isWindows()) {
	            file = new File(f.toString() + "\\Gravity");
	
	            if (file.exists()) {
	                
	            } else {
	                if (file.mkdir()) {
	                    
	                } else {
	                   
	                }
	            }
        }
        if(isUnix()) {
            
    		file = new File(f.toString()+"/gravity");
    		
            if (file.exists()) {
                
            } else {
                if (file.mkdirs()) {
                 
                } else {
                 
                }
            }
    }
	}
    public static boolean isWindows() {

		return (OS.indexOf("win") >= 0);

	}

	public static boolean isMac() {

		return (OS.indexOf("mac") >= 0);

	}

	public static boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
		
	}
	

}


