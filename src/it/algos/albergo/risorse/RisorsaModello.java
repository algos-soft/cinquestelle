package it.algos.albergo.risorse;

import java.util.ArrayList;

import it.algos.albergo.tabelle.tipirisorsa.TipoRisorsa;
import it.algos.albergo.tabelle.tipirisorsa.TipoRisorsaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.libreria.Lib;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;
import it.algos.base.wrapper.CampoValore;

public class RisorsaModello extends ModelloAlgos implements Risorsa {

	/**
	 * Costruttore completo senza parametri.
	 */
	public RisorsaModello() {
		super();
		super.setTavolaArchivio(Risorsa.NOME_TAVOLA);
	}// fine del metodo costruttore

	/**
	 * Creazione dei campi.
	 */
	protected void creaCampi() {
		Campo unCampo;

		super.creaCampi();

		/* campo numero */
		Campo campoNumero = CampoFactory.intero(Risorsa.Cam.numero);
		campoNumero.decora().obbligatorio();
		this.addCampo(campoNumero);

		/* campo link tipo risorsa */
		unCampo = CampoFactory.comboLinkPop(Risorsa.Cam.tipo);
		unCampo.setNomeModuloLinkato(TipoRisorsa.NOME_MODULO);
		unCampo.setAzioneDelete(Db.Azione.noAction);
		unCampo.decora().obbligatorio();
		unCampo.setLarghezza(150);
		unCampo.setTitoloColonna("ciao");
		this.addCampo(unCampo);

		/* campo note */
		unCampo = CampoFactory.testo(Risorsa.Cam.note);
		this.addCampo(unCampo);
		
		/* campo colore */
		unCampo = CampoFactory.intero(Risorsa.Cam.colore);
		unCampo.setPresenteScheda(false);
		this.addCampo(unCampo);


	}


	@Override
	protected Vista creaVistaDefault() {
		VistaElemento elem;
		Campo campo;

		// campo tipo risorsa
		Vista vista = new Vista();
		campo = TipoRisorsaModulo.get().getCampo(TipoRisorsa.Cam.sigla);
		elem = vista.addCampo(campo);
		elem.setTitoloColonna("Tipo");

		// regola ordine della colonna
		Ordine ordine = new Ordine();
		ordine.add(TipoRisorsaModulo.get().getCampo(TipoRisorsa.Cam.sigla));
		ordine.add(getCampo(Cam.numero));
		elem.getCampo().setOrdine(ordine);

		// campo numero
		campo = getCampo(Cam.numero);
		elem = vista.addCampo(campo);
		elem.setLarghezzaColonna(80);
		
		return vista;
	}
	
	

	@Override
	protected boolean registraRecordAnte(int codice,
			ArrayList<CampoValore> lista, Connessione conn) {
		boolean riuscito;

		riuscito = super.registraRecordAnte(codice, lista, conn);

		// devo vedere che non esista già una risorsa
		// con altro codice ma dello stesso tipo e con
		// lo stesso numero
		if (riuscito) {

			// recupera il numero dalla lista - se non c'è lo recupera dal db
			Campo campoNumero = this.getCampo(Cam.numero.get());
			CampoValore cvNumero = Lib.Camp.getCampoValore(lista, campoNumero);
			int numero;
			if (cvNumero != null) {
				numero = (Integer) cvNumero.getValore();
			} else {
				numero = query().valoreInt(campoNumero, codice, conn);
			}// fine del blocco if-else

			// recupera il tipo dalla lista - se non c'è lo recupera dal db
			Campo campoTipo = this.getCampo(Cam.tipo.get());
			CampoValore cvTipo = Lib.Camp.getCampoValore(lista, campoTipo);
			int idTipo;
			if (cvTipo != null) {
				idTipo = (Integer) cvTipo.getValore();
			} else {
				idTipo = query().valoreInt(campoTipo, codice, conn);
			}// fine del blocco if-else

			// filtro per record con altro codice ma stesso numero e tipo
			Filtro filtroNum = FiltroFactory.crea(campoNumero, numero);
			Filtro filtroTipo = FiltroFactory.crea(campoTipo, idTipo);
			Filtro filtroChiave = FiltroFactory.crea(getCampoChiave(),
					Filtro.Op.DIVERSO, codice);
			Filtro filtro = new Filtro();
			filtro.add(filtroNum);
			filtro.add(filtroTipo);
			filtro.add(filtroChiave);

			// non deve esistere
			boolean esiste = query().isEsisteRecord(filtro);
			riuscito = !esiste;

		}
		return riuscito;
	}

}
