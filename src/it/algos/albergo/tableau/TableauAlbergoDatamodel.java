package it.algos.albergo.tableau;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.camera.composizione.CompoCamera;
import it.algos.albergo.camera.composizione.CompoCameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.listino.Listino;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.prenotazione.periodo.periodorisorsa.RisorsaPeriodo;
import it.algos.albergo.prenotazione.periodo.periodorisorsa.RisorsaPeriodoModulo;
import it.algos.albergo.risorse.Risorsa;
import it.algos.albergo.risorse.RisorsaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.OnEditingFinished;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.anagrafica.cliente.Cliente;

import java.util.ArrayList;
import java.util.Date;

/**
 * Datamodel per il tableau
 */
public class TableauAlbergoDatamodel implements TableauDatamodel {

	@Override
	public ArrayList<CellPeriodoIF> getCellePeriodo(int idTipoRisorsa, Date d1,
			Date d2) {
		ArrayList<CellPeriodoIF> lista = new ArrayList<CellPeriodoIF>();
		if (idTipoRisorsa == Tableau.ID_TIPO_RISORSE_CAMERA) {
			lista = getCellePeriodoCamera(d1, d2);
		} else {
			lista = getCellePeriodoRisorsa(idTipoRisorsa, d1, d2);
		}
		return lista;
	}

	@Override
	public CellPeriodoIF getCellaPeriodo(int idTipoRisorsa, int idRecordSorgente) {
		CellPeriodoIF cella = null;
		if (idTipoRisorsa == Tableau.ID_TIPO_RISORSE_CAMERA) {
			cella = getCellaPeriodoCamera(idRecordSorgente);
		} else {
			 cella = getCellaPeriodoRisorsa(idRecordSorgente);
		}
		return cella;
	}

//	/**
//	 * Ritorna l'elenco di tutti i codici di periodo contenuti nella
//	 * prenotazione proprietaria di un dato periodo.
//	 * <p/>
//	 * 
//	 * @param codPeriodo
//	 *            il codice periodo
//	 * @return l'array con tutti i codici periodo contenuti nella prenotazione
//	 */
//	public int[] getPeriodiFratelli(int codPeriodo) {
//		/* variabili e costanti locali di lavoro */
//		int[] codici = new int[0];
//		int codPren;
//		Filtro filtro;
//
//		try { // prova ad eseguire il codice
//			Modulo modPeriodo = PeriodoModulo.get();
//			codPren = modPeriodo.query().valoreInt(
//					Periodo.Cam.prenotazione.get(), codPeriodo);
//			filtro = FiltroFactory
//					.crea(Periodo.Cam.prenotazione.get(), codPren);
//			codici = modPeriodo.query().valoriChiave(filtro);
//		} catch (Exception unErrore) { // intercetta l'errore
//			new Errore(unErrore);
//		} // fine del blocco try-catch
//		/* valore di ritorno */
//		return codici;
//	}

    /**
     * Ritorna l'elenco di tutti gli id di record sorgenti di cella contenuti
     * in una data prenotazione, relativi a un dato tipo di risorse.
     * <p/>
     * Nel caso di prenotazione di camere, ritorna gli id di periodo
     * Nel caso di prenotazione di risorse, ritorna gli id di RisorsaPeriodo di tutti i periodi
     * ecc...
     *
     * @param idPrenotazione l'id della prenotazione
     * @param tipoRisorse l'id del tipo di risorse correntemente selezionato
     * @return l'array con tutti gli id di record sorgenti contenuti nella prenotazione
     */
    public int[] getIdRecordSorgenti(int idPrenotazione, int tipoRisorse){
		int[] ids = new int[0];
    	if (tipoRisorse==Tableau.ID_TIPO_RISORSE_CAMERA) {	// camere, in questo caso torna gli id dei periodi
			Modulo modPeriodo = PeriodoModulo.get();
			Filtro filtro = FiltroFactory.crea(Periodo.Cam.prenotazione.get(), idPrenotazione);
			ids = modPeriodo.query().valoriChiave(filtro);
		} else {
			// risorse, ritorna gli id dei record di RisorsaPeriodo
			// legati alla prenotazione e relativi al tipo di risorsa richiesto
			Modulo modRisorsaPeriodo = RisorsaPeriodoModulo.get();
			Campo campoPren = PeriodoModulo.get().getCampo(Periodo.Cam.prenotazione);
			Filtro filtroPren = FiltroFactory.crea(campoPren, idPrenotazione);
			Campo campoTipo = modRisorsaPeriodo.getCampo(RisorsaPeriodo.Cam.tipoRisorsa);
			Filtro filtroTipo = FiltroFactory.crea(campoTipo, tipoRisorse);
			Filtro filtro = new Filtro();
			filtro.add(filtroPren);
			filtro.add(filtroTipo);
			ids = modRisorsaPeriodo.query().valoriChiave(filtro);
		}
    	return ids;
    }

