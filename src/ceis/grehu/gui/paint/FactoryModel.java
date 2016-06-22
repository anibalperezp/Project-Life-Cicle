package ceis.grehu.gui.paint;

import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;


import ProcessGraphServer.vo.DiagramaVO;

import ceis.grehu.gui.ProcessGraphDocking;

public class FactoryModel {

	public static FactoryModel factory = new FactoryModel();

	public FactoryModel() {

	}

	public DefaultTreeModel getModelForTreeDiagrams(ArrayList<Diagram> diagramList, ProcessGraphDocking processGraphDocking){
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Diagramas");
		for (Diagram diagram : diagramList) {
			if(diagram.getId() == diagram.getObjectParent()){
				createTreeDiagrams(rootNode, diagram, processGraphDocking);
			}
		}
		
		DefaultTreeModel defaultTreeModelDiagrams = new DefaultTreeModel(rootNode);
		return defaultTreeModelDiagrams;
	}

	public DefaultTreeModel getModelForTreeOpenDiagrams(DiagramaVO[] listMNC, ProcessGraphDocking processGraphDocking){
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("OpenDiagram");
		for (DiagramaVO diagramaVO : listMNC) {
			DefaultMutableTreeNode rootMNC = new DefaultMutableTreeNode(diagramaVO.getNameDiagram());
			processGraphDocking.getPaintManager().getListPararell().add(diagramaVO.getId().intValue());
			createTreeOpenDiagrams(rootMNC, diagramaVO, processGraphDocking);
			rootNode.add(rootMNC);
		}
		DefaultTreeModel defaultTreeModelOpenDiagrams = new DefaultTreeModel(rootNode);
		return defaultTreeModelOpenDiagrams;
	}

	public void createTreeOpenDiagrams(DefaultMutableTreeNode root, DiagramaVO diagramaVO, ProcessGraphDocking processGraphDocking){
		//DefaultMutableTreeNode treeNodeMNC = new DefaultMutableTreeNode(diagramaVO.getNameDiagram());//
		
		DiagramaVO[] diagramaVOs = PaintManager.diagramaService.getAllDiagrams(diagramaVO.getId());
		for (int j = 1; j < diagramaVOs.length; j++) {
			DefaultMutableTreeNode treeNodeDiagram = new DefaultMutableTreeNode(diagramaVOs[j].getNameDiagram());//
			processGraphDocking.getPaintManager().getListPararell().add(diagramaVOs[j].getId().intValue());
			createTreeOpenDiagrams(treeNodeDiagram, diagramaVOs[j], processGraphDocking);
			root.add(treeNodeDiagram);
		}
		//root.add(treeNodeMNC);
	}

	private void createTreeDiagrams(DefaultMutableTreeNode root, Diagram diagram, ProcessGraphDocking processGraphDocking){
		DefaultMutableTreeNode treeNode = null;
		int indexOf = diagram.getName().indexOf("*");
		if(indexOf != -1){
			treeNode = new DefaultMutableTreeNode(diagram.getName().substring(1));//
		}
		else{
			treeNode = new DefaultMutableTreeNode(diagram.getName());//
		}
		
		for(int j = 0; j < diagram.getLength();j ++){
			Stereotype stereotype = diagram.getStereotype(j);
			//creo un nodo a partir del objeto stereotipo
			if(stereotype.getType() != "Nota"){
				DefaultMutableTreeNode nodeChild = new DefaultMutableTreeNode(stereotype.getName());//
				treeNode.add(nodeChild);
				if(stereotype.getDiagramIds().size() > 0){
					ArrayList<Diagram> arrayDiagrams = processGraphDocking.getPaintManager().getDiagramsForIds(stereotype.getDiagramIds());   
					for (Diagram stereotypeDiagram : arrayDiagrams) {
						createTreeDiagrams(nodeChild,stereotypeDiagram, processGraphDocking);
					}
				}
			}
		}
		DefaultMutableTreeNode nodeLast = null;
		if(indexOf != -1){
			nodeLast = new DefaultMutableTreeNode(diagram.getName().substring(1));//
		}
		else{
			nodeLast = new DefaultMutableTreeNode(diagram.getName());//
		}
		
		treeNode.add(nodeLast);
		root.add(treeNode);
	}

	/*private void createTreeDiagrams(DefaultMutableTreeNode root,ArrayList<Diagram> listDiagram){
		for(int i = 0; i < listDiagram.size(); i ++){
			Diagram diagram = listDiagram.get(i);
			//creo un nodo a partir del objeto diagrama
			DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(diagram.getName());//
			for(int j = 0; j < diagram.getLength();j ++){
				Stereotype stereotype = diagram.getStereotype(j);
				//creo un nodo a partir del objeto stereotipo
				if(stereotype.getType() != "Nota"){
				    DefaultMutableTreeNode nodeChild = new DefaultMutableTreeNode(stereotype.getName());//
				    treeNode.add(nodeChild);
				}
			}
			DefaultMutableTreeNode nodeLast = new DefaultMutableTreeNode(diagram.getName());
			treeNode.add(nodeLast);
			root.add(treeNode);	
		}

	}*/

