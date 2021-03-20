package com.skilldistillery.learning.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="post_vote")
public class PostVote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="date_voted")
	private LocalDateTime dateVoted;
	
	private Boolean vote;
	
	// Post id connection 
	
	// user_id connection

	public PostVote() {
		super();
	}

	public PostVote(int id, LocalDateTime dateVoted, Boolean vote) {
		super();
		this.id = id;
		this.dateVoted = dateVoted;
		this.vote = vote;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDateVoted() {
		return dateVoted;
	}

	public void setDateVoted(LocalDateTime dateVoted) {
		this.dateVoted = dateVoted;
	}

	public Boolean getVote() {
		return vote;
	}

	public void setVote(Boolean vote) {
		this.vote = vote;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateVoted == null) ? 0 : dateVoted.hashCode());
		result = prime * result + id;
		result = prime * result + ((vote == null) ? 0 : vote.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PostVote other = (PostVote) obj;
		if (dateVoted == null) {
			if (other.dateVoted != null)
				return false;
		} else if (!dateVoted.equals(other.dateVoted))
			return false;
		if (id != other.id)
			return false;
		if (vote == null) {
			if (other.vote != null)
				return false;
		} else if (!vote.equals(other.vote))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PostVote [id=").append(id).append(", dateVoted=").append(dateVoted).append(", vote=")
				.append(vote).append("]");
		return builder.toString();
	}
}