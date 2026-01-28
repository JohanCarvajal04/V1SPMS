package com.uteq.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "configurations")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfigurationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idconfiguration")
    private Integer id;

    @Lob
    @Column(name = "profilepicture", columnDefinition = "bytea")
    private byte[] profilePicture;

    @Lob
    @Column(name = "scannedsignature")
    private byte[] scannedSignature;

    @Column(name = "smschannel", nullable = false)
    private Boolean smsChannel;

    @Column(name = "mailchannel", nullable = false)
    private Boolean mailChannel;

    @Column(name = "whatsappchannel", nullable = false)
    private Boolean whatsappChannel;
}
