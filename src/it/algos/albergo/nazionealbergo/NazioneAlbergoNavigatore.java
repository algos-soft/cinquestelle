package it.algos.albergo.nazionealbergo;

import it.algos.base.azione.AzSpecifica;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.gestione.indirizzo.tabelle.nazione.Nazione;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import au.com.bytecode.opencsv.CSVReader;

public class NazioneAlbergoNavigatore extends NavigatoreLS {

    public NazioneAlbergoNavigatore(Modulo unModulo) {
        super(unModulo);
        this.inizia();
    }

    
    private void inizia() {

        try { // prova ad eseguire il codice

            this.setUsaPannelloUnico(true);
            //this.setLista(new NazioneAlbergoLista(getPortaleLista()));
            this.addScheda(new NazioneAlbergoScheda(getModulo()));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia
    
	public void inizializza() {

		super.inizializza();
		this.addAzione(new AzImportaCodPS());

	}

	/**
	 * Importa i codici PS da file .csv esterno
	 */
	private void importaCodPS() {

		String messaggio = "Importa i codici di PS da file .csv nella tabella Nazioni.";
		messaggio+="\n\nFormato del file: .csv";
		messaggio+="\nColonne: CODICE PS - NOME NAZIONE";
		messaggio+="\nEncoding: UTF-8";
		messaggio+="\n\nPremere OK per continuare.";
		int retCode = JOptionPane.showConfirmDialog(this.getPortaleNavigatore(), messaggio, "Importazione codici nazioni P.S.", JOptionPane.OK_CANCEL_OPTION);
		
		if (retCode==JOptionPane.OK_OPTION) {
			
			final JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(this.getPortaleNavigatore());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				int quantiElaborati = 0;
				int quantiRegistrati = 0;

				File file = fc.getSelectedFile();
				CSVReader reader = null;
				try {
					reader = new CSVReader(new FileReader(file));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				String[] nextLine;
				try {
					while ((nextLine = reader.readNext()) != null) {
						quantiElaborati++;
						// nextLine[] is an array of values from the line
						if (nextLine.length >= 2) {
							String codice = nextLine[0];
							String nome = nextLine[1];
							if (checkNazionePS(codice, nome)) {
								quantiRegistrati++;
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				new MessaggioAvviso("Terminato."+
				"\nRecord elaborati: "+quantiElaborati+
				"\nCodici registrati: "+quantiRegistrati);

			}
			
		}
		

	}
	
	
	/**
	 * Cerca di assegnare a una nazione un codice PS
	 * @param codPS il codice PS
	 * @param nome nome della nazione
	 * @return true se ha registrato il codice
	 */
	private boolean checkNazionePS(String codPS, String nome) {
		boolean scritto = false;
		Modulo modNazione = NazioneAlbergoModulo.get();
		Campo campoNomeNazione = modNazione.getCampo(Nazione.Cam.nazione);
		Campo campoCodPS = modNazione.getCampo(NazioneAlbergo.Cam.codicePS);
		
		Filtro filtro = FiltroFactory.crea(campoNomeNazione, nome);
		filtro.setCaseSensitive(false);
		
		@SuppressWarnings("rawtypes")
		int[] chiavi = modNazione.query().valoriChiave(filtro);
		if (chiavi.length == 1) {
			int id = chiavi[0];
			
			String currCode = modNazione.query().valoreStringa(campoCodPS, id);
			if (!currCode.equals(codPS)) {
				modNazione.query().registra(id, NazioneAlbergo.Cam.codicePS, codPS);
				scritto = true;
			}
			
		}
		return scritto;
	}

	
	
	/**
	 * Importa i codici PS. </p>
	 */
	private final class AzImportaCodPS extends AzSpecifica {

		public AzImportaCodPS() {
			super();
			super.setIconaMedia("Import24");
			super.setTooltip("Importa i codici PS da file .csv esterno");
			super.setUsoLista(true);
		}

		public void actionPerformed(ActionEvent unEvento) {
			importaCodPS();
		}

	} // fine della classe 'azione interna'


}
