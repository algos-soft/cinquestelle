/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      29-mar-2005
 */
package it.algos.albergo.ristorante.lingua;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.scheda.SchedaBase;

import java.util.ArrayList;

/**
 * Presentazione grafica di un singolo record di Lingua.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea le pagine del <code>Libro</code> che vengono visualizzate nel
 * PortaleScheda del Navigatore </li>
 * <li> Ogni pagina viene creata con un set di campi o aggiungendo i singoli campi </li>
 * <li> I campi vengono posizionati in automatico oppure singolarmente </li>
 * <li> Se uno stesso campo viene posizionato su pi&ugrave; pagine, risulter&agrave;
 * visibile solo nell'ultima pagina in cui viene posizionato </li>
 * <li> Se il <code>Modello>/code> prevede il campo note, crea la pagina note </li>
 * <li> Se il flag programma &egrave; attivo, crea la pagina programmatore </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 29-mar-2005 ore 12.51.06
 */
public final class LinguaScheda extends SchedaBase implements Lingua {

    /**
     * Costruttore completo senza parametri.
     */
    public LinguaScheda(Modulo modulo) {
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
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    /**
     * Crea le pagine.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.scheda.SchedaBase#addPagina
     */
    protected void creaPagine() {
        /* variabili e costanti locali di lavoro */
        Pagina pagina = null;
        ArrayList campi = null;
        Campo campo = null;

        /* crea una pagina vuota col titolo */
        pagina = super.addPagina("Lingua");
        try {    // prova ad eseguire il codice
            campi = Libreria.hashMapToArrayList(this.getCampi());
            for (int k = 0; k < campi.size(); k++) {
                campo = (Campo)campi.get(k);
                if (campo.getCampoScheda().isPresenteInScheda()) {
                    pagina.add(campo);
                }// fine del blocco if
            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

}// fine della classe
