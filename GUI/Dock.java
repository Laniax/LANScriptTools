package scripts.LANScriptTools.GUI;

import java.awt.Font;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;

import scripts.LANScriptTools.Threading.ScriptToolsThread;
import scripts.LANScriptTools.Tools.*;

/**
 * @author Laniax
 */

public class Dock extends JFrame {

	private static final long serialVersionUID = -1416786796525087646L;
	
	private final ScriptToolsThread script;
	
	public boolean doDock = true;

	public Dock(ScriptToolsThread script) {

		this.script = script;
		
		createGUI();

		// Listen to close events on the dock.
		addWindowListener(script.adapters.getCloseListener());
	}

	/**
	 * Sets the dock's position to the coordinates of the Point (p) + offset of the tribot window, positioning it right next to it.
	 * @param p
	 * @param tribotWidth
	 */
	public void setPosition(Point p, int tribotWidth) {
		setLocation((int) (p.getX() + tribotWidth + 5), (int) p.getY());
	}

	/**
	 * Gets the index of the currently open tool.
	 * @return index
	 */
	public TABS getOpenTab() {
		return TABS.values()[tabPane.getSelectedIndex()];
	}
	
	private void onChangeTab(final TABS tab) {
		
		// We clear the things to draw on each tab switch
		script.setSelectedTile(null);
		script.tilesToDraw.clear();
		script.entitiesToDraw.clear();

		// Don't auto update if the tab isnt open.
		ObjectsTool objectsTool = (ObjectsTool)script.observers.get(TABS.OBJECTS);
		if (objectsTool != null)
			objectsTool.doAutoUpdate = false;
		
		NPCsTool npcsTool = (NPCsTool)script.observers.get(TABS.NPCS);
		if (npcsTool != null)
			npcsTool.doAutoUpdate = false;
		
		// notify the appropriate tool that the tab has became active.
		AbstractTool ob = script.observers.get(tab);
		if (ob != null)
			ob.onTabChange();
	}

