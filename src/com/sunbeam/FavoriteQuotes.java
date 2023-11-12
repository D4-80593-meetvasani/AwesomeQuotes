package com.sunbeam;

public class FavoriteQuotes {
    private int userId;
    private int quoteId;

    public FavoriteQuotes(int userId, int quoteId) {
        this.userId = userId;
        this.quoteId = quoteId;
    }

    // Getters and setters
    
    @Override
    public String toString() {
        return "FavoriteQuotes [userId=" + userId + ", quoteId=" + quoteId + "]";
    }

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(int quoteId) {
		this.quoteId = quoteId;
	}
}
