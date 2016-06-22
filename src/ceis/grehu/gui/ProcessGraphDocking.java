package ceis.grehu.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;

import ProcessGraphServer.service.DiagramaService;
import ProcessGraphServer.vo.DiagramaVO;
import ProcessGraphServer.vo.StereotypeVO;
import ceis.grehu.gui.dialog.LoadDiagram;
import ceis.grehu.gui.dialog.Printer;
import ceis.grehu.gui.paint.Diagram;
import ceis.grehu.gui.paint.FactoryModel;
import ceis.grehu.gui.paint.PaintManager;
import ceis.grehu.gui.paint.Relation;
import ceis.grehu.gui.paint.Stereotype;
import ceis.grehu.gui.splash.StatusBar;
import ceis.grehu.units.filter.JpgFilter;
import ceis.grehu.units.filter.PngFilter;
import ceis.grehu.units.undoManager.RedoAction;
import ceis.grehu.units.undoManager.UndoAction;

import com.vlsolutions.swing.docking.DockableState;
import com.vlsolutions.swing.docking.DockingConstants;
import com.vlsolutions.swing.docking.DockingDesktop;
import com.vlsolutions.swing.docking.RelativeDockablePosition;
import com.vlsolutions.swing.docking.event.DockableStateWillChangeEvent;
import com.vlsolutions.swing.docking.event.DockableStateWillChangeListener;
import com.vlsolutions.swing.toolbars.ToolBarConstraints;
import com.vlsolutions.swing.toolbars.ToolBarContainer;
import com.vlsolutions.swing.toolbars.ToolBarIO;
import com.vlsolutions.swing.toolbars.ToolBarPanel;
import com.vlsolutions.swing.toolbars.VLToolBar;

