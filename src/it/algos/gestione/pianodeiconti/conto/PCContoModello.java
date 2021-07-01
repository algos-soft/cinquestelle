/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 2 feb 2006
 */

package it.algos.gestione.pianodeiconti.conto;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.gestione.pianodeiconti.mastro.PCMastro;
import it.algos.gestione.pianodeiconti.sottoconto.PCSottoconto;

import java.util.ArrayList;

/**
 * Tracciato record della tavola PCConto.
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
public class PCContoModello extends ModelloAlgos implements PCConto {

    /**
     * nome della tavola di archivio collegata (facoltativo) <br>
     * i nomi delle tavole sono sempre minuscoli <br>
     * se vuoto usa il nome del modulo <br>
     */
    private static final String TAVOLA_ARCHIVIO = NOME_TAVOLA;

    /**
     * Testo della colonna della Lista come appare nella Vista
     */
    private static final String COLONNA_SIGLA = "conti";

    /**
     * Testo della colonna della Lista come appare nella Vista
     */
    private static final String COLONNA_DESCRIZIONE = TITOLO_TABELLA;

    /**
     * Etichetta per il campo sigla nella scheda
     */
    protected static final String TITOLO_SIGLA = "codice";

    protected static final String TESTO_MASTRO = "mastro di appartenenza";

    /**
     * Testo della legenda sotto il campo sigla nella scheda
     */
    protected static final String LEGENDA_SIGLA = "codice come appare nelle liste di altri moduli";

    /**
     * Testo della legenda sotto il campo descrizione nella scheda
     */
    protected static final String LEGENDA_DESCRIZIONE = "descrizione completa del conto";

    /**
     * Testo della legenda sotto il campo sottoconti nella scheda
     */
    protected static final String LEGENDA_SOTTOCONTI =
            "elenco dei sottoconti che usano questo conto";


    /**
     * Costruttore completo senza parametri.
     */
    public PCContoModello() {
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
        Modulo modulo;
        Navigatore nav;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo sigla */
            unCampo = CampoFactory.sigla();
            unCampo.getCampoLista().setRidimensionabile(true);
            unCampo.setTitoloColonna(COLONNA_SIGLA);
            unCampo.decora().etichetta(TITOLO_SIGLA);
            unCampo.decora().legenda(LEGENDA_SIGLA);
            this.addCampo(unCampo);

            /* campo descrizione */
            unCampo = CampoFactory.descrizione();
            unCampo.setTitoloColonna(COLONNA_DESCRIZIONE);
            unCampo.decora().legenda(LEGENDA_DESCRIZIONE);
            this.addCampo(unCampo);

            /* campo link mastro */
            unCampo = CampoFactory.comboLinkPop(CAMPO_MASTRO);
            unCampo.setAzioneDelete(Db.Azione.cascade);
            unCampo.decora().obbligatorio();
            unCampo.decora().etichetta(TESTO_MASTRO);
            unCampo.setNomeModuloLinkato(PCMastro.NOME_MODULO);
            unCampo.decora().estrattoSotto(PCMastro.Estratto.descrizione);
            this.addCampo(unCampo);

            /* campo lista sottoconti */
            modulo = Progetto.getModulo(PCSottoconto.NOME_MODULO);
            if (modulo != null) {
                nav = modulo.getNavigatore(PCSottoconto.NAVIGATORE_CONTO);
                unCampo = CampoFactory.navigatore(PCConto.CAMPO_SOTTOCONTI, nav);
                unCampo.decora().eliminaEtichetta();
                unCampo.decora().legenda(LEGENDA_SOTTOCONTI);
                this.addCampo(unCampo);
            }// fine del blocco if

            /* rende visibile il campo ordine */
            super.setCampoOrdineVisibileLista(); //

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

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione dei set aggiuntivi.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Eventuale creazione di set aggiuntivi, oltre al set base di default <br>
     * Costruisce degli ArrayList<String> di campi ordinati per
     * individuare i campi che voglio vedere in un set di campi scheda <br>
     * Viene chiamato <strong>dopo</strong> che nella sottoclasse specifica
     * sono stati costruiti tutti i campi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaSet() {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> unSet;

        try { // prova ad eseguire il codice

            /* crea il set specifico (piu' campi - uso un array) */
            unSet = new ArrayList<String>();
            unSet.add(CAMPO_CONTO);
            unSet.add(CAMPO_DESCRIZIONE);
            unSet.add(CAMPO_MASTRO);
            super.creaSet(SET_MASTRO, unSet);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce un estratto.
     * </p>
     * Metodo invocato dal modulo <br>
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
            switch ((PCContoModello.Estratto)estratto) {
                case sigla:
                    unEstratto = this.getEstratto(estratto, chiave, PCMastro.CAMPO_SIGLA);
                    break;
                case descrizione:
                    unEstratto = this.getEstratto(estratto, chiave, PCMastro.CAMPO_DESCRIZIONE);
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
