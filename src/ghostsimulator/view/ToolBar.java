package ghostsimulator.view;

import ghostsimulator.GhostManager;
import ghostsimulator.controller.SliderListener;
import ghostsimulator.model.Simulation;
import ghostsimulator.util.ImageLoader;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

public class ToolBar extends JToolBar {

	private static Dimension DIM_SLIDER_HORIZONTAL = new Dimension(250, 50);
	private static Dimension DIM_SLIDER_VERTICAL = new Dimension(50, 250);
	private JSlider speedSlider;
	private GhostManager manager;
	private ButtonGroup territoryGroup;
	private JToggleButton btnTerritoryFireball;
	private JToggleButton btnTerritoryWhiteWall;
	private JToggleButton btnTerritoryRedWall;
	private JToggleButton btnTerritoryDelete;
	private JButton btnSimStart;
	private JButton btnSimPause;
	private JButton btnSimStop;
	private Simulation t;

	public ButtonGroup getTerritoryGroup() {
		return territoryGroup;
	}

	public enum TerritoryAction {
		DELETE, ADD_FIREBALL, ADD_WHITEWALL, ADD_REDWALL
	};

	public ToolBar(final GhostManager manager) {
		super("Toolbar", SwingConstants.HORIZONTAL);
		this.manager = manager;

		JButton btnSave = new JButton(ImageLoader.getImageIcon("gnome-dev-floppy.png"));
		btnSave.setToolTipText("Save");
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manager.getEditorManager().saveEditor();
			}
		});
		JButton btnCompile = new JButton(ImageLoader.getImageIcon("Compile24.gif"));
		btnCompile.setToolTipText("Compile");
		btnCompile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manager.getEditorManager().compile();
			}
		});

		btnTerritoryFireball = new JToggleButton(ImageLoader.getImageIcon("fireball.png"));
		btnTerritoryFireball.setToolTipText("Add a fireball to the territory");
		btnTerritoryWhiteWall = new JToggleButton(ImageLoader.getScaledImageIcon("white_wall.png", 28, 24),true);
		btnTerritoryWhiteWall.setToolTipText("Add a steel wall to the territory");
		btnTerritoryRedWall = new JToggleButton(ImageLoader.getScaledImageIcon("red_wall.png", 28, 24));
		btnTerritoryRedWall.setToolTipText("Add a brick wall to the territory");
		btnTerritoryDelete = new JToggleButton(ImageLoader.getImageIcon("Delete24.gif"));
		btnTerritoryDelete.setToolTipText("Delete something from the territory");
		
		territoryGroup = new ButtonGroup();
		territoryGroup.add(btnTerritoryDelete);
		territoryGroup.add(btnTerritoryWhiteWall);
		territoryGroup.add(btnTerritoryRedWall);
		territoryGroup.add(btnTerritoryFireball);
		
		btnSimStart = new JButton(ImageLoader.getImageIcon("Run24.gif"));
		btnSimStart.setToolTipText("Start the simulation");
		btnSimStart.setEnabled(true);
		btnSimStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
					if(t != null && t.getState() == Thread.State.WAITING) {
						t.unpause();
						btnSimPause.setEnabled(true);
						btnSimStart.setEnabled(false);
						manager.getInfoLabel().setText("Simulation wieder gestartet");
					} else {
						manager.getInfoLabel().setText("Neue Simulation gestartet!");
						t = new Simulation(manager);
						t.start();
					}
			}
		});
		btnSimPause = new JButton(ImageLoader.getImageIcon("icon-pause-24.png"));
		btnSimPause.setToolTipText("Pause the simulation");
		btnSimPause.setEnabled(false);
		btnSimPause.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					t.pause();
					btnSimPause.setEnabled(false);
					btnSimStart.setEnabled(true);
			}
		});
		btnSimStop = new JButton(ImageLoader.getImageIcon("Stop24.gif"));
		btnSimStop.setToolTipText("Stop the simulation");
		btnSimStop.setEnabled(false);
		btnSimStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				t.interrupt();
			}
		});

		speedSlider = new JSlider(SwingConstants.HORIZONTAL);
		speedSlider.setToolTipText("Change the speed of the simulation");
		speedSlider.setMaximumSize(DIM_SLIDER_HORIZONTAL);
		speedSlider.setMinimum(0);
		speedSlider.setMaximum(1500);
		speedSlider.setMajorTickSpacing(500);
		speedSlider.setPaintLabels(true);
		speedSlider.setPaintTicks(true);
		speedSlider.addChangeListener(new SliderListener());
		speedSlider.setValue(250);

		// add components
		add(btnSave);
		add(btnCompile);
		addSeparator();
		add(btnTerritoryDelete);
		add(btnTerritoryFireball);
		add(btnTerritoryWhiteWall);
		add(btnTerritoryRedWall);
		addSeparator();
		add(btnSimStart);
		add(btnSimPause);
		add(btnSimStop);
		addSeparator();
		add(speedSlider);
		
		setCursor(ImageLoader.getCursor("cursor.png"));
	}

	@Override
	public void setOrientation(int o) {
		// set the orientation of the speed slider manually
		if (speedSlider != null) {
			speedSlider.setOrientation(o);
			if (o == HORIZONTAL) {
				speedSlider.setMaximumSize(DIM_SLIDER_HORIZONTAL);
				speedSlider.setPaintTicks(true);
			} else {
				speedSlider.setMaximumSize(DIM_SLIDER_VERTICAL);
				speedSlider.setPaintTicks(false);
			}
		}
		super.setOrientation(o);
	}

	public TerritoryAction getSelectedTerritoryAction() {
		if (btnTerritoryWhiteWall.isSelected())
			return TerritoryAction.ADD_WHITEWALL;
		else if (btnTerritoryFireball.isSelected())
			return TerritoryAction.ADD_FIREBALL;
		else if (btnTerritoryRedWall.isSelected())
			return TerritoryAction.ADD_REDWALL;
		else
			return TerritoryAction.DELETE;
	}

	public JButton getBtnSimStart() {
		return btnSimStart;
	}

	public JButton getBtnSimPause() {
		return btnSimPause;
	}

	public JButton getBtnSimStop() {
		return btnSimStop;
	}

}
