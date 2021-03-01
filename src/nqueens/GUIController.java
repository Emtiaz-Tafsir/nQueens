/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nqueens;

import javafx.scene.image.Image;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Lord Tafaius
 */
public class GUIController implements Initializable {
    
//    @FXML
//    private Label label;
//    
//    @FXML
//    private TextArea textArea;
//    
//    @FXML
//    private GridPane gp;
//
//    
//    @FXML
//    private void handleButtonAction(ActionEvent event) {
//        System.out.println("You clicked me!");
//        label.setText("Hello World!");
//        int u = 0;
//        textArea.setText("Begin");
//        textArea.setScrollTop(0);
//        while (u<50){
//            textArea.appendText("Hi there!! -- "+(u++)+"\n");
//            //textArea.setScrollTop(Double.MIN_VALUE);
//        }
//        ImageView imv = new ImageView(new Image(getClass().getClassLoader().getResource("resources/queen.png").toString()));
//        imv.setFitHeight(30);
//        imv.setFitWidth(30);
//        GridPane.setConstraints(imv, 1, 1);
//        gp.setGridLinesVisible(true);
//        gp.getChildren().add(imv);
//    }
    
        @FXML
    private Label labelNoC;

    @FXML
    private Label labelNoG;

    @FXML
    private TextField textfC;

    @FXML
    private TextField textfQ;

    @FXML
    private Button buttonSim;

    @FXML
    private Label labelWarn;

    @FXML
    private TextArea txaLog;
    
    @FXML
    private HBox hbox;
    
    @FXML
    protected void onButtonActionSimulate(ActionEvent event) {
        hbox.getChildren().clear();
        labelWarn.setText(" ");
        int c;
        int n;
        try{
            c = Integer.parseInt(textfC.getText());
            n = Integer.parseInt(textfQ.getText());
        }
        catch(Exception e){
            labelWarn.setText("Please Put Whole Numbers!!!");
            return;
        }
        
        labelNoC.setText(""+c);
        new GeneticAlgo(c,n,txaLog, labelNoG, this);
    }
    
    protected void genarate(int[] val){
        GridPane gp = new GridPane();
        gp.setGridLinesVisible(true);
        boolean alt = false;
        for(int i=0; i<val.length; i++){
           boolean white = !alt;
           for(int j=0; j<val.length; j++){
                ImageView imv;
                if(val[i]==(val.length-j))
                    imv = new ImageView(new Image(getClass().getClassLoader().getResource("resources/queen.png").toString()));
                else if(white)
                    imv = new ImageView(new Image(getClass().getClassLoader().getResource("resources/white.jpg").toString()));
                else
                    imv = new ImageView(new Image(getClass().getClassLoader().getResource("resources/black.jpg").toString()));
                imv.setFitHeight(40);
                imv.setFitWidth(40);
                GridPane.setConstraints(imv, i, j);
                gp.getChildren().add(imv);
                white = !white;
           }
           alt = !alt;
        }
        hbox.getChildren().add(new Group(gp));
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
