package pl.merbio.utilities;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pl.merbio.Main;

/**
 * @author Merbio
 */

public class UpdateChecker {

    private Main plugin;
    private URL filesFeed;
    
    private String version;
    private String link;
    
    public UpdateChecker(Main plugin, String url){
        this.plugin = plugin;
        try {
            this.filesFeed = new URL(url);
        } catch (MalformedURLException e) {
            plugin.getLogger().info("Nie udalo sie sprawdzic wersji pluginu.");
        }
    }
    
    public boolean updateNeeded(){
        try {
            InputStream input = this.filesFeed.openConnection().getInputStream();
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
            
            Node lastesFile = document.getElementsByTagName("item").item(0);
            NodeList children = lastesFile.getChildNodes();
            
            this.version = children.item(1).getTextContent().replaceAll("[a-zA-Z ]", "");
            this.link = children.item(3).getTextContent();
            
            //plugin.getLogger().info(this.version + " " + this.link);
            if(!plugin.getDescription().getVersion().equalsIgnoreCase(this.version)){
                return true;
            }
        } catch (Exception e) {}
        
        return false;
    }
    
    public String getVersion(){
        return this.version;
    }
    
    public String getLink(){
        return this.link;
    }
}
