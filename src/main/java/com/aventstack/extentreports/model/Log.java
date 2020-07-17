package com.aventstack.extentreports.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Log implements RunResult, Serializable, BaseEntity {
    private static final long serialVersionUID = -3690764012141784427L;

    @Builder.Default
    private Date timestamp = Calendar.getInstance().getTime();
    @Builder.Default
    private Status status = Status.PASS;
    private String details;
    @Builder.Default
    private Integer seq = -1;
    private final Map<String, Object> infoMap = new HashMap<>();
    private Media media;
    private ExceptionInfo exception;

    public final boolean hasException() {
        return exception != null;
    }

    public final void addMedia(Media media) {
        if (media != null && ((media.getPath() != null || media.getResolvedPath() != null)
                || media instanceof ScreenCapture && ((ScreenCapture) media).getBase64() != null))
            this.media = media;
    }

    public final boolean hasMedia() {
        return media != null;
    }
}