    /**
     * Apre per modificare una prenotazione in scheda.
     * <p/>
     * @param codPrenotazione il codice della prenotazione
     * @param listener listener da notificare alla chiusura della prenotazione
     */

	@Override
	public void apriPrenotazione(int codPrenotazione, OnEditingFinished listener) {
		//PrenotazioneModulo.get().presentaRecord(codPrenotazione);
		PrenotazioneModulo.get().presentaRecord(codPrenotazione, false, listener);
	}

	@Override
	public int getCodCliente(int codPeriodo) {
		return PeriodoModulo.getCodCliente(codPeriodo);
	}

	@Override
	public ArrayList<CellRisorsaIF> getCelleRisorsa(int codTipo) {
		ArrayList<CellRisorsaIF> lista = new ArrayList<CellRisorsaIF>();
		if (codTipo == Tableau.ID_TIPO_RISORSE_CAMERA) {
			lista = creaListaCelleCamere();
		} else {
			lista = creaListaCelleRisorse(codTipo);
		}
		return lista;
	}

	/**
	 * Ritorna un elenco ordinato dei tipi di risorsa supportati. <br>
	 * Questi tipi vengono mostrati nel popup. <br>
	 * Quando cambia il tipo, le callback al modello riportano l'id del tipo
	 * selezionato.
	 * 
	 * @return l'elenco dei tipi di risorsa supportati
	 */
	@Override
	public TipoRisorsaTableau[] getTipiRisorsa() {

		ArrayList<TipoRisorsaTableau> tipi = new ArrayList<TipoRisorsaTableau>();
		TipoRisorsaTableau tipoCamere = new TipoRisorsaTableau(
				Tableau.ID_TIPO_RISORSE_CAMERA, "Camere");
		tipi.add(tipoCamere);
		TipoRisorsaTableau[] aTipi = TipoRisorsaTableau.getAll();
		for (TipoRisorsaTableau tipo : aTipi) {
			tipi.add(tipo);
		}
		return tipi.toArray(new TipoRisorsaTableau[0]);
	}

	/**
	 * Crea la lista delle celle di camera
	 */
	private ArrayList<CellRisorsaIF> creaListaCelleCamere() {
		ArrayList<CellRisorsaIF> lista = new ArrayList<CellRisorsaIF>();

		Modulo modCamera = CameraModulo.get();
		Modulo modCompo = CompoCameraModulo.get();

		Campo campoChiaveCam = modCamera.getCampoChiave();
		Campo campoSiglaCam = modCamera.getCampo(Camera.Cam.camera);
		Campo campoDescrCompo = modCompo.getCampo(CompoCamera.Cam.sigla);

		Query query = new QuerySelezione(modCamera);
		query.addCampo(campoChiaveCam);
		query.addCampo(campoSiglaCam);
		query.addCampo(campoDescrCompo);

		Filtro filtro = FiltroFactory.creaFalso(Camera.Cam.escludiplanning);
		query.setFiltro(filtro);

		Ordine ordine = new Ordine(modCamera.getCampo(Camera.Cam.camera));
		query.setOrdine(ordine);

		Dati dati = modCamera.query().querySelezione(query);

		for (int k = 0; k < dati.getRowCount(); k++) {
			int codice = dati.getIntAt(k, campoChiaveCam);
			String nome = dati.getStringAt(k, campoSiglaCam);
			String composizione = dati.getStringAt(k, campoDescrCompo);
			CellCamera cella = new CellCamera(nome, composizione, codice);
			lista.add(cella);
		} // fine del ciclo for

		dati.close();

		return lista;
	}

