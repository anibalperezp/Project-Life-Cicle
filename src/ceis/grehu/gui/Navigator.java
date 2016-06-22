package ceis.grehu.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


import com.vlsolutions.swing.docking.DockKey;
import com.vlsolutions.swing.docking.Dockable;

public class Navigator extends JPanel implements Dockable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 6591861254787100380L;
	
	private DockKey key = new DockKey("Navegador");
	
	private ProcessGraphDocking processGraphDocking = null;

	private PanelOverView panelOverView = null;
	
	//private Rectangle diagramBounds = null;  //  @jve:decl-index=0: Tamaño del panel work area
    
	public Navigator(ProcessGraphDocking parent) {
	    processGraphDocking = parent;
	    setLayout(null);
	    key.setIcon(new ImageIcon(getClass().getResource("/icons/find1.png")));
	    initialize();
	
	}
	
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
	    this.setLayout(null);
	    this.setBackground(new Color(237,237,237));//gray super ligth
	    //this.setSize(new Dimension(167, 156));
	    this.add(getPanelOverView(), null);
	    
	    this.addComponentListener(new java.awt.event.ComponentAdapter() {
	        public void componentResized(java.awt.event.ComponentEvent e) {
	            getProcessGraphDocking().getPaintManager().setSizeDiagramOverview(new Rectangle(getSize()),getPanelOverView());
	        }
	    });
	}
	/**
	 * This method initializes this
	 * 
	 */
	public DockKey getDockKey() {
		return key;
	}

	public Component getComponent() {
		return this;
	}

	public ProcessGraphDocking getProcessGraphDocking() {
	    return processGraphDocking;
	}

	/**
	 * This method initializes panelOverView	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public PanelOverView getPanelOverView() {
	    if (panelOverView == null) {
		panelOverView = new PanelOverView(returnThis());
	    }
	    return panelOverView;
	}
	
	
	public Navigator returnThis(){
	    return this;
	}

	/*public Rectangle getDiagramBounds() {
	    return diagramBounds;
	}

	public void setDiagramBounds(Rectangle diagramBounds) {
	    this.diagramBounds = diagramBounds;
	}*/

}  //  @jve:decl-index=0:visual-constraint="10,10"