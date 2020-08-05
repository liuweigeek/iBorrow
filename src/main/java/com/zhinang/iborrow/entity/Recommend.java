package com.zhinang.iborrow.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

//用户
/*@Entity
@Table(name = "t_recommend")*/
public class Recommend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //推荐人
    @OneToOne
    private User referee;
    //被推荐人
    @OneToOne
    private User recommended;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getReferee() {
        return referee;
    }

    public void setReferee(User referee) {
        this.referee = referee;
    }

    public User getRecommended() {
        return recommended;
    }

    public void setRecommended(User recommended) {
        this.recommended = recommended;
    }

}