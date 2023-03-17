package currencies;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class Calculations {


   public String calculateFrom(Currency currencyFrom, Currency currencyTo) {
       double result = currencyFrom.getAmount() / currencyTo.getValue();
       return formatResult(result);
   }

   public String calculateTo(Currency currencyTo) {
       double result = currencyTo.getAmount() * currencyTo.getValue();
       return formatResult(result);
   }

   private String formatResult(double result) {
       DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
       otherSymbols.setDecimalSeparator('.');
       NumberFormat nf = new DecimalFormat("#.###", otherSymbols);
       return nf.format(result);
   }
}
