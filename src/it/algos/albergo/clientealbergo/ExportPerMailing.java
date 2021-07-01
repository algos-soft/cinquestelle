package it.algos.albergo.clientealbergo;

import it.algos.albergo.clientealbergo.tabelle.parente.Parentela;
import it.algos.albergo.clientealbergo.tabelle.parente.ParentelaModulo;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.albergo.tabelle.lingua.Lingua;
import it.algos.albergo.tabelle.lingua.LinguaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.dati.Dati;
import it.algos.base.importExport.methods.ExcelExportMethod;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.anagrafica.tabelle.titolo.Titolo;
import it.algos.gestione.anagrafica.tabelle.titolo.TitoloModulo;
import it.algos.gestione.indirizzo.Indirizzo;
import it.algos.gestione.indirizzo.IndirizzoModulo;
import it.algos.gestione.indirizzo.tabelle.citta.Citta;
import it.algos.gestione.indirizzo.tabelle.citta.CittaModulo;
import it.algos.gestione.indirizzo.tabelle.nazione.Nazione;
import it.algos.gestione.indirizzo.tabelle.nazione.NazioneModulo;
import it.algos.gestione.indirizzo.tabelle.provincia.Provincia;
import it.algos.gestione.indirizzo.tabelle.provincia.ProvinciaModulo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExportPerMailing implements Runnable {
	private Filtro filtro;
	private String pathname;
	private Modulo modCli = ClienteAlbergoModulo.get();
	FileOutputStream stream;
	ExcelExportMethod method;

	public ExportPerMailing(Filtro filtro, String pathname) {
		super();
		this.filtro = filtro;
		this.pathname = pathname;
	}

	public void run() {
		boolean cont=true;
		int[] chiavi=null;
		Ordine ordine=null;
		ArrayList<Campo> campi = null;

    	// ordine
		if (cont) {
	    	ordine = new Ordine();
	    	ordine.add(Anagrafica.Cam.cognome.get());
	    	ordine.add(Anagrafica.Cam.nome.get());
		}
    	
    	// elenco ordinato delle chiavi
		if (cont) {
			chiavi = modCli.query().valoriChiave(filtro, ordine);
		}

        /* crea il FileOutputStream di uscita */
		if (cont) {
			try {
		        File file = new File(pathname);
		        stream = new FileOutputStream(file);
			}
			catch (Exception e) {
				cont=false;
			}
		}

		// crea i campi corrispondenti alle colonne
		if (cont) {
			Campo c;
			campi = new ArrayList<Campo>();
			c = CampoFactory.testo("Titolo");
			campi.add(c);
			c = CampoFactory.testo("Cognome");
			campi.add(c);
			c = CampoFactory.testo("Nome");
			campi.add(c);
			c = CampoFactory.testo("Parentela");
			campi.add(c);
			c = CampoFactory.checkBox("Corrispondenza");
			campi.add(c);
			c = CampoFactory.testo("Email");
			campi.add(c);
			c = CampoFactory.testo("Tel");
			campi.add(c);
			c = CampoFactory.testo("Cell");
			campi.add(c);
			c = CampoFactory.testo("Lingua");
			campi.add(c);
			c = CampoFactory.testo("Indirizzo");
			campi.add(c);
			c = CampoFactory.testo("Indirizzo2");
			campi.add(c);
			c = CampoFactory.testo("Località");
			campi.add(c);
			c = CampoFactory.testo("Nazione");
			campi.add(c);
			c = CampoFactory.testo("Ult sogg");
			campi.add(c);





		}
		
		// crea l'Export Metod di tipo Excel
		if (cont) {
			method = new ExcelExportMethod();
			method.inizializza(campi, stream);
		}

		// scrive i titoli delle colonne
		if (cont) {
			ArrayList<String> titoli=new ArrayList<String>();
			for (Campo c : campi) {
				titoli.add(c.getNomeInterno());
			}
			method.writeTitles(titoli);
		}
		
		// spazzola le chiavi ed esporta ognuno
		if (cont) {
			for(int chiave : chiavi){
				esporta(chiave);
			}
		}
		
		// chiude il metodo
		if (cont) {
			method.close();
		}
		
        /* chiude il file di uscita */
        if (stream != null) {
            try {
				stream.flush();
	            stream.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
        }// fine del blocco if

		
	}
	
	
	// esporta un cliente
	private void esporta(int chiave){
		
		Query query;
		Filtro filtro;
		Dati dati;
		
		
		// creo la query standard
		filtro = FiltroFactory.codice(modCli, chiave);
		query = new QuerySelezione(modCli);
		query.setFiltro(filtro);
		Campo camTitolo = TitoloModulo.get().getCampo(Titolo.Cam.sigla.get());
		query.addCampo(camTitolo);
		query.addCampo(Anagrafica.Cam.cognome);
		query.addCampo(Anagrafica.Cam.nome);
		Campo camParentela=ParentelaModulo.get().getCampo(Parentela.Cam.sigla.get());
		query.addCampo(camParentela);
		Campo camPosta = modCli.getCampo(ClienteAlbergo.Cam.checkPosta);
		query.addCampo(camPosta);
		query.addCampo(Anagrafica.Cam.email);
		query.addCampo(Anagrafica.Cam.telefono);
		query.addCampo(Anagrafica.Cam.cellulare);
		Campo camLingua = LinguaModulo.get().getCampo(Lingua.Cam.sigla.get());
		query.addCampo(camLingua);
		
		// eseguo la query standard
		dati = modCli.query().querySelezione(query);
		String titolo = dati.getStringAt(camTitolo);
		String cognome = dati.getStringAt(Anagrafica.Cam.cognome.get());
		String nome = dati.getStringAt(Anagrafica.Cam.nome.get());
		String parentela = dati.getStringAt(camParentela);
		boolean posta = dati.getBoolAt(camPosta);
		String email = dati.getStringAt(Anagrafica.Cam.email.get());
		String tel = dati.getStringAt(Anagrafica.Cam.telefono.get());
		String cell = dati.getStringAt(Anagrafica.Cam.cellulare.get());
		String lingua = dati.getStringAt(camLingua);
		dati.close();
		
		// query speciali: indirizzo
        int codIndir = ClienteAlbergoModulo.getCodIndirizzo(chiave);
        Modulo modIndirizzo = IndirizzoModulo.get();
		Campo camIndir1 = IndirizzoModulo.get().getCampo(Indirizzo.Cam.indirizzo.get());
		Campo camIndir2 = IndirizzoModulo.get().getCampo(Indirizzo.Cam.indirizzo2.get());
		Campo camCAP = IndirizzoModulo.get().getCampo(Indirizzo.Cam.cap.get());
		Campo camCitta = CittaModulo.get().getCampo(Citta.Cam.citta.get());
		Campo camProvincia = ProvinciaModulo.get().getCampo(Provincia.Cam.sigla.get());
		Campo camNazione = NazioneModulo.get().getCampo(Nazione.Cam.nazione.get());
		filtro = FiltroFactory.codice(modIndirizzo, codIndir);
		query = new QuerySelezione(modIndirizzo);
		query.setFiltro(filtro);
		query.addCampo(camIndir1);
		query.addCampo(camIndir2);
		query.addCampo(camCAP);
		query.addCampo(camCitta);
		query.addCampo(camProvincia);
		query.addCampo(camNazione);
		dati = modIndirizzo.query().querySelezione(query);
		String indirizzo1 = dati.getStringAt(camIndir1);
		String indirizzo2 = dati.getStringAt(camIndir2);
		String CAP = dati.getStringAt(camCAP);
		String citta = dati.getStringAt(camCitta);
		String prov = dati.getStringAt(camProvincia);
		String nazione = dati.getStringAt(camNazione);
		if (nazione.equalsIgnoreCase("italia")) {
			nazione="";
		}
		dati.close();
		
		// elaborazione indirizzo
		String localita;
		if (Lib.Testo.isValida(CAP)) {
			localita = CAP + " " + citta;
		}
		else {
			localita = citta;
		}
		if (Lib.Testo.isValida(prov)) {
			localita+=" ("+prov+")";
		}
		
		// query speciali: ult soggiorno
		String ultSogg="";
        Date data = PresenzaModulo.getDataUltimoSoggiorno(chiave);
        if (Lib.Data.isValida(data)) {
            SimpleDateFormat df = Progetto.getDateFormat();
            ultSogg = df.format(data);
        }// fine del blocco if

		// creo la lista valori da scivere nelle celle
		ArrayList<Object> valori=new ArrayList<Object>();
		valori.add(titolo);
		valori.add(cognome);
		valori.add(nome);
		valori.add(parentela);
		valori.add(posta);
		valori.add(email);
		valori.add(tel);
		valori.add(cell);
		valori.add(lingua);
		valori.add(indirizzo1);
		valori.add(indirizzo2);
		valori.add(localita);
		valori.add(nazione);
		valori.add(ultSogg);


		method.writeRow(valori);
	}
	

}
