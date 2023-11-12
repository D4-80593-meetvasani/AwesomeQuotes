package com.sunbeam;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuotesDao extends Dao {
	private PreparedStatement stmtFindAll;
	private PreparedStatement stmtSave;
	private PreparedStatement stmtUpdate;
	private PreparedStatement stmtDelete;
	private PreparedStatement stmtFindQuotesById;
	private PreparedStatement stmtFindFavQuotesById;
	private PreparedStatement stmtFindQuoteById;
	private PreparedStatement stmtIsLiked;
	private PreparedStatement stmtAddLiked;
	private PreparedStatement stmtDelLiked;
	private PreparedStatement stmtGetLikedCount;

	public QuotesDao() throws Exception {
		// TODO Auto-generated constructor stub
		String sqlFindAll = "SELECT * from Quotes";
		stmtFindAll = con.prepareStatement(sqlFindAll);

		String sqlSave = "INSERT INTO Quotes VALUES (default,?,?,?)";
		stmtSave = con.prepareStatement(sqlSave);

		String sqlUpdate = "UPDATE Quotes SET text=?,author=? where id=?";
		stmtUpdate = con.prepareStatement(sqlUpdate);

		String sqlDelete = "DELETE from Quotes where id=?";
		stmtDelete = con.prepareStatement(sqlDelete);

		String sqlFindQuotesById = "SELECT * from Quotes WHERE user_id = ?";
		stmtFindQuotesById = con.prepareStatement(sqlFindQuotesById);

		String sqlFindFavQuotesById = "SELECT q.* FROM Quotes q JOIN FavoriteQuotes f ON q.id = f.quote_id WHERE f.user_id = ?";
		stmtFindFavQuotesById = con.prepareStatement(sqlFindFavQuotesById);

		String sqlFindQuoteById = "SELECT * from Quotes WHERE id = ?";
		stmtFindQuoteById = con.prepareStatement(sqlFindQuoteById);

		String sqlIsliked = "SELECT * FROM FavoriteQuotes WHERE user_id = ? AND quote_id = ?";
		stmtIsLiked = con.prepareStatement(sqlIsliked);

		String sqlAddLiked = "INSERT INTO FavoriteQuotes (user_id, quote_id) VALUES (?, ?)";
		stmtAddLiked = con.prepareStatement(sqlAddLiked);

		String sqlDelLiked = "DELETE FROM FavoriteQuotes WHERE user_id = ? AND quote_id = ?";
		stmtDelLiked = con.prepareStatement(sqlDelLiked);

		String sqlGetLikeCount = "SELECT COUNT(*) FROM FavoriteQuotes WHERE quote_id = ?";
		stmtGetLikedCount = con.prepareStatement(sqlGetLikeCount);

	}

	public int save(Quotes q) throws Exception {
		stmtSave.setString(1, q.getText());
		stmtSave.setString(2, q.getAuthor());
		stmtSave.setInt(3, q.getUserId());
		int count = stmtSave.executeUpdate();
		return count;
	}

	public int update(Quotes q) throws Exception {
		stmtUpdate.setString(1, q.getText());
		stmtUpdate.setString(2, q.getAuthor());
		stmtUpdate.setInt(3, q.getId());
		int count = stmtUpdate.executeUpdate();
		return count;
	}

	public int delete(int id) throws Exception {
		stmtDelete.setInt(1, id);
		int count = stmtDelete.executeUpdate();
		return count;
	}

	public List<Quotes> findAll() throws Exception {
		List<Quotes> list = new ArrayList<Quotes>();
		try (ResultSet rs = stmtFindAll.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt("id");
				String text = rs.getString("text");
				String author = rs.getString("author");
				int userId = rs.getInt("user_id");
				int likeCount = getLikeCount(id);
				Quotes q = new Quotes(id, text, author, userId, likeCount);
				list.add(q);
			}
		} // rs.close()
		return list;
	}

	private int getLikeCount(int quoteId) throws Exception {
		stmtGetLikedCount.setInt(1, quoteId);
		try (ResultSet rs = stmtGetLikedCount.executeQuery()) {
			if (rs.next()) {
				return rs.getInt(1);
			}
		}

		return 0;
	}

	public List<Quotes> findQuotesById(int uid) throws Exception {
		List<Quotes> list = new ArrayList<Quotes>();
		stmtFindQuotesById.setInt(1, uid);
		try (ResultSet rs = stmtFindQuotesById.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt("id");
				String text = rs.getString("text");
				String author = rs.getString("author");
				int userId = rs.getInt("user_id");
				int likeCount = getLikeCount(id);
				Quotes q = new Quotes(id, text, author, userId, likeCount);
				list.add(q);
			}
		} // rs.close()
		return list;
	}

	public List<Quotes> findFavQuotesById(int uid) throws Exception {
		List<Quotes> list = new ArrayList<Quotes>();
		stmtFindFavQuotesById.setInt(1, uid);
		try (ResultSet rs = stmtFindFavQuotesById.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt("id");
				String text = rs.getString("text");
				String author = rs.getString("author");
				int userId = rs.getInt("user_id");
				int likeCount = getLikeCount(id);
				if (likeCount > 0) {
					Quotes q = new Quotes(id, text, author, userId, likeCount);
					list.add(q);
				}
			}
		} // rs.close()
		return list;
	}

	public Quotes findQuoteById(int uid) throws Exception {
		stmtFindQuoteById.setInt(1, uid);
		try (ResultSet rs = stmtFindQuoteById.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt("id");
				String text = rs.getString("text");
				String author = rs.getString("author");
				int userId = rs.getInt("user_id");
				Quotes q = new Quotes(id, text, author, userId);
				return q;
			}
		} // rs.close()
		return null;
	}

	public boolean isQuoteLikedByUser(FavoriteQuotes favorite) throws Exception {

		stmtIsLiked.setInt(1, favorite.getUserId());
		stmtIsLiked.setInt(2, favorite.getQuoteId());
		ResultSet resultSet = stmtIsLiked.executeQuery();
		return resultSet.next();

	}

	public void addFavorite(FavoriteQuotes favorite) throws Exception {

		stmtAddLiked.setInt(1, favorite.getUserId());
		stmtAddLiked.setInt(2, favorite.getQuoteId());
		stmtAddLiked.executeUpdate();

	}

	public void removeFavorite(FavoriteQuotes favorite) throws Exception {

		stmtDelLiked.setInt(1, favorite.getUserId());
		stmtDelLiked.setInt(2, favorite.getQuoteId());
		stmtDelLiked.executeUpdate();

	}

}