	public DefaultTreeModel getModelForTreeActiveDiagrams(Diagram activeDiagram){
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(activeDiagram.getName());
		int indexOf = activeDiagram.getName().indexOf("*");
		if(indexOf != -1){
			rootNode = new DefaultMutableTreeNode(activeDiagram.getName().substring(1));
		}
		createTreeActiveDiagrams(rootNode,activeDiagram);
		DefaultTreeModel defaultTreeModelActiveDiagrams = new DefaultTreeModel(rootNode);
		return defaultTreeModelActiveDiagrams;
	}

	private void createTreeActiveDiagrams(DefaultMutableTreeNode root,Diagram diagram){
		for(int j = 0; j < diagram.getLength();j ++){
			Stereotype stereotype = diagram.getStereotype(j);
			//creo un nodo a partir del objeto stereotipo
			//Integer id = new Integer(stereotype.getId());
			if(stereotype.getType() != "Nota"){
				DefaultMutableTreeNode nodeChild = new DefaultMutableTreeNode(stereotype.getName());//
				root.add(nodeChild);
			}
		}
		DefaultMutableTreeNode nodeLast = new DefaultMutableTreeNode(diagram.getName());
		int indexOf = diagram.getName().indexOf("*");
		if(indexOf != -1){
			nodeLast = new DefaultMutableTreeNode(diagram.getName().substring(1));
		}
		root.add(nodeLast);
	}

	public DefaultComboBoxModel getModelForComboboxProcess(Diagram activeDiagram){
		DefaultComboBoxModel defaultComboBoxModelProcess  = new DefaultComboBoxModel();
		defaultComboBoxModelProcess.setSelectedItem("<<Procesos>>");
		for(int i =0;i<activeDiagram.getLength();i++){
			if(activeDiagram.getStereotype(i).getType() != "Nota")
				defaultComboBoxModelProcess.addElement(activeDiagram.getStereotype(i).getName());
		}
		return defaultComboBoxModelProcess;
	}

	public DefaultTableModel getModelForTablePropertiesToStereotype(Stereotype selectedStereotype){
		DefaultTableModel defaultTableModelProperties = new DefaultTableModel();
		ArrayList<Object> columnDataAtr = new ArrayList<Object>();
		ArrayList<Object> columnDataVal = new ArrayList<Object>();
		if(selectedStereotype.getType() != "Nota"){
			for(int i =0; i < 11 ; i++){
				if(i == 0){
					columnDataAtr.add(ShowToolTipText.getFont("nombre"));
					columnDataVal.add(selectedStereotype.getName());
				}
				if(i == 1){
					columnDataAtr.add(ShowToolTipText.getFont("tipo"));
					columnDataVal.add(selectedStereotype.getType());
				}
				if(i == 2){
					columnDataAtr.add(ShowToolTipText.getFont("punto inicial"));
					columnDataVal.add(selectedStereotype.getInicialPoint().x + " ; " + selectedStereotype.getInicialPoint().y);
				}
				if(i == 3){
					columnDataAtr.add(ShowToolTipText.getFont("punto final"));
					columnDataVal.add(selectedStereotype.getFinalPoint().x + " ; " + selectedStereotype.getFinalPoint().y);
				}
				if(i == 4){
					columnDataAtr.add(ShowToolTipText.getFont("seleccionado"));
					columnDataVal.add(selectedStereotype.isSelected());
				}
				if(i == 5){
					columnDataAtr.add(ShowToolTipText.getFont("fondo"));
					columnDataVal.add(selectedStereotype.getBackground().getRed() +" , " + selectedStereotype.getBackground().getGreen() + " , " +selectedStereotype.getBackground().getBlue());
				}
				if(i == 6){
					columnDataAtr.add(ShowToolTipText.getFont("linea"));
					columnDataVal.add(selectedStereotype.getLineColor().getRed() +" , " + selectedStereotype.getLineColor().getGreen() + " , " +selectedStereotype.getLineColor().getBlue());
				}
				if(i == 7){
					columnDataAtr.add(ShowToolTipText.getFont("ancho"));
					columnDataVal.add(selectedStereotype.getWidth());
				}
				if(i == 8){
					columnDataAtr.add(ShowToolTipText.getFont("altura"));
					columnDataVal.add(selectedStereotype.getHeigth());
				}
				if(i == 9){
					columnDataAtr.add(ShowToolTipText.getFont("visible"));
					columnDataVal.add(selectedStereotype.isVisible());
				}
				if(i == 10){
					columnDataAtr.add(ShowToolTipText.getFont("descripción"));
					columnDataVal.add(selectedStereotype.getDescription());
				}
			}
			defaultTableModelProperties.setRowCount(10);
			defaultTableModelProperties.addColumn(ShowToolTipText.getFont("Propiedad"),columnDataAtr.toArray());
			defaultTableModelProperties.addColumn(ShowToolTipText.getFont("Valor"),columnDataVal.toArray());
		}else{//es la nota
			for(int i =0; i < 8 ; i++){
				if(i == 0){
					columnDataAtr.add(ShowToolTipText.getFont("punto inicial"));
					columnDataVal.add(selectedStereotype.getInicialPoint().x + " ; " + selectedStereotype.getInicialPoint().y);
				}
				if(i == 1){
					columnDataAtr.add(ShowToolTipText.getFont("punto final"));
					columnDataVal.add(selectedStereotype.getFinalPoint().x + " ; " + selectedStereotype.getFinalPoint().y);
				}
				if(i == 2){
					columnDataAtr.add(ShowToolTipText.getFont("seleccionado"));
					columnDataVal.add(selectedStereotype.isSelected());
				}
				if(i == 3){
					columnDataAtr.add(ShowToolTipText.getFont("fondo"));
					columnDataVal.add(selectedStereotype.getBackground().getRed() +" , " + selectedStereotype.getBackground().getGreen() + " , " +selectedStereotype.getBackground().getBlue());
				}
				if(i == 4){
					columnDataAtr.add(ShowToolTipText.getFont("linea"));
					columnDataVal.add(selectedStereotype.getLineColor().getRed() +" , " + selectedStereotype.getLineColor().getGreen() + " , " +selectedStereotype.getLineColor().getBlue());
				}
				if(i == 5){
					columnDataAtr.add(ShowToolTipText.getFont("ancho"));
					columnDataVal.add(selectedStereotype.getWidth());
				}
				if(i == 6){
					columnDataAtr.add(ShowToolTipText.getFont("altura"));
					columnDataVal.add(selectedStereotype.getHeigth());
				}
				if(i == 7){
					columnDataAtr.add(ShowToolTipText.getFont("descripción"));
					columnDataVal.add(selectedStereotype.getDescription());
				}
			}
			defaultTableModelProperties.setRowCount(8);
			defaultTableModelProperties.addColumn(ShowToolTipText.getFont("Propiedad"),columnDataAtr.toArray());
			defaultTableModelProperties.addColumn(ShowToolTipText.getFont("Valor"),columnDataVal.toArray());
		}
		return defaultTableModelProperties;
	}

