package it.algos.base.wrapper;

/**
 * - @todo Manca la descrizione..
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 19-mar-2008 ore  08:53
 */
public final class SeiStringhe {

    /**
     * variabile di tipo SeiStringhe
     * le prime cinque stringhe
     */
    private CinqueStringhe primeCinque;


    /**
     * variabile di tipo testo
     * sesta stringa
     */
    private String sesta;


    /**
     * Costruttore senza parametri.
     */
    public SeiStringhe() {
        /* rimanda al costruttore della superclasse */
        super();
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con una stringa <br>
     *
     * @param precedente stringa
     */
    public SeiStringhe(String precedente) {
        /* invoca il metodo sovrascritto della classe */
        this(precedente, "", "", "", "", "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con due stringhe <br>
     *
     * @param precedenti - l'istanza con le precedenti stringhe
     */
    public SeiStringhe(DueStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(), precedenti.getSeconda(), "", "", "", "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con tre stringhe <br>
     *
     * @param precedenti - l'istanza con le precedenti stringhe
     */
    public SeiStringhe(TreStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(), precedenti.getSeconda(), precedenti.getTerza(), "", "", "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con quattro stringhe <br>
     *
     * @param precedenti - l'istanza con le precedenti stringhe
     */
    public SeiStringhe(QuattroStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(),
                precedenti.getSeconda(),
                precedenti.getTerza(),
                precedenti.getQuarta(),
                "",
                "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con cinque stringhe <br>
     *
     * @param precedenti - l'istanza con le precedenti stringhe
     */
    public SeiStringhe(CinqueStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(),
                precedenti.getSeconda(),
                precedenti.getTerza(),
                precedenti.getQuarta(),
                precedenti.getQuinta(),
                "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     *
     * @param prima - prima stringa
     * @param seconda - seconda stringa
     * @param terza - terza stringa
     * @param quarta - quarta stringa
     * @param quinta - quinta
     * @param sesta - sesta stringa
     */
    public SeiStringhe(String prima,
                       String seconda,
                       String terza,
                       String quarta,
                       String quinta,
                       String sesta) {

        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        CinqueStringhe primeCinque;
        primeCinque = new CinqueStringhe(prima, seconda, terza, quarta, quinta);

        this.setPrecedenti(primeCinque);
        this.setSesta(sesta);

    }// fine del metodo costruttore completo


    /**
     * metodo getter
     *
     * @return primeCinque
     */
    private CinqueStringhe getPrecedenti() {
        return primeCinque;
    } // fine del metodo getter


    /**
     * metodo setter
     *
     * @param primeCinque stringhe
     */
    private void setPrecedenti(CinqueStringhe primeCinque) {
        this.primeCinque = primeCinque;
    } // fine del metodo setter


    /**
     * metodo getter
     *
     * @return due stringhe
     */
    public DueStringhe getDue() {
        return this.getPrecedenti().getDue();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return tre stringhe
     */
    public TreStringhe getTre() {
        return this.getPrecedenti().getTre();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return quattro stringhe
     */
    public QuattroStringhe getQuattro() {
        return this.getPrecedenti().getQuattro();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return cinque stringhe
     */
    public CinqueStringhe getCinque() {
        return this.getPrecedenti();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return prima
     */
    public String getPrima() {
        return this.getPrecedenti().getPrima();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return seconda
     */
    public String getSeconda() {
        return this.getPrecedenti().getSeconda();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return terza
     */
    public String getTerza() {
        return this.getPrecedenti().getTerza();
    } // fine del metodo getter


    /**
     * /**
     * metodo getter
     *
     * @return quarta
     */
    public String getQuarta() {
        return this.getPrecedenti().getQuarta();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return quinta
     */
    public String getQuinta() {
        return this.getPrecedenti().getQuinta();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return sesta
     */
    public String getSesta() {
        return this.sesta;
    } // fine del metodo getter


    /**
     * metodo setter
     *
     * @param prima - prima stringa
     */
    public void setPrima(String prima) {
        this.getPrecedenti().setPrima(prima);
    } // fine del metodo setter


    /**
     * metodo setter
     *
     * @param seconda - seconda stringa
     */
    public void setSeconda(String seconda) {
        this.getPrecedenti().setSeconda(seconda);
    } // fine del metodo setter


    /**
     * metodo setter
     *
     * @param terza - terza stringa
     */
    public void setTerza(String terza) {
        this.getPrecedenti().setTerza(terza);
    } // fine del metodo setter


    /**
     * metodo setter
     *
     * @param quarta - quarta stringa
     */
    public void setQuarta(String quarta) {
        this.getPrecedenti().setQuarta(quarta);
    } // fine del metodo setter


    /**
     * metodo setter
     *
     * @param quinta - quinta stringa
     */
    public void setQuinta(String quinta) {
        this.getPrecedenti().setQuinta(quinta);
    } // fine del metodo setter


    /**
     * metodo setter
     *
     * @param sesta - sesta stringa
     */
    public void setSesta(String sesta) {
        this.sesta = sesta;
    } // fine del metodo setter


}// fine della classe