	/**
	 * Creates the Dock
	 */
	@SuppressWarnings("serial")
	private void createGUI() {

        panel7 = new Panel();
		jLabel50 = new JLabel();
		jLabel49 = new JLabel();
		jLabel48 = new JLabel();
		jScrollPane10 = new JScrollPane();
		jScrollPane9 = new JScrollPane();
		tableVarbits = new JTable();
		inputSearchVarbit = new JTextField();
		btnVarbitsStopStart = new JButton();
		listVarbitLog = new JList<>(new DefaultListModel<>());
		tabPane = new JTabbedPane();
		panel4 = new Panel();
		jLabel41 = new JLabel();
		jLabel42 = new JLabel();
		jScrollPane7 = new JScrollPane();
		tableInspect = new JTable();
		panel1 = new Panel();
		jLabel34 = new JLabel();
		jLabel36 = new JLabel();
		jScrollPane1 = new JScrollPane();
		outputPath = new JTextArea();
		jLabel38 = new JLabel();
		btnPublic = new JRadioButton();
		btnPrivate = new JRadioButton();
		inputPathName = new JTextField();
		jLabel37 = new JLabel();
		btnCopyPaths = new JButton();
		btnPathsStartStop = new JButton();
		btnClear = new JButton();
		panel2 = new Panel();
		jLabel35 = new JLabel();
		jScrollPane2 = new JScrollPane();
		tableObjects = new JTable();
		chkUpdateObjects = new JCheckBox();
		btnUpdateObjects = new JButton();
		panel3 = new Panel();
		jLabel39 = new JLabel();
		chkUpdateNPCs = new JCheckBox();
		btnUpdateNPCs = new JButton();
		jScrollPane3 = new JScrollPane();
		tableNPCs = new JTable();
		panel5 = new Panel();
		jLabel43 = new JLabel();
		jLabel44 = new JLabel();
		btnDPathNavigator = new JRadioButton();
		btnPathFinding = new JRadioButton();
		jScrollPane4 = new JScrollPane();
		outputPathFinding = new JTextArea();
		btnWalkingMinimap = new JRadioButton();
		btnWalkingScreenPath = new JRadioButton();
		btnCopyPathFinding = new JButton();
		panel6 = new Panel();
		jLabel40 = new JLabel();
		jLabel45 = new JLabel();
		jScrollPane5 = new JScrollPane();
		tableSettings = new JTable();
		btnSettingsStopStart = new JButton();
		inputSearchSetting = new JTextField();
		jLabel46 = new JLabel();
		jScrollPane6 = new JScrollPane();
		listSettingsLog = new JList<>(new DefaultListModel<>());
		chkDock = new JCheckBox();
		btngroupPaths = new ButtonGroup();
		btngroupPathfinding = new ButtonGroup();

		setTitle("[LAN] ScriptTools");
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		setBounds(new Rectangle(0, 0, 250, 0));

		tabPane.setTabPlacement(JTabbedPane.LEFT);
		tabPane.setFont(new Font("Tahoma", 0, 14));
		tabPane.addChangeListener(e -> onChangeTab(getOpenTab()));

		jLabel41.setText("This is a multifunctional tool which will give you info on any tile you click on with your middle mouse button.");

		jLabel42.setText("It includes any Objects, NPCs, Players or Item who may reside on it (at the time of selection).");

		tableInspect.setModel(new DefaultTableModel(
				new Object [][] {},
				new String [] {"ID", "Type", "Name", "Actions"}) {
			Class<?>[] types = new Class [] {Integer.class, String.class, String.class, String.class};

			public Class<?> getColumnClass(int columnIndex) {
				return types [columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		});
		jScrollPane7.setViewportView(tableInspect);

		if (tableInspect.getColumnModel().getColumnCount() > 0) {
			tableInspect.getColumnModel().getColumn(0).setResizable(false);
			tableInspect.getColumnModel().getColumn(0).setPreferredWidth(50);
			tableInspect.getColumnModel().getColumn(1).setResizable(false);
			tableInspect.getColumnModel().getColumn(1).setPreferredWidth(50);
			tableInspect.getColumnModel().getColumn(2).setResizable(false);
			tableInspect.getColumnModel().getColumn(2).setPreferredWidth(300);
			tableInspect.getColumnModel().getColumn(3).setResizable(false);
			tableInspect.getColumnModel().getColumn(3).setPreferredWidth(300);
		}

		GroupLayout panel4Layout = new GroupLayout(panel4);
		panel4.setLayout(panel4Layout);
		panel4Layout.setHorizontalGroup(
				panel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(panel4Layout.createSequentialGroup()
						.addGap(23, 23, 23)
						.addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addGroup(panel4Layout.createSequentialGroup()
									.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jScrollPane7, GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE))
										.addComponent(jLabel42)
										.addComponent(jLabel41))
										.addContainerGap(32, Short.MAX_VALUE))
				);
		panel4Layout.setVerticalGroup(
				panel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(panel4Layout.createSequentialGroup()
						.addGap(22, 22, 22)
						.addComponent(jLabel41)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jLabel42)
						.addGap(53, 53, 53)
						.addComponent(jScrollPane7, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				
								.addGap(21, 21, 21)))
				);

		tabPane.addTab("Inspect Tool", panel4);

		jLabel34.setText("Here you can easily create paths to use in your script.");

		jLabel36.setText("Click the 'Start' button below and select tiles ingame by clicking the middle mouse button (scroll-wheel).");

		outputPath.setColumns(20);
		outputPath.setRows(5);
		outputPath.setTabSize(4);
		outputPath.setText("private static final RSTile[] pathName = new RSTile[] {\n};");
		jScrollPane1.setViewportView(outputPath);

		jLabel38.setText("Path name:");

		btnPublic.setText("Public");
		btnPublic.addActionListener(evt -> ((PathsTool)script.observers.get(TABS.PATHS)).btnAccessModifierActionPerformed(evt));

		btnPrivate.setSelected(true);
		btnPrivate.setText("Private");
		btnPrivate.addActionListener(evt -> ((PathsTool)script.observers.get(TABS.PATHS)).btnAccessModifierActionPerformed(evt));

		btngroupPaths.add(btnPublic);
		btngroupPaths.add(btnPrivate);

