package it.algos.albergo.stampeobbligatorie.notifica;

import it.algos.albergo.AlbergoPref;
import it.algos.albergo.cittaalbergo.CittaAlbergo;
import it.algos.albergo.cittaalbergo.CittaAlbergoModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.clientealbergo.indirizzoalbergo.IndirizzoAlbergoModulo;
import it.algos.albergo.clientealbergo.tabelle.tipodocumento.TipoDocumento;
import it.algos.albergo.clientealbergo.tabelle.tipodocumento.TipoDocumentoModulo;
import it.algos.albergo.nazionealbergo.NazioneAlbergo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibTesto;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.indirizzo.Indirizzo;
import it.algos.gestione.indirizzo.tabelle.citta.Citta;
import it.algos.gestione.indirizzo.tabelle.citta.CittaModulo;
import it.algos.gestione.indirizzo.tabelle.nazione.Nazione;
import it.algos.gestione.indirizzo.tabelle.nazione.NazioneModulo;
import it.algos.gestione.indirizzo.tabelle.provincia.Provincia;
import it.algos.gestione.indirizzo.tabelle.provincia.ProvinciaModulo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public abstract class ExportBase implements Runnable {
	private WrapGruppoArrivato[] aGruppi;
	private Date data;
	private Path path;
	private Path file;
	
	
	/**
	 * Costruttore
	 * @param aGruppi array contenente i wrapper con i gruppi arrivati
	 * @param path la cartella di esportazione
	 * @param filename il nome del file di esportazione
	 * */
	public ExportBase(WrapGruppoArrivato[] aGruppi, Date data, String path,
			String filename) {
		super();
		this.aGruppi = aGruppi;
		this.data = data;
		this.path = Paths.get(path);
		this.file = Paths.get(filename);;
	}

	
	public void run() {
		
		// create folder if not exists
		if (Files.notExists(getPath())) {
			try {
				Files.createDirectory(getPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// create output file
		Path fullpath = getPath().resolve(getFile());
		try {
			Files.deleteIfExists(fullpath);
			Files.createFile(fullpath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// create bytes array
		ArrayList<Byte> listaBytes = new ArrayList<Byte>();
		ArrayList<String> lines = createLines();
		for (int i = 0; i < lines.size(); i++) {
			String string = lines.get(i);
			byte[] b = string.getBytes(Charset.forName("ISO-8859-1"));
			for (int j = 0; j < b.length; j++) {
				listaBytes.add(b[j]);
			}
			if (i<lines.size()-1) {
				listaBytes.add(new Byte("13"));
				listaBytes.add(new Byte("10"));				
			}
		}
		byte[] bytes = new byte[listaBytes.size()];
		for (int i = 0; i < listaBytes.size(); i++) {
			bytes[i]=listaBytes.get(i);
		}
		
		// write output file in 1 shot
		try {
			Files.write(fullpath, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// messaggio terminato
		new MessaggioAvviso("Terminato.");
		
	}
	
	
	/**
	 * Crea tutte le linee per i gruppi arrivati
	 * @return le linee create
	 */
	private ArrayList<String> createLines(){
		ArrayList<String> lines = new ArrayList<String>();
		
		for(WrapGruppoArrivato gruppo : getaGruppi()){
			lines.addAll(createLinesGruppo(gruppo));
		}
		
		return lines;
	}


	
	/**
	 * Crea le linee per un singolo gruppo arrivato
	 * @param gruppo il gruppo arrivato
	 * @return le linee create
	 */
	private ArrayList<String> createLinesGruppo(WrapGruppoArrivato gruppo){
		ArrayList<String> lines = new ArrayList<String>();
		int counter=0;
		// codice posizione per ISTAT - id scheda notifica + progressivo all'interno dela scheda
		String codPosizione="";	
		
		// crea la linea per il capogruppo
		TipoAlloggiato tipo;
		if (gruppo.isSingolo()) {
			tipo=TipoAlloggiato.SINGOLO;
		} else {
			tipo=TipoAlloggiato.CAPOGRUPPO;
		}
		counter++;
		codPosizione=""+gruppo.getIdRecordNotifica()+"."+counter;
		lines.add(createLine(gruppo.getCodCapo(),gruppo.getCodPeriodo(), tipo, codPosizione));
		
		// crea le linee per i membri
		
		for(int cod : gruppo.getCodMembri()){
			counter++;
			codPosizione=""+gruppo.getIdRecordNotifica()+"."+counter;
			lines.add(createLine(cod,gruppo.getCodPeriodo(), TipoAlloggiato.MEMBRO, codPosizione));
		}
		
		return lines;
	}
	
	

	/**
	 * Crea una mappa contenente le informazioni per una singola riga
	 * @param codCliente il codice del cliente
	 * @param codPeriodo l'id del periodo di riferimento
	 * @param tipo il tipo di alloggiato
	 * @param codPosizione ISTAT
	 */
	protected HashMap<Parti, String> creaMappa(int codCliente, int codPeriodo, TipoAlloggiato tipo, String codPosizione){
		HashMap<Parti, String> mappa = new HashMap<Parti, String>();
		String stringa;
		Dati dati;
		Query query;
		
		putMappa(mappa, Parti.tipoAlloggiato, tipo.getCodice());
		putMappa(mappa, Parti.dataArrivo, stringaData(getData()));
		
		// recupera i giorni di permanenza dal periodo
		int quantiGiorni=0;
		if (codPeriodo>0) {
			Modulo modPeriodo = PeriodoModulo.get();
			quantiGiorni = modPeriodo.query().valoreInt(modPeriodo.getCampo(Periodo.Cam.giorni), codPeriodo);
		}
		// limite max imposto dalle specifiche
		if (quantiGiorni>30) {
			quantiGiorni=30;
		}
		// se zero lo portiamo a 1
		if (quantiGiorni==0) {
			quantiGiorni=1;
		}
		// porta a 2 cifre con zeri a sx
		String sGiorni = ""+quantiGiorni;
		if (sGiorni.length()==1) {
			sGiorni="0"+sGiorni;
		}
		putMappa(mappa, Parti.ggPermanenza, sGiorni);

		// query su anagrafica cliente
		Modulo modClienti = ClienteAlbergoModulo.get();
		query = new QuerySelezione(modClienti);
		for (Campo c : modClienti.getCampiFisici()) {
			query.addCampo(c);
		}
		query.setFiltro(FiltroFactory.codice(modClienti, codCliente));
		dati = modClienti.query().querySelezione(query);
		stringa = dati.getStringAt(modClienti.getCampo(Anagrafica.Cam.cognome));
		putMappa(mappa, Parti.cognome, stringa);
		stringa = dati.getStringAt(modClienti.getCampo(Anagrafica.Cam.nome));
		putMappa(mappa, Parti.nome, stringa);
		int nSesso = dati.getIntAt(modClienti.getCampo(Anagrafica.Cam.sesso));
		stringa="";
		if (nSesso==1) {
			stringa="1";	//M
		} else {
			stringa="2";	//F
		}
		putMappa(mappa, Parti.sesso, stringa);
		Date datanascita = dati.getDataAt(modClienti.getCampo(ClienteAlbergo.Cam.dataNato));
		putMappa(mappa, Parti.dataNascita, stringaData(datanascita));
		int idTipoDoc = dati.getIntAt(modClienti.getCampo(ClienteAlbergo.Cam.tipoDoc));
		String numeroDoc = dati.getStringAt(modClienti.getCampo(ClienteAlbergo.Cam.numDoc));
		int idLuogoNato=dati.getIntAt(modClienti.getCampo(ClienteAlbergo.Cam.luogoNato));
		dati.close();
		
		// id indirizzo residenza
		//int idIndirizzoResidenza=dati.getIntAt(modClienti.getCampo(ClienteAlbergo.Cam.indirizzoInterno));
		int idIndirizzoResidenza = ClienteAlbergoModulo.getCodIndirizzo(codCliente);
		
		// query per tipo documento
		String psTipoDoc="";
		if (idTipoDoc>0) {
			Modulo modTipoDoc = TipoDocumentoModulo.get();
			Campo camCodPS = modTipoDoc.getCampo(TipoDocumento.Cam.codicePS);
			psTipoDoc = modTipoDoc.query().valoreStringa(camCodPS, idTipoDoc);
		}

		// query per citta e nazione nascita
		int idProvNato=0;
		int idNazNato=0;
		String psComuneNato="";
		if (idLuogoNato>0) {
			Modulo modCitta = CittaModulo.get();
			query = new QuerySelezione(modCitta);
			for (Campo c : modCitta.getCampiFisici()) {
				query.addCampo(c);
			}
			query.setFiltro(FiltroFactory.codice(modCitta, idLuogoNato));
			dati = modCitta.query().querySelezione(query);
			psComuneNato = dati.getStringAt(modCitta.getCampo(CittaAlbergo.Cam.codicePS));
			idProvNato=dati.getIntAt(modCitta.getCampo(Citta.Cam.linkProvincia));
			idNazNato=dati.getIntAt(modCitta.getCampo(Citta.Cam.linkNazione));
			dati.close();
		}
		
		// query per provincia nascita
		String siglaProvNato="";
		if (idProvNato>0) {
			Modulo modProv = ProvinciaModulo.get();
			query = new QuerySelezione(modProv);
			for (Campo c : modProv.getCampiFisici()) {
				query.addCampo(c);
			}
			query.setFiltro(FiltroFactory.codice(modProv, idProvNato));
			dati = modProv.query().querySelezione(query);
			siglaProvNato = dati.getStringAt(modProv.getCampo(Provincia.Cam.sigla));
			dati.close();
		}
		
		// query per sigla nazione nascita
		String siglaNazNato="";
		String psNazNato="";
		if (idNazNato>0) {
			Modulo modNaz = NazioneModulo.get();
			query = new QuerySelezione(modNaz);
			for (Campo c : modNaz.getCampiFisici()) {
				query.addCampo(c);
			}
			query.setFiltro(FiltroFactory.codice(modNaz, idNazNato));
			dati = modNaz.query().querySelezione(query);
			siglaNazNato = dati.getStringAt(modNaz.getCampo(Nazione.Cam.sigla2));
			psNazNato = dati.getStringAt(modNaz.getCampo(NazioneAlbergo.Cam.codicePS));
			dati.close();
		}
		
		// query per id citta' residenza
		int idCittaResidenza=0;
		if (idIndirizzoResidenza>0) {
			Modulo modIndirizzo = IndirizzoAlbergoModulo.get();
			query = new QuerySelezione(modIndirizzo);
			for (Campo c : modIndirizzo.getCampiFisici()) {
				query.addCampo(c);
			}
			query.setFiltro(FiltroFactory.codice(modIndirizzo, idIndirizzoResidenza));
			dati = modIndirizzo.query().querySelezione(query);
			idCittaResidenza = dati.getIntAt(modIndirizzo.getCampo(Indirizzo.Cam.citta));
			dati.close();
		}
		
		// query per id provincia residenza
		int idProvResidenza=0;
		if (idCittaResidenza>0) {
			Modulo modCitta = CittaAlbergoModulo.get();
			query = new QuerySelezione(modCitta);
			for (Campo c : modCitta.getCampiFisici()) {
				query.addCampo(c);
			}
			query.setFiltro(FiltroFactory.codice(modCitta, idCittaResidenza));
			dati = modCitta.query().querySelezione(query);
			idProvResidenza = dati.getIntAt(modCitta.getCampo(Citta.Cam.linkProvincia));
			dati.close();
		}

		// query per sigla provincia residenza
		String siglaProvResidenza="";
		if (idProvResidenza>0) {
			Modulo modProv = ProvinciaModulo.get();
			query = new QuerySelezione(modProv);
			for (Campo c : modProv.getCampiFisici()) {
				query.addCampo(c);
			}
			query.setFiltro(FiltroFactory.codice(modProv, idProvResidenza));
			dati = modProv.query().querySelezione(query);
			siglaProvResidenza = dati.getStringAt(modProv.getCampo(Provincia.Cam.sigla));
			dati.close();
		}

		// query per codice ps citta' residenza
		String psCittaResidenza="";
		if (idCittaResidenza>0) {
			Modulo modCitta = CittaModulo.get();
			query = new QuerySelezione(modCitta);
			for (Campo c : modCitta.getCampiFisici()) {
				query.addCampo(c);
			}
			query.setFiltro(FiltroFactory.codice(modCitta, idCittaResidenza));
			dati = modCitta.query().querySelezione(query);
			psCittaResidenza = dati.getStringAt(modCitta.getCampo(CittaAlbergo.Cam.codicePS));
			dati.close();
		}
		
		// query per id nazione residenza
		int idNazioneResidenza=0;
		if (idCittaResidenza>0) {
			Modulo modCitta = CittaModulo.get();
			query = new QuerySelezione(modCitta);
			for (Campo c : modCitta.getCampiFisici()) {
				query.addCampo(c);
			}
			query.setFiltro(FiltroFactory.codice(modCitta, idCittaResidenza));
			dati = modCitta.query().querySelezione(query);
			idNazioneResidenza=dati.getIntAt(modCitta.getCampo(Citta.Cam.linkNazione));
			dati.close();
		}

		// query per codice ps nazione residenza
		String psNazResidenza="";
		if (idNazioneResidenza>0) {
			Modulo modNaz = NazioneModulo.get();
			query = new QuerySelezione(modNaz);
			for (Campo c : modNaz.getCampiFisici()) {
				query.addCampo(c);
			}
			query.setFiltro(FiltroFactory.codice(modNaz, idNazioneResidenza));
			dati = modNaz.query().querySelezione(query);
			psNazResidenza = dati.getStringAt(modNaz.getCampo(NazioneAlbergo.Cam.codicePS));
			dati.close();
		}
		putMappa(mappa, Parti.cittadinanza, psNazResidenza);
		putMappa(mappa, Parti.statoResidenza, psNazResidenza);
		
		// flag se stato nascita = italia
		boolean natoItalia = (siglaNazNato.equalsIgnoreCase("IT"));
		
		// flag se residente in Italia
		boolean residenteItalia = (psNazResidenza.equalsIgnoreCase("100000100"));

		
		// comune e provincia di nascita (solo nati Italia)
		String sCom="", sProv="";
		if (natoItalia) {
			sCom=psComuneNato;
			sProv=siglaProvNato;
		}
		putMappa(mappa, Parti.comuneNascita, sCom);
		putMappa(mappa, Parti.provinciaNascita, sProv);
		
		// stato nascita
		putMappa(mappa, Parti.statoNascita, psNazNato);

		// tipo, numero e luogo rilascio documento (solo capofamiglia/capogruppo)
		String sTipoDoc="", sNumDoc="", sLuogoRilDoc="";
		if (tipo.isCapo()) {
			sTipoDoc=psTipoDoc;
			sNumDoc=numeroDoc;
			if (!psCittaResidenza.equals("")) {
				sLuogoRilDoc=psCittaResidenza;
			} else {
				sLuogoRilDoc=psNazResidenza;
			}
		}
		putMappa(mappa, Parti.tipoDoc, sTipoDoc);
		putMappa(mappa, Parti.numDoc, sNumDoc);
		putMappa(mappa, Parti.luogoRilDoc, sLuogoRilDoc);
		
		// comune e provincia di residenza (solo Italia)
		String sComRes="", sProvRes="";
		if (residenteItalia) {
			sComRes=psCittaResidenza;
			sProvRes=siglaProvResidenza;
		}
		putMappa(mappa, Parti.comuneResidenza, sComRes);
		putMappa(mappa, Parti.provinciaResidenza, sProvRes);
		
		putMappa(mappa, Parti.codiceIdentificativoPosizione, codPosizione); // cod univoco posizione ISTAT
		putMappa(mappa, Parti.modalita, "1");	// ISTAT, nuovo record

		return mappa;
	}
	
	
	
	/**
	 * Inserisce una stringa nella mappa con una data chiave
	 * Regola la lunghezza in base a quanto previsto per la chiave
	 * @param mappa la mappa alla quale aggiungere il dato
	 * @param key la chiave
	 * @param value il valore
	 */
	private void putMappa(HashMap<Parti, String> mappa, Parti key, String value){
		int len = key.getLen();
		value=padr(value, len);
		mappa.put(key, value);
	}

	

	/**
	 * Riempie una stringa aggiungendo spazi in coda
	 * @param stringa la stringa
	 * @param len la lunghezza finale
	 * @return la stringa riempita
	 */
	private String padr(String stringa, int len){
		String testoOut="";
		testoOut=Lib.Testo.pad(stringa, ' ', len,LibTesto.Posizione.fine);
		if (testoOut.length()>len) {
			testoOut=testoOut.substring(0, len);
		}
		return testoOut;
	}
	
	/**
	 * @param data una data
	 * @return la data nella forma GG/MM/AAAA
	 */
	private String stringaData(Date data){
		String d1 = Lib.Data.getDateYYYYMMDD(data);
		String sDay=d1.substring(6,8);
		String sMonth=d1.substring(4,6);
		String sYear=d1.substring(0,4);
		return sDay+"/"+sMonth+"/"+sYear;
	}
	
	
	
	/**
	 * Crea le linee per un singolo cliente arrivato
	 * @param codCliente il codice del cliente
	 * @param codPeriodo l'id del periodo di riferimento
	 * @param capo true se capogruppo
	 * @param codPosizione codice posizione per ISTAT
	 * @return la linea per il cliente
	 */
	public abstract String createLine(int codCliente, int codPeriodo, TipoAlloggiato tipo, String codPosizione);
	
	
	/**
	 * Ritorna uno spazio vuoto di misura pari a quella di una data parte
	 */
	protected String getBlank(Parti parte){
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < parte.getLen(); i++) {
		    builder.append(" ");
		}
		return builder.toString();
	}

	
	/**
	 * Enum dei tipi di alloggiato con relativi codice
	 */
	public enum TipoAlloggiato {
	    SINGOLO("16", true),
	    CAPOFAMIGLIA("17", true),
	    CAPOGRUPPO("18", true),
	    FAMILIARE("19",false),
	    MEMBRO("20",false);

	    private String codice;
	    private boolean capo;

		private TipoAlloggiato(String codice, boolean capo) {
			this.codice = codice;
			this.capo = capo;
		}

		public String getCodice() {
			return codice;
		}
		
		public boolean isCapo() {
			return capo;
		}


	}

	
	
	/**
	 * Singole parti della riga con relative lunghezze fisse
	 */
	public enum Parti {
	    tipoAlloggiato(2),
	    dataArrivo(10),
	    ggPermanenza(2),
	    cognome(50),
	    nome(30),
	    sesso(1),
	    dataNascita(10),
	    comuneNascita(9),
	    provinciaNascita(2),
	    statoNascita(9),
	    cittadinanza(9),
	    tipoDoc(5),
	    numDoc(20),
	    luogoRilDoc(9),
	    comuneResidenza(9),
	    provinciaResidenza(2),
	    statoResidenza(9),
	    indirizzo(50),
	    dataPartenza(10),
	    tipoTurismo(30),
	    mezzoTrasporto(30),
	    camereOccupate(3),
	    camereDisponibili(3),
	    lettiDisponibili(4),
	    tassaSoggiorno(1),
	    codiceIdentificativoPosizione(10),
	    modalita(1);
	    

	    int lunghezza;

		private Parti(int lunghezza) {
			this.lunghezza = lunghezza;
		}

		public int getLen() {
			return lunghezza;
		}

	}

	protected WrapGruppoArrivato[] getaGruppi() {
		return aGruppi;
	}

	protected Date getData() {
		return data;
	}

	protected Path getPath() {
		return path;
	}

	protected Path getFile() {
		return file;
	}


}
