/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 2 feb 2006
 */

package it.algos.albergo.tabelle.azienda;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.interfaccia.Generale;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;

/**
 * Tracciato record della tavola Azienda.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea il <strong>tracciato record</strong> (Abstract Data Types) di una
 * tavola </li>
 * <li> Crea i <strong>campi</strong> di questo modello (oltre a quelli base
 * della superclasse) nel metodo <code>creaCampi</code> </li>
 * <li> Ogni campo viene creato con un costruttore semplice con solo le piu'
 * comuni informazioni; le altre vengono regolate con chiamate successive </li>
 * <li> Crea eventuali <strong>viste</strong> della <code>Lista</code>
 * (oltre a quella base) nel metodo <code>creaViste</code> </li>
 * <li> Regola eventualmente i valori delle viste nel metodo <code>regolaViste</code> </li>
 * <li> Crea eventuali <strong>set</strong> della <code>Scheda</code>
 * (oltre a quello base) nel metodo <code>creaSet</code> </li>
 * <li> Regola eventualmente i valori dei set nel metodo <code>regolaSet</code> </li>
 * <li> Regola eventualmente i valori da inserire in un <code>nuovoRecord</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 2 feb 2006
 */
public final class AziendaModello extends ModelloAlgos implements Azienda {

    /**
     * nome della tavola di archivio collegata (facoltativo) <br>
     * i nomi delle tavole sono sempre minuscoli <br>
     * se vuoto usa il nome del modulo <br>
     */
    private static final String TAVOLA_ARCHIVIO = NOME_TAVOLA;

    /**
     * Testo della colonna della Lista come appare nella Vista
     */
    private static final String COLONNA_SIGLA = TITOLO_TABELLA;

    /**
     * Testo della colonna della Lista come appare nella Vista
     */
    private static final String COLONNA_DESCRIZIONE = TITOLO_TABELLA;


    /**
     * Testo della legenda sotto il campo sigla nella scheda
     */
    private static final String LEGENDA_SIGLA = Generale.LEGENDA_SIGLA;

    /**
     * Testo della legenda sotto il campo descrizione nella scheda
     */
    private static final String LEGENDA_DESCRIZIONE = "Ragione sociale dell'azienda";


    /**
     * Costruttore completo senza parametri.
     */
    public AziendaModello() {
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
        super.setTavolaArchivio(TAVOLA_ARCHIVIO);

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
        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            this.getCampoChiave().setVisibileVistaDefault();

            /* campo sigla */
            unCampo = CampoFactory.sigla();
            unCampo.getCampoLista().setRidimensionabile(true);
            unCampo.setTitoloColonna(COLONNA_SIGLA);
            unCampo.decora().legenda(LEGENDA_SIGLA);
            this.addCampo(unCampo);

            /* campo descrizione */
            unCampo = CampoFactory.descrizione();
            unCampo.setTitoloColonna(COLONNA_DESCRIZIONE);
            unCampo.decora().legenda(LEGENDA_DESCRIZIONE);
            this.addCampo(unCampo);

            /* campo indirizzo */
            unCampo = CampoFactory.testo(Cam.indirizzo);
            unCampo.setLarScheda(200);
            this.addCampo(unCampo);

            /* campo cap */
            unCampo = CampoFactory.testo(Cam.cap);
            unCampo.setLarScheda(50);
            this.addCampo(unCampo);

            /* campo località */
            unCampo = CampoFactory.testo(Cam.localita);
            unCampo.setLarScheda(200);
            this.addCampo(unCampo);

            /* campo partita IVA */
            unCampo = CampoFactory.partitaIVA(Cam.partitaIva);
            this.addCampo(unCampo);

            /* campo numero di stelle */
            unCampo = CampoFactory.intero(Cam.numStelle);
            this.addCampo(unCampo);

            /* campo preferito */
            this.setUsaCampoPreferito(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


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

        try { // prova ad eseguire il codice
            /* crea la vista specifica (un solo campo) */
            super.addVista(VISTA_SIGLA, CAMPO_SIGLA);

            /* crea la vista specifica (un solo campo) */
            super.addVista(VISTA_DESCRIZIONE, CAMPO_DESCRIZIONE);

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
        Vista unaVista;
        Campo unCampo;

        try { // prova ad eseguire il codice

            unaVista = this.getVista(VISTA_SIGLA);
            unCampo = unaVista.getCampo(CAMPO_SIGLA);
            unCampo.getCampoLista().setRidimensionabile(false);
            unCampo.setTitoloColonna(COLONNA_SIGLA);

            unaVista = this.getVista(VISTA_DESCRIZIONE);
            unCampo = unaVista.getCampo(CAMPO_DESCRIZIONE);
            unCampo.getCampoLista().setRidimensionabile(false);
            unCampo.setTitoloColonna(COLONNA_DESCRIZIONE);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce un estratto con un blocco di testo di intestazione.
     * </p>
     *
     * @param cod codice azienda
     *
     * @return l'estratto costruito
     */
    private EstrattoBase getEstrattoIntestazione(int cod) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase estratto = null;
        boolean continua;
        String testo = "";
        String nome = "";
        String indirizzo = "";
        String cap = "";
        String localita = "";
        int valIva;
        String iva = "";
        String sep = "\n";

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(cod));

            if (continua) {
                nome = this.query().valoreStringa(Azienda.Cam.descrizione.get(), cod);
                indirizzo = this.query().valoreStringa(Azienda.Cam.indirizzo.get(), cod);
                cap = this.query().valoreStringa(Azienda.Cam.cap.get(), cod);
                localita = this.query().valoreStringa(Azienda.Cam.localita.get(), cod);
                iva = this.query().valoreStringa(Azienda.Cam.partitaIva.get(), cod);
                iva = "P.I. " + iva;
            }// fine del blocco if

            if (continua) {
                testo = nome;
                testo += sep;
                testo += cap+" "+indirizzo+" "+localita;
                testo += sep;
                testo += iva;
            }// fine del blocco if

            estratto = new EstrattoBase(testo, EstrattoBase.Tipo.stringa);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return estratto;
    }


    /**
     * Restituisce un estratto.
     * </p>
     * Restituisce un estratto conforme al tipo ed al record richiesto <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param estratto codifica dell'estratto desiderato
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito
     */
    public EstrattoBase getEstratto(Estratti estratto, Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;

        try { // prova ad eseguire il codice

            /* selettore della variabile */
            switch ((Azienda.Est)estratto) {
                case intestazione:
                    unEstratto = this.getEstrattoIntestazione(Libreria.getInt(chiave));
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
    }


} // fine della classe
