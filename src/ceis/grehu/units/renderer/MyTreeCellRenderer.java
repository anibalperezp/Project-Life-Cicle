package ceis.grehu.units.renderer;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import ceis.grehu.gui.ProcessGraphDocking;
import ceis.grehu.gui.TreeActiveDiagrams;
import ceis.grehu.gui.TreeDiagrams;
import ceis.grehu.gui.paint.Diagram;
import ceis.grehu.gui.paint.Stereotype;

public class MyTreeCellRenderer extends DefaultTreeCellRenderer {
    

    /**
     * 
     */
    private static final long serialVersionUID = -3197017799989048876L;
    
    private JPanel treeOwner = null;

    public MyTreeCellRenderer(JPanel tree) {
	treeOwner = tree; 
    }

    public Component getTreeCellRendererComponent(JTree tree,Object value,boolean sel,boolean expanded,boolean leaf,
                        int row,boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row,hasFocus);
        if (leaf && isLastLeaf(value)) {
            setIcon(new ImageIcon(getClass().getResource("/icons/tree1.png")));//lastleafIcon
            setToolTipText("Representacion gráfica del diagrama");
        } 
        if(/*leaf && */isMacroLeaf(value)){
            setIcon(new ImageIcon(getClass().getResource("/icons/gif/MacroProceso.gif")));//macroicon
            setToolTipText("Macroproceso");
        }
        if(/*leaf && */isProcLeaf(value)){
            setIcon(new ImageIcon(getClass().getResource("/icons/gif/Proceso.gif")));//proc icon
            setToolTipText("Proceso");
        }
        if(/*leaf && */isSubProcLeaf(value)){
            setIcon(new ImageIcon(getClass().getResource("/icons/gif/SubProceso.gif")));//proc icon
            setToolTipText("Subproceso");
        }
        if(/*leaf && */isActServLeaf(value)){
            setIcon(new ImageIcon(getClass().getResource("/icons/gif/ActividadServ.gif")));//proc icon
            setToolTipText("Actividad Servicio");
        }
        if(/*leaf && */isActManualLeaf(value)){
            setIcon(new ImageIcon(getClass().getResource("/icons/gif/ActividadManual.gif")));//proc icon
            setToolTipText("Actividad Manual");
        }
        if(leaf && isBaseDatoLeaf(value)){
            setIcon(new ImageIcon(getClass().getResource("/icons/gif/database_l.gif")));//proc icon /icons/database_l.png
            setToolTipText("Base de Datos");
        }
        if(leaf && isDesicionLeaf(value)){
            setIcon(new ImageIcon(getClass().getResource("/icons/gif/Decision.gif")));//proc icon
            setToolTipText("Alternativa");
        }
        if(leaf && isNoteLeaf(value)){
            setIcon(new ImageIcon(getClass().getResource("/icons/gif/Note.gif")));//proc icon
            setToolTipText("Nota");
        }
        if(leaf && isEITiempoLeaf(value)){
            setIcon(new ImageIcon(getClass().getResource("/icons/gif/Initial2.gif")));//proc icon
            setToolTipText("Estado Inicial por Tiempo");
        }
        if(leaf && isEIMensajeLeaf(value)){
            setIcon(new ImageIcon(getClass().getResource("/icons/gif/Initial1.gif")));//proc icon
            setToolTipText("Estado Inicial por Mensaje");
        }
        if(leaf && isEInicialLeaf(value)){
            setIcon(new ImageIcon(getClass().getResource("/icons/gif/Initial.gif")));//proc icon
            setToolTipText("Estado Inicial");
        }
        if(leaf && isEFinalLeaf(value)){
            setIcon(new ImageIcon(getClass().getResource("/icons/gif/FinalState.gif")));//proc icon
            setToolTipText("Estado Final");
        }
        if(/*leaf && */isClienteLeaf(value)){
            setIcon(new ImageIcon(getClass().getResource("/icons/gif/Cliente.gif")));//proc icon
            setToolTipText("Cliente");
        }
        if(/*leaf && */isProveeLeaf(value)){
            setIcon(new ImageIcon(getClass().getResource("/icons/gif/Provee.gif")));//proc icon
            setToolTipText("Proveedor");
        }
        return this;
    }
    
    protected boolean isProveeLeaf(Object value) {//ActividadServ.gif
	boolean check = false;
	DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        String name = (String)node.getUserObject();
        check = determinateLeafByNameAndType(name, "Provee");
	return check;
    }
    
    protected boolean isClienteLeaf(Object value) {//ActividadServ.gif
	boolean check = false;
	DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        String name = (String)node.getUserObject();
        check = determinateLeafByNameAndType(name, "Cliente");
	return check;
    }
    
    protected boolean isEFinalLeaf(Object value) {//ActividadServ.gif
	boolean check = false;
	DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        String name = (String)node.getUserObject();
        check = determinateLeafByNameAndType(name, "EFinal");
	return check;
    }
    
    protected boolean isEInicialLeaf(Object value) {//ActividadServ.gif
	boolean check = false;
	DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        String name = (String)node.getUserObject();
        check = determinateLeafByNameAndType(name, "EInicial");
	return check;
    }
    
    protected boolean isEIMensajeLeaf(Object value) {//ActividadServ.gif
	boolean check = false;
	DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        String name = (String)node.getUserObject();
        check = determinateLeafByNameAndType(name, "EIMensaje");
	return check;
    }
    
    protected boolean isEITiempoLeaf(Object value) {//ActividadServ.gif
	boolean check = false;
	DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        String name = (String)node.getUserObject();
        check = determinateLeafByNameAndType(name, "EITiempo");
	return check;
    }
    
    protected boolean isNoteLeaf(Object value) {//ActividadServ.gif
	boolean check = false;
	DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        String name = (String)node.getUserObject();
        check = determinateLeafByNameAndType(name, "Nota");
	return check;
    }
    
    protected boolean isDesicionLeaf(Object value) {//ActividadServ.gif
	boolean check = false;
	DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        String name = (String)node.getUserObject();
        check = determinateLeafByNameAndType(name, "Decision");
	return check;
    }
    
    protected boolean isBaseDatoLeaf(Object value) {//ActividadServ.gif
	boolean check = false;
	DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        String name = (String)node.getUserObject();
        check = determinateLeafByNameAndType(name, "BaseDato");
	return check;
    }
    
    protected boolean isActManualLeaf(Object value) {//ActividadServ.gif
	boolean check = false;
	DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        String name = (String)node.getUserObject();
        check = determinateLeafByNameAndType(name, "ActManual");
	return check;
    }
    
    protected boolean isActServLeaf(Object value) {//ActividadServ.gif
	boolean check = false;
	DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        String name = (String)node.getUserObject();
        check = determinateLeafByNameAndType(name, "ActServ");
	return check;
    }
    
    protected boolean isSubProcLeaf(Object value) {
	boolean check = false;
	DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        String name = (String)node.getUserObject();
        check = determinateLeafByNameAndType(name, "SubProc");
	return check;
    }
    
    protected boolean isProcLeaf(Object value) {
	boolean check = false;
	DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        String name = (String)node.getUserObject();
        check = determinateLeafByNameAndType(name, "Proc");
	return check;
    }
    
    protected boolean isLastLeaf(Object value) {
	boolean check = false;
	DefaultMutableTreeNode nodeChild = (DefaultMutableTreeNode)value;
	DefaultMutableTreeNode nodeParent = (DefaultMutableTreeNode) nodeChild.getParent();
	    if(nodeParent != null){//porque si es null, es la raiz
        	String nameChild = (String)nodeChild.getUserObject();
        	String nameParent = (String)nodeParent.getUserObject();
        	if (nameChild == nameParent || ("*"+nameChild).equals("*"+nameParent)) {
        	    check = true;
        	}else
        	    check = false;
	    }
	return check;
    }
    
    protected boolean isMacroLeaf(Object value) {
	boolean check = false;
	DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        String name = (String)node.getUserObject();
        check = determinateLeafByNameAndType(name, "Macro");
	return check;
    }

    public JPanel getTreeOwner() {
        return treeOwner;
    }

    protected boolean determinateLeafByNameAndType(String name, String type){
        if (getTreeOwner() instanceof TreeActiveDiagrams) {
            TreeActiveDiagrams treeActiveDiagrams = (TreeActiveDiagrams) getTreeOwner();
	    if(treeActiveDiagrams != null){
	            ProcessGraphDocking processGraphDocking = treeActiveDiagrams.getProcessGraphDocking();
	            if(processGraphDocking != null){
	        	Diagram diagram = treeActiveDiagrams.getProcessGraphDocking().getPaintManager().getActiveDiagram();
	        	if(diagram != null){
	        	    Stereotype stereotype = diagram.findStereotypeByName(name);
	        	    if(stereotype != null){
	        		if (stereotype.getType().equals(type)) {
	        		    return true;
	        		} 
	        	    }
	        	}
	            }
	        }
	}
        if(getTreeOwner() instanceof TreeDiagrams){
            TreeDiagrams treeDiagrams = (TreeDiagrams) getTreeOwner();
            if(treeDiagrams != null){
        	ProcessGraphDocking processGraphDocking = treeDiagrams.getProcessGraphDocking();
        	if(processGraphDocking != null){
        	    for (Diagram diagram : processGraphDocking.getPaintManager().getDiagramList()) {
        		if(diagram != null){
        		    Stereotype stereotype = diagram.findStereotypeByName(name);
        		    if(stereotype != null){
        			if (stereotype.getType().equals(type)) {
        			    return true;
        			} 
        		    }
        		}
        	    }
        	}
            }
        }
        return false;
    }
}
