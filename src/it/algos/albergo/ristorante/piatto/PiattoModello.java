/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      18-gen-2005
 */
package it.algos.albergo.ristorante.piatto;

import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.categoria.Categoria;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.vista.Vista;

import java.util.ArrayList;

/**
 * Tracciato record della tavola Piatto.
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
 * @version 1.0    / 18-gen-2005 ore 11.11.10
 */
public final class PiattoModello extends ModelloAlgos implements Piatto {

    /**
     * nome della tavola di archivio collegata (facoltativo) <br>
     * i nomi delle tavole sono sempre minuscoli <br>
     * se vuoto usa il nome del modulo <br>
     */
    private static final String TAVOLA_ARCHIVIO = NOME_TAVOLA;

    /**
     * testi usati nelle spiegazioni dei campi
     */
    private static final String TESTO_STAMPE = " del piatto che appare nelle stampe";

    private static final String TESTO_NOME = LABEL_PIATTO;

    private static final String TESTO_DESCRIZIONE = "descrizione del piatto";

    private static final String TESTO_SPIEGAZIONE = "eventuale spiegazione aggiuntiva in ";

    private static final String ETICHETTA_CARNE = "contenuto";

    private static final String LEGENDA_CARNE = "composizione prevalente del piatto";

    private static final String TESTO_INGREDIENTI = "elenco dei principali ingredienti";

    private static final String TESTO_RICETTA = "preparazione del piatto";

    private static final String TESTO_COMANDA_A = "piatto con comanda";

    private static final String TESTO_COMANDA_B =
            "piatto che necessita di esplicita comanda per la cucina";

    private static final String TESTO_CONGELATO = "alimento congelato";

    private static final String TESTO_COPIA = "nome italiano del piatto";

    /**
     * testi usati nelle legende dei campi
     */
    private static final String LEGENDA_NOME = "nome del piatto stampato nel menu";

    private static final String LEGENDA_DESCRIZIONE =
            "eventuale descrizione aggiuntiva stampata nel menu";


