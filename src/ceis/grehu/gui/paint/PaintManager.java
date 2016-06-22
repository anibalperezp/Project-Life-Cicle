package ceis.grehu.gui.paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import ProcessGraphServer.Domain.TipoDiagramaEnum;
import ProcessGraphServer.Domain.TipoStereotipoEnum;
import ProcessGraphServer.service.DiagramaService;
import ProcessGraphServer.service.RelacionService;
import ProcessGraphServer.service.StereotypeService;
import ProcessGraphServer.vo.AnchosCalleVO;
import ProcessGraphServer.vo.CalleVO;
import ProcessGraphServer.vo.DiagramaVO;
import ProcessGraphServer.vo.LineasRelacionVO;
import ProcessGraphServer.vo.NombresCalleVO;
import ProcessGraphServer.vo.PropiedadesStereotypeVO;
import ProcessGraphServer.vo.PuntosRelacionVO;
import ProcessGraphServer.vo.RectangulosRelacionVO;
import ProcessGraphServer.vo.RelacionVO;
import ProcessGraphServer.vo.StereotypeVO;
import ceis.grehu.gui.PanelOverView;
import ceis.grehu.gui.ProcessGraphDocking;
import cujae.ceis.grehu.ServiceLocator;


public class PaintManager {
	/**
	 * Esta clase esta inicializada en ProcessGraphDocking
	 */

	private ArrayList<Diagram> diagramList;

	private Rectangle resultResizeRect = null;

	private boolean createPanel = false;

	//private boolean scrollToVisibleRect = false;

	private ArrayList<Stereotype> copyList = null;

	private ArrayList<Stereotype> cutList = null;

	private ArrayList<Point> arrayPointsToInsert;
	
	public static ServiceLocator serviceLocator = null;
	
	public static DiagramaService diagramaService = null;
	
	public static StereotypeService stereotypeService = null;
	
	public static RelacionService relacionService = null;
	
	private int exceptionStatus = 0;
	
	private Vector<Integer> listPararell = null;  //  @jve:decl-index=0:

	public PaintManager() {
		diagramList = new ArrayList<Diagram>();
		resultResizeRect = null;
		//scrollToVisibleRect = false;
		createPanel = false;
		copyList = new ArrayList<Stereotype>();
		cutList = new ArrayList<Stereotype>();
		arrayPointsToInsert = new ArrayList<Point>();
		//try {
			serviceLocator = ServiceLocator.instance();
			diagramaService = serviceLocator.getDiagramaService();
			stereotypeService = serviceLocator.getStereotypeService();
			relacionService = serviceLocator.getRelacionService();
		//} catch (Exception e) {
			exceptionStatus = 1;
		//}
		listPararell = new Vector<Integer>();
	}

	public int findByName(String name) {
		for (int i = 0; i < diagramList.size(); i++) {
			if (diagramList.get(i).getName() == name) {
				return i;
			}
		}
		return -1;
	}

	public Diagram getDiagramByName(String name) {
		for (int i = 0; i < diagramList.size(); i++) {
			if (diagramList.get(i).getName().equals(name)) {
				return diagramList.get(i);
			}
		}
		return null;
	}

	public int deleteDiagram(String pName) {
		int pos = findByName(pName);
		diagramList.remove(pos);
		return pos;
	}

	public void addDiagram(Diagram pDiagram) {
		diagramList.add(pDiagram);
	}

	public void insertDiagram(int pIndex, Diagram pDiagram) {
		diagramList.add(pIndex, pDiagram);
	}

	public Diagram getDiagram(int pIndex) {
		return diagramList.get(pIndex);
	}

	public boolean isEmpty() {
		return diagramList.isEmpty();
	}

	public void deleteDiagram(int pIndex) {
		diagramList.remove(pIndex);
	}

	public int getLength() {
		return diagramList.size();
	}

	public void replaceDiagram(int pIndex, Diagram pDiagram) {
		diagramList.set(pIndex, pDiagram);
	}

	public ArrayList<Diagram> getDiagramList() {
		return diagramList;
	}

	public void PaintSelectArea(Graphics pGraphics, Point pPointInitial, Point pPointMoved) {
		Color colorOld = pGraphics.getColor();

		Graphics2D g2 = (Graphics2D) pGraphics;// convertirlo a 2D

		g2.setColor(new Color(0, 0, 0));
		float dash1[] = { 5.0f };
		BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
		g2.setStroke(dashed);

		if ((pPointInitial.x >= pPointMoved.x)
				&& (pPointInitial.y >= pPointMoved.y)) {
			g2.draw(new Rectangle2D.Double(pPointMoved.x, pPointMoved.y,
					pPointInitial.x - pPointMoved.x, pPointInitial.y
					- pPointMoved.y));
		}
		if ((pPointInitial.x <= pPointMoved.x)
				&& (pPointInitial.y <= pPointMoved.y)) {

			g2.draw(new Rectangle2D.Double(pPointInitial.x, pPointInitial.y,
					pPointMoved.x - pPointInitial.x, pPointMoved.y
					- pPointInitial.y));
		}
		if ((pPointInitial.x >= pPointMoved.x)
				&& (pPointInitial.y <= pPointMoved.y)) {

			g2.draw(new Rectangle2D.Double(pPointMoved.x, pPointInitial.y,
					pPointInitial.x - pPointMoved.x, pPointMoved.y
					- pPointInitial.y));
		}
		if ((pPointInitial.x <= pPointMoved.x)
				&& (pPointInitial.y >= pPointMoved.y)) {

			g2.draw(new Rectangle2D.Double(pPointInitial.x, pPointMoved.y,
					pPointMoved.x - pPointInitial.x, pPointInitial.y
					- pPointMoved.y));
		}


		pGraphics.setColor(colorOld);
	}

	public Rectangle getSelectedArea(Point pPointPressed, Point pPointReleased) {
		if ((pPointPressed.x >= pPointReleased.x)
				&& (pPointPressed.y >= pPointReleased.y)) {
			return new Rectangle(pPointReleased.x, pPointReleased.y,
					pPointPressed.x - pPointReleased.x, pPointPressed.y
					- pPointReleased.y);
		}
		if ((pPointPressed.x <= pPointReleased.x)
				&& (pPointPressed.y <= pPointReleased.y)) {

			return new Rectangle(pPointPressed.x, pPointPressed.y,
					pPointReleased.x - pPointPressed.x, pPointReleased.y
					- pPointPressed.y);
		}
		if ((pPointPressed.x >= pPointReleased.x)
				&& (pPointPressed.y <= pPointReleased.y)) {

			return new Rectangle(pPointReleased.x, pPointPressed.y,
					pPointPressed.x - pPointReleased.x, pPointReleased.y
					- pPointPressed.y);
		}
		if ((pPointPressed.x <= pPointReleased.x)
				&& (pPointPressed.y >= pPointReleased.y)) {

			return new Rectangle(pPointPressed.x, pPointReleased.y,
					pPointReleased.x - pPointPressed.x, pPointPressed.y
					- pPointReleased.y);
		}
		return null;
	}


	public Diagram getActiveDiagram(){
		int i = 0;
		while (i < diagramList.size()) {
			if(!diagramList.get(i).isSelected())
				i++;
			else
				return diagramList.get(i);
		}
		return null;
	}

	public void paintBackGround(Graphics g,int width, int height){//multiplicar al width y el height por el factor de zoom
		Color colorOld = g.getColor();
		//g.setColor(Color.LIGHT_GRAY);
		g.setColor(new Color(224,233,251));//azul
		for(int i = 0; i < width; i += 15){
			g.drawLine(i, 0, i, height);
		}
		for(int i = 0; i < height; i += 15){
			g.drawLine(0, i, width, i);
		}

		g.setColor(colorOld);
	}

	public void PaintBoundStereotype(Graphics g ,Stereotype stereotype, Point pointMove, int xDifference, int yDifference){
		if(stereotype.isVisible() && stereotype != null){
			Point pointToPaint = new Point(pointMove.x - xDifference, pointMove.y - yDifference);
			Color oldColor = g.getColor();
			g.setColor(Color.BLUE);
			if(stereotype.getType() == "BaseDato"){
				float percent = stereotype.getHeigth()*85/100;
				int trace = stereotype.getHeigth() - (int)percent;
				g.drawRect(pointToPaint.x - 3, pointToPaint.y - trace - 3, stereotype.getWidth() + 6, stereotype.getHeigth() + 2 * trace + 6);
			}
			if(stereotype.getType() == "Macro"){
				g.drawRect(pointToPaint.x - 3, pointToPaint.y - 3, stereotype.getWidth() + 6 + 26, stereotype.getHeigth() + 6);
			}
			else{
				g.drawRect(pointToPaint.x - 3, pointToPaint.y - 3, stereotype.getWidth() + 6, stereotype.getHeigth() + 6);
			}
			g.setColor(oldColor);
		}
	}

	public boolean isPointContentInRectangle(Point pPoint,Rectangle pRectangle) {
		boolean content = false;
		if((pPoint.x >= pRectangle.x) && (pPoint.x <= (pRectangle.x + pRectangle.width)) 
				&& (pPoint.y >= pRectangle.y) && (pPoint.y <= (pRectangle.y + pRectangle.height))){
			content = true;
		}
		return content;
	}

	/**
	 * @param pListRect
	 * @param pointMove
	 * @return pos
	 * 0-northWest;1-north;2-northEast;3-east;4-southEast;5-south;6-southWest;7-west.
	 */
	public int getPosOfSelectionRect(ArrayList<Rectangle> pListRect,Point pointMove){
		for(int i = 0; i< pListRect.size();i++){
			if(isPointContentInRectangle(pointMove, pListRect.get(i))){
				return i;
			}
		}
		return -1;
	}

