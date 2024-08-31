package com.example.paint;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

public class HelloController {

    @FXML
    private Slider changesizew;

    @FXML
    private Slider changesizeh;

    @FXML
    private Slider changesizer;

    @FXML
    private Slider changesizes;

    @FXML
    private MenuButton changethickness;



    @FXML
    private ColorPicker changecolor;

    int shapenumber=0;
    Shape shape;

    double rotate=0;
    @FXML
    private void initialize(){
        changesizew.valueProperty().addListener((observable , oldv , newv ) -> onWidthHeightSliderChanged());
        changesizeh.valueProperty().addListener((observable , oldv , newv ) -> onWidthHeightSliderChanged());
        changesizer.valueProperty().addListener((observable , oldv , newv ) -> onWidthHeightSliderChanged());

        MenuItem item1 = new MenuItem("size1");
        item1.setOnAction(event -> changethickness.setText(item1.getText()));
        changethickness.getItems().add(item1);

        MenuItem item2 = new MenuItem("size2");
        item2.setOnAction(event -> changethickness.setText(item2.getText()));
        changethickness.getItems().add(item2);

        MenuItem item3 = new MenuItem("size3");
        item3.setOnAction(event -> changethickness.setText(item3.getText()));
        changethickness.getItems().add(item3);
    }


    private double size(String s){
        if (s.equals("size1")){
            return 1.0;
        }
        else if (s.equals("size2")){
            return 4.0;
        }
        else if (s.equals("size3")){
            return 7.0;
        }
        return 0.0;

    }


    public void drawrect(){
        shapenumber=1;
        shape= new Rectangle(100,100,1,100);
        System.out.println(shapenumber);
    }
    public void drawcircle(){
        shapenumber=2;
        shape= new Circle(100,100,1, Color.RED);
        System.out.println(shapenumber);
    }
    public void drawellipse(){
        shapenumber=3;
        shape = new Ellipse(100,100,100,100);
        System.out.println(shapenumber);
    }
    public void drawsquare(){
        shapenumber=4;
        shape= new Rectangle(100,100,1,100);
        System.out.println(shapenumber);
    }

    public void degreerotate(){
        if (hasclicked != null){
            hasclicked.setRotate(90.0);
        }
        else{
            if (shapenumber!=0){
                rotate+=1.0;
            }
            else {
                System.out.println("r");
            }
        }
        draw2();
    }

    public <T extends Shape> T returnshape(int n){
        if (n==1){
            Rectangle rectangle = new Rectangle();
            return (T) rectangle;
        }
        else if (n==2){
            Ellipse ellipse = new Ellipse();
            return (T) ellipse;
        }
        else if (n==3){
            Ellipse ellipse = new Ellipse();
            return (T) ellipse;
        }
        else if (n==4){
            Rectangle rectangle = new Rectangle();
            return (T) rectangle;
        }
        return null;
    }

    public ArrayList<Shape> shapes = new ArrayList<>();

    public void drawshapes(){
        Shape s = returnshape(shapenumber);
        double t = size(changethickness.getText());
        if (s!=null){
            if (t!=0){
                Color c = changecolor.getValue();
                double degree = rotate * 90.0;
                rotate=0;
                if (s instanceof Rectangle){
                    Rectangle r = (Rectangle) s;
                    if (shapenumber==1){
                        double w = changesizew.getValue();
                        double h = changesizeh.getValue();
                        r=new Rectangle(100-w/2,100-h/2,w,h);
                        r.setStroke(c);
                        r.setRotate(degree);
                        r.setStrokeWidth(t);
                        shapes.add(r);
                        draw2();
                    }
                    else if (shapenumber==4){
                        double w = changesizer.getValue();
                        r=new Rectangle(100-w/2,100-w/2,w,w);
                        r.setStroke(c);
                        r.setRotate(degree);
                        r.setStrokeWidth(t);
                        shapes.add(r);
                        draw2();
                    }

                }

                if (s instanceof Ellipse){
                    Ellipse e = (Ellipse) s;
                    if (shapenumber==3){
                        double w = changesizew.getValue();
                        double h = changesizeh.getValue();
                        e=new Ellipse(100,100,w/2,h/2);
                        e.setRotate(degree);
                        e.setStroke(c);
                        e.setStrokeWidth(t);
                        shapes.add(e);
                        draw2();
                    }
                    else if (shapenumber==2){
                        double r = changesizer.getValue();
                        e=new Ellipse(100,100,r/2,r/2);
                        e.setRotate(degree);
                        e.setStroke(c);
                        e.setStrokeWidth(t);
                        shapes.add(e);
                        draw2();
                    }
                }
                shapenumber=0;
            }
            else{
                System.out.println("t");
            }
        }
        else {
            System.out.println("f");
        }

    }
    @FXML
    private Canvas canvas;

