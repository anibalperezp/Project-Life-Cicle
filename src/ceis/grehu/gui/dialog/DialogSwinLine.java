package ceis.grehu.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import ceis.grehu.gui.ProcessGraphDocking;
import ceis.grehu.gui.paint.Diagram;
import ceis.grehu.gui.paint.FactoryModel;
import ceis.grehu.gui.paint.ShowToolTipText;
import ceis.grehu.gui.paint.SwinLine;

public class DialogSwinLine extends JDialog {

    private static final long serialVersionUID = 1L;

    private JPanel jContentPane = null;

    private JPanel panelBack = null;

    private JLabel labelName = null;

    private JTextField textFieldName = null;

    private JScrollPane scrollPaneLine = null;

    private JTable tableLine = null;

    private JPanel panelButtons = null;

    private JButton buttonAcept = null;

    private JButton buttonCancel = null;

    private JButton buttonEdit = null;

    private JButton buttonAdd = null;

    private JButton buttonDelete = null;
    
    private DefaultTableModel defaultTableModelLine = null;

    private JLabel labelLine = null;

    private JPanel panelTableLine = null;

    private JPanel panelButtomEdit = null;

    private JPanel panelCenter = null;
    
    private ProcessGraphDocking processGraphDocking = null;
    
    /**
     * @param owner
     */
    public DialogSwinLine(ProcessGraphDocking owner) {
    	super(owner);
    	processGraphDocking = owner;
    	initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
	this.setSize(501, 322);
	this.setResizable(true);
	this.setTitle("Resumen gráfico");
	this.setModal(true);
	this.setContentPane(getJContentPane());
	this.addComponentListener(new java.awt.event.ComponentAdapter() {
	    public void componentResized(java.awt.event.ComponentEvent e) {
		if(getHeight() < 322)
		    setSize(new Dimension(getWidth(),322));
		if(getWidth() < 501)
		    setSize(new Dimension(501, getHeight()));
	    }
	});
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
	if (jContentPane == null) {
	    jContentPane = new JPanel();
	    jContentPane.setLayout(new BorderLayout());
	    jContentPane.add(getPanelButtons(), BorderLayout.SOUTH);
	    jContentPane.add(getPanelBack(), BorderLayout.NORTH);
	    jContentPane.add(getPanelCenter(), BorderLayout.CENTER);
	}
	return jContentPane;
    }

    /**
     * This method initializes panelBack	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getPanelBack() {
        if (panelBack == null) {
    	GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
    	gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
    	gridBagConstraints3.gridx = 1;
    	gridBagConstraints3.gridy = 0;
    	gridBagConstraints3.ipadx = 313;
    	gridBagConstraints3.ipady = -3;
    	gridBagConstraints3.weightx = 1.0;
    	gridBagConstraints3.anchor = GridBagConstraints.WEST;
    	gridBagConstraints3.insets = new Insets(13, 7, 8, 17);
    	GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
    	gridBagConstraints2.insets = new Insets(13, 9, 8, 6);
    	gridBagConstraints2.gridy = 0;
    	gridBagConstraints2.ipady = 1;
    	gridBagConstraints2.gridx = 0;
    	labelLine = new JLabel();
    	labelLine.setText("Agregar Calle");
    	labelName = new JLabel();
    	labelName.setText("Nombre :");
    	panelBack = new JPanel();
    	panelBack.setLayout(new GridBagLayout());
    	panelBack.setBorder(null);
    	panelBack.setPreferredSize(new Dimension(1, 30));
    	panelBack.add(labelName, gridBagConstraints2);
    	panelBack.add(getTextFieldName(), gridBagConstraints3);
        }
        return panelBack;
    }

    /**
     * This method initializes textFieldName	
     * 	
     * @return javax.swing.JTextField	
     */
    public JTextField getTextFieldName() {
        if (textFieldName == null) {
    	textFieldName = new JTextField();
        }
        return textFieldName;
    }

