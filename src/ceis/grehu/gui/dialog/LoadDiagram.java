package ceis.grehu.gui.dialog;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import ceis.grehu.gui.ProcessGraphDocking;
import ceis.grehu.gui.paint.Diagram;
import ceis.grehu.gui.paint.FactoryModel;
import ceis.grehu.gui.paint.PaintManager;

public class LoadDiagram extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JScrollPane jScrollPane = null;

	private JLabel jLabel = null;

	private ListModel listModel = null;

	private JButton buttonAceptar = null;

	private JButton buttonCancelar = null;

	private ProcessGraphDocking processGraphDocking = null;

	private JTree treeOpenDiagrams = null;

	private DefaultTreeModel defaultTreeModelOpenDiagrams = null;  //  @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public LoadDiagram(ProcessGraphDocking owner) {
		super();
		processGraphDocking = owner;
		treeOpenDiagrams = new JTree(getDefaultTreeModelOpenDiagrams());
		treeOpenDiagrams.setRootVisible(false);
		treeOpenDiagrams.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		treeOpenDiagrams.setEditable(false);
		treeOpenDiagrams.setDragEnabled(false);
		treeOpenDiagrams.setShowsRootHandles(true);
		initialize();
	}

	public DefaultTreeModel getDefaultTreeModelOpenDiagrams() {//default load
		if (defaultTreeModelOpenDiagrams == null) {
			DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Vacio");
			defaultTreeModelOpenDiagrams = new DefaultTreeModel(rootNode);
		}
		return defaultTreeModelOpenDiagrams;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(309, 345);
		this.setResizable(false);
		setModal(true);
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		this.setContentPane(getJContentPane());
		this.setTitle("Abrir diagrama");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(16, 12, 214, 16));
			jLabel.setText("Diagramas almacenados");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getButtonAceptar(), null);
			jContentPane.add(getButtonCancelar(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(15, 31, 273, 238));
			jScrollPane.setViewportView(getTreeOpenDiagrams());
		}
		return jScrollPane;
	}

	public ListModel getListModel() {
		return listModel;
	}

	public ArrayList<Diagram> loadAllParents(Long idDiagram, ArrayList<Diagram> arrayReturn){
		Diagram diagram = getProcessGraphDocking().getPaintManager().loadDiagrama(new Long(idDiagram));
		arrayReturn.add(diagram);
		if(diagram.getObjectParent() != diagram.getId()){
			Long idParent = PaintManager.stereotypeService.findStereotype(new Long(diagram.getObjectParent()));
			Long idDiagramParent = PaintManager.stereotypeService.getOwner(idParent);
			loadAllParents(idDiagramParent, arrayReturn);
		}
		return arrayReturn;
	}

	/**
	 * This method initializes buttonAceptar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButtonAceptar() {
		if (buttonAceptar == null) {
			buttonAceptar = new JButton();
			buttonAceptar.setBounds(new Rectangle(87, 281, 97, 26));
			buttonAceptar.setText("Aceptar");
			buttonAceptar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(getTreeOpenDiagrams().getSelectionRows() != null){
						int[] idSelection = getTreeOpenDiagrams().getSelectionRows();
						int idSeleccionFinal = getProcessGraphDocking().getPaintManager().getListPararell().get(idSelection[0]);
						Diagram diagram = getProcessGraphDocking().getPaintManager().loadDiagrama(new Long(idSeleccionFinal));
						boolean existTab = false;
						for (int i = 0; i < getProcessGraphDocking().getTabbedPaneWorkArea().getCantidadTab(); i++) {
							if(getProcessGraphDocking().getTabbedPaneWorkArea().getTituloAt(i).equals(diagram.getName())){
								existTab = true;
								break;
							}
						}
						if(!existTab){
							if(diagram.getObjectParent() != diagram.getId()){
								ArrayList<Diagram> arrayAllDiagrams = new ArrayList<Diagram>();
								Long idParent = PaintManager.stereotypeService.findStereotype(new Long(diagram.getObjectParent()));
								Long idDiagramaParent = PaintManager.stereotypeService.getOwner(idParent);
								loadAllParents(idDiagramaParent, arrayAllDiagrams);
								for (Diagram parentDiagrams : arrayAllDiagrams) {
									if(getProcessGraphDocking().getPaintManager().findById(parentDiagrams.getId()) == null){
										parentDiagrams.setRectVisible(new Rectangle(500, 500));
										getProcessGraphDocking().getPaintManager().addDiagram(parentDiagrams);
									}
									/*if (getProcessGraphDocking().getTabbedPaneWorkArea().getCantidadTab() > 1) {
										JPanel panel = new JPanel();
										getProcessGraphDocking().getTabbedPaneWorkArea().getTabbedPane().insertTab(getProcessGraphDocking().getTabbedPaneWorkArea().getTituloAt(getProcessGraphDocking().getTabbedPaneWorkArea().getCantidadTab() - 1),null,panel,null,getProcessGraphDocking().getTabbedPaneWorkArea().getCantidadTab() - 1);// probar
										getProcessGraphDocking().getTabbedPaneWorkArea().getPanelWorkArea().setPreferredSize(new Dimension(2400,1400));
										getProcessGraphDocking().getTabbedPaneWorkArea().AgregarTab(parentDiagrams.getName());
										getProcessGraphDocking().getTabbedPaneWorkArea().setSelectedPosicion(getProcessGraphDocking().getTabbedPaneWorkArea().getCantidadTab() - 1);// -1
										getProcessGraphDocking().setTabSelect(getProcessGraphDocking().getTabbedPaneWorkArea().getCantidadTab() - 1);
										getProcessGraphDocking().addTabTitle(parentDiagrams.getName());
									} else {
										getProcessGraphDocking().getTabbedPaneWorkArea().getPanelWorkArea().setPreferredSize(new Dimension(2400,1400));
										getProcessGraphDocking().getTabbedPaneWorkArea().AgregarTab(parentDiagrams.getName());
										getProcessGraphDocking().getTabbedPaneWorkArea().setSelectedPosicion(getProcessGraphDocking().getTabbedPaneWorkArea().getCantidadTab() - 1);
										getProcessGraphDocking().setTabSelect(getProcessGraphDocking().getTabbedPaneWorkArea().getCantidadTab() - 1);
										getProcessGraphDocking().addTabTitle(parentDiagrams.getName());
									}
									for (int i = 1; i < getProcessGraphDocking().getTabbedPaneWorkArea().getCantidadTab(); i++) {
										getProcessGraphDocking().getTabbedPaneWorkArea().getTabbedPane().setTitleAt(i, getProcessGraphDocking().getTabTitle(i));
										getProcessGraphDocking().getTabbedPaneWorkArea().getTabbedPane().setToolTipTextAt(i, getProcessGraphDocking().getTabTitle(i));// tip
									}*/
								}
							}
							if(getProcessGraphDocking().getPaintManager().findById(diagram.getId()) == null){
								getProcessGraphDocking().getPaintManager().unActiveAllDiagrams();
								diagram.setSelected(true);
								diagram.setRectVisible(new Rectangle(500, 500));
								getProcessGraphDocking().getPaintManager().addDiagram(diagram);
							}
							else{
								getProcessGraphDocking().getPaintManager().findById(diagram.getId()).setSelected(true);
							}
							
							Diagram activeDiagram = getProcessGraphDocking().getPaintManager().getActiveDiagram();
							Double zoomFactor = activeDiagram.getZoomFactor();
							getProcessGraphDocking().getTabbedPaneWorkArea().getPanelWorkArea().setPreferredSize(new Dimension(new Double(activeDiagram.getDiagramSize().getWidth()*zoomFactor).intValue(),new Double(activeDiagram.getDiagramSize().getHeight()*zoomFactor).intValue()));
							getProcessGraphDocking().getTabbedPaneWorkArea().getPanelWorkArea().setBounds(new Rectangle(new Double(activeDiagram.getDiagramSize().getWidth()*zoomFactor).intValue(),new Double(activeDiagram.getDiagramSize().getHeight()*zoomFactor).intValue()));

							if (getProcessGraphDocking().getTabbedPaneWorkArea().getCantidadTab() > 1) {
								JPanel panel = new JPanel();
								getProcessGraphDocking().getTabbedPaneWorkArea().getTabbedPane().insertTab(getProcessGraphDocking().getTabbedPaneWorkArea().getTituloAt(getProcessGraphDocking().getTabbedPaneWorkArea().getCantidadTab() - 1),null,panel,null,getProcessGraphDocking().getTabbedPaneWorkArea().getCantidadTab() - 1);// probar
								getProcessGraphDocking().getTabbedPaneWorkArea().getPanelWorkArea().setPreferredSize(new Dimension(diagram.getDiagramSize().width,diagram.getDiagramSize().height));
								getProcessGraphDocking().getTabbedPaneWorkArea().AgregarTab(diagram.getName());
								getProcessGraphDocking().getTabbedPaneWorkArea().setSelectedPosicion(getProcessGraphDocking().getTabbedPaneWorkArea().getCantidadTab() - 1);// -1
								getProcessGraphDocking().setTabSelect(getProcessGraphDocking().getTabbedPaneWorkArea().getCantidadTab() - 1);
								getProcessGraphDocking().addTabTitle(diagram.getName());
							} else {
								getProcessGraphDocking().getTabbedPaneWorkArea().getPanelWorkArea().setPreferredSize(new Dimension(diagram.getDiagramSize().width,diagram.getDiagramSize().height));
								getProcessGraphDocking().getTabbedPaneWorkArea().AgregarTab(diagram.getName());
								getProcessGraphDocking().getTabbedPaneWorkArea().setSelectedPosicion(getProcessGraphDocking().getTabbedPaneWorkArea().getCantidadTab() - 1);
								getProcessGraphDocking().setTabSelect(getProcessGraphDocking().getTabbedPaneWorkArea().getCantidadTab() - 1);
								getProcessGraphDocking().addTabTitle(diagram.getName());
							}
							for (int i = 1; i < getProcessGraphDocking().getTabbedPaneWorkArea().getCantidadTab(); i++) {
								getProcessGraphDocking().getTabbedPaneWorkArea().getTabbedPane().setTitleAt(i, getProcessGraphDocking().getTabTitle(i));
								getProcessGraphDocking().getTabbedPaneWorkArea().getTabbedPane().setToolTipTextAt(i, getProcessGraphDocking().getTabTitle(i));// tip
							}
							getProcessGraphDocking().getTabbedPaneWorkArea().addButtomsToDiagram(getProcessGraphDocking().getPaintManager().getActiveDiagram().getType());    				

							//tree diagrams
							getProcessGraphDocking().getTreeDiagrams().setRootVisible();
							getProcessGraphDocking().getTreeDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeDiagrams(getProcessGraphDocking().getPaintManager().getDiagramList(), getProcessGraphDocking()));
							//tree active diagrams
							getProcessGraphDocking().getTreeActiveDiagrams().setRootVisible();
							getProcessGraphDocking().getTreeActiveDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeActiveDiagrams(getProcessGraphDocking().getPaintManager().getActiveDiagram()));
							//combobox
							getProcessGraphDocking().getTableProperties().setDefaultComboBoxModelProcess(FactoryModel.factory.getModelForComboboxProcess(getProcessGraphDocking().getPaintManager().getActiveDiagram()));

							getProcessGraphDocking().getPaintManager().setCreatePanel(true);//esto es para que el scroll valla para la parte de arriba
							//actualizando arboles
							getProcessGraphDocking().getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToDiagram(getProcessGraphDocking().getPaintManager().getActiveDiagram()));
							getProcessGraphDocking().getTabbedPaneWorkArea().setColorToPanelWorkArea();
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

							getProcessGraphDocking().getTabbedPaneWorkArea().getPanelWorkArea().repaint();
							getProcessGraphDocking().getNavigator().repaint();
							dispose();
						}
						else{
							JOptionPane.showMessageDialog(returnThis(), "Este diagrama ya está cargado", "Información", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			});
		}
		return buttonAceptar;
	}

	private LoadDiagram returnThis(){
		return this;
	}

	/**
	 * This method initializes buttonCancelar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButtonCancelar() {
		if (buttonCancelar == null) {
			buttonCancelar = new JButton();
			buttonCancelar.setText("Cancelar");
			buttonCancelar.setSize(new Dimension(97, 26));
			buttonCancelar.setLocation(new Point(190, 282));
			buttonCancelar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return buttonCancelar;
	}

	public ProcessGraphDocking getProcessGraphDocking() {
		return processGraphDocking;
	}

	/**
	 * This method initializes treeOpenDiagrams	
	 * 	
	 * @return javax.swing.JTree	
	 */
	public JTree getTreeOpenDiagrams() {
		if (treeOpenDiagrams == null) {
			treeOpenDiagrams = new JTree();

		}
		return treeOpenDiagrams;
	}

	public void setDefaultTreeModelOpenDiagrams(DefaultTreeModel defaultTreeModelOpenDiagrams) {
		treeOpenDiagrams.setModel(defaultTreeModelOpenDiagrams);
	}

}  //  @jve:decl-index=0:visual-constraint="46,14"
