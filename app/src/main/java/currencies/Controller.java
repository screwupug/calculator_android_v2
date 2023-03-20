package currencies;

public class Controller {
    private final JsonReader reader;
    private Currency currencyFrom;
    private Currency currencyTo;

    private final Calculations calculations;

    public Controller(JsonReader reader, Calculations calculations) {
        this.reader = reader;
        this.calculations =calculations;
    }

    public Currency getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(Currency currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public Currency getCurrencyTo() {
        return currencyTo;
    }

    public void upDateCurrencyFromAmount(String amount) {
        currencyFrom.setAmount(Double.parseDouble(amount));
    }

    public void upDateCurrencyToAmount(String amount) {
        currencyTo.setAmount(Double.parseDouble(amount));
    }

    public boolean setCurrencyTo(Currency currencyTo) {
        this.currencyTo = currencyTo;
        return reader.getCurrencyValue(currencyTo);
    }

    public String currencyFromFieldUpdated() {
        return calculations.calculateFrom(currencyFrom, currencyTo);
    }

    public String currencyToFieldUpdated() {
        return calculations.calculateTo(currencyTo);
    }

}
