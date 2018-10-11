/**
 * 
 */
package comp3111.webscraper;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Hyperlink;

import java.awt.Desktop;
import java.util.List;
import java.lang.Object;
import java.net.URI;

/**
 * 
 * @author kevinw
 *
 *
 * Controller class that manage GUI interaction. Please see document about JavaFX for details.
 * 
 */
public class Controller {

    @FXML 
    private Label labelCount; 

    @FXML 
    private Label labelPrice; 

    @FXML 
    private Hyperlink labelMin; 

    @FXML 
    private Hyperlink labelLatest; 

    @FXML
    private TextField textFieldKeyword;
    
    @FXML
    private TextArea textAreaConsole;
    
    private WebScraper scraper;
    
    /**
     * Default controller
     */
    public Controller() {
    	scraper = new WebScraper();
    }

    /**
     * Default initializer. It is empty.
     */
    @FXML
    private void initialize() {
    	
    }
    
    /**
     * Called when the search button is pressed.
     */
    @FXML
    private void actionSearch() {
    	System.out.println("actionSearch: " + textFieldKeyword.getText());
    	List<Item> result = scraper.scrape(textFieldKeyword.getText());
    	String output = "", lowest_url = "";
    	double average = -1, lowest = -1;
    	for (Item item : result) {
    		
    		output += item.getTitle() + "\t" + item.getPrice() + "\t" + item.getUrl() + "\n";
    		average += item.getPrice();
    		if(lowest == -1 || item.getPrice() < lowest) {
    			lowest = item.getPrice();
    			lowest_url = item.getUrl();
    		}
    		//System.out.println(item.getUrl());
    			
    	}
    	if(result.size() == 0) {
    		labelMin.setText("-");
    		labelLatest.setText("-");
    		labelPrice.setText("-");
    	} else {
    		final String lowest_url_final = lowest_url;
    		average /= result.size();
    		labelPrice.setText(Double.toString(average));
        	labelMin.setText(Double.toString(lowest));
        	labelMin.setOnAction(
        		event -> {
        			try { 
        				Desktop.getDesktop().browse(new URI(lowest_url_final)); 
        			} catch (Exception e) { 
        				System.out.println(e.getMessage()); 
        			} 
        		}
        	);
        	labelLatest.setText(result.get(result.size() - 1).getUrl());
        	labelLatest.setOnAction(
    			event -> {
    				try {
    					Desktop.getDesktop().browse(new URI(new String(result.get(result.size() - 1).getUrl())));
    				} catch (Exception e) {
    					System.out.println(e.getMessage());
    				}
    			}
        	);
    	}    	
    	labelCount.setText(Integer.toString(result.size()));
    	textAreaConsole.setText(output);
		//add the following line
		labelCount.setText("Hi");
    	
    }
    
    /**
     * Called when the new button is pressed. Very dummy action - print something in the command prompt.
     */
    @FXML
    private void actionNew() {
    	System.out.println("actionNew");
    }
}

