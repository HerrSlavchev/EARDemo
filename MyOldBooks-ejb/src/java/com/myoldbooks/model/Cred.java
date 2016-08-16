/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myoldbooks.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author SRVR1
 */
@Entity
@Table(name = "cred")
public class Cred implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_user")
    private Integer idUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pass")
    private String pass;
    @Basic(optional = false)
    @NotNull
    @Column(name = "iter")
    private int iter;
    @Basic(optional = false)
    @NotNull
    @Column(name = "salt")
    private String salt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "len")
    private int len;
    
    @MapsId
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @OneToOne(optional = false)
    private User user;

    public Cred() {
    }

    public Cred(Integer idUser) {
        this.idUser = idUser;
    }

    public Cred(Integer idUser, String pass, int iter, String salt, int len) {
        this.idUser = idUser;
        this.pass = pass;
        this.iter = iter;
        this.salt = salt;
        this.len = len;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getIter() {
        return iter;
    }

    public void setIter(int iter) {
        this.iter = iter;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cred)) {
            return false;
        }
        Cred other = (Cred) object;
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.myoldbooks.model.Cred[ idUser=" + idUser + " ]";
    }
    
}
