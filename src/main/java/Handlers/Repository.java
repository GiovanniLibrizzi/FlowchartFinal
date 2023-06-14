package Handlers;

import Shapes.*;
import Shapes.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Handlers.Repository - holds data for shapes and lines, and allows processing
 * @author Jacob Balikov, Giovanni Librizzi, Nicholas Zarate, Jin Wu, Umair Pathan, Amogh Prajapat
 * @version FlowchartFinal v1.0
 */
public class Repository extends Observable {

    private String currentUser = "";

    private Flowchart flowchart = new Flowchart();

    private List<Shape> unremovableShapesList = new ArrayList<>();

    private String currentShapeSelection = "RectangleStandard";
    private static Repository repo;

    /**
     * Singleton - gets the static repository instance
     * @return repository
     */
    public static Repository getInstance(){
        if(repo == null)
            repo = new Repository();
        return repo;
    }

    /**
     * Gets the current user
     * @return current user
     */
    public String getCurrentUser(){return currentUser;}

    /**
     * sets the current user
     * @param user the user who just logged in
     */
    public void setCurrentUser(String user){currentUser = user;}

    /**
     * Gets the currently selected shape
     * @return currently selected shape
     */
    public String getShapeSelection(){return currentShapeSelection;}

    /**
     * sets the currently selected shape
     * @param selection new shape to be selected
     */
    public void setShapeSelection(String selection){currentShapeSelection = selection;}

    /**
     * Adds an unremovable shape to the unremovable list
     * @param shape unremovable shape
     */
    public void addUnremovableShape(Shape shape){unremovableShapesList.add(shape);}

    /**
     * Gets all the unremovable shapes
     * @return list of shapes
     */
    public List<Shape> getUnremovableShape(){return this.unremovableShapesList;}

    /**
     * Gets an unremovable shape from an index
     * @param index
     * @return an unremovable shape
     */
    public Shape getUnremovableShape(int index){
        return unremovableShapesList.get(index);
    }

    /**
     * Adds a shape to the shapelist, repaints w/ observers
     * @param shape new shape to add
     */
    public void add (Shape shape) {
        flowchart.add(shape);
        setChanged();
        notifyObservers();
    }

    public void add(Flowchart f) {
        flowchart = f;
        setChanged();
        notifyObservers();
    }

    /**
     * Adds a line to the linelist, repaints with observers
     * @param line new line to add
     */
    public void add (ConnectingLine line) {
        flowchart.add(line);
        setChanged();
        notifyObservers();
    }

    public void undoShape() {
        flowchart.undoShape();
        setChanged();
        notifyObservers();
    }

    public void undoLine() {
        flowchart.undoLine();
        setChanged();
        notifyObservers();
    }


    /**
     * Gets a shape from the shapelist from an index
     * @param index
     * @return a shape object
     */
    public Shape getShape(int index) { return flowchart.getShapes().get(index); }

    /**
     * gets the list of shapes
     * @return a list of shapes
     */
    public List<Shape> getShapes() { return flowchart.getShapes(); }

    public Flowchart getFlowchart() { return flowchart; }


   // public List<Shapes.Rectangle> getRects() {return this.rectList;}

    /**
     * Gets the list of all current connectinglines
     * @return list of lines
     */
    public List<ConnectingLine> getLines() {return flowchart.getLines();}
    public ConnectingLine getLine(int index) {return flowchart.getLines().get(index);}

    /**
     * gets the size of the shapelist
     * @return
     */
    public int shapesSize(){ return flowchart.getShapes().size();}
    public int linesSize(){ return flowchart.getLines().size();}

    /*
    public Shapes.Rectangle checkWithinRectangle(int x, int y) {
        for (Shapes.Rectangle r : rectList) {
            Shapes.Shape s = (Shapes.Shape)r;
            if (s.checkClick(x, y)) {
                System.out.println("Clicked inside rectangle");
                return r;
            }
        }
        return null;
    }

    public Shapes.Shape checkWithinShape(int x, int y, List<Shapes.Shape> shapeList){

        for (Shapes.Shape s : shapeList) {
            if (s.checkClick(x, y)) {
                System.out.println("Clicked inside shape (non-rect)");
                return s;
            }
        }
        return null;
    }
*/

    /**
     * Updates observers
     */
    public void moved(){
        setChanged();
        notifyObservers();
    }

    /**
     * Clears all shapes and lines, updates observers
     */
    public void clear(){
        flowchart.getShapes().clear();
        flowchart.getLines().clear();
        setChanged();
        notifyObservers();
    }

}
