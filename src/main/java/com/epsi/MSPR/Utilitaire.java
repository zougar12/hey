package MSPR;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utilitaire {
    public static List<MSPRs> lireDepuisFichier(String path, String chsavehtml, String chsavepasswd) throws IOException {
        
        
        List<MSPRs> equipements = new ArrayList<>();
        Path path1;
        String nom = null, prenom = null, role = null, password = null;        
        FileWriter filehtml, fileacchtml;
        FileWriter passwd = new FileWriter(chsavepasswd + ".htpasswd");
        File dir = new File(path);
        File deldir = new File(chsavehtml);
        File[] liste = dir.listFiles();
        //System.out.println(liste.length);
        
        Arrays.stream(deldir.listFiles())
        .filter(f -> f.getName().endsWith(".html"))
        .forEach(File::delete);
        
        fileacchtml = new FileWriter(chsavehtml + "index.html");
        
        
        fileacchtml.write("<!Doctype html>\n");
        fileacchtml.write("<html>\n");
        fileacchtml.write("	<head>\n");
        fileacchtml.write("		<meta charset=\"utf-8\">\n");
        fileacchtml.write("		<link rel=\"stylesheet\" href=\"index.css\">\n");
        fileacchtml.write("		<title> Page d'accueil </title>\n");
        fileacchtml.write("	</head>\n");
        fileacchtml.write("	<body>\n");
        fileacchtml.write("		<div class=\"main-home\">\n");
        fileacchtml.write("		<div class=\"logo\">\n");
        fileacchtml.write("			<img src=\"./images/logo.png\">\n");
        fileacchtml.write("		</div>\n");
        fileacchtml.write("		<div class=\"=\"staff>\n");
        fileacchtml.write("			<h2>Agents : </h2>\n");
        fileacchtml.write("			<div class=\"staff-list\">\n");
        
        
        for (int i = 0; i < liste.length; i++) {
        	List<String> materiels = new ArrayList<>();
            //System.out.println(liste[i].getName());
            path1 = Path.of(path + liste[i].getName());
            //System.out.println(path1);
            String id = liste[i].getName().split("\\.")[0];            
            
            List<String> contenu = Files.readAllLines(path1);
            
            nom = contenu.get(0);
            prenom = contenu.get(1);
            role = contenu.get(2);
            password = contenu.get(3);
            for(int x = 5; x < contenu.size(); x++) {
                materiels.add(contenu.get(x));
            }
            
            filehtml = new FileWriter(chsavehtml + id + ".html");
            
            filehtml.write("<!DOCTYPE html>\n");
            filehtml.write("<html>\n");
            filehtml.write("    <head>\n");
            filehtml.write("        <meta charset=\"utf-8\">\n");
            filehtml.write("        <link rel=\"stylesheet\" href=\"agent.css\">\n");
            filehtml.write("        <link rel=\"stylesheet\" href=\"all.css\">\n");
            filehtml.write("        <script defer src=\"all.js\"></script>\n");
            filehtml.write("        <title> Fiche de " + nom + " " + prenom + "</title>\n");
            filehtml.write("    </head>\n");
            filehtml.write("    <body>\n");
            filehtml.write("        <div class= \"main-agent\">\n");
            filehtml.write("            <div class=\"infos\">\n");
            filehtml.write("            <div class=\"id\"><h1>" + nom + " " + prenom + "</h1></div><br>\n");
            filehtml.write("            <div class=\"id\"><h1>" + role + "</h1></div>\n");
            filehtml.write("            <div class=\"photo\"> <img src=\"./images/boris.png\"> </div>\n");
            filehtml.write("            </div>\n");
            filehtml.write("            <div class=\"liste\">\n");
            filehtml.write("                <div><h2> Liste d'équiments: </h2></div>\n");
            filehtml.write("                <br>\n");
            filehtml.write("                <div class=\"checklist\">\n");
            
            for(int o = 0 ; o<materiels.size(); o++) {
                filehtml.write("                    <div class=\"row\"><p>  <i class=\"far fa-check-circle\"></i>" + materiels.get(o) + "</p> </div>\n");
            }
                    
            filehtml.write("                </div>\n");
            filehtml.write("            </div>\n");
            filehtml.write("        </div>\n");   
            filehtml.write("    </body>\n");
            filehtml.write("</html>\n");
            filehtml.close();
     
            
	        fileacchtml.write("				<a href=" + id + ".html>" + nom + " " + prenom + "</a>\n");
	        fileacchtml.write("				<br>\n");
            fileacchtml.write("				<br>\n");


                       
            passwd.write(id + ":" + password + "\n");
            
        }
        fileacchtml.write("			</div>\n");
        fileacchtml.write("			</div>\n");
        fileacchtml.write("		</div> \n");
        fileacchtml.write("	</body>\n");
        fileacchtml.write("</html>\n");
        fileacchtml.close();
        passwd.close();
        return equipements;
    }
}