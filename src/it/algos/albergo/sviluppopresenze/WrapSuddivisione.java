/**
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      22-feb-2009
 */
package it.algos.albergo.sviluppopresenze;

/**
 * Wrapper contenente i dati chiave di una suddivisione
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 13-feb-2009 ore 9.14.26
 */
class WrapSuddivisione  {

    public int chiave = 0;

    public String sigla = "";

    public String descrizione = "";


    /**
     * Costruttore completo
     *
     * @param chiave la chiave di suddivisione
     * @param sigla la sigla della suddivisione
     * @param descrizione la descrizione della suddivisione
     */
    public WrapSuddivisione(int chiave, String sigla, String descrizione) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regola le variabili di istanza coi parametri */
        this.setChiave(chiave);
        this.setSigla(sigla);
        this.setDescrizione(descrizione);

    } /* fine del metodo costruttore completo */


    public int getChiave() {
        return chiave;
    }


    private void setChiave(int chiave) {
        this.chiave = chiave;
    }


    public String getSigla() {
        return sigla;
    }


    private void setSigla(String sigla) {
        this.sigla = sigla;
    }


    public String getDescrizione() {
        return descrizione;
    }


    private void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}// fine della classe