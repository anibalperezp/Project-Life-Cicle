package ceis.grehu.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.border.EtchedBorder;

import ceis.grehu.gui.dialog.DialogSwinLine;
import ceis.grehu.gui.paint.Diagram;
import ceis.grehu.gui.paint.FactoryModel;
import ceis.grehu.gui.paint.Relation;
import ceis.grehu.gui.paint.Stereotype;
import ceis.grehu.gui.splash.StatusBar;
import ceis.grehu.units.undoManager.UndoAction;

import com.vlsolutions.swing.docking.DockKey;
import com.vlsolutions.swing.docking.Dockable;
import com.vlsolutions.swing.toolbars.ToolBarConstraints;
import com.vlsolutions.swing.toolbars.ToolBarContainer;
import com.vlsolutions.swing.toolbars.ToolBarPanel;
import com.vlsolutions.swing.toolbars.VLToolBar;


public class TabbedPaneWorkArea extends JPanel implements Dockable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5407242231046571825L;

	private JPanel panelWelcome = null;  

	private JLabel labelWelcome = null;

	private JButton buttonSeparador = null;

	private JButton buttonHome = null;

	private JButton buttonOpen = null;

	private JButton buttonNuevo = null;

	private JButton buttonSeparador1 = null;

	private JLabel labelNuevo = null;

	private JLabel labelOpen = null;

	private JTabbedPane tabbedPane = null;

	private DockKey key = new DockKey("Area de Trabajo");

	private JButton buttonHelp = null;

	private JLabel labelHelp = null;

	private JScrollPane scrollPaneWorkArea = null;

	private JPanel panelWorkAreaBack = null; 

	private PanelWorkArea panelWorkArea = null;

	public ProcessGraphDocking processGraphDocking = null;  //  @jve:decl-index=0:visual-constraint="474,10"

	private Point pointInicial = null;//new Point();  //  @jve:decl-index=0:

	private Point pointFinal = null;//new Point();  //  @jve:decl-index=0:

	private Point pointMoved = null;//new Point();  //  @jve:decl-index=0:

	private boolean leftClickPressed = false;

	private boolean rigthClickPressed = false;

	private boolean mouseDragged = false;

	private boolean stereotypeReadyToMove = false;

	private boolean someStereotypeReadyToMove = false;

	private boolean stereotypeReadyToWillResize = false;

	private int posOfRectToWillResize = -1;

	private boolean stereotypeReadyToResize = false;

	private Stereotype stereotypeSelect = null;

	//private Point pointStereotypeToScroll = null; 

	private Stereotype stereotypeLastSelected = null;  //  @jve:decl-index=0:

	private ArrayList<Stereotype> listSelection;  //  @jve:decl-index=0:

	private ArrayList<Point> arrayRelation = null;//aqui

	private int xDifference = 0;

	private int yDifference = 0; 

	private int xBigDifference = 0;//diferencia del rectangulo de mult seleccion

	private int yBigDifference = 0;

	private ArrayList<Point> listDiference;

	//private boolean doubleClickPressed;

	private JPanel panelStatusBar = null;

	private JLabel labelPosition = null;

	private JProgressBar progressBarWorking = null;

	/**
	 * VLToolbars
	 */
	private ToolBarContainer containerBar = ToolBarContainer.createDefaultContainer(false,
			true, false, false);

	private VLToolBar toolBarActivities = null;

	private VLToolBar toolBarJoins = null;

	private VLToolBar toolBarStates = null;

	private VLToolBar toolBarMisc = null;

	//private JTextField jTextFieldName = null;

	private int countClick = 0;

	private boolean clickValid = false;


	private boolean flag = false;

	private Stereotype flagStereotype = null;  //  @jve:decl-index=0:

	private boolean relationNote = false;

	private boolean transicion = false;

	private boolean drawline = false;

	private Stereotype stereotypeInicial = null;  //  @jve:decl-index=0:

	private Relation relationSelect = null;  //  @jve:decl-index=0:

	private boolean relationSelected = false;

	private boolean relationToWillResize = false;

	private boolean relationReadyToResize = false;
	private JPopupMenu popupMenuWorkAreaToStereotype = null;  //  @jve:decl-index=0:visual-constraint="483,101"

	private JMenuItem menuItemDelete = null;

	private JMenuItem menuItemCut = null;

	private JMenuItem menuItemCopy = null;

	private JMenuItem menuItemPaste = null;

	private JPopupMenu popupMenuWorkAreaToDiagram = null;  //  @jve:decl-index=0:visual-constraint="487,163"

	private JMenuItem menuItemZoomIn = null;

	private JMenuItem menuItemZoomOut = null;

	private JMenuItem menuItemZoomReset = null;

	private JMenuItem menuItemUndo = null;

	private JMenuItem menuItemRefresh = null;

	private JMenuItem menuItemRedo = null;

	private JMenuItem menuItemSelectAll = null;

	private JPopupMenu popupMenuTabbedPane = null;  //  @jve:decl-index=0:visual-constraint="499,237"

	private JMenuItem menuItemCloseMe = null;

	private JMenuItem menuItemCloseOthers = null;

	private JMenuItem menuItemCloseAll = null;

	private boolean swinLineToWillResize = false;

	private boolean swinLineReadyToResize = false;

	int posOfSwinLineSelected = -1;

	public TabbedPaneWorkArea(ProcessGraphDocking owner) {
		setLayout(new BorderLayout());
		getTabbedPane().addTab("Bienvenido", null, getPanelWelcome(), null);
		owner.addTabTitle("Bienvenido");//Harvet
		add(getTabbedPane(), BorderLayout.CENTER);
		processGraphDocking = owner;
		key.setCloseEnabled(false);
		key.setAutoHideEnabled(false);
		listSelection = new ArrayList<Stereotype>();
		arrayRelation = new ArrayList<Point>();//aqui

		listDiference = new ArrayList<Point>();

		toolBarActivities = new VLToolBar("Actividades");
		toolBarJoins = new VLToolBar("Enlaces");
		toolBarStates = new VLToolBar("Estados");
		toolBarMisc = new VLToolBar("Miscelaneas");
	}
	private TabbedPaneWorkArea returnThis() {
		return this;
	}
	/**
	 * Agregar metodo que me permita agregarle una tab al tabbedpane que es el scrollPaneWorkArea y reciba un string para el titulo
	 */
	public void AgregarTab(String titulo){
		getTabbedPane().addTab(titulo, null, getPanelWorkAreaBack(), null/*titulo*/);//
	}
	public void AgregarTab(String titulo, Component Comp0){
		getTabbedPane().addTab(titulo, null, Comp0, null);//fix
	}
	public void InsertarTab(String titulo, int pos){
		getTabbedPane().insertTab(titulo, null, getPanelWorkAreaBack(), null/*titulo*/,pos);//
	}
	public int getCantidadTab(){
		return getTabbedPane().getTabCount();
	}

	public String getTituloAt(int pos){
		return getTabbedPane().getTitleAt(pos);
	}

	public DockKey getDockKey() {
		return key;
	}

	public Component getComponent() {
		return this;
	}


	/**
	 * This method initializes panelWelcome	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getPanelWelcome() {
		if (panelWelcome == null) {
			labelHelp = new JLabel();
			labelHelp.setFont(new Font("Tahoma", Font.PLAIN, 32));
			labelHelp.setSize(new Dimension(270, 52));
			labelHelp.setLocation(new Point(80, 231));
			labelHelp.setText("Ayuda");
			labelOpen = new JLabel();
			labelOpen.setBounds(new Rectangle(109, 167, 311, 48));
			labelOpen.setText("Abrir Diagrama");
			labelOpen.setCursor(new Cursor(Cursor.HAND_CURSOR));
			labelOpen.setFont(new Font("Tahoma", Font.PLAIN, 20));
			labelOpen.addMouseListener(new java.awt.event.MouseAdapter() {   
				public void mousePressed(java.awt.event.MouseEvent e) {    
					getProcessGraphDocking().getMenuItemOpenDiagram().doClick();
				}   

				public void mouseExited(java.awt.event.MouseEvent e) {    
					labelOpen.setFont(new Font("Tahoma", Font.PLAIN, 20));
				}
				public void mouseEntered(java.awt.event.MouseEvent e) {
					labelOpen.setFont(new Font("Tahoma", Font.PLAIN, 25));
				}
			});
			labelNuevo = new JLabel();
			labelNuevo.setBounds(new Rectangle(110, 109, 311, 37));
			labelNuevo.setText("Nuevo Mapa de Nivel Cero");
			labelNuevo.setCursor(new Cursor(Cursor.HAND_CURSOR));
			labelNuevo.setFont(new Font("Tahoma", Font.PLAIN, 20));
			labelNuevo.addMouseListener(new java.awt.event.MouseAdapter() {   
				public void mousePressed(java.awt.event.MouseEvent e) {    
					getProcessGraphDocking().getMenuItemNewDiagram().doClick();
				}   
				public void mouseExited(java.awt.event.MouseEvent e) {    
					labelNuevo.setFont(new Font("Tahoma", Font.PLAIN, 20));
				}   
				public void mouseEntered(java.awt.event.MouseEvent e) {    
					labelNuevo.setFont(new Font("Tahoma", Font.PLAIN, 25));

				}

			});
			labelWelcome = new JLabel();
			labelWelcome.setText("Inicio Rápido");
			labelWelcome.setSize(new Dimension(360, 45));
			labelWelcome.setLocation(new Point(81, 28));
			labelWelcome.setFont(new Font("Tahoma", Font.PLAIN, 32));
			panelWelcome = new JPanel();
			panelWelcome.setLayout(null);
			panelWelcome.setBackground(Color.white);
			panelWelcome.setSize(new Dimension(454, 360));
			panelWelcome.add(labelWelcome, null);
			panelWelcome.add(getButtonSeparador(), null);
			panelWelcome.add(getButtonHome(), null);
			panelWelcome.add(getButtonOpen2(), null);
			panelWelcome.add(getButtonNuevo(), null);
			panelWelcome.add(getButtonSeparador1(), null);
			panelWelcome.add(labelNuevo, null);
			panelWelcome.add(labelOpen, null);
			panelWelcome.add(getButtonHelp(), null);
			panelWelcome.add(labelHelp, null);
		}
		return panelWelcome;
	}

	/**
	 * This method initializes buttonSeparador	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getButtonSeparador() {
		if (buttonSeparador == null) {
			buttonSeparador = new JButton();
			buttonSeparador.setLocation(new Point(3, 73));
			buttonSeparador.setBackground(Color.white);
			buttonSeparador.setEnabled(false);
			buttonSeparador.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			buttonSeparador.setIcon(new ImageIcon(getClass().getResource("/icons/raya.PNG")));
			buttonSeparador.setDisabledIcon(new ImageIcon(getClass().getResource("/icons/raya.PNG")));
			buttonSeparador.setSize(new Dimension(447, 4));
		}
		return buttonSeparador;
	}

	/**
	 * This method initializes buttonHome	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getButtonHome() {
		if (buttonHome == null) {
			buttonHome = new JButton();
			buttonHome.setBounds(new Rectangle(1, 4, 77, 74));
			buttonHome.setEnabled(false);
			buttonHome.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			buttonHome.setDisabledIcon(new ImageIcon(getClass().getResource("/icons/home.png")));
			buttonHome.setIcon(new ImageIcon(getClass().getResource("/icons/home.png")));
			buttonHome.setBackground(Color.white);
		}
		return buttonHome;
	}

	/**
	 * This method initializes buttonOpen	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getButtonOpen2() {
		if (buttonOpen == null) {
			buttonOpen = new JButton();
			buttonOpen.setBounds(new Rectangle(58, 165, 43, 42));
			buttonOpen.setEnabled(false);
			buttonOpen.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			buttonOpen.setPreferredSize(new Dimension(43, 42));
			buttonOpen.setDisabledIcon(new ImageIcon(getClass().getResource("/icons/4444.png")));
			buttonOpen.setIcon(new ImageIcon(getClass().getResource("/icons/4444.png")));
			buttonOpen.setBackground(Color.white);
		}
		return buttonOpen;
	}

	/**
	 * This method initializes buttonNuevo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getButtonNuevo() {
		if (buttonNuevo == null) {
			buttonNuevo = new JButton();
			buttonNuevo.setLocation(new Point(45, 85));
			buttonNuevo.setBackground(Color.white);
			buttonNuevo.setEnabled(false);
			buttonNuevo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			buttonNuevo.setPreferredSize(new Dimension(63, 65));
			buttonNuevo.setDisabledIcon(new ImageIcon(getClass().getResource("/icons/666.png")));
			buttonNuevo.setIcon(new ImageIcon(getClass().getResource("/icons/666.png")));
			buttonNuevo.setSize(new Dimension(63, 63));
		}
		return buttonNuevo;
	}

	/**
	 * This method initializes buttonSeparador1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getButtonSeparador1() {
		if (buttonSeparador1 == null) {
			buttonSeparador1 = new JButton();
			buttonSeparador1.setBounds(new Rectangle(5, 282, 445, 4));
			buttonSeparador1.setEnabled(false);
			buttonSeparador1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			buttonSeparador1.setDisabledIcon(new ImageIcon(getClass().getResource("/icons/raya.PNG")));
			buttonSeparador1.setIcon(new ImageIcon(getClass().getResource("/icons/raya.PNG")));
			buttonSeparador1.setBackground(Color.white);
		}
		return buttonSeparador1;
	}

	/**
	 * This method initializes buttonHelp	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getButtonHelp() {
		if (buttonHelp == null) {
			buttonHelp = new JButton();
			buttonHelp.setBounds(new Rectangle(10, 221, 58, 60));
			buttonHelp.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			buttonHelp.setIcon(new ImageIcon(getClass().getResource("/icons/help11.png")));
			buttonHelp.setEnabled(false);
			buttonHelp.setDisabledIcon(new ImageIcon(getClass().getResource("/icons/help11.png")));
			buttonHelp.setBackground(Color.white);
		}
		return buttonHelp;
	}

	/**
	 * This method initializes scrollPaneWorkArea	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	public JScrollPane getScrollPaneWorkArea() {//delete miercoles
		if (scrollPaneWorkArea == null) {
			scrollPaneWorkArea = new JScrollPane();
			scrollPaneWorkArea.setViewportView(getPanelWorkArea());
			scrollPaneWorkArea.setBackground(Color.white);
			//17/10/07 faltan 2 lineas de cod
			scrollPaneWorkArea.getVerticalScrollBar().setUnitIncrement(10);
			scrollPaneWorkArea.getHorizontalScrollBar().setUnitIncrement(10);
		}
		return scrollPaneWorkArea;
	}
	/**
	 * This method initializes panelWorkAreaBack	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getPanelWorkAreaBack() {
		if (panelWorkAreaBack == null) {

			panelWorkAreaBack = new JPanel();
			panelWorkAreaBack.setLayout(new BorderLayout());
			//panelWorkAreaBack.setBackground(Color.white);
			/*panelWorkAreaBack.setSize(new Dimension(300, 300));
			panelWorkAreaBack.setPreferredSize(new Dimension(300, 300));*/
			panelWorkAreaBack.add(getScrollPaneWorkArea(), java.awt.BorderLayout.CENTER);
			panelWorkAreaBack.add(getPanelStatusBar(), BorderLayout.SOUTH);

			panelWorkAreaBack.add(containerBar, BorderLayout.WEST);
			containerBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			ToolBarPanel toolBarPanel = containerBar.getToolBarPanelAt(BorderLayout.WEST);
			toolBarPanel.add(toolBarActivities, new ToolBarConstraints(0, 0));// primera fila y tal columna
			toolBarPanel.add(toolBarMisc, new ToolBarConstraints(0, 1));
			toolBarPanel.add(toolBarJoins, new ToolBarConstraints(0, 2));
			toolBarPanel.add(toolBarStates, new ToolBarConstraints(0, 3));

		}	
		return panelWorkAreaBack;
	}
	/**
	 * This method initializes panelWorkArea	
	 * 	
	 * @return ceis.grehu.PanelWorkArea	
	 */
	public PanelWorkArea getPanelWorkArea() {
		if (panelWorkArea == null) {
			panelWorkArea = new PanelWorkArea(returnThis()/*, getJTextAreaNote()*/);
			panelWorkArea.setPreferredSize(new Dimension(2400,1400/*800, 800*/));
			panelWorkArea.setBackground(Color.WHITE);   
			panelWorkArea.setLayout(null);
			panelWorkArea.setFocusable( true );
			panelWorkArea.setFocusCycleRoot( true );
			panelWorkArea.add(getProcessGraphDocking().getJTextFieldName());
			panelWorkArea.add(getProcessGraphDocking().getJTextAreaNote());
			panelWorkArea.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {    
					panelWorkArea.transferFocus();
				}
				public void mouseExited(java.awt.event.MouseEvent e) {    
					labelPosition.setText("Posicion : 0.0");
				}

				public void mousePressed(java.awt.event.MouseEvent e) {
					Diagram diagramActive = getProcessGraphDocking().getPaintManager().getActiveDiagram();
					getProcessGraphDocking().getJTextFieldName().setEditable(true);
					getProcessGraphDocking().getJTextAreaNote().setVisible(false);
					if(e.getButton() == 1){
						if(flagStereotype != null){
							String type = flagStereotype.getType();
							if(type != "Nota" && type != "BaseDato" && type != "Decision" && type != "EITiempo" &&
									type != "EIMensaje" && type != "EInicial" && type != "EFinal"){
								String tempName = flagStereotype.getName();
								if(diagramActive.getZoomFactor() >= 1){
									flagStereotype.setName(getProcessGraphDocking().getJTextFieldName().getText());
									diagramActive.setNameById(flagStereotype.getId(), getProcessGraphDocking().getJTextFieldName().getText());
									if(getProcessGraphDocking().getJTextFieldName().isVisible()){
										getProcessGraphDocking().getTreeActiveDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeActiveDiagrams(diagramActive));
										getProcessGraphDocking().getTreeDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeDiagrams(getProcessGraphDocking().getPaintManager().getDiagramList(), getProcessGraphDocking()));
										getProcessGraphDocking().getTableProperties().setDefaultComboBoxModelProcess(FactoryModel.factory.getModelForComboboxProcess(diagramActive));
									}
								}
								boolean found = false;
								if(getProcessGraphDocking().getStringTyped() != ""){
									found = diagramActive.findNameByTypeId(getProcessGraphDocking().getStringTyped(), flagStereotype.getType(), flagStereotype.getId());
								}
								else{
									found = diagramActive.findNameByTypeId(flagStereotype.getName(), flagStereotype.getType(), flagStereotype.getId());
								}
								if(found == true){
									diagramActive.setNameById(flagStereotype.getId(), tempName);
									flagStereotype = null;
									JOptionPane.showMessageDialog(getProcessGraphDocking(), "Existe otro elemento de este tipo con el mismo nombre, modifíquelo!", "Error", JOptionPane.ERROR_MESSAGE);
									flag = true;
									getProcessGraphDocking().getJTextFieldName().setVisible(false);
								} 
								else{
									if(diagramActive.getZoomFactor() >= 1){
										flagStereotype.setName(getProcessGraphDocking().getJTextFieldName().getText());
										diagramActive.setNameById(flagStereotype.getId(), getProcessGraphDocking().getJTextFieldName().getText());
									}	
									FontMetrics fm = getPanelWorkArea().getGraphics().getFontMetrics();
									int length = fm.charsWidth( flagStereotype.getName().toCharArray(), 0 , flagStereotype.getName().length());
									if(length > flagStereotype.getWidth()){
										flagStereotype.setWidth(length + 5);
										diagramActive.setWidthById(flagStereotype.getId(), length + 2);
									}
									getProcessGraphDocking().getJTextFieldName().setVisible(false);
								}
								diagramActive.unselectAllStereotype();
								diagramActive.unselectAllRelations();

							}else if(flagStereotype.getType() == "Nota"){
								if(diagramActive.getZoomFactor() >= 1){
									flagStereotype.setDescription(getProcessGraphDocking().getJTextAreaNote().getText());
									diagramActive.setDescriptionById(flagStereotype.getId(), getProcessGraphDocking().getJTextAreaNote().getText());

									FontMetrics fm = getPanelWorkArea().getGraphics().getFontMetrics();
									String text = getProcessGraphDocking().getJTextAreaNote().getText();
									String[] a = text.split("\n");
									int max = 0;
									for (int i = 0; i < a.length; i++) {
										int l = fm.charsWidth( a[i].toCharArray(), 0 , a[i].length());
										if(l > max){
											max = l;
										}
									}
									if(max > flagStereotype.getWidth()){
										flagStereotype.setWidth(max + 5);
										diagramActive.setWidthById(flagStereotype.getId(), max + 5);
									}

									Graphics g = getPanelWorkArea().getGraphics();
									int height = new Double(fm.getLineMetrics(a[0], g).getHeight()).intValue();
									int lastPos = 0;
									for (int i = 0; i < a.length; i++) {
										lastPos = i;
									}

									int division = getProcessGraphDocking().getJTextAreaNote().getBounds().height/height;
									if(lastPos + 1 > division ){
										if(flagStereotype.getHeigth() < (height*(lastPos + 1) + 15 + 30)){
											flagStereotype.setHeigth( height*(lastPos + 1) + 15 + 30);
											diagramActive.setHeightById(flagStereotype.getId(), height*(lastPos + 1) + 15 + 30);
										}
									}
								}
								getProcessGraphDocking().getJTextAreaNote().setVisible(false);
								diagramActive.unselectAllStereotype();
								diagramActive.unselectAllRelations();
							}
						}
						//setCountClick(e.getClickCount());
						int x = new Double ( e.getX() / getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor() ).intValue();
						int y = new Double ( e.getY() / getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor() ).intValue();
						pointInicial = new Point(x,y);
						leftClickPressed = true;
						if(diagramActive.getType() == "RGS" && diagramActive.getSwinLine().posOfSwinLineSelected(pointInicial) != -1){
							setPosOfSwinLineSelected(diagramActive.getSwinLine().posOfSwinLineSelected(pointInicial));
							setSwinLineReadyToResize(true);
						}
						relationSelect = diagramActive.getSelectedRelation(pointInicial);
						/*SwinLine swinLine = diagram.*/
						if(relationSelect == null){
							stereotypeSelect = diagramActive.getSelectedStereotype(pointInicial);
							listSelection = diagramActive.getSelectedStereotypes();
							arrayRelation = diagramActive.calculateRelationship();//aqui
						}

						if(stereotypeSelect != null && stereotypeSelect.isVisible()){//estoy parado sobre un setereotipo seleccionado

							setCountClick(e.getClickCount(),stereotypeSelect.validRegion(pointInicial));

							getProcessGraphDocking().getJTextFieldName().setVisible(false);
							getProcessGraphDocking().getJTextAreaNote().setVisible(false);

							getProcessGraphDocking().getComboBoxType().setVisible(false);
							getProcessGraphDocking().getComboBoxSelected().setVisible(false);
							getProcessGraphDocking().getScrollPaneDescription().setVisible(false);
							getProcessGraphDocking().getComboBoxVisible().setVisible(false);
						}
						else if(stereotypeSelect == null && relationSelect == null){
							/**
							 * Actualizar tabla de propiedades y combo
							 */
							getProcessGraphDocking().getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToDiagram(diagramActive));
							getProcessGraphDocking().getButtonChosserBackGround().setVisible(false);
							getProcessGraphDocking().getButtonChooserLine().setVisible(false);
							getProcessGraphDocking().getComboBoxSelected().setVisible(false);
							getProcessGraphDocking().getScrollPaneDescription().setVisible(false);
							getProcessGraphDocking().getComboBoxVisible().setVisible(false);
							getProcessGraphDocking().getComboBoxType().setVisible(false);
							getProcessGraphDocking().getTableProperties().getComboBoxProcess().getModel().setSelectedItem("<<Procesos>>");
							getProcessGraphDocking().getTreeActiveDiagrams().getTreeActiveDiagrams().setSelectionRow(0);
						}

						if(relationSelect != null){
							setRelationSelected(true);
							relationReadyToResize = true;
						}

						if(stereotypeSelect != null && stereotypeSelect.isVisible()){//marco un Stereotypo
							if(listSelection.size() < 2){//hay solo uno selec
								if(flag == false){
									flagStereotype = new Stereotype(stereotypeSelect);
									stereotypeReadyToMove = true;  
								}
								xDifference = pointInicial.x - stereotypeSelect.getInicialPoint().x;
								yDifference = pointInicial.y - stereotypeSelect.getInicialPoint().y;
								String type = stereotypeSelect.getType();
								if(type != "Nota" && type != "BaseDato" && type != "Decision" && type != "EITiempo" 
									&& type != "EIMensaje" && type != "EInicial" && type != "EFinal"){
									if(stereotypeSelect.getName() != getProcessGraphDocking().getJTextFieldName().getText() /*&& stereotypeSelect.getId() == probe.getId()*/){
										getProcessGraphDocking().setNonKeyTyped(true);
									}
									if(getProcessGraphDocking().isNonKeyTyped() == true){
										getProcessGraphDocking().getJTextFieldName().setText(stereotypeSelect.getName());
										getProcessGraphDocking().setNonKeyTyped(false);
									}
									if(diagramActive.getZoomFactor() >= 1){
										stereotypeSelect.setName(getProcessGraphDocking().getJTextFieldName().getText());
										FontMetrics fm = getPanelWorkArea().getGraphics().getFontMetrics();
										int length = fm.charsWidth( stereotypeSelect.getName().toCharArray(), 0 , stereotypeSelect.getName().length());
										if(length > stereotypeSelect.getWidth()){
											stereotypeSelect.setWidth(length + 5);
										}
										diagramActive.unselectAllStereotype();
										diagramActive.unselectAllRelations();
									}
								}
								else if(stereotypeSelect.getType() == "Nota"){

									if(stereotypeSelect.getDescription() != getProcessGraphDocking().getJTextAreaNote().getText()/* && stereotypeSelect.getId() == probe.getId()*/){
										getProcessGraphDocking().setNonKeyTyped(true);
									}
									if(getProcessGraphDocking().isNonKeyTyped() == true){
										getProcessGraphDocking().getJTextAreaNote().setText(stereotypeSelect.getDescription());
										getProcessGraphDocking().setNonKeyTyped(false);
									}
									stereotypeSelect.setDescription(getProcessGraphDocking().getJTextAreaNote().getText());

									diagramActive.unselectAllStereotype();
									diagramActive.unselectAllRelations();
								}
								/**
								 * Actualizar tabla de propiedades
								 */
								getProcessGraphDocking().getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToStereotype(stereotypeSelect));
								if(stereotypeSelect.getType() != "Nota")
									getProcessGraphDocking().getTableProperties().getComboBoxProcess().getModel().setSelectedItem(stereotypeSelect.getName());
								else{//es la nota
									getProcessGraphDocking().getTreeActiveDiagrams().getTreeActiveDiagrams().setSelectionRow(-1);
									getProcessGraphDocking().getTableProperties().getComboBoxProcess().getModel().setSelectedItem("<<Procesos>>");
								}

								/**
								 * aqui es el codigo relacion
								 */

								if(getProcessGraphDocking().getButtonPressed() == "Anclar Nota"){
									if(diagramActive.getSelectedStereotype(pointInicial).getType() != "Nota"){
										setStereotypeInicial(diagramActive.getSelectedStereotype(pointInicial));
										relationNote = true;
									}
									getProcessGraphDocking().setButtonPressed("");
								}
								if(getProcessGraphDocking().getButtonPressed() == "Transicion"){
									setStereotypeInicial(diagramActive.getSelectedStereotype(pointInicial));
									transicion = true;
									getProcessGraphDocking().setButtonPressed("");
								}

							}else{//son varios el length es mayor de 2
								if(diagramActive.isStereotypeInList(stereotypeSelect, listSelection)){// estoy sobre un stereotypo seleccionado
									xBigDifference = calculateBigXDifference(pointInicial);//aqui
									yBigDifference = calculateBigYDifference(pointInicial);//aqui
									for(int i = 0; i < listSelection.size(); i ++){
										int xDiff = pointInicial.x - listSelection.get(i).getInicialPoint().x;//aqui
										int yDiff = pointInicial.y - listSelection.get(i).getInicialPoint().y;//aqui
										Point Differece = new Point(xDiff,yDiff);
										listDiference.add(Differece);
									}
									someStereotypeReadyToMove = true;
								}else{
									diagramActive.unselectAllStereotype();
									diagramActive.unselectAllRelations();
									stereotypeReadyToMove = true;
									xDifference = pointInicial.x - stereotypeSelect.getInicialPoint().x;//aqui
									yDifference = pointInicial.y - stereotypeSelect.getInicialPoint().y;//aqui
									stereotypeReadyToMove = true;
								}
							}
						}else{
							flagStereotype = null;
							diagramActive.unselectAllStereotype();//aqui
							diagramActive.unselectAllRelations();
						}
					}
					if(e.getButton() == 3){
						int x = new Double ( e.getX() / getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor() ).intValue();
						int y = new Double ( e.getY() / getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor() ).intValue();
						Stereotype stereotype = diagramActive.getSelectedStereotype(new Point(x,y));
						setRigthClickPressed(true);
						Point mousePosition = panelWorkArea.getMousePosition();
						if(stereotype != null && stereotype.isVisible()){
							stereotype.setSelected(true);
							if(stereotype.getType() != "Nota"){
								getProcessGraphDocking().getTreeActiveDiagrams().setSelectionToRow(stereotype);
								getProcessGraphDocking().getTableProperties().getComboBoxProcess().getModel().setSelectedItem(stereotype.getName());
							}

							getProcessGraphDocking().getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToStereotype(stereotype));
							getPopupMenuWorkAreaToStereotype().show(panelWorkArea, mousePosition.x, mousePosition.y);
						}
						else{
							getPopupMenuWorkAreaToStereotype().setVisible(false);
							if(diagramActive.getSelectedStereotype() != null)
								diagramActive.getSelectedStereotype().setSelected(false);
							getProcessGraphDocking().getTreeActiveDiagrams().getTreeActiveDiagrams().setSelectionRow(0);
							getProcessGraphDocking().getTableProperties().getComboBoxProcess().getModel().setSelectedItem("<<Procesos>>");
							getProcessGraphDocking().getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToDiagram(diagramActive));
							getPopupMenuWorkAreaToDiagram().show(panelWorkArea, mousePosition.x, mousePosition.y);
						}
					}
					//here
					int xPressed = new Double ( e.getX() / getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor() ).intValue();
					int yPressed = new Double ( e.getY() / getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor() ).intValue();
					getProcessGraphDocking().setPointPressed(new Point(xPressed,yPressed));

					getProcessGraphDocking().setStringTyped("");
					flag = false;
					repaint();
				}

				public void mouseReleased(java.awt.event.MouseEvent e) {
					Diagram diagramActive = getProcessGraphDocking().getPaintManager().getActiveDiagram();
					Stereotype stereotype = null;
					getPanelWorkArea().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					posOfRectToWillResize = -1;
					stereotypeReadyToWillResize = false;
					int x = new Double ( e.getX() / getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor() ).intValue();
					int y = new Double ( e.getY() / getProcessGraphDocking().getPaintManager().getActiveDiagram().getZoomFactor() ).intValue();
					pointFinal = new Point(x, y);
					if((leftClickPressed) && (getProcessGraphDocking().getButtonPressed() != "" && getProcessGraphDocking().getButtonPressed() != "Anclar Nota")){
						String tipo = getProcessGraphDocking().getButtonPressed();
						int count = diagramActive.countStereotypesByType(tipo);
						stereotype = new Stereotype(pointInicial,pointFinal,tipo,diagramActive.getId(),count);
						if(getProcessGraphDocking().getPaintManager().getCutList().size() > 0){
							for (Stereotype stereotypeCut : getProcessGraphDocking().getPaintManager().getCutList()) {
								if(stereotypeCut.getName().equals(stereotype.getName())){
									count++;
									stereotype = new Stereotype(pointInicial,pointFinal,tipo,diagramActive.getId(),count);
								}
							}
						}
						diagramActive.setSaveStatus(true);
						stereotype.setSelected(true);
						stereotypeSelect = stereotype;
						flagStereotype = stereotype;
						/**
						 * Agregar a la lista de estereotipo segun el diagrama
						 */
						getProcessGraphDocking().getPaintManager().getActiveDiagram().addStereotype(stereotype);
						/**
						 * Undo Redo Pintar
						 */
						Diagram activeDiag  = getProcessGraphDocking().getPaintManager().getActiveDiagram();
						ArrayList<Stereotype> array = new ArrayList<Stereotype>(activeDiag.getPictureList());
						UndoAction action = new UndoAction("paint");
						for(int i = 0; i < array.size() - 1; i++){
							Stereotype undoStereotype = new Stereotype(array.get(i));
							undoStereotype.setSelected(true);
							action.getArrayActions().add(undoStereotype);
						}
						if(action.getArrayActions().size() > 0)
							action.getArrayActions().get(action.getArrayActions().size() - 1).setSelected(true);//17/1/08
						if(activeDiag.getListRedo().size() > 0){
							activeDiag.getListRedo().clear();
							getProcessGraphDocking().getButtonRedo().setEnabled(false);
							getProcessGraphDocking().getMenuItemRedo().setEnabled(false);
							getMenuItemRedo().setEnabled(false);
						}
						activeDiag.getListUndo().add(action);
						getProcessGraphDocking().getButtonUndo().setEnabled(true);
						getProcessGraphDocking().getMenuItemUndo().setEnabled(true);
						getMenuItemUndo().setEnabled(true);
						/**
						 * Actualizando tree diagrams
						 */
						getProcessGraphDocking().getTreeDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeDiagrams(getProcessGraphDocking().getPaintManager().getDiagramList(), getProcessGraphDocking()));
						/**
						 * Actualizando tree Active diagrams
						 */
						getProcessGraphDocking().getTreeActiveDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeActiveDiagrams(getProcessGraphDocking().getPaintManager().getActiveDiagram()));
						getProcessGraphDocking().getTreeActiveDiagrams().setSelectionToRow(stereotype);
						/**
						 * Actualizando Combobox process tableProperties
						 */
						getProcessGraphDocking().getTableProperties().setDefaultComboBoxModelProcess(FactoryModel.factory.getModelForComboboxProcess(getProcessGraphDocking().getPaintManager().getActiveDiagram()));
						getProcessGraphDocking().getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToStereotype(stereotype));
						if(stereotype.getType() != "Nota")
							getProcessGraphDocking().getTableProperties().getComboBoxProcess().getModel().setSelectedItem(stereotype.getName());
						if(stereotype.getType() != "Nota")
							getProcessGraphDocking().showBounstoJtext( stereotype.getName(), stereotype.getType() );
						else
							getProcessGraphDocking().showBounstoJtext( stereotype.getDescription(), stereotype.getType() );
					}
					if(leftClickPressed && (getProcessGraphDocking().getButtonPressed() == "")){//Para seleccionar 
						if(mouseDragged && !stereotypeReadyToMove /*&& !relationSelected*/){//seleccion multiple
							diagramActive.setGroupSelectionToStereotypes(getProcessGraphDocking().getPaintManager().getSelectedArea(getPointInicial(), getPointMoved()));
							for (Stereotype stereotypeSelected : diagramActive.getSelectedStereotypes()) {
								for (Relation relation : stereotypeSelected.getRelatedStereotypes()) {
									diagramActive.setRelationSelected(relation);
								}
							}
							if(diagramActive.getSelectedStereotypes().size() == 0 || diagramActive.getSelectedStereotypes().size() > 1){//selecciono muchos o ninguno
								/**
								 * Actualizar tabla de propiedades
								 */
								getProcessGraphDocking().getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToDiagram(diagramActive));
								getProcessGraphDocking().getTreeActiveDiagrams().getTreeActiveDiagrams().clearSelection();
							}
							else{//selecciono uno solo
								getProcessGraphDocking().getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToStereotype(diagramActive.getSelectedStereotype()));
								if(diagramActive.getSelectedStereotype().getType() != "Nota")
									getProcessGraphDocking().getTableProperties().getComboBoxProcess().getModel().setSelectedItem(diagramActive.getSelectedStereotype().getName());
								getProcessGraphDocking().getTreeActiveDiagrams().setSelectionToRow(diagramActive.getSelectedStereotype());
							}
						}
						if(!mouseDragged && stereotypeReadyToMove /*&& !relationSelected*/){
							//simple click
							diagramActive.setSimpleSelectionToStereotype(getPointFinal());
							/**
							 * Actualizar tabla de propiedades
							 */
							getProcessGraphDocking().getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToStereotype(diagramActive.getSelectedStereotype()));
							getProcessGraphDocking().getTreeActiveDiagrams().setSelectionToRow(diagramActive.getSelectedStereotype());
							if(getCountClick() == 2 ){
								if(stereotypeSelect.getType() != "Nota"){
									if(isClickValid()){
										getProcessGraphDocking().showBounstoJtext(stereotypeSelect.getName(), stereotypeSelect.getType());
									}else if(!isClickValid()){ //el double click no es en la region valida del estereotipo para el nombre, es para hacerle el diagrama de actividad
										DialogSwinLine dialogSwinLine = new DialogSwinLine(getProcessGraphDocking());

										String name = "Resumen Grafico de " + stereotypeSelect.getName();
										String tipo = stereotypeSelect.getType();
										dialogSwinLine.setLocation((getProcessGraphDocking().getWidth() - dialogSwinLine.getWidth())/2,((getProcessGraphDocking().getHeight() - dialogSwinLine.getHeight()))/2);
										dialogSwinLine.getTextFieldName().setText(name);
										if (tipo == "Macro" || tipo == "Proc" || tipo == "SubProc") {
											//aqui va el visible
											dialogSwinLine.setVisible(true);
										}
									}
								}
								else
									getProcessGraphDocking().showBounstoJtext(stereotypeSelect.getDescription(), stereotypeSelect.getType());
							}
						}
					}
					if(!mouseDragged && isRelationSelected()){
						diagramActive.setRelationSelected(relationSelect);
					}
					if(mouseDragged && isStereotypeReadyToMove() && !isStereotypeReadyToResize() /*&& !relationSelected*/){//pintarlo cuando suelte uno solo
						Stereotype stereotypeRelation = diagramActive.getSelectedStereotype(pointFinal);
						if(stereotypeRelation != null){
							if(stereotypeRelation.getType() == "Nota" && isRelationNote()){
								if(getProcessGraphDocking().getPaintManager().findRelationDestinyForContent(stereotypeInicial, stereotypeRelation) == false){

									Stereotype undoStereotype = new Stereotype(stereotypeInicial);
									undoStereotype.setSelected(true);
									UndoAction action = new UndoAction("edit");
									action.getArrayActions().add(undoStereotype);
									if(diagramActive.getListRedo().size() > 0){
										diagramActive.getListRedo().clear();
										getProcessGraphDocking().getButtonRedo().setEnabled(false);
										getProcessGraphDocking().getMenuItemRedo().setEnabled(false);
									}
									diagramActive.getListUndo().add(action);

									int xInicial = stereotypeInicial.getInicialPoint().x + stereotypeInicial.getWidth()/2;
									int yInicial = stereotypeInicial.getInicialPoint().y + stereotypeInicial.getHeigth()/2;
									int xFinal = stereotypeRelation.getInicialPoint().x + stereotypeRelation.getWidth()/2;
									int yFinal = stereotypeRelation.getInicialPoint().y + stereotypeRelation.getHeigth()/2;
									Point point1 = new Point(xInicial, yInicial);
									Point point2 = new Point(xFinal, yFinal);
									Relation relation = getProcessGraphDocking().getPaintManager().calculatePointsRelation(point1, point2,stereotypeInicial,stereotypeRelation);
									relation.setTypeRel("Anclar Nota");
									relation.setSelected(true);
									stereotypeInicial.addRelation(relation);
									diagramActive.setSaveStatus(true);
								}
							}

							if(isTransicion()){
								if(stereotypeRelation.getType() != "Nota"){
									if(getProcessGraphDocking().getPaintManager().findRelationDestinyForContent(stereotypeInicial, stereotypeRelation) == false){

										Stereotype undoStereotype = new Stereotype(stereotypeInicial);
										undoStereotype.setSelected(true);
										UndoAction action = new UndoAction("edit");
										action.getArrayActions().add(undoStereotype);
										if(diagramActive.getListRedo().size() > 0){
											diagramActive.getListRedo().clear();
											getProcessGraphDocking().getButtonRedo().setEnabled(false);
											getProcessGraphDocking().getMenuItemRedo().setEnabled(false);
										}
										diagramActive.getListUndo().add(action);

										int xInicial = stereotypeInicial.getInicialPoint().x + stereotypeInicial.getWidth()/2;
										int yInicial = stereotypeInicial.getInicialPoint().y + stereotypeInicial.getHeigth()/2;
										int xFinal = stereotypeRelation.getInicialPoint().x + stereotypeRelation.getWidth()/2;
										int yFinal = stereotypeRelation.getInicialPoint().y + stereotypeRelation.getHeigth()/2;
										Point point1 = new Point(xInicial, yInicial);
										Point point2 = new Point(xFinal, yFinal);
										Relation relation = getProcessGraphDocking().getPaintManager().calculatePointsRelation(point1, point2,stereotypeInicial,stereotypeRelation);
										relation.setTypeRel("Transicion");
										relation.setSelected(true);
										stereotypeInicial.addRelation(relation);
										diagramActive.setSaveStatus(true);
									}
								}
							}
						}
						if(!isDrawline()){
							for (int i = 0; i < diagramActive.getLength(); i++){
								if(diagramActive.getStereotype(i) == stereotypeSelect){
									diagramActive.deleteStereotype(i);
									diagramActive.addStereotype(stereotypeSelect);

									Stereotype undoStereotype = new Stereotype(stereotypeSelect);
									undoStereotype.setSelected(true);
									UndoAction action = new UndoAction("edit");
									action.getArrayActions().add(undoStereotype);
									if(diagramActive.getListRedo().size() > 0){
										diagramActive.getListRedo().clear();
										getProcessGraphDocking().getButtonRedo().setEnabled(false);
										getProcessGraphDocking().getMenuItemRedo().setEnabled(false);
										getMenuItemRedo().setEnabled(false);
									}
									diagramActive.getListUndo().add(action);

									Point pointStereotype = new Point(pointMoved);
									Point pointInitial = new Point(pointStereotype.x - xDifference, pointStereotype.y - yDifference);
									if(pointStereotype.x - xDifference < 0 && pointStereotype.y - yDifference < 0){
										pointStereotype = new Point(4, 4);//para que quede un poco separado de la esquina
										pointInitial = new Point(pointStereotype);
									}
									else if(pointStereotype.x - xDifference < 0 && pointStereotype.y - yDifference > 0){
										pointStereotype = new Point(4, pointStereotype.y - yDifference);
										pointInitial = new Point(pointStereotype);
									}
									else if(pointStereotype.x - xDifference > 0 && pointStereotype.y - yDifference < 0){
										pointStereotype = new Point(pointStereotype.x - xDifference, 4);
										pointInitial = new Point(pointStereotype);
									}

									Point pointEnd = new Point(pointInitial.x + stereotypeSelect.getWidth(), pointInitial.y + stereotypeSelect.getHeigth());
									diagramActive.getStereotype(diagramActive.getLength() - 1).setInicialPoint(pointInitial);
									diagramActive.getStereotype(diagramActive.getLength() - 1).setFinalPoint(pointEnd);
									diagramActive.setSimpleSelectionToStereotype(pointStereotype);//cambio

									//nuevo
									Stereotype stereotypeEvaluate = diagramActive.getStereotype(diagramActive.getLength() - 1);
									ArrayList<Stereotype> array = new ArrayList<Stereotype>(getProcessGraphDocking().getPaintManager().findRelationDestiny(stereotypeEvaluate));
									if( array.size() > 0){
										for (Stereotype stereotypeContent : array) {
											int xInicial = stereotypeContent.getInicialPoint().x + stereotypeContent.getWidth()/2;
											int yInicial = stereotypeContent.getInicialPoint().y + stereotypeContent.getHeigth()/2;
											int xFinal = stereotypeEvaluate.getInicialPoint().x + stereotypeEvaluate.getWidth()/2;
											int yFinal = stereotypeEvaluate.getInicialPoint().y + stereotypeEvaluate.getHeigth()/2;
											Point point1 = new Point(xInicial, yInicial);
											Point point2 = new Point(xFinal, yFinal);
											Relation relation = getProcessGraphDocking().getPaintManager().calculatePointsRelation(point1, point2,stereotypeContent,stereotypeEvaluate);
											stereotypeContent.updateRelation(stereotypeEvaluate, relation);
										}
									}
									if(stereotypeEvaluate.getRelatedStereotypes().size() > 0){
										int xInicial = stereotypeEvaluate.getInicialPoint().x + stereotypeEvaluate.getWidth()/2;
										int yInicial = stereotypeEvaluate.getInicialPoint().y + stereotypeEvaluate.getHeigth()/2;
										Point point1 = new Point(xInicial, yInicial);
										for (Relation relation : stereotypeEvaluate.getRelatedStereotypes()) {
											int xFinal = relation.getStereotype().getInicialPoint().x + relation.getStereotype().getWidth()/2;
											int yFinal = relation.getStereotype().getInicialPoint().y + relation.getStereotype().getHeigth()/2;
											Point point2 = new Point(xFinal, yFinal);
											Relation relationEvaluate = getProcessGraphDocking().getPaintManager().calculatePointsRelation(point1, point2,stereotypeEvaluate,relation.getStereotype());
											stereotypeEvaluate.updateRelation(relation.getStereotype(), relationEvaluate);
										}
									}
									break;
								}
							}
							diagramActive.setSaveStatus(true);
							/**
							 * Actualizar tabla de propiedades
							 */
							getProcessGraphDocking().getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToStereotype(diagramActive.getSelectedStereotype()));
							getProcessGraphDocking().getTreeActiveDiagrams().setSelectionToRow(diagramActive.getSelectedStereotype());
						}
					}
					if(isSomeStereotypeReadyToMove() && mouseDragged /*&& !relationSelected*/){//pintar cuando suelte muchos

						UndoAction action = new UndoAction("edit");
						for(int i = 0; i < listSelection.size(); i++){
							Stereotype stereotypeAction = new Stereotype(listSelection.get(i));
							stereotypeAction.setSelected(true);
							action.getArrayActions().add(stereotypeAction);
						}
						if(diagramActive.getListRedo().size() > 0){
							diagramActive.getListRedo().clear();
							getProcessGraphDocking().getButtonRedo().setEnabled(false);
							getProcessGraphDocking().getMenuItemRedo().setEnabled(false);
							getMenuItemRedo().setEnabled(false);
						}
						diagramActive.getListUndo().add(action);

						Point pointInitial = new Point(diagramActive.getInicialXOfSelection() - (pointInicial.x - pointMoved.x), diagramActive.getInicialYOfSelection() - (pointInicial.y - pointMoved.y));
						for(int i = 0; i < listSelection.size(); i++){
							Point groupPoint = new Point(pointInitial.x + arrayRelation.get(i).x, pointInitial.y + arrayRelation.get(i).y);
							if(pointInitial.x < 0 && pointInitial.y < 0){
								groupPoint = new Point(arrayRelation.get(i).x + 4, arrayRelation.get(i).y + 4);
							}
							else if(pointInitial.x < 0 && pointInitial.y > 0){
								groupPoint = new Point(arrayRelation.get(i).x + 4, pointInitial.y + arrayRelation.get(i).y);
							}
							else if(pointInitial.x > 0 && pointInitial.y < 0){
								groupPoint = new Point(pointInitial.x + arrayRelation.get(i).x, arrayRelation.get(i).y + 4);
							}
							Stereotype stereotype2 = listSelection.get(i);
							for (int j = 0; j < diagramActive.getLength(); j++){
								if(diagramActive.getStereotype(j) == stereotype2){
									diagramActive.deleteStereotype(j);
									diagramActive.addStereotype(stereotype2);
									Point pointEnd = new Point(groupPoint.x + stereotype2.getWidth(), groupPoint.y + stereotype2.getHeigth());
									diagramActive.getStereotype(diagramActive.getLength() - 1).setInicialPoint(groupPoint);
									diagramActive.getStereotype(diagramActive.getLength() - 1).setFinalPoint(pointEnd);

									//nuevo
									Stereotype stereotypeEvaluate = diagramActive.getStereotype(diagramActive.getLength() - 1);
									ArrayList<Stereotype> array = new ArrayList<Stereotype>(getProcessGraphDocking().getPaintManager().findRelationDestiny(stereotypeEvaluate));
									if( array.size() > 0){
										for (Stereotype stereotypeContent : array) {
											int xInicial = stereotypeContent.getInicialPoint().x + stereotypeContent.getWidth()/2;
											int yInicial = stereotypeContent.getInicialPoint().y + stereotypeContent.getHeigth()/2;
											int xFinal = stereotypeEvaluate.getInicialPoint().x + stereotypeEvaluate.getWidth()/2;
											int yFinal = stereotypeEvaluate.getInicialPoint().y + stereotypeEvaluate.getHeigth()/2;
											Point point1 = new Point(xInicial, yInicial);
											Point point2 = new Point(xFinal, yFinal);
											Relation relation = getProcessGraphDocking().getPaintManager().calculatePointsRelation(point1, point2,stereotypeContent,stereotypeEvaluate);
											stereotypeContent.updateRelation(stereotypeEvaluate, relation);
										}
									}
									if(stereotypeEvaluate.getRelatedStereotypes().size() > 0){
										int xInicial = stereotypeEvaluate.getInicialPoint().x + stereotypeEvaluate.getWidth()/2;
										int yInicial = stereotypeEvaluate.getInicialPoint().y + stereotypeEvaluate.getHeigth()/2;
										Point point1 = new Point(xInicial, yInicial);
										for (Relation relation : stereotypeEvaluate.getRelatedStereotypes()) {
											int xFinal = relation.getStereotype().getInicialPoint().x + relation.getStereotype().getWidth()/2;
											int yFinal = relation.getStereotype().getInicialPoint().y + relation.getStereotype().getHeigth()/2;
											Point point2 = new Point(xFinal, yFinal);
											Relation relationEvaluate = getProcessGraphDocking().getPaintManager().calculatePointsRelation(point1, point2,stereotypeEvaluate,relation.getStereotype());
											stereotypeEvaluate.updateRelation(relation.getStereotype(), relationEvaluate);
										}
									}

									break;
								}
							}
						}
						arrayRelation.clear();//hasta aqui

						Rectangle rectangleselection = diagramActive.getBoundsRectOfList(listSelection);
						diagramActive.setGroupSelectionToStereotypes(rectangleselection);
						diagramActive.setSaveStatus(true);
						/**
						 * Actualizar tabla de propiedades
						 */
						getProcessGraphDocking().getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToDiagram(diagramActive));
						getProcessGraphDocking().getTreeActiveDiagrams().getTreeActiveDiagrams().clearSelection();
					}
					if(isStereotypeReadyToResize() && mouseDragged /*&& !relationSelected*/){//pintar cuando suelte de agrandar

						Stereotype undoStereotype = new Stereotype(stereotypeLastSelected);
						undoStereotype.setSelected(true);
						UndoAction action = new UndoAction("edit");
						action.getArrayActions().add(undoStereotype);
						if(diagramActive.getListRedo().size() > 0){
							diagramActive.getListRedo().clear();
							getProcessGraphDocking().getButtonRedo().setEnabled(false);
							getProcessGraphDocking().getMenuItemRedo().setEnabled(false);
							getMenuItemRedo().setEnabled(false);
						}
						diagramActive.getListUndo().add(action);

						Rectangle rectangleRest = getProcessGraphDocking().getPaintManager().getResultResizeRect();
						if(rectangleRest != null){
							stereotypeLastSelected.setInicialPoint(new Point(rectangleRest.x,rectangleRest.y));
							stereotypeLastSelected.setFinalPoint(new Point(rectangleRest.x + rectangleRest.width,rectangleRest.y + rectangleRest.height));
							stereotypeLastSelected.setWidth(rectangleRest.width);
							stereotypeLastSelected.setHeigth(rectangleRest.height);

							//nuevo
							Stereotype stereotypeEvaluate = stereotypeLastSelected;
							ArrayList<Stereotype> array = new ArrayList<Stereotype>(getProcessGraphDocking().getPaintManager().findRelationDestiny(stereotypeEvaluate));
							if( array.size() > 0){
								for (Stereotype stereotypeContent : array) {
									int xInicial = stereotypeContent.getInicialPoint().x + stereotypeContent.getWidth()/2;
									int yInicial = stereotypeContent.getInicialPoint().y + stereotypeContent.getHeigth()/2;
									int xFinal = stereotypeEvaluate.getInicialPoint().x + stereotypeEvaluate.getWidth()/2;
									int yFinal = stereotypeEvaluate.getInicialPoint().y + stereotypeEvaluate.getHeigth()/2;
									Point point1 = new Point(xInicial, yInicial);
									Point point2 = new Point(xFinal, yFinal);
									Relation relation = getProcessGraphDocking().getPaintManager().calculatePointsRelation(point1, point2,stereotypeContent,stereotypeEvaluate);
									stereotypeContent.updateRelation(stereotypeEvaluate, relation);
								}
							}
							if(stereotypeEvaluate.getRelatedStereotypes().size() > 0){
								int xInicial = stereotypeEvaluate.getInicialPoint().x + stereotypeEvaluate.getWidth()/2;
								int yInicial = stereotypeEvaluate.getInicialPoint().y + stereotypeEvaluate.getHeigth()/2;
								Point point1 = new Point(xInicial, yInicial);
								for (Relation relation : stereotypeEvaluate.getRelatedStereotypes()) {
									int xFinal = relation.getStereotype().getInicialPoint().x + relation.getStereotype().getWidth()/2;
									int yFinal = relation.getStereotype().getInicialPoint().y + relation.getStereotype().getHeigth()/2;
									Point point2 = new Point(xFinal, yFinal);
									Relation relationEvaluate = getProcessGraphDocking().getPaintManager().calculatePointsRelation(point1, point2,stereotypeEvaluate,relation.getStereotype());
									stereotypeEvaluate.updateRelation(relation.getStereotype(), relationEvaluate);
								}
							}
						}

						/**
						 * Actualizar tabla de propiedades
						 */
						if(diagramActive.getSelectedStereotype() != null){
							getProcessGraphDocking().getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToStereotype(diagramActive.getSelectedStereotype()));
							if(diagramActive.getSelectedStereotype().getType() != "Nota")
								getProcessGraphDocking().getTableProperties().getComboBoxProcess().getModel().setSelectedItem(diagramActive.getSelectedStereotype().getName());
							getProcessGraphDocking().getTreeActiveDiagrams().setSelectionToRow(diagramActive.getSelectedStereotype());
						}
						diagramActive.setSaveStatus(true);
					}
					if(isRelationReadyToResize() && mouseDragged /*&& !stereotypeReadyToMove && !stereotypeReadyToResize && !someStereotypeReadyToMove*/){
						if(getProcessGraphDocking().getPaintManager().getArrayPointsToInsert().size() > 0){
							getProcessGraphDocking().getPaintManager().setEndResized(getRelationSelect(),getProcessGraphDocking().getPaintManager().getArrayPointsToInsert().get(0), getProcessGraphDocking().getPaintManager().getArrayPointsToInsert().get(1), getPointMoved());
						}
						diagramActive.setSaveStatus(true);
					}
					if(isSwinLineReadyToResize() && mouseDragged && !stereotypeReadyToMove && !stereotypeReadyToResize && !someStereotypeReadyToMove
							&& !relationReadyToResize ){
						if(pointFinal.x > pointInicial.x){
							int width = diagramActive.getSwinLine().getArrayWidths().get(posOfSwinLineSelected) + pointFinal.x - pointInicial.x;
							diagramActive.getSwinLine().getArrayWidths().remove(posOfSwinLineSelected);
							diagramActive.getSwinLine().getArrayWidths().add(posOfSwinLineSelected, width);
						}
						if(pointFinal.x < pointInicial.x){
							int width = diagramActive.getSwinLine().getArrayWidths().get(posOfSwinLineSelected) - (pointInicial.x - pointFinal.x);
							diagramActive.getSwinLine().getArrayWidths().remove(posOfSwinLineSelected);
							diagramActive.getSwinLine().getArrayWidths().add(posOfSwinLineSelected, width);
						}
						diagramActive.setSaveStatus(true);
					}

					stereotype = null;
					pointInicial = null;
					pointFinal = null;
					pointMoved = null;

					leftClickPressed = false;
					rigthClickPressed = false;
					mouseDragged = false;
					stereotypeReadyToMove = false;
					someStereotypeReadyToMove = false;
					stereotypeReadyToResize = false;
					relationReadyToResize = false;
					swinLineReadyToResize = false;
					swinLineToWillResize = false;
					posOfSwinLineSelected = -1;
					getProcessGraphDocking().getPaintManager().setResultResizeRect(null);

					xDifference = 0;
					yDifference = 0;

					stereotypeSelect = null;
					//pointStereotypeToScroll = null;
					stereotypeLastSelected = null;
					getProcessGraphDocking().getPaintManager().getArrayPointsToInsert().clear();
					stereotypeInicial = null;
					//relationSelect = null;
					listSelection.clear();
					listDiference.clear();


					getProcessGraphDocking().setStateToggleButtonCursor(true);//harvet
					getProcessGraphDocking().setButtonPressed(""); //new
					setRelationNote(false);
					setTransicion(false);
					setDrawline(false);
					getPanelWorkAreaBack().repaint();
					//countFont = 0;
				}
			});
			panelWorkArea.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {   
				public void mouseDragged(java.awt.event.MouseEvent e) {

					repaint();
				}
			});
		}
		getProcessGraphDocking().getPaintManager().setSizeDiagramOverview(new Rectangle(getProcessGraphDocking().getNavigator().getSize()),getProcessGraphDocking().getNavigator().getPanelOverView());
		return panelWorkArea;
	}
	/**
	 * 
	 * @param Arg0
	 * @param who Actividades  Enlaces  Estados  Misc
	 */
	public void addButtomToToolBar(Component Arg0, String who) {
		if(who == "Actividades")
			toolBarActivities.add(Arg0);
		if(who == "Enlaces")
			toolBarJoins.add(Arg0);
		if(who == "Estados")
			toolBarStates.add(Arg0);
		if(who == "Misc")
			toolBarMisc.add(Arg0);
	}

	public void removeButtomFromToolBar(Component Arg0, String who) {
		if(who == "Actividades")
			toolBarActivities.remove(Arg0);
		if(who == "Enlaces")
			toolBarJoins.remove(Arg0);
		if(who == "Estados")
			toolBarStates.remove(Arg0);
		if(who == "Misc")
			toolBarMisc.remove(Arg0);
	}

	public void addButtomsToDiagram(String diagramType){
		removeAllButtomsFromToolsBars();
		if(diagramType == "MNC"){
			addButtomToToolBar(getProcessGraphDocking().getToggleButtonCursor(),"Actividades");
			addButtomToToolBar(getProcessGraphDocking().getButtonPaintMacroProc(),"Actividades");
			//addButtomToToolBar(getProcessGraphDocking().getButtonPaintProceso(),"Actividades");
			//addButtomToToolBar(getProcessGraphDocking().getButtonPaintSubProc(),"Actividades");
			addButtomToToolBar(getProcessGraphDocking().getButtonPaintProvee(),"Actividades");
			addButtomToToolBar(getProcessGraphDocking().getButtonPaintClient(),"Actividades");
			addButtomToToolBar(getProcessGraphDocking().getButtonPaintNote(),"Misc");
			addButtomToToolBar(getProcessGraphDocking().getButtonPaintAnclarNote(),"Enlaces");

			//addButtomToToolBar(getProcessGraphDocking().getButtonPaintLazo(),"Enlaces");

		}else if (diagramType == "RGS"){
			addButtomToToolBar(getProcessGraphDocking().getToggleButtonCursor(),"Actividades");
			//addButtomToToolBar(getProcessGraphDocking().getButtonPaintMacroProc(),"Actividades");
			addButtomToToolBar(getProcessGraphDocking().getButtonPaintProceso(),"Actividades");
			//addButtomToToolBar(getProcessGraphDocking().getButtonPaintSubProc(),"Actividades");
			addButtomToToolBar(getProcessGraphDocking().getButtonPaintActServ(),"Actividades");
			addButtomToToolBar(getProcessGraphDocking().getButtonPaintActManual(),"Actividades");
			//*addButtomToToolBar(getProcessGraphDocking().getButtonPaintDB(),"Misc");
			addButtomToToolBar(getProcessGraphDocking().getButtonPaintDesicion(),"Misc");
			addButtomToToolBar(getProcessGraphDocking().getButtonPaintNote(),"Misc");
			addButtomToToolBar(getProcessGraphDocking().getButtonPaintTransition(),"Enlaces");
			addButtomToToolBar(getProcessGraphDocking().getButtonPaintAnclarNote(),"Enlaces");
			addButtomToToolBar(getProcessGraphDocking().getButtonPaintInitialState(),"Estados");
			addButtomToToolBar(getProcessGraphDocking().getButtonPaintInitialStateMess(),"Estados");
			addButtomToToolBar(getProcessGraphDocking().getButtonPanitInitialStateTime(),"Estados");
			addButtomToToolBar(getProcessGraphDocking().getButtonPaintFinalState(),"Estados");
		}else if (diagramType == "RGG"){

		}
	}
	public void removeAllButtomsFromToolsBars(){
		removeButtomFromToolBar(getProcessGraphDocking().getToggleButtonCursor(),"Actividades");
		removeButtomFromToolBar(getProcessGraphDocking().getButtonPaintMacroProc(),"Actividades");
		removeButtomFromToolBar(getProcessGraphDocking().getButtonPaintProceso(),"Actividades");
		removeButtomFromToolBar(getProcessGraphDocking().getButtonPaintSubProc(),"Actividades");
		removeButtomFromToolBar(getProcessGraphDocking().getButtonPaintActServ(),"Actividades");
		removeButtomFromToolBar(getProcessGraphDocking().getButtonPaintActManual(),"Actividades");
		removeButtomFromToolBar(getProcessGraphDocking().getButtonPaintClient(),"Actividades");
		removeButtomFromToolBar(getProcessGraphDocking().getButtonPaintProvee(),"Actividades");
		removeButtomFromToolBar(getProcessGraphDocking().getButtonPaintDB(),"Misc");
		removeButtomFromToolBar(getProcessGraphDocking().getButtonPaintDesicion(),"Misc");
		removeButtomFromToolBar(getProcessGraphDocking().getButtonPaintNote(),"Misc");
		removeButtomFromToolBar(getProcessGraphDocking().getButtonPaintTransition(),"Enlaces");
		removeButtomFromToolBar(getProcessGraphDocking().getButtonPaintAnclarNote(),"Enlaces");
		removeButtomFromToolBar(getProcessGraphDocking().getButtonPaintLazo(),"Enlaces");
		removeButtomFromToolBar(getProcessGraphDocking().getButtonPaintInitialState(),"Estados");
		removeButtomFromToolBar(getProcessGraphDocking().getButtonPaintInitialStateMess(),"Estados");
		removeButtomFromToolBar(getProcessGraphDocking().getButtonPanitInitialStateTime(),"Estados");
		removeButtomFromToolBar(getProcessGraphDocking().getButtonPaintFinalState(),"Estados");
	}

	public ProcessGraphDocking getProcessGraphDocking() {
		if(processGraphDocking == null){
			StatusBar sb = new StatusBar();//cambio para bien
			processGraphDocking = new ProcessGraphDocking(sb);//esto esta bien porque nunca se va a ejecutar
			processGraphDocking.setTitle("TabbedPaneWorkArea");
		}
		return processGraphDocking;
	}

	public void setSelectedPosicion(int pos){
		tabbedPane.setSelectedIndex(pos);
	}

	public JTabbedPane getTabbedPane() {
		if(tabbedPane == null){
			tabbedPane = new JTabbedPane();
			tabbedPane.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					if(tabbedPane.getSelectedIndex() != 0){
						if(tabbedPane.getSelectedIndex() < getProcessGraphDocking().getTabSelect()){	
							int posTab = tabbedPane.getSelectedIndex();
							String titulo = tabbedPane.getTitleAt(posTab);//tengo el titulo
							System.out.println(posTab +"    "+ titulo);
							tabbedPane.insertTab(titulo, null, getPanelWorkAreaBack(),null /*titulo*/, posTab);//
							tabbedPane.setSelectedIndex(posTab);
							getProcessGraphDocking().setTabSelect(tabbedPane.getSelectedIndex());//aqui tengo el numero de la tab seleccionada
						}
						if(tabbedPane.getSelectedIndex() > getProcessGraphDocking().getTabSelect()){	
							int posTab = tabbedPane.getSelectedIndex();
							String titulo = tabbedPane.getTitleAt(posTab);//tengo el titulo
							System.out.println(posTab +"    "+ titulo);
							tabbedPane.insertTab(titulo, null, getPanelWorkAreaBack(), null/*titulo*/, posTab + 1);//
							tabbedPane.setSelectedIndex(posTab);
							getProcessGraphDocking().setTabSelect(tabbedPane.getSelectedIndex());//aqui tengo el numero de la tab seleccionada
						}
						/**
						 * actualizando tabs
						 */
						for(int i =0;i<tabbedPane.getTabCount();i++){
							tabbedPane.setTitleAt(i, getProcessGraphDocking().getTabTitle(i));
							tabbedPane.setToolTipTextAt(i, getProcessGraphDocking().getTabTitle(i));//tip
						}
						/**
						 * actualizando tabs
						 */

						/**
						 * Cambiando la tab seleccionada
						 */
						String tabselect = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
						getProcessGraphDocking().getPaintManager().unActiveAllDiagrams();
						Diagram diagramNewActive = getProcessGraphDocking().getPaintManager().getDiagramByName(tabselect);
						diagramNewActive.setSelected(true);
						Double zoomFactor = diagramNewActive.getZoomFactor();
						getPanelWorkArea().setPreferredSize(new Dimension(new Double(diagramNewActive.getDiagramSize().getWidth()*zoomFactor).intValue(),new Double(diagramNewActive.getDiagramSize().getHeight()*zoomFactor).intValue()));
						getPanelWorkArea().setBounds(new Rectangle(new Double(diagramNewActive.getDiagramSize().getWidth()*zoomFactor).intValue(),new Double(diagramNewActive.getDiagramSize().getHeight()*zoomFactor).intValue()));
						getPanelWorkArea().revalidate();
						getProcessGraphDocking().getNavigator().repaint();
						repaint();
						Diagram diagramActive = getProcessGraphDocking().getPaintManager().getActiveDiagram();
						setColorToPanelWorkArea();
						/**
						 * Actualizando tree Active diagrams
						 */
						if(diagramActive != null){// por si da click en el panel welcome
							getProcessGraphDocking().getTreeActiveDiagrams().setRootVisible(true);
							getProcessGraphDocking().getTreeActiveDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeActiveDiagrams(diagramActive));
							/**
							 * Actualizando Combo box process
							 */
							getProcessGraphDocking().getTableProperties().setDefaultComboBoxModelProcess(FactoryModel.factory.getModelForComboboxProcess(diagramActive));
							/**
							 * Actualizando TableProperties 
							 */
							if(diagramActive.getSelectedStereotype() != null){
								getProcessGraphDocking().getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToStereotype(diagramActive.getSelectedStereotype()));
								if(diagramActive.getSelectedStereotype().getType() != "Nota")
									getProcessGraphDocking().getTableProperties().getComboBoxProcess().getModel().setSelectedItem(diagramActive.getSelectedStereotype().getName());
								getProcessGraphDocking().getTreeActiveDiagrams().setSelectionToRow(diagramActive.getSelectedStereotype());
							}else{
								getProcessGraphDocking().getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToDiagram(diagramActive));
							}
							getProcessGraphDocking().getComboBoxSelected().setVisible(false);
							getProcessGraphDocking().getScrollPaneDescription().setVisible(false);
							getProcessGraphDocking().getComboBoxVisible().setVisible(false);
							getProcessGraphDocking().getComboBoxType().setVisible(false);
							/**
							 * Actualizando Undo Redo Buttons
							 */
							if(diagramActive.getListRedo().size() > 0){
								getProcessGraphDocking().getButtonRedo().setEnabled(true);
								getProcessGraphDocking().getMenuItemRedo().setEnabled(true);
								getMenuItemRedo().setEnabled(true);
							}
							else{
								getProcessGraphDocking().getButtonRedo().setEnabled(false);
								getProcessGraphDocking().getMenuItemRedo().setEnabled(false);
								getMenuItemRedo().setEnabled(false);
							}

							if(diagramActive.getListUndo().size() > 0){
								getProcessGraphDocking().getButtonUndo().setEnabled(true);
								getProcessGraphDocking().getMenuItemUndo().setEnabled(true);
								getMenuItemUndo().setEnabled(true);
							}else{
								getProcessGraphDocking().getButtonUndo().setEnabled(false);
								getProcessGraphDocking().getMenuItemUndo().setEnabled(false);
								getMenuItemUndo().setEnabled(false);
							}
							addButtomsToDiagram(diagramActive.getType());
							flagStereotype = null;
						}	
						if(e.getButton() == 3){
							Point mousePosition = tabbedPane.getMousePosition();
							getProcessGraphDocking().getTabbedPaneWorkArea().getPopupMenuTabbedPane().show(tabbedPane, mousePosition.x, mousePosition.y);
						}
					}else if(tabbedPane.getSelectedIndex() == 0){//estoy sobre el panel bienvenido
						getProcessGraphDocking().getTreeActiveDiagrams().setRootVisible(false);
						getProcessGraphDocking().getTreeActiveDiagrams().setDefaultTreeModelDiagrams(getProcessGraphDocking().getTreeActiveDiagrams().getDefaultTreeModelDiagrams());
						getProcessGraphDocking().getTableProperties().setDefaultComboBoxModelProcess(getProcessGraphDocking().getTableProperties().getDefaultComboBoxModelProcess());
						getProcessGraphDocking().getTableProperties().getTableProperties().setModel(getProcessGraphDocking().getTableProperties().getDefaultTableModelProperties());
						getProcessGraphDocking().getPaintManager().unActiveAllDiagrams();
						repaint();
					}
					getProcessGraphDocking().getJTextAreaNote().setVisible(false);
					getProcessGraphDocking().getJTextFieldName().setVisible(false);
					getProcessGraphDocking().setButtonPressed("");
				}
			});
		}
		return tabbedPane;
	}
	/**
	 * This method initializes panelStatusBar	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelStatusBar() {
		if (panelStatusBar == null) {
			labelPosition = new JLabel();
			labelPosition.setText("Posicion : 0.0");
			panelStatusBar = new JPanel();
			panelStatusBar.setLayout(new BorderLayout());
			panelStatusBar.setPreferredSize(new Dimension(1, 14));
			panelStatusBar.add(labelPosition, BorderLayout.WEST);
			panelStatusBar.add(getProgressBarWorking(), BorderLayout.EAST);
		}
		return panelStatusBar;
	}
	/**
	 * This method initializes progressBarWorking	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	public JProgressBar getProgressBarWorking() {
		if (progressBarWorking == null) {
			progressBarWorking = new JProgressBar();
			progressBarWorking.setMinimum(0);
			progressBarWorking.setMaximum(100);
		}
		return progressBarWorking;
	}
	public boolean isRigthClickPressed() {
		return rigthClickPressed;
	}
	public void setRigthClickPressed(boolean rigthClickPressed) {
		this.rigthClickPressed = rigthClickPressed;
	}
	public Point getPointFinal() {
		return pointFinal;
	}
	public void setPointFinal(Point pointFinal) {
		this.pointFinal = pointFinal;
	}
	public Point getPointInicial() {
		return pointInicial;
	}
	public void setPointInicial(Point pointInicial) {
		this.pointInicial = pointInicial;
	}
	public Point getPointMoved() {
		return pointMoved;
	}
	public void setPointMoved(Point pointMoved) {
		this.pointMoved = pointMoved;
	}
	public boolean isStereotypeReadyToMove() {
		return stereotypeReadyToMove;
	}
	public void setStereotypeReadyToMove(boolean stereotypeReadyToMove) {
		this.stereotypeReadyToMove = stereotypeReadyToMove;
	}
	public Stereotype getStereotypeSelect() {
		return stereotypeSelect;
	}
	public int getXDifference() {
		return xDifference;
	}
	public int getYDifference() {
		return yDifference;
	}
	public ArrayList<Stereotype> getListSelection() {
		return listSelection;
	}
	public void setListSelection(ArrayList<Stereotype> listSelection) {
		this.listSelection = listSelection;
	}
	public boolean isSomeStereotypeReadyToMove() {
		return someStereotypeReadyToMove;
	}
	public boolean isStereotypeReadyToWillResize() {
		return stereotypeReadyToWillResize;
	}
	public boolean isStereotypeReadyToResize() {
		return stereotypeReadyToResize;
	}
	public Stereotype getStereotypeLastSelected() {
		return stereotypeLastSelected;
	}
	/*public Point getPointStereotypeToScroll() {
	return pointStereotypeToScroll;
    }*/
	public boolean isMouseDragged() {
		return mouseDragged;
	}
	public ArrayList<Point> getListDiference() {
		return listDiference;
	}
	public int getPosOfRectToWillResize() {
		return posOfRectToWillResize;
	}
	public JLabel getLabelPosition() {
		return labelPosition;
	}
	public void setStereotypeReadyToWillResize(boolean stereotypeReadyToWillResize) {
		this.stereotypeReadyToWillResize = stereotypeReadyToWillResize;
	}
	public void setStereotypeLastSelected(Stereotype stereotypeLastSelected) {
		this.stereotypeLastSelected = stereotypeLastSelected;
	}
	public void setPosOfRectToWillResize(int posOfRectToWillResize) {
		this.posOfRectToWillResize = posOfRectToWillResize;
	}
	public void setStereotypeReadyToResize(boolean stereotypeReadyToResize) {
		this.stereotypeReadyToResize = stereotypeReadyToResize;
	}
	public void setMouseDragged(boolean mouseDragged) {
		this.mouseDragged = mouseDragged;
	}
	public boolean isLeftClickPressed() {
		return leftClickPressed;
	}
	public int getXBigDifference() {
		return xBigDifference;
	}
	public int getYBigDifference() {
		return yBigDifference;
	}
	/**
	 * metodos para el calculo de la diferencia de la mult seleccion
	 */
	public int calculateBigXDifference(Point pointSelect){
		int xDiff = -10000;
		Diagram diagram = getProcessGraphDocking().getPaintManager().getActiveDiagram();
		for(int i =0; i < diagram.getPictureList().size(); i++){
			if(diagram.getPictureList().get(i).isSelected()){
				if(pointSelect.x - diagram.getInicialXOfSelection() > xDiff) {
					xDiff = pointSelect.x - diagram.getInicialXOfSelection();
				}
			}
		}
		return xDiff;
	}

	public int calculateBigYDifference(Point pointSelect){
		int yDiff = -10000;
		Diagram diagram = getProcessGraphDocking().getPaintManager().getActiveDiagram();
		for(int i =0; i < diagram.getPictureList().size(); i++){
			if(diagram.getPictureList().get(i).isSelected()){
				if(pointSelect.y - diagram.getInicialYOfSelection() > yDiff) {
					yDiff = pointSelect.y - diagram.getInicialYOfSelection();
				}
			}
		}
		return yDiff;
	}
	/**
	 * metodos para el calculo de la diferencia de la mult seleccion
	 */	  


	public void setColorToPanelWorkArea(){
		panelWorkArea.setBackground(getProcessGraphDocking().getPaintManager().getActiveDiagram().getBackground());
	}

	public int getCountClick() {
		return countClick;
	}
	public void setCountClick(int countClick, boolean valid) {
		this.countClick = countClick;
		this.clickValid = valid;
	}

	public Stereotype getFlagStereotype() {
		return flagStereotype;
	}

	public void setFlagStereotype(Stereotype flagStereotype) {
		this.flagStereotype = flagStereotype;
	}
	public boolean isRelationNote() {
		return relationNote;
	}
	public void setRelationNote(boolean relationNote) {
		this.relationNote = relationNote;
	}
	public boolean isDrawline() {
		return drawline;
	}
	public void setDrawline(boolean drawline) {
		this.drawline = drawline;
	}
	public Stereotype getStereotypeInicial() {
		return stereotypeInicial;
	}
	public void setStereotypeInicial(Stereotype stereotypeInicial) {
		this.stereotypeInicial = stereotypeInicial;
	}
	public Relation getRelationSelect() {
		return relationSelect;
	}
	public void setRelationSelect(Relation relationNoteSelect) {
		this.relationSelect = relationNoteSelect;
	}
	public boolean isRelationSelected() {
		return relationSelected;
	}
	public void setRelationSelected(boolean relationSelected) {
		this.relationSelected = relationSelected;
	}
	public boolean isTransicion() {
		return transicion;
	}
	public void setTransicion(boolean transicion) {
		this.transicion = transicion;
	}
	public boolean isRelationToWillResize() {
		return relationToWillResize;
	}
	public void setRelationToWillResize(boolean relationToWillResize) {
		this.relationToWillResize = relationToWillResize;
	}
	public boolean isRelationReadyToResize() {
		return relationReadyToResize;
	}
	public void setRelationReadyToResize(boolean relationReadyToResize) {
		this.relationReadyToResize = relationReadyToResize;
	}	

	public boolean isClickValid() {
		return clickValid;
	}
	/**
	 * This method initializes popupMenuWorkArea	
	 * 	
	 * @return javax.swing.JPopupMenu	
	 */
	public JPopupMenu getPopupMenuWorkAreaToStereotype() {
		if (popupMenuWorkAreaToStereotype == null) {
			popupMenuWorkAreaToStereotype = new JPopupMenu();
			//popupMenuWorkAreaToStereotype.setPreferredSize(new Dimension(150, 80));
			popupMenuWorkAreaToStereotype.add(getMenuItemCut());
			popupMenuWorkAreaToStereotype.add(getMenuItemCopy());
			popupMenuWorkAreaToStereotype.add(getMenuItemDelete());
		}
		return popupMenuWorkAreaToStereotype;
	}
	/**
	 * This method initializes menuItemDelete	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	public JMenuItem getMenuItemDelete() {
		if (menuItemDelete == null) {
			menuItemDelete = new JMenuItem();
			menuItemDelete.setText("Eliminar");
			menuItemDelete.setIcon(new ImageIcon(getClass().getResource(
			"/icons/delete4.png")));
			menuItemDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0, false));
			menuItemDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getProcessGraphDocking().getMenuItemDelete().doClick();
					getPopupMenuWorkAreaToStereotype().setVisible(false);
				}
			});
		}
		return menuItemDelete;
	}
	/**
	 * This method initializes menuItemCut	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	public JMenuItem getMenuItemCut() {
		if (menuItemCut == null) {
			menuItemCut = new JMenuItem();
			menuItemCut.setText("Cortar");
			menuItemCut.setIcon(new ImageIcon(getClass().getResource(
			"/icons/cut1.png")));
			menuItemCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK, false));
			menuItemCut.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getProcessGraphDocking().getMenuItemCut().doClick();
					getPopupMenuWorkAreaToStereotype().setVisible(false);
				}
			});
		}
		return menuItemCut;
	}
	/**
	 * This method initializes menuItemCopy	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMenuItemCopy() {
		if (menuItemCopy == null) {
			menuItemCopy = new JMenuItem();
			menuItemCopy.setText("Copiar");
			menuItemCopy.setIcon(new ImageIcon(getClass().getResource(
			"/icons/copy1.png")));
			menuItemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK, false));
			menuItemCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getProcessGraphDocking().getMenuItemCopy().doClick();
					getPopupMenuWorkAreaToStereotype().setVisible(false);
				}
			});
		}
		return menuItemCopy;
	}
	/**
	 * This method initializes menuItemPaste	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	public JMenuItem getMenuItemPaste() {
		if (menuItemPaste == null) {
			menuItemPaste = new JMenuItem();
			menuItemPaste.setText("Pegar");
			menuItemPaste.setIcon(new ImageIcon(getClass().getResource(
			"/icons/paste1.png")));
			menuItemPaste.setEnabled(false);
			menuItemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK, false));
			menuItemPaste.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getProcessGraphDocking().getMenuItemPaste().doClick();
					getPopupMenuWorkAreaToDiagram().setVisible(false);
				}
			});
		}
		return menuItemPaste;
	}
	/**
	 * This method initializes popupMenuWorkAreaToDiagram	
	 * 	
	 * @return javax.swing.JPopupMenu	
	 */
	public JPopupMenu getPopupMenuWorkAreaToDiagram() {
		if (popupMenuWorkAreaToDiagram == null) {
			popupMenuWorkAreaToDiagram = new JPopupMenu();
			//popupMenuWorkAreaToDiagram.setPreferredSize(new Dimension(200, 200));
			popupMenuWorkAreaToDiagram.add(getMenuItemZoomIn());
			popupMenuWorkAreaToDiagram.add(getMenuItemZoomOut());
			popupMenuWorkAreaToDiagram.add(getMenuItemZoomReset());
			popupMenuWorkAreaToDiagram.addSeparator();
			popupMenuWorkAreaToDiagram.add(getMenuItemPaste());
			popupMenuWorkAreaToDiagram.addSeparator();
			popupMenuWorkAreaToDiagram.add(getMenuItemUndo());
			popupMenuWorkAreaToDiagram.add(getMenuItemRefresh());
			popupMenuWorkAreaToDiagram.add(getMenuItemRedo());
			popupMenuWorkAreaToDiagram.addSeparator();
			popupMenuWorkAreaToDiagram.add(getMenuItemSelectAll());
		}
		return popupMenuWorkAreaToDiagram;
	}
	/**
	 * This method initializes menuItemZoomIn	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMenuItemZoomIn() {
		if (menuItemZoomIn == null) {
			menuItemZoomIn = new JMenuItem();
			menuItemZoomIn.setText("Zoom +");
			menuItemZoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, Event.CTRL_MASK, false));
			menuItemZoomIn.setIcon(new ImageIcon(getClass().getResource(
			"/icons/zoom-in.png")));
			menuItemZoomIn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getProcessGraphDocking().getMenuItemZoomIn().doClick();
					getPopupMenuWorkAreaToDiagram().setVisible(false);
				}
			});
		}
		return menuItemZoomIn;
	}
	/**
	 * This method initializes menuItemZoomOut	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMenuItemZoomOut() {
		if (menuItemZoomOut == null) {
			menuItemZoomOut = new JMenuItem();
			menuItemZoomOut.setText("Zoom -");
			menuItemZoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, Event.CTRL_MASK, false));
			menuItemZoomOut.setIcon(new ImageIcon(getClass().getResource(
			"/icons/zoom-out.png")));
			menuItemZoomOut.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getProcessGraphDocking().getMenuItemZoomOut().doClick();
					getPopupMenuWorkAreaToDiagram().setVisible(false);
				}
			});
		}
		return menuItemZoomOut;
	}
	/**
	 * This method initializes menuItemZoomReset	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMenuItemZoomReset() {
		if (menuItemZoomReset == null) {
			menuItemZoomReset = new JMenuItem();
			menuItemZoomReset.setText("Zoom 1:1");
			menuItemZoomReset.setIcon(new ImageIcon(getClass().getResource("/icons/gif/resetZoom.png")));
			menuItemZoomReset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DIVIDE, Event.CTRL_MASK, false));
			menuItemZoomReset.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getProcessGraphDocking().getMenuItemResetZoom().doClick();
					getPopupMenuWorkAreaToDiagram().setVisible(false);
				}
			});
		}
		return menuItemZoomReset;
	}
	/**
	 * This method initializes menuItemUndo	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	public JMenuItem getMenuItemUndo() {
		if (menuItemUndo == null) {
			menuItemUndo = new JMenuItem();
			menuItemUndo.setText("Deshacer");
			menuItemUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK, false));
			menuItemUndo.setEnabled(false);
			menuItemUndo.setIcon(new ImageIcon(getClass().getResource(
			"/icons/undo.png")));
			menuItemUndo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getProcessGraphDocking().getMenuItemUndo().doClick();
					getPopupMenuWorkAreaToDiagram().setVisible(false);
				}
			});
		}
		return menuItemUndo;
	}
	/**
	 * This method initializes menuItemRefresh	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMenuItemRefresh() {
		if (menuItemRefresh == null) {
			menuItemRefresh = new JMenuItem();
			menuItemRefresh.setText("Refrescar");
			menuItemRefresh.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0, false));
			menuItemRefresh.setIcon(new ImageIcon(getClass().getResource(
			"/icons/nav-reload.png")));
			menuItemRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					repaint();
				}
			});
		}
		return menuItemRefresh;
	}
	/**
	 * This method initializes menuItemRedo	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	public JMenuItem getMenuItemRedo() {
		if (menuItemRedo == null) {
			menuItemRedo = new JMenuItem();
			menuItemRedo.setText("Rehacer");
			menuItemRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK, false));
			menuItemRedo.setEnabled(false);
			menuItemRedo.setIcon(new ImageIcon(getClass().getResource(
			"/icons/nav-redo.png")));
			menuItemRedo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getProcessGraphDocking().getMenuItemRedo().doClick();
					getPopupMenuWorkAreaToDiagram().setVisible(false);
				}
			});
		}
		return menuItemRedo;
	}
	/**
	 * This method initializes menuItemSelectAll	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	public JMenuItem getMenuItemSelectAll() {
		if (menuItemSelectAll == null) {
			menuItemSelectAll = new JMenuItem();
			menuItemSelectAll.setText("Seleccionar todo");
			menuItemSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK, false));
			menuItemSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getProcessGraphDocking().getPaintManager().getActiveDiagram().selectAllStereotype();
					repaint();
				}
			});
		}
		return menuItemSelectAll;
	}
	/**
	 * This method initializes popupMenuTabbedPane	
	 * 	
	 * @return javax.swing.JPopupMenu	
	 */
	public JPopupMenu getPopupMenuTabbedPane() {
		if (popupMenuTabbedPane == null) {
			popupMenuTabbedPane = new JPopupMenu();
			popupMenuTabbedPane.add(getMenuItemCloseMe());
			popupMenuTabbedPane.add(getMenuItemCloseOthers());
			popupMenuTabbedPane.add(getMenuItemCloseAll());
		}
		return popupMenuTabbedPane;
	}
	/**
	 * This method initializes menuItemCloseMe	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	public JMenuItem getMenuItemCloseMe() {
		if (menuItemCloseMe == null) {
			menuItemCloseMe = new JMenuItem();
			menuItemCloseMe.setText("Cerrar");
			menuItemCloseMe.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String tabTitle = getTabbedPane().getTitleAt(getTabbedPane().getSelectedIndex());
					getProcessGraphDocking().deleteTabTitle(tabTitle);
					//getProcessGraphDocking().getPaintManager().deleteDiagram(tabTitle);
					getProcessGraphDocking().getPaintManager().getDiagramByName(tabTitle).setSelected(false);
					getTabbedPane().remove(getTabbedPane().getSelectedIndex());
					if(getTabbedPane().getTabCount() == 1){//solo queda la tab welcome
						getProcessGraphDocking().getTreeActiveDiagrams().setRootVisible(false);
						getProcessGraphDocking().getTreeActiveDiagrams().setDefaultTreeModelDiagrams(getProcessGraphDocking().getTreeActiveDiagrams().getDefaultTreeModelDiagrams());
						getProcessGraphDocking().getTableProperties().setDefaultComboBoxModelProcess(getProcessGraphDocking().getTableProperties().getDefaultComboBoxModelProcess());
						getProcessGraphDocking().getTableProperties().getTableProperties().setModel(getProcessGraphDocking().getTableProperties().getDefaultTableModelProperties());

						//getProcessGraphDocking().getTreeDiagrams().setRootVisible(false);
						//getProcessGraphDocking().getTreeDiagrams().setDefaultTreeModelDiagrams(getProcessGraphDocking().getTreeDiagrams().getDefaultTreeModelDiagrams());

						getProcessGraphDocking().getPaintManager().unActiveAllDiagrams();
						repaint();
					}else{
						String newTabTitle = getTabbedPane().getTitleAt(getTabbedPane().getSelectedIndex());
						getProcessGraphDocking().getPaintManager().getDiagramByName(newTabTitle).setSelected(true);
						int posTab = getTabbedPane().getSelectedIndex();
						getTabbedPane().remove(getTabbedPane().getSelectedIndex());//eliminar porque el insertar agrega, no reemplaza
						getTabbedPane().insertTab(newTabTitle, null, getPanelWorkAreaBack(),null, posTab);//
						getTabbedPane().setSelectedIndex(posTab);
						getProcessGraphDocking().setTabSelect(getTabbedPane().getSelectedIndex());//aqui tengo el numero de la tab seleccionada
						//actualizando tabs
						for(int i =0;i<getTabbedPane().getTabCount();i++){
							getTabbedPane().setTitleAt(i, getProcessGraphDocking().getTabTitle(i));
							getTabbedPane().setToolTipTextAt(i, getProcessGraphDocking().getTabTitle(i));//tip
						}
						//getProcessGraphDocking().getTreeDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeDiagrams(getProcessGraphDocking().getPaintManager().getDiagramList()));
					}
				}
			});
		}
		return menuItemCloseMe;
	}
	/**
	 * This method initializes menuItemCloseOthers	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	public JMenuItem getMenuItemCloseOthers() {
		if (menuItemCloseOthers == null) {
			menuItemCloseOthers = new JMenuItem();
			menuItemCloseOthers.setText("Cerrar otros");
			menuItemCloseOthers.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String tabTitle = getTabbedPane().getTitleAt(getTabbedPane().getSelectedIndex());
					getProcessGraphDocking().clearTabTitle();//limpio la lista y le pongo bienvenido
					getProcessGraphDocking().addTabTitle(tabTitle);//agrego la que no quiero eliminar
					for (int i = 1; i < getTabbedPane().getTabCount(); i++) {
						if(getTabbedPane().getTitleAt(i) != tabTitle){
							getTabbedPane().remove(i);
							i = 0;
						}
					}
					getTabbedPane().setSelectedIndex(1);
					getProcessGraphDocking().setTabSelect(getTabbedPane().getSelectedIndex());
					for(int i =0;i<getTabbedPane().getTabCount();i++){
						getTabbedPane().setTitleAt(i, getProcessGraphDocking().getTabTitle(i));
						getTabbedPane().setToolTipTextAt(i, getProcessGraphDocking().getTabTitle(i));//tip
					}
				}
			});
		}
		return menuItemCloseOthers;
	}
	/**
	 * This method initializes menuItemCloseAll	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	public JMenuItem getMenuItemCloseAll() {
		if (menuItemCloseAll == null) {
			menuItemCloseAll = new JMenuItem();
			menuItemCloseAll.setText("Cerrar todos");
			menuItemCloseAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getProcessGraphDocking().clearTabTitle();
					getProcessGraphDocking().getPaintManager().unActiveAllDiagrams();
					for (int i = 1; i < getTabbedPane().getTabCount(); i++) {
						getTabbedPane().remove(i);
						i =0;
					}
					getTabbedPane().setSelectedIndex(0);//panel bienvenido
					getProcessGraphDocking().getTreeActiveDiagrams().setRootVisible(false);
					getProcessGraphDocking().getTreeActiveDiagrams().setDefaultTreeModelDiagrams(getProcessGraphDocking().getTreeActiveDiagrams().getDefaultTreeModelDiagrams());
					getProcessGraphDocking().getTableProperties().setDefaultComboBoxModelProcess(getProcessGraphDocking().getTableProperties().getDefaultComboBoxModelProcess());
					getProcessGraphDocking().getTableProperties().getTableProperties().setModel(getProcessGraphDocking().getTableProperties().getDefaultTableModelProperties());

					//getProcessGraphDocking().getTreeDiagrams().setRootVisible(false);
					//getProcessGraphDocking().getTreeDiagrams().setDefaultTreeModelDiagrams(getProcessGraphDocking().getTreeDiagrams().getDefaultTreeModelDiagrams());

					repaint();
					//actualizando tabs
					for(int i =0;i<getTabbedPane().getTabCount();i++){
						getTabbedPane().setTitleAt(i, getProcessGraphDocking().getTabTitle(i));
						getTabbedPane().setToolTipTextAt(i, getProcessGraphDocking().getTabTitle(i));//tip
					}
				}
			});
		}
		return menuItemCloseAll;
	}
	public boolean isSwinLineToWillResize() {
		return swinLineToWillResize;
	}
	public void setSwinLineToWillResize(boolean swinLineToWillResize) {
		this.swinLineToWillResize = swinLineToWillResize;
	}
	public boolean isSwinLineReadyToResize() {
		return swinLineReadyToResize;
	}
	public void setSwinLineReadyToResize(boolean swinLineReadyToResize) {
		this.swinLineReadyToResize = swinLineReadyToResize;
	}
	public int getPosOfSwinLineSelected() {
		return posOfSwinLineSelected;
	}
	public void setPosOfSwinLineSelected(int posOfSwinLineSelected) {
		this.posOfSwinLineSelected = posOfSwinLineSelected;
	}
}
