package ghostsimulator.view;

import ghostsimulator.controller.listener.CompileListener;
import ghostsimulator.controller.listener.PauseSimulationListener;
import ghostsimulator.controller.listener.SaveEditorListener;
import ghostsimulator.controller.listener.SliderListener;
import ghostsimulator.controller.listener.StartSimulationListener;
import ghostsimulator.controller.listener.StopSimulationListener;
import ghostsimulator.util.ImageLoader;
import ghostsimulator.util.Resources;

import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

public class ToolBar extends JToolBar {

	private static final long serialVersionUID = 2007794204117507675L;
	private static Dimension DIM_SLIDER_HORIZONTAL = new Dimension(250, 50);
	private static Dimension DIM_SLIDER_VERTICAL = new Dimension(50, 250);
	private JSlider speedSlider;
	private ButtonGroup territoryGroup;
	private JToggleButton btnTerritoryFireball;
	private JToggleButton btnTerritoryWhiteWall;
	private JToggleButton btnTerritoryRedWall;
	private JToggleButton btnTerritoryDelete;
	private JButton btnSimStart;
	private JButton btnSimPause;
	private JButton btnSimStop;

	public ButtonGroup getTerritoryGroup() {
		return territoryGroup;
	}

	public enum TerritoryAction {
		DELETE, ADD_FIREBALL, ADD_WHITEWALL, ADD_REDWALL
	};

	public ToolBar() {
		super("Toolbar", SwingConstants.HORIZONTAL);

		JButton btnSave = new JButton(ImageLoader.getImageIcon("gnome-dev-floppy.png"));
		btnSave.setToolTipText(Resources.getValue("btn.save.txt"));
		btnSave.addActionListener(new SaveEditorListener());
		JButton btnCompile = new JButton(ImageLoader.getImageIcon("Compile24.gif"));
		btnCompile.setToolTipText(Resources.getValue("btn.compile.txt"));
		btnCompile.addActionListener(new CompileListener());

		btnTerritoryFireball = new JToggleButton(ImageLoader.getImageIcon("fireball.png"));
		btnTerritoryFireball.setToolTipText(Resources.getValue("btn.fireball.txt"));
		btnTerritoryWhiteWall = new JToggleButton(ImageLoader.getScaledImageIcon("white_wall.png", 28, 24),true);
		btnTerritoryWhiteWall.setToolTipText(Resources.getValue("btn.whitewall.txt"));
		btnTerritoryRedWall = new JToggleButton(ImageLoader.getScaledImageIcon("red_wall.png", 28, 24));
		btnTerritoryRedWall.setToolTipText(Resources.getValue("btn.redwall.txt"));
		btnTerritoryDelete = new JToggleButton(ImageLoader.getImageIcon("Delete24.gif"));
		btnTerritoryDelete.setToolTipText(Resources.getValue("btn.deleteTerritory.txt"));
		
		territoryGroup = new ButtonGroup();
		territoryGroup.add(btnTerritoryDelete);
		territoryGroup.add(btnTerritoryWhiteWall);
		territoryGroup.add(btnTerritoryRedWall);
		territoryGroup.add(btnTerritoryFireball);
		
		btnSimStart = new JButton(ImageLoader.getImageIcon("Run24.gif"));
		btnSimStart.setToolTipText(Resources.getValue("btn.simStart.txt"));
		btnSimStart.setEnabled(true);
		btnSimStart.addActionListener(new StartSimulationListener());
		btnSimPause = new JButton(ImageLoader.getImageIcon("icon-pause-24.png"));
		btnSimPause.setToolTipText(Resources.getValue("btn.simPause.txt"));
		btnSimPause.setEnabled(false);
		btnSimPause.addActionListener(new PauseSimulationListener());
		btnSimStop = new JButton(ImageLoader.getImageIcon("Stop24.gif"));
		btnSimStop.setToolTipText(Resources.getValue("btn.simStop.txt"));
		btnSimStop.setEnabled(false);
		btnSimStop.addActionListener(new StopSimulationListener());

		speedSlider = new JSlider(SwingConstants.HORIZONTAL);
		speedSlider.setToolTipText(Resources.getValue("btn.speedSlider.txt"));
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

	public void setPauseStartStopEnabled(boolean pause, boolean start, boolean stop) {
		btnSimPause.setEnabled(pause);
		btnSimStart.setEnabled(start);
		btnSimStop.setEnabled(stop);
	}
}
