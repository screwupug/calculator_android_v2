package currencies;

import androidx.annotation.NonNull;

public enum Currencies {
    AUD("AUD"),
    AZN("AZN"),
    GBP("GBP"),
    AMD("AMD"),
    BYN("BYN"),
    BGN("BGN"),
    BRL("BRL"),
    HUF("HUF"),
    VND("VND"),
    GEL("GEL"),
    HKD("HKD"),
    DKK("DKK"),
    AED("AED"),
    USD("USD"),
    EUR("EUR"),
    EGP("EGP"),
    INR("INR"),
    IDR("IDR"),
    KZT("KZT"),
    CAD("CAD"),
    QAR("QAR"),
    KGS("KGS"),
    CNY("CNY"),
    MDL("MDL"),
    NZD("NZD"),
    NOK("NOK"),
    PLN("PLN"),
    RON("RON"),
    XDR("XDR"),
    SGD("SGD"),
    TJS("TJS"),
    THB("THB"),
    TRY("TRY"),
    TMT("TMT"),
    UZS("UZS"),
    UAH("UAH"),
    CZK("CZK"),
    SEK("SEK"),
    CHF("CHF"),
    RSD("RSD"),
    ZAR("ZAR"),
    KRW("KRW"),
    JPY("JPY");

    private final String currency;

    Currencies(String currency) {
        this.currency = currency;
    }

    @NonNull
    @Override
    public String toString() {
        return currency;
    }
}
