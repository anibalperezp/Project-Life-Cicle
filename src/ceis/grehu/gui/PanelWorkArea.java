package ceis.grehu.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import ceis.grehu.gui.paint.Diagram;
import ceis.grehu.gui.paint.FactoryModel;
import ceis.grehu.gui.paint.Relation;
import ceis.grehu.gui.paint.Stereotype;
import ceis.grehu.gui.paint.SwinLine;


public class PanelWorkArea extends JPanel implements MouseMotionListener, DropTargetListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TabbedPaneWorkArea tabbedPaneWorkArea = null;

	private boolean visibleResize = false;

	public PanelWorkArea(TabbedPaneWorkArea owner) {
		addMouseMotionListener(this); //handle mouse drags
		tabbedPaneWorkArea = owner;
		new DropTarget(this, this);
	}

	public TabbedPaneWorkArea getTabbedPaneWorkArea() {
		if(tabbedPaneWorkArea == null){
			tabbedPaneWorkArea = new TabbedPaneWorkArea(getTabbedPaneWorkArea().getProcessGraphDocking());//esto esta bien porque nunca se va a ejecutar
		}
		return tabbedPaneWorkArea;
	}

	public void mouseDragged(MouseEvent e) {
		Diagram diagramActive = getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().getActiveDiagram();
		if(getTabbedPaneWorkArea().isLeftClickPressed()){
			if(getTabbedPaneWorkArea().isRelationNote() || getTabbedPaneWorkArea().isTransicion()){
				getTabbedPaneWorkArea().setDrawline(true);
			}
			getTabbedPaneWorkArea().setMouseDragged(true);
			int x = new Double ( e.getX() / diagramActive.getZoomFactor() ).intValue();//aqui
			int y = new Double ( e.getY() / diagramActive.getZoomFactor() ).intValue();
			getTabbedPaneWorkArea().setPointMoved(new Point(x, y));

			if(getTabbedPaneWorkArea().isStereotypeReadyToWillResize()){
				getTabbedPaneWorkArea().setStereotypeReadyToResize(true);
				diagramActive.unselectAllStereotype();
			}
			if(getTabbedPaneWorkArea().isRelationToWillResize()){
				getTabbedPaneWorkArea().setRelationReadyToResize(true);
				diagramActive.unselectAllStereotype();
			}
			/*if(getTabbedPaneWorkArea().isSwinLineToWillResize()){
				getTabbedPaneWorkArea().setSwinLineReadyToResize(true);
				diagramActive.unselectAllStereotype();
			}*/
			Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
			scrollRectToVisible(r);
			Point point = new Point(x,y);
			getTabbedPaneWorkArea().getLabelPosition().setText("Posicion : "+ point.x +"."+ point.y);
			if(getTabbedPaneWorkArea().getPointMoved().x > getWidth() && getTabbedPaneWorkArea().getPointMoved().y < getHeight())
				point = new Point(getWidth()-1,getTabbedPaneWorkArea().getPointMoved().y);
			if(getTabbedPaneWorkArea().getPointMoved().y > getHeight() && getTabbedPaneWorkArea().getPointMoved().x < getWidth())
				point = new Point(getTabbedPaneWorkArea().getPointMoved().x,getHeight()-1);
			if(getTabbedPaneWorkArea().getPointMoved().y > getHeight() && getTabbedPaneWorkArea().getPointMoved().x > getWidth())
				point = new Point(getWidth()-1,getHeight()-1);
			if(getTabbedPaneWorkArea().getPointMoved().x < getX() && getTabbedPaneWorkArea().getPointMoved().y < getY())
				point = new Point(0,0);
			if(getTabbedPaneWorkArea().getPointMoved().x < getX() && getTabbedPaneWorkArea().getPointMoved().y > getY())
				point = new Point(0,getTabbedPaneWorkArea().getPointMoved().y);
			if(getTabbedPaneWorkArea().getPointMoved().x > getX() && getTabbedPaneWorkArea().getPointMoved().y < getY())
				point = new Point(getTabbedPaneWorkArea().getPointMoved().x,0);
		}
	}

	public void mouseMoved(MouseEvent e) {
		Diagram diagramActive = getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().getActiveDiagram();
		if(diagramActive != null){
			int x = new Double ( e.getX() / diagramActive.getZoomFactor() ).intValue();//aqui
			int y = new Double ( e.getY()/ diagramActive.getZoomFactor() ).intValue();
			Point pointMoving = new Point(x, y);
			getTabbedPaneWorkArea().setStereotypeLastSelected(diagramActive.getSelectedStereotype());
			//Point pointMoving = new Point(e.getPoint());
			if(getTabbedPaneWorkArea().getStereotypeLastSelected() != null){// hay uno seleccionado
				getTabbedPaneWorkArea().setPosOfRectToWillResize(getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().getPosOfSelectionRect(getTabbedPaneWorkArea().getStereotypeLastSelected().getSelectionRectangles(), pointMoving)); 
				if(getTabbedPaneWorkArea().getPosOfRectToWillResize() == 0 || getTabbedPaneWorkArea().getPosOfRectToWillResize() == 4){
					setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
					getTabbedPaneWorkArea().setStereotypeReadyToWillResize(true);
				}
				if(getTabbedPaneWorkArea().getPosOfRectToWillResize() == 1 || getTabbedPaneWorkArea().getPosOfRectToWillResize() == 5){
					setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
					getTabbedPaneWorkArea().setStereotypeReadyToWillResize(true);
				}
				if(getTabbedPaneWorkArea().getPosOfRectToWillResize() == 2 || getTabbedPaneWorkArea().getPosOfRectToWillResize() == 6){
					setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
					getTabbedPaneWorkArea().setStereotypeReadyToWillResize(true);
				}
				if(getTabbedPaneWorkArea().getPosOfRectToWillResize() == 3 || getTabbedPaneWorkArea().getPosOfRectToWillResize() == 7){
					setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
					getTabbedPaneWorkArea().setStereotypeReadyToWillResize(true);
				}
				if(getTabbedPaneWorkArea().getPosOfRectToWillResize() == -1){
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					getTabbedPaneWorkArea().setStereotypeReadyToWillResize(false);
				} 
			}else{//es null
				getTabbedPaneWorkArea().setStereotypeReadyToWillResize(false);
			}
			if(getTabbedPaneWorkArea().getRelationSelect() != null){
				if(getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().isPosOfselectionRect(getTabbedPaneWorkArea().getRelationSelect(), pointMoving)){
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					getTabbedPaneWorkArea().setRelationToWillResize(true);
				}
				else{
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					getTabbedPaneWorkArea().setRelationToWillResize(false);
				}
			}
			if(diagramActive.getSwinLine() != null){
				int pos = diagramActive.getSwinLine().posOfSwinLineSelected(pointMoving);
				int sum = diagramActive.getSwinLine().getSumOfWidths(pos);
				if(pointMoving.x == sum && pointMoving.x > 0){
					setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
					getTabbedPaneWorkArea().setSwinLineToWillResize(true);
				}
			}
			getTabbedPaneWorkArea().getLabelPosition().setText("Posicion : "+ pointMoving.x+"."+pointMoving.y);
		}
	}

	public void paintSwinLine(Graphics g, SwinLine swinLine){
		Graphics2D g2 = ( Graphics2D ) g;
		g2.setPaint( new Color( 0, 0, 0 ) );
		Stroke oldStroke = g2.getStroke();
		g2.setStroke( new BasicStroke( 2.5f ) );
		int sum = 0;
		Double zoomFactor = getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor();
		FontMetrics fm = getGraphics().getFontMetrics();

		for (int i = 0; i < swinLine.getArrayWidths().size(); i++) {
			sum = swinLine.getSumOfWidths(i);
			g2.draw(new Line2D.Double(sum, 0, sum, getHeight()/zoomFactor));
			int length = fm.charsWidth( swinLine.getArrayNameSwingLine().get(i).toCharArray(), 0 , swinLine.getArrayNameSwingLine().get(i).length());
			g2.drawString(swinLine.getArrayNameSwingLine().get(i), swinLine.getSumOfWidths(i - 1) + (swinLine.getArrayWidths().get(i) - length)/2, 15);
		}
		g2.draw(new Line2D.Double(0, 30/zoomFactor, sum, 30/zoomFactor));
		g2.setStroke(oldStroke);
	}

	public void paintResizingSwinLine(Graphics g, Point pointMoved){
		Graphics2D g2 = ( Graphics2D ) g;
		g2.setPaint( new Color( 0, 0, 0 ) );
		Stroke oldStroke = g2.getStroke();
		g2.setStroke( new BasicStroke( 2.5f ) );
		Double zoomFactor = getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor();
		g2.draw(new Line2D.Double(pointMoved.x, 0, pointMoved.x, getHeight()/zoomFactor));

		g2.setStroke(oldStroke);
	}

	public void paint(Graphics g) {
		super.paint(g);//ver si pasa algo raro
		Diagram diagram = getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().getActiveDiagram();
		if(diagram != null){
			//para habilitar botones copiar, cortar, eliminar
			if(diagram.getSelectedStereotypes().size() > 0){
				getTabbedPaneWorkArea().getProcessGraphDocking().getButtonCut().setEnabled(true);
				getTabbedPaneWorkArea().getProcessGraphDocking().getButtonCopy().setEnabled(true);
				getTabbedPaneWorkArea().getProcessGraphDocking().getButtonDelete().setEnabled(true);

				getTabbedPaneWorkArea().getProcessGraphDocking().getMenuItemCut().setEnabled(true);
				getTabbedPaneWorkArea().getProcessGraphDocking().getMenuItemCopy().setEnabled(true);
				getTabbedPaneWorkArea().getProcessGraphDocking().getMenuItemDelete().setEnabled(true);
			}else if(diagram.getSelectedRelations().size() > 0){
				getTabbedPaneWorkArea().getProcessGraphDocking().getButtonCut().setEnabled(false);
				getTabbedPaneWorkArea().getProcessGraphDocking().getButtonCopy().setEnabled(false);
				getTabbedPaneWorkArea().getProcessGraphDocking().getButtonDelete().setEnabled(true);

				getTabbedPaneWorkArea().getProcessGraphDocking().getMenuItemCut().setEnabled(false);
				getTabbedPaneWorkArea().getProcessGraphDocking().getMenuItemCopy().setEnabled(false);
				getTabbedPaneWorkArea().getProcessGraphDocking().getMenuItemDelete().setEnabled(true);
			}
			else{
				getTabbedPaneWorkArea().getProcessGraphDocking().getButtonDelete().setEnabled(false);
				getTabbedPaneWorkArea().getProcessGraphDocking().getButtonCut().setEnabled(false);
				getTabbedPaneWorkArea().getProcessGraphDocking().getButtonCopy().setEnabled(false);

				getTabbedPaneWorkArea().getProcessGraphDocking().getMenuItemDelete().setEnabled(false);
				getTabbedPaneWorkArea().getProcessGraphDocking().getMenuItemCut().setEnabled(false);
				getTabbedPaneWorkArea().getProcessGraphDocking().getMenuItemCopy().setEnabled(false);
			}
			if(diagram.isSaveStatus()){
				getTabbedPaneWorkArea().getProcessGraphDocking().getMenuItemSave().setEnabled(true);
				getTabbedPaneWorkArea().getProcessGraphDocking().getButtonSave().setEnabled(true);
				getTabbedPaneWorkArea().getProcessGraphDocking().getMenuItemSaveAll().setEnabled(true);
				getTabbedPaneWorkArea().getProcessGraphDocking().getButtonSaveAll().setEnabled(true);
				int indexOf = diagram.getName().indexOf("*");
				if(indexOf == -1){
					diagram.setName("*"+diagram.getName());
					int posTab = getTabbedPaneWorkArea().getTabbedPane().getSelectedIndex();
					getTabbedPaneWorkArea().getTabbedPane().setTitleAt(posTab, diagram.getName());
					getTabbedPaneWorkArea().getTabbedPane().setToolTipTextAt(posTab, diagram.getName());
					getTabbedPaneWorkArea().getProcessGraphDocking().getListTabsTitles().remove(posTab);
					getTabbedPaneWorkArea().getProcessGraphDocking().getListTabsTitles().add(posTab, diagram.getName());
				}
			}
			else{
				getTabbedPaneWorkArea().getProcessGraphDocking().getMenuItemSave().setEnabled(false);
				getTabbedPaneWorkArea().getProcessGraphDocking().getButtonSave().setEnabled(false);
				getTabbedPaneWorkArea().getProcessGraphDocking().getMenuItemSaveAll().setEnabled(false);
				getTabbedPaneWorkArea().getProcessGraphDocking().getButtonSaveAll().setEnabled(false);
				int indexOf = diagram.getName().indexOf("*");
				if(indexOf != -1){
					diagram.setName(diagram.getName().substring(1));
					int posTab = getTabbedPaneWorkArea().getTabbedPane().getSelectedIndex();
					getTabbedPaneWorkArea().getTabbedPane().setTitleAt(posTab, diagram.getName());
					getTabbedPaneWorkArea().getTabbedPane().setToolTipTextAt(posTab, diagram.getName());
					getTabbedPaneWorkArea().getProcessGraphDocking().getListTabsTitles().remove(posTab);
					getTabbedPaneWorkArea().getProcessGraphDocking().getListTabsTitles().add(posTab, diagram.getName());
				}
			}

			Graphics2D g2 = (Graphics2D)g;
			g2.scale( diagram.getZoomFactor(), diagram.getZoomFactor());	
			getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().paintBackGround(g, new Double(getWidth() / diagram.getZoomFactor()).intValue(), new Double(getHeight() / diagram.getZoomFactor()).intValue());

			//lo de las carreteras
			if(diagram.getSwinLine() != null){
				paintSwinLine(g, diagram.getSwinLine());
			}

			for(int i = 0 ; i < diagram.getLength(); i++){
				if( diagram.getStereotype(i).getType() == "Nota"){
					diagram.getStereotype(i).PaintStereotype(g, true);
					drawText(g, diagram.getStereotype(i));
				}
				else{
					diagram.getStereotype(i).PaintStereotype(g, true);
				}
			}
			if(diagram.getPictureList().size() > 0)
				getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().paintRelations(g, diagram.getPictureList());
			if(getTabbedPaneWorkArea().isMouseDragged() && !getTabbedPaneWorkArea().isStereotypeReadyToMove() && !getTabbedPaneWorkArea().isSomeStereotypeReadyToMove() && !getTabbedPaneWorkArea().isStereotypeReadyToResize() && !getTabbedPaneWorkArea().isRelationReadyToResize()
					&& !getTabbedPaneWorkArea().isSwinLineReadyToResize()){
				if(getTabbedPaneWorkArea().getProcessGraphDocking().getButtonPressed() == ""){
					Point point = new Point(getTabbedPaneWorkArea().getPointMoved());
					int width = new Double(getWidth()/diagram.getZoomFactor()).intValue();
					int height = new Double(getHeight()/diagram.getZoomFactor()).intValue();
					if(getTabbedPaneWorkArea().getPointMoved().x > width && getTabbedPaneWorkArea().getPointMoved().y < height)
						point = new Point(width-1,getTabbedPaneWorkArea().getPointMoved().y);
					if(getTabbedPaneWorkArea().getPointMoved().y > height && getTabbedPaneWorkArea().getPointMoved().x < width)
						point = new Point(getTabbedPaneWorkArea().getPointMoved().x,getHeight()-1);
					if(getTabbedPaneWorkArea().getPointMoved().y > height && getTabbedPaneWorkArea().getPointMoved().x > width)
						point = new Point(width-1,height-1);
					if(getTabbedPaneWorkArea().getPointMoved().x < getX() && getTabbedPaneWorkArea().getPointMoved().y < getY())
						point = new Point(0,0);
					if(getTabbedPaneWorkArea().getPointMoved().x < getX() && getTabbedPaneWorkArea().getPointMoved().y > getY())
						point = new Point(0,getTabbedPaneWorkArea().getPointMoved().y);
					if(getTabbedPaneWorkArea().getPointMoved().x > getX() && getTabbedPaneWorkArea().getPointMoved().y < getY())
						point = new Point(getTabbedPaneWorkArea().getPointMoved().x,0);
					getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().PaintSelectArea(g,getTabbedPaneWorkArea().getPointInicial(), point); 
				}
				else
					getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().PaintSelectArea(g,getTabbedPaneWorkArea().getPointInicial(), getTabbedPaneWorkArea().getPointMoved());
			}
			if(getTabbedPaneWorkArea().isStereotypeReadyToMove() && !getTabbedPaneWorkArea().isMouseDragged()  && !getTabbedPaneWorkArea().isStereotypeReadyToResize() && !getTabbedPaneWorkArea().isRelationReadyToResize()){//quizas validar mas
				getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().PaintBoundStereotype(g, getTabbedPaneWorkArea().getStereotypeSelect(), getTabbedPaneWorkArea().getPointInicial(), getTabbedPaneWorkArea().getXDifference(), getTabbedPaneWorkArea().getYDifference());
			}
			if(getTabbedPaneWorkArea().isStereotypeReadyToMove() && getTabbedPaneWorkArea().isMouseDragged() && !getTabbedPaneWorkArea().isStereotypeReadyToResize() && !getTabbedPaneWorkArea().isRelationReadyToResize()){//quizas validar mas
				/*
				 * validando la esquina superior en el mover
				 */
				Point point = new Point(getTabbedPaneWorkArea().getPointMoved());
				if(point.x - getTabbedPaneWorkArea().getXDifference() < 0 && point.y - getTabbedPaneWorkArea().getYDifference() < 0){
					//El 5,5 es porque dentro del paintbounds se le resta 3,3 al punto entonces quedara 2,2
					point = new Point(getTabbedPaneWorkArea().getXDifference() + 5, getTabbedPaneWorkArea().getYDifference() + 5);
				}else
					if(point.x - getTabbedPaneWorkArea().getXDifference() < 0 && point.y - getTabbedPaneWorkArea().getYDifference() > 0){
						point = new Point(getTabbedPaneWorkArea().getXDifference() + 5, getTabbedPaneWorkArea().getPointMoved().y);
					}else
						if(point.x - getTabbedPaneWorkArea().getXDifference() > 0 && point.y - getTabbedPaneWorkArea().getYDifference() < 0){
							point = new Point(getTabbedPaneWorkArea().getPointMoved().x, getTabbedPaneWorkArea().getYDifference() + 5);
						}
				/*
				 * validando la esquina superior al mover una figura
				 */
				if(getTabbedPaneWorkArea().isDrawline()){
					g.drawLine(getTabbedPaneWorkArea().getPointInicial().x, getTabbedPaneWorkArea().getPointInicial().y, getTabbedPaneWorkArea().getPointMoved().x, getTabbedPaneWorkArea().getPointMoved().y);
				} 
				else
					getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().PaintBoundStereotype(g, getTabbedPaneWorkArea().getStereotypeSelect(), /*getTabbedPaneWorkArea().getPointMoved()*/point, getTabbedPaneWorkArea().getXDifference(), getTabbedPaneWorkArea().getYDifference());
			} 
			if(getTabbedPaneWorkArea().isSomeStereotypeReadyToMove() && !getTabbedPaneWorkArea().isMouseDragged() && !getTabbedPaneWorkArea().isRelationReadyToResize()){
				getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().paintRectMultipleSelection(g, getTabbedPaneWorkArea().getPointInicial(), getTabbedPaneWorkArea().getXBigDifference(), getTabbedPaneWorkArea().getYBigDifference(), diagram.getMaxWidth(), diagram.getMaxHeigth()); 
			}
			if(getTabbedPaneWorkArea().isSomeStereotypeReadyToMove() && getTabbedPaneWorkArea().isMouseDragged() /*&& !getTabbedPaneWorkArea().isDrawline()*/ && !getTabbedPaneWorkArea().isRelationReadyToResize()){
				/*
				 * validando la esquina superior cuando se mueven muchas figuras
				 */
				Point point = new Point(getTabbedPaneWorkArea().getPointMoved());
				if(point.x - getTabbedPaneWorkArea().getXBigDifference() < 0 && point.y - getTabbedPaneWorkArea().getYBigDifference() < 0){
					point = new Point(getTabbedPaneWorkArea().getXBigDifference() + 5, getTabbedPaneWorkArea().getYBigDifference() + 5);
				}else
					if(point.x - getTabbedPaneWorkArea().getXBigDifference() < 0 && point.y - getTabbedPaneWorkArea().getYBigDifference() > 0){
						point = new Point(getTabbedPaneWorkArea().getXBigDifference() + 5, getTabbedPaneWorkArea().getPointMoved().y);
					}else
						if(point.x - getTabbedPaneWorkArea().getXBigDifference() > 0 && point.y - getTabbedPaneWorkArea().getYBigDifference() < 0){
							point = new Point(getTabbedPaneWorkArea().getPointMoved().x, getTabbedPaneWorkArea().getYBigDifference() + 5);
						}	
				getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().paintRectMultipleSelection(g, point, getTabbedPaneWorkArea().getXBigDifference(), getTabbedPaneWorkArea().getYBigDifference(), diagram.getMaxWidth(), diagram.getMaxHeigth());
				/*
				 * validando la esquina superior cuando se mueven muchas figuras
				 */
			}
			if(getTabbedPaneWorkArea().isStereotypeReadyToResize() && getTabbedPaneWorkArea().isMouseDragged() && !getTabbedPaneWorkArea().isRelationReadyToResize()){
				if(getTabbedPaneWorkArea().getStereotypeLastSelected() != null){
					getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().paintResizingStereotype(g,getTabbedPaneWorkArea().getStereotypeLastSelected(), getTabbedPaneWorkArea().getPosOfRectToWillResize(), getTabbedPaneWorkArea().getPointMoved());
				}
			}   
			if(getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().isCreatePanel() ){
				scrollRectToVisible(new Rectangle(0, 0, 1, 1));
				getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().setCreatePanel(false); 
			}
			if(getTabbedPaneWorkArea().isRelationReadyToResize() && getTabbedPaneWorkArea().isMouseDragged()
					/*&& !getTabbedPaneWorkArea().isSomeStereotypeReadyToMove() && !getTabbedPaneWorkArea().isStereotypeReadyToMove()
		    && !getTabbedPaneWorkArea().isStereotypeReadyToResize()*/){
				if(getTabbedPaneWorkArea().getRelationSelect() != null){
					ArrayList<Point2D> arrayPoints = getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().getPointsOfLine(getTabbedPaneWorkArea().getRelationSelect(), getTabbedPaneWorkArea().getPointInicial());
					if(arrayPoints.size() > 0){
						Point pointMoved = getTabbedPaneWorkArea().getPointMoved();
						if(pointMoved.x <= 0 && pointMoved.y <= 0){
							pointMoved = new Point(2, 2);
						}
						if(pointMoved.x <= 0 && pointMoved.y > 0){
							pointMoved = new Point(2, pointMoved.y);
						}
						if(pointMoved.x > 0 && pointMoved.y <= 0){
							pointMoved = new Point(pointMoved.x, 2);
						}
						getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().paintResizingRelation(g, getTabbedPaneWorkArea().getRelationSelect()
								, arrayPoints.get(0), arrayPoints.get(1), pointMoved);
					}
				}
			}
			if(getTabbedPaneWorkArea().isSwinLineReadyToResize() && getTabbedPaneWorkArea().isMouseDragged() && 
					!getTabbedPaneWorkArea().isStereotypeReadyToMove() && !getTabbedPaneWorkArea().isStereotypeReadyToResize()
					&& !getTabbedPaneWorkArea().isSomeStereotypeReadyToMove()&& !getTabbedPaneWorkArea().isRelationReadyToResize() ){
				paintResizingSwinLine(g, getTabbedPaneWorkArea().getPointMoved());
			}

			diagram.setRectVisible(getVisibleRect());

			double zoomFactor = diagram.getZoomFactor();

			int pictX = new Double((diagram.getMaxScrollPoint().x)* zoomFactor).intValue();
			int pictY = new Double((diagram.getMaxScrollPoint().y)* zoomFactor).intValue();
			int relationX = new Double((diagram.getMaxRelationPoint().x)* zoomFactor).intValue();
			int relationY = new Double((diagram.getMaxRelationPoint().y)* zoomFactor).intValue();

			if ( pictX > this.getWidth() && diagram.isExecute() == false && diagram.isExecuteWidth() == false &&
					diagram.isExecuteHeight() == false && isVisibleResize() == false) {
				int width = ( new Double( 2400/*800*/ * zoomFactor ).intValue() * 15 ) / 100;
				this.setSize( new Dimension( this.getWidth() + width , this.getHeight() ) );
				this.setPreferredSize( this.getSize() );
				Dimension sizeDiagramWindow = new Dimension( new Double( this.getBounds().getSize().getWidth() / zoomFactor ).intValue(),
						new Double( this.getBounds().getSize().getHeight() / zoomFactor ).intValue() );
				diagram.setDiagramSize( new Rectangle(sizeDiagramWindow));
				this.revalidate();
				getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().
				setSizeDiagramOverview(getTabbedPaneWorkArea().getProcessGraphDocking().getNavigator().getBounds()
						, getTabbedPaneWorkArea().getProcessGraphDocking().getNavigator().getPanelOverView());

				Rectangle visibleRect = this.getVisibleRect();
				getTabbedPaneWorkArea().getScrollPaneWorkArea().getViewport().setViewPosition( new Point( this.getWidth()- visibleRect.width, visibleRect.y) );
			}
			if ( pictY > this.getHeight() && diagram.isExecute() == false && diagram.isExecuteWidth() == false &&
					diagram.isExecuteHeight() == false && isVisibleResize() == false) {
				int height = ( new Double( 1400/*800*/ * zoomFactor ).intValue() * 15 ) / 100;
				this.setSize( new Dimension( this.getWidth(), this.getHeight() + height ) );
				this.setPreferredSize( this.getSize() );
				Dimension sizeDiagramWindow = new Dimension( new Double( this.getBounds().getSize().getWidth() / zoomFactor ).intValue(),
						new Double( this.getBounds().getSize().getHeight() / zoomFactor ).intValue() );
				diagram.setDiagramSize(new Rectangle(sizeDiagramWindow));
				this.revalidate();
				getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().
				setSizeDiagramOverview(getTabbedPaneWorkArea().getProcessGraphDocking().getNavigator().getBounds()
						, getTabbedPaneWorkArea().getProcessGraphDocking().getNavigator().getPanelOverView());
				Rectangle visibleRect = this.getVisibleRect();
				getTabbedPaneWorkArea().getScrollPaneWorkArea().getViewport().setViewPosition( new Point( visibleRect.x, this.getHeight() - visibleRect.height) );
			}
			if ( relationX > this.getWidth() && diagram.isExecute() == false && diagram.isExecuteWidth() == false &&
					diagram.isExecuteHeight() == false && isVisibleResize() == false) {
				int width = ( new Double( 2400/*800*/ * zoomFactor ).intValue() * 15 ) / 100;
				this.setSize( new Dimension( this.getWidth() + width , this.getHeight() ) );
				this.setPreferredSize( this.getSize() );
				Dimension sizeDiagramWindow = new Dimension( new Double( this.getBounds().getSize().getWidth() / zoomFactor ).intValue(),
						new Double( this.getBounds().getSize().getHeight() / zoomFactor ).intValue() );
				diagram.setDiagramSize( new Rectangle(sizeDiagramWindow));
				this.revalidate();
				getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().
				setSizeDiagramOverview(getTabbedPaneWorkArea().getProcessGraphDocking().getNavigator().getBounds()
						, getTabbedPaneWorkArea().getProcessGraphDocking().getNavigator().getPanelOverView());

				Rectangle visibleRect = this.getVisibleRect();
				getTabbedPaneWorkArea().getScrollPaneWorkArea().getViewport().setViewPosition( new Point( this.getWidth()- visibleRect.width, visibleRect.y) );
			}
			if ( relationY > this.getHeight() && diagram.isExecute() == false && diagram.isExecuteWidth() == false &&
					diagram.isExecuteHeight() == false && isVisibleResize() == false) {
				int height = ( new Double( 1400/*800*/ * zoomFactor ).intValue() * 15 ) / 100;
				this.setSize( new Dimension( this.getWidth(), this.getHeight() + height ) );
				this.setPreferredSize( this.getSize() );
				Dimension sizeDiagramWindow = new Dimension( new Double( this.getBounds().getSize().getWidth() / zoomFactor ).intValue(),
						new Double( this.getBounds().getSize().getHeight() / zoomFactor ).intValue() );
				diagram.setDiagramSize(new Rectangle(sizeDiagramWindow));
				this.revalidate();
				getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().
				setSizeDiagramOverview(getTabbedPaneWorkArea().getProcessGraphDocking().getNavigator().getBounds()
						, getTabbedPaneWorkArea().getProcessGraphDocking().getNavigator().getPanelOverView());
				Rectangle visibleRect = this.getVisibleRect();
				getTabbedPaneWorkArea().getScrollPaneWorkArea().getViewport().setViewPosition( new Point( visibleRect.x, this.getHeight() - visibleRect.height) );
			}
			if(diagram.isExecute()){
				this.setSize( new Dimension( this.getVisibleRect().width, this.getVisibleRect().height ) );
				this.setPreferredSize( this.getSize() );
				Dimension sizeDiagramWindow = new Dimension( new Double( this.getBounds().getSize().getWidth() / zoomFactor ).intValue(),
						new Double( this.getBounds().getSize().getHeight() / zoomFactor ).intValue() );
				diagram.setDiagramSize(new Rectangle(sizeDiagramWindow));
				this.revalidate();
				getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().
				setSizeDiagramOverview(getTabbedPaneWorkArea().getProcessGraphDocking().getNavigator().getBounds()
						, getTabbedPaneWorkArea().getProcessGraphDocking().getNavigator().getPanelOverView());
				diagram.setExecute(false);
			}
			else if(diagram.isExecuteWidth()){
				this.setSize( new Dimension( this.getVisibleRect().width, this.getHeight() ) );
				this.setPreferredSize( this.getSize() );
				Dimension sizeDiagramWindow = new Dimension( new Double( this.getBounds().getSize().getWidth() / zoomFactor ).intValue(),
						new Double( this.getBounds().getSize().getHeight() / zoomFactor ).intValue() );
				diagram.setDiagramSize(new Rectangle(sizeDiagramWindow));
				this.revalidate();
				getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().
				setSizeDiagramOverview(getTabbedPaneWorkArea().getProcessGraphDocking().getNavigator().getBounds()
						, getTabbedPaneWorkArea().getProcessGraphDocking().getNavigator().getPanelOverView());
				diagram.setExecuteWidth(false);
			}
			else if(diagram.isExecuteHeight()){
				this.setSize( new Dimension( this.getWidth(), this.getVisibleRect().height ) );
				this.setPreferredSize( this.getSize() );
				Dimension sizeDiagramWindow = new Dimension( new Double( this.getBounds().getSize().getWidth() / zoomFactor ).intValue(),
						new Double( this.getBounds().getSize().getHeight() / zoomFactor ).intValue() );
				diagram.setDiagramSize(new Rectangle(sizeDiagramWindow));
				this.revalidate();
				getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().
				setSizeDiagramOverview(getTabbedPaneWorkArea().getProcessGraphDocking().getNavigator().getBounds()
						, getTabbedPaneWorkArea().getProcessGraphDocking().getNavigator().getPanelOverView());
				diagram.setExecuteHeight(false);
			}
		}
		getTabbedPaneWorkArea().getProcessGraphDocking().getNavigator().repaint();
		setVisibleResize(false);
	}
	/**
	 * Making Component Drop Target
	 */
	public void dragEnter(DropTargetDragEvent evt) {
		// Called when the user is dragging and enters this drop target.
	}
	public void dragOver(DropTargetDragEvent evt) {
		// Called when the user is dragging and moves over this drop target.
	}
	public void dragExit(DropTargetEvent evt) {
		// Called when the user is dragging and leaves this drop target.
	}
	public void dropActionChanged(DropTargetDragEvent evt) {
		// Called when the user changes the drag action between copy or move.
	}
	public void drop(DropTargetDropEvent evt) {
		// Called when the user finishes or cancels the drag operation.
		try {
			Transferable t = evt.getTransferable();

			if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				evt.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
				String s = (String)t.getTransferData(DataFlavor.stringFlavor);
				evt.getDropTargetContext().dropComplete(true);
				//process(s)
				Integer id = new Integer(s); 
				Diagram diagram = getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().getActiveDiagram();
				diagram.unselectAllStereotype();
				Stereotype stereotype = diagram.findById(id.intValue());
				int width = stereotype.getWidth();
				int height = stereotype.getHeigth();
				Point pointIni = new Point(new Double(evt.getLocation().getX() / diagram.getZoomFactor()).intValue(),new Double(evt.getLocation().getY() / diagram.getZoomFactor()).intValue());
				stereotype.setInicialPoint(pointIni);
				stereotype.setWidth(width);
				stereotype.setHeigth(height);
				stereotype.setFinalPoint(new Point(pointIni.x + width, pointIni.y + height));
				stereotype.setVisible(true);
				stereotype.setSelected(true);
				int xInicial = stereotype.getInicialPoint().x + stereotype.getWidth()/2;
				int yInicial = stereotype.getInicialPoint().y + stereotype.getHeigth()/2;
				/*int xFinal = stereotype.getInicialPoint().x + stereotype.getWidth()/2;
				int yFinal = stereotype.getInicialPoint().y + stereotype.getHeigth()/2;*/
				Point point1 = new Point(xInicial, yInicial);
				for (Relation relationStereotype : stereotype.getRelatedStereotypes()) {
					int xFinal = relationStereotype.getStereotype().getInicialPoint().x + relationStereotype.getStereotype().getWidth()/2;
					int yFinal = relationStereotype.getStereotype().getInicialPoint().y + relationStereotype.getStereotype().getHeigth()/2;
					Point point2 = new Point(xFinal, yFinal);
					Relation relation = getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().calculatePointsRelation(point1, point2,stereotype,relationStereotype.getStereotype());
					relation.setTypeRel("Transicion");
					relation.setSelected(true);
					stereotype.updateRelation(relationStereotype.getStereotype(), relation);
				}
				ArrayList<Stereotype> array = new ArrayList<Stereotype>(getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().findRelationDestiny(stereotype));
				if( array.size() > 0){
					for (Stereotype stereotypeContent : array) {
						xInicial = stereotypeContent.getInicialPoint().x + stereotypeContent.getWidth()/2;
						yInicial = stereotypeContent.getInicialPoint().y + stereotypeContent.getHeigth()/2;
						int xFinal = stereotype.getInicialPoint().x + stereotype.getWidth()/2;
						int yFinal = stereotype.getInicialPoint().y + stereotype.getHeigth()/2;
						point1 = new Point(xInicial, yInicial);
						Point point2 = new Point(xFinal, yFinal);
						Relation relation = getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().calculatePointsRelation(point1, point2,stereotypeContent,stereotype);
						stereotypeContent.updateRelation(stereotype, relation);
					}
				}
				getTabbedPaneWorkArea().getProcessGraphDocking().getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToStereotype(stereotype));
				repaint();

			} else {
				evt.rejectDrop();
			}
		} catch (IOException e) {
			evt.rejectDrop();
		} catch (UnsupportedFlavorException e) {
			evt.rejectDrop();
		}

	}
	/**
	 * Making Component Drop Target
	 */


	public void drawText( Graphics pG, Stereotype stereotype ) {
		Graphics2D g2 = ( Graphics2D ) pG;
		g2.setColor( new Color(0, 0, 0) );
		Diagram diagram = getTabbedPaneWorkArea().getProcessGraphDocking().getPaintManager().getActiveDiagram();
		String text = stereotype.getDescription();
		String[] a = text.split("\n");
		FontMetrics fm = pG.getFontMetrics();
		double height = new Double(fm.getLineMetrics(a[0], pG).getHeight() /*- fm.getLineMetrics(a[0], pG).getLeading()*/).intValue();
		int lastPos = 0;
		for (int i = 0; i < a.length; i++) {
			height = new Double(fm.getLineMetrics(a[i], pG).getHeight() /*- fm.getLineMetrics(a[i], pG).getLeading()*/);
			g2.drawString( a[i], stereotype.getInicialPoint().x + 1, new Double(stereotype.getInicialPoint().y + 15 + 2 + height*i + 12 ).intValue());
			lastPos = i;
		}

		int division = new Double(getTabbedPaneWorkArea().getProcessGraphDocking().getJTextAreaNote().getBounds().getHeight()/height).intValue();
		if(lastPos + 1 > division ){
			if(stereotype.getHeigth() < (height*(lastPos + 1) + 15 + 30)){
				stereotype.setHeigth( new Double(height*(lastPos + 1) + 15 + 30).intValue());
				diagram.setHeightById(stereotype.getId(), new Double(height*(lastPos + 1) + 15 + 30).intValue());
			}
		}
	}

	public boolean isVisibleResize() {
		return visibleResize;
	}

	public void setVisibleResize(boolean visibleResize) {
		this.visibleResize = visibleResize;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
