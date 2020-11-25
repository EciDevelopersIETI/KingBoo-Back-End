package edu.eci.ieti.entities;


import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "provider")
public class Provider {

    private String providerName;
    private long nit;
    private String address;
    private String[] services;
    private String description;
    private int capacity;
    private String provImgUrl;
    public Provider(String providerName, long nit, String address, String[] services, String description, int capacity, String provImgUrl) {
        this.providerName = providerName;
        this.nit = nit;
        this.address = address;
        this.services = services;
        this.description = description;
        this.capacity = capacity;
        this.provImgUrl = provImgUrl;
    }

    public  Provider(){ }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public long getNit() {
        return nit;
    }

    public void setNit(long nit) {
        this.nit = nit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String[] getServices() {
        return services;
    }

    public void setServices(String[] services) {
        this.services = services;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() { return capacity; }

    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getProvImgUrl() {
        return provImgUrl;
    }

    public void setProvImgUrl(String provImgUrl) {
        this.provImgUrl = provImgUrl;
    }

    /**
    @Override
    public String toString(){
        return "Provider{" +
                ", providerName='" + providerName + '\'' +
                ", nit='" + String.valueOf(nit)  + '\'' +
                ", address='" + address + '\'' +
                ", services='" + StringUtils.join(services,',') + '\'' +
                ", description='" + description + '\'' +
                '}';
    }*/

}
