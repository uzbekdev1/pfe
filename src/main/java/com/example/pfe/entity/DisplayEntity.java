package com.example.pfe.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "displays", indexes = {@Index(columnList = "progName, display", name = "display_idx")})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DisplayEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 50)
    private String progName;

    @Column(length = 50)
    private String display;

    @Column(length = 250)
    private String Error_Name;

    @Column(length = 50)
    private String nomTable;

    @Column(length = 800)
    private String descSol;

    private Double indiceGravity;

    @Column(length = 800)
    private String userSol;

    @Column(length = 250)
    private String FreeColumn1;

    private Integer Rate;

    public DisplayEntity() {
    }

    public DisplayEntity(String progName, String display) {
        this.progName = progName;
        this.display = display;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProgName() {
        return progName;
    }

    public void setProgName(String progName) {
        this.progName = progName;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getError_Name() {
        return Error_Name;
    }

    public void setError_Name(String error_Name) {
        Error_Name = error_Name;
    }

    public String getNomTable() {
        return nomTable;
    }

    public void setNomTable(String nomTable) {
        this.nomTable = nomTable;
    }

    public String getDescSol() {
        return descSol;
    }

    public void setDescSol(String descSol) {
        this.descSol = descSol;
    }

    public Double getIndiceGravity() {
        return indiceGravity;
    }

    public void setIndiceGravity(Double indiceGravity) {
        this.indiceGravity = indiceGravity;
    }

    public String getUserSol() {
        return userSol;
    }

    public void setUserSol(String userSol) {
        this.userSol = userSol;
    }

    public String getFreeColumn1() {
        return FreeColumn1;
    }

    public void setFreeColumn1(String freeColumn1) {
        FreeColumn1 = freeColumn1;
    }

    public Integer getRate() {
        return Rate;
    }

    public void setRate(Integer rate) {
        Rate = rate;
    }
}
