package it.algos.albergo.cittaalbergo;

import it.algos.base.azione.AzSpecifica;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.gestione.indirizzo.tabelle.citta.Citta;
import it.algos.gestione.indirizzo.tabelle.citta.CittaNavigatore;
import it.algos.gestione.indirizzo.tabelle.provincia.Provincia;
import it.algos.gestione.indirizzo.tabelle.provincia.ProvinciaModulo;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import au.com.bytecode.opencsv.CSVReader;

public class CittaAlbergoNavigatore extends CittaNavigatore {

	public CittaAlbergoNavigatore(Modulo unModulo) {
		super(unModulo);
	}

	public void inizializza() {

		super.inizializza();
		this.addAzione(new AzImportaCodPS());

	}

	/**
	 * Importa i codici PS da file .csv esterno
	 */
	private void importaCodPS() {

		String messaggio = "Importa i codici di PS da file .csv nella tabella Città.";
		messaggio+="\n\nFormato del file: .csv";
		messaggio+="\nColonne: CODICE PS - NOME CITTA' - SIGLA PROVINCIA";
		messaggio+="\nEncoding: UTF-8";
		messaggio+="\n\nPremere OK per continuare.";
		int retCode = JOptionPane.showConfirmDialog(this.getPortaleNavigatore(), messaggio, "Importazione codici comuni P.S.", JOptionPane.OK_CANCEL_OPTION);
		
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
						if (nextLine.length >= 3) {
							String codice = nextLine[0];
							String nome = nextLine[1];
							String prov = nextLine[2];
							if (checkCittaPS(codice, nome, prov)) {
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
	 * Cerca di assegnare a una città un codice PS
	 * @param codPS il codice PS
	 * @param nome nome della città
	 * @param prov la sigla della provincia
	 * @return true se ha registrato il codice
	 */
	private boolean checkCittaPS(String codPS, String nome, String prov) {
		boolean scritto = false;
		Modulo modCitta = CittaAlbergoModulo.get();
		Modulo modProvincia = ProvinciaModulo.get();
		Campo campoNomeCitta = modCitta.getCampo(Citta.Cam.citta);
		Campo campoCodPS = modCitta.getCampo(CittaAlbergo.Cam.codicePS);
		Campo campoSiglaProvincia = modProvincia.getCampo(Provincia.Cam.sigla);
		
		Filtro filtroNome = FiltroFactory.crea(campoNomeCitta, nome);
		Filtro filtroProv = FiltroFactory.crea(campoSiglaProvincia, prov);
		Filtro filtro = new Filtro();
		filtro.add(filtroNome);
		filtro.add(filtroProv);
		filtro.setCaseSensitive(false);
		
		@SuppressWarnings("rawtypes")
		int[] chiavi = modCitta.query().valoriChiave(filtro);
		if (chiavi.length == 1) {
			int id = chiavi[0];
			
			String currCode = modCitta.query().valoreStringa(campoCodPS, id);
			if (!currCode.equals(codPS)) {
				modCitta.query().registra(id, CittaAlbergo.Cam.codicePS, codPS);
				scritto = true;
			}
			
		}
		return scritto;
	}

	/**
	 * Importa i codici PS. </p>
	 */
	private final class AzImportaCodPS extends AzSpecifica {

		/**
		 * Costruttore senza parametri.
		 */
		public AzImportaCodPS() {
			/* rimanda al costruttore della superclasse */
			super();

			/* regola le variabili */
			super.setIconaMedia("Import24");
			super.setTooltip("Importa i codici PS da file .csv esterno");
			super.setUsoLista(true);
		}// fine del metodo costruttore senza parametri

		/**
		 * actionPerformed, da ActionListener.
		 * 
		 * @param unEvento
		 *            evento che causa l'azione da eseguire <br>
		 */
		public void actionPerformed(ActionEvent unEvento) {
			try { // prova ad eseguire il codice
				importaCodPS();
			} catch (Exception unErrore) { // intercetta l'errore
				Errore.crea(unErrore);
			}// fine del blocco try-catch
		}

	} // fine della classe 'azione interna'

}
