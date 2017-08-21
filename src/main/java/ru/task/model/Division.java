package ru.task.model;


import javax.xml.bind.annotation.*;

/**
 * Created by nikk on 05.07.2017.
 */
@XmlRootElement(name = "division")
public class Division {

    private int id;
    private String depCode;
    private String depJob;
    private String description;

    public int getId() {
        return id;
    }
    @XmlTransient
    public void setId(int id) {
        this.id = id;
    }

    public String getDepCode(){ return depCode;}
    @XmlElement(name="depCode")
    public void setDepCode(String depCode){ this.depCode = depCode;}

    public String getDepJob(){ return depJob;}
    @XmlElement(name="depJob")
    public void setDepJob(String depJob){ this.depJob = depJob;}

    public String getDescription(){ return description;}
    @XmlElement(name="description")
    public void setDescription(String description){ this.description = description;}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + depCode.hashCode();
        result = prime * result + depJob.hashCode();
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
        Division other = (Division) obj;
        if (!depCode.equals(other.depCode))
            return false;
        if (!depJob.equals(other.depJob))
            return false;
        return true;
    }

}
