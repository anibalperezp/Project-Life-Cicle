package ceis.grehu.gui.paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Random;

public class Stereotype {

	private int id;

	private String Type;

	private Point inicialPoint;

	private Point finalPoint;

	private String name;

	private boolean selected;

	private ArrayList<Relation> relatedStereotypes;

	private Color background;

	private Color lineColor;

	private int widthF;

	private int heigthF;

	private boolean visible;

	private int parentId;

	private String description;

	private ArrayList<Integer> diagramIds;

	public Stereotype(){
		id = -1;
		Type = "";
		inicialPoint = null;
		finalPoint = null;
		name ="";
		selected = false;
		relatedStereotypes = new ArrayList<Relation>();
		background = null;
		lineColor = null;
		widthF = -1;
		heigthF = -1;
		visible = true;
		parentId = -1;
		description = "";
		diagramIds = new ArrayList<Integer>();
	}
	
	public Stereotype(Point pInicialPoint, Point pFinalPoint,String pType, int owner,int count) {
		relatedStereotypes = new ArrayList<Relation>();
		diagramIds = new ArrayList<Integer>();
		Random random = new Random();
		this.id = random.hashCode();
		this.description = "";
		this.selected = false;
		this.Type = pType;
		this.lineColor = Color.BLACK;
		this.background = Color.BLACK;//esto esta aqui para los estados iniciales que no tienen background
		//no obstante para los que tienen background se sobreescribira mas abajo.
		this.visible = true;
		this.parentId = owner;
		int tempWidth = 0;
		int tempHeight = 0;
		if((pInicialPoint.x >= pFinalPoint.x) && (pInicialPoint.y >= pFinalPoint.y)){
			this.inicialPoint = pFinalPoint;
			this.finalPoint = pInicialPoint;

			tempWidth = pInicialPoint.x - pFinalPoint.x;
			tempHeight = pInicialPoint.y - pFinalPoint.y;
		}
		if((pInicialPoint.x <= pFinalPoint.x) && (pInicialPoint.y <= pFinalPoint.y)){
			this.inicialPoint = pInicialPoint;
			this.finalPoint = pFinalPoint;

			tempWidth = pFinalPoint.x - pInicialPoint.x;
			tempHeight = pFinalPoint.y - pInicialPoint.y;
		}
		if((pInicialPoint.x >= pFinalPoint.x) && (pInicialPoint.y <= pFinalPoint.y)){
			Point point = new Point(pFinalPoint.x,pInicialPoint.y);
			this.inicialPoint = point;
			point = new Point(pInicialPoint.x,pFinalPoint.y);
			this.finalPoint = point;

			tempWidth = pInicialPoint.x - pFinalPoint.x;
			tempHeight = pFinalPoint.y - pInicialPoint.y;

		}
		if((pInicialPoint.x <= pFinalPoint.x) && (pInicialPoint.y >= pFinalPoint.y)){
			Point point = new Point(pInicialPoint.x,pFinalPoint.y);
			this.inicialPoint = point;
			point = new Point(pFinalPoint.x,pInicialPoint.y);
			this.finalPoint = point;

			tempWidth = pFinalPoint.x - pInicialPoint.x;
			tempHeight = pInicialPoint.y - pFinalPoint.y;
		}
		//actualizando width y heigth
		this.widthF = getDefaultSize().width;
		this.heigthF = getDefaultSize().height;
		if(tempWidth > getDefaultSize().width)
			this.widthF = tempWidth;
		else
			this.widthF = getDefaultSize().width;
		if(tempHeight > getDefaultSize().height)
			this.heigthF = tempHeight;
		else
			this.heigthF = getDefaultSize().height;

		//color de fondo
		if (getType() == "Macro") {
			this.background = new Color(99,97,98); //gris

			this.name = "macroproceso"+count;
		}
		if(getType() == "Proc"){
			this.background = new Color(93,133, 188);//azul

			this.name = "proceso"+count;
		}
		if(getType() == "SubProc"){
			this.background = new Color(76,111,112); //verde

			this.name = "subproceso"+count;
		}

		if(getType() == "ActServ"){
			this.background = new Color(123,86,151);//magenta;

			this.name = "servicio"+count;
		}

		if(getType() == "ActManual"){
			this.background = new Color(136,111,62);//orange;

			this.name = "actividad manual"+count;
		}

		if(getType() == "Cliente"){
			this.background = new Color(184,89,174);//pink;

			this.name = "cliente"+count;
		}

		if(getType() == "Provee"){
			this.background = new Color(178,64,85);//red;

			this.name = "proveedor"+count;
		}

		if(getType() == "BaseDato"){
			this.background = new Color(156,150,151);//LIGHT_GRAY;
			this.name = "Base de datos"+count;
		}

		if(getType() == "Decision"){
			this.background = new Color(153,150,69);//yellow;
			this.name = "Decision"+count;
		}

		if(getType() == "Nota"){
			this.background = new Color(249,249,193);//yellow;
			this.name = new Integer(this.getId()).toString();
		}
		//color de linea
		if(getType() == "EITiempo"){ 
			this.name = "Estado Inicial Tiempo"+count;
			this.lineColor = Color.white;
		}
		if(getType() == "EIMensaje"){
			this.lineColor = Color.white;   
			this.name = "Estado Inicial Mensaje"+count;
		}
		if(getType() == "EInicial"){
			this.lineColor = Color.white;   
			this.name = "Estado Inicial"+count;
		}
		if(getType() == "EFinal"){
			this.lineColor = Color.white;  
			this.name = "Estado Final"+count;
		}
	}

