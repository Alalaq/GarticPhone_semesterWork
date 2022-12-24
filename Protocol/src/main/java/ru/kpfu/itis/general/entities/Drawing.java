package ru.kpfu.itis.general.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Drawing {
    protected byte[] image;
    protected String authorName;
    protected Long authorId;
}
