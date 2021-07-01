package it.algos.albergo.odg.odgrisorse;

import it.algos.albergo.AlbergoModulo;
import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.listino.Listino;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.prenotazione.periodo.periodorisorsa.RisorsaPeriodo;
import it.algos.albergo.prenotazione.periodo.periodorisorsa.RisorsaPeriodoModulo;
import it.algos.albergo.risorse.Risorsa;
import it.algos.albergo.risorse.Risorsa.Cam;
import it.algos.albergo.risorse.RisorsaModulo;
import it.algos.albergo.tabelle.tipirisorsa.TipoRisorsa;
import it.algos.albergo.tabelle.tipirisorsa.TipoRisorsaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.componente.WrapTextArea;
import it.algos.base.database.dati.Dati;
import it.algos.base.font.FontFactory;
import it.algos.base.layout.Layout;
import it.algos.base.layout.tablelayout.TableLayout;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.stampa.Printer;
import it.algos.gestione.anagrafica.Anagrafica;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.wildcrest.j2printerworks.J2ComponentPrinter;
import com.wildcrest.j2printerworks.J2FlowPrinter;
import com.wildcrest.j2printerworks.J2TextPrinter;
import com.wildcrest.j2printerworks.VerticalGap;

/**
 * Stampa dell'ordine del giorno per un dato tipo di risorsa
 */
@SuppressWarnings("serial")
public class OdgRisorseStampa extends J2FlowPrinter {

	int idTipoRisorsa;
	Date dataOdg;
	String note;
	Printer printer;
	
	private static int ALT_RIGA_TITOLI = 25;	// altezza riga titoli
	private static int GRID_GAP = 3;	// gap orizz e vert tra le celle della griglia

	LinkedHashMap<Integer, WrapRiga> righe = new LinkedHashMap<Integer, WrapRiga>();

	public OdgRisorseStampa(int idTipoRisorsa, Date dataOdg, String note, Printer printer) {
		super();
		this.idTipoRisorsa = idTipoRisorsa;
		this.dataOdg = dataOdg;
		this.note=note;
		this.printer = printer;
		inizia();
	}

	private void inizia() {
		Component comp;

		// regola le larghezze delle colonne
		Double d = printer.getPageFormat().getPaper().getImageableWidth();
		Colonne.regolaLarghezze(printer);

		// riempie la mappa delle righe con gli oggetti completi di dati
		creaRighe();
				
		// crea un pannello con layout a griglia
		JPanel panGriglia = new JPanel();
		panGriglia.setOpaque(true);
		panGriglia.setBackground(Color.green);

		panGriglia.setLayout(this.creaLayout());
		// this.setPannello(panGriglia);

		// crea un Component Printer col pannello
		J2ComponentPrinter compGriglia = new J2ComponentPrinter(panGriglia);
		compGriglia
				.setHorizontalPageRule(J2ComponentPrinter.BREAK_ON_COMPONENTS);
		compGriglia.setHorizontalAlignment(J2ComponentPrinter.LEFT);
		compGriglia.setVerticalPageRule(J2ComponentPrinter.BREAK_ON_COMPONENTS);

		// Aggiunge i vari componenti a questo Printer
		this.addFlowable(this.creaPrinterTitolo()); // il titolo
		
		if (!Lib.Testo.isVuota(this.note)) {
			this.addFlowable(new VerticalGap(0.1)); // un gap fisso
			this.addFlowable(this.creaPrinterNote()); // l'area note
		}
		
		this.addFlowable(new VerticalGap(0.1)); // un gap fisso
		this.addFlowable(compGriglia); // la griglia

		// aggiunge i titoli di colonna alla griglia
		for(Colonne c : Colonne.values()){
			comp = new CompCellaHeader(c.getTitolo());
			panGriglia.add(comp, getConstraints(c.getPosizione(), 0));
		}
		
		int i = 1;	// riga da dove comincio
		for(WrapRiga riga : righe.values()){
			
			// cella numero risorsa
			comp = new CompCellaHeader(riga.getStringaNumero());
			panGriglia.add(comp, getConstraints(Colonne.numRisorsa.getPosizione(), i));
			
			// cella fermata
			comp = new CompCellaDati(riga, TipiMov.fermate);
			panGriglia.add(comp, getConstraints(Colonne.fermata.getPosizione(), i));

			// cella partenza
			comp = new CompCellaDati(riga, TipiMov.partenze);
			panGriglia.add(comp, getConstraints(Colonne.partenza.getPosizione(), i));

			// cella arrivo
			comp = new CompCellaDati(riga, TipiMov.arrivi);
			panGriglia.add(comp, getConstraints(Colonne.arrivo.getPosizione(), i));

			i++;
		}
		

	}

