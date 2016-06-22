package ceis.grehu.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;

import ceis.grehu.gui.paint.Diagram;
import ceis.grehu.gui.paint.FactoryModel;

public class PanelOverView extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Navigator navigator = null;

	private Double scale = 0.0;  //  @jve:decl-index=0:

	private Rectangle viewRectangle = null;

	private Rectangle scaledVisibleRectangle = null;

	private Point pointPressed = null;

	private ArrayList<Rectangle> listRectangles = null; //empiesa de izq a derecha empezando por el extremo superior
	//el ultimo rectangulo de la lista es el del medio

	private boolean dragable = false;

	private String changeZoom = null;

	private boolean autoSize = false;

	private Point pointDragged = null;

	private Rectangle resultResizeRectangle = null;  //  @jve:decl-index=0:

	private boolean resizingView = false;

	private boolean leftClick = false;


	public PanelOverView(Navigator parent) {
		super();
		navigator = parent;
		initialize();
		viewRectangle = null;
		listRectangles = new ArrayList<Rectangle>();
		this.dragable = false;
		this.changeZoom = "";
		this.autoSize = false;
	}
	/**
	 * This method initializes this
	 * 
	 */
	public PanelOverView returnThis(){
		return this;
	}

	private void initialize() {
		this.setLayout(null);
		this.setBackground(Color.white);
		this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {   
			public void mouseDragged(java.awt.event.MouseEvent e) {
				if(leftClick){
					pointDragged = new Point(e.getX(),e.getY());
					Diagram diagram = getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram();
					if(diagram != null && isDragable()){
						Rectangle rectangle = getDecreaseVisibleRectangle();
						rectangle = zoomOutVisibleRectDraw( rectangle );
						int difX = e.getX() - pointPressed.x;
						int difY = e.getY() - pointPressed.y;
						int x = new Double ( ( scaledVisibleRectangle.x + difX ) * getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor() ).intValue();
						int y = new Double ( ( scaledVisibleRectangle.y + difY ) * getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor() ).intValue();
						if ( x < 0 ) {
							x = 0;
						}
						if ( y < 0 ) {
							y = 0;
						}
						int width = new Double ( getWidth() * getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor() ).intValue();
						Rectangle rectangleDimension = zoomInVisibleRectDraw( scaledVisibleRectangle );
						if ( x > width - rectangleDimension.width - new Double ( 1 * getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor() ).intValue() ) {
							x = width - rectangleDimension.width - new Double ( 1 * getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor() ).intValue();
						}
						int height = new Double ( getHeight() * getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor()  ).intValue();
						if ( y > height - rectangleDimension.height - new Double ( 1 * getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor() ).intValue() ) {
							y = height - rectangleDimension.height - new Double ( 1 * getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor() ).intValue();
						}
						if(diagram.isExecute() == false){
							movedVisibleRectangle( new Point(new Double( x / scale ).intValue() , 
									new Double( y / scale ).intValue() ) ) ;
						}
					}
					if(diagram != null && getChangeZoom() != ""){//estoy agrandando el rectangulito
						resizingView = true;
					}
					if(diagram.getSelectedStereotype() == null){
						getNavigator().getProcessGraphDocking().getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToDiagram(diagram));
					}
					getNavigator().repaint();
				}
			}
			public void mouseMoved(java.awt.event.MouseEvent e) {
				if(getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram()!=null){
					repaint();
					//empiesa de izq a derecha empezando por el extremo superior
					//el ultimo rectangulo de la lista es el del medio
					int posOfRect = getNavigator().getProcessGraphDocking().getPaintManager().getPosOfSelectionRect(getListRectangles(), e.getPoint());
					if(getNavigator().getProcessGraphDocking().getPaintManager().isPointContentInRectangle(e.getPoint(), getViewRectangle())&& posOfRect == -1){
						//el punto esta dentro del rectangulo
						setCursor(new Cursor(Cursor.MOVE_CURSOR));
						dragable = true;
						changeZoom = "";
						autoSize = false;
					}else{
						if(posOfRect == 0 || posOfRect == 2){
							setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
							dragable = false;
							if(posOfRect == 0)
								changeZoom = "NW";
							else
								changeZoom = "SE";
							autoSize = false;
						}else
							if(posOfRect == 1 || posOfRect == 3){
								setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
								dragable = false;
								if(posOfRect == 1)
									changeZoom = "NE";
								else
									changeZoom = "SW";
								autoSize = false;
							}else   
								if(posOfRect == 4){
									setCursor(new Cursor(Cursor.HAND_CURSOR));
									dragable = false;
									changeZoom = "";
									autoSize = true;
								}else{
									setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
									dragable = false;
									changeZoom = "";
									autoSize = false;
									getListRectangles().clear();
								}
					}
				}
			}
		});
		this.addMouseListener(new java.awt.event.MouseAdapter() {   
			public void mouseReleased(java.awt.event.MouseEvent e) {   
				if(resizingView){
					getNavigator().getProcessGraphDocking().getJTextAreaNote().setVisible(false);
					getNavigator().getProcessGraphDocking().getJTextFieldName().setVisible(false);
					Rectangle rectPanelW = getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getRectVisible();
					Double newScaleW = rectPanelW.getWidth()/resultResizeRectangle.getWidth(); 
					Double newScaleH = rectPanelW.getHeight()/resultResizeRectangle.getHeight();
					Double scaleWanted = null;
					if(newScaleH <= newScaleW){
						scaleWanted = new Double(newScaleH);
					}else{
						scaleWanted = new Double(newScaleW);
					}
					getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().setZoomFactor(scaleWanted);

					Rectangle diagramWindowBounds = ( Rectangle ) getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getDiagramSize().clone();
					double newZoomFactor = getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor();
					diagramWindowBounds.width = new Double( diagramWindowBounds.width * newZoomFactor ).intValue();
					diagramWindowBounds.height = new Double( diagramWindowBounds.height * newZoomFactor ).intValue();
					getNavigator().getProcessGraphDocking().getTabbedPaneWorkArea().getPanelWorkArea().setPreferredSize(new Dimension(diagramWindowBounds.getSize().width , diagramWindowBounds.getSize().height));
					getNavigator().getProcessGraphDocking().getTabbedPaneWorkArea().getPanelWorkArea().revalidate();
					getNavigator().getProcessGraphDocking().getTabbedPaneWorkArea().getPanelWorkArea().setVisibleResize(true);
					getNavigator().getProcessGraphDocking().getPaintManager().setSizeDiagramOverview(getNavigator().getBounds(), returnThis());
					getNavigator().repaint();

					getNavigator().getProcessGraphDocking().getTabbedPaneWorkArea().repaint();

					getNavigator().getProcessGraphDocking().getTabbedPaneWorkArea().getScrollPaneWorkArea().
					getViewport().setViewPosition( new Point(new Double(resultResizeRectangle.x*newZoomFactor).intValue(),new Double(resultResizeRectangle.y*newZoomFactor).intValue()));
				}

				pointDragged = null;
				pointPressed = null;
				resultResizeRectangle = null;
				resizingView = false;
				dragable = false;
				changeZoom = "";
				autoSize = false;
				leftClick = false;
			}
			public void mousePressed(java.awt.event.MouseEvent e) {
				Diagram diagram = getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram();
				if(diagram !=null){
					if(e.getButton() == 1){
						leftClick = true;
						scaledVisibleRectangle = getDecreaseVisibleRectangle();
						scaledVisibleRectangle = zoomOutVisibleRectDraw( scaledVisibleRectangle );
						pointPressed = new Point(e.getPoint());
						if(isAutoSize() && getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getPictureList().size() > 0){
							autoSize();
						}else{
							if(isDragable() == false && getChangeZoom() == "" && isAutoSize() == false){
								Rectangle visibleRectangle = (Rectangle)getViewRectangle().clone();
								double width = visibleRectangle.getWidth()/2;
								double height  = visibleRectangle.getHeight()/2;
								getNavigator().getProcessGraphDocking().getTabbedPaneWorkArea().getScrollPaneWorkArea().getViewport().setViewPosition(new Point(new Double((e.getX() - (width))/scale*diagram.getZoomFactor()).intValue(),new Double((e.getY() - (height))/ scale*diagram.getZoomFactor()).intValue()));
							}
						}
					}
					getNavigator().getProcessGraphDocking().getTabbedPaneWorkArea().repaint();
					getNavigator().getProcessGraphDocking().getJTextFieldName().setVisible(false);
					getNavigator().getProcessGraphDocking().getJTextAreaNote().setVisible(false);
				}
			}
		});
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.scale(scale, scale);
		Diagram diagram = getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram();
		if(diagram != null){
			if(diagram.getSwinLine() != null){
				getNavigator().getProcessGraphDocking().getTabbedPaneWorkArea().getPanelWorkArea().paintSwinLine(g, diagram.getSwinLine());
			}
			if(diagram.getPictureList().size() > 0)
				getNavigator().getProcessGraphDocking().getPaintManager().paintRelations(g, diagram.getPictureList());
			for(int i = 0 ; i < diagram.getLength(); i++){
				diagram.getStereotype(i).PaintStereotype(g2, false);
			}
			if(resizingView){
				Point temp = new Point(pointDragged);
				if(pointDragged.x < 0 && pointDragged.y < 0)
					temp= new Point(0,0);
				if(pointDragged.x < 0 && pointDragged.y > 0)
					temp= new Point(0,pointDragged.y);
				if(pointDragged.x > 0 && pointDragged.y < 0)
					temp= new Point(pointDragged.x,0);
				if(pointDragged.x > this.getBounds().width && pointDragged.y > this.getBounds().height)
					temp= new Point(this.getBounds().width,this.getBounds().height);
				if(pointDragged.x > this.getBounds().width && pointDragged.y < this.getBounds().height)
					temp= new Point(this.getBounds().width,pointDragged.y);
				if(pointDragged.x < this.getBounds().width && pointDragged.y > this.getBounds().height)
					temp= new Point(pointDragged.x,this.getBounds().height);
				paintResizingVisibleRect(g, getViewRectangle(), temp);
			}else{
				paintMovingRect(g2);
			}
		}

	}
	public Navigator getNavigator() {
		return navigator;
	}
	public Double getScale() {
		return scale;
	}
	public void setScale(Double scale) {
		this.scale = scale;
	}

	private Rectangle zoomOutVisibleRectDraw( Rectangle pRectangle ) {
		Rectangle zoomOutRectangle = ( Rectangle ) pRectangle.clone();
		zoomOutRectangle.x = new Double( zoomOutRectangle.x / getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor() ).intValue();
		zoomOutRectangle.y = new Double( zoomOutRectangle.y / getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor() ).intValue(); 
		zoomOutRectangle.width = new Double( zoomOutRectangle.width / getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor() ).intValue(); 
		zoomOutRectangle.height = new Double( zoomOutRectangle.height / getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor() ).intValue();
		return zoomOutRectangle;
	}

	private Rectangle getDecreaseVisibleRectangle() {
		Rectangle drawVisibleRectangle = ( Rectangle ) getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getRectVisible().clone();
		int x = new Double ( drawVisibleRectangle.x * scale ).intValue();
		int y = new Double ( drawVisibleRectangle.y * scale ).intValue();
		int width = new Double ( drawVisibleRectangle.width * scale ).intValue();
		int height = new Double ( drawVisibleRectangle.height * scale ).intValue();
		return  new  Rectangle( x, y, width, height );
	}

	private Rectangle zoomInVisibleRectDraw( Rectangle pRectangle ) {
		Rectangle zoomInRectangle = ( Rectangle ) pRectangle.clone();
		zoomInRectangle.x = new Double( zoomInRectangle.x * getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor()  ).intValue();
		zoomInRectangle.y = new Double( zoomInRectangle.y * getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor() ).intValue(); 
		zoomInRectangle.width = new Double( zoomInRectangle.width * getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor() ).intValue(); 
		zoomInRectangle.height = new Double( zoomInRectangle.height * getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor() ).intValue(); 
		return zoomInRectangle;
	}

	private void movedVisibleRectangle( Point pPoint ) {
		getNavigator().getProcessGraphDocking().getTabbedPaneWorkArea().getScrollPaneWorkArea().getViewport().setViewPosition( new Point( pPoint.x , pPoint.y ) );
		getNavigator().getProcessGraphDocking().getTabbedPaneWorkArea().repaint();
	}

	public void paintMovingRect(Graphics2D pG){
		pG.setColor(Color.BLUE );
		if(getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram() != null){
			if(getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getRectVisible() != null){
				Rectangle drawVisibleRectangle = ( Rectangle ) getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getRectVisible().clone();
				drawVisibleRectangle = zoomOutVisibleRectDraw( drawVisibleRectangle );
				int width = new Double( this.getSize().width / scale ).intValue();
				int height = new Double( this.getSize().height / scale ).intValue();
				if ( drawVisibleRectangle.width > width ) {
					drawVisibleRectangle.width = width - 10;
				}
				if ( drawVisibleRectangle.height > height ) {
					drawVisibleRectangle.height = height - 10;
				}
				pG.draw( drawVisibleRectangle );
				viewRectangle = new Rectangle((int)(drawVisibleRectangle.x*scale),(int)(drawVisibleRectangle.y*scale),(int)(drawVisibleRectangle.width*scale),(int)(drawVisibleRectangle.height*scale));
				Rectangle rect = drawVisibleRectangle;

				Point point = new Point( rect.x - 10, rect.y - 10 );
				pG.fillRect( point.x, point.y, 24, 24 );//izq sup
				listRectangles.add(new Rectangle((int)(point.x*scale), (int)(point.y*scale), (int)(24*scale), (int)(24*scale)));

				point = new Point( rect.x + rect.width - 10, rect.y - 10 );
				pG.fillRect( point.x, point.y, 24, 24 );//der sup
				//listRectangles.add(new Rectangle(point.x, point.y, 24, 24));
				listRectangles.add(new Rectangle((int)(point.x*scale), (int)(point.y*scale), (int)(24*scale), (int)(24*scale)));

				point = new Point( rect.x + rect.width - 10, rect.y + rect.height - 10 );
				pG.fillRect( point.x, point.y, 24, 24 );//der inf
				listRectangles.add(new Rectangle((int)(point.x*scale), (int)(point.y*scale), (int)(24*scale), (int)(24*scale)));

				point = new Point( rect.x - 10, rect.y + rect.height - 10 );
				pG.fillRect( point.x, point.y, 24, 24 );//izq inf
				listRectangles.add(new Rectangle((int)(point.x*scale), (int)(point.y*scale), (int)(24*scale), (int)(24*scale)));

				Rectangle litleRectangle = new Rectangle( drawVisibleRectangle.x + drawVisibleRectangle.width - ( drawVisibleRectangle.width / 3 ) , 
						drawVisibleRectangle.y + drawVisibleRectangle.height - 22, 45, 45 );
				pG.setColor( new Color( 255, 255, 255 ) );
				pG.fill( litleRectangle ); 	
				listRectangles.add(new Rectangle((int)(litleRectangle.x*scale),(int)(litleRectangle.y*scale),(int)(litleRectangle.width*scale),(int)(litleRectangle.height*scale)));
				pG.setColor( Color.BLUE );
				Rectangle centerRectangle = new Rectangle( litleRectangle.x + ( litleRectangle.width /2 ) , 
						litleRectangle.y + ( litleRectangle.height /2 ), 8, 8);
				pG.fill( centerRectangle );
				pG.draw( litleRectangle );
			}
		}
	}
	public Rectangle getViewRectangle() {
		Rectangle rectangle = null;
		if(viewRectangle != null)
			rectangle = viewRectangle;
		else
			rectangle = new Rectangle();
		return rectangle;
	}

	public ArrayList<Rectangle> getListRectangles() {
		return listRectangles;
	}

	public Rectangle getScaledVisibleRectangle() {
		return scaledVisibleRectangle;
	}

	public Point getPointPressed() {
		return pointPressed;
	}

	public boolean isDragable() {
		return dragable;
	}

	public String getChangeZoom() {
		return changeZoom;
	}
	public boolean isAutoSize() {
		return autoSize;
	}

	private void autoSize(){
		Diagram diagram = getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram();
		diagram.setRectVisible(getNavigator().getProcessGraphDocking().getTabbedPaneWorkArea().getPanelWorkArea().getVisibleRect());
		if(diagram != null){
			//Rectangle rectangleIni = diagram.getBoundsRectOfList(diagram.getPictureList());
			Rectangle content = new Rectangle(diagram.getBoundsRectOfList(diagram.getPictureList()));
			Rectangle container = new Rectangle(zoomOutVisibleRectDraw(diagram.getRectVisible()));
			//rectangleIni = zoomOutVisibleRectDraw(rectangleIni);
			Rectangle drawVisibleRectangle = null;
			String cases = "";
			//si no esta contenido dentro del visible
			if(!getNavigator().getProcessGraphDocking().getPaintManager().isRectangleContentInRectangle(container, content)){
				//se pasa por la esquina superior isquierda || 
				//se pasa por externo superior izquierdo ||
				//se pasa por externo superior centro-izquierdo || 
				//se pasa por externo izquierdo centro-superior
				if((content.x < container.x && 
						content.y < container.y && 
						content.x + content.width > container.x && 
						content.y + content.height > container.y &&
						content.x + content.width < container.x + container.width &&
						content.y + content.height < container.y + container.height) || (
								content.x + content.width < container.x &&
								content.y + content.height < container.y) || (
										content.x < container.x &&
										content.y + content.height < container.y &&
										content.x + content.width > container.x &&
										content.x + content.width < container.x + container.width) || (
												content.x + content.width < container.x &&
												content.y < container.y &&
												content.y + content.height > container.y &&
												content.y + content.height < container.y + container.height)){
					drawVisibleRectangle = new Rectangle(diagram.getMinScrollPoint().x,diagram.getMinScrollPoint().y,
							diagram.getRectVisible().width + diagram.getRectVisible().x - diagram.getMinScrollPoint().x, 
							diagram.getRectVisible().height + diagram.getRectVisible().y - diagram.getMinScrollPoint().y);
					cases = "cornerUpLeft";
				}
				// se pasa por el centro superior || 
				//se pasa por externo superior medio
				if((content.x > container.x && 
						content.x < container.x + container.width &&
						content.y < container.y && 
						content.y + content.height > container.y && 
						content.x + content.width < container.x + container.width && 
						content.y + content.height < container.y + container.height ) || (
								content.x > container.x &&
								content.x < container.x + container.width &&
								content.y + content.height < container.y &&
								content.x + content.width > container.x &&
								content.x + content.width < container.x + container.width)){
					drawVisibleRectangle = new Rectangle(diagram.getRectVisible().x,diagram.getMinScrollPoint().y,
							diagram.getRectVisible().width, 
							diagram.getRectVisible().height + diagram.getRectVisible().y - diagram.getMinScrollPoint().y);
					cases = "centerUp";
				}
				//se pasa por la esquina superior derecha || 
				//se pasa por externo superior derecho || 
				//se pasa por externo superior centro-derecho || 
				// se pasa por externo derecho medio-superior
				if((content.x > container.x &&
						content.x < container.x + container.width &&
						content.y < container.y &&
						content.y + content.height > container.y &&
						content.x + content.width > container.x + container.width &&
						content.y + content.height < container.y + container.height) || (
								content.x > container.x + container.width &&
								content.y + content.height < container.y) || (
										content.x > container.x &&
										content.x < container.x + container.width &&
										content.y + content.height < container.y &&
										content.x + content.width > container.x + container.width) || (
												content.x > container.x + container.width &&
												content.y < container.y &&
												content.y + content.height > container.y &&
												content.y + content.height < container.y + container.height)){
					drawVisibleRectangle = new Rectangle(diagram.getRectVisible().x,diagram.getMinScrollPoint().y,
							diagram.getMaxScrollPoint().x - diagram.getRectVisible().x, 
							diagram.getRectVisible().height + diagram.getRectVisible().y - diagram.getMinScrollPoint().y);
					cases = "cornerUpRigth";
				}
				//se pasa por centro derecho ||
				//se pasa por externo medio derecho
				if((content.x > container.x &&
						content.y > container.y &&
						content.x < container.x + container.width &&
						content.y < container.y + container.height &&
						content.x + content.width > container.x + container.width &&
						content.y + content.height < container.y + container.height) || (
								content.x > container.x + container.width &&
								content.y > container.y &&
								content.y < container.y + container.height &&
								content.y + content.height > container.y &&
								content.y + content.height < container.y + container.height)){
					drawVisibleRectangle = new Rectangle(diagram.getRectVisible().x,diagram.getRectVisible().y,
							diagram.getMaxScrollPoint().x - diagram.getRectVisible().x, 
							diagram.getRectVisible().height);
					cases = "centerRigth";
				}
				//se pasa por esquina inferior derecha || 
				//se pasa por el externo inferior derecho ||
				//se pasa por externo inferior centro-derecho ||
				//se pasa por externo derecho medio-inferior
				if((content.x > container.x &&
						content.y > container.y &&
						content.x < container.x + container.width &&
						content.y < container.y + container.height &&
						content.x + content.width > container.x + container.width &&
						content.y + content.height > container.y + container.height) || (
								content.x > container.x + container.width &&
								content.y > container.y + container.height) || (
										content.x > container.x &&
										content.x < container.x + container.width &&
										content.y > container.y + container.height &&
										content.x + content.width > container.x + container.width) || (
												content.x > container.x + container.width &&
												content.y > container.y &&
												content.y < container.y + container.height &&
												content.y + content.height > container.y + container.height)){
					drawVisibleRectangle = new Rectangle(diagram.getRectVisible().x,diagram.getRectVisible().y,
							diagram.getMaxScrollPoint().x - diagram.getRectVisible().x, 
							diagram.getMaxScrollPoint().y - diagram.getRectVisible().y);
					cases = "cornerDownRigth";
				}
				//se pasa por el centro inferior ||
				// se pasa por externo medio inferior
				if((content.x > container.x &&
						content.y > container.y &&
						content.x < container.x + container.width &&
						content.y < container.y + container.height &&
						content.x + content.width < container.x + container.width &&
						content.y + content.height > container.y + container.height) || (
								content.x > container.x &&
								content.x < container.x + container.width &&
								content.y > container.y + container.height &&
								content.x + content.width > container.x &&
								content.x + content.width < container.x + container.width)){
					drawVisibleRectangle = new Rectangle(diagram.getRectVisible().x,diagram.getRectVisible().y,
							diagram.getRectVisible().width, 
							diagram.getMaxScrollPoint().y - diagram.getRectVisible().y);
					cases = "centerDown";
				}
				//se pasa por la esquina inferior izquierda ||
				//se pasa por externo inferior izquierdo ||
				//se pasa por externo inferior centro-izquierdo ||
				//se pasa por externo izquierdo medio-inferior
				if((content.x < container.x &&
						content.y > container.y &&
						content.y < container.y + container.height &&
						content.x + content.width > container.x &&
						content.x + content.width < container.x + container.width &&
						content.y + content.height > container.y + container.height) || (
								content.x + content.width < container.x &&
								content.y > container.y + container.height) || (
										content.x < container.x &&
										content.y > container.y + container.height &&
										content.x + content.width > container.x &&
										content.x + content.width < container.x + container.width) || (
												content.x + content.width < container.x &&
												content.y > container.y &&
												content.y < container.y + container.height &&
												content.y + content.height > container.y + container.height)){
					drawVisibleRectangle = new Rectangle(diagram.getMinScrollPoint().x,diagram.getRectVisible().y,
							diagram.getRectVisible().width + diagram.getRectVisible().x - diagram.getMinScrollPoint().x, 
							diagram.getMaxScrollPoint().y - diagram.getRectVisible().y);
					cases = "cornerDownLeft";
				}
				//se pasa por el centro izquierdo || 
				//se pasa por externo medio izquierdo
				if((content.x < container.x &&
						content.y > container.y &&
						content.y < container.y + container.height &&
						content.x + content.width > container.x &&
						content.x + content.width < container.x + container.width &&
						content.y + content.height < container.y + container.height) || (
								content.x + content.width < container.x &&
								content.y > container.y && 
								content.y < container.y + container.height &&
								content.y + content.height > container.y &&
								content.y + content.height < container.y + container.height)){
					drawVisibleRectangle = new Rectangle(diagram.getMinScrollPoint().x,diagram.getRectVisible().y,
							diagram.getRectVisible().width + diagram.getRectVisible().x - diagram.getMinScrollPoint().x, 
							diagram.getRectVisible().height);
					cases = "centerLeft";
				}
				//se pasa por la porcion superior || 
				// se pasa por porcion externo superior
				if((content.x < container.x && 
						content.y < container.y &&
						content.x + content.width > container.x + container.width &&
						content.y + content.height > container.y &&
						content.y + content.height < container.y + container.height) || (
								content.y + content.height < container.y &&
								content.x < container.x &&
								content.x + content.width > container.x + container.width)){
					drawVisibleRectangle = new Rectangle(diagram.getMinScrollPoint().x,diagram.getMinScrollPoint().y,
							diagram.getMaxScrollPoint().x - diagram.getMinScrollPoint().x, 
							diagram.getRectVisible().height + diagram.getRectVisible().y - diagram.getMinScrollPoint().y);
					cases = "portionUp";
				}
				//se pasa por la porcion derecha || 
				//se pasa por porcion externo derecha
				if((content.x < container.x + container.width &&
						content.x > container.x &&
						content.y < container.y &&
						content.x + content.width > container.x + container.width &&
						content.y + content.height > container.y + container.height) || (
								content.x > container.x + container.width &&
								content.y < container.y &&
								content.y + content.height > container.y + container.height)){
					drawVisibleRectangle = new Rectangle(diagram.getRectVisible().x,diagram.getMinScrollPoint().y,
							diagram.getMaxScrollPoint().x - diagram.getRectVisible().x, 
							diagram.getMaxScrollPoint().y - diagram.getMinScrollPoint().y);
					cases = "portionRigth";
				}
				//se pasa por la porcion inferior || 
				//se pasa por porcion externo inferior
				if((content.x < container.x &&
						content.y > container.y &&
						content.y < container.y + container.height &&
						content.x + content.width > container.x + container.width &&
						content.y + content.height > container.y + container.height) || (
								content.x < container.x &&
								content.y > container.y + container.height &&
								content.x + content.width > container.x + container.width)){
					drawVisibleRectangle = new Rectangle(diagram.getMinScrollPoint().x,diagram.getRectVisible().y,
							diagram.getMaxScrollPoint().x - diagram.getMinScrollPoint().x, 
							diagram.getMaxScrollPoint().y - diagram.getRectVisible().y);
					cases = "portionDown";
				}
				//se pasa por la porcion izquierda ||
				// se pasa por porcion externo izquierdo
				if((content.x < container.x &&
						content.y < container.y &&
						content.x + content.width > container.x &&
						content.x + content.width < container.x + container.width &&
						content.y + content.height > container.y + container.height) || (
								content.x + content.width < container.x &&
								content.y < container.y &&
								content.y + content.height > container.y + container.height)){
					drawVisibleRectangle = new Rectangle(diagram.getMinScrollPoint().x,diagram.getMinScrollPoint().y,
							diagram.getRectVisible().width + diagram.getRectVisible().x - diagram.getMinScrollPoint().x, 
							diagram.getMaxScrollPoint().y - diagram.getMinScrollPoint().y);
					cases = "portionLeft";
				}		// se pasa por todos lados
				if(content.x < container.x &&
						content.y < container.y &&
						content.x + content.width > container.x + container.width &&
						content.y + content.height > container.y + container.height){
					drawVisibleRectangle = new Rectangle(diagram.getMinScrollPoint().x,diagram.getMinScrollPoint().y,
							diagram.getMaxScrollPoint().x - diagram.getMinScrollPoint().x, 
							diagram.getMaxScrollPoint().y - diagram.getMinScrollPoint().y);
					cases = "allCorners";
				}
				// se pasa por porcion medio horizontal
				if(content.x < container.x &&
						content.y > container.y &&
						content.y < container.y + container.height &&
						content.x + content.width > container.x + container.width &&
						content.y + content.height > container.y &&
						content.y + content.height < container.y + container.height){
					drawVisibleRectangle = new Rectangle(diagram.getMinScrollPoint().x,diagram.getRectVisible().y,
							diagram.getMaxScrollPoint().x - diagram.getMinScrollPoint().x, 
							diagram.getRectVisible().height);
					cases = "portionMiddleHorizontal";
				}
				//se pasa por porcion medio vertical
				if(content.x > container.x &&
						content.x < container.x + container.width &&
						content.y < container.y &&
						content.x + content.width > container.x &&
						content.x + content.width < container.x + container.width &&
						content.y + content.height > container.y + container.height){
					drawVisibleRectangle = new Rectangle(diagram.getRectVisible().x,diagram.getMinScrollPoint().y,
							diagram.getRectVisible().width, 
							diagram.getMaxScrollPoint().y - diagram.getMinScrollPoint().y);
					cases = "portionMiddleVertical";
				}

				drawVisibleRectangle = zoomOutVisibleRectDraw(drawVisibleRectangle);
				Double scaleWanted = null;
				double scaleNeededWidth = (diagram.getRectVisible().getWidth()/diagram.getZoomFactor()) /drawVisibleRectangle.getWidth();
				double scaleNeededHeight = (diagram.getRectVisible().getHeight()/diagram.getZoomFactor()) /drawVisibleRectangle.getHeight();
				if(scaleNeededHeight <= scaleNeededWidth){
					scaleWanted = new Double(scaleNeededHeight);
				}else{
					scaleWanted = new Double(scaleNeededWidth);
				}
				diagram.setZoomFactor(scaleWanted);
				Rectangle diagramWindowBounds = ( Rectangle ) getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getDiagramSize().clone();
				double newZoomFactor = getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor();
				diagramWindowBounds.width = new Double( diagramWindowBounds.width * newZoomFactor ).intValue();
				diagramWindowBounds.height = new Double( diagramWindowBounds.height * newZoomFactor ).intValue();
				getNavigator().getProcessGraphDocking().getTabbedPaneWorkArea().getPanelWorkArea().setPreferredSize(new Dimension(diagramWindowBounds.getSize().width , diagramWindowBounds.getSize().height));
				getNavigator().getProcessGraphDocking().getTabbedPaneWorkArea().getPanelWorkArea().revalidate();
				getNavigator().getProcessGraphDocking().getTabbedPaneWorkArea().repaint();
				if(cases == "cornerUpLeft" || cases == "portionUp" || cases == "portionLeft" || cases == "allCorners")
					getNavigator().getProcessGraphDocking().getTabbedPaneWorkArea().getScrollPaneWorkArea().getViewport().setViewPosition(new Point(new Double((diagram.getMinScrollPoint().getX())* newZoomFactor).intValue(), new Double((diagram.getMinScrollPoint().getY())* newZoomFactor).intValue()));
				if(cases == "centerUp" || cases == "cornerUpRigth" || cases == "portionRigth" || cases == "portionMiddleVertical")
					getNavigator().getProcessGraphDocking().getTabbedPaneWorkArea().getScrollPaneWorkArea().getViewport().setViewPosition(new Point(new Double(diagram.getRectVisible().getX()* newZoomFactor).intValue(),new Double(diagram.getMinScrollPoint().getY()* newZoomFactor).intValue()));
				if(cases == "centerRigth" || cases == "cornerDownRigth" || cases == "centerDown")
					getNavigator().getProcessGraphDocking().getTabbedPaneWorkArea().getScrollPaneWorkArea().getViewport().setViewPosition(new Point(new Double(diagram.getRectVisible().getX()* newZoomFactor).intValue(),new Double(diagram.getRectVisible().getY()* newZoomFactor).intValue()));
				if(cases == "cornerDownLeft" || cases == "centerLeft" || cases == "portionDown" || cases == "portionMiddleHorizontal")
					getNavigator().getProcessGraphDocking().getTabbedPaneWorkArea().getScrollPaneWorkArea().getViewport().setViewPosition(new Point(new Double((diagram.getMinScrollPoint().getX())* newZoomFactor).intValue(), new Double((diagram.getRectVisible().getY())* newZoomFactor).intValue()));
				getNavigator().getProcessGraphDocking().getPaintManager().setSizeDiagramOverview(getNavigator().getBounds(), this);

			}
		}
	}

	private void paintResizingVisibleRect(Graphics g, Rectangle rectangle, Point pointDragging){
		Color colorOld = g.getColor();
		g.setColor(Color.BLUE);
		Point temp = new Point(pointDragging);
		if(getChangeZoom() == "NW"){
			int w = (rectangle.x + rectangle.width) - pointDragging.x;
			int h = (rectangle.y + rectangle.height) - pointDragging.y;
			if(w < 40 && h < 30){
				temp = new Point((rectangle.x + rectangle.width) - 40,(rectangle.y + rectangle.height) - 30);
				g.drawRect(new Double(temp.x/scale).intValue(), new Double(temp.y/scale).intValue() , new Double(40/scale ).intValue(), new Double(30/scale).intValue());
				resultResizeRectangle = new Rectangle(new Double(temp.x/scale).intValue(), new Double(temp.y/scale).intValue() , new Double(40/scale ).intValue(), new Double(30/scale).intValue());
			}else
				if(w > 40 && h < 30){
					temp = new Point(pointDragging.x,(rectangle.y + rectangle.height) - 30);
					g.drawRect(new Double(temp.x/scale).intValue(), new Double(temp.y/scale).intValue() , new Double(w/scale ).intValue(), new Double(30/scale).intValue());
					resultResizeRectangle = new Rectangle(new Double(temp.x/scale).intValue(), new Double(temp.y/scale).intValue() , new Double(w/scale ).intValue(), new Double(30/scale).intValue());
				}else
					if(w < 40 && h > 30){
						temp = new Point((rectangle.x + rectangle.width) - 40,pointDragging.y);
						g.drawRect(new Double(temp.x/scale).intValue(), new Double(temp.y/scale).intValue() , new Double(40/scale ).intValue(), new Double(h/scale).intValue());
						resultResizeRectangle = new Rectangle(new Double(temp.x/scale).intValue(), new Double(temp.y/scale).intValue() , new Double(40/scale ).intValue(), new Double(h/scale).intValue());
					}else{
						g.drawRect(new Double(temp.x/scale).intValue(), new Double(temp.y/scale).intValue() , new Double(w/scale ).intValue(), new Double(h/scale).intValue());
						resultResizeRectangle = new Rectangle(new Double(temp.x/scale).intValue(), new Double(temp.y/scale).intValue() , new Double(w/scale ).intValue(), new Double(h/scale).intValue());
					}
		}
		if(changeZoom == "SE"){
			int w = pointDragging.x - rectangle.x;
			int h = pointDragging.y - rectangle.y;
			if(w < 40 && h < 30){
				g.drawRect(new Double(rectangle.x/scale).intValue(), new Double(rectangle.y/scale).intValue() , new Double(40/scale ).intValue(), new Double(30/scale).intValue());
				resultResizeRectangle = new Rectangle(new Double(rectangle.x/scale).intValue(), new Double(rectangle.y/scale).intValue() , new Double(40/scale ).intValue(), new Double(30/scale).intValue());
			}else
				if(w > 40 && h < 30){
					g.drawRect(new Double(rectangle.x/scale).intValue(), new Double(rectangle.y/scale).intValue() , new Double(w/scale ).intValue(), new Double(30/scale).intValue());
					resultResizeRectangle = new Rectangle(new Double(rectangle.x/scale).intValue(), new Double(rectangle.y/scale).intValue() , new Double(w/scale ).intValue(), new Double(30/scale).intValue());
				}else
					if(w < 40 && h > 30){
						g.drawRect(new Double(rectangle.x/scale).intValue(), new Double(rectangle.y/scale).intValue() , new Double(40/scale ).intValue(), new Double(h/scale).intValue());
						resultResizeRectangle = new Rectangle(new Double(rectangle.x/scale).intValue(), new Double(rectangle.y/scale).intValue() , new Double(40/scale ).intValue(), new Double(h/scale).intValue());
					}
					else{
						g.drawRect(new Double(rectangle.x/scale).intValue(), new Double(rectangle.y/scale).intValue(),  new Double(w/scale).intValue() ,  new Double(h/scale).intValue() );
						resultResizeRectangle = new Rectangle(new Double(rectangle.x/scale).intValue(), new Double(rectangle.y/scale).intValue(),  new Double(w/scale).intValue() ,  new Double(h/scale).intValue() );
					}
		}
		if(changeZoom == "NE"){
			int w = pointDragging.x - rectangle.x;
			int h = (rectangle.y + rectangle.height) - pointDragging.y;
			if(w < 40 && h < 30){
				temp = new Point(rectangle.x,(rectangle.y + rectangle.height) - 30);
				g.drawRect(new Double(temp.x/scale).intValue(), new Double(temp.y/scale).intValue() , new Double(40/scale ).intValue(), new Double(30/scale).intValue());
				resultResizeRectangle = new Rectangle(new Double(temp.x/scale).intValue(), new Double(temp.y/scale).intValue() , new Double(40/scale ).intValue(), new Double(30/scale).intValue());
			}else
				if(w > 40 && h < 30){
					temp = new Point(rectangle.x,(rectangle.y + rectangle.height) - 30);
					g.drawRect(new Double(temp.x/scale).intValue(), new Double(temp.y/scale).intValue() , new Double(w/scale ).intValue(), new Double(30/scale).intValue());
					resultResizeRectangle = new Rectangle(new Double(temp.x/scale).intValue(), new Double(temp.y/scale).intValue() , new Double(w/scale ).intValue(), new Double(30/scale).intValue());
				}else
					if(w < 40 && h > 30){
						temp = new Point(rectangle.x,pointDragging.y);
						g.drawRect(new Double(temp.x/scale).intValue(), new Double(temp.y/scale).intValue() , new Double(40/scale ).intValue(), new Double(h/scale).intValue());
						resultResizeRectangle = new Rectangle(new Double(temp.x/scale).intValue(), new Double(temp.y/scale).intValue() , new Double(40/scale ).intValue(), new Double(h/scale).intValue());
					}
					else{
						g.drawRect(new Double(rectangle.x/scale).intValue(), new Double(pointDragging.y/scale).intValue(), new Double(w/scale).intValue(), new Double(h/scale).intValue());
						resultResizeRectangle = new Rectangle(new Double(rectangle.x/scale).intValue(), new Double(pointDragging.y/scale).intValue(), new Double(w/scale).intValue(), new Double(h/scale).intValue());
					}
		}
		if(changeZoom == "SW"){
			int w = (rectangle.x + rectangle.width) - pointDragging.x;
			int h = pointDragging.y - rectangle.y;
			if(w < 40 && h < 30){
				temp = new Point(rectangle.x + rectangle.width - 40, rectangle.y);
				g.drawRect(new Double(temp.x/scale).intValue(), new Double(temp.y/scale).intValue(), new Double(40/scale).intValue(), new Double(30/scale).intValue());
				resultResizeRectangle = new Rectangle(new Double(temp.x/scale).intValue(), new Double(temp.y/scale).intValue(), new Double(40/scale).intValue(), new Double(30/scale).intValue());
			}else
				if(w > 40 && h < 30){
					temp = new Point(pointDragging.x ,rectangle.y);
					g.drawRect(new Double(temp.x/scale).intValue(), new Double(temp.y/scale).intValue(), new Double(w/scale).intValue(), new Double(30/scale).intValue());
					resultResizeRectangle = new Rectangle(new Double(temp.x/scale).intValue(), new Double(temp.y/scale).intValue(), new Double(w/scale).intValue(), new Double(30/scale).intValue());
				}else
					if(w < 40 && h > 30){
						temp = new Point(rectangle.x + rectangle.width - 40 , rectangle.y);
						g.drawRect(new Double(temp.x/scale).intValue(), new Double(temp.y/scale).intValue(), new Double(40/scale).intValue(), new Double(h/scale).intValue());
						resultResizeRectangle = new Rectangle(new Double(temp.x/scale).intValue(), new Double(temp.y/scale).intValue(), new Double(40/scale).intValue(), new Double(h/scale).intValue());
					}
					else{
						g.drawRect(new Double(pointDragging.x/scale).intValue(), new Double(rectangle.y/scale).intValue(), new Double(w/scale).intValue(), new Double(h/scale).intValue());
						resultResizeRectangle = new Rectangle(new Double(pointDragging.x/scale).intValue(), new Double(rectangle.y/scale).intValue(), new Double(w/scale).intValue(), new Double(h/scale).intValue());
					}
		}
		Rectangle rectPanelW = getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getRectVisible();
		Double scaleDiagram = getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor();
		Double newScaleW = rectPanelW.getWidth()/resultResizeRectangle.getWidth()/scaleDiagram; 
		Double newScaleH = rectPanelW.getHeight()/resultResizeRectangle.getHeight()/scaleDiagram;
		Double scaleWanted = null;
		if(newScaleH <= newScaleW){
			scaleWanted = new Double(newScaleH);
		}else{
			scaleWanted = new Double(newScaleW);
		}
		/*if(scaleWanted < 0.38)
	    scaleWanted = 0.4;
	if(scaleWanted > 1.9)
	    scaleWanted = 1.9;*/
		getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().setZoomFactor(scaleWanted);
		Rectangle diagramWindowBounds = ( Rectangle ) getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getDiagramSize().clone();
		double newZoomFactor = getNavigator().getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor();
		diagramWindowBounds.width = new Double( diagramWindowBounds.width * newZoomFactor ).intValue();
		diagramWindowBounds.height = new Double( diagramWindowBounds.height * newZoomFactor ).intValue();
		getNavigator().getProcessGraphDocking().getTabbedPaneWorkArea().getPanelWorkArea().setPreferredSize(new Dimension(diagramWindowBounds.getSize().width , diagramWindowBounds.getSize().height));
		getNavigator().getProcessGraphDocking().getPaintManager().setSizeDiagramOverview(getNavigator().getBounds(), returnThis());
		g.setColor(colorOld);
	}

} 
