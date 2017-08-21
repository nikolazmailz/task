package ru.task.model;

/**
 * Created by nikk on 06.07.2017.
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "divisions")
@XmlAccessorType(XmlAccessType.FIELD)
public class Divisions {

    @XmlElement(name = "division")
    private List<Division> divisions = null;

    public List<Division> getDivisions() {
        return divisions;
    }

    public void setDivisions(List<Division> divisions) {
        this.divisions = divisions;
    }

}
