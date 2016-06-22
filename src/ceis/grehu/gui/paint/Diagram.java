package ceis.grehu.gui.paint;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import ceis.grehu.units.undoManager.RedoAction;
import ceis.grehu.units.undoManager.UndoAction;

public class Diagram {

	private String type;

	private int objectParent;

	private String name;

	private ArrayList<Stereotype> pictureList;

	private boolean selected;

	private Rectangle diagramSize;

	private Double zoomFactor = 0.0;

	private Rectangle rectVisible = null;//6

	private Color Background = null;

	private ArrayList<UndoAction> listUndo;

	private ArrayList<RedoAction> listRedo;

	private int id;

	private boolean executeBoth = false;

	private boolean executeWidth = false;

	private boolean executeHeight = false;

	private boolean approved = false;

	private SwinLine swinLine = null;
	
	private boolean saveStatus = false;

	/**
	 * 
	 * @param type "MNC"->Mapa de Nivel Cero. "RG"->Resumen Grafico
	 * @param objectParent
	 * @param name
	 */
	public Diagram(String type, int objectParent, String name) {
		super();
		this.type = type;
		this.name = name;
		this.Background = Color.WHITE;
		pictureList = new ArrayList<Stereotype>();
		selected = false;
		zoomFactor = 1.0;
		diagramSize = new Rectangle(0,0,2400,1400);
		listRedo = new ArrayList<RedoAction>();
		listUndo = new ArrayList<UndoAction>();
		Random random = new Random();
		this.id = random.hashCode();
		if(type == "MNC"){
			this.objectParent = this.id;
		}else{
			this.objectParent = objectParent;
		}
		this.approved = false;
		swinLine = null;
	}

	public Diagram() {
		super();
		pictureList = new ArrayList<Stereotype>();
		this.type = "";
		this.name = "";
		this.Background = null;
		selected = false;
		zoomFactor = 1.0;
		diagramSize = null;
		listRedo = new ArrayList<RedoAction>();
		listUndo = new ArrayList<UndoAction>();
		this.id = -1;
		this.objectParent = -1;
		this.approved = false;
		swinLine = null;
	}

	public int findByName(String name){
		for (int i = 0; i < pictureList.size(); i++) {
			if(pictureList.get(i).getName()== name){
				return i;
			}
		}
		return -1;
	}

	public int findPosById(int id){
		for (int i = 0; i < pictureList.size(); i++) {
			if(pictureList.get(i).getId() == id)
				return i;
		}
		return -1; 
	}

	public Stereotype findById(int id){
		for (int i = 0; i < pictureList.size(); i++) {
			if(pictureList.get(i).getId() == id)
				return pictureList.get(i);
		}
		return null; 
	}

	public boolean findByIdStereotype(int id){
		boolean found = false;
		for (int i = 0; i < pictureList.size(); i++) {
			if(pictureList.get(i).getId() == id)
				found = true;
		}
		return found; 
	}

	public int deleteStereotype(String pName){
		int pos = findByName(pName);
		pictureList.remove(pos);
		return pos;
	}
	public void deleteStereotypeById(int id){
		for (int i = 0; i < pictureList.size(); i++) {
			if(pictureList.get(i).getId() == id){
				pictureList.remove(i);
				break;
			}
		}
	}

	public void hideStereotypeById(int id){
		for (int i = 0; i < pictureList.size(); i++) {
			if(pictureList.get(i).getId() == id){
				pictureList.get(i).setVisible(false);
				break;
			}
		}
	}

	public void addStereotype(Stereotype pStereotype){
		pictureList.add(pStereotype);
	}


	public void insertStereotype(int pIndex,Stereotype pStereotype){
		pictureList.add(pIndex, pStereotype);
	}

	public Stereotype getStereotype(int pIndex){
		return pictureList.get(pIndex);
	}

	public boolean isEmpty(){
		return	pictureList.isEmpty();
	}


