package fetchgroups;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

public class GroupDAO {
// Assume you have a database connection
	private Connection con = null;

	// Constructor to initialize the connection
	public GroupDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/billsplit", "root", "student");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	public String getUserPhone(int id) {
	    String phone = null;
	    String sql = "SELECT phone FROM users WHERE user_id = ?";
	    
	    try {
	        PreparedStatement preparedStatement = con.prepareStatement(sql);
	        preparedStatement.setInt(1, id);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            phone = resultSet.getString("phone");
	        } else {
	            System.out.println("No phone number found for user with ID: " + id);
	        }

	        // Close resources
	        resultSet.close();
	        preparedStatement.close();
	    } catch (SQLException e) {
	        e.printStackTrace(); // Handle the exception appropriately
	    }

	    return phone;
	}

	public Group getGroupDetails(String groupId) {

		Group group = null;
		String sql = "SELECT * FROM user_groups WHERE group_id = ?";
		String name = "";
		try {

			try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
				preparedStatement.setString(1, groupId);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						// Populate the Group object with data from the result set
						group = new Group();
						group.setGroupId(Integer.parseInt(resultSet.getString("group_id")));
						group.setGroupName(resultSet.getString("group_name"));
						group.setCreatedBy(resultSet.getInt("created_by"));
						group.setLink(resultSet.getString("share_link"));
						group.setCreatedOn(resultSet.getString("created_on"));
						group.setTotalAmt(resultSet.getInt("total_amt"));
						// Set other properties as needed
					}
				}
			} catch (SQLException e) {
				e.printStackTrace(); // Handle the exception appropriately
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return group;
	}

	public List<Entry<Integer, String>> getGroupMembers(String groupId) {
		List<Entry<Integer, String>> groupMembers = new ArrayList<>();
		String sql = "SELECT u.user_id, u.username FROM users u " + "JOIN group_members gm ON u.user_id = gm.user_id "
				+ "WHERE gm.group_id = ?";

		try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
			preparedStatement.setString(1, groupId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					// Add each member's user_id and username as a pair to the list
					int userId = resultSet.getInt("user_id");
					String username = resultSet.getString("username");
					groupMembers.add(new SimpleEntry<>(userId, username));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle the exception appropriately
		}

		return groupMembers;
	}

	// Overview table
	public double getTotalExpenses(String groupId) {
		double total = 0;
		String sql = "SELECT SUM(amount) AS total_expenses FROM expenses WHERE group_id = ?";

		try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
			preparedStatement.setString(1, groupId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					total = resultSet.getDouble("total_expenses");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		total = Double.parseDouble(String.format("%.2f", total));
		return total;
	}

	public double getYourExpense(String groupId, int userId) {
		double total = 0;
		String sql = "SELECT SUM(amount) AS individual_expense FROM expenses WHERE group_id = ? AND paid_by = ?";

		try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
			preparedStatement.setString(1, groupId);
			preparedStatement.setInt(2, userId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					total = resultSet.getDouble("individual_expense");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		total = Double.parseDouble(String.format("%.2f", total));
		return total;
	}

	public double getYouOweOthers(String groupId, int userId) {
		double amountYouOweOthers = 0;
		String sql = "SELECT SUM(share_amount) AS Owed_expense FROM transactions WHERE user_id = ? AND expense_id in(SELECT expense_id FROM expenses WHERE group_id = ?)";

		try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
			preparedStatement.setInt(1, userId);
			preparedStatement.setString(2, groupId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					amountYouOweOthers = resultSet.getDouble("Owed_expense");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		amountYouOweOthers = Double.parseDouble(String.format("%.2f", amountYouOweOthers));
		return amountYouOweOthers;
	}

	public double getOthersOweYou(String groupId, int userId) {
		double amountOthersOweYou = 0;
		String sql = "SELECT SUM(share_amount) AS Owed_expense FROM transactions WHERE expense_id in(select expense_id from expenses where group_id = ? AND paid_by = ?)";

		try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
			preparedStatement.setString(1, groupId);
			preparedStatement.setInt(2, userId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					amountOthersOweYou = resultSet.getDouble("Owed_expense");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		amountOthersOweYou = Double.parseDouble(String.format("%.2f", amountOthersOweYou));
		return amountOthersOweYou;
	}
	
	
	//Transactions 
	public List<Transaction> getAllTransactions(String groupId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT t.transaction_id, e.expense_id, t.user_id, e.paid_by, t.share_amount, e.expense_name, e.date " +
                     "FROM transactions t " +
                     "INNER JOIN expenses e ON t.expense_id = e.expense_id " +
                     "WHERE e.group_id = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, groupId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
            	int transactionid = resultSet.getInt("transaction_id");
            	int expense_id = resultSet.getInt("expense_id");
                String sender = resultSet.getString("user_id");
                String recipient = resultSet.getString("paid_by");
                double amount = resultSet.getDouble("share_amount");
                String expenseName = resultSet.getString("expense_name");
                String date = resultSet.getString("date");
                
                // Create a new Transaction object and add it to the list
                Transaction transaction = new Transaction(transactionid, expense_id, sender, recipient, amount, expenseName, date);
                transactions.add(transaction);
            }
            // Close resources
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return transactions;
    }
	
	//Delete transaction 
	public boolean archiveTransactionAndExpense(int transactionId, int expenseId) {
	    String archiveTransactionSql = "INSERT INTO transaction_history SELECT * FROM transactions WHERE transaction_id = ?";
	    String deleteTransactionSql = "DELETE FROM transactions WHERE transaction_id = ?";
	    String archiveExpenseSql = "INSERT INTO expense_history SELECT * FROM expenses WHERE expense_id = ?";
	    String deleteExpenseSql = "DELETE FROM expenses WHERE expense_id = ? AND NOT EXISTS (SELECT 1 FROM transactions WHERE expense_id = ?)";

	    System.out.println("Starting transaction and expense archival process");
	    try {
	        con.setAutoCommit(false); // Start transaction
	        // Archive the transaction
	        System.out.println("Archiving transaction with ID: " + transactionId);
	        try (PreparedStatement archiveTransactionStmt = con.prepareStatement(archiveTransactionSql)) {
	            archiveTransactionStmt.setInt(1, transactionId);
	            int rowsAffected = archiveTransactionStmt.executeUpdate();
	            System.out.println("Rows affected by archiveTransaction: " + rowsAffected);
	        }
	        // Delete the transaction
	        System.out.println("Deleting transaction with ID: " + transactionId);
	        try (PreparedStatement deleteTransactionStmt = con.prepareStatement(deleteTransactionSql)) {
	            deleteTransactionStmt.setInt(1, transactionId);
	            int rowsAffected = deleteTransactionStmt.executeUpdate();
	            System.out.println("Rows affected by deleteTransaction: " + rowsAffected);
	        }
	        // Archive the expense if there are no remaining transactions for that expense
	        System.out.println("Archiving expense with ID: " + expenseId);
	        try (PreparedStatement archiveExpenseStmt = con.prepareStatement(archiveExpenseSql)) {
	            archiveExpenseStmt.setInt(1, expenseId);
	            int rowsAffected = archiveExpenseStmt.executeUpdate();
	            System.out.println("Rows affected by archiveExpense: " + rowsAffected);
	        }
	        // Delete the expense if there are no remaining transactions for that expense
	        System.out.println("Deleting expense with ID: " + expenseId);
	        try (PreparedStatement deleteExpenseStmt = con.prepareStatement(deleteExpenseSql)) {
	            deleteExpenseStmt.setInt(1, expenseId);
	            deleteExpenseStmt.setInt(2, expenseId);
	            int rowsAffected = deleteExpenseStmt.executeUpdate();
	            System.out.println("Rows affected by deleteExpense: " + rowsAffected);
	        }
	        con.commit();
	        System.out.println("Transaction and expense archival process completed successfully");
	        return true;
	    } catch (SQLException e) {
	        try {
	            con.rollback(); 
	            System.out.println("Transaction rolled back due to error");
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        e.printStackTrace();
	        System.out.println("SQLException: " + e.getMessage());
	    } finally {
	        try {
	            con.setAutoCommit(true); // Reset auto-commit to true
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    System.out.println("Transaction and expense archival process failed");
	    return false;
	}
	
	public List<Expense> getAllArchivedExpenses(String groupId) {
        List<Expense> archivedExpenses = new ArrayList<>();
        String sql = "SELECT * FROM expense_history WHERE group_id = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, groupId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Expense expense = new Expense();
                    expense.setExpenseId(resultSet.getInt("expense_id"));
                    expense.setExpenseName(resultSet.getString("expense_name"));
                    expense.setAmount(resultSet.getDouble("amount"));
                    expense.setDate(resultSet.getString("date"));
                    expense.setGroupId(resultSet.getInt("group_id"));
                    expense.setPaidBy(resultSet.getInt("paid_by"));
                    archivedExpenses.add(expense);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return archivedExpenses;
    }
}
