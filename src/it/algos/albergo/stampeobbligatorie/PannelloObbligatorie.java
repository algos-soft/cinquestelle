/**
 * Title:     PannelloObbligatorie
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      20-giu-2008
 */
package it.algos.albergo.stampeobbligatorie;

import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.stampeobbligatorie.testastampe.NavigatoreDoppio;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampe;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampeModulo;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampeNavigatore;
import it.algos.base.errore.Errore;
import it.algos.base.evento.BaseAzione;
import it.algos.base.evento.BaseListener;
import it.algos.base.evento.lista.ListaDoppioClicAz;
import it.algos.base.evento.lista.ListaDoppioClicEve;
import it.algos.base.evento.lista.ListaSelAz;
import it.algos.base.evento.lista.ListaSelEve;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.lista.Lista;
import it.algos.base.lista.TavolaModello;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.portale.Portale;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.tavola.Tavola;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

/**
 * Pannello base per i diversi tipi di stampe </p>
 * 
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 20-giu-2008 ore 12.07.48
 */
public class PannelloObbligatorie extends PannelloFlusso {

	/* dialogo di riferimento */
	private StampeObbligatorieDialogo dialogo;

	/* bottone Vai al Cliente */
	private JButton botVaiCliente;

	/* bottone Stampa */
	private JButton botStampa;

	/* bottone Annulla Ultimo Giorno */
	private JButton botAnnulla;

	private NavigatoreDoppio navDoppio;

	private Filtro filtroTesta;

	protected StampeObbLogica logica;

	/*
	 * elenco dei listeners aggiunti da questo oggetto, viene riempito quando li
	 * aggiunge vengono rimossi quando l'oggetto viene chiuso
	 */
	private ArrayList<ListenerElem> listenersAggiunti;

	// altezza dei bottoni
	protected int altBottoni = 36;

