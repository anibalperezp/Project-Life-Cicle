package ceis.grehu.gui.paint;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class Relation {

	private Stereotype stereotype = null;

	private int parentId = 0;

	private String typeRel = null;

	private ArrayList<Point> arraypoints = null;

	private String orientation = "";

	private Point intersection = null;	

	private String name = "";

	private int id = 0;

	private boolean selected = false;

	private ArrayList<Rectangle> arrayRectangles;

	private ArrayList<Line2D> arrayLines;


	public Relation() {
		super();
		this.stereotype = null;
		this.typeRel = "";
		this.parentId = -1;
		this.id = -1;
		arraypoints = new ArrayList<Point>();
		orientation = "";
		intersection = null;
		name = "";
		selected = false;
		arrayRectangles = new ArrayList<Rectangle>();
		arrayLines = new ArrayList<Line2D>();
	}


	public Relation(Stereotype stereotype){
		super();
		this.stereotype = stereotype;
		arraypoints = new ArrayList<Point>();
		arrayRectangles = new ArrayList<Rectangle>();
		arrayLines = new ArrayList<Line2D>();
		Random random = new Random();
		this.id = random.hashCode();
	}

	public Stereotype getStereotype() {
		return stereotype;
	}

	public void setStereotype(Stereotype stereotype) {
		this.stereotype = stereotype;
	}

	public String getTypeRel() {
		return typeRel;
	}

	public void setTypeRel(String typeRel) {
		this.typeRel = typeRel;
	}

	public ArrayList<Point> getArraypoints() {
		return arraypoints;
	}

	public void setArraypoints(ArrayList<Point> arraypoints) {
		this.arraypoints = arraypoints;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public Point getIntersection() {
		return intersection;
	}

	public void setIntersection(Point intersection) {
		this.intersection = intersection;
	}  

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public ArrayList<Rectangle> getArrayRectangles() {
		return arrayRectangles;
	}

	public void setArrayRectangles(ArrayList<Rectangle> arrayRectangles) {
		this.arrayRectangles = arrayRectangles;
	}

	public ArrayList<Line2D> getArrayLines() {
		return arrayLines;
	}

	public void setArrayLines(ArrayList<Line2D> arrayLines) {
		this.arrayLines = arrayLines;
	}

	public boolean isPointExtreme(Point2D point){
		boolean found = false;

		if(point.getX() == getArraypoints().get(0).getX() && point.getY() == getArraypoints().get(0).getY()){
			found = true;
		}

		if(point.getX() == getArraypoints().get(getArraypoints().size() - 1).getX() && point.getY() == getArraypoints().get(getArraypoints().size() - 1).getY()){
			found = true;
		}

		return found;
	}

	public boolean findPoint(Point point){
		boolean found = false;
		for (int i = 0; i < getArraypoints().size(); i++) {
			if(getArraypoints().get(i).equals(point)){
				found = true;
				break;
			}
		}
		return found;
	}
}