    public void draw2(){
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.clearRect(0,0,720,690);
        for (int i=0; i< shapes.size();i++){
            if (shapes.get(i) instanceof Rectangle){
                graphicsContext.setStroke(shapes.get(i).getStroke());
                graphicsContext.setLineWidth(shapes.get(i).getStrokeWidth());
                graphicsContext.save();
                graphicsContext.translate(((Rectangle) shapes.get(i)).getX()+((Rectangle) shapes.get(i)).getWidth()/2,((Rectangle) shapes.get(i)).getY()+((Rectangle) shapes.get(i)).getHeight()/2);
                graphicsContext.rotate(shapes.get(i).getRotate());
                graphicsContext.strokeRect(-(((Rectangle) shapes.get(i)).getWidth()/2),-(((Rectangle) shapes.get(i)).getArcHeight()/2),((Rectangle) shapes.get(i)).getWidth(),((Rectangle) shapes.get(i)).getHeight());
                graphicsContext.restore();
            }
            if (shapes.get(i) instanceof Ellipse){
                graphicsContext.setStroke(shapes.get(i).getStroke());
                graphicsContext.setLineWidth(shapes.get(i).getStrokeWidth());
                graphicsContext.save();
                graphicsContext.translate(((Ellipse) shapes.get(i)).getCenterX(),((Ellipse) shapes.get(i)).getCenterY());
                graphicsContext.rotate(shapes.get(i).getRotate());
                graphicsContext.strokeOval(-((Ellipse) shapes.get(i)).getRadiusX(),-((Ellipse) shapes.get(i)).getRadiusY(),((Ellipse) shapes.get(i)).getRadiusX()*2,((Ellipse) shapes.get(i)).getRadiusY()*2);
                graphicsContext.restore();
            }
        }

    }
    public Shape hasclicked;
    double mx;
    double my;
    @FXML
    private void clickeonshape(MouseEvent m){
        mx = m.getX();
        my = m.getY();
        for (int i=0 ; i<shapes.size();i++){
            if (checkshape(shapes.get(i),mx,my)){
                hasclicked=shapes.get(i);
            }
        }

        mx = m.getX();
        my = m.getY();
        if (hasclicked!=null){
            uc();
        }



    }

    private <T extends Shape> boolean checkshape(T shapes , double mx , double my){
        if (shapes instanceof Rectangle){
            Rectangle s = (Rectangle) shapes;
            if (mx >= s.getX() && mx <= (s.getX() + s.getWidth())) {
                if (my>= s.getY() && my <= (s.getY() + s.getHeight())){
                    return true;
                }
            }
            return false;
        }
        else if (shapes instanceof Ellipse){
            Ellipse e = (Ellipse) shapes;
            double cx = e.getCenterX();
            double cy = e.getCenterY();
            double rx = e.getRadiusX();
            double ry = e.getRadiusY();
            double d1 =((mx-cx) * (mx-cx)) / (rx * rx);
            double d2 =((my-cy) * (my-cy)) / (ry * ry);

            if (d2 + d1 <=1) {
                return true;
            }
            return false;
        }
        return false;

    }

    @FXML
    private void move(MouseEvent m){
        if (hasclicked!=null){
            double m1=m.getX();
            double m2=m.getY();
            double movex = m.getX() - mx;
            double movey = m.getY() - my;

            if (hasclicked instanceof Rectangle){
                ((Rectangle) hasclicked).setX(((Rectangle) hasclicked).getX() + movex);
                ((Rectangle) hasclicked).setY(((Rectangle) hasclicked).getY() + movey);
            }

            else if (hasclicked instanceof Ellipse){
                ((Ellipse) hasclicked).setCenterX(((Ellipse) hasclicked).getCenterX()+movex);
                ((Ellipse) hasclicked).setCenterY(((Ellipse) hasclicked).getCenterY()+movey);
            }

            mx=m1;
            my=m2;
            draw2();
        }

    }

    @FXML
    private void unselect(MouseEvent m){
        mx = m.getX();
        my = m.getY();
        boolean clicked=false;
        for (int i=0 ; i<shapes.size();i++){
            if (checkshape(shapes.get(i),mx,my)){
                clicked=true;
            }
        }

        if (!clicked){
            hasclicked=null;
        }
    }

    private void uc(){
        if (hasclicked instanceof Rectangle){
            changesizew.setValue(((Rectangle) hasclicked).getWidth());
            changesizeh.setValue(((Rectangle) hasclicked).getHeight());
        }
    }
    @FXML
    private void changesizedegree(){
        if (hasclicked!=null){
            if (hasclicked instanceof Rectangle){
                ((Rectangle) hasclicked).setWidth(changesizew.getValue());
                ((Rectangle) hasclicked).setHeight(changesizeh.getValue());
                System.out.println("t");
            }
            draw2();
        }
    }

    @FXML
    private void onWidthHeightSliderChanged(){
        if (hasclicked!=null){
            double nw = changesizew.getValue();
            double nh = changesizeh.getValue();
            if (hasclicked instanceof Rectangle){
                if (((Rectangle) hasclicked).getWidth() == ((Rectangle) hasclicked).getHeight()){
                    double newa = changesizer.getValue();
                    ((Rectangle) hasclicked).setHeight(newa);
                    ((Rectangle) hasclicked).setWidth(newa);
                }
                else {
                    ((Rectangle) hasclicked).setWidth(nw);
                    ((Rectangle) hasclicked).setHeight(nh);
                }
            }
            else if (hasclicked instanceof Ellipse){
                if (((Ellipse) hasclicked).getRadiusY() == ((Ellipse) hasclicked).getRadiusX()){
                    double newr = changesizer.getValue();
                    ((Ellipse) hasclicked).setRadiusX(newr);
                    ((Ellipse) hasclicked).setRadiusY(newr);
                }
                else{
                    ((Ellipse) hasclicked).setRadiusX(nw);
                    ((Ellipse) hasclicked).setRadiusY(nh);
                }
            }
            draw2();
        }
    }

    private void rotate(){
        if (hasclicked!=null){
            rotate+=1.0;
        }
        draw2();
    }








}