/**
 * Title:     SchedaIndirizzo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25-ott-2007
 */
package it.algos.albergo.clientealbergo.indirizzoalbergo;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.gestione.indirizzo.Indirizzo;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA CLASSE .
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 3-mag-2004 ore 13.19.37
 */
public final class SchedaPopIndirizzo extends IndirizzoAlbergoScheda {


    /**
     * Costruttore completo.
     *
     * @param unModulo di riferimento per la scheda
     */
    public SchedaPopIndirizzo(Modulo unModulo) {
        /* rimanda al costruttore della superclasse */
        super(unModulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice
            this.setNomeChiave("popIndirizzo");
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Crea le pagine della scheda.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaPagine() {
        Pagina pag;

        try { // prova ad eseguire il codice
            pag = super.addPagina("La mia pagina 1");
            pag.add(Indirizzo.Cam.indirizzo);
            pag.add(Indirizzo.Cam.indirizzo2);
            pag.add(Indirizzo.Cam.citta);
            pag.add(Indirizzo.Cam.cap);
            pag.add(Indirizzo.Cam.note);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    } /* fine del metodo */


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Recupera il db <br>
     * Chiede al db i dati del record <br>
     * Regola i dati della scheda <br>
     */
    public void avvia(int codice) {
        super.avvia(codice);
    }


    /**
     * Sincronizzazione della scheda.
     * <p/>
     * Chiamato dalla sincronizzazione del portale
     * che contiene la scheda. <br>
     * Sincronizza la status bar <br>
     */
    public void sincronizza() {
        super.sincronizza();
    }


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Sovrascritto dalle sottoclassi
     */
    protected void eventoMemoriaModificata(Campo campo) {
        int a = 87;
    }
}// fine della classe
