package fetchgroups;

public class Transaction {
	private int transactionId;
	private int expenseId;
    private String sender;
    private String recipient;
    private double amount;
    private String expenseName;
    private String date;

    public Transaction(int transactionId, int expenseId, String sender, String recipient, double amount, String expenseName, String date) {
        this.transactionId = transactionId;
        this.expenseId = expenseId;
    	this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.expenseName = expenseName;
        this.date = date;
    }

    public int getExpenseId() {
    	return expenseId;
    }
    
    public void setExpenseId(int expenseId) {
    	this.expenseId = expenseId;
    }
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }
    
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", amount=" + amount +
                ", expenseName='" + expenseName + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

