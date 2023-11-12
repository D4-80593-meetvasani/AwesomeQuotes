package com.sunbeam;

import java.util.List;
import java.util.Scanner;

public class Main {
	public static Scanner sc;

	public static void clientOperations() {
		// TODO Auto-generated method stub
		int choice;
		Users curUser = null;
		do {
			System.out.println("\n0. Exit\n1. Sign Up\n2. Sign In\nEnter choice: ");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				signUp();
				break;
			case 2:
				curUser = signIn();
				if (curUser != null) {
					userOperations(curUser);
				} else {
					System.out.println("Enter a valid user id and password !");
				}
				break;
			case 0:
				System.out.println("Exit done ! Bye !");
				break;
			default:
				break;

			}
		} while (choice != 0);
	}

	public static Users signIn() {
		// TODO Auto-generated method stub
		String email;
		String password;
		System.out.print("Enter email: ");
		email = sc.next();
		System.out.print("Enter password: ");
		password = sc.next();
		try (UsersDao dao = new UsersDao()) {
			Users u = dao.findByEmail(email);
			if (u != null && password.equals(u.getPassword())) {
				return u;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void signUp() {
		// TODO Auto-generated method stub
		System.out.print("First Name: ");
		String fname = sc.next();
		System.out.print("Last Name: ");
		String lname = sc.next();
		System.out.print("Email: ");
		String email = sc.next();
		System.out.print("Password: ");
		String passwd = sc.next();
		System.out.print("Mobile: ");
		String mobile = sc.next();
		if (fname.isEmpty() || lname.isEmpty() || email.isEmpty() || passwd.isEmpty() || mobile.isEmpty()) {
			System.out.println("All fields must be filled.");
			return;
		}
		Users u = new Users(0, fname, lname, email, passwd, mobile);
		try (UsersDao dao = new UsersDao()) {
			int count = dao.save(u);
			System.out.println("User registered: " + count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Users userOperations(Users curUser) {
		// TODO Auto-generated method stub
		int choice;
		do {
			System.out.println("=====Menu=========");
			System.out.println("0. Sign Out\n" + "1. All Quotes\n" + "2. My Quotes\n" + "3. My Favorite Quotes\n"
					+ "4. Like/Unlike Quote\n" + "5. New Quote\n" + "6. Edit Quote\n" + "7. Delete Quote\n"
					+ "8. Change Password\n" + "9. Edit Proﬁle ");
			System.out.println("==================");
			System.out.println("Enter Choice : ");

			choice = sc.nextInt();
//	        sc.nextLine();
			switch (choice) {
			case 0:
				System.out.println("Signed Out !");
				break;
			case 1:// All Quotes
				displayAllQuotes();
				break;
			case 2:// My Quotes
				displayMyQuotes(curUser);
				break;
			case 3:// My Favorite Quotes
				displayFavQuotes(curUser);
				break;
			case 4:// Like/Unlike Quote (Toggle)
				toggleQuote(curUser);
				break;
			case 5:// New Quote
				addNewQuote(curUser);
				break;
			case 6:// 6. Edit Quote
				editMyQuote(curUser);
				break;
			case 7:// delete Quote
				try {
					deleteMyQuote(curUser);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 8:// Change Password
				changeMyPassword(curUser);
				break;
			case 9:// Edit Proﬁle
				editProfile(curUser);
				break;
			default:
				System.out.println("Invalid Choice !");
				break;
			}

		} while (choice != 0);

		return null;
	}

	public static void toggleQuote(Users curUser) {
		System.out.print("Enter quote id to like/unlike: ");
		int quoteId = sc.nextInt();

		try (QuotesDao dao = new QuotesDao()) {
			Quotes quote = dao.findQuoteById(quoteId);

			if (quote != null) {
				if (quote.getUserId() != curUser.getId()) {
					FavoriteQuotes favorite = new FavoriteQuotes(curUser.getId(), quoteId);

					if (dao.isQuoteLikedByUser(favorite)) {
						dao.removeFavorite(favorite);
						System.out.println("Quote unliked!");
					} else {
						dao.addFavorite(favorite);
						System.out.println("Quote liked!");
					}

					// displayAllQuotes();
				} else {
					System.out.println("You cannot like/unlike your own quotes ");
				}
			} else {
				System.out.println("Invalid quote id!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void changeMyPassword(Users curUser) {
//		sc.nextLine(); 
		System.out.print("Enter curr password : ");
		String passwd = sc.next().trim();
		if (passwd.equals(curUser.getPassword())) {
			System.out.println("Enter new password : ");
			String newPasswd = sc.next().trim();
			sc.nextLine();
			try (UsersDao dao = new UsersDao()) {
				curUser.setPassword(newPasswd);
				int count = dao.updatePassword(curUser.getId(), newPasswd);
				System.out.println("Password Updated Succesfully !  Rows Affected : " + count);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return;
		} else {
			System.out.println("Enter valid current password ! ");
			return;
		}

	}

	public static void editProfile(Users curUser) {
		System.out.println("Enter password : ");
		String passwd = sc.next().trim();
		sc.nextLine();
		if (passwd.equals(curUser.getPassword())) {
			System.out.println("Enter new First Name : ");
			String newFName = sc.next().trim();
			sc.nextLine(); 
			System.out.println("Enter new Last Name : ");
			String newLName = sc.next().trim();
			sc.nextLine(); 
			System.out.println("Enter new email : ");
//			sc.nextLine(); 
			String newEmail = sc.next().trim();
//			sc.nextLine();
			try (UsersDao dao = new UsersDao()) {
				int count = dao.update(curUser.getId(), newFName, newLName, newEmail);
				System.out.println("Profile edited ! rows affected " + count);
				return;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			System.out.println("Enter valid current password ! ");
			return;
		}

	}

	public static void displayAllQuotes() {
		// TODO Auto-generated method stub
		try (QuotesDao dao = new QuotesDao()) {
			List<Quotes> list = dao.findAll();
			list.forEach(System.out::println);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void displayMyQuotes(Users curUser) {
		// TODO Auto-generated method stub
		try (QuotesDao dao = new QuotesDao()) {
			List<Quotes> list = dao.findQuotesById(curUser.getId());
			System.out.println("My Quotes : ");
			list.forEach(System.out::println);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void displayFavQuotes(Users curUser) {
		// TODO Auto-generated method stub
		try (QuotesDao dao = new QuotesDao()) {
			List<Quotes> list = dao.findFavQuotesById(curUser.getId());
			if (list.isEmpty()) {
				System.out.println("You have no favorite quotes.");
			} else {
				System.out.println("My Favorite Quotes : ");
				list.forEach(System.out::println);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String addNewQuote(Users curUser) {
		System.out.print("Enter your quote: ");
		sc.nextLine();
		String quoteText = sc.nextLine().trim();
		if (quoteText.isEmpty()) {
			System.out.println("Quote cannot be empty.");
			return null;
		}

		System.out.print("Enter author name: ");
		String author = sc.nextLine().trim();
		if (author.isEmpty()) {
			System.out.println("Author name cannot be empty.");
			return null;
		}

		Quotes newQuote = new Quotes(0, quoteText, author, curUser.getId());

		try (QuotesDao dao = new QuotesDao()) {
			int count = dao.save(newQuote);
			System.out.println("New quote added: " + count);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return quoteText;
	}

	public static void deleteMyQuote(Users curUser) throws Exception {
		System.out.println("Enter id of quote you want to delete : ");
		int qid = sc.nextInt();

		if (!isQuoteBelongsToUser(qid, curUser.getId())) {
			System.out.println("You can only delete your own quotes.");
			return;
		}

		try (QuotesDao dao = new QuotesDao()) {
			int count = dao.delete(qid);
			System.out.println("Quotes deleted :" + count);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

//	private static boolean checkUserExistence(int userId) {
//	    try (UsersDao dao = new UsersDao()) {
//	        Users user = dao.findById(userId);
//	        return user != null;
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return false;
//	    }
//	}

	private static boolean isQuoteBelongsToUser(int quoteId, int userId) {
		try (QuotesDao dao = new QuotesDao()) {
			Quotes quote = dao.findQuoteById(quoteId);
			return quote != null && quote.getUserId() == userId;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Quotes getQuoteById(int quoteId) {
		try (QuotesDao dao = new QuotesDao()) {
			return dao.findQuoteById(quoteId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String editMyQuote(Users curUser) {
		System.out.println("Enter id of quote you want to edit : ");
		int qid = sc.nextInt();

		if (!isQuoteBelongsToUser(qid, curUser.getId())) {
			System.out.println("You can only edit your own quotes.");
			return null;
		}

		Quotes curQuote = getQuoteById(qid);

		if (curQuote == null) {
			System.out.println("Invalid quote ID.");
			return null;
		}

		System.out.println(
				"\nCurrent Quote : " + "\n" + curQuote.getText() + "\n Author : \n -" + curQuote.getAuthor() + "\n");

		System.out.print("Enter your new quote: ");
		sc.nextLine();
		String quoteText = sc.nextLine().trim();
		if (quoteText.isEmpty()) {
			System.out.println("Quote cannot be empty.");
			return null;
		}

		System.out.print("Enter author name: ");
		String author = sc.nextLine().trim();
		if (author.isEmpty()) {
			System.out.println("Author name cannot be empty.");
			return null;
		}

		Quotes newQuote = new Quotes(qid, quoteText, author, curUser.getId());

		try (QuotesDao dao = new QuotesDao()) {
			int count = dao.update(newQuote);
			System.out.println("New quote added: " + count);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return quoteText;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		sc = new Scanner(System.in);
		clientOperations();
		sc.close();
	}

}
