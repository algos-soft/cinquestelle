package it.algos.albergo.odg.odgrisorse;

import it.algos.albergo.odg.OdgLogica;
import it.algos.albergo.tabelle.tipirisorsa.TipoRisorsa;
import it.algos.albergo.tabelle.tipirisorsa.TipoRisorsaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.matrice.MatriceDoppia;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/** 
 * Dialogo di impostazione della stampa Odg Risorse
 */
@SuppressWarnings("serial")
public class OdgRisorseDialogo extends JDialog {
	private Date dataDefault;
	private JComboBox<Settore> comboSettori;
	private Campo campoData;
	private Campo campoNote;

	/**
	 * Costruttore
	 * @param la data proposta
	 */
	public OdgRisorseDialogo(Date dataOdg) {
		super();
		this.dataDefault = dataOdg;
		inizia();
		pack();
		Lib.Gui.centraFinestra(this);
	}
	

	private void inizia(){
		
		setModal(false);
		setAlwaysOnTop(false);
		setTitle("Stampa ordini servizio");
		add(creaPanContenuti().getPanFisso());
		add(creaPanBottoni(),BorderLayout.PAGE_END);

		
	}
	
	private Pannello creaPanContenuti(){
		Pannello pan = PannelloFactory.verticale(null);
		pan.getPanFisso().setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		// campo data
		campoData = CampoFactory.data("data");
		campoData.decora().eliminaEtichetta();
		campoData.decora().etichettaSinistra("Data");
		campoData.avvia();
		pan.add(campoData.getPannelloCampo());
		if (Lib.Data.isValida(dataDefault)) {
			campoData.setValore(dataDefault);
		}
		
		// combo settori
		Settore[] settori = getSettori();
		comboSettori = new JComboBox<Settore>(settori);
		if (settori.length>0) {
			comboSettori.setSelectedIndex(0);
		}
		
		// crea un pannello con label e aggiunge
		Pannello panCombo = PannelloFactory.orizzontale(null);
		panCombo.setAllineamento(Layout.ALLINEA_CENTRO);
		panCombo.add(new JLabel("Settore"));
		panCombo.add(comboSettori);
		pan.add(panCombo.getPanFisso());
		
		// campo note in stampa
		campoNote = CampoFactory.testoArea("note stampa");
		campoNote.setNumeroRighe(8);
		campoNote.setLarScheda(400);
		campoNote.avvia();
		pan.add(campoNote.getPannelloComponenti());
		
		return pan;

		
	}
	
	
	private JPanel creaPanBottoni(){
		JPanel pan = new JPanel();
		
		JButton bAnnulla = new JButton("Annulla");
		bAnnulla.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		pan.add(bAnnulla);
		
		JButton bConferma = new JButton("Conferma");
		bConferma.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				eventoConferma();
			}
		});
		pan.add(bConferma);

		return pan;
	}



	private void eventoConferma() {
		int idSettore = getIdSettore();
		Date data = getData();
		String note = getNote();
		
		if ((idSettore<=0) || (!Lib.Data.isValida(data))) {
			JOptionPane.showMessageDialog(this, "Specificare il settore e la data.", "Errore", JOptionPane.ERROR_MESSAGE);
		}else{
	        OdgLogica.stampaOdgRisorse(data, idSettore, note);
		}
	}
	
	
	/**
	 * @return l'id del settore selezionato, 0 se non selezionato
	 */
	private int getIdSettore(){
		int idSettore=0;
		Settore sett = (Settore)comboSettori.getSelectedItem();
		if (sett!=null) {
			idSettore=sett.getIdTipoRisorsa();
		}
		return idSettore;
	}
	
	/**
	 * @return la data inserita, null se assente
	 */
	private Date getData(){
		return campoData.getData();
	}
	
	/**
	 * @return le note inserite
	 */
	private String getNote(){
		return campoNote.getString();
	}




	/**
	 * Restituisce un array con tutti gli oggetti Settore per il combo
	 */
	private Settore[] getSettori(){
		ArrayList<Settore> listaSettori = new ArrayList<Settore>();
		Modulo mod = TipoRisorsaModulo.get();
		MatriceDoppia mat = mod.query().valoriDoppi(TipoRisorsa.Cam.settore.get());
		for (int i = 0; i < mat.size(); i++) {
			int id = mat.getCodiceAt(i+1);
			String settore = Libreria.getString(mat.getValoreAt(i+1));
			listaSettori.add(new Settore(id, settore));
		}
		return listaSettori.toArray(new Settore[0]);
	}
	
	
	class Settore {
		int idTipoRisorsa;
		String nomeSettore;
		public Settore(int idTipoRisorsa, String nomeSettore) {
			super();
			this.idTipoRisorsa = idTipoRisorsa;
			this.nomeSettore = nomeSettore;
		}
		
		@Override
		public String toString() {
			return nomeSettore;
		}

		public int getIdTipoRisorsa() {
			return idTipoRisorsa;
		}

		
	}
}