public class ProcessGraphDocking extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JButton ButtonNewDiagram = null;

	private JButton ButtonOpen = null;

	private JButton ButtonSave = null;

	private JButton ButtonSaveAll = null;

	private JButton ButtonPrint = null;

	private JButton buttonPrintPreView = null;

	private JButton ButtonCut = null;

	private JButton buttonCopy = null;

	private JButton buttonPaste = null;

	private JButton buttonDelete = null;

	private JButton buttonFind = null;

	private JButton buttonZoomIn = null;

	private JButton buttonZoomOut = null;

	private JButton buttonUndo = null;

	private JButton buttonRefresh = null;

	private JButton buttonRedo = null;

	private JMenuBar MenuBarMain = null;

	private JMenu MenuFile = null;

	public JMenuItem MenuItemNewDiagram = null;

	private JSeparator SeparatorMenuFile1 = null;

	private JMenuItem MenuItemOpenDiagram = null;

	private JSeparator SeparatorMenuFile2 = null;

	private JMenuItem MenuItemSave = null;

	private JMenuItem MenuItemSaveAll = null;

	private JSeparator SeparatorMenuFile3 = null;

	private JMenuItem MenuItemClose = null;

	private JMenuItem MenuItemCloseAll = null;

	private JSeparator SeparatorMenuFile4 = null;

	private JMenuItem MenuItemPrint = null;

	private JSeparator SeparatorMenuFile5 = null;

	private JMenuItem MenuItemExit = null;

	private JMenu MenuEdit = null;

	private JMenuItem MenuItemUndo = null;

	private JMenuItem MenuItemRedo = null;

	private JSeparator SeparatorMenuEdit1 = null;

	private JMenuItem MenuItemCut = null;

	private JMenuItem MenuItemCopy = null;

	private JMenuItem MenuItemPaste = null;

	private JMenuItem MenuItemDelete = null;

	private JSeparator SeparatorMenuEdit2 = null;

	//private JMenuItem MenuItemFind = null;	

	private JMenuItem MenuItemSelectAll = null;

	private JMenu MenuView = null;

	private JMenuItem MenuItemZoomIn = null;

	private JMenuItem MenuItemZoomOut = null;

	private JCheckBoxMenuItem CheckBoxMenuItemExplorer = null;

	private JCheckBoxMenuItem CheckBoxMenuItemActiveDiagram = null;

	private JCheckBoxMenuItem CheckBoxMenuItemProperties = null;

	private JCheckBoxMenuItem CheckBoxMenuItemNavigator = null;

	private JMenu MenuTools = null;

	private JMenuItem MenuItemProperties = null;

	private JMenu MenuHelp = null;

	private JMenuItem MenuItemAbout = null;

	private JButton buttonPaintDesicion = null;

	private JButton buttonPaintDB = null;

	private JButton buttonPaintTransition = null; // @jve:decl-index=0:visual-constraint="624,700"

	private JButton buttonPaintNote = null; // @jve:decl-index=0:visual-constraint="703,717"

	private JButton buttonPaintInitialState = null; // @jve:decl-index=0:visual-constraint="779,702"

	private JButton buttonPaintInitialStateMess = null; // @jve:decl-index=0:visual-constraint="862,720"

	private JButton buttonPanitInitialStateTime = null; // @jve:decl-index=0:visual-constraint="596,763"

	private JButton buttonPaintFinalState = null; // @jve:decl-index=0:visual-constraint="675,762"

	private JButton buttonPaintAnclarNote = null; // @jve:decl-index=0:visual-constraint="239,767"

	private JButton buttonPaintLazo = null; // @jve:decl-index=0:visual-constraint="783,779"

	private JButton buttonPaintMacroProc = null; // @jve:decl-index=0:visual-constraint="890,780"

	private JButton buttonPaintProceso = null; // @jve:decl-index=0:visual-constraint="652,829"

	private JButton buttonPaintSubProc = null; // @jve:decl-index=0:visual-constraint="753,833"

	private JButton buttonPaintActServ = null; // @jve:decl-index=0:visual-constraint="859,848"

	private JButton buttonPaintActManual = null; // @jve:decl-index=0:visual-constraint="664,905"

	private JButton buttonPaintClient = null; // @jve:decl-index=0:visual-constraint="761,913"

	private JButton buttonPaintProvee = null; // @jve:decl-index=0:visual-constraint="859,922"

	public JToggleButton toggleButtonCursor = null;

	// new2
	private String buttonPressed = ""; // Macro,Proc,SubProc,ActServ,ActManual,Cliente,Provee,EITiempo,EIMensaje
	// // @jve:decl-index=0:

	private JMenuItem MenuItemExportAs = null;

	// EInicial,EFinal,BaseDato,Decision,Linea,AnclarNota,Nota,Lazo
	private int tabSelect;

	private ArrayList<String> listTabsTitles = new ArrayList<String>();  //  @jve:decl-index=0:

	private PaintManager paintManager = null;

	//private FactoryModel factoryModel = null;

	public DockingDesktop desk = new DockingDesktop();

	/**
	 * VLToolbars
	 */
	ToolBarContainer container = ToolBarContainer.createDefaultContainer(true,
			true, true, true);

	public VLToolBar toolBarFile = null;

	public VLToolBar toolBarEdit = null;

	public VLToolBar toolBarZoom = null;

	public VLToolBar toolBarDo = null;

	/**
	 * VLToolbars
	 */
	/**
	 * Save workspace
	 */
	public File perspectiveFile = new File("perspective/perspective.xml"); // fichero
	// para
	// salvar
	// y
	// guardar
	// el
	// workspace
	// //
	// @jve:decl-index=0:

	public File defaultperspectiveFile = new File(
	"perspective/defaultPerspective.xml"); // @jve:decl-index=0:

	public File toolBarsFile = new File("perspective/toolsBars.xml");

	public File defaultToolBarsFile = new File(
	"perspective/defaultToolsBars.xml");

	/**
	 * Save workspace
	 */
	private TreeDiagrams treeDiagrams = null;

	private TreeActiveDiagrams treeActiveDiagrams = null;

	private TableProperties tableProperties = null;

	private Navigator navigator = null;

	private TabbedPaneWorkArea tabbedPaneWorkArea = null;

	private JButton buttonChosserBackGround = null;  //  @jve:decl-index=0:visual-constraint="202,830"

	private JButton buttonChooserLine = null;  //  @jve:decl-index=0:visual-constraint="251,830"

	private JComboBox comboBoxSelected = null;  //  @jve:decl-index=0:visual-constraint="63,830"

	private DefaultComboBoxModel defaultComboBoxModelSelected = null;

	private DefaultComboBoxModel defaultComboBoxModelVisible = null;

	private JComboBox comboBoxVisible = null;  //  @jve:decl-index=0:visual-constraint="61,875"

	private JComboBox comboBoxType = null;  //  @jve:decl-index=0:visual-constraint="69,913"

	private DefaultComboBoxModel defaultComboBoxModelType = null;

	private DefaultComboBoxModel defaultComboBoxModelTypeEstate = null;



	private JTextField jTextFieldName = null;

	private boolean valid = false;

	private String stringTyped = ""; 

	private boolean nonKeyTyped = true;

	private JTextArea jTextAreaNote = null;

	private Double countZoom = null;

	private boolean posibleTyped = false;

	private int countToCompareNote = 0;

	private int countToCompareName = 0;

	private String actionEdit = "";  //  @jve:decl-index=0:

	private JButton buttonResetZoom = null;

	private JMenuItem menuItemResetZoom = null;

	private JTextArea textAreaDescription = null;

	private JScrollPane scrollPaneDescription = null;

	private Point pointPressed = null;    

	private int sizeTextArea = 0;

	private int sizeFieldName = 0;

	/**
	 * This is the default constructor
	 */
	public ProcessGraphDocking(StatusBar sb) {// puedo poner in if preguntando
		// por si sb == null
		super();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		sb.showStatus("Cargando Componentes :");
		System.out.println("Cargando Componentes :");
		sb.incProgress(4);//aumenta el 10 porciento 2+4

		sb.showStatus("Cargando Componentes : TabbedPaneWorkArea");
		System.out.println("Cargando Componentes : TabbedPaneWorkArea");
		sb.incProgress(2);
		desk.addDockable(getTabbedPaneWorkArea());
		sb.incProgress(2);

		sb.showStatus("Cargando Componentes : Spliter TabbedPaneWorkArea    TreeActiveDiagrams");
		System.out.println("Cargando Componentes : Spliter TabbedPaneWorkArea    TreeActiveDiagrams");
		sb.incProgress(2);
		desk.split(getTabbedPaneWorkArea(), getTreeActiveDiagrams(),DockingConstants.SPLIT_LEFT);
		sb.incProgress(2);

		sb.showStatus("Cargando Componentes : Spliter TreeActiveDiagrams    TableProperties");
		System.out.println("Cargando Componentes : Spliter TreeActiveDiagrams    TableProperties");
		sb.incProgress(2);
		desk.split(getTreeActiveDiagrams(), getTableProperties(),DockingConstants.SPLIT_BOTTOM);
		sb.incProgress(2);

		sb.showStatus("Cargando Componentes : Tab TreeActiveDiagrams    TreeDiagrams");
		System.out.println("Cargando Componentes : Tab TreeActiveDiagrams    TreeDiagrams");
		sb.incProgress(2);
		desk.createTab(getTreeActiveDiagrams(), getTreeDiagrams(), 1);
		sb.incProgress(2);

		sb.showStatus("Cargando Componentes : Tab TableProperties    Navigator");
		System.out.println("Cargando Componentes : Tab TableProperties    Navigator");
		sb.incProgress(2);
		desk.createTab(getTableProperties(), getNavigator(), 1);
		sb.incProgress(2);

		sb.showStatus("Cargando Componentes : Agregando VLToolbars");
		System.out.println("Cargando Componentes : Agregando VLToolbars");
		sb.incProgress(2);
		getJContentPane().add(container, BorderLayout.NORTH);
		/** VLToolbars* */
		sb.incProgress(2);

		sb.showStatus("Cargando Componentes : Agregando Docking DeskTop");
		System.out.println("Cargando Componentes : Agregando Docking DeskTop");
		sb.incProgress(2);
		getJContentPane().add(desk, BorderLayout.CENTER);
		sb.incProgress(2);

		sb.showStatus("Cargando Componentes : Agregando Eventos");
		System.out.println("Cargando Componentes : Agregando Eventos");
		sb.incProgress(2);
		desk.addDockableStateWillChangeListener(listener);// agrego el
		sb.incProgress(2);
		// evento

		sb.showStatus("Estableciendo conección con el servidor");
		System.out.println("Estableciendo conección con el servidor");
		sb.incProgress(2);
		paintManager = new PaintManager();
		System.out.println(paintManager.getExceptionStatus());
		sb.incProgress(2);
		//factoryModel = new FactoryModel();
		// harvet
		pointPressed = new Point(0,0);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initialize(StatusBar sb) {
		sb.showStatus("Cargando Componentes : Estableciendo Tamaño de Ventana");
		System.out.println("Cargando Componentes : Estableciendo Tamaño de Ventana");
		sb.incProgress(2);
		this.setSize(1006, 680);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		sb.incProgress(2);

		sb.showStatus("Cargando Componentes : Cargando Barra de Menus");
		System.out.println("Cargando Componentes : Cargando Barra de Menus");
		sb.incProgress(2);
		this.setJMenuBar(getMenuBarMain());
		sb.incProgress(2);

		sb.showStatus("Cargando Componentes : Cargando Panel de Contenido");
		System.out.println("Cargando Componentes : Cargando Panel de Contenido");
		sb.incProgress(2);
		this.setContentPane(getJContentPane());
		sb.incProgress(2);

		sb.showStatus("Cargando Componentes : Cargando Titulo en Ventana");
		System.out.println("Cargando Componentes : Cargando Titulo en Ventana");
		sb.incProgress(2);
		this.setTitle("Gestión de Procesos");
		sb.incProgress(2);

		sb.showStatus("Cargando Componentes : Calculando posicion en Pantalla");
		System.out.println("Cargando Componentes : Calculando posicion en Pantalla");
		sb.incProgress(2);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - getWidth()) / 2,((screenSize.height - getHeight()) / 2) - 35);
		sb.incProgress(2);

		/**
		 * VLToolbars
		 */
		sb.showStatus("Cargando Componentes : Cargando ToolsBars");
		System.out.println("Cargando Componentes : Cargando ToolsBars");
		sb.incProgress(2);
		ToolBarPanel topToolBarPanel = container.getToolBarPanelAt(BorderLayout.NORTH);
		toolBarFile = new VLToolBar("Archivo");
		toolBarEdit = new VLToolBar("Edicion");
		toolBarZoom = new VLToolBar("Zoom");
		toolBarDo = new VLToolBar("Historial");
		sb.incProgress(2);

		sb.showStatus("Cargando Componentes : Cargando ToolBar Archivo");
		System.out.println("Cargando Componentes : Cargando ToolBar Archivo");
		sb.incProgress(2);
		toolBarFile.add(getButtonNewDiagram());
		toolBarFile.add(getButtonOpen());
		toolBarFile.add(getButtonSave());
		toolBarFile.add(getButtonSaveAll());
		toolBarFile.add(getButtonPrint());
		//toolBarFile.add(getButtonPrintPreView());
		sb.incProgress(2);

		sb.showStatus("Cargando Componentes : Cargando ToolBar Edicion");
		System.out.println("Cargando Componentes : Cargando ToolBar Edicion");
		sb.incProgress(2);
		toolBarEdit.add(getButtonCut());
		toolBarEdit.add(getButtonCopy());
		toolBarEdit.add(getButtonPaste());
		toolBarEdit.add(getButtonDelete());
		//toolBarEdit.add(getButtonFind());
		sb.incProgress(2);

		sb.showStatus("Cargando Componentes : Cargando ToolBar Zoom");
		System.out.println("Cargando Componentes : Cargando ToolBar Zoom");
		sb.incProgress(2);
		toolBarZoom.add(getButtonZoomIn());
		toolBarZoom.add(getButtonZoomOut());
		toolBarZoom.add(getButtonResetZoom());
		sb.incProgress(2);

		sb.showStatus("Cargando Componentes : Cargando ToolBar Undo-Redo");
		System.out.println("Cargando Componentes : Cargando ToolBar Undo-Redo");
		sb.incProgress(2);
		toolBarDo.add(getButtonUndo());
		toolBarDo.add(getButtonRefresh());
		toolBarDo.add(getButtonRedo());
		sb.incProgress(2);

		sb.showStatus("Cargando Componentes : Cargando ToolsBars en Contenedor");
		System.out.println("Cargando Componentes : Cargando ToolsBars en Contenedor");
		sb.incProgress(2);
		topToolBarPanel.add(toolBarFile, new ToolBarConstraints(0, 0));// primera fila y tal columna
		sb.incProgress(2);
		topToolBarPanel.add(toolBarEdit, new ToolBarConstraints(0, 1));
		sb.incProgress(2);
		topToolBarPanel.add(toolBarZoom, new ToolBarConstraints(0, 2));
		sb.incProgress(2);
		topToolBarPanel.add(toolBarDo, new ToolBarConstraints(0, 3));
		sb.incProgress(2);
		/**
		 * VLToolbars
		 */
		sb.showStatus("Cargando Componentes : Cargando Eventos Open-Close");
		System.out.println("Cargando Componentes : Cargando Eventos Open-Close");
		sb.incProgress(4);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				loadPerspective();
				loadToolsBars();
				/**
				 * cargando menu segun situacion de los docking
				 */
				for (int i = 0; i < desk.getDockables().length; i++) {
					if (desk.getDockables()[i].isHidden()) {
						if (desk.getDockables()[i].getDockable().getDockKey()
								.getKey() == "Navegador")
							getCheckBoxMenuItemNavigator().setState(false);
						if (desk.getDockables()[i].getDockable().getDockKey()
								.getKey() == "Diagramas")
							getCheckBoxMenuItemExplorer().setState(false);
						if (desk.getDockables()[i].getDockable().getDockKey()
								.getKey() == "Diagrama Activo") // "Diagrama
							// Activo")
							getCheckBoxMenuItemActiveDiagram().setState(false);
						if (desk.getDockables()[i].getDockable().getDockKey()
								.getKey() == "Propiedades")
							getCheckBoxMenuItemProperties().setState(false);
					} else {// fin del if de si es hidden
						if (desk.getDockables()[i].getDockable().getDockKey()
								.getKey() == "Navegador")
							getCheckBoxMenuItemNavigator().setState(true);
						if (desk.getDockables()[i].getDockable().getDockKey()
								.getKey() == "Diagramas")
							getCheckBoxMenuItemExplorer().setState(true);
						if (desk.getDockables()[i].getDockable().getDockKey()
								.getKey() == "Diagrama Activo") // "Diagrama
							// Activo")
							getCheckBoxMenuItemActiveDiagram().setState(true);
						if (desk.getDockables()[i].getDockable().getDockKey()
								.getKey() == "Propiedades")
							getCheckBoxMenuItemProperties().setState(true);
					}
				}// fin del for
			}

			public void windowClosing(java.awt.event.WindowEvent e) {
				savePerspective();
				saveToolsBars();
			}
		});
		sb.incProgress(4);

	}//fin initialize

	/**
	 * Evento Onclose de los dockings
	 */
	DockableStateWillChangeListener listener = new DockableStateWillChangeListener() {
		public void dockableStateWillChange(DockableStateWillChangeEvent event) {
			DockableState current = event.getCurrentState(); // @jve:decl-index=0:
			/**
			 * Aqui mando a esconder cualquiera de los componentes en el
			 * evento onclose
			 */
			if (event.getFutureState().isClosed()) {
				event.cancel();
				/**
				 * Actualizando menu
				 */
				if (current.getDockable() == navigator)
					getCheckBoxMenuItemNavigator().setState(false);
				if (current.getDockable() == treeDiagrams)
					getCheckBoxMenuItemExplorer().setState(false);
				if (current.getDockable() == treeActiveDiagrams)
					getCheckBoxMenuItemActiveDiagram().setState(false);
				if (current.getDockable() == tableProperties)
					getCheckBoxMenuItemProperties().setState(false);

				desk.addHiddenDockable(current.getDockable(),
						RelativeDockablePosition.TOP_LEFT);
			}
			// }
			/**
			 * captura del evento onhide
			 */
			if (event.getFutureState().isDocked()) {
				if (current.getDockable() == navigator)
					getCheckBoxMenuItemNavigator().setState(true);
				if (current.getDockable() == treeDiagrams)
					getCheckBoxMenuItemExplorer().setState(true);
				if (current.getDockable() == treeActiveDiagrams)
					getCheckBoxMenuItemActiveDiagram().setState(true);
				if (current.getDockable() == tableProperties)
					getCheckBoxMenuItemProperties().setState(true);
			}

		}
	};

	private JMenuItem MenuItemCloseOther = null;

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
		}
		return jContentPane;
	}

	/**
	 * This method initializes ButtonNewDiagram
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getButtonNewDiagram() {
		if (ButtonNewDiagram == null) {
			ButtonNewDiagram = new JButton();
			ButtonNewDiagram.setToolTipText("Nuevo");
			ButtonNewDiagram.setIcon(new ImageIcon(getClass().getResource(
			"/icons/new.png")));
			ButtonNewDiagram
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMenuItemNewDiagram().doClick();
				}
			});
		}
		return ButtonNewDiagram;
	}

	/**
	 * This method initializes ButtonOpen
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getButtonOpen() {
		if (ButtonOpen == null) {
			ButtonOpen = new JButton();
			ButtonOpen.setIcon(new ImageIcon(getClass().getResource(
			"/icons/folder-open.png")));
			ButtonOpen.setToolTipText("Abrir");
			ButtonOpen.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMenuItemOpenDiagram().doClick();
				}
			});
		}
		return ButtonOpen;
	}

	/**
	 * This method initializes ButtonSave
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonSave() {
		if (ButtonSave == null) {
			ButtonSave = new JButton();
			ButtonSave.setIcon(new ImageIcon(getClass().getResource(
			"/icons/save2.png")));
			ButtonSave.setEnabled(false);
			ButtonSave.setToolTipText("Guardar");
		}
		return ButtonSave;
	}

	/**
	 * This method initializes ButtonSaveAll
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonSaveAll() {
		if (ButtonSaveAll == null) {
			ButtonSaveAll = new JButton();
			ButtonSaveAll.setToolTipText("Guardar Todo");
			ButtonSaveAll.setEnabled(false);
			ButtonSaveAll.setIcon(new ImageIcon(getClass().getResource(
			"/icons/savecopy2.png")));
		}
		return ButtonSaveAll;
	}

	/**
	 * This method initializes ButtonPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getButtonPrint() {
		if (ButtonPrint == null) {
			ButtonPrint = new JButton();
			ButtonPrint.setToolTipText("Imprmir");
			ButtonPrint.setIcon(new ImageIcon(getClass().getResource(
			"/icons/printer2.png")));
		}
		return ButtonPrint;
	}

	/**
	 * This method initializes buttonPrintPreView
	 * 
	 * @return javax.swing.JButton
	 */
	@SuppressWarnings("unused")
	private JButton getButtonPrintPreView() {
		if (buttonPrintPreView == null) {
			buttonPrintPreView = new JButton();
			buttonPrintPreView.setToolTipText("Vista Previa");
			buttonPrintPreView.setIcon(new ImageIcon(getClass().getResource(
			"/icons/print_preview.PNG")));
		}
		return buttonPrintPreView;
	}

	/**
	 * This method initializes ButtonCut
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonCut() {
		if (ButtonCut == null) {
			ButtonCut = new JButton();
			ButtonCut.setToolTipText("Cortar");
			ButtonCut.setEnabled(false);
			ButtonCut.setIcon(new ImageIcon(getClass().getResource(
			"/icons/cut1.png")));
			ButtonCut.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMenuItemCut().doClick();
				}
			});
		}
		return ButtonCut;
	}

	/**
	 * This method initializes buttonCopy
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonCopy() {
		if (buttonCopy == null) {
			buttonCopy = new JButton();
			buttonCopy.setToolTipText("Copiar");
			buttonCopy.setEnabled(false);
			buttonCopy.setIcon(new ImageIcon(getClass().getResource(
			"/icons/copy1.png")));
			buttonCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMenuItemCopy().doClick();
				}
			});
		}
		return buttonCopy;
	}

	/**
	 * This method initializes buttonPaste
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getButtonPaste() {
		if (buttonPaste == null) {
			buttonPaste = new JButton();
			buttonPaste.setToolTipText("Pegar");
			buttonPaste.setEnabled(false);
			buttonPaste.setIcon(new ImageIcon(getClass().getResource(
			"/icons/paste1.png")));
			buttonPaste.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMenuItemPaste().doClick();
				}
			});
		}
		return buttonPaste;
	}

	/**
	 * This method initializes buttonDelete
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonDelete() {
		if (buttonDelete == null) {
			buttonDelete = new JButton();
			buttonDelete.setToolTipText("Eliminar");
			buttonDelete.setEnabled(false);
			buttonDelete.setIcon(new ImageIcon(getClass().getResource(
			"/icons/delete4.png")));
			buttonDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMenuItemDelete().doClick();
				}
			});
		}
		return buttonDelete;
	}

	/**
	 * This method initializes buttonFind
	 * 
	 * @return javax.swing.JButton
	 */
	@SuppressWarnings("unused")
	private JButton getButtonFind() {
		if (buttonFind == null) {
			buttonFind = new JButton();
			buttonFind.setToolTipText("Buscar");
			buttonFind.setIcon(new ImageIcon(getClass().getResource(
			"/icons/search2.PNG")));
		}
		return buttonFind;
	}

	/**
	 * This method initializes buttonZoomIn
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonZoomIn() {
		if (buttonZoomIn == null) {
			buttonZoomIn = new JButton();
			buttonZoomIn.setToolTipText("Zoom In");
			buttonZoomIn.setIcon(new ImageIcon(getClass().getResource(
			"/icons/zoom-in.png")));
			buttonZoomIn.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseReleased(java.awt.event.MouseEvent e) {
					getMenuItemZoomIn().doClick();
				}
			});
		}
		return buttonZoomIn;
	}

	/**
	 * This method initializes buttonZoomOut
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonZoomOut() {
		if (buttonZoomOut == null) {
			buttonZoomOut = new JButton();
			buttonZoomOut.setToolTipText("Zoom Out");
			buttonZoomOut.setIcon(new ImageIcon(getClass().getResource(
			"/icons/zoom-out.png")));
			buttonZoomOut.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseReleased(java.awt.event.MouseEvent e) {
					getMenuItemZoomOut().doClick();
				}
			});
		}
		return buttonZoomOut;
	}

	/**
	 * This method initializes buttonUndo
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonUndo() {
		if (buttonUndo == null) {
			buttonUndo = new JButton();
			buttonUndo.setToolTipText("Deshacer");
			buttonUndo.setEnabled(false);
			buttonUndo.setIcon(new ImageIcon(getClass().getResource(
			"/icons/undo.png")));
			buttonUndo.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					getMenuItemUndo().doClick();
				}
			});
		}
		return buttonUndo;
	}

	/**
	 * This method initializes buttonRefresh
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getButtonRefresh() {
		if (buttonRefresh == null) {
			buttonRefresh = new JButton();
			buttonRefresh.setToolTipText("Refrescar");
			buttonRefresh.setIcon(new ImageIcon(getClass().getResource(
			"/icons/nav-reload.png")));
			buttonRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getTabbedPaneWorkArea().repaint();
				}
			});
		}
		return buttonRefresh;
	}

	/**
	 * This method initializes buttonRedo
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonRedo() {
		if (buttonRedo == null) {
			buttonRedo = new JButton();
			buttonRedo.setToolTipText("Rehacer");
			buttonRedo.setEnabled(false);
			buttonRedo.setIcon(new ImageIcon(getClass().getResource(
			"/icons/nav-redo.png")));
			buttonRedo.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					/*redoAction();
	            getTreeDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeDiagrams(getPaintManager().getDiagramList()));
	            getTreeActiveDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeActiveDiagrams(getPaintManager().getActiveDiagram()));*/
					getMenuItemRedo().doClick();
				}
			});
		}
		return buttonRedo;
	}

	/**
	 * This method initializes MenuBarMain
	 * 
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getMenuBarMain() {
		if (MenuBarMain == null) {
			MenuBarMain = new JMenuBar();
			MenuBarMain.setComponentOrientation(ComponentOrientation.UNKNOWN);
			MenuBarMain.add(getMenuFile());
			MenuBarMain.add(getMenuEdit());
			MenuBarMain.add(getMenuView());
			MenuBarMain.add(getMenuTools());
			MenuBarMain.add(getMenuHelp());
		}
		return MenuBarMain;
	}

	/**
	 * This method initializes MenuFile
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getMenuFile() {
		if (MenuFile == null) {
			MenuFile = new JMenu();
			MenuFile.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuFile.setText("Archivo");
			MenuFile.setMnemonic(KeyEvent.VK_A);
			MenuFile.add(getMenuItemNewDiagram());
			MenuFile.add(getSeparatorMenuFile1());
			MenuFile.add(getMenuItemOpenDiagram());
			MenuFile.add(getSeparatorMenuFile2());
			MenuFile.add(getMenuItemSave());
			MenuFile.add(getMenuItemSaveAll());
			MenuFile.add(getSeparatorMenuFile3());
			MenuFile.add(getMenuItemClose());
			MenuFile.add(getMenuItemCloseOther());
			MenuFile.add(getMenuItemCloseAll());
			MenuFile.add(getSeparatorMenuFile4());
			MenuFile.add(getMenuItemExportAs());
			MenuFile.add(getMenuItemPrint());
			MenuFile.add(getSeparatorMenuFile5());
			MenuFile.add(getMenuItemExit());
		}
		return MenuFile;
	}

	/**
	 * This method initializes MenuItemNewDiagram
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getMenuItemNewDiagram() {
		if (MenuItemNewDiagram == null) {
			MenuItemNewDiagram = new JMenuItem();
			MenuItemNewDiagram.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuItemNewDiagram.setIcon(new ImageIcon(getClass().getResource(
			"/icons/new.png")));
			MenuItemNewDiagram.setText("Nuevo Diagrama");
			MenuItemNewDiagram.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK, false));
			MenuItemNewDiagram.setMnemonic(KeyEvent.VK_N);
			MenuItemNewDiagram.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					String name = (String) JOptionPane.showInputDialog(returnThis(), "Nombre :","Mapa de Nivel Cero",JOptionPane.PLAIN_MESSAGE, null, null,"Sin Nombre");
					if (name != null) {
						int count = 1;
						if ((name.length() == 0)|| (name.startsWith(" "))|| (name.equalsIgnoreCase("Sin Nombre"))) {
							for (int i = 0; i < getPaintManager().getLength(); i++) {
								if (getPaintManager().getDiagram(i).getName().length() > 10) {
									String aux = getPaintManager().getDiagram(i).getName().substring(1, 12);
									if (aux.compareTo("Sin Nombre_") == 0) {
										String c = getPaintManager().getDiagram(i).getName().substring(12);
										Integer integer = new Integer(c);
										count = integer.intValue() + 1;
									}
								}
							}
							name = "Sin Nombre_" + count;
							count = 1;

							Diagram newDiagram = new Diagram("MNC", 0,name);
							newDiagram.setSelected(true);
							newDiagram.setSaveStatus(true);
							for (int i = 0; i < paintManager.getLength(); i++) {
								paintManager.getDiagram(i).setSelected(false);
							}
							paintManager.addDiagram(newDiagram);

							/**
							 * Nuevvo
							 */
							if (getTabbedPaneWorkArea().getCantidadTab() > 1) {
								JPanel panel = new JPanel();
								getTabbedPaneWorkArea().getTabbedPane().insertTab(getTabbedPaneWorkArea().getTituloAt(getTabbedPaneWorkArea().getCantidadTab() - 1),null,panel,null,getTabbedPaneWorkArea().getCantidadTab() - 1);// probar
								//name = "*"+name;
								getTabbedPaneWorkArea().getPanelWorkArea().setPreferredSize(new Dimension(2400,1400/*800,800*/));

								getTabbedPaneWorkArea().AgregarTab(name);
								getTabbedPaneWorkArea().setSelectedPosicion(getTabbedPaneWorkArea().getCantidadTab() - 1);// -1
								setTabSelect(getTabbedPaneWorkArea().getCantidadTab() - 1);
								addTabTitle(name);
							} else {
								getTabbedPaneWorkArea().getPanelWorkArea().setPreferredSize(new Dimension(2400,1400/*800,800*/));
								//name = "*"+name;
								getTabbedPaneWorkArea().AgregarTab(name);
								getTabbedPaneWorkArea().setSelectedPosicion(getTabbedPaneWorkArea().getCantidadTab() - 1);
								setTabSelect(getTabbedPaneWorkArea().getCantidadTab() - 1);
								addTabTitle(name);
							}
							/**
							 * nuevo
							 */

							/**
							 * instancia de diagrama
							 */


						} else {//escribieron un nombre valido
							/**
							 * nuevo
							 */
							String temp = name;
							for (int i = 0; i < getPaintManager().getLength(); i++) {
								String c = null;
								if (getPaintManager().getDiagram(i).getName().regionMatches(true, 0, name, 0, name.length())) {
									try{
										c = getPaintManager().getDiagram(i).getName().substring(name.length() + 1);
										Integer integer = new Integer(c);
										count = integer.intValue() + 1;
										temp = name + "_" + count;
									}catch(Exception exception){
										if(c == null)
											temp = name + "_" + count;
									}
								}
							}
							name = temp;
							count = 1;

							Diagram newDiagram = new Diagram("MNC", 0,name);
							newDiagram.setSelected(true);
							newDiagram.setSaveStatus(true);
							for (int i = 0; i < paintManager.getLength(); i++) {
								paintManager.getDiagram(i).setSelected(false);
							}
							paintManager.addDiagram(newDiagram);

							if (getTabbedPaneWorkArea().getCantidadTab() > 1) {
								JPanel panel = new JPanel();
								getTabbedPaneWorkArea().getTabbedPane().insertTab(getTabbedPaneWorkArea().getTituloAt(getTabbedPaneWorkArea().getCantidadTab() - 1),null,panel,null,getTabbedPaneWorkArea().getCantidadTab() - 1);// probar// tip
								getTabbedPaneWorkArea().getPanelWorkArea().setPreferredSize(new Dimension(2400,1400/*800,800*/));
								getTabbedPaneWorkArea().AgregarTab(name);
								getTabbedPaneWorkArea().setSelectedPosicion(getTabbedPaneWorkArea().getCantidadTab() - 1);// -1
								setTabSelect(getTabbedPaneWorkArea().getCantidadTab() - 1);
								addTabTitle(name);
							} else {
								getTabbedPaneWorkArea().getPanelWorkArea().setPreferredSize(new Dimension(2400,1400/*800,800*/));
								getTabbedPaneWorkArea().AgregarTab(name);

								getTabbedPaneWorkArea().setSelectedPosicion(getTabbedPaneWorkArea().getCantidadTab() - 1);
								setTabSelect(getTabbedPaneWorkArea().getCantidadTab() - 1);
								addTabTitle(name);
							}
							/**
							 * nuevo
							 */
						}
						/**
						 * actualizando tabs
						 */
						for (int i = 0; i < getTabbedPaneWorkArea().getCantidadTab(); i++) {
							getTabbedPaneWorkArea().getTabbedPane().setTitleAt(i, getTabTitle(i));
							getTabbedPaneWorkArea().getTabbedPane().setToolTipTextAt(i, getTabTitle(i));// tip
						}
						/**
						 * actualizando tabs
						 */
						getTabbedPaneWorkArea().addButtomsToDiagram(getPaintManager().getActiveDiagram().getType());
						/**
						 * Actualizar treeDiagrams
						 */
						getTreeDiagrams().setRootVisible();
						getTreeDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeDiagrams(getPaintManager().getDiagramList(), returnThis()));
						/**
						 * Actualizar treeActiveDiagrams
						 */
						getTreeActiveDiagrams().setRootVisible();
						getTreeActiveDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeActiveDiagrams(getPaintManager().getActiveDiagram()));
						/**
						 * Actualizar Combobox Process
						 */
						getTableProperties().setDefaultComboBoxModelProcess(FactoryModel.factory.getModelForComboboxProcess(getPaintManager().getActiveDiagram()));

						Rectangle rectVisible = new Rectangle(500,500);
						getPaintManager().getActiveDiagram().setRectVisible(rectVisible);
						Rectangle rectangle = new Rectangle(2400,1400/*800,800*/);
						getPaintManager().getActiveDiagram().setDiagramSize(rectangle);
						paintManager.setCreatePanel(true);
						/**
						 * Actualizando tableproperties
						 */
						getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToDiagram(paintManager.getActiveDiagram()));
						getTabbedPaneWorkArea().setColorToPanelWorkArea();
						getTabbedPaneWorkArea().repaint();
						getJTextAreaNote().setVisible(false);
						getJTextFieldName().setVisible(false);
						getPaintManager().setSizeDiagramOverview(new Rectangle(getNavigator().getSize()),getNavigator().getPanelOverView());
					}

					getButtonChosserBackGround().setVisible(false);
					getButtonChooserLine().setVisible(false);
					getComboBoxSelected().setVisible(false);
					getComboBoxVisible().setVisible(false);
					getComboBoxType().setVisible(false);
					getScrollPaneDescription().setVisible(false);

					getJTextAreaNote().setVisible(false);

					getNavigator().repaint();
					repaint();//para eliminar la resaca que deja el input dialog
					MenuItemUndo.setEnabled(false);
					MenuItemRedo.setEnabled(false);
					buttonUndo.setEnabled(false);
					buttonRedo.setEnabled(false);
				}
			});
		}
		return MenuItemNewDiagram;
	}

	/**
	 * This method initializes SeparatorMenuFile1
	 * 
	 * @return javax.swing.JSeparator
	 */
	private JSeparator getSeparatorMenuFile1() {
		if (SeparatorMenuFile1 == null) {
			SeparatorMenuFile1 = new JSeparator();
		}
		return SeparatorMenuFile1;
	}

	/**
	 * This method initializes MenuItemOpenDiagram
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getMenuItemOpenDiagram() {
		if (MenuItemOpenDiagram == null) {
			MenuItemOpenDiagram = new JMenuItem();
			MenuItemOpenDiagram.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuItemOpenDiagram.setIcon(new ImageIcon(getClass().getResource(
			"/icons/folder-open.png")));
			MenuItemOpenDiagram.setText("Abrir Diagrama");
			MenuItemOpenDiagram.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK, false));
			MenuItemOpenDiagram.setMnemonic(KeyEvent.VK_A);
			MenuItemOpenDiagram.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DiagramaService diagramaService = PaintManager.serviceLocator.getDiagramaService();
					DiagramaVO[] listMNC = diagramaService.getAllMNC();
					LoadDiagram loadDiagram = new LoadDiagram(returnThis());
					loadDiagram.setLocation((getWidth() - loadDiagram.getWidth())/2,((getHeight() - loadDiagram.getHeight()))/2);
					loadDiagram.setDefaultTreeModelOpenDiagrams(FactoryModel.factory.getModelForTreeOpenDiagrams(listMNC, returnThis()));
					loadDiagram.getTreeOpenDiagrams().setSelectionRow(0);
					PaintManager.expandAll(loadDiagram.getTreeOpenDiagrams());
					loadDiagram.setVisible(true);
				}
			});
		}
		return MenuItemOpenDiagram;
	}

	/**
	 * This method initializes SeparatorMenuFile2
	 * 
	 * @return javax.swing.JSeparator
	 */
	private JSeparator getSeparatorMenuFile2() {
		if (SeparatorMenuFile2 == null) {
			SeparatorMenuFile2 = new JSeparator();
		}
		return SeparatorMenuFile2;
	}

	/**
	 * This method initializes MenuItemSave
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getMenuItemSave() {
		if (MenuItemSave == null) {
			MenuItemSave = new JMenuItem();
			MenuItemSave.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuItemSave.setText("Guardar Diagrama");
			MenuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK, false));
			MenuItemSave.setEnabled(false);
			MenuItemSave.setIcon(new ImageIcon(getClass().getResource(
			"/icons/save2.png")));
			MenuItemSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int indexOf = getPaintManager().getActiveDiagram().getName().indexOf("*");
					if(indexOf != -1){
						getPaintManager().getActiveDiagram().setName(getPaintManager().getActiveDiagram().getName().substring(1));
						int posTab = getTabbedPaneWorkArea().getTabbedPane().getSelectedIndex();
						getTabbedPaneWorkArea().getTabbedPane().setTitleAt(posTab, getPaintManager().getActiveDiagram().getName());
						getTabbedPaneWorkArea().getTabbedPane().setToolTipTextAt(posTab, getPaintManager().getActiveDiagram().getName());
						getTabbedPaneWorkArea().getProcessGraphDocking().getListTabsTitles().remove(posTab);
						getTabbedPaneWorkArea().getProcessGraphDocking().getListTabsTitles().add(posTab, getPaintManager().getActiveDiagram().getName());
					}
					getPaintManager().saveDiagram(getPaintManager().getActiveDiagram(), returnThis());
					getTabbedPaneWorkArea().repaint();
				}
			});
		}
		return MenuItemSave;
	}

	/*      
		"Macro"
	    "Proc"
	    "SubProc"
		"ActServ"
		"ActManual"
		"Cliente"
		"Provee"
		"BaseDato"
		"Decision"
		"Nota"
		"EITiempo"
		"EIMensaje"
		"EInicial"
		"EFinal"
	 */

	/**
	 * This method initializes MenuItemSaveAll
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getMenuItemSaveAll() {
		if (MenuItemSaveAll == null) {
			MenuItemSaveAll = new JMenuItem();
			MenuItemSaveAll.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuItemSaveAll.setText("Guardar Todo");
			MenuItemSaveAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK | Event.SHIFT_MASK, false));
			MenuItemSaveAll.setEnabled(false);
			MenuItemSaveAll.setIcon(new ImageIcon(getClass().getResource(
					"/icons/savecopy2.png")));
			MenuItemSaveAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					for (int i = 0; i < getPaintManager().getDiagramList().size(); i++) {
						Diagram diagram = getPaintManager().getDiagramList().get(i);
						int indexOf = diagram.getName().indexOf("*");
						if(indexOf != -1){
							diagram.setName(diagram.getName().substring(1));
							getTabbedPaneWorkArea().getTabbedPane().setTitleAt(i + 1, diagram.getName());
							getTabbedPaneWorkArea().getTabbedPane().setToolTipTextAt(i + 1, diagram.getName());
							getListTabsTitles().remove(i + 1);
							getListTabsTitles().add(i + 1, diagram.getName());
						}
					}
					getPaintManager().saveAllDiagrams(getPaintManager().getDiagramList(), returnThis());
					getTabbedPaneWorkArea().repaint();
				}
			});
		}
		return MenuItemSaveAll;
	}

	/**
	 * This method initializes SeparatorMenuFile3
	 * 
	 * @return javax.swing.JSeparator
	 */
	private JSeparator getSeparatorMenuFile3() {
		if (SeparatorMenuFile3 == null) {
			SeparatorMenuFile3 = new JSeparator();
		}
		return SeparatorMenuFile3;
	}

	/**
	 * This method initializes MenuItemClose
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMenuItemClose() {
		if (MenuItemClose == null) {
			MenuItemClose = new JMenuItem();
			MenuItemClose.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuItemClose.setText("Cerrar");
			MenuItemClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getTabbedPaneWorkArea().getMenuItemCloseMe().doClick();
				}
			});
		}
		return MenuItemClose;
	}

	/**
	 * This method initializes MenuItemCloseAll
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMenuItemCloseAll() {
		if (MenuItemCloseAll == null) {
			MenuItemCloseAll = new JMenuItem();
			MenuItemCloseAll.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuItemCloseAll.setText("Cerrar todos");
			MenuItemCloseAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getTabbedPaneWorkArea().getMenuItemCloseAll().doClick();
				}
			});
		}
		return MenuItemCloseAll;
	}

	/**
	 * This method initializes SeparatorMenuFile4
	 * 
	 * @return javax.swing.JSeparator
	 */
	private JSeparator getSeparatorMenuFile4() {
		if (SeparatorMenuFile4 == null) {
			SeparatorMenuFile4 = new JSeparator();
		}
		return SeparatorMenuFile4;
	}

	/**
	 * This method initializes MenuItemPrint
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMenuItemPrint() {
		if (MenuItemPrint == null) {
			MenuItemPrint = new JMenuItem();
			MenuItemPrint.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuItemPrint.setText("Imprimir Diagrama");
			MenuItemPrint.setIcon(new ImageIcon(getClass().getResource(
			"/icons/printer1.png")));
			MenuItemPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new Printer(returnThis());
					String[] args = {};
					Printer.main(args);
				}
			});
		}
		return MenuItemPrint;
	}

	/**
	 * This method initializes SeparatorMenuFile5
	 * 
	 * @return javax.swing.JSeparator
	 */
	private JSeparator getSeparatorMenuFile5() {
		if (SeparatorMenuFile5 == null) {
			SeparatorMenuFile5 = new JSeparator();
		}
		return SeparatorMenuFile5;
	}

	/**
	 * This method initializes MenuItemExit
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMenuItemExit() {
		if (MenuItemExit == null) {
			MenuItemExit = new JMenuItem();
			MenuItemExit.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuItemExit.setText("Salir");
			MenuItemExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return MenuItemExit;
	}

	/**
	 * This method initializes MenuEdit
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getMenuEdit() {
		if (MenuEdit == null) {
			MenuEdit = new JMenu();
			MenuEdit.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuEdit.setText("Edición");
			MenuEdit.setMnemonic(KeyEvent.VK_E);
			MenuEdit.add(getMenuItemUndo());
			MenuEdit.add(getMenuItemRedo());
			MenuEdit.add(getSeparatorMenuEdit1());
			MenuEdit.add(getMenuItemCut());
			MenuEdit.add(getMenuItemCopy());
			MenuEdit.add(getMenuItemPaste());
			MenuEdit.add(getMenuItemDelete());
			MenuEdit.add(getSeparatorMenuEdit2());
			MenuEdit.add(getMenuItemFind());
		}
		return MenuEdit;
	}

	/**
	 * This method initializes MenuItemUndo
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getMenuItemUndo() {
		if (MenuItemUndo == null) {
			MenuItemUndo = new JMenuItem();
			MenuItemUndo.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuItemUndo.setText("Deshacer");
			MenuItemUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK, false));
			MenuItemUndo.setEnabled(false);
			MenuItemUndo.setIcon(new ImageIcon(getClass().getResource(
					"/icons/undo.png")));
			MenuItemUndo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getJTextAreaNote().setVisible(false);
					getJTextFieldName().setVisible(false);
					countToCompareName = 0;
					countToCompareNote = 0;
					getPaintManager().getActiveDiagram().setSaveStatus(true);
					undoAction();
					getTreeActiveDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeActiveDiagrams(getPaintManager().getActiveDiagram()));
					getTreeDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeDiagrams(getPaintManager().getDiagramList(), returnThis()));
					getTableProperties().setDefaultComboBoxModelProcess(FactoryModel.factory.getModelForComboboxProcess(getPaintManager().getActiveDiagram()));
					if(getPaintManager().getActiveDiagram().getSelectedStereotype() != null)// simple steretipo seleccionado
						getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToStereotype(getPaintManager().getActiveDiagram().getSelectedStereotype()));
					else //muchos seleccionados
						getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToDiagram(getPaintManager().getActiveDiagram()));
				}
			});
		}
		return MenuItemUndo;
	}

	/**
	 * This method initializes MenuItemRedo
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getMenuItemRedo() {
		if (MenuItemRedo == null) {
			MenuItemRedo = new JMenuItem();
			MenuItemRedo.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuItemRedo.setText("Rehacer");
			MenuItemRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK, false));
			MenuItemRedo.setEnabled(false);
			MenuItemRedo.setIcon(new ImageIcon(getClass().getResource(
					"/icons/nav-redo.png")));
			MenuItemRedo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getJTextAreaNote().setVisible(false);
					getJTextFieldName().setVisible(false);
					countToCompareName = 0;
					countToCompareNote = 0;
					getPaintManager().getActiveDiagram().setSaveStatus(true);
					redoAction();
					getTreeActiveDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeActiveDiagrams(getPaintManager().getActiveDiagram()));
					getTreeDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeDiagrams(getPaintManager().getDiagramList(), returnThis()));
					getTableProperties().setDefaultComboBoxModelProcess(FactoryModel.factory.getModelForComboboxProcess(getPaintManager().getActiveDiagram()));
					if(getPaintManager().getActiveDiagram().getSelectedStereotype() != null)// simple steretipo seleccionado
						getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToStereotype(getPaintManager().getActiveDiagram().getSelectedStereotype()));
					else //muchos seleccionados
						getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToDiagram(getPaintManager().getActiveDiagram()));
				}
			});
		}
		return MenuItemRedo;
	}

	/**
	 * This method initializes SeparatorMenuEdit1
	 * 
	 * @return javax.swing.JSeparator
	 */
	private JSeparator getSeparatorMenuEdit1() {
		if (SeparatorMenuEdit1 == null) {
			SeparatorMenuEdit1 = new JSeparator();
		}
		return SeparatorMenuEdit1;
	}

	/**
	 * This method initializes MenuItemCut
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getMenuItemCut() {
		if (MenuItemCut == null) {
			MenuItemCut = new JMenuItem();
			MenuItemCut.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuItemCut.setText("Cortar");
			MenuItemCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK, false));
			MenuItemCut.setIcon(new ImageIcon(getClass().getResource(
					"/icons/cut1.png")));
			MenuItemCut.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getPaintManager().getCopyList().clear();
					getPaintManager().getCutList().clear();
					getJTextAreaNote().setVisible(false);
					getJTextFieldName().setVisible(false);
					Diagram diagram = getPaintManager().getActiveDiagram();
					if(diagram != null){
						getPaintManager().setCutList(diagram.getSelectedStereotypes());

						UndoAction action = new UndoAction("supr");
						for(int i = 0; i < diagram.getSelectedStereotypes().size(); i++){
							Stereotype stereotypeAction = new Stereotype(diagram.getSelectedStereotypes().get(i));
							stereotypeAction.setSelected(true);
							action.getArrayActions().add(stereotypeAction);
						}
						if(diagram.getListRedo().size() > 0){
							diagram.getListRedo().clear();
							getButtonRedo().setEnabled(false);
							getMenuItemRedo().setEnabled(false);
							getTabbedPaneWorkArea().getMenuItemRedo().setEnabled(false);
						}
						diagram.getListUndo().add(action);

						//sabado
						if(diagram.getSelectedStereotypes().size() > 0){
							getTabbedPaneWorkArea().getMenuItemPaste().setEnabled(true);
							getMenuItemPaste().setEnabled(true);
							getButtonPaste().setEnabled(true);
						}else{
							getTabbedPaneWorkArea().getMenuItemPaste().setEnabled(false);
							getMenuItemPaste().setEnabled(false);
							getButtonPaste().setEnabled(false);
						}

						for (Stereotype stereotype : diagram.getSelectedStereotypes()) {
							getPaintManager().hideStereotypeInDiagram(stereotype.getParentId(), stereotype);
						}

						actionEdit = "Cut";
						getTableProperties().setDefaultComboBoxModelProcess(FactoryModel.factory.getModelForComboboxProcess(diagram));
						getTreeActiveDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeActiveDiagrams(diagram));
						getTreeDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeDiagrams(getPaintManager().getDiagramList(), returnThis()));
						getTabbedPaneWorkArea().getPanelWorkArea().repaint();
					}
				}
			});
		}
		return MenuItemCut;
	}

	/**
	 * This method initializes MenuItemCopy
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getMenuItemCopy() {
		if (MenuItemCopy == null) {
			MenuItemCopy = new JMenuItem();
			MenuItemCopy.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuItemCopy.setText("Copiar");
			MenuItemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK, false));
			MenuItemCopy.setIcon(new ImageIcon(getClass().getResource(
					"/icons/copy1.png")));
			MenuItemCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getPaintManager().getCopyList().clear();
					getPaintManager().getCutList().clear();
					Diagram diagram = getPaintManager().getActiveDiagram();
					if(diagram != null){
						getPaintManager().setCopyList(diagram.getSelectedStereotypes());
						actionEdit = "Copiar";
					}
					//sabado
					if(diagram.getSelectedStereotypes().size() > 0){
						getTabbedPaneWorkArea().getMenuItemPaste().setEnabled(true);
						getMenuItemPaste().setEnabled(true);
						getButtonPaste().setEnabled(true);
					}else{
						getTabbedPaneWorkArea().getMenuItemPaste().setEnabled(false);
						getMenuItemPaste().setEnabled(false);
						getButtonPaste().setEnabled(false);
					}
				}
			});
		}
		return MenuItemCopy;
	}

	/**
	 * This method initializes MenuItemPaste
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getMenuItemPaste() {
		if (MenuItemPaste == null) {
			MenuItemPaste = new JMenuItem();
			MenuItemPaste.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuItemPaste.setText("Pegar");
			MenuItemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK, false));
			MenuItemPaste.setEnabled(false);
			MenuItemPaste.setIcon(new ImageIcon(getClass().getResource(
					"/icons/paste1.png")));
			MenuItemPaste.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Diagram diagram = getPaintManager().getActiveDiagram();
					if(diagram != null){
						int idDiagram = diagram.getId();
						if(actionEdit == "Copiar"){
							boolean banderaCopy = false;
							ArrayList<Stereotype> array = new ArrayList<Stereotype>(getPaintManager().getCopyList());
							int idStereotypeDiagram = array.get(0).getParentId();
							ArrayList<Stereotype> arrayNotes = diagram.findNoteInList(array);
							//intento copiar un estereotipo en el mismo diagrama
							if(idDiagram == idStereotypeDiagram && arrayNotes.size() == 0){
								JOptionPane.showMessageDialog(returnThis(), "Existe otro elemento de este tipo con el mismo nombre, acción cancelada!", "Error", JOptionPane.ERROR_MESSAGE);
							}
							else if(idDiagram == idStereotypeDiagram && arrayNotes.size() > 0){
								diagram.unselectAllStereotype();
								ArrayList<Stereotype> arrayTemp = new ArrayList<Stereotype>();
								for (Stereotype stereotype : arrayNotes) {
									//if(diagram.findNameByType(stereotype.getName(), stereotype.getType()) == false){
									Stereotype stereotype2 = new Stereotype(stereotype);
									Random random = new Random();
									stereotype2.setId(random.hashCode());
									stereotype2.setSelected(true);

									stereotype2.setName(new Integer(stereotype2.getId()).toString());
									//here para pegarlo en donde este el click
									if(getPointPressed().x != 0 && getPointPressed().y != 0 && arrayNotes.size() == 1){
										stereotype2.setInicialPoint(getPointPressed());
										stereotype2.setFinalPoint(new Point(stereotype2.getInicialPoint().x + stereotype2.getWidth(), stereotype2.getInicialPoint().y + stereotype2.getHeigth()));
									}else{
										stereotype2.setInicialPoint(new Point(stereotype.getInicialPoint().x + 10,stereotype.getInicialPoint().y + 10));
										stereotype2.setFinalPoint(new Point(stereotype.getFinalPoint().x + 10, stereotype.getFinalPoint().y + 10));
									}

									diagram.addStereotype(stereotype2);
									arrayTemp.add(stereotype2);
									banderaCopy = true;
									//}
								}
								if(banderaCopy == true){
									UndoAction action = new UndoAction("paste");
									for(int i = 0; i < arrayTemp.size(); i++){
										Stereotype undoStereotype = new Stereotype(arrayTemp.get(i));
										action.getArrayActions().add(undoStereotype);
									}
									if(diagram.getListRedo().size() > 0){
										diagram.getListRedo().clear();
										getButtonRedo().setEnabled(false);
										getMenuItemRedo().setEnabled(false);
										getTabbedPaneWorkArea().getMenuItemRedo().setEnabled(false);
									}
									diagram.getListUndo().add(action);
									getButtonUndo().setEnabled(true);
									getMenuItemUndo().setEnabled(true);
									getTabbedPaneWorkArea().getMenuItemUndo().setEnabled(true);
								}

							}else if(idDiagram != idStereotypeDiagram){
								diagram.unselectAllStereotype();
								ArrayList<Stereotype> arrayTemp = new ArrayList<Stereotype>();
								for (Stereotype stereotype : array) {
									if(diagram.findNameByType(stereotype.getName(), stereotype.getType()) == false){
										Stereotype stereotype2 = new Stereotype(stereotype);
										Random random = new Random();
										stereotype2.setId(random.hashCode());
										stereotype2.setParentId(diagram.getId());
										//here
										if(getPointPressed().x != 0 && getPointPressed().y != 0 && array.size() == 1){
											stereotype2.setInicialPoint(getPointPressed());
											stereotype2.setFinalPoint(new Point(stereotype2.getInicialPoint().x + stereotype2.getWidth(), stereotype2.getInicialPoint().y + stereotype2.getHeigth()));
										}
										diagram.addStereotype(stereotype2);
										arrayTemp.add(stereotype2);
										banderaCopy = true;
									}
								}
								if(banderaCopy == true){
									UndoAction action = new UndoAction("paste");
									for(int i = 0; i < arrayTemp.size(); i++){
										Stereotype undoStereotype = new Stereotype(arrayTemp.get(i));
										action.getArrayActions().add(undoStereotype);
									}
									if(diagram.getListRedo().size() > 0){
										diagram.getListRedo().clear();
										getButtonRedo().setEnabled(false);
										getMenuItemRedo().setEnabled(false);
										getTabbedPaneWorkArea().getMenuItemRedo().setEnabled(false);
									}
									diagram.getListUndo().add(action);
									getButtonUndo().setEnabled(true);
									getMenuItemUndo().setEnabled(true);	  
									getTabbedPaneWorkArea().getMenuItemUndo().setEnabled(true);
								}
							}
						}
						else if(actionEdit == "Cut"){
							boolean banderaCut = false;
							ArrayList<Stereotype> array = new ArrayList<Stereotype>(getPaintManager().getCutList());
							diagram.unselectAllStereotype();
							ArrayList<Stereotype> arrayTemp = new ArrayList<Stereotype>();
							for (Stereotype stereotype : array) {
								//stereotype.setParentId(diagram.getId());
								//here
								if(getPointPressed().x != 0 && getPointPressed().y != 0 && array.size() == 1){
									stereotype.setInicialPoint(getPointPressed());
									stereotype.setFinalPoint(new Point(stereotype.getInicialPoint().x + stereotype.getWidth(), stereotype.getInicialPoint().y + stereotype.getHeigth()));
								}
								if(diagram.getId() != stereotype.getParentId()){
									Diagram diagramParent = getPaintManager().findById(stereotype.getParentId());
									if(diagramParent.getType().equals(diagram.getType())){
										diagramParent.deleteStereotypeById(stereotype.getId());
										stereotype.setVisible(true);
										stereotype.setParentId(diagram.getId());
										/*if(!stereotype.getDiagramIds().isEmpty()){
											for (Integer diagramId : stereotype.getDiagramIds()) {
												Long idDiagramContent = PaintManager.diagramaService.findDiagrama(new Long(diagramId));
												if(idDiagramContent != -1){
													DiagramaVO diagramaVO = PaintManager.diagramaService.getDiagrama(idDiagramContent);
													diagramaVO.setOwner(new Long(stereotype.getId()));
													PaintManager.diagramaService.updateDiagrama(diagramaVO);
												}
											}
										}*/
										//actualizar stereotype
										Long idStereotype = PaintManager.stereotypeService.findStereotype(new Long(stereotype.getId()));
										if(idStereotype != -1){//crear stereotype y propiedades
											Long idDiagramParent = PaintManager.diagramaService.findDiagrama(new Long(diagram.getId()));
											if(idDiagramParent != -1){
												StereotypeVO stereotypeVO = PaintManager.stereotypeService.getStereotype(idStereotype);
												stereotypeVO.setOwner(idDiagramParent);
												PaintManager.stereotypeService.updateStereotype(stereotypeVO);
											}
										}
										diagram.addStereotype(stereotype);
									}
								}
								else if(diagram.getId() == stereotype.getParentId()){
									stereotype.setVisible(true);
								}
								//diagram.addStereotype(stereotype);
								arrayTemp.add(stereotype);
								banderaCut = true;
							}
							if(banderaCut == true){
								UndoAction action = new UndoAction("paste");
								for(int i = 0; i < arrayTemp.size(); i++){
									Stereotype undoStereotype = new Stereotype(arrayTemp.get(i));
									action.getArrayActions().add(undoStereotype);
								}
								if(diagram.getListRedo().size() > 0){
									diagram.getListRedo().clear();
									getButtonRedo().setEnabled(false);
									getMenuItemRedo().setEnabled(false);
									getTabbedPaneWorkArea().getMenuItemRedo().setEnabled(false);
								}
								diagram.getListUndo().add(action);
								getButtonUndo().setEnabled(true);
								getMenuItemUndo().setEnabled(true);
								getTabbedPaneWorkArea().getMenuItemUndo().setEnabled(true);
							}
						}
						actionEdit = "";
						diagram.setSaveStatus(true);
						getTabbedPaneWorkArea().getPanelWorkArea().repaint();
						//actualizando tablas treeviews combo

						getTableProperties().setDefaultComboBoxModelProcess(FactoryModel.factory.getModelForComboboxProcess(diagram));
						if(diagram.getSelectedStereotype() != null){
							getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToStereotype(diagram.getSelectedStereotype()));
							if(diagram.getSelectedStereotype().getType() != "Nota")
								getTableProperties().getComboBoxProcess().getModel().setSelectedItem(diagram.getSelectedStereotype().getName());
							//seleccionarlo en el treeactive diagram
						}

						getTreeActiveDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeActiveDiagrams(diagram));
						getTreeDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeDiagrams(getPaintManager().getDiagramList(), returnThis()));
						getPaintManager().getCopyList().clear();
						getPaintManager().getCutList().clear();
					}
					getTabbedPaneWorkArea().getMenuItemPaste().setEnabled(false);
					getMenuItemPaste().setEnabled(false);
					getButtonPaste().setEnabled(false);
				}
			});
		}
		return MenuItemPaste;
	}

	/**
	 * This method initializes MenuItemDelete
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getMenuItemDelete() {
		if (MenuItemDelete == null) {
			MenuItemDelete = new JMenuItem();
			MenuItemDelete.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuItemDelete.setText("Eliminar");
			MenuItemDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0, false));
			MenuItemDelete.setIcon(new ImageIcon(getClass().getResource(
					"/icons/delete4.png")));
			MenuItemDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Diagram diagram = getPaintManager().getActiveDiagram();
					getJTextAreaNote().setVisible(false);
					getJTextFieldName().setVisible(false);
					if(diagram != null){
						ArrayList<Stereotype> listStereotypes  = diagram.getSelectedStereotypes();
						ArrayList<Relation> arraRelation = diagram.getSelectedRelations();
						if(listStereotypes.size() > 0){
							Object[] options = {"Si","No"};
							int n = JOptionPane.showOptionDialog(returnThis(),"Desea eliminar el / los elemento(s) seleccionado(s)?",
									"Eliminar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,new ImageIcon(getClass().getResource(
									"/icons/trash_full.png")),options,  //the titles of buttons
									options[0]); //default button title
							if(n == 0){//si
								ArrayList<Relation> relationDeleted = new ArrayList<Relation>();
								//para la nota
								UndoAction action1 = new UndoAction("supr");
								for (int i =0; i<listStereotypes.size() ;i++ ) {
									if(listStereotypes.get(i).getType().equals("Nota")){
										ArrayList<Stereotype> array = getPaintManager().findRelationDestiny(listStereotypes.get(i));
										if( array.size() > 0){
											for (Stereotype stereotypeContent : array) {
												Relation relation = stereotypeContent.deleteRelation(listStereotypes.get(i));
												relation.setParentId(stereotypeContent.getId());
												relationDeleted.add(relation);
											}
										}
										//karel
										//undo redo
										Stereotype stereotypeAction = new Stereotype(listStereotypes.get(i));
										stereotypeAction.setSelected(true);
										action1.getArrayActions().add(stereotypeAction);
										//karel
										diagram.deleteStereotypeById(listStereotypes.get(i).getId());
										Long idStereotype = PaintManager.stereotypeService.findStereotype(new Long(listStereotypes.get(i).getId()));
										if(idStereotype != -1){//crear stereotype y propiedades
											PaintManager.stereotypeService.deleteStereotype(idStereotype);
										}
										listStereotypes.remove(i);
										i=-1;//para que empiece desde el principio
										diagram.setSaveStatus(true);
										getTabbedPaneWorkArea().repaint();
									}
								}
								//karel
								if(diagram.getListRedo().size() > 0){
									diagram.getListRedo().clear();
									getButtonRedo().setEnabled(false);
									getMenuItemRedo().setEnabled(false);
									getTabbedPaneWorkArea().getMenuItemRedo().setEnabled(false);
								}
								if(relationDeleted.size() > 0)
									action1.setArrayRelations(relationDeleted);
								diagram.getListUndo().add(action1);
								//karel
								if(listStereotypes.size() > 0){
									int answer = JOptionPane.showOptionDialog(returnThis(),"Desea eliminarlo(s) completamente del diagrama?",
											"Eliminar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,new ImageIcon(getClass().getResource(
											"/icons/trash_empty.png")),options,  //the titles of buttons
											options[0]);
									if(answer == 0){//si
										relationDeleted = new ArrayList<Relation>();
										for(int i = 0; i < listStereotypes.size(); i++){
											Stereotype stereotypeEvaluate = listStereotypes.get(i);
											ArrayList<Stereotype> array = new ArrayList<Stereotype>(getPaintManager().findRelationDestiny(stereotypeEvaluate));
											if( array.size() > 0){
												for (Stereotype stereotypeContent : array) {
													Relation relation = stereotypeContent.deleteRelation(stereotypeEvaluate);
													relation.setParentId(stereotypeContent.getId());
													relationDeleted.add(relation);
												}
											}
										}
										//undo redo
										UndoAction action = new UndoAction("supr");
										for(int i = 0; i < listStereotypes.size(); i++){
											Stereotype stereotypeAction = new Stereotype(listStereotypes.get(i));
											stereotypeAction.setSelected(true);
											action.getArrayActions().add(stereotypeAction);
										}
										if(relationDeleted.size() > 0)
											action.setArrayRelations(relationDeleted);

										if(diagram.getListRedo().size() > 0){
											diagram.getListRedo().clear();
											getButtonRedo().setEnabled(false);
											getMenuItemRedo().setEnabled(false);
											getTabbedPaneWorkArea().getMenuItemRedo().setEnabled(false);
										}
										diagram.getListUndo().add(action);

										for(int i = 0; i <listStereotypes.size();i++){
											diagram.deleteStereotypeById(listStereotypes.get(i).getId());
											if(listStereotypes.get(i).getDiagramIds().size() > 0){
												for (Integer idDiagram : listStereotypes.get(i).getDiagramIds()) {
													Diagram diagramDelete = getPaintManager().findById(idDiagram);
													deleteTabTitle(diagramDelete.getName());
													//getProcessGraphDocking().getPaintManager().deleteDiagram(tabTitle);
													//getPaintManager().getDiagramByName(tabTitle).setSelected(false);
													int selectedIndex = -1;
													for (int j = 0; j < getTabbedPaneWorkArea().getTabbedPane().getTabCount(); j++) {
														if(getTabbedPaneWorkArea().getTituloAt(j).equals(diagramDelete.getName())){
															selectedIndex = j;
														}
													}
													if(selectedIndex != -1){
														getTabbedPaneWorkArea().getTabbedPane().remove(selectedIndex);
														if(getTabbedPaneWorkArea().getTabbedPane().getTabCount() == 1){//solo queda la tab welcome
															getTreeActiveDiagrams().setRootVisible(false);
															getTreeActiveDiagrams().setDefaultTreeModelDiagrams(getTreeActiveDiagrams().getDefaultTreeModelDiagrams());
															getTableProperties().setDefaultComboBoxModelProcess(getTableProperties().getDefaultComboBoxModelProcess());
															getTableProperties().getTableProperties().setModel(getTableProperties().getDefaultTableModelProperties());

															//getProcessGraphDocking().getTreeDiagrams().setRootVisible(false);
															//getProcessGraphDocking().getTreeDiagrams().setDefaultTreeModelDiagrams(getProcessGraphDocking().getTreeDiagrams().getDefaultTreeModelDiagrams());

															getPaintManager().unActiveAllDiagrams();
														}else{
															String newTabTitle = getTabbedPaneWorkArea().getTabbedPane().getTitleAt(getTabbedPaneWorkArea().getTabbedPane().getSelectedIndex());
															getPaintManager().getDiagramByName(newTabTitle).setSelected(true);
															int posTab = getTabbedPaneWorkArea().getTabbedPane().getSelectedIndex();
															getTabbedPaneWorkArea().getTabbedPane().remove(posTab);//eliminar porque el insertar agrega, no reemplaza
															getTabbedPaneWorkArea().getTabbedPane().insertTab(newTabTitle, null, getTabbedPaneWorkArea().getPanelWorkAreaBack(),null, posTab);//
															getTabbedPaneWorkArea().getTabbedPane().setSelectedIndex(posTab);
															setTabSelect(getTabbedPaneWorkArea().getTabbedPane().getSelectedIndex());//aqui tengo el numero de la tab seleccionada
															//actualizando tabs
															for(int k =0;k<getTabbedPaneWorkArea().getTabbedPane().getTabCount();k++){
																getTabbedPaneWorkArea().getTabbedPane().setTitleAt(k, getTabTitle(k));
																getTabbedPaneWorkArea().getTabbedPane().setToolTipTextAt(k, getTabTitle(k));//tip
															}
															//getProcessGraphDocking().getTreeDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeDiagrams(getProcessGraphDocking().getPaintManager().getDiagramList()));
														}
														getPaintManager().deleteDiagram(diagramDelete.getName());
														Long idDiagramDelete = PaintManager.diagramaService.findDiagrama(new Long(diagramDelete.getId()));
														if(idDiagramDelete != -1){//crear stereotype y propiedades
															PaintManager.diagramaService.deleteDiagrama(idDiagramDelete);
														}
													}
												}
											}
											Long idStereotype = PaintManager.stereotypeService.findStereotype(new Long(listStereotypes.get(i).getId()));
											if(idStereotype != -1){//crear stereotype y propiedades
												PaintManager.stereotypeService.deleteStereotype(idStereotype);
											}
										}
										//actualizar tree
										getTreeActiveDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeActiveDiagrams(diagram));
										getTreeDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeDiagrams(getPaintManager().getDiagramList(), returnThis()));
										getTableProperties().setDefaultComboBoxModelProcess(FactoryModel.factory.getModelForComboboxProcess(diagram));
										getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToDiagram(diagram));
										getTableProperties().getComboBoxProcess().getModel().setSelectedItem("<<Procesos>>");
										//sabado
										getTabbedPaneWorkArea().getMenuItemPaste().setEnabled(false);
										getMenuItemPaste().setEnabled(false);
										getButtonPaste().setEnabled(false);
									}else{//esconder
										//undo redo
										UndoAction action = new UndoAction("edit");
										for(int i = 0; i < listStereotypes.size(); i++){
											Stereotype stereotypeAction = new Stereotype(listStereotypes.get(i));
											stereotypeAction.setSelected(true);
											action.getArrayActions().add(stereotypeAction);
										}
										if(diagram.getListRedo().size() > 0){
											diagram.getListRedo().clear();
											getButtonRedo().setEnabled(false);
											getMenuItemRedo().setEnabled(false);
											getTabbedPaneWorkArea().getMenuItemRedo().setEnabled(false);
										}
										diagram.getListUndo().add(action);

										for(int i = 0; i <listStereotypes.size();i++){
											diagram.hideStereotypeById(listStereotypes.get(i).getId());
										}
										if(diagram.getSelectedStereotype()!= null){
											getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToStereotype(diagram.getSelectedStereotype()));
											getTableProperties().getComboBoxProcess().setSelectedItem(diagram.getSelectedStereotype().getName());
										}
									}
									diagram.setSaveStatus(true);
								}
							}
						}
						if(listStereotypes.size() == 0 && arraRelation.size() > 0){
							Object[] options = {"Si","No"};
							int n = JOptionPane.showOptionDialog(returnThis(),"Desea eliminar el / los elemento(s) seleccionado(s)?",
									"Eliminar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,new ImageIcon(getClass().getResource(
									"/icons/trash_full.png")),options,  //the titles of buttons
									options[0]); //default button title
							if(n == 0){
								UndoAction action = new UndoAction("edit");
								ArrayList<Stereotype> arrayStereotypes = diagram.getStereotypesContent(arraRelation);
								for(int i = 0; i < arrayStereotypes.size(); i++){
									Stereotype stereotypeAction = new Stereotype(arrayStereotypes.get(i));
									stereotypeAction.setSelected(true);
									action.getArrayActions().add(stereotypeAction);
								}
								if(diagram.getListRedo().size() > 0){
									diagram.getListRedo().clear();
									getButtonRedo().setEnabled(false);
									getMenuItemRedo().setEnabled(false);
								}
								diagram.getListUndo().add(action);
								diagram.deleteRelations(arraRelation);
								for (Relation relation : arraRelation) {
									Long idRelation = PaintManager.relacionService.findRelacion(new Long(relation.getId()));
									if(idRelation != -1){
										PaintManager.relacionService.deleteRelacion(idRelation);
									}
								}
								diagram.setSaveStatus(true);
							}
						}
					}
					getTabbedPaneWorkArea().repaint();
				}
			});
		}
		return MenuItemDelete;
	}

	/**
	 * This method initializes SeparatorMenuEdit2
	 * 
	 * @return javax.swing.JSeparator
	 */
	private JSeparator getSeparatorMenuEdit2() {
		if (SeparatorMenuEdit2 == null) {
			SeparatorMenuEdit2 = new JSeparator();
		}
		return SeparatorMenuEdit2;
	}

	/**
	 * This method initializes MenuItemFind
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMenuItemFind() {
		if (MenuItemSelectAll == null) {
			MenuItemSelectAll = new JMenuItem();
			MenuItemSelectAll.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuItemSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK, false));
			MenuItemSelectAll.setText("Seleccionar todo");
			MenuItemSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getTabbedPaneWorkArea().getMenuItemSelectAll().doClick();
				}
			});
		}
		return MenuItemSelectAll;
	}

	/**
	 * This method initializes MenuView
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getMenuView() {
		if (MenuView == null) {
			MenuView = new JMenu();
			MenuView.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuView.setText("Ver");
			MenuView.setMnemonic(KeyEvent.VK_V);
			MenuView.add(getMenuItemZoomIn());
			MenuView.add(getMenuItemZoomOut());
			MenuView.add(getMenuItemResetZoom());
			MenuView.add(getCheckBoxMenuItemExplorer());
			MenuView.add(getCheckBoxMenuItemActiveDiagram());
			MenuView.add(getCheckBoxMenuItemProperties());
			MenuView.add(getCheckBoxMenuItemNavigator());
		}
		return MenuView;
	}

	/**
	 * This method initializes MenuItemZoomIn
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getMenuItemZoomIn() {
		if (MenuItemZoomIn == null) {
			MenuItemZoomIn = new JMenuItem();
			MenuItemZoomIn.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuItemZoomIn.setText("Zoom +");
			MenuItemZoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, Event.CTRL_MASK, false));
			MenuItemZoomIn.setIcon(new ImageIcon(getClass().getResource(
					"/icons/zoom-in.png")));
			MenuItemZoomIn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Diagram diagram = getPaintManager().getActiveDiagram();
					if(diagram != null){
						getJTextAreaNote().setVisible(false);
						getJTextFieldName().setVisible(false);
						double zoomFactor = getPaintManager().getActiveDiagram().getZoomFactor();
						countZoom = zoomFactor + 0.3;
						if(countZoom < 2.2){
							getPaintManager().getActiveDiagram().setZoomFactor( zoomFactor + 0.3 );

							Rectangle diagramWindowBounds = ( Rectangle ) getPaintManager().getActiveDiagram().getDiagramSize().clone();
							double newZoomFactor = getPaintManager().getActiveDiagram().getZoomFactor();
							Font fontTextArea = new Font("", Font.PLAIN, new Double(getSizeTextArea()*newZoomFactor).intValue());
							jTextAreaNote.setFont(fontTextArea);
							Font fontFieldName = new Font("", Font.PLAIN, new Double(getSizeFieldName()*newZoomFactor).intValue());
							jTextFieldName.setFont(fontFieldName);
							diagramWindowBounds.width = new Double( diagramWindowBounds.width * newZoomFactor ).intValue();
							diagramWindowBounds.height = new Double( diagramWindowBounds.height * newZoomFactor ).intValue();
							getTabbedPaneWorkArea().getPanelWorkArea().setPreferredSize(  new Dimension(diagramWindowBounds.getSize().width + 10, diagramWindowBounds.getSize().height + 2) );
							getTabbedPaneWorkArea().getPanelWorkArea().revalidate();
							getTabbedPaneWorkArea().getPanelWorkArea().repaint();
							if(diagram.getSelectedStereotype() == null)
								getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToDiagram(diagram));
						}
					}
				}
			});
		}
		return MenuItemZoomIn;
	}

	/**
	 * This method initializes MenuItemZoomOut
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getMenuItemZoomOut() {
		if (MenuItemZoomOut == null) {
			MenuItemZoomOut = new JMenuItem();
			MenuItemZoomOut.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuItemZoomOut.setText("Zoom -");
			MenuItemZoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, Event.CTRL_MASK, false));
			MenuItemZoomOut.setIcon(new ImageIcon(getClass().getResource(
					"/icons/zoom-out.png")));
			MenuItemZoomOut.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Diagram diagram = getPaintManager().getActiveDiagram();
					if(diagram != null){
						Double zoomFactor = getPaintManager().getActiveDiagram().getZoomFactor();
						getJTextAreaNote().setVisible(false);
						getJTextFieldName().setVisible(false);
						countZoom = zoomFactor - 0.3;
						if(countZoom > 0.2){
							getPaintManager().getActiveDiagram().setZoomFactor( zoomFactor - 0.3 );
							zoomFactor = getPaintManager().getActiveDiagram().getZoomFactor();
							Font fontTextArea = new Font("", Font.PLAIN, new Double(getSizeTextArea()*zoomFactor).intValue());
							jTextAreaNote.setFont(fontTextArea);
							Font fontFieldName = new Font("", Font.PLAIN, new Double(getSizeFieldName()*zoomFactor).intValue());
							jTextFieldName.setFont(fontFieldName);
							Rectangle diagramWindowBounds = ( Rectangle ) getPaintManager().getActiveDiagram().getDiagramSize().clone();
							double newZoomFactor = getPaintManager().getActiveDiagram().getZoomFactor();
							diagramWindowBounds.width = new Double( diagramWindowBounds.width * newZoomFactor ).intValue();
							diagramWindowBounds.height = new Double( diagramWindowBounds.height * newZoomFactor ).intValue();
							getTabbedPaneWorkArea().getPanelWorkArea().setPreferredSize(new Dimension(diagramWindowBounds.getSize().width , diagramWindowBounds.getSize().height));
							if(getTabbedPaneWorkArea().getPanelWorkArea().getPreferredSize().width < getTabbedPaneWorkArea().getPanelWorkArea().getVisibleRect().width
									&& getTabbedPaneWorkArea().getPanelWorkArea().getPreferredSize().height < getTabbedPaneWorkArea().getPanelWorkArea().getVisibleRect().height){
								diagram.setExecute(true);
							}
							else if(getTabbedPaneWorkArea().getPanelWorkArea().getPreferredSize().width < getTabbedPaneWorkArea().getPanelWorkArea().getVisibleRect().width
									&& getTabbedPaneWorkArea().getPanelWorkArea().getPreferredSize().height > getTabbedPaneWorkArea().getPanelWorkArea().getVisibleRect().height){
								diagram.setExecuteWidth(true);
							}
							else if(getTabbedPaneWorkArea().getPanelWorkArea().getPreferredSize().width > getTabbedPaneWorkArea().getPanelWorkArea().getVisibleRect().width
									&& getTabbedPaneWorkArea().getPanelWorkArea().getPreferredSize().height < getTabbedPaneWorkArea().getPanelWorkArea().getVisibleRect().height){
								diagram.setExecuteHeight(true);
							}
							getTabbedPaneWorkArea().getPanelWorkArea().revalidate();
							getPaintManager().setSizeDiagramOverview(new Rectangle(getNavigator().getSize().width,getNavigator().getSize().height),getNavigator().getPanelOverView());
							getTabbedPaneWorkArea().getPanelWorkArea().repaint();

							if(diagram.getSelectedStereotype() == null)
								getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToDiagram(diagram));
						}
					}
				}
			});
		}
		return MenuItemZoomOut;
	}

	/**
	 * This method initializes CheckBoxMenuItemExplorer
	 * 
	 * @return javax.swing.JCheckBoxMenuItem
	 */
	private JCheckBoxMenuItem getCheckBoxMenuItemExplorer() {
		if (CheckBoxMenuItemExplorer == null) {
			CheckBoxMenuItemExplorer = new JCheckBoxMenuItem();
			CheckBoxMenuItemExplorer
			.setFont(new Font("Dialog", Font.PLAIN, 12));
			CheckBoxMenuItemExplorer.setText("Explorador");
			CheckBoxMenuItemExplorer
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (CheckBoxMenuItemExplorer.getState() == true)
						desk.setAutoHide(treeDiagrams, false);
					else
						desk.setAutoHide(treeDiagrams, true);
				}
			});
		}
		return CheckBoxMenuItemExplorer;
	}

	/**
	 * This method initializes CheckBoxMenuItemActiveDiagram
	 * 
	 * @return javax.swing.JCheckBoxMenuItem
	 */
	private JCheckBoxMenuItem getCheckBoxMenuItemActiveDiagram() {
		if (CheckBoxMenuItemActiveDiagram == null) {
			CheckBoxMenuItemActiveDiagram = new JCheckBoxMenuItem();
			CheckBoxMenuItemActiveDiagram.setFont(new Font("Dialog",
					Font.PLAIN, 12));
			CheckBoxMenuItemActiveDiagram.setText("Diagrama");
			CheckBoxMenuItemActiveDiagram
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (CheckBoxMenuItemActiveDiagram.getState() == true)
						desk.setAutoHide(treeActiveDiagrams, false);
					else
						desk.setAutoHide(treeActiveDiagrams, true);
				}
			});
		}
		return CheckBoxMenuItemActiveDiagram;
	}

	/**
	 * This method initializes CheckBoxMenuItemProperties
	 * 
	 * @return javax.swing.JCheckBoxMenuItem
	 */
	private JCheckBoxMenuItem getCheckBoxMenuItemProperties() {
		if (CheckBoxMenuItemProperties == null) {
			CheckBoxMenuItemProperties = new JCheckBoxMenuItem();
			CheckBoxMenuItemProperties.setFont(new Font("Dialog", Font.PLAIN,
					12));
			CheckBoxMenuItemProperties.setText("Propiedades");
			CheckBoxMenuItemProperties
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (CheckBoxMenuItemProperties.getState() == true)
						desk.setAutoHide(tableProperties, false);
					else
						desk.setAutoHide(tableProperties, true);
				}
			});
		}
		return CheckBoxMenuItemProperties;
	}

	/**
	 * This method initializes CheckBoxMenuItemNavigator
	 * 
	 * @return javax.swing.JCheckBoxMenuItem
	 */
	private JCheckBoxMenuItem getCheckBoxMenuItemNavigator() {
		if (CheckBoxMenuItemNavigator == null) {
			CheckBoxMenuItemNavigator = new JCheckBoxMenuItem();
			CheckBoxMenuItemNavigator
			.setFont(new Font("Dialog", Font.PLAIN, 12));
			CheckBoxMenuItemNavigator.setText("Navegador");
			CheckBoxMenuItemNavigator
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (CheckBoxMenuItemNavigator.getState() == true)
						desk.setAutoHide(navigator, false);
					else
						desk.setAutoHide(navigator, true);
				}
			});
		}
		return CheckBoxMenuItemNavigator;
	}

	/**
	 * This method initializes MenuTools
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getMenuTools() {
		if (MenuTools == null) {
			MenuTools = new JMenu();
			MenuTools.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuTools.setText("Herramientas");
			MenuTools.setMnemonic(KeyEvent.VK_H);
			MenuTools.add(getMenuItemProperties());
		}
		return MenuTools;
	}

	/**
	 * This method initializes MenuItemProperties
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMenuItemProperties() {
		if (MenuItemProperties == null) {
			MenuItemProperties = new JMenuItem();
			MenuItemProperties.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuItemProperties.setText("Propiedades");
		}
		return MenuItemProperties;
	}

	/**
	 * This method initializes MenuHelp
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getMenuHelp() {
		if (MenuHelp == null) {
			MenuHelp = new JMenu();
			MenuHelp.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuHelp.setText("Ayuda");
			MenuHelp.setMnemonic(KeyEvent.VK_Y);
			MenuHelp.add(getMenuItemAbout());
		}
		return MenuHelp;
	}

	/**
	 * This method initializes MenuItemAbout
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMenuItemAbout() {
		if (MenuItemAbout == null) {
			MenuItemAbout = new JMenuItem();
			MenuItemAbout.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuItemAbout.setIcon(new ImageIcon(getClass().getResource(
			"/icons/21.png")));
			MenuItemAbout.setText("Sobre Graficador");
			MenuItemAbout.setMnemonic(KeyEvent.VK_F1);
		}
		return MenuItemAbout;
	}

	/**
	 * This method initializes buttonPaintDesicion
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonPaintDesicion() {
		if (buttonPaintDesicion == null) {
			buttonPaintDesicion = new JButton();
			buttonPaintDesicion.setIcon(new ImageIcon(getClass().getResource(
					"/icons/gif/Decision.gif")));
			buttonPaintDesicion.setToolTipText("Decision");
			buttonPaintDesicion
			.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					setButtonPressed("Decision"); // new
					toggleButtonCursor.setSelected(false);
				}
			});
		}

		return buttonPaintDesicion;
	}

	/**
	 * This method initializes buttonPaintDB
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonPaintDB() {
		if (buttonPaintDB == null) {
			buttonPaintDB = new JButton();
			buttonPaintDB.setIcon(new ImageIcon(getClass().getResource(
					"/icons/database_l.png")));
			buttonPaintDB.setToolTipText("Base de Datos");
			buttonPaintDB.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 1 && e.getButton() == 1) {
						setButtonPressed("BaseDato"); // new
						toggleButtonCursor.setSelected(false);
					}
				}
			});
		}
		return buttonPaintDB;
	}

	/**
	 * Save workspace or workbenches
	 */
	public void savePerspective() {
		try {
			// opcional
			desk.registerDockable(navigator);
			desk.registerDockable(tabbedPaneWorkArea);
			desk.registerDockable(tableProperties);
			desk.registerDockable(treeActiveDiagrams);
			desk.registerDockable(treeDiagrams);
			// opcional
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(perspectiveFile));
			desk.writeXML(out);
			out.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * Save workspace
	 */
	/**
	 * Load WorkSapace o workbenches
	 */
	public void loadPerspective() {// pasarle el splash igual que al
		// constructor
		try {
			desk.registerDockable(navigator);
			desk.registerDockable(tabbedPaneWorkArea);
			desk.registerDockable(tableProperties);
			desk.registerDockable(treeActiveDiagrams);
			desk.registerDockable(treeDiagrams);
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(perspectiveFile));
			desk.readXML(in);
			in.close();
		} catch (Exception ioe) {
			ioe.printStackTrace();
			try {
				desk.registerDockable(navigator);
				desk.registerDockable(tabbedPaneWorkArea);
				desk.registerDockable(tableProperties);
				desk.registerDockable(treeActiveDiagrams);
				desk.registerDockable(treeDiagrams);
				BufferedInputStream defaultIn = new BufferedInputStream(
						new FileInputStream(defaultperspectiveFile));
				desk.readXML(defaultIn);
				defaultIn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Load WorkSapace o workbenches
	 */
	/**
	 * This method initializes tabbedPaneWorkArea
	 * 
	 * @return javax.swing.JButton
	 */
	public TabbedPaneWorkArea getTabbedPaneWorkArea() {
		if (tabbedPaneWorkArea == null) {
			tabbedPaneWorkArea = new TabbedPaneWorkArea(returnThis());
		}
		return tabbedPaneWorkArea;
	}

	private ProcessGraphDocking returnThis() {
		return this;
	}

	/**
	 * This method initializes navigator
	 * 
	 * @return javax.swing.JButton
	 */
	public Navigator getNavigator() {
		if (navigator == null) {
			navigator = new Navigator(returnThis());
		}
		return navigator;
	}

	/**
	 * This method initializes tableProperties
	 * 
	 * @return javax.swing.JButton
	 */
	public TableProperties getTableProperties() {
		if (tableProperties == null) {
			tableProperties = new TableProperties(returnThis());
			tableProperties.getTableProperties().add(getButtonChosserBackGround());
			tableProperties.getTableProperties().add(getButtonChooserLine());
			tableProperties.getTableProperties().add(getComboBoxSelected());
			tableProperties.getTableProperties().add(getComboBoxVisible());
			tableProperties.getTableProperties().add(getComboBoxType());
			tableProperties.getTableProperties().add(getScrollPaneDescription());
		}
		return tableProperties;
	}

	/**
	 * This method initializes treeActiveDiagrams
	 * 
	 * @return javax.swing.JButton
	 */
	public TreeActiveDiagrams getTreeActiveDiagrams() {
		if (treeActiveDiagrams == null) {
			treeActiveDiagrams = new TreeActiveDiagrams(returnThis());
		}
		return treeActiveDiagrams;
	}

	/**
	 * This method initializes treeDiagrams
	 * 
	 * @return javax.swing.JButton
	 */
	public TreeDiagrams getTreeDiagrams() {
		if (treeDiagrams == null) {
			treeDiagrams = new TreeDiagrams(returnThis());
		}
		return treeDiagrams;
	}

	/**
	 * Save toolsbars
	 */
	public void saveToolsBars() {
		try {
			container.registerToolBar(toolBarFile);
			container.registerToolBar(toolBarEdit);
			container.registerToolBar(toolBarZoom);
			container.registerToolBar(toolBarDo);
			// save as xml
			ToolBarIO toolBarIO = new ToolBarIO(container);
			// OutputStream out = new OutputStream();
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(toolBarsFile));
			toolBarIO.writeXML(out);
			// out.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * Save toolsbar
	 */
	/**
	 * Load toolsbar
	 */
	public void loadToolsBars() {// pasarle el splash igual que al
		// constructor
		try {
			container.registerToolBar(toolBarFile);
			container.registerToolBar(toolBarEdit);
			container.registerToolBar(toolBarZoom);
			container.registerToolBar(toolBarDo);
			// load from xml
			ToolBarIO toolBarIO = new ToolBarIO(container);
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(toolBarsFile));
			toolBarIO.readXML(in);
			// in.close();
		} catch (Exception ioe) {
			ioe.printStackTrace();
			try {
				container.registerToolBar(toolBarFile);
				container.registerToolBar(toolBarEdit);
				container.registerToolBar(toolBarZoom);
				container.registerToolBar(toolBarDo);
				ToolBarIO toolBarIO = new ToolBarIO(container);
				BufferedInputStream defaultIn = new BufferedInputStream(
						new FileInputStream(defaultToolBarsFile));
				toolBarIO.readXML(defaultIn);
				// defaultIn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Load toolsbar
	 */

	/**
	 * This method initializes buttonPaintTransition
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonPaintTransition() {
		if (buttonPaintTransition == null) {
			buttonPaintTransition = new JButton();
			buttonPaintTransition.setIcon(new ImageIcon(getClass().getResource(
					"/icons/gif/Synchronous.gif")));
			buttonPaintTransition.setToolTipText("Transicion");
			buttonPaintTransition.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 1 && e.getButton() == 1) {
						setButtonPressed("Transicion"); // new
						toggleButtonCursor.setSelected(false);
					}
				}
			});
		}

		return buttonPaintTransition;
	}

	/**
	 * This method initializes buttonPaintNote
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonPaintNote() {
		if (buttonPaintNote == null) {
			buttonPaintNote = new JButton();
			buttonPaintNote.setIcon(new ImageIcon(getClass().getResource(
					"/icons/gif/Note.gif")));
			buttonPaintNote.setToolTipText("Nota");
			buttonPaintNote.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					setButtonPressed("Nota"); // new
					toggleButtonCursor.setSelected(false);
				}
			});
		}

		return buttonPaintNote;

	}

	/**
	 * This method initializes buttonPaintInitialState
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonPaintInitialState() {
		if (buttonPaintInitialState == null) {
			buttonPaintInitialState = new JButton();
			buttonPaintInitialState.setIcon(new ImageIcon(getClass()
					.getResource("/icons/gif/Initial.gif")));
			buttonPaintInitialState.setToolTipText("Estado Inicial");
			buttonPaintInitialState
			.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 1 && e.getButton() == 1) {
						setButtonPressed("EInicial"); // new
						toggleButtonCursor.setSelected(false);
					}
				}
			});

		}
		return buttonPaintInitialState;
	}

	/**
	 * This method initializes buttonPaintInitialStateMess
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonPaintInitialStateMess() {
		if (buttonPaintInitialStateMess == null) {
			buttonPaintInitialStateMess = new JButton();
			buttonPaintInitialStateMess.setIcon(new ImageIcon(getClass()
					.getResource("/icons/gif/Initial1.gif")));
			buttonPaintInitialStateMess.setToolTipText("Mensaje Inicial");
			buttonPaintInitialStateMess
			.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 1 && e.getButton() == 1) {
						setButtonPressed("EIMensaje"); // new
						toggleButtonCursor.setSelected(false);
					}
				}
			});
		}
		return buttonPaintInitialStateMess;
	}

	/**
	 * This method initializes buttonPanitInitialStateTime
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonPanitInitialStateTime() {
		if (buttonPanitInitialStateTime == null) {
			buttonPanitInitialStateTime = new JButton();
			buttonPanitInitialStateTime.setToolTipText("Tiempo Inicial");
			buttonPanitInitialStateTime.setIcon(new ImageIcon(getClass()
					.getResource("/icons/gif/Initial2.gif")));
			buttonPanitInitialStateTime
			.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 1 && e.getButton() == 1) {
						setButtonPressed("EITiempo"); // new
						toggleButtonCursor.setSelected(false);

					}
				}
			});
		}
		return buttonPanitInitialStateTime;
	}

	/**
	 * This method initializes buttonPaintFinalState
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonPaintFinalState() {
		if (buttonPaintFinalState == null) {
			buttonPaintFinalState = new JButton();
			buttonPaintFinalState.setToolTipText("Estado Final");
			buttonPaintFinalState.setIcon(new ImageIcon(getClass().getResource(
			"/icons/gif/FinalState.gif")));
			buttonPaintFinalState
			.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 1 && e.getButton() == 1) {
						setButtonPressed("EFinal"); // new
						toggleButtonCursor.setSelected(false);
					}
				}
			});
		}
		return buttonPaintFinalState;
	}

	/**
	 * This method initializes buttonPaintAnclarNote
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonPaintAnclarNote() {
		if (buttonPaintAnclarNote == null) {
			buttonPaintAnclarNote = new JButton();
			buttonPaintAnclarNote.setToolTipText("Anclar Nota");
			buttonPaintAnclarNote.setIcon(new ImageIcon(getClass().getResource(
			"/icons/gif/Dependency.gif")));
			buttonPaintAnclarNote.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 1 && e.getButton() == 1) {
						setButtonPressed("Anclar Nota"); // new
						toggleButtonCursor.setSelected(false);
					}
				}
			});
		}
		return buttonPaintAnclarNote;
	}

	/**
	 * This method initializes buttonPaintLazo
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonPaintLazo() {
		if (buttonPaintLazo == null) {
			buttonPaintLazo = new JButton();
			buttonPaintLazo.setToolTipText("Lazo");
			buttonPaintLazo.setIcon(new ImageIcon(getClass().getResource(
			"/icons/gif/Self_del.gif")));
		}
		return buttonPaintLazo;
	}

	/**
	 * This method initializes buttonPaintMacroProc
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonPaintMacroProc() {
		if (buttonPaintMacroProc == null) {
			buttonPaintMacroProc = new JButton();
			buttonPaintMacroProc.setToolTipText("Macro Proceso");
			buttonPaintMacroProc.setIcon(new ImageIcon(getClass().getResource(
			"/icons/gif/MacroProceso.gif")));
			// }
			buttonPaintMacroProc
			.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 1 && e.getButton() == 1) {
						setButtonPressed("Macro"); // new
						toggleButtonCursor.setSelected(false);

					}
				}
			});
		}
		return buttonPaintMacroProc;

	}

	/**
	 * This method initializes buttonPaintProceso
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonPaintProceso() {
		if (buttonPaintProceso == null) {
			buttonPaintProceso = new JButton();
			buttonPaintProceso.setToolTipText("Proceso");
			buttonPaintProceso.setIcon(new ImageIcon(getClass().getResource(
			"/icons/gif/Proceso.gif")));
			buttonPaintProceso
			.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 1 && e.getButton() == 1) {
						setButtonPressed("Proc"); // new
						toggleButtonCursor.setSelected(false);
					}
				}
			});
		}
		return buttonPaintProceso;
	}

	/**
	 * This method initializes buttonPaintSubProc
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonPaintSubProc() {
		if (buttonPaintSubProc == null) {
			buttonPaintSubProc = new JButton();
			buttonPaintSubProc.setToolTipText("SubProceso");
			buttonPaintSubProc.setIcon(new ImageIcon(getClass().getResource(
			"/icons/gif/SubProceso.gif")));
			buttonPaintSubProc
			.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 1 && e.getButton() == 1) {
						setButtonPressed("SubProc"); // new
						toggleButtonCursor.setSelected(false);
					}
				}
			});
		}
		return buttonPaintSubProc;
	}

	/**
	 * This method initializes buttonPaintActServ
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonPaintActServ() {
		if (buttonPaintActServ == null) {
			buttonPaintActServ = new JButton();
			buttonPaintActServ.setToolTipText("Actividad Servicio");
			buttonPaintActServ.setIcon(new ImageIcon(getClass().getResource(
			"/icons/gif/ActividadServ.gif")));
			buttonPaintActServ
			.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 1 && e.getButton() == 1) {
						setButtonPressed("ActServ"); // new
						toggleButtonCursor.setSelected(false);
					}
				}
			});
		}
		return buttonPaintActServ;
	}

	/**
	 * This method initializes buttonPaintActManual
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonPaintActManual() {
		if (buttonPaintActManual == null) {
			buttonPaintActManual = new JButton();
			buttonPaintActManual.setToolTipText("Actividad Manual");
			buttonPaintActManual.setIcon(new ImageIcon(getClass().getResource(
			"/icons/gif/ActividadManual.gif")));
			buttonPaintActManual
			.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 1 && e.getButton() == 1) {
						setButtonPressed("ActManual"); // new
						toggleButtonCursor.setSelected(false);

					}
				}
			});
		}
		return buttonPaintActManual;
	}

	/**
	 * This method initializes buttonPaintClient
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonPaintClient() {
		if (buttonPaintClient == null) {
			buttonPaintClient = new JButton();
			buttonPaintClient.setToolTipText("Cliente");
			buttonPaintClient.setIcon(new ImageIcon(getClass().getResource("/icons/gif/Cliente.gif")));
			buttonPaintClient
			.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 1 && e.getButton() == 1) {
						setButtonPressed("Cliente"); // new
						toggleButtonCursor.setSelected(false);
					}
				}
			});
		}
		return buttonPaintClient;
	}

	public String getButtonPressed() { // new2
		return buttonPressed;
	}

	public void setButtonPressed(String buttonPressed) {
		this.buttonPressed = buttonPressed;
	}

	/**
	 * This method initializes buttonPaintProvee
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonPaintProvee() {
		if (buttonPaintProvee == null) {
			buttonPaintProvee = new JButton();
			// }
			buttonPaintProvee.setToolTipText("Proveedor");
			buttonPaintProvee.setIcon(new ImageIcon(getClass().getResource("/icons/gif/Provee.gif")));
			buttonPaintProvee
			.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 1 && e.getButton() == 1) {
						setButtonPressed("Provee"); // new
						toggleButtonCursor.setSelected(false);
					}
				}
			});

		}
		return buttonPaintProvee;

	}

	public JToggleButton getToggleButtonCursor() {
		if (toggleButtonCursor == null) {
			toggleButtonCursor = new JToggleButton();
			toggleButtonCursor.setIcon(new ImageIcon(getClass().getResource(
					"/icons/Cursor.png")));
			toggleButtonCursor.setSelected(true);

			// }
			toggleButtonCursor.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					toggleButtonCursor.setSelected(true);
					setButtonPressed(""); // new
				}
			});
		}
		return toggleButtonCursor;

	}

	public void setStateToggleButtonCursor(boolean pState) {
		toggleButtonCursor.setSelected(pState);
	}

	public int getTabSelect() {
		return tabSelect;
	}

	public void setTabSelect(int tabSelect) {
		this.tabSelect = tabSelect;
	}

	public void addTabTitle(String tabTitle) {
		listTabsTitles.add(tabTitle);
	}

	public String getTabTitle(int pos) {
		return listTabsTitles.get(pos);
	}

	public int deleteTabTitle(String tabTitle){
		for(int i = 0; i < listTabsTitles.size();i++){
			if(listTabsTitles.get(i).equals(tabTitle)){
				listTabsTitles.remove(i);
				return i;
			}
		}
		return -1;
	}

	public void clearTabTitle(){
		listTabsTitles.clear();
		listTabsTitles.add("Bienvenido");
	}

	public PaintManager getPaintManager() {//ahora
		return paintManager;
	}

	/**
	 * This method initializes MenuItemExportAs
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMenuItemExportAs() {
		if (MenuItemExportAs == null) {
			MenuItemExportAs = new JMenuItem();
			MenuItemExportAs.setText("Exportar como...");
			MenuItemExportAs.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuItemExportAs
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Diagram diagram = new Diagram();//para guardar el diagrama
					diagram = getPaintManager().getActiveDiagram();
					final JFileChooser fileChooser = new JFileChooser();//este es el dialogo seleccionar fichero
					fileChooser.addChoosableFileFilter(new PngFilter());//need
					fileChooser.addChoosableFileFilter(new JpgFilter());//need
					int returnVal = fileChooser
					.showSaveDialog(returnThis());//parametro  parent
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						//fijarme por ACGTool
						Rectangle diagramWindowBounds = getTabbedPaneWorkArea()
						.getPanelWorkArea().getBounds();//aqui tengo las coordenadas del diagrama diagramWindowBounds = new Rectangle( 0 ,0, 1024, 919 );
						//Create a buffered image in which to draw
						BufferedImage bufferedImage = new BufferedImage(
								new Double(diagramWindowBounds.getWidth()/diagram.getZoomFactor()).intValue(),
								new Double(diagramWindowBounds.getHeight() / diagram.getZoomFactor()).intValue(),
								BufferedImage.TYPE_INT_RGB);//creo la imagen segun el width y el heigth
						//Create a graphics contents on the buffered image
						Graphics2D g2d = bufferedImage.createGraphics();//creo un graphic de acuerdo a la imagen creada
						Color bg = getTabbedPaneWorkArea()
						.getPanelWorkArea().getBackground();//le pongo el mismo color del diagrama
						Color newBg = new Color(bg.getRed(), bg
								.getGreen(), bg.getBlue(), 250);//creo un color a partir del anterior y le agrego el parametro alpha
						g2d.setColor(newBg);//asigno color
						g2d.fillRect(0, 0, 
								new Double(diagramWindowBounds.getWidth()/diagram.getZoomFactor()).intValue(),
								new Double(diagramWindowBounds.getHeight() / diagram.getZoomFactor()).intValue());//relleno el graphics con ese color
						g2d.scale(1.0, 1.0);
						/**
						 * nuevo codigo
						 */
						if(diagram.getSwinLine() != null){
							getTabbedPaneWorkArea().getPanelWorkArea().paintSwinLine(g2d, diagram.getSwinLine());
						}
						if(diagram.getPictureList().size() > 0)
							getNavigator().getProcessGraphDocking().getPaintManager().paintRelations(g2d, diagram.getPictureList());

						for (int i = 0; i < diagram.getLength(); i++) {
							diagram.getStereotype(i).PaintStereotype(g2d, false);
							if(diagram.getStereotype(i).getType() == "Nota"){
								getTabbedPaneWorkArea().getPanelWorkArea().drawText(g2d, diagram.getStereotype(i));
							}
						}
						g2d.dispose();//tengo el graphic lleno
						//creando fichero
						File file = fileChooser.getSelectedFile();
						FileFilter fileFilter = fileChooser
						.getFileFilter();
						boolean acept = fileFilter.accept(file);
						if (acept != true) {
							try {
								file = new File(file.getPath()
										+ ((PngFilter) fileFilter)
										.getExtension());
							} catch (Exception e1) {
								file = new File(file.getPath()
										+ ((JpgFilter) fileFilter)
										.getExtension());
							}
						}
						RenderedImage rendImage = bufferedImage;
						//  Write generated image to a file
						try {
							if (fileFilter instanceof PngFilter) {
								// Save as PNG									
								ImageIO.write(rendImage, "png", file);
							}
							if (fileFilter instanceof JpgFilter) {
								// Save as JPG
								ImageIO.write(rendImage, "jpg", file);
							}
						} catch (IOException e1) {
						}
					} else {//cancelado por el usuario

					}
					repaint();
				}
			});
		}
		return MenuItemExportAs;
	}

	/**
	 * Actions Undo Redo
	 */
	public void undoAction(){
		Diagram diagramActive = getPaintManager().getActiveDiagram();
		ArrayList<Stereotype> arrayFinal = new ArrayList<Stereotype>();
		if(buttonUndo.isEnabled()){//para que se deshabilite el mismo
			UndoAction undoAction = diagramActive.getListUndo().get(diagramActive.getListUndo().size() - 1);
			diagramActive.getListUndo().remove(diagramActive.getListUndo().size() - 1);
			if(undoAction.getActionType() == "edit"){
				//buttonRedo.setEnabled(true);
				RedoAction redoAction = new RedoAction("edit");

				diagramActive.unselectAllStereotype();//17/1/08
				ArrayList<Stereotype> arrayStereotypes = diagramActive.getPictureList();
				for(int i = 0; i < undoAction.getArrayActions().size(); i++){
					Stereotype stereotype = undoAction.getArrayActions().get(i);
					for(int j = 0; j < arrayStereotypes.size(); j++){
						if(arrayStereotypes.get(j).getId() == stereotype.getId()){
							redoAction.getArrayActions().add(arrayStereotypes.get(j));
							diagramActive.getPictureList().remove(j);
							diagramActive.getPictureList().add(j, stereotype);
							/*if(stereotype.getRelatedStereotypes().size() == 0 && getPaintManager().findRelationDestiny(stereotype).size() == 0)
				setBandera(true);
			    else
				setBandera(false);*/
							arrayFinal.add(stereotype);
						}
					}
				}

				for (Stereotype stereotype : redoAction.getArrayActions()) {
					stereotype.setSelected(true);
				}
				diagramActive.getListRedo().add(redoAction);
			}
			else if(undoAction.getActionType() == "paint"){
				//buttonRedo.setEnabled(true);
				if(undoAction.getArrayActions().size() > 0){
					RedoAction redoAction = new RedoAction("paint");
					ArrayList<Stereotype> arrayStereotypes = new ArrayList<Stereotype>(diagramActive.getPictureList());

					for(int i = 0; i < arrayStereotypes.size() - 1; i++){
						arrayStereotypes.get(i).setSelected(false);
					}
					redoAction.setArrayActions(arrayStereotypes);
					redoAction.getArrayActions().get(redoAction.getArrayActions().size() - 1).setSelected(true);
					diagramActive.getListRedo().add(redoAction);
					ArrayList<Stereotype> arrayDistinct = new ArrayList<Stereotype>();
					for (Stereotype stereotype : diagramActive.getPictureList()) {
						boolean distinct = false;
						for (Stereotype stereotype2 : undoAction.getArrayActions()) {
							if(stereotype2.getId() == stereotype.getId()){
								distinct = true;
								break;
							}
						}
						if(!distinct){
							arrayDistinct.add(stereotype);
						}
					}
					for (Stereotype stereotype : arrayDistinct) {
						if(stereotype.getDiagramIds().size() > 0){
							for (Integer idDiagram : stereotype.getDiagramIds()) {
								Diagram diagramDelete = getPaintManager().findById(idDiagram);
								deleteTabTitle(diagramDelete.getName());
								//getProcessGraphDocking().getPaintManager().deleteDiagram(tabTitle);
								//getPaintManager().getDiagramByName(tabTitle).setSelected(false);
								int selectedIndex = -1;
								for (int j = 0; j < getTabbedPaneWorkArea().getTabbedPane().getTabCount(); j++) {
									if(getTabbedPaneWorkArea().getTituloAt(j).equals(diagramDelete.getName())){
										selectedIndex = j;
									}
								}
								if(selectedIndex != -1){
									getTabbedPaneWorkArea().getTabbedPane().remove(selectedIndex);
									if(getTabbedPaneWorkArea().getTabbedPane().getTabCount() == 1){//solo queda la tab welcome
										getTreeActiveDiagrams().setRootVisible(false);
										getTreeActiveDiagrams().setDefaultTreeModelDiagrams(getTreeActiveDiagrams().getDefaultTreeModelDiagrams());
										getTableProperties().setDefaultComboBoxModelProcess(getTableProperties().getDefaultComboBoxModelProcess());
										getTableProperties().getTableProperties().setModel(getTableProperties().getDefaultTableModelProperties());

										//getProcessGraphDocking().getTreeDiagrams().setRootVisible(false);
										//getProcessGraphDocking().getTreeDiagrams().setDefaultTreeModelDiagrams(getProcessGraphDocking().getTreeDiagrams().getDefaultTreeModelDiagrams());

										getPaintManager().unActiveAllDiagrams();
										repaint();
									}else{
										String newTabTitle = getTabbedPaneWorkArea().getTabbedPane().getTitleAt(getTabbedPaneWorkArea().getTabbedPane().getSelectedIndex());
										getPaintManager().getDiagramByName(newTabTitle).setSelected(true);
										int posTab = getTabbedPaneWorkArea().getTabbedPane().getSelectedIndex();
										getTabbedPaneWorkArea().getTabbedPane().remove(posTab);//eliminar porque el insertar agrega, no reemplaza
										getTabbedPaneWorkArea().getTabbedPane().insertTab(newTabTitle, null, getTabbedPaneWorkArea().getPanelWorkAreaBack(),null, posTab);//
										getTabbedPaneWorkArea().getTabbedPane().setSelectedIndex(posTab);
										setTabSelect(getTabbedPaneWorkArea().getTabbedPane().getSelectedIndex());//aqui tengo el numero de la tab seleccionada
										//actualizando tabs
										for(int k =0;k<getTabbedPaneWorkArea().getTabbedPane().getTabCount();k++){
											getTabbedPaneWorkArea().getTabbedPane().setTitleAt(k, getTabTitle(k));
											getTabbedPaneWorkArea().getTabbedPane().setToolTipTextAt(k, getTabTitle(k));//tip
										}
										//getProcessGraphDocking().getTreeDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeDiagrams(getProcessGraphDocking().getPaintManager().getDiagramList()));
									}
									getPaintManager().deleteDiagram(diagramDelete.getName());
									Long idDiagramDelete = PaintManager.diagramaService.findDiagrama(new Long(diagramDelete.getId()));
									if(idDiagramDelete != -1){//crear stereotype y propiedades
										PaintManager.diagramaService.deleteDiagrama(idDiagramDelete);
									}
								}
							}
						}
						Long idStereotype = PaintManager.stereotypeService.findStereotype(new Long(stereotype.getId()));
						if(idStereotype != -1){//crear stereotype y propiedades
							PaintManager.stereotypeService.deleteStereotype(idStereotype);
						}
					}
					diagramActive.getPictureList().clear();
					diagramActive.setPictureList(undoAction.getArrayActions());
				}
				else{
					RedoAction redoAction = new RedoAction("paint");
					ArrayList<Stereotype> arrayStereotypes = new ArrayList<Stereotype>(diagramActive.getPictureList());

					redoAction.setArrayActions(arrayStereotypes);
					redoAction.getArrayActions().get(redoAction.getArrayActions().size() - 1).setSelected(true);
					diagramActive.getListRedo().add(redoAction);

					ArrayList<Stereotype> arrayDistinct = new ArrayList<Stereotype>();
					for (Stereotype stereotype : diagramActive.getPictureList()) {
						boolean distinct = false;
						for (Stereotype stereotype2 : undoAction.getArrayActions()) {
							if(stereotype2.getId() == stereotype.getId()){
								distinct = true;
								break;
							}
						}
						if(!distinct){
							arrayDistinct.add(stereotype);
						}
					}
					for (Stereotype stereotype : arrayDistinct) {
						if(stereotype.getDiagramIds().size() > 0){
							for (Integer idDiagram : stereotype.getDiagramIds()) {
								Diagram diagramDelete = getPaintManager().findById(idDiagram);
								deleteTabTitle(diagramDelete.getName());
								//getProcessGraphDocking().getPaintManager().deleteDiagram(tabTitle);
								//getPaintManager().getDiagramByName(tabTitle).setSelected(false);
								int selectedIndex = -1;
								for (int j = 0; j < getTabbedPaneWorkArea().getTabbedPane().getTabCount(); j++) {
									if(getTabbedPaneWorkArea().getTituloAt(j).equals(diagramDelete.getName())){
										selectedIndex = j;
									}
								}
								if(selectedIndex != -1){
									getTabbedPaneWorkArea().getTabbedPane().remove(selectedIndex);
									if(getTabbedPaneWorkArea().getTabbedPane().getTabCount() == 1){//solo queda la tab welcome
										getTreeActiveDiagrams().setRootVisible(false);
										getTreeActiveDiagrams().setDefaultTreeModelDiagrams(getTreeActiveDiagrams().getDefaultTreeModelDiagrams());
										getTableProperties().setDefaultComboBoxModelProcess(getTableProperties().getDefaultComboBoxModelProcess());
										getTableProperties().getTableProperties().setModel(getTableProperties().getDefaultTableModelProperties());

										//getProcessGraphDocking().getTreeDiagrams().setRootVisible(false);
										//getProcessGraphDocking().getTreeDiagrams().setDefaultTreeModelDiagrams(getProcessGraphDocking().getTreeDiagrams().getDefaultTreeModelDiagrams());

										getPaintManager().unActiveAllDiagrams();
										repaint();
									}else{
										String newTabTitle = getTabbedPaneWorkArea().getTabbedPane().getTitleAt(getTabbedPaneWorkArea().getTabbedPane().getSelectedIndex());
										getPaintManager().getDiagramByName(newTabTitle).setSelected(true);
										int posTab = getTabbedPaneWorkArea().getTabbedPane().getSelectedIndex();
										getTabbedPaneWorkArea().getTabbedPane().remove(posTab);//eliminar porque el insertar agrega, no reemplaza
										getTabbedPaneWorkArea().getTabbedPane().insertTab(newTabTitle, null, getTabbedPaneWorkArea().getPanelWorkAreaBack(),null, posTab);//
										getTabbedPaneWorkArea().getTabbedPane().setSelectedIndex(posTab);
										setTabSelect(getTabbedPaneWorkArea().getTabbedPane().getSelectedIndex());//aqui tengo el numero de la tab seleccionada
										//actualizando tabs
										for(int k =0;k<getTabbedPaneWorkArea().getTabbedPane().getTabCount();k++){
											getTabbedPaneWorkArea().getTabbedPane().setTitleAt(k, getTabTitle(k));
											getTabbedPaneWorkArea().getTabbedPane().setToolTipTextAt(k, getTabTitle(k));//tip
										}
										//getProcessGraphDocking().getTreeDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeDiagrams(getProcessGraphDocking().getPaintManager().getDiagramList()));
									}
									getPaintManager().deleteDiagram(diagramDelete.getName());
									Long idDiagramDelete = PaintManager.diagramaService.findDiagrama(new Long(diagramDelete.getId()));
									if(idDiagramDelete != -1){//crear stereotype y propiedades
										PaintManager.diagramaService.deleteDiagrama(idDiagramDelete);
									}
								}
							}
						}
						Long idStereotype = PaintManager.stereotypeService.findStereotype(new Long(stereotype.getId()));
						if(idStereotype != -1){//crear stereotype y propiedades
							PaintManager.stereotypeService.deleteStereotype(idStereotype);
						}
					}
					diagramActive.getPictureList().clear();
					//buttonUndo.setEnabled(false);
				}
			}else if(undoAction.getActionType() == "supr"){
				RedoAction redoAction = new RedoAction("supr");
				diagramActive.unselectAllStereotype();//17/1/08

				if(undoAction.getArrayRelations().size() > 0){
					for (Relation relation : undoAction.getArrayRelations()) {
						for (Stereotype stereotype : diagramActive.getPictureList()) {
							if(relation.getParentId() == stereotype.getId()){
								redoAction.getArrayRelations().add(relation);
								stereotype.addRelation(relation);
							}
							//redoAction.getArrayActions().add(stereotype);
						}
					}
				}
				for(int i = 0; i < undoAction.getArrayActions().size(); i++){
					Stereotype stereotype = undoAction.getArrayActions().get(i);
					redoAction.getArrayActions().add(stereotype);
					diagramActive.getPictureList().add(stereotype);
				}
				diagramActive.getListRedo().add(redoAction);
			}
			else if(undoAction.getActionType() == "paste"){
				RedoAction redoAction = new RedoAction("paste");
				diagramActive.unselectAllStereotype();//17/1/08
				ArrayList<Stereotype> arrayStereotypes = new ArrayList<Stereotype>(diagramActive.getPictureList());
				for(int i = 0; i < undoAction.getArrayActions().size(); i++){
					Stereotype stereotype = undoAction.getArrayActions().get(i);
					for(int j = 0; j < arrayStereotypes.size(); j++){
						if(arrayStereotypes.get(j).getId() == stereotype.getId()){
							redoAction.getArrayActions().add(stereotype);
							diagramActive.deleteStereotypeById(stereotype.getId());
							//diagramActive.getPictureList().remove(j);
						}
					}
				}
				for (Stereotype stereotype : redoAction.getArrayActions()) {
					stereotype.setSelected(true);
				}
				diagramActive.getListRedo().add(redoAction);

			}
			if(diagramActive.getListUndo().size() == 0){
				buttonUndo.setEnabled(false);
				MenuItemUndo.setEnabled(false);
			}

			buttonRedo.setEnabled(true);
			MenuItemRedo.setEnabled(true);
			getTabbedPaneWorkArea().repaint();//test

			for (Stereotype stereotype : arrayFinal) {
				ArrayList<Stereotype> array = new ArrayList<Stereotype>(getPaintManager().findRelationDestiny(stereotype));
				if( array.size() > 0){
					for (Stereotype stereotypeContent : array) {
						int xInicial = stereotypeContent.getInicialPoint().x + stereotypeContent.getWidth()/2;
						int yInicial = stereotypeContent.getInicialPoint().y + stereotypeContent.getHeigth()/2;
						int xFinal = stereotype.getInicialPoint().x + stereotype.getWidth()/2;
						int yFinal = stereotype.getInicialPoint().y + stereotype.getHeigth()/2;
						Point point1 = new Point(xInicial, yInicial);
						Point point2 = new Point(xFinal, yFinal);
						Relation relation = getPaintManager().calculatePointsRelation(point1, point2,stereotypeContent,stereotype);
						stereotypeContent.updateRelation(stereotype, relation);
					}
				}
				if(stereotype.getRelatedStereotypes().size() > 0){
					int xInicial = stereotype.getInicialPoint().x + stereotype.getWidth()/2;
					int yInicial = stereotype.getInicialPoint().y + stereotype.getHeigth()/2;
					Point point1 = new Point(xInicial, yInicial);
					for (Relation relation : stereotype.getRelatedStereotypes()) {
						for (Stereotype stereotypeDiagram : diagramActive.getPictureList()) {
							if(relation.getStereotype().getId() == stereotypeDiagram.getId()){
								relation.setStereotype(stereotypeDiagram);
							}
						}
						//relation.setStereotype(diagramActive.findById(relation.getStereotype().getId()));
						int xFinal = relation.getStereotype().getInicialPoint().x + relation.getStereotype().getWidth()/2;
						int yFinal = relation.getStereotype().getInicialPoint().y + relation.getStereotype().getHeigth()/2;
						Point point2 = new Point(xFinal, yFinal);
						Relation relationEvaluate = getPaintManager().calculatePointsRelation(point1, point2,stereotype,relation.getStereotype());
						stereotype.updateRelation(relation.getStereotype(), relationEvaluate);
					}
				}
			}
			for (Stereotype stereotype : diagramActive.getPictureList()) {
				for (Stereotype stereotypeArray : arrayFinal) {
					if(stereotypeArray.getId() == stereotype.getId()){
						stereotype.setRelatedStereotypes(stereotypeArray.getRelatedStereotypes());
					}
				}
			}
		}
	}

	public void redoAction(){
		Diagram diagramActive = getPaintManager().getActiveDiagram();
		if(buttonRedo.isEnabled()){
			RedoAction redoAction = diagramActive.getListRedo().get(diagramActive.getListRedo().size() - 1);
			ArrayList<Stereotype> arrayFinal = new ArrayList<Stereotype>();
			diagramActive.getListRedo().remove(diagramActive.getListRedo().size() - 1);
			if(redoAction.getActionType() == "edit"){
				UndoAction undoAction = new UndoAction("edit");
				diagramActive.unselectAllStereotype();//17/1/08
				ArrayList<Stereotype> arrayStereotypes = getPaintManager().getActiveDiagram().getPictureList();
				for(int i = 0; i < redoAction.getArrayActions().size(); i++){
					Stereotype stereotype = redoAction.getArrayActions().get(i);
					for(int j = 0; j < arrayStereotypes.size(); j++){
						if(arrayStereotypes.get(j).getId() == stereotype.getId()){
							undoAction.getArrayActions().add(arrayStereotypes.get(j));
							getPaintManager().getActiveDiagram().getPictureList().remove(j);
							getPaintManager().getActiveDiagram().getPictureList().add(j, stereotype);
							arrayFinal.add(stereotype);
						}
					}
				}
				for (Stereotype stereotype : undoAction.getArrayActions()) {
					stereotype.setSelected(true);
				}
				diagramActive.getListUndo().add(undoAction);
			}
			else if(redoAction.getActionType() == "paint"){
				UndoAction undoAction = new UndoAction("paint");
				if(redoAction.getArrayActions().size() == 1){
					getPaintManager().getActiveDiagram().setPictureList(redoAction.getArrayActions());
				}
				else if(redoAction.getArrayActions().size() > 1){
					undoAction.setArrayActions(diagramActive.getPictureList());
					getPaintManager().getActiveDiagram().setPictureList(redoAction.getArrayActions());
				}
				diagramActive.getListUndo().add(undoAction);
			}else if(redoAction.getActionType() == "supr"){
				UndoAction undoAction = new UndoAction("supr");
				undoAction.setArrayActions(redoAction.getArrayActions());

				if(redoAction.getArrayRelations().size() > 0){
					diagramActive.deleteRelations(redoAction.getArrayRelations());
					undoAction.setArrayRelations(redoAction.getArrayRelations());
				}
				for(int i = 0; i < redoAction.getArrayActions().size(); i++){
					Stereotype stereotype = redoAction.getArrayActions().get(i);
					for(int j = 0; j < diagramActive.getLength(); j++){
						if(diagramActive.getStereotype(j).getId() == stereotype.getId()){
							getPaintManager().getActiveDiagram().getPictureList().remove(j);
						}
					}
				}
				diagramActive.getListUndo().add(undoAction);
			}else if(redoAction.getActionType() == "paste"){
				UndoAction undoAction = new UndoAction("paste");
				diagramActive.unselectAllStereotype();//17/1/08
				//ArrayList<Stereotype> arrayStereotypes = new ArrayList<Stereotype>(getPaintManager().getActiveDiagram().getPictureList());
				for(int i = 0; i < redoAction.getArrayActions().size(); i++){
					undoAction.getArrayActions().add(redoAction.getArrayActions().get(i));
					getPaintManager().getActiveDiagram().getPictureList().add(redoAction.getArrayActions().get(i));
				}
				for (Stereotype stereotype : undoAction.getArrayActions()) {
					stereotype.setSelected(true);
				}
				diagramActive.getListUndo().add(undoAction);
			}
			if(diagramActive.getListRedo().size() == 0){
				buttonRedo.setEnabled(false);
				MenuItemRedo.setEnabled(false);
			}
			buttonUndo.setEnabled(true);
			MenuItemUndo.setEnabled(true);
			getTabbedPaneWorkArea().repaint();//test

			//if(isBandera() == false){
			for (Stereotype stereotype : arrayFinal) {
				ArrayList<Stereotype> array = new ArrayList<Stereotype>(getPaintManager().findRelationDestiny(stereotype));
				if( array.size() > 0){
					for (Stereotype stereotypeContent : array) {
						int xInicial = stereotypeContent.getInicialPoint().x + stereotypeContent.getWidth()/2;
						int yInicial = stereotypeContent.getInicialPoint().y + stereotypeContent.getHeigth()/2;
						int xFinal = stereotype.getInicialPoint().x + stereotype.getWidth()/2;
						int yFinal = stereotype.getInicialPoint().y + stereotype.getHeigth()/2;
						Point point1 = new Point(xInicial, yInicial);
						Point point2 = new Point(xFinal, yFinal);
						Relation relation = getPaintManager().calculatePointsRelation(point1, point2,stereotypeContent,stereotype);
						stereotypeContent.updateRelation(stereotype, relation);
					}
				}
				if(stereotype.getRelatedStereotypes().size() > 0){

					int xInicial = stereotype.getInicialPoint().x + stereotype.getWidth()/2;
					int yInicial = stereotype.getInicialPoint().y + stereotype.getHeigth()/2;
					Point point1 = new Point(xInicial, yInicial);
					for (Relation relation : stereotype.getRelatedStereotypes()) {
						for (Stereotype stereotypeDiagram : diagramActive.getPictureList()) {
							if(relation.getStereotype().getId() == stereotypeDiagram.getId()){
								relation.setStereotype(stereotypeDiagram);
							}
						}
						int xFinal = relation.getStereotype().getInicialPoint().x + relation.getStereotype().getWidth()/2;
						int yFinal = relation.getStereotype().getInicialPoint().y + relation.getStereotype().getHeigth()/2;
						Point point2 = new Point(xFinal, yFinal);
						Relation relationEvaluate = getPaintManager().calculatePointsRelation(point1, point2,stereotype,relation.getStereotype());
						stereotype.updateRelation(relation.getStereotype(), relationEvaluate);
					}
				}
			}
			for (Stereotype stereotype : diagramActive.getPictureList()) {
				for (Stereotype stereotypeArray : arrayFinal) {
					if(stereotypeArray.getId() == stereotype.getId()){
						stereotype.setRelatedStereotypes(stereotypeArray.getRelatedStereotypes());
					}
				}
			}
			//}
			// setBandera(false);
		}
	}

	/**
	 * This method initializes buttonChosserBackGround	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getButtonChosserBackGround() {
		if (buttonChosserBackGround == null) {
			buttonChosserBackGround = new JButton();
			buttonChosserBackGround.setSize(new Dimension(34, 13));
			buttonChosserBackGround.setText("...");
			buttonChosserBackGround.setVisible(false);
			buttonChosserBackGround.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Diagram diagramAct = getPaintManager().getActiveDiagram();
					if(diagramAct.getSelectedStereotype() == null){
						Color color = JColorChooser.showDialog(returnThis(), "Seleccione el Color de Fondo del Diagrama", diagramAct.getBackground());
						if(color != null){
							diagramAct.setBackground(color);
							getTabbedPaneWorkArea().setColorToPanelWorkArea();
							getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToDiagram(diagramAct));
						}
					}else{
						Color color = JColorChooser.showDialog(returnThis(), "Seleccione el Color de Fondo del Estereotipo", diagramAct.getSelectedStereotype().getBackground());
						if(color != null){
							Stereotype undoStereotype = new Stereotype(diagramAct.getSelectedStereotype());
							undoStereotype.setSelected(true);
							UndoAction action = new UndoAction("edit");
							action.getArrayActions().add(undoStereotype);
							if(diagramAct.getListRedo().size() > 0){
								diagramAct.getListRedo().clear();
								getButtonRedo().setEnabled(false);
								getMenuItemRedo().setEnabled(false);
								getTabbedPaneWorkArea().getMenuItemRedo().setEnabled(false);
							}
							diagramAct.getListUndo().add(action);
							diagramAct.getSelectedStereotype().setBackground(color);
							getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToStereotype(diagramAct.getSelectedStereotype()));
							getTabbedPaneWorkArea().repaint();
						}
					}
				}
			});
		}
		return buttonChosserBackGround;
	}

	/**
	 * This method initializes buttonChooserLine	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getButtonChooserLine() {
		if (buttonChooserLine == null) {
			buttonChooserLine = new JButton();
			buttonChooserLine.setSize(new Dimension(33, 14));
			buttonChooserLine.setText("...");
			buttonChooserLine.setVisible(false);
			buttonChooserLine.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Stereotype stereotype = getPaintManager().getActiveDiagram().getSelectedStereotype();
					if(stereotype != null){
						Color color = JColorChooser.showDialog(returnThis(), "Seleccione el Color de Linea del Estereotipo", stereotype.getLineColor());
						if(color != null){
							Stereotype undoStereotype = new Stereotype(getPaintManager().getActiveDiagram().getSelectedStereotype());
							undoStereotype.setSelected(true);
							UndoAction action = new UndoAction("edit");
							action.getArrayActions().add(undoStereotype);
							if(getPaintManager().getActiveDiagram().getListRedo().size() > 0){
								getPaintManager().getActiveDiagram().getListRedo().clear();
								getButtonRedo().setEnabled(false);
								getMenuItemRedo().setEnabled(false);
								getTabbedPaneWorkArea().getMenuItemRedo().setEnabled(false);
							}
							getPaintManager().getActiveDiagram().getListUndo().add(action);
							stereotype.setLineColor(color);
							getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToStereotype(stereotype));
							getTabbedPaneWorkArea().repaint();
						}
					}
				}
			});
		}
		return buttonChooserLine;
	}

	/**
	 * This method initializes comboBoxSelected	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	public JComboBox getComboBoxSelected() {
		if (comboBoxSelected == null) {
			comboBoxSelected = new JComboBox();
			comboBoxSelected.setSize(new Dimension(128, 14));
			comboBoxSelected.setVisible(false);
			comboBoxSelected.setModel(getDefaultComboBoxModelSelected());
			comboBoxSelected.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Stereotype stereotypeSel = getPaintManager().getActiveDiagram().getSelectedStereotype();
					if(stereotypeSel != null){
						if(comboBoxSelected.getSelectedItem().toString() == "true"){
							stereotypeSel.setSelected(true);
						}else{
							stereotypeSel.setSelected(false);
							getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToDiagram(getPaintManager().getActiveDiagram()));
							getTableProperties().getComboBoxProcess().getModel().setSelectedItem("<<Procesos>>");
							getComboBoxSelected().setVisible(false);
							getTreeActiveDiagrams().getTreeActiveDiagrams().setSelectionRow(0);
						}
						getTabbedPaneWorkArea().repaint();
					}
				}
			});
		}
		return comboBoxSelected;
	}

	public DefaultComboBoxModel getDefaultComboBoxModelSelected() {
		if (defaultComboBoxModelSelected == null) {
			defaultComboBoxModelSelected = new DefaultComboBoxModel();
			defaultComboBoxModelSelected.addElement(true);
			defaultComboBoxModelSelected.addElement(false);
		}
		return defaultComboBoxModelSelected;
	}

	/**
	 * This method initializes comboBoxVisible	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	public JComboBox getComboBoxVisible() {
		if (comboBoxVisible == null) {
			comboBoxVisible = new JComboBox();
			comboBoxVisible.setSize(new Dimension(127, 19));
			comboBoxVisible.setVisible(false);
			comboBoxVisible.setModel(getDefaultComboBoxModelVisible());
			comboBoxVisible.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Stereotype stereotypeSel = getPaintManager().getActiveDiagram().getSelectedStereotype();
					if(stereotypeSel != null){
						if(comboBoxVisible.getSelectedItem().toString() == "true"){
							stereotypeSel.setVisible(true);
						}else{
							stereotypeSel.setVisible(false);
							comboBoxVisible.setVisible(false);
							getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToStereotype(stereotypeSel));
						}
						getTabbedPaneWorkArea().repaint();
					}
				}
			});
		}
		return comboBoxVisible;
	}

	public DefaultComboBoxModel getDefaultComboBoxModelVisible() {
		if (defaultComboBoxModelVisible == null) {
			defaultComboBoxModelVisible = new DefaultComboBoxModel();
			defaultComboBoxModelVisible.addElement(true);
			defaultComboBoxModelVisible.addElement(false);
		}
		return defaultComboBoxModelVisible;
	}

	/**
	 * This method initializes comboBoxType	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	public JComboBox getComboBoxType() {
		if (comboBoxType == null) {
			comboBoxType = new JComboBox();
			comboBoxType.setSize(new Dimension(130, 18));
			comboBoxType.setVisible(false);
			comboBoxType.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Stereotype stereotypeSel = getPaintManager().getActiveDiagram().getSelectedStereotype();
					String type = comboBoxType.getSelectedItem().toString();
					stereotypeSel.setType(type);
					getTabbedPaneWorkArea().repaint();
					comboBoxType.setVisible(false);
					getTreeActiveDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeActiveDiagrams(getPaintManager().getActiveDiagram()));
					getTreeDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeDiagrams(getPaintManager().getDiagramList(), returnThis()));
					getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToStereotype(stereotypeSel));
					getTableProperties().setDefaultComboBoxModelProcess(FactoryModel.factory.getModelForComboboxProcess(getPaintManager().getActiveDiagram()));
					if(stereotypeSel.getType() != "Nota")
						getTableProperties().getComboBoxProcess().getModel().setSelectedItem(stereotypeSel.getName());
				}
			});
		}
		return comboBoxType;
	}

	public DefaultComboBoxModel getDefaultComboBoxModelType() {
		if (defaultComboBoxModelType == null) {
			defaultComboBoxModelType = new DefaultComboBoxModel();
			defaultComboBoxModelType.addElement("Macro");
			defaultComboBoxModelType.addElement("Proc");
			defaultComboBoxModelType.addElement("SubProc");
			defaultComboBoxModelType.addElement("ActServ");
			defaultComboBoxModelType.addElement("ActManual");
			defaultComboBoxModelType.addElement("Cliente");
			defaultComboBoxModelType.addElement("Provee");
		}
		return defaultComboBoxModelType;
	}

	public DefaultComboBoxModel getDefaultComboBoxModelTypeEstate() {
		if (defaultComboBoxModelTypeEstate == null) {
			defaultComboBoxModelTypeEstate = new DefaultComboBoxModel();
			defaultComboBoxModelTypeEstate.addElement("EITiempo");
			defaultComboBoxModelTypeEstate.addElement("EIMensaje");
			defaultComboBoxModelTypeEstate.addElement("EInicial");
			defaultComboBoxModelTypeEstate.addElement("EFinal");
		}
		return defaultComboBoxModelTypeEstate;
	}

	public JTextField getJTextFieldName() {
		if(jTextFieldName == null){

			jTextFieldName = new JTextField();
			setSizeFieldName(jTextFieldName.getFont().getSize());
			jTextFieldName.setVisible(false);
			jTextFieldName.setSize( new Dimension(120, 13) );
			jTextFieldName.setBorder( BorderFactory.createCompoundBorder(null, null) );
			jTextFieldName.addFocusListener( new java.awt.event.FocusAdapter() {
				public void focusGained( java.awt.event.FocusEvent e ) {
					//jTextFieldName.select( 0, jTextFieldName.getText().length() );
					jTextFieldName.selectAll();
				}
			} );
			jTextFieldName.addKeyListener(new java.awt.event.KeyAdapter() {   
				public void keyPressed(java.awt.event.KeyEvent e) {  
					if((e.getModifiersEx() == 128 && e.getKeyCode() == 67) ||(e.getModifiersEx() == 128 && e.getKeyCode() == 86)
							|| (e.getModifiersEx() == 128 && e.getKeyCode() == 88)){
						jTextFieldName.setEditable(false);
					}
					else
						jTextFieldName.setEditable(true);

					if(e.getKeyCode() == 10 || e.getKeyCode() == 8 || e.getKeyCode() == 127)
						valid = false;
					else 
						valid = true;

					if(e.getKeyCode() == 8 || e.getKeyCode() == 10 || e.getKeyCode() == 16 || e.getKeyCode() == 17 ){
						posibleTyped = true;
					}
					if(e.getKeyCode() == 10){
						getPaintManager().getActiveDiagram().getSelectedStereotype().setName(jTextFieldName.getText());
						jTextFieldName.setVisible(false);
						getTreeActiveDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeActiveDiagrams(getPaintManager().getActiveDiagram()));
						getTreeDiagrams().setDefaultTreeModelDiagrams(FactoryModel.factory.getModelForTreeDiagrams(getPaintManager().getDiagramList(), returnThis()));
						getTableProperties().setDefaultComboBoxModelProcess(FactoryModel.factory.getModelForComboboxProcess(getPaintManager().getActiveDiagram()));
						getTableProperties().getComboBoxProcess().getModel().setSelectedItem(jTextFieldName.getText());
					}
				}   

				public void keyTyped(java.awt.event.KeyEvent e) {
					if(valid){
						setNonKeyTyped(false);
						int start = jTextFieldName.getSelectionStart();
						int end = jTextFieldName.getSelectionEnd();
						String resultString1 = jTextFieldName.getText().substring(0, start);
						String resultString2 = jTextFieldName.getText().substring(end, jTextFieldName.getText().length());
						setStringTyped(resultString1+e.getKeyChar()+resultString2);
						if(posibleTyped == false){
							Stereotype undoStereotype = new Stereotype(getPaintManager().getActiveDiagram().getSelectedStereotype());
							countToCompareNote++;
							if(countToCompareNote == 1){
								undoStereotype.setSelected(true);
								UndoAction action = new UndoAction("edit");
								action.getArrayActions().add(undoStereotype);
								if(getPaintManager().getActiveDiagram().getListRedo().size() > 0){
									getPaintManager().getActiveDiagram().getListRedo().clear();
									getButtonRedo().setEnabled(false);
									getMenuItemRedo().setEnabled(false);
									getTabbedPaneWorkArea().getMenuItemRedo().setEnabled(false);
								}
								getPaintManager().getActiveDiagram().getListUndo().add(action);
							}
						}
					}
				}
			});
		}
		return jTextFieldName;
	}

	public void showBounstoJtext( String text, String tipo ) {
		if(getPaintManager().getActiveDiagram().getZoomFactor() >= 1.0){
			if (tipo == "Macro" || tipo == "Proc" || tipo == "SubProc" || tipo == "ActServ"
				|| tipo == "ActManual" || tipo == "Cliente" || tipo == "Provee") {
				Double zoom = new Double(getPaintManager().getActiveDiagram().getZoomFactor());
				int x = new Double(getTabbedPaneWorkArea().getStereotypeSelect().getInicialPoint().x*zoom + 1*zoom).intValue();
				int y = new Double(getTabbedPaneWorkArea().getStereotypeSelect().getInicialPoint().y*zoom + 23*zoom).intValue() ;
				int w = new Double(getTabbedPaneWorkArea().getStereotypeSelect().getWidth()*zoom - 2*zoom).intValue();
				int h = new Double(13*zoom).intValue();
				jTextFieldName.setBackground( getTabbedPaneWorkArea().getStereotypeSelect().getBackground().brighter() );
				jTextFieldName.setBounds( new Rectangle( x, y, w, h ) );
				jTextFieldName.setText( text );
				jTextFieldName.setVisible( true );
				jTextFieldName.grabFocus();
			}
			else if(tipo == "Nota"){
				Double zoom = new Double(getPaintManager().getActiveDiagram().getZoomFactor());
				int x = new Double(getTabbedPaneWorkArea().getStereotypeSelect().getInicialPoint().x*zoom + 1*zoom).intValue();
				int y = new Double(getTabbedPaneWorkArea().getStereotypeSelect().getInicialPoint().y*zoom + 15*zoom + 1*zoom).intValue();
				int w = new Double(getTabbedPaneWorkArea().getStereotypeSelect().getWidth()*zoom - 2*zoom).intValue();
				int h = new Double(getTabbedPaneWorkArea().getStereotypeSelect().getHeigth()*zoom - 15*zoom).intValue();
				jTextAreaNote.setBackground( getTabbedPaneWorkArea().getStereotypeSelect().getBackground() );
				jTextAreaNote.setBounds(x, y, w, h);
				jTextAreaNote.setText( text );//
				jTextAreaNote.setVisible( true );
				jTextAreaNote.grabFocus();

			}
		}

	}

	public String getStringTyped() {
		return stringTyped;
	}

	public void setStringTyped(String stringTyped) {
		this.stringTyped = stringTyped;
	}

	public boolean isNonKeyTyped() {
		return nonKeyTyped;
	}

	public void setNonKeyTyped(boolean nonKeyTyped) {
		this.nonKeyTyped = nonKeyTyped;
	}

	public JTextArea getJTextAreaNote() {
		if(jTextAreaNote == null){
			jTextAreaNote = new JTextArea();
			setSizeTextArea(jTextAreaNote.getFont().getSize());
			//jTextAreaNote.setLineWrap(true);
			//jTextAreaNote.setWrapStyleWord(true);
			jTextAreaNote.addKeyListener(new java.awt.event.KeyAdapter() {   
				public void keyPressed(java.awt.event.KeyEvent e) {  
					if(e.getKeyCode() == 8 || e.getKeyCode() == 10 || e.getKeyCode() == 16 || e.getKeyCode() == 17 ){
						posibleTyped = true;
					}
					boolean flag = false;
					int start = jTextAreaNote.getSelectionStart();
					int end = jTextAreaNote.getSelectionEnd();
					String value = "";
					String resultString1 = jTextAreaNote.getText().substring(0, start);
					String resultString2 = jTextAreaNote.getText().substring(end, jTextAreaNote.getText().length());
					if(e.getKeyCode() == 8 || e.getKeyCode() == 10 || e.getKeyCode() == 16 || e.getKeyCode() == 17 || e.getKeyCode() == 127){
						value = resultString1+resultString2;
						flag = true;
					}
					else
						value = resultString1+e.getKeyChar()+resultString2;
					Diagram diagram = getPaintManager().getActiveDiagram();
					if(flag){
						diagram.getSelectedStereotype().setDescription(value);
						diagram.setDescriptionById(diagram.getSelectedStereotype().getId(), value);
					}
					else{
						diagram.getSelectedStereotype().setDescription(jTextAreaNote.getText());
						diagram.setDescriptionById(diagram.getSelectedStereotype().getId(), jTextAreaNote.getText());
					}

					FontMetrics fm = getTabbedPaneWorkArea().getPanelWorkArea().getGraphics().getFontMetrics();
					String text = jTextAreaNote.getText();
					String[] a = text.split("\n");
					int max = 0;
					for (int i = 0; i < a.length; i++) {
						int l = fm.charsWidth( a[i].toCharArray(), 0 , a[i].length());
						if(l > max){
							max = l;
						}
					}
					if(max > diagram.getSelectedStereotype().getWidth()){
						diagram.getSelectedStereotype().setWidth(max + 5);
						diagram.setWidthById(diagram.getSelectedStereotype().getId(), max + 5);
					}

					//FontMetrics fm = pG.getFontMetrics();
					Graphics g = getTabbedPaneWorkArea().getPanelWorkArea().getGraphics();
					int height = new Double(fm.getLineMetrics(a[0], g).getHeight()).intValue();
					int lastPos = 0;
					for (int i = 0; i < a.length; i++) {
						//g2.drawString( a[i], stereotype.getInicialPoint().x + 1, stereotype.getInicialPoint().y + 15 + 2 + height*i + 13 );
						lastPos = i;

					}

					int division = jTextAreaNote.getBounds().height/height;
					if(lastPos + 1 > division ){
						if(diagram.getSelectedStereotype().getHeigth() < (height*(lastPos + 1) + 15 + 30)){
							diagram.getSelectedStereotype().setHeigth( height*(lastPos + 1) + 15 + 30);
							diagram.setHeightById(diagram.getSelectedStereotype().getId(), height*(lastPos + 1) + 15 + 30);
						}
					}
					getTabbedPaneWorkArea().repaint();
				}
				public void keyTyped(java.awt.event.KeyEvent e) {
					if(posibleTyped == true){
						countToCompareName++;
						if(countToCompareName == 1){
							Stereotype undoStereotype = new Stereotype(getPaintManager().getActiveDiagram().getSelectedStereotype());
							undoStereotype.setSelected(true);
							UndoAction action = new UndoAction("edit");
							action.getArrayActions().add(undoStereotype);
							if(getPaintManager().getActiveDiagram().getListRedo().size() > 0){
								getPaintManager().getActiveDiagram().getListRedo().clear();
								getButtonRedo().setEnabled(false);
								getMenuItemRedo().setEnabled(false);
								getTabbedPaneWorkArea().getMenuItemRedo().setEnabled(false);
							}
							getPaintManager().getActiveDiagram().getListUndo().add(action);
						}
					}

					int start = jTextAreaNote.getSelectionStart();
					int end = jTextAreaNote.getSelectionEnd();
					String resultString1 = jTextAreaNote.getText().substring(0, start);
					String resultString2 = jTextAreaNote.getText().substring(end, jTextAreaNote.getText().length());
					setStringTyped(resultString1+e.getKeyChar()+resultString2);
					posibleTyped = false;
				}
			});
			jTextAreaNote.addFocusListener(new java.awt.event.FocusAdapter() {   
				public void focusGained(java.awt.event.FocusEvent e) {  
					jTextAreaNote.selectAll();
				}

			});
		}
		return jTextAreaNote;
	}

	/**
	 * This method initializes buttonResetZoom	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButtonResetZoom() {
		if (buttonResetZoom == null) {
			buttonResetZoom = new JButton();
			buttonResetZoom.setIcon(new ImageIcon(getClass().getResource("/icons/gif/resetZoom.png")));
			buttonResetZoom.setToolTipText("Zoom 1:1");
			buttonResetZoom.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMenuItemResetZoom().doClick();
				}
			});
		}
		return buttonResetZoom;
	}

	/**
	 * This method initializes menuItemResetZoom	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	public JMenuItem getMenuItemResetZoom() {
		if (menuItemResetZoom == null) {
			menuItemResetZoom = new JMenuItem();
			menuItemResetZoom.setText("Zoom 1:1");
			menuItemResetZoom.setIcon(new ImageIcon(getClass().getResource("/icons/gif/resetZoom.png")));
			menuItemResetZoom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DIVIDE, Event.CTRL_MASK, false));
			menuItemResetZoom.setFont(new Font("Dialog", Font.PLAIN, 12));
			menuItemResetZoom.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(getPaintManager().getActiveDiagram() != null){
						getJTextAreaNote().setVisible(false);
						getJTextFieldName().setVisible(false);
						getPaintManager().getActiveDiagram().setZoomFactor( 1.0 );
						double newZoomFactor = getPaintManager().getActiveDiagram().getZoomFactor();
						Font fontTextArea = new Font("", Font.PLAIN, new Double(getSizeTextArea()*newZoomFactor).intValue());
						jTextAreaNote.setFont(fontTextArea);
						Font fontFieldName = new Font("", Font.PLAIN, new Double(getSizeFieldName()*newZoomFactor).intValue());
						jTextFieldName.setFont(fontFieldName);
						Rectangle diagramWindowBounds = ( Rectangle ) getPaintManager().getActiveDiagram().getDiagramSize().clone();
						diagramWindowBounds.width = new Double( diagramWindowBounds.width * newZoomFactor ).intValue();
						diagramWindowBounds.height = new Double( diagramWindowBounds.height * newZoomFactor ).intValue();
						getTabbedPaneWorkArea().getPanelWorkArea().setPreferredSize(new Dimension(diagramWindowBounds.getSize().width , diagramWindowBounds.getSize().height));

						getTabbedPaneWorkArea().getPanelWorkArea().revalidate();
						getPaintManager().setSizeDiagramOverview(new Rectangle(getNavigator().getSize().width,getNavigator().getSize().height),getNavigator().getPanelOverView());
						getTabbedPaneWorkArea().getPanelWorkArea().repaint();

						if(getPaintManager().getActiveDiagram().getSelectedStereotype() == null)
							getTableProperties().getTableProperties().setModel(FactoryModel.factory.getModelForTablePropertiesToDiagram(getPaintManager().getActiveDiagram()));
					}
				}
			});
		}
		return menuItemResetZoom;
	}

	/**
	 * This method initializes textAreaDescription	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	public JTextArea getTextAreaDescription() {
		if (textAreaDescription == null) {
			textAreaDescription = new JTextArea();
			textAreaDescription.setWrapStyleWord(true);
			textAreaDescription.setAutoscrolls(true);
			textAreaDescription.setLineWrap(true);
			textAreaDescription.addCaretListener(new javax.swing.event.CaretListener() {
				public void caretUpdate(javax.swing.event.CaretEvent e) {
					getPaintManager().getActiveDiagram().getSelectedStereotype().setDescription(getTextAreaDescription().getText());
					getJTextAreaNote().setText(getTextAreaDescription().getText());
					getTabbedPaneWorkArea().repaint();
				}
			});
			textAreaDescription.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					Diagram diagram = getPaintManager().getActiveDiagram();

					FontMetrics fm = getTabbedPaneWorkArea().getPanelWorkArea().getGraphics().getFontMetrics();
					String text = jTextAreaNote.getText();
					String[] a = text.split("\n");
					int max = 0;
					for (int i = 0; i < a.length; i++) {
						int l = fm.charsWidth( a[i].toCharArray(), 0 , a[i].length());
						if(l > max){
							max = l;
						}
					}
					if(max > diagram.getSelectedStereotype().getWidth()){
						diagram.getSelectedStereotype().setWidth(max + 5);
						diagram.setWidthById(diagram.getSelectedStereotype().getId(), max + 5);
					}

					//FontMetrics fm = pG.getFontMetrics();
					Graphics g = getTabbedPaneWorkArea().getPanelWorkArea().getGraphics();
					int height = new Double(fm.getLineMetrics(a[0], g).getHeight()).intValue();
					int lastPos = 0;
					for (int i = 0; i < a.length; i++) {
						//g2.drawString( a[i], stereotype.getInicialPoint().x + 1, stereotype.getInicialPoint().y + 15 + 2 + height*i + 13 );
						lastPos = i;

					}

					int division = jTextAreaNote.getBounds().height/height;
					if(lastPos + 1 > division ){
						if(diagram.getSelectedStereotype().getHeigth() < (height*(lastPos + 1) + 15 + 30)){
							diagram.getSelectedStereotype().setHeigth( height*(lastPos + 1) + 15 + 30);
							diagram.setHeightById(diagram.getSelectedStereotype().getId(), height*(lastPos + 1) + 15 + 30);
						}
					}
					getTabbedPaneWorkArea().repaint();
				}
			});
		}
		return textAreaDescription;
	}

	/**
	 * This method initializes scrollPaneDescription	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	public JScrollPane getScrollPaneDescription() {
		if (scrollPaneDescription == null) {
			scrollPaneDescription = new JScrollPane();
			scrollPaneDescription.setViewportView(getTextAreaDescription());
			scrollPaneDescription.setVisible(false);
		}
		return scrollPaneDescription;
	}  

	public Point getPointPressed() {
		return pointPressed;
	}

	public void setPointPressed(Point pointPressed) {
		this.pointPressed = pointPressed;
	}

	public int getSizeTextArea() {
		return sizeTextArea;
	}

	public void setSizeTextArea(int sizeTextArea) {
		this.sizeTextArea = sizeTextArea;
	}

	public int getSizeFieldName() {
		return sizeFieldName;
	}

	public void setSizeFieldName(int sizeFieldName) {
		this.sizeFieldName = sizeFieldName;
	}

	/**
	 * This method initializes MenuItemCloseOther	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMenuItemCloseOther() {
		if (MenuItemCloseOther == null) {
			MenuItemCloseOther = new JMenuItem();
			MenuItemCloseOther.setFont(new Font("Dialog", Font.PLAIN, 12));
			MenuItemCloseOther.setText("Cerrar otros");
			MenuItemCloseOther.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getTabbedPaneWorkArea().getMenuItemCloseOthers().doClick();
				}
			});
		}
		return MenuItemCloseOther;
	}

	public ArrayList<String> getListTabsTitles() {
		return listTabsTitles;
	}

	public void setListTabsTitles(ArrayList<String> listTabsTitles) {
		this.listTabsTitles = listTabsTitles;
	}

	/*public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }*/

	/**
	 * This method initializes scrollPaneNote	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */

} //  @jve:decl-index=0:visual-constraint="10,10"
