package it.algos.albergo.nazionealbergo;

import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.gestione.indirizzo.tabelle.nazione.Nazione;
import it.algos.gestione.indirizzo.tabelle.nazione.NazioneScheda;

public class NazioneAlbergoScheda extends NazioneScheda {

    public NazioneAlbergoScheda(Modulo unModulo) {
        super(unModulo);
    }
    

    protected void creaPagine() {

        Pagina pag = super.addPagina("generale");

        // nome mazione
        pag.add(Nazione.Cam.nazione);

        // codice PS
        pag.add(NazioneAlbergo.Cam.codicePS);

        // iso
        pag.add(Nazione.Cam.sigla2);

    }



}
