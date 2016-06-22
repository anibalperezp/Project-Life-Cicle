package ceis.grehu.units.printer;

import java.awt.*;
import java.awt.print.*;

import ceis.grehu.gui.ProcessGraphDocking;
import ceis.grehu.gui.paint.Diagram;

//La clase debe de implementar la impresi�n implements Printable
public class PrintObject implements Printable {
    
    private ProcessGraphDocking docking = null;
    
    public PrintObject(ProcessGraphDocking owner){
	this.docking = owner;
    }

    public int print(Graphics g, PageFormat f, int pageIndex) {
	//Creamos un objeto 2D para dibujar en el
	Graphics2D g2 = (Graphics2D) g;
	/**Creamos el rect�ngulo
	*  getImagebleX() coge la parte de la hoja donde podemos 
	*  imprimir quitando los bordes. Si no hiciesemos 
	*  esto as� y tuviesemos bordes definidos en la impresi�n 
	*  lo que dibujasemos fuera de los bordes no lo 
	*  imprimir�a aunque cupiese en la hoja f�sicamente.*/
	
	//Rectangle2D rect = new Rectangle2D.Double(f.getImageableX(), f
		//.getImageableY(), f.getImageableWidth(), f.getImageableHeight());

	Diagram diagram = getDocking().getPaintManager().getActiveDiagram();
	Rectangle diagramWindowBounds = getDocking().getTabbedPaneWorkArea()
	.getPanelWorkArea().getBounds();

	/**pageIndex indica el n�mero de la p�gina que se imprime
	*  cuando es 0 primera p�gina a imprimir,
	*  cuando es 1 segunda p�gina a imprimir,
	*  En otro caso se devulve que no hay m�s p�ginas a imprimir*/
	switch (pageIndex) {
	case 0: //P�gina 1: Dibujamos sobre g y luego lo pasamos a g2
	   /* g.setColor(Color.black);
	    g.fillRect(110, 120, 30, 5);
	    g.setColor(Color.pink);
	    g.drawLine(0, 0, 200, 200);
	    g2 = (Graphics2D) g;*/
	    Color bg = getDocking().getTabbedPaneWorkArea()
	    .getPanelWorkArea().getBackground();
	    Color newBg = new Color(bg.getRed(), bg
		    .getGreen(), bg.getBlue(), 250);
	    g.setColor(newBg);//asigno color
	    g.fillRect(0, 0, 
		    new Double(diagramWindowBounds.getWidth()/diagram.getZoomFactor()).intValue(),
		    new Double(diagramWindowBounds.getHeight() / diagram.getZoomFactor()).intValue());
	    if(diagram.getSwinLine() != null)
		getDocking().getTabbedPaneWorkArea().getPanelWorkArea().paintSwinLine(g, diagram.getSwinLine());
	    if(diagram.getPictureList().size() > 0)
		getDocking().getNavigator().getProcessGraphDocking().getPaintManager().paintRelations(g, diagram.getPictureList());
	    for (int i = 0; i < diagram.getLength(); i++) {
		diagram.getStereotype(i).PaintStereotype(g, false);
		if(diagram.getStereotype(i).getType() == "Nota"){
		    getDocking().getTabbedPaneWorkArea().getPanelWorkArea().drawText(g, diagram.getStereotype(i));
		}
	    }
	    g2 = (Graphics2D) g;
	    g2.scale(1.0, 1.0);
	    g.dispose();
	    g2 = (Graphics2D) g;
	    return PAGE_EXISTS; //La p�gina 1 existe y se imprimir�
	    /*case 1: //P�gina 2: Circunferencia y rect�ngulo
	    g2.setColor(Color.red);
	    g2.draw(circle);
	    g2.draw(rect);
	    return PAGE_EXISTS; //La p�gina 2 existe y se imprimir�
	     */	
	default:
	    return NO_SUCH_PAGE; //No se imprimir�n m�s p�ginas
	}
    }

    public ProcessGraphDocking getDocking() {
        return docking;
    }
}
