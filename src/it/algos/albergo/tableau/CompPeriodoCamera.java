/**
 * Title:     CompCamera
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25-feb-2009
 */
package it.algos.albergo.tableau;

import it.algos.albergo.AlbergoLib;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFlusso;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Date;

/**
 * Componente grafico per una camera nella barra delle camere Usato dal renderer
 * delle celle
 * <p/>
 * E' composto da 3 subcomponenti: - uno iniziale per l'entrata (arrivo o
 * cambio) - uno centrale per il soggiorno (nome cliente) - uno finale per
 * l'uscita (cambio o partenza)
 * 
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 25-feb-2009 ore 15.09.49
 */
class CompPeriodoCamera extends CompPeriodoAbs {

	private JLabel labelEntrata;

	private JLabel labelCentro;

	private JLabel labelUscita;

	/**
	 * Costruttore completo senza parametri.<br>
	 */
	public CompPeriodoCamera() {

		/* rimanda al costruttore della superclasse */
		super();

		try { // prova ad eseguire il codice
			/* regolazioni iniziali di riferimenti e variabili */
			this.inizia();
		} catch (Exception unErrore) { // intercetta l'errore
			new Errore(unErrore);
		}// fine del blocco try-catch
	}// fine del metodo costruttore completo

	/**
	 * Regolazioni immediate di riferimenti e variabili. <br>
	 * Metodo chiamato direttamente dal costruttore <br>
	 * 
	 * @throws Exception
	 *             unaEccezione
	 */
	private void inizia() throws Exception {

		try { // prova ad eseguire il codice

			/* pannello generale */
			Pannello pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);

			/* componente di entrata */
			pan.add(this.creaCompEntrata());

			/* molla */
			pan.add(Box.createHorizontalGlue());

			/* componente centrale */
			pan.add(this.creaCompCentrale());

			/* molla */
			pan.add(Box.createHorizontalGlue());

			/* componente di uscita */
			pan.add(this.creaCompUscita());

			/* aggiunge il pannello completo */
			this.add(pan.getPanFisso());
			
		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
		}// fine del blocco try-catch

	}

	/**
	 * Crea il componente di entrata.
	 * <p/>
	 * 
	 * @return il componente di entrata
	 */
	private Component creaCompEntrata() {
		/* variabili e costanti locali di lavoro */
		JLabel label = null;

		try { // prova ad eseguire il codice
			label = new JLabel();
			label.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 0));
			label.setFont(Tableau.FONT_CAMERE_CAMBIO);
			label.setForeground(Tableau.COLORE_CAMERE_CAMBIO);
			this.setLabelEntrata(label);
		} catch (Exception unErrore) { // intercetta l'errore
			new Errore(unErrore);
		} // fine del blocco try-catch

		/* valore di ritorno */
		return label;
	}

	/**
	 * Crea il componente centrale.
	 * <p/>
	 * 
	 * @return il componente di entrata
	 */
	private Component creaCompCentrale() {
		/* variabili e costanti locali di lavoro */
		JLabel label = null;

		try { // prova ad eseguire il codice
			label = new JLabel();
			this.setLabelCentro(label);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setFont(Tableau.FONT_NOMI_CLIENTI);
			label.setOpaque(false);
			label.setForeground(Tableau.COLORE_NOME_CLIENTE);

			label.setMinimumSize(new Dimension(10, 20));

		} catch (Exception unErrore) { // intercetta l'errore
			new Errore(unErrore);
		} // fine del blocco try-catch

		/* valore di ritorno */
		return label;
	}

	/**
	 * Crea il componente di uscita.
	 * <p/>
	 * 
	 * @return il componente di uscita
	 */
	private Component creaCompUscita() {
		/* variabili e costanti locali di lavoro */
		JLabel label = null;

		try { // prova ad eseguire il codice
			label = new JLabel();
			label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 3));
			label.setFont(Tableau.FONT_CAMERE_CAMBIO);
			label.setForeground(Tableau.COLORE_CAMERE_CAMBIO);
			this.setLabelUscita(label);
		} catch (Exception unErrore) { // intercetta l'errore
			new Errore(unErrore);
		} // fine del blocco try-catch

		/* valore di ritorno */
		return label;
	}

	@Override
	public void pack(GrafoPrenotazioni graph, UserObjectPeriodo uo) {

		super.pack(graph, uo);

		String testo = "";
		int codCamera;

		/* nome cliente */
		String nome = Libreria.getString(get(UserObjectPeriodo.KEY_CLIENTE));
		this.getLabelCentro().setText(nome);

		/* camera provenienza */
		codCamera = Libreria.getInt(get(UserObjectPeriodo.KEY_PROVENIENZA));
		if (codCamera != 0) {
			testo = graph.getPanGrafi().getNomeRisorsa(codCamera);
		}// fine del blocco if
		setCameraEntrata(testo);

		/* camera destinazione */
		codCamera = Libreria.getInt(get(UserObjectPeriodo.KEY_DESTINAZIONE));
		if (codCamera != 0) {
			testo = graph.getPanGrafi().getNomeRisorsa(codCamera);
		}// fine del blocco if
		setCameraUscita(testo);
		
		 /* tooltip */
		 setToolTipText(creaTooltipText(graph, uo));

	}

	/**
	 * Assegna il nome della camera.
	 * <p/>
	 * 
	 * @param nome
	 *            della camera
	 */
	public void setNomeCliente(String nome) {
		this.getLabelCentro().setText(nome);
	}


	/**
	 * Assegna la camera di entrata.
	 * <p/>
	 * 
	 * @param nome
	 *            della camera di entrata
	 */
	public void setCameraEntrata(String nome) {
		/* variabili e costanti locali di lavoro */
		String testo = "";
		if (Lib.Testo.isValida(nome)) {
			testo = ">" + nome;
		}// fine del blocco if
		this.getLabelEntrata().setText(testo);
	}

	/**
	 * Assegna la camera di uscita.
	 * <p/>
	 * 
	 * @param nome
	 *            della camera di uscita
	 */
	public void setCameraUscita(String nome) {
		/* variabili e costanti locali di lavoro */
		String testo = "";
		if (Lib.Testo.isValida(nome)) {
			testo = nome + ">";
		}// fine del blocco if
		this.getLabelUscita().setText(testo);
	}

	private JLabel getLabelEntrata() {
		return labelEntrata;
	}

	private void setLabelEntrata(JLabel labelEntrata) {
		this.labelEntrata = labelEntrata;
	}

	private JLabel getLabelCentro() {
		return labelCentro;
	}

	private void setLabelCentro(JLabel labelCentro) {
		this.labelCentro = labelCentro;
	}

	private JLabel getLabelUscita() {
		return labelUscita;
	}

	private void setLabelUscita(JLabel labelUscita) {
		this.labelUscita = labelUscita;
	}

	

}// fine della classe