	private void creaRighe() {

		// crea la mappa ordinata delle righe
		Modulo modRisorse = RisorsaModulo.get();
		Filtro filtro = FiltroFactory.crea(Risorsa.Cam.tipo.get(), idTipoRisorsa);
		Ordine ordine = new Ordine(modRisorse.getCampo(Risorsa.Cam.numero));
		Query query = new QuerySelezione(modRisorse);
		Campo campoChiave = modRisorse.getCampoChiave();
		Campo campoNumero = modRisorse.getCampo(Risorsa.Cam.numero);
		query.addCampo(campoChiave);
		query.addCampo(campoNumero);
		query.setFiltro(filtro);
		query.setOrdine(ordine);
		Dati dati = modRisorse.query().querySelezione(query);
		for (int i = 0; i < dati.getRowCount(); i++) {
			int chiave = dati.getIntAt(i, campoChiave);
			int numero = dati.getIntAt(i, campoNumero);
			WrapRiga riga = new WrapRiga(numero);
			righe.put(chiave, riga);
		}
		dati.close();

		// riempie le righe con i dati di arrivo
		scanMovimenti(TipiMov.arrivi);

		// riempie le righe con i dati di partenza
		scanMovimenti(TipiMov.partenze);

		// riempie le righe con i dati di fermata
		scanMovimenti(TipiMov.fermate);

	}

	/**
	 * Crea il layout per il pannello in base alle mappe di righe e colonne.
	 * <p/>
	 * 
	 * @return il layout creato
	 */
	private TableLayout creaLayout() {
		/* variabili e costanti locali di lavoro */
		ArrayList<Double> lista;

		lista = new ArrayList<Double>();
		for (Colonne c : Colonne.values()) {
			int lar = c.getLarghezza();
			lista.add((double) lar);
		}
		double[] colsizes = Lib.Array.toDoubleArray(lista);

		lista = new ArrayList<Double>();
		lista.add(0d+ALT_RIGA_TITOLI);	// per la riga dei titoli
		for (WrapRiga wrapper : this.righe.values()) {
			int alt = wrapper.getAltezza();
			lista.add((double) alt);
		}
		double[] rowsizes = Lib.Array.toDoubleArray(lista);

		double[][] sizes = { colsizes, rowsizes };
		TableLayout layout = new TableLayout(sizes);
		layout.setHGap(GRID_GAP);
		layout.setVGap(GRID_GAP);

		/* valore di ritorno */
		return layout;
	}

	/**
	 * Crea il printer con il titolo della stampa.
	 * <p/>
	 * 
	 * @return il printer creato
	 */
	private J2ComponentPrinter creaPrinterTitolo() {

		String nomeGiorno = Lib.Data.getGiorno(dataOdg);
		nomeGiorno = Lib.Testo.primaMaiuscola(nomeGiorno);
		String strData = nomeGiorno + " " + Lib.Data.getDataEstesa(dataOdg);
		
		// recupera il nome del settore, prova con settore e se manca usa la sigla
		String nomeTipoRisorsa = TipoRisorsaModulo.get().query().valoreStringa(TipoRisorsa.Cam.settore.get(), idTipoRisorsa);
		if (nomeTipoRisorsa.equals("")) {
			nomeTipoRisorsa = TipoRisorsaModulo.get().query().valoreStringa(TipoRisorsa.Cam.sigla.get(), idTipoRisorsa);
		}
		String titolo = strData + " - " + nomeTipoRisorsa;

		JLabel label = new JLabel(titolo);
		label.setFont(FontFactory.creaPrinterFont(Font.BOLD, 18f));
		J2ComponentPrinter printer = new J2ComponentPrinter(label);
		printer.setHorizontalAlignment(J2ComponentPrinter.LEFT);

		return printer;
	}
	
	/**
	 * Crea il printer con le note di stampa.
	 * <p/>
	 * 
	 * @return il printer creato
	 */
	private J2TextPrinter creaPrinterNote() {
		WrapTextArea area = new WrapTextArea(this.note);
		area.setFont(FontFactory.creaPrinterFont(Font.PLAIN, 16f));
		area.setOptimalHeight();
		J2TextPrinter printer = new J2TextPrinter(area);
		return printer;
	}

	
	
	

	/**
	 * Ritorna una constraint per il TableLayout dati gli indici di colonna e
	 * riga.
	 * <p/>
	 * 
	 * @param col
	 *            indice della colonna
	 * @param row
	 *            indice della riga
	 * 
	 * @return la stringa di constraint
	 */
	private String getConstraints(int col, int row) {
		return "" + col + ", " + row;
	}

	/**
	 * Componente per rappresentare una cella di dati
	 * <p/>
	 */
	private class CompCellaDati extends PannelloFlusso {