	/**
	 * Crea la lista delle celle di risorsa
	 */
	private ArrayList<CellRisorsaIF> creaListaCelleRisorse(int codTipoRisorsa) {
		ArrayList<CellRisorsaIF> lista = new ArrayList<CellRisorsaIF>();

		Modulo modRisorse = RisorsaModulo.get();

		Campo campoChiaveRisorse = modRisorse.getCampoChiave();
		Campo campoNumeroRisorsa = modRisorse.getCampo(Risorsa.Cam.numero);
		Campo campoDettagliRisorsa = modRisorse.getCampo(Risorsa.Cam.note);

		Query query = new QuerySelezione(modRisorse);
		query.addCampo(campoChiaveRisorse);
		query.addCampo(campoNumeroRisorsa);
		query.addCampo(campoDettagliRisorsa);

		Filtro filtro = FiltroFactory.crea(Risorsa.Cam.tipo.get(),
				codTipoRisorsa);
		query.setFiltro(filtro);

		Ordine ordine = new Ordine(modRisorse.getCampo(Risorsa.Cam.numero));
		query.setOrdine(ordine);

		Dati dati = modRisorse.query().querySelezione(query);

		for (int k = 0; k < dati.getRowCount(); k++) {
			int codice = dati.getIntAt(k, campoChiaveRisorse);
			int numero = dati.getIntAt(k, campoNumeroRisorsa);
			String dettaglio = dati.getStringAt(k, campoDettagliRisorsa);
			String nome = "" + numero;
			CellRisorsa cella = new CellRisorsa(codice, nome, dettaglio);
			lista.add(cella);
		}

		dati.close();

		return lista;
	}

	/**
	 * Ritorna un elenco di celle di periodo di camera corrispondente a un
	 * intervallo di date fornito
	 * <p/>
	 * 
	 * @param d1
	 *            data iniziale
	 * @param d2
	 *            data finale
	 * @return la lista delle celle
	 */
	public ArrayList<CellPeriodoIF> getCellePeriodoCamera(Date d1, Date d2) {
		/* variabili e costanti locali di lavoro */
		ArrayList<CellPeriodoIF> listaCelle = new ArrayList<CellPeriodoIF>();
		Filtro filtro;
		Filtro filtroDate;
		Filtro filtroAzienda;
		Filtro filtroNoDisdettte;

		int[] chiavi;

		CellPeriodoCamera cella;

		try { // prova ad eseguire il codice

			/* crea il filtro dei periodi interessati */
			filtroDate = PeriodoModulo.getFiltroInteressati(d1, d2);
			filtroAzienda = PrenotazioneModulo.get().getFiltroAzienda();
			Campo campoDisd = PrenotazioneModulo.get().getCampo(
					Prenotazione.Cam.disdetta);
			filtroNoDisdettte = FiltroFactory.crea(campoDisd, false);
			filtro = new Filtro();
			filtro.add(filtroDate);
			filtro.add(filtroAzienda);
			filtro.add(filtroNoDisdettte);

			/* recupera le chiavi e crea le celle */
			chiavi = PeriodoModulo.get().query().valoriChiave(filtro);
			for (int cod : chiavi) {
				cella = this.getCellaPeriodoCamera(cod);
				listaCelle.add(cella);
			}

		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
		}// fine del blocco try-catch

		/* valore di ritorno */
		return listaCelle;
	}

