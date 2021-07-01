package it.algos.gestione.tabelle.valuta;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pref.Pref;
import it.algos.base.scheda.SchedaBase;

/**
 * Presentazione grafica di un singolo record di Valuta.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea le pagine del <code>Libro</code> che vengono visualizzate nel
 * PortaleScheda del Navigatore </li>
 * <li> Ogni pagina viene creata con un set di campi o aggiungendo i singoli campi </li>
 * <li> I campi vengono posizionati in automatico oppure singolarmente </li>
 * <li> Se uno stesso campo viene posizionato su più pagine, risulterà
 * visibile solo nell'ultima pagina in cui viene posizionato </li>
 * <li> Se il <code>Modello>/code> prevede il campo note, crea la pagina note </li>
 * <li> Se il flag programma è attivo, crea la pagina programmatore </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 23-apr-2007 ore 20.30.12
 */
public final class ValutaScheda extends SchedaBase {

    /**
     * Costruttore completo con parametri.
     *
     * @param unModulo di riferimento per la scheda
     */
    public ValutaScheda(Modulo unModulo) {
        /* rimanda al costruttore della superclasse */
        super(unModulo);

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
     * @see it.algos.base.scheda.SchedaBase#add
     */
    protected void creaPagine() {
        /* variabili e costanti locali di lavoro */
        Pagina pagina;
        Pannello panSopra;
        Pannello panMedio;

        try {    // prova ad eseguire il codice
            /* crea una pagina vuota col voce */
            pagina = super.addPagina("generale");

            /* valuta e iso */
            panSopra = PannelloFactory.orizzontale(this);
            panSopra.add(Valuta.Cam.valuta);
            panSopra.add(Valuta.Cam.codiceIso);
            pagina.add(panSopra);

            /* cambio e data */
            panMedio = PannelloFactory.orizzontale(this);
            panMedio.add(Valuta.Cam.cambio);
            panMedio.add(Valuta.Cam.conCambio);
            panMedio.add(Valuta.Cam.dataCambio);
            pagina.add(panMedio);

            /* nazioni */
            if (Pref.Gen.livello.comboInt() > Pref.Livello.basso.ordinal() + 1) {
                pagina.add(Valuta.Cam.subNazioni);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }
}// fine della classe
