package fr.pantheonsorbonne.miage.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public record Quota( int vendorId, int seated, int standing, int concertId) {
}
