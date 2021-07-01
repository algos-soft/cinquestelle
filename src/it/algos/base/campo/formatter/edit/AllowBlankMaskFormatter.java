package it.algos.base.campo.formatter.edit;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

/**
 * A special version of the {@link javax.swing.text.MaskFormatter} for
 * {@link javax.swing.JFormattedTextField formatted text fields} that supports
 * the field being emptied/left blank.
 *
 * @author R.J. Lorimer
 */
public class AllowBlankMaskFormatter extends MaskFormatter {

    private boolean allowBlankField = true;

    private String blankRepresentation;


    public AllowBlankMaskFormatter() {
        super();
    }


    public AllowBlankMaskFormatter(String mask) throws ParseException {
        super(mask);
    }


    public void setAllowBlankField(boolean allowBlankField) {
        this.allowBlankField = allowBlankField;
    }


    public boolean isAllowBlankField() {
        return allowBlankField;
    }


    /**
     * Update our blank representation whenever the mask is updated.
     */
    @Override
    public void setMask(String mask) throws ParseException {
        super.setMask(mask);
        updateBlankRepresentation();
    }


    /**
     * Update our blank representation whenever the mask is updated.
     */
    @Override
    public void setPlaceholderCharacter(char placeholder) {
        super.setPlaceholderCharacter(placeholder);
        updateBlankRepresentation();
    }


    /**
     * Override the stringToValue method to check the blank representation.
     */
    @Override
    public Object stringToValue(String value) throws ParseException {
        /* variabili e costanti locali di lavoro */
        Object result = value;
        boolean continua = true;

        /* se permette campo vuoto e si tratta di campo vuoto
         * ritorna null */
        if (continua) {
            if (isAllowBlankField()) {
                if (this.getBlankRepresentation() != null) {
                    if (value.equals(this.getBlankRepresentation())) {
                        result = null;
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        /* se non ha intercettato il caso precedente,
         * rimanda alla superclasse */
        if (continua) {
            result = super.stringToValue(value);
        }// fine del blocco if

        /* valore di ritorno */
        return result;
    }


    /**
     * Override the stringToValue method to check the blank representation.
     */
    @Override
    public String valueToString(Object value) throws ParseException {
        /* variabili e costanti locali di lavoro */
        String result = "";

        result = super.valueToString(value);

        return result;
    }


    private void updateBlankRepresentation() {
        try {
            // calling valueToString on the parent class with a null attribute will get the 'blank'
            // representation.
            this.setBlankRepresentation(valueToString(null));
        } catch (ParseException e) {
            this.setBlankRepresentation(null);
        }
    }


    protected String getBlankRepresentation() {
        return blankRepresentation;
    }


    private void setBlankRepresentation(String blankRepresentation) {
        this.blankRepresentation = blankRepresentation;
    }
}