		inputPathName.setText("pathName");
		inputPathName.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				((PathsTool)script.observers.get(TABS.PATHS)).refreshFirstLine();
			}
			public void removeUpdate(DocumentEvent e) {
				((PathsTool)script.observers.get(TABS.PATHS)).refreshFirstLine();
			}
			public void insertUpdate(DocumentEvent e) {
				((PathsTool)script.observers.get(TABS.PATHS)).refreshFirstLine();
			}
		});

		jLabel37.setText("Clicking the tile again will remove it from the path.");

		btnCopyPaths.setText("Copy to clipboard");
		btnCopyPaths.setInheritsPopupMenu(true);
		btnCopyPaths.setMargin(new Insets(0, 0, 0, 0));
		btnCopyPaths.addActionListener(evt -> ((PathsTool)script.observers.get(TABS.PATHS)).btnCopyPathsActionPerformed(evt));

		btnPathsStartStop.setText("Start");
		btnPathsStartStop.setInheritsPopupMenu(true);
		btnPathsStartStop.setMargin(new Insets(0, 0, 0, 0));
		btnPathsStartStop.addActionListener(evt -> ((PathsTool)script.observers.get(TABS.PATHS)).btnPathsStartStopActionPerformed(evt));

		btnClear.setText("Clear");
		btnClear.setInheritsPopupMenu(true);
		btnClear.setMargin(new Insets(0, 0, 0, 0));
		btnClear.addActionListener(evt -> ((PathsTool)script.observers.get(TABS.PATHS)).btnClearActionPerformed(evt));

		GroupLayout panel1Layout = new GroupLayout(panel1);
		panel1.setLayout(panel1Layout);
		panel1Layout.setHorizontalGroup(
				panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(panel1Layout.createSequentialGroup()
						.addGap(22, 22, 22)
						.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(jLabel36)
								.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addGroup(panel1Layout.createSequentialGroup()
												.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
														.addComponent(jLabel34)
														.addComponent(jLabel37))
														.addGap(352, 352, 352))
														.addGroup(GroupLayout.Alignment.LEADING, panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
																.addGroup(panel1Layout.createSequentialGroup()
																		.addComponent(btnPathsStartStop, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
																		.addGap(325, 325, 325)
																		.addComponent(btnCopyPaths, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE))
																		.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
																				.addGroup(GroupLayout.Alignment.LEADING, panel1Layout.createSequentialGroup()
																						.addComponent(jLabel38)
																						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
																						.addComponent(inputPathName, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
																						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
																						.addComponent(btnPublic)
																						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
																						.addComponent(btnPrivate)
																						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																						.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
																						.addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 597, GroupLayout.PREFERRED_SIZE)))))
																						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		panel1Layout.setVerticalGroup(
				panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(panel1Layout.createSequentialGroup()
						.addGap(20, 20, 20)
						.addComponent(jLabel34)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jLabel36)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jLabel37)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel38)
								.addComponent(btnPublic)
								.addComponent(btnPrivate)
								.addComponent(inputPathName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(btnCopyPaths, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnPathsStartStop, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
										.addContainerGap())
				);

		tabPane.addTab("Paths", panel1);

		jLabel35.setText("This tab displays information about objects around you.");

		tableObjects.setModel(new ObjectTableModel(new RSObject[0]));
		tableObjects.getColumn("Projection").setCellEditor(new ButtonEditor(new JCheckBox(), script));
		tableObjects.getColumn("Projection").setCellRenderer(new ButtonRenderer());
		tableObjects.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		jScrollPane2.setViewportView(tableObjects);

		chkUpdateObjects.setText("Auto Update");
		chkUpdateObjects.setSelected(true);
		chkUpdateObjects.addActionListener(evt -> ((ObjectsTool)script.observers.get(TABS.OBJECTS)).chkUpdateObjectsActionPerformed(evt));
		btnUpdateObjects.setText("Update");
		btnUpdateObjects.addActionListener(evt -> ((ObjectsTool)script.observers.get(TABS.OBJECTS)).update());

		GroupLayout panel2Layout = new GroupLayout(panel2);
		panel2.setLayout(panel2Layout);
		panel2Layout.setHorizontalGroup(
				panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(panel2Layout.createSequentialGroup()
						.addGap(25, 25, 25)
						.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addGroup(panel2Layout.createSequentialGroup()
										.addComponent(chkUpdateObjects)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(btnUpdateObjects))
										.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(jLabel35)
												.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 607, GroupLayout.PREFERRED_SIZE)))
												.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		panel2Layout.setVerticalGroup(
				panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(panel2Layout.createSequentialGroup()
						.addGap(25, 25, 25)
						.addComponent(jLabel35)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(chkUpdateObjects)
								.addComponent(btnUpdateObjects))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 544, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(22, Short.MAX_VALUE))
				);

		tabPane.addTab("Objects", panel2);

		jLabel39.setText("This tab displays information about npcs around you.");

		chkUpdateNPCs.setText("Auto Update");
		chkUpdateNPCs.setSelected(true);
		chkUpdateNPCs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				((NPCsTool)script.observers.get(TABS.NPCS)).chkUpdateNPCsActionPerformed(evt);
			}
		});

		btnUpdateNPCs.setText("Update");
		btnUpdateNPCs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				((NPCsTool)script.observers.get(TABS.NPCS)).update();
			}
		});

		tableNPCs.setModel(new NPCTableModel(new RSNPC[0]));
		tableNPCs.getColumn("Projection").setCellEditor(new ButtonEditor(new JCheckBox(), script));
		tableNPCs.getColumn("Projection").setCellRenderer(new ButtonRenderer());
		tableNPCs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jScrollPane3.setViewportView(tableNPCs);

		GroupLayout panel3Layout = new GroupLayout(panel3);
		panel3.setLayout(panel3Layout);
		panel3Layout.setHorizontalGroup(
				panel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(panel3Layout.createSequentialGroup()
						.addGap(25, 25, 25)
						.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addGroup(panel3Layout.createSequentialGroup()
										.addComponent(chkUpdateNPCs)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(btnUpdateNPCs))
										.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(jLabel39)
												.addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 607, GroupLayout.PREFERRED_SIZE)))
												.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		panel3Layout.setVerticalGroup(
				panel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(panel3Layout.createSequentialGroup()
						.addGap(25, 25, 25)
						.addComponent(jLabel39)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(chkUpdateNPCs)
								.addComponent(btnUpdateNPCs))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 544, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(22, Short.MAX_VALUE))
				);

		tabPane.addTab("NPCs", panel3);

		jLabel43.setText("Here you can check how different pathfinding methods would walk to a tile.");
		jLabel44.setText("Simply select a destination tile by clicking with the middle mouse button (scroll-wheel) on the tile and select your pathfinding algorithm below.");

		outputPathFinding.setColumns(20);
		outputPathFinding.setRows(5);
		jScrollPane4.setViewportView(outputPathFinding);

		btnDPathNavigator.setText("DPathNavigator");
		btnPathFinding.setText("PathFinding");
		btnWalkingMinimap.setText("Walking Minimap");
		btnWalkingScreenPath.setText("Walking Screen Path");

		btngroupPathfinding.add(btnDPathNavigator);
		btngroupPathfinding.add(btnPathFinding);
		btngroupPathfinding.add(btnWalkingMinimap);
		btngroupPathfinding.add(btnWalkingScreenPath);

		btnDPathNavigator.addActionListener(evt -> ((PathfindingTool)script.observers.get(TABS.PATHFINDING)).update());
		btnPathFinding.addActionListener(evt -> ((PathfindingTool)script.observers.get(TABS.PATHFINDING)).update());
		btnWalkingMinimap.addActionListener(evt -> ((PathfindingTool)script.observers.get(TABS.PATHFINDING)).update());
		btnWalkingScreenPath.addActionListener(evt -> ((PathfindingTool)script.observers.get(TABS.PATHFINDING)).update());

		btnCopyPathFinding.setText("Copy to clipboard");
		btnCopyPathFinding.setInheritsPopupMenu(true);
		btnCopyPathFinding.setMargin(new Insets(0, 0, 0, 0));
		btnCopyPathFinding.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				((PathfindingTool)script.observers.get(TABS.PATHFINDING)).btnCopyPathfindingActionPerformed(evt);
			}
		});

		GroupLayout panel5Layout = new GroupLayout(panel5);
		panel5.setLayout(panel5Layout);
		panel5Layout.setHorizontalGroup(
				panel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(panel5Layout.createSequentialGroup()
						.addGap(26, 26, 26)
						.addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(jLabel44)
								.addComponent(jLabel43)
								.addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addComponent(btnCopyPathFinding, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
										.addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
												.addGroup(panel5Layout.createSequentialGroup()
														.addComponent(btnDPathNavigator)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(btnPathFinding)
														.addGap(59, 59, 59)
														.addComponent(btnWalkingMinimap)
														.addGap(51, 51, 51)
														.addComponent(btnWalkingScreenPath))
														.addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, 592, GroupLayout.PREFERRED_SIZE))))
														.addContainerGap(24, Short.MAX_VALUE))
				);
		panel5Layout.setVerticalGroup(
				panel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(panel5Layout.createSequentialGroup()
						.addGap(24, 24, 24)
						.addComponent(jLabel43)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jLabel44)
						.addGap(52, 52, 52)
						.addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(btnWalkingScreenPath)
								.addComponent(btnPathFinding)
								.addComponent(btnDPathNavigator)
								.addComponent(btnWalkingMinimap))
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, 439, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(btnCopyPathFinding, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(22, Short.MAX_VALUE))
				);

		tabPane.addTab("Pathfinding", panel5);

		jLabel40.setText("This is a setting explorer, which allows you to explore the game's settings carefully.");

		jLabel45.setText("This tool runs by default and works even though you are using other tools at the same time.");

		tableSettings.setModel(new DefaultTableModel(
				new Object [][] {

				},
				new String [] {
						"ID", "Value"
				}
				) {
			Class<?>[] types = new Class [] {
					Integer.class, Integer.class
			};
			
			public Class<?> getColumnClass(int columnIndex) {
				return types [columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		});
		tableSettings.getTableHeader().setReorderingAllowed(false);
		jScrollPane5.setViewportView(tableSettings);
		if (tableSettings.getColumnModel().getColumnCount() > 0) {
			tableSettings.getColumnModel().getColumn(0).setResizable(false);
			tableSettings.getColumnModel().getColumn(1).setResizable(false);
			tableSettings.getColumnModel().getColumn(1).setPreferredWidth(300);
		}

		btnSettingsStopStart.setText("Stop");
		
		btnSettingsStopStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				((SettingsTool)script.observers.get(TABS.SETTINGS)).btnSettingsStopStartActionPerformed(evt);
			}
		});

		btnVarbitsStopStart.setText("Stop");

		btnVarbitsStopStart.addActionListener(evt -> ((VarBitTool)script.observers.get(TABS.VARBIT)).btnSettingsStopStartActionPerformed(evt));
		
		inputSearchSetting.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				((SettingsTool)script.observers.get(TABS.SETTINGS)).doFilter();
			}
			public void removeUpdate(DocumentEvent e) {
				((SettingsTool)script.observers.get(TABS.SETTINGS)).doFilter();
			}
			public void insertUpdate(DocumentEvent e) {
				((SettingsTool)script.observers.get(TABS.SETTINGS)).doFilter();
			}
		});

		inputSearchVarbit.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				((VarBitTool)script.observers.get(TABS.VARBIT)).doFilter();
			}
			public void removeUpdate(DocumentEvent e) {
				((VarBitTool)script.observers.get(TABS.VARBIT)).doFilter();
			}
			public void insertUpdate(DocumentEvent e) {
				((VarBitTool)script.observers.get(TABS.VARBIT)).doFilter();
			}
		});


		jLabel46.setText("Search ID:");

		jScrollPane6.setViewportView(listSettingsLog);

		GroupLayout panel6Layout = new GroupLayout(panel6);
		panel6.setLayout(panel6Layout);
		panel6Layout.setHorizontalGroup(
				panel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(panel6Layout.createSequentialGroup()
						.addGap(28, 28, 28)
						.addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(jLabel45)
								.addComponent(jLabel40)
								.addGroup(panel6Layout.createSequentialGroup()
										.addComponent(jLabel46)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(inputSearchSetting, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
										.addGroup(panel6Layout.createSequentialGroup()
												.addComponent(jScrollPane5, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(jScrollPane6, GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)))
												.addContainerGap())
												.addGroup(panel6Layout.createSequentialGroup()
														.addGap(222, 222, 222)
														.addComponent(btnSettingsStopStart, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE)
														.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		panel6Layout.setVerticalGroup(
				panel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(panel6Layout.createSequentialGroup()
						.addGap(28, 28, 28)
						.addComponent(jLabel40)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jLabel45)
						.addGap(35, 35, 35)
						.addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(inputSearchSetting, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel46))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(jScrollPane6)
										.addComponent(jScrollPane5, GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE))
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(btnSettingsStopStart, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addContainerGap())
				);

		tabPane.addTab("Settings", panel6);

		jLabel48.setText("This is a VarBit explorer, which allows you to explore the game's varbits carefully.");

		jLabel49.setText("This tool runs by default and works even though you are using other tools at the same time.");

		tableVarbits.setModel(new javax.swing.table.DefaultTableModel(
				new Object [][] {

				},
				new String [] {

						"ID", "Value"
				}
		) {
			Class[] types = new Class [] {
					java.lang.Integer.class, java.lang.Integer.class
			};
			boolean[] canEdit = new boolean [] {
					false, false
			};

			public Class getColumnClass(int columnIndex) {
				return types [columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
			}
		});
		tableVarbits.getTableHeader().setReorderingAllowed(false);
		jScrollPane9.setViewportView(tableVarbits);
		if (tableVarbits.getColumnModel().getColumnCount() > 0) {
			tableVarbits.getColumnModel().getColumn(0).setResizable(false);
			tableVarbits.getColumnModel().getColumn(1).setResizable(false);
			tableVarbits.getColumnModel().getColumn(1).setPreferredWidth(300);
		}

		btnVarbitsStopStart.setText("Stop");

		jLabel50.setText("Search ID:");

		jScrollPane10.setViewportView(listVarbitLog);

		javax.swing.GroupLayout panel7Layout = new javax.swing.GroupLayout(panel7);
		panel7.setLayout(panel7Layout);
		panel7Layout.setHorizontalGroup(
				panel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(panel7Layout.createSequentialGroup()
								.addGap(28, 28, 28)
								.addGroup(panel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jLabel49)
										.addComponent(jLabel48)
										.addGroup(panel7Layout.createSequentialGroup()
												.addComponent(jLabel50)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(inputSearchVarbit, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(panel7Layout.createSequentialGroup()
												.addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)))
								.addContainerGap())
						.addGroup(panel7Layout.createSequentialGroup()
								.addGap(222, 222, 222)
								.addComponent(btnVarbitsStopStart, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel7Layout.setVerticalGroup(
				panel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(panel7Layout.createSequentialGroup()
								.addGap(28, 28, 28)
								.addComponent(jLabel48)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jLabel49)
								.addGap(35, 35, 35)
								.addGroup(panel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(inputSearchVarbit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jLabel50))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(panel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jScrollPane10)
										.addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(btnVarbitsStopStart, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
		);

		tabPane.addTab("Varbits", panel7);

		chkDock.setSelected(true);
		chkDock.setText("Dock to the TriBot window");
		chkDock.addActionListener(evt -> doDock = chkDock.isSelected());

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(chkDock)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(tabPane, GroupLayout.PREFERRED_SIZE, 736, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(tabPane, GroupLayout.PREFERRED_SIZE, 650, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(chkDock))
				);

		pack();
	}

	public JList<String> listVarbitLog;
	public JButton btnVarbitsStopStart;
	public JTextField inputSearchVarbit;
	public JTable tableVarbits;
	private JScrollPane jScrollPane9;
	private JScrollPane jScrollPane10;
	private JLabel jLabel48;
	private JLabel jLabel49;
	private JLabel jLabel50;
	private Panel panel7;

	private JButton btnClear;
	private JButton btnCopyPathFinding;
	private JButton btnCopyPaths;
	private JRadioButton btnDPathNavigator;
	private JRadioButton btnPathFinding;
	private JRadioButton btnPrivate;
	public JRadioButton btnPublic;
	private ButtonGroup btngroupPaths;
	public ButtonGroup btngroupPathfinding;
	public JButton btnSettingsStopStart;
	public JButton btnPathsStartStop;
	public JButton btnUpdateNPCs;
	public JButton btnUpdateObjects;
	private JRadioButton btnWalkingMinimap;
	private JRadioButton btnWalkingScreenPath;
	private JCheckBox chkDock;
	public JCheckBox chkUpdateNPCs;
	public JCheckBox chkUpdateObjects;
	public JTextField inputPathName;
	public JTextField inputSearchSetting;
	private JLabel jLabel34;
	private JLabel jLabel35;
	private JLabel jLabel36;
	private JLabel jLabel37;
	private JLabel jLabel38;
	private JLabel jLabel39;
	private JLabel jLabel40;
	private JLabel jLabel41;
	private JLabel jLabel42;
	private JLabel jLabel43;
	private JLabel jLabel44;
	private JLabel jLabel45;
	private JLabel jLabel46;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JScrollPane jScrollPane3;
	private JScrollPane jScrollPane4;
	private JScrollPane jScrollPane5;
	private JScrollPane jScrollPane6;
	private JScrollPane jScrollPane7;
	private JTabbedPane tabPane;
	public JList<String> listSettingsLog;
	public JTextArea outputPath;
	public JTextArea outputPathFinding;
	private Panel panel1;
	private Panel panel2;
	private Panel panel3;
	private Panel panel4;
	private Panel panel5;
	private Panel panel6;
	public JTable tableInspect;
	public JTable tableNPCs;
	public JTable tableObjects;
	public JTable tableSettings;

}