package server.nanuer_server.domain.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
public enum UserRole {
    ROLE_USER,
    ROLE_ADMIN

}