	/**
	 * Ritorna la cella di periodo di camera relativa a un dato periodo.
	 * <p/>
	 * 
	 * @param codPeriodo
	 *            il codice del periodo
	 * @return la cella del periodo
	 */
	public CellPeriodoCamera getCellaPeriodoCamera(int codPeriodo) {
		/* variabili e costanti locali di lavoro */
		CellPeriodoCamera cella = null;

		/* variabili e costanti locali di lavoro */
		Filtro filtro;
		Query query;
		Dati dati;

		String cliente;
		String agenzia;
		int camera;
		int camProvenienza;
		int camDestinazione;
		Date dataInizio;
		Date dataFine;
		boolean opzione;
		boolean confermata;
		Date scadenza;
		int numAd;
		int numBa;
		String trattamento;
		String preparazione;

		int codProvenienza, codDestinazione;
		boolean arrivato, partito;
		int codTrattamento;

		try { // prova ad eseguire il codice

			Modulo modCliente = ClienteAlbergoModulo.get();
			Modulo modPrenotazione = PrenotazioneModulo.get();
			Modulo modCompoCamera = CompoCameraModulo.get();
			Modulo modPeriodo = PeriodoModulo.get();

			Campo campoNomeCliente = modCliente
					.getCampo(Anagrafica.Cam.soggetto);
			Campo campoCamera = modPeriodo.getCampo(Periodo.Cam.camera);
			Campo campoLinkProv = modPeriodo
					.getCampo(Periodo.Cam.linkProvenienza);
			Campo campoLinkDest = modPeriodo
					.getCampo(Periodo.Cam.linkDestinazione);
			Campo campoDataInizio = modPeriodo
					.getCampo(Periodo.Cam.arrivoPrevisto);
			Campo campoDataFine = modPeriodo
					.getCampo(Periodo.Cam.partenzaPrevista);
			Campo campoOpzione = modPrenotazione
					.getCampo(Prenotazione.Cam.opzione);
			Campo campoConfermata = modPrenotazione
					.getCampo(Prenotazione.Cam.confermata);
			Campo campoScadenza = modPrenotazione
					.getCampo(Prenotazione.Cam.dataScadenza);
			Campo campoArrivato = modPeriodo.getCampo(Periodo.Cam.arrivato);
			Campo campoPartito = modPeriodo.getCampo(Periodo.Cam.partito);
			Campo campoNumAd = modPeriodo.getCampo(Periodo.Cam.adulti);
			Campo campoNumBa = modPeriodo.getCampo(Periodo.Cam.bambini);
			Campo campoCodTratt = modPeriodo.getCampo(Periodo.Cam.trattamento);
			Campo campoPrep = modCompoCamera.getCampo(CompoCamera.Cam.sigla);
			Campo campoChiave = modPeriodo.getCampoChiave();

			/* crea il filtro per il periodo interessato */
			filtro = FiltroFactory.codice(modPeriodo, codPeriodo);

			/* crea ed esegue la query */
			query = new QuerySelezione(PeriodoModulo.get());
			query.addCampo(campoNomeCliente);
			query.addCampo(campoCamera);
			query.addCampo(campoLinkProv);
			query.addCampo(campoLinkDest);
			query.addCampo(campoDataInizio);
			query.addCampo(campoDataFine);
			query.addCampo(campoOpzione);
			query.addCampo(campoConfermata);
			query.addCampo(campoScadenza);
			query.addCampo(campoArrivato);
			query.addCampo(campoPartito);
			query.addCampo(campoNumAd);
			query.addCampo(campoNumBa);
			query.addCampo(campoCodTratt);
			query.addCampo(campoPrep);
			query.addCampo(campoChiave);
			query.setFiltro(filtro);
			dati = modPeriodo.query().querySelezione(query);

			/* spazzola i periodi e crea le celle */
			for (int k = 0; k < dati.getRowCount(); k++) {

				cliente = dati.getStringAt(k, campoNomeCliente);
				agenzia = "";
				camera = dati.getIntAt(k, campoCamera);
				codProvenienza = dati.getIntAt(k, campoLinkProv);
				codDestinazione = dati.getIntAt(k, campoLinkDest);
				dataInizio = dati.getDataAt(k, campoDataInizio);
				dataFine = dati.getDataAt(k, campoDataFine);
				opzione = dati.getBoolAt(k, campoOpzione);
				confermata = dati.getBoolAt(k, campoConfermata);
				scadenza = dati.getDataAt(k, campoScadenza);
				arrivato = dati.getBoolAt(k, campoArrivato);
				partito = dati.getBoolAt(k, campoPartito);
				numAd = dati.getIntAt(k, campoNumAd);
				numBa = dati.getIntAt(k, campoNumBa);
				codTrattamento = dati.getIntAt(k, campoCodTratt);
				preparazione = dati.getStringAt(k, campoPrep);
				codPeriodo = dati.getIntAt(k, campoChiave);

				/* recupera eventuale camera provenienza */
				camProvenienza = 0;
				if (codProvenienza != 0) {
					camProvenienza = modPeriodo.query().valoreInt(
							Periodo.Cam.camera.get(), codProvenienza);
				}// fine del blocco if

				/* recupera eventuale camera destinazione */
				camDestinazione = 0;
				if (codDestinazione != 0) {
					camDestinazione = modPeriodo.query().valoreInt(
							Periodo.Cam.camera.get(), codDestinazione);
				}// fine del blocco if

				/* stringa trattamento */
				trattamento = Listino.PensioniPeriodo.getSigla(codTrattamento);

				/* crea la cella */
				cella = new CellPeriodoCamera(camera, cliente, agenzia,
						camProvenienza, camDestinazione, dataInizio, dataFine,
						opzione, confermata, scadenza, arrivato, partito,
						numAd, numBa, trattamento, preparazione, codPeriodo);

			} // fine del ciclo for
			dati.close();

		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
		}// fine del blocco try-catch

		/* valore di ritorno */
		return cella;
	}

