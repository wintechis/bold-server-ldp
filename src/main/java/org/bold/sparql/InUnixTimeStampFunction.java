package org.bold.sparql;

import org.bold.sim.Vocabulary;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.query.algebra.evaluation.ValueExprEvaluationException;
import org.eclipse.rdf4j.query.algebra.evaluation.function.Function;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * This custom function turns an xsd:dateTime into an integer Unix timestamp (number of milliseconds since Jan, 1st 1970).
 */
public class InUnixTimeStampFunction implements Function {

    private DatatypeFactory factory;

    public InUnixTimeStampFunction() {
        try {
            factory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace(); // TODO proper handling
        }
    }

    @Override
    public String getURI() {
        return Vocabulary.NS + "inUnixTimeStamp";
    }

    @Override
    public Value evaluate(ValueFactory valueFactory, Value... args) throws ValueExprEvaluationException {
        if (factory == null) throw new ValueExprEvaluationException(); // TODO not exactly the best exception

        XMLGregorianCalendar date = ((Literal) args[0]).calendarValue();
        Long millis = date.toGregorianCalendar().getTimeInMillis();

        return Vocabulary.VALUE_FACTORY.createLiteral(millis);
    }
}
