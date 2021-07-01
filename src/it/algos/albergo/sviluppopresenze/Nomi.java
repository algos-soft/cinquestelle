package it.algos.albergo.sviluppopresenze;

/**
 * Nomi dei campi
 */
enum Nomi {
    chiave(),
    sigla(),
    descrizione(),
    pres_ad(),
    pres_ba(),
    pres_tot(),
    percent_pres(),
    valore,
    percent_val();


    /**
     * Ritorna il nome interno del campo.
     * <p/>
     * @return il nome interno
     */
    public String get() {
        return toString();
    }
}