	/**
	 * Ritorna un elenco di celle di periodo di risorsa corrispondente a un
	 * intervallo di date fornito
	 * <p/>
	 * 
	 * @param idTipoRisorsa
	 *            l'id del tipo di risorsa
	 * @param d1
	 *            la data iniziale
	 * @param d2
	 *            la data finale
	 * @return la lista delle celle
	 */
	private ArrayList<CellPeriodoIF> getCellePeriodoRisorsa(int idTipoRisorsa,
			Date d1, Date d2) {
		/* variabili e costanti locali di lavoro */
		ArrayList<CellPeriodoIF> listaCelle = new ArrayList<CellPeriodoIF>();
		Filtro filtro;
		Filtro filtroNoDisdettte;

		int[] chiavi;

		CellPeriodoRisorsa cella;

		/* crea il filtro dei periodi interessati */
		Filtro filtroPeriodi = RisorsaPeriodoModulo.getFiltroInteressati(
				idTipoRisorsa, d1, d2);
		Filtro filtroAzienda = PrenotazioneModulo.get().getFiltroAzienda();
		Campo campoDisd = PrenotazioneModulo.get().getCampo(
				Prenotazione.Cam.disdetta);
		filtroNoDisdettte = FiltroFactory.crea(campoDisd, false);

		filtro = new Filtro();
		filtro.add(filtroPeriodi);
		filtro.add(filtroAzienda);
		filtro.add(filtroNoDisdettte);

		/* recupera le chiavi e crea le celle */
		chiavi = RisorsaPeriodoModulo.get().query().valoriChiave(filtro);
		for (int cod : chiavi) {
			cella = this.getCellaPeriodoRisorsa(cod);
			listaCelle.add(cella);
		}

		return listaCelle;
	}

