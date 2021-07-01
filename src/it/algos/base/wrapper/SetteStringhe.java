package it.algos.base.wrapper;

/**
 * Wrapper di sette oggetti di tipo String.
 * </p>
 * <p/>
 * Si appoggia sull'oggetto di classe SeiStringhe <br>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 23-mar-2008 ore  09:22
 */
public final class SetteStringhe {

    /**
     * variabile di tipo SeiStringhe
     * le prime sei stringhe
     */
    private SeiStringhe primeSei;

    /**
     * variabile di tipo testo
     * settima stringa
     */
    private String settima;


    /**
     * Costruttore senza parametri.
     */
    public SetteStringhe() {
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
    public SetteStringhe(String precedente) {
        /* invoca il metodo sovrascritto della classe */
        this(precedente, "", "", "", "", "", "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con due stringhe <br>
     *
     * @param precedenti - l'istanza con le precedenti stringhe
     */
    public SetteStringhe(DueStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(), precedenti.getSeconda(), "", "", "", "", "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con tre stringhe <br>
     *
     * @param precedenti - l'istanza con le precedenti stringhe
     */
    public SetteStringhe(TreStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(), precedenti.getSeconda(), precedenti.getTerza(), "", "", "", "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con quattro stringhe <br>
     *
     * @param precedenti - l'istanza con le precedenti stringhe
     */
    public SetteStringhe(QuattroStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(),
                precedenti.getSeconda(),
                precedenti.getTerza(),
                precedenti.getQuarta(),
                "",
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
    public SetteStringhe(CinqueStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(),
                precedenti.getSeconda(),
                precedenti.getTerza(),
                precedenti.getQuarta(),
                precedenti.getQuinta(),
                "",
                "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con sei stringhe <br>
     *
     * @param precedenti - l'istanza con le precedenti stringhe
     */
    public SetteStringhe(SeiStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(),
                precedenti.getSeconda(),
                precedenti.getTerza(),
                precedenti.getQuarta(),
                precedenti.getQuinta(),
                precedenti.getSesta(),
                "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     *
     * @param prima - prima stringa
     * @param seconda - seconda stringa
     * @param terza - terza stringa
     * @param quarta - quarta stringa
     * @param quinta - quinta stringa
     * @param sesta - sesta stringa
     * @param settima - settima stringa
     */
    public SetteStringhe(String prima,
                         String seconda,
                         String terza,
                         String quarta,
                         String quinta,
                         String sesta,
                         String settima) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        SeiStringhe primeSei;
        primeSei = new SeiStringhe(prima, seconda, terza, quarta, quinta, sesta);

        this.setPrecedenti(primeSei);
        this.setSettima(settima);
    }// fine del metodo costruttore completo


    /**
     * metodo getter
     *
     * @return primeSei
     */
    private SeiStringhe getPrecedenti() {
        return primeSei;
    } // fine del metodo getter


    /**
     * metodo setter
     *
     * @param primeSei stringhe
     */
    private void setPrecedenti(SeiStringhe primeSei) {
        this.primeSei = primeSei;
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
        return this.getPrecedenti().getCinque();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return sei stringhe
     */
    public SeiStringhe getSei() {
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
        return this.getPrecedenti().getSesta();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return settima
     */
    public String getSettima() {
        return this.settima;
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
        this.getPrecedenti().setSesta(sesta);
    } // fine del metodo setter


    /**
     * metodo setter
     *
     * @param settima - settima stringa
     */
    public void setSettima(String settima) {
        this.settima = settima;
    } // fine del metodo setter

}// fine della classe