	/**
	 * 
	 * @param stereotypeToResize
	 * @param posResize
	 * 0-northWest;1-north;2-northEast;3-east;4-southEast;5-south;6-southWest;7-west.
	 * @param pointMoving
	 */
	public void paintResizingStereotype(Graphics g, Stereotype stereotypeToResize, int posResize , Point pointMoving/*, int height*/){
		if(stereotypeToResize.isVisible()){
			Color colorOld = g.getColor();
			g.setColor(Color.BLUE);
			String text = "";
			if(stereotypeToResize.getType() != "Nota")
				text = stereotypeToResize.getName();
			else
				text = stereotypeToResize.getDescription();

			String[] a = text.split("\n");
			FontMetrics fm = g.getFontMetrics();
			int heigth = new Double(fm.getLineMetrics(a[0], g).getHeight()).intValue();
			int count = 0;
			int max = 0;
			for (int i = 0; i < a.length; i++) {
				int l = fm.charsWidth( a[i].toCharArray(), 0 , a[i].length());
				if(l > max){
					max = l;
				}
				count++;
			}
			int result = heigth*count + 15 + 30;
			if(stereotypeToResize.getType() != "EITiempo" && stereotypeToResize.getType() != "EIMensaje" 
				&& stereotypeToResize.getType() != "EInicial" && stereotypeToResize.getType() != "EFinal"
					&& stereotypeToResize.getType() != "Decision" && stereotypeToResize.getType() != "BaseDato" && stereotypeToResize.getType() != "Nota"
						&& stereotypeToResize.getType() != "Macro"){
				if(posResize == 0){
					int w = stereotypeToResize.getFinalPoint().x - pointMoving.x;
					int h = stereotypeToResize.getFinalPoint().y - pointMoving.y;
					if(w > stereotypeToResize.getDefaultSize().width && h > stereotypeToResize.getDefaultSize().height && max < w){

						g.drawRect(pointMoving.x-3, pointMoving.y -3, w + 6, h+6);
						resultResizeRect = new Rectangle(pointMoving.x, pointMoving.y, w, h);
					}
				}
				if(posResize == 1){
					int w = stereotypeToResize.getWidth();
					int h = stereotypeToResize.getFinalPoint().y - pointMoving.y;
					if(h > stereotypeToResize.getDefaultSize().height){
						g.drawRect(stereotypeToResize.getInicialPoint().x-3, pointMoving.y-3, w+6, h+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, pointMoving.y, w, h);
					}
				}
				if(posResize == 2){
					int w = pointMoving.x - stereotypeToResize.getInicialPoint().x;
					int h = stereotypeToResize.getFinalPoint().y - pointMoving.y;
					if(w > stereotypeToResize.getDefaultSize().width && h > stereotypeToResize.getDefaultSize().height && max < w){

						g.drawRect(stereotypeToResize.getInicialPoint().x-3, pointMoving.y-3, w+6, h+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, pointMoving.y, w, h);
					}
				}
				if(posResize == 3){
					int w = pointMoving.x - stereotypeToResize.getInicialPoint().x;
					int h = stereotypeToResize.getHeigth();
					if(w > stereotypeToResize.getDefaultSize().width && max < w){

						g.drawRect(stereotypeToResize.getInicialPoint().x-3, stereotypeToResize.getInicialPoint().y-3, w+6, h+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, stereotypeToResize.getInicialPoint().y, w, h);
					}
				}
				if(posResize == 4){
					int w = pointMoving.x - stereotypeToResize.getInicialPoint().x;
					int h = pointMoving.y - stereotypeToResize.getInicialPoint().y;
					if(w > stereotypeToResize.getDefaultSize().width && h > stereotypeToResize.getDefaultSize().height && max < w){

						g.drawRect(stereotypeToResize.getInicialPoint().x-3, stereotypeToResize.getInicialPoint().y-3, w+6, h+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, stereotypeToResize.getInicialPoint().y, w, h);
					}
				}
				if(posResize == 5){//falta a partir de aqui
					int w = stereotypeToResize.getWidth();
					int h = pointMoving.y - stereotypeToResize.getInicialPoint().y;
					if(h > stereotypeToResize.getDefaultSize().height){
						g.drawRect(stereotypeToResize.getInicialPoint().x-3, stereotypeToResize.getInicialPoint().y-3, w+6, h+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, stereotypeToResize.getInicialPoint().y, w, h);
					}
				}
				if(posResize == 6){
					int w = stereotypeToResize.getFinalPoint().x - pointMoving.x;
					int h = pointMoving.y - stereotypeToResize.getInicialPoint().y;
					if(w > stereotypeToResize.getDefaultSize().width && h > stereotypeToResize.getDefaultSize().height && max < w){

						g.drawRect(pointMoving.x-3, stereotypeToResize.getInicialPoint().y-3, w+6, h+6);
						resultResizeRect = new Rectangle(pointMoving.x, stereotypeToResize.getInicialPoint().y, w, h);
					}
				}
				if(posResize == 7){
					int w = stereotypeToResize.getFinalPoint().x - pointMoving.x;
					int h = stereotypeToResize.getHeigth();
					if(w > stereotypeToResize.getDefaultSize().width && max < w){

						g.drawRect(pointMoving.x-3, stereotypeToResize.getInicialPoint().y-3, w+6, h+6);
						resultResizeRect = new Rectangle(pointMoving.x, stereotypeToResize.getInicialPoint().y, w, h);

					}
				}
			}
			else if(stereotypeToResize.getType() == "Macro" ){

				if(posResize == 0){
					int w = stereotypeToResize.getFinalPoint().x + 26 - pointMoving.x;
					int h = stereotypeToResize.getFinalPoint().y - pointMoving.y;
					if(w > stereotypeToResize.getDefaultSize().width && h > stereotypeToResize.getDefaultSize().height && max < w){
						g.drawRect(pointMoving.x-3, pointMoving.y -3, w + 6, h+6);
						resultResizeRect = new Rectangle(pointMoving.x, pointMoving.y, w, h);
					}
				}
				if(posResize == 1){
					int w = stereotypeToResize.getWidth() + 26;
					int h = stereotypeToResize.getFinalPoint().y - pointMoving.y;
					if(h > stereotypeToResize.getDefaultSize().height){
						g.drawRect(stereotypeToResize.getInicialPoint().x-3, pointMoving.y-3, w+6, h+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, pointMoving.y, w, h);
					}
				}
				if(posResize == 2){
					int w = pointMoving.x - stereotypeToResize.getInicialPoint().x;
					int h = stereotypeToResize.getFinalPoint().y - pointMoving.y;
					if(w > stereotypeToResize.getDefaultSize().width && h > stereotypeToResize.getDefaultSize().height && max < w){
						g.drawRect(stereotypeToResize.getInicialPoint().x-3, pointMoving.y-3, w+6, h+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, pointMoving.y, w, h);
					}
				}
				if(posResize == 3){
					int w = pointMoving.x - stereotypeToResize.getInicialPoint().x;
					int h = stereotypeToResize.getHeigth();
					if(w > stereotypeToResize.getDefaultSize().width && max < w){
						g.drawRect(stereotypeToResize.getInicialPoint().x-3, stereotypeToResize.getInicialPoint().y-3, w+6, h+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, stereotypeToResize.getInicialPoint().y, w, h);
					}
				}
				if(posResize == 4){
					int w = pointMoving.x - stereotypeToResize.getInicialPoint().x;
					int h = pointMoving.y - stereotypeToResize.getInicialPoint().y;
					if(w > stereotypeToResize.getDefaultSize().width && h > stereotypeToResize.getDefaultSize().height && max < w){
						g.drawRect(stereotypeToResize.getInicialPoint().x-3, stereotypeToResize.getInicialPoint().y-3, w+6, h+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, stereotypeToResize.getInicialPoint().y, w, h);
					}
				}
				if(posResize == 5){//falta a partir de aqui
					int w = stereotypeToResize.getWidth() + 26;
					int h = pointMoving.y - stereotypeToResize.getInicialPoint().y;
					if(h > stereotypeToResize.getDefaultSize().height){
						g.drawRect(stereotypeToResize.getInicialPoint().x-3, stereotypeToResize.getInicialPoint().y-3, w+6, h+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, stereotypeToResize.getInicialPoint().y, w, h);
					}
				}
				if(posResize == 6){
					int w = stereotypeToResize.getFinalPoint().x + 26 - pointMoving.x;
					int h = pointMoving.y - stereotypeToResize.getInicialPoint().y;
					if(w > stereotypeToResize.getDefaultSize().width && h > stereotypeToResize.getDefaultSize().height && max < w){
						g.drawRect(pointMoving.x-3, stereotypeToResize.getInicialPoint().y-3, w+6, h+6);
						resultResizeRect = new Rectangle(pointMoving.x, stereotypeToResize.getInicialPoint().y, w, h);
					}
				}
				if(posResize == 7){
					int w = stereotypeToResize.getFinalPoint().x + 26 - pointMoving.x;
					int h = stereotypeToResize.getHeigth();
					if(w > stereotypeToResize.getDefaultSize().width && max < w){

						g.drawRect(pointMoving.x-3, stereotypeToResize.getInicialPoint().y-3, w+6, h+6);
						resultResizeRect = new Rectangle(pointMoving.x, stereotypeToResize.getInicialPoint().y, w, h);

					}
				}
			}
			else if(stereotypeToResize.getType() == "Nota" ){
				if(posResize == 0){
					int w = stereotypeToResize.getFinalPoint().x - pointMoving.x;
					int h = stereotypeToResize.getFinalPoint().y - pointMoving.y;
					if(w > stereotypeToResize.getDefaultSize().width && h > stereotypeToResize.getDefaultSize().height && max < w && result < h){
						g.drawRect(pointMoving.x-3, pointMoving.y -3, w + 6, h+6);
						resultResizeRect = new Rectangle(pointMoving.x, pointMoving.y, w, h);
					}
					else if(w > stereotypeToResize.getDefaultSize().width && h > stereotypeToResize.getDefaultSize().height && max < w && result > h){
						g.drawRect(pointMoving.x-3, pointMoving.y -3, w + 6, result+6);
						resultResizeRect = new Rectangle(pointMoving.x, pointMoving.y, w, result);
					}
					else if(w > stereotypeToResize.getDefaultSize().width && h > stereotypeToResize.getDefaultSize().height && max > w && result < h){
						g.drawRect(pointMoving.x-3, pointMoving.y -3, max + 6, h+6);
						resultResizeRect = new Rectangle(pointMoving.x, pointMoving.y, max, h);
					}
				}
				if(posResize == 1){
					int w = stereotypeToResize.getWidth();
					int h = stereotypeToResize.getFinalPoint().y - pointMoving.y;
					if(h > stereotypeToResize.getDefaultSize().height && result < h){
						g.drawRect(stereotypeToResize.getInicialPoint().x-3, pointMoving.y-3, w+6, h+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, pointMoving.y, w, h);
					}
					else if(h > stereotypeToResize.getDefaultSize().height && result > h){
						g.drawRect(stereotypeToResize.getInicialPoint().x-3, stereotypeToResize.getInicialPoint().y-3, w+6, result + 6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, stereotypeToResize.getInicialPoint().y, w, result );
					}
				}
				if(posResize == 2){
					int w = pointMoving.x - stereotypeToResize.getInicialPoint().x;
					int h = stereotypeToResize.getFinalPoint().y - pointMoving.y;
					if(w > stereotypeToResize.getDefaultSize().width && h > stereotypeToResize.getDefaultSize().height && max < w && result < h){
						g.drawRect(stereotypeToResize.getInicialPoint().x-3, pointMoving.y-3, w+6, h+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, pointMoving.y, w, h);
					}
					else if(w > stereotypeToResize.getDefaultSize().width && h > stereotypeToResize.getDefaultSize().height && max < w && result > h){
						g.drawRect(stereotypeToResize.getInicialPoint().x-3, pointMoving.y-3, w+6, result+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, pointMoving.y, w, result);
					}
					else if(w > stereotypeToResize.getDefaultSize().width && h > stereotypeToResize.getDefaultSize().height && max > w && result < h){
						g.drawRect(stereotypeToResize.getInicialPoint().x-3, pointMoving.y-3, max+6, h+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, pointMoving.y, max, h);
					}
				}

				if(posResize == 3){
					int w = pointMoving.x - stereotypeToResize.getInicialPoint().x;
					int h = stereotypeToResize.getHeigth();
					if(w > stereotypeToResize.getDefaultSize().width && max < w /*&& result < h*/){
						g.drawRect(stereotypeToResize.getInicialPoint().x-3, stereotypeToResize.getInicialPoint().y-3, w+6, h+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, stereotypeToResize.getInicialPoint().y, w, h);
					}
					else if(w > stereotypeToResize.getDefaultSize().width && max < w && result > h){
						g.drawRect(stereotypeToResize.getInicialPoint().x-3, stereotypeToResize.getInicialPoint().y-3, w+6, result+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, stereotypeToResize.getInicialPoint().y, w, result);
					}
					else if(w > stereotypeToResize.getDefaultSize().width && max > w && result < h){
						g.drawRect(stereotypeToResize.getInicialPoint().x-3, stereotypeToResize.getInicialPoint().y-3, max+6, h+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, stereotypeToResize.getInicialPoint().y, max + 2, h);
					}
				}

				if(posResize == 4){
					int w = pointMoving.x - stereotypeToResize.getInicialPoint().x;
					int h = pointMoving.y - stereotypeToResize.getInicialPoint().y;
					if(w > stereotypeToResize.getDefaultSize().width && h > stereotypeToResize.getDefaultSize().height && max < w && result < h){
						g.drawRect(stereotypeToResize.getInicialPoint().x-3, stereotypeToResize.getInicialPoint().y-3, w+6, h+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, stereotypeToResize.getInicialPoint().y, w, h);
					}
					else if(w > stereotypeToResize.getDefaultSize().width && h > stereotypeToResize.getDefaultSize().height && max < w && result > h){
						g.drawRect(stereotypeToResize.getInicialPoint().x-3, stereotypeToResize.getInicialPoint().y-3, w+6, result+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, stereotypeToResize.getInicialPoint().y, w, result);
					}
					else if(w > stereotypeToResize.getDefaultSize().width && h > stereotypeToResize.getDefaultSize().height && max > w && result < h){
						g.drawRect(stereotypeToResize.getInicialPoint().x-3, stereotypeToResize.getInicialPoint().y-3, max+6, h+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, stereotypeToResize.getInicialPoint().y, max, h);
					}
				}
				if(posResize == 5){//falta a partir de aqui
					int w = stereotypeToResize.getWidth();
					int h = pointMoving.y - stereotypeToResize.getInicialPoint().y;
					if(h > stereotypeToResize.getDefaultSize().height && result < h){
						g.drawRect(stereotypeToResize.getInicialPoint().x-3, stereotypeToResize.getInicialPoint().y-3, w+6, h+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, stereotypeToResize.getInicialPoint().y, w, h);
					}
					else if(h > stereotypeToResize.getDefaultSize().height && result > h){
						g.drawRect(stereotypeToResize.getInicialPoint().x-3, stereotypeToResize.getInicialPoint().y-3, w+6, result+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, stereotypeToResize.getInicialPoint().y, w, result);
					}
				}

				if(posResize == 6){
					int w = stereotypeToResize.getFinalPoint().x - pointMoving.x;
					int h = pointMoving.y - stereotypeToResize.getInicialPoint().y;
					if(w > stereotypeToResize.getDefaultSize().width && h > stereotypeToResize.getDefaultSize().height && max < w && result < h){
						g.drawRect(pointMoving.x-3, stereotypeToResize.getInicialPoint().y-3, w+6, h+6);
						resultResizeRect = new Rectangle(pointMoving.x, stereotypeToResize.getInicialPoint().y, w, h);
					}
					else if(w > stereotypeToResize.getDefaultSize().width && h > stereotypeToResize.getDefaultSize().height && max < w && result > h){
						g.drawRect(pointMoving.x-3, stereotypeToResize.getInicialPoint().y-3, w+6, result+6);
						resultResizeRect = new Rectangle(pointMoving.x, stereotypeToResize.getInicialPoint().y, w, result);
					}
					else if(w > stereotypeToResize.getDefaultSize().width && h > stereotypeToResize.getDefaultSize().height && max > w && result < h){
						g.drawRect(pointMoving.x-3, stereotypeToResize.getInicialPoint().y-3, max+6, h+6);
						resultResizeRect = new Rectangle(pointMoving.x, stereotypeToResize.getInicialPoint().y, max, h);
					}
				}

				if(posResize == 7){
					int w = stereotypeToResize.getFinalPoint().x - pointMoving.x;
					int h = stereotypeToResize.getHeigth();
					if(w > stereotypeToResize.getDefaultSize().width && max < w /*&& result < h*/){
						g.drawRect(pointMoving.x-3, stereotypeToResize.getInicialPoint().y-3, w+6, h+6);
						resultResizeRect = new Rectangle(pointMoving.x, stereotypeToResize.getInicialPoint().y, w, h);
					}
					else if(w > stereotypeToResize.getDefaultSize().width && max < w /*&& result > h*/){
						g.drawRect(pointMoving.x-3, stereotypeToResize.getInicialPoint().y-3, w+6, result+6);
						resultResizeRect = new Rectangle(pointMoving.x, stereotypeToResize.getInicialPoint().y, w, result);
					}
					else if(w > stereotypeToResize.getDefaultSize().width && max > w /*&& result < h*/){
						g.drawRect(stereotypeToResize.getInicialPoint().x-3, stereotypeToResize.getInicialPoint().y-3, max+6, h+6);
						resultResizeRect = new Rectangle(stereotypeToResize.getInicialPoint().x, stereotypeToResize.getInicialPoint().y, max, h);
					}
				}
			}
			stereotypeToResize.setSelected(true);	
			g.setColor(colorOld);
		}
	}

	public Rectangle getResultResizeRect() {
		return resultResizeRect;
	}

	public void setResultResizeRect(Rectangle resultResizeRect) {
		this.resultResizeRect = resultResizeRect;
	}

