package com.isaqurbanov.file_storage_service.holder;

import com.isaqurbanov.file_storage_service.model.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
@Getter
@Setter
public class CurrentUserHolder {

    private User user;

    public boolean isInitialized() {
        return user != null;
    }
}