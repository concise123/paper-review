package com.review.paper_review.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPK implements Serializable {

    private String email;
    private String registrationId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPK userPK = (UserPK) o;
        return Objects.equals(email, userPK.email) &&
                Objects.equals(registrationId, userPK.registrationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, registrationId);
    }

}
