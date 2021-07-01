package it.algos.gestione.indirizzo.tabelle.citta;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.scheda.SchedaDefault;
import it.algos.gestione.indirizzo.tabelle.provincia.Provincia;
import it.algos.gestione.indirizzo.tabelle.provincia.ProvinciaModulo;

/**
 * Presentazione grafica di un singolo record di Rubrica.
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
 * @version 1.0    / 3-lug-2007 ore 17.53.27
 */
public class CittaScheda extends SchedaDefault implements Citta {

    /**
     * Costruttore completo con parametri.
     *
     * @param unModulo di riferimento per la scheda
     */
    public CittaScheda(Modulo unModulo) {
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
     * Crea le pagine della scheda.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.scheda.SchedaBase#add
     */
    @Override protected void creaPagine() {
        /* variabili e costanti locali di lavoro */
        Pagina pag;
        Pannello pan;
        Pannello pan2;

        try {    // prova ad eseguire il codice
            /* crea una pagina vuota col titolo */
            pag = super.addPagina("generale");

            /* aggiunge i campi */
            pag.add(Citta.Cam.citta);

            pan = PannelloFactory.orizzontale(this);
            pan.creaBordo("");
            pan.add(Citta.Cam.cap);
            pan.add(Citta.Cam.prefisso);
            pan.add(Citta.Cam.codice);
            pag.add(pan);

            pan = PannelloFactory.verticale(this);
            pan.creaBordo("");
            pan2 = PannelloFactory.orizzontale(this);
            pan2.setGapPreferito(50);
            pan2.add(Citta.Cam.linkNazione);
            pan2.add(Citta.Cam.verificato);
            pan.add(pan2);
            pan.add(Citta.Cam.linkProvincia);
            pag.add(pan);

            pan = PannelloFactory.verticale(this);
            pan.creaBordo("");
            pan2 = PannelloFactory.orizzontale(this);
            pan.add(Citta.Cam.status);
            pan2.add(Citta.Cam.altitudine);
            pan2.add(Citta.Cam.abitanti);
            pan2.add(Citta.Cam.coordinate);
            pan.add(pan2);
            pag.add(pan);

            /* note */
            pag.add(Citta.Cam.note);

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo creaPagine


    protected void eventoMemoriaModificata(Campo campo) {
        /* variabili e costanti locali di lavoro */
        Modulo modProvincia;
        int codProvincia;
        int codNazione;
        Connessione conn;
        Campo unCampo;
        Filtro filtro = null;

        try { // prova ad eseguire il codice

            /* se ho inserito una nazione valida, costruisce un filtro di province solo per quella nazione */
            if (this.isCampo(campo, Cam.linkNazione)) {
                codNazione = this.getInt(Cam.linkNazione.get());
                if (codNazione == 0) {

                } else {
                    unCampo = this.getCampo(Cam.linkProvincia);
                    filtro = FiltroFactory.crea(Provincia.Cam.linkNazione.get(), codNazione);
                    unCampo.setFiltroCorrente(filtro);
                }// fine del blocco if-else


            }// fine del blocco if

            /* se ho inserito una provincia valida, e la provincia
             * è associata a una nazione, assegna la nazione */
            if (this.isCampo(campo, Cam.linkProvincia)) {
                codProvincia = this.getInt(Cam.linkProvincia.get());
                if (codProvincia > 0) {
                    modProvincia = ProvinciaModulo.get();
                    conn = this.getConnessione();
                    codNazione = modProvincia.query().valoreInt(Provincia.Cam.linkNazione.get(),
                            codProvincia,
                            conn);
                    this.setValore(Cam.linkNazione.get(), codNazione);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sincronizzazione della scheda.
     * <p/>
     * Chiamato dalla sincronizzazione del portale
     * che contiene la scheda. <br>
     * Sincronizza la status bar <br>
     */
    @Override
    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        int codProvincia;
        Campo unCampo;

        try { // prova ad eseguire il codice

            codProvincia = this.getInt(Cam.linkProvincia.get());
            unCampo = this.getCampo(Cam.linkNazione);

            if (codProvincia == 0) {
                if (this.getCodice() != 0) {
                    unCampo.setModificabile(true);
                }// fine del blocco if
            } else {
                unCampo.setModificabile(false);
            }// fine del blocco if-else

            super.sincronizza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }
}// fine della classe