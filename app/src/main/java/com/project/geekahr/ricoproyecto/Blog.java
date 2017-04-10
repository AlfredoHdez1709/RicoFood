package com.project.geekahr.ricoproyecto;

/**
 * Created by alfredohernandezrodriguez on 02/12/16.
 */

public class Blog {
    private String title;
    private String desc;
    private String image;
    private String direccion;
    private String phone;
    private String horario;
    private String menu;
    private String latitud;



    public Blog(){

    }

    public Blog(String title, String desc, String image, String direccion, String phone, String horario, String menu, String latitud) {
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.direccion = direccion;
        this.phone = phone;
        this.horario= horario;
        this.menu = menu;
        this.latitud = latitud;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
