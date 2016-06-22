package ceis.grehu.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;

import ceis.grehu.gui.paint.Diagram;
import ceis.grehu.gui.paint.FactoryModel;
import ceis.grehu.gui.paint.ShowToolTipText;
import ceis.grehu.gui.paint.Stereotype;

import com.vlsolutions.swing.docking.DockKey;
import com.vlsolutions.swing.docking.Dockable;

public class TableProperties extends JPanel implements Dockable, DropTargetListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8893909415608705083L;

	private JTable tableProperties = null;

	private DockKey key = new DockKey("Propiedades");

	private DefaultTableModel defaultTableModelProperties = null;

	private JComboBox comboBoxProcess = null;  //  @jve:decl-index=0:visual-constraint="10,124"

	private BasicComboBoxRenderer.UIResource UIResource = null;

	private DefaultComboBoxModel defaultComboBoxModelProcess = null;

	private ProcessGraphDocking processGraphDocking = null;

	private String descriptionTxt = "";  //  @jve:decl-index=0:

	/**
	 * This method initializes this
	 * 
	 */
	/*private void initialize() {
        this.setSize(new Dimension(194, 104));

	}*/
	public TableProperties(ProcessGraphDocking owner) {
		setLayout(new BorderLayout());
		new DropTarget(this, this);
		getTableProperties().setModel(getDefaultTableModelProperties());
		JScrollPane scrollPaneProperties = new JScrollPane(getTableProperties());
		scrollPaneProperties.setPreferredSize(new Dimension(100,100));
		add(getComboBoxProcess(),BorderLayout.NORTH);
		add(scrollPaneProperties, BorderLayout.CENTER);

		key.setIcon(new ImageIcon(getClass().getResource("/icons/table.png")));
		processGraphDocking = owner;
		descriptionTxt = "";
	}


	public DefaultTableModel getDefaultTableModelProperties() {//vacio
		if (defaultTableModelProperties == null) {
			defaultTableModelProperties = new DefaultTableModel();
			defaultTableModelProperties.setRowCount(0);
			defaultTableModelProperties.addColumn(ShowToolTipText.getFont("Propiedad"));
			defaultTableModelProperties.addColumn(ShowToolTipText.getFont("Valor"));
		}
		return defaultTableModelProperties;
	}

	public DefaultComboBoxModel getDefaultComboBoxModelProcess() {//vacio
		if (defaultComboBoxModelProcess == null) {
			defaultComboBoxModelProcess = new DefaultComboBoxModel();
			defaultComboBoxModelProcess.setSelectedItem("<<Procesos>>");
		}
		return defaultComboBoxModelProcess;
	}
	public JComboBox getComboBoxProcess() {
		if (comboBoxProcess == null) {
			UIResource = new BasicComboBoxRenderer.UIResource();
			UIResource.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			comboBoxProcess = new JComboBox();
			comboBoxProcess.setPreferredSize(new java.awt.Dimension(31, 20));
			comboBoxProcess.setSize(new Dimension(177, 20));
			comboBoxProcess.setRenderer(UIResource);
			comboBoxProcess.setModel(getDefaultComboBoxModelProcess());//default
			comboBoxProcess.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String selection = (String)comboBoxProcess.getSelectedItem();
					if(selection != "" && selection != "<<Procesos>>"){
						String name = selection;
						//Integer integerId =  new Integer(name);
						Diagram diagram = getProcessGraphDocking().getPaintManager().getActiveDiagram();
						diagram.unselectAllStereotype();
						int id = diagram.findIdByName(name);
						Stereotype stereotype = diagram.findById(id);
						stereotype.setSelected(true);
						getProcessGraphDocking().getTreeActiveDiagrams().setSelectionToRow(stereotype);
						getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToStereotype(stereotype));
						getProcessGraphDocking().getTabbedPaneWorkArea().repaint();
					}
				}
			});
		}
		return comboBoxProcess;
	}
	//initialize();

	public DockKey getDockKey() {
		// TODO Auto-generated method stub
		return key;
	}

	public Component getComponent() {
		// TODO Auto-generated method stub
		return this;
	}

	public void setDefaultComboBoxModelProcess(DefaultComboBoxModel defaultComboBoxModelProcess) {
		comboBoxProcess.setModel(defaultComboBoxModelProcess);
	}

	@SuppressWarnings("serial")
	public JTable getTableProperties() {
		if(tableProperties == null){
			tableProperties = new JTable(){
				public boolean isCellEditable(int row, int column)
				{
					switch (column) {
					case 1:
						return true;
					default:
						return false;
					}
				}
			};
			tableProperties.setRowHeight(18);
			tableProperties.setMinimumSize(new Dimension(375, 90));
			tableProperties.addMouseListener(new java.awt.event.MouseAdapter() {   
				public void mousePressed(java.awt.event.MouseEvent e) {
					Diagram diagramAct = getProcessGraphDocking().getPaintManager().getActiveDiagram();
					if(tableProperties.getRowCount() == 7){//es la tabla de diagrama
						if(tableProperties.getSelectedRow() == 4){//color de fondo
							getProcessGraphDocking().getButtonChosserBackGround().setSize(30, tableProperties.getRowHeight(4));
							int widthOnScreen = tableProperties.getWidth() - getProcessGraphDocking().getButtonChosserBackGround().getWidth();
							int heightOnScreen = tableProperties.getRowHeight() * 4;
							getProcessGraphDocking().getButtonChosserBackGround().setLocation(widthOnScreen, heightOnScreen);
							getProcessGraphDocking().getButtonChosserBackGround().setVisible(true);

							String color = (String)tableProperties.getValueAt(4, 1);
							try{
								String r = color.substring(0, color.indexOf(',') -1);// -1 para quitar un espacio al final
								int red = new Integer(r).intValue();
								color = color.substring(color.indexOf(',') + 2);
								String g = color.substring(0, color.indexOf(',') -1);
								int green = new Integer(g).intValue();
								color = color.substring(color.indexOf(',') + 2);
								int blue = new Integer(color).intValue();
								Color colorNew = new Color(red,green,blue); 
								//asignar
								diagramAct.setBackground(colorNew);
								getProcessGraphDocking().getTabbedPaneWorkArea().setColorToPanelWorkArea();
								getProcessGraphDocking().getTabbedPaneWorkArea().repaint();
							}catch(Exception exception){
								String backGround = diagramAct.getBackground().getRed() +" , " + diagramAct.getBackground().getGreen() + " , " +diagramAct.getBackground().getBlue();
								tableProperties.setValueAt(backGround, 4, 1);
							}
						}else{
							String backGround = diagramAct.getBackground().getRed() +" , " + diagramAct.getBackground().getGreen() + " , " +diagramAct.getBackground().getBlue();
							tableProperties.setValueAt(backGround, 4, 1);
							getProcessGraphDocking().getButtonChosserBackGround().setVisible(false);
						}
					}
					if(tableProperties.getRowCount() == 8){//es una nota
						if(tableProperties.getSelectedRow() == 2){//seleccionado
							Rectangle cellBounds = tableProperties.getCellRect(2, 1, true);
							getProcessGraphDocking().getComboBoxSelected().setSize(cellBounds.width,cellBounds.height);
							getProcessGraphDocking().getComboBoxSelected().setLocation(cellBounds.x, cellBounds.y);
							if(diagramAct.getSelectedStereotype().isSelected()){
								getProcessGraphDocking().getComboBoxSelected().setSelectedItem(true);
							}else{
								getProcessGraphDocking().getComboBoxSelected().setSelectedItem(false);
							}
							getProcessGraphDocking().getComboBoxSelected().setVisible(true);
						}else{
							boolean select = diagramAct.getSelectedStereotype().isSelected();
							tableProperties.setValueAt(select, 2, 1); 
							getProcessGraphDocking().getComboBoxSelected().setVisible(false);
						}
						if(tableProperties.getSelectedRow() == 3){//color de fondo
							getProcessGraphDocking().getButtonChosserBackGround().setSize(30, tableProperties.getRowHeight(3));
							int widthOnScreen = tableProperties.getWidth() - getProcessGraphDocking().getButtonChosserBackGround().getWidth();
							int heightOnScreen = tableProperties.getRowHeight() * 3;
							getProcessGraphDocking().getButtonChosserBackGround().setLocation(widthOnScreen, heightOnScreen);
							getProcessGraphDocking().getButtonChosserBackGround().setVisible(true);
							String color = (String)tableProperties.getValueAt(3, 1);
							try{
								String r = color.substring(0, color.indexOf(',') -1);// -1 para quitar un espacio al final
								int red = new Integer(r).intValue();
								color = color.substring(color.indexOf(',') + 2);
								String g = color.substring(0, color.indexOf(',') -1);
								int green = new Integer(g).intValue();
								color = color.substring(color.indexOf(',') + 2);
								int blue = new Integer(color).intValue();
								Color colorNew = new Color(red,green,blue); 
								//asignar
								diagramAct.getSelectedStereotype().setBackground(colorNew);
								getProcessGraphDocking().getTabbedPaneWorkArea().repaint();
							}catch(Exception exception){
								String backGround = diagramAct.getSelectedStereotype().getBackground().getRed() +" , " + diagramAct.getSelectedStereotype().getBackground().getGreen() + " , " +diagramAct.getSelectedStereotype().getBackground().getBlue();
								tableProperties.setValueAt(backGround, 3, 1);
							}
						}else{
							String backGround = diagramAct.getSelectedStereotype().getBackground().getRed() +" , " + diagramAct.getSelectedStereotype().getBackground().getGreen() + " , " +diagramAct.getSelectedStereotype().getBackground().getBlue();
							tableProperties.setValueAt(backGround, 3, 1); 
							getProcessGraphDocking().getButtonChosserBackGround().setVisible(false);
						}
						if(tableProperties.getSelectedRow() == 4){//color de linea
							getProcessGraphDocking().getButtonChooserLine().setSize(30, tableProperties.getRowHeight(4));
							int widthOnScreen = tableProperties.getWidth() - getProcessGraphDocking().getButtonChooserLine().getWidth();
							int heightOnScreen = tableProperties.getRowHeight() * 4;
							getProcessGraphDocking().getButtonChooserLine().setLocation(widthOnScreen, heightOnScreen);
							getProcessGraphDocking().getButtonChooserLine().setVisible(true);
							String color = (String)tableProperties.getValueAt(4, 1);
							try{
								String r = color.substring(0, color.indexOf(',') -1);// -1 para quitar un espacio al final
								int red = new Integer(r).intValue();
								color = color.substring(color.indexOf(',') + 2);
								String g = color.substring(0, color.indexOf(',') -1);
								int green = new Integer(g).intValue();
								color = color.substring(color.indexOf(',') + 2);
								int blue = new Integer(color).intValue();
								Color colorNew = new Color(red,green,blue); 
								//asignar
								diagramAct.getSelectedStereotype().setLineColor(colorNew);
								getProcessGraphDocking().getTabbedPaneWorkArea().repaint();
							}catch(Exception exception){
								String line = diagramAct.getSelectedStereotype().getLineColor().getRed() +" , " + diagramAct.getSelectedStereotype().getLineColor().getGreen() + " , " +diagramAct.getSelectedStereotype().getLineColor().getBlue();
								tableProperties.setValueAt(line, 4, 1);
							}
						}else{
							String line = diagramAct.getSelectedStereotype().getLineColor().getRed() +" , " + diagramAct.getSelectedStereotype().getLineColor().getGreen() + " , " +diagramAct.getSelectedStereotype().getLineColor().getBlue();
							tableProperties.setValueAt(line, 4, 1); 
							getProcessGraphDocking().getButtonChooserLine().setVisible(false);
						}
						if(tableProperties.getSelectedRow() == 7){// descripcion
							//if(tableProperties.getSelectedColumn() == 1){
							Rectangle cellBounds = tableProperties.getCellRect(7, 1, true);
							getProcessGraphDocking().getScrollPaneDescription().setSize(cellBounds.width,cellBounds.height);
							getProcessGraphDocking().getScrollPaneDescription().setLocation(cellBounds.x, cellBounds.y);
							getProcessGraphDocking().getTextAreaDescription().setText(diagramAct.getSelectedStereotype().getDescription());
							getProcessGraphDocking().getScrollPaneDescription().setVisible(true);
							getProcessGraphDocking().getTextAreaDescription().grabFocus();
							//}else{
							//encontrar el problema
							//getProcessGraphDocking().getTextAreaDescription().setText(diagramAct.getSelectedStereotype().getDescription());
							//diagramAct.getSelectedStereotype().setDescription(getProcessGraphDocking().getTextAreaDescription().getText());
							//getProcessGraphDocking().getJTextAreaNote().setText(getProcessGraphDocking().getTextAreaDescription().getText());
							//tableProperties.setValueAt(diagramAct.getSelectedStereotype().getDescription(), 7, 1);
							//getProcessGraphDocking().getTabbedPaneWorkArea().repaint();
							//getProcessGraphDocking().getScrollPaneDescription().setVisible(false);
							//}
						}else{
							String description = diagramAct.getSelectedStereotype().getDescription();
							tableProperties.setValueAt(description, 7, 1); 
							getProcessGraphDocking().getScrollPaneDescription().setVisible(false);
						}
					}
					if(tableProperties.getRowCount() == 11){//es un estereoyipo real
						if(tableProperties.getSelectedRow() == 1){//Tipo de estereotipo
							Rectangle cellBounds = tableProperties.getCellRect(1, 1, true);
							getProcessGraphDocking().getComboBoxType().setSize(cellBounds.width,cellBounds.height);
							getProcessGraphDocking().getComboBoxType().setLocation(cellBounds.x, cellBounds.y);
							String type = diagramAct.getSelectedStereotype().getType();
							if(type == "Proc" || type == "ActServ"
								|| type == "ActManual" || type == "Cliente" || type == "Provee"){
								getProcessGraphDocking().getComboBoxType().setModel(getProcessGraphDocking().getDefaultComboBoxModelType());
								getProcessGraphDocking().getComboBoxType().setSelectedItem(type);
								getProcessGraphDocking().getComboBoxType().setVisible(true);
							}
							if(type == "EITiempo" || type == "EIMensaje" || type == "EInicial" || type == "EFinal"){
								getProcessGraphDocking().getComboBoxType().setModel(getProcessGraphDocking().getDefaultComboBoxModelTypeEstate());
								getProcessGraphDocking().getComboBoxType().setSelectedItem(type);
								getProcessGraphDocking().getComboBoxType().setVisible(true);
							}
							if(type == "Decision" || type == "Nota"){
								getProcessGraphDocking().getComboBoxType().setSelectedItem(type);
								getProcessGraphDocking().getComboBoxType().setVisible(false);
							}

						}else{
							String type = diagramAct.getSelectedStereotype().getType();
							tableProperties.setValueAt(type, 1, 1); 
							getProcessGraphDocking().getComboBoxType().setVisible(false);
						}
						if(tableProperties.getSelectedRow() == 4){//seleccionado
							Rectangle cellBounds = tableProperties.getCellRect(4, 1, true);
							getProcessGraphDocking().getComboBoxSelected().setSize(cellBounds.width,cellBounds.height);
							getProcessGraphDocking().getComboBoxSelected().setLocation(cellBounds.x, cellBounds.y);
							if(diagramAct.getSelectedStereotype().isSelected()){
								getProcessGraphDocking().getComboBoxSelected().setSelectedItem(true);
							}else{
								getProcessGraphDocking().getComboBoxSelected().setSelectedItem(false);
							}
							getProcessGraphDocking().getComboBoxSelected().setVisible(true);
						}else{
							boolean select = diagramAct.getSelectedStereotype().isSelected();
							tableProperties.setValueAt(select, 4, 1); 
							getProcessGraphDocking().getComboBoxSelected().setVisible(false);
						}
						if(tableProperties.getSelectedRow() == 5){//color de fondo
							getProcessGraphDocking().getButtonChosserBackGround().setSize(30, tableProperties.getRowHeight(5));
							int widthOnScreen = tableProperties.getWidth() - getProcessGraphDocking().getButtonChosserBackGround().getWidth();
							int heightOnScreen = tableProperties.getRowHeight() * 5;
							getProcessGraphDocking().getButtonChosserBackGround().setLocation(widthOnScreen, heightOnScreen);
							getProcessGraphDocking().getButtonChosserBackGround().setVisible(true);
							String color = (String)tableProperties.getValueAt(5, 1);
							try{
								String r = color.substring(0, color.indexOf(',') -1);// -1 para quitar un espacio al final
								int red = new Integer(r).intValue();
								color = color.substring(color.indexOf(',') + 2);
								String g = color.substring(0, color.indexOf(',') -1);
								int green = new Integer(g).intValue();
								color = color.substring(color.indexOf(',') + 2);
								int blue = new Integer(color).intValue();
								Color colorNew = new Color(red,green,blue); 
								//asignar
								diagramAct.getSelectedStereotype().setBackground(colorNew);
								getProcessGraphDocking().getTabbedPaneWorkArea().repaint();
							}catch(Exception exception){
								String backGround = diagramAct.getSelectedStereotype().getBackground().getRed() +" , " + diagramAct.getSelectedStereotype().getBackground().getGreen() + " , " +diagramAct.getSelectedStereotype().getBackground().getBlue();
								tableProperties.setValueAt(backGround, 5, 1);
							}
						}else{
							String backGround = diagramAct.getSelectedStereotype().getBackground().getRed() +" , " + diagramAct.getSelectedStereotype().getBackground().getGreen() + " , " +diagramAct.getSelectedStereotype().getBackground().getBlue();
							tableProperties.setValueAt(backGround, 5, 1); 
							getProcessGraphDocking().getButtonChosserBackGround().setVisible(false);
						}
						if(tableProperties.getSelectedRow() == 6){//color de linea
							getProcessGraphDocking().getButtonChooserLine().setSize(30, tableProperties.getRowHeight(6));
							int widthOnScreen = tableProperties.getWidth() - getProcessGraphDocking().getButtonChooserLine().getWidth();
							int heightOnScreen = tableProperties.getRowHeight() * 6;
							getProcessGraphDocking().getButtonChooserLine().setLocation(widthOnScreen, heightOnScreen);
							getProcessGraphDocking().getButtonChooserLine().setVisible(true);
							String color = (String)tableProperties.getValueAt(6, 1);
							try{
								String r = color.substring(0, color.indexOf(',') -1);// -1 para quitar un espacio al final
								int red = new Integer(r).intValue();
								color = color.substring(color.indexOf(',') + 2);
								String g = color.substring(0, color.indexOf(',') -1);
								int green = new Integer(g).intValue();
								color = color.substring(color.indexOf(',') + 2);
								int blue = new Integer(color).intValue();
								Color colorNew = new Color(red,green,blue); 
								//asignar
								diagramAct.getSelectedStereotype().setLineColor(colorNew);
								getProcessGraphDocking().getTabbedPaneWorkArea().repaint();
							}catch(Exception exception){
								String line = diagramAct.getSelectedStereotype().getLineColor().getRed() +" , " + diagramAct.getSelectedStereotype().getLineColor().getGreen() + " , " +diagramAct.getSelectedStereotype().getLineColor().getBlue();
								tableProperties.setValueAt(line, 6, 1);
							}
						}else{
							String line = diagramAct.getSelectedStereotype().getLineColor().getRed() +" , " + diagramAct.getSelectedStereotype().getLineColor().getGreen() + " , " +diagramAct.getSelectedStereotype().getLineColor().getBlue();
							tableProperties.setValueAt(line, 6, 1); 
							getProcessGraphDocking().getButtonChooserLine().setVisible(false);
						}
						if(tableProperties.getSelectedRow() == 9){//visible
							Rectangle cell = tableProperties.getCellRect(9, 1, true);
							getProcessGraphDocking().getComboBoxVisible().setSize(cell.width,cell.height);
							getProcessGraphDocking().getComboBoxVisible().setLocation(cell.x, cell.y);
							if(diagramAct.getSelectedStereotype().isVisible()){
								getProcessGraphDocking().getComboBoxVisible().setSelectedItem(true);
							}else{
								getProcessGraphDocking().getComboBoxVisible().setSelectedItem(false);
							}
							getProcessGraphDocking().getComboBoxVisible().setVisible(true);
						}else{
							boolean visible = diagramAct.getSelectedStereotype().isVisible();
							tableProperties.setValueAt(visible, 9, 1); 
							getProcessGraphDocking().getComboBoxVisible().setVisible(false);
						}
					}
				}
			});
			tableProperties.addComponentListener(new java.awt.event.ComponentAdapter() {
				public void componentResized(java.awt.event.ComponentEvent e) {
					getProcessGraphDocking().getButtonChosserBackGround().setVisible(false);
					getProcessGraphDocking().getButtonChooserLine().setVisible(false);
					getProcessGraphDocking().getComboBoxSelected().setVisible(false);
					getProcessGraphDocking().getComboBoxVisible().setVisible(false);
					getProcessGraphDocking().getScrollPaneDescription().setVisible(false);
				}
			});
			tableProperties.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					Diagram diagramAct = getProcessGraphDocking().getPaintManager().getActiveDiagram();
					if(e.getKeyCode() == 10){//enter
						if(tableProperties.getRowCount() == 7){//es un diagrama
							if(tableProperties.getSelectedRow() == 4 && tableProperties.getSelectedColumn() == 1){
								String color = (String)tableProperties.getValueAt(4, 1);
								try{
									String r = color.substring(0, color.indexOf(',') -1);// -1 para quitar un espacio al final
									int red = new Integer(r).intValue();
									color = color.substring(color.indexOf(',') + 2);
									String g = color.substring(0, color.indexOf(',') -1);
									int green = new Integer(g).intValue();
									color = color.substring(color.indexOf(',') + 2);
									int blue = new Integer(color).intValue();
									Color colorNew = new Color(red,green,blue); 
									//asignar
									diagramAct.setBackground(colorNew);
									getProcessGraphDocking().getTabbedPaneWorkArea().setColorToPanelWorkArea();
									getProcessGraphDocking().getTabbedPaneWorkArea().repaint();
								}catch(Exception exception){
									String backGround = diagramAct.getBackground().getRed() +" , " + diagramAct.getBackground().getGreen() + " , " +diagramAct.getBackground().getBlue();
									tableProperties.setValueAt(backGround, 4, 1);
								}
								getProcessGraphDocking().getButtonChosserBackGround().setVisible(false);
							}
						}
						if(tableProperties.getRowCount() == 8){//es una nota
							if(tableProperties.getSelectedRow() == 3 && tableProperties.getSelectedColumn() == 1){//color de fondo
								String color = (String)tableProperties.getValueAt(3, 1);
								try{
									String r = color.substring(0, color.indexOf(',') -1);// -1 para quitar un espacio al final
									int red = new Integer(r).intValue();
									color = color.substring(color.indexOf(',') + 2);
									String g = color.substring(0, color.indexOf(',') -1);
									int green = new Integer(g).intValue();
									color = color.substring(color.indexOf(',') + 2);
									int blue = new Integer(color).intValue();
									Color colorNew = new Color(red,green,blue); 
									//asignar
									diagramAct.getSelectedStereotype().setBackground(colorNew);
									getProcessGraphDocking().getTabbedPaneWorkArea().repaint();
								}catch(Exception exception){
									String backGround = diagramAct.getSelectedStereotype().getBackground().getRed() +" , " + diagramAct.getSelectedStereotype().getBackground().getGreen() + " , " +diagramAct.getSelectedStereotype().getBackground().getBlue();
									tableProperties.setValueAt(backGround, 3, 1);
								}
								getProcessGraphDocking().getButtonChosserBackGround().setVisible(false);
							}
							if(tableProperties.getSelectedRow() == 4 && tableProperties.getSelectedColumn() == 1){//color de linea
								String color = (String)tableProperties.getValueAt(4, 1);
								try{
									String r = color.substring(0, color.indexOf(',') -1);// -1 para quitar un espacio al final
									int red = new Integer(r).intValue();
									color = color.substring(color.indexOf(',') + 2);
									String g = color.substring(0, color.indexOf(',') -1);
									int green = new Integer(g).intValue();
									color = color.substring(color.indexOf(',') + 2);
									int blue = new Integer(color).intValue();
									Color colorNew = new Color(red,green,blue); 
									//asignar
									diagramAct.getSelectedStereotype().setLineColor(colorNew);
									getProcessGraphDocking().getTabbedPaneWorkArea().repaint();
								}catch(Exception exception){
									String line = diagramAct.getSelectedStereotype().getLineColor().getRed() +" , " + diagramAct.getSelectedStereotype().getLineColor().getGreen() + " , " +diagramAct.getSelectedStereotype().getLineColor().getBlue();
									tableProperties.setValueAt(line, 4, 1);
								}
								getProcessGraphDocking().getButtonChooserLine().setVisible(false);
							}
						}
						if(tableProperties.getRowCount() == 11){//es un estereotipo
							if(tableProperties.getSelectedRow() == 5 && tableProperties.getSelectedColumn() == 1){//color de fondo
								String color = (String)tableProperties.getValueAt(5, 1);
								try{
									String r = color.substring(0, color.indexOf(',') -1);// -1 para quitar un espacio al final
									int red = new Integer(r).intValue();
									color = color.substring(color.indexOf(',') + 2);
									String g = color.substring(0, color.indexOf(',') -1);
									int green = new Integer(g).intValue();
									color = color.substring(color.indexOf(',') + 2);
									int blue = new Integer(color).intValue();
									Color colorNew = new Color(red,green,blue); 
									//asignar
									diagramAct.getSelectedStereotype().setBackground(colorNew);
									getProcessGraphDocking().getTabbedPaneWorkArea().repaint();
								}catch(Exception exception){
									String backGround = diagramAct.getSelectedStereotype().getBackground().getRed() +" , " + diagramAct.getSelectedStereotype().getBackground().getGreen() + " , " +diagramAct.getSelectedStereotype().getBackground().getBlue();
									tableProperties.setValueAt(backGround, 5, 1);
								}
								getProcessGraphDocking().getButtonChosserBackGround().setVisible(false);
							}
							if(tableProperties.getSelectedRow() == 6 && tableProperties.getSelectedColumn() == 1){//color de linea
								String color = (String)tableProperties.getValueAt(6, 1);
								try{
									String r = color.substring(0, color.indexOf(',') -1);// -1 para quitar un espacio al final
									int red = new Integer(r).intValue();
									color = color.substring(color.indexOf(',') + 2);
									String g = color.substring(0, color.indexOf(',') -1);
									int green = new Integer(g).intValue();
									color = color.substring(color.indexOf(',') + 2);
									int blue = new Integer(color).intValue();
									Color colorNew = new Color(red,green,blue); 
									//asignar
									diagramAct.getSelectedStereotype().setLineColor(colorNew);
									getProcessGraphDocking().getTabbedPaneWorkArea().repaint();
								}catch(Exception exception){
									String line = diagramAct.getSelectedStereotype().getLineColor().getRed() +" , " + diagramAct.getSelectedStereotype().getLineColor().getGreen() + " , " +diagramAct.getSelectedStereotype().getLineColor().getBlue();
									tableProperties.setValueAt(line, 6, 1);
								}
								getProcessGraphDocking().getButtonChooserLine().setVisible(false);
							}
						}
					}
				}
			});

		}
		return tableProperties;
	}
	/**
	 * Making a Component a Drop Target
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
				Diagram diagram = getProcessGraphDocking().getPaintManager().getActiveDiagram();
				Stereotype stereotype = diagram.findById(id.intValue());
				tableProperties.setModel(FactoryModel.factory.getModelForTablePropertiesToStereotype(stereotype));
				setDefaultComboBoxModelProcess(FactoryModel.factory.getModelForComboboxProcess(diagram));
				comboBoxProcess.setSelectedIndex(diagram.findPosById(id.intValue()));

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
	 * Making a Component a Drop Target
	 */

	public ProcessGraphDocking getProcessGraphDocking() {
		return processGraphDocking;
	}

	public String getDescriptionTxt() {
		return descriptionTxt;
	}

	public void setDescriptionTxt(String descriptionTxt) {
		this.descriptionTxt = descriptionTxt;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