    /**
     * This method initializes scrollPaneLine	
     * 	
     * @return javax.swing.JScrollPane	
     */
    private JScrollPane getScrollPaneLine() {
        if (scrollPaneLine == null) {
    	scrollPaneLine = new JScrollPane();
    	scrollPaneLine.setViewportView(getTableLine());
        }
        return scrollPaneLine;
    }

    /**
     * This method initializes tableLine	
     * 	
     * @return javax.swing.JTable	
     */
    private JTable getTableLine() {
    	if (tableLine == null) {
    		tableLine = new JTable();
    		tableLine.setModel(getDefaultTableModelLine());

    	}
    	return tableLine;
    }

    public DefaultTableModel getDefaultTableModelLine() {//vacio
	if (defaultTableModelLine == null) {
	    defaultTableModelLine = new DefaultTableModel();
	    defaultTableModelLine.setRowCount(0);
	    defaultTableModelLine.addColumn(ShowToolTipText.getFont("Nombre"));
	    defaultTableModelLine.addColumn(ShowToolTipText.getFont("Ancho"));
	}
	return defaultTableModelLine;
    }
    
    /**
     * This method initializes panelButtons	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getPanelButtons() {
        if (panelButtons == null) {
    	FlowLayout flowLayout = new FlowLayout();
    	flowLayout.setAlignment(java.awt.FlowLayout.RIGHT);
    	panelButtons = new JPanel();
    	panelButtons.setLayout(flowLayout);
    	panelButtons.setPreferredSize(new Dimension(1, 35));
    	panelButtons.add(getButtonAcept(), null);
    	panelButtons.add(getButtonCancel(), null);
        }
        return panelButtons;
    }

    /**
     * This method initializes buttonAcept	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getButtonAcept() {
        if (buttonAcept == null) {
    	buttonAcept = new JButton();
    	buttonAcept.setText("Aceptar");
    	buttonAcept.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(java.awt.event.ActionEvent e) {

    			ProcessGraphDocking owner = getProcessGraphDocking();
    			String name = getTextFieldName().getText();

    			Diagram newDiagram = new Diagram("RGS", owner.getTabbedPaneWorkArea().getStereotypeSelect().getId(),name);
    			newDiagram.setSelected(true);
    			//lo de las carreteras
    			boolean flag = true;
    			if(getDefaultTableModelLine().getRowCount() > 0){
    				for (int i = 0; i < getDefaultTableModelLine().getRowCount(); i++) {
    					for (int j = 0; j < getDefaultTableModelLine().getColumnCount(); j++) {
    						if(getDefaultTableModelLine().getValueAt(i, j).equals("")){
    							JOptionPane.showMessageDialog(returnThis(), "No se puede dejar información en blanco", "Error", JOptionPane.ERROR_MESSAGE);
    							flag = false;
    							break;
    						}
    					}
    				}
    			}

    			//newDiagram
    			if(flag){
    				SwinLine swinLine = new SwinLine();
    				for (int i = 0; i < getDefaultTableModelLine().getRowCount(); i++) {
    					for (int j = 0; j < getDefaultTableModelLine().getColumnCount(); j++) {
    						if(j == 0)
    							swinLine.getArrayNameSwingLine().add(getDefaultTableModelLine().getValueAt(i, j).toString());
    						else if(j == 1){
    							String strWidth = (getDefaultTableModelLine().getValueAt(i, j)).toString();
    							int width = Integer.parseInt(strWidth);
    							swinLine.getArrayWidths().add(width);
    						}
    					}
    				}
    				newDiagram.setSwinLine(swinLine);
    				newDiagram.setSaveStatus(true);
    				getProcessGraphDocking().getPaintManager().unActiveAllDiagrams();
    				getProcessGraphDocking().getPaintManager().addDiagram(newDiagram);
    				owner.getTabbedPaneWorkArea().getStereotypeSelect().getDiagramIds().add(newDiagram.getId());

    				if (owner.getTabbedPaneWorkArea().getCantidadTab() > 1) {
    					JPanel panel = new JPanel();
    					owner.getTabbedPaneWorkArea().getTabbedPane().insertTab(owner.getTabbedPaneWorkArea().getTituloAt(owner.getTabbedPaneWorkArea().getCantidadTab() - 1),null,panel,null,owner.getTabbedPaneWorkArea().getCantidadTab() - 1);// probar// tip
    					owner.getTabbedPaneWorkArea().getPanelWorkArea().setPreferredSize(new Dimension(2400,1400));
    					owner.getTabbedPaneWorkArea().AgregarTab(name);
    					owner.getTabbedPaneWorkArea().setSelectedPosicion(owner.getTabbedPaneWorkArea().getCantidadTab() - 1);// -1
    					getProcessGraphDocking().setTabSelect(owner.getTabbedPaneWorkArea().getCantidadTab() - 1);
    					getProcessGraphDocking().addTabTitle(name);
    				} else {
    					owner.getTabbedPaneWorkArea().getPanelWorkArea().setPreferredSize(new Dimension(2400,1400));
    					owner.getTabbedPaneWorkArea().AgregarTab(name);

    					owner.getTabbedPaneWorkArea().setSelectedPosicion(owner.getTabbedPaneWorkArea().getCantidadTab() - 1);
    					getProcessGraphDocking().setTabSelect(owner.getTabbedPaneWorkArea().getCantidadTab() - 1);
    					getProcessGraphDocking().addTabTitle(name);
    				}
    				//actualizando tabs
    				for (int i = 0; i < owner.getTabbedPaneWorkArea().getCantidadTab(); i++) {
    					owner.getTabbedPaneWorkArea().getTabbedPane().setTitleAt(i, getProcessGraphDocking().getTabTitle(i));
    					owner.getTabbedPaneWorkArea().getTabbedPane().setToolTipTextAt(i, getProcessGraphDocking().getTabTitle(i));// tip
    				}
    				//agregando botones
    				/*owner.getTabbedPaneWorkArea().removeAllButtomsFromToolsBars();
					owner.getTabbedPaneWorkArea().addButtomToToolBar(owner.getButtonPaintActServ(),"Actividades");
					owner.getTabbedPaneWorkArea().addButtomToToolBar(owner.getButtonPaintActManual(),"Actividades");
					owner.getTabbedPaneWorkArea().addButtomToToolBar(owner.getButtonPaintDB(),"Misc");
					owner.getTabbedPaneWorkArea().addButtomToToolBar(owner.getButtonPaintDesicion(),"Misc");
					owner.getTabbedPaneWorkArea().addButtomToToolBar(owner.getButtonPaintNote(),"Misc");
					owner.getTabbedPaneWorkArea().addButtomToToolBar(owner.getButtonPaintTransition(),"Enlaces");
					owner.getTabbedPaneWorkArea().addButtomToToolBar(owner.getButtonPaintAnclarNote(),"Enlaces");
					owner.getTabbedPaneWorkArea().addButtomToToolBar(owner.getButtonPaintInitialState(),"Estados");
					owner.getTabbedPaneWorkArea().addButtomToToolBar(owner.getButtonPaintInitialStateMess(),"Estados");
					owner.getTabbedPaneWorkArea().addButtomToToolBar(owner.getButtonPanitInitialStateTime(),"Estados");
					owner.getTabbedPaneWorkArea().addButtomToToolBar(owner.getButtonPaintFinalState(),"Estados");*/
    				owner.getTabbedPaneWorkArea().addButtomsToDiagram(owner.getPaintManager().getActiveDiagram().getType());    				
    				
