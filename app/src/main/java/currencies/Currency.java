package currencies;

public class Currency {
    private String name;
    private double value;

    private double amount;

    public Currency(String name, double value, double amount) {
        this.name = name;
        this.value = value;
    }

    public Currency(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public Currency(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(double value) {
        this.value = value;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
