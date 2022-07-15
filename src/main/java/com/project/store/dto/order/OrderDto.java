package com.project.store.dto.order;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class OrderDto {


    Integer id;

    @NotNull(message = "date.time.cannot.be.null")
    LocalDateTime dateTime;

    @NotNull(message = "customer.id.cannot.be.null")
    Integer customerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