	//Type;inicialPoint; finalPoint;name; selected; relatedStereotypes;background;lineColor;

	//private int widthF; heigthF; visible;parentId;Arr
	public Stereotype( Stereotype stereotype ) {
		this.Type = stereotype.getType();
		this.id = stereotype.getId();
		this.inicialPoint = stereotype.getInicialPoint();
		this.finalPoint = stereotype.getFinalPoint();
		this.name = stereotype.getName();
		this.selected = stereotype.isSelected();
		this.relatedStereotypes = new ArrayList<Relation>(stereotype.getRelatedStereotypes());
		this.background = stereotype.getBackground();
		this.lineColor = stereotype.getLineColor();
		this.widthF = stereotype.getWidth();
		this.heigthF = stereotype.getHeigth();
		this.visible = stereotype.isVisible();
		this.parentId = stereotype.getParentId();
		this.description = stereotype.getDescription();
		diagramIds = stereotype.getDiagramIds();
	}

	public void PaintStereotype(Graphics g, boolean posibleSelection){
		if(isVisible()){
			Color colorOld = g.getColor();
			double arcWidth = 0.2*155;
			double arcHeight = 0.2*155;
			int width = getWidth();
			int heigth = getHeigth();
			if (getType() == "Macro") {
				width = getFinalPoint().x - getInicialPoint().x;
				heigth = getFinalPoint().y - getInicialPoint().y;

				if(width < 138 && heigth <127){
					width = 163;
					//arcWidth = 0.2*137;
					heigth =126;
					//arcHeight = 0.2*126;
				}
				if(width > 163 && heigth >126){
					width = getFinalPoint().x - getInicialPoint().x;
					heigth = getFinalPoint().y - getInicialPoint().y;
				}
				if(width < 164 && heigth >126){
					width = 163;
					//arcWidth = 0.2*137;
					heigth = getFinalPoint().y - getInicialPoint().y;
				}
				if(width > 163 && heigth <127){
					width = getFinalPoint().x - getInicialPoint().x;
					heigth =126;
					//arcHeight = 0.2*126;
				}


				/*g.setColor(getBackground());
				g.fillRect(getInicialPoint().x,getInicialPoint().y,widthF,heigthF,(int)arcWidth, (int)arcHeight);

				g.setColor(getLineColor());*/
				
				Point point1 = new Point(getInicialPoint().x,getInicialPoint().y);
				Point point2 = new Point(getInicialPoint().x+widthF,getInicialPoint().y);
				Point point3 = new Point(getInicialPoint().x,getInicialPoint().y+heigthF);
				Point point4 = new Point(getInicialPoint().x+widthF,getInicialPoint().y+heigthF);
				Point point5 = new Point(getInicialPoint().x+widthF+26,getInicialPoint().y+heigthF/2);
				int[]arrayX = {point1.x,point2.x,point5.x,point4.x,point3.x};
				int[]arrayY = {point1.y,point2.y,point5.y,point4.y,point3.y};
				g.setColor(getBackground());
				g.fillPolygon(arrayX, arrayY, 5);
				g.setColor(getLineColor());
				Graphics2D g2 = ( Graphics2D ) g;
				Stroke oldStroke = g2.getStroke();
				g2.setStroke( new BasicStroke( 1.2f ) );
				g.drawPolygon(arrayX, arrayY, 5);
				g2.setStroke(oldStroke);
				
				
				//g.drawRect(getInicialPoint().x,getInicialPoint().y,widthF,heigthF/*,(int)arcWidth, (int)arcHeight*/);
				g.drawString("<<macroproceso>>", getInicialPoint().x + widthF/2 - 8*7, getInicialPoint().y+15);
				//drawName(g);
				g2.setColor( new Color(0, 0, 0) );
				FontMetrics fm = g2.getFontMetrics();
				int length = 0;
				length = fm.charsWidth( name.toCharArray(), 0 , name.length());
				int pos =  widthF  / 2;
				int count = length / 2;
				int inc = pos - count;
				g2.drawString( name, getInicialPoint().x + inc, getInicialPoint().y + 35 );
			}
			if(getType() == "Proc"){
				width = getFinalPoint().x - getInicialPoint().x;
				heigth = getFinalPoint().y - getInicialPoint().y;

				if(width < 138 && heigth <127){
					width = 137;
					//arcWidth = 0.2*137;
					heigth =126;
					//arcHeight = 0.2*126;
				}
				if(width > 137 && heigth >126){
					width = getFinalPoint().x - getInicialPoint().x;
					heigth = getFinalPoint().y - getInicialPoint().y;
				}
				if(width < 138 && heigth >126){
					width = 137;
					//arcWidth = 0.2*137;
					heigth = getFinalPoint().y - getInicialPoint().y;
				}
				if(width > 137 && heigth <127){
					width = getFinalPoint().x - getInicialPoint().x;
					heigth =126;
					//arcHeight = 0.2*126;
				}
				g.setColor(getBackground());
				g.fillRoundRect(getInicialPoint().x,getInicialPoint().y,widthF,heigthF,(int)arcWidth, (int)arcHeight);
				g.setColor(getLineColor());
				Graphics2D g2 = ( Graphics2D ) g;
				Stroke oldStroke = g2.getStroke();
				g2.setStroke( new BasicStroke( 1.2f ) );
				g.drawRoundRect(getInicialPoint().x,getInicialPoint().y,widthF,heigthF,(int)arcWidth, (int)arcHeight);
				g2.setStroke(oldStroke);
				g.drawString("<<proceso>>", getInicialPoint().x + widthF/2 - 5*7, getInicialPoint().y+15);
				drawName(g);

			}
			if(getType() == "SubProc"){
				width = getFinalPoint().x - getInicialPoint().x;
				heigth = getFinalPoint().y - getInicialPoint().y;

				if(width < 138 && heigth <127){
					width = 137;
					//arcWidth = 0.2*137;
					heigth =126;
					//arcHeight = 0.2*126;
				}
				if(width > 137 && heigth >126){
					width = getFinalPoint().x - getInicialPoint().x;
					heigth = getFinalPoint().y - getInicialPoint().y;
				}
				if(width < 138 && heigth >126){
					width = 137;
					//arcWidth = 0.2*137;
					heigth = getFinalPoint().y - getInicialPoint().y;
				}
				if(width > 137 && heigth <127){
					width = getFinalPoint().x - getInicialPoint().x;
					heigth =126;
					//arcHeight = 0.2*126;
				}
				g.setColor(getBackground());
				g.fillRoundRect(getInicialPoint().x,getInicialPoint().y,widthF,heigthF,(int)arcWidth, (int)arcHeight);
				g.setColor(getLineColor());
				g.drawRoundRect(getInicialPoint().x,getInicialPoint().y,widthF,heigthF,(int)arcWidth, (int)arcHeight);
				g.drawString("<<subproceso>>", getInicialPoint().x + widthF/2 - 7*7, getInicialPoint().y+15);
				drawName(g);
			}

			if(getType() == "ActServ"){
				width = getFinalPoint().x - getInicialPoint().x;
				heigth = getFinalPoint().y - getInicialPoint().y;

				if(width < 138 && heigth <127){
					width = 137;
					//arcWidth = 0.2*137;
					heigth =126;
					//arcHeight = 0.2*126;
				}
				if(width > 137 && heigth >126){
					width = getFinalPoint().x - getInicialPoint().x;
					heigth = getFinalPoint().y - getInicialPoint().y;
				}
				if(width < 138 && heigth >126){
					width = 137;
					//arcWidth = 0.2*137;
					heigth = getFinalPoint().y - getInicialPoint().y;
				}
				if(width > 137 && heigth <127){
					width = getFinalPoint().x - getInicialPoint().x;
					heigth =126;
					//arcHeight = 0.2*126;
				}
				g.setColor(getBackground());
				g.fillRoundRect(getInicialPoint().x,getInicialPoint().y,widthF,heigthF,(int)arcWidth, (int)arcHeight);
				g.setColor(getLineColor());
				Graphics2D g2 = ( Graphics2D ) g;
				Stroke oldStroke = g2.getStroke();
				g2.setStroke( new BasicStroke( 1.2f ) );
				g.drawRoundRect(getInicialPoint().x,getInicialPoint().y,widthF,heigthF,(int)arcWidth, (int)arcHeight);
				g2.setStroke(oldStroke);
				g.drawString("<<servicio>>", getInicialPoint().x + widthF/2 - 6*7, getInicialPoint().y+15);
				drawName(g);
			}

			if(getType() == "ActManual"){
				width = getFinalPoint().x - getInicialPoint().x;
				heigth = getFinalPoint().y - getInicialPoint().y;

				if(width < 138 && heigth <127){
					width = 137;
					//arcWidth = 0.2*137;
					heigth =126;
					//arcHeight = 0.2*126;
				}
				if(width > 137 && heigth >126){
					width = getFinalPoint().x - getInicialPoint().x;
					heigth = getFinalPoint().y - getInicialPoint().y;
				}
				if(width < 138 && heigth >126){
					width = 137;
					//arcWidth = 0.2*137;
					heigth = getFinalPoint().y - getInicialPoint().y;
				}
				if(width > 137 && heigth <127){
					width = getFinalPoint().x - getInicialPoint().x;
					heigth =126;
					//arcHeight = 0.2*126;
				}
				g.setColor(getBackground());
				g.fillRoundRect(getInicialPoint().x,getInicialPoint().y,widthF,heigthF,(int)arcWidth, (int)arcHeight);
				g.setColor(getLineColor());
				Graphics2D g2 = ( Graphics2D ) g;
				Stroke oldStroke = g2.getStroke();
				g2.setStroke( new BasicStroke( 1.2f ) );
				g.drawRoundRect(getInicialPoint().x,getInicialPoint().y,widthF,heigthF,(int)arcWidth, (int)arcHeight);
				g2.setStroke(oldStroke);
				g.drawString("<<manual>>", getInicialPoint().x + widthF/2 - 5*7, getInicialPoint().y+15);
				drawName(g);
			}

			if(getType() == "Cliente"){
				width = getFinalPoint().x - getInicialPoint().x;
				heigth = getFinalPoint().y - getInicialPoint().y;

				if(width < 138 && heigth <127){
					width = 137;
					//arcWidth = 0.2*137;
					heigth =126;
					//arcHeight = 0.2*126;
				}
				if(width > 137 && heigth >126){
					width = getFinalPoint().x - getInicialPoint().x;
					heigth = getFinalPoint().y - getInicialPoint().y;
				}
				if(width < 138 && heigth >126){
					width = 137;
					//arcWidth = 0.2*137;
					heigth = getFinalPoint().y - getInicialPoint().y;
				}
				if(width > 137 && heigth <127){
					width = getFinalPoint().x - getInicialPoint().x;
					heigth =126;
					//arcHeight = 0.2*126;
				}
				g.setColor(getBackground());
				g.fillRect(getInicialPoint().x,getInicialPoint().y,widthF,heigthF/*,(int)arcWidth, (int)arcHeight*/);
				g.setColor(getLineColor());
				Graphics2D g2 = ( Graphics2D ) g;
				Stroke oldStroke = g2.getStroke();
				g2.setStroke( new BasicStroke( 1.2f ) );
				g.drawRect(getInicialPoint().x,getInicialPoint().y,widthF,heigthF/*,(int)arcWidth, (int)arcHeight*/);
				g2.setStroke(oldStroke);
				g.drawString("<<cliente>>", getInicialPoint().x + widthF/2 - 5*7, getInicialPoint().y+15);
				drawName(g);
			}

			if(getType() == "Provee"){
				width = getFinalPoint().x - getInicialPoint().x;
				heigth = getFinalPoint().y - getInicialPoint().y;

				if(width < 138 && heigth <127){
					width = 137;
					//arcWidth = 0.2*137;
					heigth =126;
					//arcHeight = 0.2*126;
				}
				if(width > 137 && heigth >126){
					width = getFinalPoint().x - getInicialPoint().x;
					heigth = getFinalPoint().y - getInicialPoint().y;
				}
				if(width < 138 && heigth >126){
					width = 137;
					//arcWidth = 0.2*137;
					heigth = getFinalPoint().y - getInicialPoint().y;
				}
				if(width > 137 && heigth <127){
					width = getFinalPoint().x - getInicialPoint().x;
					heigth =126;
					//arcHeight = 0.2*126;
				}
				g.setColor(getBackground());
				g.fillRect(getInicialPoint().x,getInicialPoint().y,widthF,heigthF/*,(int)arcWidth, (int)arcHeight*/);
				g.setColor(getLineColor());
				Graphics2D g2 = ( Graphics2D ) g;
				Stroke oldStroke = g2.getStroke();
				g2.setStroke( new BasicStroke( 1.2f ) );
				g.drawRect(getInicialPoint().x,getInicialPoint().y,widthF,heigthF/*,(int)arcWidth, (int)arcHeight*/);
				g2.setStroke(oldStroke);
				g.drawString("<<proveedor>>", getInicialPoint().x + width/2 - 6*7, getInicialPoint().y+15);
				drawName(g);
			}

			if(getType() == "EITiempo"){	
				Point pointEnd = new Point(getInicialPoint().x + 30,getInicialPoint().y + 30);
				width = pointEnd.x - getInicialPoint().x;
				heigth = pointEnd.y - getInicialPoint().y;
				g.setColor(getBackground());
				g.fillOval(getInicialPoint().x, getInicialPoint().y, width+1, heigth+1);
				g.drawOval(getInicialPoint().x, getInicialPoint().y, width, heigth);
				g.setColor(getLineColor());
				Point pointini = new Point(getInicialPoint().x + 5,getInicialPoint().y + 5);
				Point pointend = new Point(pointEnd.x - 5,pointEnd.y - 5);
				int width1 = pointend.x - pointini.x;
				int heigth1 = pointend.y - pointini.y;
				g.drawOval(pointini.x, pointini.y, width1, heigth1);
				Point pointCenter = new Point(pointini.x + width1/2, pointini.y + heigth1/2);
				g.drawLine(pointCenter.x, pointCenter.y, pointCenter.x, pointini.y);
				g.drawLine(pointCenter.x, pointCenter.y, pointend.x, pointCenter.y);
			}

			if(getType() == "EIMensaje"){	
				Point pointEnd = new Point(getInicialPoint().x + 30,getInicialPoint().y + 30);
				width = pointEnd.x - getInicialPoint().x;
				heigth = pointEnd.y - getInicialPoint().y;
				g.setColor(getBackground());
				g.fillOval(getInicialPoint().x, getInicialPoint().y, width+1, heigth+1);
				g.drawOval(getInicialPoint().x, getInicialPoint().y, width, heigth);
				g.setColor(getLineColor());
				Point pointini = new Point(getInicialPoint().x + 5,getInicialPoint().y + 8);
				Point pointend = new Point(pointEnd.x - 5,pointEnd.y - 8);
				int width1 = pointend.x - pointini.x;
				int heigth1 = pointend.y - pointini.y;
				g.drawRect(pointini.x, pointini.y, width1, heigth1);
				g.drawLine(pointini.x, pointini.y, pointend.x - width1/2 - 3, pointend.y - 3);
				g.drawLine(pointend.x - width1/2 - 3, pointend.y - 3, pointend.x - width1/2 + 3, pointend.y - 3);
				g.drawLine(pointend.x - width1/2 + 3, pointend.y - 3, pointend.x, pointini.y );
			}	

			if(getType() == "EInicial"){	
				Point pointEnd = new Point(getInicialPoint().x + 30,getInicialPoint().y + 30);
				width = pointEnd.x - getInicialPoint().x;
				heigth = pointEnd.y - getInicialPoint().y;
				g.setColor(getBackground());
				g.fillOval(getInicialPoint().x, getInicialPoint().y, width+1, heigth+1);
				g.setColor(getLineColor());
				g.drawOval(getInicialPoint().x, getInicialPoint().y, width, heigth);
			}

			if(getType() == "EFinal"){	
				Point pointEnd = new Point(getInicialPoint().x + 30,getInicialPoint().y + 30);
				width = pointEnd.x - getInicialPoint().x;
				heigth = pointEnd.y - getInicialPoint().y;
				g.setColor(getBackground());
				g.fillOval(getInicialPoint().x, getInicialPoint().y, width+1, heigth+1);
				g.drawOval(getInicialPoint().x, getInicialPoint().y, width, heigth);
				g.setColor(getLineColor());
				Point pointini = new Point(getInicialPoint().x + 5,getInicialPoint().y + 5);
				Point pointend = new Point(pointEnd.x - 5,pointEnd.y - 5);
				int width1 = pointend.x - pointini.x;
				int heigth1 = pointend.y - pointini.y;
				g.drawOval(pointini.x, pointini.y, width1, heigth1);

			}

			if(getType() == "BaseDato"){
				width = getFinalPoint().x - getInicialPoint().x;
				heigth = getFinalPoint().y - getInicialPoint().y;
				//default
				if(width != 40 && heigth != 50){
					width = 40;
					heigth =50;
				}

				float percent = heigth*85/100;
                //central
				//g2.setColor(Color.black);
				g.setColor(getLineColor());
				g.drawRect(getInicialPoint().x - 1, getInicialPoint().y, width + 1, heigth);
				//ovalo abajo
				g.setColor(getBackground());
				g.fillOval(getInicialPoint().x, getInicialPoint().y + (int)percent, width,2*(heigth - (int)percent));
				//g.setColor(Color.black);
				g.setColor(getLineColor());
				g.drawOval(getInicialPoint().x, getInicialPoint().y + (int)percent, width,2*(heigth - (int)percent));
				//rect central
				g.setColor(getBackground());
				g.fillRect(getInicialPoint().x, getInicialPoint().y, width, heigth);
				//ovalo arriba ultimo
				g.setColor(getBackground());
				g.fillOval(getInicialPoint().x, getInicialPoint().y - (heigth - (int)percent), width,2*(heigth - (int)percent));
				g.setColor(getLineColor());
				g.drawOval(getInicialPoint().x, getInicialPoint().y - (heigth - (int)percent), width,2*(heigth - (int)percent));
			}

			if(getType() == "Decision"){
				width = getFinalPoint().x - getInicialPoint().x;
				heigth = getFinalPoint().y - getInicialPoint().y;
				//default
				if(width != 40 && heigth != 40){
					width = 40;
					heigth =40;
				}
				Point pointUp = new Point(getInicialPoint().x+width/2,getInicialPoint().y);
				Point pointRight = new Point(getInicialPoint().x+width,getInicialPoint().y+heigth/2);
				Point pointDown = new Point(getInicialPoint().x+width/2,getInicialPoint().y+heigth);
				Point pointLeft = new Point(getInicialPoint().x,getInicialPoint().y+heigth/2);
				int[]arrayX = {pointUp.x,pointRight.x,pointDown.x,pointLeft.x};
				int[]arrayY = {pointUp.y,pointRight.y,pointDown.y,pointLeft.y};
				g.setColor(getBackground());
				g.fillPolygon(arrayX, arrayY, 4);
				g.setColor(getLineColor());
				g.drawPolygon(arrayX, arrayY, 4);
			}

			if(getType() == "Nota"){
				width = getFinalPoint().x - getInicialPoint().x;
				heigth = getFinalPoint().y - getInicialPoint().y;
				//default
				if(width < 61 && heigth <71){
					width = 60;
					heigth =70;
				}
				if(width > 61 && heigth >71){
					width = getFinalPoint().x - getInicialPoint().x;
					heigth = getFinalPoint().y - getInicialPoint().y;
				}
				if(width < 61 && heigth >71){
					width = 60;
					heigth = getFinalPoint().y - getInicialPoint().y;
				}
				if(width > 61 && heigth <71){
					width = getFinalPoint().x - getInicialPoint().x;
					heigth =70;
				}
				float percent = 15;

				//el punto inicial ya lo tengo
				Point pointLeftDown = new Point(getInicialPoint().x,getInicialPoint().y+heigth);
				Point pointRigthDown = new Point(getInicialPoint().x+width,getInicialPoint().y+heigth);
				Point pointRightUp = new Point(getInicialPoint().x+width,getInicialPoint().y + (int)percent);
				Point pointRightUp2 = new Point(getInicialPoint().x + width - (int)percent,getInicialPoint().y);
				int[]arrayXNote = {getInicialPoint().x,pointLeftDown.x,pointRigthDown.x,pointRightUp.x,pointRightUp2.x};
				int[]arrayYNote = {getInicialPoint().y,pointLeftDown.y,pointRigthDown.y,pointRightUp.y,pointRightUp2.y};
				Point pointTriangle = new Point(getInicialPoint().x+width- (int)percent,getInicialPoint().y+(int)percent);
				int[]arrayXTri = {pointRightUp.x,pointTriangle.x,pointRightUp2.x};
				int[]arrayYTri = {pointRightUp.y,pointTriangle.y,pointRightUp2.y};
				g.setColor(getBackground());

				g.fillPolygon(arrayXNote, arrayYNote, 5);
				g.setColor(getLineColor());
				g.drawPolygon(arrayXNote, arrayYNote, 5);
				g.drawPolygon(arrayXTri, arrayYTri, 3); 
			}
			//calculando el punto final
			Point point = new Point(getInicialPoint().x + getWidth(),getInicialPoint().y + getHeigth());
			setFinalPoint(point);
			//seleccion
			g.setColor(Color.BLACK);
			if(isSelected() && posibleSelection == true){
				paintSelectionToStereotype(g);
			}

			g.setColor(colorOld);
		}
	}

