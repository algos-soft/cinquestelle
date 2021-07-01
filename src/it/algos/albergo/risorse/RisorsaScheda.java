package it.algos.albergo.risorse;

import it.algos.albergo.risorse.Risorsa.Cam;
import it.algos.base.campo.base.Campo;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.scheda.SchedaDefault;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RisorsaScheda extends SchedaDefault {

	private JPanel previewBox;
	private JButton chooseColorButton;
	private JButton resetColorButton;

	public RisorsaScheda(Modulo modulo) {
		super(modulo);
		inizia();
	}

	private void inizia() {
		setOpaque(true);
	}

	@Override
	public void avvia(int codice) {
		super.avvia(codice);
		int codColore = Libreria.getInt(getValore(Risorsa.Cam.colore.get()));
		Color colore;
		if (codColore != 0) {
			colore = getColore(codColore);
		} else {
			colore = Risorsa.COLORE_DEFAULT_CELLE;
		}
		// setColore(colore);
		previewBox.setBackground(colore);
	}

	@Override
	protected void creaPagine() {
		super.creaPagine();

		Pannello pan = creaPanColore();
		addComponente(pan.getPanFisso());

	}

	private Pannello creaPanColore() {
		Pannello panColore = PannelloFactory.orizzontale(null);
		panColore.setAllineamento(Layout.ALLINEA_CENTRO);
		panColore.setRidimensionaComponenti(false);
		panColore.setGapMassimo(10);

		previewBox = new JPanel();
		previewBox.setOpaque(true);
		previewBox.setPreferredSize(new Dimension(30, 30));
		Lib.Comp.bloccaDim(previewBox);

		chooseColorButton = new JButton("Scegli...");
		chooseColorButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Window parent = getRisorsaScheda().getPortale().getFinestra().getFinestraBase();
				ColorChooserDialog dialog = new ColorChooserDialog(parent, null);
				dialog.setVisible(true);
				if (dialog.isConfirmed()) {
					Color newColor = dialog.getColor();
					if (newColor != null) {
						setColore(newColor);
					}
				}

			}
		});

		// bottone ripristina
		resetColorButton = new JButton("Ripristina");
		resetColorButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setColore(null);
			}
		});

		panColore.add(new JLabel("Colore:"));
		panColore.add(previewBox);
		panColore.add(chooseColorButton);
		panColore.add(resetColorButton);

		return panColore;

	}

	private Color getColore(int codColore) {
		return new Color(codColore);
	}

	private int getCodColore(Color colore) {
		return colore.getRGB();
	}

	private void setColore(Color colore) {
		Campo campo = getCampo(Cam.colore);

		if (colore!=null) {
			previewBox.setBackground(colore);
			campo.setValore(getCodColore(colore));
		} else {
			previewBox.setBackground(Risorsa.COLORE_DEFAULT_CELLE);
			campo.setValore(0);
		}
	}

	private RisorsaScheda getRisorsaScheda() {
		return this;
	}

	@Override
	public void sincronizza() {
		super.sincronizza();
		chooseColorButton.setEnabled(getCodice() > 0);
		resetColorButton.setEnabled(getCodice() > 0);
	}

}
