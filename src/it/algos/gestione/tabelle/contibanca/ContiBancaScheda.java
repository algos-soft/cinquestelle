/**
 * Title:     ContiBancaScheda
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      13-mar-2007
 */
package it.algos.gestione.tabelle.contibanca;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.scheda.SchedaBase;

/**
 * Presentazione grafica di un singolo record di Indirizzo.
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
 * @version 1.0 / 3-4-05
 */
public final class ContiBancaScheda extends SchedaBase implements ContiBanca {

    /**
     * Costruttore completo.
     * <p/>
     *
     * @param modulo di riferimento
     */
    public ContiBancaScheda(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

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
    }


    public void inizializza() {
        super.inizializza();
    }


    protected void inizializzaCampi() {
        super.inizializzaCampi();
    }


    protected void creaPagine() {
        /* variabili e costanti locali di lavoro */
        Pagina pagina;
        Pannello pan;

        try { // prova ad eseguire il codice
            /* crea una pagina vuota col voce */
            pagina = super.addPagina("Conto");

            pan = PannelloFactory.orizzontale(this);
            pan.add(Cam.sigla.get());
            pan.add(Cam.nomeBanca.get());
            pagina.add(pan);

            pan = PannelloFactory.orizzontale(this);
            pan.creaBordo("coordinate");
            pan.add(Cam.cin.get());
            pan.add(Cam.abi.get());
            pan.add(Cam.cab.get());
            pan.add(Cam.conto.get());
            pagina.add(pan);

            pan = PannelloFactory.orizzontale(this);
            pan.creaBordo("estero");
            pan.add(Cam.iban.get());
            pan.add(Cam.bic.get());
            pagina.add(pan);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */

} // fine della classe
