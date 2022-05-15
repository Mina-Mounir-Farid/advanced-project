package com.example.philojx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import org.apache.poi.ss.usermodel.Sheet;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import org.openxmlformats.schemas.drawingml.x2006.chart.STSplitType;


public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane pane = new BorderPane();
        VBox vBox=new VBox();
        HBox hBox =new HBox();
        hBox.setSpacing(5);
        String link = new File("Example.xlsx").getAbsolutePath();

        Iterator<Sheet> iter = sheetReader.read(link);

        Service service = sheetReader.analyse(iter);
        Iterator<Operation> operations = service.getOperations();


        while (operations.hasNext()){
            Operation op = operations.next();
            String StTitle=op.getName() + "\n" + op.get_HTTP_opertion() + "\t" + op.get_REST_URL();
            Label lblTitle=new Label(StTitle);
            lblTitle.setFont(new Font("BOLD",20));

            vBox.getChildren().add(lblTitle);
           // pane.setCenter(vBox);

            for(int i=0;i<op.getSizeOfField();i++) {
                Field fTemp=op.getFieldByIndex(i);
                if(fTemp instanceof  ObjField ObjF){
                    Button btObj =new Button(ObjF.getName());
                    btObj.setFont(new Font("BOLD",15));
                    //btObj.setPrefSize(100,100);
                    hBox.getChildren().add(btObj);
                    hBox.setStyle("-fx-background-color: #fe5f55 ");
                    hBox.setPadding(new Insets(5,5,5,5));
                    btObj.setOnAction(e->{
                        VBox vBoxOfObj=new VBox();
                        vBoxOfObj.setSpacing(5);
                        vBoxOfObj.setPadding(new Insets(5,5,5,5));
                        for (int j=0;j<ObjF.getSizeOfSubField();j++){
                            if(ObjF.getFieldByIndex(j) instanceof StringField StringSub){
                                String StData=StringSub.getDirection() + "\t" + StringSub.getName()+ "\t" + "string" +"\t" +Arrays.toString(StringSub.getAllowedVals())+"\t" + (StringSub.isMandatory()?"y":"N");
                                //probaly error
                                Label lblSt=new Label(StData);
                                lblSt.setFont(new Font("BOLD",16));
                                vBoxOfObj.getChildren().add(lblSt);
                                vBoxOfObj.setStyle("-fx-background-color: #eef5d8");

                            }
                            else if(ObjF.getFieldByIndex(j) instanceof ObjField ObjSub){
                                String StDataObjField=ObjSub.getDirection() + "\t" + ObjSub.getName() + "\t" + "object" +"\t \t" + (ObjSub.isMandatory()?"y":"N");
                                Label lblObj=new Label(StDataObjField);
                                Button btObjSub=new Button(ObjSub.getName());
                                vBoxOfObj.getChildren().add(btObjSub);
                                btObjSub.setOnAction(x->{
                                    for(int k=0;k<ObjSub.getSizeOfSubField();k++){
                                        if(ObjSub.getFieldByIndex(k) instanceof StringField StringSubSub){
                                            VBox vBoxSubSub=new VBox();
                                            String StData=StringSubSub.getDirection() + "\t" + StringSubSub.getName()+ "\t" + "string" +"\t" +Arrays.toString(StringSubSub.getAllowedVals())+"\t" + (StringSubSub.isMandatory()?"y":"N");
                                            //probaly error
                                            Label lblSt=new Label(StData);
                                            lblSt.setFont(new Font("BOLD",16));
                                            vBoxSubSub.getChildren().add(lblSt);
                                            vBoxOfObj.getChildren().add(vBoxSubSub);

                                        }

                                    }
                                });

                                lblObj.setFont(new Font("BOLD",16));
                                vBoxOfObj.getChildren().add(lblObj);
                                vBoxOfObj.setStyle("-fx-background-color: #eef5d8");

                            }
                            pane.setCenter(vBoxOfObj);//pos
                        }
                    });

                }
                else if(op.getFieldByIndex(i) instanceof StringField f){
                    Button btNoParent=new Button("No Object Parent");
                    hBox.getChildren().add(btNoParent);
                    btNoParent.setOnAction(e->{
                        VBox vboxOfNoChilds=new VBox();
                        String StDataStringField=f.getDirection() + "\t" + f.getName()+ "\t" + "string" +"\t" +Arrays.toString(f.getAllowedVals())+"\t" + (f.isMandatory()?"y":"N");
                        Label lblSt=new Label(StDataStringField);
                        lblSt.setFont(new Font("BOLD",16));
                        vboxOfNoChilds.getChildren().add(lblSt);
                        vboxOfNoChilds.setStyle("-fx-background-color: #eef5d8");
                        pane.setCenter(vboxOfNoChilds);

                    });


                }


            }

        }

        Label lblDes=new Label("HELLO this is the GUI of program");
        lblDes.setFont(new Font("Verdana",20));
        lblDes.setTextFill(Color.RED);
        pane.setCenter(lblDes);

        vBox.setStyle("-fx-background-color: #7a9e9f ");
        hBox.setAlignment(Pos.TOP_CENTER);
        pane.setTop(hBox);
        pane.setLeft(vBox);
        Scene scene = new Scene(pane, 1000, 500);
        stage.setTitle("CourseProject"); // Set the stage title
        stage.setScene(scene); // Put scene in the stage
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
