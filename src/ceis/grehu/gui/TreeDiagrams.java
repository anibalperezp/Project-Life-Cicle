package ceis.grehu.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import ceis.grehu.units.renderer.MyTreeCellRenderer;

import com.vlsolutions.swing.docking.DockKey;
import com.vlsolutions.swing.docking.Dockable;

public class TreeDiagrams extends JPanel implements Dockable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2976207897331635102L;
	
	private JTree treeDiagrams = new JTree();
	
	private DockKey key = new DockKey("Diagramas");
	
	private DefaultTreeCellRenderer defaultTreeCellRendererDiagrams = null;
	
	private DefaultTreeModel defaultTreeModelDiagrams = null;  //  @jve:decl-index=0:
	
	private ProcessGraphDocking processGraphDocking;
	
	
	public TreeDiagrams(ProcessGraphDocking owner) {
		setLayout(new BorderLayout());
		JScrollPane scrollPaneTreeDiagrams = new JScrollPane(treeDiagrams);
		key.setIcon(new ImageIcon(getClass().getResource("/icons/tree.png")));
		
//		Set the icon for last leaf nodes.
	        defaultTreeCellRendererDiagrams = new MyTreeCellRenderer(returnThis());
	        //set open icon
		defaultTreeCellRendererDiagrams.setOpenIcon(new ImageIcon(getClass().getResource("/icons/folder-add.png")));
		//set close icon
		defaultTreeCellRendererDiagrams.setClosedIcon(new ImageIcon(getClass().getResource("/icons/folder2.png")));
	            
	        treeDiagrams.setCellRenderer(defaultTreeCellRendererDiagrams);
		
		treeDiagrams.setEditable(false);
		
		
		//no permite seleccion multiple
		treeDiagrams.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		 //Enable tool tips.
	        ToolTipManager.sharedInstance().registerComponent(treeDiagrams);
		
		treeDiagrams.setShowsRootHandles(true);
		
		treeDiagrams.setRootVisible(false);//default load
		
		treeDiagrams.setDragEnabled(false);
		
		
		treeDiagrams.setModel(getDefaultTreeModelDiagrams());//default load
		
		scrollPaneTreeDiagrams.setPreferredSize(new Dimension(100,100));
		add(scrollPaneTreeDiagrams, BorderLayout.CENTER);
		processGraphDocking = owner;
	}
	private TreeDiagrams returnThis(){
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

	public JTree getTreeDiagrams() {
	    /*if(treeDiagrams == null){
		treeDiagrams = new JTree();
	    }*/
	    return treeDiagrams;
	}

	public void setDefaultTreeModelDiagrams(DefaultTreeModel defaultTreeModelDiagrams) {
	    treeDiagrams.setModel(defaultTreeModelDiagrams);
	}
	
	public void setRootVisible(){
	    treeDiagrams.setRootVisible(true);
	}

	public DefaultTreeCellRenderer getDefaultTreeCellRendererDiagrams() {
	    return defaultTreeCellRendererDiagrams;
	}
	public ProcessGraphDocking getProcessGraphDocking() {
	    return processGraphDocking;
	}
}
