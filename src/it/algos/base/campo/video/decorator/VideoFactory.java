/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 5 luglio 2003 alle 13.46
 */
package it.algos.base.campo.video.decorator;

import it.algos.base.azione.Azione;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.wrapper.Estratti;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Factory per la creazione di oggetti decoratori ai campi CampoVideo.
 * </p>
 * Questa classe astratta factory: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Factory Method</b> </li>
 * <li> Fornisce metodi <code>statici</code> per la  creazione degli oggetti di questo
 * package </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  5 luglio 2003 ore 13.46
 */
public abstract class VideoFactory {

    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Uso il modificatore <i>private<i/> perche' la classe e' astratta <br>
     */
    private VideoFactory() {
        /* rimanda al costruttore della superclasse */
        super();
    } /* fine del metodo costruttore completo */

//    /**
//     * Crea un campo decoratore Etichetta.
//     * <p/>
//     * Questo metodo factory: <ul>
//     * <li> crea un un wrapper intorno ad un preesistente campo video
//     * di cui estende le funzionalita al runtime </li>
//     * <li> crea una Etichetta con un testo descrittivo fisso (statico) </li>
//     * </ul>
//     *
//     * @param unCampo campo 'parente' del campo video da decorare
//     * @param pos     posizione della label all'interno del pannelloCampo
//     *                (codifica in CVDecoratore.Pos)
//     *
//     * @see it.algos.base.layout.Layout
//     * @see it.algos.base.campo.video.CVBase
//     * @see CVDecoratore
//     * @see CVDEtichetta
//     * @since 11-5-04
//     */
//    private static CVDEtichetta etichetta(Campo unCampo, CVDecoratore.Pos pos) {
//        /* variabili e costanti locali di lavoro */
//        CVDEtichetta campoDecorato = null;
//        CampoVideo campoVideoDaDecorare;
//
//        try {    // prova ad eseguire il codice
//            /* recupera dal campo 'parente', il campo video da decorare */
//            campoVideoDaDecorare = unCampo.getCampoVideo();
//
//            /* crea l'instanza */
//            campoDecorato = new CVDEtichetta(campoVideoDaDecorare);
//
//            /* regola la posizione */
//            campoDecorato.setPos(pos);
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            Errore.crea(unErrore);
//        } /* fine del blocco try-catch */
//
//        /* valore di ritorno */
//        return campoDecorato;
//    }

//    /**
//     * Crea un campo decoratore Etichetta.
//     * <p/>
//     * Questo metodo factory: <ul>
//     * <li> crea un un wrapper intorno ad un preesistente campo video
//     * di cui estende le funzionalita al runtime </li>
//     * <li> crea una Etichetta con un testo descrittivo fisso (statico) </li>
//     * <li> di default la Etichetta viene posizionata sopra il pannelloComponenti </li>
//     * <li> di default il testo della Etichetta � il nome interno del Campo </li>
//     * </ul>
//     *
//     * @param unCampo campo 'parente' del campo video da decorare
//     *
//     * @return unCampoEtichettato il campo video decorato con l'etichetta
//     *
//     * @see it.algos.base.campo.video.CVBase
//     * @see it.algos.base.campo.video.decorator.CVDecoratore
//     * @see it.algos.base.campo.video.decorator.CVDEtichetta
//     */
//    public static CVDEtichetta etichetta(Campo unCampo) {
//        /* invoca il metodo sovrascritto di questa classe */
//        return etichetta(unCampo, CVDecoratore.Pos.SOPRA);
//    }
//
//
//    /**
//     * Crea un campo decoratore Etichetta a sinistra del pannelloComponenti.
//     * <p/>
//     * Questo metodo factory: <ul>
//     * <li> crea un un wrapper intorno ad un preesistente campo video
//     * di cui estende le funzionalita al runtime </li>
//     * <li> crea una Etichetta con un testo descrittivo fisso (statico) </li>
//     * <li> la Etichetta viene posizionata a sinistra del pannelloComponenti </li>
//     * <li> di default il testo della Etichetta � il nome interno del Campo </li>
//     * </ul>
//     *
//     * @param unCampo campo 'parente' del campo video da decorare
//     *
//     * @see it.algos.base.campo.video.CVBase
//     * @see it.algos.base.campo.video.decorator.CVDecoratore
//     * @see it.algos.base.campo.video.decorator.CVDEtichetta
//     */
//    public static CVDEtichetta etichettaSinistra(Campo unCampo) {
//        /* invoca il metodo sovrascritto di questa classe */
//        return etichetta(unCampo, CVDecoratore.Pos.SINISTRA);
//    }


