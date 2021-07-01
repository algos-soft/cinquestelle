package it.algos.albergo.cittaalbergo;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.gestione.indirizzo.tabelle.citta.Citta;
import it.algos.gestione.indirizzo.tabelle.citta.CittaScheda;

public class CittaAlbergoScheda extends CittaScheda {

	public CittaAlbergoScheda(Modulo unModulo) {
		super(unModulo);
	}
	
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
            pan.add(CittaAlbergo.Cam.codicePS);
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


}