    				//tree diagrams
    				getProcessGraphDocking().getTreeDiagrams().setRootVisible();
    				getProcessGraphDocking().getTreeDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeDiagrams(getProcessGraphDocking().getPaintManager().getDiagramList(), getProcessGraphDocking()));
    				//tree active diagrams
    				getProcessGraphDocking().getTreeActiveDiagrams().setRootVisible();
    				getProcessGraphDocking().getTreeActiveDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeActiveDiagrams(getProcessGraphDocking().getPaintManager().getActiveDiagram()));
    				//combobox
    				getProcessGraphDocking().getTableProperties().setDefaultComboBoxModelProcess(FactoryModel.factory.getModelForComboboxProcess(getProcessGraphDocking().getPaintManager().getActiveDiagram()));

    				Rectangle rectVisible = new Rectangle(500,500);
    				getProcessGraphDocking().getPaintManager().getActiveDiagram().setRectVisible(rectVisible);
    				Rectangle rectangle = new Rectangle(2400,1400);
    				getProcessGraphDocking().getPaintManager().getActiveDiagram().setDiagramSize(rectangle);
    				getProcessGraphDocking().getPaintManager().setCreatePanel(true);//esto es para que el scroll valla para la parte de arriba
    				//actualizando arboles
    				getProcessGraphDocking().getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToDiagram(getProcessGraphDocking().getPaintManager().getActiveDiagram()));
    				owner.getTabbedPaneWorkArea().setColorToPanelWorkArea();
    				repaint();
    				getProcessGraphDocking().getJTextAreaNote().setVisible(false);
    				getProcessGraphDocking().getJTextFieldName().setVisible(false);
    				getProcessGraphDocking().getPaintManager().setSizeDiagramOverview(new Rectangle(getProcessGraphDocking().getNavigator().getSize()),getProcessGraphDocking().getNavigator().getPanelOverView());

    				getProcessGraphDocking().getButtonChosserBackGround().setVisible(false);
    				getProcessGraphDocking().getButtonChooserLine().setVisible(false);
    				getProcessGraphDocking().getComboBoxSelected().setVisible(false);
    				getProcessGraphDocking().getComboBoxVisible().setVisible(false);
    				getProcessGraphDocking().getComboBoxType().setVisible(false);
    				getProcessGraphDocking().getScrollPaneDescription().setVisible(false);
    				getProcessGraphDocking().getJTextAreaNote().setVisible(false);
    				getProcessGraphDocking().getNavigator().repaint();
    				getProcessGraphDocking().getMenuItemUndo().setEnabled(false);
    				getProcessGraphDocking().getMenuItemRedo().setEnabled(false);
    				getProcessGraphDocking().getButtonUndo().setEnabled(false);
    				getProcessGraphDocking().getButtonRedo().setEnabled(false);
    				dispose();
    			}
    		}
    	});
        }
        return buttonAcept;
    }

    /**
     * This method initializes buttonCancel	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getButtonCancel() {
        if (buttonCancel == null) {
    	buttonCancel = new JButton();
    	buttonCancel.setText("Cancelar");
    	buttonCancel.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(java.awt.event.ActionEvent e) {
    			dispose();
    		}
    	});
        }
        return buttonCancel;
    }

    /**
     * This method initializes buttonEdit	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getButtonEdit() {
        if (buttonEdit == null) {
    	buttonEdit = new JButton();
    	buttonEdit.setText("Editar");
    	buttonEdit.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(java.awt.event.ActionEvent e) {
    			System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
    		}
    	});
        }
        return buttonEdit;
    }
    
    private DialogSwinLine returnThis(){
    	return this;
    }

    /**
     * This method initializes buttonAdd	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getButtonAdd() {
        if (buttonAdd == null) {
    	buttonAdd = new JButton();
    	buttonAdd.setText("Agregar");
    	buttonAdd.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(java.awt.event.ActionEvent e) {
    			//int rowCount = getDefaultTableModelLine().getRowCount();
    			//getDefaultTableModelLine().setRowCount(rowCount++);
    			boolean flag = true;
    			if(getDefaultTableModelLine().getRowCount() > 0){
    				
    				for (int i = 0; i < getDefaultTableModelLine().getRowCount(); i++) {
						for (int j = 0; j < getDefaultTableModelLine().getColumnCount(); j++) {
							if(getDefaultTableModelLine().getValueAt(i, j).equals("")){
								JOptionPane.showMessageDialog(returnThis(), "No se puede dejar información en blanco", "Error", JOptionPane.ERROR_MESSAGE);
								flag = false;
								break;
							}
						}
					}
    			}
    			if(flag){
    				ArrayList<Object> rowData = new ArrayList<Object>();
    				rowData.add("");
    				rowData.add(300);
    				getDefaultTableModelLine().addRow(rowData.toArray());
    			}
    		}
    	});
        }
        return buttonAdd;
    }

    /**
     * This method initializes buttonDelete	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getButtonDelete() {
        if (buttonDelete == null) {
    	buttonDelete = new JButton();
    	buttonDelete.setText("Eliminar");
    	buttonDelete.addActionListener(new java.awt.event.ActionListener() {   
    		public void actionPerformed(java.awt.event.ActionEvent e) {    
    			getDefaultTableModelLine().removeRow(getTableLine().getSelectedRow());
    		}
    	
    	});
        }
        return buttonDelete;
    }

    /**
     * This method initializes panelTableLine	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getPanelTableLine() {
        if (panelTableLine == null) {
    	GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
    	gridBagConstraints1.insets = new Insets(4, 6, 1, 259);
    	gridBagConstraints1.gridy = 0;
    	gridBagConstraints1.ipadx = 42;
    	gridBagConstraints1.ipady = -1;
    	gridBagConstraints1.anchor = GridBagConstraints.WEST;
    	gridBagConstraints1.gridx = 0;
    	GridBagConstraints gridBagConstraints = new GridBagConstraints();
    	gridBagConstraints.fill = GridBagConstraints.BOTH;
    	gridBagConstraints.gridx = 0;
    	gridBagConstraints.gridy = 1;
    	gridBagConstraints.ipadx = 369;
    	gridBagConstraints.ipady = 70;
    	gridBagConstraints.weightx = 1.0;
    	gridBagConstraints.weighty = 1.0;
    	gridBagConstraints.anchor = GridBagConstraints.WEST;
    	gridBagConstraints.insets = new Insets(2, 6, 3, 6);
    	panelTableLine = new JPanel();
    	panelTableLine.setLayout(new GridBagLayout());
    	panelTableLine.setPreferredSize(new Dimension(1, 1));
    	panelTableLine.add(getScrollPaneLine(), gridBagConstraints);
    	panelTableLine.add(labelLine, gridBagConstraints1);
        }
        return panelTableLine;
    }

    /**
     * This method initializes panelButtomEdit	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getPanelButtomEdit() {
        if (panelButtomEdit == null) {
    	FlowLayout flowLayout1 = new FlowLayout();
    	flowLayout1.setAlignment(java.awt.FlowLayout.RIGHT);
    	flowLayout1.setHgap(5);
    	flowLayout1.setVgap(3);
    	panelButtomEdit = new JPanel();
    	panelButtomEdit.setLayout(flowLayout1);
    	panelButtomEdit.add(getButtonDelete(), null);
    	panelButtomEdit.add(getButtonAdd(), null);
    	panelButtomEdit.add(getButtonEdit(), null);
        }
        return panelButtomEdit;
    }

    /**
     * This method initializes panelCenter	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getPanelCenter() {
        if (panelCenter == null) {
    	panelCenter = new JPanel();
    	panelCenter.setLayout(new BorderLayout());
    	panelCenter.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    	panelCenter.add(getPanelButtomEdit(), BorderLayout.SOUTH);
    	panelCenter.add(getPanelTableLine(), BorderLayout.CENTER);
        }
        return panelCenter;
    }

	public ProcessGraphDocking getProcessGraphDocking() {
		return processGraphDocking;
	}

	public void setProcessGraphDocking(ProcessGraphDocking processGraphDocking) {
		this.processGraphDocking = processGraphDocking;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