    /**
     * Crea un campo decoratore Etichetta.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea una Etichetta con un testo descrittivo fisso (statico) </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     * @param testo testo della Etichetta
     * @param pos posizione della Etichetta all'interno del pannelloCampo
     * (codifica in CVDecoratore.Pos)
     *
     * @see it.algos.base.campo.video.CVBase
     * @see CVDecoratore
     * @see CVDEtichetta
     * @since 11-5-04
     */
    private static CVDEtichetta etichetta(Campo unCampo, String testo, CVDecoratore.Pos pos) {
        /* variabili e costanti locali di lavoro */
        CVDEtichetta campoDecorato = null;
        CampoVideo campoVideoDaDecorare;

        try {    // prova ad eseguire il codice
            /* recupera dal campo 'parente', il campo video da decorare */
            campoVideoDaDecorare = unCampo.getCampoVideo();

            /* crea l'instanza */
            campoDecorato = new CVDEtichetta(campoVideoDaDecorare);

            /* regola il testo */
            campoDecorato.setTestoLabel(testo);

            /* regola la posizione */
            campoDecorato.setPos(pos);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return campoDecorato;
    }


    /**
     * Crea un campo decoratore Etichetta.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea una Etichetta con un testo descrittivo fisso (statico) </li>
     * <li> di default la Etichetta viene posizionata sopra il pannelloComponenti </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     * @param testo testo della etichetta
     *
     * @see it.algos.base.campo.video.CVBase
     * @see CVDecoratore
     * @see CVDEtichetta
     */
    public static CVDEtichetta etichetta(Campo unCampo, String testo) {
        /* invoca il metodo sovrascritto di questa classe */
        VideoFactory.eliminaEtichetta(unCampo.getCampoVideo());
        return etichetta(unCampo, testo, CVDecoratore.Pos.SOPRA);
    } /* fine del metodo */


    /**
     * Crea un campo decoratore Etichetta.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea una Etichetta con un testo descrittivo fisso (statico) </li>
     * <li> la Etichetta viene posizionata a sinistra del pannelloComponenti </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     * @param testo testo della etichetta
     *
     * @see it.algos.base.campo.video.CVBase
     * @see it.algos.base.campo.video.decorator.CVDecoratore
     * @see it.algos.base.campo.video.decorator.CVDEtichetta
     */
    public static CVDEtichetta etichettaSinistra(Campo unCampo, String testo) {
        /* invoca il metodo sovrascritto di questa classe */
        return etichetta(unCampo, testo, CVDecoratore.Pos.SINISTRA);
    } /* fine del metodo */


    /**
     * Sposta l'etichetta nella posizione a sinistra del campo.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> recupera il testo dell'etichetta </li>
     * <li> se il testo è inesistente, utilizza il nome del campo </li>
     * <li> elimina l'etichetta (in qualunque posizione si trovasse) </li>
     * <li> l'etichetta viene posizionata a sinistra del pannelloComponenti </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     */
    public static CVDEtichetta etichettaSinistra(Campo unCampo) {
        /* variabili e costanti locali di lavoro */
        CVDEtichetta etichetta = null;
        String testo;


        try { // prova ad eseguire il codice
            testo = unCampo.getTestoEtichetta();
            unCampo.decora().eliminaEtichetta();
            etichetta = etichetta(unCampo, testo, CVDecoratore.Pos.SINISTRA);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return etichetta;
    } /* fine del metodo */


    /**
     * Crea un campo decoratore Legenda.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea una Legenda con un testo descrittivo fisso (statico) </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     * @param testo testo della legenda
     * @param pos posizione della label all'interno del pannelloCampo
     * (codifica in CVDecoratore.Pos)
     *
     * @see it.algos.base.campo.video.CVBase
     * @see CVDecoratore
     * @see CVDLegenda
     * @since 11-5-04
     */
    private static CVDLegenda legenda(Campo unCampo, String testo, CVDecoratore.Pos pos) {
        /* variabili e costanti locali di lavoro */
        CVDLegenda campoDecorato = null;
        CampoVideo campoVideoDaDecorare;

        try {    // prova ad eseguire il codice
            /* recupera dal campo 'parente', il campo video da decorare */
            campoVideoDaDecorare = unCampo.getCampoVideo();

            /* crea l'instanza */
            campoDecorato = new CVDLegenda(campoVideoDaDecorare);

            /* regola il testo */
            campoDecorato.setTestoLabel(testo);

            /* regola la posizione */
            campoDecorato.setPos(pos);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return campoDecorato;
    }


    /**
     * Crea un campo decoratore Legenda.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea una Legenda con un testo descrittivo fisso (statico) </li>
     * <li> di default la Legenda viene posizionata sotto il pannelloComponenti </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     * @param testo testo della legenda
     *
     * @see it.algos.base.campo.video.CVBase
     * @see CVDecoratore
     * @see CVDLegenda
     */
    public static CVDLegenda legenda(Campo unCampo, String testo) {
        /* invoca il metodo sovrascritto di questa classe */
        return legenda(unCampo, testo, CVDecoratore.Pos.SOTTO);
    } /* fine del metodo */


    /**
     * Crea un campo decoratore Legenda.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea una Legenda con un testo descrittivo fisso (statico) </li>
     * <li> la Legenda viene posizionata a destra del pannelloComponenti </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     * @param testo testo della legenda
     *
     * @see it.algos.base.campo.video.CVBase
     * @see it.algos.base.campo.video.decorator.CVDecoratore
     * @see it.algos.base.campo.video.decorator.CVDLegenda
     */
    public static CVDLegenda legendaDestra(Campo unCampo, String testo) {
        /* invoca il metodo sovrascritto di questa classe */
        return legenda(unCampo, testo, CVDecoratore.Pos.DESTRA);
    } /* fine del metodo */


    /**
     * Crea un campo decoratore calcolato.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea una Label che recupera il valore da un altro campo della scheda </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     * @param campoValore nome del campo da cui recuperare il testo della label
     * @param pos posizione della label all'interno del pannelloCampo
     * (codifica in CVDecoratore.Pos)
     *
     * @see it.algos.base.layout.Layout
     * @see it.algos.base.campo.video.CVBase
     * @see CVDecoratore
     * @see CVDLabel
     * @since 11-5-04
     */
    private static CVDCalcolato calcolato(Campo unCampo, String campoValore, CVDecoratore.Pos pos) {
        /* variabili e costanti locali di lavoro */
        CVDCalcolato campoDecorato = null;
        CampoVideo campoVideoDaDecorare;

        try {    // prova ad eseguire il codice
            /* recupera dal campo 'parente', il campo video da decorare */
            campoVideoDaDecorare = unCampo.getCampoVideo();

            /* crea l'instanza */
            campoDecorato = new CVDCalcolato(campoVideoDaDecorare);

            /* regola il campo di riferimento */
            campoDecorato.setCampoOsservato(campoValore);

            /* regola la posizione */
            campoDecorato.setPos(pos);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return campoDecorato;
    }


    /**
     * Crea un campo decoratore calcolato.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea una Label che recupera il valore da un altro campo della scheda </li>
     * <li> di default la Label viene posizionata sotto il pannelloComponenti </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     * @param campoValore nome del campo da cui recuperare il testo della label
     *
     * @see it.algos.base.campo.video.CVBase
     * @see it.algos.base.campo.video.decorator.CVDecoratore
     * @see it.algos.base.campo.video.decorator.CVDLabel
     * @since 11-5-04
     */
    public static CVDCalcolato calcolato(Campo unCampo, String campoValore) {
        /* invoca il metodo sovrascritto di questa classe */
        return calcolato(unCampo, campoValore, CVDecoratore.Pos.SOTTO);
    }


    /**
     * Crea un campo decoratore copiato.
     * <p/>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     * @param campi nomi dei campi da riempire
     *
     * @see it.algos.base.campo.video.CVBase
     * @see it.algos.base.campo.video.decorator.CVDecoratore
     * @see it.algos.base.campo.video.decorator.CVDLabel
     * @since 11-5-04
     */
    public static CVDCopiato copiato(Campo unCampo, String... campi) {
        /* variabili e costanti locali di lavoro */
        CVDCopiato campoDecorato = null;
        CampoVideo campoVideoDaDecorare;
        ArrayList<String> lista;

        try {    // prova ad eseguire il codice
            /* recupera dal campo 'parente', il campo video da decorare */
            campoVideoDaDecorare = unCampo.getCampoVideo();

            /* crea l'instanza */
            campoDecorato = new CVDCopiato(campoVideoDaDecorare);

            /* regola i campi di riferimento */
            lista = new ArrayList<String>();
            /* traverso tutta la collezione */
            for (String nome : campi) {
                lista.add(nome);
            } // fine del ciclo for-each
            campoDecorato.setListaCampi(lista);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return campoDecorato;
    }


    /**
     * Crea un campo decoratore calcolato a destra del pannelloComponenti.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea una Label che recupera il valore da un altro campo della scheda </li>
     * <li> la Label viene posizionata a destra del pannelloComponenti </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     * @param campoValore nome del campo da cui recuperare il testo della label
     *
     * @see it.algos.base.campo.video.CVBase
     * @see it.algos.base.campo.video.decorator.CVDecoratore
     * @see it.algos.base.campo.video.decorator.CVDLabel
     * @since 11-5-04
     */
    public static CVDCalcolato calcolatoDestra(Campo unCampo, String campoValore) {
        /* invoca il metodo sovrascritto di questa classe */
        return calcolato(unCampo, campoValore, CVDecoratore.Pos.DESTRA);
    }


    /**
     * Crea un campo decoratore calcolato sopra il pannelloComponenti.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea una Label che recupera il valore da un altro campo della scheda </li>
     * <li> la Label viene posizionata sopra il pannelloComponenti </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     * @param campoValore nome del campo da cui recuperare il testo della label
     *
     * @see it.algos.base.campo.video.CVBase
     * @see it.algos.base.campo.video.decorator.CVDecoratore
     * @see it.algos.base.campo.video.decorator.CVDLabel
     * @since 11-5-04
     */
    public static CVDCalcolato calcolatoSopra(Campo unCampo, String campoValore) {
        /* invoca il metodo sovrascritto di questa classe */
        return calcolato(unCampo, campoValore, CVDecoratore.Pos.SOPRA);
    }


    /**
     * Crea un campo decoratore estratto.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea un Estratto che recupera il valore da un campo
     * di un'altro modulo </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     * @param nome dell'estratto di un modulo
     * @param pos posizione dell'estratto all'interno del pannelloCampo
     * (codifica in CVDecoratore.Pos)
     *
     * @see it.algos.base.layout.Layout
     * @see it.algos.base.campo.video.CVBase
     * @see CVDecoratore
     * @see CVDLabel
     * @since 11-5-04
     */
    private static CVDEstratto estratto(Campo unCampo, Estratti nome, CVDecoratore.Pos pos) {
        /* variabili e costanti locali di lavoro */
        CVDEstratto campoDecorato = null;
        CampoVideo campoVideoDaDecorare;

        try {    // prova ad eseguire il codice
            /* recupera dal campo 'parente', il campo video da decorare */
            campoVideoDaDecorare = unCampo.getCampoVideo();

            /* crea l'instanza */
            campoDecorato = new CVDEstratto(campoVideoDaDecorare);

            /* regola l'estratto di riferimento */
            campoDecorato.setEstratto(nome);

            /* regola la posizione */
            campoDecorato.setPos(pos);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return campoDecorato;
    }


    /**
     * Crea un campo decoratore estratto.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea un Estratto che recupera il valore da un campo
     * di un'altro modulo </li>
     * <li> di default l'Estratto viene posizionata a destra del
     * pannelloComponenti </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     * @param nome dell'estratto di un modulo
     *
     * @see it.algos.base.campo.video.CVBase
     * @see CVDecoratore
     * @see CVDLabel
     * @since 11-5-04
     */
    public static CVDEstratto estratto(Campo unCampo, Estratti nome) {
        /* invoca il metodo sovrascritto di questa classe */
        return estratto(unCampo, nome, CVDecoratore.Pos.DESTRA);
    }


    /**
     * Crea un campo decoratore estratto.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea un Estratto che recupera il valore da un campo
     * di un'altro modulo </li>
     * <li> l'Estratto viene posizionata sotto il pannelloComponenti </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     * @param nome dell'estratto di un modulo
     *
     * @see it.algos.base.campo.video.CVBase
     * @see CVDecoratore
     * @see CVDLabel
     * @since 11-5-04
     *        <p/>
     *        Elimina l'etichetta associato al campo video.
     *        <p/>
     *        Se non esiste non fa niente
     */
    public static CVDEstratto estrattoSotto(Campo unCampo, Estratti nome) {
        /* invoca il metodo sovrascritto di questa classe */
        return estratto(unCampo, nome, CVDecoratore.Pos.SOTTO);
    }


    /**
     * Crea un campo decoratore check.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea un campo check </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     *
     * @see it.algos.base.layout.Layout
     * @see it.algos.base.campo.video.CVBase
     * @see CVDecoratore
     * @see CVDLabel
     * @since 11-5-04
     */
    public static CVDCheck check(Campo unCampo, boolean selezionato) {
        /* variabili e costanti locali di lavoro */
        CVDCheck campoDecorato = null;
        CampoVideo campoVideoDaDecorare;

        try {    // prova ad eseguire il codice
            /* recupera dal campo 'parente', il campo video da decorare */
            campoVideoDaDecorare = unCampo.getCampoVideo();

            /* crea l'instanza */
            campoDecorato = new CVDCheck(campoVideoDaDecorare);

            /* regola la posizione */
            campoDecorato.setPos(CVDecoratore.Pos.DESTRA);

            /* check selezionato inizialmente */
            campoDecorato.setSelezionatoIniziale(selezionato);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return campoDecorato;
    }


    /**
     * Crea un campo decoratore check.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea un campo check </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     *
     * @see it.algos.base.layout.Layout
     * @see it.algos.base.campo.video.CVBase
     * @see CVDecoratore
     * @see CVDLabel
     * @since 11-5-04
     */
    public static CVDCheck check(Campo unCampo) {
        /* valore di ritorno */
        return check(unCampo, false);
    }


    /**
     * Crea un campo con un bottone a destra.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea un campo check </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     *
     * @see it.algos.base.layout.Layout
     * @see it.algos.base.campo.video.CVBase
     * @see CVDecoratore
     * @see CVDLabel
     * @since 11-5-04
     */
    public static CVDBottone bottone(Campo unCampo) {
        /* variabili e costanti locali di lavoro */
        CVDBottone campoDecorato = null;
        CampoVideo campoVideoDaDecorare;

        try {    // prova ad eseguire il codice
            /* recupera dal campo 'parente', il campo video da decorare */
            campoVideoDaDecorare = unCampo.getCampoVideo();

            /* crea l'instanza */
            campoDecorato = new CVDBottone(campoVideoDaDecorare);

            /* regola la posizione */
            campoDecorato.setPos(CVDecoratore.Pos.DESTRA);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return campoDecorato;
    }


    /**
     * Crea un campo con un bottone a destra.
     * <p/>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     * @param bottone da utilizzare
     *
     * @return il campo video creato
     */
    public static CVDBottone bottone(Campo unCampo, JButton bottone) {
        /* variabili e costanti locali di lavoro */
        CVDBottone campoDecorato = null;
        CampoVideo campoVideoDaDecorare;

        try {    // prova ad eseguire il codice
            /* recupera dal campo 'parente', il campo video da decorare */
            campoVideoDaDecorare = unCampo.getCampoVideo();

            /* crea l'instanza */
            campoDecorato = new CVDBottone(campoVideoDaDecorare, bottone);

            /* regola la posizione */
            campoDecorato.setPos(CVDecoratore.Pos.DESTRA);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return campoDecorato;
    }


    /**
     * Crea un campo con un bottone/azione a destra.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea un campo check </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     *
     * @see it.algos.base.layout.Layout
     * @see it.algos.base.campo.video.CVBase
     * @see CVDecoratore
     * @see CVDLabel
     * @since 11-5-04
     */
    public static CVDAzione azione(Campo unCampo, Azione azione) {
        /* variabili e costanti locali di lavoro */
        CVDAzione campoDecorato = null;
        CampoVideo campoVideoDaDecorare;

        try {    // prova ad eseguire il codice
            /* recupera dal campo 'parente', il campo video da decorare */
            campoVideoDaDecorare = unCampo.getCampoVideo();

            /* crea l'instanza */
            campoDecorato = new CVDAzione(campoVideoDaDecorare, azione);

            /* regola la posizione */
            campoDecorato.setPos(CVDecoratore.Pos.DESTRA);

            /* regola il bottone */
            campoDecorato.getBottone().setText("");
            Icon icona = azione.getIcona("frecciaDx16");
            campoDecorato.getBottone().setIcon(icona);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return campoDecorato;
    }


    /**
     * Crea un campo con un bottone a destra.
     * <p/>
     * Il bottone non ha testo <br>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea un campo check </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     * @param modulo di riferimento
     * @param nomeIcona icona del bottone
     *
     * @see it.algos.base.layout.Layout
     * @see it.algos.base.campo.video.CVBase
     * @see CVDecoratore
     * @see CVDLabel
     * @since 11-5-04
     */
    public static CVDBottone bottone(Campo unCampo, Modulo modulo, String nomeIcona) {
        /* variabili e costanti locali di lavoro */
        CVDBottone campoDecorato = null;
        ImageIcon icona;

        try {    // prova ad eseguire il codice
            /* Crea il decoratore */
            campoDecorato = VideoFactory.bottone(unCampo);

            /* crea l'icona */
            icona = Lib.Risorse.getIcona(modulo, nomeIcona);

            /* regola il bottone */
            campoDecorato.getBottone().setText("");
            campoDecorato.getBottone().setIcon(icona);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return campoDecorato;
    }


    /**
     * Crea un campo con un bottone a destra.
     * <p/>
     * Il bottone non ha testo <br>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea un campo check </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     * @param nomeIcona icona dalle icone base
     * @param toolTip testo di aiuto
     *
     * @see it.algos.base.layout.Layout
     * @see it.algos.base.campo.video.CVBase
     * @see CVDecoratore
     * @see CVDLabel
     * @since 11-5-04
     */
    public static CVDBottone bottone(Campo unCampo, String nomeIcona, String toolTip) {
        /* variabili e costanti locali di lavoro */
        CVDBottone campoDecorato = null;
        ImageIcon icona;

        try {    // prova ad eseguire il codice
            /* Crea il decoratore */
            campoDecorato = VideoFactory.bottone(unCampo);

            /* crea l'icona */
            icona = Lib.Risorse.getIconaBase(nomeIcona);

            /* regola il bottone */
            campoDecorato.getBottone().setText("");
            campoDecorato.getBottone().setToolTipText(toolTip);
            campoDecorato.getBottone().setIcon(icona);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return campoDecorato;
    }


    /**
     * Crea un campo con un bottone a destra.
     * <p/>
     * Il bottone non ha testo <br>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea un campo check </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     * @param nomeIcona icona dalle icone base
     *
     * @see it.algos.base.layout.Layout
     * @see it.algos.base.campo.video.CVBase
     * @see CVDecoratore
     * @see CVDLabel
     * @since 11-5-04
     */
    public static CVDBottone bottone(Campo unCampo, String nomeIcona) {
        /* invoca il metodo delegato della classe */
        return VideoFactory.bottone(unCampo, nomeIcona, "");
    }


    /**
     * Crea un campo con un bottone a destra.
     * <p/>
     * Il bottone non ha testo <br>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea un campo check </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     * @param nomeIcona icona del bottone
     * @param tooltip testo di aiuto
     *
     * @see it.algos.base.layout.Layout
     * @see it.algos.base.campo.video.CVBase
     * @see CVDecoratore
     * @see CVDLabel
     * @since 11-5-04
     */
    public static CVDBottone bottone(Campo unCampo,
                                     Modulo modulo,
                                     String nomeIcona,
                                     String tooltip) {
        /* variabili e costanti locali di lavoro */
        CVDBottone campoDecorato = null;

        try {    // prova ad eseguire il codice
            /* Crea il decoratore */
            campoDecorato = VideoFactory.bottone(unCampo, modulo, nomeIcona);

            /* regola il bottone */
            campoDecorato.getBottone().setToolTipText(tooltip);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return campoDecorato;
    }


    /**
     * Crea un campo decoratore con un bottone a due stati.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea un campo check </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     * @param icona0 icona per lo stato iniziale (0)
     * @param icona1 icona per lo stato opposto (1)
     *
     * @see it.algos.base.layout.Layout
     * @see it.algos.base.campo.video.CVBase
     * @see CVDecoratore
     * @see CVDLabel
     * @since 11-5-04
     */
    public static CVDBottone2stati bottone2stati(Campo unCampo, Icon icona0, Icon icona1) {
        /* variabili e costanti locali di lavoro */
        CVDBottone2stati campoDecorato = null;
        CampoVideo campoVideoDaDecorare;

        try {    // prova ad eseguire il codice

            /* recupera dal campo 'parente', il campo video da decorare */
            campoVideoDaDecorare = unCampo.getCampoVideo();

            /* crea l'instanza */
            campoDecorato = new CVDBottone2stati(campoVideoDaDecorare, icona0, icona1);


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return campoDecorato;
    }


    /**
     * Crea un campo decoratore con un lucchetto a due stati.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea un campo check </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     *
     * @see it.algos.base.layout.Layout
     * @see it.algos.base.campo.video.CVBase
     * @see CVDecoratore
     * @see CVDLabel
     * @since 11-5-04
     */
    public static CVDLucchetto lucchetto(Campo unCampo, boolean chiuso) {
        /* variabili e costanti locali di lavoro */
        CVDLucchetto campoDecorato = null;

        try {    // prova ad eseguire il codice

            /* crea l'instanza */
            campoDecorato = new CVDLucchetto(unCampo.getCampoVideo(), chiuso);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return campoDecorato;
    }


    /**
     * Crea un campo decoratore con un lucchetto a due stati.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un un wrapper intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * <li> crea un campo check </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     *
     * @see it.algos.base.layout.Layout
     * @see it.algos.base.campo.video.CVBase
     * @see CVDecoratore
     * @see CVDLabel
     * @since 11-5-04
     */
    public static CVDBottone2stati lucchetto(Campo unCampo) {
        /* valore di ritorno */
        return lucchetto(unCampo, false);
    }


    /**
     * Crea un campo decoratore obbligatorio.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un bordo intorno ad un preesistente campo video
     * di cui estende le funzionalita al runtime </li>
     * </ul>
     *
     * @param unCampo campo 'parente' del campo video da decorare
     *
     * @see it.algos.base.layout.Layout
     * @see it.algos.base.campo.video.CVBase
     * @see CVDecoratore
     * @see CVDLabel
     * @since 11-5-04
     */
    public static CVDObbligatorio obbligatorio(Campo unCampo) {
        /* variabili e costanti locali di lavoro */
        CVDObbligatorio campoDecorato = null;
        CampoVideo campoVideoDaDecorare;

        try {    // prova ad eseguire il codice
            /* recupera dal campo 'parente', il campo video da decorare */
            campoVideoDaDecorare = unCampo.getCampoVideo();

            /* crea l'instanza */
            campoDecorato = new CVDObbligatorio(campoVideoDaDecorare);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return campoDecorato;
    }


    /**
     * Crea un campo congelato per mostrare un combobox oppure un testo.
     * <p/>
     * Questo metodo factory: <ul>
     * </ul>
     *
     * @param campo campo video da decorare
     * @param nomeCampo testo associato
     *
     * @return decoratore creato
     */
    public static CVDCongelato congelato(CampoVideo campo, String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        CVDCongelato campoDecorato = null;

        try {    // prova ad eseguire il codice

            /* crea l'instanza */
            campoDecorato = new CVDCongelato(campo);

            /* regola i parametri */
            campoDecorato.setNomeCampoAssociato(nomeCampo);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return campoDecorato;
    }


    /**
     * Crea un campo preferenza per mostrare un componente disabilitato con un valore fisso.
     * <p/>
     * Questo metodo factory: <ul>
     * </ul>
     *
     * @param campo campo video da decorare
     * @param valore da regolare
     * @param notaUte nota a margine
     *
     * @return decoratore creato
     */
    public static CVDPreferenza preferenza(CampoVideo campo, Object valore, String notaUte) {
        /* variabili e costanti locali di lavoro */
        CVDPreferenza campoDecorato = null;

        try {    // prova ad eseguire il codice

            /* crea l'instanza */
            campoDecorato = new CVDPreferenza(campo);

            /* regola i parametri */
            campoDecorato.setStandard(valore);
            campoDecorato.setNotaUte(notaUte);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return campoDecorato;
    }


    /**
     * Crea un campo preferenza per mostrare un componente disabilitato con un valore fisso.
     * <p/>
     * Questo metodo factory: <ul>
     * </ul>
     *
     * @param campo campo video da decorare
     * @param valore da regolare
     *
     * @return decoratore creato
     */
    public static CVDPreferenza preferenza(CampoVideo campo, Object valore) {
        /* variabili e costanti locali di lavoro */
        CVDPreferenza campoDecorato = null;

        try {    // prova ad eseguire il codice

            /* crea l'instanza */
            campoDecorato = new CVDPreferenza(campo);

            /* regola i parametri */
            campoDecorato.setStandard(valore);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return campoDecorato;
    }


    /**
     * Elimina un decoratore.
     * <p/>
     * Se non esiste non fa niente
     *
     * @param campo video da cui eliminare il decoratore
     * @param classe del decoratore da eliminare
     */
    private static void eliminaDecoratore(CampoVideo campo, Class classe) {
        /* variabili e costanti locali di lavoro */
        Campo campoParente;
        CampoVideo campoVideoPrimo;
        CVDecoratore decPrec = null;
        CampoVideo campoVideoCorr;
        CampoVideo campoVideoSucc;
        CVDecoratore decoratore;

        try {    // prova ad eseguire il codice
            /* recupera il campo parente */
            campoParente = campo.getCampoParente();

            /* recupera il primo campo video della catena */
            campoVideoPrimo = campoParente.getCampoVideo();
            campoVideoCorr = campoVideoPrimo;

            /* ripete finche' esistono decoratori */
            while (campoVideoCorr instanceof CVDecoratore) {
                decoratore = (CVDecoratore)campoVideoCorr;

                if (decoratore.getCampoVideoDecorato() != decPrec) {
                    campoVideoSucc = decoratore.getCampoVideoDecorato();
                } else {
                    campoVideoSucc = campoVideoCorr.getCampoParente().getCampoVideoNonDecorato();
                }// fine del blocco if-else

                if (decoratore.getClass().equals(classe)) {
                    /* elimina un componente già inserito a video */
                    decoratore.remove();

                    if (campoVideoCorr == campoVideoPrimo) {
                        campoParente.setCampoVideoAssoluto(campoVideoSucc);
                    } else {
                        if (decPrec != null) {
                            decPrec.setCampoVideoDecorato(campoVideoSucc);
                        }// fine del blocco if
                    }// fine del blocco if-else
//                    break;
                } else {
                    decPrec = decoratore;
                }// fine del blocco if-else
                campoVideoCorr = campoVideoSucc;
            } /* fine del blocco while */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Elimina l'etichetta associato al campo video.
     * <p/>
     * Se non esiste non fa niente
     *
     * @param campo video da cui eliminare l'etichetta
     */
    public static void eliminaEtichetta(CampoVideo campo) {
        VideoFactory.eliminaDecoratore(campo, CVDEtichetta.class);
    }


    /**
     * Elimina la legenda associato al campo video.
     * <p/>
     * Se non esiste non fa niente
     *
     * @param campo video da cui eliminare l'etichetta
     */
    public static void eliminaLegenda(CampoVideo campo) {
        VideoFactory.eliminaDecoratore(campo, CVDLegenda.class);
    }


    /**
     * Elimina il decoratore obbligatorio associato al campo video.
     * <p/>
     * Se non esiste non fa niente
     *
     * @param campo video da cui eliminare l'etichetta
     */
    public static void eliminaObligatorio(CampoVideo campo) {
        VideoFactory.eliminaDecoratore(campo, CVDObbligatorio.class);
    }


    /**
     * Trova un decoratore.
     * <p/>
     * Se non esiste non restituisce niente
     *
     * @param campo video in cui ricercare
     * @param classe del decoratore
     *
     * @return decoratore trovato
     */
    public static CVDecoratore getDecoratore(CampoVideo campo, Class classe) {
        /* variabili e costanti locali di lavoro */
        CVDecoratore decoratoreOut = null;
        CVDecoratore decoratore = null;
        Campo campoParente;
        CampoVideo campoVideoPrimo;
        CVDecoratore decPrec = null;
        CampoVideo campoVideoCorr;
        CampoVideo campoVideoSucc;

        try {    // prova ad eseguire il codice
            /* recupera il campo parente */
            campoParente = campo.getCampoParente();

            /* recupera il primo campo video della catena */
            campoVideoPrimo = campoParente.getCampoVideo();
            campoVideoCorr = campoVideoPrimo;

            /* ripete finche' esistono decoratori */
            while (campoVideoCorr instanceof CVDecoratore) {
                decoratore = (CVDecoratore)campoVideoCorr;

                if (decoratore.getCampoVideoDecorato() != decPrec) {
                    campoVideoSucc = decoratore.getCampoVideoDecorato();
                } else {
                    campoVideoSucc = campoVideoCorr.getCampoParente().getCampoVideoNonDecorato();
                }// fine del blocco if-else

                if (decoratore.getClass().equals(classe)) {
                    decoratoreOut = decoratore;
                    break;
                } else {
                    decPrec = decoratore;
                }// fine del blocco if-else
                campoVideoCorr = campoVideoSucc;
            } /* fine del blocco while */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return decoratoreOut;
    }

//    /**
//     * Recupera il testo della eventuale etichetta.
//     * <p/>
//     *
//     * @param campo video da cui recuperare il testo dell'etichetta
//     *
//     * @return il testo dell'etichetta, null se non ha etichetta
//     */
//    public static String getTestoEtichetta(CampoVideo campo) {
//        /* variabili e costanti locali di lavoro */
//        String testo = null;
//        Campo campoParente;
//        CampoVideo campoVideo;
//        CVDecoratore decoratore;
//        CVDEtichetta etichetta;
//
//        try {    // prova ad eseguire il codice
//
//            /* recupera il campo parente */
//            campoParente = campo.getCampoParente();
//
//            /* recupera il primo campo video della catena */
//            campoVideo = campoParente.getCampoVideo();
//
//            /* ripete finche' esistono decoratori */
//            while (campoVideo instanceof CVDecoratore) {
//                decoratore = (CVDecoratore) campoVideo;
//                if (decoratore instanceof CVDEtichetta) {
//                    etichetta = (CVDEtichetta) decoratore;
//                    testo = etichetta.getTestoEtichetta();
//                    break;
//                } else {
//                    campoVideo = decoratore.getCampoVideoDecorato();
//                }// fine del blocco if-else
//            } /* fine del blocco while */
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            Errore.crea(unErrore);
//        } /* fine del blocco try-catch */
//
//        /* valore di ritorno */
//        return testo;
//    }


    /**
     * Elimina il decoratore associato al campo video.
     * <p/>
     * Se ne esiste piu' di uno, elimina solo il primo
     * Se non ne esistono non fa niente
     *
     * @param campoParente base da cui eliminare il decoratore video
     */
    public static boolean eliminaDecoratore(Campo campoParente) {
        /* variabili e costanti locali di lavoro */
        boolean decoratoreEliminato = false;
        CampoVideo campo;
        CVDecoratore campoDecoratore;
        CampoVideo campoVideo;

        try {    // prova ad eseguire il codice
            /* recupera il campo video */
            campo = campoParente.getCampoVideo();

            /* controllo che il video sia un decoratore */
            if (campo instanceof CVDecoratore) {
                /* effettuo il casting per accedere ai metodi specifici */
                campoDecoratore = (CVDecoratore)campo;

                campoVideo = campoDecoratore.getCampoVideoDecorato();

                campoDecoratore.eliminaPannelloComponenti();

                /* controllo di congruita' */
                if (campoVideo != null) {
                    campoParente.setCampoVideoAssoluto(campoVideo);
                    decoratoreEliminato = true;
                } /* fine del blocco if */
            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return decoratoreEliminato;
    }


    /**
     * Elimina tutti i decoratori associati al campo video.
     * <p/>
     * Se non ne esistono non fa niente
     *
     * @param campo da cui eliminare i decoratori video
     */
    public static void eliminaTuttiDecoratori(Campo campo) {
        try {    // prova ad eseguire il codice
            /* ripete finche' esistono decoratori */
            while (eliminaDecoratore(campo)) {
            } /* fine del blocco while */
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


}// fine della classe