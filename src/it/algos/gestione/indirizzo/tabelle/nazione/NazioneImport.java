package it.algos.gestione.indirizzo.tabelle.nazione;
/**
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      15-apr-2007
 */

import it.algos.base.campo.base.Campo;
import it.algos.base.dialogo.DialogoImport;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.DieciStringhe;
import it.algos.gestione.tabelle.valuta.Valuta;
import it.algos.gestione.tabelle.valuta.ValutaModulo;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Importazione delle nazioni (stati) dal server di supporto.
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 15-apr-2007 ore 10.10.46
 */
public final class NazioneImport extends DialogoImport implements Nazione {


    /**
     * Costruttore completo .
     *
     * @param modulo di riferimento
     * @param mostraDialogo flag per usare o meno il dialogo
     */
    public NazioneImport(Modulo modulo, boolean mostraDialogo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        /* regola le variabili di istanza coi parametri */
        this.setMostraDialogo(mostraDialogo);

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
        if (this.isMostraDialogo()) {
            this.creaDialogo();
        } else {
            super.creaCondizionato();
        }// fine del blocco if-else
    }// fine del metodo inizia


    /**
     * Creazione dialogo.
     * <p/>
     * Metodo invocato dal bottone specifico nella toolbar della lista <br>
     */
    private void creaDialogo() {
        try { // prova ad eseguire il codice
            super.creaDialogo("Nazioni codificate ISO 3166");
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea tutti i records.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     * <p/>
     * Recupera i dati dalla pagina ISO 4217 di wikipedia <br>
     */
    @Override
    protected void creaRecords() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice
            this.creaNazioni();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea tutti i records.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     * <p/>
     * Recupera i dati da una pagina di wikipedia <br>
     * I dati bengono passati con un wrapper <br>
     */
    private void creaNazioni() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        ArrayList<DieciStringhe> listaGrezza;
        ArrayList<Naz> listaFinale = null;

        try { // prova ad eseguire il codice
            /* recupera la lista delle nazioni in ordine alfabetico di codice */
            listaGrezza = this.getNazioniIso();
            continua = (listaGrezza != null && listaGrezza.size() > 0);

            /* ordina per importanza le nazioni europee */
            if (continua) {
                listaFinale = this.ordinaEuropa(listaGrezza);
            }// fine del blocco if

            if (continua) {
                for (Naz naz : listaFinale) {
                    this.creaRecord(naz);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea un singolo record.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     *
     * @param naz valori della singola nazione (stato)
     */
    private void creaRecord(Naz naz) {
        /* variabili e costanti locali di lavoro */
        Modulo mod;
        ArrayList<CampoValore> campi;
        CampoValore cv;
        Campo campo;
        String valuta;
        int linkValuta;

        try { // prova ad eseguire il codice

            try { // prova ad eseguire il codice

                mod = this.getModulo();
                campi = new ArrayList<CampoValore>();

                campo = mod.getCampo(Cam.nazione);
                if (campo != null) {
                    cv = new CampoValore(campo, naz.getDieci().getPrima());
                    campi.add(cv);
                }// fine del blocco if

                campo = mod.getCampo(Cam.nazioneCompleto);
                if (campo != null) {
                    cv = new CampoValore(campo, naz.getDieci().getSeconda());
                    campi.add(cv);
                }// fine del blocco if

                campo = mod.getCampo(Cam.sigla2);
                if (campo != null) {
                    cv = new CampoValore(campo, naz.getDieci().getTerza());
                    campi.add(cv);
                }// fine del blocco if

                campo = mod.getCampo(Cam.sigla3);
                if (campo != null) {
                    cv = new CampoValore(campo, naz.getDieci().getQuarta());
                    campi.add(cv);
                }// fine del blocco if

                campo = mod.getCampo(Cam.tld);
                if (campo != null) {
                    cv = new CampoValore(campo, naz.getDieci().getQuinta());
                    campi.add(cv);
                }// fine del blocco if

                campo = mod.getCampo(Cam.linkValuta);
                if (campo != null) {
                    valuta = naz.getDieci().getSesta();
                    linkValuta = this.getCodValuta(valuta);

                    cv = new CampoValore(campo, linkValuta);
                    campi.add(cv);
                }// fine del blocco if

                campo = mod.getCampo(Cam.capitale);
                if (campo != null) {
                    cv = new CampoValore(campo, naz.getDieci().getSettima());
                    campi.add(cv);
                }// fine del blocco if

                campo = mod.getCampo(Cam.telefono);
                if (campo != null) {
                    cv = new CampoValore(campo, naz.getDieci().getOttava());
                    campi.add(cv);
                }// fine del blocco if

                campo = mod.getCampo(Cam.offsetGMT);
                if (campo != null) {
                    cv = new CampoValore(campo, naz.getDieci().getNona());
                    campi.add(cv);
                }// fine del blocco if

                campo = mod.getCampo(ModelloAlgos.NOME_CAMPO_NOTE);
                if (campo != null) {
                    cv = new CampoValore(campo, naz.getDieci().getDecima());
                    campi.add(cv);
                }// fine del blocco if

                campo = mod.getCampo(Cam.checkEuropa);
                if (campo != null) {
                    cv = new CampoValore(campo, naz.isEuropa());
                    campi.add(cv);
                }// fine del blocco if

                super.creaRecord(mod, campi, Nazione.Cam.nazione.get(), naz.getDieci().getPrima());

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Recupera la lista delle nazioni.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     * Recupera i dati dalla pagina Wikipedia:Data/Stati di wikipedia <br>
     * Per ogni riga crea un wrapper di dati <br>
     *
     * @return la lista delle nazioni
     */
    private ArrayList<DieciStringhe> getNazioniIso() {
        /* variabili e costanti locali di lavoro */
        ArrayList<DieciStringhe> lista = null;
        boolean continua;
        String titolo = "geo/stati/Stati";
        String testo;

        try { // prova ad eseguire il codice
            testo = Lib.Web.downLoadData(titolo);
            continua = (Lib.Testo.isValida(testo));

            if (continua) {
                lista = Lib.Web.getCsvDieci(testo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera il codice della valuta.
     * <p/>
     *
     * @param valuta sigla della valuta
     *
     * @return codice del record sulla tavola valuta
     */
    private int getCodValuta(String valuta) {
        /* variabili e costanti locali di lavoro */
        int cod = 0;
        Modulo mod;

        try { // prova ad eseguire il codice
            valuta = valuta.trim();

            mod = ValutaModulo.get();
            if (mod != null) {
                cod = mod.query().valoreChiave(Valuta.Cam.codiceIso.get(), valuta);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return cod;
    }


    /**
     * Ordina per importanza le nazioni.
     * <p/>
     * Prima le 6 nazioni fondatrici dell'Unione Europea (6) <br>
     * Poi tutte le altre nazioni dell'Unione Europea (27-6)
     * Le rimanenti in ordine alfabetico e non di codice iso <br>
     *
     * @param listaIn ingresso non ordinata
     *
     * @return lista ordinata secondo i criteri
     */
    private ArrayList<Naz> ordinaEuropa(ArrayList<DieciStringhe> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Naz> listaOut = null;
        ArrayList<Naz> listaAltreEuropa;
        ArrayList<Naz> listaAltreMondo;
        String nomeIn;
        String nomeOut;
        boolean esiste;

        try { // prova ad eseguire il codice
            listaOut = new ArrayList<Naz>();

            /* inserisco le prime 6 */
            for (Nazione.Europa nazione : Nazione.Europa.values()) {
                if (nazione.isFondatore()) {
                    for (DieciStringhe naz : listaIn) {
                        if (naz.getTerza().equals(nazione.getSigla())) {
                            listaOut.add(new Naz(naz, true));
                            break;
                        }// fine del blocco if
                    } // fine del ciclo for-each
                }// fine del blocco if
            } // fine del ciclo for-each

            /* recupero le altre 21 europee */
            listaAltreEuropa = new ArrayList<Naz>();
            for (Nazione.Europa nazione : Nazione.Europa.values()) {
                if (!nazione.isFondatore()) {
                    for (DieciStringhe naz : listaIn) {
                        if (naz.getTerza().equals(nazione.getSigla())) {
                            listaAltreEuropa.add(new Naz(naz, true));
                            break;
                        }// fine del blocco if
                    } // fine del ciclo for-each
                }// fine del blocco if
            } // fine del ciclo for-each

            /* ordino le rimanenti in alfabetico sul primo campo (colonna) */
            listaAltreEuropa = ordinaNazioni(listaAltreEuropa);

            /* aggiungo le rimanenti */
            listaOut = sommaNazioni(listaOut, listaAltreEuropa);

            /* recupero le rimanenti */
            listaAltreMondo = new ArrayList<Naz>();
            for (DieciStringhe dieci : listaIn) {
                nomeIn = dieci.getPrima();
                esiste = false;
                for (Naz naz : listaOut) {
                    nomeOut = naz.getDieci().getPrima();
                    if (nomeOut.equals(nomeIn)) {
                        esiste = true;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each

                if (!esiste) {
                    listaAltreMondo.add(new Naz(dieci, false));
                }// fine del blocco if
            } // fine del ciclo for-each

            /* ordino le rimanenti in alfabetico sul primo campo (colonna) */
            listaAltreMondo = ordinaNazioni(listaAltreMondo);

            /* aggiungo le rimanenti */
            listaOut = sommaNazioni(listaOut, listaAltreMondo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Ordina una lista di nazioni.
     * <p/>
     *
     * @param listaIn da ordinare
     *
     * @return lista ordinata
     */
    private ArrayList<Naz> ordinaNazioni(ArrayList<Naz> listaIn) {
        ArrayList<Naz> listaOut = null;
        boolean continua;
        LinkedHashMap<String, Naz> mappa;
        ArrayList<String> listaTemp;
        String unNome;
        Naz nazione;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (listaIn != null && listaIn.size() > 0);

            if (continua) {
                listaOut = new ArrayList<Naz>();
                listaTemp = new ArrayList<String>();
                mappa = new LinkedHashMap<String, Naz>();

                for (Naz naz : listaIn) {
                    unNome = naz.getDieci().getPrima();
                    mappa.put(unNome, naz);
                    listaTemp.add(unNome);
                } // fine del ciclo for-each

                /* ordina la lista di riferimento */
                listaTemp = Lib.Array.ordina(listaTemp);

                for (String nome : listaTemp) {
                    nazione = mappa.get(nome);
                    listaOut.add(nazione);
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Somma due liste di nazioni.
     * <p/>
     *
     * @param prima lista da sommare
     * @param seconda lista da sommare
     *
     * @return somma delle due liste
     */
    private ArrayList<Naz> sommaNazioni(ArrayList<Naz> prima, ArrayList<Naz> seconda) {
        ArrayList<Naz> somma = null;
        boolean continua;

        try { // prova ad eseguire il codice
            continua = (prima != null && seconda != null);

            if (continua) {
                somma = new ArrayList<Naz>();
            }// fine del blocco if

            if (continua) {
                /* traverso tutta la collezione */
                for (Naz naz : prima) {
                    somma.add(naz);
                } // fine del ciclo for-each

                /* traverso tutta la collezione */
                for (Naz naz : seconda) {
                    somma.add(naz);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return somma;
    }


    /**
     * Classe 'interna'. </p>
     */
    public final class Naz {

        private DieciStringhe dieci;

        private boolean europa;


        /**
         * Costruttore completo con parametri.
         *
         * @param dieci - dieci stringhe
         * @param europa - flag per le nazioni dell'Unione Europea
         */
        public Naz(DieciStringhe dieci, boolean europa) {
            /* rimanda al costruttore della superclasse */
            super();

            this.setDieci(dieci);
            this.setEuropa(europa);
        }// fine del metodo costruttore completo


        public DieciStringhe getDieci() {
            return dieci;
        }


        private void setDieci(DieciStringhe dieci) {
            this.dieci = dieci;
        }


        public boolean isEuropa() {
            return europa;
        }


        private void setEuropa(boolean europa) {
            this.europa = europa;
        }
    } // fine della classe 'interna'

}// fine della classe
