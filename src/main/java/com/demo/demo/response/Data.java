package com.demo.demo.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class Data<T> implements Serializable {

    protected T data;
    protected long totalCount;
    protected boolean isSuccess;
    protected ApplicationError error;

    public Data(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Data(T data) {
        this.data = data;
        this.isSuccess = true;
    }

    public Data(ApplicationError error) {
        this.error = error;
        this.isSuccess = false;
    }

    public Data(T data, long totalCount) {
        this.data = data;
        this.isSuccess = true;
        this.totalCount = totalCount;
    }
}