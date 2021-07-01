package it.algos.albergo.risorse;

import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class ColorChooserDialog extends JDialog {

	private boolean confirmed = false;
	private Color chosenColor = null;
	private static JColorChooser chooser=new CustomColorChooser();

	public ColorChooserDialog(Window parent, Color startColor) {
		super(parent);
		setModal(true);
		setAlwaysOnTop(true);
		setTitle("Scegli il colore");

		chooser.setPreviewPanel(new JPanel()); // removes preview panel

		if (startColor!=null) {
			chooser.setColor(startColor);
		}
		
		add(chooser);

		JButton botAnnulla = new JButton("Annulla");
		botAnnulla.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		JButton botConferma = new JButton("Conferma");
		botConferma.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				confirmed = true;
				chosenColor = chooser.getColor();
				dispose();
			}
		});

		Pannello panBottoni = PannelloFactory.orizzontale(null);
		panBottoni.add(Box.createHorizontalGlue());
		panBottoni.add(botAnnulla);
		panBottoni.add(botConferma);
		panBottoni.add(Box.createHorizontalGlue());
		panBottoni.getPanFisso().setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		add(panBottoni.getPanFisso(), BorderLayout.PAGE_END);

		pack();

		super.setLocationRelativeTo(parent);

	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public Color getColor() {
		return chosenColor;
	}
	
}