	public Point getMaxPoint(ArrayList<Stereotype> listSelect){
		int xMax = 0;
		int yMax = 0;
		for (int i = 0; i < listSelect.size(); i++) {
			if(listSelect.get(i).getFinalPoint().x > xMax)
				xMax = listSelect.get(i).getFinalPoint().x;
			if(listSelect.get(i).getFinalPoint().y > yMax)
				yMax = listSelect.get(i).getFinalPoint().y;
		}
		return new Point(xMax,yMax);
	}
	/**
	 * 
	 * @param recNav - Rectangulo navegador
	 * @param view - Panel de la vista en miniatura(El blanco)
	 */
	public void setSizeDiagramOverview(Rectangle recNav,PanelOverView view) {
		Diagram diagram = getActiveDiagram();
		if (diagram != null) {

			double percentWidth = ( recNav.getSize().width * 100 ) / diagram.getDiagramSize().width;			
			double percentHeight = ( recNav.getSize().height * 100 ) / diagram.getDiagramSize().height;
			int width = 0;
			int height = 0;
			double scale = 0.0;
			if ( percentWidth == percentHeight ) {
				scale = percentWidth / 100;				
			}
			else {
				if ( percentWidth < percentHeight ) {
					scale = percentWidth / 100;
				}
				else {
					scale = percentHeight / 100;
				}
			}
			view.setScale( scale );
			width = new Double( diagram.getDiagramSize().width * view.getScale() ).intValue();
			height = new Double( diagram.getDiagramSize().height * view.getScale() ).intValue();
			view.setSize( width + 1, height + 1  );
		}
	}

	public boolean isCreatePanel() {
		return createPanel;
	}

	public void setCreatePanel(boolean createPanel) {
		this.createPanel = createPanel;
	}
	/*
    public boolean isScrollToVisibleRect() {
	return scrollToVisibleRect;
    }

    public void setScrollToVisibleRect(boolean scrollToVisibleRect) {
	this.scrollToVisibleRect = scrollToVisibleRect;
    }*/
	/**
	 * metodo para pintar el rectangulo de la mult seleccion
	 */
	public void paintRectMultipleSelection(Graphics g, Point pointMoved, int xBigDifference, int yBigDifference, int width, int height){
		Point pointToPaint = new Point(pointMoved.x - xBigDifference, pointMoved.y - yBigDifference);
		Color oldColor = g.getColor();
		g.setColor(Color.BLUE);
		g.drawRect(pointToPaint.x - 3, pointToPaint.y - 3, width + 6, height + 6);
		g.setColor(oldColor);
	}

	public void unActiveAllDiagrams(){
		for (Diagram iterable_element : diagramList) {
			iterable_element.setSelected(false);
		}
	}

	public Diagram findById(int id){
		for (int i = 0; i < getLength(); i++) {
			if(getDiagramList().get(i).getId() == id)
				return getDiagramList().get(i);
		}
		return null;
	}

	public ArrayList<Stereotype> getCopyList() {
		return copyList;
	}

	public ArrayList<Stereotype> getCutList() {
		return cutList;
	}

	public void setCopyList(ArrayList<Stereotype> copyList) {
		this.copyList = copyList;
	}

	public void setCutList(ArrayList<Stereotype> cutList) {
		this.cutList = cutList;
	}

	public void hideStereotypeInDiagram(int idDiagram, Stereotype stereotype){
		for (Diagram diagram : diagramList) {
			if(diagram.getId() == idDiagram){
				stereotype.setVisible(false);
			}
		}
	}
	public boolean isRectangleContentInRectangle(Rectangle container, Rectangle content){
		if(container.x <= content.x && container.y <= content.y && 
				(container.x + container.width) >= (content.x + content.width) && 
				(container.y + container.height) >= (content.y + content.height)){
			return true;
		}
		return false;
	}

	public Relation calculatePointsRelation(Point point1, Point point2, Stereotype stereotypeInicial, Stereotype stereotypeDestiny){
		Point si = new Point(stereotypeInicial.getInicialPoint());
		Point sf = new Point(stereotypeInicial.getInicialPoint().x + stereotypeInicial.getWidth(), stereotypeInicial.getInicialPoint().y + stereotypeInicial.getHeigth());
		Relation relation = new Relation(stereotypeDestiny);
		Point pointIntersection = null;
		ArrayList<Point> array = new ArrayList<Point>();
		ArrayList<Line2D> arrayLines = new ArrayList<Line2D>();
		Line2D line2D = null;
		String orientation = "";
		if(point2.x <= si.x && point2.y <= si.y){//1
			if((point1.x - point2.x) <= (point1.y - point2.y)){
				if(si.x - point2.x <= 10){
					array.add(new Point(stereotypeInicial.getInicialPoint().x, point1.y));
					array.add(new Point(si.x - 10, point1.y));
					line2D = new Line2D.Double(new Point(stereotypeInicial.getInicialPoint().x, point1.y), new Point(si.x - 10, point1.y));
					arrayLines.add(line2D);
					pointIntersection = new Point(si.x - 10, stereotypeDestiny.getInicialPoint().y + stereotypeDestiny.getHeigth());
					array.add(pointIntersection);
					line2D = new Line2D.Double(new Point(si.x - 10, point1.y), pointIntersection);
					arrayLines.add(line2D);
				}
				else{
					array.add(new Point(stereotypeInicial.getInicialPoint().x, point1.y));
					array.add(new Point(point2.x, point1.y));
					line2D = new Line2D.Double(new Point(stereotypeInicial.getInicialPoint().x, point1.y), new Point(point2.x, point1.y));
					arrayLines.add(line2D);
					pointIntersection = new Point(point2.x, stereotypeDestiny.getInicialPoint().y + stereotypeDestiny.getHeigth());
					array.add(pointIntersection);
					line2D = new Line2D.Double(new Point(point2.x, point1.y), pointIntersection);
					arrayLines.add(line2D);
				}
				orientation = "verticalIzquierdaArriba";
			}
			else if((point1.x - point2.x) > (point1.y - point2.y)){
				if(si.y - point2.y <= 10){
					array.add(new Point(point1.x, stereotypeInicial.getInicialPoint().y));
					array.add(new Point(point1.x, si.y - 10));
					line2D = new Line2D.Double(new Point(point1.x, stereotypeInicial.getInicialPoint().y), new Point(point1.x, si.y - 10));
					arrayLines.add(line2D);
					pointIntersection = new Point(stereotypeDestiny.getInicialPoint().x + stereotypeDestiny.getWidth(), si.y - 10);
					array.add(pointIntersection);
					line2D = new Line2D.Double(new Point(point1.x, si.y - 10), pointIntersection);
					arrayLines.add(line2D);
				}
				else{
					array.add(new Point(point1.x, stereotypeInicial.getInicialPoint().y));
					array.add(new Point(point1.x, point2.y));
					line2D = new Line2D.Double(new Point(point1.x, stereotypeInicial.getInicialPoint().y), new Point(point1.x, point2.y));
					arrayLines.add(line2D);
					pointIntersection = new Point(stereotypeDestiny.getInicialPoint().x + stereotypeDestiny.getWidth(), point2.y);
					array.add(pointIntersection);
					line2D = new Line2D.Double(new Point(point1.x, point2.y), pointIntersection);
					arrayLines.add(line2D);
				}
				orientation = "horizontalIzquierdaArriba";
			}
		}
		if(si.x < point2.x && sf.x > point2.x && si.y > point2.y){//2
			array.add(new Point(point2.x, stereotypeInicial.getInicialPoint().y));
			pointIntersection = new Point(point2.x ,stereotypeDestiny.getInicialPoint().y + stereotypeDestiny.getHeigth());
			array.add(pointIntersection);
			line2D = new Line2D.Double(new Point(point2.x, stereotypeInicial.getInicialPoint().y), pointIntersection);
			arrayLines.add(line2D);
			orientation = "verticalArriba";
		}
		if(point2.x >= sf.x && point2.y <= si.y){//3
			if((point2.x - point1.x) <= (point1.y - point2.y)){
				if(point2.x - sf.x <= 10){
					array.add(new Point(sf.x, point1.y));
					array.add(new Point(sf.x + 10, point1.y));
					line2D = new Line2D.Double(new Point(sf.x, point1.y), new Point(sf.x + 10, point1.y));
					arrayLines.add(line2D);
					pointIntersection = new Point(sf.x + 10,stereotypeDestiny.getInicialPoint().y + stereotypeDestiny.getHeigth());
					array.add(pointIntersection);
					line2D = new Line2D.Double(new Point(sf.x + 10, point1.y), pointIntersection);
					arrayLines.add(line2D);
				}
				else{
					array.add(new Point(sf.x, point1.y));
					array.add(new Point(point2.x, point1.y));
					line2D = new Line2D.Double(new Point(sf.x, point1.y), new Point(point2.x, point1.y));
					arrayLines.add(line2D);
					pointIntersection = new Point(point2.x,stereotypeDestiny.getInicialPoint().y + stereotypeDestiny.getHeigth());
					array.add(pointIntersection);
					line2D = new Line2D.Double(new Point(point2.x, point1.y), pointIntersection);
					arrayLines.add(line2D);
				}
				orientation = "verticalDerechaArriba";
			}
			else if((point2.x - point1.x) > (point1.y - point2.y)){
				if(si.y - point2.y <= 10){
					array.add(new Point(point1.x,si.y));
					array.add(new Point(point1.x, si.y - 10));
					line2D = new Line2D.Double(new Point(point1.x,si.y), new Point(point1.x, si.y - 10));
					arrayLines.add(line2D);
					pointIntersection = new Point(stereotypeDestiny.getInicialPoint().x, si.y - 10);
					array.add(pointIntersection);
					line2D = new Line2D.Double(new Point(point1.x, si.y - 10), pointIntersection);
					arrayLines.add(line2D);
				}
				else{
					array.add(new Point(point1.x, si.y));
					array.add(new Point(point1.x, point2.y));
					line2D = new Line2D.Double(new Point(point1.x, si.y), new Point(point1.x, point2.y));
					arrayLines.add(line2D);
					pointIntersection = new Point(stereotypeDestiny.getInicialPoint().x, point2.y);
					array.add(pointIntersection);
					line2D = new Line2D.Double(new Point(point1.x, point2.y), pointIntersection);
					arrayLines.add(line2D);
				}
				orientation = "horizontalDerechaArriba";
			}
		}
		if(point2.x > sf.x && point2.y > si.y && point2.y < sf.y){//4
			array.add(new Point(sf.x, point2.y));
			pointIntersection = new Point(stereotypeDestiny.getInicialPoint().x, point2.y);
			array.add(pointIntersection);
			line2D = new Line2D.Double(new Point(sf.x, point2.y), pointIntersection);
			arrayLines.add(line2D);
			orientation = "horizontalDerecha";
		}
		if(point2.x >= sf.x && point2.y >= sf.y){//5
			if((point2.x - point1.x) <= (point2.y - point1.y)){
				if(point2.x - sf.x <= 10){
					array.add(new Point(sf.x, point1.y));
					array.add(new Point(sf.x + 10, point1.y));
					line2D = new Line2D.Double(new Point(sf.x, point1.y), new Point(sf.x + 10, point1.y));
					arrayLines.add(line2D);
					pointIntersection = new Point(sf.x + 10, stereotypeDestiny.getInicialPoint().y);
					array.add(pointIntersection);
					line2D = new Line2D.Double(new Point(sf.x + 10, point1.y), pointIntersection);
					arrayLines.add(line2D);
				}
				else{
					array.add(new Point(sf.x, point1.y));
					array.add(new Point(point2.x, point1.y));
					line2D = new Line2D.Double(new Point(sf.x, point1.y), new Point(point2.x, point1.y));
					arrayLines.add(line2D);
					pointIntersection = new Point(point2.x, stereotypeDestiny.getInicialPoint().y);
					array.add(pointIntersection);
					line2D = new Line2D.Double(new Point(point2.x, point1.y), pointIntersection);
					arrayLines.add(line2D);
				}
				orientation = "verticalDerechaAbajo";
			}
			else if((point2.x - point1.x) > (point2.y - point1.y)){
				if(point2.y - sf.y <= 10){
					array.add(new Point(point1.x, sf.y));
					array.add(new Point(point1.x, sf.y + 10));
					line2D = new Line2D.Double(new Point(point1.x, sf.y), new Point(point1.x, sf.y + 10));
					arrayLines.add(line2D);
					pointIntersection = new Point(stereotypeDestiny.getInicialPoint().x, sf.y + 10);
					array.add(pointIntersection);
					line2D = new Line2D.Double(new Point(point1.x, sf.y + 10), pointIntersection);
					arrayLines.add(line2D);
				}
				else{
					array.add(new Point(point1.x, sf.y));
					array.add(new Point(point1.x, point2.y));
					line2D = new Line2D.Double(new Point(point1.x, sf.y), new Point(point1.x, point2.y));
					arrayLines.add(line2D);
					pointIntersection = new Point(stereotypeDestiny.getInicialPoint().x, point2.y);
					array.add(pointIntersection);
					line2D = new Line2D.Double(new Point(point1.x, point2.y), pointIntersection);
					arrayLines.add(line2D);
				}
				orientation = "horizontalDerechaAbajo";
			}
		}
		if(si.x < point2.x && sf.x > point2.x && point2.y > sf.y){//6
			array.add(new Point(point2.x, sf.y));
			pointIntersection = new Point(point2.x, stereotypeDestiny.getInicialPoint().y);
			array.add(pointIntersection);
			line2D = new Line2D.Double(new Point(point2.x, sf.y), pointIntersection);
			arrayLines.add(line2D);
			orientation = "verticalAbajo";
		}
		if(si.x >= point2.x && sf.y <= point2.y){//7
			if((point1.x - point2.x) <= (point2.y - point1.y)){
				if(si.x - point2.x <= 10){
					array.add(new Point(si.x, point1.y));
					array.add(new Point(si.x - 10, point1.y));
					line2D = new Line2D.Double(new Point(si.x, point1.y), new Point(si.x - 10, point1.y));
					arrayLines.add(line2D);
					pointIntersection = new Point(si.x - 10, stereotypeDestiny.getInicialPoint().y);
					array.add(pointIntersection);
					line2D = new Line2D.Double(new Point(si.x - 10, point1.y), pointIntersection);
					arrayLines.add(line2D);
				}
				else{
					array.add(new Point(si.x, point1.y));
					array.add(new Point(point2.x, point1.y));
					line2D = new Line2D.Double(new Point(si.x, point1.y), new Point(point2.x, point1.y));
					arrayLines.add(line2D);
					pointIntersection = new Point(point2.x, stereotypeDestiny.getInicialPoint().y);
					array.add(pointIntersection);
					line2D = new Line2D.Double(new Point(point2.x, point1.y), pointIntersection);
					arrayLines.add(line2D);
				}
				orientation = "verticalIzquierdaAbajo";
			}
			else if((point1.x - point2.x) > (point2.y - point1.y)){
				if(point2.y - sf.y <= 10){
					array.add(new Point(point1.x, sf.y));
					array.add(new Point(point1.x, sf.y + 10));
					line2D = new Line2D.Double(new Point(point1.x, sf.y), new Point(point1.x, sf.y + 10));
					arrayLines.add(line2D);
					pointIntersection = new Point(stereotypeDestiny.getInicialPoint().x + stereotypeDestiny.getWidth(), sf.y + 10);
					array.add(pointIntersection);
					line2D = new Line2D.Double(new Point(point1.x, sf.y + 10), pointIntersection);
					arrayLines.add(line2D);
				}
				else{
					array.add(new Point(point1.x, sf.y));
					array.add(new Point(point1.x, point2.y));
					line2D = new Line2D.Double(new Point(point1.x, sf.y), new Point(point1.x, point2.y));
					arrayLines.add(line2D);
					pointIntersection = new Point(stereotypeDestiny.getInicialPoint().x + stereotypeDestiny.getWidth(), point2.y);
					array.add(pointIntersection);
					line2D = new Line2D.Double(new Point(point1.x, point2.y), pointIntersection);
					arrayLines.add(line2D);
				}
				orientation = "horizontalIzquierdaAbajo";
			}
		}
		if(point2.x < si.x && point2.y > si.y && point2.y < sf.y){//8
			array.add(new Point(si.x, point2.y));
			pointIntersection = new Point(stereotypeDestiny.getInicialPoint().x + stereotypeDestiny.getWidth(), point2.y);
			array.add(pointIntersection);
			line2D = new Line2D.Double(new Point(si.x, point2.y), pointIntersection);
			arrayLines.add(line2D);
			orientation = "horizontalIzquierda";
		}
		relation.setParentId(stereotypeInicial.getId());
		relation.setOrientation(orientation);
		relation.setArraypoints(array);
		relation.setArrayLines(arrayLines);
		relation.setIntersection(pointIntersection);
		return relation;
		//stereotypeInicial.addRelation(relation);
	}

