package net.kkennib.house.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse<T> {
    @JsonProperty("isSuccess")
    private boolean isSuccess = false;
    private String message = "";
    private T data;

    public ServiceResponse(boolean isSuccess, T data) {
        this.isSuccess = isSuccess;
        this.data = data;
    }
}