		/**
		 * Crea una label per il titolo di colonna
		 * <p/>
		 * 
		 * @param titolo
		 *            titolo da visualizzare
		 */
		public CompCellaDati(WrapRiga riga, TipiMov tipoMov) {
			super(Layout.ORIENTAMENTO_VERTICALE);
			this.setOpaque(false);
			setGapPreferito(0);
			this.setBorder(BorderFactory.createLineBorder(Color.lightGray));

			String rigaMain="";
			String rigaDett="dettagli";
			String rigaNote="";
			
			switch (tipoMov) {
			case arrivi:
				rigaMain = riga.getStringaArrivo();
				rigaDett = riga.getDettagliArrivo();
				rigaNote = riga.getNoteArrivo();
				break;
			case partenze:
				rigaMain = riga.getStringaPartenza();
				rigaDett = riga.getDettagliPartenza();
				rigaNote = riga.getNotePartenza();
				break;
				
			case fermate:
				rigaMain = riga.getStringaFermata();
				rigaDett = riga.getDettagliFermata();
				rigaNote = riga.getNoteFermata();
				break;

			}
			
			JLabel label;
			
			// elastic spacer
			add(Box.createVerticalGlue());
			
			// riga principale
			label = labelFactory(rigaMain);
			label.setFont(FontFactory.creaPrinterFont(Font.BOLD, 16f));
			add(label);
			
			// riga dettaglio
			if (!rigaDett.equals("")) {
				label = labelFactory(rigaDett);
				label.setFont(FontFactory.creaPrinterFont(Font.PLAIN, 13f));
				add(label);
			}

			// riga note
			if (!rigaNote.equals("")) {
				label = labelFactory(rigaNote);
				label.setFont(FontFactory.creaPrinterFont(Font.PLAIN, 13f));
				add(label);
			}
			
			// elastic spacer
			add(Box.createVerticalGlue());


		}
		
		private JLabel labelFactory(String text){
			JLabel label = new JLabel(text);
			Lib.Comp.setMaximumWidth(label, Integer.MAX_VALUE);
			label.setOpaque(false);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setFont(FontFactory.creaPrinterFont(Font.BOLD, 16f));
			return label;
		}

	} // fine della classe 'interna'
	
	/**
	 * Componente per rappresentare una cella di header
	 * <p/>
	 */
	private class CompCellaHeader extends JLabel {

		/**
		 * Crea una label per il titolo di colonna
		 * <p/>
		 * 
		 * @param titolo
		 *            titolo da visualizzare
		 */
		public CompCellaHeader(String titolo) {
			super();

			this.setText(titolo);
			this.setFont(FontFactory.creaPrinterFont(Font.BOLD, 16f));

			this.setOpaque(true);
			setBackground(Color.darkGray);
			setForeground(Color.white);
			this.setHorizontalAlignment(SwingConstants.CENTER);
			setPreferredSize(new Dimension(100,100));

		}

	} // fine della classe 'interna'


	/**
	 * Analizza il database e inserisce i movimenti 
	 * di un dato tipo nelle celle corrispondenti
	 * @param tipoMov il tipo di movimenti
	 */
	private void scanMovimenti(TipiMov tipoMov) {
		Modulo modPeriodoRisorse = RisorsaPeriodoModulo.get();
		Modulo modPeriodo = PeriodoModulo.get();

		Campo campoIdRisorsa = modPeriodoRisorse.getCampo(RisorsaPeriodo.Cam.risorsa);
		Campo campoNomeCamera = CameraModulo.get().getCampo(Camera.Cam.camera);
		Campo campoNomeCliente = ClienteAlbergoModulo.get().getCampo(Anagrafica.Cam.soggetto);
		Campo campoNote = modPeriodoRisorse.getCampo(RisorsaPeriodo.Cam.note);
		Campo campoTrattamento = modPeriodo.getCampo(Periodo.Cam.trattamento);
		Campo campoAd = modPeriodo.getCampo(Periodo.Cam.adulti);
		Campo campoBa = modPeriodo.getCampo(Periodo.Cam.bambini);
		
		Query query = new QuerySelezione(modPeriodoRisorse);
		query.addCampo(campoIdRisorsa);
		query.addCampo(campoNomeCamera);
		query.addCampo(campoNomeCliente);
		query.addCampo(campoNote);
		query.addCampo(campoTrattamento);
		query.addCampo(campoAd);
		query.addCampo(campoBa);

		query.setFiltro(getFiltro(tipoMov));

		Dati dati = modPeriodoRisorse.query().querySelezione(query);
		
		for (int i = 0; i < dati.getRowCount(); i++) {
			int idRisorsa = dati.getIntAt(i, campoIdRisorsa);
			WrapRiga wrapper = righe.get(idRisorsa);
			if (wrapper!=null) {
				
				String strCamera = dati.getStringAt(i, campoNomeCamera);
				String strCliente = dati.getStringAt(i, campoNomeCliente);
				String strNote = dati.getStringAt(i, campoNote);

				// costruzione stringa dettagli
				String strDettagli = "";
				int idTrattamento = dati.getIntAt(i, campoTrattamento);
				String trattamento = Listino.PensioniPeriodo.getSigla(idTrattamento);
				int numAd = dati.getIntAt(i, campoAd);
				int numBa = dati.getIntAt(i, campoBa);
				if (numAd>0) {
					strDettagli+=numAd+" Ad";
				}
				if (numBa>0) {
					strDettagli+=" + "+numBa+"Ba";
				}
				if (!trattamento.equals("")) {
					strDettagli+=" - "+trattamento;
				}

				switch (tipoMov) {
				case arrivi:
					wrapper.setCameraArrivo(strCamera);
					wrapper.setClienteArrivo(strCliente);
					wrapper.setDettagliArrivo(strDettagli);
					wrapper.setNoteArrivo(strNote);
					break;

				case partenze:
					wrapper.setCameraPartenza(strCamera);
					wrapper.setClientePartenza(strCliente);
					wrapper.setDettagliPartenza(strDettagli);
					wrapper.setNotePartenza(strNote);
					break;
					
				case fermate:
					wrapper.setCameraFermata(strCamera);
					wrapper.setClienteFermata(strCliente);
					wrapper.setDettagliFermata(strDettagli);
					wrapper.setNoteFermata(strNote);
					break;

				}

			}
		}
		
		dati.close();
	}
	

