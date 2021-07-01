package it.algos.base.azione;

import it.algos.base.azione.adapter.AzAdapterAction;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;

/**
 * Superclasse delle azioni generiche di progetto.
 * </p>
 * Mantiene alcune variabili <br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 28-dic-2004 ore 16.13.23
 */
public abstract class AzioneLocale extends AzAdapterAction {

    /**
     * Modulo di riferimento
     */
    private Modulo modulo;

    /**
     * Menu di inserimento
     */
    private String posMenu;

    private boolean usaIconaModulo;

    protected static final String POS = "archivio/dati";


    /**
     * Costruttore con parametri.
     *
     * @param modulo di riferimento
     */
    public AzioneLocale(Modulo modulo) {
        /* rimanda al costruttore di questa classe */
        this(modulo, "");
    }// fine del metodo costruttore


    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento
     * @param posMenu di posizionamento
     */
    public AzioneLocale(Modulo modulo, String posMenu) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setModulo(modulo);
        this.setPosMenu(posMenu);
    }// fine del metodo costruttore completo


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo chiamato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @return vero se viene inizializzato adesso;
     *         falso se era gi&agrave; stato inizializzato
     */
    @Override
    public boolean inizializza() {
        /* variabili e costanti locali di lavoro */
        boolean inizializzato = false;
        String iconaPiccola;
        String iconaMedia;
        String iconaGrande;
        Modulo mod;

        try { // prova ad eseguire il codice
            inizializzato = super.inizializza();

            if (this.isUsaIconaModulo()) {
                mod = this.getModulo();

                iconaPiccola = this.getNomeIconaPiccola();
                iconaMedia = this.getNomeIconaMedia();
                iconaGrande = this.getNomeIconaGrande();

                /**
                 *  regola la proprieta dell'azione (SMALL_ICON, MEDIUM_ICON,
                 *  LARGE_ICON), col metodo creaIcona che restituisce un'icona<br>
                 *  la proprieta dell'azione verra usata per mostrare
                 *  il disegno nei menu e nei bottoni
                 */
                this.putValue(ICONA_PICCOLA, Lib.Risorse.getIcona(mod, iconaPiccola));
                this.putValue(ICONA_MEDIA, Lib.Risorse.getIcona(mod, iconaMedia));
                this.putValue(ICONA_GRANDE, Lib.Risorse.getIcona(mod, iconaGrande));
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return inizializzato;
    }


    protected Modulo getModulo() {
        return modulo;
    }


    private void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }


    public String getPosMenu() {
        return posMenu;
    }


    private void setPosMenu(String posMenu) {
        this.posMenu = posMenu;
    }


    protected boolean isUsaIconaModulo() {
        return usaIconaModulo;
    }


    protected void setUsaIconaModulo(boolean usaIconaModulo) {
        this.usaIconaModulo = usaIconaModulo;
    }
}// fine della classe