	public DefaultTableModel getModelForTablePropertiesToDiagram(Diagram activeDiagram){
		DefaultTableModel defaultTableModelProperties = new DefaultTableModel();
		ArrayList<Object> columnDataAtr = new ArrayList<Object>();
		ArrayList<Object> columnDataVal = new ArrayList<Object>();
		for(int i =0; i < 7 ; i++){
			if(i == 0){
				columnDataAtr.add(ShowToolTipText.getFont("nombre"));
				int indexOf = activeDiagram.getName().indexOf("*");
				if(indexOf != -1){
					columnDataVal.add(activeDiagram.getName().substring(1));
				}
				else{
					columnDataVal.add(activeDiagram.getName());
				}
			}
			if(i == 1){
				columnDataAtr.add(ShowToolTipText.getFont("tipo"));
				columnDataVal.add(activeDiagram.getType());
			}
			if(i == 2){
				columnDataAtr.add(ShowToolTipText.getFont("padre"));
				columnDataVal.add(activeDiagram.getObjectParent());
			}
			if(i == 3){
				columnDataAtr.add(ShowToolTipText.getFont("factor de zoom"));
				columnDataVal.add(activeDiagram.getZoomFactor().toString().substring(0, 3));
			}
			if(i == 4){
				columnDataAtr.add(ShowToolTipText.getFont("fondo"));
				columnDataVal.add(activeDiagram.getBackground().getRed() +" , " + activeDiagram.getBackground().getGreen() + " , " +activeDiagram.getBackground().getBlue());
			}
			if(i == 5){
				columnDataAtr.add(ShowToolTipText.getFont("tamaño"));
				columnDataVal.add(activeDiagram.getDiagramSize().width + "," + activeDiagram.getDiagramSize().height);
			}
			if(i == 6){
				columnDataAtr.add(ShowToolTipText.getFont("rectangulo visible"));
				columnDataVal.add(activeDiagram.getRectVisible().x +","+activeDiagram.getRectVisible().y+","+activeDiagram.getRectVisible().width+","+activeDiagram.getRectVisible().height);
			}
		}

		defaultTableModelProperties.setRowCount(7);
		defaultTableModelProperties.addColumn(ShowToolTipText.getFont("Propiedad"),columnDataAtr.toArray());
		defaultTableModelProperties.addColumn(ShowToolTipText.getFont("Valor"),columnDataVal.toArray());
		return defaultTableModelProperties;
	}

}
