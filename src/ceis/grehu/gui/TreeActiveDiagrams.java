package ceis.grehu.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;


import ceis.grehu.gui.paint.Diagram;
import ceis.grehu.gui.paint.FactoryModel;
import ceis.grehu.gui.paint.Stereotype;
import ceis.grehu.units.renderer.MyTreeCellRenderer;

import com.vlsolutions.swing.docking.DockKey;
import com.vlsolutions.swing.docking.Dockable;

public class TreeActiveDiagrams extends JPanel implements Dockable, DragGestureListener, DragSourceListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8211806286710644061L;

	private JTree treeActiveDiagrams = null;

	private DockKey key = new DockKey("Diagrama Activo");

	private DefaultTreeCellRenderer defaultTreeCellRendererDiagrams = null;

	private DefaultTreeModel defaultTreeModelDiagrams = null;

	private DragSource dragSource;

	private ProcessGraphDocking processGraphDocking;


	public TreeActiveDiagrams(ProcessGraphDocking owner) {
		setLayout(new BorderLayout());
		JScrollPane scrollPaneTreeActiveDiagrams = new JScrollPane(getTreeActiveDiagrams());
		scrollPaneTreeActiveDiagrams.setPreferredSize(new Dimension(100,100));
		add(scrollPaneTreeActiveDiagrams, BorderLayout.CENTER);

		//treeActiveDiagrams.setDragEnabled(true);
		/**
		 * Making a Component Draggable
		 */
		dragSource = new DragSource();
		dragSource.createDefaultDragGestureRecognizer(treeActiveDiagrams, DnDConstants.ACTION_COPY_OR_MOVE, this);
		/**
		 * Making a Component Draggable
		 */

		treeActiveDiagrams.setShowsRootHandles(true);
		treeActiveDiagrams.setRootVisible(false);
		key.setIcon(new ImageIcon(getClass().getResource("/icons/tree1.png")));

		//Set the icon for last leaf nodes.
		defaultTreeCellRendererDiagrams = new MyTreeCellRenderer(returnThis());
		//set open icon
		defaultTreeCellRendererDiagrams.setOpenIcon(new ImageIcon(getClass().getResource("/icons/folder-add.png")));
		//set close icon
		defaultTreeCellRendererDiagrams.setClosedIcon(new ImageIcon(getClass().getResource("/icons/folder2.png")));

		treeActiveDiagrams.setCellRenderer(defaultTreeCellRendererDiagrams);

		treeActiveDiagrams.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		treeActiveDiagrams.setEditable(false);
		//Enable tool tips.
		ToolTipManager.sharedInstance().registerComponent(treeActiveDiagrams);
		treeActiveDiagrams.setModel(getDefaultTreeModelDiagrams());//default load
		processGraphDocking = owner;
	}
	private TreeActiveDiagrams returnThis(){
		return this;
	}
	/**
	 * Este metodo es para que el treeview aparesca en blanco
	 * @return
	 */
	public DefaultTreeModel getDefaultTreeModelDiagrams() {//default load
		if (defaultTreeModelDiagrams == null) {
			DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Vacio");
			defaultTreeModelDiagrams = new DefaultTreeModel(rootNode);
		}
		return defaultTreeModelDiagrams;
	}

	public DockKey getDockKey() {
		return key;
	}

	public Component getComponent() {
		return this;
	}

	public void setDefaultTreeModelDiagrams(DefaultTreeModel defaultTreeModelDiagrams) {
		treeActiveDiagrams.setModel(defaultTreeModelDiagrams);
	}

	public void setRootVisible(){
		treeActiveDiagrams.setRootVisible(true);
	}
	/**
	 * Making a Component Draggable
	 */
	public void dragGestureRecognized(DragGestureEvent evt) {
		int location = treeActiveDiagrams.getRowForLocation(evt.getDragOrigin().x, evt.getDragOrigin().y);
		if(location != -1 && location != 0 && location != treeActiveDiagrams.getRowCount()- 1){
			// no fue en el vacio && no es la raiz && no es la representacion grafica
			String selectionPath = treeActiveDiagrams.getPathForLocation(evt.getDragOrigin().x, evt.getDragOrigin().y).toString();
			String nodeName = selectionPath.substring(selectionPath.indexOf(",") + 2,selectionPath.indexOf("]"));
			String nodeId = new Integer(getProcessGraphDocking().getPaintManager().getActiveDiagram().findIdByName(nodeName)).toString();
			//+ 2 para que tome la , y el espacio.
			//este name busvcarlo en la lista de stereotipos de diagrama activo si esta pongo el cursor de mover
			// si no esta pongo el de copiar
			Transferable t = new StringSelection(nodeId);
			dragSource.startDrag (evt, DragSource.DefaultLinkNoDrop, t, this);//esto es lo que hay que poner en los eventos
		}
	}
	public void dragEnter(DragSourceDragEvent evt) {
		// Called when the user is dragging this drag source and enters
		// the drop target.
		evt.getDragSourceContext().setCursor(DragSource.DefaultLinkDrop);
	}
	public void dragOver(DragSourceDragEvent evt) {
		// Called when the user is dragging this drag source and moves
		// over the drop target.
		evt.getDragSourceContext().setCursor(DragSource.DefaultLinkDrop);
	}
	public void dragExit(DragSourceEvent evt) {
		// Called when the user is dragging this drag source and leaves
		// the drop target.
		evt.getDragSourceContext().setCursor(DragSource.DefaultLinkNoDrop);
	}
	public void dropActionChanged(DragSourceDragEvent evt) {
		// Called when the user changes the drag action between copy or move.
	}
	public void dragDropEnd(DragSourceDropEvent evt) {
		// Called when the user finishes or cancels the drag operation.
	}
	/**
	 * Making a Component Draggable
	 */
	public DragSource getDragSource() {
		return dragSource;
	}

	public void setRootVisible(boolean visible){
		treeActiveDiagrams.setRootVisible(visible);
	}
	public ProcessGraphDocking getProcessGraphDocking() {
		return processGraphDocking;
	}
	public JTree getTreeActiveDiagrams() {
		if(treeActiveDiagrams == null){
			treeActiveDiagrams = new JTree();
			treeActiveDiagrams.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {

					Diagram diagram = getProcessGraphDocking().getPaintManager().getActiveDiagram();
					int location = treeActiveDiagrams.getRowForLocation(e.getX(), e.getY());
					if(location != -1){//no fue en el vacio 
						if(location != 0 && location != treeActiveDiagrams.getRowCount()- 1){
							String selectionPath = treeActiveDiagrams.getPathForLocation(e.getX(), e.getY()).toString();
							String nodeName = selectionPath.substring(selectionPath.indexOf(",") + 2,selectionPath.length()-1);
							int id = diagram.findIdByName(nodeName);
							Stereotype stereotype =  diagram.findById(id);
							diagram.unselectAllStereotype();
							stereotype.setSelected(true);
							if(e.getButton() == 1 && getProcessGraphDocking().getPaintManager().getActiveDiagram() != null){
								if(stereotype.getType() != "Nota")
									getProcessGraphDocking().getTableProperties().getComboBoxProcess().getModel().setSelectedItem(stereotype.getName());
								getProcessGraphDocking().getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToStereotype(stereotype));
								getProcessGraphDocking().getTabbedPaneWorkArea().repaint();
								getProcessGraphDocking().getComboBoxSelected().setVisible(false);
								getProcessGraphDocking().getComboBoxType().setVisible(false);
								getProcessGraphDocking().getComboBoxVisible().setVisible(false);
								getProcessGraphDocking().getScrollPaneDescription().setVisible(false);
							}
							if(e.getButton() == 3 && getProcessGraphDocking().getPaintManager().getActiveDiagram() != null){
								treeActiveDiagrams.setSelectionRow(location);
								getProcessGraphDocking().getTabbedPaneWorkArea().repaint();
								Point mousePosition = treeActiveDiagrams.getMousePosition();
								getProcessGraphDocking().getTabbedPaneWorkArea().getPopupMenuWorkAreaToStereotype().show(treeActiveDiagrams, mousePosition.x, mousePosition.y);
							}
						}else if(location == 0 || location == treeActiveDiagrams.getRowCount()- 1){
							diagram.unselectAllStereotype();
							getProcessGraphDocking().getTabbedPaneWorkArea().repaint();
							if(e.getButton() == 1 && getProcessGraphDocking().getPaintManager().getActiveDiagram() != null){
								getProcessGraphDocking().getTableProperties().getComboBoxProcess().getModel().setSelectedItem("<<Procesos>>");
								getProcessGraphDocking().getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToDiagram(diagram));
							}
							if(e.getButton() == 3 && getProcessGraphDocking().getPaintManager().getActiveDiagram() != null){
								treeActiveDiagrams.setSelectionRow(location);
								Point mousePosition = treeActiveDiagrams.getMousePosition();
								getProcessGraphDocking().getTabbedPaneWorkArea().getPopupMenuWorkAreaToDiagram().show(treeActiveDiagrams, mousePosition.x, mousePosition.y);
							}
						}
					}else if(location == -1){
						if(diagram != null){
							treeActiveDiagrams.clearSelection();
							diagram.unselectAllStereotype();
							getProcessGraphDocking().getTabbedPaneWorkArea().repaint();
							getProcessGraphDocking().getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToDiagram(diagram));
							getProcessGraphDocking().getTableProperties().getComboBoxProcess().getModel().setSelectedItem("<<Procesos>>");
						}
					}
				}
			});
		}
		return treeActiveDiagrams;
	}

	public void setSelectionToRow(Stereotype selectStereotype){	    //-1 para no cojer el ultimo que es la representacion grafica
		for (int i = 1; i < treeActiveDiagrams.getRowCount() - 1; i++) {//=1 para no cojer el primero que es el nombre del diagrama
			String path = treeActiveDiagrams.getPathForRow(i).toString();
			String nodeName = path.substring(path.indexOf(",") + 2,path.length() -1);
			if(nodeName.equals(selectStereotype.getName())){
				treeActiveDiagrams.setSelectionRow(i);
				break;
			}
		}
	}
}
