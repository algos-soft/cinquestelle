/**
 * Title:     PannelloPS
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      20-giu-2008
 */
package it.algos.albergo.stampeobbligatorie.notifica;

import it.algos.albergo.AlbergoPref;
import it.algos.albergo.stampeobbligatorie.PannelloObbligatorie;
import it.algos.albergo.stampeobbligatorie.StampeObbligatorieDialogo;
import it.algos.albergo.stampeobbligatorie.testastampe.NavigatoreDoppio;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampe;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibFile;
import it.algos.base.libreria.Libreria;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Pannello di gestione delle stampe delle schede di notifica </p>
 * 
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 20-giu-2008 ore 12.04.54
 */
@SuppressWarnings("serial")
public class PannelloNotifica extends PannelloObbligatorie {

	/* bottone Esporta PS */
	private JButton botEsportaPS;
	
	/* bottone Esporta Istat */
	private JButton botEsportaIstat;

	
	// memorizzato nella classe perché il listener del bottone Campia deve avere accesso
	private Campo campoPath;

	/**
	 * Costruttore base.
	 * <p/>
	 * 
	 * @param dialogo
	 *            di riferimento
	 */
	public PannelloNotifica(StampeObbligatorieDialogo dialogo) {

		/* rimanda al costruttore della superclasse */
		super(dialogo);

		try { // prova ad eseguire il codice
			/* regolazioni iniziali di riferimenti e variabili */
			this.inizia();
		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
		}// fine del blocco try-catch
	}// fine del metodo costruttore completo

