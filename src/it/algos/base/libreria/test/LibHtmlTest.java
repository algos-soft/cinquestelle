package it.algos.base.libreria.test;
/**
 * Created by IntelliJ IDEA.
 * User: gac
 * Date: 7-lug-2006
 * Time: 13.34.51
 * To change this template use File | Settings | File Templates.
 */

import it.algos.base.libreria.Lib;
import junit.framework.TestCase;

import java.util.ArrayList;

public class LibHtmlTest extends TestCase {


    public void testGetIniTabella() throws Exception {
        String risultato;
        String richiesto;

        richiesto =
                "<HTML>\n" +
                        "<HEAD><META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html\"></HEAD>\n" +
                        "<BODY><TABLE BORDER=1>\n";
        risultato = Lib.Html.getIniTabella();

        if (!risultato.equals(richiesto)) {
            fail("incipit errato");
        }// fine del blocco if
    }


    public void testGetEndTabella() throws Exception {
        String risultato;
        String richiesto;

        richiesto = "</TABLE></BODY></HTML>";
        risultato = Lib.Html.getEndTabella();

        if (!risultato.equals(richiesto)) {
            fail("chiusura errata");
        }// fine del blocco if

    }


    public void testGetTitoli() throws Exception {
        String originale;
        String risultato;
        String richiesto;


        richiesto =
                "<TR>\n" +
                        "<TH>data</TH>\n" +
                        "<TH>luogo</TH>\n" +
                        "<TH>manifestazione</TH>\n" +
                        "<TH>suonatori</TH>\n" +
                        "<TH>note</TH>\n" +
                        "</TR>\n";

        originale = "data,luogo,manifestazione,suonatori,note";
        risultato = Lib.Html.getTitoliTabella(originale);

        if (!risultato.equals(richiesto)) {
            fail("titoli");
        }// fine del blocco if

        risultato = Lib.Html.getTitoliTabella(originale, ",");

        if (!risultato.equals(richiesto)) {
            fail("titoli con virgola");
        }// fine del blocco if


        originale = "data\tluogo\tmanifestazione\tsuonatori\tnote";
        risultato = Lib.Html.getTitoliTabella(originale, "\t");

        if (!risultato.equals(richiesto)) {
            fail("titoli con tab");
        }// fine del blocco if

        originale = "data, luogo, manifestazione, suonatori, note";
        risultato = Lib.Html.getTitoliTabella(originale);

        if (!risultato.equals(richiesto)) {
            fail("titoli con spazio");
        }// fine del blocco if
    }


    public void testGetHtml() throws Exception {
        ArrayList<String> originale = null;
        String risultato;
        String richiesto;

        originale = new ArrayList<String>();
        originale.add("Sabato, 22  luglio 06");
        originale.add("Cosola (AL)");
        originale.add("pom e sera    Festa delle Aie");
        originale.add("Stefano Valla - Daniele Scurati");
        originale.add(
                "Comune di Cabella Ligure (AL)     per le vie del paese<BR>daniele 338-1239203");

        richiesto =
                "<TR>\n" +
                        "<TD>Sabato, 22  luglio 06</TD>\n" +
                        "<TD>Cosola (AL)</TD>\n" +
                        "<TD>pom e sera    Festa delle Aie</TD>\n" +
                        "<TD>Stefano Valla - Daniele Scurati</TD>\n" +
                        "<TD>Comune di Cabella Ligure (AL)     per le vie del paese<BR>daniele 338-1239203</TD>\n" +
                        "</TR>\n";

        risultato = Lib.Html.getRigaTabella(originale);

        if (!risultato.equals(richiesto)) {
            fail("corpo riga");
        }// fine del blocco if
    }
}