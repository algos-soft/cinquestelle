/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      2-mar-2005
 */
package it.algos.albergo.ristorante.lingua;

import it.algos.albergo.ristorante.Ristorante;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.logica.LogicaBase;

/**
 * Repository di logiche specifiche del modulo Lingua.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 2-mar-2005 ore 23.33.25
 */
public class LinguaLogica extends LogicaBase {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param modulo il modulo proprietario di questa logica.
     */
    public LinguaLogica(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

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
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    /**
     * Ritorna il nome di un dato pasto in una data lingua.
     * <p/>
     *
     * @param codPasto il codice del pasto
     * @param codLingua il codice della lingua
     *
     * @return il nome del pasto
     *
     * @see it.algos.albergo.ristorante.Ristorante
     * @see Lingua
     */
    public String getNomePasto(int codPasto, int codLingua) {
        /* variabili e costanti locali di lavoro */
        String nomePasto = "";
        String nomeCampo = null;
        int pasto = 0;

        try {    // prova ad eseguire il codice

            /* adegua i codici alle posizioni nel database */
            pasto = codPasto - 1;

            /* determina il campo dal quale prendere il valore */
            switch (pasto) {
                case Ristorante.COLAZIONE:
                    nomeCampo = Lingua.CAMPO_COLAZIONE;
                    break;
                case Ristorante.PRANZO:
                    nomeCampo = Lingua.CAMPO_PRANZO;
                    break;
                case Ristorante.CENA:
                    nomeCampo = Lingua.CAMPO_CENA;
                    break;
                default: // caso non definito
                    throw new Exception("Codice pasto non riconosciuto");
            } // fine del blocco switch

            /* recupera il valore */
            nomePasto = this.getModulo().query().valoreStringa(nomeCampo, codLingua);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nomePasto;
    }


    /**
     * Ritorna il nome di un dato pasto nella lingua principale.
     * <p/>
     *
     * @param codPasto il codice del pasto
     *
     * @return il nome del pasto nella lingua principale
     *
     * @see it.algos.albergo.ristorante.Ristorante
     */
    public String getNomePasto(int codPasto) {
        return this.getNomePasto(codPasto, LinguaModulo.getCodLinguaPreferita());
    }


    /**
     * Ritorna la congiunzione nella lingua principale.
     * <p/>
     *
     * @return la congiunzione nella lingua principale
     */
    public String getCongiunzione() {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        Object valore = null;
        int codLinguaMain = 0;
        LinguaModulo moduloLingua = null;


        try {    // prova ad eseguire il codice

            moduloLingua = (LinguaModulo)this.getModulo();

            codLinguaMain = moduloLingua.getChiaveLinguaPrincipale();

            valore = moduloLingua.query().valoreCampo(Lingua.CAMPO_CONGIUNZIONE, codLinguaMain);

            stringa = Lib.Testo.getStringa(valore);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


}// fine della classe
