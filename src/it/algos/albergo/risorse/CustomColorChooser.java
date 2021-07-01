package it.algos.albergo.risorse;

import javax.swing.JColorChooser;
import javax.swing.colorchooser.AbstractColorChooserPanel;

class CustomColorChooser extends JColorChooser {
	public CustomColorChooser() {
		super();
		
//		pare che non fuzioni in windows - alex mag-2014
//		// removes all the non-swatches panels
//        AbstractColorChooserPanel[] panels = getChooserPanels();
//        for (AbstractColorChooserPanel accp : panels) {
//            if (!accp.getDisplayName().equals("Swatches")) {
//            	removeChooserPanel(accp);
//            }
//        }

	}

}