	/**
	 * Costruttore completo con parametri.
	 * <p/>
	 * 
	 * @param dialogo
	 *            di riferimento
	 */
	public PannelloObbligatorie(StampeObbligatorieDialogo dialogo) {
		/* rimanda al costruttore della superclasse */
		super(Layout.ORIENTAMENTO_VERTICALE);

		/* regola le variabili di istanza coi parametri */
		this.setDialogo(dialogo);

		try { // prova ad eseguire il codice
			/* regolazioni iniziali di riferimenti e variabili */
			this.inizia();
		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
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
		/* variabili e costanti locali di lavoro */
		StampeObbligatorieDialogo dialogo;
		JButton bot;
		Icon icona;
		Dimension dimStampa = new Dimension(150, altBottoni);
		Dimension dimAnnulla = new Dimension(190, altBottoni);

		try { // prova ad eseguire il codice

			/* lista vuota di listeners aggiunti */
			this.setListenersAggiunti(new ArrayList<ListenerElem>());

			dialogo = this.getDialogo();

			this.getPanFisso().setBorder(
					BorderFactory.createEmptyBorder(8, 8, 8, 8));
			this.setOpaque(true);
			this.setBackground(dialogo.getColoreSfondo());

			/* crea i bottoni standard */
			icona = ClienteAlbergoModulo.get().getIcona("clientealbergo24");
			bot = this.creaBottone("Vai a cliente", new AzVaiCliente(), icona);
			this.setBotVaiCliente(bot);

			icona = Lib.Risorse.getIconaBase("Stampe24");
			bot = this.creaBottone("Stampa non abilitata", new AzStampa(),
					icona);
			this.setBotStampa(bot);

			icona = Lib.Risorse.getIconaBase("chiudischeda24");
			bot = this.creaBottone("Annulla non abilitato", new AzAnnulla(),
					icona);
			this.setBotAnnulla(bot);

			/* crea, registra e aggiunge il navigatore doppio */
			this.creaNavigatore();

			/* larghezza fissa dei bottoni (il testo è variabile) */
			bot = this.getBotStampa();
			this.fixBot(bot, dimStampa);

			bot = this.getBotAnnulla();
			this.fixBot(bot, dimAnnulla);

			/**
			 * Registra un riferimento a questo pannello nel navigatore delle
			 * righe
			 */
			Navigatore nav = this.getNavSlave();
			if ((nav != null) && (nav instanceof ObbligNavigatore)) {
				ObbligNavigatore navObbl = (ObbligNavigatore) nav;
				navObbl.setPanObbligatorie(this);
			}// fine del blocco if

		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
		}// fine del blocco try-catch

	}

	/**
	 * Regolazioni iniziali, dopo che sono stati regolati dalla sottoclasse i
	 * parametri indispensabili (tra cui il riferimento al modulo) Metodo
	 * chiamato dalla classe che crea questo oggetto Viene eseguito una sola
	 * volta
	 */
	public void inizializza() {
		/* variabili e costanti locali di lavoro */
		Filtro filtro;
		Navigatore navTesta;
		Navigatore navDoppio;
		JSplitPane splitPane;

		try { // prova ad eseguire il codice

			/* aggiunge tutti i bottoni al dalogo */
			Pannello pan = PannelloFactory.orizzontale(null);
			this.addBottoni(pan);
			this.add(pan);

			/* crea e registra il filtro per i records di testa */
			this.creaFiltroTesta();

			/* assegna il filtro al navigatore di testa */
			filtro = this.getFiltroTesta();
			navTesta = this.getNavMaster();
			navTesta.setFiltroBase(filtro);

			/* aggiunge i listeners alle liste */
			this.aggiungiListeners();

			/**
			 * regola la policy di resizing dello split pane del navigatore
			 * doppio se espando la finestra il navigatore di destra si prende
			 * tutto lo spazio
			 */
			navDoppio = this.getNavDoppio();
			if (navDoppio != null) {
				splitPane = navDoppio.getPortaleNavigatore().getSplitPane();
				if (splitPane != null) {
					splitPane.setResizeWeight(0);
				}// fine del blocco if
			}// fine del blocco if

			/* crea i records */
			this.creaRecords();

			/* sincronizza i bottoni */
			this.sincronizzaStampa();

		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
		}// fine del blocco try-catch
	}

	/**
	 * Aggiunge i listeners alle liste utilizzate.
	 * <p/>
	 */
	private void aggiungiListeners() {
		/* variabili e costanti locali di lavoro */
		BaseListener listener;
		Lista lista;

		try { // prova ad eseguire il codice

			/* aggiunge i listeners alle liste */
			lista = this.getListaMaster();
			listener = new AzSelListaMaster();
			this.aggiungiListener(lista, listener);

			lista = this.getListaSlave();
			listener = new AzSelListaSlave();
			this.aggiungiListener(lista, listener);
			listener = new AzDoppioClicListaSlave();
			this.aggiungiListener(lista, listener);

		} catch (Exception unErrore) { // intercetta l'errore
			new Errore(unErrore);
		} // fine del blocco try-catch
	}

	/**
	 * Aggiunge i listeners che ha aggiunto.
	 * <p/>
	 */
	public void rimuoviListeners() {
		/* variabili e costanti locali di lavoro */
		ArrayList<ListenerElem> elenco;
		Lista lista;
		BaseListener listener;

		try { // prova ad eseguire il codice
			elenco = this.getListenersAggiunti();
			for (ListenerElem elem : elenco) {
				lista = elem.getLista();
				listener = elem.getListener();
				lista.removeListener(listener);
			}
		} catch (Exception unErrore) { // intercetta l'errore
			new Errore(unErrore);
		} // fine del blocco try-catch
	}

	/**
	 * Aggiunge un Listener a una Lista.
	 * <p/>
	 * Lo aggiunge alla lista e anche alla collezione dei listener aggiunti che
	 * serve per poterli rimuovere alla fine
	 * 
	 * @param lista
	 *            alla quale aggiungere il listener
	 * @param listener
	 *            da aggiungere
	 */
	private void aggiungiListener(Lista lista, BaseListener listener) {
		/* variabili e costanti locali di lavoro */
		ListenerElem elem;

		try { // prova ad eseguire il codice

			elem = new ListenerElem(lista, listener);
			this.getListenersAggiunti().add(elem);

			lista.addListener(listener);

		} catch (Exception unErrore) { // intercetta l'errore
			new Errore(unErrore);
		} // fine del blocco try-catch
	}

	private void creaRecords() {
		this.getLogica().cancellaRecords();
		this.getLogica().creaRecords();
		this.getListaMaster().caricaTutti();
		this.posiziona();
	} /* fine del metodo */

	/**
	 * Invocato quando si modifica la scheda di un cliente da parte di un
	 * oggetto Monitor Cliente.
	 * <p/>
	 */
	public void clienteModificato() {
	}

	/**
	 * Aggiunge graficamente i bottoni.
	 * <p/>
	 */
	protected void addBottoni(Pannello pan) {
		pan.add(this.getBotStampa());
		pan.add(this.getBotAnnulla());
		pan.add(this.getBotVaiCliente());
	}

	/**
	 * Regola le dimensioni (FISSE) del bottone.
	 * 
	 * @param bot
	 *            bottone da regolare
	 * @param dim
	 *            dimensione del bottone
	 */
	protected void fixBot(JButton bot, Dimension dim) {
		/* variabili e costanti locali di lavoro */
		boolean continua;

		try { // prova ad eseguire il codice
			/* controllo di congruità */
			continua = (bot != null && dim != null);

			if (continua) {
				bot.setHorizontalAlignment(SwingConstants.LEFT);
				bot.setSize(dim);
				bot.setPreferredSize(dim);
				bot.setMinimumSize(dim);
				bot.setMaximumSize(dim);
			} // fine del blocco if

		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
		}// fine del blocco try-catch
	}

	/**
	 * Posizionamento iniziale sulla prima data stampabile.
	 * <p/>
	 * Se non ci sono date stampabili, non si posiziona <br>
	 */
	private void posiziona() {
		/* variabili e costanti locali di lavoro */
		boolean continua;
		Lista listaMaster;
		int cod = 0;

		try { // prova ad eseguire il codice

			listaMaster = this.getListaMaster();
			continua = (listaMaster != null);

			if (continua) {
				cod = this.getLogica().primoRecordDaStampare();
				continua = (cod > 0);
			} // fine del blocco if \

			if (continua) {
				listaMaster.setRecordVisibileSelezionato(cod);
			} // fine del blocco if

		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
		}// fine del blocco try-catch
	}

	/**
	 * Restituisce il navigatore della testa.
	 * <p/>
	 * 
	 * @return navigatore sinistro (testa)
	 */
	protected Navigatore getNavMaster() {
		/* variabili e costanti locali di lavoro */
		Navigatore navMaster = null;
		Navigatore nav;

		try { // prova ad eseguire il codice
			nav = this.getNavDoppio();

			if (nav != null) {
				navMaster = nav.getNavMaster();
			} // fine del blocco if

		} catch (Exception unErrore) { // intercetta l'errore
			new Errore(unErrore);
		} // fine del blocco try-catch

		/* valore di ritorno */
		return navMaster;
	}

	/**
	 * Restituisce il navigatore delle righe.
	 * <p/>
	 * 
	 * @return navigatore destro (righe)
	 */
	protected Navigatore getNavSlave() {
		/* variabili e costanti locali di lavoro */
		Navigatore navSlave = null;
		Navigatore nav;

		try { // prova ad eseguire il codice
			nav = this.getNavDoppio();

			if (nav != null) {
				navSlave = nav.getNavSlave();
			} // fine del blocco if
		} catch (Exception unErrore) { // intercetta l'errore
			new Errore(unErrore);
		} // fine del blocco try-catch

		/* valore di ritorno */
		return navSlave;
	}

	/**
	 * Restituisce la lista della testa.
	 * <p/>
	 * 
	 * @return lista sinistra (testa)
	 */
	protected Lista getListaMaster() {
		/* variabili e costanti locali di lavoro */
		Lista listaMaster = null;
		Navigatore navMaster;

		try { // prova ad eseguire il codice
			navMaster = this.getNavMaster();

			if (navMaster != null) {
				listaMaster = navMaster.getLista();
			} // fine del blocco if
		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
		}// fine del blocco try-catch

		/* valore di ritorno */
		return listaMaster;
	}

	/**
	 * Restituisce la lista delle righe.
	 * <p/>
	 * 
	 * @return lista destra (righe)
	 */
	protected Lista getListaSlave() {
		/* variabili e costanti locali di lavoro */
		Lista listaSlave = null;
		Navigatore navSlave;

		try { // prova ad eseguire il codice
			navSlave = this.getNavSlave();

			if (navSlave != null) {
				listaSlave = navSlave.getLista();
			} // fine del blocco if
		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
		}// fine del blocco try-catch

		/* valore di ritorno */
		return listaSlave;
	}

	/**
	 * Restituisce il modulo della testa.
	 * <p/>
	 * 
	 * @return modulo sinistro (testa)
	 */
	protected Modulo getModuloMaster() {
		/* variabili e costanti locali di lavoro */
		Modulo moduloMaster = null;
		Navigatore navMaster;

		try { // prova ad eseguire il codice
			navMaster = this.getNavMaster();

			if (navMaster != null) {
				moduloMaster = navMaster.getModulo();
			} // fine del blocco if
		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
		}// fine del blocco try-catch

		/* valore di ritorno */
		return moduloMaster;
	}

	/**
	 * Restituisce il modulo delle righe.
	 * <p/>
	 * 
	 * @return modulo destro (righe)
	 */
	protected Modulo getModuloSlave() {
		/* variabili e costanti locali di lavoro */
		Modulo moduloSlave = null;
		Navigatore navSlave;

		try { // prova ad eseguire il codice
			navSlave = this.getNavSlave();

			if (navSlave != null) {
				moduloSlave = navSlave.getModulo();
			} // fine del blocco if
		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
		}// fine del blocco try-catch

		/* valore di ritorno */
		return moduloSlave;
	}

	/**
	 * Invocato quando si modifica la data arrivo nel dialogo.
	 * <p/>
	 * Sovrascritto dalle sottoclassi
	 */
	protected void dataModificata() {
	}

	/**
	 * Ritorna il codice della azienda attiva.
	 * <p/>
	 * 
	 * @return il codice della azienda attiva
	 */
	int getCodAzienda() {
		return this.getDialogo().getCodAzienda();
	}

	/**
	 * Crea un bottone.
	 * <p/>
	 * 
	 * @param testo
	 *            per il bottone
	 * @param listener
	 *            ascoltatore del bottone
	 * @param icona
	 *            a sinistra del testo
	 * 
	 * @return il bottone creato
	 */
	protected JButton creaBottone(String testo, ActionListener listener,
			Icon icona) {
		/* variabili e costanti locali di lavoro */
		JButton bot = null;

		try { // prova ad eseguire il codice
			bot = new JButton(testo);
			bot.setIcon(icona);
			bot.setOpaque(false);
			bot.setFocusable(false);
			bot.setEnabled(false);
			bot.addActionListener(listener);
		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
		} // fine del blocco try-catch

		/* valore di ritorno */
		return bot;
	}

	/**
	 * Navigatore doppio con testa (sinistra) e righe (destra).
	 * <p/>
	 */
	private void creaNavigatore() {
		/* variabili e costanti locali di lavoro */
		boolean continua;
		NavigatoreDoppio nav;
		Portale portale;

		try { // prova ad eseguire il codice

			nav = this.getNavigatore();
			continua = (nav != null);

			if (continua) {

				Navigatore navMaster = nav.getNavMaster();
				if ((navMaster != null)
						&& (navMaster instanceof TestaStampeNavigatore)) {
					TestaStampeNavigatore navTesta = (TestaStampeNavigatore) navMaster;
					navTesta.setPannello(this);
				}// fine del blocco if

				/* regola i riferimenti incrociati pannello-navigatore */
				// nav.setPannello(this);
				this.setNavDoppio(nav);

				/* aggiunge graficamente al pannello */
				portale = nav.getPortaleNavigatore();
				this.add(portale);

			} // fine del blocco if

		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
		}// fine del blocco try-catch

	}

	/**
	 * .
	 * <p/>
	 * 
	 * @return navigatore richiesto
	 */
	protected NavigatoreDoppio getNavigatore() {
		return null;
	}

	/**
	 * Crea e registra il filtro per i records di testa da visualizzare nel
	 * navigatore.
	 * <p/>
	 */
	private void creaFiltroTesta() {
		/* variabili e costanti locali di lavoro */
		Filtro filtro;
		int tipoRecord;
		int codAzienda;

		try { // prova ad eseguire il codice
			tipoRecord = this.getLogica().getCodTipoRecord();
			codAzienda = this.getLogica().getCodAzienda();
			filtro = FiltroFactory.crea(TestaStampe.Cam.tipo.get(), tipoRecord);
			filtro.add(FiltroFactory.crea(TestaStampe.Cam.azienda.get(),
					codAzienda));
			this.setFiltroTesta(filtro);
		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
		}// fine del blocco try-catch
	}

	/**
	 * Apre la scheda del cliente relativo alla riga correntemente selezionata
	 * nella lista righe.
	 * <p/>
	 * 
	 * @return true se l'editing del cliente è stato confermato
	 */
	protected boolean vaiCliente() {
		/* variabili e costanti locali di lavoro */
		boolean confermato = false;
		boolean continua;
		int codCliente;
		Modulo modCliente = null;

		try { // prova ad eseguire il codice

			codCliente = this.getCodClienteSelezionato();
			continua = (codCliente > 0);

			if (continua) {
				modCliente = ClienteAlbergoModulo.get();
				continua = (modCliente != null);
			}// fine del blocco if

			if (continua) {
				confermato = modCliente.presentaRecord(codCliente);
			}// fine del blocco if

			if (confermato) {
				this.getNavSlave().aggiornaLista();
			}// fine del blocco if

		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
		}// fine del blocco try-catch

		/* valore di ritorno */
		return confermato;
	}

	/**
	 * Ritorna il codice del cliente correntemente selezionato.
	 * <p/>
	 * Utilizzato dalla funzione Vai a Cliente Sovrascritto dalle sottoclassi
	 * 
	 * @return il codice del cliente selezionato
	 */
	protected int getCodClienteSelezionato() {
		return 0;
	}

	/**
	 * Controlla se un cliente è correntemente selezionato.
	 * <p/>
	 * 
	 * @return true se un cliente è selezionato
	 */
	protected boolean isClienteSelezionato() {
		return (this.getCodClienteSelezionato() > 0);
	}

	/**
	 * Controlla i giorni da stampare per il tipo richiesto (ps, notifica,
	 * istat).
	 * <p/>
	 * Metodo invocato alla creazione del dialogo ed ogni volta che si premono i
	 * bottoni della stampa <br>
	 * Filtra i records di testa in base al tipo richiesto <br>
	 * Ordina i records di testa per data di arrivo <br>
	 * Si può stampare se c'è almeno un giorno non stampato <br>
	 * Si può annullare se c'è almeno un giorno stampato <br>
	 * Regola l'aspetto dei bottoni <br>
	 * Indica la data del giorno da stampare/annullare <br>
	 */
	protected void sincronizzaStampa() {
		/* variabili e costanti locali di lavoro */
		boolean continua;
		StampeObbLogica logica;
		JButton botStampa = null;
		JButton botAnnulla = null;
		Date dataSelezionata = null;
		Date dataStampa = null;
		Date dataAnnulla = null;
		String giorno;
		boolean isStampabile = false;
		boolean isAnnullabile = false;
		boolean isRistampa;
		int codTesta = 0;
		AzStampa azStampa = null;
		Icon iconaStampa = Lib.Risorse.getIconaBase("Stampe24");
		Icon iconaRistampa = Lib.Risorse.getIconaBase("Ristampa24");
		int codPrimo;

		try { // prova ad eseguire il codice

			logica = this.getLogica();
			continua = (logica != null);

			if (continua) {
				botStampa = this.getBotStampa();
				continua = (botStampa != null);
			} // fine del blocco if

			if (continua) {
				azStampa = (AzStampa) botStampa.getActionListeners()[0];
				continua = (azStampa != null);
			} // fine del blocco if
			
			if (continua) {
				codTesta = getCodTesta();
				continua = (codTesta>0);
			}

			if (continua) {
				botAnnulla = this.getBotAnnulla();
				continua = (botAnnulla != null);
			} // fine del blocco if

			if (continua) {
				dataSelezionata = getDataSelezionata();
				continua = (Lib.Data.isValida(dataSelezionata));
			} // fine del blocco if

			if (continua) {
				dataStampa = logica.primoGiornoDaStampare();
				dataAnnulla = logica.ultimoGiornoStampato();
				isStampabile = true;
				isAnnullabile = Lib.Data.isValida(dataAnnulla);
			} // fine del blocco if

			if (continua) {

				if (Lib.Data.isValida(dataStampa)) {
					isRistampa = Lib.Data.isPrecedente(dataStampa,
							dataSelezionata);
				} else {
					isRistampa = true;
				}// fine del blocco if-else

				if (isStampabile) {
					azStampa.setStampabile(true);
					if (isRistampa) {
						azStampa.setRistampa(true);
						azStampa.setCodPrimo(codTesta);
						azStampa.setGiornoStampa(dataSelezionata);
						giorno = Lib.Data.getDataBrevissima(dataSelezionata);
						botStampa.setText("Ristampa il " + giorno);
						botStampa.setEnabled(true);
						botStampa.setIcon(iconaRistampa);
					} else {
						azStampa.setRistampa(false);
						codPrimo = logica.primoRecordDaStampare();
						azStampa.setCodPrimo(codPrimo);
						azStampa.setGiornoStampa(dataStampa);
						giorno = Lib.Data.getDataBrevissima(dataStampa);
						botStampa.setText("Stampa il " + giorno);
						botStampa.setEnabled(true);
						botStampa.setIcon(iconaStampa);
					} // fine del blocco if-else
				} else {
					azStampa.setStampabile(false);
					azStampa.setCodPrimo(0);
					azStampa.setGiornoStampa(Lib.Data.getVuota());
					botStampa.setText("Stampa non abilitata");
					botStampa.setEnabled(false);
					botStampa.setIcon(iconaStampa);
				} // fine del blocco if-else
			} // fine del blocco if

			if (continua) {
				if (isAnnullabile) {
					giorno = Lib.Data.getDataBrevissima(dataAnnulla);
					botAnnulla.setText("Annulla stampa del " + giorno);
					botAnnulla.setEnabled(true);
				} else {
					botAnnulla.setText("Annulla non disponibile");
					botAnnulla.setEnabled(false);
				} // fine del blocco if-else
			} // fine del blocco if

		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
		}// fine del blocco try-catch
	}

	/**
	 * @return la data selezionata nel navigatore dei giorni
	 */
	protected Date getDataSelezionata() {
		Date data;
		Modulo mod = this.getModuloTesta();
		data = mod.query().valoreData(TestaStampe.Cam.data.get(), getCodTesta());
		return data;
	}
	
	
	/**
	 * @return il codice del record correntemente selezionato nel navigatore di testa
	 */
	private int getCodTesta(){
		Tavola tavolaTesta = this.getListaMaster().getTavola();
		int pos = tavolaTesta.getSelectedRow();
		TavolaModello modello = tavolaTesta.getModello();
		int codTesta = modello.getChiave(pos);
		return codTesta;
	}

	/**
	 * Stampa il documento del tipo richiesto (ps, notifica, istat).
	 * <p/>
	 * Controlla che il flag nell'azione del bottone sia valido <br>
	 * (il flag è stato regolato nel metodo sincronizzaStampa) <br>
	 * Filtra i records di testa in base al tipo richiesto <br>
	 * Ordina i records di testa per data di arrivo <br>
	 * Trova il primo record di testa che risulta non stampato <br>
	 * Stampa il documento <br>
	 * Segna come stampato il record di testa <br>
	 * 
	 * @param stampaWrap
	 *            wrapper dell'azione con alcuni parametri
	 */
	private void stampa(AzStampa stampaWrap) {
		/* variabili e costanti locali di lavoro */
		boolean continua;
		StampeObbLogica logica = null;
		int codPrimoGiorno = 0;
		boolean primaStampa;

		try { // prova ad eseguire il codice

			/* controllo di congruità */
			continua = (stampaWrap != null);

			/* Controlla che il flag nell'azione del bottone sia valido */
			/* (il flag è stato regolato nel metodo sincronizzaStampa) */
			if (continua) {
				continua = stampaWrap.isStampabile();
			} // fine del blocco if

			/*
			 * recupera il primo record stampabile dalla variabile conservata
			 * nell'azione
			 */
			if (continua) {
				codPrimoGiorno = stampaWrap.getCodPrimo();
				continua = (codPrimoGiorno > 0);
			} // fine del blocco if

			if (continua) {
				logica = this.getLogica();
				continua = (logica != null);
			} // fine del blocco if

			if (continua) {

				primaStampa = (!stampaWrap.isRistampa());
				logica.stampa(codPrimoGiorno, primaStampa);

				/* se era prima stampa aggiorna il video */
				if (primaStampa) {
					this.getListaMaster().caricaSelezione();
				}// fine del blocco if

			} // fine del blocco if

		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
		}// fine del blocco try-catch
	}

	/**
	 * Annulla i records (righe) dell'ultimo giorno stampato.
	 * <p/>
	 * Cancella i records <br>
	 * Forza la ricostruzione degli stessi, come in apertura dialogo <br>
	 */
	private void annulla() {
		/* variabili e costanti locali di lavoro */
		StampeObbLogica logica;

		try { // prova ad eseguire il codice
			logica = this.getLogica();

			if (logica != null) {
				logica.annullaUltimo();
				this.creaRecords();
			} // fine del blocco if

		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
		}// fine del blocco try-catch
	}

	/**
	 * todo provvisorio!! Pone tutti i records di testa tranne quello di oggi
	 * come Stampato.
	 * <p/>
	 */
	private void setStampato() {
		/* variabili e costanti locali di lavoro */
		boolean continua;
		MessaggioDialogo dialogo;
		StampeObbLogica logica;

		try { // prova ad eseguire il codice

			dialogo = new MessaggioDialogo(
					"Vuoi contrassegnare tutti i records "
							+ "fino ad oggi (escluso) come già stampati?");
			continua = dialogo.isConfermato();

			if (continua) {
				logica = this.getLogica();

				if (logica != null) {
					logica.setStampato();
				} // fine del blocco if

			}// fine del blocco if

		} catch (Exception unErrore) { // intercetta l'errore
			Errore.crea(unErrore);
		}// fine del blocco try-catch
	}

	/**
	 * Sincronizza il Pannello.
	 * <p/>
	 * Abilita i bottoni
	 */
	public void sincronizza() {
		/* variabili e costanti locali di lavoro */
		JButton bVaiCliente;

		try { // prova ad eseguire il codice

			/* abilitazione del bottone Vai al Cliente */
			bVaiCliente = this.getBotVaiCliente();
			bVaiCliente.setEnabled(this.isClienteSelezionato());

		} catch (Exception unErrore) { // intercetta l'errore
			new Errore(unErrore);
		} // fine del blocco try-catch
	}

	protected StampeObbligatorieDialogo getDialogo() {
		return dialogo;
	}

	private void setDialogo(StampeObbligatorieDialogo dialogo) {
		this.dialogo = dialogo;
	}

	protected JButton getBotVaiCliente() {
		return botVaiCliente;
	}

	private JButton setBotVaiCliente(JButton botVaiCliente) {
		this.botVaiCliente = botVaiCliente;
		return this.getBotVaiCliente();
	}

	protected JButton getBotStampa() {
		return botStampa;
	}

	private JButton setBotStampa(JButton botStampa) {
		this.botStampa = botStampa;
		return this.getBotStampa();
	}

	protected JButton getBotAnnulla() {
		return botAnnulla;
	}

	private JButton setBotAnnulla(JButton botAnnulla) {
		this.botAnnulla = botAnnulla;
		return this.getBotAnnulla();
	}

	protected Navigatore getNavDoppio() {
		return navDoppio;
	}

	protected void setNavDoppio(NavigatoreDoppio navDoppio) {
		this.navDoppio = navDoppio;
	}

	private ArrayList<ListenerElem> getListenersAggiunti() {
		return listenersAggiunti;
	}

	private void setListenersAggiunti(ArrayList<ListenerElem> listenersAggiunti) {
		this.listenersAggiunti = listenersAggiunti;
	}

	/**
	 * Ritorna il modulo di testa.
	 * <p/>
	 * 
	 * @return il modulo di testa
	 */
	protected Modulo getModuloTesta() {
		return TestaStampeModulo.get();
	}

	protected Filtro getFiltroTesta() {
		return filtroTesta;
	}

	protected void setFiltroTesta(Filtro filtroTesta) {
		this.filtroTesta = filtroTesta;
	}

	public StampeObbLogica getLogica() {
		return logica;
	}

	public void setLogica(StampeObbLogica logica) {
		this.logica = logica;
	}

	/**
	 * Cambio del record selezionato nella lista Slave. </p>
	 */
	private class AzSelListaSlave extends ListaSelAz {
		public void listaSelAz(ListaSelEve unEvento) {
			sincronizza();
		}
	} // fine della classe 'interna'

	/**
	 * Cambio del record selezionato nella lista Master. </p>
	 */
	private class AzSelListaMaster extends ListaSelAz {
		public void listaSelAz(ListaSelEve unEvento) {
			sincronizza();
			sincronizzaStampa();
		}
	} // fine della classe 'interna'

	/**
	 * Azione di doppio clic su un record nella lista slave.
	 * <p/>
	 */
	private class AzDoppioClicListaSlave extends ListaDoppioClicAz {
		public void listaDoppioClicAz(ListaDoppioClicEve unEvento) {
			vaiCliente();
		}
	} // fine della classe interna

	/**
	 * Va alla scheda del cliente. </p>
	 */
	private final class AzVaiCliente implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			vaiCliente();
		}
	} // fine della classe 'interna'

	/**
	 * Esegue la stampa </p>
	 */
	private final class AzStampa extends BaseAzione implements ActionListener {

		private boolean isStampabile;

		private boolean isRistampa;

		/* codice del primo record di testa stampabile */
		private int codPrimo;

		private Date giornoStampa;

		public void actionPerformed(ActionEvent event) {
			stampa(this);
		}

		public boolean isStampabile() {
			return isStampabile;
		}

		public void setStampabile(boolean stampabile) {
			isStampabile = stampabile;
		}

		public boolean isRistampa() {
			return isRistampa;
		}

		public void setRistampa(boolean ristampa) {
			isRistampa = ristampa;
		}

		public int getCodPrimo() {
			return codPrimo;
		}

		public void setCodPrimo(int codPrimo) {
			this.codPrimo = codPrimo;
		}

		public Date getGiornoStampa() {
			return giornoStampa;
		}

		public void setGiornoStampa(Date giornoStampa) {
			this.giornoStampa = giornoStampa;
		}

	} // fine della classe 'interna'

	/**
	 * Annulla l'ultimo giorno </p>
	 */
	private final class AzAnnulla implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			annulla();
		}
	} // fine della classe 'interna'

	/**
	 * Annulla l'ultimo giorno </p>
	 */
	private final class AzSetStampato implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			setStampato();
		}
	} // fine della classe 'interna'

	/**
	 * Classe 'interna' </p> Wrapper Lista-Listener per registrazione e
	 * de-registrazione dei listener aggiunti alle liste
	 */
	private class ListenerElem {

		private Lista lista;
		private BaseListener listener;

		/**
		 * Costruttore completo con parametri. <br>
		 * 
		 * @param lista
		 *            lista alla quale il listener è stato aggiunto
		 * @param listener
		 *            listener che è stato aggiunto alla lista
		 */
		private ListenerElem(Lista lista, BaseListener listener) {
			/* rimanda al costruttore della superclasse */
			super();

			/* regola le variabili di istanza coi parametri */
			this.setLista(lista);
			this.setListener(listener);

		}// fine del metodo costruttore completo

		public Lista getLista() {
			return lista;
		}

		private void setLista(Lista lista) {
			this.lista = lista;
		}

		public BaseListener getListener() {
			return listener;
		}

		private void setListener(BaseListener listener) {
			this.listener = listener;
		}

	} // fine della classe 'interna'

}// fine della classe