	/**
	 * Ritorna la cella di periodo di risorsa.
	 * <p/>
	 * 
	 * @todo da completare!
	 * 
	 * @param idPeriodoRisorsa
	 *            id del PeriodoRisorsa
	 * @return la cella creata
	 */
	public CellPeriodoRisorsa getCellaPeriodoRisorsa(int idPeriodoRisorsa) {
		/* variabili e costanti locali di lavoro */
		CellPeriodoRisorsa cella = null;

		/* variabili e costanti locali di lavoro */
		Filtro filtro;
		Query query;
		Dati dati;

		// recupera i moduli necessari
		Modulo modRisorsaPeriodo = RisorsaPeriodoModulo.get();
		Modulo modCliente = ClienteAlbergoModulo.get();
		Modulo modPrenotazione = PrenotazioneModulo.get();
		Modulo modPeriodo = PeriodoModulo.get();

		// campi da caricare
		Campo campoIdRisorsa = modRisorsaPeriodo
				.getCampo(RisorsaPeriodo.Cam.risorsa);
		Campo campoIdPeriodo = modRisorsaPeriodo.getCampo(RisorsaPeriodo.Cam.periodo);
		Campo campoNomeCliente = modCliente.getCampo(Cliente.Cam.soggetto);
		Campo campoNomeCamera = CameraModulo.get().getCampo(Camera.Cam.camera);
		Campo campoDataInizio = modRisorsaPeriodo
				.getCampo(RisorsaPeriodo.Cam.dataInizio);
		Campo campoDataFine = modRisorsaPeriodo
				.getCampo(RisorsaPeriodo.Cam.dataFine);
		Campo campoOpzione = modPrenotazione.getCampo(Prenotazione.Cam.opzione);
		Campo campoConfermata = modPrenotazione
				.getCampo(Prenotazione.Cam.confermata);
		Campo campoDataScadenza = modPrenotazione
				.getCampo(Prenotazione.Cam.dataScadenza);
		Campo campoArrivato = modPeriodo.getCampo(Periodo.Cam.arrivato);
		Campo campoPartito = modPeriodo.getCampo(Periodo.Cam.partito);
		Campo campoNumAd = modPeriodo.getCampo(Periodo.Cam.adulti);
		Campo campoNumBa = modPeriodo.getCampo(Periodo.Cam.bambini);
		Campo campoCodTratt = modPeriodo.getCampo(Periodo.Cam.trattamento);
		Campo campoNote = modRisorsaPeriodo.getCampo(RisorsaPeriodo.Cam.note);


		// crea il filtro sul PeriodoRisorsa interessato
		filtro = FiltroFactory.codice(modRisorsaPeriodo, idPeriodoRisorsa);

		// crea la query
		query = new QuerySelezione(modRisorsaPeriodo);
		query.addCampo(campoIdRisorsa);
		query.addCampo(campoIdPeriodo);
		query.addCampo(campoNomeCliente);
		query.addCampo(campoNomeCamera);
		query.addCampo(campoDataInizio);
		query.addCampo(campoDataFine);
		query.addCampo(campoDataFine);
		query.addCampo(campoOpzione);
		query.addCampo(campoConfermata);
		query.addCampo(campoDataScadenza);
		query.addCampo(campoArrivato);
		query.addCampo(campoPartito);
		query.addCampo(campoNumAd);
		query.addCampo(campoNumBa);
		query.addCampo(campoCodTratt);
		query.addCampo(campoNote);
		query.setFiltro(filtro);
		
		// esegue la query
		dati = modRisorsaPeriodo.query().querySelezione(query);

		// recupera i valori necessari dall'oggetto dati
		int idRisorsa = dati.getIntAt(campoIdRisorsa);
		int idPeriodo = dati.getIntAt(campoIdPeriodo);
		String nomeCliente = dati.getStringAt(campoNomeCliente);
		String nomeCamera = dati.getStringAt(campoNomeCamera);
		Date dataInizio = dati.getDataAt(campoDataInizio);
		Date dataFine = dati.getDataAt(campoDataFine);
		boolean opzione = dati.getBoolAt(campoOpzione);
		boolean confermata = dati.getBoolAt(campoConfermata);
		Date dataScadenza = dati.getDataAt(campoDataScadenza);
		boolean arrivato = dati.getBoolAt(campoArrivato);
		boolean partito = dati.getBoolAt(campoPartito);
		int numAd = dati.getIntAt(campoNumAd);
		int numBa = dati.getIntAt(campoNumBa);
		int codTrattamento = dati.getIntAt(campoCodTratt);
		String trattamento = Listino.PensioniPeriodo.getSigla(codTrattamento);
		String note = dati.getStringAt(campoNote);

		// chiude l'oggetto dati
		dati.close();

		// crea la cella
		cella = new CellPeriodoRisorsa(idRisorsa, idPeriodoRisorsa, idPeriodo, nomeCliente, nomeCamera,
				dataInizio, dataFine, opzione, confermata, dataScadenza, arrivato, partito, numAd, numBa, trattamento, note);

		// valore di ritorno
		return cella;
	}

}
