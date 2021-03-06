package com.skilldistillery.learning.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	@NotBlank
	@Column(name="first_name")
	private String firstName;

	@NotBlank
	@Column(name="last_name")
	private String lastName;
	
	@AssertTrue 
	private Boolean enabled;
	
	private String role;
	
	@NotBlank
	@Size(min=10, max=400, message="Bio must be between 10 and 400 characters")
	private String bio;
	
	private String image;
	
	@OneToMany(mappedBy = "user")
	private List<Comment> comments;
	
	@ManyToMany
	@JoinTable(name="user_has_roadmap",
    joinColumns=@JoinColumn(name="user_id"),
    inverseJoinColumns=@JoinColumn(name="roadmap_id"))
	private List<Roadmap> roadmaps;
	
	@OneToMany(mappedBy = "user")
	private List<UserRoadmapTask> userRoadmapTasks;
	
	@OneToMany(mappedBy = "user")
	private List<Post> posts;
	
	@OneToMany(mappedBy = "user")
	private List<PostVote> postVotes;
	
//Methods
// Constructor
	public User() {
		super();
	}
	
// Add / Remove roadmap
	
	public void addRoadmap (Roadmap roadmap) {
		if(roadmaps == null) { 
			roadmaps = new ArrayList<>();
		}
		if(!roadmaps.contains(roadmap)) {
			roadmaps.add(roadmap);
			roadmap.addUser(this);
		}
	}
	
	public void removeRoadmap(Roadmap roadmap) {
		if(roadmaps != null && roadmaps.contains(roadmap)) {
			roadmaps.remove(roadmap);
			roadmap.removeUser(this);
		}
	}
	
// Add / Remove Comment
	
	public void addComment(Comment comment) {
		if(comments == null) comments = new ArrayList<>();
		
		if (!comments.contains(comment)) {
			comments.add(comment);
			if (comment.getUser() != null) {
				comment.getUser().getComments().remove(comment);
			}
			comment.setUser(this);
		}
	}
	public void removeComment(Comment comment) {
		comment.setUser(null);
		if (comments != null) {
			comments.remove(comment);
		}
	}
// Add / Remove UserRoadmapTask
	
	public void addUserRoadmapTask(UserRoadmapTask userRoadmapTask) {
		if(userRoadmapTasks == null) userRoadmapTasks = new ArrayList<>();
		
		if (!userRoadmapTasks.contains(userRoadmapTask)) {
			userRoadmapTasks.add(userRoadmapTask);
			if (userRoadmapTask.getUser() != null) {
				userRoadmapTask.getUser().getUserRoadmapTasks().remove(userRoadmapTask);
			}
			userRoadmapTask.setUser(this);
		}
	}
	public void removeUserRoadmapTask(UserRoadmapTask userRoadmapTask) {
		userRoadmapTask.setUser(null);
		if (userRoadmapTasks != null) {
			userRoadmapTasks.remove(userRoadmapTask);
		}
	}
	
// Add / Remove PostVote
	
	public void addPostVote(PostVote postVote) {
		if(postVotes == null) postVotes = new ArrayList<>();
		
		if (!postVotes.contains(postVote)) {
			postVotes.add(postVote);
			if (postVote.getUser() != null) {
				postVote.getUser().getPostVotes().remove(postVote);
			}
			postVote.setUser(this);
		}
	}
	public void removeUserPostVote(PostVote postVote) {
		postVote.setUser(null);
		if (postVotes != null) {
			postVotes.remove(postVote);
		}
	}
// Add / Remove Post
	
	public void addPost(Post post) {
		if(posts == null) posts = new ArrayList<>();
		
		if (!posts.contains(post)) {
			posts.add(post);
			if (post.getUser() != null) {
				post.getUser().getPosts().remove(post);
			}
			post.setUser(this);
		}
	}
	public void removeUserPost(Post post) {
		post.setUser(null);
		if (posts != null) {
			posts.remove(post);
		}
	}
	
// Get / Set 
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Roadmap> getRoadmaps() {
		return roadmaps;
	}

	public void setRoadmaps(List<Roadmap> roadmaps) {
		this.roadmaps = roadmaps;
	}

	public List<UserRoadmapTask> getUserRoadmapTasks() {
		return userRoadmapTasks;
	}

	public void setUserRoadmapTasks(List<UserRoadmapTask> userRoadmapTasks) {
		this.userRoadmapTasks = userRoadmapTasks;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<PostVote> getPostVotes() {
		return postVotes;
	}

	public void setPostVotes(List<PostVote> postVotes) {
		this.postVotes = postVotes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return username + " " + role;
	}
	
	
}
