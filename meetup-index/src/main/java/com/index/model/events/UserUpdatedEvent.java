package com.index.model.events;

import com.index.enums.SystemEventType;

/**
 * User Created Event.
 *
 * @author David Sapozhnik
 */
public class UserUpdatedEvent extends SystemEvent {
    private String email;
    private String phone;
    private String fullName;

    public UserUpdatedEvent() {
        super(SystemEventType.USER_UPDATED);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