	public Color getBackground() {
		return background;
	}

	public Point getCenterStereotype(){
		return new Point(((getFinalPoint().x - getInicialPoint().x)/2) + getInicialPoint().x,
				((getFinalPoint().y - getInicialPoint().y)/2) + getInicialPoint().y);
	}
	public void setBackground(Color background) {
		this.background = background;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}


	/*public boolean isContentInArea() {
		return contentInArea;
	}*/

	/*public void setContentInArea(boolean contentInArea) {
		this.contentInArea = contentInArea;
	}*/

	public Point getFinalPoint() {
		return finalPoint;
	}

	public void setFinalPoint(Point finalPoint) {
		this.finalPoint = finalPoint;
	}

	public Point getInicialPoint() {
		return inicialPoint;
	}

	public void setInicialPoint(Point inicialPoint) {
		this.inicialPoint = inicialPoint;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Relation> getRelatedStereotypes() {
		return relatedStereotypes;
	}

	public void setRelatedStereotypes(ArrayList<Relation> relatedStereotypes) {
		this.relatedStereotypes = relatedStereotypes;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/*public static boolean isPointRightToPoint( Point pPoint, Point pPointSource ) {
    	boolean isRight = false;

    	if ( pPoint.x > pPointSource.x ) {
    		isRight = true;
    	}

    	return isRight;
	}*/


	/*public static boolean isPointRightToLine( Point pPoint, Point pPointLine1, Point pPointLine2 ) {
    	boolean isRight = false;

    	if ( pPoint.x > pPointLine1.x && pPointLine2.x < pPoint.x ) {
    		isRight = true;
    	}

    	return isRight;
	}*/

	/*public static boolean isPointUnderToPoint( Point pPoint, Point pPointSource ) {
    	boolean isUnder = false;

    	if ( pPoint.y > pPointSource.y ) {
    		isUnder = true;
    	}

    	return isUnder;
	}*/

	/*public static boolean isPointUnderToLine( Point pPoint, Point pPointLine1, Point pPointLine2 ) {
    	boolean isUnder = false;

    	if ( pPoint.y > pPointLine1.y && pPointLine2.y < pPoint.y ) {
    		isUnder = true;
    	}

    	return isUnder;
    }*/

	public boolean isPointContentInStereotype(Point pPoint) {
		boolean content = false;
		Rectangle pRectangle = new Rectangle(getInicialPoint().x,getInicialPoint().y,getWidth(), getHeigth());
		if((pPoint.x >= pRectangle.x) && (pPoint.x <= (pRectangle.x + pRectangle.width)) 
				&& (pPoint.y >= pRectangle.y) && (pPoint.y <= (pRectangle.y + pRectangle.height))){
			content = true;
		}
		return content;
	}

	public boolean isStereotypeContentInRectangle(Rectangle pRectangleExternal ) {
		boolean content = false;
		Rectangle pRectangleInside = new Rectangle(getInicialPoint().x,getInicialPoint().y,getWidth(), getHeigth());
		if((pRectangleInside.x >= pRectangleExternal.x) && (pRectangleInside.y >= pRectangleExternal.y)
				&& ((pRectangleInside.x + pRectangleInside.width) <= (pRectangleExternal.x + pRectangleExternal.width))
				&& ((pRectangleInside.y + pRectangleInside.height) <= (pRectangleExternal.y + pRectangleExternal.height))){
			content = true;
		}
		return content;
	}

	private void paintSelectionToStereotype(Graphics g){
		Color colorOld = g.getColor();

		Point pointIni = getInicialPoint();
		Point pointFin = getFinalPoint();
		Point pointCen = getCenterStereotype();

		if(getType() == "BaseDato"){
			float percent = getHeigth()*85/100;
			//extremos
			g.fillRect(pointIni.x - 3, pointIni.y - 3 - (getHeigth() - (int)percent), 6, 6);
			g.fillRect(pointFin.x - 3, pointIni.y - 3 - (getHeigth() - (int)percent), 6, 6);
			g.fillRect(pointFin.x - 3, pointFin.y - 3 + getHeigth() - (int)percent, 6, 6);
			g.fillRect(pointIni.x - 3, pointFin.y - 3 + getHeigth() - (int)percent, 6, 6);
			//centros
			g.fillRect(pointCen.x - 3, pointIni.y - 3 - (getHeigth() - (int)percent), 6, 6);
			g.fillRect(pointFin.x - 3, pointCen.y - 3, 6, 6);
			g.fillRect(pointCen.x - 3, pointFin.y - 3 + getHeigth() - (int)percent, 6, 6);
			g.fillRect(pointIni.x - 3, pointCen.y - 3, 6, 6);
		}
		if(getType() == "Macro"){
			//extremos
			g.fillRect(pointIni.x - 3, pointIni.y - 3, 6, 6);
			g.fillRect(pointFin.x + 25 - 3, pointIni.y - 3, 6, 6);
			g.fillRect(pointFin.x + 25 - 3, pointFin.y - 3, 6, 6);
			g.fillRect(pointIni.x - 3, pointFin.y - 3, 6, 6);
			//centros
			g.fillRect(pointCen.x + 13 - 3, pointIni.y - 3, 6, 6);
			g.fillRect(pointFin.x + 25 - 3, pointCen.y - 3, 6, 6);
			g.fillRect(pointCen.x + 13 - 3, pointFin.y - 3, 6, 6);
			g.fillRect(pointIni.x - 3, pointCen.y - 3, 6, 6);
		}
		else{
			//extremos
			g.fillRect(pointIni.x - 3, pointIni.y - 3, 6, 6);
			g.fillRect(pointFin.x - 3, pointIni.y - 3, 6, 6);
			g.fillRect(pointFin.x - 3, pointFin.y - 3, 6, 6);
			g.fillRect(pointIni.x - 3, pointFin.y - 3, 6, 6);
			//centros
			g.fillRect(pointCen.x - 3, pointIni.y - 3, 6, 6);
			g.fillRect(pointFin.x - 3, pointCen.y - 3, 6, 6);
			g.fillRect(pointCen.x - 3, pointFin.y - 3, 6, 6);
			g.fillRect(pointIni.x - 3, pointCen.y - 3, 6, 6);
		}
		g.setColor(colorOld);
	}
	/*public Dimension getSize( Point pPointPressed, Point pPointReleased ) {

    	return new Dimension( pPointReleased.x - pPointPressed.x, pPointReleased.y - pPointPressed.y);
    }*/

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public int getHeigth() {
		return heigthF;
	}

	public void setHeigth(int heigth) {
		this.heigthF = heigth;
	}

	public int getWidth() {
		return widthF;
	}

	public void setWidth(int width) {
		this.widthF = width;
	}

	/*public static void draw( Graphics pG, Point pStartPoint, Point pEndPoint ) {

		Graphics2D g2 = ( Graphics2D ) pG;
		Rectangle rect = SelectionArea.getDrawableArea( pStartPoint, pEndPoint );
		g2.setColor( new Color( 0, 0, 0 ) );
		g2.draw( new Rectangle2D.Double( rect.x, rect.y, rect.width,
				rect.height ) );
	}*/

	/*public void clear( Graphics pG, Color pColor ) {
		Graphics2D g2 = ( Graphics2D ) pG;
		Color oldColor = g2.getColor();
		g2.setColor( pColor );
		g2.fillRect( rectangleDraw.x, rectangleDraw.y, rectangleDraw.width + 1, rectangleDraw.height + 1 );
		g2.setColor( oldColor );
		for ( Enumeration< ACGGraficInterface > e = acgGraficInterfaceList.elements(); e.hasMoreElements(); ) {
			ACGGraficInterface currentACGGraficInterface = e.nextElement();
			currentACGGraficInterface.clearInterfaceInGeneralComponent( pG, pColor );
		}
	}*/

	/**
	 * @return java.util.ArrayList<Rectangle>
	 * orden
	 * northWest,north,northEast,east,southEast,south,southWest,west.
	 **/
	public ArrayList<Rectangle> getSelectionRectangles(){

		ArrayList<Rectangle> list = new ArrayList<Rectangle>();
		Point pointIni = getInicialPoint();
		Point pointFin = getFinalPoint();
		Point pointCen = getCenterStereotype();
		Rectangle northWest = null;
		Rectangle northEast = null;
		Rectangle southEast = null;
		Rectangle southWest = null;
		Rectangle north = null;
		Rectangle east = null;
		Rectangle south = null;
		Rectangle west = null;
		if(getType() == "BaseDato"){
			float percent = getHeigth()*85/100;
			//extremos
			northWest = new Rectangle(pointIni.x - 3, pointIni.y - 3 - (getHeigth() - (int)percent), 6, 6);
			northEast = new Rectangle(pointFin.x - 3, pointIni.y - 3 - (getHeigth() - (int)percent), 6, 6);
			southEast = new Rectangle(pointFin.x - 3, pointFin.y - 3 + getHeigth() - (int)percent, 6, 6);
			southWest = new Rectangle(pointIni.x - 3, pointFin.y - 3 + getHeigth() - (int)percent, 6, 6);
			//centros
			north = new Rectangle(pointCen.x - 3, pointIni.y - 3 - (getHeigth() - (int)percent), 6, 6);
			east = new Rectangle(pointFin.x - 3, pointCen.y - 3, 6, 6);
			south = new Rectangle(pointCen.x - 3, pointFin.y - 3 + getHeigth() - (int)percent, 6, 6);
			west = new Rectangle(pointIni.x - 3, pointCen.y - 3, 6, 6);
		}
		if(getType() == "Macro"){
			//extremos
			northWest = new Rectangle(pointIni.x - 3, pointIni.y - 3, 6, 6);
			northEast = new Rectangle(pointFin.x + 26 - 3, pointIni.y - 3, 6, 6);
			southEast = new Rectangle(pointFin.x + 26 - 3, pointFin.y - 3, 6, 6);
			southWest = new Rectangle(pointIni.x - 3, pointFin.y - 3, 6, 6);
			//centros
			north = new Rectangle(pointCen.x + 13 - 3, pointIni.y - 3, 6, 6);
			east = new Rectangle(pointFin.x + 26 - 3, pointCen.y - 3, 6, 6);
			south = new Rectangle(pointCen.x + 13 - 3, pointFin.y - 3, 6, 6);
			west = new Rectangle(pointIni.x - 3, pointCen.y - 3, 6, 6);
		}
		else{
			//extremos
			northWest = new Rectangle(pointIni.x - 3, pointIni.y - 3, 6, 6);
			northEast = new Rectangle(pointFin.x - 3, pointIni.y - 3, 6, 6);
			southEast = new Rectangle(pointFin.x - 3, pointFin.y - 3, 6, 6);
			southWest = new Rectangle(pointIni.x - 3, pointFin.y - 3, 6, 6);
			//centros
			north = new Rectangle(pointCen.x - 3, pointIni.y - 3, 6, 6);
			east = new Rectangle(pointFin.x - 3, pointCen.y - 3, 6, 6);
			south = new Rectangle(pointCen.x - 3, pointFin.y - 3, 6, 6);
			west = new Rectangle(pointIni.x - 3, pointCen.y - 3, 6, 6);
		}
		list.add(northWest);
		list.add(north);
		list.add(northEast);
		list.add(east);
		list.add(southEast);
		list.add(south);
		list.add(southWest);
		list.add(west);
		return list;
	}

	public Rectangle getDefaultSize(){
		if (getType() == "Proc" || getType() == "SubProc" || getType() == "ActServ"
			|| getType() == "ActManual" || getType() == "Cliente" || getType() == "Provee") {
			return new Rectangle(getInicialPoint().x,getInicialPoint().y,137,126);
		}
		if(getType() == "Macro"){
			return new Rectangle(getInicialPoint().x,getInicialPoint().y,163,126);
		}
		if(getType() == "EITiempo" || getType() == "EIMensaje" || getType() == "EInicial" || getType() == "EFinal"){
			return new Rectangle(getInicialPoint().x,getInicialPoint().y,30,30);
		}
		if(getType() == "BaseDato"){
			return new Rectangle(getInicialPoint().x,getInicialPoint().y,40,50);
		}
		if(getType() == "Decision"){
			return new Rectangle(getInicialPoint().x,getInicialPoint().y,40,40);
		}
		if(getType() == "Nota"){
			return new Rectangle(getInicialPoint().x,getInicialPoint().y,60,70);
		}
		return null;
	}

	public Point getBigPoint(){
		return new Point(getInicialPoint().x + getWidth(),getInicialPoint().y + getHeigth());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getParentId() {
		return parentId;
	}

	private void drawName( Graphics pG ) {
		Graphics2D g2 = ( Graphics2D ) pG;
		g2.setColor( new Color(0, 0, 0) );
		FontMetrics fm = g2.getFontMetrics();
		int length = 0;
		if(getType() != "Nota")
			length = fm.charsWidth( name.toCharArray(), 0 , name.length());
		else
			length = fm.charsWidth( description.toCharArray(), 0 , name.length());
		int pos =  widthF  / 2;
		int count = length / 2;
		int inc = pos - count;
		if(getType() != "Nota")
			g2.drawString( name, getInicialPoint().x + inc, getInicialPoint().y + 35 );
		else
			g2.drawString( name, getInicialPoint().x + inc, getInicialPoint().y + 35 );
		g2.drawLine(getInicialPoint().x,getInicialPoint().y + 36  , getInicialPoint().x + widthF, (getInicialPoint().y + 36));
	}


	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void addRelation(Relation relation){
		relatedStereotypes.add(relation);
	}  

	public boolean validRegion(Point point){
		int x = point.x;
		int y = point.y; 
		if(x > getInicialPoint().x && x < getInicialPoint().x + widthF && y > getInicialPoint().y && y < getInicialPoint().y + 36)
			return true;
		return false;
	}

	public void updateRelation(Stereotype stereotypeDestiny, Relation relation){
		for (Relation currentRelation : getRelatedStereotypes()) {
			if(currentRelation.getStereotype().getId() == stereotypeDestiny.getId()){
				currentRelation.setArraypoints(relation.getArraypoints());
				currentRelation.setIntersection(relation.getIntersection());
				currentRelation.setOrientation(relation.getOrientation());
				currentRelation.setArrayLines(relation.getArrayLines());
				break;
			}
		}
	}

	public Relation deleteRelation(Stereotype stereotypeDestiny){
		Relation relation = null;
		for (int i = 0; i < getRelatedStereotypes().size(); i++) {
			if(getRelatedStereotypes().get(i).getStereotype().getId() == stereotypeDestiny.getId()){
				relation = getRelatedStereotypes().get(i);
				getRelatedStereotypes().remove(i);
				break;
			}
		}
		return relation;
	}

	public ArrayList<Integer> getDiagramIds() {
		return diagramIds;
	}

	public void setDiagramList(ArrayList<Integer> diagramIds) {
		this.diagramIds = diagramIds;
	}
}