	public void paintRelations(Graphics g, ArrayList<Stereotype> list){
		for (Stereotype stereotype : list) {
			if(stereotype.getRelatedStereotypes().size() > 0 && stereotype.isVisible()){
				Graphics2D g2 = (Graphics2D) g;// convertirlo a 2D
				Stroke oldStroke = g2.getStroke();
				for (Relation relation : stereotype.getRelatedStereotypes()) {
					Vector<Integer> coordinatesX = new Vector<Integer>();
					Vector<Integer> coordinatesY = new Vector<Integer>();
					//revalidate(relation);
					for (int i = 0; i < relation.getArraypoints().size(); i++) {
						coordinatesX.add(relation.getArraypoints().get(i).x);
						coordinatesY.add(relation.getArraypoints().get(i).y);
					}
					if(relation.getTypeRel().equals("Anclar Nota")){
						g2.setColor(new Color(0, 0, 0));
						if(relation.isSelected()){
							float dash1[] = { 5.0f };
							BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
							g2.setStroke(dashed);
							for(int i = 0; i < coordinatesX.size(); i++){
								if(i + 1 <= coordinatesX.size() - 1)
									g.drawLine(coordinatesX.get(i), coordinatesY.get(i), coordinatesX.get(i + 1), coordinatesY.get(i + 1));
							}
							g2.setStroke(oldStroke);
							for (int i = 0; i < relation.getArraypoints().size(); i++) {
								g.drawRect(relation.getArraypoints().get(i).x - 3, relation.getArraypoints().get(i).y - 3, 6, 6);
								g.fillRect(relation.getArraypoints().get(i).x - 3, relation.getArraypoints().get(i).y - 3, 6, 6);
								relation.getArrayRectangles().add(new Rectangle(relation.getArraypoints().get(i).x - 3, relation.getArraypoints().get(i).y - 3, 6, 6));
							}
							//g2.setStroke(oldStroke);
						}
						else{
							float dash1[] = { 5.0f };
							BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
							g2.setStroke(dashed);
							for(int i = 0; i < coordinatesX.size(); i++){
								if(i + 1 <= coordinatesX.size() - 1)
									g.drawLine(coordinatesX.get(i), coordinatesY.get(i), coordinatesX.get(i + 1), coordinatesY.get(i + 1));
							}
							g2.setStroke(oldStroke);
						}
						//
					}
					if(relation.getTypeRel().equals("Transicion")){
						g2.setColor(new Color(0, 0, 0));
						if(relation.isSelected()){
							for(int i = 0; i < coordinatesX.size(); i++){
								if(i + 1 <= coordinatesX.size() - 1)
									g.drawLine(coordinatesX.get(i), coordinatesY.get(i), coordinatesX.get(i + 1), coordinatesY.get(i + 1));
							}
							for (int i = 0; i < relation.getArraypoints().size(); i++) {
								g.drawRect(relation.getArraypoints().get(i).x - 3, relation.getArraypoints().get(i).y - 3, 6, 6);
								g.fillRect(relation.getArraypoints().get(i).x - 3, relation.getArraypoints().get(i).y - 3, 6, 6);
								relation.getArrayRectangles().add(new Rectangle(relation.getArraypoints().get(i).x - 3, relation.getArraypoints().get(i).y - 3, 6, 6));
							}
						}
						else{
							for(int i = 0; i < coordinatesX.size(); i++){
								if(i + 1 <= coordinatesX.size() - 1)
									g.drawLine(coordinatesX.get(i), coordinatesY.get(i), coordinatesX.get(i + 1), coordinatesY.get(i + 1));
							}
						}
						//
					}


					if(relation.getOrientation().equals("verticalIzquierdaArriba") || relation.getOrientation().equals("verticalArriba") ||
							relation.getOrientation().equals("verticalDerechaArriba")){
						g.drawLine(relation.getIntersection().x, relation.getIntersection().y, relation.getIntersection().x - 3, relation.getIntersection().y + 6);
						g.drawLine(relation.getIntersection().x, relation.getIntersection().y, relation.getIntersection().x + 3, relation.getIntersection().y + 6);
						g.drawLine(relation.getIntersection().x - 3, relation.getIntersection().y + 6, relation.getIntersection().x + 3, relation.getIntersection().y + 6);

						int[] fillCoordinatesX = {0,1,2,3,4,5,6,7,8,9};
						int[] fillCoordinatesY = {0,1,2,3,4,5,6,7,8,9};
						fillCoordinatesX[0] = relation.getIntersection().x;
						fillCoordinatesY[0] = relation.getIntersection().y;
						fillCoordinatesX[1] = relation.getIntersection().x - 3;
						fillCoordinatesY[1] = relation.getIntersection().y + 6;
						fillCoordinatesX[2] = relation.getIntersection().x + 3;
						fillCoordinatesY[2] = relation.getIntersection().y + 6;

						g.fillPolygon(fillCoordinatesX, fillCoordinatesY, 3);
					}
					if(relation.getOrientation().equals("horizontalIzquierdaArriba") || relation.getOrientation().equals("horizontalIzquierda") ||
							relation.getOrientation().equals("horizontalIzquierdaAbajo")){
						g.drawLine(relation.getIntersection().x, relation.getIntersection().y, relation.getIntersection().x + 6, relation.getIntersection().y - 3);
						g.drawLine(relation.getIntersection().x, relation.getIntersection().y, relation.getIntersection().x + 6, relation.getIntersection().y + 3);
						g.drawLine(relation.getIntersection().x + 6, relation.getIntersection().y - 3, relation.getIntersection().x + 6, relation.getIntersection().y + 3);

						int[] fillCoordinatesX = {0,1,2,3,4,5,6,7,8,9};
						int[] fillCoordinatesY = {0,1,2,3,4,5,6,7,8,9};
						fillCoordinatesX[0] = relation.getIntersection().x;
						fillCoordinatesY[0] = relation.getIntersection().y;
						fillCoordinatesX[1] = relation.getIntersection().x + 6;
						fillCoordinatesY[1] = relation.getIntersection().y - 3;
						fillCoordinatesX[2] = relation.getIntersection().x + 6;
						fillCoordinatesY[2] = relation.getIntersection().y + 3;

						g.fillPolygon(fillCoordinatesX, fillCoordinatesY, 3);
					}
					if(relation.getOrientation().equals("verticalIzquierdaAbajo") || relation.getOrientation().equals("verticalAbajo") ||
							relation.getOrientation().equals("verticalDerechaAbajo")){
						g.drawLine(relation.getIntersection().x, relation.getIntersection().y, relation.getIntersection().x - 3, relation.getIntersection().y - 6);
						g.drawLine(relation.getIntersection().x, relation.getIntersection().y, relation.getIntersection().x + 3, relation.getIntersection().y - 6);
						g.drawLine(relation.getIntersection().x - 3, relation.getIntersection().y - 6, relation.getIntersection().x + 3, relation.getIntersection().y - 6);

						int[] fillCoordinatesX = {0,1,2,3,4,5,6,7,8,9};
						int[] fillCoordinatesY = {0,1,2,3,4,5,6,7,8,9};
						fillCoordinatesX[0] = relation.getIntersection().x;
						fillCoordinatesY[0] = relation.getIntersection().y;
						fillCoordinatesX[1] = relation.getIntersection().x - 3;
						fillCoordinatesY[1] = relation.getIntersection().y - 6;
						fillCoordinatesX[2] = relation.getIntersection().x + 3;
						fillCoordinatesY[2] = relation.getIntersection().y - 6;

						g.fillPolygon(fillCoordinatesX, fillCoordinatesY, 3);
					}
					if(relation.getOrientation().equals("horizontalDerechaArriba") || relation.getOrientation().equals("horizontalDerecha") ||
							relation.getOrientation().equals("horizontalDerechaAbajo")){
						g.drawLine(relation.getIntersection().x, relation.getIntersection().y, relation.getIntersection().x - 6, relation.getIntersection().y - 3);
						g.drawLine(relation.getIntersection().x, relation.getIntersection().y, relation.getIntersection().x - 6, relation.getIntersection().y + 3);
						g.drawLine(relation.getIntersection().x - 6, relation.getIntersection().y - 3, relation.getIntersection().x - 6, relation.getIntersection().y + 3);

						int[] fillCoordinatesX = {0,1,2,3,4,5,6,7,8,9};
						int[] fillCoordinatesY = {0,1,2,3,4,5,6,7,8,9};
						fillCoordinatesX[0] = relation.getIntersection().x;
						fillCoordinatesY[0] = relation.getIntersection().y;
						fillCoordinatesX[1] = relation.getIntersection().x - 6;
						fillCoordinatesY[1] = relation.getIntersection().y - 3;
						fillCoordinatesX[2] = relation.getIntersection().x - 6;
						fillCoordinatesY[2] = relation.getIntersection().y + 3;

						g.fillPolygon(fillCoordinatesX, fillCoordinatesY, 3);
					}
					g2.setStroke(oldStroke);
				}
			}
		}
	}

	public ArrayList<Stereotype> findRelationDestiny(Stereotype stereotypeDestiny){
		ArrayList<Stereotype> array = new ArrayList<Stereotype>();
		for (Stereotype stereotype : getActiveDiagram().getPictureList()) {
			for (Relation relation : stereotype.getRelatedStereotypes()) {
				if(relation.getStereotype().getId() == stereotypeDestiny.getId())
					array.add(stereotype);
			}
		}
		return array;
	}

	public boolean findRelationDestinyForContent(Stereotype stereotypeContent, Stereotype stereotypeDestiny){
		boolean found = false;
		for (Relation relation : stereotypeContent.getRelatedStereotypes()) {
			if(relation.getStereotype().getId() == stereotypeDestiny.getId())
				found = true;
		}
		return found;
	}

	public boolean isPosOfselectionRect(Relation relation, Point pointMove){
		boolean found = false;
		for(int i = 0; i < relation.getArrayRectangles().size();i++){
			if(isPointContentInRectangle(pointMove, relation.getArrayRectangles().get(i))){
				found = true;
			}
		}
		if(getActiveDiagram().isPointContentInPolyline(relation, pointMove)){
			found = true;
		}
		return found;
	}

	public ArrayList<Point2D> getPointsOfLine(Relation relation, Point pointPressed){
		ArrayList<Point2D> arrayPoints = new ArrayList<Point2D>();
		for (int i = 0; i < relation.getArrayLines().size(); i++) {
			if((relation.getArrayLines().get(i).getX1() - 2 <= pointPressed.getX() && relation.getArrayLines().get(i).getX1() + 2 >= pointPressed.getX()) && ((relation.getArrayLines().get(i).getY1() <= pointPressed.getY() && relation.getArrayLines().get(i).getY2() >= pointPressed.getY())
					|| (relation.getArrayLines().get(i).getY1() >= pointPressed.getY() && relation.getArrayLines().get(i).getY2() <= pointPressed.getY()))){
				arrayPoints.add(relation.getArrayLines().get(i).getP1());
				arrayPoints.add(relation.getArrayLines().get(i).getP2());
				break;
			}
			else if((relation.getArrayLines().get(i).getY1() - 2 <= pointPressed.getY() && relation.getArrayLines().get(i).getY1() + 2 >= pointPressed.getY()) && ((relation.getArrayLines().get(i).getX1() <= pointPressed.getX() && relation.getArrayLines().get(i).getX2() >= pointPressed.getX())
					|| (relation.getArrayLines().get(i).getX1() >= pointPressed.getX() && relation.getArrayLines().get(i).getX2() <= pointPressed.getX()))){
				arrayPoints.add(relation.getArrayLines().get(i).getP1());
				arrayPoints.add(relation.getArrayLines().get(i).getP2());
				break;
			}
		}
		return arrayPoints;
	}

