/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      16-mag-2005
 */
package it.algos.base.pannello;

import it.algos.base.errore.Errore;
import it.algos.base.interfaccia.ContenitoreCampi;
import it.algos.base.layout.Layout;
import it.algos.base.layout.LayoutFactory;

/**
 * Factory per la creazione di oggetti del package pannello.
 * </p>
 * Questa classe astratta factory: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Factory Method</b> </li>
 * <li> Fornisce metodi <code>statici</code> per la  creazione degli oggetti di questo
 * package </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 16-mag-2005 ore 11.32.32
 */
public abstract class PannelloFactory implements Pannello {

    /**
     * Codifica dell'orientamento verticale
     */
    public static final int VERTICALE = Layout.ORIENTAMENTO_VERTICALE;

    /**
     * Codifica dell'orientamento orizzontale
     */
    public static final int ORIZZONTALE = Layout.ORIENTAMENTO_ORIZZONTALE;


    /**
     * Crea un pannello standard.
     * <p/>
     *
     * @param cont contenitore (scheda o dialogo) in cui è inserito il pannello
     * @param codOrientamento codifica per l'orientamento
     * @param oggetto oggetti da disporre in un pannello; puo' essere:
     * Component - un singolo componente (Campo od altro)
     * String - un nome set, un nome di campo singolo, una lista di nomi
     * ArrayList - di nomi, di oggetti Campo, di componenti <br>
     * @param lar larghezza del pannello
     * @param alt altezza del pannello
     *
     * @return pannello appena creato
     */
    private static Pannello crea(ContenitoreCampi cont,
                                 int codOrientamento,
                                 Object oggetto,
                                 int lar,
                                 int alt) {
        /* variabili e costanti locali di lavoro */
        Pannello pannello = null;

        try {    // prova ad eseguire il codice

            /* crea l'instanza */
            pannello = new PannelloFlusso(cont);

            /* regola alcuni parametri di default */
            pannello.add(oggetto);

            /* crea il layout del pannello */
            pannello.getPanFisso().setLayout(LayoutFactory.crea(pannello, codOrientamento));

            /* regola solo per valori significativi */
            if ((lar != 0) && (alt != 0)) {
                //@todo rimettere
//                pannello.setDimensioneEsterna(new Dimension(lar, alt));
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return pannello;
    }


    /**
     * Crea un pannello verticale.
     * <p/>
     *
     * @param cont contenitore (scheda o dialogo) in cui � inserito il pannello
     * @param oggetto oggetti da disporre in un pannello; puo' essere:
     * Component - un singolo componente (Campo od altro)
     * String - un nome set, un nome di campo singolo, una lista di nomi
     * ArrayList - di nomi, di oggetti Campo, di componenti <br>
     * @param lar larghezza del pannello
     * @param alt altezza del pannello
     *
     * @return pannello appena creato
     */
    public static Pannello verticale(ContenitoreCampi cont, Object oggetto, int lar, int alt) {
        /* variabili e costanti locali di lavoro */
        Pannello pannello = null;

        try {    // prova ad eseguire il codice

            /* nuova istanza */
            pannello = crea(cont, VERTICALE, oggetto, lar, alt);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return pannello;
    }


    /**
     * Crea un pannello verticale.
     * <p/>
     *
     * @param cont contenitore (scheda o dialogo) in cui � inserito il pannello
     * @param oggetto oggetti da disporre in un pannello; puo' essere:
     * Component - un singolo componente (Campo od altro)
     * String - un nome set, un nome di campo singolo, una lista di nomi
     * ArrayList - di nomi, di oggetti Campo, di componenti <br>
     *
     * @return pannello appena creato
     */
    public static Pannello verticale(ContenitoreCampi cont, Object oggetto) {
        /* invoca il metodo delegato della classe */
        return verticale(cont, oggetto, 0, 0);
    }


    /**
     * Crea un pannello verticale.
     * <p/>
     *
     * @param cont contenitore (scheda o dialogo) in cui è inserito il pannello
     *
     * @return pannello appena creato
     */
    public static Pannello verticale(ContenitoreCampi cont) {
        /* invoca il metodo delegato della classe */
        return verticale(cont, null, 0, 0);
    }


    /**
     * Crea un pannello verticale.
     * <p/>
     *
     * @param cont in cui è inserito il pannello
     * @param lar larghezza del pannello
     * @param alt altezza del pannello
     *
     * @return pannello appena creato
     */
    public static Pannello verticale(ContenitoreCampi cont, int lar, int alt) {
        /* invoca il metodo delegato della classe */
        return verticale(cont, null, lar, alt);
    }


    /**
     * Crea un pannello verticale.
     * <p/>
     *
     * @param cont contenitore (scheda o dialogo) in cui è inserito il pannello
     * @param oggetto oggetti da disporre in un pannello; puo' essere:
     * Component - un singolo componente (Campo od altro)
     * String - un nome set, un nome di campo singolo, una lista di nomi
     * ArrayList - di nomi, di oggetti Campo, di componenti <br>
     * @param lar larghezza del pannello
     * @param alt altezza del pannello
     * @param cont contenitore (scheda o dialogo) in cui è inserito il pannello
     * @param oggetto oggetti da disporre in un pannello; puo' essere:
     * Component - un singolo componente (Campo od altro)
     * String - un nome set, un nome di campo singolo, una lista di nomi
     * ArrayList - di nomi, di oggetti Campo, di componenti <br>
     * @param lar larghezza del pannello
     * @param alt altezza del pannello
     *
     * @return pannello appena creato
     */
//    public static Pannello verticale(ContenitoreCampi cont, Object oggetto, int lar, int alt) {
//        /* invoca il metodo delegato della classe */
//        return verticale(cont, TIPO_LAYOUT_DEFAULT, oggetto, lar, alt);
//    }

//    /**
//     * Crea un pannello verticale.
//     * <p/>
//     *
//    * @param cont contenitore (scheda o dialogo) in cui è inserito il pannello
//     * @param lar     larghezza del pannello
//     * @param alt     altezza del pannello
//     * @param scheda  in cui � inserito il pannello
//     * @param oggetto oggetti da disporre in un pannello; puo' essere:
//     *                Component - un singolo componente (Campo od altro)
//     *                String - un nome set, un nome di campo singolo, una lista di nomi
//     *                ArrayList - di nomi, di oggetti Campo, di componenti <br>
//     * @param lar     larghezza del pannello
//     * @param alt     altezza del pannello
//     * @param scheda  in cui � inserito il pannello
//     * @param oggetto oggetti da disporre in un pannello; puo' essere:
//     *                Component - un singolo componente (Campo od altro)
//     *                String - un nome set, un nome di campo singolo, una lista di nomi
//     *                ArrayList - di nomi, di oggetti Campo, di componenti <br>
//     * @param lar     larghezza del pannello
//     * @param alt     altezza del pannello
//     *
//     * @return pannello appena creato
//     */
//    public static Pannello verticale(ContenitoreCampi cont, int lar, int alt) {
//        /* invoca il metodo delegato della classe */
//        return verticale(cont, TIPO_LAYOUT_DEFAULT, null, lar, alt);
//    }

    /**
     * Crea un pannello orizzontale.
     * <p/>
     *
     * @param cont contenitore (scheda o dialogo) in cui è inserito il pannello
     * @param oggetto    oggetti da disporre in un pannello; puo' essere:
     *                   Component - un singolo componente (Campo od altro)
     *                   String - un nome set, un nome di campo singolo, una lista di nomi
     *                   ArrayList - di nomi, di oggetti Campo, di componenti <br>
     * @param lar        larghezza del pannello
     * @param alt        altezza del pannello
     *
     * @return pannello appena creato
     */
    public static Pannello orizzontale(ContenitoreCampi cont, Object oggetto, int lar, int alt) {
        /* variabili e costanti locali di lavoro */
        Pannello pannello = null;

        try {    // prova ad eseguire il codice

            /* nuova istanza */
            pannello = crea(cont, ORIZZONTALE, oggetto, lar, alt);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return pannello;
    }


    /**
     * Crea un pannello orizzontale.
     * <p/>
     *
     * @param cont contenitore (scheda o dialogo) in cui è inserito il pannello
     * @param oggetto oggetti da disporre in un pannello; puo' essere:
     * Component - un singolo componente (Campo od altro)
     * String - un nome set, un nome di campo singolo, una lista di nomi
     * ArrayList - di nomi, di oggetti Campo, di componenti <br>
     *
     * @return pannello appena creato
     */
    public static Pannello orizzontale(ContenitoreCampi cont, Object oggetto) {
        /* invoca il metodo delegato della classe */
        return orizzontale(cont, oggetto, 0, 0);
    }


    /**
     * Crea un pannello orizzontale.
     * <p/>
     *
     * @param cont contenitore (scheda o dialogo) in cui è inserito il pannello
     *
     * @return pannello appena creato
     */
    public static Pannello orizzontale(ContenitoreCampi cont) {
        /* invoca il metodo delegato della classe */
        return orizzontale(cont, null, 0, 0);
    }


    /**
     * Crea un pannello orizzontale.
     * <p/>
     *
     * @param cont contenitore (scheda o dialogo) in cui è inserito il pannello
     * @param lar larghezza del pannello
     * @param alt altezza del pannello
     *
     * @return pannello appena creato
     */
    public static Pannello orizzontale(ContenitoreCampi cont, int lar, int alt) {
        /* invoca il metodo delegato della classe */
        return orizzontale(cont, null, lar, alt);
    }


}// fine della classe
