package MSPR;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Programme {
    public static void main(String[] args) throws IOException {
    	
    	String path = "C:\\Users\\zouga\\OneDrive\\Bureau\\Nouveau dossier\\";
    	String chsavehtml = "C:\\wamp\\www\\authentification\\";
        String chsavepasswd = "C:\\wamp\\www\\authentification\\";
        List<MSPRs> equipementss = Utilitaire.lireDepuisFichier(path, chsavehtml, chsavepasswd);
    }
}