	public ArrayList<Point> getPointsContents(Relation relation, Point2D point1, Point2D point2, Point pointMoving){
		Point searchPoint1 = null;
		Point searchPoint2 = null;
		boolean firstSegment = false;
		if(relation.getOrientation().equals("verticalIzquierdaArriba")){
			if(relation.isPointExtreme(point1)){
				searchPoint1 = new Point( new Double( point1.getX() - 10).intValue(), new Double( point1.getY() ).intValue());
				if(point1.getX() - point2.getX() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint1 = new Point( new Double( point1.getX() ).intValue(), new Double( point1.getY() ).intValue());
			if(relation.isPointExtreme(point2)){
				searchPoint2 = new Point( new Double( point2.getX() ).intValue(), new Double( point2.getY() + 10 ).intValue());
				if(point1.getY() - point2.getY() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint2 = new Point( new Double( point2.getX() ).intValue(), new Double( point2.getY() ).intValue());
		}
		if(relation.getOrientation().equals("verticalArriba")){
			if(relation.isPointExtreme(point1)){
				searchPoint1 = new Point( new Double( point1.getX()).intValue(), new Double( point1.getY() - 10 ).intValue());
				if(point1.getY() - point2.getY() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint1 = new Point( new Double( point1.getX() ).intValue(), new Double( point1.getY() ).intValue());
			if(relation.isPointExtreme(point2)){
				searchPoint2 = new Point( new Double( point2.getX() ).intValue(), new Double( point2.getY() + 10 ).intValue());
				if(point1.getY() - point2.getY() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint2 = new Point( new Double( point2.getX() ).intValue(), new Double( point2.getY() ).intValue());
		}
		if(relation.getOrientation().equals("verticalDerechaArriba")){
			if(relation.isPointExtreme(point1)){
				searchPoint1 = new Point( new Double( point1.getX() + 10 ).intValue(), new Double( point1.getY() ).intValue());
				if(point2.getX() - point1.getX() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint1 = new Point( new Double( point1.getX() ).intValue(), new Double( point1.getY() ).intValue());
			if(relation.isPointExtreme(point2)){
				searchPoint2 = new Point( new Double( point2.getX() ).intValue(), new Double( point2.getY() + 10 ).intValue());
				if(point1.getY() - point2.getY() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint2 = new Point( new Double( point2.getX() ).intValue(), new Double( point2.getY() ).intValue());
		}
		if(relation.getOrientation().equals("horizontalIzquierdaArriba")){
			if(relation.isPointExtreme(point1)){
				searchPoint1 = new Point( new Double( point1.getX() ).intValue(), new Double( point1.getY() - 10 ).intValue());
				if(point1.getY() - point2.getY() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint1 = new Point( new Double( point1.getX() ).intValue(), new Double( point1.getY() ).intValue());
			if(relation.isPointExtreme(point2)){
				searchPoint2 = new Point( new Double( point2.getX() + 10 ).intValue(), new Double( point2.getY() ).intValue());
				if(point1.getX() - point2.getX() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint2 = new Point( new Double( point2.getX() ).intValue(), new Double( point2.getY() ).intValue());
		}
		if(relation.getOrientation().equals("horizontalIzquierda")){
			if(relation.isPointExtreme(point1)){
				searchPoint1 = new Point( new Double( point1.getX() - 10 ).intValue(), new Double( point1.getY() ).intValue());
				if(point1.getX() - point2.getX() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint1 = new Point( new Double( point1.getX() ).intValue(), new Double( point1.getY() ).intValue());
			if(relation.isPointExtreme(point2)){
				searchPoint2 = new Point( new Double( point2.getX() + 10 ).intValue(), new Double( point2.getY() ).intValue());
				if(point1.getX() - point2.getX() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint2 = new Point( new Double( point2.getX() ).intValue(), new Double( point2.getY() ).intValue());
		}
		if(relation.getOrientation().equals("horizontalIzquierdaAbajo")){
			if(relation.isPointExtreme(point1)){
				searchPoint1 = new Point( new Double( point1.getX() ).intValue(), new Double( point1.getY() + 10 ).intValue());
				if(point2.getY() - point1.getY() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint1 = new Point( new Double( point1.getX() ).intValue(), new Double( point1.getY() ).intValue());
			if(relation.isPointExtreme(point2)){
				searchPoint2 = new Point( new Double( point2.getX() - 10 ).intValue(), new Double( point2.getY() ).intValue());
				if(point1.getX() - point2.getX() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint2 = new Point( new Double( point2.getX() ).intValue(), new Double( point2.getY() ).intValue());
		}
		if(relation.getOrientation().equals("verticalIzquierdaAbajo")){
			if(relation.isPointExtreme(point1)){
				searchPoint1 = new Point( new Double( point1.getX() - 10 ).intValue(), new Double( point1.getY() ).intValue());
				if(point1.getX() - point2.getX() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint1 = new Point( new Double( point1.getX() ).intValue(), new Double( point1.getY() ).intValue());
			if(relation.isPointExtreme(point2)){
				searchPoint2 = new Point( new Double( point2.getX() ).intValue(), new Double( point2.getY() - 10 ).intValue());
				if(point2.getY() - point1.getY() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint2 = new Point( new Double( point2.getX() ).intValue(), new Double( point2.getY() ).intValue());
		}
		if(relation.getOrientation().equals("verticalAbajo")){
			if(relation.isPointExtreme(point1)){
				searchPoint1 = new Point( new Double( point1.getX() ).intValue(), new Double( point1.getY() + 10 ).intValue());
				if(point2.getY() - point1.getY() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint1 = new Point( new Double( point1.getX() ).intValue(), new Double( point1.getY() ).intValue());
			if(relation.isPointExtreme(point2)){
				searchPoint2 = new Point( new Double( point2.getX() ).intValue(), new Double( point2.getY() - 10 ).intValue());
				if(point2.getY() - point1.getY() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint2 = new Point( new Double( point2.getX() ).intValue(), new Double( point2.getY() ).intValue());
		}
		if(relation.getOrientation().equals("verticalDerechaAbajo")){
			if(relation.isPointExtreme(point1)){
				searchPoint1 = new Point( new Double( point1.getX() + 10 ).intValue(), new Double( point1.getY() ).intValue());
				if(point2.getX() - point1.getX() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint1 = new Point( new Double( point1.getX() ).intValue(), new Double( point1.getY() ).intValue());
			if(relation.isPointExtreme(point2)){
				searchPoint2 = new Point( new Double( point2.getX() ).intValue(), new Double( point2.getY() - 10 ).intValue());
				if(point2.getY() - point1.getY() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint2 = new Point( new Double( point2.getX() ).intValue(), new Double( point2.getY() ).intValue());
		}
		if(relation.getOrientation().equals("horizontalDerechaArriba")){
			if(relation.isPointExtreme(point1)){
				searchPoint1 = new Point( new Double( point1.getX() ).intValue(), new Double( point1.getY() - 10 ).intValue());
				if(point1.getY() - point2.getY() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint1 = new Point( new Double( point1.getX() ).intValue(), new Double( point1.getY() ).intValue());
			if(relation.isPointExtreme(point2)){
				searchPoint2 = new Point( new Double( point2.getX() - 10 ).intValue(), new Double( point2.getY() ).intValue());
				if(point2.getX() - point1.getX() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint2 = new Point( new Double( point2.getX() ).intValue(), new Double( point2.getY() ).intValue());
		}
		if(relation.getOrientation().equals("horizontalDerecha")){
			if(relation.isPointExtreme(point1)){
				searchPoint1 = new Point( new Double( point1.getX() + 10 ).intValue(), new Double( point1.getY() ).intValue());
				if(point2.getX() - point1.getX() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint1 = new Point( new Double( point1.getX() ).intValue(), new Double( point1.getY() ).intValue());
			if(relation.isPointExtreme(point2)){
				searchPoint2 = new Point( new Double( point2.getX() - 10 ).intValue(), new Double( point2.getY() ).intValue());
				if(point2.getX() - point1.getX() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint2 = new Point( new Double( point2.getX() ).intValue(), new Double( point2.getY() ).intValue());
		}
		if(relation.getOrientation().equals("horizontalDerechaAbajo")){
			if(relation.isPointExtreme(point1)){
				searchPoint1 = new Point( new Double( point1.getX() ).intValue(), new Double( point1.getY() + 10 ).intValue());
				if(point2.getY() - point1.getY() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint1 = new Point( new Double( point1.getX() ).intValue(), new Double( point1.getY() ).intValue());
			if(relation.isPointExtreme(point2)){
				searchPoint2 = new Point( new Double( point2.getX() - 10 ).intValue(), new Double( point2.getY() ).intValue());
				if(point2.getX() - point1.getX() <= 10){
					firstSegment = true;
				}
			}
			else
				searchPoint2 = new Point( new Double( point2.getX() ).intValue(), new Double( point2.getY() ).intValue());
		}
		ArrayList<Point> array = new ArrayList<Point>();
		if(firstSegment == false){
			array.add(searchPoint1);
			array.add(searchPoint2);
			arrayPointsToInsert.add(searchPoint1);
			arrayPointsToInsert.add(searchPoint2);
		}
		return array;
	}

	public int posOfPoint(Relation relation, Point point){
		int pos = -1;
		for(int i = 0; i < relation.getArraypoints().size(); i++){
			if(point.equals(relation.getArraypoints().get(i))){
				pos = i;
			}
		}
		return pos;
	}

	public void fixArrayPoints(ArrayList<Point> array){
		Vector<Point> vectorPoints = new Vector<Point>();
		//Vector<Point> removePoints = new Vector<Point>();
		for (Point point : array) {
			vectorPoints.add(point);
		}
		/*vectorPoints.add(new Point(10, 15));
    	vectorPoints.add(new Point(15, 20));
    	vectorPoints.add(new Point(10, 15));*/
		for (int i = 0; i < vectorPoints.size(); i++) {
			Point currentPoint = vectorPoints.get(i);
			//boolean found = false;
			for (int j = 0; j < vectorPoints.size(); j++) {
				if(currentPoint.equals(vectorPoints.get(j))&& j != i){
					//found = true;
					/*if(removePoints.contains(currentPoint) == false)
    						removePoints.add(currentPoint);*/
					//else
					vectorPoints.remove(j);	
				}

			}

		}
		//vectorPoints.removeAll(removePoints);
		array.clear();
		for (Point point : vectorPoints) {
			array.add(point);
		}
	}

	public void setEndResized(Relation relation, Point2D point1, Point2D point2, Point pointMoved ) {
		Point pointMovedFinal = pointMoved;
		if(pointMoved.x <= 0 && pointMoved.y <= 0){
			pointMovedFinal = new Point(2, 2);
		}
		if(pointMoved.x <= 0 && pointMoved.y > 0){
			pointMovedFinal = new Point(2, pointMoved.y);
		}
		if(pointMoved.x > 0 && pointMoved.y <= 0){
			pointMovedFinal = new Point(pointMoved.x, 2);
		}
		Point p1 = new Point( new Double( point1.getX() ).intValue(),
				new Double( point1.getY() ).intValue());
		Point p2 = new Point( new Double( point2.getX() ).intValue(),
				new Double( point2.getY() ).intValue());
		ArrayList<Point> arrayFinalPoints = new ArrayList<Point>();
		ArrayList<Line2D> arrayLines = new ArrayList<Line2D>();
		if ( point1.getX() == point2.getX() ) {
			int pos = posOfPoint(relation, p1);
			if(pos != -1){
				for(int i = 0; i < pos; i++){
					arrayFinalPoints.add(relation.getArraypoints().get(i));
				}
				boolean flag = false;
				if(relation.findPoint(new Point( pointMovedFinal.x, p1.y ))){
					for (int i = 0; i < arrayFinalPoints.size(); i++) {
						if(arrayFinalPoints.get(i).equals(new Point( pointMovedFinal.x, p1.y ))){
							arrayFinalPoints.remove(i);
							break;
						}
					}
					flag = true;
				}
				if(flag == false){
					arrayFinalPoints.add(new Point( pointMovedFinal.x, p1.y  ));
				}
				flag = false;
				if(relation.findPoint(new Point( pointMovedFinal.x, p2.y ))){
					for (int i = 0; i < arrayFinalPoints.size(); i++) {
						if(arrayFinalPoints.get(i).equals(new Point( pointMovedFinal.x, p2.y ))){
							arrayFinalPoints.remove(i);
							break;
						}
					}
					flag = true;
				}
				if(flag == false){
					arrayFinalPoints.add(new Point( pointMovedFinal.x, p2.y ));
				}
				//arrayFinalPoints.add(new Point( pointMovedFinal.x, p1.y  ));
				//arrayFinalPoints.add(new Point( pointMovedFinal.x, p2.y  ));
			}
			else if(pos == -1){
				arrayFinalPoints.add(relation.getArraypoints().get(0));
				arrayFinalPoints.add( new Point( p1.x, p1.y  ));
				boolean flag = false;
				if(relation.findPoint(new Point( pointMovedFinal.x, p1.y ))){
					for (int i = 0; i < arrayFinalPoints.size(); i++) {
						if(arrayFinalPoints.get(i).equals(new Point( pointMovedFinal.x, p1.y ))){
							arrayFinalPoints.remove(i);
							break;
						}
					}
					flag = true;
				}
				if(flag == false){
					arrayFinalPoints.add(new Point( pointMovedFinal.x, p1.y ));
				}
				flag = false;
				if(relation.findPoint(new Point( pointMovedFinal.x, p2.y ))){
					for (int i = 0; i < arrayFinalPoints.size(); i++) {
						if(arrayFinalPoints.get(i).equals(new Point( pointMovedFinal.x, p2.y ))){
							arrayFinalPoints.remove(i);
							break;
						}
					}
					flag = true;
				}
				if(flag == false){
					arrayFinalPoints.add(new Point( pointMovedFinal.x, p2.y ));
				}
				//arrayFinalPoints.add(new Point( pointMovedFinal.x, p1.y  ));
				//arrayFinalPoints.add(new Point( pointMovedFinal.x, p2.y  ));
			}
			pos = posOfPoint(relation, p2);
			if(pos != -1){
				for(int i = pos + 1 ; i < relation.getArraypoints().size(); i++){
					Point pointOne = new Point(pointMovedFinal.x, p2.y);
					Point pointTwo = new Point(relation.getArraypoints().get(i));
					if(comparePoints(pointOne, pointTwo) == false )
						arrayFinalPoints.add(relation.getArraypoints().get(i));
				}
			}
			else if(pos == -1){
				boolean flag = false;
				for(int i = 0; i < relation.getArraypoints().size(); i++){
					Point pointOne = new Point(pointMovedFinal.x, p2.y);
					Point pointTwo = new Point(relation.getArraypoints().get(i));
					if(comparePoints(pointOne, pointTwo))
						flag = true;
				}
				if(flag == false)
					arrayFinalPoints.add(new Point( pointMovedFinal.x, p2.y ));
				arrayFinalPoints.add(new Point(p2));
				arrayFinalPoints.add(relation.getArraypoints().get(relation.getArraypoints().size() - 1));
			}
		}
		else if ( point1.getY() == point2.getY() ) {
			int pos = posOfPoint(relation, p1);
			if(pos != -1){
				for(int i = 0; i < pos; i++){
					arrayFinalPoints.add(relation.getArraypoints().get(i));
				}
				boolean flag = false;
				if(relation.findPoint(new Point( p1.x, pointMovedFinal.y ))){
					for (int i = 0; i < arrayFinalPoints.size(); i++) {
						if(arrayFinalPoints.get(i).equals(new Point( p1.x, pointMovedFinal.y ))){
							arrayFinalPoints.remove(i);
							break;
						}
					}
					flag = true;
				}
				if(flag == false){
					arrayFinalPoints.add(new Point( p1.x, pointMovedFinal.y ));
				}
				flag = false;
				if(relation.findPoint(new Point( p2.x, pointMovedFinal.y ))){
					for (int i = 0; i < arrayFinalPoints.size(); i++) {
						if(arrayFinalPoints.get(i).equals(new Point( p2.x, pointMovedFinal.y ))){
							arrayFinalPoints.remove(i);
							break;
						}
					}
					flag = true;
				}
				if(flag == false){
					arrayFinalPoints.add(new Point( p2.x, pointMovedFinal.y ));
				}
				//arrayFinalPoints.add(new Point( p1.x, pointMovedFinal.y ));
				//arrayFinalPoints.add(new Point( p2.x, pointMovedFinal.y  ));
			}
			else if(pos == -1){
				arrayFinalPoints.add(relation.getArraypoints().get(0));
				arrayFinalPoints.add( new Point( p1.x, p1.y  ));
				boolean flag = false;
				if(relation.findPoint(new Point( p1.x, pointMovedFinal.y ))){
					for (int i = 0; i < arrayFinalPoints.size(); i++) {
						if(arrayFinalPoints.get(i).equals(new Point( p1.x, pointMovedFinal.y ))){
							arrayFinalPoints.remove(i);
							break;
						}
					}
					flag = true;
				}
				if(flag == false){
					arrayFinalPoints.add(new Point( p1.x, pointMovedFinal.y ));
				}
				flag = false;
				if(relation.findPoint(new Point( p2.x, pointMovedFinal.y ))){
					for (int i = 0; i < arrayFinalPoints.size(); i++) {
						if(arrayFinalPoints.get(i).equals(new Point( p2.x, pointMovedFinal.y ))){
							arrayFinalPoints.remove(i);
							break;
						}
					}
					flag = true;
				}
				if(flag == false){
					arrayFinalPoints.add(new Point( p2.x, pointMovedFinal.y  ));
				}
				//arrayFinalPoints.add(new Point( p1.x, pointMovedFinal.y  ));
				//arrayFinalPoints.add(new Point( p2.x, pointMovedFinal.y  ));
			}
			pos = posOfPoint(relation, p2);
			if(pos != -1){
				for(int i = pos + 1; i < relation.getArraypoints().size(); i++){
					Point pointOne = new Point(p2.x, pointMovedFinal.y);
					Point pointTwo = new Point(relation.getArraypoints().get(i));
					if(comparePoints(pointOne, pointTwo) == false)
						arrayFinalPoints.add(relation.getArraypoints().get(i));
				}
			}
			else if(pos == -1){
				boolean flag = false;
				for(int i = 0; i < relation.getArraypoints().size(); i++){
					Point pointOne = new Point(p2.x, pointMovedFinal.y);
					Point pointTwo = new Point(relation.getArraypoints().get(i));
					if(comparePoints(pointOne, pointTwo))
						flag = true;
				}
				if(flag == false)
					arrayFinalPoints.add(new Point(p2.x, pointMovedFinal.y));
				arrayFinalPoints.add(new Point(p2));
				arrayFinalPoints.add(relation.getArraypoints().get(relation.getArraypoints().size() - 1));
			}
		}
		fixArrayPoints(arrayFinalPoints);
		relation.setArraypoints(arrayFinalPoints);
		for (int i = 0; i < arrayFinalPoints.size(); i++) {
			if(i + 1 <= arrayFinalPoints.size() - 1){
				arrayLines.add(new Line2D.Double(arrayFinalPoints.get(i), arrayFinalPoints.get(i + 1)));
			}
		}
		relation.setArrayLines(arrayLines);
		relation.setSelected(true);
		for (Stereotype stereotype : getActiveDiagram().getPictureList()) {
			if(stereotype.getId() == relation.getParentId()){
				for(int i = 0; i < stereotype.getRelatedStereotypes().size(); i++){
					if(stereotype.getRelatedStereotypes().get(i).getId() == relation.getId()){
						stereotype.getRelatedStereotypes().remove(i);
						stereotype.getRelatedStereotypes().add(i, relation);
					}
				}
			}
		}
	}

	public boolean comparePoints(Point point1, Point point2){
		boolean found = false;
		if(point1.equals(point2))
			found = true;
		return found;
	}

	public void paintResizingRelation(Graphics g, Relation relation, Point2D point1, Point2D point2, Point pointMoving){
		ArrayList<Point> array = new ArrayList<Point>(getPointsContents(relation, point1, point2, pointMoving));
		if(array.size() > 0){
			Point p1 = array.get(0);
			Point p2 = array.get(1);
			Graphics2D g2 = ( Graphics2D ) g;
			g2.setPaint( new Color( 0, 0, 0 ) );
			Stroke oldStroke = g2.getStroke();
			g2.setStroke( new BasicStroke( 1.5f ) );
			//ver si es horizontal o vertical el desplazamiento
			//si es horizontal el desplazamiento
			if ( point1.getX() == point2.getX() ) {
				g2.draw( new Line2D.Double( p1.x, p1.y, pointMoving.x, p1.y ) );
				g2.draw( new Line2D.Double( pointMoving.x, p1.y, pointMoving.x, p2.y ) );
				g2.draw( new Line2D.Double( p2.x, p2.y, pointMoving.x, p2.y ) );
			}//si es vertical
			else if ( point1.getY() == point2.getY() ) {
				g2.draw( new Line2D.Double( p1.x, p1.y, p1.x, pointMoving.y ) );
				g2.draw( new Line2D.Double( p1.x, pointMoving.y, p2.x, pointMoving.y ) );
				g2.draw( new Line2D.Double( p2.x, p2.y, p2.x, pointMoving.y ) );
			}		
			g2.setStroke( oldStroke );
		}
	}

	public ArrayList<Point> getArrayPointsToInsert() {
		return arrayPointsToInsert;
	}

	public void setArrayPointsToInsert(ArrayList<Point> arrayPointsToInsert) {
		this.arrayPointsToInsert = arrayPointsToInsert;
	}
	
	public ArrayList<Diagram> getDiagramsForIds(ArrayList<Integer> arrayIds){
		ArrayList<Diagram> arrayDiagrams = new ArrayList<Diagram>();
		for (int id : arrayIds) {
			for (Diagram diagram : getDiagramList()) {
				if(diagram.getId() == id){
					arrayDiagrams.add(diagram);
				}
			}
		}
		return arrayDiagrams;
	}
	
	public int getIdDiagramByStereotype(int idStereotype){
		int idDiagram = -1;
		for (Diagram diagram : getDiagramList()) {
			for (Stereotype stereotype : diagram.getPictureList()) {
				if(stereotype.getId() == idStereotype){
					idDiagram = diagram.getId();
					break;
				}
			}
		}
		return idDiagram;
	}
	
	public void saveDiagram(Diagram diagramActive, ProcessGraphDocking processGraphDocking){
		diagramActive.setSaveStatus(false);
		ArrayList<Stereotype> arrayStereotypes = diagramActive.getPictureList();
		
        //creando el VO diagrama
		DiagramaVO diagramaVO = new DiagramaVO();
		diagramaVO.setApproved(diagramActive.isApproved());
		String backGround = diagramActive.getBackground().getRed() + "," + diagramActive.getBackground().getGreen() + "," + diagramActive.getBackground().getBlue();
		diagramaVO.setBackGround(backGround);
		diagramaVO.setExecuteBoth(diagramActive.isExecute());
		diagramaVO.setExecuteHeight(diagramActive.isExecuteHeight());
		diagramaVO.setExecuteWidth(diagramActive.isExecuteWidth());
		diagramaVO.setHeight(new Long(diagramActive.getDiagramSize().height));
		diagramaVO.setIdentificador(new Long(diagramActive.getId()));
		diagramaVO.setNameDiagram(diagramActive.getName());
		diagramaService.unSelectAllDiagrams();
		diagramaVO.setSelected(diagramActive.isSelected());
		if(diagramActive.getType() == "MNC"){
			diagramaVO.setTipo(TipoDiagramaEnum.MNC);
		}
		if(diagramActive.getType() == "RGG"){
			diagramaVO.setTipo(TipoDiagramaEnum.RGG);
		}
		if(diagramActive.getType() == "RGS"){
			diagramaVO.setTipo(TipoDiagramaEnum.RGS);
		}
		diagramaVO.setWidth(new Long(diagramActive.getDiagramSize().width));
		diagramaVO.setZoomFactor(new Float(diagramActive.getZoomFactor()));
		
		Long idDiagrama = diagramaService.findDiagrama(new Long(diagramActive.getId()));
		Long idCreateDiagram = new Long(-1);
		if(idDiagrama == -1){
			
			if(diagramActive.getType() == "MNC"){
				idCreateDiagram = diagramaService.createDiagrama(diagramaVO, new Long(1), "");
				diagramaVO.setId(idCreateDiagram);
			}
			if(diagramActive.getType() == "RGS"){
				Long idParent = new Long(diagramActive.getObjectParent());
				Long idStereotypeParent = stereotypeService.findStereotype(idParent);
				if(idStereotypeParent != -1){
					idCreateDiagram = diagramaService.createDiagrama(diagramaVO, idStereotypeParent, "stereotype");
					diagramaVO.setId(idCreateDiagram);
				}
				else if(idStereotypeParent == -1){// el padre no se ha salvado
					int idDiagramSearch = getIdDiagramByStereotype(diagramActive.getObjectParent());
					Diagram diagramSearch = findById(idDiagramSearch);
					int indexOf = diagramSearch.getName().indexOf("*");
					if(indexOf != -1){
						diagramSearch.setSaveStatus(false);
						String name = diagramSearch.getName();
						int selectedTab = -1;
						for (int i = 0; i < processGraphDocking.getTabbedPaneWorkArea().getTabbedPane().getTabCount(); i++) {
							if(processGraphDocking.getTabbedPaneWorkArea().getTituloAt(i).equals(diagramSearch.getName())){
								selectedTab = i;
							}
						}
						if(selectedTab != -1){
							name = diagramSearch.getName().substring(1);
							processGraphDocking.getListTabsTitles().remove(selectedTab);
							processGraphDocking.getListTabsTitles().add(selectedTab, name);
							processGraphDocking.getTabbedPaneWorkArea().getTabbedPane().setTitleAt(selectedTab, name);
						}
						diagramSearch.setName(name);
					}
					
					saveDiagram(diagramSearch, processGraphDocking);
					idStereotypeParent = stereotypeService.findStereotype(idParent);
					idCreateDiagram = diagramaService.createDiagrama(diagramaVO, idStereotypeParent, "stereotype");
					diagramaVO.setId(idCreateDiagram);
				}

				//crear las calles//////////////////////////////////////////////////////////////////////////
				CalleVO calleVO = new CalleVO();
				calleVO.setIdentificador(new Long(diagramActive.getSwinLine().getId()));
				Long idCreateCalle = new Long(-1);
				idCreateCalle = diagramaService.createCalle(calleVO, diagramaVO.getId());
				calleVO.setId(idCreateCalle);
				//crear nombres y anchos///////////////////////////////////////////////////////////////

				for (String name : diagramActive.getSwinLine().getArrayNameSwingLine()) {
					NombresCalleVO nombresCalleVO = new NombresCalleVO();
					nombresCalleVO.setNombre(name);
					diagramaService.createNombresCalle(nombresCalleVO, calleVO.getId());
				}
				for (int width : diagramActive.getSwinLine().getArrayWidths()) {
					AnchosCalleVO anchosCalleVO = new AnchosCalleVO();
					anchosCalleVO.setWidth(new Long(width));
					diagramaService.createAnchosCalle(anchosCalleVO, calleVO.getId());
				}
			}
			
			for (Stereotype stereotype : arrayStereotypes) {

				StereotypeVO stereotypeVO = new StereotypeVO();
				stereotypeVO.setPrimeraCoordenadaX(new Long(stereotype.getInicialPoint().x));
				stereotypeVO.setPrimeraCoordenadaY(new Long(stereotype.getInicialPoint().y));
				stereotypeVO.setSegundaCoordenadaX(new Long(stereotype.getInicialPoint().x + stereotype.getWidth()));
				stereotypeVO.setSegundaCoordenadaY(new Long(stereotype.getInicialPoint().y + stereotype.getHeigth()));
				stereotypeVO.setIdentificador(new Long(stereotype.getId()));
				stereotypeVO.setOwner(idCreateDiagram);
				
				PropiedadesStereotypeVO propiedadesStereotypeVO = new PropiedadesStereotypeVO();
				backGround = stereotype.getBackground().getRed() + "," + stereotype.getBackground().getGreen() + "," + stereotype.getBackground().getBlue();
				propiedadesStereotypeVO.setBackGround(backGround);
				propiedadesStereotypeVO.setDescripcion(stereotype.getDescription());
				propiedadesStereotypeVO.setHeight(new Long(stereotype.getHeigth()));
				String lineColor = stereotype.getLineColor().getRed() + "," + stereotype.getLineColor().getGreen() + "," + stereotype.getLineColor().getBlue();
				propiedadesStereotypeVO.setLineColor(lineColor);
				propiedadesStereotypeVO.setName(stereotype.getName());
				propiedadesStereotypeVO.setSelected(stereotype.isSelected());
				if(stereotype.getType() == "Macro"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.MACRO);
				}
				if(stereotype.getType() == "Proc"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.PROC);
				}
				if(stereotype.getType() == "SubProc"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.SUB_PROC);
				}
				if(stereotype.getType() == "ActServ"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.ACT_SERV);
				}
				if(stereotype.getType() == "ActManual"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.ACT_MANUAL);
				}
				if(stereotype.getType() == "Cliente"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.CLIENTE);
				}
				if(stereotype.getType() == "Provee"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.PROVEE);
				}
				if(stereotype.getType() == "BaseDato"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.BASE_DATO);
				}
				if(stereotype.getType() == "Decision"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.DECISION);
				}
				if(stereotype.getType() == "Nota"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.NOTA);
				}
				if(stereotype.getType() == "EITiempo"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.EITIEMPO);
				}
				if(stereotype.getType() == "EIMensaje"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.EIMENSAJE);
				}
				if(stereotype.getType() == "EInicial"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.EINICIAL);
				}
				if(stereotype.getType() == "EFinal"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.EFINAL);
				}
				propiedadesStereotypeVO.setVisible(stereotype.isVisible());
				propiedadesStereotypeVO.setWidth(new Long(stereotype.getWidth()));
				propiedadesStereotypeVO.setIdentificador(new Long(stereotype.getId()));
				
				Long idStereotype = stereotypeService.findStereotype(new Long(stereotype.getId()));
				Long idPropiedades = stereotypeService.findPropiedades(new Long(stereotype.getId()));
				Long idCreatePropiedades = new Long(-1);
				if(idPropiedades == -1){
					idCreatePropiedades = stereotypeService.createPropiedades(propiedadesStereotypeVO);
				}
				else if(idStereotype != -1){
					stereotypeVO.setId(idStereotype);
					stereotypeService.updateStereotype(stereotypeVO);
				}
				if(idStereotype == -1){
					stereotypeService.createStereotype(stereotypeVO, stereotypeVO.getOwner(), idCreatePropiedades);
				}
				
				
			}
            //relaciones
			for (Stereotype stereotype : arrayStereotypes) {
				if(stereotype.getRelatedStereotypes().size() > 0){
					for (Relation relation : stereotype.getRelatedStereotypes()) {
						Long idStereotypeContent = stereotypeService.findStereotype(new Long(stereotype.getId()));
						Stereotype stereotypeDestiny = diagramActive.findById(relation.getStereotype().getId());
						Long idStereotypeDestiny = stereotypeService.findStereotype(new Long(stereotypeDestiny.getId()));
						RelacionVO relacionVO = new RelacionVO();
						relacionVO.setIdentificador(new Long(relation.getId()));
						relacionVO.setPrimeraInterseccion(new Long(relation.getIntersection().x));
						relacionVO.setSegundaInterseccion(new Long(relation.getIntersection().y));
						relacionVO.setName(relation.getName());
						relacionVO.setOrientation(relation.getOrientation());
						relacionVO.setSelected(relation.isSelected());
						relacionVO.setTipo(relation.getTypeRel());
						Long idCreateRelation = relacionService.createRelacion(relacionVO, idStereotypeContent, idStereotypeDestiny);
						relacionVO.setId(idCreateRelation);
						//crear atributos relacion////////////////

						for (Line2D line : relation.getArrayLines()) {
							LineasRelacionVO lineasRelacionVO = new LineasRelacionVO();
							lineasRelacionVO.setX1(new Long(new Double(line.getP1().getX()).intValue()));
							lineasRelacionVO.setY1(new Long(new Double(line.getP1().getY()).intValue()));
							lineasRelacionVO.setX2(new Long(new Double(line.getP2().getX()).intValue()));
							lineasRelacionVO.setY2(new Long(new Double(line.getP2().getY()).intValue()));

							relacionService.createLineasRelacion(lineasRelacionVO, relacionVO.getId());
						}
						for (Point point : relation.getArraypoints()) {
							PuntosRelacionVO puntosRelacionVO = new PuntosRelacionVO();
							puntosRelacionVO.setX(new Long(point.x));
							puntosRelacionVO.setY(new Long(point.y));

							relacionService.createPuntosRelacion(puntosRelacionVO, relacionVO.getId());
						}
						for (Rectangle rectangle : relation.getArrayRectangles()) {
							RectangulosRelacionVO rectangulosRelacionVO = new RectangulosRelacionVO();
							rectangulosRelacionVO.setX(new Long(rectangle.x));
							rectangulosRelacionVO.setY(new Long(rectangle.y));
							rectangulosRelacionVO.setHeight(new Long(rectangle.height));
							rectangulosRelacionVO.setWidth(new Long(rectangle.width));

							relacionService.createRectangulosRelacion(rectangulosRelacionVO, relacionVO.getId());
						}
					}
				}
			}
		}
		else{
			diagramaVO.setId(idDiagrama);
			diagramaService.updateDiagrama(diagramaVO);
			//aqui////////////////////////////////////////////////////////
			
			if(diagramActive.getType() == "RGS"){
				CalleVO calleVO = new CalleVO();
				calleVO.setIdentificador(new Long(diagramActive.getSwinLine().getId()));
				Long idCalle = diagramaService.findCalle(new Long(diagramActive.getSwinLine().getId()));
				AnchosCalleVO[] anchosCalleVOs = new AnchosCalleVO[diagramActive.getSwinLine().getArrayWidths().size()];
				for (int i = 0; i < diagramActive.getSwinLine().getArrayWidths().size(); i++) {
					AnchosCalleVO anchosCalleVO = new AnchosCalleVO();
					anchosCalleVO.setWidth(new Long(diagramActive.getSwinLine().getArrayWidths().get(i)));
					anchosCalleVOs[i] = anchosCalleVO;
				}
				diagramaService.updateAnchosCalle(anchosCalleVOs, idCalle);
			}
			
			for (Stereotype stereotype : arrayStereotypes) {
				
				StereotypeVO stereotypeVO = new StereotypeVO();
				stereotypeVO.setPrimeraCoordenadaX(new Long(stereotype.getInicialPoint().x));
				stereotypeVO.setPrimeraCoordenadaY(new Long(stereotype.getInicialPoint().y));
				stereotypeVO.setSegundaCoordenadaX(new Long(stereotype.getInicialPoint().x + stereotype.getWidth()));
				stereotypeVO.setSegundaCoordenadaY(new Long(stereotype.getInicialPoint().y + stereotype.getHeigth()));
				stereotypeVO.setIdentificador(new Long(stereotype.getId()));
				stereotypeVO.setOwner(idDiagrama);
				
				PropiedadesStereotypeVO propiedadesStereotypeVO = new PropiedadesStereotypeVO();
				backGround = stereotype.getBackground().getRed() + "," + stereotype.getBackground().getGreen() + "," + stereotype.getBackground().getBlue();
				propiedadesStereotypeVO.setBackGround(backGround);
				propiedadesStereotypeVO.setDescripcion(stereotype.getDescription());
				propiedadesStereotypeVO.setHeight(new Long(stereotype.getHeigth()));
				String lineColor = stereotype.getLineColor().getRed() + "," + stereotype.getLineColor().getGreen() + "," + stereotype.getLineColor().getBlue();
				propiedadesStereotypeVO.setLineColor(lineColor);
				propiedadesStereotypeVO.setName(stereotype.getName());
				propiedadesStereotypeVO.setSelected(stereotype.isSelected());
				if(stereotype.getType() == "Macro"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.MACRO);
				}
				if(stereotype.getType() == "Proc"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.PROC);
				}
				if(stereotype.getType() == "SubProc"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.SUB_PROC);
				}
				if(stereotype.getType() == "ActServ"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.ACT_SERV);
				}
				if(stereotype.getType() == "ActManual"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.ACT_MANUAL);
				}
				if(stereotype.getType() == "Cliente"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.CLIENTE);
				}
				if(stereotype.getType() == "Provee"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.PROVEE);
				}
				if(stereotype.getType() == "BaseDato"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.BASE_DATO);
				}
				if(stereotype.getType() == "Decision"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.DECISION);
				}
				if(stereotype.getType() == "Nota"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.NOTA);
				}
				if(stereotype.getType() == "EITiempo"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.EITIEMPO);
				}
				if(stereotype.getType() == "EIMensaje"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.EIMENSAJE);
				}
				if(stereotype.getType() == "EInicial"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.EINICIAL);
				}
				if(stereotype.getType() == "EFinal"){
					propiedadesStereotypeVO.setTipo(TipoStereotipoEnum.EFINAL);
				}
				propiedadesStereotypeVO.setVisible(stereotype.isVisible());
				propiedadesStereotypeVO.setWidth(new Long(stereotype.getWidth()));
				propiedadesStereotypeVO.setIdentificador(new Long(stereotype.getId()));
				
				Long idPropiedades = stereotypeService.findPropiedades(propiedadesStereotypeVO.getIdentificador()); 
				Long idStereotype = stereotypeService.findStereotype(stereotypeVO.getIdentificador());
				if(idStereotype == -1){//crear stereotype y propiedades
					idPropiedades = stereotypeService.createPropiedades(propiedadesStereotypeVO);
					stereotypeService.createStereotype(stereotypeVO, stereotypeVO.getOwner(), idPropiedades);
				}
				else if(idStereotype != -1){
					//actualizar stereotype y propiedades
					propiedadesStereotypeVO.setId(idPropiedades);
					stereotypeVO.setId(idStereotype);
					stereotypeService.updatePropiedades(propiedadesStereotypeVO);
					stereotypeService.updateStereotype(stereotypeVO);
				}
			}
            //relaciones
			for (Stereotype stereotype : arrayStereotypes) {
				if(stereotype.getRelatedStereotypes().size() > 0){
					for (Relation relation : stereotype.getRelatedStereotypes()) {
						Long idStereotypeContent = stereotypeService.findStereotype(new Long(stereotype.getId()));
						Stereotype stereotypeDestiny = diagramActive.findById(relation.getStereotype().getId());
						Long idStereotypeDestiny = stereotypeService.findStereotype(new Long(stereotypeDestiny.getId()));
						RelacionVO relacionVO = new RelacionVO();
						relacionVO.setIdentificador(new Long(relation.getId()));
						relacionVO.setPrimeraInterseccion(new Long(relation.getIntersection().x));
						relacionVO.setSegundaInterseccion(new Long(relation.getIntersection().y));
						relacionVO.setName(relation.getName());
						relacionVO.setOrientation(relation.getOrientation());
						relacionVO.setSelected(relation.isSelected());
						relacionVO.setTipo(relation.getTypeRel());
						Long idRelacion = relacionService.findRelacion(relacionVO.getIdentificador());
						if(idRelacion == -1){//no existe la relacion
							Long idCreateRelation = relacionService.createRelacion(relacionVO, idStereotypeContent, idStereotypeDestiny);
							relacionVO.setId(idCreateRelation);
							//crear atributos relacion////////////////
							
							for (Line2D line : relation.getArrayLines()) {
								LineasRelacionVO lineasRelacionVO = new LineasRelacionVO();
								lineasRelacionVO.setX1(new Long(new Double(line.getP1().getX()).intValue()));
								lineasRelacionVO.setY1(new Long(new Double(line.getP1().getY()).intValue()));
								lineasRelacionVO.setX2(new Long(new Double(line.getP2().getX()).intValue()));
								lineasRelacionVO.setY2(new Long(new Double(line.getP2().getY()).intValue()));
								
								relacionService.createLineasRelacion(lineasRelacionVO, relacionVO.getId());
							}
							for (Point point : relation.getArraypoints()) {
								PuntosRelacionVO puntosRelacionVO = new PuntosRelacionVO();
								puntosRelacionVO.setX(new Long(point.x));
								puntosRelacionVO.setY(new Long(point.y));
								
								relacionService.createPuntosRelacion(puntosRelacionVO, relacionVO.getId());
							}
							for (Rectangle rectangle : relation.getArrayRectangles()) {
								RectangulosRelacionVO rectangulosRelacionVO = new RectangulosRelacionVO();
								rectangulosRelacionVO.setX(new Long(rectangle.x));
								rectangulosRelacionVO.setY(new Long(rectangle.y));
								rectangulosRelacionVO.setHeight(new Long(rectangle.height));
								rectangulosRelacionVO.setWidth(new Long(rectangle.width));
								
								relacionService.createRectangulosRelacion(rectangulosRelacionVO, relacionVO.getId());
							}
							
						}
						else{//existe la relacion////////
							relacionVO.setId(idRelacion);
							relacionService.updateRelacion(relacionVO);
							//actualizar atributos//////
							LineasRelacionVO[] lineasRelacionVOs = new LineasRelacionVO[relation.getArrayLines().size()];
							PuntosRelacionVO[] puntosRelacionVOs = new PuntosRelacionVO[relation.getArraypoints().size()];
							RectangulosRelacionVO[] rectangulosRelacionVOs = new RectangulosRelacionVO[relation.getArrayRectangles().size()];
							for (int i = 0; i < relation.getArrayLines().size(); i++) {
								LineasRelacionVO lineasRelacionVO = new LineasRelacionVO();
								lineasRelacionVO.setX1(new Long(new Double(relation.getArrayLines().get(i).getP1().getX()).intValue()));
								lineasRelacionVO.setY1(new Long(new Double(relation.getArrayLines().get(i).getP1().getY()).intValue()));
								lineasRelacionVO.setX2(new Long(new Double(relation.getArrayLines().get(i).getP2().getX()).intValue()));
								lineasRelacionVO.setY2(new Long(new Double(relation.getArrayLines().get(i).getP2().getY()).intValue()));
								lineasRelacionVOs[i] = lineasRelacionVO;
							} 
							relacionService.updateLineasRelacion(lineasRelacionVOs, idRelacion);
							
							for (int i = 0; i < relation.getArraypoints().size(); i++) {
								PuntosRelacionVO puntosRelacionVO = new PuntosRelacionVO();
								puntosRelacionVO.setX(new Long(relation.getArraypoints().get(i).x));
								puntosRelacionVO.setY(new Long(relation.getArraypoints().get(i).y));
								puntosRelacionVOs[i] = puntosRelacionVO;
							}
							relacionService.updatePuntosRelacion(puntosRelacionVOs, idRelacion);
							
							for (int i = 0; i < relation.getArrayRectangles().size(); i++) {
								RectangulosRelacionVO rectangulosRelacionVO = new RectangulosRelacionVO();
								rectangulosRelacionVO.setX(new Long(relation.getArrayRectangles().get(i).x));
								rectangulosRelacionVO.setY(new Long(relation.getArrayRectangles().get(i).y));
								rectangulosRelacionVO.setHeight(new Long(relation.getArrayRectangles().get(i).height));
								rectangulosRelacionVO.setWidth(new Long(relation.getArrayRectangles().get(i).width));
								rectangulosRelacionVOs[i] = rectangulosRelacionVO;
							}
							relacionService.updateRectangulosRelacion(rectangulosRelacionVOs, idRelacion);
						}
					}
				}
			}
		}
	}
	
	public void saveAllDiagrams(ArrayList<Diagram> diagramList, ProcessGraphDocking processGraphDocking){
		for (Diagram diagram : diagramList) {
			diagram.setSaveStatus(false);
			saveDiagram(diagram, processGraphDocking); 
		}
	}
	
	public Diagram loadDiagrama(Long idDiagram){
		/*DiagramaService diagramaService = serviceLocator.getDiagramaService();
		StereotypeService stereotypeService = serviceLocator.getStereotypeService();
		RelacionService relacionService = serviceLocator.getRelacionService();*/

		Diagram diagram = new Diagram();
		DiagramaVO diagramaVO = diagramaService.getDiagrama(idDiagram);
		diagram.setApproved(diagramaVO.getApproved());

		String backGround = diagramaVO.getBackGround();
		String[] colorSplitted = backGround.split(",");
		int red = Integer.parseInt(colorSplitted[0]);
		int green = Integer.parseInt(colorSplitted[1]);
		int blue = Integer.parseInt(colorSplitted[2]);
		//Color background = new Color(red, green, blue);
		diagram.setBackground(new Color(red, green, blue));

		Rectangle diagramSize = new Rectangle(diagramaVO.getWidth().intValue(), diagramaVO.getHeight().intValue());
		diagram.setDiagramSize(diagramSize);

		diagram.setExecute(diagramaVO.getExecuteBoth());
		diagram.setExecuteHeight(diagramaVO.getExecuteHeight());
		diagram.setExecuteWidth(diagramaVO.getExecuteWidth());
		diagram.setId(diagramaVO.getIdentificador().intValue());

		diagram.setName(diagramaVO.getNameDiagram());
		diagram.setObjectParent(diagramaVO.getOwner().intValue());
		diagram.setType(diagramaVO.getTipo().getValue());
		diagram.setZoomFactor(diagramaVO.getZoomFactor().doubleValue());

		//calle
		if(diagram.getType() == "RGS"){
			SwinLine swinLine = new SwinLine();
			CalleVO calleVO = diagramaService.getCalle(diagramaVO.getId());
			swinLine.setId(calleVO.getIdentificador().intValue());
			NombresCalleVO[] nombresCalleVOs = diagramaService.getAllNombresCalle(calleVO.getId());
			AnchosCalleVO[] anchosCalleVOs = diagramaService.getAllAnchosCalle(calleVO.getId());
			swinLine.getArrayNameSwingLine().add(nombresCalleVOs[nombresCalleVOs.length - 1].getNombre());
			for (int i = 0; i < nombresCalleVOs.length - 1; i++) {
				swinLine.getArrayNameSwingLine().add(nombresCalleVOs[i].getNombre());
			}
			swinLine.getArrayWidths().add(anchosCalleVOs[anchosCalleVOs.length - 1].getWidth().intValue());
			for (int i = 0; i < anchosCalleVOs.length - 1; i++) {
				swinLine.getArrayWidths().add(anchosCalleVOs[i].getWidth().intValue());
			}
			diagram.setSwinLine(swinLine);
		}

		//stereotypes
		StereotypeVO[] stereotypeVOs = diagramaService.getAllStereotypes(diagramaVO.getId());
		for (StereotypeVO stereotypeVO : stereotypeVOs) {
			Stereotype stereotype = new Stereotype();
			stereotype.setInicialPoint(new Point(stereotypeVO.getPrimeraCoordenadaX().intValue(), stereotypeVO.getPrimeraCoordenadaY().intValue()));
			stereotype.setFinalPoint(new Point(stereotypeVO.getSegundaCoordenadaX().intValue(), stereotypeVO.getSegundaCoordenadaY().intValue()));
			stereotype.setId(stereotypeVO.getIdentificador().intValue());
			for (Integer diagramsIds : stereotypeVO.getDiagramas()) {
				stereotype.getDiagramIds().add(diagramsIds);
			}
			PropiedadesStereotypeVO propiedadesStereotypeVO = stereotypeService.getPropiedades(stereotypeVO.getId());

			String backGroundStereotype = propiedadesStereotypeVO.getBackGround();
			String[] splittedColor = backGroundStereotype.split(",");
			red = Integer.parseInt(splittedColor[0]);
			green = Integer.parseInt(splittedColor[1]);
			blue = Integer.parseInt(splittedColor[2]);
			//Color backgroundFinal = new Color(red, green, blue);
			stereotype.setBackground(new Color(red, green, blue));

			stereotype.setDescription(propiedadesStereotypeVO.getDescripcion());
			stereotype.setHeigth(propiedadesStereotypeVO.getHeight().intValue());

			String lineColor = propiedadesStereotypeVO.getLineColor();
			String[] splittedLineColor = lineColor.split(",");
			red = Integer.parseInt(splittedLineColor[0]);
			green = Integer.parseInt(splittedLineColor[1]);
			blue = Integer.parseInt(splittedLineColor[2]);
			//Color lineColorFinal = new Color(red, green, blue);
			stereotype.setLineColor(new Color(red, green, blue));

			stereotype.setName(propiedadesStereotypeVO.getName());
			stereotype.setParentId(diagramaVO.getIdentificador().intValue());
			stereotype.setSelected(propiedadesStereotypeVO.getSelected());
			/*      
				"Macro"
			    "Proc"
			    "SubProc"
				"ActServ"
				"ActManual"
				"Cliente"
				"Provee"
				"BaseDato"
				"Decision"
				"Nota"
				"EITiempo"
				"EIMensaje"
				"EInicial"
				"EFinal"
			 */
			if(propiedadesStereotypeVO.getTipo() == TipoStereotipoEnum.MACRO){
				stereotype.setType("Macro");
			}
			if(propiedadesStereotypeVO.getTipo() == TipoStereotipoEnum.PROC){
				stereotype.setType("Proc");
			}
			if(propiedadesStereotypeVO.getTipo() == TipoStereotipoEnum.SUB_PROC){
				stereotype.setType("SubProc");
			}
			if(propiedadesStereotypeVO.getTipo() == TipoStereotipoEnum.ACT_SERV){
				stereotype.setType("ActServ");
			}
			if(propiedadesStereotypeVO.getTipo() == TipoStereotipoEnum.ACT_MANUAL){
				stereotype.setType("ActManual");
			}
			if(propiedadesStereotypeVO.getTipo() == TipoStereotipoEnum.CLIENTE){
				stereotype.setType("Cliente");
			}
			if(propiedadesStereotypeVO.getTipo() == TipoStereotipoEnum.PROVEE){
				stereotype.setType("Provee");
			}
			if(propiedadesStereotypeVO.getTipo() == TipoStereotipoEnum.DECISION){
				stereotype.setType("Decision");
			}
			if(propiedadesStereotypeVO.getTipo() == TipoStereotipoEnum.NOTA){
				stereotype.setType("Nota");
			}
			if(propiedadesStereotypeVO.getTipo() == TipoStereotipoEnum.EITIEMPO){
				stereotype.setType("EITiempo");
			}
			if(propiedadesStereotypeVO.getTipo() == TipoStereotipoEnum.EIMENSAJE){
				stereotype.setType("EIMensaje");
			}
			if(propiedadesStereotypeVO.getTipo() == TipoStereotipoEnum.EINICIAL){
				stereotype.setType("EInicial");
			}
			if(propiedadesStereotypeVO.getTipo() == TipoStereotipoEnum.EFINAL){
				stereotype.setType("EFinal");
			}
			if(propiedadesStereotypeVO.getTipo() == TipoStereotipoEnum.BASE_DATO){
				stereotype.setType("BaseDato");
			}

			stereotype.setVisible(propiedadesStereotypeVO.getVisible());
			stereotype.setWidth(propiedadesStereotypeVO.getWidth().intValue());
			
			diagram.getPictureList().add(stereotype);
		}
		//relaciones
		for (StereotypeVO stereotypeVO : stereotypeVOs) {
			RelacionVO[] relacionVOs = stereotypeService.getAllRelations(stereotypeVO.getId());
			for (RelacionVO relacionVO : relacionVOs) {
				Relation relation = new Relation();
				relation.setId(relacionVO.getIdentificador().intValue());
				relation.setIntersection(new Point(relacionVO.getPrimeraInterseccion().intValue(), relacionVO.getSegundaInterseccion().intValue()));
				relation.setName(relacionVO.getName());
				relation.setOrientation(relacionVO.getOrientation());
				relation.setParentId(stereotypeVO.getIdentificador().intValue());
				relation.setSelected(relacionVO.getSelected());
				relation.setStereotype(diagram.findById(relacionVO.getStereotypeContent().intValue()));
				relation.setTypeRel(relacionVO.getTipo());
				//puntos, lineas y rectangulos
				PuntosRelacionVO[] puntosRelacionVOs = relacionService.getAllPuntosRelacion(relacionVO.getId());
				LineasRelacionVO[] lineasRelacionVOs = relacionService.getAllLineasRelacion(relacionVO.getId());
				RectangulosRelacionVO[] rectangulosRelacionVOs = relacionService.getAllRectangulosRelacion(relacionVO.getId());
				relation.getArraypoints().add(new Point(puntosRelacionVOs[puntosRelacionVOs.length - 1].getX().intValue(), puntosRelacionVOs[puntosRelacionVOs.length - 1].getY().intValue()));
				for (int i = 0; i < puntosRelacionVOs.length - 1; i++) {
					relation.getArraypoints().add(new Point(puntosRelacionVOs[i].getX().intValue(), puntosRelacionVOs[i].getY().intValue()));
				}
				relation.getArrayLines().add(new Line2D.Double(lineasRelacionVOs[lineasRelacionVOs.length - 1].getX1().intValue(), lineasRelacionVOs[lineasRelacionVOs.length - 1].getY1().intValue(),
						lineasRelacionVOs[lineasRelacionVOs.length - 1].getX2().intValue(), lineasRelacionVOs[lineasRelacionVOs.length - 1].getY2().intValue()));
				for (int i = 0; i < lineasRelacionVOs.length - 1; i++) {
					relation.getArrayLines().add(new Line2D.Double(lineasRelacionVOs[i].getX1().intValue(), lineasRelacionVOs[i].getY1().intValue(),
							lineasRelacionVOs[i].getX2().intValue(), lineasRelacionVOs[i].getY2().intValue()));
				}
				relation.getArrayRectangles().add(new Rectangle(rectangulosRelacionVOs[rectangulosRelacionVOs.length - 1].getX().intValue(), rectangulosRelacionVOs[rectangulosRelacionVOs.length - 1].getY().intValue(),
						rectangulosRelacionVOs[rectangulosRelacionVOs.length - 1].getWidth().intValue(), rectangulosRelacionVOs[rectangulosRelacionVOs.length - 1].getHeight().intValue()));
				for (int i = 0; i < rectangulosRelacionVOs.length - 1; i++) {
					relation.getArrayRectangles().add(new Rectangle(rectangulosRelacionVOs[i].getX().intValue(), rectangulosRelacionVOs[i].getY().intValue(),
							rectangulosRelacionVOs[i].getWidth().intValue(), rectangulosRelacionVOs[i].getHeight().intValue()));
				}
				diagram.findById(stereotypeVO.getIdentificador().intValue()).getRelatedStereotypes().add(relation);
			}
		}
		diagram.setSaveStatus(false);
		return diagram;
	}

	public int getExceptionStatus() {
		return exceptionStatus;
	}

	public void setExceptionStatus(int exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
	}
	
	public Vector<Integer> getListPararell() {
		return listPararell;
	}

	public void setListPararell(Vector<Integer> listPararell) {
		this.listPararell = listPararell;
	}
	
	public static void expandAll(JTree tree) {
		int row = 0;
		while (row < tree.getRowCount()) {
			tree.expandRow(row);
			row++;
		}
	}
	
	public static void collapseAll(JTree tree) {
		int row = tree.getRowCount() - 1;
		while (row >= 0) {
			tree.collapseRow(row);
			row--;
		}
	}
	
	public static void expandToLast(JTree tree) {
		// expand to the last leaf from the root
		DefaultMutableTreeNode  root;
		root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		tree.scrollPathToVisible(new TreePath(root.getLastLeaf().getPath()));
	}

}
