package com.shop.core.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class GuestToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guest_id", nullable = false, unique = true)
    private String guestId;

    @Column(name = "guest_token", nullable = false)
    private String guestToken;

    @Column(name = "client_ip")
    private String clientIp;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "expire_date", nullable = false)
    private LocalDateTime expireDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}