	public void deleteStereotype(int pIndex){
		pictureList.remove(pIndex);
	}

	public int getLength(){
		return pictureList.size();
	}

	public void replaceStereotype(int pIndex,Stereotype pStereotype){
		pictureList.set(pIndex, pStereotype);
	}

	public ArrayList<Stereotype> getPictureList() {
		return pictureList;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getObjectParent() {
		return objectParent;
	}

	public void setObjectParent(int objectParent) {
		this.objectParent = objectParent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Rectangle getDiagramSize() {
		return diagramSize;
	}

	public void setDiagramSize(Rectangle diagramSize) {
		this.diagramSize = diagramSize;
	}

	public Point getMaxScrollPoint(){
		int xMax = 0;
		int yMax = 0;
		if(pictureList.size() > 0){
			for (int i = 0; i < pictureList.size(); i++) {
				if((int)pictureList.get(i).getInicialPoint().getX() + pictureList.get(i).getWidth() > xMax)
					xMax = (int)pictureList.get(i).getInicialPoint().getX() + pictureList.get(i).getWidth();
				if((int)pictureList.get(i).getInicialPoint().getY() + pictureList.get(i).getHeigth() > yMax)
					yMax = (int)pictureList.get(i).getInicialPoint().getY() + pictureList.get(i).getHeigth();
			}
			return new Point(xMax,yMax);
		}
		else 
			return new Point(2400,1400);
	}

	public void setGroupSelectionToStereotypes(Rectangle selectedArea){
		int count = 0;
		int pos = 0;
		for(int i = 0 ; i < getLength(); i++){
			if(getStereotype(i).isStereotypeContentInRectangle(selectedArea) && getStereotype(i).isVisible()){
				getStereotype(i).setSelected(true);
				count ++;
				pos = i;
			}
		}
		if(count == 1){//seleccione un solo elemento
			if(pos != getLength()){//no ese el ultimo
				Stereotype stereotype = getStereotype(pos);
				deleteStereotype(pos);
				addStereotype(stereotype);
				getStereotype(getLength()-1).setSelected(true);
			}
		}
	}

	public void setSimpleSelectionToStereotype(Point pointClick){
		int pos = -1;
		for(int i = 0 ; i < getLength(); i++){
			if(getStereotype(i).isPointContentInStereotype(pointClick)){
				pos = i;
			}
		}
		if(pos != -1){//encontre
			if(pos != getLength()){// no es el ultimo
				Stereotype stereotype = getStereotype(pos);
				deleteStereotype(pos);
				addStereotype(stereotype);
				getStereotype(getLength()-1).setSelected(true);
			}else{
				getStereotype(pos).setSelected(true);
			}
		}
	}

	public Stereotype getSelectedStereotype(Point point){
		int pos = -1;
		for(int i = 0 ; i < getLength(); i++){
			if(getStereotype(i).isPointContentInStereotype(point)){
				pos = i;
			}
		}
		if(pos != -1)//encontre
			return getStereotype(pos);
		else
			return null;
	}
	/**
	 * 
	 * @return el stereotipo seleccionado si es el unico que lo esta.
	 */
	public Stereotype getSelectedStereotype(){
		int pos = -1;
		int count = 0;
		for(int i = 0 ; i < getLength(); i++){
			if(getStereotype(i).isSelected()){
				pos = i;
				count ++;
			}
		}
		if(pos != -1 && count == 1)//encontre y es unico
			return getStereotype(pos);
		else
			return null;
	}

	public void unselectAllStereotype(){
		for(int i = 0 ; i < getLength(); i++)
			getStereotype(i).setSelected(false);
	}

	public void selectAllStereotype(){
		for(int i = 0 ; i < getLength(); i++)
			getStereotype(i).setSelected(true);
	}

	public ArrayList<Stereotype> getSelectedStereotypes(){
		ArrayList<Stereotype> listSelected = new ArrayList<Stereotype>();
		for(int i = 0; i< getLength(); i++){
			if(getStereotype(i).isSelected()){
				listSelected.add(getStereotype(i));
			}
		}
		return listSelected;
	}

	public boolean isStereotypeInList(Stereotype stereotype, ArrayList<Stereotype> list){
		boolean content = false;
		for(int i = 0; i< list.size(); i++){
			if(list.get(i) == stereotype){
				content = true;
				break;
			}
		}
		return content;
	}

	public Rectangle getBoundsRectOfList(ArrayList<Stereotype> listStereotypes){
		int xMin = 10000;
		int yMin = 10000;
		int widthMax = 0;
		int heigthMax = 0;
		for(int i =0;i< listStereotypes.size();i++){
			if(listStereotypes.get(i).getInicialPoint().x < xMin)
				xMin = listStereotypes.get(i).getInicialPoint().x;
			if(listStereotypes.get(i).getInicialPoint().y < yMin)
				yMin = listStereotypes.get(i).getInicialPoint().y;
			if((listStereotypes.get(i).getInicialPoint().x + listStereotypes.get(i).getWidth()) > widthMax)
				widthMax = (listStereotypes.get(i).getInicialPoint().x + listStereotypes.get(i).getWidth());
			if((listStereotypes.get(i).getInicialPoint().y + listStereotypes.get(i).getHeigth()) > heigthMax)
				heigthMax = (listStereotypes.get(i).getInicialPoint().y + listStereotypes.get(i).getHeigth());
		}
		return new Rectangle(xMin,yMin,widthMax - xMin,heigthMax - yMin);
	}

	public Double getZoomFactor() {
		return zoomFactor;
	}
//	16/1/08
	public void setZoomFactor(Double pZoomFactor) {
		if ( pZoomFactor > 0.0 /*&& pZoomFactor < 2.0*/ ) {
			this.zoomFactor = pZoomFactor;
		}
	}

	public Rectangle getRectVisible() {
		return rectVisible;
	}

	public void setRectVisible(Rectangle rectVisible) {
		this.rectVisible = rectVisible;
	}

	public Color getBackground() {
		return Background;
	}

	public void setBackground(Color background) {
		Background = background;
	}
	/*
	 * metodo para calcular la relacion entre figuras seleccionadas
	 */
	public ArrayList<Point> calculateRelationship(){
		Point relation = new Point();
		ArrayList<Point> listRelation = new ArrayList<Point>();
		ArrayList<Stereotype> listSelected = new ArrayList<Stereotype>();
		listSelected = getSelectedStereotypes();
		for (int i = 0; i < listSelected.size(); i++) {
			relation = new Point(listSelected.get(i).getInicialPoint().x - getInicialXOfSelection(), listSelected.get(i).getInicialPoint().y - getInicialYOfSelection());
			listRelation.add(relation);
		}
		return listRelation;
	}
	/*
	 * metodo para calcular la relacion entre figuras seleccionadas
	 */

	/*
	 * metodos para calcular la x y la y inicial de la seleccion
	 */
	public int getInicialXOfSelection(){
		int x = 10000;
		for (int i = 0; i < pictureList.size(); i++) {
			if(pictureList.get(i).isSelected()){
				if(x > pictureList.get(i).getInicialPoint().x){
					x = pictureList.get(i).getInicialPoint().x;
				}
			}
		}
		return x;
	}

	public int getInicialYOfSelection(){
		int y = 10000;
		for (int i = 0; i < pictureList.size(); i++) {
			if(pictureList.get(i).isSelected()){
				if(y > pictureList.get(i).getInicialPoint().y)
					y = pictureList.get(i).getInicialPoint().y;
			}
		}
		return y;
	}
	/*
	 * metodos para calcular la x y la y inicial de la seleccion
	 */

	/*
	 * metodos para el calculo del width y el height del rect de seleccion
	 */
	public int getMaxWidth(){
		int xMax = 0;
		for (int i = 0; i < pictureList.size(); i++) {
			if(pictureList.get(i).isSelected()){
				if((int)pictureList.get(i).getInicialPoint().getX() + pictureList.get(i).getWidth() > xMax){
					xMax = (int)pictureList.get(i).getInicialPoint().getX() + pictureList.get(i).getWidth();
				}
			}
		}
		int xMin = getInicialXOfSelection();
		return xMax - xMin;
	}

	public int getMaxHeigth(){
		int yMax = 0;
		for (int i = 0; i < pictureList.size(); i++) {
			if(pictureList.get(i).isSelected()){
				if((int)pictureList.get(i).getInicialPoint().getY() + pictureList.get(i).getHeigth() > yMax){
					yMax = (int)pictureList.get(i).getInicialPoint().getY() + pictureList.get(i).getHeigth();
				}
			}
		}
		int yMin = getInicialYOfSelection();
		return yMax - yMin;
	}
	/*
	 * metodos para el calculo del width y el height del rect de seleccion
	 */

	public ArrayList<RedoAction> getListRedo() {
		return listRedo;
	}

	public void setListRedo(ArrayList<RedoAction> listRedo) {
		this.listRedo = listRedo;
	}

	public ArrayList<UndoAction> getListUndo() {
		return listUndo;
	}

	public void setListUndo(ArrayList<UndoAction> listUndo) {
		this.listUndo = listUndo;
	}

	public void setPictureList(ArrayList<Stereotype> pictureList) {
		this.pictureList = pictureList;
	}

	public int getId() {
		return id;
	}

	public int getPosOfStereotype(Stereotype stereotype){
		for (int i = 0; i < getLength(); i++) {
			if(getPictureList().get(i) == stereotype){
				return i;
			}
		}
		return -1;
	}  

	public int countStereotypesByType(String type){
		int count = 0;
		for(int i = 0; i < getPictureList().size(); i++){
			if(getPictureList().get(i).getType() == type){
				count++;
			}
		}
		return count;
	}

	public boolean findNameByTypeId(String pName, String pType, int id){
		boolean found = false;
		/*if(pType != "BaseDato" && pType != "Decision" && pType != "Nota" && pType != "EITiempo" &&
				pType != "EIMensaje" && pType != "EInicial" && pType != "EFinal"){*/
		for(int i = 0; i < getPictureList().size(); i++){
			if((getStereotype(i).getName().equals(pName)) && (getStereotype(i).getType().equals(pType)) && (getStereotype(i).getId() != id)){
				found = true;
				break;
			}
		}
		//}
		return found;
	}

	public boolean findNameByType(String pName, String pType){
		boolean found = false;
		/*if(pType != "BaseDato" && pType != "Decision" && pType != "Nota" && pType != "EITiempo" &&
				pType != "EIMensaje" && pType != "EInicial" && pType != "EFinal"){*/
		for(int i = 0; i < getPictureList().size(); i++){
			if((getStereotype(i).getName().equals(pName)) && (getStereotype(i).getType().equals(pType))){
				found = true;
				break;
			}
		}
		//}
		return found;
	}

	public void setNameById(int id, String pName){
		for (int i = 0; i < pictureList.size(); i++) {
			if(pictureList.get(i).getId() == id)
				pictureList.get(i).setName(pName);
		}
	}

	public void setDescriptionById(int id, String pDesc){
		for (int i = 0; i < pictureList.size(); i++) {
			if(pictureList.get(i).getId() == id)
				pictureList.get(i).setDescription(pDesc);
		}
	}

	public void setWidthById(int id, int width){
		for (int i = 0; i < pictureList.size(); i++) {
			if(pictureList.get(i).getId() == id)
				pictureList.get(i).setWidth(width);
		}
	}

	public void setHeightById(int id, int heigth){
		for (int i = 0; i < pictureList.size(); i++) {
			if(pictureList.get(i).getId() == id)
				pictureList.get(i).setHeigth(heigth);
		}
	}

	public boolean isExecuteHeight() {
		return executeHeight;
	}

	public void setExecuteHeight(boolean executeHeight) {
		this.executeHeight = executeHeight;
	}

	public boolean isExecuteWidth() {
		return executeWidth;
	}

	public void setExecuteWidth(boolean executeWidth) {
		this.executeWidth = executeWidth;
	}

	public boolean isExecute() {
		return executeBoth;
	}

	public void setExecute(boolean execute) {
		this.executeBoth = execute;
	}

	public int findIdByName(String pName){
		for (int i = 0; i < pictureList.size(); i++) {
			if(pictureList.get(i).getName().equals(pName))
				return pictureList.get(i).getId();
		}
		return -1; 
	}

	public void deleteSelectedStereotypes(){
		for (Stereotype stereotype : pictureList) {
			if(stereotype.isSelected()){
				pictureList.remove(stereotype);
			}
		}
	}

	public ArrayList<Stereotype> findNoteInList(ArrayList<Stereotype> array){
		ArrayList<Stereotype> arrayStereotypes = new ArrayList<Stereotype>();
		for (Stereotype stereotype : array) {
			if(stereotype.getType() == "Nota"){
				arrayStereotypes.add(stereotype);
			}
		}
		return arrayStereotypes;
	}   
	public Point getMinScrollPoint(){
		double xMin = 100000;
		double yMin = 100000;
		if(pictureList.size() > 0){
			for (int i = 0; i < pictureList.size(); i++) {
				if(pictureList.get(i).getInicialPoint().getX() < xMin)
					xMin = pictureList.get(i).getInicialPoint().getX();
				if(pictureList.get(i).getInicialPoint().getY() < yMin)
					yMin = pictureList.get(i).getInicialPoint().getY();
			}
			return new Point(new Double(xMin).intValue(),new Double(yMin).intValue());
		}
		else 
			return null;
	}
	public Stereotype findStereotypeByName(String pName){
		for (int i = 0; i < pictureList.size(); i++) {
			if(pictureList.get(i).getName().equals(pName)){
				return pictureList.get(i);
			}
		}
		return null;
	}

	public boolean isPointContentInPolyline(Relation relation, Point pPoint ) {
		boolean content = false;
		for (int i = 0; i < relation.getArrayLines().size(); i++) {
			if((relation.getArrayLines().get(i).getX1() - 2 <= pPoint.getX() && relation.getArrayLines().get(i).getX1() + 2 >= pPoint.getX()) && ((relation.getArrayLines().get(i).getY1() <= pPoint.getY() && relation.getArrayLines().get(i).getY2() >= pPoint.getY())
					|| (relation.getArrayLines().get(i).getY1() >= pPoint.getY() && relation.getArrayLines().get(i).getY2() <= pPoint.getY()))){
				content = true;
				break;
			}
			else if((relation.getArrayLines().get(i).getY1() - 2 <= pPoint.getY() && relation.getArrayLines().get(i).getY1() + 2 >= pPoint.getY()) && ((relation.getArrayLines().get(i).getX1() <= pPoint.getX() && relation.getArrayLines().get(i).getX2() >= pPoint.getX())
					|| (relation.getArrayLines().get(i).getX1() >= pPoint.getX() && relation.getArrayLines().get(i).getX2() <= pPoint.getX()))){
				content = true;
				break;
			}
			/*if(((pPoint.x <= relation.getArraypoints().get(i).x + 2 && pPoint.x >= relation.getArraypoints().get(i).x - 2) 
		    && (pPoint.y >= relation.getArraypoints().get(i).y - 2 && pPoint.y <= relation.getArraypoints().get(i).y + 2))){
		content = true;
		break;
	    }*/ 
		}
		return content;
	}

	public Relation getSelectedRelation(Point point){
		Relation relation = null;
		for (Stereotype stereotype : getPictureList()) {
			for (Relation currentRelation : stereotype.getRelatedStereotypes()) {
				if(isPointContentInPolyline(currentRelation, point)){
					relation = currentRelation;
				}
			}
		}
		return relation;
	}

	public void setRelationSelected(Relation relation){
		if(relation != null)
			relation.setSelected(true);
	}

	public void unselectAllRelations(){
		for (Stereotype stereotype : getPictureList()) {
			for (Relation currentRelation : stereotype.getRelatedStereotypes()) {
				currentRelation.setSelected(false);
			}
		}
	}

	public ArrayList<Relation> getSelectedRelations(){
		ArrayList<Relation> listRelation = new ArrayList<Relation>();
		for (Stereotype stereotype : getPictureList()) {
			for (Relation relation : stereotype.getRelatedStereotypes()) {
				if(relation.isSelected()){
					listRelation.add(relation); 
				}
			}
		}
		return listRelation;
	}

	public void deleteRelations(ArrayList<Relation> listRelation){
		for (Stereotype stereotype : getPictureList()) {
			for (Relation relation : listRelation) {
				for (int i = 0; i < stereotype.getRelatedStereotypes().size(); i++) {
					Relation relationStereotype = stereotype.getRelatedStereotypes().get(i);
					if(relation.getId() == relationStereotype.getId()){
						stereotype.getRelatedStereotypes().remove(i); 
					}
				}
			}
		}
	}

	public ArrayList<Stereotype> getStereotypesContent(ArrayList<Relation> list){
		ArrayList<Stereotype> arrayStereotype = new ArrayList<Stereotype>();
		boolean flag = false;
		for (Stereotype stereotype : getPictureList()) {
			for (Relation relation : list) {
				for (Relation currentRelation : stereotype.getRelatedStereotypes()) {
					if(relation.getId() == currentRelation.getId()){
						arrayStereotype.add(stereotype);
						flag = true;
						break;
					}
				}
				if(flag){
					flag = false;
					break;
				}
			}
		}
		return arrayStereotype;
	}

	public Stereotype getStereotypeParent(Relation relation){
		Stereotype returnStereotype = null;
		for (Stereotype stereotype : getPictureList()) {
			if(relation.getParentId() == stereotype.getId()){
				returnStereotype = stereotype;
				break;
			}
		}
		return returnStereotype;
	}

	public Point getMinRelationPoint(){
		int xMin = 0;
		int yMin = 0;
		if(pictureList.size() > 0){
			for (int i = 0; i < pictureList.size(); i++) {
				for (Relation relation : pictureList.get(i).getRelatedStereotypes()) {
					for (Point point : relation.getArraypoints()) {
						if(point.getX() < xMin)
							xMin = (int)point.getX();
						if(point.getY() < yMin)
							yMin = (int)point.getY();
					}
				}
			}
			return new Point(xMin,yMin);
		}
		else 
			return new Point(0,0);
	}

	public Point getMaxRelationPoint(){
		int xMax = 0;
		int yMax = 0;
		if(pictureList.size() > 0){
			for (int i = 0; i < pictureList.size(); i++) {
				for (Relation relation : pictureList.get(i).getRelatedStereotypes()) {
					for (Point point : relation.getArraypoints()) {
						if(point.getX() > xMax)
							xMax = (int)point.getX();
						if(point.getY() > yMax)
							yMax = (int)point.getY();
					}
				}
			}
			return new Point(xMax,yMax);
		}
		else 
			return new Point(2400,1400);
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public SwinLine getSwinLine() {
		return swinLine;
	}

	public void setSwinLine(SwinLine swinLine) {
		this.swinLine = swinLine;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isSaveStatus() {
		return saveStatus;
	}

	public void setSaveStatus(boolean saveStatus) {
		this.saveStatus = saveStatus;
	}
	
}
