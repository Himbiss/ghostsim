package ghostsimulator.view;

import ghostsimulator.GhostManager;
import ghostsimulator.util.ImageLoader;

import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;


public class ToolBar extends JToolBar {

	private static Dimension DIM_SLIDER_HORIZONTAL = new Dimension(250,50);
	private static Dimension DIM_SLIDER_VERTICAL = new Dimension(50,250);
	private JSlider speedSlider;
	private GhostManager manager;

	public ToolBar(GhostManager manager) {
		super("Toolbar", SwingConstants.HORIZONTAL);
		this.manager = manager;

		JButton btnSave = new JButton(ImageLoader.getImageIcon("gnome-dev-floppy.png"));
		btnSave.setToolTipText("Save");
		JButton btnCompile = new JButton(ImageLoader.getImageIcon("Compile24.gif"));
		btnCompile.setToolTipText("Compile");

		JToggleButton btnTerritoryEktoplasm = new JToggleButton(ImageLoader.getImageIcon("fireball.png"));
		btnTerritoryEktoplasm.setToolTipText("Add a fireball to the territory");
		JToggleButton btnTerritoryWhiteWall = new JToggleButton(ImageLoader.getScaledImageIcon("white_wall.png", 28, 24));
		btnTerritoryWhiteWall.setToolTipText("Add a steel wall to the territory");
		JToggleButton btnTerritoryRedWall = new JToggleButton(ImageLoader.getScaledImageIcon("red_wall.png", 28, 24));
		btnTerritoryRedWall.setToolTipText("Add a brick wall to the territory");
		JToggleButton btnTerritoryDelete = new JToggleButton(ImageLoader.getImageIcon("Delete24.gif"));
		btnTerritoryDelete.setToolTipText("Delete something from the territory");
		
		ButtonGroup territoryGroup = new ButtonGroup();
		territoryGroup.add(btnTerritoryDelete);
		territoryGroup.add(btnTerritoryWhiteWall);
		territoryGroup.add(btnTerritoryRedWall);
		territoryGroup.add(btnTerritoryEktoplasm);

		JButton btnSimStart = new JButton(ImageLoader.getImageIcon("Run24.gif"));
		btnSimStart.setToolTipText("Start the simulation");
		JButton btnSimPause = new JButton(ImageLoader.getImageIcon("icon-pause-24.png"));
		btnSimPause.setToolTipText("Pause the simulation");
		JButton btnSimStop = new JButton(ImageLoader.getImageIcon("Stop24.gif"));
		btnSimStop.setToolTipText("Stop the simulation");

		speedSlider = new JSlider(SwingConstants.HORIZONTAL);
		speedSlider.setToolTipText("Change the speed of the simulation");
		speedSlider.setMaximumSize(DIM_SLIDER_HORIZONTAL);
		speedSlider.setMinimum(0);
		speedSlider.setMaximum(20);
		speedSlider.setMajorTickSpacing(5);
		speedSlider.setPaintLabels(true);
		speedSlider.setPaintTicks(true);

		// add components
		add(btnSave);
		add(btnCompile);
		addSeparator();
		add(btnTerritoryDelete);
		add(btnTerritoryEktoplasm);
		add(btnTerritoryWhiteWall);
		add(btnTerritoryRedWall);
		addSeparator();
		add(btnSimStart);
		add(btnSimStop);
		add(btnSimPause);
		addSeparator();
		add(speedSlider);
	}

	@Override
	public void setOrientation(int o) {
		// set the orientation of the speed slider manually
		if(speedSlider != null) {
			speedSlider.setOrientation(o);
			if(o == HORIZONTAL) {
				speedSlider.setMaximumSize(DIM_SLIDER_HORIZONTAL);
				speedSlider.setPaintTicks(true);
			} else {
				speedSlider.setMaximumSize(DIM_SLIDER_VERTICAL);
				speedSlider.setPaintTicks(false);
			}
		}
		super.setOrientation(o);
	}
	
	
}
