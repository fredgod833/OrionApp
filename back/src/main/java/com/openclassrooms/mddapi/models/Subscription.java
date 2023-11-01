package com.openclassrooms.mddapi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Topic topic;

    @CreationTimestamp
    @Column(name = "subscribed_at", updatable = false)
    private Date subscribedAt;

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void setUser(User user) {
      this.user = user;
    }


    public Date getSubscribedAt() {
        return subscribedAt;
    }
}
