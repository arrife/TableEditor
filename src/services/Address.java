package services;

public final class Address {
    private final int row;
    private final int column;

    public Address(String code) {
        row = TermParser.getAddressRow(code);
        column = TermParser.getAddressColumn(code);
    }

    public boolean belongTo(int firstRow, int lastRow, int column) {
        return (firstRow <= row) && (row <= lastRow) && (this.column == column || column == -1);
    }
}