	/**
	 * Ritorna il filtro in base a data e tiporisorsa correnti, 
	 * e tipo movimento dato
	 * @param tipoMov il tipo di movimenti
	 * @return il filtro
	 */
	private Filtro getFiltro(TipiMov tipoMov) {
		Filtro filtro = new Filtro();

		switch (tipoMov) {
		case arrivi:
			filtro.add(FiltroFactory.crea(RisorsaPeriodo.Cam.dataInizio.get(),
					this.dataOdg));
			break;

		case partenze:
			filtro.add(FiltroFactory.crea(RisorsaPeriodo.Cam.dataFine.get(),
					this.dataOdg));
			break;

		case fermate:
			filtro.add(FiltroFactory.crea(RisorsaPeriodo.Cam.dataInizio.get(),
					Filtro.Op.MINORE, this.dataOdg));
			filtro.add(FiltroFactory.crea(RisorsaPeriodo.Cam.dataFine.get(),
					Filtro.Op.MAGGIORE, this.dataOdg));
			break;
		}

		filtro.add(FiltroFactory.crea(RisorsaPeriodo.Cam.tipoRisorsa.get(),
				this.idTipoRisorsa));
		
		// no le pren disdette
		Campo campoPrenDisdetta = PrenotazioneModulo.get().getCampo(Prenotazione.Cam.disdetta);
		filtro.add(FiltroFactory.crea(campoPrenDisdetta, false));
		
		// filtro azienda
        filtro.add(PrenotazioneModulo.get().getFiltroAzienda());
		
		return filtro;
	}

	enum TipiMov {
		arrivi, partenze, fermate;
	}
	
	
	enum Colonne {
		
		numRisorsa(0, 60, "Num."),
		fermata(1, 250, "Fermata"),
		partenza(2, 250, "Partenza"),
		arrivo(3, 250, "Arrivo");
	
		private int posizione;
		private int larghezza;
		private String titolo;

		/**
		 * @param posizione - posizione nela tabella (0 per la prima)
		 * @param larghezza - larghezza della colonna (viene poi cambiata per alcune colonne)
		 * @param larghezza - titolo della colonna
		 */
		Colonne(int posizione, int larghezza, String titolo) {
			this.posizione = posizione;
			this.larghezza = larghezza;
			this.titolo = titolo;
		}

		public int getPosizione() {
			return posizione;
		}

		public int getLarghezza() {
			return larghezza;
		}

		
		private void setLarghezza(int larghezza) {
			this.larghezza = larghezza;
		}

		public String getTitolo() {
			return titolo;
		}
		
		/**
		 * Regola le larghezze delle colonne in base alla larghezza totale disponibile.
		 * Toglie la larghezza della colonna numRisorsa che è fissa e divide il 
		 * resto tra le altre colonne
		 * @param printer - il printer per recuperare le misure
		 */
		public static void regolaLarghezze(Printer printer){
			int numColonneVariabili = 3;
			Double d = printer.getPageFormat().getPaper().getImageableWidth();
			d = d/printer.getScale();
			int wTot = d.intValue()-(GRID_GAP*numColonneVariabili);
			int wRimanente = wTot-numRisorsa.getLarghezza()-1;	// -1 per sicurezza arrotondamenti
			int wCad = wRimanente/numColonneVariabili;
			fermata.setLarghezza(wCad);
			partenza.setLarghezza(wCad);
			arrivo.setLarghezza(wCad);
		}

	}
}