	/**
	 * Regolazioni iniziali di riferimenti e variabili. </p> Metodo invocato
	 * direttamente dal costruttore (init) <br>
	 * 
	 * @throws Exception
	 *             unaEccezione
	 */
	private void inizia() throws Exception {
		try { // prova ad eseguire il codice
			this.setLogica(new NotificaLogica(this));

			Icon icona;
			JButton bot;
			Dimension dimEsporta;
			
			// crea il bottone Esporta PS
			icona = Lib.Risorse.getIconaBase("Export24");
			bot = this.creaBottone("Esporta PS non abilitato", null, icona);
			bot.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					esportaPS();
				}
			});
			dimEsporta = new Dimension(150, altBottoni);
			this.fixBot(bot, dimEsporta);

			this.setBotEsportaPS(bot);
			
			// crea il bottone Esporta Istat
			icona = Lib.Risorse.getIconaBase("Export24");
			bot = this.creaBottone("Esporta ISTAT non abilitato", null, icona);
			bot.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					esportaIstat();
				}
			});
			dimEsporta = new Dimension(150, altBottoni);
			this.fixBot(bot, dimEsporta);

			this.setBotEsportaIstat(bot);


		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
		}// fine del blocco try-catch
	}// fine del metodo inizia

	/**
	 * Aggiunge graficamente i bottoni.
	 * <p/>
	 */
	protected void addBottoni(Pannello pan) {
		pan.add(this.getBotStampa());
		pan.add(this.getBotEsportaPS());
		pan.add(this.getBotEsportaIstat());
		pan.add(this.getBotAnnulla());
		pan.add(this.getBotVaiCliente());
	}

	/**
	 * Sincronizza il bottone Esporta
	 */
	protected void sincronizzaStampa() {
		super.sincronizzaStampa();

		Date dataSelezionata = getDataSelezionata();
		if (Lib.Data.isValida(dataSelezionata)) {
			String giorno = Lib.Data.getDataBrevissima(dataSelezionata);
			getBotEsportaPS().setText("Esporta PS " + giorno);
			getBotEsportaPS().setEnabled(true);
			
			getBotEsportaIstat().setText("Esporta ISTAT " + giorno);
			getBotEsportaIstat().setEnabled(true);

		} else {
			getBotEsportaPS().setText("Esporta PS disabilitato");
			getBotEsportaPS().setEnabled(false);
			
			getBotEsportaIstat().setText("Esporta ISTAT disabilitato");
			getBotEsportaIstat().setEnabled(false);

		}

	}

	/**
	 * Ritorna il codice del cliente correntemente selezionato.
	 * <p/>
	 * Utilizzato dalla funzione Vai a Cliente
	 * 
	 * @return il codice del cliente selezionato
	 */
	protected int getCodClienteSelezionato() {
		return getNavNotifica().getCodClienteSelezionato();
	}

	/*
	 * Ritorna i codici *
	 */

	/**
	 * Ritorna il navigatore doppio.
	 * <p/>
	 */
	@Override
	protected NavigatoreDoppio getNavigatore() {
		/* variabili e costanti locali di lavoro */
		Navigatore nav;
		NavigatoreDoppio navDoppio = null;

		try { // prova ad eseguire il codice
			nav = this.getModuloTesta().getNavigatore(
					TestaStampe.Nav.notifica.get());
			if ((nav != null) && (nav instanceof NavigatoreDoppio)) {
				navDoppio = (NavigatoreDoppio) nav;
			}// fine del blocco if

		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
		}// fine del blocco try-catch

		/* valore di ritorno */
		return navDoppio;
	}

	/**
	 * @return il navigatore con sopra le camere e sotto i clienti
	 */
	private NotificaNavigatore getNavNotifica() {
		NotificaNavigatore navNotifica = null;
		Navigatore nav = this.getNavSlave();
		if (nav != null) {
			if (nav instanceof NotificaNavigatore) {
				navNotifica = (NotificaNavigatore) nav;
			}// fine del blocco if
		}// fine del blocco if
		return navNotifica;
	}

	private JButton getBotEsportaPS() {
		return botEsportaPS;
	}

	private void setBotEsportaPS(JButton botEsporta) {
		this.botEsportaPS = botEsporta;
	}

	
	
	private JButton getBotEsportaIstat() {
		return botEsportaIstat;
	}

	private void setBotEsportaIstat(JButton botEsportaIstat) {
		this.botEsportaIstat = botEsportaIstat;
	}

	/**
	 * Esporta le schede di notifica in formato Servizio Alloggiati della
	 * Polizia di Stato
	 */
	private void esportaPS() {

		WrapGruppoArrivato[] aGruppi = getNavNotifica().getGruppiArrivati();
		Date data = getDataSelezionata();

		Icon icon = Lib.Risorse.getIconaBase("Export24");

		int code = JOptionPane.showOptionDialog(this, getDialogComponent(data, aGruppi, getFileNamePS(data)),
				"Esporta per Servizio Alloggiati PS",
				JOptionPane.OK_CANCEL_OPTION, 
				JOptionPane.QUESTION_MESSAGE,
				icon,
				null,
				null
				);

		if (code == JOptionPane.OK_OPTION) {
			NotificaExport export = new NotificaExport(aGruppi, data, getExportPath(), getFileNamePS(data));
			export.run();
		}

	}
	
	/**
	 * Esporta le schede di notifica in formato Istat
	 */
	private void esportaIstat() {

		WrapGruppoArrivato[] aGruppi = getNavNotifica().getGruppiArrivati();
		Date data = getDataSelezionata();

		Icon icon = Lib.Risorse.getIconaBase("Export24");

		int code = JOptionPane.showOptionDialog(this, getDialogComponent(data, aGruppi, getFileNameIstat(data)),
				"Esporta per ISTAT",
				JOptionPane.OK_CANCEL_OPTION, 
				JOptionPane.QUESTION_MESSAGE,
				icon,
				null,
				null
				);

		if (code == JOptionPane.OK_OPTION) {
			IstatExport export = new IstatExport(aGruppi, data, getExportPath(), getFileNameIstat(data));
			export.run();
		}

	}

	
	
	/**
	 * Costruisce il contenuto del dialogo di esportazione
	 * @return il componente contenuto nel dialogo
	 */
	private Component getDialogComponent(Date data, WrapGruppoArrivato[] aGruppi, String filename){
		JPanel pan = new JPanel(new BorderLayout());
		
		// conta i totali gruppi e persone
		int totGruppi=0;
		int totPersone=0;
		for (WrapGruppoArrivato gruppo: aGruppi) {
			totGruppi++;
			totPersone=totPersone+gruppo.getPersoneTotali();
		}
		
		// registra un path di default nelle preferenze, se mancante
        if (getExportPath().equals("")) {
        	setExportPath(System.getProperty("user.home"));
		}
		
		// label
        pan.add(new JLabel("Destinazione"), BorderLayout.PAGE_START);

        // campo path, lazy creation
        if (campoPath==null) {
            campoPath = CampoFactory.testo("");
            campoPath.setLarghezza(300);
            campoPath.setAbilitato(false);
            campoPath.avvia();
		}
        campoPath.setValore(getExportPath());
        pan.add(campoPath.getPannelloCampo(),BorderLayout.CENTER);
        
        // bottone cambia path...
        JButton bot = new JButton("Cambia...");
        bot.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
                File file = LibFile.getDir("Seleziona la cartella");
                if (file != null) {
                    String newpath = file.getPath();
                    setExportPath(newpath);
                    campoPath.setValore(getExportPath());
                }// fine del blocco if
			}
		});
        pan.add(bot, BorderLayout.LINE_END);
        
        // legenda
        String sData = Lib.Data.getDataBreve(data);
        String text = sData+", "+totGruppi+" gruppi, "+totPersone+" persone";
        text+="\nFile: "+filename;
        JTextArea area = new JTextArea(text);
        area.setEditable(false);
        area.setOpaque(false);
        pan.add(area, BorderLayout.PAGE_END);

		return pan;
	}
	
	/**
	 * @return il path di esportazione dalle preferenze
	 */
	private String getExportPath(){
        Object path = AlbergoPref.Albergo.pathTxtPS.getWrap().getValore();
        return Libreria.getString(path);
	}
	
	/**
	 * Registra l'export path nelle preferenze
	 * @param il path da registrare
	 */
	private void setExportPath(String path){
        AlbergoPref.Albergo.pathTxtPS.getWrap().setValore(path);
        AlbergoPref.registra();
	}
	
	/**
	 * Ritorna il nome del file di esportazione PS
	 * @param data di competenza
	 * @return il nome del file di esportazione
	 */
	private String getFileNamePS(Date data){
		String filename = Lib.Data.getDateYYYYMMDD(data);
        filename+="_NOTIF.TXT";
        return filename;
	}
	
	/**
	 * Ritorna il nome del file di esportazione ISTAT
	 * @param data di competenza
	 * @return il nome del file di esportazione
	 */
	private String getFileNameIstat(Date data){
		String filename = Lib.Data.getDateYYYYMMDD(data);
        filename+="_ISTAT.TXT";
        return filename;
	}

	
	

}// fine della classe