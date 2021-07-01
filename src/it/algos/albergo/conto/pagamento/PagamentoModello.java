/**
 * Title:     PagamentoModello
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      18-lug-2006
 */
package it.algos.albergo.conto.pagamento;

import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoLogica;
import it.algos.albergo.conto.movimento.Movimento;
import it.algos.albergo.conto.movimento.MovimentoModello;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;
import it.algos.base.wrapper.CampoValore;
import it.algos.gestione.tabelle.mezzopagamento.MezzoPagamento;

import java.util.ArrayList;

/**
 * Tracciato record della tavola Pagamento.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 18-lug-2006
 */
public class PagamentoModello extends MovimentoModello implements Pagamento {


    /**
     * Costruttore completo senza parametri.
     */
    public PagamentoModello() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* regola il nome della tavola dalla costante */
        super.setTavolaArchivio(Pagamento.NOME_TAVOLA);

        /* campo totale sincronizzato */
        this.setCampoContoSync(Conto.Cam.totPagato);


    }


    /**
     * Creazione dei campi.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Creazione dei campi record di questo modello <br>
     * I campi verranno visualizzati nell'ordine di inserimento <br>
     * Ogni campo viene creato con un costruttore semplice con solo le piu'
     * comuni informazioni; le altre vengono regolate con chiamate successive <br>
     * Invoca il metodo sovrascritto della superclasse <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.progetto.Progetto#preparaModuli
     * @see it.algos.base.modello.ModelloAlgos#creaCampi
     * @see it.algos.base.campo.base.CampoFactory
     * @see it.algos.base.campo.video.decorator.VideoFactory
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        /* invoca il metodo sovrascritto della superclasse */
        super.creaCampi();

        try { // prova ad eseguire il codice

            /* campo titolo del pagamento */
            unCampo = CampoFactory.comboInterno(Pagamento.Cam.titolo);
            unCampo.setValoriInterni(TitoloPagamento.getDescrizioni());
            unCampo.decora().obbligatorio();
            this.addCampo(unCampo);

            /* campo mezzo di pagamento */
            unCampo = CampoFactory.comboLinkPop(Pagamento.Cam.mezzo);
            unCampo.setNomeModuloLinkato(MezzoPagamento.NOME_MODULO);
            unCampo.setAzioneDelete(Db.Azione.setNull);
            this.addCampo(unCampo);

            /* campo ricevuta */
            unCampo = CampoFactory.intero(Pagamento.Cam.ricevuta);
            this.addCampo(unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea la Vista di default
     *
     * @return la Vista creata
     */
    protected Vista creaVistaDefault() {
        /* variabili e costanti locali di lavoro */
        Vista vista = null;

        try {    // prova ad eseguire il codice

            /* crea una vista vuota */
            vista = new Vista(this.getModulo());

            /* aggiunge i campi desiderati */
            vista.addCampo(Movimento.Cam.data.get());
            vista.addCampo(Movimento.Cam.conto.get());
            vista.addCampo(Pagamento.Cam.titolo.get());
            vista.addCampo(Pagamento.Cam.mezzo.get());
            vista.addCampo(Movimento.Cam.importo.get());
            vista.addCampo(Pagamento.Cam.ricevuta.get());
            vista.addCampo(Movimento.Cam.note.get());

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return vista;

    } /* fine del metodo */


    /**
     * Costruisce un ArrayList di riferimenti ordinati (oggetti Campo) per
     * individuare i campi che voglio vedere nella scheda di default
     */
    protected ArrayList<Campo> creaSetDefault() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> unSet = null;

        try { // prova ad eseguire il codice
            unSet = new ArrayList<Campo>();
            unSet.add(this.getCampo(Pagamento.Cam.data.get()));
            unSet.add(this.getCampo(Pagamento.Cam.conto.get()));
            unSet.add(this.getCampo(Pagamento.Cam.titolo.get()));
            unSet.add(this.getCampo(Pagamento.Cam.mezzo.get()));
            unSet.add(this.getCampo(Pagamento.Cam.importo.get()));
            unSet.add(this.getCampo(Pagamento.Cam.ricevuta.get()));
            unSet.add(this.getCampo(Pagamento.Cam.note.get()));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unSet;

    } /* fine del metodo */


    /**
     * Creazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Eventuale creazione di viste aggiuntive, oltre alla vista base di default <br>
     * Costruisce degli ArrayList di riferimenti ordinati (oggetti <code>Vista</code>)
     * per individuare i campi che voglio vedere nelle liste alternative ed
     * aggiuntive a quella standard (costruita in automatico nella superclasse) <br>
     * Gli array vengono creati coi campi di questo modello, oppure con
     * viste di altri moduli, oppure con campi di altri modelli <br>
     * Viene chiamato <strong>dopo</strong> che nella sottoclasse sono stati
     * costruiti tutti i campi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.progetto.Progetto#preparaModuli()
     * @see #regolaViste
     */
    protected void creaViste() {
        Vista vista;
        VistaElemento elemento;

        super.creaViste();

        try { // prova ad eseguire il codice

            /* vista per il navigatore pagamenti all'interno del conto */
            vista = this.creaVista(Pagamento.Vis.vistaConto.get());
            vista.addCampo(Movimento.Cam.data.get());
            vista.addCampo(Pagamento.Cam.titolo.get());
            elemento = vista.addCampo(Pagamento.Cam.mezzo.get());
            elemento.setTitoloColonna("a mezzo");
            elemento = vista.addCampo(Pagamento.Cam.ricevuta.get());
            elemento.setLarghezzaColonna(80);
            elemento = vista.addCampo(Movimento.Cam.importo.get());
            elemento.setTitoloColonna("importo");
            elemento.setLarghezzaColonna(60);
            elemento = vista.addCampo(Movimento.Cam.note.get());
            this.addVista(vista);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Eventuale regolazione delle caratteristiche specifiche di ogni copia dei
     * campi delle viste; le variazioni modificano <strong>solo</strong> le copie <br>
     * Viene chiamato <strong>dopo</strong> che nella superclasse sono state
     * <strong>clonate</strong> tutte le viste <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see #creaViste
     */
    protected void regolaViste() {
        /* variabili e costanti locali di lavoro */
        Vista vista;
        Campo campo;
        Campo campoSottocFisso;
        Campo campoSottocOrdine;
        Campo campoSiglaConto;
        Modulo modConto;
        Modulo modSottoconto;

        super.regolaViste();

        try { // prova ad eseguire il codice

//            /* vista default - regola il nome della colonna conto */
//            modConto = Progetto.getModulo(Conto.NOME_MODULO);
//            campoSiglaConto = modConto.getCampo(Conto.CAMPO_SIGLA);
//            vista = this.getVistaDefault();
//            campo = vista.getCampo(campoSiglaConto);
//            campo.setTitoloColonna("conto cliente");
//            campo.setLarLista(150);

//            /* vista in conto */
//            vista = this.getVista(VISTA_CONTO);
//            campo = vista.getCampo(CAMPO_GIORNO);
//            modSottoconto = Albergo.Moduli.AlbSottoconto();
//            campoSottocFisso = modSottoconto.getCampo(AlbSottoconto.CAMPO_FISSO);
//            campoSottocOrdine = modSottoconto.getCampoOrdine();
//            campo.getCampoLista().addOrdinePrivato(campoSottocFisso, Operatore.DISCENDENTE);
//            campo.getCampoLista().addOrdinePrivato(campoSottocOrdine);

//            /* vista in dialogo */
//            vista = this.getVista(VISTA_DIALOGO);
//            campo = vista.getCampo(Albergo.Moduli.conto.getCampo(Conto.CAMPO_SIGLA));
//            campo.setLarLista(150);
//            campo.setRidimensionabile(true);
//            campo = vista.getCampo(
//                    Albergo.Moduli.listinoBase.getCampo(Listino.CAMPO_DESCRIZIONE));
//            campo.setLarLista(150);
//            campo.setRidimensionabile(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }
    
    

    /**
     * Metodo invocato dopo l'eliminazione di un record esistente.
     * <p/>
     * Se la registrazione eliminata aveva l'importo totale diverso da zero,
     * aggiorna il corrispondente totale movimenti nel conto.
     *
     * @param codice del record eliminato
     * @param listaPre lista dei valori contenuti nel record al momento della eliminazione
     * @param conn la connessione utilizzata per effettuare la query
     *
     * @return true se riuscito
     */
    protected boolean eliminaRecordPost(int codice,
                                        ArrayList<CampoValore> listaPre,
                                        Connessione conn) {

        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        boolean riuscito = true;
        CampoValore cv;

        try { // prova ad eseguire il codice
        	
        	continua = super.eliminaRecordPost(codice, listaPre, conn);
            
            // se ho eliminato una registrazione di pagamento per caparra, 
        	// tolgo il flag caparraAddebitata alla corrispondente prenotazione
            if (continua) {
            	
            	// il campo Titolo Pagamento
                Campo campoTitolo = PagamentoModulo.get().getCampo(Pagamento.Cam.titolo);
                cv = Lib.Camp.getCampoValore(listaPre, campoTitolo);
                if (cv != null) {
                    int titolo = Libreria.getInt(cv.getValore());
                    if (titolo == Pagamento.TitoloPagamento.caparra.getCodice()) {
                        Campo campoLinkConto = PagamentoModulo.get().getCampo(Movimento.Cam.conto);
                        cv = Lib.Camp.getCampoValore(listaPre, campoLinkConto);
                        int idConto = Libreria.getInt(cv.getValore());
                        if (idConto!=0) {
                        	ContoLogica.RemoveFlagCaparraAccreditata(idConto, conn);
						}
					}
                } else {
                    continua = false;
                }// fine del blocco if-else

			}            

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    } // fine del metodo




} // fine della classe
