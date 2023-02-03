package it.jobhunt.JobHunt.entity;

import it.jobhunt.JobHunt.enums.CountryCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Address {
    @Column(name = "STREET")
    protected String street;
    @Column(name = "BUILDING_NUMBER")
    protected String buildingNumber;
    @Column(name = "CITY")
    protected String city;
    @Column(name = "ZIP_CODE")
    protected String zipCode;
    @Column(name = "COUNTRY")
    @Enumerated(EnumType.STRING)
    private CountryCode countryCode;
}