    /**
     * Costruttore completo senza parametri.
     */
    public PiattoModello() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


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
    }// fine del metodo inizia


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
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.campo.base.CampoFactory
     * @see it.algos.base.campo.video.decorator.VideoFactory
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        int larNome = 300;
        int larDes = larNome;

        /* invoca il metodo sovrascritto della superclasse */
        super.creaCampi();

        try { // prova ad eseguire il codice

            /* campo nome base di riferimento (in italiano) */
            unCampo = CampoFactory.testo(CAMPO_NOME_ITALIANO);
            unCampo.decora().obbligatorio();
            unCampo.setVisibileVistaDefault();
            unCampo.setTitoloColonna("piatto");
            unCampo.decora().etichetta("nome del piatto");
            unCampo.setLarLista(250);
            unCampo.setLarScheda(larNome);
            unCampo.decora().legenda(LEGENDA_NOME);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo link alla tabella categoria */
            unCampo = CampoFactory.comboLinkPop(CAMPO_CATEGORIA);
            unCampo.decora().obbligatorio();
            unCampo.setVisibileVistaDefault();
            unCampo.setNomeModuloLinkato(Ristorante.MODULO_CATEGORIA);
            unCampo.setNomeColonnaListaLinkata(Categoria.CAMPO_SIGLA);
//            unCampo.setNomeVistaLinkata(Categoria.VISTA_SIGLA);
            unCampo.setNomeCampoValoriLinkato(Categoria.CAMPO_SIGLA);
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.decora().etichetta("categoria");
            unCampo.setLarScheda(155);
            this.addCampo(unCampo);

            /* campo spiegazione base in italiano */
            unCampo = CampoFactory.testoArea(CAMPO_SPIEGAZIONE_ITALIANO);
            unCampo.decora().etichetta(TESTO_DESCRIZIONE);
            unCampo.setLarScheda(larDes);
            unCampo.decora().legenda(LEGENDA_DESCRIZIONE);
            this.addCampo(unCampo);

            /* campo piatto carne/pesce (radio bottoni) */
            unCampo = CampoFactory.radioInterno(CAMPO_CARNE);
            unCampo.setVisibileVistaDefault();
            unCampo.setValoriInterni(Piatto.Contenuti.getLista());
            unCampo.setTitoloColonna("c/p");
            unCampo.decora().etichetta(ETICHETTA_CARNE);
            unCampo.decora().legenda(LEGENDA_CARNE);
            unCampo.setLarLista(50);
            unCampo.setRidimensionabile(false);
            unCampo.setOrientamentoComponenti(Layout.ORIENTAMENTO_ORIZZONTALE);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo ingredienti */
            unCampo = CampoFactory.testoArea(CAMPO_INGREDIENTI);
            unCampo.getCampoVideo().setNumeroRighe(6);
            unCampo.decora().legenda(TESTO_INGREDIENTI);
            unCampo.setLarScheda(300);
            this.addCampo(unCampo);

            /* campo ricetta */
            unCampo = CampoFactory.testoArea(CAMPO_RICETTA);
            unCampo.getCampoVideo().setNumeroRighe(6);
            unCampo.decora().legenda(TESTO_RICETTA);
            unCampo.setLarScheda(300);
            this.addCampo(unCampo);

            /* campi nome e spiegazione (uno per ogni lingua aggiuntiva) */
            for (int k = 1; k < CAMPO_LINGUA.length; k++) {

                /* campo nome del piatto */
                unCampo = CampoFactory.testo(NOME + CAMPO_LINGUA[k]);
                unCampo.decora().etichetta(NOME + " " + ETICHETTA_LINGUA[k]);
                unCampo.setLarScheda(larNome);
//                ((CVBase)unCampo.getCampoVideo()).setNomeCampoValoreLegenda(CAMPO_NOME_ITALIANO);
//                ((CVBase)unCampo.getCampoVideo()).setComponenteLegenda(new JLabel("ciao"));
//                VideoFactory.label(unCampo, CAMPO_NOME_ITALIANO);
                this.addCampo(unCampo);

                /* campo descrizione del piatto */
                unCampo = CampoFactory.testoArea(SPIEGAZIONE + CAMPO_LINGUA[k]);
                unCampo.decora().etichetta(TESTO_SPIEGAZIONE + ETICHETTA_LINGUA[k]);
                unCampo.setLarScheda(larDes);
//                VideoFactory.label(unCampo, CAMPO_SPIEGAZIONE_ITALIANO);
                this.addCampo(unCampo);

            } /* fine del blocco for */

            /* campo checkbox piatto comandabile */
            unCampo = CampoFactory.checkBox(CAMPO_COMANDA);
            unCampo.setValoreDefault(true);
            unCampo.setVisibileVistaDefault();
            unCampo.setTitoloColonna("com");
            unCampo.setToolTipLista(TESTO_COMANDA_A);
            unCampo.setTestoComponente(TESTO_COMANDA_A);
            unCampo.setLarScheda(150);
            unCampo.decora().legenda(TESTO_COMANDA_B);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo flag alimento congelato */
            unCampo = CampoFactory.checkBox(CAMPO_CONGELATO);
            unCampo.setTestoComponente(TESTO_CONGELATO);
            unCampo.setVisibileVistaDefault();
            unCampo.setTitoloColonna("cong");
            unCampo.setLarScheda(150);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo numero di volte che il piatto e' stato 'offerto'
     * per calcolare il coefficente di gradimento */
            unCampo = CampoFactory.intero(CAMPO_OFFERTO);
            unCampo.getCampoScheda().setPresenteScheda(false);
            this.addCampo(unCampo);

            /* campo numero di volte che il piatto e' stato 'ordinato'
             * per calcolare il coefficente di gradimento */
            unCampo = CampoFactory.intero(CAMPO_ORDINATO);
            unCampo.getCampoScheda().setPresenteScheda(false);
            this.addCampo(unCampo);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Eventuale creazione di viste aggiuntive, oltre alla vista base di default <br>
     * Costruisce degli ArrayList di riferimenti ordinati (oggetti <code>Vista</code>) per
     * individuare i campi che voglio vedere nelle liste alternative ed
     * aggiuntive a quella standard (costruita in automatico nella superclasse) <br>
     * Gli array vengono creati coi campi di questo modello, oppure con
     * viste di altri moduli, oppure con campi di altri modelli <br>
     * Viene chiamato <strong>dopo</strong> che nella sottoclasse sono stati costruiti tutti i campi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaViste() {
        /* variabili e costanti locali di lavoro */
        ArrayList unArray = null;

        try { // prova ad eseguire il codice

            /* crea la vista specifica (un solo campo) */
            super.addVista(VISTA_NOME, CAMPO_NOME_ITALIANO);

            /* crea la vista specifica (piu' campi - uso un array) */
            unArray = new ArrayList();
            unArray.add(CAMPO_CATEGORIA);
            unArray.add(CAMPO_NOME_ITALIANO);
            super.addVista(VISTA_CATEGORIA_NOME, unArray);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Eventuale regolazione delle caratteristiche specifiche di ogni copia
     * dei campi delle viste; le variazioni modificano <strong>solo</strong> le copie <br>
     * Viene chiamato <strong>dopo</strong> che nella superclasse sono state
     * <strong>clonate</strong> tutte le viste <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void regolaViste() {
        /* variabili e costanti locali di lavoro */
        Vista unaVista = null;
        Campo unCampo = null;

        try { // prova ad eseguire il codice

            unaVista = this.getVista(VISTA_COMPLETA);
            unCampo = unaVista.getCampo(CAMPO_SIGLA);
            unCampo.getCampoLista().addOrdinePubblico(this.getCampo(CAMPO_NOME_ITALIANO));

            unaVista = this.getVista(VISTA_CATEGORIA_NOME);
            unCampo = unaVista.getCampo(CAMPO_SIGLA);
            unCampo.getCampoLista().setRidimensionabile(false);
            unCampo.setLarLista(70);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione dei set aggiuntivi.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Eventuale creazione di set aggiuntivi, oltre al set base di default <br>
     * Costruisce degli ArrayList di riferimenti ordinati (oggetti <code>Campo</code>) per
     * individuare i campi che voglio vedere in un set di campi scheda <br>
     * Gli array vengono creati coi campi di questo modello, oppure con
     * campi di altri moduli <br>
     * Viene chiamato <strong>dopo</strong> che nella sottoclasse specifica sono stati
     * costruiti tutti i campi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaSet() {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> unSet = null;
        String nome = "";
        String spiegazione = "";

        nome = NOME;
        spiegazione = SPIEGAZIONE;

        try {    // prova ad eseguire il codice

            /* crea il set specifico (piu' campi - uso un array) */
            unSet = new ArrayList<String>();
            unSet.add(CAMPO_NOME_ITALIANO);
            unSet.add(CAMPO_CATEGORIA);
            unSet.add(CAMPO_SPIEGAZIONE_ITALIANO);
            unSet.add(CAMPO_CARNE);
            unSet.add(CAMPO_COMANDA);
            unSet.add(CAMPO_CONGELATO);
            super.creaSet(SET_ITALIANO, unSet);

            /* crea il set specifico (piu' campi - uso un array) */
            unSet = new ArrayList<String>();
            unSet.add(nome + CAMPO_LINGUA[TEDESCO]);
            unSet.add(spiegazione + CAMPO_LINGUA[TEDESCO]);
            super.creaSet(SET_TEDESCO, unSet);

            /* crea il set specifico (piu' campi - uso un array) */
            unSet = new ArrayList<String>();
            unSet.add(nome + CAMPO_LINGUA[INGLESE]);
            unSet.add(spiegazione + CAMPO_LINGUA[INGLESE]);
            super.creaSet(SET_INGLESE, unSet);

            /* crea il set specifico (piu' campi - uso un array) */
            unSet = new ArrayList<String>();
            unSet.add(nome + CAMPO_LINGUA[FRANCESE]);
            unSet.add(spiegazione + CAMPO_LINGUA[FRANCESE]);
            super.creaSet(SET_FRANCESE, unSet);

            /* crea il set specifico (piu' campi - uso un array) */
            unSet = new ArrayList<String>();
            unSet.add(CAMPO_INGREDIENTI);
            unSet.add(CAMPO_RICETTA);
            super.creaSet(SET_RICETTA, unSet);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazione dei set aggiuntivi.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Eventuale regolazione delle caratteristiche specifiche di ogni copia
     * dei campi dei set; le variazioni modificano <strong>solo</strong> le copie <br>
     * Viene chiamato <strong>dopo</strong> che nella sottoclasse specifica sono stati
     * costruiti tutti i campi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void regolaSet() {
    } /* fine del metodo */


}// fine della classe