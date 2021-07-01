/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.base.test;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.vista.Vista;

import java.util.ArrayList;

/**
 * Tracciato record della tavola TestTabella.
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
 * @version 1.0 / 3-4-05
 */
public final class TestTabellaModello extends ModelloAlgos implements TestTabella {

    /**
     * nome della tavola di archivio collegata (facoltativo) <br>
     * i nomi delle tavole sono sempre minuscoli <br>
     * se vuoto usa il nome del modulo <br>
     */
    private static final String TAVOLA_ARCHIVIO = NOME_TAVOLA;


    /**
     * Costruttore completo senza parametri.
     */
    public TestTabellaModello() {

        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
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


    public boolean inizializza(Modulo unModulo) {
        return super.inizializza(unModulo);
    } /* fine del metodo */


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
            unCampo.setVisibileVistaDefault(true);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo descrizione */
            unCampo = CampoFactory.descrizione();
            unCampo.setVisibileVistaDefault();
            unCampo.getCampoLista().setRidimensionabile(true);
            unCampo.setLarScheda(100);
            unCampo.decora().legendaDestra("betta");
            unCampo.setRicercabile(true);
            unCampo.setAbilitato(false);
            this.addCampo(unCampo);

            /* campo numerico 1 */
            unCampo = CampoFactory.intero(CAMPO_NUMERO);
            unCampo.setVisibileVistaDefault();
            unCampo.setRicercabile(true);
            unCampo.setUsaRangeRicerca(true);
            unCampo.setTotalizzabile(true);
            this.addCampo(unCampo);

            /* campo numerico 2 */
            unCampo = CampoFactory.intero(CAMPO_NUMERO_DUE);
            unCampo.setVisibileVistaDefault(false);
            unCampo.setRicercabile(true);
            unCampo.setUsaRangeRicerca(false);
            this.addCampo(unCampo);

            /* campo importo */
            unCampo = CampoFactory.valuta(CAMPO_IMPORTO);
            unCampo.setVisibileVistaDefault();
            unCampo.setNumDecimali(3);
            unCampo.setTotalizzabile(true);
            this.addCampo(unCampo);

            /* campo data */
            unCampo = CampoFactory.data(CAMPO_DATA);
            unCampo.setInit(null);
            unCampo.setRicercabile(true);
            unCampo.setUsaRangeRicerca(false);
            this.addCampo(unCampo);

            /* campo data nascita */
            unCampo = CampoFactory.data(CAMPO_DATA_NASCITA);
            unCampo.setInit(null);
            unCampo.setRicercabile(true);
            unCampo.setUsaRangeRicerca(false);
            this.addCampo(unCampo);

//            unCampo = CampoFactory.sigla();
//            unCampo.decora().estratto(Nazione.Estratto.descrizione);
//            this.addCampo(unCampo);

            /* campo check box */
            unCampo = CampoFactory.checkBox(CAMPO_CHECK);
            unCampo.setValoriInterni("primo, secondo");
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo singolo radio bottone */
            unCampo = CampoFactory.radioBox(CAMPO_RADIO_SINGLE);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo gruppo radio bottoni */
            unCampo = CampoFactory.radioInterno(CAMPO_RADIO_GROUP);
            unCampo.setValoriInterni("Si, No");
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo combo */
            unCampo = CampoFactory.comboInterno(CAMPO_COMBO);
            unCampo.setValoriInterni("alfa, beta, gamma");
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo colore (gruppo di check interno) */
            unCampo = CampoFactory.checkInterno(CAMPO_COLORI);
            unCampo.setValoriInterni(TestTabella.Colori.getStringaValori());
            unCampo.decora().etichetta("Colore");
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

//            /* campo sub lista/estratto indirizzo */
//            modulo = Progetto.getModulo(Indirizzo.NOME_MODULO);
//            nav = modulo.getNavigatoreDefault();
//            nav.setFinestraPop(true);
//            unCampo = CampoFactory.navigatore(CAMPO_NUMERO, nav);
//            nav.setUsaFrecceSpostaOrdineLista(true);
//            nav.setRigheLista(3);
//            nav.getPortaleLista().setUsaStatusBar(false);
//            this.addCampo(unCampo);

//            /* campo descrizione */
//            unCampo = CampoFactory.descrizione();
//            unCampo.setTestoEtichetta("Codice fiscale");
//            unCampo.setValidatore(new ValidatoreCF());
//            this.addCampo(unCampo);

//            /* campo numerico */
//            unCampo = CampoFactory.intero("numero");
//            unCampo.setVisibileVistaDefault();
//            unCampo.setValidatore(new ValidatoreTest());
//            this.addCampo(unCampo);
//
//            /* campo numerico */
//            unCampo = CampoFactory.data("data");
//            unCampo.setVisibileVistaDefault();
//            this.addCampo(unCampo);

            /* campo note (testo area) */
            unCampo = CampoFactory.testoArea(CAMPO_NOTE);
            unCampo.setRicercabile(true);
            unCampo.setAbilitato(false);
//            unCampo.setLarScheda(180);
            this.addCampo(unCampo);

            /* rende visibile il campo ordine */
            super.setCampoOrdineVisibileLista(); //

//            for(Campo campo : this.getCampiModello().values()){
//                   campo.setRicercabile(false);
//            }

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
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
        /* variabili e costanti locali di lavoro */
        ArrayList unArray = null;

        try { // prova ad eseguire il codice

            /* crea la vista specifica (un solo campo) */
//            super.addVista(VISTA_SIGLA, CAMPO_SIGLA);

            /* crea la vista specifica (un solo campo) */
//            super.addVista(VISTA_DESCRIZIONE, CAMPO_DESCRIZIONE);

            /* crea la vista specifica (piu' campi - uso un array) */
//            unArray = new ArrayList();
//            unArray.add(CAMPO_SIGLA);
//            unArray.add(CAMPO_DESCRIZIONE);
//            super.addVista(VISTA_XXX, unArray);

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
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
        Vista unaVista = null;
        Campo unCampo = null;

        try { // prova ad eseguire il codice

//            unaVista = this.getVista(VISTA_SIGLA);
//            unCampo = unaVista.getCampo(CAMPO_SIGLA);
//            unCampo.getCampoLista().setRidimensionabile(false);
//            unCampo.setTitoloColonna(COLONNA_SIGLA);

//            unaVista = this.getVista(VISTA_DESCRIZIONE);
//            unCampo = unaVista.getCampo(CAMPO_DESCRIZIONE);
//            unCampo.getCampoLista().setRidimensionabile(false);
//            unCampo.setTitoloColonna(COLONNA_DESCRIZIONE);

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


} // fine della classe
